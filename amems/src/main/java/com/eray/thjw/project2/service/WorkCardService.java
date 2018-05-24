package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.WorkCard;

/**
 * @Description 工卡接口
 * @CreateTime 2017-8-15 下午3:20:56
 * @CreateBy 刘兵
 */
public interface WorkCardService {
	
	/**
	 * @Description 新增工卡
	 * @CreateTime 2017-8-15 下午3:34:36
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	String save(WorkCard workCard) throws BusinessException;
	
	/**
	 * @Description 编辑工卡
	 * @CreateTime 2017-8-18 下午4:43:24
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	String update(WorkCard workCard) throws BusinessException;
	
	/**
	 * @Description 审核工卡
	 * @CreateTime 2017-8-21 下午1:44:03
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	String doAudit(WorkCard workCard) throws BusinessException;
	
	/**
	 * @Description 批准工卡
	 * @CreateTime 2017-8-21 下午1:44:03
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	String doApprove(WorkCard workCard) throws BusinessException;
	
	/**
	 * @Description 改版工卡
	 * @CreateTime 2017-8-22 下午7:05:57
	 * @CreateBy 刘兵
	 * @param workCard 工卡 
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	String modify(WorkCard workCard) throws BusinessException;
	
	/**
	 * @Description 删除工卡
	 * @CreateTime 2017-8-22 下午4:56:19
	 * @CreateBy 刘兵
	 * @param id 工卡id 
	 * @throws BusinessException
	 */
	void delete(String id) throws BusinessException;
	
	/**
	 * @Description 失效工卡
	 * @CreateTime 2017-8-22 下午4:56:19
	 * @CreateBy 刘兵
	 * @param id 工卡id 
	 * @throws BusinessException
	 */
	void doInvalid(String id) throws BusinessException;
	
	/**
	 * @Description 批量审核
	 * @CreateTime 2017-8-23 上午9:51:17
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @param yj 审核意见
	 * @return String 审核结果
	 * @throws BusinessException
	 */
	String updateBatchAudit(List<String> idList, String yj) throws BusinessException;
	
	/**
	 * @Description 批量批准
	 * @CreateTime 2017-8-23 上午9:56:40
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @param yj 批准意见
	 * @return String 批准结果
	 * @throws BusinessException
	 */
	String updateBatchApprove(List<String> idList, String yj) throws BusinessException;
	
	/**
	 * @Description 工卡分页列表查询
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	Map<String, Object> queryAllPageList(WorkCard workCard);
	
	/**
	 * @Description 工卡分页列表查询(弹窗需要的数据)
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	Map<String, Object> queryWinAllPageList(WorkCard workCard);
	
	/**
	 * @Description 根据工卡id查询工卡及用户信息
	 * @CreateTime 2017-8-17 下午5:23:17
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @return WorkCard 工卡
	 */
	WorkCard selectById(String id);
	
	/**
	 * @Description 查询指定工卡的版本集合
	 * @CreateTime 2017年8月24日 下午3:58:16
	 * @CreateBy 韩武
	 * @param workCard
	 * @return
	 */
	List<WorkCard> queryVersionList(WorkCard workCard);
	
	/**
	  * @Description 根据工卡id查询当前工卡的历史版本集合
	  * @CreateTime 2017-8-28 下午5:43:59
	  * @CreateBy 刘兵
	  * @param id 工卡id
	  * @return List<WorkCard> 工卡集合
	  */
	 List<WorkCard> queryHistoryListById(String id);
	 
	 /**
	  * @Description 工单135:获取工卡来源信息
	  * @CreateTime 2017-10-10 上午9:33:49
	  * @CreateBy 雷伟
	  * @param workCard 工卡
	  * @return Map<String, Object>
	  */
	 Map<String, Object> queryOriginatingCardList(WorkCard workCard);
	
	/**
	 * @Description 根据组织机构查询所有有效的工卡数据
	 * @CreateTime 2017年11月15日 下午2:06:06
	 * @CreateBy 胡黄驰
	 * @param String dprtcode
	 * @return List<WorkCard>
	 */
	public List<WorkCard> findByDprtcode(String dprtcode) throws BusinessException;
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<WorkCard>
	 */
	List<WorkCard> doExportExcel(WorkCard paramObj);
}
