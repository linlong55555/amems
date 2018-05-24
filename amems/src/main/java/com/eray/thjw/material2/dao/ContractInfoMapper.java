package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.ContractInfo;

/**
 * @Description 合同明细mapper
 * @CreateTime 2018-3-8 下午2:08:08
 * @CreateBy 刘兵
 */
public interface ContractInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractInfo record);

    int insertSelective(ContractInfo record);

    ContractInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractInfo record);

    int updateByPrimaryKey(ContractInfo record);
    
    /**
     * @Description 批量新增合同明细
     * @CreateTime 2017-8-19 下午3:02:02
     * @CreateBy 刘兵
     * @param contractInfoList 合同明细集合
     * @return int
     */
    int insert4Batch(List<ContractInfo> contractInfoList);
    
    /**
     * @Description 批量删除合同明细
     * @CreateTime 2017-8-19 下午5:36:22
     * @CreateBy 刘兵
     * @param idList 合同明细id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 批量删除合同明细(逻辑删除)
     * @CreateTime 2017-8-19 下午5:36:22
     * @CreateBy 刘兵
     * @param idList 合同明细id集合
     * @return int
     */
    int cancel4Batch(List<String> idList);
    
    /**
	 * @Description 根据mainid删除合同明细
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 */
    int deleteByMainid(String mainid);
    
    /**
	 * @Description 根据条件查询合同明细列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param record 合同明细
	 * @return List<ContractInfo> 合同明细集合
	 */
    List<ContractInfo> queryAllList(ContractInfo record);
    
    /**
	 * @Description 根据mainid查询合同明细列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param mainid
	 * @return List<ContractInfo> 合同明细集合
	 */
    List<ContractInfo> queryByMainid(String mainid);
    
    /**
	 * 
	 * @Description 查询所有在途数量
	 * @CreateTime 2018-3-14 下午3:22:02
	 * @CreateBy 孙霁
	 * @param contractInfo
	 * @return
	 */
    List<ContractInfo> queryAll(ContractInfo record);
    
    /**
	 * @Description 根据查询条件分页查询合同明细信息(弹窗)
	 * @CreateTime 2018-3-15 上午11:48:48
	 * @CreateBy 刘兵
	 * @param contractInfo 合同明细
	 * @return List<ContractInfo> 合同明细集合
	 */
    List<ContractInfo> queryWinAllPageList(ContractInfo record);
    
    /**
	 * @Description 根据合同id查询合同明细(带库存)
	 * @CreateTime 2018-3-15 上午10:44:33
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return List<ContractInfo> 合同明细集合
	 */
    List<ContractInfo> queryDetailByMainid(String mainid);
    
    /**
	 * @Description 根据需求清单id集合查询合同明细 
	 * @CreateTime 2018-4-2 下午2:46:51
	 * @CreateBy 刘兵
	 * @param xqqdIdList
	 * @return List<ContractInfo>
	 */
    List<ContractInfo> queryByXqqdIdList(List<String> xqqdIdList);

	List<ContractInfo> queryContractdetailsList(ContractInfo contractInfo);
    
}