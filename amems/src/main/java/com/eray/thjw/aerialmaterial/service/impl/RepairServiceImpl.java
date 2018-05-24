package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.MaterialRepairMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveMainMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveWorkOrderMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.aerialmaterial.po.ReserveWorkOrder;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.RepairService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.EnquiryStatusEnum;
import enu.FormTypeEnum;
import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 航材提订管理service,用于业务逻辑处理
 * @develop date 2016.10.12
 */
@Service
public class RepairServiceImpl implements RepairService {
	
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
	 * @description 航材送修Mapper
	 * @develop date 2016.11.01
	 */
    @Resource
    private MaterialRepairMapper materialRepairMapper;
    
    /**
  	 * @author liub
  	 * @description 提订单/送修单-相关工单Mapper
  	 * @develop date 2016.09.19
  	 */
    @Resource
    private ReserveWorkOrderMapper reserveWorkOrderMapper;
    
    /**
	 * @author liub
	 * @description 库存Mapper
	 * @develop date 2016.11.01
	 */
    @Resource
	private StockMapper stockMapper;
    
    /**
	 * @author liub
	 * @description 部件履历service
	 * @develop date 2016.11.01
	 */
	@Autowired
	private MaterialHistoryService materialHistoryService;
	
	 /**
  	 * @author liub
  	 * @description 附件service
  	 * @develop date 2016.11.06
  	 */
  @Resource
  private AttachmentService attachmentService;
	
	/**
	 * @author liub
	 * @description 新增送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	@Override
	public String add(ReserveMain reserve) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String sqdh;	
		try {
			sqdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCSX.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		reserve.setId(id);
		reserve.setSqdh(sqdh);
		reserve.setDjlx(FormTypeEnum.REPAIR_FORM.getId());
		reserve.setDprtcode(user.getJgdm());
		reserve.setSqrid(user.getId());
		reserve.setSqbmid(user.getBmdm());
		reserveMainMapper.insertSelective(reserve);
		//新增部件履历
		Stock stock = stockMapper.queryById(reserve.getKcid());
		MaterialHistory materialHistory = new MaterialHistory(stock);
		UUID materialHistoryUuid = UUID.randomUUID();//获取随机的uuid
		String materialHistoryId = materialHistoryUuid.toString();
		materialHistory.setId(materialHistoryId);
		materialHistoryService.insert(materialHistory);
		//更改库存表状态为冻结
		stockMapper.updateLocked(reserve.getKcid(), StockStatusEnum.LOCKED.getId().toString());
		//增加送修航材
		MaterialRepair materialRepair = new MaterialRepair();
		UUID materialRepairUuid = UUID.randomUUID();//获取随机的uuid
		String materialRepairId = materialRepairUuid.toString();
		materialRepair.setId(materialRepairId);
		materialRepair.setMainid(id);
		materialRepair.setKcllid(materialHistoryId);
		materialRepair.setZt(EffectiveEnum.YES.getId());
		materialRepair.setXjzt(EnquiryStatusEnum.NO_ENQUIRY.getId());
		materialRepair.setWhrid(user.getId());
		materialRepair.setWhdwid(user.getBmdm());
		materialRepairMapper.insertSelective(materialRepair);
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
		return materialRepairId;
	}
	
	/**
	 * @author liub
	 * @description 编辑送修单
	 * @param reserve
	 * @develop date 2016.11.01
	 * @throws BusinessException 
	 */
	@Override
	public void edit(ReserveMain reserve) throws BusinessException{
		
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(reserve.getId());
		verification(new Integer[]{ReserveStatusEnum.SAVE.getId()},obj.getZt(),"该送修单已更新，请刷新后再进行操作!");
		
		String kcid = reserve.getKcid();
		String kcidOld = reserve.getKcidOld();
		String kcllid = reserve.getKcllid();
		//编辑送修单
		User user = ThreadVarUtil.getUser();
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
		//判断是否更换送修航材
		if(null != kcidOld && !kcidOld.equals(kcid)){
			//更改之前的库存表状态为正常
			stockMapper.updateLocked(kcidOld, StockStatusEnum.NORMAL.getId().toString());
			//更改当前的库存表状态为冻结
			stockMapper.updateLocked(kcid, StockStatusEnum.LOCKED.getId().toString());
			materialHistoryService.updateByStock(kcllid, kcid);
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
	 * @description 编辑送修详情
	 * @param materialRepair
	 * @develop date 2016.12.16
	 * @throws BusinessException 
	 */
	@Override
	public void updateMaterialRepair(MaterialRepair materialRepair) throws BusinessException{
		MaterialRepair obj = materialRepairMapper.selectByPrimaryKey(materialRepair.getId());
		verification(new Integer[]{EnquiryStatusEnum.NO_ENQUIRY.getId()},obj.getXjzt(),"该送修单已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		materialRepair.setWhrid(user.getId());
		materialRepair.setWhdwid(user.getBmdm());
		materialRepairMapper.updateByPrimaryKeySelective(materialRepair);
	}
	
	/**
	 * @author liub
	 * @description 审核送修单
	 * @param reserve
	 * @develop date 2016.11.02
	 * @throws BusinessException 
	 */
	@Override
	public void editAudit(ReserveMain reserve) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(reserve.getId());
		verification(new Integer[]{ReserveStatusEnum.SUBMIT.getId()},obj.getZt(),"该送修单已更新，请刷新后再进行操作!");
		//编辑送修单
		User user = ThreadVarUtil.getUser();
		reserve.setSprid(user.getId());
		reserve.setSpbmid(user.getBmdm());
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
		attachmentService.eidtAttachment(reserve.getAttachments(), reserve.getId(),obj.getDprtcode());//编辑附件
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
	 * @description  作废
	 * @param id
	 * @return
	 * @develop date 2016.11.03
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(id);
		verification(new Integer[]{ReserveStatusEnum.SAVE.getId()},obj.getZt(),"该送修单已更新，请刷新后再进行操作!");
		ReserveMain reserve = new ReserveMain();
		reserve.setId(id);
		reserve.setZt(ReserveStatusEnum.CANCEL.getId());
		updateByPrimaryKeySelective(reserve);
		//作废送修航材
		MaterialRepair materialRepair = new MaterialRepair();
		materialRepair.setMainid(id);
		materialRepair.setZt(EffectiveEnum.NO.getId());
		materialRepairMapper.updateByMainid(materialRepair);
		Map<String, Object> map = reserveMainMapper.selectMapById(id);
		String kcid = (String) map.get("KCID");
		//更改库存表状态为正常
		stockMapper.updateLocked(kcid, StockStatusEnum.NORMAL.getId().toString());
	}
	
	/**
	 * @author liub
	 * @description 指定结束送修单信息
	 * @param reserve
	 * @return
	 * @develop date 2016.11.03
	 */
	@Override
	public void updateShutDown(ReserveMain reserve) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(reserve.getId());
		verification(new Integer[]{ReserveStatusEnum.SUBMIT.getId(),ReserveStatusEnum.AUDIT_PASS.getId()},obj.getZt(),"该送修单已更新，请刷新后再进行操作!");
		reserve.setZt(ReserveStatusEnum.CLOSE.getId());
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
		//作废送修航材
		MaterialRepair materialRepair = new MaterialRepair();
		materialRepair.setMainid(reserve.getId());
		materialRepair.setZt(EffectiveEnum.NO.getId());
		materialRepairMapper.updateByMainid(materialRepair);
		Map<String, Object> map = reserveMainMapper.selectMapById(reserve.getId());
		String kcid = (String) map.get("KCID");
		//更改库存表状态为正常
		stockMapper.updateLocked(kcid, StockStatusEnum.NORMAL.getId().toString());
	}
	
	/**
	 * @author liub
	 * @description 编辑送修单信息
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 */
	@Override
	public void updateByPrimaryKeySelective(ReserveMain reserve) throws BusinessException{
		reserveMainMapper.updateByPrimaryKeySelective(reserve);
	}
	
	/**
	 * @author liub
	 * @description 检查送修单编辑权限
	 * @param id
	 * @develop date 2016.11.01
	 */
	@Override
	public void checkEdit(String id) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(id);
		//验证是否保存状态
		Integer zt = obj.getZt();
		if(null == zt || ReserveStatusEnum.SAVE.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有保存状态下的送修单才能编辑!");
		}
	}
	
	/**
	 * @author liub
	 * @description 检查送修单审核权限
	 * @param id
	 * @develop date 2016.11.02
	 */
	@Override
	public void checkAudit(String id) throws BusinessException{
		ReserveMain obj = reserveMainMapper.selectByPrimaryKey(id);
		//验证是否保存状态
		Integer zt = obj.getZt();
		if(null == zt || ReserveStatusEnum.SUBMIT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有提交状态下的送修单才能审核!");
		}
	}
	
	/**
	 * @author liub
	 * @description  根据id查询送修单、送修航材信息
	 * @param id
	 * @return Map<String, Object>
	 * @develop date 2016.11.01
	 */
	@Override
	public Map<String, Object> selectMapById(String id){
		return reserveMainMapper.selectMapById(id);
	}
	
	/**
	 * @author liub
	 * @description 验证是否存在
	 * @param 
	 * @return
	 * @develop date 2017.04.15
	 */
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}
}
