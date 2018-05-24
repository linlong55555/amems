package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.MeasurementTools;
import com.eray.thjw.material2.po.MeasurementToolsDetails;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 计量工具service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface MeasurementToolsDetailsService {
	
	/**
	 * @Description 计量工具分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(MeasurementToolsDetails measurementToolsDetails)throws BusinessException ;
	
	/**
	 * @Description 保存计量工具
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	public String save(MeasurementToolsDetails measurementToolsDetails)throws BusinessException;
	
	/**
	 * @Description 计量工具删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	public void delete(MeasurementToolsDetails measurementToolsDetails)throws BusinessException;

	/**
	 * 
	 * @Description 根据计量工具id查询计量工具信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return
	 * @throws BusinessException
	 */
	public MeasurementToolsDetails getInfoById(MeasurementToolsDetails measurementToolsDetails)throws BusinessException;

	/**
	 * 
	 * @Description 修改保存计量工具
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	public String update(MeasurementToolsDetails measurementToolsDetails)throws BusinessException;

	/**
	 * 
	 * @Description 新增多个b_w_001
	 * @CreateTime 2018年2月9日 下午1:30:36
	 * @CreateBy 林龙
	 * @param measurementToolsDetails
	 * @param user
	 * @param uuid
	 * @throws BusinessException
	 */
	public void insertList(List<MeasurementToolsDetails> measurementToolsDetails, User user,String uuid)throws BusinessException;

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018年2月12日 上午10:09:57
	 * @CreateBy 林龙
	 * @param measurementTools
	 * @return
	 * @throws BusinessException
	 */
	public String update(MeasurementTools measurementTools)throws BusinessException;

	/**
	 * 
	 * @Description 查询校验历史列表
	 * @CreateTime 2018年3月7日 下午2:08:41
	 * @CreateBy 林龙
	 * @param measurementToolsDetails
	 * @return
	 * @throws BusinessException
	 */
	public List<MeasurementToolsDetails> queryAllLogList(MeasurementToolsDetails measurementToolsDetails)throws BusinessException;
	
}
