package com.eray.thjw.aerialmaterial.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.ComponentHistory;
import com.eray.thjw.produce.po.InstallationListEditable;

/**
 * @Description B_H2_025 部件数据更新 DAO
 * @CreateTime 2017年10月20日 上午10:37:06
 * @CreateBy 徐勇
 */
public interface ComponentHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComponentHistory record);

    int insertSelective(ComponentHistory record);

    ComponentHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComponentHistory record);

    int updateByPrimaryKey(ComponentHistory record);
    
    /**
     * @Description 根据装机清单新增部件数据
     * @CreateTime 2017年10月20日 上午10:41:14
     * @CreateBy 徐勇
     * @return
     */
    int insertByInstallList(InstallationListEditable installationListEditable);
    
    /**
     * @Description 根据装机清单更新部件数据
     * @CreateTime 2017年10月20日 上午10:41:43
     * @CreateBy 徐勇
     * @return
     */
    int updateByInstallList(InstallationListEditable installationListEditable);
    
    /**
     * @Description 根据装机清单id删除
     * @CreateTime 2017年11月25日 下午2:11:05
     * @CreateBy 徐勇
     * @param zjqdid 根据装要清单id删除
     * @return
     */
    int deleteByZjqdid(@Param("zjqdid")String zjqdid);

	ComponentHistory selectByPrimaryComponentHistory(ComponentHistory componentHistory);
	/**
	 * 
	 * @Description 根据装机清单id获取数据
	 * @CreateTime 2018-3-21 上午11:56:56
	 * @CreateBy 孙霁
	 * @param zjqdid
	 * @return
	 */
	ComponentHistory selectByZjqdid(String zjqdid);
    
}