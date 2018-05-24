package com.eray.thjw.produce.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.DefectKeep;

/**
 * 
 * @Description 缺陷保留service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface DefectKeepService {
	
	/**
	 * @Description 缺陷保留分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(DefectKeep defectKeep)throws BusinessException ;
	
	/**
	 * @Description 保存缺陷保留
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 缺陷保留id
	 * @throws BusinessException
	 */
	public String save(DefectKeep defectKeep)throws BusinessException;
	
	/**
	 * @Description 缺陷保留删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 缺陷保留id
	 * @throws BusinessException
	 */
	public void delete(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 根据缺陷保留id查询缺陷保留信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	public DefectKeep getInfoById(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 修改保存缺陷保留
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 缺陷保留id
	 * @throws BusinessException
	 */
	public String update(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 批准缺陷保留单
	 * @CreateTime 2017年9月28日 上午11:02:08
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @return
	 */
	public String approval(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description  再次保留缺陷保留
	 * @CreateTime 2017年9月28日 下午4:51:25
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留对象
	 * @return 缺陷保留id
	 */
	public String saveagainkeep(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 关闭缺陷保留
	 * @CreateTime 2017年9月29日 上午10:07:23
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留对象
	 * @return 
	 * @throws BusinessException
	 */
	public String updateEndModal(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 完成缺陷保留
	 * @CreateTime 2017年9月29日 上午11:42:15
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	public String updategConfirm(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 上传附件
	 * @CreateTime 2017年10月25日 下午3:29:01
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @return
	 * @throws BusinessException
	 */
	public String updatefilesDownSub(DefectKeep defectKeep)throws BusinessException;

	/**
	 * 
	 * @Description 缺陷保留监控列表分页数据
	 * @CreateTime 2017年10月31日 下午1:47:05
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryMonitorAllPageList(DefectKeep defectKeep)throws BusinessException;
	
}
