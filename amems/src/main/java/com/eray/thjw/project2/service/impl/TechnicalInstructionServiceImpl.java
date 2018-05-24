package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.PersonnelReceiptMapper;
import com.eray.thjw.project2.dao.TechnicalInstructionMapper;
import com.eray.thjw.project2.po.Bulletin;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.PersonnelReceipt;
import com.eray.thjw.project2.po.TechnicalInstruction;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TechnicalInstructionService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.project2.service.WorkContentService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.RevMarkEnum;
import enu.produce.NodeEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TechnicalInstructionStatusEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;

@Service
public class TechnicalInstructionServiceImpl implements TechnicalInstructionService {

	@Resource
	private TechnicalInstructionMapper technicalInstructionMapper;
	@Resource
	private SaibongUtilService saibongUtilService;
	@Resource
	private TodoService todoService;
	@Resource
	private CommonService commonService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private WorkContentService workContentService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private TodorsService todorsService;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private PersonnelReceiptMapper personnelReceiptMapper;

	public String insertInstruction(TechnicalInstruction instruction) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		// 获取当前登入人对象
		User user = ThreadVarUtil.getUser();
		// 检查是否有飞机权限存在
		List<String> jxList = new ArrayList<String>();
		jxList.add(instruction.getJx());
		boolean fola = planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList);
		if (!fola) {
			throw new BusinessException("机型权限已变更,请刷新后再进行操作");
		}
		String czls = UUID.randomUUID().toString();
		String instructionId = UUID.randomUUID().toString();// id
		try {
			if(StringUtils.isBlank(instruction.getJszlh())){
				instruction.setJszlh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JSZL.getName()));
			}
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		instruction.setZdrid(user.getId());
		instruction.setId(instructionId); // 主键id
		instruction.setZxbs(RevMarkEnum.INITIAL.getId());
		instruction.setZdsj(new Date()); // 制单时间
		instruction.setZdbmid(user.getBmdm()); // 制单时间
		instruction.setDprtcode(user.getJgdm()); // 组织机构
		technicalInstructionMapper.insertSelective(instruction);// 新增技术
		// 下达指令
		if (instruction.getIsList() != null) {
			List<String> lyidList = doInstructionsource(instruction);
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(instruction.getJx(), lyidList, SendOrderEnum.JSZL.getId(),
					instruction.getZdrid(), instruction.getId(), instruction.getJszlh(), instruction.getBb(),
					instruction.getZhut(), instruction.getZt());
		}
		if (instruction.getZt() == TechnicalInstructionStatusEnum.SAVE.getId()) {
			// 保存附件信息
			saveAttachments(instruction, czls);
			// 新增工作内容
			workContentService.saveWorkContentList(instruction.getWorkContentList(),
					ProjectBusinessEnum.TECHNICAL_ORDER.getId(), instructionId, czls, instructionId,
					instruction.getDprtcode(), LogOperationEnum.SAVE_WO);
			// 新增状态下技术文件日志
			commonRecService.write(instructionId, TableEnum.B_G_004, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, instructionId);
		} else {
			// 保存附件信息
			attachmentService.eidtAttachment(instruction.getAttachments(), instructionId, czls, instructionId,
					instruction.getDprtcode(), LogOperationEnum.SUBMIT_WO);
			// 新增工作内容
			workContentService.saveWorkContentList(instruction.getWorkContentList(),
					ProjectBusinessEnum.TECHNICAL_ORDER.getId(), instructionId, czls, instructionId,
					instruction.getDprtcode(), LogOperationEnum.SUBMIT_WO);
			// 提交状态下技术文件日志
			commonRecService.write(instructionId, TableEnum.B_G_004, user.getId(), czls, LogOperationEnum.SUBMIT_WO,
					UpdateTypeEnum.SAVE, instructionId);
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(instruction.getId());
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
			strSm.append(instruction.getJx());
			strSm.append("机型的");
			strSm.append(instruction.getJszlh());
			strSm.append(" R");
			strSm.append(instruction.getBb());
			strSm.append("技术指令。");
			todoService.insertSelectiveTechnical(instruction,strSm.toString(),"project2:instruction:main:03",NodeEnum.NODE2.getId(),null,TodoEnum.TECHNICAL_ORDER.getId());
			
		}
		return instructionId;
	}

	/**
	 * 
	 * @Description 获取技术指令
	 * @CreateTime 2017年9月5日 下午4:49:47
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @return
	 */
	private List<String> doInstructionsource(TechnicalInstruction instruction) {
		List<String> lyidList = new ArrayList<String>();
		List<Instructionsource> isList = instruction.getIsList();
		for (Instructionsource is : isList) {
			is.setZlbh(instruction.getJszlh());
			is.setZlid(instruction.getId());
			is.setZlxl(ProjectBusinessEnum.TECHNICAL_ORDER.getId());
			is.setZlbb(instruction.getBb());
			is.setYwzt(instruction.getZhut());
			is.setYwdjzt(instruction.getZt());
			lyidList.add(is.getPgdid());
		}
		instructionsourceMapper.insertInstructionSource(isList);
		return lyidList;
	}

	@Override
	public TechnicalInstruction getRecord(String id) {
		return technicalInstructionMapper.selectByPrimaryKey(id);
	}
	/**
	 * @Description 技术指令
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年9月14日 下午6:01:44
	 */
	@Override
	public Map<String, Object> queryAllInstruction(TechnicalInstruction instruction) throws BusinessException {
		String id = instruction.getId();
		instruction.setId("");
		try {
			// 获取登入user
			User user = ThreadVarUtil.getUser();
			instruction.getParamsMap().put("userId", user.getId());
			instruction.getParamsMap().put("userType", user.getUserType());
			PageHelper.startPage(instruction.getPagination());
			List<TechnicalInstruction> list = technicalInstructionMapper.queryInstructionList(instruction);
			if (((Page) list).getTotal() > 0) {
				// 获取关联评估单
				getPgds(list);
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if (!PageUtil.hasRecord(list, id)) {// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						// 在查询条件中增加ID
						TechnicalInstruction newRecord = new TechnicalInstruction();
						newRecord.setId(id);
						newRecord.getParamsMap().put("userId", user.getId());
						List<TechnicalInstruction> newRecordList = technicalInstructionMapper
								.queryInstructionList(newRecord);
						// 获取关联评估单
						getPgds(list);
						if (newRecordList != null && newRecordList.size() == 1) {
							// 将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}

					}
				}
				return PageUtil.pack4PageHelper(list, instruction.getPagination());

			} else {
				List<TechnicalInstruction> newRecordList = new ArrayList<TechnicalInstruction>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					TechnicalInstruction newRecord = new TechnicalInstruction();
					newRecord.setId(id);
					newRecord.getParamsMap().put("userId", user.getId());
					newRecordList = technicalInstructionMapper.queryInstructionList(newRecord);
					// 获取关联评估单
					getPgds(list);
					if (newRecordList != null && newRecordList.size() == 1) {
						return PageUtil.pack(1, newRecordList, instruction.getPagination());
					}

				}
				return PageUtil.pack(0, newRecordList, instruction.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 技术评估单
	 * @CreateTime 2017年9月14日 下午6:02:33
	 * @CreateBy 岳彬彬
	 * @param instructionList
	 */
	private void getPgds(List<TechnicalInstruction> instructionList) {
		List<String> zlidList = new ArrayList<String>();// 指令id集合
		for (TechnicalInstruction instruction : instructionList) {
			zlidList.add(instruction.getId());
		}
		if (zlidList.size() > 0) {
			// 获取达指令集合
			List<Instructionsource> isList = instructionsourceMapper.getListByZlidList(zlidList);
			if (isList.size() > 0) {
				List<Instructionsource> list = null;
				for (TechnicalInstruction instruction : instructionList) {
					list = new ArrayList<Instructionsource>();
					for (Instructionsource instructionsource : isList) {
						if (instructionsource.getZlid().equals(instruction.getId())) {
							list.add(instructionsource);
						}
					}
					if (list.size() > 0) {
						instruction.setIsList(list);
					}
				}
			}
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017年9月6日 上午10:12:46
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateRecord(TechnicalInstruction instruction) throws BusinessException {
		String czls = UUID.randomUUID().toString();
		Date currentDate = commonService.getSysdate();//当前时间
		User user = ThreadVarUtil.getUser();
		// 验证机型是否存在
		validation4Jx(user, instruction);
		// 验证单据状态
		validation4CurrentZt(instruction.getId(),
				Integer.valueOf((String) instruction.getParamsMap().get("currentZt")));
		// 删除旧的下达指令
		instructionsourceMapper.deleteInstructionSourceByZlid(instruction.getId());
		// 删除待办事宜待处理结果
		todorsService.deleteTodorsByYwid(instruction.getId());
		instruction.setZdrid(user.getId());
		instruction.setZdsj(new Date()); // 制单时间
		instruction.setZdbmid(user.getBmdm()); // 制单时间
		// 新增下达指令
		if (instruction.getIsList() != null) {
			List<String> lyidList = doInstructionsource(instruction);
			// 新增待办事宜
			todorsService.insertTodorsList(instruction.getJx(), lyidList, SendOrderEnum.JSZL.getId(),
					instruction.getZdrid(), instruction.getId(), instruction.getJszlh(), instruction.getBb().toString(),
					instruction.getZhut(), instruction.getZt());
		}
		// 编辑附件信息
		attachmentService.eidtAttachment(instruction.getAttachments(), instruction.getId(), czls, instruction.getId(),
				instruction.getDprtcode(), LogOperationEnum.EDIT);
		// 编辑工作内容
		workContentService.updateWorkContentList(instruction.getWorkContentList(),
				ProjectBusinessEnum.TECHNICAL_ORDER.getId(), instruction.getId(), czls, instruction.getId(),
				instruction.getDprtcode(), LogOperationEnum.EDIT);
		technicalInstructionMapper.updateByPrimaryKey(instruction);
		commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE, instruction.getId());

		if(instruction.getZt()==2){ //提交
			
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(instruction.getId());
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
			strSm.append(instruction.getJx());
			strSm.append("机型的");
			strSm.append(instruction.getJszlh());
			strSm.append(" R");
			strSm.append(instruction.getBb());
			strSm.append("技术指令。");
			todoService.insertSelectiveTechnical(instruction,strSm.toString(),"project2:instruction:main:03",NodeEnum.NODE2.getId(),null,TodoEnum.TECHNICAL_ORDER.getId());
		}
		
		
		return instruction.getId();
	}

	/**
	 * 
	 * @Description 验证机型是否存在
	 * @CreateTime 2017年8月15日 上午10:59:51
	 * @CreateBy 岳彬彬
	 * @param user
	 * @param record
	 * @throws BusinessException 
	 */
	private void validation4Jx(User user, TechnicalInstruction instruction) throws BusinessException {
		List<String> jxList = new ArrayList<String>();
		String dprtcode=instruction.getDprtcode();
		if (instruction.getJx() != null && StringUtils.isNotBlank(instruction.getJx())) {
			jxList.add(instruction.getJx());
		} else {
			TechnicalInstruction newinstruction = technicalInstructionMapper.selectByPrimaryKey(instruction.getId());// 审核批准前端没有传机型，需要后端查询
			if (newinstruction != null && StringUtils.isNotBlank(newinstruction.getJx())) {
				jxList.add(newinstruction.getJx());
				dprtcode=newinstruction.getDprtcode();
			}
		}
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(),dprtcode, jxList);
	}
	/**
	 * 
	 * @Description 验证数据状态是否一致
	 * @CreateTime 2017年9月14日 下午6:00:32
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param zt
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(String id, int zt) throws BusinessException {
		Integer bzt = technicalInstructionMapper.getCurrentZt4Validation(id);
		if (bzt == null || bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年9月6日 上午10:12:39
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public String updateBatchAudit(List<String> idList, String yj) throws BusinessException {
		List<String> auditIdList = new ArrayList<String>();
		List<String> jxList = new ArrayList<String>();//用于检查机型权限
		String dprtcode =null;
		StringBuffer buffer = new StringBuffer();
		List<TechnicalInstruction> fItemList = technicalInstructionMapper.queryByIdList(idList);
		for (TechnicalInstruction item : fItemList) {
			if (TechnicalInstructionStatusEnum.SUBMIT.getId() == item.getZt()) {
				auditIdList.add(item.getId());
				dprtcode = item.getDprtcode();
				jxList.add(item.getJx());
				buffer.append("技术指令【").append(item.getJszlh()).append("】审核通过!<br>");
			} else {
				buffer.append("技术指令【").append(item.getJszlh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		if (null != auditIdList && auditIdList.size() > 0) {
			String czls = UUID.randomUUID().toString();// 操作流水
			User user = ThreadVarUtil.getUser();
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			paramMap.put("zt", TechnicalInstructionStatusEnum.AUDITED.getId());
			technicalInstructionMapper.updateBatchAudit(paramMap);// 批量审核
			instructionsourceMapper.updateYwdjztByZlidList(TechnicalInstructionStatusEnum.AUDITED.getId(), auditIdList);// 批量更新下达指令状态
			todorsService.updateZtByYwidList(TechnicalInstructionStatusEnum.AUDITED.getId(), auditIdList);// 批量更新待办事宜数据状态
			commonRecService.write("id", auditIdList, TableEnum.B_G_004, user.getId(), czls,
					LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, null);// 插入日志
			
			//批量新增待办事宜
			todoService.saveBatchTodoAuditList(auditIdList,TodoEnum.TECHNICAL_ORDER.getId());
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年9月6日 上午10:13:28
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public String updateBatchApprove(List<String> idList, String yj) throws BusinessException {
		List<String> auditIdList = new ArrayList<String>();
		List<String> jxList = new ArrayList<String>();//用于检查机型权限
		Date currentDate = commonService.getSysdate();//当前时间
		String dprtcode =null;
		StringBuffer buffer = new StringBuffer();
		List<TechnicalInstruction> fItemList = technicalInstructionMapper.queryByIdList(idList);
		for (TechnicalInstruction item : fItemList) {
			if (TechnicalInstructionStatusEnum.AUDITED.getId() == item.getZt()) {
				auditIdList.add(item.getId());
				dprtcode = item.getDprtcode();
				jxList.add(item.getJx());
				buffer.append("技术指令【").append(item.getJszlh()).append("】批准通过!<br>");
			} else {
				buffer.append("技术指令【").append(item.getJszlh()).append("】数据已更新,未能批准成功!<br>");
			}
		}

		if (null != auditIdList && auditIdList.size() > 0) {
			String czls = UUID.randomUUID().toString();// 操作流水
			User user = ThreadVarUtil.getUser();
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			paramMap.put("zt", TechnicalInstructionStatusEnum.TAKEEFFECT.getId());
			paramMap.put("zxbs", RevMarkEnum.LATEST_VERSION.getId());
			technicalInstructionMapper.updateBatchApprove(paramMap);// 批量批准
			commonRecService.write("id", auditIdList, TableEnum.B_G_004, user.getId(), czls,
					LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, null);// 插入日志
			technicalInstructionMapper.updateZt4sxByIdlist(auditIdList);// 批量生效
			instructionsourceMapper.updateYwdjztByZlidList(TechnicalInstructionStatusEnum.TAKEEFFECT.getId(),
					auditIdList);// 批量更新下达指令状态
			todorsService.updateZtByYwidList(TechnicalInstructionStatusEnum.TAKEEFFECT.getId(), auditIdList);// 批量更新待办事宜数据状态
			// 更新前版本的最新标识为老版本
			technicalInstructionMapper.updateBatchVersionStatus(paramMap);
			
			for (String id : auditIdList) {
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
	 * 
	 * @Description 审核批准
	 * @CreateTime 2017年9月6日 上午10:37:27
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String getReviewAndApproveRecord(TechnicalInstruction instruction) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();
		// 验证机型是否存在
		validation4Jx(user, instruction);
		// 验证单据状态是否已变更
		validation4CurrentZt(instruction.getId(),
				Integer.valueOf((String) instruction.getParamsMap().get("currentZt")));
		if (instruction.getZt() == 3) {// 审核通过
			technicalInstructionMapper.updateShByPrimaryKey(instruction);
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls,
					LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, instruction.getId());
			instructionsourceMapper.updateYwdjztByZlid(instruction.getZt(), instruction.getId());// 更新下达指令业务单号状态
			todorsService.updateYwdjztByYwid(instruction.getZt(), instruction.getId());// 更新待办事宜状态
			
			TechnicalInstruction record1=technicalInstructionMapper.selectByPrimaryKey(instruction.getId());
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record1.getId());
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
			strSm.append(record1.getJx());
			strSm.append("机型的");
			strSm.append(record1.getJszlh());
			strSm.append(" R");
			strSm.append(record1.getBb());
			strSm.append("技术指令。");
			todoService.insertSelectiveTechnical(record1,strSm.toString(),"project2:instruction:main:04",NodeEnum.NODE3.getId(),null,TodoEnum.TECHNICAL_ORDER.getId());
			
		} else if (instruction.getZt() == 5) {// 审核驳回
			technicalInstructionMapper.updateShByPrimaryKey(instruction);
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls,
					LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE, instruction.getId());
			instructionsourceMapper.updateYwdjztByZlid(instruction.getZt(), instruction.getId());// 更新下达指令业务单号状态
			todorsService.updateYwdjztByYwid(instruction.getZt(), instruction.getId());// 更新待办事宜状态
			
			TechnicalInstruction record1=technicalInstructionMapper.selectByPrimaryKey(instruction.getId());
			//拼接说明
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record1.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			StringBuffer strSm = new StringBuffer();
			strSm.append(record1.getJx());
			strSm.append("机型的");
			strSm.append(record1.getJszlh());
			strSm.append(" R");
			strSm.append(record1.getBb());
			strSm.append("技术指令已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(record1,strSm.toString(),"project2:instruction:main:02",NodeEnum.NODE5.getId(),record1.getZdrid(),TodoEnum.TECHNICAL_ORDER.getId());
		} else if (instruction.getZt() == 4) {// 批准通过
			instruction.setZxbs(RevMarkEnum.LATEST_VERSION.getId());// 设置最新标识
			technicalInstructionMapper.updatePzByPrimaryKey(instruction);
			// 生效
			technicalInstructionMapper.updateZt4Sx(instruction.getId());
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls,
					LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, instruction.getId());
			instructionsourceMapper.updateYwdjztByZlid(TechnicalInstructionStatusEnum.TAKEEFFECT.getId(), instruction.getId());// 更新下达指令业务单号状态
			todorsService.updateYwdjztByYwid(TechnicalInstructionStatusEnum.TAKEEFFECT.getId(), instruction.getId());// 更新待办事宜状态
		
			// 更新上个版本的状态为老版本
			TechnicalInstruction current = technicalInstructionMapper.selectByPrimaryKey(instruction.getId());
			if(StringUtils.isNotBlank(current.getfBbid())){
				TechnicalInstruction previous = new TechnicalInstruction();
				previous.setId(current.getfBbid());
				previous.setZxbs(RevMarkEnum.OLD_VERSION.getId());
				technicalInstructionMapper.updateByPrimaryKeySelective(previous);
			}
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(instruction.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
		} else if (instruction.getZt() == 6) {// 批准驳回
			technicalInstructionMapper.updatePzByPrimaryKey(instruction);
			commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls,
					LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE, instruction.getId());
			instructionsourceMapper.updateYwdjztByZlid(instruction.getZt(), instruction.getId());// 更新下达指令业务单号状态
			todorsService.updateYwdjztByYwid(instruction.getZt(), instruction.getId());// 更新待办事宜状态
			
			TechnicalInstruction record1=technicalInstructionMapper.selectByPrimaryKey(instruction.getId());
			
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record1.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(record1.getJx());
			strSm.append("机型的");
			strSm.append(record1.getJszlh());
			strSm.append(" R");
			strSm.append(record1.getBb());
			strSm.append("技术指令已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(record1,strSm.toString(),"project2:instruction:main:02",NodeEnum.NODE5.getId(),record1.getZdrid(),TodoEnum.TECHNICAL_ORDER.getId());
		}			
		return instruction.getId();
	}

	/**
	 * 
	 * @Description 作废
	 * @CreateTime 2017年9月6日 上午11:13:22
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(TechnicalInstruction instruction) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 验证单据状态是否已变更
		validation4CurrentZt(instruction.getId(),
				Integer.valueOf((String) instruction.getParamsMap().get("currentZt")));
		commonRecService.writeByWhere("b.ywid = '".concat(instruction.getId()).concat("' ").concat("and b.zt=1"),
				TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,
				instruction.getId());
		commonRecService.write(instruction.getId(), TableEnum.B_G_004, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, instruction.getId());
		commonRecService.writeByWhere(" b.mainid = '".concat(instruction.getId()).concat("' ").concat("and b.yxzt=1"),
				TableEnum.D_011, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,
				instruction.getId());
		attachmentMapper.updateByMainid(instruction.getId());// 作废对应附件
		TechnicalInstruction dbData = technicalInstructionMapper.selectByPrimaryKey(instruction.getId());	// 数据库技术指令数据
		technicalInstructionMapper.deleteByPrimaryKey(instruction.getId());// 作废对应技术指令
		todorsService.deleteTodorsByYwid(instruction.getId());// 删除待办事宜待处理结果
		instructionsourceMapper.deleteInstructionSourceByZlid(instruction.getId());// 删除下达指令
		
		// 清除前版本的后版本id
		if(StringUtils.isNotBlank(dbData.getfBbid())){
			TechnicalInstruction previous = new TechnicalInstruction();
			previous.setId(dbData.getfBbid());
			previous.setbBbid(null);
			technicalInstructionMapper.updatePrivousVersionStatus(previous);
		}
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(instruction.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	}

	@Override
	public void update4Close(TechnicalInstruction instruction) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证单据状态
		validation4CurrentZt(instruction.getId(),
				Integer.valueOf((String) instruction.getParamsMap().get("currentZt")));
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		instruction.setZdjsrid(user.getId());
		technicalInstructionMapper.updateByPrimaryKeySelective(instruction);
		commonRecService.write(instruction.getId(), TableEnum.B_G_004,
				user.getId(), czls, instruction.getZt() == TechnicalInstructionStatusEnum.CLOSETOEND.getId()
						? LogOperationEnum.GUANBI : LogOperationEnum.WANCHEN,
				UpdateTypeEnum.UPDATE, instruction.getId());
		instructionsourceMapper.updateYwdjztByZlid(instruction.getZt(), instruction.getId());// 更新下达指令业务单号状态
		todorsService.updateYwdjztByYwid(instruction.getZt(), instruction.getId());// 更新待办事宜状态
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(instruction.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	}

	@Override
	public void updateZt4Isjs(TechnicalInstruction instruction) {
		User user = ThreadVarUtil.getUser();
		if (instruction.getJszt() != null && instruction.getJszt() == 0
				&& user.getId().equals(instruction.getJsrid())) {
			technicalInstructionMapper.updateJsztById(instruction.getId());
			PersonnelReceipt pr = new PersonnelReceipt();
			pr.setId(UUID.randomUUID().toString());
			pr.setDprtcode(instruction.getDprtcode());
			pr.setYwlx(ProjectBusinessEnum.TECHNICAL_ORDER.getId());// 业务类型
			pr.setYwid(instruction.getId());// 业务id
			pr.setJsbmid(user.getBmdm());// 接收部门
			pr.setJssj(new Date());// 制单时间
			pr.setJsrid(user.getId());// 制单人
			personnelReceiptMapper.insertSelective(pr);
		}
	}

	/**
	 * @Description 查询历史版本
	 * @CreateTime 2018年2月23日 下午3:04:52
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public List<TechnicalInstruction> queryHistoryList(String id) {
		return technicalInstructionMapper.queryHistoryList(id);
	}

	/**
	 * @Description 改版技术指令
	 * @CreateTime 2018年2月24日 上午10:31:34
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public String doRevision(TechnicalInstruction record)
			throws BusinessException {
		// 新增当前版本的数据
		String currentId = insertInstruction(record);
		
		// 修改前版本的后版本id和最新标识
		TechnicalInstruction previous = new TechnicalInstruction();
		previous.setId(record.getfBbid());
		previous.setbBbid(currentId);
		technicalInstructionMapper.updatePrivousVersionStatus(previous);
		
		return currentId;
	}
	
	/**
	 * @Description 保存附件
	 * @CreateTime 2018年2月24日 下午2:19:24
	 * @CreateBy 韩武
	 * @param record
	 * @param czls
	 */
	private void saveAttachments(TechnicalInstruction record, String czls){
		if(StringUtils.isBlank(record.getfBbid())){	// 新增/修改
			attachmentService.eidtAttachment(record.getAttachments(), record.getId(), 
					czls, record.getId(), record.getDprtcode(), LogOperationEnum.SAVE_WO);
		}else{	// 改版
			attachmentService.saveAttachment4Modify(record.getAttachments(), record.getfBbid(), record.getId(),
					czls, record.getId(), record.getDprtcode(), LogOperationEnum.REVISION);
		}
	}
}
