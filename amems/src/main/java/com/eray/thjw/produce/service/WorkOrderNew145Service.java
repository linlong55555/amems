package com.eray.thjw.produce.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder145;

import enu.LogOperationEnum;

/**
 * 
 * @Description 145工单service
 * @CreateTime 2017年10月10日 上午9:28:47
 * @CreateBy 林龙
 */
public interface WorkOrderNew145Service {
	
	/**
	 * 
	 * @Description 工单列表加载
	 * @CreateTime 2017年10月9日 下午9:10:43
	 * @CreateBy 林龙
	 * @param workorder 工单
	 * @return
	 */
	Map<String, Object> queryAllList(Workorder145 workorder145)throws BusinessException;

	/**
	 * 
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 林龙
	 * @param gdid 工单id
	 */
	Workorder145 selectByPrimaryKey(String gdid);

	void updateWOMain(Workorder145 workorder145, Date currentDate, String czls,LogOperationEnum operation);
	/**
	 * 
	 * @Description 工包明细中工单附件处理
	 * @CreateTime 2017年10月20日 上午10:53:38
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void doAttachment(Workorder145 record) throws BusinessException;
	/**
	 * 
	 * @Description 选择工单
	 * @CreateTime 2017年10月20日 上午11:05:48
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	Map<String,Object> getWorkorderList(Workorder145 record) throws BusinessException;
	/**
	 * 
	 * @Description 工包添加工单
	 * @CreateTime 2017年10月20日 上午11:15:18
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void updateGbid(Workorder145 record) throws BusinessException;

	/**
	 * @Description 145工单主列表
	 * @CreateTime 2017-10-23 上午11:17:54
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	Map<String, Object> queryAllPageList(Workorder145 workorder);
	
	/**
	 * @Description 工单145信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:27:24
	 * @CreateBy 刘兵
	 * @param workorder 145工单
	 * @return 工单列表
	 */
	Map<String, Object> queryAllPageListWin(Workorder145 workorder);

	/**
	 * @Description 新增工单145
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @return
	 * @throws BusinessException
	 */
	String save(Workorder145 workorder) throws BusinessException;

	/**
	 * @Description 根据ID查询145工单
	 * @CreateTime 2017-10-23 下午4:28:08
	 * @CreateBy 雷伟
	 * @param gdid
	 * @return
	 */
	Workorder145 selectWOById(String gdid);

	/**
	 * @Description 编辑工单145
	 * @CreateTime 2017-10-10 下午8:50:14
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @return
	 * @throws BusinessException
	 */
	String update(Workorder145 workorder) throws BusinessException;

	/**
	 * @Description 145工单航材工具
	 * @CreateTime 2017-10-24 下午1:55:53
	 * @CreateBy 雷伟
	 * @param workorder 145工单
	 * @return
	 */
	Map<String, Object> getGDHCToolDetail(Workorder145 workorder);

	/**
	 * @Description 删除工单
	 * @CreateTime 2017-10-12 下午2:54:11
	 * @CreateBy 雷伟
	 * @param woId 工单ID
	 * @throws BusinessException
	 */
	void doDelete(String woId) throws BusinessException;

	/**
	 * @Description 完工关闭
	 * @CreateTime 2017-10-24 下午2:44:25
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException 
	 */
	String doWGClose(Workorder145 workorder) throws BusinessException;

	/**
	 * @Description 指定结束
	 * @CreateTime 2017-10-24 下午2:44:47
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	String doZDClose(Workorder145 workorder) throws BusinessException;

	/**
	 * @Description 工单反馈
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单145
	 * @return
	 * @throws BusinessException
	 */
	String doFeedback(Workorder145 workorder) throws BusinessException;

	/**
	 * 
	 * @Description 工种完工注水图数据
	 * @CreateTime 2017年10月23日 下午2:55:56
	 * @CreateBy 林龙
	 * @param workorder145
	 * @return
	 */
	List<Map<String, Object>> queryDiagramList(Workorder145 workorder145);

	/**
	 * 
	 * @Description 工种执行进度图数据
	 * @CreateTime 2017年10月24日 下午1:52:48
	 * @CreateBy 林龙
	 * @param workorder145
	 * @return
	 */
	List<Map<String, Object>> queryProgressList(Workorder145 workorder145);

	String doXDClose(Workorder145 workorder)throws BusinessException;


}
