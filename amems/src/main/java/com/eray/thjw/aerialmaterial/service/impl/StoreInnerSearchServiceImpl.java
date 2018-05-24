package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StoreInnerSearchMapper;
import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolBorrowRecord;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.StoreInnerSearchServcie;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.FrozenHistory;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.ThreadVar;
import enu.ThresholdEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockHistoryOperationEnum;

@Service
public class StoreInnerSearchServiceImpl implements StoreInnerSearchServcie{

@Resource
private StoreInnerSearchMapper storeInnerSearchMapper;

@Resource
private MaterialHistoryMapper materialHistoryMapper;

@Resource
private CommonRecService commonRecService;

@Resource
private ComponentCertificateService componentCertificateService;

@Resource
private ComponentMapper componentMapper;

@Resource
private MonitorsettingsService monitorsettingsService;

@Resource
private MaterialBatchInfoService MaterialBatchInfoService;

	@Override
	public Map<String, Object> queryInnerStorePageList(Stock stock)
			throws BusinessException {
		User user=ThreadVarUtil.getUser();
		PageHelper.startPage(stock.getPagination());
		List<Stock> list=storeInnerSearchMapper.queryAll(stock);
		Map<String,Object> map=PageUtil.pack4PageHelper(list, stock.getPagination());
		//获取货架寿命监控值
		Monitorsettings monitorsettingsHj =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.HCSM.getName(), user.getJgdm());
		//获取工具校验监控值
		Monitorsettings monitorsettingsTool =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GJJK.getName(), user.getJgdm());
		map.put("monitorsettingsHj", monitorsettingsHj);	
		map.put("monitorsettingsTool", monitorsettingsTool);
		return map;
	}

	@Override
	public List<StockHistory> queryStoreHistoryByKcid(String id)
			throws BusinessException {
		
		return storeInnerSearchMapper.querByKcid(id);
	}

	@Override
	public int save(Stock stock) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		StockHistory  history=new StockHistory();
		
       Stock st=queryKcByKcid(stock.getId());
       if(st==null){
    	   throw new BusinessException("该数据已经被更改,请刷新后再操作");
       }	
		//修改 b_h_001  库存信息
		stock.setWhrid(user.getId());
		storeInnerSearchMapper.updateStoreById(stock);
		commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE,"");
		
		//修改物料批次信息
		MaterialBatchInfoService.insertOrUpdate(stock.getDprtcode(), stock.getBjh(), stock.getSn(), stock.getPch(), stock.getKccb(), stock.getBiz(), stock.getJz(), stock.getJzbz());
			
		//保存证书
	    List<ComponentCertificate> certificates= stock.getComponentCertificateList();   
		InstallationListEditable record=new InstallationListEditable();
		record.setBjh(stock.getBjh());
		record.setXlh(stock.getSn());
		record.setPch(stock.getPch());
		record.setDprtcode(stock.getDprtcode());
		record.setCertificates(stock.getComponentCertificateList());
		record.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		componentCertificateService.save(record);
		
		//根据库存id=b_h2_025.装机清单id来增量处理
		storeInnerSearchMapper.batchInsertOrUpdateBj(stock);
		
		//刷新b_h_010 部件表数据
		InstallationListEditable installationListEditable=new InstallationListEditable();
		installationListEditable.setDprtcode(stock.getDprtcode());
		installationListEditable.setBjh(stock.getBjh());
		installationListEditable.setXlh(stock.getSn());
		installationListEditable.setLlklx(stock.getLlklx());
		installationListEditable.setLlkbh(stock.getLlkbh());
		installationListEditable.setBjgzjl(stock.getBjgzjl());
		installationListEditable.setChucrq(stock.getScrq());
		installationListEditable.setTsn(stock.getTsn());
		installationListEditable.setTso(stock.getTso());
		installationListEditable.setCsn(StringUtils.isEmpty(stock.getCsn())?null:Integer.valueOf(stock.getCsn()));
		installationListEditable.setCso(StringUtils.isEmpty(stock.getCso())?null:Integer.valueOf(stock.getCso()));
		installationListEditable.setCcsj(new Date());
		int updateCount = componentMapper.updateCompnent2Newest(installationListEditable);
		if(updateCount == 0){
			this.componentMapper.insertByInstallList(installationListEditable);
		}
		//写入b_h_017  部件库存履历
		String uuid=UUID.randomUUID().toString();
		MaterialHistory materialHistory=stock.toMaterialHistory();
		materialHistory.setId(uuid);
		materialHistoryMapper.insert(materialHistory);	
		//写入b_h_025  //库存履历
		history.setId(uuid);
		history.setDprtcode(stock.getDprtcode());
		history.setCzrbmid(user.getBmdm());
		history.setCzrid(user.getId());
		history.setCzr(user.getRealname());
		history.setCzzlx(null);
		history.setCzlx(StockHistoryOperationEnum.kcxg.getId());
		history.setCzsm(StockHistoryOperationEnum.kcxg.getName());
		history.setKcid(stock.getId());
		storeInnerSearchMapper.saveStoreHistory(history);
		
		return 0;
	}

	  @Override
		public List<ToolBorrowRecord> getListBykcid(String kcid)
				throws BusinessException {
			// TODO Auto-generated method stub
			return storeInnerSearchMapper.getListBykcid(kcid);
		}

	@Override
	public Stock queryKcByKcid(String id) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		return storeInnerSearchMapper.queryKcByKcid(id,user.getId(),user.getJgdm());
	}

	@Override
	public Map<String, Object> queryFrozenHistoryBykcid(FrozenHistory frozenHistory)
			throws BusinessException {
		PageHelper.startPage(frozenHistory.getPagination());
		List<FrozenHistory> list=storeInnerSearchMapper.queryFrozenHistoryBykcid(frozenHistory.getId());
		return PageUtil.pack4PageHelper(list, frozenHistory.getPagination());
	}
	
	@Override
	public Map<String, Object> getListAllBykcid(ToolBorrowRecord toolBorrowRecord)
				throws BusinessException {
		PageHelper.startPage(toolBorrowRecord.getPagination());
		List<ToolBorrowRecord> list=storeInnerSearchMapper.getListAllBykcid(toolBorrowRecord.getKcid());
			// TODO Auto-generated method stub
			return PageUtil.pack4PageHelper(list, toolBorrowRecord.getPagination());
		}
	/**
	 * 
	 * @Description 根据仓库id更新uuid短码
	 * @CreateTime 2018年3月16日 下午3:05:15
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	@Override
	public void updateUuiddm4Print(String id) {
		
		storeInnerSearchMapper.updateUuiddm4Print(id);
	}
	
	/**
	 * 
	 * @Description 根据id获取数据用于打印二维码
	 * @CreateTime 2018年3月16日 下午3:35:27
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> getData4Print(String id) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("DATATYPE", "2-kc");
		resultMap.put("ID", id);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list.add(storeInnerSearchMapper.getData4Print(id));
		resultMap.put("DATALIST", list);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryNoMaintenanceData(Stock stock)
			throws BusinessException {
		User user=ThreadVarUtil.getUser();
		stock.setDprtcode(user.getJgdm());
		PageHelper.startPage(stock.getPagination());
		List<Stock> list=storeInnerSearchMapper.queryNoMaintenanceData(stock);
		Map<String,Object> map=PageUtil.pack4PageHelper(list, stock.getPagination());	
		    return map;
	}

}
