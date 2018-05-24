package com.eray.thjw.project2.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.MonitoringLast;
import com.eray.thjw.produce.po.MonitoringObject;
import com.eray.thjw.produce.po.MonitoringPlan;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.dao.ProductionOrderMapper;
import com.eray.thjw.project2.dao.ProductionOrderMonitorMapper;
import com.eray.thjw.project2.po.ProductionOrder;
import com.eray.thjw.project2.po.ProductionOrderMonitor;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.ProductionOrderMonitorService;
import com.eray.thjw.project2.service.ProductionOrderPlaneService;
import com.eray.thjw.project2.service.ProductionOrderService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.RevMarkEnum;
import enu.produce.MaintenanceTypeEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoStatusEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.MonitorProjectUnitEnum;
import enu.project2.ProductionOrderStatusEnum;
import enu.project2.TodoEnum;

/**
 * @Description 生产指令服务实现类
 * @CreateTime 2018年5月3日 下午3:55:12
 * @CreateBy 韩武
 */
@Service
public class ProductionOrderServiceImpl implements ProductionOrderService {
	
	@Resource
	private ProductionOrderMapper productionOrderMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private ProductionOrderMonitorService productionOrderMonitorService;
	
	@Resource
	private ProductionOrderPlaneService productionOrderPlaneService;
	
	@Resource
	private ProcessRecordService processRecordService;
	
	@Resource
	private TodoService todoService;
	
	@Resource
	private MonitorDataService monitorDataService;
	
	@Resource
	private ProductionOrderMonitorMapper productionOrderMonitorMapper;

	/**
	 * @Description 查询生产指令分页列表
	 * @CreateTime 2018年5月4日 上午10:27:07
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	@Override
	public Map<String, Object> queryPageableList(ProductionOrder order) {
		
		User user = ThreadVarUtil.getUser();
		order.getParamsMap().put("userId", user.getId());
		order.getParamsMap().put("userType", user.getUserType());
		
		PageHelper.startPage(order.getPagination());
		List<ProductionOrder> list = productionOrderMapper.queryList(order);
		return PageUtil.pack4PageHelper(list, order.getPagination());
	}

	/**
	 * @Description 保存生产指令
	 * @CreateTime 2018年5月3日 下午3:56:12
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 * @throws SaibongException 
	 */
	@Override
	public String doSave(ProductionOrder order) throws BusinessException, SaibongException {
		
		// 设置初始值并且验证-保存
		setInitDataAndValidateWhenSave(order);
		
		// 保存生产指令
		saveProductionOrder(order);
		
		// 保存附件
		saveAttachments(order);
		
		// 保存生产指令-监控项设置
		productionOrderMonitorService.save(order);
		
		// 保存生产指令-飞机关系
		productionOrderPlaneService.save(order);
		
		return order.getId();
	}
	
	/**
	 * @Description 验证唯一性
	 * @CreateTime 2018年5月7日 下午2:18:44
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	private void validateUniqueness(ProductionOrder order) throws BusinessException{
		if(StringUtils.isNotBlank(order.getZlbh()) &&
				productionOrderMapper.getCountByZlbhAndBbAndDprtcode(order) > 0){
			throw new BusinessException("生产指令编号"+order.getZlbh()+"已存在!");
		}
	}
	
	/**
	 * @Description 设置初始值并且验证-保存
	 * @CreateTime 2018年5月7日 下午2:25:40
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	private void setInitDataAndValidateWhenSave(ProductionOrder order) throws BusinessException{
		// 验证状态
		if(StringUtils.isNotBlank(order.getId())){
			ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
			if(!ProductionOrderStatusEnum.SAVE.getId().equals(dbData.getZt())){
				throw new BusinessException("该数据已更新，请刷新后再进行操作！");
			}
		}
		//验证唯一性
		validateUniqueness(order);
		
		order.setCzls(UUID.randomUUID().toString());	// 操作流水
		if(StringUtils.isNotBlank(order.getfBbid())){	// 日志类型
			order.setLogOperationEnum(LogOperationEnum.REVISION);
		}else if(StringUtils.isBlank(order.getId())){
			order.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		}else{
			order.setLogOperationEnum(LogOperationEnum.EDIT);
		}
		order.setZt(ProductionOrderStatusEnum.SAVE.getId());	// 状态
	}
	
	/**
	 * @Description 提交验证
	 * @CreateTime 2018年5月7日 下午2:32:31
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	private void setInitDataAndValidateWhenSubmit(ProductionOrder order) throws BusinessException{
		// 验证状态
		if(StringUtils.isNotBlank(order.getId())){
			ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
			if(!ProductionOrderStatusEnum.SAVE.getId().equals(dbData.getZt())
					&& !ProductionOrderStatusEnum.AUDITDOWN.getId().equals(dbData.getZt())
					&& !ProductionOrderStatusEnum.APPROVALDOWN.getId().equals(dbData.getZt())){
				throw new BusinessException("该数据已更新，请刷新后再进行操作！");
			}
		}
		//验证唯一性
		validateUniqueness(order);
		
		order.setCzls(UUID.randomUUID().toString());	// 操作流水
		if(StringUtils.isNotBlank(order.getfBbid())){	// 日志类型
			order.setLogOperationEnum(LogOperationEnum.REVISION);
		}else{
			order.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);	
		}
		order.setZt(ProductionOrderStatusEnum.SUBMIT.getId());	// 状态
	}
	
	/**
	 * @Description 审核验证
	 * @CreateTime 2018年5月7日 下午2:34:38
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	private void validateWhenAudit(ProductionOrder dbData) throws BusinessException{
		// 验证状态
		if(!ProductionOrderStatusEnum.SUBMIT.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
	}
	
	/**
	 * @Description 审批验证
	 * @CreateTime 2018年5月7日 下午2:34:58
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	private void validateWhenApprove(ProductionOrder dbData) throws BusinessException{
		// 验证状态
		if(!ProductionOrderStatusEnum.AUDITED.getId().equals(dbData.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
	}
	
	/**
	 * @Description 保存生产指令
	 * @CreateTime 2018年5月3日 下午4:11:16
	 * @CreateBy 韩武
	 * @param order
	 * @throws SaibongException 
	 */
	private void saveProductionOrder(ProductionOrder order) throws SaibongException{
		
		User user = ThreadVarUtil.getUser();
		order.setWhrid(user.getId());
		order.setWhsj(new Date());
		order.setWhbmid(user.getBmdm());
		
		if (StringUtils.isBlank(order.getId())){	// 新增生产指令
			order.setId(UUID.randomUUID().toString());
			if(StringUtils.isBlank(order.getZlbh())){	// 生产指令不填写时系统自动生成
				order.setZlbh(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.GC_SCZL.getName(), order));
			}
			order.setZxbs(RevMarkEnum.INITIAL.getId());
			productionOrderMapper.insertSelective(order);
			// 保存历史记录信息
			commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
					order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.SAVE, order.getId());
			
		} else {	// 修改生产指令
			productionOrderMapper.updateByProductionOrder(order);
			// 保存历史记录信息
			commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
					order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.UPDATE, order.getId());
		}
	}
	
	/**
	 * @Description 保存附件
	 * @CreateTime 2018年5月3日 下午4:48:30
	 * @CreateBy 韩武
	 * @param order
	 */
	private void saveAttachments(ProductionOrder order){
		if(StringUtils.isBlank(order.getfBbid())){	// 新增/修改
			attachmentService.eidtAttachment(order.getAttachments(), order.getId(), 
					order.getCzls(), order.getId(), order.getDprtcode(), order.getLogOperationEnum());
		}else{	// 改版
			attachmentService.saveAttachment4Modify(order.getAttachments(), order.getfBbid(), order.getId(),
					order.getCzls(), order.getId(), order.getDprtcode(), order.getLogOperationEnum());
		}
	}

	/**
	 * @Description 查询生产指令详情
	 * @CreateTime 2018年5月7日 上午9:45:10
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public ProductionOrder queryDetail(String id) {
		
		// 查询生产指令详情
		ProductionOrder detail = productionOrderMapper.queryDetail(id);
		
		// 生产指令-监控项，分钟转化为小时
		switchMinuteToHour(detail);
		
		return detail;
	}
	
	/**
	 * @Description 生产指令-监控项，分钟转化为小时
	 * @CreateTime 2018年5月7日 上午9:57:22
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	private ProductionOrder switchMinuteToHour(ProductionOrder order){
		if(order == null){
			return order;
		}
		List<ProductionOrderMonitor> list = order.getMonitors();
		if(list != null && !list.isEmpty()){
			for (ProductionOrderMonitor monitor : list) {
				// 时间类型的监控项目
				if(MonitorProjectEnum.isTime(monitor.getJklbh())){
					
					monitor.setScz(StringAndDate_Util.convertToHour(monitor.getScz()));
					
					monitor.setZqz(StringAndDate_Util.convertToHour(monitor.getZqz()));
					
					monitor.setYdzQ(StringAndDate_Util.convertToHour(monitor.getYdzQ()));
					
					monitor.setYdzH(StringAndDate_Util.convertToHour(monitor.getYdzH()));
				}
			}
		}
		return order;
	}

	/**
	 * @Description 提交生产指令
	 * @CreateTime 2018年5月7日 下午2:37:41
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 * @throws BusinessException
	 * @throws SaibongException
	 */
	@Override
	public String doSubmit(ProductionOrder order) throws BusinessException,
			SaibongException {
		
		// 设置初始值并且验证-提交
		setInitDataAndValidateWhenSubmit(order);
		
		// 保存生产指令
		saveProductionOrder(order);
		
		// 保存附件
		saveAttachments(order);
		
		// 保存生产指令-监控项设置
		productionOrderMonitorService.save(order);
		
		// 保存生产指令-飞机关系
		productionOrderPlaneService.save(order);
		
		// 记录流程数据
		processRecordService.addRecord(order.getId(), order.getDprtcode(), "提交生产指令");
		
		// 增加待办
//		String sm = "请审核"+order.getZlbh()+" R"+order.getBb()+"生产指令";
//		todoService.insertSelectiveTechnical(order, sm, "project2:production:main:06", 
//				NodeEnum.NODE2.getId(), null, TodoEnum.PRODUCTION_ORDER.getId());
		
		// 删除待办
		Todo todo = new Todo();
		todo.setDbywid(order.getId());
		todo.setZt(UndoStatusEnum.DCL.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE5.getId());
		todo.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(todo);
		
		// 更新主表状态=7生效，最新标识=2最新，生效时间
		ProductionOrder currentVersion = new ProductionOrder();
		currentVersion.setId(order.getId());
		currentVersion.setZxbs(RevMarkEnum.LATEST_VERSION.getId());
		currentVersion.setZt(ProductionOrderStatusEnum.TAKEEFFECT.getId());
		currentVersion.setSxsj(new Date());
		productionOrderMapper.updateByPrimaryKeySelective(currentVersion);	
		
		// 更新前版本的生产指令最新标识=3老版本
		ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
		if(StringUtils.isNotBlank(dbData.getfBbid())){
			ProductionOrder previousVersion = new ProductionOrder();
			previousVersion.setId(dbData.getfBbid());
			previousVersion.setZxbs(RevMarkEnum.OLD_VERSION.getId());
			productionOrderMapper.updateByPrimaryKeySelective(previousVersion);	
		}
		
		//更新生产监控数据，由徐勇提供
		this.processMonitorData(dbData, false);
				
		return order.getId();
	}
	
	/**
	 * @Description 生产指令审核通过
	 * @CreateTime 2018年5月7日 下午3:46:25
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Override
	public void doAuditAgree(ProductionOrder order) throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.YISHENHE_WO);
		
		// 审核验证
		ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
		validateWhenAudit(dbData);
		
		// 更新主表状态=3已审核
		productionOrderMapper.updateZtByPrimaryKey(order.getId(), ProductionOrderStatusEnum.AUDITED.getId());
		
		// 记录rec数据
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.AUDIT, order.getId());
		
		// 记录流程数据：d_025 流程记录
		processRecordService.addRecord(order.getId(), order.getDprtcode()
				, "审核通过 "+(StringUtils.isNotBlank(order.getShyj()) ? order.getShyj() : ""));
		
		// 增加待办
		String sm = "请审批"+order.getZlbh()+" R"+order.getBb()+"生产指令";
		todoService.insertSelectiveTechnical(order, sm, "project2:production:main:07", 
				NodeEnum.NODE3.getId(), null, TodoEnum.PRODUCTION_ORDER.getId());
		
		// 删除待办
		Todo todo = new Todo();
		todo.setDbywid(order.getId());
		todo.setZt(UndoStatusEnum.DCL.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE2.getId());
		todo.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(todo);
		
	}

	/**
	 * @Description 生产指令审核驳回
	 * @CreateTime 2018年5月7日 下午3:46:35
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Override
	public void doAuditReject(ProductionOrder order) throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.SHENHEBOHUI_WO);
		
		// 审核验证
		ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
		validateWhenAudit(dbData);
		
		// 更新主表状态=5审核驳回
		productionOrderMapper.updateZtByPrimaryKey(order.getId(), ProductionOrderStatusEnum.AUDITDOWN.getId());
		
		// 记录rec数据
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.AUDIT, order.getId());
		
		// 记录流程数据：d_025 流程记录
		processRecordService.addRecord(order.getId(), order.getDprtcode()
				, "审核驳回 "+(StringUtils.isNotBlank(order.getShyj()) ? order.getShyj() : ""));
		
		// 增加待办
		String sm = order.getZlbh()+" R"+order.getBb()+"生产指令已经被"+user.getRealname()+"驳回，请重新提交";
		todoService.insertSelectiveTechnical(order, sm, "project2:production:main:02", 
				NodeEnum.NODE5.getId(), dbData.getWhrid(), TodoEnum.PRODUCTION_ORDER.getId());
		
		// 删除待办
		Todo todo = new Todo();
		todo.setDbywid(order.getId());
		todo.setZt(UndoStatusEnum.DCL.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE2.getId());
		todo.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(todo);
	}

	/**
	 * @Description 生产指令审批通过
	 * @CreateTime 2018年5月7日 下午3:46:45
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Override
	public void doApproveAgree(ProductionOrder order) throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.YIPIZHUN_WO);
		
		// 审批验证
		ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
		validateWhenApprove(dbData);
		
		// 更新主表状态=7生效，最新标识=2最新，生效时间
		ProductionOrder currentVersion = new ProductionOrder();
		currentVersion.setId(order.getId());
		currentVersion.setZxbs(RevMarkEnum.LATEST_VERSION.getId());
		currentVersion.setZt(ProductionOrderStatusEnum.TAKEEFFECT.getId());
		currentVersion.setSxsj(new Date());
		productionOrderMapper.updateByPrimaryKeySelective(currentVersion);	
		
		// 更新前版本的生产指令最新标识=3老版本
		if(StringUtils.isNotBlank(dbData.getfBbid())){
			ProductionOrder previousVersion = new ProductionOrder();
			previousVersion.setId(dbData.getfBbid());
			previousVersion.setZxbs(RevMarkEnum.OLD_VERSION.getId());
			productionOrderMapper.updateByPrimaryKeySelective(previousVersion);	
		}
		
		// 记录rec数据
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.APPROVE, order.getId());
		
		// 记录流程数据：d_025 流程记录
		processRecordService.addRecord(order.getId(), order.getDprtcode()
				, "审批通过 "+(StringUtils.isNotBlank(order.getSpyj()) ? order.getSpyj() : ""));
		
		//更新生产监控数据，由徐勇提供
		this.processMonitorData(dbData, false);

		// 删除待办
		Todo todo = new Todo();
		todo.setDbywid(order.getId());
		todo.setZt(UndoStatusEnum.DCL.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE3.getId());
		todo.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(todo);
	}

	/**
	 * @Description 生产指令审批驳回
	 * @CreateTime 2018年5月7日 下午3:46:54
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Override
	public void doApproveReject(ProductionOrder order) throws BusinessException {

		User user = ThreadVarUtil.getUser();
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.SHENPIBOHUI_WO);
		
		// 审批验证
		ProductionOrder dbData = productionOrderMapper.selectByPrimaryKey(order.getId());
		validateWhenApprove(dbData);
		
		// 更新主表状态=6审批驳回
		productionOrderMapper.updateZtByPrimaryKey(order.getId(), ProductionOrderStatusEnum.APPROVALDOWN.getId());
		
		// 记录rec数据
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.APPROVE, order.getId());
		
		// 记录流程数据：d_025 流程记录
		processRecordService.addRecord(order.getId(), order.getDprtcode()
				, "审批驳回 "+(StringUtils.isNotBlank(order.getSpyj()) ? order.getSpyj() : ""));
		
		// 增加待办
		String sm = order.getZlbh()+" R"+order.getBb()+"生产指令已经被"+user.getRealname()+"驳回，请重新提交";
		todoService.insertSelectiveTechnical(order, sm, "project2:production:main:02", 
				NodeEnum.NODE5.getId(), dbData.getWhrid(), TodoEnum.PRODUCTION_ORDER.getId());
		
		// 删除待办
		Todo todo = new Todo();
		todo.setDbywid(order.getId());
		todo.setZt(UndoStatusEnum.DCL.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE3.getId());
		todo.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(todo);
		
	}

	/**
	 * @Description 生产指令改版保存
	 * @CreateTime 2018年5月8日 上午9:59:57
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 * @throws SaibongException 
	 */
	@Override
	public void doRevisionSave(ProductionOrder current) throws BusinessException, SaibongException {

		// 前版本生产指令
		String previousId = current.getId();
		ProductionOrder previous = productionOrderMapper.selectByPrimaryKey(previousId);
		
		// 保存改版后的维修项目
		current.setId(null);
		current.setfBbid(previousId);
		current.setBb(previous.getBb().add(BigDecimal.ONE));
		doSave(current);
		
		// 修改上一版本的后版本id=此版本id
		productionOrderMapper.updateBBbidByPrimaryKey(previousId, current.getId());
		
	}

	/**
	 * @Description 生产指令改版提交
	 * @CreateTime 2018年5月8日 上午10:00:19
	 * @CreateBy 韩武
	 * @param order
	 * @throws BusinessException
	 */
	@Override
	public void doRevisionSubmit(ProductionOrder current) throws BusinessException, SaibongException {
		
		// 前版本生产指令
		String previousId = current.getId();
		ProductionOrder previous = productionOrderMapper.selectByPrimaryKey(previousId);
		
		// 保存改版后的维修项目
		current.setId(null);
		current.setfBbid(previousId);
		current.setBb(previous.getBb().add(BigDecimal.ONE));
		doSubmit(current);
		
		// 修改上一版本的后版本id=此版本id
		productionOrderMapper.updateBBbidByPrimaryKey(previousId, current.getId());
	}

	/**
	 * @Description 查询生产指令版本历史
	 * @CreateTime 2018年5月8日 上午11:45:37
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	@Override
	public List<ProductionOrder> queryVersionList(ProductionOrder order) {
		return productionOrderMapper.queryVersionList(order);
	}

	/**
	 * @Description 查询生产指令历史版本
	 * @CreateTime 2018年5月8日 下午1:25:25
	 * @CreateBy 韩武
	 * @param order
	 * @return
	 */
	@Override
	public List<ProductionOrder> queryHistoryList(ProductionOrder order) {
		order = productionOrderMapper.selectByPrimaryKey(order.getId());
		return productionOrderMapper.queryHistoryList(order);
	}

	/**
	 * @Description 删除生产指令
	 * @CreateTime 2018年5月8日 下午3:25:40
	 * @CreateBy 韩武
	 * @param id
	 */
	@Override
	public void doDelete(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 验证状态=1保存、5审核驳回、6审批驳回
		ProductionOrder order = productionOrderMapper.selectByPrimaryKey(id);
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.DELETE);
		if(order == null || (!ProductionOrderStatusEnum.SAVE.getId().equals(order.getZt())
				&& !ProductionOrderStatusEnum.AUDITDOWN.getId().equals(order.getZt())
				&& !ProductionOrderStatusEnum.APPROVALDOWN.getId().equals(order.getZt()))){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		
		// 删除b_g2_014
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.DELETE, order.getId());
		productionOrderMapper.deleteByPrimaryKey(id);
		// 删除b_g2_01401
		productionOrderMonitorService.delete(order);
		// 删除b_g2_01402
		productionOrderPlaneService.delete(order);
		
		// 修改上一版本的后版本id=null
		if(StringUtils.isNotBlank(order.getfBbid())){
			productionOrderMapper.updateBBbidByPrimaryKey(order.getfBbid(), null);
		}
		
		// 删除待办
		Todo todo = new Todo();
		todo.setDbywid(order.getId());
		todo.setZt(UndoStatusEnum.DCL.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE2.getId());
		jdlist.add(NodeEnum.NODE3.getId());
		jdlist.add(NodeEnum.NODE5.getId());
		todo.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(todo);
	}

	/**
	 * @Description 关闭生产指令
	 * @CreateTime 2018年5月8日 下午3:28:37
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void doClose(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 验证状态=7生效
		ProductionOrder order = productionOrderMapper.selectByPrimaryKey(id);
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.INVALID);
		if(order == null || (!ProductionOrderStatusEnum.TAKEEFFECT.getId().equals(order.getZt()))){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		
		// 更新主表状态=9关闭
		productionOrderMapper.updateZtByPrimaryKey(order.getId(), ProductionOrderStatusEnum.CLOSE.getId());
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.UPDATE, order.getId());
		
		// 记录流程数据：d_025 流程记录
		processRecordService.addRecord(order.getId(), order.getDprtcode(), "关闭生产指令");
		
		//更新生产监控数据-徐勇提供
		//删除当前监控数据
		this.monitorDataService.removeByLybhAndLx(order.getZlbh(), MaintenanceTypeEnum.PRODUCTION_ORDER.getId(), order.getDprtcode());
	}

	/**
	 * @Description 启用生产指令
	 * @CreateTime 2018年5月8日 下午3:28:44
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void doOpen(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		// 验证状态=9关闭
		ProductionOrder order = productionOrderMapper.selectByPrimaryKey(id);
		order.setCzls(UUID.randomUUID().toString());
		order.setLogOperationEnum(LogOperationEnum.ENABLED);
		if(order == null || (!ProductionOrderStatusEnum.CLOSE.getId().equals(order.getZt()))){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		
		// 更新主表状态=7生效
		productionOrderMapper.updateZtByPrimaryKey(order.getId(), ProductionOrderStatusEnum.TAKEEFFECT.getId());
		commonRecService.write(order.getId(), TableEnum.B_G2_014, user.getId(), 
				order.getCzls() ,order.getLogOperationEnum(), UpdateTypeEnum.UPDATE, order.getId());
		
		// 记录流程数据：d_025 流程记录
		processRecordService.addRecord(order.getId(), order.getDprtcode(), "开启生产指令");
		
		//更新生产监控数据-徐勇提供
		this.processMonitorData(order, true);
	}

	/**
	 * @Description 生产指令生效时处理监控数据
	 * @CreateTime 2018年5月2日 下午1:44:42
	 * @CreateBy 徐勇
	 * @param productionOrder 生产指令
	 * @param isOpen  开启   生产指令  功能
	 */
	private void processMonitorData(ProductionOrder productionOrder, boolean isOpen){
		//当生产指令有前版本时应按指令编号删除当前监控数据
		if(!isOpen && StringUtils.isNotBlank(productionOrder.getfBbid())){
			this.monitorDataService.removeByLybhAndLx(productionOrder.getZlbh(), MaintenanceTypeEnum.PRODUCTION_ORDER.getId(), productionOrder.getDprtcode());
		}

		//生成最新监控数据
		//查询应生成的监控数据
		List<MonitoringObject> moList = this.monitorDataService.queryPONeedMonitorDataList(productionOrder.getId());
		
		if(moList.isEmpty()){//当无应监控数据时无需继续处理
			return;
		}
		
		//查询生产指令的监控数据
		List<ProductionOrderMonitor> monitorList = this.productionOrderMonitorMapper.selectMonitorByMainId(productionOrder.getId());
		
		//当生产指令有前版本时 查询前版本生产指令未执行数据(带计划、上次执行)
		Map<String, MonitoringObject> notExeMap = null;//前版本维修方案未执行数据(带计划、上次执行)
		if(isOpen){
			notExeMap = this.monitorDataService.queryNotExeMonitorDataMapByLyid(productionOrder.getId());
		} else if(StringUtils.isNotBlank(productionOrder.getfBbid( ))){
			notExeMap = this.monitorDataService.queryNotExeMonitorDataMapByLyid(productionOrder.getfBbid());
		}
		
		//查询 机型下飞机/在机序列件的 日历初始值，map中key值为 飞机注册号/装机清单ID
		//Map<String, String> calInitMap = this.monitorDataService.queryAllCalInitByFjjx(productionOrder.getDprtcode(), productionOrder.getJx());
		Map<String, String> calInitMap = new HashMap<String, String>(0);//因为日历监控项的日历初始值改成了日期，现不需要取飞机的日历初始值 
		
		List<MonitoringObject> cMOList = new ArrayList<MonitoringObject>();//待新增的监控数据
		List<MonitoringObject> uMOList = new ArrayList<MonitoringObject>();//待修改的监控数据
		List<MonitoringPlan> cMPlanList = new ArrayList<MonitoringPlan>();//待新增的计划数据（待新增及待修改的监控数据的计划数据）
		
		//组装当前监控数据 及 计划、上次执行数据
		for (MonitoringObject monitoringObject : moList) {
			monitoringObject.setLylx(MaintenanceTypeEnum.PRODUCTION_ORDER.getId());
			//获取原监控数据
			MonitoringObject notExeMO = this.monitorDataService.getMatchedMonitorObject(notExeMap, monitoringObject, false);//原监控数据
			this.buildMonitorData(cMOList, uMOList, cMPlanList, monitoringObject, notExeMO, monitorList, calInitMap, productionOrder, ThreadVarUtil.getUser());
		}
		
		//删除待修改监控数据的计划数据
		this.monitorDataService.removeMonitorPlanByMainIdBatch(uMOList);
		//批量修改监控数据（处理待修改监控数据）
		this.monitorDataService.updateMonitorObjectBatch(uMOList);
		//批量新增监控数据（处理待新增监控数据）
		this.monitorDataService.saveMonitorObjectBatch(cMOList);
		//批量新增计划数据（处理待新增计划数据）
		this.monitorDataService.saveMonitorPlanBatch(cMPlanList);
		//根据待修改、待新增监控数据 同步到 当前监控数据中
		uMOList.addAll(cMOList);
		this.monitorDataService.saveCurrentMonitor4BatchSync(uMOList);
	}
	
	/**
	 * @Description 构建监控数据、监控计划
	 * @CreateTime 2017年10月10日 下午8:22:56
	 * @CreateBy 徐勇
	 * @param cMOList 待新增监控数据
	 * @param uMOList 待更新监控数据
	 * @param cMPlanList 待新增监控计划
	 * @param monitoringObject 新监控对象
	 * @param notExeMO 原监控对象
	 * @param mpMap 维修项目清单
	 * @param calInitMap 飞机/在机序列件日历初始值
	 * @param scheme 维修方案
	 * @param user 登陆用户令牌
	 */
	private void buildMonitorData(List<MonitoringObject> cMOList, List<MonitoringObject> uMOList, List<MonitoringPlan> cMPlanList, 
			MonitoringObject monitoringObject, MonitoringObject notExeMO, List<ProductionOrderMonitor> monitorList,
			Map<String, String> calInitMap, ProductionOrder productionOrder, User user){
		
		if(notExeMO == null){//不存在原监控数据，按维修项目生成监控数据
			String jksjid = UUID.randomUUID().toString();//监控数据ID
			//生成监控对象
			monitoringObject.setId(jksjid);
			monitoringObject.setWhbmid(user.getBmdm());
			monitoringObject.setWhrid(user.getId());
			monitoringObject.setLylx(MaintenanceTypeEnum.PRODUCTION_ORDER.getId());
			cMOList.add(monitoringObject);
			
			//生成计划数据
			MonitoringPlan monitoringPlan = null;
			for (ProductionOrderMonitor productionOrderMonitor : monitorList) {
				monitoringPlan = new MonitoringPlan(productionOrderMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(jksjid);
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
//					//日历计划值为初始+首次; 装机清单ID不为空时按装机清单取日历初始值，否则按飞机取
//					String strCalInit = calInitMap.get(monitoringObject.getFjzch());
//					//异常处理，当日历初始值或首次值为空 则 计划值为空
//					if(StringUtils.isNotBlank(strCalInit) && StringUtils.isNotBlank(productionOrderMonitor.getScz())){
//						try {
//							monitoringPlan.setJhz(DateUtil.getOffsetDate(strCalInit, Integer.parseInt(monitoringPlan.getScz()), this.monitorDataService.getOffsetUnit(monitoringPlan.getDwScz())));
//						} catch (ParseException e) {}
//					}
					//日历计划值就是日历
					monitoringPlan.setJhz(monitoringPlan.getScz());
					if(StringUtils.isNotBlank(monitoringPlan.getScz())){
						monitoringPlan.setScz("0");
						monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
					}else{
						monitoringPlan.setScz(null);
						monitoringPlan.setDwScz(null);
					}
				}else{//非日历
					monitoringPlan.setJhz(productionOrderMonitor.getScz());
				}
				cMPlanList.add(monitoringPlan);
			}
			
		}else{//存在原监控数据，更新原监控数据，并按维修项目生成新的计划
			
			//修改原监控对象
			notExeMO.setWhbmid(user.getBmdm());
			notExeMO.setWhrid(user.getId());
			notExeMO.setLyid(monitoringObject.getLyid());
			notExeMO.setLybh(monitoringObject.getLybh());
			notExeMO.setWz(monitoringObject.getWz());
			notExeMO.setJsgs(monitoringObject.getJsgs());
			notExeMO.setHdwz(monitoringObject.getHdwz());
			uMOList.add(notExeMO);
			//生成计划数据
			List<MonitoringPlan> notExeMOPlanList = notExeMO.getMonitoringPlanList();//原监控数据计划数据
			List<MonitoringLast> notExeMOLastList = notExeMO.getMonitoringLastList();//原监控数据上次执行数据
			MonitoringPlan monitoringPlan = null;
			for (ProductionOrderMonitor productionOrderMonitor : monitorList) {
				monitoringPlan = new MonitoringPlan(productionOrderMonitor);
				monitoringPlan.setId(UUID.randomUUID().toString());
				monitoringPlan.setJksjid(notExeMO.getId());
				monitoringPlan.setWhdwid(user.getBmdm());
				monitoringPlan.setWhrid(user.getId());
				monitoringPlan.setWz(monitoringObject.getWz());
				
				//判断原监控计划是否有该监控项，有该监控项使用该监控项的T0值进行计算
				for (MonitoringPlan notExeMOPlan : notExeMOPlanList) {
					if(notExeMOPlan.getJklbh().equals(monitoringPlan.getJklbh())){
						monitoringPlan.setJhqsz(notExeMOPlan.getJhqsz());
						break;
					}
				}
				
				//计算间隔 = 首次or周期
				String interval = null;//间隔
				int intervalUnit = 0;//间隔单位
				boolean isSc = false;//是否首次
				
				//T0无值，判断是否有上次执行数据，有上次执行数据以周期计算，否则以首次计算
				if((notExeMOLastList != null && notExeMOLastList.size() > 0) || StringUtils.isNotBlank(notExeMO.getfJksjid())){
					//以周期进行计算
					interval = monitoringPlan.getZqz();
					intervalUnit = monitoringPlan.getDwZqz();
				}else{
					//以首次进行计算
					interval = monitoringPlan.getScz();
					intervalUnit = monitoringPlan.getDwScz();
					isSc = true;
				}
				
				//间隔不为空时计算计划值，间隔为空则计划值为空
				if(StringUtils.isNotBlank(interval)){
					if(MonitorProjectEnum.isCalendar(monitoringPlan.getJklbh())){
						//日历：为 T0为空时 使用初始+间隔，否则使用T0+间隔
						String t0 = monitoringPlan.getJhqsz();
						if(StringUtils.isBlank(t0)){
							//t0为空 后面以初始值进行计算
							//t0 = calInitMap.get(monitoringObject.getFjzch());
							t0 = monitoringPlan.getScz();
						}
						//异常处理，当日历初始值或首次值为空 则 计划值为空
						if(StringUtils.isNotBlank(t0)){
							if(isSc){
								monitoringPlan.setJhz(t0);
							}else{
								try {
									monitoringPlan.setJhz(DateUtil.getOffsetDate(t0, Integer.parseInt(interval), this.monitorDataService.getOffsetUnit(intervalUnit)));
								} catch (ParseException e) {}
							}
						}
						
						if(StringUtils.isNotBlank(monitoringPlan.getScz())){
							monitoringPlan.setScz("0");
							monitoringPlan.setDwScz(MonitorProjectUnitEnum.DAY.getCode());
						}else{
							monitoringPlan.setScz(null);
							monitoringPlan.setDwScz(null);
						}
						
					}else{
						//非日历 T0+间隔
						monitoringPlan.setJhz(String.valueOf(Integer.parseInt(interval) + Integer.parseInt(StringUtils.isNotBlank(monitoringPlan.getJhqsz())?monitoringPlan.getJhqsz():"0")));
					}
				}else{
					monitoringPlan.setJhqsz(null);
				}
				cMPlanList.add(monitoringPlan);
			}
		}
	}
	
	
}
