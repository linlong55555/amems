package com.eray.thjw.baseinfo.servcice;

import java.util.List;
import java.util.Map;

import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.exception.BusinessException;

/** 
 * @Description 客户信息service接口
 * @CreateTime 2017-9-15 下午4:57:06
 * @CreateBy 甘清	
 */
public interface CustomerService {
	/**
	 * @Description 获得客户信息列表
	 * @CreateTime 2017-9-18 下午2:00:46
	 * @CreateBy 甘清
	 * @param customer 前端搜索条件
	 * @return List<Customer> 
	 */
	Map<String, Object> getCustomerList(Customer customer);
	
	/**
	 * @Description 添加客户信息
	 * @CreateTime 2017-9-18 下午4:13:48
	 * @CreateBy 甘清
	 * @param customer
	 */
	Customer addCustomer(Customer customer) throws BusinessException;
	
	/**
	 * @Description 根据客户编号获得客户信息
	 * @CreateTime 2017-9-18 下午4:17:02
	 * @CreateBy 甘清
	 * @param customer 客户信息
	 * @return Customer
	 */
	Customer getCustomerById(Customer customer);
	
	/**
	 * @Description 更新客户信息
	 * @CreateTime 2017-9-18 下午4:20:38
	 * @CreateBy 甘清
	 * @param customer 客户信息参数
	 */
	void updateCustomer(Customer customer) throws BusinessException;
	/**
	 * 
	 * @Description 根据组织机构查询客户信息
	 * @CreateTime 2017-9-19 下午2:14:58
	 * @CreateBy 孙霁
	 * @param dprtcode
	 * @return List<Customer>
	 */
	Map<String, Object> selectByDprtcode(Customer customer);

	
	/**
	 * @Description 删除项目
	 * @CreateTime 2017-9-24 下午8:22:32
	 * @CreateBy 甘清
	 * @param id 项目ID
	 * @throws BusinessException
	 */
	void delCustomer(String id) throws BusinessException;
	/**
	 * 
	 * @Description 客户信息下拉框
	 * @CreateTime 2017年10月17日 下午2:49:53
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Customer> getCustomerList(String dprtcode);
}
