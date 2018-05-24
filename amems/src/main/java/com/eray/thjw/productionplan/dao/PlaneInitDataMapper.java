package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.PlaneInitData;

public interface PlaneInitDataMapper {
    int insert(PlaneInitData record) throws RuntimeException;

    int insertSelective(PlaneInitData record) throws RuntimeException;
    
    int batchInsert(List<PlaneInitData> planeInitDatas) throws RuntimeException;
    
    void deleteByFjzch(PlaneInitData record) throws RuntimeException;
    
    PlaneInitData selectByKey(PlaneInitData pd);       //  根据给出条件查询飞机数据
    
    /**
	 * 根据飞机注册号查询对应的初始化数据
	 */
    Map<String, Object> findByFjzch(PlaneData pd);

	void updateByPrimaryKeySelective(PlaneInitData planeInitData);

	int queryCount(PlaneInitData planeInitData);
}