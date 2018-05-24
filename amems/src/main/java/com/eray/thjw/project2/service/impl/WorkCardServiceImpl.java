package com.eray.thjw.project2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.CoverPlateMapper;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.ProjectEffectiveMapper;
import com.eray.thjw.project2.dao.WorkCardMapper;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.service.ApplicableUnitService;
import com.eray.thjw.project2.service.CoverPlateService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.project2.service.ReferenceService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.project2.service.WorkCard2RelatedService;
import com.eray.thjw.project2.service.WorkCardService;
import com.eray.thjw.project2.service.WorkContentService;
import com.eray.thjw.project2.service.WorkHourService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.MaterialSecondTypeEnum;
import enu.MaterialTypeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.RevMarkEnum;
import enu.produce.NodeEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
import enu.project2.WorkCardStatusEnum;

/**
 * @Description 工卡接口实现类
 * @CreateTime 2017-8-15 下午3:22:03
 * @CreateBy 刘兵
 */
@Service
public class WorkCardServiceImpl implements WorkCardService {
	
	@Resource
	private WorkCardMapper workCardMapper;
	@Resource
	private TodoService todoService;
	@Resource
	private CommonService commonService;
	
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	
	@Resource
	private ProjectEffectiveMapper projectEffectiveMapper;
	
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	
	@Resource
	private CoverPlateMapper coverPlateMapper;
	
	@Resource
	private InstructionsourceService instructionsourceService;
	
	@Resource
	private CoverPlateService coverPlateService;
	
	@Resource
	private WorkHourService workHourService;
	
	@Resource
	private ReferenceService referenceService;
	
	@Resource
	private WorkCard2RelatedService workCard2RelatedService;
	
	@Resource
	private MaterialToolService materialToolService;
	
	@Resource
	private WorkContentService workContentService;
	
	@Resource
	private ApplicableUnitService applicableUnitService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private TodorsService todorsService;

	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 新增工卡
	 * @CreateTime 2017-8-15 下午3:34:36
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Override
	public String save(WorkCard workCard) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//获取随机的uuid
		String id = UUID.randomUUID().toString();
		workCard.setId(id);
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(workCard.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), user.getJgdm(), jxList);
		//检查工卡是否存在
		checkExist(workCard.getJx(), workCard.getGkh(), user.getJgdm());
		//判断维修项目编号是否为空
		if(StringUtils.isNotBlank(workCard.getWxxmbh())){
			//检查维修项目编号在其他工卡中是否存在
			checkExistWithWxxm(workCard.getJx(), workCard.getGkh(), user.getJgdm(), workCard.getWxxmbh());
		}
		workCard.setZt(WorkCardStatusEnum.SAVE.getId());
		if(null != workCard.getParamsMap() && null != workCard.getParamsMap().get("operationType")){
			workCard.setZt(WorkCardStatusEnum.SUBMIT.getId());
		}

		workCard.setWhrid(user.getId());
		workCard.setWhbmid(user.getBmdm());
		workCard.setDprtcode(user.getJgdm());
		workCard.setZxbs(RevMarkEnum.INITIAL.getId());
		//新增工卡附件
		if(null != workCard.getWorkCardAttachment()){
			workCard.setGkfjid(UUID.randomUUID().toString());
			attachmentService.addAttachment(workCard.getWorkCardAttachment(), workCard.getGkfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		}
		//新增工作内容附件
		if(null != workCard.getWorkContentAttachment()){
			workCard.setGznrfjid(UUID.randomUUID().toString());
			attachmentService.addAttachment(workCard.getWorkContentAttachment(), workCard.getGznrfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		}
		//保存工卡
		workCardMapper.insertSelective(workCard);
		//保存历史记录信息
		commonRecService.write(id, TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);
		//保存附件信息
		attachmentService.eidtAttachment(workCard.getAttachmentList(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工卡-关联工卡
		workCard2RelatedService.saveWorkCard2RelatedList(workCard.getWorkCard2RelatedList(), id, czls, id, LogOperationEnum.SAVE_WO);
		//保存接近/盖板
		coverPlateService.saveCoverPlateList(workCard.getCoverPlateList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工种工时
		workHourService.saveWorkHourList(workCard.getWorkHourList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存参考文件
		referenceService.saveReferenceList(workCard.getReferenceList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存器材/工具
		materialToolService.saveMaterialToolList(workCard.getMaterialToolList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工作内容
		workContentService.saveWorkContentList(workCard.getWorkContentList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工卡-适用单位
		applicableUnitService.saveApplicableUnitList(workCard.getApplicableUnitList(), id, czls, id, LogOperationEnum.SAVE_WO);
		//保存下达指令
		instructionsourceService.saveInstructionSourceList(workCard.getInstructionsourceList(), ProjectBusinessEnum.WORK_CARD.getId(), id, workCard.getDprtcode());
		//新增待办事宜处理结果
		if (null != workCard.getInstructionsourceList()) {
			List<String> lyidList = new ArrayList<String>();
			for(Instructionsource instructionsource : workCard.getInstructionsourceList()){
				lyidList.add(instructionsource.getPgdid());
			}
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(
					  workCard.getJx()
					, lyidList
					, SendOrderEnum.XDGK.getId()
					, workCard.getWhrid()
					, workCard.getId()
					, workCard.getGkh()
					, workCard.getBb().toString()
					, workCard.getGzbt()
					,workCard.getZt());
		}
		if(workCard.getZt().equals(WorkCardStatusEnum.SUBMIT.getId())){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(5);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审核");
			strSm.append(workCard.getJx());
			strSm.append("机型的");
			strSm.append(workCard.getGkh());
			strSm.append(" R");
			strSm.append(workCard.getBb());
			strSm.append("工卡。");
			todoService.insertSelectiveTechnical(workCard,strSm.toString(),"project2:workcard:main:03",NodeEnum.NODE2.getId(),null,TodoEnum.WORK_CARD.getId());
		}
		
		return id;
	}
	
	/**
	 * @Description 编辑工卡
	 * @CreateTime 2017-8-18 下午4:43:24
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Override
	public String update(WorkCard workCard) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		WorkCard wc = workCardMapper.selectByPrimaryKey(workCard.getId());
		if(null == wc){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(workCard.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), wc.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				WorkCardStatusEnum.SAVE.getId(),
				WorkCardStatusEnum.AUDIT_REJEC.getId(),
				WorkCardStatusEnum.APPROVE_REJEC.getId()
				},wc.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//版本号不能小于工卡号+机型已经存在的最大版本
		BigDecimal maxBb = workCardMapper.getMaxBb(wc.getJx(), wc.getGkh(), wc.getDprtcode(), wc.getId());
		if(null != maxBb && workCard.getBb().compareTo(maxBb) != 1){
			throw new BusinessException("版本必须大于老版本!");
		}
		//判断维修项目编号是否为空
		if(StringUtils.isNotBlank(workCard.getWxxmbh())){
			//检查维修项目编号在其他工卡中是否存在
			checkExistWithWxxm(workCard.getJx(), workCard.getGkh(), user.getJgdm(), workCard.getWxxmbh());
		}
		workCard.setZt(wc.getZt());
		workCard.setWhrid(user.getId());
		workCard.setWhbmid(user.getBmdm());
		if(null != workCard.getParamsMap() && null != workCard.getParamsMap().get("operationType")){
			workCard.setZt(WorkCardStatusEnum.SUBMIT.getId());
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(5);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审核");
			strSm.append(workCard.getJx());
			strSm.append("机型的");
			strSm.append(workCard.getGkh());
			strSm.append(" R");
			strSm.append(workCard.getBb());
			strSm.append("工卡。");
			todoService.insertSelectiveTechnical(workCard,strSm.toString(),"project2:workcard:main:03",NodeEnum.NODE2.getId(),null,TodoEnum.WORK_CARD.getId());
		}
		//编辑工卡附件
		if(null != wc.getGkfjid() && !"".equals(wc.getGkfjid())){
			if(workCard.getWorkCardAttachment() == null){
				workCard.setGkfjid("");
				//删除工卡附件
				attachmentService.delFiles(wc.getGkfjid(), czls, workCard.getId(), LogOperationEnum.EDIT);
			}else{
				//编辑工卡附件
				attachmentService.editAttachment(workCard.getWorkCardAttachment(), wc.getGkfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
			}
		}else{
			if(workCard.getWorkCardAttachment() != null){
				workCard.setGkfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(workCard.getWorkCardAttachment(), workCard.getGkfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
			}
		}
		//编辑工作内容附件
		if(null != wc.getGznrfjid() && !"".equals(wc.getGznrfjid())){
			if(workCard.getWorkContentAttachment() == null){
				workCard.setGznrfjid("");
				//删除工卡附件
				attachmentService.delFiles(wc.getGznrfjid(), czls, workCard.getId(), LogOperationEnum.EDIT);
			}else{
				//编辑工卡附件
				attachmentService.editAttachment(workCard.getWorkContentAttachment(), wc.getGznrfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
			}
		}else{
			if(workCard.getWorkContentAttachment() != null){
				workCard.setGznrfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(workCard.getWorkContentAttachment(), workCard.getGznrfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
			}
		}

		//编辑工卡
		workCardMapper.updateById(workCard);
		//保存历史记录信息
		commonRecService.write(workCard.getId(), TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, workCard.getId());
		//编辑附件信息
		attachmentService.eidtAttachment(workCard.getAttachmentList(), workCard.getId(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
		//编辑工卡-关联工卡
		workCard2RelatedService.updateWorkCard2RelatedList(workCard.getWorkCard2RelatedList(), workCard.getId(), czls, workCard.getId(), LogOperationEnum.EDIT);
		//编辑接近/盖板
		coverPlateService.updateCoverPlateList(workCard.getCoverPlateList(), ProjectBusinessEnum.WORK_CARD.getId(), workCard.getId(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
		//编辑工种工时
		workHourService.updateWorkHourList(workCard.getWorkHourList(), ProjectBusinessEnum.WORK_CARD.getId(), workCard.getId(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
		//编辑参考文件
		referenceService.updateReferenceList(workCard.getReferenceList(), ProjectBusinessEnum.WORK_CARD.getId(), workCard.getId(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
		//编辑器材/工具
		materialToolService.updateMaterialToolList(workCard.getMaterialToolList(), ProjectBusinessEnum.WORK_CARD.getId(), workCard.getId(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
		//编辑工作内容
		workContentService.updateWorkContentList(workCard.getWorkContentList(), ProjectBusinessEnum.WORK_CARD.getId(), workCard.getId(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.EDIT);
		//编辑工卡-适用单位
		applicableUnitService.updateApplicableUnitList(workCard.getApplicableUnitList(), workCard.getId(), czls, workCard.getId(), LogOperationEnum.EDIT);
		//编辑下达指令
		instructionsourceService.updateInstructionSourceList(workCard.getInstructionsourceList(), ProjectBusinessEnum.WORK_CARD.getId(), workCard.getId(), workCard.getDprtcode());
		// 删除旧的待办事宜待处理结果
		todorsService.deleteTodorsByYwid(workCard.getId());
		//新增待办事宜处理结果
		if (null != workCard.getInstructionsourceList()) {
			List<String> lyidList = new ArrayList<String>();
			for(Instructionsource instructionsource : workCard.getInstructionsourceList()){
				lyidList.add(instructionsource.getPgdid());
			}
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(
					  workCard.getJx()
					, lyidList
					, SendOrderEnum.XDGK.getId()
					, workCard.getWhrid()
					, workCard.getId()
					, workCard.getGkh()
					, workCard.getBb().toString()
					, workCard.getGzbt()
					,workCard.getZt());
		}
		return workCard.getId();
	}
	
	/**
	 * @Description 审核工卡
	 * @CreateTime 2017-8-21 下午1:44:03
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Override
	public String doAudit(WorkCard workCard) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		WorkCard wc = workCardMapper.selectByPrimaryKey(workCard.getId());
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(workCard.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), wc.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{WorkCardStatusEnum.SUBMIT.getId()}, wc.getZt(), "该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		workCard.setShrid(user.getId());
		workCard.setShbmid(user.getBmdm());
		if(null != workCard.getParamsMap() && null != workCard.getParamsMap().get("audit")){
			workCard.setZt(WorkCardStatusEnum.AUDITED.getId());
			//编辑工卡
			workCardMapper.updateByPrimaryKeySelective(workCard);
			//保存历史记录信息
			commonRecService.write(workCard.getId(), TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, workCard.getId());
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
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
			strSm.append(wc.getJx());
			strSm.append("机型的");
			strSm.append(wc.getGkh());
			strSm.append(" R");
			strSm.append(wc.getBb());
			strSm.append("工卡。");
			todoService.insertSelectiveTechnical(wc,strSm.toString(),"project2:workcard:main:04",NodeEnum.NODE3.getId(),null,TodoEnum.WORK_CARD.getId());
		}else{
			workCard.setZt(WorkCardStatusEnum.AUDIT_REJEC.getId());
			//编辑工卡
			workCardMapper.updateByPrimaryKeySelective(workCard);
			//保存历史记录信息
			commonRecService.write(workCard.getId(), TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE, workCard.getId());
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(wc.getJx());
			strSm.append("机型的");
			strSm.append(wc.getGkh());
			strSm.append(" R");
			strSm.append(wc.getBb());
			strSm.append("工卡已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(wc,strSm.toString(),"project2:workcard:main:02",NodeEnum.NODE5.getId(),wc.getWhrid(),TodoEnum.WORK_CARD.getId());
		}
		//更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(workCard.getZt(), workCard.getId());
		//更新待办事宜状态
		todorsService.updateYwdjztByYwid(workCard.getZt(),workCard.getId());
		return workCard.getId();
	}
	
	/**
	 * @Description 批准工卡
	 * @CreateTime 2017-8-21 下午1:44:03
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Override
	public String doApprove(WorkCard workCard) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		WorkCard wc = workCardMapper.selectByPrimaryKey(workCard.getId());
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(workCard.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), wc.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{WorkCardStatusEnum.AUDITED.getId()}, wc.getZt(), "该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		workCard.setPfrid(user.getId());
		workCard.setPfbmid(user.getBmdm());
		if(null != workCard.getParamsMap() && null != workCard.getParamsMap().get("approve")){
			workCard.setZt(WorkCardStatusEnum.APPROVED.getId());
			//编辑工卡
			workCardMapper.updateByPrimaryKeySelective(workCard);
			//保存历史记录信息
			commonRecService.write(workCard.getId(), TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, workCard.getId());
			//工卡生效
			doWorkCardEffective(wc);
			workCard.setZt(WorkCardStatusEnum.EFFECTIVE.getId());
	
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
		}else{
			workCard.setZt(WorkCardStatusEnum.APPROVE_REJEC.getId());
			//编辑工卡
			workCardMapper.updateByPrimaryKeySelective(workCard);
			//保存历史记录信息
			commonRecService.write(workCard.getId(), TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE, workCard.getId());
	
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(wc.getJx());
			strSm.append("机型的");
			strSm.append(wc.getGkh());
			strSm.append(" R");
			strSm.append(wc.getBb());
			strSm.append("工卡已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(wc,strSm.toString(),"project2:workcard:main:02",NodeEnum.NODE5.getId(),wc.getWhrid(),TodoEnum.WORK_CARD.getId());
		}
		//更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(workCard.getZt(), workCard.getId());
		//更新待办事宜状态
		todorsService.updateYwdjztByYwid(workCard.getZt(),workCard.getId());
		return workCard.getId();
	}
	
	/**
	 * @Description 改版工卡
	 * @CreateTime 2017-8-22 下午7:05:57
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Override
	public String modify(WorkCard workCard) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(workCard.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), workCard.getDprtcode(), jxList);
		WorkCard wc = workCardMapper.selectByPrimaryKey(workCard.getId());
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{WorkCardStatusEnum.EFFECTIVE.getId()}, wc.getZt(), "该数据已更新，请刷新后再进行操作!");
		validation4CurrentZt(new Integer[]{RevMarkEnum.LATEST_VERSION.getId()}, wc.getZxbs(), "该数据已更新，请刷新后再进行操作!");
		if(StringUtils.isNotBlank(wc.getbBbid())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 end */
		//版本号不能小于工卡号+机型已经存在的最大版本
		BigDecimal maxBb = workCardMapper.getMaxBb(wc.getJx(), wc.getGkh(), wc.getDprtcode(), "");
		if(null != maxBb && workCard.getBb().compareTo(maxBb) != 1){
			throw new BusinessException("版本必须大于老版本!");
		}
		//判断维修项目编号是否为空
		if(StringUtils.isNotBlank(workCard.getWxxmbh())){
			//检查维修项目编号在其他工卡中是否存在
			checkExistWithWxxm(workCard.getJx(), workCard.getGkh(), user.getJgdm(), workCard.getWxxmbh());
		}
		workCard.setZt(WorkCardStatusEnum.SAVE.getId());
		if(null != workCard.getParamsMap() && null != workCard.getParamsMap().get("operationType")){
			workCard.setZt(WorkCardStatusEnum.SUBMIT.getId());
			
			
		}
		//获取随机的uuid
		String id = UUID.randomUUID().toString();
		//更新前版本的工卡后版本id=改版后的工卡id
		WorkCard previousVersion = new WorkCard();
		previousVersion.setId(wc.getId());
		previousVersion.setWhrid(user.getId());
		previousVersion.setWhbmid(user.getBmdm());
		previousVersion.setbBbid(id);
		workCardMapper.updateByPrimaryKeySelective(previousVersion);
		//保存历史记录信息
		commonRecService.write(wc.getId(), TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.REVISION, UpdateTypeEnum.UPDATE, wc.getId());
		
		workCard.setId(id);
		workCard.setWhrid(user.getId());
		workCard.setWhbmid(user.getBmdm());
		workCard.setZxbs(RevMarkEnum.INITIAL.getId());
		workCard.setfBbid(wc.getId());
		//新增工卡附件
		if(null != workCard.getWorkCardAttachment()){
			workCard.setGkfjid(UUID.randomUUID().toString());
			attachmentService.addAttachment(workCard.getWorkCardAttachment(), workCard.getGkfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		}
		//新增工作内容附件
		if(null != workCard.getWorkContentAttachment()){
			workCard.setGznrfjid(UUID.randomUUID().toString());
			attachmentService.addAttachment(workCard.getWorkContentAttachment(), workCard.getGznrfjid(), czls, workCard.getId(), workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		}
		//保存工卡
		workCardMapper.insertSelective(workCard);
		//保存历史记录信息
		commonRecService.write(id, TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);
		//保存附件信息
		attachmentService.saveAttachment4Modify(workCard.getAttachmentList(), wc.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工卡-关联工卡
		workCard2RelatedService.saveWorkCard2RelatedList(workCard.getWorkCard2RelatedList(), id, czls, id, LogOperationEnum.SAVE_WO);
		//保存接近/盖板
		coverPlateService.saveCoverPlateList(workCard.getCoverPlateList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工种工时
		workHourService.saveWorkHourList(workCard.getWorkHourList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存参考文件
		referenceService.saveReferenceList(workCard.getReferenceList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存器材/工具
		materialToolService.saveMaterialToolList(workCard.getMaterialToolList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工作内容
		workContentService.saveWorkContentList(workCard.getWorkContentList(), ProjectBusinessEnum.WORK_CARD.getId(), id, czls, id, workCard.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存工卡-适用单位
		applicableUnitService.saveApplicableUnitList(workCard.getApplicableUnitList(), id, czls, id, LogOperationEnum.SAVE_WO);
		//保存下达指令
		instructionsourceService.saveInstructionSourceList(workCard.getInstructionsourceList(), ProjectBusinessEnum.WORK_CARD.getId(), id, workCard.getDprtcode());
		// 删除旧的待办事宜待处理结果
		todorsService.deleteTodorsByYwid(workCard.getId());
		//新增待办事宜处理结果
		if (null != workCard.getInstructionsourceList()) {
			List<String> lyidList = new ArrayList<String>();
			for(Instructionsource instructionsource : workCard.getInstructionsourceList()){
				lyidList.add(instructionsource.getPgdid());
			}
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(
					  workCard.getJx()
					, lyidList
					, SendOrderEnum.XDGK.getId()
					, workCard.getWhrid()
					, workCard.getId()
					, workCard.getGkh()
					, workCard.getBb().toString()
					, workCard.getGzbt()
					, workCard.getZt());
		}
		
		if(workCard.getZt().equals(WorkCardStatusEnum.SUBMIT.getId())){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(workCard.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(5);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审核");
			strSm.append(workCard.getJx());
			strSm.append("机型的");
			strSm.append(workCard.getGkh());
			strSm.append(" R");
			strSm.append(workCard.getBb());
			strSm.append("工卡。");
			todoService.insertSelectiveTechnical(workCard,strSm.toString(),"project2:workcard:main:03",NodeEnum.NODE2.getId(),null,TodoEnum.WORK_CARD.getId());
		}
		
		
		return id;
	}
	
	/**
	 * @Description 删除工卡
	 * @CreateTime 2017-8-22 下午4:56:19
	 * @CreateBy 刘兵
	 * @param id 工卡id 
	 * @throws BusinessException
	 */
	@Override
	public void delete(String id) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		WorkCard wc = workCardMapper.selectByPrimaryKey(id);
		if(null == wc){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(wc.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), wc.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				WorkCardStatusEnum.SAVE.getId(),
				WorkCardStatusEnum.AUDIT_REJEC.getId(),
				WorkCardStatusEnum.APPROVE_REJEC.getId()
				},wc.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//更新前版本的工卡后版本id置空
		if(StringUtils.isNotBlank(wc.getfBbid())){
			WorkCard previousVersion = new WorkCard();
			previousVersion.setId(wc.getfBbid());
			previousVersion.setWhrid(user.getId());
			previousVersion.setWhbmid(user.getBmdm());
			previousVersion.setbBbid("");
			workCardMapper.updateByPrimaryKeySelective(previousVersion);
		}
		//保存历史记录信息
		commonRecService.write(id, TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, id);
		workCardMapper.deleteByPrimaryKey(id);
		// 删除待办事宜待处理结果
		todorsService.deleteTodorsByYwid(id);
		// 删除下达指令
		instructionsourceMapper.deleteInstructionSourceByZlid(id);
		//编辑工卡附件
		if(null != wc.getGkfjid() && !"".equals(wc.getGkfjid())){
			//删除工卡附件
			attachmentService.delFiles(wc.getGkfjid(), czls, id, LogOperationEnum.DELETE);
		}
		//编辑工作内容附件
		if(null != wc.getGznrfjid() && !"".equals(wc.getGznrfjid())){
			//删除工作内容附件
			attachmentService.delFiles(wc.getGznrfjid(), czls, id, LogOperationEnum.DELETE);
		}
		//删除附件信息
		attachmentService.delFiles(id, czls, id, LogOperationEnum.DELETE);
		//删除工卡-关联工卡
		workCard2RelatedService.deleteByMainid(id, czls, id, LogOperationEnum.DELETE);
		//删除接近/盖板
		coverPlateService.deleteByYwid(id, czls, id, LogOperationEnum.DELETE);
		//删除工种工时
		workHourService.deleteByYwid(id, czls, id, LogOperationEnum.DELETE);
		//删除参考文件
		referenceService.deleteByYwid(id, czls, id, LogOperationEnum.DELETE);
		//删除器材/工具
		materialToolService.deleteByYwid(id, czls, id, LogOperationEnum.DELETE);
		//删除工作内容
		workContentService.deleteByYwid(id, czls, id, LogOperationEnum.DELETE);
		//删除工卡-适用单位
		applicableUnitService.deleteByMainid(id, czls, id, LogOperationEnum.DELETE);
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(id);
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	}
	
	/**
	 * @Description 失效工卡
	 * @CreateTime 2017-8-22 下午4:56:19
	 * @CreateBy 刘兵
	 * @param id 工卡id 
	 * @throws BusinessException
	 */
	@Override
	public void doInvalid(String id) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		WorkCard wc = workCardMapper.selectByPrimaryKey(id);
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(wc.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), wc.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{WorkCardStatusEnum.EFFECTIVE.getId()}, wc.getZt(), "该数据卡已更新，请刷新后再进行操作!");
		validation4CurrentZt(new Integer[]{RevMarkEnum.LATEST_VERSION.getId()}, wc.getZxbs(), "该数据已更新，请刷新后再进行操作!");
		if(StringUtils.isNotBlank(wc.getbBbid())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 end */
		
		
		
		//设置工卡失效,更新工卡状态=8失效
		WorkCard currentVersion = new WorkCard();
		currentVersion.setId(wc.getId());
		currentVersion.setZt(WorkCardStatusEnum.INVALID.getId());
		currentVersion.setGbrid(user.getId());
		workCardMapper.updateByPrimaryKeySelective(currentVersion);
		//更新下达指令业务单号状态
		instructionsourceMapper.updateYwdjztByZlid(currentVersion.getZt(), currentVersion.getId());
		//更新待办事宜状态
		todorsService.updateYwdjztByYwid(currentVersion.getZt(),currentVersion.getId());
		//保存历史记录信息
		commonRecService.write(id, TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.INVALID, UpdateTypeEnum.UPDATE, id);
	
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(id);
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
	}
	
	/**
	 * @Description 批量审核
	 * @CreateTime 2017-8-23 上午9:51:17
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @param yj 审核意见
	 * @return String 审核结果
	 * @throws BusinessException
	 */
	@Override
	public String updateBatchAudit(List<String> idList, String yj) throws BusinessException{
		List<String> auditIdList = new ArrayList<String>();//可审核的工卡id集合
		List<String> jxList = new ArrayList<String>();//用于检查机型权限
		StringBuffer buffer = new StringBuffer();
		User user = ThreadVarUtil.getUser();
		List<WorkCard> wcList = workCardMapper.queryByIdList(idList);
		String drpt = null;
		for (WorkCard wc : wcList) {
			if(null == drpt){
				drpt = wc.getDprtcode();
			}
			if(WorkCardStatusEnum.SUBMIT.getId().intValue() == wc.getZt().intValue()){
				jxList.add(wc.getJx());
				auditIdList.add(wc.getId());
				buffer.append("工卡【").append(wc.getGkh()).append("】审核通过!<br>");
			}else{
				buffer.append("工卡【").append(wc.getGkh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		//检查用户机型的权限
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), drpt, jxList);
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			workCardMapper.updateBatchAudit(paramMap);
			//批量更新下达指令状态
			instructionsourceMapper.updateYwdjztByZlidList(WorkCardStatusEnum.AUDITED.getId(), auditIdList);
			//批量更新待办事宜数据状态
			todorsService.updateZtByYwidList(WorkCardStatusEnum.AUDITED.getId(),auditIdList);
			//保存历史记录信息
			commonRecService.write("id", auditIdList, TableEnum.B_G2_013, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, null);
		
			//批量新增待办事宜
			todoService.saveBatchTodoAuditList(auditIdList,TodoEnum.WORK_CARD.getId());
		}
		return buffer.toString();
	}
	

	/**
	 * @Description 批量批准
	 * @CreateTime 2017-8-23 上午9:56:40
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @param yj 批准意见
	 * @return String 批准结果
	 * @throws BusinessException
	 */
	@Override
	public String updateBatchApprove(List<String> idList, String yj) throws BusinessException{
		Date currentDate = commonService.getSysdate();//当前时间
		List<String> approveIdList = new ArrayList<String>();//可批准的工卡id集合
		List<String> jxList = new ArrayList<String>();//用于检查机型权限
		List<WorkCard> workCardEffectList = new ArrayList<WorkCard>();//可生效的工卡集合
		StringBuffer buffer = new StringBuffer();
		User user = ThreadVarUtil.getUser();
		List<WorkCard> wcList = workCardMapper.queryByIdList(idList);
		String drpt = null;
		for (WorkCard wc : wcList) {
			if(null == drpt){
				drpt = wc.getDprtcode();
			}
			if(WorkCardStatusEnum.AUDITED.getId().intValue() == wc.getZt().intValue()){
				jxList.add(wc.getJx());
				workCardEffectList.add(wc);
				approveIdList.add(wc.getId());
				buffer.append("工卡【").append(wc.getGkh()).append("】批准通过!<br>");
			}else{
				buffer.append("工卡【").append(wc.getGkh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		//检查用户机型的权限
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), drpt, jxList);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String czls = UUID.randomUUID().toString();//操作流水
		paramMap.put("pfrid", user.getId());
		paramMap.put("pfbmid", user.getBmdm());
		paramMap.put("idList", approveIdList);
		paramMap.put("pfyj", yj);
		if(null != approveIdList && approveIdList.size() > 0){
			workCardMapper.updateBatchApprove(paramMap);
			//批量更新下达指令状态
			instructionsourceMapper.updateYwdjztByZlidList(WorkCardStatusEnum.EFFECTIVE.getId(), approveIdList);
			//批量更新待办事宜数据状态
			todorsService.updateZtByYwidList(WorkCardStatusEnum.EFFECTIVE.getId(),approveIdList);
			//保存历史记录信息
			commonRecService.write("id",approveIdList, TableEnum.B_G2_013, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, null);
			for (WorkCard wc : workCardEffectList) {
				//工卡生效
				doWorkCardEffective(wc);
			}
			
			for (String id : approveIdList) {
				//关闭待办
				Todo tododbyw=new Todo();
				tododbyw.setDbywid(id);
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(3);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				tododbyw.setZt(TodoStatusEnum.DCL.getId());
				tododbyw.setBlrid(user.getId());
				tododbyw.setFksj(currentDate);
				todoService.updateByDbyw(tododbyw);
			}
		}
		return buffer.toString();
	}
	
	/**
	 * @Description 工卡分页列表查询
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPageList(WorkCard workCard){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = workCard.getId();
		workCard.setId("");
		PageHelper.startPage(workCard.getPagination());
		List<WorkCard> workCardList = workCardMapper.queryAllPageList(workCard);
		if (((Page) workCardList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(workCardList, id)) {
					// 在查询条件中增加ID
					WorkCard newRecord = new WorkCard();
					newRecord.setId(id);
					List<WorkCard> newRecordList = workCardMapper.queryAllPageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						workCardList.add(0, newRecordList.get(0));
					}
				}
			}
			setCoverPlateList(workCardList);//设置接近/盖板集合
			resultMap = PageUtil.pack4PageHelper(workCardList, workCard.getPagination());
			return resultMap;
		} else {
			List<WorkCard> newRecordList = new ArrayList<WorkCard>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				WorkCard newRecord = new WorkCard();
				newRecord.setId(id);
				newRecordList = workCardMapper.queryAllPageList(newRecord);
				if (newRecordList != null && newRecordList.size() == 1) {
					setCoverPlateList(workCardList);//设置接近/盖板集合
					resultMap = PageUtil.pack(1, newRecordList, workCard.getPagination());
					return resultMap;
				}

			}
			resultMap = PageUtil.pack(0, newRecordList, workCard.getPagination());
			return resultMap;
		}
	}
	
	/**
	 * @Description 工卡分页列表查询(弹窗需要的数据)
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryWinAllPageList(WorkCard workCard){
		PageHelper.startPage(workCard.getPagination());
		List<WorkCard> workCardList = workCardMapper.queryWinAllPageList(workCard);
		return PageUtil.pack4PageHelper(workCardList, workCard.getPagination());
	}
	
	/**
	 * @Description 根据工卡id查询工卡及用户信息
	 * @CreateTime 2017-8-17 下午5:23:17
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @return WorkCard 工卡
	 */
	@Override
	public WorkCard selectById(String id){
		return workCardMapper.selectById(id);
	}
	
	/**
	 * @Description 工卡生效
	 * @CreateTime 2017-8-22 下午1:50:13
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 */
	private void doWorkCardEffective(WorkCard workCard){
		User user = ThreadVarUtil.getUser();
		//设置工卡生效,更新工卡状态=7生效，最新标识=2新版本
		WorkCard currentVersion = new WorkCard();
		currentVersion.setId(workCard.getId());
		currentVersion.setZxbs(RevMarkEnum.LATEST_VERSION.getId());
		currentVersion.setZt(WorkCardStatusEnum.EFFECTIVE.getId());
		currentVersion.setWhrid(user.getId());
		currentVersion.setWhbmid(user.getBmdm());
		workCardMapper.updateByPrimaryKeySelective(currentVersion);
		
		//更新前版本的工卡最新标识=3老版本
		if(StringUtils.isNotBlank(workCard.getfBbid())){
			WorkCard previousVersion = new WorkCard();
			previousVersion.setId(workCard.getfBbid());
			previousVersion.setZxbs(RevMarkEnum.OLD_VERSION.getId());
			previousVersion.setWhrid(user.getId());
			previousVersion.setWhbmid(user.getBmdm());
			workCardMapper.updateByPrimaryKeySelective(previousVersion);
		}
		doProjectEffective(workCard);
	}
	
	/**
	 * @Description 修改维修项目及生效区工卡信息
	 * @CreateTime 2017-8-22 下午1:50:13
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 */
	private void doProjectEffective(WorkCard workCard){
		//将维修项目工卡编号设为空
		maintenanceProjectMapper.updateGkbh2Null(workCard.getGkh(), workCard.getJx(), workCard.getDprtcode());
		//将非生效状态维修项目工卡编号设为空
		maintenanceProjectMapper.updateGkbh2Null4NoEffective(workCard.getGkh(), workCard.getJx(), workCard.getDprtcode());
		//将维修方案生效区-维修项目关系工卡设为空
		projectEffectiveMapper.updateGk2Null(workCard.getGkh(), workCard.getJx(), workCard.getDprtcode());
		if(StringUtils.isNotBlank(workCard.getWxxmbh())){
			//修改维修项目工卡编号
			maintenanceProjectMapper.updateGkbh(workCard.getGkh(), workCard.getJx(), workCard.getWxxmbh(), workCard.getDprtcode());
			//修改非生效状态维修项目工卡编号
			maintenanceProjectMapper.updateGkbh4NoEffective(workCard.getGkh(), workCard.getJx(), workCard.getWxxmbh(), workCard.getDprtcode());
			//修改维修方案生效区-维修项目关系工卡编号、工卡id
			projectEffectiveMapper.updateGk(workCard.getGkh(), workCard.getId(), workCard.getJx(), workCard.getWxxmbh(), workCard.getDprtcode());
		}
	}
	
	/**
	 * @Description 设置获取接近/盖板集合,并赋值
	 * @CreateTime 2017-8-24 下午6:51:14
	 * @CreateBy 刘兵
	 * @param workCardList 工卡集合
	 */
	private void setCoverPlateList(List<WorkCard> workCardList){
		List<String> ywidList = new ArrayList<String>();
		for (WorkCard wc : workCardList) {
			ywidList.add(wc.getId());
		}
		if(0 != ywidList.size()){
			List<CoverPlate> queryByYwidList = coverPlateMapper.queryByYwidList(ywidList);
			Map<String, List<CoverPlate>> map = new HashMap<String, List<CoverPlate>>();
			for (CoverPlate coverPlate : queryByYwidList){
				List<CoverPlate> coverPlateList = map.get(coverPlate.getYwid());
				if(null == coverPlateList){
					coverPlateList = new ArrayList<CoverPlate>();
					map.put(coverPlate.getYwid(), coverPlateList);
				}
				coverPlateList.add(coverPlate);
			}
			for (WorkCard wc : workCardList) {
				wc.setCoverPlateList(map.get(wc.getId()));
			}
		}
	}
	
	/**
	 * @Description 验证表单重复提交,当前状态是否存在于数组中,不存在:抛出异常message
	 * @CreateTime 2017-8-18 下午5:06:49
	 * @CreateBy 刘兵
	 * @param ztArr 可操作状态数组
	 * @param currentZt 当前状态
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(Integer[] ztArr, Integer currentZt,String message) throws BusinessException{
		if(currentZt != null && !ArrayUtils.contains(ztArr,currentZt)){
			throw new BusinessException(message);
		}
	}

	/**
	 * @Description 检查工卡是否存在
	 * @CreateTime 2017-8-16 下午6:29:39
	 * @CreateBy 刘兵
	 * @param jx 机型
	 * @param gkh 工卡号
	 * @param dprtcode 机构代码
	 * @throws BusinessException
	 */
	private void checkExist(String jx, String gkh, String dprtcode) throws BusinessException{
		int iCount = workCardMapper.getCount4CheckExist(jx, gkh, dprtcode);
		if(iCount > 0){
			throw new BusinessException(new StringBuffer().append("工卡编号[").append(gkh).append("]已存在!").toString());
		}
	}
	
	/**
	 * @Description 检查工卡是否存在(带维修项目)
	 * @CreateTime 2017-9-20 下午2:16:28
	 * @CreateBy 刘兵
	 * @param jx 机型
	 * @param gkh 工卡号
	 * @param dprtcode 机构代码
	 * @param wxxmbh 维修项目编号
	 * @throws BusinessException
	 */
	private void checkExistWithWxxm(String jx, String gkh, String dprtcode, String wxxmbh) throws BusinessException{
		List<WorkCard> workCardList = workCardMapper.query4CheckExistWithWxxm(jx, gkh, dprtcode, wxxmbh);
		if(workCardList.size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append("维修项目编号[").append(wxxmbh).append("]在其他工卡[");
			for (WorkCard workCard : workCardList) {
				if(sb.indexOf(workCard.getGkh()) == -1){
					sb.append(" ").append(workCard.getGkh()).append(" ");
				}
			}
			sb.append("]已存在!");
			throw new BusinessException(sb.toString());
		}
	}

	/**
	 * @Description 查询指定工卡的版本集合
	 * @CreateTime 2017年8月24日 下午3:58:16
	 * @CreateBy 韩武
	 * @param workCard
	 * @return
	 */
	@Override
	public List<WorkCard> queryVersionList(WorkCard workCard) {
		return workCardMapper.queryVersionList(workCard);
	}
	
	/**
	  * @Description 根据工卡id查询当前工卡的历史版本集合
	  * @CreateTime 2017-8-28 下午5:43:59
	  * @CreateBy 刘兵
	  * @param id 工卡id
	  * @return List<WorkCard> 工卡集合
	  */
	@Override
	public List<WorkCard> queryHistoryListById(String id) {
		return workCardMapper.queryHistoryListById(id);
	}

	/**
	  * @Description 工单135:获取工卡来源信息
	  * @CreateTime 2017-10-10 上午9:33:49
	  * @CreateBy 雷伟
	  * @param workCard 工卡
	  * @return Map<String, Object>
	  */
	@Override
	public Map<String, Object> queryOriginatingCardList(WorkCard workCard) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = workCard.getId();
		workCard.setId("");
		PageHelper.startPage(workCard.getPagination());
		List<WorkCard> workCardList = workCardMapper.queryOriginatingCardList(workCard);
		if (((Page) workCardList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(workCardList, id)) {
					// 在查询条件中增加ID
					WorkCard newRecord = new WorkCard();
					newRecord.setId(id);
					List<WorkCard> newRecordList = workCardMapper.queryOriginatingCardList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						workCardList.add(0, newRecordList.get(0));
					}
				}
			}
			getCoverPlateList(workCardList);//获取接近/盖板集合
			resultMap = PageUtil.pack4PageHelper(workCardList, workCard.getPagination());
			return resultMap;

		} else {
			List<WorkCard> newRecordList = new ArrayList<WorkCard>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				WorkCard newRecord = new WorkCard();
				newRecord.setId(id);
				newRecordList = workCardMapper.queryOriginatingCardList(newRecord);
				if (newRecordList != null && newRecordList.size() == 1) {
					getCoverPlateList(workCardList);//获取接近/盖板集合
					resultMap = PageUtil.pack(1, newRecordList, workCard.getPagination());
					return resultMap;
				}

			}
			resultMap = PageUtil.pack(0, newRecordList, workCard.getPagination());
			return resultMap;
		}
	}
	/**
	 * 
	 * @Description 获取接近/盖板集合
	 * @CreateTime 2017年10月31日 下午4:06:06
	 * @CreateBy 岳彬彬
	 * @param workCardList
	 */
	private void getCoverPlateList(List<WorkCard> workCardList){
		List<String> ywidList = new ArrayList<String>();
		for (WorkCard wc : workCardList) {
			ywidList.add(wc.getId());
		}
		if(0 != ywidList.size()){
			List<CoverPlate> queryByYwidList = coverPlateMapper.queryBylist(ywidList);
			Map<String, List<CoverPlate>> map = new HashMap<String, List<CoverPlate>>();
			for (CoverPlate coverPlate : queryByYwidList){
				List<CoverPlate> coverPlateList = map.get(coverPlate.getYwid());
				if(null == coverPlateList){
					coverPlateList = new ArrayList<CoverPlate>();
					map.put(coverPlate.getYwid(), coverPlateList);
				}
				coverPlateList.add(coverPlate);
			}
			for (WorkCard wc : workCardList) {
				wc.setCoverPlateList(map.get(wc.getId()));
			}
		}
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<WorkCard>
	 */
	@Override
	public List<WorkCard> doExportExcel(WorkCard paramObj) {
		List<WorkCard> list = workCardMapper.doExportExcel(paramObj);
		List<String> ywidList = new ArrayList<String>();
		for (WorkCard workCard : list) {
			if(null != workCard.getJhgsRs() && null != workCard.getJhgsXss()){
				StringBuffer bzgs = new StringBuffer();
				bzgs.append(workCard.getJhgsRs()).append("人x");
				bzgs.append(workCard.getJhgsXss()).append("时=");
				bzgs.append(workCard.getJhgsRs().multiply(workCard.getJhgsXss())).append("时");
				workCard.getParamsMap().put("bzgs", bzgs);
			}
			formatCoverPlate(workCard);
			ywidList.add(workCard.getId());
		}
		//设置器材工具设备
		if(0 != ywidList.size()){
			List<MaterialTool> maList =  new ArrayList<MaterialTool>();
			for (int i = 0; i < Math.ceil((double) ywidList.size() / SysContext.BATCH_PROCESS_SIZE); i++) {
				MaterialTool mtparam = new MaterialTool();
				mtparam.setDprtcode(paramObj.getDprtcode());
				mtparam.getParamsMap().put("sort", "sort");
				mtparam.getParamsMap().put("ywidList", ywidList.subList(i * SysContext.BATCH_PROCESS_SIZE,
						ywidList.size() < ((i + 1) * SysContext.BATCH_PROCESS_SIZE) ? ywidList.size() : ((i + 1) * SysContext.BATCH_PROCESS_SIZE)));
				maList.addAll(materialToolService.queryEquipmentList(mtparam));
			}
			
			Map<String, List<MaterialTool>> map = new HashMap<String, List<MaterialTool>>();
			for (MaterialTool materialTool : maList){
				formatMaterialTool(materialTool);
				List<MaterialTool> mList = map.get(materialTool.getYwid());
				if(null == mList){
					mList = new ArrayList<MaterialTool>();
					map.put(materialTool.getYwid(), mList);
				}
				mList.add(materialTool);
			}
			for (WorkCard workCard : list) {
				List<MaterialTool> mtList = map.get(workCard.getId());
				if(null == mtList){
					mtList = new ArrayList<MaterialTool>(1);
					mtList.add(new MaterialTool());
				}
				workCard.setMaterialToolList(mtList);
			}
		}
		return list;
	}
	/**
	 * @Description 设置接近/盖板集合
	 * @CreateTime 2017-12-25 下午2:29:29
	 * @CreateBy 刘兵
	 * @param workCard
	 */
	private void formatCoverPlate(WorkCard workCard){
		if(null != workCard.getCoverPlateList() && workCard.getCoverPlateList().size() > 0){
			StringBuffer qy = new StringBuffer();
			StringBuffer jj = new StringBuffer();
			StringBuffer fjzw = new StringBuffer();
			for (CoverPlate coverPlate : workCard.getCoverPlateList()) {
				if(coverPlate.getLx() == 1 && StringUtils.isNotBlank(coverPlate.getGbh())){
					qy.append(coverPlate.getGbh());
				}else if(coverPlate.getLx() == 2 && StringUtils.isNotBlank(coverPlate.getGbh())){
					jj.append(coverPlate.getGbh()).append(",");
				}else if(coverPlate.getLx() == 3 && StringUtils.isNotBlank(coverPlate.getGbh())){
					fjzw.append(coverPlate.getGbh());
				}
			}
			if(jj.length() > 0){
				jj.deleteCharAt(jj.length() - 1);
			}
			workCard.getParamsMap().put("qy", qy);
			workCard.getParamsMap().put("jj", jj);
			workCard.getParamsMap().put("fjzw", fjzw);
		}
	}
	
	/**
	 * @Description 设置器材工具设备
	 * @CreateTime 2017-12-25 下午2:29:29
	 * @CreateBy 刘兵
	 * @param materialTool
	 */
	private void formatMaterialTool(MaterialTool materialTool){
		if(null != materialTool.getHcMainData()){
			String bjlx = MaterialTypeEnum.getName(materialTool.getHcMainData().getHclx());
			if(MaterialTypeEnum.MATERIAL.getId() == materialTool.getHcMainData().getHclx() && null != materialTool.getHcMainData().getHclxEj()){
				bjlx = MaterialSecondTypeEnum.getName(materialTool.getHcMainData().getHclxEj());
			}
			materialTool.getParamsMap().put("bjlx", bjlx);
		}
		if(null != materialTool.getParamsMap().get("tdjxx")){
			StringBuffer hhxx = new StringBuffer();
			String[] arr1 = String.valueOf(materialTool.getParamsMap().get("tdjxx")).split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				hhxx.append(arr2[0]).append(" : ").append(StringUtils.isNotBlank(arr2[1])?arr2[1]:0);
				hhxx.append(" , ").append(arr2[3]);
				if(i != arr1.length - 1){
					hhxx.append("\n");
				}
			}
			materialTool.getParamsMap().put("hhxx", hhxx);
		}
	}
	
	/**
	 * @Description 根据组织机构查询所有有效的工卡数据
	 * @CreateTime 2017年11月15日 下午2:06:06
	 * @CreateBy 胡黄驰
	 * @param String dprtcode
	 * @return List<WorkCard>
	 * */
	public List<WorkCard> findByDprtcode(String dprtcode)
			throws BusinessException {
		return workCardMapper.findByDprtcode(dprtcode);
	}
}
