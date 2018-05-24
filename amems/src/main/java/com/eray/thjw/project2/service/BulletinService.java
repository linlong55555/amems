package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Bulletin;
import com.eray.thjw.project2.po.DistributionDepartment;
/**
 * 
 *@author 岳彬彬
 *@Description 技术通告Serviece
 *
 */
public interface BulletinService {
	/**
	 * @CreateBy 岳彬彬
	 * @Description 新增一条记录
	 * @param record
	 * @return
	 * @throws Exception
	 */
	String insertSelective(Bulletin record) throws Exception;

	/**
	 * @CreateBy 岳彬彬
	 * @Description 更新一条记录
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	String updateRecord(Bulletin record) throws BusinessException;

	/**
	 * @CreateBy 岳彬彬
	 * @Description 获取技术通告列表
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> selectBulletinList(Bulletin record) throws BusinessException;

	/**
	 * @CreateBy 岳彬彬
	 * @description 审核审批技术通告
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	String getReviewAndApproveRecord(Bulletin record) throws BusinessException;

	/**
	 * @CreateBy 岳彬彬
	 * @description 根据id作废技术通告
	 * @param id
	 * @throws BusinessException 
	 */
	void deleteRecord(Bulletin record) throws BusinessException;
	
	/**
	 * @CreateBy 岳彬彬
	 * @Description 通告传阅列表
	 * @CreateTime 2017年8月12日 上午11:43:23
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	Map<String, Object> getCirlationList(Bulletin record) throws BusinessException;
	/**
	 * 
	 * @Description 获取分发部门
	 * @CreateTime 2017年8月22日 上午9:32:28
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	List<DistributionDepartment> getDistributionDepartment(String id);
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月22日 上午9:32:44
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException 
	 */
	String updateBatchAudit(List<String> idList,String yj) throws BusinessException;
	/**
	 * 
	 * @Description 批量新增
	 * @CreateTime 2017年8月22日 上午9:32:55
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException 
	 */
	String updateBatchApprove(List<String> idList,String yj) throws BusinessException;
	/**
	 * 
	 * @Description 根据id获取技术通告数据
	 * @CreateTime 2017年8月25日 上午11:32:10
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	Bulletin getRecord(String id) throws BusinessException;
	/**
	 * 
	 * @Description 圈阅
	 * @CreateTime 2017年8月25日 上午11:32:23
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws Exception
	 */
	void view4IsCirculuation(String id) throws Exception;
	
	/**
	 * @Description 查询历史版本
	 * @CreateTime 2018年2月23日 下午3:04:52
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	List<Bulletin> queryHistoryList(String id);
	
	/**
	 * @Description 改版维护提示
	 * @CreateTime 2018年2月24日 上午10:31:34
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	String doRevision(Bulletin record) throws Exception;
}
