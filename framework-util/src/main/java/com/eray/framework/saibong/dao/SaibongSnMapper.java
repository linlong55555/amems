package com.eray.framework.saibong.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.framework.saibong.po.SaibongSn;

/**
 * @Description 采番流水mapper
 * @CreateTime 2018-1-10 下午4:58:18
 * @CreateBy 刘兵
 */
public interface SaibongSnMapper {

    int insertSelective(SaibongSn record);

    /**
     * @Description 修改当前流水号
     * @CreateTime 2018-1-11 上午9:35:47
     * @CreateBy 刘兵
     * @param record 采番流水
     * @return int
     */
    int updateDqlsh(SaibongSn record);
    
    /**
     * @Description 查询采番流水
     * @CreateTime 2018-1-10 下午4:00:52
     * @CreateBy 刘兵
     * @param dprtcode 机构代码
     * @param cfkey 采番key
     * @param djh djh
     * @return SaibongSn 采番流水
     */
    SaibongSn getSaibongSnByDprtAndKeyAndDjh(@Param("dprtcode")String dprtcode, @Param("cfkey")String cfkey, @Param("djh")String djh);
    
    
    /**
     * @Description 项目编号采番流水号
     * @CreateTime 2018-1-25 下午3:22:53
     * @CreateBy 甘清
     * @param dprtcode 机构码
     * @param cfkey 采番KEY
     * @return SaibongSn 采番流水
     */
    SaibongSn getSaibongSn4ProjectCode(@Param("dprtcode")String dprtcode, @Param("cfkey")String cfkey);
    
    /**
     * @Description 更新项目采番流水号
     * @CreateTime 2018-1-25 下午4:05:11
     * @CreateBy 甘清
     * @param record 采番值
     */
    void updateSaibongSn4ProjectCode(SaibongSn record);

}