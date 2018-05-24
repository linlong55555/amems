package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlanTaskSnap;

/**
 * 计划任务快照Dao
 * @author zhuchao
 *
 */
public interface PlanTaskSnapMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlanTaskSnap record);

    int insertSelective(PlanTaskSnap record);

    PlanTaskSnap selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PlanTaskSnap record);

    int updateByPrimaryKey(PlanTaskSnap record);

    /**
     * 根据ids 查询快照列表
     * @param params
     * @return
     */
	List<PlanTaskSnap> queryList(Map<String, Object> params);

}