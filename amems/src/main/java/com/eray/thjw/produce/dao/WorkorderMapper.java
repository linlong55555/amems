package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;

/**
 * 
 * @Description 工单Mapper
 * @CreateTime 2017年9月28日 上午10:28:19
 * @CreateBy 岳彬彬
 */
public interface WorkorderMapper {
	int deleteByPrimaryKey(String id);

	int insert(Workorder record);

	int insertSelective(Workorder record);

	Workorder selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Workorder record);

	int updateByPrimaryKey(Workorder record);

	/**
	 * 
	 * @Description 获取工包下工单状态不为9和10的工单数量
	 * @CreateTime 2017年9月28日 上午10:27:34
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	Integer getCountByGbid4endClsoe(String gbid);

	/**
	 * 
	 * @Description 获取工包下工单
	 * @CreateTime 2017年9月28日 上午10:34:36
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	Integer getCountByGbid(String gbid);

	/**
	 * 
	 * @Description 获取工包下提交状态的工单数量
	 * @CreateTime 2017年9月28日 上午10:44:42
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	Integer getSubmitCountByGbid(String gbid);

	/**
	 * 
	 * @Description 根据工包id下发
	 * @CreateTime 2017年9月28日 上午11:02:53
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updateByGbid4Issued(Workorder record);

	/**
	 * 
	 * @Description 获取工包明细
	 * @CreateTime 2017年9月29日 上午10:15:38
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	List<Map<String, Object>> getWorkorderListByGbid(@Param("searchMap")Map<String, Object> searchMap);

	/**
	 * 
	 * @Description 选择不在工包和预组包中的工单
	 * @CreateTime 2017年9月29日 下午2:00:19
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param fjzch
	 * @param gbid
	 * @return
	 */
	List<Workorder> getWorkorderList(Workorder record);

	/**
	 * 
	 * @Description 工包中添加工单
	 * @CreateTime 2017年9月29日 下午5:06:05
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updateGbid(Workorder record);

	/**
	 * 
	 * @Description 验证选中工单集合的状态和工包id是否为空
	 * @CreateTime 2017年9月29日 下午5:26:04
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	int getCountByztAndGdid(List<String> list);

	/**
	 * @Description 工单135主列表查询
	 * @CreateTime 2017-9-30 下午1:40:22
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	List<Workorder> queryAllPageList(Workorder workorder);

	/**
	 * 
	 * @Description 工包明细的工单的航材工具
	 * @CreateTime 2017年9月30日 上午10:19:44
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @param dprtcode
	 * @return
	 */
	List<Map<String, Object>> getHcToolDetail(@Param("gbid") String gbid, @Param("dprtcode") String dprtcode);
	
	/**
	 * @Description 根据注册号，机构代码与标题查询工单
	 * @CreateTime 2017年10月7日 下午5:50:20
	 * @CreateBy 胡才秋
	 * @param data
	 * @return
	 */
	List<Workorder> queryListByTitle(Workorder data);
	
	/**
	 * 
	 * @Description 条件查询历史任务主列表
	 * @CreateTime 2017-10-9 上午11:21:03
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return List<Workorder>
	 */
	List<Workorder> queryAllTaskhistory(Workorder workorder);
	
	/**
	 * @Description 根据条件查询NRC工单列表
	 * @CreateTime 2017-10-13 上午9:58:19
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
	 */
	List<Workorder> queryNRCWorkOrderList(Workorder workorder);

	/**
	 * @Description 根据参数查询工单信息
	 * @CreateTime 2017-10-10 下午9:17:44
	 * @CreateBy 雷伟
	 * @param paramMap
	 * @return
	 */
	List<Workorder> selectByParam(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description 获取工包下的提交状态的工单
	 * @CreateTime 2017年10月9日 下午6:25:45
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	List<Workorder> getSubmitWorkorderByGbid(String gbid);


	/**
	 * 
	 * @Description 工单列表加载
	 * @CreateTime 2017年10月9日 下午9:10:43
	 * @CreateBy 林龙
	 * @param workorder 工单
	 * @return
	 */
	List<Workorder> queryAllList(Workorder workorder);
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-10-11 下午4:03:33
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return
	 */
	Workorder selectById(String id);

	/**
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 雷伟
	 * @param gdid 工单id
	 */
	Workorder selectWOById(String gdid);
	
	/**
	 * @Description 查询监控数据id集合,检查监控数据id是否存在
	 * @CreateTime 2017-10-13 下午5:43:57
	 * @CreateBy 刘兵
	 * @param map 参数
	 * @return List<String> 监控数据id集合
	 */
	List<String> query4CheckExist(Map<String, Object> map);
	
	/**
	 * @Description 工单的航材工具
	 * @CreateTime 2017年9月30日 上午10:19:44
	 * @CreateBy 雷伟
	 * @return
	 */
	List<Map<String, Object>> getGDHCToolDetail(Map<String, Object> map);

	/**
	 * @Description 工单执行历史
	 * @CreateTime 2017年9月30日 上午10:19:44
	 * @CreateBy 雷伟
	 * @return
	 */
	List<Map<String, Object>> getGDZxhistoryInfo(Map<String, Object> map);

	/**
	 * @Description 更新故障保留工单ID
	 * @CreateTime 2017-10-16 上午11:27:12
	 * @CreateBy 雷伟
	 * @param map
	 */
	void updateGZBLByGdid(Map<String, Object> map);

	void updateQXBLByGdid(Map<String, Object> map);
	
	/**
	 * @Description 修改NRC工单上工包id
	 * @CreateTime 2017-10-17 下午3:33:00
	 * @CreateBy 刘兵
	 * @param gbid
	 */
	void updateNRCGbid(String gbid);
	
	/**
	 * @Description 获取需要生成工单的维修项目和EO
	 * @CreateTime 2017-10-17 下午3:55:26
	 * @CreateBy 刘兵
	 * @param fjzch 飞机注册号
	 * @param dprtcode 机构代码
	 * @param gbid 工包id
	 * @return
	 */
	List<Workorder> queryProduceList(@Param("fjzch")String fjzch, @Param("dprtcode")String dprtcode, @Param("gbid")String gbid);

	/**
	 * @Description 查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-17 下午8:47:39
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 */
	List<Workorder> queryWorkOrderList(Workorder workorder);
	
	/**
	 * @Description 查询工包明细维修计划135(工单清单列表)
	 * @CreateTime 2017-10-20 下午3:06:05
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 */
	List<Workorder> queryWorkOrder135List(Workorder workorder);
	/**
	 * 
	 * @Description 根据工单id集合获取工单
	 * @CreateTime 2017年10月20日 下午4:10:21
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	List<Workorder> selectByIdList(List<String> list);
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年10月20日 下午4:18:15
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4Issued(Workorder record);
	
	/**
	 * 
	 * @Description 加载执行历史
	 * @CreateTime 2017-10-27 下午2:55:08
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return
	 */
	List<Workorder> queryAllPerformHistory(Workorder workorder);
	/**
	 * 
	 * @Description 根据工单识别id获取工单
	 * @CreateTime 2017年11月14日 下午4:20:55
	 * @CreateBy 岳彬彬
	 * @param gdsbid
	 * @return
	 */
	Workorder getIdByGdsbid(String gdsbid);
	/**
	 * 
	 * @Description 145工单更新同步到135工单
	 * @CreateTime 2017年11月14日 下午4:21:07
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updateByGdsbid(Workorder145 record);
	/**
	 * 
	 * @Description 145工单新增同步到135工单
	 * @CreateTime 2017年11月14日 下午4:21:21
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void insertByWO145(Workorder145 record);
	/**
	 * 
	 * @Description 移除工包中的工单
	 * @CreateTime 2017年11月15日 上午9:34:17
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	void update4RemoveWO(String id);
	
	/**
	 * @Description 检查工单是否存在
	 * @CreateTime 2017-11-15 下午3:48:17
	 * @CreateBy 刘兵
	 * @param gdbh 工单编号
	 * @param dprtcode 机构代码
	 * @return int 工单数量
	 */
	int getCount4CheckExist(@Param("gdbh")String gdbh, @Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 根据工单识别id查询工单列表
	 * @CreateTime 2017-11-25 下午2:03:39
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 */
	List<Workorder> queryByGdsbid(Workorder workorder);
	
	/**
	 * @Description 查询工包明细维修计划135(工单数，已反馈，已关闭)数量
	 * @CreateTime 2017-11-27 下午3:01:33
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return Map<String, Object> 工单数量集合
	 */
	Map<String, Object> queryCount4WorkOrder(Workorder workorder);
	
	/**
	 * @Description 根据工单更新飞行记录本完成工作 
	 * @CreateTime 2017年11月30日 下午3:04:05
	 * @CreateBy 徐勇
	 * @param workOrderId 工单id
	 * @return
	 */
	int updateFlbWorkByWorkorder(@Param("workOrderId")String workOrderId);
	
	/**
	 * @Description 工单135信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:27:24
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return 工单列表
	 */
	List<Workorder> queryAllPageListWin(Workorder workorder);

	int queryCount(Workorder fai);
	
	/**
	 * 
	 * @Description 维修工时明细数据
	 * @CreateTime 2018年4月2日 上午10:36:30
	 * @CreateBy 岳彬彬
	 * @param workOrder
	 * @return
	 */
	List<Map<String,Object>> selectWxGs(Workorder workOrder);
	/**
	 * 
	 * @Description 清洁工时明细数据
	 * @CreateTime 2018年4月2日 上午10:39:36
	 * @CreateBy 岳彬彬
	 * @param workOrder
	 * @return
	 */
	List<Map<String,Object>> selectQjgs(Workorder workOrder);
}