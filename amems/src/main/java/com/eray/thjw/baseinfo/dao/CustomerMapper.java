package com.eray.thjw.baseinfo.dao;

import java.util.List;

import com.eray.thjw.baseinfo.po.Customer;
/** 
 * @Description 客户信息接口
 * @CreateTime 2017-9-15 下午4:57:47
 * @CreateBy 甘清	
 */
public interface CustomerMapper {
	/**
	 * @Description 根据前端条件获得客户信息
	 * @CreateTime 2017-9-18 下午2:11:39
	 * @CreateBy 甘清
	 * @param customer
	 * @return List<Customer>
	 */
	List<Customer> getCustomerList(Customer customer);
	
	/**
	 * @Description 添加客户信息
	 * @CreateTime 2017-9-18 下午4:27:16
	 * @CreateBy 甘清
	 * @param customer 信息参数信息
	 */
	void addCustomer(Customer customer);
	
	/**
	 * @Description 根据ID获得客户信息
	 * @CreateTime 2017-9-18 下午4:30:03
	 * @CreateBy 甘清
	 * @param customer 前端客户信息
	 * @return Customer
	 */
	Customer getCustomerById(Customer customer);
	/**
	 * @Description 更新客户信息
	 * @CreateTime 2017-9-18 下午4:33:22
	 * @CreateBy 甘清
	 * @param customer 客户信息
	 */
	void updateCustomer(Customer customer);
	
	/**
	 * @Description 删除客户信息
	 * @CreateTime 2017-9-24 下午8:19:56
	 * @CreateBy 甘清
	 * @param customer 前端参数
	 */
	void delCustomer(Customer customer);
	
	/**
	 * 
	 * @Description 根据组织机构查询客户信息
	 * @CreateTime 2017-9-19 下午2:14:58
	 * @CreateBy 孙霁
	 * @param dprtcode
	 * @return List<Customer>
	 */
	List<Customer> selectByDprtcode(Customer customer);
	
	/**
	 * @Description 根据客户编号检查客户是否已经存储在
	 * @CreateTime 2017-9-25 下午9:49:45
	 * @CreateBy 甘清
	 * @param customer 检查的信息
	 * @return
	 */
	List<Customer> checkCustomer(Customer customer);
	/**
	 * 
	 * @Description 根据组织机构下客户信息
	 * @CreateTime 2017年10月17日 下午2:53:02
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Customer> selectByDprt(String dprtcode);
	/**
	 * 
	 * @Description 获取客户信息弹窗
	 * @CreateTime 2017年10月18日 上午11:41:06
	 * @CreateBy 岳彬彬
	 * @param customer
	 * @return
	 */
	List<Customer> selectByCustomer(Customer customer);

}
