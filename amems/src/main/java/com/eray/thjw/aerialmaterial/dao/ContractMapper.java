package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Contract;

public interface ContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contract record);

    int insertSelective(Contract record);

    Contract selectByPrimaryKey(String id);
    
    void cancel(String id);

    int updateByPrimaryKeySelective(Contract record);

    int updateByPrimaryKey(Contract record);
    
    /**
	 * @author liub
	 * @description  根据id查询合同信息(制单人、指定结束人、合同费用)
	 * @param id
	 * @return Contract
	 * @develop date 2016.12.09
	 */
	public Contract selectById(String id);
    
    /**
	 * @author liub
	 * @description 根据条件修改合同状态为完成 
	 * @param id
	 * @return int
	 * @develop date 2016.11.10
	 */
	public int updateFinish(String id);
    
    /**
	 * @author liub
	 * @description  根据查询条件分页查询合同信息
	 * @param record
	 * @return List<Contract>
	 * @develop date 2016.11.08
	 */
	public List<Contract> queryAllPageList(Contract record);
		
	/**
	 * 模态框查询合同数据
	 * @param record
	 * @return
	 */
	public List<Contract> queryPageInModal(Contract record);
}