package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.StockMapper;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.MeasurementToolsDetailsMapper;
import com.eray.thjw.material2.dao.ToolBorrowReturnMapper;
import com.eray.thjw.material2.dao.ToolBorrowReturnRecordMapper;
import com.eray.thjw.material2.po.MeasurementToolsDetails;
import com.eray.thjw.material2.po.ToolBorrowReturn;
import com.eray.thjw.material2.po.ToolBorrowReturnRecord;
import com.eray.thjw.material2.service.ToolBorrowReturnService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.MaterialTypeEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EnableEnum;
import enu.material2.ToolBorrowReturnTypeEnum;

/**
 * @Description 工具/设备借归service实现类
 * @CreateTime 2018年3月27日 下午4:21:56
 * @CreateBy 韩武
 */
@Service("toolBorrowReturnService")
public class ToolBorrowReturnServiceImpl implements ToolBorrowReturnService  {

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private StockMapper stockMapper;
	
	@Resource
	private MeasurementToolsDetailsMapper measurementToolsDetailsMapper;
	
	@Resource
	private ToolBorrowReturnMapper toolBorrowReturnMapper;
	
	@Resource
	private ToolBorrowReturnRecordMapper toolBorrowReturnRecordMapper;
	
	/**
	 * @Description 加载人员
	 * @CreateTime 2018年3月27日 下午3:28:50
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public User loadUser(Map<String, String> record) throws BusinessException{
		User user = userMapper.selectByPrimaryKey(record.get("id"));
		// 验证状态=启用的用户是否存在
		if(user == null || EnableEnum.DISABLED.getId().equals(user.getState())){
			throw new BusinessException((ToolBorrowReturnTypeEnum.BORROW.getId().equals(record.get("type")) ? "借用" : "归还") 
					+ "人"+record.get("realname")+" 账号不存在！");
		}
		return user;
	}

	/**
	 * @Description 加载工具
	 * @CreateTime 2018年3月27日 下午3:31:03
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	@Override
	public Stock loadTool(Map<String, String> record) throws BusinessException{
		Stock stock = stockMapper.selectById(record.get("id"));
		// 验证库存是否存在
		if(stock == null){
			throw new BusinessException("工具/设备"+record.get("bjh")+" 信息不存在，可能还未进行资产入库或者已经出库或者已经销毁！");
		}
		// 验证库存是否为工具设备
		if(stock.gethCMainData() != null 
				&& !MaterialTypeEnum.APPOINTED.getId().equals(stock.gethCMainData().getHclx())){
			throw new BusinessException(record.get("bjh")+" 非工具/设备，不可借归！");
		}
		// 借用
		if(ToolBorrowReturnTypeEnum.BORROW.getId().equals(record.get("type"))){
			// 检查货架寿命
			if(stock.getHjsm() != null && stock.getHjsm().compareTo(new Date()) <= 0){
				throw new BusinessException("工具"+stock.getBjh()+" 已经达到最大货位寿命，不可借用！");
			}
			// 检查状态
			if(stock.getDjsl() != null && stock.getDjsl().compareTo(stock.getKcsl()) == 0){
				throw new BusinessException("工具"+stock.getBjh()+" 已冻结，可能已经报废或者正处于报废流程，不可借用！");
			}
			// 检查工具校验日期
			MeasurementToolsDetails detail = measurementToolsDetailsMapper.queryByJhAndXlh(stock.getBjh(), stock.getSn(), stock.getDprtcode());
			if(detail != null && detail.getJyXcrq() != null && detail.getJyXcrq().compareTo(new Date()) <= 0){
				throw new BusinessException("工具"+stock.getBjh()+"  已经超过校验期，不可借用！");
			}
		}
		
		// 查询工具借用/归还状态
		ToolBorrowReturn status = toolBorrowReturnMapper.selectByPrimaryKey(stock.getId());
		// 存在
		if(status != null){
			// 状态 = 借用
			if(ToolBorrowReturnTypeEnum.BORROW.getId().equals(record.get("type"))){
				throw new BusinessException("工具"+stock.getBjh()+" 在此之前已经借出给"+status.getJyZrrmc()+"，不可借用！");
			}
		}
		// 不存在
		else{
			// 状态 = 归还
			if(ToolBorrowReturnTypeEnum.RETURN.getId().equals(record.get("type"))){
				stock.getParamsMap().put("msg", "工具"+stock.getBjh()+" 未借用，但可以归还！");
			}
		}
		return stock;
	}

	/**
	 * @Description 根据关键字查询人员/工具
	 * @CreateTime 2018年3月27日 下午3:31:58
	 * @CreateBy 韩武
	 * @param keyword
	 * @return
	 */
	@Override
	public Map<String, Object> loadByKeyword(Map<String, String> record) throws BusinessException{
		
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		
		// 查询人员
		User userParam = new User();
		userParam.setJgdm(record.get("dprtcode"));
		userParam.setKeyword(record.get("keyword"));
		List<User> users = userMapper.queryUserAllByDprtcode(userParam);
		resultMap.put("users", users);
		
		// 查询库存
		List<Stock> stocks = new ArrayList<Stock>();
		for (Stock stock : stockMapper.queryByToolBorrowReturn(record)) {
			// 借用
			if(ToolBorrowReturnTypeEnum.BORROW.getId().equals(record.get("type"))){
				// 检查货架寿命
				if(stock.getHjsm() != null && stock.getHjsm().compareTo(new Date()) <= 0){
					continue;
				}
				// 检查状态
				if(StockStatusEnum.SCRAP.getId().equals(stock.getZt())){
					continue;
				}
				// 检查工具校验日期
				MeasurementToolsDetails detail = measurementToolsDetailsMapper.queryByJhAndXlh(stock.getBjh(), stock.getSn(), stock.getDprtcode());
				if(detail != null && detail.getJyXcrq() != null && detail.getJyXcrq().compareTo(new Date()) <= 0){
					continue;
				}
				// 查询工具借用/归还状态
				ToolBorrowReturn status = toolBorrowReturnMapper.selectByPrimaryKey(stock.getId());
				if(status != null){
					continue;
				}
			}
			
			stocks.add(stock);
		}
		if(users.isEmpty() && stocks.isEmpty()){
			throw new BusinessException("输入信息："+record.get("keyword")+"，匹配不到工具或者人员，请检查后重新输入！");
		}
		resultMap.put("stocks", stocks);
		return resultMap;
	}

	/**
	 * @Description 工具/设备借归提交
	 * @CreateTime 2018年4月2日 下午3:24:51
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	@Override
	public void doSubmit(List<ToolBorrowReturn> list) throws BusinessException {
		
		for (ToolBorrowReturn tool : list) {
			
			// 借用
			if(ToolBorrowReturnTypeEnum.BORROW.getId().equals(tool.getType())){
				// 验证库存数据
				validateStockData(tool);
				// 验证用户数据
				validateUserData(tool);
				// 保存b_h_00101 工具借用/归还状态
				saveToolBorrowReturn(tool);
				// 保存b_h_030 工具借用/归还记录
				saveToolBorrowReturnRecord(tool, ToolBorrowReturnTypeEnum.BORROW);
			}
			
			// 归还
			else if(ToolBorrowReturnTypeEnum.RETURN.getId().equals(tool.getType())){
				// 验证用户数据
				validateUserData(tool);
				// 保存b_h_030 工具借用/归还记录
				saveToolBorrowReturnRecord(tool, ToolBorrowReturnTypeEnum.RETURN);
				// 根据库存id删除b_h_00101 工具借用/归还状态表数据
				deleteToolBorrowReturn(tool);
			}
		}
	}
	
	/**
	 * @Description 验证数据
	 * @CreateTime 2018年4月2日 下午3:38:08
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	private void validateStockData(ToolBorrowReturn tool) throws BusinessException {
		
		Stock stock = stockMapper.selectById(tool.getId());
		// 验证库存是否存在
		if(stock == null){
			throw new BusinessException("库存不存在，请刷新后重试！");
		}
		// 检查货架寿命
		if(stock.getHjsm() != null && stock.getHjsm().compareTo(new Date()) <= 0){
			throw new BusinessException("工具"+stock.getBjh()+" 已经达到最大货位寿命，不可借用！");
		}
		// 检查状态
		if(stock.getDjsl() != null && stock.getDjsl().compareTo(stock.getKcsl()) == 0){
			throw new BusinessException("工具"+stock.getBjh()+" 已冻结，可能已经报废或者正处于报废流程，不可借用！");
		}
		// 检查工具校验日期
		MeasurementToolsDetails detail = measurementToolsDetailsMapper.queryByJhAndXlh(stock.getBjh(), stock.getSn(), stock.getDprtcode());
		if(detail != null && detail.getJyXcrq() != null && detail.getJyXcrq().compareTo(new Date()) <= 0){
			throw new BusinessException("工具"+stock.getBjh()+"  已经超过校验期，不可借用！");
		}
		
		// 查询工具借用/归还状态
		ToolBorrowReturn status = toolBorrowReturnMapper.selectByPrimaryKey(stock.getId());
		// 存在且状态 = 借用
		if(status != null && ToolBorrowReturnTypeEnum.BORROW.getId().equals(tool.getType())){
			throw new BusinessException("工具"+stock.getBjh()+" 在此之前已经借出给"+status.getJyZrrmc()+"，不可借用！");
		}
		
	}
	
	/**
	 * @Description 验证用户数据
	 * @CreateTime 2018年4月2日 下午3:52:37
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	private void validateUserData(ToolBorrowReturn tool) throws BusinessException {
		
		User user = userMapper.selectByPrimaryKey(tool.getJyZrrid());
		// 验证状态=启用的用户是否存在
		if(user == null || EnableEnum.DISABLED.getId().equals(user.getState())){
			throw new BusinessException((ToolBorrowReturnTypeEnum.BORROW.getId().equals(tool.getType()) ? "借用" : "归还") 
					+ "人"+tool.getJyZrrmc()+" 账号不存在！");
		}
	}
	
	/**
	 * @Description 保存b_h_00101 工具借用/归还状态
	 * @CreateTime 2018年4月2日 下午3:56:27
	 * @CreateBy 韩武
	 * @param tool
	 */
	private void saveToolBorrowReturn(ToolBorrowReturn tool){
		tool.setJySj(new Date());
		
		User user=ThreadVarUtil.getUser();
		tool.setCzrid(user.getId());
		tool.setCzbmid(user.getBmdm());
		tool.setCzsj(new Date());
		toolBorrowReturnMapper.insertSelective(tool);
	}
	
	/**
	 * @Description 根据库存id删除b_h_00101 工具借用/归还状态表数据
	 * @CreateTime 2018年4月2日 下午4:24:09
	 * @CreateBy 韩武
	 * @param tool
	 */
	private void deleteToolBorrowReturn(ToolBorrowReturn tool){
		toolBorrowReturnMapper.deleteByPrimaryKey(tool.getId());
	}
	
	/**
	 * @Description 保存b_h_030 工具借用/归还记录
	 * @CreateTime 2018年4月2日 下午4:02:26
	 * @CreateBy 韩武
	 * @param tool
	 */
	private void saveToolBorrowReturnRecord(ToolBorrowReturn tool, ToolBorrowReturnTypeEnum toolBorrowReturnTypeEnum){
		
		ToolBorrowReturnRecord record = new ToolBorrowReturnRecord();
		record.setId(UUID.randomUUID().toString());
		
		Stock stock = stockMapper.selectById(tool.getId());
		record.setKcid(stock.getId());
		record.setBjid(stock.getBjid());
		record.setBjh(stock.getBjh());
		record.setBjxlh(stock.getSn());
		record.setJllx(Integer.valueOf(toolBorrowReturnTypeEnum.getId()));
		
		record.setJyZrrid(tool.getJyZrrid());
		record.setJyZrrmc(tool.getJyZrrmc());
		record.setJySj(new Date());
		record.setJySl(stock.getKcsl());
		
		// 只有借用时，才写入借用备注
		if(ToolBorrowReturnTypeEnum.BORROW.equals(toolBorrowReturnTypeEnum)){
			record.setCqjybs(tool.getCqjybs());
			record.setJyBz(tool.getJyBz());
		}
		
		User user=ThreadVarUtil.getUser();
		record.setDprtcode(user.getJgdm());
		record.setCzrid(user.getId());
		record.setCzbmid(user.getBmdm());
		record.setCzsj(new Date());
		
		toolBorrowReturnRecordMapper.insertSelective(record);
	}
}