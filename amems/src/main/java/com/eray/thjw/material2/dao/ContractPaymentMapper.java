package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.ContractPayment;

/**
 * @Description 合同收付款信息mapper
 * @CreateTime 2018-3-8 下午2:08:50
 * @CreateBy 刘兵
 */
public interface ContractPaymentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractPayment record);

    int insertSelective(ContractPayment record);

    ContractPayment selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(ContractPayment record);

    int updateByPrimaryKey(ContractPayment record);
    /**
	 * @Description 根据mainid删除合同付款
	 * @CreateTime 2018-3-15 上午10:27:36
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 */
    int deleteByMainid(String mainid);
    
    /**
	 * @Description 根据id查询合同付款(带明细)
	 * @CreateTime 2018-3-15 上午10:29:26
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return 合同付款
	 */
    ContractPayment selectById(String id);
    
    /**
	 * @Description 根据合同id查询合同付款列表
	 * @CreateTime 2018-3-15 上午10:29:26
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return 合同付款集合
	 */
    List<ContractPayment> selectByMainid(String mainid);
}