package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.ActType;

/**
 * 
 * @Description 机型Service
 * @CreateTime 2017年8月14日 上午10:17:57
 * @CreateBy 林龙
 */
public interface ActTypeService {

	/**
	 * @Description 机型分页列表查询
	 * @CreateTime 2017年8月14日 上午10:27:56
	 * @CreateBy 林龙
	 * @param actType
	 * @return 分页数据
	 */
	public Map<String, Object> queryAllPageList(ActType actType);

	/**
	 * @Description 机型保存
	 * @CreateTime 2017年8月14日 上午10:28:52
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	public String save(ActType actType)throws BusinessException;

	/**
	 * @Description 机型修改
	 * @CreateTime 2017年8月14日 上午10:29:01
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	public String update(ActType actType)throws BusinessException;

	/**
	 * @Description 注销机型
	 * @CreateTime 2017年8月14日 下午6:14:01
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	public String updatenote(ActType actType)throws BusinessException;

	/**
	 * @Description 启用机型
	 * @CreateTime 2017年8月14日 下午6:14:01
	 * @CreateBy 林龙
	 * @param actType
	 * @return 机型名称
	 */
	public String updateEnabled(ActType actType)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据组织机构查询机型
	 * @CreateTime 2017年11月8日 上午11:40:52
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<ActType> findByDprtcode(String dprtcode);

}
