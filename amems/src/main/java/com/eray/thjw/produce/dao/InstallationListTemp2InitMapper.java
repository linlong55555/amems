package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.InstallationListTemp2Init;

/**
 * @Description b_s2_00201 装机清单临时区-部件初始化
 * @CreateTime 2017年10月9日 上午10:45:22
 * @CreateBy 徐勇
 */
public interface InstallationListTemp2InitMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstallationListTemp2Init record);

    int insertSelective(InstallationListTemp2Init record);

    InstallationListTemp2Init selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstallationListTemp2Init record);

    int updateByPrimaryKey(InstallationListTemp2Init record);
    
    /**
     * @Description 根据mainid删除对应的初始化数据
     * @CreateTime 2017年9月28日 下午4:58:20
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
}