package com.eray.thjw.project2.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Technical;


/**
 * 
 * @Description 技术评估单-附加信息Service
 * @CreateTime 2017年8月17日 下午5:22:39
 * @CreateBy 林龙
 */
public interface TechnicalAttachedService {

	/**
	 * 
	 * @param technical 
	 * @Description 新增技术评估单-附加信息
	 * @CreateTime 2017年8月17日 下午5:31:40
	 * @CreateBy 林龙
	 * @param technicalAttached
	 * @param mainid 评估单主表id
	 * @param czls 评估单流水号
	 * @throws BusinessException
	 */
	public void insertTechnicalAttached(Technical technical, String mainid,
			String czls)throws BusinessException;

	/**
	 * 
	 * @Description 修改技术评估单-附加信息
	 * @CreateTime 2017年8月17日 下午5:31:40
	 * @CreateBy 林龙
	 * @param technicalAttached
	 * @param mainid 评估单主表id
	 * @param czls 评估单流水号
	 * @throws BusinessException
	 */
	public void updateTechnicalAttached(Technical technical, String id, String czls)throws BusinessException;
	
	/**
	 * 
	 * @Description  根据mainid 技术评估单id查询 技术评估单附表
	 * @CreateTime 2017年8月22日 上午9:57:30
	 * @CreateBy 林龙
	 * @param mainid 技术评估单id
	 * @return 数量
	 * @throws BusinessException
	 */
	public int selectByMainidCount(String mainid)throws BusinessException;

	/**
	 * 
	 * @Description 根据mainid 技术评估单id删除 技术评估单附表
	 * @CreateTime 2017年8月24日 下午3:23:44
	 * @CreateBy 林龙
	 * @param mainid  技术文件评估单id
	 * @param czls 	流水号
	 */
	public void deleteTechnicalAttached(String mainid, String czls);

	



}
