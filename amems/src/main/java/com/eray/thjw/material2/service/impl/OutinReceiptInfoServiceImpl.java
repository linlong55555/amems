package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.material2.dao.OutinReceiptInfoMapper;
import com.eray.thjw.material2.dao.OutinReceiptShelvesMapper;
import com.eray.thjw.material2.dao.ReceivingRelationshipMapper;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptInfo;
import com.eray.thjw.material2.po.OutinReceiptShelves;
import com.eray.thjw.material2.po.ReceivingRelationship;
import com.eray.thjw.material2.service.OutinReceiptInfoService;
import com.eray.thjw.material2.service.OutinReceiptShelvesService;
import com.eray.thjw.project2.service.TodoService;

import enu.SaiBongEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.material2.ReceiptSourceTypeEnum;
import enu.material2.ReceiptStatusEnum;
import enu.produce.NodeEnum;
import enu.project2.TodoEnum;

/**
 * @Description 航材收货单明细serivce
 * @CreateTime 2018年3月12日 上午9:51:26
 * @CreateBy 韩武
 */
@Service("outinReceiptInfoService")
public class OutinReceiptInfoServiceImpl implements OutinReceiptInfoService {
	
	@Resource
	private OutinReceiptInfoMapper outinReceiptInfoMapper;
	
	@Resource
	private StorageMapper storageMapper;
	
	@Resource
	private StoreMapper storeMapper;
	
	@Resource
	private OutinReceiptShelvesService outinReceiptShelvesService;
	
	@Resource
	private OutinReceiptShelvesMapper outinReceiptShelvesMapper;
	
	@Resource
	private ReceivingRelationshipMapper receivingRelationshipMapper;
	@Resource
	private TodoService todoService;

	/**
	 * @Description 保存航材收货单明细
	 * @CreateTime 2018年3月12日 上午9:49:32
	 * @CreateBy 韩武
	 * @param mainid
	 * @param details
	 * @throws SaibongException 
	 */
	@Override
	public void save(OutinReceipt record) throws SaibongException {
		if(record.getDetails() != null && !record.getDetails().isEmpty()){
			for (OutinReceiptInfo detail : record.getDetails()) {
				
				// 不需质检，则无需填写临时存放位置，直接入库
				if(WhetherEnum.NO.getId().equals(detail.getIsZj())) {	
					detail.setLsckid(null);
					detail.setLskwid(null);
					detail.setLscfwz(null);
				}
				
				// 状态 = 提交，且没有批次号，则采番生成批次号
				if(ReceiptStatusEnum.SUBMIT.getId().equals(record.getZt()) && StringUtils.isBlank(detail.getPch())){
					detail.setPch(SNGeneratorFactory.generate(record.getDprtcode(), SaiBongEnum.HC_PCH.getName(), record));
				}
				
				if (StringUtils.isBlank(detail.getId())){	// 新增收货单明细
					detail.setId(UUID.randomUUID().toString());
					detail.setMainid(record.getId());
					outinReceiptInfoMapper.insertSelective(detail);
				} else {	// 修改收货单明细
					outinReceiptInfoMapper.updateByOutinReceiptInfo(detail);
				}
				
				// 无收货单-上架信息，则生成一条默认的收货单-上架信息
				if(detail.getShelves() == null || detail.getShelves().isEmpty()){	
					
					List<OutinReceiptShelves> shelves = new ArrayList<OutinReceiptShelves>();
					OutinReceiptShelves shelf = new OutinReceiptShelves();
					
					// 填写了临时仓库、临时库位
					if(StringUtils.isNotBlank(detail.getLskwid())){
						Storage storage = storageMapper.selectByIdWithRel(detail.getLskwid());
						Store store = storage != null ? storage.getStore() : null;
						shelf.setCkid(store != null ? store.getId() : null);
						shelf.setCkh(store != null ? store.getCkh() : null);
						shelf.setCkmc(store != null ? store.getCkmc() : null);
						shelf.setCklb(store != null ? store.getCklb() : null);
						shelf.setKwid(storage.getId());
						shelf.setKwh(storage.getKwh());
					}
					// 只填写了临时仓库，无临时库位
					else if(StringUtils.isNotBlank(detail.getLsckid())){
						Store store = storeMapper.selectByPrimaryKey(detail.getLsckid());
						shelf.setCkid(store != null ? store.getId() : null);
						shelf.setCkh(store != null ? store.getCkh() : null);
						shelf.setCkmc(store != null ? store.getCkmc() : null);
						shelf.setCklb(store != null ? store.getCklb() : null);
					}
					// 默认收货仓库
					else{
						Store store = storeMapper.selectByPrimaryKey(record.getShck());
						shelf.setCkid(store != null ? store.getId() : null);
						shelf.setCkh(store != null ? store.getCkh() : null);
						shelf.setCkmc(store != null ? store.getCkmc() : null);
						shelf.setCklb(store != null ? store.getCklb() : null);
					}
					shelf.setMainid(detail.getId());
					shelf.setSjsl(detail.getSl());
					shelf.setJldw(detail.getDw());
					shelves.add(shelf);
					detail.setShelves(shelves);
				}
				
				// 保存收货单-上架信息
				saveShelves(detail.getId(), detail.getShelves());
				
				// 保存收货关系
				saveReceivingRelationship(detail, record);
				
				
				
			}
		}
	}

	/**
	 * @Description 根据收货单id删除收货单明细
	 * @CreateTime 2018年3月12日 上午9:50:15
	 * @CreateBy 韩武
	 * @param shdid
	 */
	@Override
	public void deleteNotExistByShdid(String shdid, List<OutinReceiptInfo> details) {
		// 删除收货单明细
		outinReceiptInfoMapper.deleteNotExistByShdid(shdid, details);
		// 删除收货单-上架信息
		outinReceiptShelvesMapper.deleteNotExistByShdid(shdid, details);
		// 删除收货关系
		receivingRelationshipMapper.deleteNotExistByShdid(shdid, details);
	}
	
	/**
	 * @Description 保存收货单-上架信息
	 * @CreateTime 2018年3月12日 上午11:11:16
	 * @CreateBy 韩武
	 * @param mainid
	 * @param shelves
	 */
	private void saveShelves(String mainid, List<OutinReceiptShelves> shelves){
		// 根据收货单明细id删除上架信息
		outinReceiptShelvesService.deleteNotExistByMainid(mainid, shelves);
		// 保存收货单-上架信息
		outinReceiptShelvesService.save(mainid, shelves);
	}
	
	/**
	 * @Description 保存收货关系
	 * @CreateTime 2018年3月12日 上午11:47:24
	 * @CreateBy 韩武
	 * @param detail
	 */
	private void saveReceivingRelationship(OutinReceiptInfo detail, OutinReceipt record){
		
		// 删除原有的收货关系
		receivingRelationshipMapper.deleteByShmxid(detail.getId());
		// 来源明细类型=采购/送修/租入/租出/交换/退料
		if(detail.getLymxlx() != null){
			ReceivingRelationship relat = new ReceivingRelationship();
			relat.setId(UUID.randomUUID().toString());
			relat.setDprtcode(record.getDprtcode());
			relat.setZt(EffectiveEnum.YES.getId());
			relat.setLylx(detail.getLymxlx());
			relat.setLyid(record.getLyid());
			relat.setLymxid(detail.getLymxid());
			relat.setShdid(record.getId());
			relat.setShmxid(detail.getId());
			relat.setShsl(detail.getSl());
			relat.setDw(detail.getDw());
			// 退料 来源id = 来源明细id
			if(ReceiptSourceTypeEnum.RETURN_MATERIAL.getId().equals(detail.getLymxlx())){
				relat.setLyid(detail.getLymxid());
			}
			// 保存收货关系
			receivingRelationshipMapper.insertSelective(relat);
		}
	}
}