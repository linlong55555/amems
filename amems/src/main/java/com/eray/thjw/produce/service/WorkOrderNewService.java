package com.eray.thjw.produce.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.InstallAndRemove;
import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workpackage;

import enu.LogOperationEnum;

/**
 * @Description 135工单Service
 * @CreateTime 2017-9-29 上午10:31:48
 * @CreateBy 雷伟
 */
public interface WorkOrderNewService {
	/**
	 * 
	 * @Description 选择不在工包和预组包中的工单
	 * @CreateTime 2017年9月29日 下午2:01:38
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param fjzch
	 * @param gbid
	 * @return
	 */
	Map<String, Object> getWorkorderList(Workorder record);

	/**
	 * 
	 * @Description 工包添加工单
	 * @CreateTime 2017年9月29日 下午5:00:20
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @throws BusinessException
	 */
	void updateGbid(Workorder record) throws BusinessException;

	/**
	 * @Description 工单135列表查询
	 * @CreateTime 2017-9-30 下午1:34:35
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	Map<String, Object> queryAllPageList(Workorder workorder);
	
	/**
	 * @Description 工单135信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:27:24
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return 工单列表
	 */
	Map<String, Object> queryAllPageListWin(Workorder workorder);
	
	/**
	 * 
	 * @Description 工包明细列表处理附件
	 * @CreateTime 2017年9月30日 下午2:33:46
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	void doAttachment(Workorder record) throws BusinessException;

	/**
	 * @Description 新增工单
	 * @CreateTime 2017-10-10 下午8:52:06
	 * @CreateBy 雷伟
	 * @param workorder 工单135
	 * @return
	 * @throws BusinessException
	 */
	String save(Workorder workorder) throws BusinessException;

	/**
	 * 
	 * @Description 工单列表加载
	 * @CreateTime 2017年10月9日 下午9:10:43
	 * @CreateBy 林龙
	 * @param workorder 工单
	 * @return
	 */
	Map<String, Object> queryAllList(Workorder workorder)throws BusinessException;
	
	/**
	 * @Description 根据条件查询维修历史清单列表
	 * @CreateTime 2017-10-09上午11:06:29
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单数据集合
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
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 林龙
	 * @param gdid 工单id
	 */
	Workorder selectByPrimaryKey(String gdid);

	/**
	 * @Description 根据id查询工单信息
	 * @CreateTime 2017年10月10日 上午9:49:26
	 * @CreateBy 雷伟
	 * @param gdid 工单id
	 */
	Workorder selectWOById(String gdid);

	/**
	 * @Description 编辑工单
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	String update(Workorder workorder) throws BusinessException;

	/**
	 * 
	 * @Description 修改工单
	 * @CreateTime 2017年10月13日 上午11:46:11
	 * @CreateBy 林龙
	 * @param workorder
	 * @throws BusinessException
	 */
	public void updateWOMain(Workorder workorder, Date currentDate,String czls, LogOperationEnum operation) throws BusinessException;

	/**
	 * @Description 删除工单
	 * @CreateTime 2017-10-12 下午2:55:18
	 * @CreateBy 雷伟
	 * @param woId 工单ID
	 */
	void doDelete(String woId) throws BusinessException;

	/**
	 * @Description 工单反馈
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	String doFeedback(Workorder workorder) throws BusinessException;

	/**
	 * @Description 工单关闭
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	String doWGClose(Workorder workorder) throws BusinessException;

	/**
	 * @Description 工单关闭,指定结束
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param workorder 工单
	 * @return
	 * @throws BusinessException
	 */
	String doZDClose(Workorder workorder) throws BusinessException;
	
	/**
	 * @Description 飞行记录本修订 指定结束 工单
	 * @CreateTime 2017年11月27日 上午9:46:26
	 * @CreateBy 徐勇
	 * @param workOrderId 工单ID
	 */
	public void doZDClose4FLB(String workOrderId);

	/**
	 * @Description 工单航材明细
	 * @CreateTime 2017-10-14 下午1:56:23
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	Map<String, Object> getGDHCToolDetail(Workorder workorder) throws BusinessException;

	/**
	 * @Description 工单执行历史
	 * @CreateTime 2017-10-14 下午3:40:25
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> getGDZxhistoryInfo(Workorder workorder) throws BusinessException;

	/**
	 * @Description 设置完成时限
	 * @CreateTime 2017-10-11 下午4:38:29
	 * @CreateBy 雷伟
	 * @param wo
	 * @return 
	 */
	Workorder getCompleteLimit(Workorder wo);
	
	/**
	 * @Description 查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-17 下午9:10:03
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return List<Workorder> 工单集合
	 */
	List<Workorder> queryWorkOrderList(Workorder workorder);
	
	/**
	 * @Description 据查询条件分页查询工包明细(工单清单列表)
	 * @CreateTime 2017-10-20 上午11:36:49
	 * @CreateBy 刘兵
	 * @param workorder 工单
	 * @return Map<String, Object> 页面数据
	 */
	Map<String, Object> queryAllPageWorkOrderList(Workorder workorder);

	/**
	 * @Description 根据多个id查询工单
	 * @CreateTime 2017年12月12日 下午2:36:47
	 * @CreateBy 徐勇
	 * @param ids
	 * @return 
	 */
	public List<Workorder> queryByIds(List<String> ids);
	
	/**
	 * @Description FLB中撤销工单和工单拆装记录
	 * @CreateTime 2017年12月12日 下午2:14:59
	 * @CreateBy 徐勇
	 * @param workOrderIds 待撤销的flb工单id
	 * @param ioIds 待撤销的flb中手工拆装记录
	 * @param workOrderList flb涉及的所有工单
	 * @param installRemoveUndoList 补充的待撤销拆装，可为null
	 * @throws BusinessException
	 */
	public void doWOUndo4FLB(List<String> workOrderIds, List<String> ioIds, List<Workorder> workOrderList, List<InstallAndRemove> installRemoveUndoList) throws BusinessException;
	
	/**
	 * @Description FLB提交/修订 处理工单修订和拆装记录修订
	 * @CreateTime 2017年12月12日 下午3:57:23
	 * @CreateBy 徐勇
	 * @param flbWorkOrderUndoIds 待撤销的flb工单id
	 * @param workOrderIOUndoIds 待撤销的工单拆装记录id
	 * @param addWOList 待新增的flb工单
	 * @param editWOList 待修改的工单
	 * @param addIOList 待新增的工单拆装记录
	 * @param editIOList 待修改的工单拆装记录
	 * @param installRemoveUndoList 待修改的拆装记录涉及的 拆装撤销
	 * @param installRemoveList 待修改的拆装记录涉及的 拆装生效
	 * @param workOrderIds FLB涉及的所有工单id
	 * @throws BusinessException 
	 */
	public void doWOUpdate4FLB(List<String> flbWorkOrderUndoIds, List<String> workOrderIOUndoIds, 
			List<Workorder> addWOList, List<Workorder> editWOList, 
			List<WorkOrderIORecord> addIOList, List<WorkOrderIORecord> editIOList,
			List<InstallAndRemove> installRemoveUndoList, List<InstallAndRemove> installRemoveList, List<String> workOrderIds) throws BusinessException;
	
	/**
	 * 
	 * @Description 移除工包中的工单
	 * @CreateTime 2017年11月15日 上午9:35:51
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	void update4RemoveWO(String id);
	
	/**
	 * 
	 * @Description 工单修订
	 * @CreateTime 2017-11-24 下午3:21:14
	 * @CreateBy 孙霁
	 * @param workorder
	 */
	String doWGRevision(Workorder workorder)throws BusinessException;
	
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
	 * 
	 * @Description 验证该工单是否能下发
	 * @CreateTime 2017年12月12日 下午2:42:08
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	void doValidation4Exist(String id)throws BusinessException;
	/**
	 * 
	 * @Description MCC/其他工单下发
	 * @CreateTime 2017年12月13日 上午10:33:05
	 * @CreateBy 岳彬彬
	 * @param workorder
	 */
	void doIssued(Workorder workorder);
	/**
	 * 
	 * @Description 处理MCC/其他工单下发带工包的新增
	 * @CreateTime 2017年12月13日 上午10:44:36
	 * @CreateBy 岳彬彬
	 * @param workpackage
	 * @throws BusinessException 
	 */
	void doWoIssued(Workpackage workpackage) throws BusinessException;
	
	/**
	 * @Description 据查询条件分页查询工包明细(工单清单列表)(导出)
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前工单参数
	 * @return List<Workorder>
	 */
	List<Workorder> exportExcelWO(Workorder paramObj);
	
	/**
	 * @Description 据查询条件分页查询工包明细(预组包工单清单列表)(导出)
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前工单参数
	 * @return List<Workorder>
	 */
	List<Workorder> exportExcelYzbWO(Workorder paramObj);
}