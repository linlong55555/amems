package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.WorkOrder;

public interface WorkOrderService {
	/**
	 * @author meizhiliang
	 * @discription 查询工单列表所有的工单
	 */
    List<WorkOrder> selectWorkOrderList(WorkOrder record);                              //分页查询工单
	
    public void updateByPrimaryKeys(WorkOrder wrokorder);
    
	/**
	 * @author meizhiliang
	 * @discription 关联工单-查询所有的工单
	 */
	Map<String, Object> selectAllWorkOrder(WorkOrder record);    
	
	/**
	 * @author meizhiliang
	 * @discription 增加工单
	 */
	String insertSelective(WorkOrder record);                         
	
	/**
	 * @author meizhiliang
	 * @discription 查询一条工单的信息
	 */
	WorkOrder selectByWorkOrder(WorkOrder record);            //查询一条工单信息
	
	/**
	 * @author meizhiliang
	 * @discription 审核工单
	 */
	int updateAudit(WorkOrder record);           //   审核工单
	/**
	 * @author meizhiliang
	 * @discription 批量审核工单
	 */
	String doUpdateBatchReview(WorkOrder record);     
	/**
	 * @author meizhiliang
	 * @discription 批量批准工单
	 */
	String doUpdateBatchApprove(WorkOrder record);         
	
	/**
	 * @author meizhiliang
	 * @discription 批准工单
	 */
	int updateReply(WorkOrder record);           //   批准工单
	/**
	 * @author meizhiliang
	 * @discription 指定结束工单
	 */
	int updateOver(WorkOrder record);           //   指定结束
	/**
	 * @author meizhiliang
	 * @discription 作废工单
	 */
    int updateCancel(WorkOrder record);           //   作废工单
    /**
	 * @author meizhiliang
	 * @discription 更新工单
	 */
    int updateByPrimaryKeySelective(WorkOrder record);            //更新工单   
    
    /**
   	 * @author meizhiliang
   	 * @discription 查询工单及相关航材，关联工单，指令来源，工单内容，工单附件，执行对象公共方法
   	 */
    public Map<String,Object> initializtionWo(String id, Integer gddlx);
    /**
   	 * @author meizhiliang
   	 * @discription 指定结束定检任务单 结束所有的定检公单
   	 */
    void doEnd( WorkOrder workorder);  
    /**
   	 * @author meizhiliang
   	 * @discription 查询所有能被复制的工单
   	 */
    List<WorkOrder> selectCopyList(WorkOrder record);   
    
       /**
	 * @author sunji
	 * @discription 关联工单-查询所有的工单和工卡
	 */
	List<WorkOrder> queryRelatedJobCardAll(WorkOrder record); 
	
	/**
	 * @author sunji
	 * @discription 关联工单-根据机型查询所有的工单和工卡
	 */
	List<WorkOrder> selectRealtedJobCardByjx(WorkOrder record);  
	
	/**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<WorkOrder> queryAll(Map<String, Object> map);
	
	/**
	 * 开窗分页查询 相关工单
	 * @param record
	 * @author xu.yong
	 * @return
	 */
	public Map<String, Object> queryGlgdPage(BaseEntity record);
	
	/**
	 * 查询所有EO工单（不分页）
	 * @param gczlmxId
	 * @author sunji
	 * @return
	 */
	public List<WorkOrder> queryAllByDetailEngineeringId();		
	/**
	 * 进入修改工单页面
	 * @param gczlmxId
	 * @author sunji
	 * @return
	 */
	public ModelAndView intoEditByWorkOrder(String id,Integer gddlx);
	/**
	 * 更新定检任务的监控备注
	 * @param gczlmxId
	 * @author sunji
	 * @return
	 */
	public int UpdateCheckTaskBill(WorkOrder workorder);	
	/**
	 * 查询工单下面所有的关联信息
	 * @param workorder
	 * @return
	 */
	public WorkOrder selectByCopy(WorkOrder workorder);
	
	/**
	 * @author liub
	 * @description 根据工程指令id查询EO工单数据
	 * @param gczlid
	 * @return List<WorkOrder>
	 * @develop date 2017.03.17
	*/
	public List<WorkOrder> queryByGczlId(String gczlid);
	
	/**
	 * @author liub
	 * @description 根据评估单id、工单类型查询非例行工单数据
	 * @param pgdid,gdlx
	 * @return List<WorkOrder>
	 * @develop date 2017.03.20
	*/
	public List<WorkOrder> queryByPgdIdAndGdlx(String pgdid,String gdlx);
}
