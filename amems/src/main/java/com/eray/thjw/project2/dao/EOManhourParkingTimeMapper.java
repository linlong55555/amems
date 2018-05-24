package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EOManhourParkingTime;


/**
 * @Description EO-工时/停场时间 Mapper
 * @CreateTime 2017-8-22 上午9:39:16
 * @CreateBy 雷伟
 */
public interface EOManhourParkingTimeMapper {
	
	/**
	 * @Description 新增EO工时/停场时间
	 * @CreateTime 2017-8-22 上午10:12:13
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 */
	int insert(EOManhourParkingTime eoManhourParkingTime);
	
	/**
	 * @Description 修改EO工时/停场时间
	 * @CreateTime 2017-8-22 上午10:12:17
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime 数据集合
	 * @return
	 */
	int updateByPrimaryKeySelective(EOManhourParkingTime eoManhourParkingTime);

	/**
	 * @Description 批量新增EO工时/停场时间
	 * @CreateTime 2017-8-23 上午10:21:05
	 * @CreateBy 雷伟
	 * @param gstcshList 数据集合
	 */
	void insert4Batch(List<EOManhourParkingTime> gstcshList);

	/**
	 * @Description 查询EO工时/停场时间
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 * @throws BusinessException
	 */
	List<EOManhourParkingTime> queryAllList(EOManhourParkingTime eoManhourParkingTime);

	/**
	 * @Description 根据EO主表ID删记录
	 * @CreateTime 2017-8-25 下午6:15:42
	 * @CreateBy 雷伟
	 * @param eoId EO主表ID
	 */
	void deleteByMainid(String eoId);
}