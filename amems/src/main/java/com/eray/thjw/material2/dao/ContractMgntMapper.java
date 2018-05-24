package com.eray.thjw.material2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.material2.po.ContractMgnt;

/**
 * @Description 合同管理mapper
 * @CreateTime 2018-3-8 下午2:07:22
 * @CreateBy 刘兵
 */
public interface ContractMgntMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractMgnt record);

    int insertSelective(ContractMgnt record);

    ContractMgnt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractMgnt record);
    
    int updateByPrimaryKey(ContractMgnt record);
    
    int updateZtById(@Param("zt")Integer zt, @Param("id")String id);
    
    /**
     * @Description 查询合同数,检查合同是否存在
     * @CreateTime 2018-3-12 下午3:50:06
     * @CreateBy 刘兵
     * @param hth 合同号
     * @param dprtcode 机构代码
     * @return int 合同数
     */
	int getCount4CheckExist(@Param("hth")String hth, @Param("dprtcode")String dprtcode);

    
    /**
     * @Description 合同列表查询
     * @CreateTime 2018-3-9 上午11:21:55
     * @CreateBy 刘兵
     * @param record 合同
     * @return List<ContractMgnt> 合同集合
     */
	List<ContractMgnt> queryAllPageList(ContractMgnt record);
	
	/**
	 * @Description 模态框中获取合同 
	 * @CreateTime 2018-4-3 上午11:48:28
	 * @CreateBy 刘兵
	 * @param record 合同
	 * @return List<ContractMgnt> 合同集合
	 */
	List<ContractMgnt> queryModelList(ContractMgnt record);
	
	/**
	 * @Description 根据合同id查询合同及用户信息
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @return ContractMgnt 合同
	 */
	ContractMgnt selectById(String id);
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-3-27 上午9:51:28
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @return ContractMgnt 合同
	 */
	ContractMgnt exportWord(String id);
	
	/**
	 * @Description 根据合同明细id查询
	 * @CreateTime 2018年5月21日 下午4:11:15
	 * @CreateBy 韩武
	 * @param childId
	 * @return
	 */
	ContractMgnt findByChildId(String childId);
}