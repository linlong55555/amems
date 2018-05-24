package com.eray.thjw.produce.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.rest.common.SysConfig;
import com.eray.rest.enu.DeferredDefectStatusEnum;
import com.eray.rest.service.DeferredDefectService;
import com.eray.rest.vo.DeferredDefect;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.dao.FailureKeepJKMapper;
import com.eray.thjw.produce.dao.FailureKeepMapper;
import com.eray.thjw.produce.dao.FailureKeepQTMapper;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.DefectKeep;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.po.FailureKeepJK;
import com.eray.thjw.produce.po.FailureKeepQT;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.service.FailureKeepService;
import com.eray.thjw.produce.service.WorkOrderNew145Service;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.project2.dao.TodoMapper;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.produce.FailureKeepJKEnum;
import enu.produce.FailureKeepQTEnum;
import enu.produce.FailureKeepStatusEnum;
import enu.produce.NodeEnum;
import enu.produce.RetentionTypeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.TodoStatusEnum;
/**
 * 
 * @Description 故障保留serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("failureKeepService")
public class FailureKeepServiceImpl implements FailureKeepService  {

	@Resource
	private FailureKeepMapper failureKeepMapper;

	@Resource
	private AttachmentService attachmentService;

	@Resource
	private SaibongUtilService saibongUtilService;

	@Resource
	private CommonRecService commonRecService;

	@Resource
	private MaterialToolService materialToolService;

	@Resource
	private WorkOrderNewService workOrderNewService;

	@Resource
	private WorkOrderNew145Service workOrderNew145Service;

	@Resource
	private PlaneModelDataService planeModelDataService;

	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;

	@Resource
	private FailureKeepJKMapper failureKeepJkMapper;

	@Resource
	private FailureKeepQTMapper failureKeepQTMapper;

	@Resource
	private CommonService commonService;
	
	@Resource
	private DeferredDefectService deferredDefectService;
	
	@Resource
	private TodorsService  todorsService;

	/**
	 * @Description 故障保留分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(FailureKeep failureKeep)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = failureKeep.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		failureKeep.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		failureKeep.getParamsMap().put("userId", user.getId());
		failureKeep.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(failureKeep.getPagination());
		List<FailureKeep> list = failureKeepMapper.queryAllPageList(failureKeep);
		
		//把字符串数据转换成集合
		convertStr2List(list);
		
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					FailureKeep param = new FailureKeep();
					param.setId(id);
					List<FailureKeep> newRecordList = failureKeepMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, failureKeep.getPagination());
		}else{
			List<FailureKeep> newRecordList = new ArrayList<FailureKeep>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				FailureKeep param = new FailureKeep();
				param.setId(id);
				newRecordList = failureKeepMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, failureKeep.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, failureKeep.getPagination());
		}
	}

	/**
	 * @Description 把字符串数据转换成集合
	 * @CreateTime 2018-1-31 下午1:25:09
	 * @CreateBy 雷伟
	 * @param list
	 */
	@SuppressWarnings("null")
	private void convertStr2List(List<FailureKeep> sourceList) {
		if (null == sourceList || sourceList.size() <= 0) {
			return;
		}
		
		//每一行,保留期限类型
		String[] jklbhList = {MonitorProjectEnum.CAL.getCode(),MonitorProjectEnum.FH.getCode(),MonitorProjectEnum.FC.getCode()};
		
		for (FailureKeep obj : sourceList) {
			
			if(StringUtils.isNotBlank(obj.getFxsj())){
				obj.setFxsjExcelDatas(StringAndDate_Util.convertToHour(String.valueOf(obj.getFxsj()))+"FH"); //转换飞行时间，供excel导出使用
			}
			
			Map<String, String> sjMap = new HashMap<String, String>();//实际Map
			Map<String, String> syMap = new HashMap<String, String>();//剩余Map
			Map<String, String> scMap = new HashMap<String, String>();//首次Map
			Map<String, String> zcMap = new HashMap<String, String>();//再次Map
			
			if(StringUtils.isNotBlank(obj.getSjsyStrs())){
				String[] sjsyRows = obj.getSjsyStrs().split(","); //实际剩余行
				for(int i = 0; sjsyRows != null && i < sjsyRows.length; i++) {
					String[] columns = sjsyRows[i].split("#_#");
					
					String jklbh = "";
					String sjval = "";
					String syval = "";
					
					//监控类别号
					if(columns.length >= 1){
						jklbh = columns[0];
					}
					
					//实际值
					if(columns.length >= 2){
						sjval = columns[1];
					}
					
					//剩余值
					if(columns.length >= 3){
						syval = columns[2];
					}
					
					if(StringUtils.isNotBlank(jklbh)){
						sjMap.put(jklbh, sjval);
						syMap.put(jklbh, syval);
					}
				}
			}
			
			if(StringUtils.isNotBlank(obj.getScStrs())){
				String[] scRows = obj.getScStrs().split(","); //首次期限
				for(int i = 0; scRows != null && i < scRows.length; i++) {
					String[] columns = scRows[i].split("#_#");
					
					String jklbh = "";
					String scval = "";
					
					//监控类别号
					if(columns.length >= 1){
						jklbh = columns[0];
					}
					
					//首次期限值
					if(columns.length >= 2){
						scval = columns[1];
					}
					
					if(StringUtils.isNotBlank(jklbh)){
						scMap.put(jklbh, scval);
					}
				}
			}
			
			if(StringUtils.isNotBlank(obj.getZcStrs())){
				String[] zcRows = obj.getZcStrs().split(","); //再次期限
				for(int i = 0; zcRows != null && i < zcRows.length; i++) {
					String[] columns = zcRows[i].split("#_#");
					
					String jklbh = "";
					String zcval = "";
					
					//监控类别号
					if(columns.length >= 1){
						jklbh = columns[0];
					}
					
					//首次期限值
					if(columns.length >= 2){
						zcval = columns[1];
					}
					
					if(StringUtils.isNotBlank(jklbh)){
						zcMap.put(jklbh, zcval);
					}
				}
			}
			
			//按順序,每行生成三条数据
			for (int i = 0; i < jklbhList.length; i++) {
				String jklbh = jklbhList[i];
				String until = MonitorProjectEnum.getUnit(jklbh);
				
				String scZ = scMap.get(jklbh);
				String zcZ = zcMap.get(jklbh);
				boolean allNull = (null == scZ && null == zcZ); 
				
				//实际
				if (sjMap.containsKey(jklbh)) {
					List<String> resultList = obj.getSjList()==null?new ArrayList<String>():obj.getSjList();
					if(jklbh.equals(MonitorProjectEnum.FH.getCode())){
						resultList.add(StringAndDate_Util.convertToHour(String.valueOf(sjMap.get(jklbh)))+" "+until);
					}else{
						resultList.add(sjMap.get(jklbh)+" "+until);
					}
					obj.setSjList(resultList);
				} else {
					if (!allNull) {
						List<String> resultList = obj.getSjList()==null?new ArrayList<String>():obj.getSjList();
						resultList.add("");
						obj.setSjList(resultList);
					}
				}
				
				//剩余
				if (syMap.containsKey(jklbh)) {
					if(StringUtils.isBlank(until)){
						until = "D";
					}
					List<String> resultList = obj.getSyList()==null?new ArrayList<String>():obj.getSyList();
					if(jklbh.equals(MonitorProjectEnum.FH.getCode())){
						resultList.add(StringAndDate_Util.convertToHour(String.valueOf(syMap.get(jklbh)))+" "+until);
					}else{
						resultList.add(syMap.get(jklbh)+" "+until);
					}
					obj.setSyList(resultList);
				} else {
					if (!allNull) {
						List<String> resultList = obj.getSyList()==null?new ArrayList<String>():obj.getSyList();
						resultList.add("");
						obj.setSyList(resultList);
					}
				}
				
				if(until.equals("D")){
					until = "";
				}
				
				//首次
				if (scMap.containsKey(jklbh)) {
					List<String> resultList = obj.getScblqxList()==null?new ArrayList<String>():obj.getScblqxList();
					if(jklbh.equals(MonitorProjectEnum.FH.getCode())){
						resultList.add(StringAndDate_Util.convertToHour(String.valueOf(scMap.get(jklbh)))+" "+until);
					}else{
						resultList.add(scMap.get(jklbh)+" "+until);
					}
					obj.setScblqxList(resultList);
				} else {
					if (!allNull) {
						List<String> resultList = obj.getScblqxList()==null?new ArrayList<String>():obj.getScblqxList();
						resultList.add("");
						obj.setScblqxList(resultList);
					}
				}
				
				//再次
				if (zcMap.containsKey(jklbh)) {
					List<String> resultList = obj.getZcblqxList()==null?new ArrayList<String>():obj.getZcblqxList();
					if(jklbh.equals(MonitorProjectEnum.FH.getCode())){
						resultList.add(StringAndDate_Util.convertToHour(String.valueOf(zcMap.get(jklbh)))+" "+until);
					}else{
						resultList.add(zcMap.get(jklbh)+" "+until);
					}
					obj.setZcblqxList(resultList);
				} else {
					if (!allNull) {
						List<String> resultList = obj.getZcblqxList()==null?new ArrayList<String>():obj.getZcblqxList();
						resultList.add("");
						obj.setZcblqxList(resultList);
					}
				}
			}
			
			//处理excel需要的数据
			String sjExcelDatas = "";
			for (int i = 0; obj.getSjList() != null && i < obj.getSjList().size(); i++) {
				if(i == obj.getSjList().size() - 1){
					sjExcelDatas += obj.getSjList().get(i);
				}else{
					sjExcelDatas += obj.getSjList().get(i)+"\n";
				}
			}
			obj.setSjExcelDatas(sjExcelDatas);
			
			
			String syExcelDatas = "";
			for (int i = 0; obj.getSyList() != null && i < obj.getSyList().size(); i++) {
				if(i == obj.getSyList().size() - 1){
					syExcelDatas += obj.getSyList().get(i);
				}else{
					syExcelDatas += obj.getSyList().get(i)+"\n";
				}
			}
			obj.setSyExcelDatas(syExcelDatas);
			

			String scblqxExcelDatas = "";
			for (int i = 0; obj.getScblqxList() != null && i < obj.getScblqxList().size(); i++) {
				if(i == obj.getScblqxList().size() - 1){
					scblqxExcelDatas += obj.getScblqxList().get(i);
				}else{
					scblqxExcelDatas += obj.getScblqxList().get(i)+"\n";
				}
			}
			obj.setScblqxExcelDatas(scblqxExcelDatas);
			
			String zcblqxExcelDatas = "";
			for (int i = 0; obj.getZcblqxList() != null && i < obj.getZcblqxList().size(); i++) {
				if(i == obj.getZcblqxList().size() - 1){
					zcblqxExcelDatas += obj.getZcblqxList().get(i);
				}else{
					zcblqxExcelDatas += obj.getZcblqxList().get(i)+"\n";
				}
			}
			obj.setZcblqxExcelDatas(zcblqxExcelDatas);
			
		}
	}

	/**
	 * 
	 * @Description 故障保留列表分页数据 
	 * @CreateTime 2017年10月31日 上午11:14:30
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryMonitorAllPageList(FailureKeep failureKeep)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = failureKeep.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		failureKeep.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		failureKeep.getParamsMap().put("userId", user.getId());
		failureKeep.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(failureKeep.getPagination());
		List<FailureKeep> list = failureKeepMapper.queryMonitorAllPageList(failureKeep);
		
		this.convertStr2List(list);
		
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					FailureKeep param = new FailureKeep();
					param.setId(id);
					List<FailureKeep> newRecordList = failureKeepMapper.queryMonitorAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, failureKeep.getPagination());
		}else{
			List<FailureKeep> newRecordList = new ArrayList<FailureKeep>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				FailureKeep param = new FailureKeep();
				param.setId(id);
				newRecordList = failureKeepMapper.queryMonitorAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, failureKeep.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, failureKeep.getPagination());
		}
	}

	/**
	 * @Description 保存/提交 故障保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 故障保留
	 * @return 故障保留id
	 * @throws BusinessException
	 */
	@Override
	public String save(FailureKeep failureKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人

		//新增故障保留 
		insertSelective(failureKeep,uuid,czls,user);

		//修改器材/工具
		materialToolService.updateMaterialToolList(failureKeep.getMaterialToolList(), ProjectBusinessEnum.FAILUREKEEP.getId(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);

		//新增附件
		attachmentService.eidtAttachment(failureKeep.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);

		//新增b_s2_01301 故障保留-监控
		insertFailureKeepJK(failureKeep,user,czls,uuid,FailureKeepJKEnum.SCBL.getId());

		//新增b_s2_01302 故障保留-其他
		insertSCFailureKeepQT(failureKeep,user,czls,uuid);
		
		if(FailureKeepStatusEnum.ASSESSMENT.getId().equals(failureKeep.getZt())){//如果是提交，写入待办事件
			StringBuilder builder=new StringBuilder();
			builder.append("请审批 ").append(failureKeep.getFjzch()).append("飞机的").append(failureKeep.getBldh()).append("故障保留");
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), failureKeep.getId(), failureKeep.getBldh(), null, failureKeep.getFjzch(),
					UndoEnum.GZBL.getId(), NodeEnum.NODE3.getId(), builder.toString(), null, null, "produce:reservation:fault:main:04", failureKeep.getId(), null);		
		//根据待办业务id=故障保留 id 和 jd=5 和状态 =1待处理 来修改状态=2已处理，办理人id=当前人id，反馈时间=sysdate			
			todorsService.updateToDo(failureKeep.getId(), NodeEnum.NODE5.getId(), UndoStatusEnum.DCL.getId(), user.getId());
			
		}
		return uuid;
	}

	/**
	 * 新增 b_s2_01302 故障保留-其他
	 * @Description 
	 * @CreateTime 2018-1-29 下午9:13:27
	 * @CreateBy 雷伟
	 * @param failureKeep
	 * @param user
	 * @param czls
	 * @param mainid
	 */
	private void insertSCFailureKeepQT(FailureKeep failureKeep, User user,String czls, String mainid) {

		List<FailureKeepQT> qtList = new ArrayList<FailureKeepQT>();
		Date whsj = commonService.getSysdate();

		//类型:1影响服务程度、
		for (int i = 0; null != failureKeep.getYxfwdmList() && i < failureKeep.getYxfwdmList().size(); i++) {
			FailureKeepQT qtObj = new FailureKeepQT();
			qtObj.setId(UUID.randomUUID().toString());
			qtObj.setMainid(mainid);
			qtObj.setLx(FailureKeepQTEnum.SERVICE.getId()+"");
			qtObj.setSz(failureKeep.getYxfwdmList().get(i));
			qtObj.setDprtcode(user.getJgdm());    //组织机构
			qtObj.setWhbmid(user.getBmdm());	  //维护部门id
			qtObj.setWhrid(user.getId());		  //维护人id
			qtObj.setWhsj(whsj);
			qtList.add(qtObj);
		}

		//2涉及部门
		for (int i = 0; null != failureKeep.getSjbmdmList() && i < failureKeep.getSjbmdmList().size(); i++) {
			FailureKeepQT qtObj = new FailureKeepQT();
			qtObj.setId(UUID.randomUUID().toString());
			qtObj.setMainid(mainid);
			qtObj.setLx(FailureKeepQTEnum.DEPT.getId()+"");
			qtObj.setSz(failureKeep.getSjbmdmList().get(i));
			qtObj.setDprtcode(user.getJgdm());    //组织机构
			qtObj.setWhbmid(user.getBmdm());	  //维护部门id
			qtObj.setWhrid(user.getId());		  //维护人id
			qtObj.setWhsj(whsj);
			qtList.add(qtObj);
		}

		//3运行限制
		for (int i = 0; null != failureKeep.getYxxzdmList() && i < failureKeep.getYxxzdmList().size(); i++) {
			FailureKeepQT qtObj = new FailureKeepQT();
			qtObj.setId(UUID.randomUUID().toString());
			qtObj.setMainid(mainid);
			qtObj.setLx(FailureKeepQTEnum.LIMIT.getId()+"");
			qtObj.setSz(failureKeep.getYxxzdmList().get(i));
			qtObj.setDprtcode(user.getJgdm());    //组织机构
			qtObj.setWhbmid(user.getBmdm());	  //维护部门id
			qtObj.setWhrid(user.getId());		  //维护人id
			qtObj.setWhsj(whsj);
			qtList.add(qtObj);
		}

		//4保留原因代码
		for (int i = 0; null != failureKeep.getBlyydmList() && i < failureKeep.getBlyydmList().size(); i++) {
			FailureKeepQT qtObj = new FailureKeepQT();
			qtObj.setId(UUID.randomUUID().toString());
			qtObj.setMainid(mainid);
			qtObj.setLx(FailureKeepQTEnum.CODE.getId()+"");
			qtObj.setSz(failureKeep.getBlyydmList().get(i));
			qtObj.setDprtcode(user.getJgdm());    //组织机构
			qtObj.setWhbmid(user.getBmdm());	  //维护部门id
			qtObj.setWhrid(user.getId());		  //维护人id
			qtObj.setWhsj(whsj);
			qtList.add(qtObj);
		}

		if (null != qtList && qtList.size() > 0) {
			failureKeepQTMapper.insert4Batch(qtList);
			//添加新增日志
			commonRecService.write(mainid, TableEnum.B_S2_01302,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,mainid);
		}
	}

	/**
	 * @Description 新增b_s2_01301 故障保留-监控
	 * @CreateTime 2018-1-29 下午8:25:46
	 * @CreateBy 雷伟
	 * @param failureKeep
	 */
	private void insertFailureKeepJK(FailureKeep failureKeep,User user,String czls,String mainid,Integer JD) {

		List<FailureKeepJK> jkList = new ArrayList<FailureKeepJK>();

		Date whsj = commonService.getSysdate();

		//首次保留期限
		if(null != failureKeep.getScBlqx() && JD == FailureKeepJKEnum.SCBL.getId())
		{
			FailureKeepJK jkObj = new FailureKeepJK();
			jkObj.setId(UUID.randomUUID().toString());
			jkObj.setMainid(mainid);
			jkObj.setJklbh(MonitorProjectEnum.CAL.getCode());
			jkObj.setJkflbh(MonitorProjectEnum.CAL.getFl());
			jkObj.setJkz(new SimpleDateFormat("yyyy-MM-dd").format(failureKeep.getScBlqx()));
			jkObj.setJd(JD);
			jkObj.setJkbs(FailureKeepJKEnum.JKYES.getId());
			jkObj.setDprtcode(user.getJgdm());    //组织机构
			jkObj.setWhbmid(user.getBmdm());	  //维护部门id
			jkObj.setWhrid(user.getId());		  //维护人id
			jkObj.setWhsj(whsj);
			jkList.add(jkObj);
		}

		//再次保留期限
		if(null != failureKeep.getZcBlqx() && JD == FailureKeepJKEnum.ZCBL.getId())
		{
			FailureKeepJK jkObj = new FailureKeepJK();
			jkObj.setId(UUID.randomUUID().toString());
			jkObj.setMainid(mainid);
			jkObj.setJklbh(MonitorProjectEnum.CAL.getCode());
			jkObj.setJkflbh(MonitorProjectEnum.CAL.getFl());
			jkObj.setJkz(new SimpleDateFormat("yyyy-MM-dd").format(failureKeep.getZcBlqx()));
			jkObj.setJd(JD);
			jkObj.setJkbs(FailureKeepJKEnum.JKYES.getId());
			jkObj.setDprtcode(user.getJgdm());    //组织机构
			jkObj.setWhbmid(user.getBmdm());	  //维护部门id
			jkObj.setWhrid(user.getId());		  //维护人id
			jkObj.setWhsj(whsj);
			jkList.add(jkObj);
		}

		//飞行时间
		if(StringUtils.isNotBlank(failureKeep.getBlfxsj()))
		{
			FailureKeepJK jkObj = new FailureKeepJK();
			jkObj.setId(UUID.randomUUID().toString());
			jkObj.setMainid(mainid);
			jkObj.setJklbh(MonitorProjectEnum.FH.getCode());
			jkObj.setJkflbh(MonitorProjectEnum.FH.getFl());
			jkObj.setJkz(failureKeep.getBlfxsj());
			jkObj.setJd(JD);
			jkObj.setJkbs(FailureKeepJKEnum.JKYES.getId());
			jkObj.setDprtcode(user.getJgdm());    //组织机构
			jkObj.setWhbmid(user.getBmdm());	  //维护部门id
			jkObj.setWhrid(user.getId());		  //维护人id
			jkObj.setWhsj(whsj);
			jkList.add(jkObj);
		}

		//飞行循环
		if(StringUtils.isNotBlank(failureKeep.getBlfxxh()))
		{
			FailureKeepJK jkObj = new FailureKeepJK();
			jkObj.setId(UUID.randomUUID().toString());
			jkObj.setMainid(mainid);
			jkObj.setJklbh(MonitorProjectEnum.FC.getCode());
			jkObj.setJkflbh(MonitorProjectEnum.FC.getFl());
			jkObj.setJd(JD);
			jkObj.setJkz(failureKeep.getBlfxxh());
			jkObj.setJkbs(FailureKeepJKEnum.JKYES.getId());
			jkObj.setDprtcode(user.getJgdm());    //组织机构
			jkObj.setWhbmid(user.getBmdm());	  //维护部门id
			jkObj.setWhrid(user.getId());		  //维护人id
			jkObj.setWhsj(whsj);
			jkList.add(jkObj);
		}

		if (null != jkList && jkList.size() > 0) {
			failureKeepJkMapper.insert4Batch(jkList);
			//添加新增日志
			commonRecService.write(mainid, TableEnum.B_S2_01301,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,mainid);
		}
	}

	/**
	 * @Description 新增故障保留
	 * @CreateTime 2017年9月27日 下午1:43:30
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @param uuid 故障保留id
	 * @param czls 流水号
	 * @param user 当前登录人
	 */
	private void insertSelective(FailureKeep failureKeep, String uuid,String czls,User user)throws BusinessException {

		// 验证飞机是否存在
		this.validationFjzch(failureKeep,user);

		//当页面没有填写故障保留编号时
		if(failureKeep.getBldh() == null || "".equals(failureKeep.getBldh())){ 
			String bldh=setBldh(failureKeep,user); //根据故障获取最新故障保留单号
			failureKeep.setBldh(bldh);
		}else{
			//验证验证故障保留单编号 唯一
			validationFailureKeep(failureKeep);
		}
		failureKeep.setZcblbs(WhetherEnum.NO.getId()); 	  //再次保留标识默认0
		failureKeep.setDprtcode(user.getJgdm());          //组织机构
		failureKeep.setId(uuid);                          //id
		failureKeep.setWhdwid(user.getBmdm());	          //部门id
		failureKeep.setWhrid(user.getId());		          //用户id

		//新增故障保留
		this.switchHourToMinute(failureKeep);//飞行时间：转换为分钟
		failureKeepMapper.insertSelective(failureKeep);

		//添加新增日志
		commonRecService.write(uuid, TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid);

	}

	private void switchHourToMinute(FailureKeep failureKeep) {
		// 是时间类型的监控项目
		if(StringUtils.isNotBlank(failureKeep.getFxsj())){
			failureKeep.setFxsj(StringAndDate_Util.convertToMinuteStr(failureKeep.getFxsj()));
		}
		if(StringUtils.isNotBlank(failureKeep.getBlfxsj())){
			failureKeep.setBlfxsj(StringAndDate_Util.convertToMinuteStr(failureKeep.getBlfxsj()));
		}
	}

	/**
	 * @Description 验证飞机权限
	 * @CreateTime 2017年9月25日 下午3:04:16
	 * @CreateBy 林龙
	 * @param record
	 * @param user
	 * @throws BusinessException
	 */
	private void validationFjzch(FailureKeep failureKeep, User user) throws BusinessException {
		List<String> fjzchList = new ArrayList<String>();
		fjzchList.add(failureKeep.getFjzch());
		planeModelDataService.existsAircraft4145Expt(user.getId(), user.getUserType(), failureKeep.getDprtcode(), fjzchList);
	}

	/**
	 * @Description 根据故障保留单对象获取最新故障保留单编号
	 * @CreateTime 2017年9月27日 下午1:48:51
	 * @CreateBy 林龙
	 * @param failureKeep  故障保留对象
	 * @param user 当前用户
	 * @return
	 * @throws BusinessException 
	 */
	private String setBldh(FailureKeep failureKeep,User user) throws BusinessException {
		FailureKeep fai = new FailureKeep(); //new 故障保留
		boolean b = true;
		String bldh = null;
		while(b){
			try {
				//采番获得技术文件编号-参数组织机构，代码：JSPG  例：技术评估单号-PG-2016-流水号（0000）
				bldh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.DDBLD.getName());
			}
			catch (SaibongException e) {
				e.printStackTrace();
			}
			fai.setBldh(bldh);
			fai.setDprtcode(user.getJgdm());
			//根据技术文件对象查询故障保留数量
			int i = failureKeepMapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return bldh;
	}

	/**
	 * @Description //验证验证故障保留单编号 唯一
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 */
	private void validationFailureKeep(FailureKeep failureKeep)throws BusinessException {
		int  iNum = failureKeepMapper.queryCount(failureKeep);
		if (iNum > 0) {
			throw new BusinessException("该故障保留单编号已存在!");
		}
	}

	/**
	 * @Description 删除
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 */
	@Override
	public void delete(FailureKeep failureKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		// 验证单据状态是否已变更
		validation4CurrentZt(failureKeep.getId(), Integer.valueOf((String) failureKeep.getParamsMap().get("currentZt")));

		//添加新增日志
		commonRecService.write(failureKeep.getId(), TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,failureKeep.getId());
		//删除故障保留
		failureKeepMapper.deleteByPrimaryKey(failureKeep.getId());
		failureKeepJkMapper.deleteByMainid(failureKeep.getId());
		failureKeepQTMapper.deleteByMainid(failureKeep.getId());
		//删除待办
		todorsService.deleteToDo(failureKeep.getId(), null, null);
	}

	/**
	 * @Description 根据故障保留id查询故障保留信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public FailureKeep getInfoById(FailureKeep failureKeep)throws BusinessException {
		FailureKeep result = failureKeepMapper.getInfoById(failureKeep);

		//获取从表：保留期限、飞行时间、飞行循环
		List<FailureKeepJK> scjkList = failureKeepJkMapper.selectByMainidAndJd(failureKeep.getId(), FailureKeepJKEnum.SCBL.getId()+"");
		if(null != scjkList  && scjkList.size() > 0){
			for (FailureKeepJK failureKeepJK : scjkList) {
				if (MonitorProjectEnum.CAL.getFl().equals(failureKeepJK.getJkflbh())) {
					try {
						result.setScBlqx(new SimpleDateFormat("yyyy-MM-dd").parse(failureKeepJK.getJkz()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (MonitorProjectEnum.FH.getFl().equals(failureKeepJK.getJkflbh())) {
					result.setBlfxsj(failureKeepJK.getJkz());
				} else if (MonitorProjectEnum.FC.getFl().equals(failureKeepJK.getJkflbh())) {
					result.setBlfxxh(failureKeepJK.getJkz());
				}
			}
		}
		
		List<FailureKeepJK> zcjkList = failureKeepJkMapper.selectByMainidAndJd(failureKeep.getId(), FailureKeepJKEnum.ZCBL.getId()+"");
		if(null != zcjkList  && zcjkList.size() > 0){
			for (FailureKeepJK failureKeepJK : zcjkList) {
				if (MonitorProjectEnum.CAL.getFl().equals(failureKeepJK.getJkflbh())) {
					try {
						result.setZcBlqx(new SimpleDateFormat("yyyy-MM-dd").parse(failureKeepJK.getJkz()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (MonitorProjectEnum.FH.getFl().equals(failureKeepJK.getJkflbh())) {
					result.setZcblfxsj(failureKeepJK.getJkz());
				} else if (MonitorProjectEnum.FC.getFl().equals(failureKeepJK.getJkflbh())) {
					result.setZcblfxxh(failureKeepJK.getJkz());
				}
			}
		}

		//获取其他代码信息
		List<FailureKeepQT> qtList = failureKeepQTMapper.selectByMainid(failureKeep.getId());
		if(null != qtList  && qtList.size() > 0){
			for (FailureKeepQT failureKeepQT : qtList) {
				List<String> list = new ArrayList<String>();
				String sz = failureKeepQT.getSz();
				if(failureKeepQT.getLx().equals(FailureKeepQTEnum.SERVICE.getId()+"")){
					list = result.getYxfwdmList()==null?list:result.getYxfwdmList();
					list.add(sz);
					result.setYxfwdmList(list);
				}else if(failureKeepQT.getLx().equals(FailureKeepQTEnum.DEPT.getId()+"")){
					list = result.getSjbmdmList()==null?list:result.getSjbmdmList();
					list.add(sz);
					result.setSjbmdmList(list);
				}else if(failureKeepQT.getLx().equals(FailureKeepQTEnum.LIMIT.getId()+"")){
					list = result.getYxxzdmList()==null?list:result.getYxxzdmList();
					list.add(sz);
					result.setYxxzdmList(list);
				}else if(failureKeepQT.getLx().equals(FailureKeepQTEnum.CODE.getId()+"")){
					list = result.getBlyydmList()==null?list:result.getBlyydmList();
					list.add(sz);
					result.setBlyydmList(list);
				}
			}
		}

		return result;
	}

	/**
	 * @Description 修改保存故障保留
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 故障保留id
	 * @throws BusinessException
	 */
	@Override
	public String update(FailureKeep failureKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人

		//修改故障保留
		this.switchHourToMinute(failureKeep);//飞行时间：转换为分钟
		updateByPrimaryKeySelective(failureKeep,czls,user);

		//修改器材/工具
		materialToolService.updateMaterialToolList(failureKeep.getMaterialToolList(), ProjectBusinessEnum.FAILUREKEEP.getId(), failureKeep.getId(), czls, failureKeep.getId(), failureKeep.getDprtcode(), LogOperationEnum.EDIT);

		//修改附件
		attachmentService.eidtAttachment(failureKeep.getAttachmentList(), failureKeep.getId(), czls, failureKeep.getId(), failureKeep.getDprtcode(), LogOperationEnum.EDIT);

		String mainid = failureKeep.getId();
		//删除，新增b_s2_01301 故障保留-监控
		failureKeepJkMapper.deleteByMainid(mainid);
		insertFailureKeepJK(failureKeep,user,czls,mainid,FailureKeepJKEnum.SCBL.getId());

		//删除，新增b_s2_01302 故障保留-其他
		failureKeepQTMapper.deleteByMainid(mainid);
		insertSCFailureKeepQT(failureKeep,user,czls,mainid);
		
		
		if(FailureKeepStatusEnum.ASSESSMENT.getId().equals(failureKeep.getZt())){//如果是提交，写入待办事件			
			StringBuilder builder=new StringBuilder();
			builder.append("请审批").append(failureKeep.getFjzch()).append("飞机的").append(failureKeep.getBldh()).append("故障保留");
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), failureKeep.getId(), failureKeep.getBldh(), null, failureKeep.getFjzch(),
					UndoEnum.GZBL.getId(), NodeEnum.NODE3.getId(), builder.toString(), null, null, "produce:reservation:fault:main:04", failureKeep.getId(), null);			
			//根据待办业务id=故障保留 id 和 jd=5 和状态 =1待处理 来修改状态=2已处理，办理人id=当前人id，反馈时间=sysdate
			todorsService.updateToDo(failureKeep.getId(), NodeEnum.NODE5.getId(), UndoStatusEnum.DCL.getId(), user.getId());		
		}

		return failureKeep.getId();
	}

	/**
	 * @Description 修改故障保留
	 * @CreateTime 2017年9月27日 下午4:40:24
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @param czls 流水号
	 */
	private void updateByPrimaryKeySelective(FailureKeep failureKeep,String czls,User user)throws BusinessException {

		// 验证飞机是否存在
		this.validationFjzch(failureKeep,user);

		// 验证单据状态是否已变更
		validation4CurrentZt(failureKeep.getId(), Integer.valueOf((String) failureKeep.getParamsMap().get("currentZt")));

		// 验证145是否存在
		this.validation145(failureKeep);
		
		failureKeep.setWhrid(user.getBmdm()); //维护部门
		failureKeep.setWhrid(user.getId());   //维护人
		failureKeepMapper.updateByPrimaryKeySelective(failureKeep);
		//添加修改日志
		commonRecService.write(failureKeep.getId(), TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,failureKeep.getId());
	}

	/**
	 * 
	 * @Description 验证145是否存在
	 * @CreateTime 2018年4月8日 上午10:58:45
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	private void validation145(FailureKeep failureKeep) throws BusinessException{
		FailureKeep failureKeepnew = failureKeepMapper.selectByPrimaryKey(failureKeep.getId());
		if(failureKeepnew.getBs145() != null){
			if (failureKeepnew.getBs145() == 0 && failureKeep.getBs145() == 1) {
				throw new BusinessException("您没有135工单选择权限!");
			}
			if (failureKeepnew.getBs145() == 1 && failureKeep.getBs145() == 0) {
				throw new BusinessException("您没有145工单选择权限!");
			}
			
		}
		
	}
	
	/**
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer zt)throws BusinessException {
		int bzt = failureKeepMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * @Description 验证单据再次保留标识是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZcblbs(String id, Integer zcblbs)throws BusinessException {
		int bzcblbs = failureKeepMapper.getCurrentZcblbs4Validation(id);
		if (bzcblbs != zcblbs) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * @Description 批准故障保留单
	 * @CreateTime 2017年9月28日 上午11:01:01
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String approval(FailureKeep failureKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();				//流水号
		User user = ThreadVarUtil.getUser();				  	//当前登陆人
		// 验证飞机是否存在
		this.validationFjzch(failureKeep,user);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(failureKeep.getId(), Integer.valueOf((String) failureKeep.getParamsMap().get("currentZt")));

		failureKeep.setScPzrbmid(user.getBmdm());			   	//批复部门id
		failureKeep.setScPzrid(user.getId());				   	//批复人id

		failureKeepMapper.updateByPrimaryKeySelective(failureKeep);

		//添加技术评估单已批准日志
		commonRecService.write(failureKeep.getId(), TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,failureKeep.getId());
		
		//同步故障保留单到海航易建运控系统
		syncHhyk(failureKeep.getId(), DeferredDefectStatusEnum.APPROVED);
		
		
		if(FailureKeepStatusEnum.APPROVALDOWN.getId().equals(failureKeep.getZt())){//如果是审批驳回写待办
			//故障保留批准待办
			String whrid=failureKeepMapper.getInfoById(failureKeep).getWhrid();
			StringBuilder builder=new StringBuilder();
			builder.append(failureKeep.getFjzch()).append("飞机的").append(failureKeep.getBldh()).append("故障保留已经驳回,请重新提交");
			todorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), failureKeep.getId(), failureKeep.getBldh(), null, failureKeep.getFjzch(),
					UndoEnum.GZBL.getId(), NodeEnum.NODE5.getId(), builder.toString(), null, whrid, "produce:reservation:fault:main:02", failureKeep.getId(), null);	
			
			todorsService.updateToDo(failureKeep.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());
			
		}	
		if(FailureKeepStatusEnum.APPROVAL.getId().equals(failureKeep.getZt())){//如果是批准通过写待办
			//故障保留批准待办
			todorsService.updateToDo(failureKeep.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());
		}	       
		return failureKeep.getId();
	}
	
	/**
	 * @Description 同步故障保留单到海航易建运控系统
	 * @CreateTime 2018年2月2日 下午3:08:26
	 * @CreateBy 韩武
	 * @param id
	 */
	private void syncHhyk(String id, DeferredDefectStatusEnum deferredDefectStatusEnum){
		
		// 海航运控接口是否启用
		if(SysConfig.isHhykEnabled()){
			
			// 查询详情
			FailureKeep failureKeep = failureKeepMapper.queryDetailById(id);
			
			// 对象转换
			DeferredDefect record = new DeferredDefect();
			record.setDdCode(failureKeep.getBldh());	// 保留单号
			record.setRegNum(failureKeep.getFjzch());	// 飞机注册号
			record.setType(1);	// 类型（1：DD）
			record.setExpDate(getDqrq(failureKeep));	// 到期日期
			record.setDescription(failureKeep.getGzms());	// 故障报告
			record.setDocument(failureKeep.getBlyj());	// 保留依据
			record.setDeadline(getXfqx(failureKeep));	// 修复期限
			record.setRunLtdContent(failureKeep.getYxxzsm());	// 运行限制内容
			record.setState(deferredDefectStatusEnum.getId());	// 状态（70：已审批，20：已关闭）
			record.setUpdateBy(ThreadVarUtil.getUser().getRealname());	// 更新人
			record.setUpdateDate(new Date());	// 更新时间
			record.setSyncCode(failureKeep.getId());	// 同步Code(主键id)
			record.setReason(failureKeep.getZcBlyy() != null ? failureKeep.getZcBlyy() : failureKeep.getBlyy());	// 优先为再次保留原因，如无则为保留原因
			record.setOccuDate(failureKeep.getZcSqrq() != null ? failureKeep.getZcSqrq() : failureKeep.getScSqrq());	// 办理日期
			//record.setRunLtdStart();	// 运行限制开始日期
			//record.setRunLtdEnd();	// 运行限制结束日期
			
			// 故障保留单同步
			deferredDefectService.doSync(record);
		}
	}
	
	/**
	 * @Description 获取到期日期
	 * @CreateTime 2018年2月2日 下午4:46:55
	 * @CreateBy 韩武
	 * @param failureKeep
	 */
	private Date getDqrq(FailureKeep failureKeep){
		List<FailureKeepJK> list = failureKeep.getMonitors();
		if(list != null && !list.isEmpty()){
			for (FailureKeepJK failureKeepJK : list) {
				if(MonitorProjectEnum.CAL.getCode().equals(failureKeepJK.getJklbh())){
					try {
						return DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, failureKeepJK.getJkz());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @Description 获取修复期限
	 * @CreateTime 2018年2月2日 下午5:03:04
	 * @CreateBy 韩武
	 * @param failureKeep
	 * @return
	 */
	private String getXfqx(FailureKeep failureKeep){
		List<FailureKeepJK> list = failureKeep.getMonitors();
		if(list != null && !list.isEmpty()){
			String xfqs = "";
			for (FailureKeepJK failureKeepJK : list) {
				String jkz = failureKeepJK.getJkz();
				if(MonitorProjectEnum.isTime(failureKeepJK.getJklbh())){
					jkz = StringAndDate_Util.convertToHour(failureKeepJK.getJkz());
				}
				xfqs += jkz + MonitorProjectEnum.getUnit(failureKeepJK.getJklbh()) + ",";
			}
			xfqs = xfqs.substring(0, xfqs.length() - 1);
			return xfqs;
		}
		return null;
	}

	/**
	 * @Description  再次保留故障保留
	 * @CreateTime 2017年9月28日 下午4:51:25
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留
	 * @return 故障保留id
	 */
	@Override
	public String saveagainkeep(FailureKeep failureKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();			//流水号
		User user = ThreadVarUtil.getUser();				//当前登陆人

		failureKeep.setZcblbs(WhetherEnum.YES.getId());  	//再次标识标识为1
		// 验证单据状态是否已变更
		validation4CurrentZt(failureKeep.getId(), Integer.valueOf((String) failureKeep.getParamsMap().get("currentZt")));

		failureKeep.setWhrid(user.getBmdm()); 				//维护部门
		failureKeep.setWhrid(user.getId());   				//维护人
		this.switchHourToMinute(failureKeep);               //飞行时间：转换为分钟
		failureKeepMapper.updateByPrimaryKeySelective(failureKeep);
		//添加修改日志
		commonRecService.write(failureKeep.getId(), TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,failureKeep.getId());

		//新增附件
		attachmentService.eidtAttachment(failureKeep.getAttachmentList(), failureKeep.getId(), czls, failureKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);


		String mainid = failureKeep.getId();
		//取消首次保留监控：b_s2_01301 故障保留-监控
		failureKeepJkMapper.updateJkBsByMainidAndJD(mainid,FailureKeepJKEnum.SCBL.getId()+"",FailureKeepJKEnum.JKNO.getId()+"");

		//删除再保留监控：b_s2_01301 故障保留-监控
		failureKeepJkMapper.deleteByMainidAndJD(mainid,FailureKeepJKEnum.ZCBL.getId()+"");

		//新增再次保留监控：b_s2_01301 故障保留-监控
		insertFailureKeepJK(failureKeep,user,czls,mainid,FailureKeepJKEnum.ZCBL.getId());
		
		//同步故障保留单到海航易建运控系统
		syncHhyk(failureKeep.getId(), DeferredDefectStatusEnum.APPROVED);

		return failureKeep.getId();
	}

	/**
	 * @Description 完成故障保留
	 * @CreateTime 2017年9月29日 上午10:07:23
	 * @CreateBy 林龙
	 * @param failureKeep 故障保留对象
	 * @throws BusinessException
	 */
	@Override
	public void updateEndModal(FailureKeep failureKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) failureKeep.getParamsMap().get("currentZt"));
		// 验证单据状态是否已变更
		validation4CurrentZt(failureKeep.getId(), iCurrentZt);

		FailureKeep failureKeepNew = failureKeepMapper.selectByPrimaryKey(failureKeep.getId());
		if(failureKeepNew.getGdid() != null){//当工单id不为null时判断工单状态是否是已关闭/指定结束
			if(failureKeepNew.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = workOrderNew145Service.selectByPrimaryKey(failureKeepNew.getGdid());
				if(workorder145 != null){
					if(workorder145.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder145.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可关闭故障保留!");
					}
				}
			}else{
				Workorder workorder=workOrderNewService.selectByPrimaryKey(failureKeepNew.getGdid());
				if(workorder!=null){
					if(workorder.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可关闭故障保留!");
					}
				}
			}
		}
		failureKeep.setZt(FailureKeepStatusEnum.WANCHENG.getId()); //状态为指定结束

		failureKeep.setGbrid(user.getId());
		failureKeep.setGbrbmid(user.getBmdm());

		//完成
		failureKeepMapper.updateByPrimaryKeySelective(failureKeep);

		//添加新增日志
		commonRecService.write(failureKeep.getId(), TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.WANCHEN, UpdateTypeEnum.UPDATE,failureKeep.getId());

		//新增附件
		attachmentService.eidtAttachment(failureKeep.getAttachmentList(), failureKeep.getId(), czls, failureKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		//同步故障保留单到海航易建运控系统
		syncHhyk(failureKeep.getId(), DeferredDefectStatusEnum.COLSED);
	}

	/**
	 * @Description 指定结束故障保留
	 * @CreateTime 2017年9月29日 上午11:42:15
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Override
	public void updategConfirm(FailureKeep failureKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) failureKeep.getParamsMap().get("currentZt"));
		// 验证单据状态是否已变更
		validation4CurrentZt(failureKeep.getId(), iCurrentZt);

		// 验证单据再次保留标识是否已变更
		validation4CurrentZcblbs(failureKeep.getId(), Integer.valueOf((String) failureKeep.getParamsMap().get("currentzcblbs")));

		FailureKeep failureKeepNew = failureKeepMapper.selectByPrimaryKey(failureKeep.getId());
		if(failureKeepNew.getGdid() != null){//当工单id不为null时判断工单状态是否是已关闭/指定结束
			if(failureKeepNew.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = workOrderNew145Service.selectByPrimaryKey(failureKeepNew.getGdid());
				if(workorder145 != null){
					if(workorder145.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder145.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可指定结束故障保留!");
					}
				}
			}else{
				Workorder workorder=workOrderNewService.selectByPrimaryKey(failureKeepNew.getGdid());
				if(workorder != null){
					if(workorder.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可指定结束故障保留!");
					}
				}
			}
		}
		failureKeep.setZt(FailureKeepStatusEnum.CLOSE.getId()); //状态为指定结束
		failureKeep.setGbrid(user.getId());
		failureKeep.setGbrbmid(user.getBmdm());

		//指定结束	
		failureKeepMapper.updateByPrimaryKeySelective(failureKeep);

		//添加新增日志
		commonRecService.write(failureKeep.getId(), TableEnum.B_S2_013,user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,failureKeep.getId());

		//同步故障保留单到海航易建运控系统
		syncHhyk(failureKeep.getId(), DeferredDefectStatusEnum.COLSED);
		
		//指定结束删除待办
		todorsService.deleteToDo(failureKeep.getId(), null, null);
	}

	/**
	 * @Description 飞行记录本查询故障保留单
	 * @CreateTime 2017年10月24日 下午2:08:13
	 * @CreateBy 韩武
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<FailureKeep> queryListByFLB(FlightSheet sheet) throws BusinessException {
		
		List<FailureKeep> list = failureKeepMapper.queryListByFLB(sheet);
		// 把字符串数据转换成集合
		this.convertStr2List(list);
		return list;
	}

	/**
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:57:23
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Override
	public String updatefilesDownSub(FailureKeep failureKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		//新增附件
		attachmentService.eidtAttachment(failureKeep.getAttachmentList(), failureKeep.getId(), czls, failureKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);

		return failureKeep.getId();
	}

	@Override
	public List<AircraftinfoStatus> getDefaultFHFC(FailureKeep failureKeep) {
		return aircraftinfoStatusMapper.getDefaultFHFC(failureKeep);
	}
}