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
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.BulletinMapper;
import com.eray.thjw.project2.dao.DistributionDepartmentMapper;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.dao.PersonnelReceiptMapper;
import com.eray.thjw.project2.po.Bulletin;
import com.eray.thjw.project2.po.DistributionDepartment;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.PersonnelReceipt;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.BulletinService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.RevMarkEnum;
import enu.common.WhetherEnum;
import enu.produce.NodeEnum;
import enu.project2.BulletinStatusEnum;
import enu.project2.DistributionDepartmentTypeEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;

/**
 * @author 岳彬彬
 * @Description 技术通告接口实现类
 * @CreateTime 2017年8月12日 上午10:18:58
 *
 */
@Service
public class BulletinServiceImpl implements BulletinService {

	@Resource
	private BulletinMapper bulletinMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private TodoService todoService;
	@Resource
	private AttachmentMapper attachmentMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	@Resource
	private SaibongUtilService saibongUtilService;
	@Resource
	private DistributionDepartmentMapper distributionDepartmentMapper;
	@Resource
	private PersonnelReceiptMapper personnelReceiptMapper;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private TodorsService todorsService;

	/**
	 * @CreateBy 岳彬彬
	 * @description 获取技术通告列表
	 * @param record
	 */
	@Override
	public Map<String, Object> selectBulletinList(Bulletin record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<Bulletin> bulletinList = bulletinMapper.selectBulletinList(record);
			if (((Page) bulletinList).getTotal() > 0) {
				// 获取关联评估单
				getPgds(bulletinList);
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(bulletinList, id)) {
						// 在查询条件中增加ID
						Bulletin newRecord = new Bulletin();
						newRecord.setId(id);
						List<Bulletin> newRecordList = bulletinMapper.selectBulletinList(newRecord);
						// 获取关联评估单
						getPgds(newRecordList);
						if (newRecordList != null && newRecordList.size() == 1) {
							bulletinList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(bulletinList, record.getPagination());
				return resultMap;

			} else {
				List<Bulletin> newRecordList = new ArrayList<Bulletin>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Bulletin newRecord = new Bulletin();
					newRecord.setId(id);
					newRecordList = bulletinMapper.selectBulletinList(newRecord);
					// 获取关联评估单
					getPgds(newRecordList);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}

	/**
	 * 
	 * @CreateBy 岳彬彬
	 * @description 新增技术通告
	 * @param record
	 */
	@Override
	public String insertSelective(Bulletin record) throws Exception {
		User user = ThreadVarUtil.getUser();
		Date currentDate = commonService.getSysdate();//当前时间
		String id = UUID.randomUUID().toString();
		record.setId(id);
		record.setZxbs(RevMarkEnum.INITIAL.getId());
		String czls = UUID.randomUUID().toString();
		// 验证机型是否存在
		validation4Jx(user, record);
		if (record.getJstgh() == null || "".equals(record.getJstgh())) {
			boolean b = true;
			while (b) {
				String jstgh;
				try {
					jstgh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JSTB.getName());
					record.setJstgh(jstgh);
					int bcount = bulletinMapper.getCount4Validation(record);
					if (bcount == 0) {
						b = false;
					}
				} catch (SaibongException e) {
					throw new RuntimeException(e);
				}
			}
		} else {
			// 验证技术通告编号+版本 唯一
			validation(record);
		}
		// 分发部门
		if (record.getParamsMap().get("department") != null && !"".equals(record.getParamsMap().get("department"))) {
			doAddDepartment(record, czls, user, (String) record.getParamsMap().get("department"));
		}
		// 下达指令
		if (record.getIsList() != null) {
			List<String> lyidList = doInstructionsource(record);
			
			
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(record.getJx(), lyidList, SendOrderEnum.JSTG.getId(), record.getZdrid(),
					record.getId(), record.getJstgh(), record.getBb().toString(), record.getZhut(), record.getZt());
		}
		// 附件
		saveAttachments(record, czls, user);
		bulletinMapper.insertSelective(record);
		if (record.getZt() == BulletinStatusEnum.SAVE.getId()) {
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, record.getId());
		} else {
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record.getId());
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
			strSm.append(record.getJx());
			strSm.append("机型的");
			strSm.append(record.getJstgh());
			strSm.append(" R");
			strSm.append(record.getBb());
			strSm.append("维护提示。");
			todoService.insertSelectiveTechnical(record,strSm.toString(),"project2:bulletin:03",NodeEnum.NODE2.getId(),null,TodoEnum.TECHNICAL_BULLETIN.getId());
			
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.SUBMIT_WO,
					UpdateTypeEnum.SAVE, record.getId());
		}
		return id;
	}

	/**
	 * @CreateBy 岳彬彬
	 * @description 更新技术通告数据
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public String updateRecord(Bulletin record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证机型是否存在
		validation4Jx(user, record);
		// 验证单据状态是否已变更
		validation4CurrentZt(record.getId(), Integer.valueOf((String) record.getParamsMap().get("currentZt")));
		// 新增分发部门
		if (record.getParamsMap().get("add") != null && !"".equals(record.getParamsMap().get("add"))) {
			doAddDepartment(record, czls, user, (String) record.getParamsMap().get("add"));
		}
		// 删除分发部门
		if (record.getParamsMap().get("del") != null && !"".equals(record.getParamsMap().get("del"))) {
			doDelDepartment(record, czls, user);
		}
		// 删除旧的下达指令
		instructionsourceMapper.deleteInstructionSourceByZlid(record.getId());
		// 删除待办事宜待处理结果
		todorsService.deleteTodorsByYwid(record.getId());
		// 新增下达指令
		if (record.getIsList() != null) {
			List<String> lyidList = doInstructionsource(record);
			
			
			
			// 新增待办事宜
			todorsService.insertTodorsList(record.getJx(), lyidList, SendOrderEnum.JSTG.getId(), record.getZdrid(),
					record.getId(), record.getJstgh(), record.getBb().toString(), record.getZhut(), record.getZt());
		}
		// 处理附件
		doAttachmentList(record, user, czls);
		// 更新技术通告
		bulletinMapper.updateByPrimaryKey(record);
		if (record.getZt() == BulletinStatusEnum.SAVE.getId() || record.getZt() == BulletinStatusEnum.AUDITDOWN.getId()
				|| record.getZt() == BulletinStatusEnum.APPROVALDOWN.getId()) {
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, record.getId());
		} else {
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record.getId());
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
			strSm.append(record.getJx());
			strSm.append("机型的");
			strSm.append(record.getJstgh());
			strSm.append(" R");
			strSm.append(record.getBb());
			strSm.append("维护提示。");
			todoService.insertSelectiveTechnical(record,strSm.toString(),"project2:bulletin:03",NodeEnum.NODE2.getId(),null,TodoEnum.TECHNICAL_BULLETIN.getId());
			
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.SUBMIT_WO,
					UpdateTypeEnum.SAVE, record.getId());
		}
		return record.getId();
	}

	/**
	 * @CreateBy 岳彬彬
	 * @description 获取评估单
	 * @param bulletinList
	 */
	private void getPgds(List<Bulletin> bulletinList) {
		List<String> zlidList = new ArrayList<String>();// 指令id集合
		for (Bulletin bulletin : bulletinList) {
			zlidList.add(bulletin.getId());
		}
		if (zlidList.size() > 0) {
			// 获取达指令集合
			List<Instructionsource> isList = instructionsourceMapper.getListByZlidList(zlidList);
			if (isList.size() > 0) {
				List<Instructionsource> list = null;
				for (Bulletin bulletin : bulletinList) {
					list = new ArrayList<Instructionsource>();
					for (Instructionsource instructionsource : isList) {
						if (instructionsource.getZlid().equals(bulletin.getId())) {
							list.add(instructionsource);
						}
					}
					if (list.size() > 0) {
						bulletin.setIsList(list);
					}
				}
			}
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @description 处理下达指令
	 * @param record
	 */
	private List<String> doInstructionsource(Bulletin record) {
		List<String> lyidList = new ArrayList<String>();
		List<Instructionsource> isList = record.getIsList();
		for (Instructionsource is : isList) {
			is.setZlbh(record.getJstgh());
			is.setZlid(record.getId());
			is.setZlxl(ProjectBusinessEnum.TECHNICAL_BULLETIN.getId());
			is.setZlbb(record.getBb().toString());
			is.setYwzt(record.getZhut());
			is.setYwdjzt(record.getZt());
			lyidList.add(is.getPgdid());
		}
		instructionsourceMapper.insertInstructionSource(isList);
		return lyidList;
	}

	/**
	 * @CreateBy 岳彬彬
	 * @description 根据id获取技术通告数据
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Bulletin getRecord(String id) throws BusinessException {
		Bulletin bulletin = bulletinMapper.selectByPrimaryKey(id);
		if (bulletin == null) {
			throw new BusinessException("该维护提示不存在，请刷新列表页面!");
		}
		Attachment attachment = new Attachment();
		attachment.setMainid(id);
		List<Attachment> attachemntList = attachmentMapper.queryListAttachments(attachment);
		if (attachemntList.size() > 0) {
			bulletin.setAttachments(attachemntList);
		}
		List<DistributionDepartment> dDepartmentList = distributionDepartmentMapper.getDepartmentByYwid(id);
		if (dDepartmentList.size() > 0) {
			bulletin.setdDepartmentList(dDepartmentList);
		}
		return bulletin;
	}

	/**
	 * @CreateBy 岳彬彬
	 * @description 处理新增分发部门
	 * @CreateTime 2017年8月12日 下午4:00:00
	 * @param record
	 */
	private void doAddDepartment(Bulletin record, String czls, User user, String departments) {
		DistributionDepartment ddepartment = null;
		List<String> ddepartmentIdList = new ArrayList<String>();
		List<DistributionDepartment> list = new ArrayList<DistributionDepartment>();
		if (departments.indexOf(",") > 0) {
			String[] strs = departments.split(",");
			for (String string : strs) {
				ddepartment = new DistributionDepartment();
				ddepartment.setId(UUID.randomUUID().toString());
				ddepartment.setDprtcode(record.getDprtcode());
				ddepartment.setYwlx(ProjectBusinessEnum.TECHNICAL_BULLETIN.getId());// 业务类型
				ddepartment.setYwid(record.getId());// 业务id
				ddepartment.setLx(DistributionDepartmentTypeEnum.department.getId());// 类型1
																						// 部门
				ddepartment.setDxid(string);// 对象id
				ddepartment.setIsJs(WhetherEnum.NO.getId());// 接收标识:否
				ddepartment.setZt(EffectiveEnum.YES.getId());// 状态
				ddepartment.setWhrid(user.getId());
				ddepartment.setWhdwid(user.getBmdm());
				ddepartment.setWhsj(new Date());
				ddepartmentIdList.add(ddepartment.getId());
				list.add(ddepartment);
			}
		} else {
			ddepartment = new DistributionDepartment();
			ddepartment.setId(UUID.randomUUID().toString());
			ddepartment.setDprtcode(record.getDprtcode());
			ddepartment.setYwlx(ProjectBusinessEnum.TECHNICAL_BULLETIN.getId());// 业务类型
			ddepartment.setYwid(record.getId());// 业务id
			ddepartment.setLx(DistributionDepartmentTypeEnum.department.getId());// 类型1
																					// 部门
			ddepartment.setDxid(departments);// 对象id
			ddepartment.setIsJs(WhetherEnum.NO.getId());// 接收标识:否
			ddepartment.setZt(EffectiveEnum.YES.getId());// 状态：1
			ddepartment.setWhrid(user.getId());
			ddepartment.setWhdwid(user.getBmdm());
			ddepartment.setWhsj(new Date());
			ddepartmentIdList.add(ddepartment.getId());
			list.add(ddepartment);
		}
		distributionDepartmentMapper.insertDistributionDepartment(list);
		commonRecService.write("id", ddepartmentIdList, TableEnum.B_G2_998, user.getId(), czls,
				LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, record.getId());
	}

	/**
	 * 
	 * @CreateBy 岳彬彬
	 * @description 处理新增技术通告附件
	 * @param record
	 * @param czls
	 * @param user
	 */
	private void doAttachment(Bulletin record, String czls, User user) {
		List<Attachment> list = record.getAttachments();
		if (list != null && !list.isEmpty()) {
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : list) {
				attachment.setMainid(record.getId());
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, record.getId());
		}
	}

	/**
	 * 
	 * @author yuebinbin
	 * @description 处理删除的部门
	 * @param record
	 * @param czls
	 * @param user
	 */
	private void doDelDepartment(Bulletin record, String czls, User user) {
		String departments = (String) record.getParamsMap().get("del");
		List<String> list = new ArrayList<String>();
		if (departments.indexOf(",") > 0) {
			String[] strs = departments.split(",");
			for (String string : strs) {
				list.add(string);
			}
		} else {
			list.add(departments);
		}
		distributionDepartmentMapper.updateZtByIds(list);
		commonRecService.write("id", list, TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, record.getId());
	}

	/**
	 * @CreateBy 岳彬彬
	 * @Description 处理修改技术通告的附件
	 * @CreateTime 2017年8月12日 上午10:15:25
	 * @param record
	 * @param user
	 * @param czls
	 */
	private void doAttachmentList(Bulletin record, User user, String czls) {
		List<Attachment> attachments = record.getAttachments();
		if (attachments != null && !attachments.isEmpty()) {
			for (Attachment attachment : attachments) {
				// 新增附件
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(record.getId());
					attachment.setDprtcode(record.getDprtcode());
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE, record.getId());
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {// 删除附件
					attachmentMapper.updateByPrimaryKey(attachment.getId());
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, record.getId());
				} else if (attachment.getOperate().equals(OperateEnum.EDIT)) {

				}
			}
		}
	}

	/**
	 * @Description 验证技术通告编号+版本 唯一
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月12日 上午10:15:10
	 * @param Record
	 * @throws BusinessException
	 */
	private void validation(Bulletin record) throws BusinessException {
		int bcount = bulletinMapper.getCount4Validation(record);
		if (bcount > 0) {
			throw new BusinessException("该维护提示编号+版本已存在!");
		}
	}

	/**
	 * 
	 * @Description 审核批准
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月12日 上午10:14:30
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public String getReviewAndApproveRecord(Bulletin record) throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();
		// 验证机型是否存在
		validation4Jx(user, record);
		// 验证单据状态是否已变更
		validation4CurrentZt(record.getId(), Integer.valueOf((String) record.getParamsMap().get("currentZt")));
		if (record.getZt() == 3) {// 审核通过
			
			bulletinMapper.updateShByPrimaryKey(record);
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.YISHENHE_WO,
					UpdateTypeEnum.UPDATE, record.getId());
			
			Bulletin record1=bulletinMapper.selectByPrimaryKey(record.getId());
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
			strSm.append(record1.getJstgh());
			strSm.append(" R");
			strSm.append(record1.getBb());
			strSm.append("维护提示。");
			todoService.insertSelectiveTechnical(record1,strSm.toString(),"project2:bulletin:04",NodeEnum.NODE3.getId(),null,TodoEnum.TECHNICAL_BULLETIN.getId());
			
		} else if (record.getZt() == 5) {// 审核驳回
			bulletinMapper.updateShByPrimaryKey(record);
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls,
					LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE, record.getId());
			Bulletin record1=bulletinMapper.selectByPrimaryKey(record.getId());
			
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
			strSm.append(record1.getJx());
			strSm.append("机型的");
			strSm.append(record1.getJstgh());
			strSm.append(" R");
			strSm.append(record1.getBb());
			strSm.append("维护提示已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(record1,strSm.toString(),"project2:eo:main:02",NodeEnum.NODE5.getId(),record1.getZdrid(),TodoEnum.TECHNICAL_BULLETIN.getId());
		} else if (record.getZt() == 4) {// 批准通过
			
			record.setZxbs(RevMarkEnum.LATEST_VERSION.getId());// 设置最新标识
			bulletinMapper.updatePzByPrimaryKey(record);
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.YIPIZHUN_WO,
					UpdateTypeEnum.UPDATE, record.getId());
		
			// 更新上个版本的状态为老版本
			Bulletin current = bulletinMapper.selectByPrimaryKey(record.getId());
			if(StringUtils.isNotBlank(current.getfBbid())){
				Bulletin previous = new Bulletin();
				previous.setId(current.getfBbid());
				previous.setZxbs(RevMarkEnum.OLD_VERSION.getId());
				bulletinMapper.updateByPrimaryKeySelective(previous);
			}
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
		} else if (record.getZt() == 6) {// 批准驳回
			bulletinMapper.updatePzByPrimaryKey(record);
			commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls,
					LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE, record.getId());
			Bulletin record1=bulletinMapper.selectByPrimaryKey(record.getId());
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record.getId());
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
			strSm.append(record1.getJstgh());
			strSm.append(" R");
			strSm.append(record1.getBb());
			strSm.append("维护提示已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(record1,strSm.toString(),"project2:eo:main:02",NodeEnum.NODE5.getId(),record1.getZdrid(),TodoEnum.TECHNICAL_BULLETIN.getId());
		}
		instructionsourceMapper.updateYwdjztByZlid(record.getZt(), record.getId());// 更新下达指令业务单号状态
		todorsService.updateYwdjztByYwid(record.getZt(), record.getId());// 更新待办事宜状态
		return record.getId();
	}

	/**
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月12日 上午10:13:48
	 * @Description 作废
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(Bulletin record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 验证单据状态是否已变更
		validation4CurrentZt(record.getId(), Integer.valueOf((String) record.getParamsMap().get("currentZt")));
		commonRecService.writeByWhere("b.ywid = '".concat(record.getId()).concat("' ").concat("and b.zt=1"),
				TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, record.getId());
		commonRecService.write(record.getId(), TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, record.getId());
		commonRecService.writeByWhere(" b.mainid = '".concat(record.getId()).concat("' ").concat("and b.yxzt=1"),
				TableEnum.D_011, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, record.getId());
		distributionDepartmentMapper.updateZtByYwid(record.getId());// 作废对应分发部门
		attachmentMapper.updateByMainid(record.getId());// 作废对应附件
		Bulletin dbData = bulletinMapper.selectByPrimaryKey(record.getId());	// 数据库维护提示数据
		bulletinMapper.deleteByPrimaryKey(record.getId());// 作废对应技术通告
		todorsService.deleteTodorsByYwid(record.getId());// 删除待办事宜待处理结果
		instructionsourceMapper.deleteInstructionSourceByZlid(record.getId());// 删除下达指令
		
		// 清除前版本的后版本id
		if(StringUtils.isNotBlank(dbData.getfBbid())){
			Bulletin previous = new Bulletin();
			previous.setId(dbData.getfBbid());
			previous.setbBbid(null);
			bulletinMapper.updatePrivousVersionStatus(previous);
		}
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(record.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	}

	/**
	 * @throws Exception
	 * @CreateBy 岳彬彬
	 * @description 圈阅
	 * @CreateTime 2017年8月12日 上午10:13:23
	 * @UpdateBy 岳彬彬
	 * @UpdateTime 2017年8月14日 上午10:10:53
	 */
	@Override
	public void view4IsCirculuation(String id) throws Exception {
		User user = ThreadVarUtil.getUser();
		if(StringUtils.isBlank(user.getBmdm())){
			return;
		}
		// 查询当前该数据对应的分发部门信息
		DistributionDepartment dDepartment = distributionDepartmentMapper.getDepartmentByYwidAndDxid4Circulation(id,
				user.getBmdm());
		// 当前登录用户所在部门与该技术通告部门是同一个部门且未圈阅且是批准状态
		if(dDepartment == null){
			return;
		}
		if (dDepartment.getIsJs() == 0) {
			dDepartment.setWhrid(user.getId());
			distributionDepartmentMapper.updateisJsByid(dDepartment);// 更新标识为已阅
			PersonnelReceipt personnelReceipt = new PersonnelReceipt();// 人员接收表
			personnelReceipt.setId(UUID.randomUUID().toString());
			personnelReceipt.setDprtcode(dDepartment.getDprtcode());
			personnelReceipt.setYwlx(ProjectBusinessEnum.TECHNICAL_BULLETIN.getId());// 业务类型
			personnelReceipt.setYwid(id);// 业务id
			personnelReceipt.setJsbmid(dDepartment.getDxid());// 接收部门
			personnelReceipt.setJssj(new Date());// 制单时间
			personnelReceipt.setJsrid(user.getId());// 制单人
			personnelReceiptMapper.insertSelective(personnelReceipt);
			String czls = UUID.randomUUID().toString();
			commonRecService.write(dDepartment.getId(), TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, id);
			commonRecService.write(id, TableEnum.B_G2_002, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, id);
		}else{
			PersonnelReceipt pr = personnelReceiptMapper.getPersonnelReceipt4Validation(id, user.getId());
			if (pr == null && dDepartment != null) {
				pr = new PersonnelReceipt();
				pr.setId(UUID.randomUUID().toString());
				pr.setDprtcode(dDepartment.getDprtcode());
				pr.setYwlx(ProjectBusinessEnum.TECHNICAL_BULLETIN.getId());// 业务类型
				pr.setYwid(id);// 业务id
				pr.setJsbmid(dDepartment.getDxid());// 接收部门
				pr.setJssj(new Date());// 制单时间
				pr.setJsrid(user.getId());// 制单人
				personnelReceiptMapper.insertSelective(pr);
			}
		}
	}

	/**
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月12日 上午11:43:44
	 * @Description 通告传阅
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getCirlationList(Bulletin record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<Bulletin> bulletinList = bulletinMapper.getCirlationList(record);
			if (((Page) bulletinList).getTotal() > 0) {
				// 获取关联评估单
				getPgds(bulletinList);
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(bulletinList, id)) {
						// 在查询条件中增加ID
						Bulletin newRecord = new Bulletin();
						newRecord.setId(id);
						List<Bulletin> newRecordList = bulletinMapper.getCirlationList(newRecord);
						// 获取关联评估单
						getPgds(newRecordList);
						if (newRecordList != null && newRecordList.size() == 1) {
							bulletinList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(bulletinList, record.getPagination());
				return resultMap;

			} else {
				List<Bulletin> newRecordList = new ArrayList<Bulletin>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Bulletin newRecord = new Bulletin();
					newRecord.setId(id);
					newRecordList = bulletinMapper.getCirlationList(newRecord);
					// 获取关联评估单
					getPgds(newRecordList);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}

	/**
	 * 
	 * @Description 验证当前单据状态跟页面单据状态是否一致
	 * @CreateTime 2017年8月15日 上午10:40:07
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param zt
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(String id, int zt) throws BusinessException {
		Integer bzt = bulletinMapper.getCurrentZt4Validation(id);
		if (bzt == null || bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
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
	private void validation4Jx(User user, Bulletin record) throws BusinessException {
		List<String> jxList = new ArrayList<String>();
		String dprtcode=record.getDprtcode();
		if (record.getJx() != null && StringUtils.isNotBlank(record.getJx())) {
			jxList.add(record.getJx());
		} else {
			Bulletin newRecord = bulletinMapper.selectByPrimaryKey(record.getId());// 审核批准前端没有传机型，需要后端查询
			if (newRecord != null && StringUtils.isNotBlank(newRecord.getJx())) {
				jxList.add(newRecord.getJx());
				dprtcode=newRecord.getDprtcode();
			}
		}
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
	}

	/**
	 * 
	 * @Description 获取分发部门
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月22日 上午9:33:40
	 * @param id
	 */
	@Override
	public List<DistributionDepartment> getDistributionDepartment(String id) {

		return distributionDepartmentMapper.getDepartmentByYwid(id);
	}

	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月22日 上午9:32:44
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
		List<Bulletin> fItemList = bulletinMapper.queryByIdList(idList);
		for (Bulletin item : fItemList) {
			if (2 == item.getZt()) {
				dprtcode = item.getDprtcode();
				auditIdList.add(item.getId());
				jxList.add(item.getJx());
				buffer.append("维护提示【").append(item.getJstgh()).append("】审核通过!<br>");
			} else {
				buffer.append("维护提示【").append(item.getJstgh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		if (null != auditIdList && auditIdList.size() > 0) {
			User user = ThreadVarUtil.getUser();
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
			String czls = UUID.randomUUID().toString();// 操作流水			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("zt", BulletinStatusEnum.AUDIT.getId());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			bulletinMapper.updateBatchAudit(paramMap);
			instructionsourceMapper.updateYwdjztByZlidList(BulletinStatusEnum.AUDIT.getId(), auditIdList);// 批量更新下达指令状态
			todorsService.updateZtByYwidList(BulletinStatusEnum.AUDIT.getId(), auditIdList);// 批量更新待办事宜数据状态
			commonRecService.write("id", auditIdList, TableEnum.B_G2_002, user.getId(), czls,
					LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, null);// 插入日志
			
			//批量新增待办事宜
			todoService.saveBatchTodoAuditList(auditIdList,TodoEnum.TECHNICAL_BULLETIN.getId());
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年8月22日 上午9:32:55
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
		StringBuffer buffer = new StringBuffer();
		String dprtcode=null;
		List<Bulletin> fItemList = bulletinMapper.queryByIdList(idList);
		for (Bulletin item : fItemList) {
			if (BulletinStatusEnum.AUDIT.getId() == item.getZt()) {
				dprtcode = item.getDprtcode();
				auditIdList.add(item.getId());
				jxList.add(item.getJx());
				buffer.append("维护提示【").append(item.getJstgh()).append("】批准通过!<br>");
			} else {
				buffer.append("维护提示【").append(item.getJstgh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		if (null != auditIdList && auditIdList.size() > 0) {
			User user = ThreadVarUtil.getUser();
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
			String czls = UUID.randomUUID().toString();// 操作流水
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("zt", BulletinStatusEnum.APPROVAL.getId());
			paramMap.put("idList", auditIdList);
			paramMap.put("pfyj", yj);
			bulletinMapper.updateBatchApprove(paramMap);
			instructionsourceMapper.updateYwdjztByZlidList(BulletinStatusEnum.APPROVAL.getId(), auditIdList);// 批量更新下达指令状态
			
			
			todorsService.updateZtByYwidList(BulletinStatusEnum.APPROVAL.getId(), auditIdList);// 批量更新待办事宜数据状态
			commonRecService.write("id", auditIdList, TableEnum.B_G2_002, user.getId(), czls,
					LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, null);// 插入日志
			
			// 更新前版本的最新标识为老版本
			bulletinMapper.updateBatchVersionStatus(paramMap);
			
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
	 * @Description 查询历史版本
	 * @CreateTime 2018年2月23日 下午3:04:52
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public List<Bulletin> queryHistoryList(String id) {
		return bulletinMapper.queryHistoryList(id);
	}

	/**
	 * @Description 改版维护提示
	 * @CreateTime 2018年2月24日 上午10:31:34
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws Exception 
	 */
	@Override
	public String doRevision(Bulletin record) throws Exception {
		
		// 新增当前版本的数据
		String currentId = insertSelective(record);
		
		// 修改前版本的后版本id和最新标识
		Bulletin previous = new Bulletin();
		previous.setId(record.getfBbid());
		previous.setbBbid(currentId);
		bulletinMapper.updatePrivousVersionStatus(previous);
		
		return currentId;
	}
	
	/**
	 * @Description 保存附件
	 * @CreateTime 2018年2月24日 下午2:19:24
	 * @CreateBy 韩武
	 * @param record
	 * @param czls
	 * @param user
	 */
	private void saveAttachments(Bulletin record, String czls, User user){
		if(StringUtils.isBlank(record.getfBbid())){	// 新增/修改
			attachmentService.eidtAttachment(record.getAttachments(), record.getId(), 
					czls, record.getId(), record.getDprtcode(), LogOperationEnum.SAVE_WO);
		}else{	// 改版
			attachmentService.saveAttachment4Modify(record.getAttachments(), record.getfBbid(), record.getId(),
					czls, record.getId(), record.getDprtcode(), LogOperationEnum.REVISION);
		}
	}

}
