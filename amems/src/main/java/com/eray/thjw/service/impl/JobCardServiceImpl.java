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

import com.eray.thjw.dao.BaseWorkOrderMapper;
import com.eray.thjw.dao.JobCardMapper;
import com.eray.thjw.dao.NonWOCardMapper;
import com.eray.thjw.dao.WOAirMaterialMapper;
import com.eray.thjw.dao.WOJobContentMapper;
import com.eray.thjw.dao.WOJobEnclosureMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseWorkOrder;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.NonWOCard;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOAirMaterial;
import com.eray.thjw.po.WOJobContent;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.JobCardService;
import com.eray.thjw.service.JobCardToBookService;
import com.eray.thjw.service.NonWOCardService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.WOAirMaterialService;
import com.eray.thjw.service.WOJobContentService;
import com.eray.thjw.service.WOJobEnclosureService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.ordersource.OrderSourceEnum;
@Service
public class JobCardServiceImpl implements JobCardService{

	@Autowired
	private JobCardMapper jobCardMapper;
	@Autowired
	private BaseWorkOrderMapper baseWorkOrderMapper;
	@Autowired
	private WOAirMaterialService wOAirMaterialService;
	@Autowired
	private WOJobContentService wOJobContentService;
	@Autowired
	private WOJobEnclosureService wOJobEnclosureService;
	@Autowired
	private NonWOCardService nonWOCardService;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private NonWOCardMapper nonWOCardMapper;
	@Autowired
	private WOAirMaterialMapper wOAirMaterialMapper;
	@Autowired
	private WOJobContentMapper wOJobContentMapper;
	@Autowired
	private WOJobEnclosureMapper wOJobEnclosureMapper;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	@Autowired
	private JobCardToBookService jobCardToBookService;
	
	@Override
	public List<JobCard> queryAll(JobCard jobCard) {
		return jobCardMapper.queryAll(jobCard);
	}

	@Override
	public int queryCount(JobCard jobCard) {
		return jobCardMapper.queryCount(jobCard);
	}
	@Override
	public int validationQueryCount(JobCard jobCard) {
		return jobCardMapper.queryCount(jobCard);
	}

	@Override
	public JobCard selectByCode(String gdbh) {
		return jobCardMapper.selectByCode(gdbh);
	}

	//添加工卡
	public String insertJobCard(JobCard jobCard) throws BusinessException {
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(jobCard.getJx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),user.getJgdm(), jxList);
		if(!fola){
			throw new BusinessException("机型权限已变更,请刷新后再进行操作");
		}
		//工单基础id
		String baseWorkOrderUuid = UUID.randomUUID().toString();
		//工卡id
		String jobCardUuid = UUID.randomUUID().toString();
		
		String czls = UUID.randomUUID().toString();  //日志的操作流水
		
		//1.添加工单基础表
		BaseWorkOrder baseWorkOrder=new BaseWorkOrder();
		baseWorkOrder.setId(baseWorkOrderUuid);
		baseWorkOrder.setGdbh(jobCard.getGdbh());
		baseWorkOrder.setZddwid(user.getBmdm());
		baseWorkOrder.setZdrid(user.getId());
		baseWorkOrder.setZdsj(new Date());
		baseWorkOrder.setDprtcode(user.getJgdm());
		baseWorkOrder.setDyzt(0);
		baseWorkOrderMapper.insertSelective(baseWorkOrder);
		if(jobCard.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
			commonRecService.write(baseWorkOrderUuid, TableEnum.B_G_006, user.getId(), czls, LogOperationEnum.SAVE_WO,  UpdateTypeEnum.SAVE,jobCard.getId());
		}else{
			commonRecService.write(baseWorkOrderUuid, TableEnum.B_G_006, user.getId(), czls, LogOperationEnum.SUBMIT_WO,  UpdateTypeEnum.SAVE,jobCard.getId());
		}
		//2.添加工卡
		jobCard.setId(jobCardUuid);
		jobCard.setGdjcid(baseWorkOrderUuid);
		jobCard.setZddwid(user.getBmdm());
		jobCard.setZdrid(user.getId());
		jobCard.setZdsj(new Date());
		jobCard.setDprtcode(user.getJgdm());
		jobCardMapper.insertSelective(jobCard);
		if(jobCard.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
			commonRecService.write(jobCardUuid, TableEnum.B_G_013, user.getId(), czls, LogOperationEnum.SAVE_WO,  UpdateTypeEnum.SAVE,jobCard.getId());
		}else{
			commonRecService.write(jobCardUuid, TableEnum.B_G_013, user.getId(), czls, LogOperationEnum.SUBMIT_WO,  UpdateTypeEnum.SAVE,jobCard.getId());
		}
		
		//1.5添加修订工卡通知书
		if(jobCard.getXdtzsid() != null && jobCard.getXdtzsid().size()>0){
			jobCardToBookService.saveJobCardToBook(jobCardUuid, jobCard.getXdtzsid(), czls, jobCard.getId());
		}
		//3.添加航材
		List<WOAirMaterial> woAirMaterialList=jobCard.getWoAirMaterialList();
		List<WOJobContent> woJobContentList=jobCard.getWoJobContentList();
		List<WOJobEnclosure> woJobenclosureList=jobCard.getWoJobenclosureList();
		List<NonWOCard> nonwocardList=jobCard.getNonwocardList();
		
		WorkOrder workorder=new WorkOrder();
		baseWorkOrder.setWoAirMaterial(woAirMaterialList);
		baseWorkOrder.setWoJobContent(woJobContentList);
		baseWorkOrder.setWoJobenclosure(woJobenclosureList);
		baseWorkOrder.setNonwocardList(nonwocardList);
		workorder.setBaseWorkOrder(baseWorkOrder);
		workorder.setGdjcid(baseWorkOrderUuid);
		workorder.setCzls(czls);
		workorder.setId(jobCard.getId());
		if(jobCard.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()){
			workorder.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		}else{
			workorder.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		}
		wOAirMaterialService.insertSelective(workorder);
		//4.添加工作内容
		wOJobContentService.insertSelective(workorder);
		//5.添加附件上传
		wOJobEnclosureService.insertSelective(workorder);
		//6.添加相关工卡
		nonWOCardService.insertSelective(workorder);
		
		
		return jobCardUuid;
	}
	/**
	 * @author sunji
	 * @description 检查定检工卡修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user, String id) {
		//根据id查询该技术通告
		JobCard jobCard=jobCardMapper.selectByPrimaryKey(id);
		Map<String, Object> returnMap = validatePri(user, jobCard);
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
	private Map<String, Object> validatePri(User user ,JobCard jobCard) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		//验证用户是否存在
		if(null == user){
			returnMap.put("state", "error");
			returnMap.put("message", "登录超时,请先登录!");
			return returnMap;
		}
		
		//验证定检工卡是否存在
		if(null == jobCard){
			returnMap.put("state", "error");
			returnMap.put("message", "定检工卡不存在!");
			return returnMap;
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * @author sunji
	 * @description 根据id查询数据
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public JobCard getByPrimaryKey(String id) {
		return jobCardMapper.selectByPrimaryKey(id);
	}

	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheJobCard(JobCard jobCard) throws BusinessException {
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.EVALUATED.getId()};
		boolean b=this.verification(jobCard.getId(), i);
		if(b){
			throw new BusinessException("该工卡已更新，请刷新后再进行操作");
		}
		
		jobCard.setShsj(new Date());
		jobCard.setShrid(user.getId());
		jobCard.setShbmid(user.getBmdm());
		jobCardMapper.updateByPrimaryKeySelective(jobCard);
		if(jobCard.getZt()==OrderSourceEnum.CHECKED.getId()){
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,jobCard.getId());
		}else if(jobCard.getZt()==OrderSourceEnum.REVIEW_THE_REJECTED.getId()){
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,jobCard.getId());
		}
	}
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuJobCard(JobCard jobCard) throws BusinessException {
		//获取登入人id
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int i[]={OrderSourceEnum.CHECKED.getId()};
		boolean b=this.verification(jobCard.getId(), i);
		if(b){
			throw new BusinessException("该工卡已更新，请刷新后再进行操作");
		}
		jobCard.setPfsj(new Date());
		jobCard.setPfrid(user.getId());
		jobCard.setPfbmid(user.getBmdm());
		jobCardMapper.updateByPrimaryKeySelective(jobCard);
		if(jobCard.getZt()==OrderSourceEnum.APPROVE.getId()){
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,jobCard.getId());
		}else if(jobCard.getZt()==OrderSourceEnum.APPROVE_TURN_.getId()){
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,jobCard.getId());
		}else if(jobCard.getZt()==OrderSourceEnum.SUSPEND.getId()){
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,jobCard.getId());
		}
	}

	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteJobCard(String id)
			throws BusinessException {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();//获取登入人对象
		String czls=UUID.randomUUID().toString();
		
		int []i={OrderSourceEnum.NOT_EVALUATED.getId(),OrderSourceEnum.REVIEW_THE_REJECTED.getId(),OrderSourceEnum.APPROVE_TURN_.getId()};
		boolean b=this.verification(id, i);
		if(b){
			returnMap.put("state", "error");
			returnMap.put("message", "该工卡已更新，请刷新后再进行操作");
			return returnMap;
		}
		
		JobCard sbAn = new JobCard();
		sbAn.setId(id);
		sbAn.setZt(StatusEnum.CANCEL.getId());
		int a=jobCardMapper.updateByPrimaryKeySelective(sbAn);
		if(a>0){
			returnMap.put("state", "success"); 
			returnMap.put("message", "作废成功");
			return returnMap;
		}
		//添加历史数据表
		commonRecService.write(sbAn.getId(), TableEnum.B_G_013, user.getId(), czls, sbAn.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,id);
		return returnMap;
	}

	/**
	 * @author sunji
	 * @description 修改工卡
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void modifyJobCard(JobCard jobCard) throws BusinessException{
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		String mainid=jobCard.getGdjcid();
		String czls=UUID.randomUUID().toString();
		
		int []i={OrderSourceEnum.NOT_EVALUATED.getId(),
				OrderSourceEnum.EVALUATED.getId(),
				OrderSourceEnum.CHECKED.getId(),
				OrderSourceEnum.APPROVE.getId(),
				OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
				OrderSourceEnum.APPROVE_TURN_.getId()};
		boolean b=this.verification(jobCard.getId(),i);
		if(b){
			throw new BusinessException("该工卡已更新，请刷新后再进行操作");
		}
		
		
		//1.修改基础信息
		jobCardMapper.updateByPrimaryKeySelective(jobCard);
		if(jobCard.getZt()==null || jobCard.getZt()==OrderSourceEnum.EVALUATED.getId()){
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.UPDATE,jobCard.getId());
		}else{
			commonRecService.write(jobCard.getId(), TableEnum.B_G_013, user.getId(), czls, jobCard.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,jobCard.getId());
		}
		
		//1.5修改关联修订工卡表
		jobCardToBookService.updateJobCardToBook(jobCard.getId(), jobCard.getXdtzsid(), czls, jobCard.getId());
		
		//2.修改相关工卡
			//（1）获取旧数据id
			List <String> oldXggkIds=jobCard.getOldNonwocardList();
			//（2）获取新数据id
			List <String> newXggkIds=new ArrayList<String>();
			//（3）获取新数据list集合
			List <NonWOCard> nonwocardList=jobCard.getNonwocardList();
			//（4）遍历新数据集合，并把id放入newXggkIds
			for (NonWOCard nonWOCard : nonwocardList) {
				newXggkIds.add(nonWOCard.getId());
				if(nonWOCard.getId().equals("1")){
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					nonWOCard.setId(id);
					nonWOCard.setMainid(mainid);
					nonWOCard.setWhdwid(user.getBmdm()); 
					nonWOCard.setWhrid(user.getId());
					nonWOCard.setZt(1);
					nonWOCard.setWhsj(new Date());
					nonWOCard.setDprtcode(jobCard.getDprtcode());
					nonWOCardMapper.insertSelective(nonWOCard);
					commonRecService.write(id, TableEnum.B_G_00605, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,jobCard.getId());
				}
			}
			//遍历旧数据id
			for (String oldXggkId : oldXggkIds) {
				if(newXggkIds.contains(oldXggkId)){
					/*for (NonWOCard nonWOCard : nonwocardList) {
						if(oldXggkId.equals(nonWOCard.getId())){
							nonWOCardMapper.updateByPrimaryKeySelective(nonWOCard);
							//添加历史数据表
							commonRecService.write(nonWOCard.getId(), TableEnum.B_G_00605, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,jobCard.getId());
						}
					}*/
				}else{
					nonWOCardMapper.deleteByPrimaryKey(oldXggkId);
					//添加历史数据表
					commonRecService.write(oldXggkId, TableEnum.B_G_00901, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,jobCard.getId());
				}
			}
				
			
		//3.修改航材耗材
			//（1）获取旧数据id
			List <String> oldHchcIds=jobCard.getOldWoAirMaterialList();
			//（2）获取新数据id
			List <String> newHchcIds=new ArrayList<String>();
			//（3）获取新数据list集合
			List <WOAirMaterial> wOAirMaterialList=jobCard.getWoAirMaterialList();
			//（4）遍历新数据集合，并把id放入newXggkIds
			for (WOAirMaterial wOAirMaterial : wOAirMaterialList) {
				newHchcIds.add(wOAirMaterial.getId());
				if(wOAirMaterial.getId().equals("1")){
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					wOAirMaterial.setId(id);
					wOAirMaterial.setMainid(mainid);
					wOAirMaterial.setWhdwid(user.getBmdm());
					wOAirMaterial.setWhrid(user.getId());
					wOAirMaterial.setDprtcode(jobCard.getDprtcode());
					wOAirMaterialMapper.insertSelective(wOAirMaterial);
					commonRecService.write(id, TableEnum.B_G_00601, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,jobCard.getId());
				}
			}
			//遍历旧数据id
			for (String oldHchcId : oldHchcIds) {
				if(newHchcIds.contains(oldHchcId)){
					for (WOAirMaterial wOAirMaterial : wOAirMaterialList) {
						if(oldHchcId.equals(wOAirMaterial.getId())){
							wOAirMaterialMapper.updateByPrimaryKeySelective(wOAirMaterial);
							//添加历史数据表
							commonRecService.write(wOAirMaterial.getId(), TableEnum.B_G_00601, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,jobCard.getId());
						}
					}
				}else{
					//添加历史数据表
					commonRecService.write(oldHchcId, TableEnum.B_G_00601, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,jobCard.getId());
					wOAirMaterialMapper.deleteByPrimaryKey(oldHchcId);
				}
			}
		//4.修改工作内容
			//（1）获取旧数据id
			List <String> oldGznrIds=jobCard.getOldWoJobContentList();
			//（2）获取新数据id
			List <String> newGznrIds=new ArrayList<String>();
			//（3）获取新数据list集合
			List <WOJobContent> wOJobContentList=jobCard.getWoJobContentList();
			//（4）遍历新数据集合，并把id放入newXggkIds
			for (WOJobContent wOJobContent : wOJobContentList) {
				newGznrIds.add(wOJobContent.getId());
				if(wOJobContent.getId().equals("1")){
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					wOJobContent.setId(id);
					wOJobContent.setMainid(mainid);
					wOJobContent.setWhdwid(user.getBmdm());
					wOJobContent.setWhrid(user.getId());
					wOJobContent.setDprtcode(jobCard.getDprtcode());
					wOJobContentMapper.insertSelective(wOJobContent);
					commonRecService.write(id, TableEnum.B_G_00602, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,jobCard.getId());
				}
			}
			//遍历旧数据id
			for (String oldGznrId : oldGznrIds) {
				if(newGznrIds.contains(oldGznrId)){
					for (WOJobContent wOJobContent : wOJobContentList) {
						if(oldGznrId.equals(wOJobContent.getId())){
							wOJobContentMapper.updateByPrimaryKeySelective(wOJobContent);
							//添加历史数据表
							commonRecService.write(wOJobContent.getId(), TableEnum.B_G_00602, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,jobCard.getId());
						}
					}
				}else{
					wOJobContentMapper.deleteByPrimaryKey(oldGznrId);
					//添加历史数据表
					commonRecService.write(oldGznrId, TableEnum.B_G_00602, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,jobCard.getId());
				}
			}
		//5.修改附件信息
			//（1）获取旧数据id
			List <String> oldGdfjIds=jobCard.getOldWoJobenclosureList();
			//（2）获取新数据id
			List <String> newGdfjIds=new ArrayList<String>();
			//（3）获取新数据list集合
			List <WOJobEnclosure> woJobenclosureList=jobCard.getWoJobenclosureList();
			//（4）遍历新数据集合，并把id放入newXggkIds
			for (WOJobEnclosure wOJobEnclosure : woJobenclosureList) {
				newGdfjIds.add(wOJobEnclosure.getId());
				if(wOJobEnclosure.getId().equals("1")){
					UUID uuid = UUID.randomUUID();
					String id = uuid.toString();
					wOJobEnclosure.setId(id);
					wOJobEnclosure.setMainid(mainid);
					wOJobEnclosure.setCzdwid(user.getBmdm());
					wOJobEnclosure.setCzrid(user.getId());
					wOJobEnclosure.setDprtcode(jobCard.getDprtcode());
					wOJobEnclosureMapper.insertSelective(wOJobEnclosure);
					commonRecService.write(wOJobEnclosure.getId(), TableEnum.B_G_00603, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,jobCard.getId());
				}
			}
			//遍历旧数据id
			for (String oldGdfjId : oldGdfjIds) {
				if(newGdfjIds.contains(oldGdfjId)){
					/*for (WOJobEnclosure wOJobEnclosure : woJobenclosureList) {
						if(oldGdfjId.equals(wOJobEnclosure.getId())){
							wOJobEnclosureMapper.updateByPrimaryKeySelective(wOJobEnclosure);
							//添加历史数据表
							commonRecService.write(wOJobEnclosure.getId(), TableEnum.B_G_00603, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,jobCard.getId());
						}
					}*/
				}else{
					wOJobEnclosureMapper.deleteByPrimaryKey(oldGdfjId);
					//添加历史数据表
					commonRecService.write(oldGdfjId, TableEnum.B_G_00603, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,jobCard.getId());
				}
			}
		
		
		
	}

	/**
	 * @author sunji
	 * @description  关闭（指定结束）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offJobCard(JobCard jbd)
			throws BusinessException {
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		User user = ThreadVarUtil.getUser();//获取登入人对象
		String czls=UUID.randomUUID().toString();

		int []i={OrderSourceEnum.APPROVE.getId()};
		boolean b=this.verification(jbd.getId(), i);
		if(b){
			returnMap.put("state", "error");
			returnMap.put("message", "该工卡已更新，请刷新后再进行操作");
			return returnMap;
		}

		jbd.setZt(StatusEnum.CLOSE.getId());
		jbd.setZdjsrq(new Date());
		jbd.setZdjsrid(user.getId());
		int a=jobCardMapper.updateByPrimaryKeySelective(jbd);
		if(a>0){
			returnMap.put("state", "success");
			returnMap.put("message", "指定结束成功");
		}
		//添加历史数据表
		commonRecService.write(jbd.getId(), TableEnum.B_G_013, user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,jbd.getId());
	return returnMap;
	}
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchAudit(List<String> idList,String yj) throws RuntimeException{
		
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<JobCard> fItemList = jobCardMapper.queryByIdList(idList);
		for (JobCard item : fItemList) {
			if(1 == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("工卡【").append(item.getGdbh()).append("】审核通过!<br>");
			}else{
				buffer.append("工卡【").append(item.getGdbh()).append("】数据已更新,未能审核成功!<br>");
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
			jobCardMapper.updateBatchAudit(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_013, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,null);//插入日志
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
	public String updateBatchApprove(List<String> idList,String yj) throws RuntimeException{
		
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<JobCard> fItemList = jobCardMapper.queryByIdList(idList);
		for (JobCard item : fItemList) {
			if(OrderSourceEnum.CHECKED.getId() == item.getZt()){
				auditIdList.add(item.getId());
				buffer.append("工卡【").append(item.getGdbh()).append("】批准通过!<br>");
			}else{
				buffer.append("工卡【").append(item.getGdbh()).append("】数据已更新,未能批准成功!<br>");
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
			jobCardMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_013, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,null);//插入日志
		}
		return buffer.toString();
	}

	@Override
	public JobCard getCopyJobCard(JobCard record) {
		JobCard jobcard = getByPrimaryKey(record.getId()) ;      
		if(jobcard!=null){
			WOAirMaterial wam=new WOAirMaterial();                                                                                      //工单的航材耗材
			wam.setMainid(jobcard.getGdjcid());
			List <WOAirMaterial> woAirMaterial=wOAirMaterialService.selectWOAirMaterialList(wam);
			
			WOJobContent wjc=new WOJobContent();                                                                                          //工单的工作内容
	        wjc.setMainid(jobcard.getGdjcid());
			List <WOJobContent> wOJobContent=wOJobContentService.selectByWOJobContentList(wjc);
			
			jobcard.setWoJobContentList(wOJobContent);
			jobcard.setWoAirMaterialList(woAirMaterial);
	    }
		return jobcard;
	}

	@Override
	public JobCard selectBydjgznrid(String id) {
		return jobCardMapper.selectBydjgznrid(id);
	}
	private boolean verification(String id, int [] i){
		User user = ThreadVarUtil.getUser();
		boolean b=true;
		JobCard jobCard=jobCardMapper.selectByPrimaryKey(id);
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(jobCard.getJx());
		boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),jobCard.getDprtcode(), jxList);
		if(!fola){
			b=false;
		}
		for (int a = 0; a < i.length; a++) {
			
			if(jobCard.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}

    /**
     * @author sunji
     * @description 查询列表
     * @param JobCard
     */
	public Map<String , Object> queryAllJobCard(JobCard jobCard) throws BusinessException {
		String id=jobCard.getId();
		jobCard.setId("");
		//获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		try {
			PageHelper.startPage(jobCard.getPagination());
			List<JobCard> list=jobCardMapper.queryAll(jobCard);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						JobCard newRecord = new JobCard();
						newRecord.setId(id);
						newRecord.getParamsMap().put("userId",user.getId());
						List<JobCard> newRecordList = jobCardMapper.queryAll(jobCard);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}

					}
				}
				return PageUtil.pack4PageHelper(list, jobCard.getPagination());
				
			}else{
				List<JobCard> newRecordList = new ArrayList<JobCard>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					JobCard newRecord = new JobCard();
					newRecord.setId(id);
					newRecord.getParamsMap().put("userId", user.getId());
					newRecordList =jobCardMapper.queryAll(jobCard);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, jobCard.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, jobCard.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询失败!");
		}
	}

	 /**
     * @author sunji
     * @description 根据组织机构查询所有
     * @param JobCard
     */
	public List<JobCard> findByDprtcode(String dprtcode)
			throws BusinessException {
		return jobCardMapper.findByDprtcode(dprtcode);
	}
	
	/**
 	 * @author liub
 	 * @description 根据飞机机型查询工卡(弹窗)
 	 * @param jobCard
 	 * @return List<JobCard> 
 	 */
	public List<JobCard> queryWinByFjjx(JobCard jobCard)throws BusinessException {
		return jobCardMapper.queryWinByFjjx(jobCard);
	}
}
