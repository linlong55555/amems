package com.eray.thjw.baseinfo.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.baseinfo.servcice.CustomerService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Pagination;

/** 
 * @Description 客户控制器
 * @CreateTime 2017-9-15 下午4:55:20
 * @CreateBy 甘清	
 */
@Controller
@RequestMapping("/baseinfo/customer")
public class CustomerController extends BaseController {
	
	@Resource
	private CustomerService  customerService;
    /**
     * @Description 客户信息展示列表
     * @CreateTime 2017-9-18 下午1:56:21
     * @CreateBy 甘清
     * @return view
     */
	@Privilege(code = "baseinfo:customer:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		return new ModelAndView("baseinfo/customer/customer_main");
	}
	
	/**
	 * @Description 客户列表信息加载
	 * @CreateTime 2017-9-18 下午5:13:04
	 * @CreateBy 甘清
	 * @param customer 客户信息
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "getcustomerList", method = RequestMethod.POST)
	public Map<String, Object> getCustomerList(@RequestBody Customer customer) {	
		Map<String, Object> map = customerService.getCustomerList(customer);
		return map;
	}
	/**
	 * @Description 新增客户信息
	 * @CreateTime 2017-9-18 下午4:44:52
	 * @CreateBy 甘清
	 * @param customer 客户信息
	 * @throws BusinessException
	 */
	@Privilege(code = "baseinfo:customer:main:add")
	@ResponseBody
	@RequestMapping(value = "addcustomer", method = RequestMethod.POST)
	public Map<String, Object> addCustomer(@RequestBody Customer customer) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Customer cust = customerService.addCustomer(customer);
			map.put("customer", cust);
			return map;
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("添加客户信息失败！", e);
		}
	}
	
	/**
	 * @Description 删除客户信息（逻辑删除）
	 * @CreateTime 2017-9-24 下午7:39:14
	 * @CreateBy 甘清
	 * @param stage 状态
	 * @throws BusinessException
	 */
	@Privilege(code = "baseinfo:customer:main:del")
	@ResponseBody
	@RequestMapping(value = "del/{id}", method = RequestMethod.POST)
	public void delCustomer(@PathVariable("id")String id) throws BusinessException {
		try{
			customerService.delCustomer(id);
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("删除客户失败！",e);
		}
	}
	
	/**
	 * @Description 根据id获得客户信息
	 * @CreateTime 2017-9-18 下午4:08:17
	 * @CreateBy 甘清
	 * @param customer 客户信息
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "getcustomerbyid", method = RequestMethod.POST)
	public Map<String, Object> getCustomerById(@RequestBody Customer customer) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customer", customerService.getCustomerById(customer));
		return map;
	}
	
	/**
	 * @Description 更新客户信息
	 * @CreateTime 2017-9-18 下午4:19:03
	 * @CreateBy 甘清
	 * @param customer 客户信息
	 * @throws BusinessException
	 */
	@Privilege(code = "baseinfo:customer:main:edit")
	@ResponseBody
	@RequestMapping(value = "updatecustomer", method = RequestMethod.POST)
	public void updateCustomer(@RequestBody Customer customer) throws BusinessException {
		try{
			customerService.updateCustomer(customer);
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("更新客户信息失败！", e);
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
	@ResponseBody
	@RequestMapping("selectByDprtcode")
	public Map<String, Object> selectByDprtcode(@RequestBody Customer customer) throws BusinessException {
		try {
			return customerService.selectByDprtcode(customer);
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	/**
	 * 
	 * @Description 获取组织机构下客户信息下拉框
	 * @CreateTime 2017年10月17日 下午2:53:56
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getCustomers", method = RequestMethod.POST)
	public List<Customer> getCustomers(String dprtcode) throws BusinessException {
		try {
			return customerService.getCustomerList(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 客户弹窗
	 * @CreateTime 2017年10月17日 下午4:57:21
	 * @CreateBy 岳彬彬
	 * @param customer
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getCustomersList", method = RequestMethod.POST)
	public Map<String,Object> getCustomersList(@RequestBody Customer customer) throws BusinessException {
		try {
			return customerService.selectByDprtcode(customer);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * 
	 * @Description 导出客户信息
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "CustomerInfo.xls")
	public String export(Customer customer, HttpServletRequest request,Model model) throws BusinessException {
		try {
			customer.setDprtcode(new String (customer.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			customer.setPagination(p);
			Map<String, Object> resultMap = customerService.getCustomerList(customer);
			List<Customer> list = (List<Customer>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "khxx", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
