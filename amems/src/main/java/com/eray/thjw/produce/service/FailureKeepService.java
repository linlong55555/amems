package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.po.FlightSheet;

/**
 * 
 * @Description 故障保留service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface FailureKeepService {
	
	/**
	 * @Description 故障保留分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(FailureKeep failureKeep)throws BusinessException ;
	
	/**
	 * @Description 保存故障保留
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 故障保留id
	 * @throws BusinessException
	 */
	public String save(FailureKeep failureKeep)throws BusinessException;
	
	/**
	 * @Description 故障保留删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 故障保留id
	 * @throws BusinessException
	 */
	public void delete(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description 根据故障保留id查询故障保留信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	public FailureKeep getInfoById(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description 修改保存故障保留
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 故障保留id
	 * @throws BusinessException
	 */
	public String update(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description 批准故障保留单
	 * @CreateTime 2017年9月28日 上午11:02:08
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @return
	 */
	public String approval(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description  再次保留故障保留
	 * @CreateTime 2017年9月28日 下午4:51:25
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留对象
	 * @return 故障保留id
	 */
	public String saveagainkeep(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description 关闭故障保留
	 * @CreateTime 2017年9月29日 上午10:07:23
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留对象
	 * @throws BusinessException
	 */
	public void updateEndModal(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description 完成故障保留
	 * @CreateTime 2017年9月29日 上午11:42:15
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	public void updategConfirm(FailureKeep failureKeep)throws BusinessException;
	
	/**
	 * @Description 飞行记录本查询故障保留单
	 * @CreateTime 2017年10月24日 下午2:08:13
	 * @CreateBy 韩武
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	List<FailureKeep> queryListByFLB(FlightSheet sheet)throws BusinessException;

	/**
	 * 
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:57:23
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	public String updatefilesDownSub(FailureKeep failureKeep)throws BusinessException;

	/**
	 * 
	 * @Description 故障保留列表分页数据 
	 * @CreateTime 2017年10月31日 上午11:14:30
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryMonitorAllPageList(FailureKeep failureKeep)throws BusinessException;

	/**
	 * @Description 获取飞行时间、飞行循环默认值
	 * @CreateTime 2018-1-29 下午5:10:33
	 * @CreateBy 雷伟
	 * @param failureKeep
	 * @return
	 */
	public List<AircraftinfoStatus> getDefaultFHFC(FailureKeep failureKeep);
	
}
