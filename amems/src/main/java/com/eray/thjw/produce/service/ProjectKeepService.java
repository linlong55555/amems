package com.eray.thjw.produce.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.po.ProjectKeep;

/**
 * 
 * @Description 项目保留service
 * @CreateTime 2017年10月10日 下午2:53:03
 * @CreateBy 林龙
 */
public interface ProjectKeepService {
	
	/**
	 * @Description 项目保留分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(ProjectKeep projectKeep)throws BusinessException ;
	
	/**
	 * @Description 保存项目保留
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return 项目保留id
	 * @throws BusinessException
	 */
	public String save(ProjectKeep projectKeep)throws BusinessException;
	
	/**
	 * @Description 项目保留删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return 项目保留id
	 * @throws BusinessException
	 */
	public void delete(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 根据项目保留id查询项目保留信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return
	 * @throws BusinessException
	 */
	public ProjectKeep getInfoById(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 修改保存项目保留
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return 项目保留id
	 * @throws BusinessException
	 */
	public String update(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 关闭项目保留
	 * @CreateTime 2017年9月29日 上午10:07:23
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留对象
	 * @throws BusinessException
	 */
	public void updateEndModal(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 完成项目保留
	 * @CreateTime 2017年9月29日 上午11:42:15
	 * @CreateBy 林龙
	 * @param ProjectKeep
	 * @throws BusinessException
	 */
	public void updategConfirm(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 重新执行
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	public String updateReExecute(ProjectKeep projectKeep)throws BusinessException;
	
	/**
	 * 
	 * @Description 查询工单信息
	 * @CreateTime 2017年10月17日 上午11:24:39
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return 
	 * @throws BusinessException
	 */
	public ProjectKeep selectGetBygdId(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 上传附件
	 * @CreateTime 2017年10月25日 下午3:22:32
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	public String updatefilesDownSub(ProjectKeep projectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 项目保留监控列表加载
	 * @CreateTime 2017年10月31日 下午1:43:48
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryMonitorAllPageList(ProjectKeep projectKeep)throws BusinessException;
	
}
