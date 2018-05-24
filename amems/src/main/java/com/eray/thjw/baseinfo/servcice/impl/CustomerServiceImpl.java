package com.eray.thjw.baseinfo.servcice.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.baseinfo.dao.CustomerMapper;
import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.baseinfo.servcice.CustomerService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/** 
 * @Description 客户实现
 * @CreateTime 2017-9-18 下午2:03:11
 * @CreateBy 甘清	
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerMapper customerMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Override
	public Map<String, Object> getCustomerList(Customer customer) {
		String id = customer.getId();
		customer.setId("");
		PageHelper.startPage(customer.getPagination());
		List<Customer> list = customerMapper.getCustomerList(customer);
		if (((Page)list).getTotal() > 0) {
			if(StringUtils.isNotBlank(id)) {
				if (!PageUtil.hasRecord(list, id)) {
					Customer s = new Customer();
					s.setId(id);
					Customer firtCustomer = customerMapper.getCustomerById(s);
					if (firtCustomer != null) {
						list.add(0, firtCustomer);
					}
				}
			}
			return PageUtil.pack4PageHelper(list, customer.getPagination());
		} else {
			List<Customer> custlist = new ArrayList<Customer>();
			if(StringUtils.isNotBlank(id)) {
				Customer s = new Customer();
				s.setId(id);
				Customer cust = customerMapper.getCustomerById(s);
				if (cust != null) {
					custlist.add(cust);
				}
			}
			return PageUtil.pack(1, custlist, customer.getPagination());
		}
	}

	@Override
	public Customer addCustomer(Customer customer) throws BusinessException {
		//检查客户编号是否重复出现
		User user = ThreadVarUtil.getUser();
		Customer checkCustomer = new  Customer();
		checkCustomer.setDprtcode(user.getJgdm());
		checkCustomer.setKhbm(customer.getKhbm());
		List<Customer> cts = customerMapper.checkCustomer(checkCustomer);
		if (cts == null || cts.size() < 1 ) {
			String id = UUID.randomUUID().toString();
			String czls = UUID.randomUUID().toString();
			customer.setId(id);
			customer.setWhsj(new Date());
			customer.setDprtcode(user.getJgdm());
			customer.setWhrid(user.getId());
			customer.setWhbmid(user.getBmdm());
			customer.setZt(EffectiveEnum.YES.getId());
			customerMapper.addCustomer(customer);
			commonRecService.write(customer.getId(), TableEnum.D_019, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, customer.getId());
			return customer;
		} else {
			throw new BusinessException("客户编号:" + customer.getKhbm() + "已存在!");
		}
	}

	@Override
	public Customer getCustomerById(Customer customer) {
		return customerMapper.getCustomerById(customer);
	}

	@Override
	public void updateCustomer(Customer customer) throws BusinessException {
		Customer oldCustomer = customerMapper.getCustomerById(customer);
		if (oldCustomer == null) {
			throw new BusinessException("该客户信息已删除，请刷新后再进行操作!");
		}
		//检查客户编号是否重复出现
		User user = ThreadVarUtil.getUser();
		Customer checkCustomer = new  Customer();
		checkCustomer.setDprtcode(oldCustomer.getDprtcode()); //取老数据的组织机构码
		checkCustomer.setKhbm(customer.getKhbm());
		List<Customer> cts = customerMapper.checkCustomer(checkCustomer);
		if (cts != null && cts.size() > 0) {
			if (cts.size() > 1) {
				throw new BusinessException("客户编号:" + customer.getKhbm() + "已存在!");
			}
			if (oldCustomer.getId().equals(customer.getId())) {
				String czls = UUID.randomUUID().toString();
				customer.setWhsj(new Date());
				customer.setWhrid(user.getId());
				customer.setWhbmid(user.getBmdm());
				customer.setZt(EffectiveEnum.YES.getId());
				customer.setDprtcode(oldCustomer.getDprtcode());
				commonRecService.write(customer.getId(), TableEnum.D_019, user.getId(), czls, LogOperationEnum.EDIT,
						UpdateTypeEnum.UPDATE, customer.getId());
				customerMapper.updateCustomer(customer);
			} else {
				throw new BusinessException("客户编号:" + customer.getKhbm() + "已存在!");
			}
		} else {
			throw new BusinessException("该客户信息已删除，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 根据组织机构查询客户信息
	 * @CreateTime 2017-9-19 下午2:14:58
	 * @CreateBy 孙霁
	 * @param dprtcode
	 * @return List<Customer>
	 */
	public Map<String, Object> selectByDprtcode(Customer customer) {
		PageHelper.startPage(customer.getPagination());
		List<Customer> list = customerMapper.selectByCustomer(customer);
		return PageUtil.pack4PageHelper(list, customer.getPagination());
	}
    /**
     * 删除客户信息
     */
	@Override
	public void delCustomer(String id) throws BusinessException {
		Customer customer = new Customer();
		User user = ThreadVarUtil.getUser();
		customer.setId(id);
		customer.setZt(EffectiveEnum.NO.getId());
		String czls = UUID.randomUUID().toString();
		commonRecService.write(id, TableEnum.D_019, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, id);
		customerMapper.delCustomer(customer);
		
	}
	/**
	 * 
	 * @Description 客户信息下拉框
	 * @CreateTime 2017年10月17日 下午2:49:53
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	@Override
	public List<Customer> getCustomerList(String dprtcode) {
		
		return customerMapper.selectByDprt(dprtcode);
	}

}
