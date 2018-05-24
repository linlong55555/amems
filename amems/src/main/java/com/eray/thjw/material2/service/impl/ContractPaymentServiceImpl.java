package com.eray.thjw.material2.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.ContractMgntMapper;
import com.eray.thjw.material2.dao.ContractPaymentMapper;
import com.eray.thjw.material2.po.ContractMgnt;
import com.eray.thjw.material2.po.ContractPayment;
import com.eray.thjw.material2.service.ContractPaymentService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.EffectiveEnum;
import enu.material2.ContractMgntStatusEnum;

/**
 * @Description 合同付款service实现类
 * @CreateTime 2018-3-15 上午10:23:54
 * @CreateBy 刘兵
 */
@Service
public class ContractPaymentServiceImpl implements ContractPaymentService{
	
	@Resource
	private ContractPaymentMapper contractPaymentMapper;
	
	@Resource
	private ContractMgntMapper contractMgntMapper;
	
	/**
	 * @Description 保存合同付款
	 * @CreateTime 2018-3-15 上午10:25:32
	 * @CreateBy 刘兵
	 * @param payment 合同付款
	 */
	@Override
	public void save(ContractPayment payment) throws BusinessException{
		//获取随机的uuid
		String id = UUID.randomUUID().toString();
		ContractMgnt cm = contractMgntMapper.selectByPrimaryKey(payment.getMainid());
		if(null == cm){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				ContractMgntStatusEnum.SUBMIT.getId()
				,ContractMgntStatusEnum.HEISHI.getId()
				,ContractMgntStatusEnum.REVISION.getId()},cm.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		payment.setId(id);
		payment.setZt(EffectiveEnum.YES.getId());
		contractPaymentMapper.insertSelective(payment);
	}
	/**
	 * @Description 编辑合同付款
	 * @CreateTime 2018-3-15 上午10:25:32
	 * @CreateBy 刘兵
	 * @param payment 合同付款
	 */
	@Override
	public void update(ContractPayment payment) throws BusinessException{
		ContractMgnt cm = contractMgntMapper.selectByPrimaryKey(payment.getMainid());
		if(null == cm){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				ContractMgntStatusEnum.SUBMIT.getId()
				,ContractMgntStatusEnum.HEISHI.getId()
				,ContractMgntStatusEnum.REVISION.getId()},cm.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		contractPaymentMapper.updateByPrimaryKeySelective(payment);
	}
	/**
	 * @Description 删除合同付款
	 * @CreateTime 2018-3-15 上午10:27:36
	 * @CreateBy 刘兵
	 * @param id
	 */
	@Override
	public void deleteByPrimaryKey(String id) throws BusinessException{
		contractPaymentMapper.deleteByPrimaryKey(id);
	}
	/**
	 * @Description 根据mainid删除合同付款
	 * @CreateTime 2018-3-15 上午10:27:36
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 */
	@Override
	public void deleteByMainid(String mainid) throws BusinessException{
		contractPaymentMapper.deleteByMainid(mainid);
	}
	
	/**
	 * @Description 根据id查询合同付款(带明细)
	 * @CreateTime 2018-3-15 上午10:29:26
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return 合同付款
	 */
	@Override
	public ContractPayment selectById(String id){
		return contractPaymentMapper.selectById(id);
	}
	
	/**
	 * @Description 根据合同id查询合同付款列表
	 * @CreateTime 2018-3-15 上午10:29:26
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return 合同付款集合
	 */
	@Override
	public List<ContractPayment> selectByMainid(String mainid) {
		return contractPaymentMapper.selectByMainid(mainid);
	}
	
	/**
	 * @Description 验证表单重复提交,当前状态是否存在于数组中,不存在:抛出异常message
	 * @CreateTime 2018-3-15 上午10:57:35
	 * @CreateBy 刘兵
	 * @param ztArr 可操作状态数组
	 * @param currentZt 当前状态
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(Integer[] ztArr, Integer currentZt,String message) throws BusinessException{
		if(currentZt != null && !ArrayUtils.contains(ztArr,currentZt)){
			throw new BusinessException(message);
		}
	}

}
