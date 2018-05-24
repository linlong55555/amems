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
import com.eray.thjw.dao.InstructionContentMapper;
import com.eray.thjw.dao.InstructionMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Instruction;
import com.eray.thjw.po.InstructionContent;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.InstructionService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.ordersource.OrderSourceEnum;
@Service
public class InstructionServiceImpl implements InstructionService{

	@Autowired
	private InstructionMapper instructionMapper;
	@Autowired
	private SaibongUtilService saibongUtilService;     
	@Autowired
	private OrderSourceService orderSourceService;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private InstructionContentMapper instructionContentMapper;
	@Autowired
	private OrderAttachmentService orderAttachmentService;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Instruction> queryInstructionAll(Instruction record) {
		return instructionMapper.queryInstructionAll(record);
	}
	/**
	 * @author sunji
	 * @description 保存提交
	 * @param Instruction
	 * @return map
	 * @throws BusinessException 
	 * @develop date 2016.08.15
	 */
	public String insertInstruction(Instruction instruction) throws BusinessException {
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(instruction.getJx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),user.getJgdm(), jxList);
		if(!fola){
			throw new BusinessException("机型权限已变更,请刷新后再进行操作");
		}
		String czls=UUID.randomUUID().toString();
		String instructionId=String.valueOf(UUID.randomUUID());
		try {
			instruction.setJszlh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JSZL.getName()));
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		instruction.setZdrid(user.getId());
		instruction.setId(instructionId);				//主键id
		instruction.setZdsj(new Date());									//制单时间
		instruction.setZdbmid(user.getBmdm());									//制单时间
		instruction.setDprtcode(user.getJgdm());							//组织机构
		instruction.setJszt(0);							//接收状态
		if(instruction.getCkzl()!=null && !"".equals(instruction.getCkzl())){
			if(instruction.getCkzl().length()>500){
				instruction.setCkzl(instruction.getCkzl().substring(0,500));
			}
		}
		instructionMapper.insertSelective(instruction);
		//添加历史数据表
		if(instruction.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,instruction.getId());
		}else{
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,instruction.getId());
		}
		//添加工作内容表
		List<InstructionContent> instructionContentList=instruction.getInstructionContentList();
		for (InstructionContent instructionContent : instructionContentList) {
			instructionContent.setId(UUID.randomUUID().toString());
			instructionContent.setMainid(instruction.getId());
			instructionContent.setCzrid(user.getId()); 
			instructionContent.setCzdwid(user.getBmdm());
			instructionContent.setCzsj(new Date());
			instructionContentMapper.insertSelective(instructionContent);
			commonRecService.write(instructionContent.getId(), TableEnum.B_G_00401, user.getId(), czls, instructionContent.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,instruction.getId());
		}
		
		//2.添加指令来源表信息
		List<OrderSource> orderSourceList=instruction.getOrderSourceList();
		for (OrderSource orderSource : orderSourceList) {
			//创建插入对象
			orderSource.setZlbh(instruction.getJszlh());
			orderSource.setZlid(instruction.getId());
			orderSource.setZlxl(2);
			orderSource.setDprtcode(user.getJgdm());
			orderSourceService.insert(orderSource);
		}
		//3.添加上传附件
		List<OrderAttachment> orderAttachmentList=instruction.getOrderAttachmentList();
		for (OrderAttachment orderAttachment : orderAttachmentList) {
			String id = UUID.randomUUID().toString();//获取随机的uuid
			orderAttachment.setId(id);
			orderAttachment.setMainid(instruction.getId());
			orderAttachment.setCzbmid(user.getBmdm());
			orderAttachment.setCzsj(new Date());
			orderAttachment.setCzrid(user.getId());
			orderAttachment.setDprtcode(user.getJgdm());
			orderAttachment.setYxzt(1);
			orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
			orderAttachmentService.insertSelective(orderAttachment);
			//添加历史数据表
			commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,instruction.getId());
		}
		
		return instructionId;
	}
	/**
	 * @author sunji
	 * @description 检查技术指令修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdIt(User user, String id) {
			//根据id查询该技术通告
			Instruction instruction=instructionMapper.selectByPrimaryKey(id);
			Map<String, Object> returnMap = validatePri(user, instruction);
			//验证维修方案操作权限
			if (!"success".equals(returnMap.get("state"))) {	
				return returnMap;
			}
			return returnMap;
	}
	/**
	 * @author sunji
	 * @description 验证技术指令操作权限
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePri(User user ,Instruction instruction) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		//验证用户是否存在
		if(null == user){
			returnMap.put("state", "error");
			returnMap.put("message", "登录超时,请先登录!");
			return returnMap;
		}
		
		//验证技术指令是否存在
		if(null == instruction){
			returnMap.put("state", "error");
			returnMap.put("message", "技术指令不存在!");
			return returnMap;
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}
	/**
	 * @author sunji
	 * @description 根据主键查询
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public Instruction getByPrimaryKey(String id) {
		return instructionMapper.selectByPrimaryKey(id);
	}
	/**
	 * @author sunji
	 * @description 修改
	 * @param Instruction
	 * @return map
	 */
	public String updateInstruction(Instruction instruction, int[] i) throws BusinessException{
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		instruction.setJszt(0);
		if(instruction.getCkzl()!=null && !"".equals(instruction.getCkzl())){
			if(instruction.getCkzl().length()>500){
				instruction.setCkzl(instruction.getCkzl().substring(0,500));
			}
		}
		boolean b=this.verification(instruction.getId(), i);
		if(b){
			throw new BusinessException("该技术指令已更新，请刷新后再进行操作");
		}
		instructionMapper.updateByPrimaryKeySelective(instruction);
		
		//添加历史数据表
		
		if( instruction.getZt()==OrderSourceEnum.EVALUATED.getId()){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.UPDATE,instruction.getId());
		}else{
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,instruction.getId());
		}
		//2.删除此id关联的所有来源信息
		orderSourceService.deleteOrderSourceByZlid(instruction.getId());
		
		//3.添加指令来源表信息
		List<OrderSource> orderSourceList=instruction.getOrderSourceList();
		for (OrderSource orderSource : orderSourceList) {
			//创建插入对象
			orderSource.setZlbh(instruction.getJszlh());
			orderSource.setZlid(instruction.getId());
			orderSource.setZlxl(2);
			orderSource.setDprtcode(instruction.getDprtcode());
			orderSourceService.insert(orderSource);
		}
		//4.添加工作内容
		List<InstructionContent> instructionContentList=instruction.getInstructionContentList();
		//旧数据id
		List<String> oldInstructionContentIds=instruction.getOldInstructionContentIds();
		//新数据id
		List<String> newInstructionContentIds=new ArrayList<String>();
		
		for (InstructionContent instructionContent : instructionContentList) {
			newInstructionContentIds.add(instructionContent.getId());
			if(instructionContent.getId().equals("1")){
				instructionContent.setId(UUID.randomUUID().toString());
				instructionContent.setMainid(instruction.getId());
				instructionContent.setCzrid(user.getId());
				instructionContent.setCzdwid(instruction.getDprtcode());
				instructionContent.setCzsj(new Date());
				instructionContentMapper.insertSelective(instructionContent);
				commonRecService.write(instructionContent.getId(), TableEnum.B_G_00401, user.getId(), czls, instruction.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,instruction.getId());
			}
		}
		for (String instructionId : oldInstructionContentIds) {
			if(newInstructionContentIds.contains(instructionId)){
				for (InstructionContent instructionContent : instructionContentList) {
					if(instructionContent.getId().equals(instructionId)){
						instructionContentMapper.updateByPrimaryKeySelective(instructionContent);
						commonRecService.write(instructionId, TableEnum.B_G_00401, user.getId(), czls, instruction.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,instruction.getId());
					}
				}
			}else{
				commonRecService.write(instructionId, TableEnum.B_G_00401, user.getId(), czls, instruction.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,instruction.getId());
				instructionContentMapper.deleteByPrimaryKey(instructionId);
			}
			
		}
		
		if(instruction.getOldOrderAttachmentIds()!=null && instruction.getOrderAttachmentList()!=null){
			//对附件表进行操作
			//（1）获取旧数据id
			List <String> oldGdfjIds=instruction.getOldOrderAttachmentIds();
			//（2）获取新数据id
			List <String> newGdfjIds=new ArrayList<String>();
			//（3）获取新数据list集合
			List <OrderAttachment> orderAttachmentList=instruction.getOrderAttachmentList();
			//（4）遍历新数据集合，并把id放入newXggkIds
			for (OrderAttachment orderAttachment : orderAttachmentList) {
				newGdfjIds.add(orderAttachment.getId());
				if(orderAttachment.getId().equals("1")){
					String id = UUID.randomUUID().toString();//获取随机的uuid
					orderAttachment.setId(id);
					orderAttachment.setMainid(instruction.getId());
					orderAttachment.setCzbmid(user.getBmdm());
					orderAttachment.setCzsj(new Date());
					orderAttachment.setCzrid(user.getId());
					orderAttachment.setDprtcode(instruction.getDprtcode());
					orderAttachment.setYxzt(1);
					orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
					orderAttachmentService.insertSelective(orderAttachment);
					//添加历史数据表
					commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,instruction.getId());
				}
			}
			//遍历旧数据id
			for (String oldGdfjId : oldGdfjIds) {
				if(newGdfjIds.contains(oldGdfjId)){
					for (OrderAttachment orderAttachment : orderAttachmentList) {
						if(oldGdfjId.equals(orderAttachment.getId())){
							/*orderAttachmentService.updateByPrimaryKeySelective(orderAttachment);*/
							//添加历史数据表
							/*commonRecService.write(orderAttachment.getId(), TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,instruction.getId());*/
						}
					}
				}else{
					//添加历史数据表
					commonRecService.write(oldGdfjId, TableEnum.D_011, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,instruction.getId());
					orderAttachmentService.deleteByPrimaryKey(oldGdfjId);
				}
			}
			
			}
		
		return instruction.getId();
		
	}
	public Map<String, Object> offInstruction(User user, Instruction sbAn)
			throws Exception {
		String czls=UUID.randomUUID().toString();
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
			int []i={OrderSourceEnum.APPROVE.getId()};
			boolean b=this.verification(sbAn.getId(), i);
			if(b){
				returnMap.put("state", "error");
				returnMap.put("message", "该技术指令已更新，请刷新后再进行操作");
				return returnMap;
			}
			
			sbAn.setZt(StatusEnum.CLOSE.getId());
			sbAn.setZdjsrq(new Date());
			sbAn.setZdjsrid(user.getId());
			int a=instructionMapper.updateByPrimaryKeySelective(sbAn);
			if(a>0){
				returnMap.put("state", "success");
				returnMap.put("message", "作废成功!");
			}
			returnMap.put("id", sbAn.getId());
			//添加历史数据表
			commonRecService.write(sbAn.getId(), TableEnum.B_G_004, user.getId(), czls, sbAn.getLogOperationEnum().GUANBI, UpdateTypeEnum.UPDATE,sbAn.getId());
			/*returnMap = updateMaintenance(sbAn);
			
			//判断修改是否成功
			if (!"success".equals(returnMap.get("state"))) {	
				returnMap.put("message", "作废"+maintenance.getZwms()+"时,操作失败!");
				return returnMap;
			}*/
		/*}*/
		return returnMap;
	}
	@Override
	public Map<String, Object> deleteInstruction(User user, String id)
			throws Exception {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		String czls=UUID.randomUUID().toString();
		int []i={OrderSourceEnum.NOT_EVALUATED.getId(),
				OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
				OrderSourceEnum.APPROVE_TURN_.getId()};
		boolean b=this.verification(id, i);
		if(b){
			returnMap.put("state", "error");
			returnMap.put("message", "该技术指令已更新，请刷新后再进行操作");
			return returnMap;
		}
			Instruction sbAn = new Instruction();
			sbAn.setId(id);
			sbAn.setZt(StatusEnum.CANCEL.getId());
			int a=instructionMapper.updateByPrimaryKeySelective(sbAn);
			if(a>0){
				returnMap.put("state", "success");
				returnMap.put("message", "作废成功");
			}
			//添加历史数据表
			commonRecService.write(sbAn.getId(), TableEnum.B_G_004, user.getId(), czls, sbAn.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.UPDATE,id);
		return returnMap;
	}

	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheInstruction(Instruction instruction)
			throws BusinessException {
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int i[]={OrderSourceEnum.EVALUATED.getId()};
		boolean b=this.verification(instruction.getId(), i);
		if(b){
			throw new BusinessException("该技术指令已更新，请刷新后再进行操作");
		}
		
		instruction.setShsj(new Date());
		instruction.setShrid(user.getId());
		instruction.setShbmid(user.getBmdm());
		
		int a=instructionMapper.updateByPrimaryKeySelective(instruction);
		if(a<0){
			throw new BusinessException("操作失败");
		}
		//添加历史数据表
		if(instruction.getZt()==2){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,instruction.getId());
		}else if(instruction.getZt()==5){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,instruction.getId());
		}
	}
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuInstruction(Instruction instruction)
			throws BusinessException {
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.CHECKED.getId()};
		boolean b=this.verification(instruction.getId(), i);
		if(b){
			throw new BusinessException("该技术指令已更新，请刷新后再进行操作");
		}
		
		instruction.setPfsj(new Date());
		instruction.setPfrid(user.getId());
		instruction.setPfbmid(user.getBmdm());
		instructionMapper.updateByPrimaryKeySelective(instruction);
		//添加历史数据表
		if(instruction.getZt()==3){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,instruction.getId());
		}else if(instruction.getZt()==6){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,instruction.getId());
		}else if(instruction.getZt()==4){
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,instruction.getId());
		}
	}
	/**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<Instruction> queryAll(Map<String,Object> map) {
		return instructionMapper.queryAll(map);
	}
	@Override
	public void updateJszt(Instruction instruction) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int i =instructionMapper.updateByPrimaryKeySelective(instruction);
		//添加历史数据表
		 commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, instruction.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,instruction.getId());
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
		List<Instruction> fItemList = instructionMapper.queryByIdList(idList);
		for (Instruction item : fItemList) {
			if(OrderSourceEnum.EVALUATED.getId() == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("技术指令【").append(item.getJszlh()).append("】审核通过!<br>");
			}else{
				buffer.append("技术指令【").append(item.getJszlh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			paramMap.put("zt", OrderSourceEnum.CHECKED.getId());
			instructionMapper.updateBatchAudit(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_004, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);//插入日志
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
		List<Instruction> fItemList = instructionMapper.queryByIdList(idList);
		for (Instruction item : fItemList) {
			if(2 == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("技术指令【").append(item.getJszlh()).append("】批准通过!<br>");
			}else{
				buffer.append("技术指令【").append(item.getJszlh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			paramMap.put("zt", OrderSourceEnum.APPROVE.getId());
			instructionMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_004, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}
	
	/**
	 * @author liub
	 * @description 通过评估单id查询技术指令
	 * @param pgdid
	 * @develop date 2017.03.15
	*/
	@Override
	public List<Instruction> queryByPgdId(String pgdid){
		List<Instruction> instructionlist = instructionMapper.queryByPgdId(pgdid);
		if(null != instructionlist && instructionlist.size() > 0){
			acquirePgjg(instructionlist, instructionlist.get(0));
		}
		return instructionlist;
	}
	
	public boolean verification(String id, int [] i){
		User user = ThreadVarUtil.getUser();
		boolean b=true;
		Instruction instruction=instructionMapper.selectByPrimaryKey(id);
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(instruction.getJx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),instruction.getDprtcode(), jxList);
		if(!fola){
			b=false;
		}
		for (int a = 0; a < i.length; a++) {
			
			if(instruction.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}
	/**
	 * @author sunji
	 * @description 通过条件查询技术指令
	 * @param instruction
	 * @throws BusinessException 
	*/
	public Map<String, Object> queryAllInstruction(Instruction instruction)throws BusinessException {
		String id=instruction.getId();
		instruction.setId("");
		try {
			//获取登入user
			User user=ThreadVarUtil.getUser();
			instruction.getParamsMap().put("userId", user.getId());
			instruction.getParamsMap().put("userType",user.getUserType());
			PageHelper.startPage(instruction.getPagination());
			List <Instruction> list= instructionMapper.queryInstructionAll(instruction);
			if(((Page)list).getTotal() > 0){
				//获取关联评估单
				acquirePgjg(list,instruction);
				
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Instruction newRecord = new Instruction();
						newRecord.setId(id);
						newRecord.getParamsMap().put("userId", user.getId());
						List<Instruction> newRecordList = instructionMapper.queryInstructionAll(newRecord);
						//获取关联评估单
						acquirePgjg(newRecordList,instruction);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
						
					}
				}
				return PageUtil.pack4PageHelper(list, instruction.getPagination());
				
			}else{
				List<Instruction> newRecordList = new ArrayList<Instruction>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Instruction newRecord = new Instruction();
					newRecord.setId(id);
					newRecord.getParamsMap().put("userId", user.getId());
					newRecordList = instructionMapper.queryInstructionAll(newRecord);
					//获取关联评估单
					acquirePgjg(newRecordList,instruction);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, instruction.getPagination());
					}
					
				}
				return PageUtil.pack(0, newRecordList, instruction.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	public void acquirePgjg(List<Instruction> instructionList,Instruction instruction){
		List<String> zlids=new ArrayList<String>();
		OrderSource orderSource=new OrderSource();
		orderSource.setZlxl(2);
		orderSource.setDprtcode(instruction.getDprtcode());
		//遍历列表数据，获取id
		for (Instruction itt : instructionList) {
			zlids.add(itt.getId());
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
		for (Instruction itt : instructionList) {
			itt.setPgjg(map.get(itt.getId())==null?"":map.get(itt.getId()).toString());
			
		}
}
	
	
}
