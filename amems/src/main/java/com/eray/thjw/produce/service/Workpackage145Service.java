package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workpackage145;

/**
 * 
 * @Description 工包service
 * @CreateTime 2017年9月23日 下午3:56:34
 * @CreateBy 岳彬彬
 */
public interface Workpackage145Service {
	/**
	 * 
	 * @Description 新增145工包
	 * @CreateTime 2017年10月16日 下午5:28:01
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String addRecord(Workpackage145 record) throws BusinessException;

	/**
	 * 
	 * @Description 145工包列表
	 * @CreateTime 2017年10月16日 下午5:28:17
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getWorkpackageList(Workpackage145 record) throws BusinessException;
	
	
	/**
	 * @Description 查询航材工具需求清单工包信息 
	 * @CreateTime 2018-4-12 上午11:33:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	Map<String, Object> queryMToolDetail145List(Workpackage145 record) throws BusinessException;
	
	/**
	 * 
	 * @Description 根据id获取工包数据
	 * @CreateTime 2017年10月18日 下午2:42:34
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	Workpackage145 getRecord(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 更新
	 * @CreateTime 2017年10月18日 下午4:21:23
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void updateRecord(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 删除工包
	 * @CreateTime 2017年10月18日 下午5:11:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void deleteRecord(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年10月18日 下午5:36:00
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4Issued(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 完工反馈
	 * @CreateTime 2017年10月19日 上午9:42:52
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4Feedback(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年10月19日 上午11:54:21
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4Close(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年10月19日 下午1:59:57
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void update4End(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 工包明细
	 * @CreateTime 2017年10月20日 上午9:55:05
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	Map<String,Object> getWorkpackageDetail(Workpackage145 record);

	/**
	 * 
	 * @Description 维修计划列表
	 * @CreateTime 2017年10月23日 下午1:44:32
	 * @CreateBy 林龙
	 * @param record
	 * @return
	 * @throws BusinessException 
	 */
	Map<String, Object> getWorkpackagePlanList(Workpackage145 record) throws BusinessException;
	/**
	 * 
	 * @Description 航材工具需求清单
	 * @CreateTime 2017年10月25日 上午10:17:56
	 * @CreateBy 林龙
	 * @param workpackage145
	 * @return
	 */
	List<Map<String, Object>> getMaterialsDetail(Workpackage145 workpackage145);

	/**
	 * 
	 * @Description 修订工包
	 * @CreateTime 2017年11月24日 下午3:56:54
	 * @CreateBy 林龙
	 * @param workpackage145
	 * @return
	 * @throws BusinessException
	 */
	String doXDClose(Workpackage145 workpackage145) throws BusinessException;
}
