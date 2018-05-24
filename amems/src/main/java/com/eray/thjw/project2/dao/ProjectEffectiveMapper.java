package com.eray.thjw.project2.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.MaintenanceScheme;

/**
 * @Description 维修方案生效区-维修项目关系Mapper
 * @CreateTime 2017年8月21日 下午7:24:06
 * @CreateBy 韩武
 */
public interface ProjectEffectiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProjectEffectiveMapper record);

    int insertSelective(ProjectEffectiveMapper record);

    ProjectEffectiveMapper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProjectEffectiveMapper record);

    int updateByPrimaryKey(ProjectEffectiveMapper record);
    
    /**
     * @Description 维修项目同步到生效区
     * @CreateTime 2017年8月21日 下午7:31:53
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    int synchronize(MaintenanceScheme scheme);
    
    /**
     * @Description 将维修方案生效区-维修项目关系工卡设为空
     * @CreateTime 2017-8-22 下午3:40:37
     * @CreateBy 刘兵
     * @param gkbh 工卡编号
     * @param jx 机型
     * @param dprtcode 机构代码
     * @return int
     */
    int updateGk2Null(@Param("gkbh")String gkbh, @Param("jx")String jx, @Param("dprtcode")String dprtcode);
    
    /**
     * @Description 修改维修方案生效区-维修项目关系工卡编号、工卡id
     * @CreateTime 2017-8-22 下午3:40:37
     * @CreateBy 刘兵
     * @param gkbh 工卡编号
     * @param gkid 工卡id
     * @param jx 机型
     * @param wxxmbh 维修项目编号
     * @param dprtcode 机构代码
     * @return int
     */
    int updateGk(@Param("gkbh")String gkbh, @Param("gkid")String gkid, @Param("jx")String jx, @Param("wxxmbh")String wxxmbh, @Param("dprtcode")String dprtcode);
}