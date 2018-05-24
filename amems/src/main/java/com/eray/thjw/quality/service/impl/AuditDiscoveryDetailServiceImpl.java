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
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.common.EffectiveEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
import enu.quality.ProblemStatusEnum;

/** 
 * @Description 发现问题通知单service实现类
 * @CreateTime 2018年1月8日 下午1:51:31
 * @CreateBy 韩武	
 */
@Service("auditDiscoveryDetailService")
public class AuditDiscoveryDetailServiceImpl implements AuditDiscoveryDetailService{
	
	@Resource
	private AuditDiscoveryDetailMapper auditDiscoveryDetailMapper;
	
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private CommonService commonService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private ProcessRecordService processRecordService;
	
	@Resource
	private TodoService todoService;	
	
	@Resource
	private TodorsService todorsService;
	
	@Resource
	private AuditDiscoveryMapper auditDiscoveryMapper;
	
	/**
	 * @Description 保存发现问题通知单详情
	 * @CreateTime 2018年1月8日 下午1:49:43
	 * @CreateBy 韩武
	 * @param discovery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void doSave(AuditDiscovery record) throws BusinessException, SaibongException {
		List<AuditDiscoveryDetail> details= record.getDetails();
		List<AuditDiscoveryDetail> addList= new ArrayList<AuditDiscoveryDetail>();
		List<AuditDiscoveryDetail> updateList= new ArrayList<AuditDiscoveryDetail>();
		List<String> delList= new ArrayList<String>();
		User user = ThreadVarUtil.getUser();
		for (AuditDiscoveryDetail auditDiscoveryDetail : details) {
			if(StringUtils.isBlank(auditDiscoveryDetail.getId())){//新增发现问题
				if(null != auditDiscoveryDetail.getParamsMap() && null != auditDiscoveryDetail.getParamsMap().get("idList")){
					delList.addAll((List<String>)auditDiscoveryDetail.getParamsMap().get("idList"));
					break;
				}
				auditDiscoveryDetail.setId(UUID.randomUUID().toString());
				auditDiscoveryDetail.setDprtcode(user.getJgdm());
				auditDiscoveryDetail.setZt(record.getZt());
				auditDiscoveryDetail.setWhbmid(user.getBmdm());
				auditDiscoveryDetail.setWhrid(user.getId());				
				auditDiscoveryDetail.setShwtdid(record.getId());
				auditDiscoveryDetail.setWtfkrbmid(record.getZrrbmid());
				auditDiscoveryDetail.setWtfkrid(record.getZrrid());
				if(StringUtils.isBlank(auditDiscoveryDetail.getShwtbh())){
					boolean b = true;
					while (b) {
						String shwtbh;
						try {
							shwtbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.WTBH.getName());
							auditDiscoveryDetail.setShwtbh(shwtbh);
							int bcount = auditDiscoveryDetailMapper.getCount(shwtbh,user.getJgdm());
							if (bcount == 0) {
								b = false;
							}
						} catch (SaibongException e) {
							throw new RuntimeException(e);
						}
					}
				}else{
					if(auditDiscoveryDetailMapper.getCount(auditDiscoveryDetail.getShwtbh(),user.getJgdm())>0){
						throw new BusinessException("该问题编号"+auditDiscoveryDetail.getShwtbh()+"已存在！");
					}
				}						
				addList.add(auditDiscoveryDetail);
			}else{//修改发现问题
				auditDiscoveryDetail.setWhbmid(user.getBmdm());
				auditDiscoveryDetail.setWhrid(user.getId());
				auditDiscoveryDetail.setWhsj(new Date());
				auditDiscoveryDetail.setZt(record.getZt());
				auditDiscoveryDetail.setWtfkrbmid(record.getZrrbmid());
				auditDiscoveryDetail.setWtfkrid(record.getZrrid());
				updateList.add(auditDiscoveryDetail);
			}
		}
		if(null != addList && addList.size()>0){
			//批量新增
			List<AuditDiscoveryDetail> addTemp = new ArrayList<AuditDiscoveryDetail>();
			for (int i = 0; i < addList.size(); i++) {
				addTemp.add(addList.get(i));
				if(addTemp.size() >= 500 || i == addList.size() - 1){
					auditDiscoveryDetailMapper.batchInsert(addTemp);
					addTemp.clear();
				}
			}
			
		}
		if(null != updateList && updateList.size()>0){
			//批量修改
			List<AuditDiscoveryDetail> updateTemp = new ArrayList<AuditDiscoveryDetail>();
			for (int i = 0; i < updateList.size(); i++) {
				updateTemp.add(updateList.get(i));
				if(updateTemp.size() >= 500 || i == updateList.size() - 1){
					auditDiscoveryDetailMapper.batchUpdate(updateTemp);
					updateTemp.clear();
				}
			}
		}
		if(null != delList && delList.size()>0){
			//批量删除
			auditDiscoveryDetailMapper.batchDel(delList);
		}
		
		
		//发现问题通知单下发时写入待办(提交)
		if(ProblemStatusEnum.SUBMIT.getId().equals(record.getZt())){
			StringBuilder builder=new StringBuilder();			
			  if(addList!=null){
				  for(int j=0;j<addList.size();j++){
					  builder.append("请整改:"+(StringUtils.isBlank(addList.get(j).getWtms())?"":addList.get(j).getWtms()));
					  if(builder.length()>1000)
						  builder.substring(0, 1000);
					  todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(),
							  addList.get(j).getId(), addList.get(j).getShwtbh(), null, null, UndoEnum.NSWTZG.getId(), 
							  NodeEnum.NODE21.getId(), builder.toString(), record.getYqfkrq(), record.getZrrid(), "quality:correctivemeasures:main", addList.get(j).getId(), null);
					  builder.delete(0, builder.length());
				  }
			  }
			  if(updateList!=null){
				   for(int k=0;k<updateList.size();k++){
					   builder.append("请整改:"+(StringUtils.isBlank(updateList.get(k).getWtms())?"":updateList.get(k).getWtms()));
					   todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(),
							   updateList.get(k).getId(), updateList.get(k).getShwtbh(), null, null, UndoEnum.NSWTZG.getId(), 
								  NodeEnum.NODE21.getId(), builder.toString(), record.getYqfkrq(), record.getZrrid(), "quality:correctivemeasures:main", updateList.get(k).getId(), null);
					   builder.delete(0, builder.length());
				   }
			  }
			
		}
	}
	
	/**
     * 
     * @Description 根据审核问题单id获取审核问题清单
     * @CreateTime 2018年1月10日 上午11:07:56
     * @CreateBy 岳彬彬
     * @param shwtdid
     * @return
     */
	@Override
	public List<AuditDiscoveryDetail> getByShwtdid(String shwtdid) {
		
		return auditDiscoveryDetailMapper.getByShwtdid(shwtdid);
	}
	
	/**
	 * 
	 * @Description 根据审核问题单id删除审核问题清单
	 * @CreateTime 2018年1月10日 下午5:08:45
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 */
	@Override
	public void deleteByShwtdid(String shwtdid) {
	
		auditDiscoveryDetailMapper.deleteByShwtdid(shwtdid);
	}
	
	/**
     * 
     * @Description 批量关闭
     * @CreateTime 2018年1月11日 上午9:52:01
     * @CreateBy 岳彬彬
     * @param list
     */
	@SuppressWarnings("unchecked")
	@Override
	public void doBatchClose(AuditDiscoveryDetail record) {	
		auditDiscoveryDetailMapper.batchClose(record);
		//流程记录
		processRecordService.addBatchRecord((List<String>)record.getParamsMap().get("list"), record.getDprtcode(),"关闭问题");
	}
	
	/**
     * 
     * @Description 问题整改措施列表
     * @CreateTime 2018年1月11日 下午3:25:18
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
	@Override
	public Map<String, Object> getList(AuditDiscoveryDetail record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		record.getParamsMap().put("userBm", user.getBmdm());
		try {
			PageHelper.startPage(record.getPagination());
			List<AuditDiscoveryDetail> recordList = auditDiscoveryDetailMapper.queryList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						AuditDiscoveryDetail newRecord = new AuditDiscoveryDetail();
						newRecord.setId(id);
						List<AuditDiscoveryDetail> newRecordList = auditDiscoveryDetailMapper.queryList(record);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<AuditDiscoveryDetail> newRecordList = new ArrayList<AuditDiscoveryDetail>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					AuditDiscoveryDetail newRecord = new AuditDiscoveryDetail();
					newRecord.setId(id);
					newRecordList = auditDiscoveryDetailMapper.queryList(record);
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
     * @Description 获取数据
     * @CreateTime 2018年1月12日 上午10:25:17
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
	@Override
	public AuditDiscoveryDetail getRecord(AuditDiscoveryDetail record) throws BusinessException {
		
		return auditDiscoveryDetailMapper.getById(record.getId());
	}
	/**
     * 
     * @Description 修改数据
     * @CreateTime 2018年1月12日 上午11:47:07
     * @CreateBy 岳彬彬
     * @param record
     * @throws BusinessException
     */
	@Override
	public String updateRecord(AuditDiscoveryDetail record) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		//检验单据状态
		AuditDiscoveryDetail detail = validation4Update(record.getId());
		User user = ThreadVarUtil.getUser();
		if(null != record.getParamsMap() && !"1".equals(record.getParamsMap().get("editType"))){//提交
			StringBuffer sbf = new StringBuffer();
			AuditDiscoveryDetail auditDiscoveryDetail =auditDiscoveryDetailMapper.selectByPrimaryKey(record.getId());
			if(null != record.getZxrid() && !"".equals(record.getZxrid())){//指派
				sbf.append("指派"+record.getParamsMap().get("username")+"执行");
				
				//删除待办
				Todo tododbyw=new Todo();
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(22);
				tododbyw.setDbywid(record.getId());
				tododbyw.setDbgzlx(TodoEnum.NSWTZG.getId());
				tododbyw.setYxzt(1);
				tododbyw.setZt(1);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				todoService.deletedbyw(tododbyw);
			
				if(!record.getZxrid().equals(user.getId())){
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("请执行整改:");
					if(auditDiscoveryDetail.getWtms().length()>1000){
						strSm.append(auditDiscoveryDetail.getWtms().substring(0, 1000));
					}else{
						strSm.append(auditDiscoveryDetail.getWtms());
					}
					todoService.insertSelectiveTechnical(auditDiscoveryDetail,strSm.toString(),"quality:correctivemeasures:main",NodeEnum.NODE22.getId(),record.getZxrid(),TodoEnum.NSWTZG.getId());
				}
				
			}else{			//提交
				record.setZxrbmid(user.getBmdm());
				record.setZxrid(user.getId());
				record.setZxsj(new Date());
				sbf.append("已执行");
				
				//新增待办事宜
				//关闭待办
				Todo tododbyw=new Todo();
				tododbyw.setDbywid(auditDiscoveryDetail.getId());
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(22);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				tododbyw.setZt(TodoStatusEnum.DCL.getId());
				tododbyw.setBlrid(user.getId());
				tododbyw.setFksj(currentDate);
				todoService.updateByDbyw(tododbyw);
				
			}
			//添加流程记录
			processRecordService.addRecord(record.getId(), detail.getDprtcode(),sbf.toString());
		}
		record.setWhrid(user.getId());
		record.setWhbmid(user.getBmdm());
		record.setWhsj(new Date());
		auditDiscoveryDetailMapper.updateByPrimaryKeySelective(record);
		//处理附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), UUID.randomUUID().toString(), record.getId(), record.getDprtcode(),
				LogOperationEnum.EDIT);
		return record.getId();
	}
	/**
	 * 
	 * @Description 验证问题清单状态为1 2 6才能保存、提交、指派
	 * @CreateTime 2018年1月12日 上午11:53:49
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	private AuditDiscoveryDetail validation4Update(String id) throws BusinessException {
		AuditDiscoveryDetail record = auditDiscoveryDetailMapper.selectByPrimaryKey(id);
		if (null != record && ProblemStatusEnum.SUBMIT.getId() != record.getZt() && ProblemStatusEnum.EXECUTE.getId() != record.getZt()
				&& ProblemStatusEnum.REJECT.getId() != record.getZt()) {
			throw new BusinessException("该问题清单已更新，请刷新页面后操作！");
		}
		return record;
	}
	
	/**
     * 
     * @Description 反馈
     * @CreateTime 2018年1月15日 上午10:39:21
     * @CreateBy 岳彬彬
     * @param record
     * @throws BusinessException
     */
	@SuppressWarnings("unchecked")
	@Override
	public void update4Feedback(AuditDiscoveryDetail record) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		User user = ThreadVarUtil.getUser();
		if(null != record.getParamsMap() && null != record.getParamsMap().get("idList")){
			List<String> idList = (List<String>) record.getParamsMap().get("idList");
			//校验问题清单
			AuditDiscoveryDetail detail = validationDetails(idList);
			//反馈
			List<String> addTemp = new ArrayList<String>();
			for (int i = 0; i < idList.size(); i++) {
				addTemp.add(idList.get(i));
				if(addTemp.size() >= 500 || i == idList.size() - 1){
					auditDiscoveryDetailMapper.batchFeedback(addTemp);
					addTemp.clear();
					
					AuditDiscoveryDetail auditDiscoveryDetail=auditDiscoveryDetailMapper.selectByPrimaryKey(idList.get(i));
					
					//关闭待办
					Todo tododbyw=new Todo();
					tododbyw.setDbywid(auditDiscoveryDetail.getId());
					List<Integer> jdlist=new ArrayList<Integer>();
					jdlist.add(21);
					jdlist.add(22);
					tododbyw.getParamsMap().put("jdlist", jdlist);
					tododbyw.setZt(TodoStatusEnum.DCL.getId());
					tododbyw.setBlrid(user.getId());
					tododbyw.setFksj(currentDate);
					todoService.updateByDbyw(tododbyw);
					
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("请评估审核问题:");
					if(auditDiscoveryDetail.getWtms().length()>1000){
						strSm.append(auditDiscoveryDetail.getWtms().substring(0, 1000));
					}else{
						strSm.append(auditDiscoveryDetail.getWtms());
					}
					todoService.insertSelectiveTechnical(auditDiscoveryDetail,strSm.toString(),"quality:rectifymeasuresfollow:main",NodeEnum.NODE23.getId(),null,TodoEnum.NSWTZG.getId());
					
				}
			}
			
			//流程记录 
			processRecordService.addBatchRecord(idList, detail.getDprtcode(),"已反馈");
			
			
		}
	}
	/**
	 * 
	 * @Description 校验问题清单
	 * @CreateTime 2018年1月15日 上午10:43:58
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @throws BusinessException 
	 */
	private AuditDiscoveryDetail validationDetails(List<String> idList) throws BusinessException{
		if(null != idList && idList.size()>0){
			List<AuditDiscoveryDetail> list = auditDiscoveryDetailMapper.getByIdList(idList);
			if(null != list && list.size()>0){
				StringBuffer sbf = new StringBuffer();
				boolean flag = true;
				for (AuditDiscoveryDetail detail : list) {
					if(ProblemStatusEnum.SUBMIT.getId() != detail.getZt() && ProblemStatusEnum.EXECUTE.getId() != detail.getZt()
							&& ProblemStatusEnum.REJECT.getId() != detail.getZt()){
						sbf.append("问题清单:"+detail.getShwtbh()+"状态已更新！<br>");
					}
				}
				if(!flag){
					throw new BusinessException(sbf.toString());
				}
				return list.get(0);
			}
		}else{
			throw new BusinessException("请选中数据后再进行操作！");
		}
		return null;
	}
	
	/**
     * 
     * @Description 批准
     * @CreateTime 2018年1月17日 上午11:50:39
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
	@Override
	public String update4Approve(AuditDiscoveryDetail record) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		//验证状态
		AuditDiscoveryDetail detail = validation4Approve(record.getId(),ProblemStatusEnum.FEEDBACK.getId());
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhbmid(user.getBmdm());
		record.setWhsj(new Date());
		record.setPgrid(user.getId());
		record.setPgrbmid(user.getBmdm());
		record.setPgsj(new Date());
		auditDiscoveryDetailMapper.updateByPrimaryKeySelective(record);
		//流程记录
		processRecordService.addRecord(record.getId(), detail.getDprtcode(),"评估");
		//处理附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), UUID.randomUUID().toString(), record.getId(), record.getDprtcode(),
				LogOperationEnum.EDIT);
		AuditDiscoveryDetail auditDiscoveryDetail =auditDiscoveryDetailMapper.selectByPrimaryKey(record.getId());
		
		//新增待办事宜
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(record.getId());
		List<Integer> jdlist1=new ArrayList<Integer>();
		jdlist1.add(23);
		tododbyw.getParamsMap().put("jdlist", jdlist1);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		if(record.getZt()==4){
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请验证整改内容：");
			if(auditDiscoveryDetail.getWtms().length()>1000){
				strSm.append(auditDiscoveryDetail.getWtms().substring(0, 1000));
			}else{
				strSm.append(auditDiscoveryDetail.getWtms());
			}
			todoService.insertSelectiveTechnical(auditDiscoveryDetail,strSm.toString(),"quality:rectifymeasuresfollow:main",NodeEnum.NODE24.getId(),null,TodoEnum.NSWTZG.getId());
		}
		
		//评估界面关闭删除待办(评估科关闭)
		if(Integer.valueOf(5).equals(record.getZt())){
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(NodeEnum.NODE22.getId());
			todorsService.deleteToDo(record.getId(),UndoStatusEnum.DCL.getId() , jdlist);
		}
		
		return record.getId();
	}
	/**
	 * 
	 * @Description 验证、评估状态
	 * @CreateTime 2018年1月17日 下午2:02:55
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	private AuditDiscoveryDetail validation4Approve(String id,int zt) throws BusinessException {
		AuditDiscoveryDetail record = auditDiscoveryDetailMapper.selectByPrimaryKey(id);
		if (null != record && zt !=record.getZt()) {
			throw new BusinessException("该问题清单已更新，请刷新页面后操作！");
		}
		return record;
	}
	/**
     * 
     * @Description 整改措施评估
     * @CreateTime 2018年1月17日 下午4:37:49
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
	public Map<String, Object> queryReticList(AuditDiscoveryDetail record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		try {
			PageHelper.startPage(record.getPagination());
			List<AuditDiscoveryDetail> recordList = auditDiscoveryDetailMapper.queryReticList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						AuditDiscoveryDetail newRecord = new AuditDiscoveryDetail();
						newRecord.setId(id);
						List<AuditDiscoveryDetail> newRecordList = auditDiscoveryDetailMapper.queryReticList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<AuditDiscoveryDetail> newRecordList = new ArrayList<AuditDiscoveryDetail>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					AuditDiscoveryDetail newRecord = new AuditDiscoveryDetail();
					newRecord.setId(id);
					newRecordList = auditDiscoveryDetailMapper.queryReticList(record);
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
     * @Description 验证提交
     * @CreateTime 2018年1月18日 上午9:36:29
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
	@Override
	public String update4Valid(AuditDiscoveryDetail record) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		//验证状态
		AuditDiscoveryDetail detail = validation4Approve(record.getId(),ProblemStatusEnum.TO_BE_VERIFIED.getId());
		User user = ThreadVarUtil.getUser();
		record.setYzrid(user.getId());
		record.setYzrbmid(user.getBmdm());
		record.setYzsj(new Date());
		auditDiscoveryDetailMapper.updateByPrimaryKeySelective(record);
		//流程记录
		processRecordService.addRecord(record.getId(), detail.getDprtcode(),"验证");
		//处理附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), UUID.randomUUID().toString(), record.getId(), record.getDprtcode(),
				LogOperationEnum.EDIT);
		
		//新增待办事宜
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(record.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(24);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		return record.getId();
		
	}
	
	/**
     * 
     * @Description 关闭
     * @CreateTime 2018年1月18日 上午11:57:36
     * @CreateBy 岳彬彬
     * @param record
     * @return
     * @throws BusinessException
     */
	@Override
	public String update4Close(AuditDiscoveryDetail record) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		//验证状态
		AuditDiscoveryDetail detail = validation4Close(record.getId());
		StringBuffer sbf = new StringBuffer();
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhbmid(user.getBmdm());
		record.setWhsj(new Date());
		if(ProblemStatusEnum.CLOSED.getId() == record.getZt()){
			record.setGbrid(user.getId());
			record.setGbrbmid(user.getBmdm());
			record.setGbsj(new Date());	
			sbf.append("关闭");
			
			//删除待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(record.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(22);
			jdlist.add(23);
			jdlist.add(24);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			todoService.deletedbyw(tododbyw);
			
			
			
		}else if(ProblemStatusEnum.REJECT.getId() == record.getZt()){	
			//新增待办事宜
			AuditDiscoveryDetail  details=auditDiscoveryDetailMapper.getById(record.getId());
			AuditDiscovery auditDiscovery=auditDiscoveryMapper.selectByPrimaryKey(details.getShwtdid());
			sbf.append("请整改:").append(StringUtils.isBlank(details.getWtms())?"":details.getWtms());
			if(sbf.length()>1000)
				sbf.substring(0, 1000);
			todorsService.updateToDo(record.getId(), NodeEnum.NODE21.getId(), UndoStatusEnum.DCL.getId(), user.getId());
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), record.getId(),
					details.getShwtbh(), null, null, UndoEnum.NSWTZG.getId(), NodeEnum.NODE21.getId(),
					sbf.toString(), auditDiscovery.getYqfkrq(), auditDiscovery.getZrrid(), "quality:correctivemeasures:main", record.getId(), null);
			//关闭待办
			Todo tododbyw1=new Todo();
			tododbyw1.setDbywid(record.getId());
			List<Integer> jdlist1=new ArrayList<Integer>();
			jdlist1.add(NodeEnum.NODE23.getId());
			tododbyw1.getParamsMap().put("jdlist", jdlist1);
			tododbyw1.setZt(TodoStatusEnum.DCL.getId());
			tododbyw1.setBlrid(user.getId());
			tododbyw1.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw1);
			
			
		}
		auditDiscoveryDetailMapper.updateByPrimaryKeySelective(record);
		//流程记录
		processRecordService.addRecord(record.getId(), detail.getDprtcode(),sbf.toString());
		//处理附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), UUID.randomUUID().toString(), record.getId(), record.getDprtcode(),
				LogOperationEnum.EDIT);
		return record.getId();
	}
	/**
	 * 
	 * @Description 关闭和驳回验证状态
	 * @CreateTime 2018年1月18日 下午1:46:06
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	private AuditDiscoveryDetail validation4Close(String id) throws BusinessException {
		AuditDiscoveryDetail record = auditDiscoveryDetailMapper.selectByPrimaryKey(id);
		if (null != record && (ProblemStatusEnum.SAVE.getId() == record.getZt() || ProblemStatusEnum.CLOSED.getId() == record.getZt())) {
			throw new BusinessException("该问题清单已更新，请刷新页面后操作！");
		}
		return record;
	}
}
