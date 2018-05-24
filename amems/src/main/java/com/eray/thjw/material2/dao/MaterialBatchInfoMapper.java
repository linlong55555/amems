package com.eray.thjw.material2.dao;

import com.eray.thjw.material2.po.MaterialBatchInfo;

/**
 * @Description 物料批次信息mapper
 * @CreateTime 2018年3月23日 下午3:48:27
 * @CreateBy 韩武
 */
public interface MaterialBatchInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaterialBatchInfo record);

    int insertSelective(MaterialBatchInfo record);

    MaterialBatchInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialBatchInfo record);

    int updateByPrimaryKey(MaterialBatchInfo record);
    
    /**
     * @Description 根据业务主键更新
     * @CreateTime 2018年3月23日 下午3:48:16
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByBusinessKey(MaterialBatchInfo record);
}