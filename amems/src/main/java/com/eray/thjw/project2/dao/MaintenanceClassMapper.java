package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.MaintenanceClass;

public interface MaintenanceClassMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceClass record);

    int insertSelective(MaintenanceClass record);

    MaintenanceClass selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceClass record);

    int updateByPrimaryKey(MaintenanceClass record);
    
    /**
     * 
     * @Description 根据飞机机型查询维修方案大类(弹窗)
     * @CreateTime 2017-8-17 上午11:25:07
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<MaintenanceClass> queryWinByFjjx(MaintenanceClass record);
    /**
     * 
     * @Description 根据飞机机型查询维修方案大类(分页)
     * @CreateTime 2017-8-17 上午11:25:18
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    List<MaintenanceClass> queryAll(MaintenanceClass record);
    /**
     * 
     * @Description  唯一验证
     * @CreateTime 2017-8-17 上午11:25:25
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    int findBydlbh(MaintenanceClass record);
    
    
    /**
     *@Description  根据机构代码获取最大项次
     */ 
    int findMaxXc(String dprtcode);
    
    /**
     *@Description  批量更新
     */
    int batchMerge(List<MaintenanceClass> list);
    
    
    /**
     * @Description      获取当前组织结构下所有的维修方案大类
     * @param dprtcode  组织机构代码
     */
    List<MaintenanceClass> queryByDprtcode(String dprtcode);

    /**
     * @Description 获取维修大类，导入专用
     * @CreateTime 2017-12-13 下午3:14:36
     * @CreateBy 雷伟
     * @param dprtcode
     * @param jx
     * @param zt
     * @return
     */
	List<MaintenanceClass> getWxdlByParam(@Param("dprtcode")String dprtcode, @Param("jx")String jx);
}