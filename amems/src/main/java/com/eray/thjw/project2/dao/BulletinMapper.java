package com.eray.thjw.project2.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.project2.po.Bulletin;

/**
 * 
 * @Description 技术通告
 * @CreateTime 2017年8月14日 下午7:13:18
 * @CreateBy 岳彬彬
 */
public interface BulletinMapper {
	/**
	 * @CreateBy 岳彬彬
	 * @Description 根据id删除记录
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 新增一条记录
	 * @param record
	 * @return
	 */
	int insert(Bulletin record);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 新增一条记录
	 * @param record
	 * @return
	 */
	int insertSelective(Bulletin record);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 根据id查询技术通告
	 * @param id
	 * @return
	 */
	Bulletin selectByPrimaryKey(String id);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 更新一条记录
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Bulletin record);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 更新一条记录
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(Bulletin record);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 获取技术通告列表
	 * @param record
	 * @return
	 */
	List<Bulletin> selectBulletinList(Bulletin record);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 验证技术通告编号+版本 唯一
	 * @param record
	 * @return
	 */
	int getCount4Validation(Bulletin record);

	/**
	 * @CreateBy 岳彬彬
	 * @Description 通告传阅列表
	 * @CreateTime 2017年8月12日 上午11:41:26
	 * @param record
	 * @return
	 */
	List<Bulletin> getCirlationList(Bulletin record);

	/**
	 * 
	 * @Description 根据id获取当前状态
	 * @CreateTime 2017年8月15日 上午10:21:58
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	Integer getCurrentZt4Validation(String id);

	/**
	 * 
	 * @Description 根据id集合获取技术通告
	 * @CreateTime 2017年8月21日 下午7:46:01
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @return
	 */
	List<Bulletin> queryByIdList(List<String> idList);

	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月21日 下午7:45:49
	 * @CreateBy 岳彬彬
	 * @param map
	 * @return
	 */
	int updateBatchAudit(Map<String, Object> map);

	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年8月21日 下午7:46:48
	 * @CreateBy 岳彬彬
	 * @param map
	 * @return
	 */
	int updateBatchApprove(Map<String, Object> map);
	/**
	 * 
	 * @Description 审核
	 * @CreateTime 2017年8月23日 下午2:21:31
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updateShByPrimaryKey(Bulletin record);
	/**
	 * 
	 * @Description 审批
	 * @CreateTime 2017年8月23日 下午2:21:39
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updatePzByPrimaryKey(Bulletin record);
	
	/**
	 * @Description 查询历史版本
	 * @CreateTime 2018年2月23日 下午3:05:44
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	List<Bulletin> queryHistoryList(String id);
	
	/**
	 * @Description 更新上个版本的版本状态
	 * @CreateTime 2018年2月24日 上午10:43:19
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updatePrivousVersionStatus(Bulletin record);
	
	/**
	 * @Description 批量更新上个版本的版本状态为老版本
	 * @CreateTime 2018年2月26日 上午9:30:19
	 * @CreateBy 韩武
	 * @param map
	 * @return
	 */
	int updateBatchVersionStatus(Map<String, Object> map);
}