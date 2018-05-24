	package com.eray.thjw.quality.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.quality.dao.AnnualPlanMapper;
import com.eray.thjw.quality.po.AnnualPlan;
import com.eray.thjw.quality.service.AnnualPlanService;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.produce.NodeEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
import enu.quality.AnnualPlanStatusEnum;
/**
 * 
 * @Description 年度计划serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("annualPlanService")
public class AnnualPlanServiceImpl implements AnnualPlanService  {

	@Resource
	private AnnualPlanMapper annualPlanMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
    private AttachmentMapper attachmentMapper;
	@Resource
	private ProcessRecordService processRecordService;
	
	@Resource
	private CommonService commonService;
	@Resource
	private TodoService todoService;

	@Override
	public Map<String, Object> queryAllPageList(AnnualPlan annualPlan)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @Description 保存年度计划
	 * @CreateTime 2018年1月8日 上午10:06:42
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(AnnualPlan annualPlan) throws BusinessException {
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//新增年度计划 
		insertSelective(annualPlan,uuid,user);
		
		//新增附件
		attachmentService.eidtAttachment(annualPlan.getAttachmentList(), uuid, null, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		return uuid;
	}

	/**
	 * 
	 * @Description 新增数据
	 * @CreateTime 2018年1月8日 上午10:07:01
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @param uuid
	 * @param user
	 */
	private void insertSelective(AnnualPlan annualPlan, String uuid, User user) {
		Date currentDate = commonService.getSysdate();//当前时间
		
		annualPlan.setId(uuid);							//id
		annualPlan.setDprtcode(user.getJgdm());			//组织机构
		annualPlan.setWhbmid(user.getBmdm());	        //部门id
		annualPlan.setWhrid(user.getId());		        //用户id
		annualPlan.setWhsj(currentDate);				//当前时间
		annualPlan.setZt(AnnualPlanStatusEnum.SAVE.getId());//状态为保存
		annualPlanMapper.insertSelective(annualPlan);
	}
	
	/**
	 * @Description 升级版本
	 * @CreateTime 2018-1-19 上午11:00:21
	 * @CreateBy 刘兵
	 * @param annualPlan
	 * @return String新版本的年度计划id
	 * @throws BusinessException
	 */
	@Override
	public String doModify(AnnualPlan annualPlan)throws BusinessException{
		String oldId = annualPlan.getId();
		Date currentDate = commonService.getSysdate();//当前时间
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		annualPlan.setId(uuid);							//id
		annualPlan.setWhbmid(user.getBmdm());	        //部门id
		annualPlan.setWhrid(user.getId());		        //用户id
		annualPlan.setWhsj(currentDate);				//当前时间
		annualPlan.setZt(AnnualPlanStatusEnum.SAVE.getId());//状态为保存
		annualPlan.setBbh(annualPlan.getBbh() + 1);
		//新增新版本数据
		annualPlanMapper.insertSelective(annualPlan);
		//copy附件
		attachmentMapper.insert4Copy(uuid, oldId);
		return uuid;
	}

	/**
	 * 
	 * @Description 修改年度计划信息
	 * @CreateTime 2018年1月8日 上午11:06:12
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(AnnualPlan annualPlan) throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//修改年度计划
		updateByPrimaryKeySelective(annualPlan,user);
		
		//修改附件
		attachmentService.eidtAttachment(annualPlan.getAttachmentList(), annualPlan.getId(), null, annualPlan.getId(), annualPlan.getDprtcode(), LogOperationEnum.EDIT);
		
		return annualPlan.getId();
	}
	
	/**
	 * 
	 * @Description 修改数据
	 * @CreateTime 2018年1月8日 上午11:09:58
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @param user
	 */
	private void updateByPrimaryKeySelective(AnnualPlan annualPlan, User user) {
		Date currentDate = commonService.getSysdate();//当前时间
		
		annualPlan.setWhbmid(user.getBmdm()); 	//维护部门id
		annualPlan.setWhrid(user.getId());   	//维护人
		annualPlan.setWhsj(currentDate);		//当前时间
		annualPlanMapper.updateByPrimaryKeySelective(annualPlan);
	}
	
	/**
	 * @Description 提交年度计划信息
	 * @CreateTime 2018-1-19 上午11:00:21
	 * @CreateBy 刘兵
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doSubmit(AnnualPlan annualPlan) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		User user = ThreadVarUtil.getUser();		//当前登陆人
		AnnualPlan oldObj = annualPlanMapper.selectByPrimaryKey(annualPlan.getId());
		if(null == oldObj){
			throw new BusinessException("年度计划不存在，请新增年度计划!");
		}
		if(!(AnnualPlanStatusEnum.SAVE.getId() == oldObj.getZt()
				|| AnnualPlanStatusEnum.AUDITDOWN.getId() == oldObj.getZt()
				|| AnnualPlanStatusEnum.APPROVALDOWN.getId() == oldObj.getZt()
				)){
			throw new BusinessException("只有保存、驳回的年度计划才能提交!");
		}
		annualPlan.setZt(AnnualPlanStatusEnum.ASSESSMENT.getId());
		//修改年度计划
		updateByPrimaryKeySelective(annualPlan,user);
		//
		processRecordService.addRecord(annualPlan.getId(), annualPlan.getDprtcode(), "提交");
		
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(annualPlan.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(1);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append("请审核");
		strSm.append(oldObj.getNf());
		strSm.append("年R");
		strSm.append(oldObj.getBbh());
		strSm.append("年度计划。");
		//新增待办
		todoService.insertSelectiveTechnical(oldObj,strSm.toString(),"quality:annualplan:audit",NodeEnum.NODE2.getId(),null,TodoEnum.ZLNSJH.getId());
		
		return annualPlan.getId();
	}

	@Override
	public void delete(AnnualPlan annualPlan) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	/**
	 * @Description  根据年度计划id查询年度计划
	 * @CreateTime 2018-4-27 上午11:07:48
	 * @CreateBy 刘兵
	 * @param id
	 * @return
	 */
	@Override
	public AnnualPlan getInfoById(String id){
		return annualPlanMapper.selectByPrimaryKey(id);
	}


	/**
	 * 
	 * @Description 获取最新版本相关数据
	 * @CreateTime 2018年1月4日 下午2:30:24
	 * @CreateBy 林龙
	 * @param nf
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<AnnualPlan> getLatestVersionList(String nf, String dprtcode) throws BusinessException {
		return annualPlanMapper.getLatestVersionList(nf,dprtcode);
	}
	
	/**
	 * 
	 * @Description 获取最新版本相关数据
	 * @CreateTime 2018年1月4日 下午2:30:24
	 * @CreateBy 林龙
	 * @param nf
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public AnnualPlan getLatestVersion(String nf, String dprtcode,String bbh) throws BusinessException {
		return annualPlanMapper.getLatestVersion(nf,dprtcode,bbh);
	}

	/**
	 * @Description 查询变更记录
	 * @CreateTime 2018年1月11日 下午3:08:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<AnnualPlan> queryChangeRecord(AnnualPlan record) {
		return annualPlanMapper.queryChangeRecord(record);
	}

	/**
	 * @Description 根据状态，组织机构获取数据
	 * @CreateTime 2018年1月11日 下午3:08:52
	 * @CreateBy sunji
	 * @param record
	 * @return
	 */
	@Override
	public List<AnnualPlan> queryAllByZt(AnnualPlan record) {
		return annualPlanMapper.queryAllByZt(record);
	}

	/**
	 * 
	 * @Description 批准驳回
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateApprovalAndAudit(AnnualPlan annualPlan,Integer zt,Integer yzzt) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		User user=ThreadVarUtil.getUser();					//当前登陆人
		AnnualPlan oldObj = annualPlanMapper.selectByPrimaryKey(annualPlan.getId());
		if(yzzt != oldObj.getZt()){
			throw new BusinessException("数据已更新，请刷新后再进行操作");
		}
		//操作年度计划表
		annualPlan.setZt(zt);
		annualPlan.setPfsj(new Date());
		annualPlanMapper.updateByPrimaryKeySelective(annualPlan);
		//添加流程信息
		if(annualPlan.getShyj() != null){
			processRecordService.addRecord(annualPlan.getId(), annualPlan.getDprtcode(), AnnualPlanStatusEnum.getName(zt)+" "+annualPlan.getShyj());		
		}else{
			processRecordService.addRecord(annualPlan.getId(), annualPlan.getDprtcode(), AnnualPlanStatusEnum.getName(zt)+" "+annualPlan.getPfyj());		
		}
		
		if(zt == AnnualPlanStatusEnum.AUDIT.getId()){//审核
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(annualPlan.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审批");
			strSm.append(oldObj.getNf());
			strSm.append("年R");
			strSm.append(oldObj.getBbh());
			strSm.append("年度计划。");
			//新增待办
			todoService.insertSelectiveTechnical(oldObj,strSm.toString(),"quality:annualplan:approval",NodeEnum.NODE3.getId(),null,TodoEnum.ZLNSJH.getId());
		}else if(zt == AnnualPlanStatusEnum.APPROVAL.getId()){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(annualPlan.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
		}else if(zt == AnnualPlanStatusEnum.AUDITDOWN.getId() || zt == AnnualPlanStatusEnum.APPROVALDOWN.getId()){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(annualPlan.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请修改");
			strSm.append(oldObj.getNf());
			strSm.append("年R");
			strSm.append(oldObj.getBbh());
			strSm.append("年度计划。");
			//新增待办
			todoService.insertSelectiveTechnical(oldObj,strSm.toString(),"quality:annualplan:audit",NodeEnum.NODE1.getId(),oldObj.getWhrid(),TodoEnum.ZLNSJH.getId());
		}
	}

     
}