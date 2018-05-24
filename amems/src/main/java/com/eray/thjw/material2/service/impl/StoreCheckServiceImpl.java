package com.eray.thjw.material2.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.StockHistoryMapper;
import com.eray.thjw.material2.dao.StoreCheckMapper;
import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.material2.po.StoreCheck;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.material2.service.StoreCheckService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.MaterialTypeEnum;
import enu.SaiBongEnum;
import enu.SupervisoryLevelEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;
import enu.common.PartSnValidationEnum;
import enu.material2.StockCheckStatusEnum;
import enu.material2.StockHistorySubtypeEnum;
import enu.material2.StockHistoryTypeEnum;

/**
 * @Description 盘点记录service实现类
 * @CreateTime 2018-3-20 下午2:11:56
 * @CreateBy 刘兵
 */
@Service
public class StoreCheckServiceImpl implements StoreCheckService{
	
	@Resource
	private StoreCheckMapper storeCheckMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;
	
	@Resource
	private StockHistoryMapper stockHistoryMapper;

	@Resource
	private MaterialBatchInfoService materialBatchInfoService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private CommonRecService commonRecService;

	@Resource
	private ComponentCertificateService componentCertificateService;

	/**
	 * @Description 新增库存
	 * @CreateTime 2018-3-21 下午5:55:37
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return String 库存id
	 * @throws BusinessException
	 */
	@Override
	public String save(Stock stock) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		StockHistory  history=new StockHistory();
		//新增 b_h_001  库存信息
		stock.setId(UUID.randomUUID().toString());
		stock.setRkrid(user.getId());
		stock.setRkbmid(user.getBmdm());
		stock.setWhrid(user.getId());
		stock.setDprtcode(user.getJgdm());
		stock.setZt(StockStatusEnum.NORMAL.getId());
		
		//验证序列号重复性
		if(StringUtils.isNotBlank(stock.getGljb()) && SupervisoryLevelEnum.SERIES_NUMBER.getId().intValue() == Integer.parseInt(stock.getGljb())){
			if(StringUtils.isBlank(stock.getSn())){
				throw new BusinessException("序列号不能为空!");
			}
			stockSerivce.getCount4ValidationBjAndXlh(stock.getId(), stock.getBjh(), stock.getSn(), stock.getDprtcode(), PartSnValidationEnum.MATERIAL.getId());
		}
		
		if(StringUtils.isBlank(stock.getPch())){
			try {
				stock.setPch(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_PCH.getName()));
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
		}
		stockMapper.insertSelective(stock);
		commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.SAVE,"");
		//新增或修改物料批次信息
		materialBatchInfoService.insertOrUpdate(stock.getDprtcode(), stock.getBjh(), stock.getSn(), stock.getPch(), stock.getKccb(), stock.getBiz(), stock.getJz(), stock.getJzbz());
		//保存证书
		InstallationListEditable record=new InstallationListEditable();
		record.setBjh(stock.getBjh());
		record.setXlh(stock.getSn());
		record.setPch(stock.getPch());
		record.setDprtcode(stock.getDprtcode());
		record.setCertificates(stock.getComponentCertificateList());
		record.setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
		componentCertificateService.save(record);
		
		//写入b_h_017  部件库存履历
		MaterialHistory materialHistory = new MaterialHistory(stock);
		materialHistory.setId(UUID.randomUUID().toString());
		materialHistoryMapper.insertSelective(materialHistory);
		//写入b_h_025  //库存履历
		history.setId(materialHistory.getId());
		history.setDprtcode(stock.getDprtcode());
		history.setCzrbmid(user.getBmdm());
		history.setCzrid(user.getId());
		history.setCzr(user.getRealname());
		history.setCzlx(StockHistoryTypeEnum.STOCK_COUNT.getId());
		history.setCzzlx(StockHistorySubtypeEnum.STOCK_COUNT_INVENTORY_PROFIT.getId());
		history.setCzsm(new StringBuffer()
			.append(StockHistorySubtypeEnum.STOCK_COUNT_INVENTORY_PROFIT.getName())
			.append(" 数量：")
			.append(stock.getKcsl()).toString()
			);
		history.setKcid(materialHistory.getKcid());
		stockHistoryMapper.insertSelective(history);
		//写入b_h_031 盘点记录表
		StoreCheck storeCheck = new StoreCheck();
		storeCheck.setId(UUID.randomUUID().toString());
		storeCheck.setDprtcode(stock.getDprtcode());
		storeCheck.setZdbmid(user.getBmdm());
		storeCheck.setZdrid(user.getId());
		storeCheck.setZt(EffectiveEnum.YES.getId());
		storeCheck.setBz(stock.getBz());
		storeCheck.setYkbs(StockCheckStatusEnum.INVENTORY_PROFIT.getId());
		storeCheck.setYksl(stock.getKcsl());
		storeCheck.setYkcsl(new BigDecimal("0.00"));
		storeCheck.setKcllid(materialHistory.getId());
		if(StringUtils.isNotBlank(stock.getHclx())){
			storeCheck.setWllx(Integer.parseInt(stock.getHclx()));
		}
		storeCheckMapper.insertSelective(storeCheck);
		return stock.getId();
	}
	
	/**
	 * @Description 修改库存
	 * @CreateTime 2018-3-21 下午5:55:37
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return String 库存id
	 * @throws BusinessException
	 */
	@Override
	public String update(Stock stock) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		Stock oldStock = stockMapper.selectById(stock.getId());
		if(oldStock == null){
			throw new BusinessException("库存数量不存在，可能已经出库或者销毁下架！");
		}
		BigDecimal sjsl = stock.getKcsl();
		if(null != oldStock.getDjsl() && oldStock.getDjsl().compareTo(sjsl) == 1){
			throw new BusinessException("刚刚有新的冻结活动， 导致目前实际数量小于冻结数量，不可操作盈亏登记！");
		}
		if(sjsl != null && sjsl.compareTo(oldStock.getKcsl()) == 0){
			throw new BusinessException("未发生盈亏的库存数据不用登记！");
		}
		//写入b_h_017  部件库存履历
		MaterialHistory materialHistory = new MaterialHistory(oldStock);
		materialHistory.setId(UUID.randomUUID().toString());
		materialHistory.setKcsl(sjsl);
		materialHistoryMapper.insertSelective(materialHistory);
		if(sjsl.compareTo(BigDecimal.ZERO) == 0){
			commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.DELETE,"");
			stockMapper.delete(stock);
		}else{
			//新增 b_h_001  库存信息
			stockMapper.update(stock);
			commonRecService.write(stock.getId(), TableEnum.B_H_001, user.getId(), UpdateTypeEnum.UPDATE,"");
		}
		StockHistory history=new StockHistory();
		//写入b_h_025  //库存履历
		history.setId(materialHistory.getId());
		history.setDprtcode(oldStock.getDprtcode());
		history.setCzrbmid(user.getBmdm());
		history.setCzrid(user.getId());
		history.setCzr(user.getRealname());
		history.setCzlx(StockHistoryTypeEnum.STOCK_COUNT.getId());
		StringBuffer czsm = new StringBuffer();
		if(sjsl.compareTo(oldStock.getKcsl()) == 1){
			history.setCzzlx(StockHistorySubtypeEnum.STOCK_COUNT_INVENTORY_PROFIT.getId());
			czsm.append(StockHistorySubtypeEnum.STOCK_COUNT_INVENTORY_PROFIT.getName())
			.append(" 数量：")
			.append((sjsl.subtract(oldStock.getKcsl())));
		}else{
			history.setCzzlx(StockHistorySubtypeEnum.STOCK_COUNT_INVENTORY_LOSSES.getId());
			czsm.append(StockHistorySubtypeEnum.STOCK_COUNT_INVENTORY_PROFIT.getName())
			.append(" 数量：")
			.append((oldStock.getKcsl().subtract(sjsl)));
		}
		history.setCzsm(czsm.toString());
		history.setKcid(materialHistory.getKcid());
		stockHistoryMapper.insertSelective(history);
		//写入b_h_031 盘点记录表
		StoreCheck storeCheck = new StoreCheck();
		storeCheck.setId(UUID.randomUUID().toString());
		storeCheck.setDprtcode(oldStock.getDprtcode());
		storeCheck.setZdbmid(user.getBmdm());
		storeCheck.setZdrid(user.getId());
		storeCheck.setZt(EffectiveEnum.YES.getId());
		storeCheck.setBz(stock.getBz());
		if(sjsl.compareTo(oldStock.getKcsl()) == 1){
			storeCheck.setYkbs(StockCheckStatusEnum.INVENTORY_PROFIT.getId());
		}else{
			storeCheck.setYkbs(StockCheckStatusEnum.INVENTORY_LOSS.getId());
		}
		storeCheck.setYksl(sjsl.subtract(oldStock.getKcsl()));
		storeCheck.setYkcsl(oldStock.getKcsl());
		storeCheck.setKcllid(materialHistory.getId());
		if(StringUtils.isNotBlank(stock.getHclx())){
			storeCheck.setWllx(Integer.parseInt(stock.getHclx()));
		}
		storeCheckMapper.insertSelective(storeCheck);
		return stock.getId();
	}
	
	/**
	 * @Description 盈亏历史分页列表查询
	 * @CreateTime 2018-3-22 上午11:55:12
	 * @CreateBy 刘兵
	 * @param storeCheck 盘点记录
	 * @return List<StoreCheck> 盘点记录集合
	 */
	@Override
	public List<StoreCheck> queryAllPageList(StoreCheck storeCheck) {
		return storeCheckMapper.queryAllPageList(storeCheck);
	}
	
	/**
	 * @Description 获取库存数据
	 * @CreateTime 2018-3-23 上午11:13:58
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @return Stock 库存
	 * @throws BusinessException
	 */
	@Override
	public Stock getByStock(Stock param) throws BusinessException{
		if(StringUtils.isNotBlank(param.getId())){
			Stock stock = stockMapper.getById(param.getId(), param.getDprtcode());
			validStock(stock, param.getHclx());
			return stock;
		}else{
			Stock stock = stockMapper.getByUuiddm((String)param.getParamsMap().get("uuiddm"), param.getDprtcode());
			validStock(stock, param.getHclx());
			return stock;
		}
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-3-26 上午10:13:59
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<StoreCheck>
	 */
	@Override
	public List<StoreCheck> doExportExcel(StoreCheck paramObj) {
		List<StoreCheck> list = storeCheckMapper.queryAllPageList(paramObj);
		for (StoreCheck storeCheck : list) {
			StringBuffer yk = new StringBuffer();
			if(StockCheckStatusEnum.INVENTORY_PROFIT.getId() == storeCheck.getYkbs()){
				yk.append("+");
			}else{
				storeCheck.getParamsMap().put("preyk", true);
			}
			yk.append(storeCheck.getYksl());
			storeCheck.getParamsMap().put("yk", yk);
		}
		return list;
	}
	
	/**
	 * @Description 验证库存
	 * @CreateTime 2018-3-23 上午11:34:13
	 * @CreateBy 刘兵
	 * @param stock 库存
	 * @param type 航材分类
	 */
	private void validStock(Stock stock, String type) throws BusinessException{
		if(null == stock || null == stock.getHclx()){
			throw new BusinessException("该条码对应的库存不存在！");
		}
		if("1".equals(type)){
			validation4CurrentLx(new Integer[]{
					MaterialTypeEnum.MATERIAL.getId(),
					MaterialTypeEnum.DANGEROUS_GOODS.getId(),
					MaterialTypeEnum.LOW_PRICE.getId(),
					MaterialTypeEnum.SONGSHAN.getId(),
					MaterialTypeEnum.OTHER.getId()
					}
			, Integer.parseInt(stock.getHclx())
			,"该条码对应的是工具，如果需要，请移至工具库管理-盘点功能中完成盘点登记!");
		}else{
			validation4CurrentLx(new Integer[]{MaterialTypeEnum.APPOINTED.getId()}
				,Integer.parseInt(stock.getHclx())
				,"该条码对应的是航材，如果需要，请移至航材库管理-盘点功能中完成盘点登记!");
		}
	}
	
	/**
	 * @Description 验证航材工具类型,当前类型是否存在于数组中,不存在:抛出异常message
	 * @CreateTime 2018-3-23 上午11:43:37
	 * @CreateBy 刘兵
	 * @param lxArr 可操作状态数组
	 * @param currentLx 当前状态
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	private void validation4CurrentLx(Integer[] lxArr, Integer currentLx,String message) throws BusinessException{
		if(currentLx != null && !ArrayUtils.contains(lxArr, currentLx)){
			throw new BusinessException(message);
		}
	}
	
}
