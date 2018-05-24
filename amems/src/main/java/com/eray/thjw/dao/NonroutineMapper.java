package com.eray.thjw.dao;

import com.eray.thjw.po.Nonroutine;

public interface NonroutineMapper {

    int insertSelective(Nonroutine nonroutine);                                 //增加非例行工单
    
    int insertSelective2(Nonroutine nonroutine);                                 //增加EO工单

    void updateByPrimaryKeySelective(Nonroutine nonroutine);
    
     int updateByPrimaryKey(Nonroutine record);           //   审核工单
     
     int updateByPrimaryKey2(Nonroutine record);           //   审批工单
     
     int updateByPrimaryKey3(Nonroutine record);           //   指定结束
     
     int updateByPrimaryKey4(Nonroutine record);           //   作废工单

    /**
     * 按条件更新非例行工单
     * @param record
     */
	void updateByGdgcidSelective(Nonroutine record);
	
	public Nonroutine selectByPrimaryKey(Nonroutine nonroutine);             //查询非例行工单的信息
	
	public Nonroutine selectByEOWork(Nonroutine nonroutine);        //根据EO工单实体查出工单信息 
	
	public Nonroutine selectByDingJianWork(Nonroutine nonroutine);        //根据定检工单实体查出工单信息
	
	/**
	 * 指定结束非例行工单
	 * @param nonroutine
	 */
	public void doEnd(Nonroutine nonroutine);
	
	
}