package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.DestructionDetailMapper;
import com.eray.thjw.aerialmaterial.dao.DestructionMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.Destruction;
import com.eray.thjw.aerialmaterial.po.DestructionDetail;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.DestructionSerivce;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockRecBizTypeEnum;
@Service
public class DestructionSerivceImpl implements DestructionSerivce{

	@Autowired
	private DestructionMapper destructionMapper;
	@Autowired
	private StockSerivce stockSerivce;
	@Autowired
	private MaterialHistoryMapper materialHistoryMapper;
	@Autowired
	private OrderAttachmentService orderAttachmentService;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private MaterialRecService materialRecService;
	@Autowired
	private DestructionDetailMapper destructionDetailMapper;
	@Autowired
	private StockMapper stockMapper;
	@Autowired
	private SaibongUtilService saibongUtilService;
	@Autowired
	private AttachmentMapper attachmentMapper;
	/**
	 * @author sunji
	 * @description  根据条件分页销毁记录单
	 * @param stock
	 * @return Map<String, Object>
	 */
	public List<Destruction> queryDestructionList(Destruction record)
			throws BusinessException {
		return destructionMapper.queryDestructionList(record);
	}
	/**
	 * @author sunji
	 * @description  保存销毁记录单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String, Object> save(Destruction record)
			throws BusinessException {
		Map<String, Object> map= new HashMap<String, Object>();
		//获取登入user
		User user=ThreadVarUtil.getUser();
		//操作流水id
		String czls=UUID.randomUUID().toString();
		//添加主数据
		String destructionId=UUID.randomUUID().toString();
		String xhdh = "";
		try {
			xhdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.XHD.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		record.setId(destructionId);
		record.setZdrid(user.getId());
		record.setZdbmid(user.getBmdm());
		record.setDprtcode(user.getJgdm());
		record.setZdsj(new Date());
		record.setXhdh(xhdh);
		int i =destructionMapper.insertSelective(record);
		//commonRecService.write(destructionId, TableEnum.B_H_021, user.getId(), czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,destructionId);
		materialRecService.writeStockRec(destructionId, czls, destructionId, xhdh, StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.SAVE);
		//获取需要销毁的数据，并加入到b_h_007
		List<String> ids=record.getStockIds();
		List<Stock> stockList=stockSerivce.queryByIds(ids);
		if(stockList.size()>0){
			for (Stock stock : stockList) {
				stock.setKcid(stock.getId());
				String id=UUID.randomUUID().toString();
				stock.setId(id);
				materialHistoryMapper.insertBystock(stock);
				materialRecService.writeStockRec(stock.getId(), czls, destructionId, null, StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.SAVE);
				//commonRecService.write(stock.getId(), TableEnum.B_H_017, user.getId(), czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,destructionId);
				//添加主表明细
				DestructionDetail destructionDetail=new DestructionDetail();
				destructionDetail.setId(UUID.randomUUID().toString());
				destructionDetail.setMainid(destructionId);
				destructionDetail.setKcllid(id);
				destructionDetailMapper.insert(destructionDetail);
			}
		}
		//添加附件表
		List<OrderAttachment> orderAttachmentList=record.getOrderAttachmentList();
		for (OrderAttachment orderAttachment : orderAttachmentList) {
			String id = UUID.randomUUID().toString();//获取随机的uuid
			orderAttachment.setId(id);
			orderAttachment.setMainid(destructionId);
			orderAttachment.setCzbmid(user.getBmdm());
			orderAttachment.setCzsj(new Date());
			orderAttachment.setCzrid(user.getId());
			orderAttachment.setDprtcode(user.getJgdm());
			orderAttachment.setYxzt(1);
			orderAttachment.setHzm(orderAttachment.getHzm());
			orderAttachmentService.insertSelective(orderAttachment);
			//添加历史数据表
			 //commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,destructionId);
		}
		if(i>0){
			map.put("state", "success");
			map.put("message", "保存成功");
		}else{
			map.put("state", "error");
			map.put("message", "保存失败");
		}
		return map;
	}
	
	/**
	 * @author sunji
	 * @description  提交销毁记录单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String, Object> submit(Destruction record)
			throws BusinessException {
		Map<String, Object> map=new HashMap<String, Object>();
		this.save(record);
		List<String> ids=record.getStockIds();
		int i=0;
		if(ids.size()>0){
			i=stockMapper.deleteids(ids);
		}
		if(i>0){
			map.put("state", "success");
			map.put("message", "保存成功");
		}else{
			map.put("state", "error");
			map.put("message", "保存失败");
		}
		return map;
	}
	/**
	 * @author sunji
	 * @description  根据id查询单个
	 * @param Destructions
	 * @return Map<String, Object>
	 */
	public Destruction selectByPrimaryKey(String id) throws BusinessException {
		return destructionMapper.selectByPrimaryKey(id);
	}
	/**
	 * @author sunji
	 * @description  修改销毁记录单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String, Object> edit(Destruction record)
			throws BusinessException {
		Map<String, Object> map=new HashMap<String, Object>();
		//获取登入user
		User user=ThreadVarUtil.getUser();
		//操作流水
		String czls=UUID.randomUUID().toString();
		//修改主表数据
		int i=destructionMapper.updateByPrimaryKeySelective(record);
		//修改明细数据
		List<String> ids= record.getStockIds();
		if(ids.size()>0){
			//commonRecService.write("id", ids, TableEnum.B_H_017, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.DELETE,record.getId());
			materialHistoryMapper.deleteids(ids);

		}
		//修改上传附件数据
		//1.删除
		List<String> scwjIds=record.getDeleteScwjId();
		if(scwjIds.size()>0){
			attachmentMapper.delFileByids(scwjIds);
			commonRecService.write("id", scwjIds, TableEnum.D_011, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.DELETE,record.getId());
		}
		//2.添加
		List<OrderAttachment> OrderAttachmentList= record.getOrderAttachmentList();
		if(OrderAttachmentList.size()>0){
			for (OrderAttachment orderAttachment : OrderAttachmentList) {
				String id = UUID.randomUUID().toString();//获取随机的uuid
				orderAttachment.setId(id);
				orderAttachment.setMainid(record.getId());
				orderAttachment.setCzbmid(user.getBmdm());
				orderAttachment.setCzsj(new Date());
				orderAttachment.setCzrid(user.getId());
				orderAttachment.setDprtcode(record.getDprtcode());
				orderAttachment.setYxzt(1);
				orderAttachment.setHzm(orderAttachment.getHzm());
				orderAttachmentService.insertSelective(orderAttachment);
				//添加历史数据表
				 commonRecService.write(id, TableEnum.D_011, user.getId(), czls, orderAttachment.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,record.getId());
			}
		}
		if(i>0){
			map.put("state", "success");
			map.put("message", "保存成功");
		}else{
			map.put("state", "error");
			map.put("message", "保存失败");
		}
				
		return map;
	}
	/**
	 * @author sunji
	 * @description  修改销毁记录单(提交)
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String, Object> update(Destruction record)
			throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		//操作流水
		String czls=UUID.randomUUID().toString();
		Map<String, Object> map=new HashMap<String, Object>();
		record.setZt(2);
		map=this.edit(record);
		
		//根据id获取b_h_007数据id
		List<MaterialHistory> materialHistoryList= materialHistoryMapper.queryChoStock(record.getId());
		if(materialHistoryList.size()>0){
			List<String> ids=new ArrayList<String>();
			for (MaterialHistory materialHistory : materialHistoryList) {
				ids.add(materialHistory.getKcid());
			}
			int count=stockMapper.selectCount4exit(ids);
			if(count==0){
				throw new RuntimeException("该数据已提交!");
			}
			//插入日志表
			//commonRecService.write("id", ids, TableEnum.B_H_017, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.DELETE,record.getId());
				int i=stockMapper.deleteids(ids);
		}
		return map;
	}
	/**
	 * @author sunji
	 * @description  删除销毁单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String, Object> deleteDestruction(String id)
			throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		//操作流水
		String czls=UUID.randomUUID().toString();
		Map<String, Object> map=new HashMap<String, Object>();
		Destruction destruction=new Destruction();
		destruction.setId(id);
		destruction.setZt(8);
		int i = destructionMapper.updateByPrimaryKeySelective(destruction);
		//插入日志表
		//commonRecService.write(id, TableEnum.B_H_021, user.getId(), czls,LogOperationEnum.EDIT, UpdateTypeEnum.DELETE,id);
		materialRecService.writeStockRec(id, czls, id, destruction.getXhdh(), StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.DELETE);
		if(i>0){
			map.put("state", "success");
			map.put("message", "作废成功");
		}else{
			map.put("state", "error");
			map.put("message", "作废失败");
		}
		return map;
	}
	/**
	 * @author sunji
	 * @description  撤销销毁单
	 * @param Destruction
	 * @return Map<String, Object>
	 */
	public Map<String, Object> updateUndoDestruction(String id)
			throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		//操作流水
		String czls=UUID.randomUUID().toString();
		//修改主表数据
		Map<String, Object> map=new HashMap<String, Object>();
		Destruction destruction=new Destruction();
		destruction.setId(id);
		destruction.setZt(11);
		int i = destructionMapper.updateByPrimaryKeySelective(destruction);
		//插入日志表
		//commonRecService.write(id, TableEnum.B_H_021, user.getId(), czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,id);
		materialRecService.writeStockRec(id, czls, id, destruction.getXhdh(), StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.UPDATE);
		//根据id获取b_h_007数据id
		List<DestructionDetail> DestructionDetailList= destructionDetailMapper.selectByMainid(id);
		if(DestructionDetailList.size()>0){
			List<String> ids=new ArrayList<String>();
			for (DestructionDetail destructionDetail : DestructionDetailList) {
				ids.add(destructionDetail.getKcllid());
			}
			//1.根据ids添加数据
			List<MaterialHistory> materialHistoryList= materialHistoryMapper.queryByIds(ids);
			//materialHistoryMapper.deleteids(ids);
			for (MaterialHistory materialHistory : materialHistoryList) {
				//3.添加b_h_001
				materialHistory.setWhrid(user.getId());
				materialHistory.setWhsj(new Date());
				stockMapper.insertMater(materialHistory);
				//插入日志表
				//commonRecService.write(materialHistory.getId(), TableEnum.B_H_017, user.getId(), czls,LogOperationEnum.EDIT, UpdateTypeEnum.SAVE,id);
				materialRecService.writeStockRec(materialHistory.getId(), czls, id, null, StockRecBizTypeEnum.TYPE8, UpdateTypeEnum.SAVE);
			}
			
		}
		
		if(i>0){
			map.put("state", "success");
			map.put("message", "撤销成功");
		}else{
			map.put("state", "error");
			map.put("message", "撤销失败");
		}
		return map;
	}

}
