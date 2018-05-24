package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.CheckTaskBill;

public interface CheckTaskBillMapper {
    int deleteByPrimaryKey(String id);

    int insert(CheckTaskBill record);

    int insertSelective(CheckTaskBill record);

    CheckTaskBill selectByPrimaryKey(String id);                                                       //查询单个定检任务单
    
    
    List<CheckTaskBill> selectCheckTaskBill(CheckTaskBill record);                    //分页查询定检任务单
	
	int updateByPrimaryKeySelective(CheckTaskBill record);               //指定结束定检任务单

    int updateByPrimaryKey(CheckTaskBill record);
    
    /**
	 * 1、新增b_g_014 定检任务主表
	 * @param record
	 * @return+
	 * 
	 * @throws Exception
	 */
	public void save(CheckTaskBill checkTaskBill) throws Exception;
	
	/**
	 * @author liub
	 * @description 检查定检任务主表是否存在数据
	 * @param Map<String, Object>
	 * @return
	 * @develop date 2016.09.26
	 */
	public List<CheckTaskBill> checkIsExist(Map<String, Object> paramMap) throws RuntimeException;

	CheckTaskBill queryKey(String xggdid, Integer rwlx)throws RuntimeException;

	void updateByPrimaryKeySelective1(CheckTaskBill checkTaskBill)throws RuntimeException;
}