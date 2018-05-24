package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.WorkOrder;

public interface WorkOrderMapper {

	List<WorkOrder> selectCopyList(WorkOrder record); // 查询所有能被复制的工单

	List<WorkOrder> selectAllWorkOrder(WorkOrder record); // 关联工单-查询所有的工单

	List<WorkOrder> queryGlgdPage(BaseEntity record); // 关联工单-查询所有的工单

	int insertSelective(WorkOrder record); // 增加工单

	List<WorkOrder> selectWorkOrderList(WorkOrder record); // 分页查询工单

	WorkOrder selectByWorkOrder(WorkOrder record); // 查询一条工单信息

	int updateAudit(WorkOrder record); // 审核工单

	int updateReply(WorkOrder record); // 批准工单

	int updateOver(WorkOrder record); // 指定结束

	int updateCancel(WorkOrder record); // 作废工单

	void doEnd(WorkOrder workorder); // 指定结束定检任务单 结束所有的定检公单

	void updateBatchReviewAndApproveNon(WorkOrder workorder); // 批量审核批准非例行工单

	void updateBatchReviewAndApproveEO(WorkOrder workorder); // 批量审核批准EO工单

	int updateByPrimaryKeySelective(WorkOrder record); // 更新工单

	List<WorkOrder> queryAll(Map<String, Object> map);

	List<WorkOrder> queryAllByDetailEngineeringId(); // 查询所有EO工单（不分页）

	int insert(WorkOrder record);

	int deleteByPrimaryKey(String id);

	WorkOrder selectByPrimaryKey(String id);

	List<WorkOrder> queryRelatedJobCardAll(WorkOrder workOrder);

	List<WorkOrder> selectRealtedJobCardByjx(WorkOrder workOrder);

	public void updateByPrimaryKeys(WorkOrder wrokorder);
	/**
	 * @author liub
	 * @description 根据工程指令id查询EO工单数据
	 * @param gczlid
	 * @develop date 2017.03.17
	 */
	List<WorkOrder> queryByGczlId(String gczlid);

	/**
	 * @author liub
	 * @description 根据评估单id、工单类型查询非例行工单数据
	 * @param pgdid
	 *            ,gdlx
	 * @return List<WorkOrder>
	 * @develop date 2017.03.20
	 */
	List<WorkOrder> queryByPgdIdAndGdlx(@Param("pgdid") String pgdid,
			@Param("gdlx") String gdlx);

	/**
	 * @author liub
	 * @description 根据工程指令id集合查询EO工单数据
	 * @param gczlIdList
	 * @develop date 2017.03.17
	 */
	List<WorkOrder> queryByGczlIdList(List<String> gczlIdList);

	/**
	 * @author liub
	 * @description 根据非例行工单id集合查询非例行工单数据
	 * @param flxgdIdList
	 * @develop date 2017.03.20
	 */
	List<WorkOrder> queryByIdList(List<String> flxgdIdList);

	List<WorkOrder> queryByIdListEo(List<String> idList);

	List<WorkOrder> queryByIdListNon(List<String> idList);
}