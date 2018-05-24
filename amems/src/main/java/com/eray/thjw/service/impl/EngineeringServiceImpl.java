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
import com.eray.thjw.dao.DetailEngineeringMapper;
import com.eray.thjw.dao.EngineeringMapper;
import com.eray.thjw.dao.OrderSourceMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.DetailEngineering;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.EngineeringService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.JobCardStatusEnum;
import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.ordersource.OrderSourceEnum;

@Service
public class EngineeringServiceImpl implements EngineeringService{
	
	@Autowired
	private EngineeringMapper engineeringMapper;
	@Autowired
	private SaibongUtilService saibongUtilService;     
	@Autowired
	private DetailEngineeringMapper detailEngineeringMapper;
	@Autowired
	private OrderSourceMapper orderSourceMapper;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private TechnicalFileService technicalFileService;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Engineering> queryEngineeringAll(Engineering engineering) {
		return engineeringMapper.queryEngineeringAll(engineering);
	}
	/**
	 * @author sunji
	 * @description 根据查询总条数
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int queryCount(Engineering engineering) {
		return engineeringMapper.queryCount(engineering);
	}
	/**
	 * @author sunji
	 * @description 验证根据查询总条数
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int validationQueryCount(Engineering engineering) {
		return engineeringMapper.validationQueryCount(engineering);
	}
	/**
	 * @author sunji
	 * @description 添加工程指令
	 * @param engineering
	 * @return int
	 * @throws BusinessException 
	 * @develop date 2016.08.15
	 */
	public String saveEngineering(Engineering engineering) throws BusinessException {
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(engineering.getFjjx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),user.getJgdm(), jxList);
		if(!fola){
			throw new BusinessException("机型权限已变更,请刷新后再进行操作");
		}
		//uuid
		String czls=UUID.randomUUID().toString();
		String eingineeringId=(String.valueOf(UUID.randomUUID()));
		
		Engineering engin=new Engineering();
		if(engineering.getGczlbh()==null || "".equals(engineering.getGczlbh())){
			boolean b=true;
			while(b){
				String gczlbh;
				try {
					gczlbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.GCLZ.getName());
				} catch (SaibongException e) {
					throw new RuntimeException(e);
				}
				engineering.setGczlbh(gczlbh);
				engin.setGczlbh(gczlbh);
				engin.setDprtcode(user.getJgdm());
				int i=queryCount(engin);
				if(i<=0){
					b=false;
				}
			}
		}
		engineering.setId(eingineeringId);
		engineering.setZdrid(user.getId());
		engineering.setZdsj(new Date());
		engineering.setDprtcode(user.getJgdm());
		if(engineering.getCkzl()!=null && !"".equals(engineering.getCkzl())){
			if(engineering.getCkzl().length()>500){
				engineering.setCkzl(engineering.getCkzl().substring(0,500));
			}
		}
		//添加工程指令
		engineeringMapper.insertSelective(engineering);
		//添加历史数据表
		if(engineering.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
			commonRecService.write(eingineeringId, TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,engineering.getId());
		}else{
			commonRecService.write(eingineeringId, TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,engineering.getId());
		}
		//添加工作内容明细
		List <DetailEngineering> detailEngineeringList = engineering.getDetailEngineeringList();
		//检查是否有飞机权限存在
		List<String> fjzchList=new ArrayList<String>();
		for (DetailEngineering detailEngineering : detailEngineeringList) {
			if(!"".equals(detailEngineering.getFjzch()) && detailEngineering.getFjzch()!=null){
				if (!fjzchList.contains(detailEngineering.getFjzch())){
					fjzchList.add(detailEngineering.getFjzch());
				} 
			}
		}
		if(fjzchList.size()>0){
			boolean b=planeModelDataService.existsAircraft(user.getId(),user.getUserType(),user.getJgdm(), fjzchList);
			if(!b){
				throw new BusinessException("机型权限已变更,请刷新后再进行操作");
			}
		}
		for (DetailEngineering detailEngineering : detailEngineeringList) {
			detailEngineering.setId(String.valueOf(UUID.randomUUID()));
			detailEngineering.setMainid(engineering.getId());
			detailEngineering.setDprtcode(user.getJgdm());
			detailEngineering.setWhdwid(user.getBmdm());
			detailEngineering.setWhrid(user.getId());
			detailEngineering.setWhsj(new Date());
			detailEngineeringMapper.insertSelective(detailEngineering);
			//添加历史数据表
			commonRecService.write(detailEngineering.getId(), TableEnum.B_G_00901, user.getId(), czls, detailEngineering.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,engineering.getId());
		}
		//添加评估单中间表
		List <OrderSource> orderSourceList=engineering.getOrderSourceList();
		for (OrderSource orderSource : orderSourceList) {
			orderSource.setZlid(eingineeringId); 
			orderSource.setZlbh(engineering.getGczlbh());
			orderSource.setDprtcode(user.getJgdm());
			orderSource.setZlxl(6);
			orderSourceMapper.insert(orderSource);
		}
		return eingineeringId;
	}
	/**
	 * @author sunji
	 * @description 修改工程指令
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void updateEngineering(Engineering engineering, int[] i) throws BusinessException{
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		if(engineering.getCkzl()!=null && !"".equals(engineering.getCkzl())){
			if(engineering.getCkzl().length()>500){
				engineering.setCkzl(engineering.getCkzl().substring(0,500));
			}
		}
		
		boolean b=this.verification(engineering.getId(), i);
		if(b){
			throw new BusinessException("该EO指令已更新，请刷新后再进行操作");
		}
		
		//修改
		engineeringMapper.updateByPrimaryKeySelective(engineering);
		//修改下达指令来源表
		List<OrderSource> orderSourceList=engineering.getOrderSourceList();
		//添加历史数据表
		if(engineering.getZt()==OrderSourceEnum.EVALUATED.getId()){
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.UPDATE,engineering.getId());
		}else{
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,engineering.getId());
		}
		
		//删除原有指令
		orderSourceMapper.deleteOrderSourceByZlid(engineering.getId());
		for (OrderSource orderSource : orderSourceList) {
			orderSource.setDprtcode(engineering.getDprtcode());
			orderSource.setZlbh(engineering.getGczlbh());
			orderSource.setZlid(engineering.getId());
			orderSource.setZlxl(6);
		}
		if(orderSourceList.size()>0){
			orderSourceMapper.insertOrderSource(orderSourceList);
		}
		
		//明细表修改 
		List <DetailEngineering> detailEngineeringList=engineering.getDetailEngineeringList();//新
		//检查是否有飞机权限存在
		List<String> fjzchList=new ArrayList<String>();
		for (DetailEngineering detailEngineering : detailEngineeringList) {
			if(!"".equals(detailEngineering.getFjzch()) && detailEngineering.getFjzch()!=null){
				if (!fjzchList.contains(detailEngineering.getFjzch())){
					fjzchList.add(detailEngineering.getFjzch());
				}
			}
		}
		if(fjzchList.size()>0){
			boolean fola=planeModelDataService.existsAircraft(user.getId(),user.getUserType(),user.getJgdm(), fjzchList);
			if(!fola){
				throw new BusinessException("机型权限已变更,请刷新后再进行操作");
			}
		}
		//旧数据Id
		List <String> olddetailEngineeringListId=engineering.getOlddetailEngineeringListId();//旧
		//新数据Id
		List <String> newdetailEngineeringListId=new ArrayList<String>();
		
		//当id==1时，进行添加
		for (DetailEngineering detailEngineering : detailEngineeringList) {
			newdetailEngineeringListId.add(detailEngineering.getId());
			if(detailEngineering.getId().equals("1")){
				detailEngineering.setId(UUID.randomUUID().toString());
				detailEngineering.setMainid(engineering.getId());
				detailEngineering.setDprtcode(engineering.getDprtcode());
				detailEngineering.setWhdwid(user.getBmdm());
				detailEngineering.setWhrid(user.getId());
				detailEngineering.setWhsj(new Date());
				detailEngineeringMapper.insertSelective(detailEngineering);
				//添加历史数据表
				commonRecService.write(detailEngineering.getId(), TableEnum.B_G_00901, user.getId(), czls, detailEngineering.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,engineering.getId());
			}
		}
		
		
		
		//遍历旧数据id
		for (String olddetailEngineeringId : olddetailEngineeringListId) {
			if(newdetailEngineeringListId.contains(olddetailEngineeringId)){
				for (DetailEngineering detailEngineering : detailEngineeringList) {
					if(olddetailEngineeringId.equals(detailEngineering.getId())){
						detailEngineeringMapper.updateByPrimaryKeySelective(detailEngineering);
						//添加历史数据表
						commonRecService.write(detailEngineering.getId(), TableEnum.B_G_00901, user.getId(), czls, detailEngineering.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,engineering.getId());
					}
				}
			}else{
				//添加历史数据表
				commonRecService.write(olddetailEngineeringId, TableEnum.B_G_00901, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,engineering.getId());
				detailEngineeringMapper.deleteByPrimaryKey(olddetailEngineeringId);
			}
		}
	}
	/**
	 * @author sunji
	 * @description 根据主键id查询对象
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public Engineering getByPrimaryKey(String id) {
		return engineeringMapper.selectByPrimaryKey(id);
	}
	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheEngineering(Engineering engineering)
			throws BusinessException {
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.EVALUATED.getId()};
		boolean b=this.verification(engineering.getId(), i);
		if(b){
			throw new BusinessException("该EO指令已更新，请刷新后再进行操作");
		}
		engineering.setShsj(new Date());
		engineering.setShrid(user.getId());
		engineering.setShbmid(user.getBmdm());
		engineeringMapper.updateByPrimaryKeySelective(engineering);
		if(engineering.getZt()==OrderSourceEnum.CHECKED.getId()){
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,engineering.getId());
		}else if(engineering.getZt()==OrderSourceEnum.REVIEW_THE_REJECTED.getId()){
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,engineering.getId());
		}
		
	}
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuEngineering(Engineering engineering)
			throws BusinessException {
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.CHECKED.getId()};
		boolean b=this.verification(engineering.getId(), i);
		if(b){
			throw new BusinessException("该EO指令已更新，请刷新后再进行操作");
		}
		engineering.setPfsj(new Date());
		engineering.setPfrid(user.getId());
		engineering.setPfbmid(user.getBmdm());
		engineeringMapper.updateByPrimaryKeySelective(engineering);
		if(engineering.getZt()==OrderSourceEnum.APPROVE.getId()){
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,engineering.getId());
		}else if(engineering.getZt()==OrderSourceEnum.APPROVE_TURN_.getId()){
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,engineering.getId());
		}else if(engineering.getZt()==OrderSourceEnum.SUSPEND.getId()){
			commonRecService.write(engineering.getId(), TableEnum.B_G_009, user.getId(), czls, engineering.getLogOperationEnum().SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,engineering.getId());
		}
	}
	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteEngineering(String id)
			throws BusinessException {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();//获取登入人对象
		String czls=UUID.randomUUID().toString();
		
		int []i={OrderSourceEnum.NOT_EVALUATED.getId(),OrderSourceEnum.REVIEW_THE_REJECTED.getId(),OrderSourceEnum.APPROVE_TURN_.getId()};
		boolean b=this.verification(id, i);
		if(b){
			returnMap.put("state", "error");
			returnMap.put("message", "该EO指令已更新，请刷新后再进行操作");
			return returnMap;
		}
		
		//查看该工程指令下是否存在eo工单，如果存在，不能删除
		int count = engineeringMapper.queryCountbyEoId(id);
		if(count>0){
			returnMap.put("state", "error");
			returnMap.put("message", "请先结束该指令下的EO工单，再进行该操作");
			return returnMap;
		}
		Engineering sbAn = new Engineering();
		sbAn.setId(id);
		sbAn.setZt(StatusEnum.CANCEL.getId());
		int a=engineeringMapper.updateByPrimaryKeySelective(sbAn);
		if(a>0){
			returnMap.put("state", "success"); 
			returnMap.put("message", "作废成功");
			//添加历史数据表
			commonRecService.write(sbAn.getId(), TableEnum.B_G_009, user.getId(), czls, sbAn.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,id);
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
	private Map<String, Object> validatePri(User user ,Engineering engineering) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		//验证用户是否存在
		if(null == user){
			returnMap.put("state", "error");
			returnMap.put("message", "登录超时,请先登录!");
			return returnMap;
		}
		
		//验证技术指令是否存在
		if(null == engineering){
			returnMap.put("state", "error");
			returnMap.put("message", "工程指令不存在!");
			return returnMap;
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}
	/**
	 * @author sunji
	 * @description  关闭（指定结束）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offEngineering(Engineering sbAn)
			throws BusinessException {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();//获取登入人对象
		String czls=UUID.randomUUID().toString();

		int []i={OrderSourceEnum.APPROVE.getId()};
		boolean b=this.verification(sbAn.getId(), i);
		if(b){
			returnMap.put("state", "error");
			returnMap.put("text", "该EO指令已更新，请刷新后再进行操作");
			return returnMap;
		}
		
		
		sbAn.setZt(StatusEnum.CLOSE.getId());
		sbAn.setZdjsrq(new Date());
		sbAn.setZdjsrid(user.getId());
		returnMap.put("id", sbAn.getId());
		// 获取对应EO工单集合
		List<Integer> ztList = engineeringMapper.getEOStatusList(sbAn.getId());
		// 工程指令是否完成
		boolean isFinished = true;
		for (Integer zt : ztList) {
			if(zt.intValue() != JobCardStatusEnum.STOPPED.getId().intValue() 
					&& zt.intValue() != JobCardStatusEnum.CANCEL.getId().intValue()
					&& zt.intValue() != JobCardStatusEnum.CLOSE.getId().intValue()
					&& zt.intValue() != JobCardStatusEnum.FINISH.getId().intValue()){
				isFinished = false;
				break;
			}
		}
		
		// EO工单状态全部在（4终止、8作废、9指定结束、10完成），且工程指令的状态为3审核
		if(isFinished){
			engineeringMapper.updateByPrimaryKeySelective(sbAn);
			// 写入日志
			commonRecService.write(sbAn.getId(), TableEnum.B_G_009, ThreadVarUtil.getUser().getId(), czls,
					 sbAn.getLogOperationEnum().GUANBI, UpdateTypeEnum.UPDATE,sbAn.getId());
		}
		
		if(isFinished){
			returnMap.put("state", "success");
			returnMap.put("text", "指定结束成功");
		}else{
			returnMap.put("text", "请先结束该指令下的所有EO工单");
		}
	return returnMap;
	}
	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(String id) {
		//获取登入人对象
		User user = ThreadVarUtil.getUser();
		//根据id查询该技术通告
		Engineering engineering=engineeringMapper.selectByPrimaryKey(id);
		Map<String, Object> returnMap = validatePri(user, engineering);
		//验证维修方案操作权限
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		return returnMap;
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
	@Override
	public List<Engineering> queryEngineeringPageList(Engineering record) {
		return engineeringMapper.queryEngineeringPageList(record);
	}

	@Override
	public void updateEngineeringTj(Engineering engineering) {
		List<OrderSource> orderSourceList=engineering.getOrderSourceList();
		User user = ThreadVarUtil.getUser();//获取登入人对象
		for (OrderSource orderSource : orderSourceList) {
			orderSource.setZlid(engineering.getId()); 
			orderSource.setZlbh(engineering.getGczlbh());
			orderSource.setDprtcode(user.getJgdm());
			orderSource.setZlxl(6);
			orderSourceMapper.insert(orderSource);
		}
		
		
	}
	@Override
	public List<Engineering> queryAll(Map<String, Object> map) {
		return engineeringMapper.queryAll(map);
	}
	@Override
	public String updateBatchReview(List<String> idList,String yj)
			throws BusinessException {
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<Engineering> fItemList = engineeringMapper.queryByIdList(idList);
		for (Engineering item : fItemList) {
			if(1 == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("工程指令【").append(item.getGczlbh()).append("】审核通过!<br>");
			}else{
				buffer.append("工程指令【").append(item.getGczlbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("zt", OrderSourceEnum.CHECKED.getId());
			paramMap.put("shsj", new Date());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			engineeringMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_009, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}
	@Override
	public String updateBatchApprove(List<String> idList,String yj)
			throws BusinessException {
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<Engineering> fItemList = engineeringMapper.queryByIdList(idList);
		for (Engineering item : fItemList) {
			if(OrderSourceEnum.CHECKED.getId() == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("工程指令【").append(item.getGczlbh()).append("】批准通过!<br>");
			}else{
				buffer.append("工程指令【").append(item.getGczlbh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		//获取登入人对象
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("zt", OrderSourceEnum.APPROVE.getId());
			paramMap.put("pfsj", new Date());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			int i =engineeringMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_009, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}
	
	/**
	 * 更新工程指令状态
	 */
	@Override
	public boolean updateStatus(String id, String czls, LogOperationEnum logOperationEnum) {
		// 获取工程指令对象
		Engineering engi = engineeringMapper.selectByPrimaryKey(id);
		// 获取对应EO工单集合
		List<Integer> ztList = engineeringMapper.getEOStatusList(id);
		// 工程指令是否完成
		boolean isFinished = true;
		for (Integer zt : ztList) {
			if(zt.intValue() != JobCardStatusEnum.STOPPED.getId().intValue() 
					&& zt.intValue() != JobCardStatusEnum.CANCEL.getId().intValue()
					&& zt.intValue() != JobCardStatusEnum.CLOSE.getId().intValue()
					&& zt.intValue() != JobCardStatusEnum.FINISH.getId().intValue()){
				isFinished = false;
				break;
			}
		}
		
		// EO工单状态全部在（4终止、8作废、9指定结束、10完成），且工程指令的状态为3审核
		if(isFinished && engi.getZt().intValue() == OrderSourceEnum.APPROVE.getId().intValue()){
			// 更新工程指令状态为10完成
			engi.setZt(OrderSourceEnum.WANCHEN.getId());
			engineeringMapper.updateByPrimaryKeySelective(engi);
			// 写入日志
			commonRecService.write(engi.getId(), TableEnum.B_G_009, ThreadVarUtil.getUser().getId(), czls,
					logOperationEnum, UpdateTypeEnum.UPDATE,engi.getId());
			return true;
		}
		// EO工单状态存在一个及以上不在（4终止、8作废、9指定结束、10完成），且工程指令的状态为10完成
		if(!isFinished && engi.getZt().intValue() == OrderSourceEnum.WANCHEN.getId().intValue()){
			// 更新工程指令状态为3审核
			engi.setZt(JobCardStatusEnum.APPROVED.getId());
			engineeringMapper.updateByPrimaryKeySelective(engi);
			// 写入日志
			commonRecService.write(engi.getId(), TableEnum.B_G_009, ThreadVarUtil.getUser().getId(), czls,
					logOperationEnum, UpdateTypeEnum.UPDATE,engi.getId());
		}
		return false;
	}
	
	/**
	 * 根据EO工单id找到工程指令
	 */
	@Override
	public Engineering findByEOId(String eoId) {
		return engineeringMapper.findByEOId(eoId);
	}
	/**
	 * @author sunji
	 * @description  查看该id下的所有eo工卡的总条数
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public int queryCountbyEoId(String id) throws BusinessException {
		return engineeringMapper.queryCountbyEoId(id);
	}

	/**
	 * @author liub
	 * @description 通过评估单id查询EO指令
	 * @param pgdid
	 * @develop date 2017.03.16
	*/
	@Override
	public List<Engineering> queryByPgdId(String pgdid){
		List<Engineering> engineeringlist = engineeringMapper.queryByPgdId(pgdid);
		if(null != engineeringlist && engineeringlist.size() > 0){
			acquirePgjg(engineeringlist, engineeringlist.get(0));
		}
		return engineeringlist;
	}
	
	public boolean verification(String id, int [] i){
		User user = ThreadVarUtil.getUser();
		boolean b=true;
		Engineering engineering=engineeringMapper.selectByPrimaryKey(id);
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(engineering.getFjjx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),engineering.getDprtcode(), jxList);
		if(!fola){
			b=false;
		}
		for (int a = 0; a < i.length; a++) {
			
			if(engineering.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}
	/**
	 * @author sunji
	 * @description 查询列表
	 * @param engineering
	*/
	public Map<String, Object> queryAllEngineering(Engineering engineering)throws BusinessException {
		String id=engineering.getId();
		engineering.setId("");
		try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(engineering.getPagination());
			List<Engineering> list=engineeringMapper.queryEngineeringAll(engineering);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Engineering newRecord = new Engineering();
						newRecord.setId(id);
						newRecord.getParamsMap().put("userId", user.getId());
						List<Engineering> newRecordList = engineeringMapper.queryEngineeringAll(newRecord);
						//获取关联评估单
						acquirePgjg(newRecordList,engineering);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}

					}
				}
				acquirePgjg(list,engineering);
				return PageUtil.pack4PageHelper(list, engineering.getPagination());
				
			}else{
				List<Engineering> newRecordList = new ArrayList<Engineering>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Engineering newRecord = new Engineering();
					newRecord.setId(id);
					newRecord.getParamsMap().put("userId", user.getId());
					newRecordList = engineeringMapper.queryEngineeringAll(newRecord);
					//获取关联评估单
					acquirePgjg(newRecordList,engineering);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, engineering.getPagination());
					}

				}
				return PageUtil.pack(0, newRecordList, engineering.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	private void acquirePgjg(List<Engineering> engineeringlist,Engineering engineering){
		List<String> zlids=new ArrayList<String>();
		OrderSource orderSource=new OrderSource();
		orderSource.setZlxl(6);
		orderSource.setDprtcode(engineering.getDprtcode());
		//查询所有指令来源
		//遍历列表数据，获取id
		for (Engineering row : engineeringlist) {
			zlids.add(row.getId());
		}
		orderSource.setZlids(zlids);
		List<OrderSource> orderSourceList= orderSourceMapper.queryOrderSourceListByZlxl(orderSource);
		
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
		for (Engineering eng : engineeringlist) {
			eng.setPgjg(map.get(eng.getId())==null?"":map.get(eng.getId()).toString());
		}
	}	
}
