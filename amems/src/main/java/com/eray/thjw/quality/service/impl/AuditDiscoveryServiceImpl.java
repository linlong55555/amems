package com.eray.thjw.quality.service.impl;

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
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.quality.dao.AuditDiscoveryDetailMapper;
import com.eray.thjw.quality.dao.AuditDiscoveryMapper;
import com.eray.thjw.quality.po.AuditDiscovery;
import com.eray.thjw.quality.po.AuditDiscoveryDetail;
import com.eray.thjw.quality.service.AuditDiscoveryDetailService;
import com.eray.thjw.quality.service.AuditDiscoveryService;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.quality.service.ProjectAndProblemMapperService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoStatusEnum;
import enu.quality.ProblemStatusEnum;

/** 
 * @Description 发现问题通知单service实现类
 * @CreateTime 2018年1月8日 下午1:51:31
 * @CreateBy 韩武	
 */
@Service("auditDiscoveryService")
public class AuditDiscoveryServiceImpl implements AuditDiscoveryService{
	
	@Resource
	private AuditDiscoveryMapper auditDiscoveryMapper;
	@Resource
	private TodoService todoService;
	
	@Resource
	private AuditDiscoveryDetailService auditDiscoveryDetailService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private ProcessRecordService processRecordService;
	
	@Resource
	private ProjectAndProblemMapperService projectAndProblemMapperService;
	
	@Resource
    private AuditDiscoveryDetailMapper auditDiscoveryDetailMapper;
	
	@Resource
	private TodorsService todorsService;

	/**
	 * @Description 保存发现问题通知单
	 * @CreateTime 2018年1月8日 下午1:49:43
	 * @CreateBy 韩武
	 * @param discovery
	 * @return
	 * @throws SaibongException 
	 */
	@Override
	public String doSave(AuditDiscovery record) throws BusinessException, SaibongException {
		
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setWhbmid(user.getBmdm());
		
		if (StringUtils.isBlank(record.getId())){	// 新增发现问题通知单
			record.setId(UUID.randomUUID().toString());
			if(StringUtils.isBlank(record.getShwtdbh())){
				boolean b = true;
				while (b) {
					String shwtdbh;
					try {
						shwtdbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.WTDBH.getName());
						record.setShwtdbh(shwtdbh);
						int bcount = auditDiscoveryMapper.getCount(shwtdbh,user.getJddm());
						if (bcount == 0) {
							b = false;
						}
					} catch (SaibongException e) {
						throw new RuntimeException(e);
					}
				}
			}else{
				int bcount = auditDiscoveryMapper.getCount(record.getShwtdbh(),user.getJgdm());
				if(bcount >0){
					throw new BusinessException("该问题通知单编号"+record.getShwtdbh()+"已存在!");
				}
			}
			auditDiscoveryMapper.insertSelective(record);
			//审核项目单与审核问题单关系 
			if(null != record.getParamsMap() && null != record.getParamsMap().get("shxmdid") && !"".equals(record.getParamsMap().get("shxmdid"))){
				projectAndProblemMapperService.addRecord(record.getId(), (String)record.getParamsMap().get("shxmdid"));
			}			
		} else {	
			//验证通知单状态是否为保存状态
			validation4Update(record.getId());
			// 修改发现问题通知单
			auditDiscoveryMapper.updateByPrimaryKeySelective(record);
		}
		// 保存附件信息
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), UUID.randomUUID().toString(), record.getId(), record.getDprtcode(),
				LogOperationEnum.SUBMIT_WO);
		
		// 保存发现问题通知单详情
		auditDiscoveryDetailService.doSave(record);
		return record.getId();
	}
	
	/**
	 * 
	 * @Description 验证工单
	 * @CreateTime 2018年1月10日 下午5:31:10
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	private void validation4Update(String id) throws BusinessException{
		AuditDiscovery record = auditDiscoveryMapper.selectByPrimaryKey(id);
		if(null == record){
			throw new BusinessException("该通知单已被删除!");
		}
		if(ProblemStatusEnum.SAVE.getId() != record.getZt()){
			throw new BusinessException("该通知单已更新，请刷新页面后操作!");
		}
	}
	/**
	 * 
	 * @Description 发现问题通知单列表
	 * @CreateTime 2018年1月9日 下午5:12:53
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getList(AuditDiscovery record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		try {
			PageHelper.startPage(record.getPagination());
			List<AuditDiscovery> recordList = auditDiscoveryMapper.queryList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						AuditDiscovery newRecord = new AuditDiscovery();
						newRecord.setId(id);
						List<AuditDiscovery> newRecordList = auditDiscoveryMapper.queryList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<AuditDiscovery> newRecordList = new ArrayList<AuditDiscovery>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					AuditDiscovery newRecord = new AuditDiscovery();
					newRecord.setId(id);
					newRecordList = auditDiscoveryMapper.queryList(record);
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
	 * @Description 获取问题通知单
	 * @CreateTime 2018年1月10日 上午11:01:16
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public AuditDiscovery getRecord(AuditDiscovery record) throws BusinessException {
		record = auditDiscoveryMapper.selectByPrimaryKey(record.getId());
		List<AuditDiscoveryDetail>  detailList = auditDiscoveryDetailService.getByShwtdid(record.getId());
		record.setDetails(detailList);
		return record;
	}
	
	/**
	 * 
	 * @Description 删除通知单
	 * @CreateTime 2018年1月10日 下午5:03:27
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(AuditDiscovery discovery) throws BusinessException {
		//验证通知单状态是否为保存状态
		validation4Update(discovery.getId());
		//删除通知单
		auditDiscoveryMapper.deleteByPrimaryKey(discovery.getId());
		//删除问题清单
		auditDiscoveryDetailService.deleteByShwtdid(discovery.getId());
		//删除审核项目单与审核问题单关系
		projectAndProblemMapperService.deleteRecord(discovery.getId());
	}
	/**
	 * 
	 * @Description 关闭通知单
	 * @CreateTime 2018年1月10日 下午5:03:27
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @throws BusinessException
	 */
	@Override
	public void update4CloseRecord(AuditDiscovery discovery) throws BusinessException {
		//验证
		AuditDiscovery record = validation4Close(discovery.getId());
		User user = ThreadVarUtil.getUser();
		//关闭问题单
		doAuditDiscover(discovery,user);
		//更新问题清单
		doDetails(discovery.getId(),user);
		//增加d_025流程
		processRecordService.addRecord(discovery.getId(), record.getDprtcode(),"关闭问题");
		
		List<AuditDiscoveryDetail> list= auditDiscoveryDetailService.getByShwtdid(discovery.getId());
		
		for (AuditDiscoveryDetail auditDiscoveryDetail : list) {
			//删除待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(auditDiscoveryDetail.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(22);
			jdlist.add(23);
			jdlist.add(24);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			todoService.deletedbyw(tododbyw);
			
			
			
		}
		
		//发现问题通通知单关闭删除待办
		//根据审核单id获取审核清单信息
		List<Integer> jdList=new ArrayList<Integer>();
		jdList.add(NodeEnum.NODE21.getId());
		for(AuditDiscoveryDetail item:list){
			todorsService.deleteToDo(item.getId(), UndoStatusEnum.DCL.getId(), jdList);
		}
	}
	
	/**
	 * 
	 * @Description 验证工单
	 * @CreateTime 2018年1月10日 下午5:31:10
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	private AuditDiscovery validation4Close(String id) throws BusinessException{
		AuditDiscovery record = auditDiscoveryMapper.selectByPrimaryKey(id);
		if(null == record){
			throw new BusinessException("该通知单已被删除!");
		}
		if(ProblemStatusEnum.SUBMIT.getId() != record.getZt()){
			throw new BusinessException("该通知单已更新，请刷新页面后操作!");
		}
		return record;
	}
	/**
	 * 
	 * @Description 关闭问题单
	 * @CreateTime 2018年1月11日 上午9:44:22
	 * @CreateBy 岳彬彬
	 * @param discovery
	 * @param user
	 */
	private void doAuditDiscover(AuditDiscovery discovery,User user){
		discovery.setZt(ProblemStatusEnum.CLOSED.getId());
		discovery.setGbrid(user.getId());
		discovery.setGbsm("关闭问题");
		discovery.setGbrbmid(user.getBmdm());
		discovery.setGbsj(new Date());
		auditDiscoveryMapper.updateByPrimaryKeySelective(discovery);
	}
	/**
	 * 
	 * @Description 关闭问题清单
	 * @CreateTime 2018年1月11日 上午9:45:26
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 * @param user
	 */
	private void doDetails(String shwtdid,User user){
		List<AuditDiscoveryDetail>  detailList = auditDiscoveryDetailService.getByShwtdid(shwtdid);
		List<String> list = new ArrayList<String>();
		for (AuditDiscoveryDetail detail : detailList) {
			if(ProblemStatusEnum.CLOSED.getId() != detail.getZt()){
				list.add(detail.getId());
			}
		}
		if(null != list && list.size() > 0){
			AuditDiscoveryDetail record = new AuditDiscoveryDetail();
			record.setGbrbmid(user.getBmdm());
			record.setGbrid(user.getId());
			record.setGbsm("关闭问题");
			record.setZt(ProblemStatusEnum.CLOSED.getId());
			record.getParamsMap().put("list", list);
			auditDiscoveryDetailService.doBatchClose(record);
		}
	}
	
}
