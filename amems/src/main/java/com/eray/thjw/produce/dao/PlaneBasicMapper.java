package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.PlaneBasic;

public interface PlaneBasicMapper {
    int deleteByPrimaryKey(PlaneBasic record);

    int insert(PlaneBasic record);

    int insertSelective(PlaneBasic record);

    PlaneBasic selectByPrimaryKey(PlaneBasic record);

    int updateByPrimaryKeySelective(PlaneBasic record);

    int updateByPrimaryKey(PlaneBasic record);
    
    int selectByFjzchAndDprtcode(PlaneBasic record);
    /**
     * 
     * @Description 获取组织机构下所有有效的飞机
     * @CreateTime 2017年12月13日 下午2:50:50
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<PlaneBasic> getFjList(PlaneBasic record);
}