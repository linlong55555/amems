package com.eray.thjw.project2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.WorkCard;


/**
 * @Description EO Mapper
 * @CreateTime 2017-8-22 上午9:39:16
 * @CreateBy 雷伟
 */
public interface EngineeringOrderMapper {

	/**
	 * @Description EO主列表查询
	 * @CreateTime 2017-8-22 下午9:48:50
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return
	 */
	List<EngineeringOrder> queryAllPageList(EngineeringOrder engineeringOrder);
	
	/**
	 * @Description 新增EO主表数据
	 * @CreateTime 2017-8-22 上午10:12:54
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return
	 */
	int insert(EngineeringOrder engineeringOrder);
	
	/**
	 * @Description 修改EO主表数据
	 * @CreateTime 2017-8-22 上午10:13:18
	 * @CreateBy 雷伟
	 * @param engineeringOrder EO
	 * @return
	 */
	int updateByPrimaryKeySelective(EngineeringOrder engineeringOrder);
	
	/**
	 * @Description 查询维修项目获取可关联EO列表
	 * @CreateTime 2017年8月23日 下午3:43:31
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
	List<EngineeringOrder> queryAssociateList(MaintenanceScheme scheme);

	/**
	 * @Description 根据EOId查询EO及相关信息
	 * @CreateTime 2017-8-24 上午11:02:03
	 * @CreateBy 雷伟
	 * @param id paramMap 参数
	 * @return EO信息
	 */
	EngineeringOrder selectById(HashMap<String, String> paramMap);

	/**
	 * @Description 根据参数查询EO及相关信息
	 * @CreateTime 2017-8-24 上午11:02:03
	 * @CreateBy 雷伟
	 * @param id paramMap 参数
	 * @return EO信息
	 */
	List<EngineeringOrder> selectByParam(HashMap<String, Object> paramMap);

	/**
	 * @Description 根据主键ID查找EO信息
	 * @CreateTime 2017-8-25 下午9:37:37
	 * @CreateBy 雷伟
	 * @param id 主键ID
	 * @return
	 */
	EngineeringOrder selectByPrimaryKey(String id);

	/**
	 * @Description 根据EO编号,查最大版本编号
	 * @CreateTime 2017-8-26 上午11:36:47
	 * @CreateBy 雷伟
	 * @param paramMap 查询参数
	 * @return
	 */
	Integer selectMaxbbByParam(HashMap<String, Object> paramMap);

	/**
	 * @Description  根据EOId查询当前工卡的历史版本集合
	 * @CreateTime 2017-8-31 上午9:06:37
	 * @CreateBy 雷伟
	 * @param id EOID
	 * @return
	 * @throws BusinessException
	 */
	List<WorkCard> queryHistoryListById(String id);
	
	/**
	 * @Description 删除
	 * @CreateTime 2017-9-13 下午5:33:11
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * @Description EO执行对象
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	List<Map<String, Object>> getEOExecutionObjs(String id);
	
	
	/**
	 * @Description 根据EO编号查询eo信息
	 * @CreateTime 2017年12月12日 下午2:21:37
	 * @CreateBy 刘邓
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	EngineeringOrder selectByEobh(EngineeringOrder engineeringOrder);
	
}