package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workpackage;

/**
 * 
 * @Description 工包service
 * @CreateTime 2017年9月23日 下午3:56:34
 * @CreateBy 岳彬彬
 */
public interface WorkpackageService {
	/**
	 * 
	 * @Description 新增工包
	 * @CreateTime 2017年9月23日 下午4:02:24
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String addRecord(Workpackage record) throws BusinessException;

	/**
	 * 
	 * @Description 工包列表
	 * @CreateTime 2017年9月25日 下午3:59:53
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getWorkpackageList(Workpackage record) throws BusinessException;
	
	/**
	 * @Description 查询航材工具需求清单工包信息 
	 * @CreateTime 2018-4-12 上午11:33:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	Map<String, Object> queryMToolDetailList(Workpackage record) throws BusinessException;
	
	/**
	 * @Description 查询预组包工包信息
	 * @CreateTime 2018-4-12 上午11:33:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	Map<String, Object> queryBurstificationWPList(Workpackage record) throws BusinessException;
	
	/**
	 * 
	 * @Description 根据id获取工包数据
	 * @CreateTime 2017年9月27日 下午3:19:31
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	Workpackage getRecord(Workpackage record) throws BusinessException;
	 /**
	  * 
	  * @Description 更新工包
	  * @CreateTime 2017年9月27日 下午3:20:32
	  * @CreateBy 岳彬彬
	  * @param record
	  * @throws BusinessException
	  */
	void updateRecord(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 删除工包
	 * @CreateTime 2017年9月27日 下午5:09:55
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void deleteRecord(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年9月27日 下午6:20:19
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4EndClose(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年9月28日 上午10:40:43
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4Issued(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 完工反馈
	 * @CreateTime 2017年9月28日 上午11:37:52
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4Feedback(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年9月28日 下午1:37:24
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4Close(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 获取工包明细
	 * @CreateTime 2017年9月30日 上午10:22:24
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param dprtcode
	 * @return
	 */
	Map<String,Object> getWorkpackageDetail(Workpackage record);
	
	/**
	 * @Description 飞行记录本查询工包数据
	 * @CreateTime 2017年10月17日 下午3:04:16
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<Workpackage> query4FLB(Workpackage record);
	
	/**
	 * @Description 同步145工包和工单数据 
	 * @CreateTime 2017-10-17 下午6:54:30
	 * @CreateBy 刘兵
	 * @param wp 工包
	 * @param user 用户
	 * @param workorderList 工单集合
	 * @param clzs 操作流水
	 */
	void doWorkpackageAndWorkoder145(Workpackage wp, List<Workorder> workorderList, User user, String czls);
	
	/**
	 * @Description 根据条件查询工包信息
	 * @CreateTime 2017-10-19 下午2:37:19
	 * @CreateBy 刘兵
	 * @param record 工包
	 * @return List<Workpackage>工包集合
	 */
	List<Workpackage> queryWorkpackageList(Workpackage record);
	/**
	 * 
	 * @Description 自动下发工单
	 * @CreateTime 2017年10月26日 上午10:02:05
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param idList
	 */
	void doAutoIssuedWorkorder(String gbid,List<String> idList);
	/**
	 * 
	 * @Description 修订
	 * @CreateTime 2017年11月27日 上午10:17:31
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException 
	 */
	void update4Revision(Workpackage record) throws BusinessException;
	/**
	 * 
	 * @Description 获取下发状态下的工包
	 * @CreateTime 2017年12月12日 下午4:55:26
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	List<Workpackage> getIssuedWp(Workpackage record);
}
