package com.eray.thjw.material2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockFreezeHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.StockFreezeHistory;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.material2.dao.ScrappedApplyMapper;
import com.eray.thjw.material2.dao.ScrappedInfoMapper;
import com.eray.thjw.material2.po.ScrappedApply;
import com.eray.thjw.material2.po.ScrappedInfo;
import com.eray.thjw.material2.service.ScrappedApplyService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.MaterialTypeEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
import enu.common.EffectiveEnum;
import enu.material2.ApplyStatusEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
/**
 * 
 * @Description 报废申请
 * @CreateTime 2018年3月22日 上午11:29:07
 * @CreateBy 岳彬彬
 */
@Service
public class ScrappedApplyServiceImpl implements ScrappedApplyService{
	
	@Resource
	private ScrappedApplyMapper scrappedApplyMapper;	
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private StockMapper stockMapper;
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	@Resource
	private ScrappedInfoMapper scrappedInfoMapper;
	@Resource
	private StockFreezeHistoryMapper stockFreezeHistoryMapper;
	@Resource
	private MaterialRecService materialRecService;
	@Resource
	private ProcessRecordService processRecordService;
	@Resource
	private TodorsService todorsService;
	
	/**
	 * 
	 * @Description 报废申请主列表
	 * @CreateTime 2018年3月22日 下午2:30:17
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getScrappedApplyList(ScrappedApply record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<ScrappedApply> recordList = scrappedApplyMapper.getAllList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						ScrappedApply newRecord = new ScrappedApply();
						newRecord.setId(id);
						List<ScrappedApply> newRecordList = scrappedApplyMapper.getAllList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<ScrappedApply> newRecordList = new ArrayList<ScrappedApply>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					ScrappedApply newRecord = new ScrappedApply();
					newRecord.setId(id);
					newRecordList = scrappedApplyMapper.getAllList(newRecord);
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
	 * @Description 获取报废申请明细信息
	 * @CreateTime 2018年3月22日 下午2:28:39
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> getListById(ScrappedApply record) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageHelper.startPage(record.getPagination());
		List<Map<String,Object>> list = scrappedApplyMapper.getListById(record);
		resultMap = PageUtil.pack4PageHelper(list, record.getPagination());
		return resultMap;
	}
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年3月22日 下午5:18:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String addRecord(ScrappedApply record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		if(StringUtils.isNotBlank(record.getBfdh())){
			//验证报废单号唯一
			checkExist(record.getBfdh(),user.getJgdm());
		}else{
			record.setBfdh(buildBfdh(user.getJgdm()));
		}
		String id = UUID.randomUUID().toString();
		record.setId(id);
		record.setDprtcode(user.getJgdm());
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setSqrid(user.getId());
		record.setWhbmid(user.getBmdm());
		record.setSqbmid(user.getBmdm());
		record.setSqsj(new Date());
		//报废从表信息
		addInfo(record,user);
		//保存报废主表信息
		scrappedApplyMapper.insertSelective(record);
		
		//写待办
		if(ApplyStatusEnum.SUBMIT.getId().equals(record.getZt())){
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), id, record.getBfdh(),
					null, null, UndoEnum.BF.getId(), NodeEnum.NODE3.getId(), 
			"请审批 "+record.getBfdh()+"报废单", null, record.getSprid(), "material:scrapped:audit:main", id, null);
			
			todorsService.updateToDo(record.getId(), NodeEnum.NODE5.getId(), TodoStatusEnum.DCL.getId(), user.getId());
		}
		
		
		return id;
	}
	/**
	 * 
	 * @Description 验证报废单号唯一
	 * @CreateTime 2018年3月22日 下午5:28:40
	 * @CreateBy 岳彬彬
	 * @param bfdh
	 * @param dprtcode
	 * @throws BusinessException
	 */
	private void checkExist(String bfdh, String dprtcode) throws BusinessException{
		int iCount = scrappedApplyMapper.getCount4CheckExist(bfdh, dprtcode);
		if(iCount > 0){
			throw new BusinessException(new StringBuffer().append("报废单号号[").append(bfdh).append("]已存在!").toString());
		}
	}
	/**
	 * 
	 * @Description 生成报废单号
	 * @CreateTime 2018年3月22日 下午5:31:08
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	private String buildBfdh(String dprtcode) throws BusinessException {
		String bfdh = null;
		boolean b = true;
		while(b){ 
			
			try {
				bfdh = SNGeneratorFactory.generate(dprtcode, "6_HC_BFD");
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			int iCount = scrappedApplyMapper.getCount4CheckExist(bfdh, dprtcode);
			if(iCount <= 0){
				b = false;
			}
		}
		return bfdh;
	}
	/**
	 * 
	 * @Description 处理关联表数据
	 * @CreateTime 2018年3月22日 下午5:46:34
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException 
	 */
	private void addInfo(ScrappedApply record, User user) throws BusinessException{
		//附件信息
		String czls = UUID.randomUUID().toString();//操作流水
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(), record.getDprtcode(), LogOperationEnum.SAVE_WO);
		List<ScrappedInfo> infoList = record.getInfoList();//报废申请明细
		if(null != infoList&& infoList.size()>0){
			List<String> idList = new ArrayList<String>();
			for (ScrappedInfo scrappedInfo : infoList) {
				idList.add(scrappedInfo.getKcid());
			}
			List<Stock>  list = stockMapper.getList4ExistByIdlist(idList);
			if(null != list && list.size() == idList.size()){//单个报废单不能重复选择同一个库存	
				//写库存履历数据
				List<MaterialHistory> materialHistoryList = new ArrayList<MaterialHistory>();//库存履历集合
				for (Stock stock : list) {
					MaterialHistory materialHistory=new MaterialHistory(stock);//履历表实体
					String materialHistoryuuid = UUID.randomUUID().toString();//库存履历id
					for (ScrappedInfo scrappedInfo : infoList) {
						if(scrappedInfo.getKcid().equals(stock.getId())){
							//验证报废数量<=库存数量-冻结数量
							double kcsl = stock.getKcsl() == null?0:stock.getKcsl().doubleValue();
							double djsl = stock.getDjsl() == null?0:stock.getDjsl().doubleValue();
							double bfsl = scrappedInfo.getKcsl() == null?0:scrappedInfo.getKcsl().doubleValue();
							if(kcsl <= 0){
								throw new BusinessException("该报废单中选择的部件"+stock.getBjh()+"的库存数量为0!");
							}
							if(kcsl - djsl - bfsl <0){
								throw new BusinessException("该报废单中选择的部件"+stock.getBjh()+"的报废数量大于库存数量-冻结数量!");
							}else{								
								materialHistory.setKcsl(scrappedInfo.getKcsl());//库存履历数量为报废数量
								scrappedInfo.setId(UUID.randomUUID().toString());
								scrappedInfo.setMainid(record.getId());
								scrappedInfo.setWhdwid(user.getBmdm());
								scrappedInfo.setWhrid(user.getId());
								scrappedInfo.setKcllid(materialHistoryuuid);
							}
						}
					}			
					materialHistory.setId(materialHistoryuuid);
					materialHistoryList.add(materialHistory);
				}
				// 新增库存履历
				materialHistoryMapper.insertBatchMaterialHistory(materialHistoryList);
				// 写b_h2_00901的数据
				scrappedInfoMapper.insertBachInfo(infoList);
				if(ApplyStatusEnum.SAVE.getId() == record.getZt()){
					// 记录详情新增的流程记录
					processRecordService.addRecord(record.getId(), record.getDprtcode(), "保存");
				}
				// 状态为2提交需要写冻结履历和流程记录
				if(ApplyStatusEnum.SUBMIT.getId() == record.getZt()){
					//冻结履历操作
					addStockFreeze(infoList,czls,record.getBfdh());
					// 记录详情新增的流程记录
					processRecordService.addRecord(record.getId(), record.getDprtcode(), "提交");
				}			
			}else{
				throw new BusinessException("该报废单中选择的部件在库存中已经不存在!");
			}
		}else{
			throw new BusinessException("该报废单没有选择部件!");
		}
	}
	/**
	 * 
	 * @Description 写冻结履历
	 * @CreateTime 2018年3月23日 下午4:57:10
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	private void addStockFreeze(List<ScrappedInfo> list,String czls,String bfdh){
		List<StockFreezeHistory> freezeList = new ArrayList<StockFreezeHistory>();
		List<String> idList = new ArrayList<String>();
		StockFreezeHistory stockFreeze ;
		for (ScrappedInfo scrappedInfo : list) {
			stockFreeze = new StockFreezeHistory();
			String id = UUID.randomUUID().toString();
			stockFreeze.setId(id);
			stockFreeze.setKcid(scrappedInfo.getKcid());
			stockFreeze.setDjsl(scrappedInfo.getKcsl());
			stockFreeze.setKclx(1);
			stockFreeze.setYwlx(9);
			stockFreeze.setYwid(scrappedInfo.getId());
			stockFreeze.setYwbh(bfdh);
			stockFreeze.setCzsj(new Date());
			freezeList.add(stockFreeze);
			idList.add(scrappedInfo.getKcid());
			//库存日志
			materialRecService.writeStockRec(scrappedInfo.getKcid(), czls, scrappedInfo.getMainid(), bfdh,
					StockRecBizTypeEnum.TYPE6, UpdateTypeEnum.UPDATE, "", bfdh,
					scrappedInfo.getKcsl());
		}
		stockFreezeHistoryMapper.insertBatchRecord(freezeList);//批量增加冻结履历
		stockMapper.update4Djsl(idList);//批量更新库存表冻结数据
	}
	/**
	 * 根据id获取数据
	 */
	@Override
	public ScrappedApply getRecord(String id) throws BusinessException {
		
		return scrappedApplyMapper.getRecord(id);
	}
	/**
	 * 
	 * @Description 更新
	 * @CreateTime 2018年3月23日 下午4:29:02
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateRecrod(ScrappedApply record) throws BusinessException {
		//验证状态
		doValidation4Zt(record);
		//处理主表数据
		User user = ThreadVarUtil.getUser();
		record.setWhbmid(user.getBmdm());
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setSqrid(user.getId());
		record.setWhbmid(user.getBmdm());
		record.setSqbmid(user.getBmdm());
		record.setSqsj(new Date());
		scrappedApplyMapper.updateByPrimaryKey(record);
		//处理从表
		doDetail(record,user);		
		//写待办
		if(ApplyStatusEnum.SUBMIT.getId().equals(record.getZt())){
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), record.getId(), record.getBfdh(),
					null, null, UndoEnum.BF.getId(), NodeEnum.NODE3.getId(), 
			"请审批 "+record.getBfdh()+"报废单", null, record.getSprid(), "material:scrapped:audit:main", record.getId(), null);
			
			todorsService.updateToDo(record.getId(), NodeEnum.NODE5.getId(), TodoStatusEnum.DCL.getId(), user.getId());		
		}
	
		return record.getId();
	}
	/**
	 * 
	 * @Description 验证状态
	 * @CreateTime 2018年3月23日 下午5:18:24
	 * @CreateBy 岳彬彬
	 * @throws BusinessException
	 */
	private void doValidation4Zt(ScrappedApply record)throws BusinessException {
		ScrappedApply newRecord = scrappedApplyMapper.selectByPrimaryKey(record.getId());
		if(null == newRecord){
			throw new BusinessException("该报废单已被删除，请刷新页面!");
		}
		if(null != newRecord && ApplyStatusEnum.SAVE.getId() != newRecord.getZt() && ApplyStatusEnum.AUDITDOWN.getId() != newRecord.getZt()){
			throw new BusinessException("该报废单已更新，请刷新页面!");
		}
	}
	/**
	 * 
	 * @Description 处理从表数据
	 * @CreateTime 2018年3月23日 下午5:31:08
	 * @CreateBy 岳彬彬
	 * @throws BusinessException
	 */
	private void doDetail(ScrappedApply record,User user)throws BusinessException {
		scrappedInfoMapper.deleteByMainid(record.getId());//删除报废明细数据
		//新增明细数据及相关联数据
		addInfo(record,user);
	}
	/**
	 * 
	 * @Description 删除数据
	 * @CreateTime 2018年3月23日 下午5:31:08
	 * @CreateBy 岳彬彬
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(ScrappedApply record) throws BusinessException {
		//验证状态
		doValidation4Zt(record);
		scrappedApplyMapper.deleteByPrimaryKey(record.getId());//删除报废主表数据
		scrappedInfoMapper.deleteByMainid(record.getId());//删除报废明细数据
		processRecordService.deleteRecord(record.getId());//删除流程记录
		
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(NodeEnum.NODE3.getId());
		jdlist.add(NodeEnum.NODE5.getId());
		//添加待办
		todorsService.deleteToDo(record.getId(),UndoStatusEnum.DCL.getId(),jdlist);
		
	}
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年3月26日 上午11:45:56
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateRecord4Close(ScrappedApply record) throws BusinessException {
		//验证状态
		doValidation4Clsoe(record);
		//验证报废单明细(在报废明细中没有一个已经销毁才能关闭)
		doValidation4Info(record);
		//修改主表数据
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setWhbmid(user.getBmdm());
		record.setZdjsrid(user.getId());
		record.setZdjsrq(new Date());
		record.setZt(ApplyStatusEnum.CLOSED.getId());
		scrappedApplyMapper.updateByPrimaryKeySelective(record);
		//解冻库存
		List<String> kcidList = doDetail4ThawingStock(record,UpdateTypeEnum.CLOSE);
		
		 //指令结束删除待办
		todorsService.deleteToDo(record.getId(), null, null);
		  for(int i=0;kcidList!=null&&i<kcidList.size();i++){
			  todorsService.deleteToDo(kcidList.get(i), null, null);
		  }
		    	 
		   

		
		return record.getId();
	}
	/**
	 * 
	 * @Description 验证报废单状态是否为提交或已审核状态
	 * @CreateTime 2018年3月26日 上午11:48:47
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void doValidation4Clsoe(ScrappedApply record)throws BusinessException {
		ScrappedApply newRecord = scrappedApplyMapper.selectByPrimaryKey(record.getId());
		if(null == newRecord){
			throw new BusinessException("该报废单已被删除，请刷新页面!");
		}
		if(null != newRecord && ApplyStatusEnum.SUBMIT.getId() != newRecord.getZt() && ApplyStatusEnum.AUDIT.getId() != newRecord.getZt()){
			throw new BusinessException("该报废单已更新，请刷新页面!");
		}
	}
	/**
	 * 
	 * @Description 在报废明细中没有一个已经销毁才能关闭
	 * @CreateTime 2018年3月26日 上午11:49:52
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void doValidation4Info(ScrappedApply record)throws BusinessException {
		int count = scrappedInfoMapper.doValidation4Close(record.getId());
		if(count >0){
			throw new BusinessException("该报废单下有已经销毁的部件!");
		}
	}
	/**
	 * 
	 * @Description 获取该报废单下面所有的明细单处理冻结履历数据(解冻库存)
	 * @CreateTime 2018年3月26日 下午1:34:30
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private List<String> doDetail4ThawingStock(ScrappedApply record,UpdateTypeEnum type)throws BusinessException {
		List<ScrappedInfo> list = scrappedInfoMapper.selectByMainid(record.getId());
		String czls = UUID.randomUUID().toString();
		//处理冻结履历数据
		List<String> ywidList = new ArrayList<String>();
		List<String> kcidList = new ArrayList<String>();
		for (ScrappedInfo scrappedInfo : list) {
			ywidList.add(scrappedInfo.getId());
			kcidList.add(scrappedInfo.getKcid());
			//库存日志
			materialRecService.writeStockRec(scrappedInfo.getKcid(), czls, record.getId(), record.getBfdh(),
					StockRecBizTypeEnum.TYPE6, type, "", record.getBfdh(),
					scrappedInfo.getKcsl());
		}
		stockFreezeHistoryMapper.deleteByYwidList(ywidList);//删除冻结履历数据
		stockMapper.update4Djsl(kcidList);//批量更新库存表冻结数据
		return kcidList;
	}
	/**
	 * 
	 * @Description 报废审核列表
	 * @CreateTime 2018年3月26日 下午2:07:23
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getAuditList(ScrappedApply record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<ScrappedApply> recordList = scrappedApplyMapper.getAuditList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						ScrappedApply newRecord = new ScrappedApply();
						newRecord.setId(id);
						List<ScrappedApply> newRecordList = scrappedApplyMapper.getAuditList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<ScrappedApply> newRecordList = new ArrayList<ScrappedApply>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					ScrappedApply newRecord = new ScrappedApply();
					newRecord.setId(id);
					newRecordList = scrappedApplyMapper.getAuditList(newRecord);
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
	 * @Description 审核
	 * @CreateTime 2018年3月27日 上午9:57:56
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update4Audit(ScrappedApply record) throws BusinessException {
		//验证报废单状态为提交状态
		doValidation4Audit(record);
		//审核驳回
		if(ApplyStatusEnum.AUDITDOWN.getId() == record.getZt()){
			//根据报废明细解冻库存
			doDetail4ThawingStock(record,UpdateTypeEnum.AUDIT);
			// 记录详情新增的流程记录
			processRecordService.addRecord(record.getId(), record.getDprtcode(), "审核驳回");
			
			//报废审核驳回添加待办
			addTodoReject(record);
			
		}else{
			// 记录详情新增的流程记录
			processRecordService.addRecord(record.getId(), record.getDprtcode(), "审核通过");
			
			//报废审核通过添加待办
		     addTodoPass(record);
			
		}
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setWhbmid(user.getBmdm());
		record.setSpbmid(user.getBmdm());
		record.setSpsj(new Date());
		scrappedApplyMapper.updateByPrimaryKeySelective(record);
		return record.getId();
	}
	
	private void addTodoReject(ScrappedApply record) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), record.getId(),
		record.getBfdh(), null, null, UndoEnum.BF.getId(), NodeEnum.NODE5.getId(), record.getBfdh()+"报废申请已经驳回,请重新提交", null, record.getSqrid(),"material:scrapped:apply:main", record.getId(), null);	
		todorsService.updateToDo(record.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());
	}
	private void addTodoPass(ScrappedApply record) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		List<Map<String, Object>>  list=scrappedApplyMapper.getListById(record);
		String kcid="";
		String bjywms="";
		String bjh="";
		String xlh="";
		String pch="";
		BigDecimal wllx=null;
		String sm="";
		String gnbm="";
		Integer dbgzlx=null;
		for(int i=0;list!=null&&i<list.size();i++){
			Map<String,Object> map=list.get(i);
			kcid=map.get("KCID")==null?"":map.get("KCID").toString();
			bjywms=map.get("YWMS")==null?"":map.get("YWMS").toString();
			bjh=map.get("BJH")==null?"":map.get("BJH").toString();
			xlh=map.get("SN")==null?"":map.get("SN").toString();
			pch=map.get("PCH")==null?"":map.get("PCH").toString();
			wllx=(BigDecimal) (map.get("HCLX")==null?null:map.get("HCLX"));
			
			if(MaterialTypeEnum.APPOINTED.getId().intValue()!=wllx.intValue()){//如果不是工具设备
				dbgzlx=UndoEnum.HCXHXJ.getId();
				gnbm="material:destroy:airmaterial:main";
			}else{//如果是工具设备
				dbgzlx=UndoEnum.GJXHXJ.getId();
				gnbm="material:destroy:tool:main";
			}

			StringBuilder  smXj=new StringBuilder("");
			smXj.append("待销毁下架: ").append(bjywms);
			if(StringUtils.isNotBlank(bjh)){
				smXj.append(" / ").append(bjh);
			}
			if(StringUtils.isNotBlank(xlh)){
				smXj.append(" / ").append(xlh);
			}
			if(StringUtils.isNotBlank(pch)){
				smXj.append(" / ").append(pch);
			}
			
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), kcid, null, null, null, dbgzlx, NodeEnum.NODE32.getId(),
					smXj.toString(), null, null, gnbm, kcid, null);
				
		}
		
		todorsService.updateToDo(record.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());
		
	}
	/**
	 * 
	 * @Description 验证报废单状态为提交状态
	 * @CreateTime 2018年3月27日 上午10:01:19
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void doValidation4Audit(ScrappedApply record)throws BusinessException {
		ScrappedApply newRecord = scrappedApplyMapper.selectByPrimaryKey(record.getId());
		if(null == newRecord){
			throw new BusinessException("该报废单已被删除，请刷新页面!");
		}
		if(null != newRecord && ApplyStatusEnum.SUBMIT.getId() != newRecord.getZt()){
			throw new BusinessException("该报废单已更新，请刷新页面!");
		}
	}
}
