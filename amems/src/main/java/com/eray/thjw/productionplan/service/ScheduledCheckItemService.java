package com.eray.thjw.productionplan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.ScheduledCheckMonitorItem;
import com.eray.thjw.sched.po.ProductPlanSched;

import enu.LogOperationEnum;


public interface ScheduledCheckItemService {
	
	public List<ScheduledCheckItem> queryAllPageList(ScheduledCheckItem scheduledCheckItem) throws Exception;
	
	public int queryCount(ScheduledCheckItem scheduledCheckItem) throws Exception;
	
	public String saveEditable(ScheduledCheckItem scheduledCheckItem, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	//保存编辑区定检项目
	
	List<ScheduledCheckItem> queryEditableByZjqdid(String zjdqid) throws RuntimeException;	// 编辑区-根据装机清单查询
	
	void deleteEditable(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	// 删除定检项目
	
	void deleteByZjqdid(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	// 根据装机清单删除定检项目
	
	void cascadeDeleteByZjqdid(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	// 级联删除定检项目
	
	
	String shengyu(ScheduledCheckMonitorItem scheduledCheckMonitorItem, Map<String, String> map)throws Exception;//计算剩余
	
	
	public Map<String, Object> checkUpdMt(User user,String ids) throws Exception;//定检的验证
	
	public Map<String, Object> saveScheduledcheckitem(String ids) throws Exception;//提交计划
	
	void updateEditable(ScheduledCheckItem item, String czls, LogOperationEnum logOperationEnum) throws RuntimeException;	// 修改定检项目

	String jihua(ScheduledCheckMonitorItem scheduledCheckMonitorItem)
			throws Exception;

	public List<ScheduledCheckItem> geshihua(List<ScheduledCheckItem> list,PlaneModelData planeModelData, ScheduledCheckItem scheduledCheckItem) throws Exception;
	/**
	 * 预排定检监控
	 * @param list
	 * @param planeModelData
	 * @param param 
	 * @return
	 * @throws Exception
	 */
	public List<ScheduledCheckItem> installRecursivelyScheduledCheck(ScheduledCheckItem param,User user)throws Exception;
	
	ScheduledCheckItem selectXlh(ScheduledCheckItem  sc)throws RuntimeException;            //工单查询飞机序号和左右发

	public void updatePlaneEditable(ScheduledCheckItem scheduledCheckItem1);

	public Map<String, Object> saveJkbz(ScheduledCheckItem scheduledCheckItem)throws RuntimeException;

	public List<ScheduledCheckItem> queryAllPageList1(ScheduledCheckItem scheduledCheckItem)throws Exception;
	
	/**
	 * 更具条件查询生产计划预排</p>
	 * 1，查指令（飞机注册号，机构，预排截至日期，批次 倒序取第一条）。</br>
	 * 2，没有找到：根据用户日期计算生成指令和数据，查出第一页数据。</br>
	 * 3，找到了：检查用户截至日期是否在有效期内。</br>
	 * 31，在有效期内：查数据（按用户截至时间）</br>
	 * 32，不在有效期内：先将指令预判日期与设定预排最大期取小，在将该值与用户日期取大，</br>
	 *    生成指令，生成数据，查数据。</br>
	 * @param scheduledCheckItem
	 * @return
	 */
	public Map<String, Object> doQuerySched(ScheduledCheckItem scheduledCheckItem,User user) throws BusinessException;

	/**
	 * 保存预排内容到DB
	 * @param scheduledCheckItem
	 * @param id
	 * @param user
	 * @param dprtcode
	 * @param jzrq
	 * @param currentDate
	 * @param term4Validity
	 * @param maximumPeriod
	 * @throws BusinessException
	 */
	void saveSched(ScheduledCheckItem scheduledCheckItem, String zlid, User user, Date currentDate, Monitorsettings term4Validity, Monitorsettings maximumPeriod) throws BusinessException;

	/**
	 * 后台批量更新预排信息
	 * @throws BusinessException
	 */
	public void batchUpdateSched() throws BusinessException;

	/**
	 * 查询预排列表
	 * @param scheduledCheckItem
	 * @param zlid
	 * @return
	 */
	public List<ProductPlanSched> querySchedList(ScheduledCheckItem scheduledCheckItem, String zlid);

	public List<ProductPlanSched> querySchedAll(ScheduledCheckItem scheduledCheckItem);

}
