package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.InstallationListTemp;

/**
 * @Description b_s2_002 装机清单临时区
 * @CreateTime 2017年10月9日 上午10:45:02
 * @CreateBy 徐勇
 */
public interface InstallationListTempMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstallationListTemp record);

    int insertSelective(InstallationListTemp record);

    InstallationListTemp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstallationListTemp record);

    int updateByPrimaryKey(InstallationListTemp record);
    
    List<InstallationListTemp> queryCxList(InstallationListTemp record);
    
    List<InstallationListTemp> queryZsList(InstallationListTemp record);
    
    /**
     * @Description 更新装机清单临时区 - 飞行记录本
     * @CreateTime 2017年10月26日 下午3:40:08
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByFLB(InstallationListTemp record);
}