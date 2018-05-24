package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialRequisitionDetailMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialRequisitionDetailWorkOrderMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialRequisitionMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.MaterialRequisition;
import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetail;
import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetailWorkOrder;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialRequisitionService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.SaiBongEnum;
import enu.aerialmaterial.RequisitionStatusEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;

/**
 * 
 * 航材领用业务实现层
 * @author xu.yong
 *
 */
@Service("materialRequisitionService")
public class MaterialRequisitionServiceImpl implements MaterialRequisitionService {

	@Resource
	private MaterialRequisitionMapper materialRequisitionMapper;
	
	@Resource
	private MaterialRequisitionDetailMapper materialRequisitionDetailMapper;
	
	@Resource
	private MaterialRequisitionDetailWorkOrderMapper materialRequisitionDetailWorkOrderMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	/**
	 * 查询用户保存状态的领用申请单 
	 * @return
	 */
	public MaterialRequisition query() {
		User user = ThreadVarUtil.getUser();
		
		//查询用户保存状态的申请单
		List<MaterialRequisition> materialRequisitionList= materialRequisitionMapper.selectUserSaved(user.getId());
		MaterialRequisition materialRequisition = new MaterialRequisition();
		//当存在用户保存状态的申请单时查询申请航材信息
		if(materialRequisitionList != null && materialRequisitionList.size() > 0){
			materialRequisition = materialRequisitionList.get(0);
			List<MaterialRequisitionDetail> detailList = this.materialRequisitionDetailMapper.selectByMainId(materialRequisition.getId());
			if(detailList != null && !detailList.isEmpty()){
				//根据领料申请单ID查询所有航材的工单
				List<MaterialRequisitionDetailWorkOrder> materialRequisitionDetailWorkOrder = this.materialRequisitionDetailWorkOrderMapper.selectByMainMainId(materialRequisition.getId());
				List<MaterialRequisitionDetailWorkOrder> workOrderList = null;//用于存储单条航材的工单
				
				for (MaterialRequisitionDetail detail : detailList) {
					workOrderList = new ArrayList<MaterialRequisitionDetailWorkOrder>();
					for (MaterialRequisitionDetailWorkOrder workOrder : materialRequisitionDetailWorkOrder) {
						if(workOrder.getMainid().equals(detail.getId())){
							workOrderList.add(workOrder);
						}
					}
					detail.setWorkOrderList(workOrderList);
				}
			}
			materialRequisition.setDetailList(detailList);
		}
		return materialRequisition;
	}

	/**
	 * 根据ID查询领用申请单
	 * @return
	 */
	public MaterialRequisition queryById(String id) {
		MaterialRequisition materialRequisition = materialRequisitionMapper.selectByPrimaryKey(id);
		//当存在用户保存状态的申请单时查询申请航材信息
		if(materialRequisition != null){
			
			List<MaterialRequisitionDetail> detailList = this.materialRequisitionDetailMapper.selectByMainId(materialRequisition.getId());
			if(detailList != null && !detailList.isEmpty()){
				//根据领料申请单ID查询所有航材的工单
				List<MaterialRequisitionDetailWorkOrder> materialRequisitionDetailWorkOrder = this.materialRequisitionDetailWorkOrderMapper.selectByMainMainId(id);
				List<MaterialRequisitionDetailWorkOrder> workOrderList = null;//用于存储单条航材的工单
				
				for (MaterialRequisitionDetail detail : detailList) {
					workOrderList = new ArrayList<MaterialRequisitionDetailWorkOrder>();
					for (MaterialRequisitionDetailWorkOrder workOrder : materialRequisitionDetailWorkOrder) {
						if(workOrder.getMainid().equals(detail.getId())){
							workOrderList.add(workOrder);
							//materialRequisitionDetailWorkOrder.remove(workOrder);
						}
					}
					detail.setWorkOrderList(workOrderList);
				}
			}
			materialRequisition.setDetailList(detailList);
		}
		return materialRequisition;
	}
	
	/**
	 * 判断用户是否有保存状态的领用申请单
	 * @return
	 */
	public boolean hasSaved(){
		User user = ThreadVarUtil.getUser();
		//查询本人制单的保存状态领用单
		List<MaterialRequisition> materialRequisition = this.materialRequisitionMapper.selectUserSaved(user.getId());
		if(materialRequisition == null){
			return false;
		}
		return true;
	}
	
	
	/**
	 * 添加领用航材
	 * @param kcid 界面领用的航材库存ID
	 */
	public void addMaterial(String kcid){
		//查询领用的库存
		Stock stock = this.stockMapper.queryById(kcid);
		if(stock == null || !StockStatusEnum.NORMAL.getId().equals(stock.getZt())){
			throw new RuntimeException("库存不存在");
		}
		
		User user = ThreadVarUtil.getUser();
		//查询本人制单的保存状态领用单
		List<MaterialRequisition> materialRequisitionList = this.materialRequisitionMapper.selectUserSaved(user.getId());
		MaterialRequisition materialRequisition;
		//当不存在本人制单的保存状态领用单时：新增领用申请单
		if(materialRequisitionList == null || materialRequisitionList.size() < 1 ){
			materialRequisition = new MaterialRequisition();
			materialRequisition.setId(UUID.randomUUID().toString());
			materialRequisition.setDprtcode(user.getJgdm());
			try {
				materialRequisition.setLysqdh(saibongUtilService.generate(user.getJgdm(),SaiBongEnum.LYSQ.getName()));
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			materialRequisition.setSqbmid(user.getBmdm());
			materialRequisition.setSqrid(user.getId());
			materialRequisition.setCkid(stock.getCkid());
			materialRequisition.setCkh(stock.getCkh());
			materialRequisition.setCkmc(stock.getCkmc());
			materialRequisition.setZt(RequisitionStatusEnum.SAVED.getId());
			materialRequisition.setZdbmid(user.getBmdm());
			materialRequisition.setZdrid(user.getId());
			this.materialRequisitionMapper.insert(materialRequisition);
		}else{
			materialRequisition = materialRequisitionList.get(0);
		}
		//验证该库存是否已添加到领用申请单
		int count = this.materialRequisitionDetailMapper.selectByMainIdKcIdCount(materialRequisition.getId(), stock.getId());
		//该库存未被领用过时添加领用航材
		if(count == 0){
			
			//FIXME 从表的KCID字段 由 库存表ID 更改为 履历表ID，领用时增加履历数据
			MaterialHistory history = stock.toMaterialHistory();
			history.setId(UUID.randomUUID().toString());
			this.materialHistoryMapper.insertSelective(history);
			
			MaterialRequisitionDetail detail = new MaterialRequisitionDetail();
			detail.setId(UUID.randomUUID().toString());
			detail.setMainid(materialRequisition.getId());
			detail.setKcid(history.getId());
			detail.setBjid(stock.getBjid());
			detail.setBjh(stock.getBjh());
			detail.setZt(DelStatus.TAKE_EFFECT.getId());
			detail.setWhrid(user.getId());
			detail.setWhdwid(user.getJgdm());
			BigDecimal bg0 = new BigDecimal(0);
			detail.setYlysl(bg0);
			detail.setTksl(bg0);
			this.materialRequisitionDetailMapper.insert(detail);
		}
	}
	
	/**
	 * 修改领用申请单
	 * @param materialRequisition 领用申请单信息
	 * @param modifyList 修改清单
	 * @param idList 删除清单
	 */
	public void save(MaterialRequisition materialRequisition, List<Map> modifyList, List<String> idList){
		
		if(materialRequisition == null || StringUtils.isBlank(materialRequisition.getId())){
			throw new RuntimeException("参数传递错误");
		}
		
		//检验业务状态
		MaterialRequisition materialRequisition1 = this.materialRequisitionMapper.selectByPrimaryKey(materialRequisition.getId());
		if(materialRequisition1.getZt().intValue() != RequisitionStatusEnum.SAVED.getId().intValue()){
			throw new RuntimeException("当前领用申请单状态为"+RequisitionStatusEnum.getName(materialRequisition1.getZt())+"，不可进行该操作");
		}
		
		//删除领用清单
		if(null != idList && !idList.isEmpty()){
			this.materialRequisitionDetailMapper.deleteByIds(idList);
			//删除领用清单的关联工单
			this.materialRequisitionDetailWorkOrderMapper.deleteByMainIds(idList);
		}
		
		User user = ThreadVarUtil.getUser();
		
		//修改领用清单
		if(modifyList != null && !modifyList.isEmpty()){
			for (Map map : modifyList) {
				//修改申请领用数量
				if(map.get("sqlysl") != null){
					BigDecimal sqlysl = null;
					sqlysl = new BigDecimal((String)map.get("sqlysl"));
					this.materialRequisitionDetailMapper.updateNumber((String)map.get("id"), sqlysl);
				}
				
				//修改相关工单
				List<Map<String, Object>> workOrderList = (List<Map<String, Object>>)map.get("workOrderList");
				if(workOrderList != null){
					List<String> ids = new ArrayList<String>();
					for (Map<String, Object> workOrderMap : workOrderList) {
						if(workOrderMap.get("id") != null){
							ids.add((String)workOrderMap.get("id"));
						}
					}
					//删除不存在的工单
					if(ids.size() > 0){
						Map<String, Object> paramsMap = new HashMap<String, Object>();
						paramsMap.put("mainId", map.get("id"));
						paramsMap.put("list", ids);
						this.materialRequisitionDetailWorkOrderMapper.deleteByMainIdNotIds(paramsMap);
					}else{
						this.materialRequisitionDetailWorkOrderMapper.deleteByMainId((String)map.get("id"));
					}
					//增加新的工单
					for (Map<String, Object> workOrderMap : workOrderList) {
						if(workOrderMap.get("id") == null){
							workOrderMap.put("id", UUID.randomUUID().toString());
							workOrderMap.put("mainid", map.get("id"));
							workOrderMap.put("zt", EffectiveEnum.YES.getId());
							workOrderMap.put("whdwid", user.getJgdm());
							workOrderMap.put("whrid", user.getId());
							this.materialRequisitionDetailWorkOrderMapper.insertMapSelective(workOrderMap);
						}
					}
				}
			}
		}
		
		//修改领用申请单
		this.materialRequisitionMapper.updateByPrimaryKeySelective(materialRequisition);
		
	}
	
	/**
	 * 提交领用申请单
	 * @param id 领用申请单ID
	 */
	public void saveSubmit(String id){
		//验证领用清单的库存是否满足领用要求
		List<MaterialRequisitionDetail> list = this.materialRequisitionDetailMapper.selectByMainId(id);
		if(list == null){
			throw new RuntimeException("没有领用航材信息");
		}
		for (MaterialRequisitionDetail materialRequisitionDetail : list) {
			if(materialRequisitionDetail.getStock() != null && materialRequisitionDetail.getStock().getKcsl() != null){
				if(materialRequisitionDetail.getSqlysl() == null){
					throw new RuntimeException("航材领用数不正确");
				}
				if(materialRequisitionDetail.getStock().getKcsl().compareTo(materialRequisitionDetail.getSqlysl()) == -1){
					throw new RuntimeException("库存数量不足申请数量");
				}
			}else{
				throw new RuntimeException("库存中没有领用的航材");
			}
		}
		//更新状态
		MaterialRequisition materialRequisition = new MaterialRequisition();
		materialRequisition.setId(id);
		materialRequisition.setZt(RequisitionStatusEnum.SUBMITED.getId());
		this.materialRequisitionMapper.updateByPrimaryKeySelective(materialRequisition);
	}
	
	/**
	 * 更新
	 * @param id 
	 * @param zt
	 */
	public void updateState(String id, Integer zt){
		MaterialRequisition materialRequisition = this.materialRequisitionMapper.selectByPrimaryKey(id);
		if(materialRequisition != null){
			MaterialRequisition record = new MaterialRequisition();
			record.setId(materialRequisition.getId());
			if(RequisitionStatusEnum.INVALID.getId().intValue() == zt.intValue()){
				if(materialRequisition.getZt().intValue() != RequisitionStatusEnum.SAVED.getId().intValue()){
					throw new RuntimeException("当前领用申请单状态为"+RequisitionStatusEnum.getName(materialRequisition.getZt())+"，不可进行该操作");
				}
				record.setZt(RequisitionStatusEnum.INVALID.getId());
				this.materialRequisitionMapper.updateByPrimaryKeySelective(record);
			}else if(RequisitionStatusEnum.CLOSED.getId().intValue() == zt.intValue()){
				if(materialRequisition.getZt().intValue() != RequisitionStatusEnum.SUBMITED.getId().intValue()){
					throw new RuntimeException("领用申请单当前状态不能指定结束");
				}
				record.setZt(RequisitionStatusEnum.CLOSED.getId());
				User user = ThreadVarUtil.getUser();
				this.materialRequisitionMapper.updateByPrimaryKeySelective(record);
			}else{
				throw new RuntimeException("非法操作");
			}
		}
		
		
	}
	
	/**
	 * 分页查询领用申请单
	 * @param materialRequisition
	 * @return
	 */
	public List<MaterialRequisition> queryPage(MaterialRequisition materialRequisition){
		return this.materialRequisitionMapper.queryPage(materialRequisition);
	}
	

	@Override
	public List<MaterialRequisition> queryAllPageList(
			MaterialRequisition materialRequisition) throws RuntimeException {
		return materialRequisitionMapper.queryAllPageList(materialRequisition);
	}

	@Override
	public MaterialRequisition selectByPrimaryKey(String xgdjid) {
		
		return materialRequisitionMapper.selectByPrimaryKey(xgdjid);
	}

	@Override
	public void updateByPrimaryKeySelective(
			MaterialRequisition materialRequisition1) {
		materialRequisitionMapper.updateByPrimaryKeySelective(materialRequisition1);
		
	}

	
	public void saveClose(String id, String zdjsyy) {
		
		//检验业务状态
		MaterialRequisition materialRequisition1 = this.materialRequisitionMapper.selectByPrimaryKey(id);
		if(materialRequisition1.getZt().intValue() != RequisitionStatusEnum.SUBMITED.getId().intValue()){
			throw new RuntimeException("当前领用申请单状态为"+RequisitionStatusEnum.getName(materialRequisition1.getZt())+"，不可进行该操作");
		}
		
		MaterialRequisition materialRequisition = new MaterialRequisition();
		materialRequisition.setId(id);
		User user = ThreadVarUtil.getUser();
		materialRequisition.setZdjsrid(user.getId());
		materialRequisition.setZdjsyy(zdjsyy);
		materialRequisition.setZt(RequisitionStatusEnum.CLOSED.getId());
		materialRequisitionMapper.updateByPrimaryKeySelective(materialRequisition);
	}





}
