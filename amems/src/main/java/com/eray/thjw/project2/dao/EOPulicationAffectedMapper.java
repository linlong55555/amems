package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EOPulicationAffected;


/**
 * @Description EO-受影响出版物 Mapper
 * @CreateTime 2017-8-22 上午9:39:16
 * @CreateBy 雷伟
 */
public interface EOPulicationAffectedMapper {
	
	/**
	 * @Description 新增 EO-受影响出版物
	 * @CreateTime 2017-8-22 上午10:12:13
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 */
	int insert(EOPulicationAffected eoPulicationAffected);
	
	/**
	 * @Description 修改 EO-受影响出版物
	 * @CreateTime 2017-8-22 上午10:12:17
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 */
	int updateByPrimaryKeySelective(EOPulicationAffected eoPulicationAffected);

	/**
	 * @Description 批量新增 EO-受影响出版物
	 * @CreateTime 2017-8-23 上午9:49:44
	 * @CreateBy 雷伟
	 * @param syxcbwList
	 */
	void insert4Batch(List<EOPulicationAffected> syxcbwList);

	/**
	 * @Description 查询EO受影响出版物
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoPulicationAffected EO受影响出版物
	 * @return
	 * @throws BusinessException
	 */
	List<EOPulicationAffected> queryAllList(EOPulicationAffected eoPulicationAffected);

	/**
	 * @Description 根据EO主表ID删记录
	 * @CreateTime 2017-8-25 下午6:15:42
	 * @CreateBy 雷伟
	 * @param eoId EO主表ID
	 */
	void deleteByMainid(String eoId);
}