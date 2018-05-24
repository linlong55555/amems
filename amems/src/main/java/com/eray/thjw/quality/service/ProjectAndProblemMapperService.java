package com.eray.thjw.quality.service;

/**
 * 
 * @Description 审核项目单与审核问题单关系service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 岳彬彬
 */
public interface ProjectAndProblemMapperService {
	/**
	 * 
	 * @Description 新增审核项目单与审核问题单关系
	 * @CreateTime 2018年1月11日 上午10:09:17
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 * @param shxmdid
	 */
	void addRecord(String shwtdid,String shxmdid);
	/**
	 * 
	 * @Description 根据审核问题单id删除记录
	 * @CreateTime 2018年1月11日 下午2:32:19
	 * @CreateBy 岳彬彬
	 * @param shwtdid
	 */
	void deleteRecord(String shwtdid);
}
