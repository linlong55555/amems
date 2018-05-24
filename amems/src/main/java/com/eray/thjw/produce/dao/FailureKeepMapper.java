package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.po.FlightSheet;

public interface FailureKeepMapper {
    int deleteByPrimaryKey(String id);

    int insert(FailureKeep record);

    int insertSelective(FailureKeep record);

    FailureKeep selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FailureKeep record);

    int updateByPrimaryKey(FailureKeep record);

    /**
	 * @Description 技术评估单分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	List<FailureKeep> queryAllPageList(FailureKeep param);

	int queryCount(FailureKeep fai);

	int getCount4Validation(FailureKeep failureKeep);

	FailureKeep getInfoById(FailureKeep failureKeep);

	int getCurrentZt4Validation(String id);

	int getCurrentZcblbs4Validation(String id);
	
	/**
	 * @Description 飞行记录本查询故障保留单
	 * @CreateTime 2017年10月24日 下午2:10:05
	 * @CreateBy 韩武
	 * @param failureKeep
	 * @return
	 */
	List<FailureKeep> queryListByFLB(FlightSheet sheet);

	List<FailureKeep> queryMonitorAllPageList(FailureKeep failureKeep);
	
	/**
	 * @Description 根据id查询故障保留单详情
	 * @CreateTime 2018年2月2日 下午4:32:02
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	FailureKeep queryDetailById(String id);
}