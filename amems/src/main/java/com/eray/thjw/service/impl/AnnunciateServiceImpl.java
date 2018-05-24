package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.AnnunciateMapper;
import com.eray.thjw.dao.OrderSourceMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.Send;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;
import com.eray.thjw.service.AnnunciateService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.SendService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.ordersource.OrderSourceEnum;
@Service
public class AnnunciateServiceImpl implements AnnunciateService{

	@Autowired
	private AnnunciateMapper annunciateMapper;
	@Autowired
	private SaibongUtilService saibongUtilService;     
	@Autowired
	private OrderSourceService orderSourceService;
	@Autowired
	private SendService sendservice;
	@Autowired
	private TechnicalFileService technicalFileService;
	@Autowired
	private OrderSourceMapper orderSourceMapper;
	@Autowired
	private SendService sendService;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderAttachmentService orderAttachmentService;
	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Annunciate> queryAll(Annunciate annunciate) {
		return annunciateMapper.queryJoinOrderSource(annunciate);
	}
	
	@Override
	public String insertAnnunciate(Annunciate annunciate)throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		UUID uuid = UUID.randomUUID();
		String wxfabh;
		try {
			wxfabh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JSTB.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		annunciate.setId(uuid.toString());
		annunciate.setJstgh(wxfabh);  
		annunciate.setZdbmid(user.getBmdm());
		annunciate.setZdrid(user.getId());//
		annunciate.setZdsj(new Date());
		annunciate.setDprtcode(user.getJgdm());
		if(annunciate.getCkzl()!=null && !"".equals(annunciate.getCkzl())){
			if(annunciate.getCkzl().length()>500){
				annunciate.setCkzl(annunciate.getCkzl().substring(0,500));
			}
		}
		
		annunciateMapper.insertSelective(annunciate);
		if(annunciate.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
			commonRecService.write(uuid.toString(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,annunciate.getId());
		}else{
			commonRecService.write(uuid.toString(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,annunciate.getId());
		}
		//遍历评估单id,并添加指令来源表
		List<OrderSource> orderSourceList= annunciate.getOrderSourceList();
		
		
		for (OrderSource orderSource : orderSourceList) {
			orderSource.setDprtcode(user.getJgdm());
			orderSource.setZlxl(1);
			orderSource.setZlid(uuid.toString());
			orderSource.setZlbh(wxfabh);
			orderSourceService.insert(orderSource);
		}
		//遍历用户id,并添加用户发送表
		List<Send> sendList= annunciate.getSendList();
		for (Send send : sendList) {
			String uuid1=UUID.randomUUID().toString();
			send.setId(uuid1);
			send.setMainid(uuid.toString());
			//send.setJssj(new Date());
			send.setZt(OrderSourceEnum.NOT_EVALUATED.getId());
			send.setDyzt(0);
			send.setJsfs(1);
			send.setDprtcode(user.getJgdm());
			sendservice.insertSelective(send);
			//添加历史数据表
			 commonRecService.write(uuid1, TableEnum.B_G_00201, user.getId(), czls, send.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,annunciate.getId());
		}
		
		//添加附件表
		List<OrderAttachment> orderAttachmentList=annunciate.getOrderAttachmentList();
		for (OrderAttachment orderAttachment : orderAttachmentList) {
			String id = UUID.randomUUID().toString();//获取随机的uuid
			orderAttachment.setId(id);
			orderAttachment.setMainid(uuid.toString());
			orderAttachment.setCzbmid(user.getBmdm());
			orderAttachment.setCzsj(new Date());
			orderAttachment.setCzrid(user.getId());
			orderAttachment.setDprtcode(user.getJgdm());
			orderAttachment.setYxzt(1);
			orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
			orderAttachmentService.insertSelective(orderAttachment);
			//添加历史数据表
			 commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,annunciate.getId());
		}
		
		return uuid.toString();
	}

	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user, String id) {
		//根据id查询该技术通告
		Annunciate annunciate=annunciateMapper.selectByPrimaryKey(id);
		Map<String, Object> returnMap = validatePri(user, annunciate);
		//验证维修方案操作权限
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		return returnMap;
	}
	/**
	 * @author sunji
	 * @description 验证技术通告操作权限
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePri(User user ,Annunciate annunciate) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		//验证用户是否存在
		if(null == user){
			returnMap.put("state", "error");
			returnMap.put("message", "登录超时,请先登录!");
			return returnMap;
		}
		
		//验证技术通告是否存在
		if(null == annunciate){
			returnMap.put("state", "error");
			returnMap.put("message", "维护提示不存在!");
			return returnMap;
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * @author sunji
	 * @description 根据主键查询技术通告信息并判断是否为未提交或者已提交
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public Annunciate getByPrimaryKey(String id) {
		return annunciateMapper.selectByPrimaryKey(id);
	}

	/**
	 * @author sunji
	 * @description 根据主键查询已选择的评估单号
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public List<TechnicalFile> selectChoosePgd(String id) {
		return technicalFileService.selectChoosePgd(id);
	}

	/**
	 * @author sunji
	 * @description 修改提交后的技术通告
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public Map<String, Object> updateAnnunciate(Map<String, Object> map) {
		//遍历评估单id,并添加指令来源表
		List<String> pgdIdAndPgdh= (List<String>) map.get("pgdIdAndPgdh");
		for (String stu : pgdIdAndPgdh) {
			OrderSource orderSource=new OrderSource();
			String pgdid=stu.substring(0,stu.indexOf(","));
			String pgdh=stu.substring(stu.indexOf(",")+1);
			orderSource.setDprtcode((String) map.get("dprtCode"));
			orderSource.setPgdid(pgdid);
			orderSource.setPgdh(pgdh);
			orderSource.setZlxl(1);
			orderSource.setZlid((String)map.get("annunciateId"));
			orderSource.setZlbh((String)map.get("zlbh"));
			orderSourceService.insert(orderSource);
		}
		map.put("state", "success");
		return map; 
	}

	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteAnnunciate(User user, String id)
			throws Exception {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		String czls=UUID.randomUUID().toString();
		int []i={OrderSourceEnum.NOT_EVALUATED.getId(),
				OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
				OrderSourceEnum.APPROVE_TURN_.getId()};
		boolean b=this.verification(id, i);
		if(b){
			returnMap.put("state", "error");
			returnMap.put("message", "该维护提示已更新，请刷新后再进行操作");
			return returnMap;
		}
		Annunciate sbAn = new Annunciate();
		sbAn.setId(id);
		sbAn.setZt(OrderSourceEnum.CANCELLATION.getId());
		int a=annunciateMapper.updateByPrimaryKeySelective(sbAn);
		if(a>0){
			returnMap.put("state", "success");
			returnMap.put("message", "作废成功!");
		}
		//添加历史数据表
		 commonRecService.write(sbAn.getId(), TableEnum.B_G_002, user.getId(), czls, sbAn.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,id);
		return returnMap;
	}

	/**
	 * @author sunji
	 * @description  指定结束（关闭）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offAnnunciate(User user, Annunciate sbAn)
			throws Exception {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
			int []i={OrderSourceEnum.APPROVE.getId()};
			boolean b=this.verification(sbAn.getId(), i);
			if(b){
				returnMap.put("state", "error");
				returnMap.put("message", "该维护提示已更新，请刷新后再进行操作");
				return returnMap;
			}
			String czls=UUID.randomUUID().toString();
			sbAn.setZt(OrderSourceEnum.CLOSE.getId());
			sbAn.setZdjsrq(new Date());
			sbAn.setZdjsrid(user.getId());
			int a=annunciateMapper.updateByPrimaryKeySelective(sbAn);
			if(a>0){
				returnMap.put("state", "success");
				returnMap.put("message", "作废成功!");
			}
			returnMap.put("id", sbAn.getId());
			//添加历史数据表
			 commonRecService.write(sbAn.getId(), TableEnum.B_G_002, user.getId(), czls, sbAn.getLogOperationEnum().GUANBI, UpdateTypeEnum.UPDATE,sbAn.getId());
		return returnMap;
	}
	//修改保存
	@Override
	public void modifyAnnunciate(Annunciate annunciate,int []i)throws BusinessException{
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		boolean b=this.verification(annunciate.getId(), i);
		if(b){
			throw new BusinessException("该维护提示已更新，请刷新后再进行操作");
		}
		
		//1.删除所有关联的评估单
		orderSourceMapper.deleteOrderSourceByZlid(annunciate.getId());
		
		//2.遍历评估单id,并添加指令来源表
		List<OrderSource> orderSourceList= annunciate.getOrderSourceList();
		for (OrderSource orderSource : orderSourceList) {
			
			orderSource.setDprtcode(annunciate.getDprtcode());
			orderSource.setZlxl(1);
			orderSource.setZlid(annunciate.getId());
			orderSource.setZlbh(annunciate.getJstgh());
			orderSourceService.insert(orderSource);
		}
		//3.查询该指令下的发送表，进行比较并进行操作
		List <User> userList=userService.selectUserToSendYes(annunciate.getJstgh(),annunciate.getId());
		
		//
		Send send =new Send();
		send.setMainid(annunciate.getId());
		List<Send> oldSendList=sendService.selectByPrimaryKey(send);
		List<Send> newSendList=annunciate.getSendList();
		//旧数据id
		List<String> oldSendIds=new ArrayList<String>();
		//新数据id
		List<String> newSendIds=new ArrayList<String>();
		
		//遍历旧数据
		for (Send row : oldSendList) {
			oldSendIds.add(row.getJsrid());
		}
		//遍历新数据
		for(Send row :newSendList){
			newSendIds.add(row.getJsrid());
		}
		
		//删除
		for(String oldId : oldSendIds){
			
			if(!newSendIds.contains(oldId)){
				for(Send row:oldSendList){
					if(oldId==row.getJsrid()){
						row.setMainid(annunciate.getId());
						//添加历史数据表
						commonRecService.write(row.getId(), TableEnum.B_G_00201, user.getId(), czls, row.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,annunciate.getId());
						sendService.delete(row);
					}
				}
				
			}
		}
		
		//新增
		for(String newId : newSendIds){
			if(!oldSendIds.contains(newId)){
				for(Send row:newSendList){
					if(newId==row.getJsrid()){
						String id=UUID.randomUUID().toString();
						row.setId(id);
						row.setMainid(annunciate.getId());
						row.setZt(OrderSourceEnum.NOT_EVALUATED.getId());
						row.setDyzt(OrderSourceEnum.NOT_EVALUATED.getId());
						row.setJsfs(1);
						row.setDprtcode(annunciate.getDprtcode());
						sendService.insertSelective(row);
						
					//添加历史数据表
					 commonRecService.write(id, TableEnum.B_G_00201, user.getId(), czls, row.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,annunciate.getId());
					}
				}
			}
		}
	if(annunciate.getOldOrderAttachmentIds()!=null && annunciate.getOrderAttachmentList()!=null){
		//对附件表进行操作
		//（1）获取旧数据id
		List <String> oldGdfjIds=annunciate.getOldOrderAttachmentIds();
		//（2）获取新数据id
		List <String> newGdfjIds=new ArrayList<String>();
		//（3）获取新数据list集合
		List <OrderAttachment> orderAttachmentList=annunciate.getOrderAttachmentList();
		//（4）遍历新数据集合，并把id放入newXggkIds
		for (OrderAttachment orderAttachment : orderAttachmentList) {
			newGdfjIds.add(orderAttachment.getId());
			if(orderAttachment.getId().equals("1")){
				String id = UUID.randomUUID().toString();//获取随机的uuid
				orderAttachment.setId(id);
				orderAttachment.setMainid(annunciate.getId());
				orderAttachment.setCzbmid(user.getBmdm());
				orderAttachment.setCzsj(new Date());
				orderAttachment.setCzrid(user.getId());
				orderAttachment.setDprtcode(annunciate.getDprtcode());
				orderAttachment.setYxzt(OrderSourceEnum.EVALUATED.getId());
				orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
				orderAttachmentService.insertSelective(orderAttachment);
				//添加历史数据表
				commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,annunciate.getId());
			}
		}
		//遍历旧数据id
		for (String oldGdfjId : oldGdfjIds) {
			if(newGdfjIds.contains(oldGdfjId)){
				for (OrderAttachment orderAttachment : orderAttachmentList) {
					if(oldGdfjId.equals(orderAttachment.getId())){
					/*	orderAttachmentService.updateByPrimaryKeySelective(orderAttachment);
						//添加历史数据表
						commonRecService.write(orderAttachment.getId(), TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,annunciate.getId());*/
					}
				}
			}else{
				//添加历史数据表
				commonRecService.write(oldGdfjId, TableEnum.D_011, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,annunciate.getId());
				orderAttachmentService.deleteByPrimaryKey(oldGdfjId);
			}
		}
		
		}
		
		
		
		//5.修改该数据
			//annunciate.setDprtcode(user.getJgdm());
			if(annunciate.getCkzl()!=null && !"".equals(annunciate.getCkzl())){
				if(annunciate.getCkzl().length()>500){
					annunciate.setCkzl(annunciate.getCkzl().substring(0,500));
				}
			}
			annunciateMapper.updateByPrimaryKeySelective(annunciate);
			//添加历史数据表
			if(annunciate.getZt()==OrderSourceEnum.EVALUATED.getId() ){
				commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.UPDATE,annunciate.getId());
			}else {
				commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,annunciate.getId());
			}
			
		//6.批量修改圈阅状态为0;
			Send se=new Send();
			se.setMainid(annunciate.getId());
			se.setZt(OrderSourceEnum.NOT_EVALUATED.getId());
			
			sendService.updateByMainid(se);
	}

	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheAnnunciate(Annunciate annunciate)throws BusinessException{
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.EVALUATED.getId()};
		boolean b=this.verification(annunciate.getId(), i);
		if(b){
			throw new BusinessException("该维护提示已更新，请刷新后再进行操作");
		}
		
		annunciate.setShsj(new Date());
		annunciate.setShrid(user.getId());
		annunciate.setShbmid(user.getBmdm());
		int a=annunciateMapper.updateByPrimaryKeySelective(annunciate);
		if(a<0){
			throw new BusinessException("操作失败");
		}
		//添加历史数据表
		if(annunciate.getZt()==OrderSourceEnum.CHECKED.getId()){
			commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,annunciate.getId());
		}else {
			commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,annunciate.getId());
		}
		
	}
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuAnnunciate(Annunciate annunciate)throws BusinessException{
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.CHECKED.getId()};
		boolean b=this.verification(annunciate.getId(), i);
		if(b){
			throw new BusinessException("该维护提示已更新，请刷新后再进行操作");
		}
		
		annunciate.setPfsj(new Date());
		annunciate.setPfrid(user.getId());
		annunciate.setPfbmid(user.getBmdm());
		int a=annunciateMapper.updateByPrimaryKeySelective(annunciate);
		if(a<0){
			throw new BusinessException("操作失败");
		}
		//添加历史数据表
		if(annunciate.getZt()==OrderSourceEnum.APPROVE.getId()){
			commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,annunciate.getId());
		}else if(annunciate.getZt()==OrderSourceEnum.APPROVE_TURN_.getId()){
			commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,annunciate.getId());
		}else{
			commonRecService.write(annunciate.getId(), TableEnum.B_G_002, user.getId(), czls, annunciate.getLogOperationEnum().SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,annunciate.getId());
		}
	}

	@Override
	public Annunciate selectByNum(String num)
			throws BusinessException {
		return annunciateMapper.selectByNum(num);
	}

	/**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<Annunciate> queryAll(Map<String, Object> map) {
		return annunciateMapper.queryAll(map);
	}

	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchAudit(List<String> idList,String yj){
		
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<Annunciate> fItemList = annunciateMapper.queryByIdList(idList);
		for (Annunciate item : fItemList) {
			if(1 == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("维护提示【").append(item.getJstgh()).append("】审核通过!<br>");
			}else{
				buffer.append("维护提示【").append(item.getJstgh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("zt", OrderSourceEnum.CHECKED.getId());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			annunciateMapper.updateBatchAudit(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_002, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}
	
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchApprove(List<String> idList,String yj){
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<Annunciate> fItemList = annunciateMapper.queryByIdList(idList);
		for (Annunciate item : fItemList) {
			if(OrderSourceEnum.CHECKED.getId() == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("维护提示【").append(item.getJstgh()).append("】批准通过!<br>");
			}else{
				buffer.append("维护提示【").append(item.getJstgh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("zt", OrderSourceEnum.APPROVE.getId());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			int i =annunciateMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_002, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}
	
	/**
	 * @author liub
	 * @description 通过评估单id查询技术通告
	 * @param pgdid
	 * @develop date 2017.03.14
	*/
	@Override
	public List<Annunciate> queryByPgdId(String pgdid){
		List<Annunciate> annunciatelist = annunciateMapper.queryByPgdId(pgdid);
		if(null != annunciatelist && annunciatelist.size() > 0){
			acquirePgjg(annunciatelist, annunciatelist.get(0));
		}
		
		return annunciatelist;
	}

	/*//获取数据库时间
	public String selectDate() {
		return annunciateMapper.selectDate();
	}*/
	public boolean verification(String id, int [] i){
		boolean b=true;
		Annunciate annunciate=annunciateMapper.selectByPrimaryKey(id);
		for (int a = 0; a < i.length; a++) {
			if(annunciate.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}
	/**
	 * sunji
	 * 查询列表
	 * @param annunciate
	 * @return map
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllAnnunciate(Annunciate annunciate)
			throws BusinessException {
	Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=annunciate.getId();
		annunciate.setId("");
		try {
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		PageHelper.startPage(annunciate.getPagination());
		List<Annunciate> list=annunciateMapper.queryJoinOrderSource(annunciate);
		if(((Page)list).getTotal() > 0){
			//获取关联评估单
			acquirePgjg(list,annunciate);
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Annunciate newRecord = new Annunciate();
					newRecord.setId(id);
					List<Annunciate> newRecordList = annunciateMapper.queryJoinOrderSource(newRecord);
					//获取关联评估单
					acquirePgjg(newRecordList,annunciate);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
					//将记录放入结果集第一条
					
				}
			}
			resultMap=PageUtil.pack4PageHelper(list, annunciate.getPagination());
			return resultMap;
			
		}else{
			List<Annunciate> newRecordList = new ArrayList<Annunciate>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Annunciate newRecord = new Annunciate();
				newRecord.setId(id);
				newRecordList = annunciateMapper.queryJoinOrderSource(newRecord);
				//获取关联评估单
				acquirePgjg(newRecordList,annunciate);
				if(newRecordList != null && newRecordList.size() == 1){
					resultMap= PageUtil.pack(1, newRecordList, annunciate.getPagination());
					return resultMap;
				}
				
			}
			resultMap= PageUtil.pack(0, newRecordList, annunciate.getPagination());
			return resultMap;
		}
	} catch (Exception e) {
		throw new BusinessException("查询数据失败",e);
	}
}
	private void acquirePgjg(List<Annunciate> annunciatelist,Annunciate annunciate){
		List<String> zlids=new ArrayList<String>();
		OrderSource orderSource=new OrderSource();
		orderSource.setZlxl(1);
		orderSource.setDprtcode(annunciate.getDprtcode());
		//遍历列表数据，获取id
		for (Annunciate ann : annunciatelist) {
			zlids.add(ann.getId());
		}
		orderSource.setZlids(zlids);
		//查询所有指令来源
		List<OrderSource> orderSourceList= orderSourceService.queryOrderSourceListByZlxl(orderSource);
		Map<String ,StringBuffer > map=new HashMap<String, StringBuffer>();
		for (OrderSource row : orderSourceList) {
			if(map.containsKey(row.getZlid())){
				map.put(row.getZlid(), map.get(row.getZlid()).append(",").append(row.getPgdh()).append("^").append(row.getPgdid()));
			}else{
				StringBuffer str=new StringBuffer();
				map.put(row.getZlid(),str.append(row.getPgdh()).append("^").append(row.getPgdid()));
			}
		}
		//遍历列表数据，拼接评估结果
		for (Annunciate ann : annunciatelist) {
			ann.setPgjg(map.get(ann.getId())==null?"":map.get(ann.getId()).toString());
		}
	}
	
}
