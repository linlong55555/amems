package com.eray.thjw.material2.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractPayment;
import com.eray.thjw.material2.service.ContractPaymentService;

/**
 * @Description 合同付款
 * @CreateTime 2018-3-15 上午10:39:21
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("material/contractpayment")
public class ContractPaymentController {
	
	/**
	 * @Description 合同管理service
	 * @CreateTime 2018-3-8 上午11:32:12
	 * @CreateBy 刘兵
	 */
	@Autowired
	private ContractPaymentService contractPaymentService;
	
	/**
	 * @Description  新增合同付款
	 * @CreateTime 2018-3-15 上午10:42:36
	 * @CreateBy 刘兵
	 * @param payment 合同付款
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void save(@RequestBody ContractPayment payment) throws BusinessException{
		try {
			contractPaymentService.save(payment);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存合同付款失败!",e);
		}
	}
	
	/**
	 * @Description 编辑合同付款
	 * @CreateTime 2018-3-15 上午10:42:36
	 * @CreateBy 刘兵
	 * @param payment 合同付款
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:01")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(@RequestBody ContractPayment payment) throws BusinessException{
		try {
			contractPaymentService.update(payment);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("编辑合同付款失败!",e);
		}
	}
	
	/**
	 * @Description 删除合同付款
	 * @CreateTime 2018-3-15 上午10:43:40
	 * @CreateBy 刘兵
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code="material:contract:mgnt:01")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(String id) throws BusinessException{
		try {
			contractPaymentService.deleteByPrimaryKey(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!",e);
		}
	}
	
	/**
	 * @Description 根据id查询合同付款
	 * @CreateTime 2018-3-15 上午10:44:33
	 * @CreateBy 刘兵
	 * @param id id
	 * @return LisContractPayment 合同付款
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public ContractPayment selectById(String id) throws BusinessException {
		try {
			return contractPaymentService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询合同付款失败!",e);
		}
	}
	
	/**
	 * @Description 根据合同id查询合同付款
	 * @CreateTime 2018-3-15 上午10:44:33
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return List<ContractPayment> 合同付款集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByMainid",method={RequestMethod.POST,RequestMethod.GET})
	public List<ContractPayment> selectByMainid(String mainid) throws BusinessException {
		try {
			return contractPaymentService.selectByMainid(mainid);
		} catch (Exception e) {
			throw new BusinessException("查询合同付款失败!",e);
		}
	}
}
