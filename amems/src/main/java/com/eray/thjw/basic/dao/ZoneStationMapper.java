package com.eray.thjw.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.project2.po.CoverPlate;

public interface ZoneStationMapper {
    int deleteByPrimaryKey(String id);
    
    int getCount(ZoneStation record);

    int insert(ZoneStation record);

    int insertSelective(ZoneStation record);

    ZoneStation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ZoneStation record);

    int updateByPrimaryKey(ZoneStation record);

	List<ZoneStation> getZoneStationList(ZoneStation zoneStation);
	
	  /**
     * 
     * @Description 根据机型和组织机构查询数据
     * @CreateTime 2017-8-22 上午10:27:32
     * @CreateBy 孙霁
     * @param record
     * @return List<Position>
     */
    List<ZoneStation> selectByJx(ZoneStation record);
    
    /**
     * @Description 根据数值+机型+组织机构查询
     * @CreateTime 2017年10月13日 下午5:10:07
     * @CreateBy 韩武
     * @param record
     * @return
     */
    ZoneStation selectByBusinessKey(ZoneStation record);
    
    /**
     * 
     * @Description 导入添加或修改
     * @CreateTime 2017-11-30 下午2:03:58
     * @CreateBy 孙霁
     * @param list
     */
    void insertAndUpdate(List<ZoneStation> list);
    
    /**
     * 
     * @Description 修改
     * @CreateTime 2017-12-6 上午11:26:42
     * @CreateBy 孙霁
     * @param record
     */
    void updateById(ZoneStation record);

    /**
     * @Description 根据机构代码、机型、类型、状态=1、值
     * @CreateTime 2017-12-12 上午10:28:29
     * @CreateBy 雷伟
     * @param dprtcode 机构代码
     * @param jx 机型
     * @param lx 类型
     * @param zt 状态
     * @param sz 值
     * @return
     */
	ZoneStation getZoneStationByParam(@Param("dprtcode")String dprtcode, @Param("jx")String jx, @Param("lx")String lx, @Param("zt")String zt,@Param("sz")String sz);
}