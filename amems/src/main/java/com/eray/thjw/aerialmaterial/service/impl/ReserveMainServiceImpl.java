package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.MaterialToPlaneModelMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveMainMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveWorkOrderMapper;
import com.eray.thjw.aerialmaterial.po.MaterialToPlaneModel;
import com.eray.thjw.aerialmaterial.po.ReserveDetail;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.po.ReserveWorkOrder;
import com.eray.thjw.aerialmaterial.service.ReserveMainService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.EnquiryStatusEnum;
import enu.FormTypeEnum;
import enu.MaterialProgressEnum;
import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 航材提订管理service,用于业务逻辑处理
 * @develop date 2016.10.12
 */
@Service
public class ReserveMainServiceImpl implements ReserveMainService {
	
	/**
	 * @author liub
	 * @description 航材提订管理Mapper
	 * @develop date 2016.10.12
	 */
    @Resource
    private ReserveMainMapper reserveMainMapper;
    
    /**
	 * @author liub
	 * @description 采番service
	 * @develop date 2016.08.16
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	/**
	 * @author liub
	 * @description 提订详情Mapper
	 * @develop date 2016.10.13
	 */
    @Resource
    private ReserveDetailMapper reserveDetailMapper;
    
    /**
   	 * @author liub
   	 * @description 航材机型关系Mapper
   	 * @develop date 2016.09.19
   	 */
   @Resource
   private MaterialToPlaneModelMapper materialToPlaneModelMapper;
   
   /**
  	 * @author liub
  	 * @description 提订单/送修单-相关工单Mapper
  	 * @develop date 2016.09.19
  	 */
  @Resource
  private ReserveWorkOrderMapper reserveWorkOrderMapper;
   
   /**
  	 * @author liub
  	 * @description 附件service
  	 * @develop date 2016.11.06
  	 */
   @Resource
   private AttachmentService attachmentService;
	
	/**
	 * @author liub
	 * @description 增加提订单
	 * @param reserve
	 * @develop date 2016.10.12
	 * @throws BusinessException 
	 */
	@Override
	public String add(ReserveMain reserve) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String sqdh;
		try {
			sqdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCTD.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		reserve.setId(id);
		reserve.setSqdh(sqdh);
		reserve.setDjlx(FormTypeEnum.RESERVE_FORM.getId());
		reserve.setDprtcode(user.getJgdm());
		reserve.setSqrid(user.getId());
		reserve.setSqbmid(user.getBmdm());
		reserveMainMapper.insertSelective(reserve);
		//增加提订详情
		for (ReserveDetail reserveDetail : reserve.getReserveDetailList()) {
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			reserveDetail.setId(detailId);
			reserveDetail.setMainid(id);
			reserveDetail.setZt(EffectiveEnum.YES.getId());
			reserveDetail.setXjzt(EnquiryStatusEnum.NO_ENQUIRY.getId());
			reserveDetail.setShsl(BigDecimal.ZERO);
			reserveDetail.setSl(BigDecimal.ZERO);
			reserveDetail.setYcgsl(BigDecimal.ZERO);
			reserveDetail.setWhrid(user.getId());
			reserveDetail.setWhdwid(user.getBmdm());
			reserveDetailMapper.insertSelective(reserveDetail);
		}
		//添加相关工单
		List<ReserveWorkOrder> reserveWorkOrderList = reserve.getReserveWorkOrderList();
		if(null != reserveWorkOrderList && reserveWorkOrderList.size() > 0){
			for (ReserveWorkOrder reserveWorkOrder : reserveWorkOrderList) {
				reserveWorkOrder.setId(UUID.randomUUID().toString());
				reserveWorkOrder.setMainid(id);
				reserveWorkOrder.setZt(EffectiveEnum.YES.getId());
				reserveWorkOrder.setWhrid(user.getId());
				reserveWorkOrder.setWhdwid(user.getBmdm());
				reserveWorkOrderMapper.insertSelective(reserveWorkOrder);
			}
		}
		return id;
	}
	
	/**
	 * @author liub
	 * @description 编辑提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@Override
	public void edit(ReserveMain reserve) throws BusinessException{
		
		ReserveMain oldRm = reserveMainMapper.selectByPrimaryKey(reserve.getId());
		verification(new Integer[]{ReserveStatusEnum.SAVE.getId()},oldRm.getZt(),"该提订单已更新，请刷新后再进行操作!");
		
		List<String> oldReserveDetailIdList = reserve.getReserveDetailIdList();
		List<String> newReserveDetailIdList = new ArrayList<String>();
		//编辑提订单
		User user = ThreadVarUtil.getUser();
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
		//新增或编辑提订详情信息
		for(ReserveDetail reserveDetail : reserve.getReserveDetailList()){
			newReserveDetailIdList.add(reserveDetail.getId());
			reserveDetail.setWhrid(user.getId());
			reserveDetail.setWhdwid(user.getBmdm());
			if(null != reserveDetail.getId() && !"".equals(reserveDetail.getId())){
				reserveDetailMapper.updateByPrimaryKeySelective(reserveDetail);
			}else{
				UUID uuid2 = UUID.randomUUID();//获取随机的uuid
				String detailId = uuid2.toString();
				reserveDetail.setId(detailId);
				reserveDetail.setMainid(reserve.getId());
				reserveDetail.setZt(EffectiveEnum.YES.getId());
				reserveDetail.setXjzt(EnquiryStatusEnum.NO_ENQUIRY.getId());
				reserveDetailMapper.insertSelective(reserveDetail);
			}
		}
		//删除提订详情信息
		for (String oldReserveDetailId : oldReserveDetailIdList) {
			if(!newReserveDetailIdList.contains(oldReserveDetailId)){
				reserveDetailMapper.deleteByPrimaryKey(oldReserveDetailId);
			}
		}
		reserveWorkOrderMapper.deleteByMainId(reserve.getId());
		//添加相关工单
		List<ReserveWorkOrder> reserveWorkOrderList = reserve.getReserveWorkOrderList();
		if(null != reserveWorkOrderList && reserveWorkOrderList.size() > 0){
			for (ReserveWorkOrder reserveWorkOrder : reserveWorkOrderList) {
				reserveWorkOrder.setId(UUID.randomUUID().toString());
				reserveWorkOrder.setMainid(reserve.getId());
				reserveWorkOrder.setZt(EffectiveEnum.YES.getId());
				reserveWorkOrder.setWhrid(user.getId());
				reserveWorkOrder.setWhdwid(user.getBmdm());
				reserveWorkOrderMapper.insertSelective(reserveWorkOrder);
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 修改提订详情
	 * @param reserveDetail
	 * @develop date 2016.12.16
	 * @throws BusinessException 
	 */
	@Override
	public void updateReserveDetail(ReserveDetail reserveDetail) throws BusinessException{
		ReserveDetail obj = reserveDetailMapper.selectByPrimaryKey(reserveDetail.getId());
		verification(new Integer[]{EnquiryStatusEnum.NO_ENQUIRY.getId()},obj.getXjzt(),"该提订单已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		reserveDetail.setWhrid(user.getId());
		reserveDetail.setWhdwid(user.getBmdm());
		reserveDetailMapper.updateByPrimaryKeySelective(reserveDetail);
	}
	
	/**
	 * @author liub
	 * @description 审核提订单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@Override
	public void editAudit(ReserveMain reserve) throws BusinessException{
		
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(reserve.getId());
		verification(new Integer[]{ReserveStatusEnum.SUBMIT.getId()},obj.getZt(),"该提订单已更新，请刷新后再进行操作!");
		
		//编辑提订单
		User user = ThreadVarUtil.getUser();
		reserve.setSprid(user.getId());
		reserve.setSpbmid(user.getBmdm());
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
		//编辑提订详情信息
		for(ReserveDetail reserveDetail : reserve.getReserveDetailList()){
			reserveDetail.setWhrid(user.getId());
			reserveDetail.setWhdwid(user.getBmdm());
			reserveDetail.setSl(reserveDetail.getShsl());
			reserveDetailMapper.updateByPrimaryKeySelective(reserveDetail);
		}
		attachmentService.eidtAttachment(reserve.getAttachments(), reserve.getId(),obj.getDprtcode());//编辑附件
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @develop date 2016.10.14
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		ReserveMain oldRm = reserveMainMapper.selectByPrimaryKey(id);
		verification(new Integer[]{ReserveStatusEnum.SAVE.getId()},oldRm.getZt(),"该提订单已更新，请刷新后再进行操作!");
		ReserveMain reserve = new ReserveMain();
		reserve.setId(id);
		reserve.setZt(ReserveStatusEnum.CANCEL.getId());
		updateByPrimaryKeySelective(reserve);
	}
	
	/**
	 * @author liub
	 * @description 编辑提订单信息
	 * @param reserve
	 * @develop date 2016.10.14
	 */
	@Override
	public void updateByPrimaryKeySelective(ReserveMain reserve){
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
	}
	
	/**
	 * @author liub
	 * @description 提订单指定结束
	 * @param reserve
	 * @develop date 2017.04.12
	 */
	@Override
	public void updateShutDown(ReserveMain reserve) throws BusinessException{
		ReserveMain oldRm = reserveMainMapper.selectByPrimaryKey(reserve.getId());
		verification(new Integer[]{ReserveStatusEnum.SUBMIT.getId(),ReserveStatusEnum.AUDIT_PASS.getId()},oldRm.getZt(),"该提订单已更新，请刷新后再进行操作!");
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
	}
	
	/**
	 * @author liub
	 * @description 检查提订单编辑权限
	 * @param id
	 * @develop date 2016.10.13
	 */
	@Override
	public void checkEdit(String id) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(id);
		//验证是否保存状态
		Integer zt = obj.getZt();
		if(null == zt || ReserveStatusEnum.SAVE.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有保存状态下的提订单才能编辑!");
		}
	}
	
	/**
	 * @author liub
	 * @description 检查提订单审核权限
	 * @param id
	 * @develop date 2016.10.14
	 */
	@Override
	public void checkAudit(String id) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(id);
		//验证是否保存状态
		Integer zt = obj.getZt();
		if(null == zt || ReserveStatusEnum.SUBMIT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有提交状态下的提订单才能审核!");
		}
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.10.12
	 */
	@Override
	public Map<String, Object> queryAllPageList(ReserveMain reserve){
		User user = ThreadVarUtil.getUser();
		reserve.setZdrid(user.getId());
		String id = reserve.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		reserve.setId(null);
		reserve.setDjlx(FormTypeEnum.RESERVE_FORM.getId());
		PageHelper.startPage(reserve.getPagination());
		List<ReserveMain> list = reserveMainMapper.queryAllPageList(reserve);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ReserveMain newRecord = new ReserveMain();
					newRecord.setId(id);
					List<ReserveMain> newRecordList = reserveMainMapper.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, reserve.getPagination());
		}else{
			List<ReserveMain> newRecordList = new ArrayList<ReserveMain>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ReserveMain newRecord = new ReserveMain();
				newRecord.setId(id);
				newRecordList = reserveMainMapper.queryAllPageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, reserve.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, reserve.getPagination());
		}
	}

	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材提订单信息
	 * @param reserve
	 * @return Map<String, Object>
	 * @develop date 2016.10.14
	 */
	public Map<String, Object> queryApprovePageList(ReserveMain reserve){
		User user = ThreadVarUtil.getUser();
		reserve.setZdrid(user.getId());
		String id = reserve.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		reserve.setId(null);
		reserve.setDjlx(FormTypeEnum.RESERVE_FORM.getId());
		PageHelper.startPage(reserve.getPagination());
		List<ReserveMain> list = reserveMainMapper.queryApprovePageList(reserve);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ReserveMain newRecord = new ReserveMain();
					newRecord.setId(id);
					List<ReserveMain> newRecordList = reserveMainMapper.queryApprovePageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, reserve.getPagination());
		}else{
			List<ReserveMain> newRecordList = new ArrayList<ReserveMain>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ReserveMain newRecord = new ReserveMain();
				newRecord.setId(id);
				newRecordList = reserveMainMapper.queryApprovePageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, reserve.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, reserve.getPagination());
		}
	}
	
	/**
	 * @author liub
	 * @description  根据提订单id查询提订详情信息
	 * @param mainid
	 * @return List<ReserveDetail>
	 * @develop date 2016.10.13
	 */
	@Override
	public List<ReserveDetail> queryReserveDetailListByMainId(String mainid){
		List<ReserveDetail> resultList = reserveDetailMapper.queryReserveDetailListByMainId(mainid);
		setMaterialModel(resultList);
		return resultList;
	}
	
	/**
	 * @author liub
	 * @description  根据提订单id集合查询提订详情信息
	 * @param mainidList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.12
	 */
	@Override
	public List<Map<String, Object>> queryReserveDetailListByIds(List<String> mainidList){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		if(null != mainidList && 0 < mainidList.size()){
			resultList = formatContractMap(reserveDetailMapper.queryReserveDetailListByIds(mainidList));
			setMaterialModelByListMap(resultList);//设置机型
			setMaterialProgress(resultList);//设置航材进度
		}
		return resultList;
	}
	
	/**
	 * @author liub
	 * @description 根据主键id查询提订单信息
	 * @param id
	 * @return ReserveMain
	 * @develop date 2016.10.13
	 */
	@Override
	public ReserveMain selectByPrimaryKey(String id){
		return reserveMainMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @author liub
	 * @description 根据主键id查询提订单信息(包含申请人、审核人)
	 * @param id
	 * @return ReserveMain
	 * @develop date 2016.12.19
	 */
	@Override
	public ReserveMain selectById(String id){
		return reserveMainMapper.selectById(id);
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材提订详情信息(弹窗)
	 * @param reserveDetail
	 * @return Map<String, Object>
	 * @develop date 2016.11.07
	 */
	@Override
	public List<Map<String, Object>> queryReserveDetailPageList(ReserveDetail reserveDetail){
		return reserveDetailMapper.queryReserveDetailPageList(reserveDetail);
	}
	
	/**
	 * @author liub
	 * @description  根据mainid查询相关工单信息
	 * @param mainid
	 * @return List<ReserveWorkOrder>
	 * @develop date 2016.11.18
	 */
	@Override
	public List<ReserveWorkOrder> queryWorkOrderListByMainId(String mainid){
		return reserveWorkOrderMapper.queryWorkOrderListByMainId(mainid);
	}
	
	/**
	 * @author liub
	 * @description 设置获取航材适用机型,并赋值
	 * @param reserveDetailList
	 * @develop date 2016.10.13
	 */
	private void setMaterialModel(List<ReserveDetail> reserveDetailList){
		List<String> mainIdList = new ArrayList<String>();
		for (ReserveDetail rd : reserveDetailList) {
			if(null == rd.getHcMainData().getXh() || "".equals(rd.getHcMainData().getXh()) || "-".equals(rd.getHcMainData().getXh())){
				mainIdList.add(rd.getHcMainData().getId());
			}
		}
		if(0 != mainIdList.size()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mainIdList", mainIdList);
			List<MaterialToPlaneModel> mToPlaneModelList = materialToPlaneModelMapper.queryByMainIdList(map);
			for (ReserveDetail rd : reserveDetailList) {
				if(null == rd.getHcMainData().getXh() || "".equals(rd.getHcMainData().getXh()) || "-".equals(rd.getHcMainData().getXh())){
					StringBuffer xh = new StringBuffer();
					for (MaterialToPlaneModel materialToPlaneModel : mToPlaneModelList) {
						if(rd.getHcMainData().getId().equals(materialToPlaneModel.getMainid())){
							xh.append(materialToPlaneModel.getFjjx()).append(",");
						}
					}
					if(xh.length() > 0){
						xh.deleteCharAt(xh.length() - 1);
					}
					rd.getHcMainData().setXh(xh.toString());
				}
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 设置合同信息
	 * @param reserveDetailList
	 */
	private List<Map<String, Object>> formatContractMap(List<Map<String, Object>> reserveDetailList){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();//页面结果集
		Map<Object, Map<String, Object>> tempMap = new HashMap<Object, Map<String, Object>>();//临时map集合key:id,value:map
		for(Map<String, Object> reserveDetailMap : reserveDetailList){
			//将合同id、合同流水号放入集合
			Map<String, Object> contractMap = new HashMap<String, Object>();
			contractMap.put("HTID", reserveDetailMap.get("HTID"));
			contractMap.put("HTLSH", reserveDetailMap.get("HTLSH"));
			Map<String, Object> resultMap = tempMap.get(reserveDetailMap.get("ID"));//从临时map集合中取出
			//如果临时map集合中没有存放则存放
			if(null == resultMap){
				tempMap.put(reserveDetailMap.get("ID"), reserveDetailMap);//id对应的数据存放临时map集合
				List<Map<String, Object>> contractMapList = new ArrayList<Map<String,Object>>();
				contractMapList.add(contractMap);
				reserveDetailMap.put("contractList", contractMapList);
				resultList.add(reserveDetailMap);
			}else{
				resultMap.put("HTSL", formatBigDecimal((BigDecimal) resultMap.get("HTSL")).add(formatBigDecimal((BigDecimal) reserveDetailMap.get("HTSL"))));
				resultMap.put("DHSL", formatBigDecimal((BigDecimal) resultMap.get("DHSL")).add(formatBigDecimal((BigDecimal) reserveDetailMap.get("DHSL"))));
				resultMap.put("RKSL", formatBigDecimal((BigDecimal) resultMap.get("RKSL")).add(formatBigDecimal((BigDecimal) reserveDetailMap.get("RKSL"))));
				List<Map<String, Object>> contractMapList = (List<Map<String, Object>>) resultMap.get("contractList");
				contractMapList.add(contractMap);
			}
		}
		return resultList;
	}
	
	/**
	 * @author liub
	 * @description 设置获取航材适用机型,并赋值
	 * @param reserveDetailList
	 * @develop date 2016.10.14
	 */
	private void setMaterialModelByListMap(List<Map<String, Object>> reserveDetailList){
		List<String> mainIdList = new ArrayList<String>();
		for(Map<String, Object> map : reserveDetailList){
			if(null == map.get("XH") || "".equals((String)map.get("XH")) || "-".equals((String)map.get("XH"))){
				mainIdList.add((String)map.get("BJID"));
			}
		}
		if(0 != mainIdList.size()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mainIdList", mainIdList);
			List<MaterialToPlaneModel> mToPlaneModelList = materialToPlaneModelMapper.queryByMainIdList(map);
			
			for (Map<String, Object> reserveDetailMap : reserveDetailList) {
				if(null == reserveDetailMap.get("XH") || "".equals((String)reserveDetailMap.get("XH")) || "-".equals((String)reserveDetailMap.get("XH"))){
					StringBuffer xh = new StringBuffer();
					for (MaterialToPlaneModel materialToPlaneModel : mToPlaneModelList) {
						if(null != reserveDetailMap.get("BJID") && ((String)reserveDetailMap.get("BJID")).equals(materialToPlaneModel.getMainid())){
							xh.append(materialToPlaneModel.getFjjx()).append(",");
						}
					}
					if(xh.length() > 0){
						xh.deleteCharAt(xh.length() - 1);
					}
					reserveDetailMap.put("XH", xh.toString());
				}
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 设置航材提订进度
	 * @param reserveDetailList
	 * @develop date 2016.10.17
	 */
	private void setMaterialProgress(List<Map<String, Object>> reserveDetailList){
		for(Map<String, Object> map : reserveDetailList){
			String jdzt = null;
			Integer ttzt = ((BigDecimal) map.get("RESERVEZT")).intValue();
			Integer xjzt = ((BigDecimal) map.get("XJZT")).intValue();
			if(ttzt.intValue() == ReserveStatusEnum.SAVE.getId().intValue()){
				jdzt = MaterialProgressEnum.SAVE.getName();
			}else if(ttzt.intValue() == ReserveStatusEnum.SUBMIT.getId().intValue()){
				jdzt = MaterialProgressEnum.SUBMIT.getName();
				if((null != map.get("SHSL") && ((BigDecimal) map.get("SHSL")).intValue() > 0) || xjzt.intValue() != EnquiryStatusEnum.NO_ENQUIRY.getId().intValue()){
					jdzt = MaterialProgressEnum.AUDITING.getName();
				}
			}else if(ttzt.intValue()== ReserveStatusEnum.AUDIT_PASS.getId().intValue() || ttzt.intValue() == ReserveStatusEnum.AUDIT_NOT_PASS.getId().intValue()){
				jdzt = MaterialProgressEnum.AUDITED.getName();
			}
			if(null != map.get("HTH")){
				jdzt = MaterialProgressEnum.CONTRACTED.getName();
			}
			if(null != map.get("DHSL") && ((BigDecimal) map.get("DHSL")).intValue() > 0 && null != map.get("DHSL") && (null == map.get("RKSL") || ((BigDecimal) map.get("RKSL")).intValue() == 0)){
				jdzt = MaterialProgressEnum.ARRIVED_NO_STORAGE.getName();
			}
			if(null != map.get("RKSL") && ((BigDecimal) map.get("RKSL")).intValue() > 0 && null != map.get("HTSL") && ((BigDecimal) map.get("HTSL")).compareTo(((BigDecimal) map.get("RKSL"))) == 1 ){
				jdzt = MaterialProgressEnum.PART_STORAGED.getName();
			}
			if(null != map.get("RKSL") && null != map.get("HTSL") && ((BigDecimal) map.get("HTSL")).compareTo(((BigDecimal) map.get("RKSL"))) == 0){
				jdzt = MaterialProgressEnum.ALL_STORAGED.getName();
			}
			if(ttzt.intValue() == ReserveStatusEnum.CLOSE.getId().intValue()){
				jdzt = MaterialProgressEnum.STOP.getName();
			}
			map.put("JDZT",jdzt);
		}
	}
	
	/**
	 * @author liub
	 * @description 如果是空返回0
	 * @param v
	 */
	private BigDecimal formatBigDecimal(BigDecimal v){
		return v == null?BigDecimal.ZERO:v;
	}
	
	/**
	 * @author liub
	 * @description 验证是否存在
	 * @param i,j,message
	 * @develop date 2017.04.15
	 */
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}

}
