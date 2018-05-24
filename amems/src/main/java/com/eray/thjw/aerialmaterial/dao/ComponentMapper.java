package com.eray.thjw.aerialmaterial.dao;

import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.produce.po.InstallationListEditable;

/**
 * @Description 部件mapper
 * @CreateTime 2017年10月9日 下午4:52:09
 * @CreateBy 韩武
 */
public interface ComponentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Component record);

    int insertSelective(Component record);

    Component selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Component record);

    int updateByPrimaryKey(Component record);
    
    /**
     * @Description 根据件号和序列号查找
     * @CreateTime 2017年10月9日 下午4:57:59
     * @CreateBy 韩武
     * @param record
     * @return
     */
    Component findByJhAndXlh(Component record);
    
    /**
     * @Description 将b_h_010部件信息 更新到最新的数据
     * @CreateTime 2017年10月20日 上午10:40:13
     * @CreateBy 徐勇
     * @param installationListEditable 装机清单编辑区数据
     * @return
     */
    int updateCompnent2Newest(InstallationListEditable installationListEditable);
    
    /**
     * @Description 根据装机清单新增 
     * @CreateTime 2017年10月20日 下午1:45:11
     * @CreateBy 徐勇
     * @param installationListEditable 装机清单编辑区数据
     * @return
     */
    int insertByInstallList(InstallationListEditable installationListEditable);
}