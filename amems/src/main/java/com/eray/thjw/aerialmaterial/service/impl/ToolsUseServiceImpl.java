package com.eray.thjw.aerialmaterial.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.dao.ToolsUseMapper;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.ToolsUse;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.aerialmaterial.service.ToolsUseService;
import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.SaiBongEnum;
import enu.aerialmaterial.ToolUseStatusEnum;

/**
 * 工具，设备借用服务实现
 * @author zhuchao
 *
 */
@Service
public class ToolsUseServiceImpl implements ToolsUseService{

	@Resource
	private ToolsUseMapper toolsUseMapper;
	
	@Resource
	private CommonRecService commonRecService; 
	
	@Resource
	private StockMapper stockMapper; 
	
	@Resource
	private HCMainDataMapper hcMainDataMapper; 
	
	@Resource
	private SaibongUtilService saibongUtilService;     
	
	@Resource
	private CommonMapper commonMapper;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@SuppressWarnings("serial")
	@Override
	public Map<String, Object> doApply(ToolsUse record) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = Boolean.TRUE;
		Stock param = new Stock();
		param.getParamsMap().put("dprtcode", record.getDprtcode());
		param.getParamsMap().put("kcid", record.getKcid());
		param.getParamsMap().put("status", "3");//空闲
		List<Stock> stocks = stockSerivce.queryList4Tool(param);
		if (stocks!=null && !stocks.isEmpty()) {
			//空闲状态可以申请借出
			Date date = commonMapper.getSysdate();
			//提交数据：借用人id，借用人名称  借用备注,库存ID
			User user = ThreadVarUtil.getUser();
			String id = UUID.randomUUID().toString();
			String jldh;	
			try {
				jldh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.GJSY.getName());
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			record.setJldh(jldh); 
			
			Stock stock = stockMapper.selectById(record.getKcid());
			
			record.setId(id);
			record.setKcid(stock.getId());
			record.setBjid(stock.getBjid());
			record.setBjh(stock.getBjh());
			record.setBjxlh(stock.getSn());
			if (stock.gethCMainData()!=null) {
				record.setXh(stock.gethCMainData().getXingh());
				record.setGg(stock.gethCMainData().getGg());
			}
			record.setJySl(stock.getKcsl());
			record.setJySj(date);
			
			record.setWhrid(user.getId());
			record.setWhbmid(user.getBmdm());
			record.setZt(ToolUseStatusEnum.APPLY.getId());
			toolsUseMapper.insertSelective(record);
		}
		else{
			isLegitimate = Boolean.FALSE;
			result.put("message", "空闲状态的工具，设备才能借出。");
		}
		result.put("isLegitimate", isLegitimate);
		return result;
	}

	@Override
	public Map<String, Object> doConfirm(ToolsUse record) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = Boolean.TRUE;
		Stock param = new Stock();
		param.getParamsMap().put("dprtcode", record.getDprtcode());
		param.getParamsMap().put("kcid", record.getKcid());
		param.getParamsMap().put("status", "1");//申请
		List<Stock> stocks = stockSerivce.queryList4Tool(param);
		if (stocks!=null && !stocks.isEmpty()) {
			//申请状态可以确认借出
			//提交数据：借出备注,借出ID,库存ID
			User user = ThreadVarUtil.getUser();
			Date date = commonMapper.getSysdate();
			ToolsUse recordDb = toolsUseMapper.selectByPrimaryKey(record.getId());
			record.setJcZrrid(user.getId());
			record.setJcSj(date);
			record.setJcSl(recordDb.getJySl());
			
			record.setWhrid(user.getId());
			record.setWhbmid(user.getBmdm());
			record.setZt(ToolUseStatusEnum.CONFIRM.getId());
			toolsUseMapper.updateByPrimaryKeySelective(record);
		}
		else{
			isLegitimate = Boolean.FALSE;
			result.put("message", "申请状态的工具，设备才能借出确认。");
		}
		result.put("isLegitimate", isLegitimate);
		return result;
	}

	@Override
	public Map<String, Object> doReturn(ToolsUse record) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = Boolean.TRUE;
		Stock param = new Stock();
		param.getParamsMap().put("dprtcode", record.getDprtcode());
		param.getParamsMap().put("kcid", record.getKcid());
		param.getParamsMap().put("status", "2");//借用(已确认)
		List<Stock> stocks = stockSerivce.queryList4Tool(param);
		if (stocks!=null && !stocks.isEmpty()) {
			//借用(已确认)状态可以确认借出
			//提交数据：归还备注,借出ID,库存ID，归还人id,归还人名称
			User user = ThreadVarUtil.getUser();
			Date date = commonMapper.getSysdate();
			ToolsUse recordDb = toolsUseMapper.selectByPrimaryKey(record.getId());
			
			record.setGhSj(date);
			record.setGhSl(recordDb.getJcSl());
			
			record.setWhrid(user.getId());
			record.setWhbmid(user.getBmdm());
			record.setZt(ToolUseStatusEnum.RETURNED.getId());
			toolsUseMapper.updateByPrimaryKeySelective(record);
		}
		else{
			isLegitimate = Boolean.FALSE;
			result.put("message", "申请状态的工具，设备才能借出确认。");
		}
		result.put("isLegitimate", isLegitimate);
		return result;
		
	}

	@Override
	public Map<String, Object> doEnd(ToolsUse record) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = Boolean.TRUE;
		Stock param = new Stock();
		param.getParamsMap().put("dprtcode", record.getDprtcode());
		param.getParamsMap().put("kcid", record.getKcid());
		param.getParamsMap().put("inStatus", Arrays.asList("1", "2","10"));//申请
		List<Stock> stocks = stockSerivce.queryList4Tool(param);
		if (stocks!=null && !stocks.isEmpty()) {
			//提交数据：借出ID,库存ID，归还人id,归还人名称
			String czls = UUID.randomUUID().toString();
			User user = ThreadVarUtil.getUser();
			record.setWhrid(user.getId());
			record.setWhbmid(user.getBmdm());
			record.setZt(ToolUseStatusEnum.CLOSE.getId());
			toolsUseMapper.updateByPrimaryKeySelective(record);
		}
		else{
			isLegitimate = Boolean.FALSE;
			result.put("message", "申请状态的工具，设备才能借出确认。");
		}
		result.put("isLegitimate", isLegitimate);
		return result;
	}

	@Override
	public List<ToolsUse> queryList(ToolsUse record) {
		return toolsUseMapper.queryList(record);
	}

	@Override
	public Map<String, Object> doConfirmFast(ToolsUse record) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = Boolean.TRUE;
		Stock param = new Stock();
		param.getParamsMap().put("dprtcode", record.getDprtcode());
		param.getParamsMap().put("kcid", record.getKcid());
		param.getParamsMap().put("status", "3");//空闲
		List<Stock> stocks = stockSerivce.queryList4Tool(param);
		if (stocks!=null && !stocks.isEmpty()) {

			Date date = commonMapper.getSysdate();
			Stock stock = stockMapper.selectById(record.getKcid());
			//提交数据：借用人id，借用人名称  借用备注,库存ID
			String czls = UUID.randomUUID().toString();
			User user = ThreadVarUtil.getUser();
			String id = UUID.randomUUID().toString();
			String jldh;	
			try {
				jldh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.GJSY.getName());
			} catch (SaibongException e) {
				throw new RuntimeException(e);
			}
			record.setJldh(jldh); 
			record.setId(id);
			record.setKcid(stock.getId());
			record.setBjid(stock.getBjid());
			record.setBjh(stock.getBjh());
			record.setBjxlh(stock.getSn());
			if (stock.gethCMainData()!=null) {
				record.setXh(stock.gethCMainData().getXingh());
				record.setGg(stock.gethCMainData().getGg());
			}
			record.setJySl(stock.getKcsl());
			
			record.setJcZrrid(user.getId());
			record.setJcSj(date);
			record.setJcSl(stock.getKcsl());
			record.setJcBz(record.getJyBz());
			
			record.setWhrid(user.getId());
			record.setWhbmid(user.getBmdm());
			record.setZt(ToolUseStatusEnum.CONFIRM.getId());
			toolsUseMapper.insertSelective(record);
		}
		else{
			isLegitimate = Boolean.FALSE;
			result.put("message", "申请状态的工具，设备才能借出确认。");
		}
		result.put("isLegitimate", isLegitimate);
		return result;
		
		
	}

}
