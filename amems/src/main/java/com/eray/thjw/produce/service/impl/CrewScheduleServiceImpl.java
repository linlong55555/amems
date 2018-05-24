package com.eray.thjw.produce.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.SystemException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.CrewScheduleMapper;
import com.eray.thjw.produce.dao.CrewScheduleObjectMapper;
import com.eray.thjw.produce.po.CrewSchedule;
import com.eray.thjw.produce.po.CrewScheduleObject;
import com.eray.thjw.produce.service.CrewScheduleObjectService;
import com.eray.thjw.produce.service.CrewScheduleService;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.ScheduleStatusEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class CrewScheduleServiceImpl implements CrewScheduleService {
	@Autowired
	private CrewScheduleMapper crewScheduleMapper;
	@Autowired
	private CrewScheduleObjectService crewScheduleObjectService;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private CrewScheduleObjectMapper crewScheduleObjectMapper;
	@Autowired
	private PlaneModelDataService planeModelDataService;

	/** 排班类型-飞机 */
	private static final Integer PBLX_PLANE = 1;

	/** 排班类型-机务 */
	private static final Integer PBLX_CREW = 2;

	Map<String, Object> resultMap = new HashMap<String, Object>();

	@Override
	public List<CrewSchedule> queryAllPageList(CrewSchedule crewSchedule) throws Exception {
		List<CrewSchedule> crewScheduleList = crewScheduleMapper.queryAllPageList(crewSchedule);
		Date scheduleDateBegin = crewSchedule.getScheduleDateBegin();
		Date scheduleDateEnd = crewSchedule.getScheduleDateEnd();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		long oneday = 24 * 60 * 60 * 1000;
		Long time = scheduleDateBegin.getTime();
		List<CrewSchedule> newCrewduleList = new ArrayList<CrewSchedule>();
		while (time <= scheduleDateEnd.getTime()) {
			Date d = new Date(time);
			if (crewScheduleList.size() == 0) {
				CrewSchedule crew = new CrewSchedule();
				String crewScheduleID = UUID.randomUUID().toString();
				crew.setPbrq(d);
				crew.setId(crewScheduleID);
				newCrewduleList.add(crew);
			} else {
				for (int i = 0; i < crewScheduleList.size(); i++) {
					String pbrq = df.format(crewScheduleList.get(i).getPbrq());
					if (df.format(d).equals(pbrq)) {
						newCrewduleList.add(crewScheduleList.get(i));
						break;
					}
					if (!df.format(d).equals(pbrq) && i == (crewScheduleList.size() - 1)) {
						CrewSchedule crew = new CrewSchedule();
						String crewScheduleID = UUID.randomUUID().toString();
						crew.setPbrq(d);
						crew.setId(crewScheduleID);
						newCrewduleList.add(crew);
						break;
					}
				}
			}
			time += oneday;
		}

		return newCrewduleList;
	}

	@Override
	public CrewSchedule queryCrewScheduleById(String id) throws Exception {

		return crewScheduleMapper.queryCrewScheduleById(id);
	}

	/**
	 * 查询飞机排班数据
	 */
	@Override
	public List<PlaneData> queryPlaneScheduling(PlaneData pd) {
		pd.getParamsMap().put("userId", ThreadVarUtil.getUser().getId());
		return crewScheduleMapper.queryPlaneScheduling(pd);
	}

	@Override
	public void updateCrewScheduleById(CrewSchedule crewSchedule) throws Exception {
		List<String> deleteList=new ArrayList<String>();//记录删除的id
		List<String> addList=new ArrayList<String>();//新增的id
		if (crewSchedule.getZt() == 0) {
			CrewScheduleObject crewScheduleObject = new CrewScheduleObject();
			crewScheduleObject.setMainid(crewSchedule.getId());
			crewScheduleObjectService.updateZtByMainid(crewScheduleObject);
		} else {
			User user = ThreadVarUtil.getUser();
			String recCrewScheduleObjectId = UUID.randomUUID().toString();
			CrewScheduleObject crewScheduleObject = new CrewScheduleObject();
			crewScheduleObject.setMainid(crewSchedule.getId());
			int count = crewScheduleMapper.selectByPbrq(crewSchedule);
			if (!(crewSchedule.getJxs()).equals("")) {
				addList.addAll(query(crewSchedule, 21, recCrewScheduleObjectId));
			}
			if((crewSchedule.getJxs()).equals("")){
				crewScheduleObject.setDxlx(21);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getJxby()).equals("")) {
				addList.addAll(query(crewSchedule, 25, recCrewScheduleObjectId));				
			}
			if((crewSchedule.getJxby()).equals("")){
				crewScheduleObject.setDxlx(25);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getJxy()).equals("")) {
				addList.addAll(query(crewSchedule, 23, recCrewScheduleObjectId));			
			}
			if((crewSchedule.getJxy()).equals("")){
				crewScheduleObject.setDxlx(23);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getJxyby()).equals("")) {
				addList.addAll(query(crewSchedule, 26, recCrewScheduleObjectId));				
			}
			if((crewSchedule.getJxyby()).equals("")){
				crewScheduleObject.setDxlx(26);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getDzs()).equals("")) {
				addList.addAll(query(crewSchedule, 22, recCrewScheduleObjectId));				
			}
			if((crewSchedule.getDzs()).equals("")){
				crewScheduleObject.setDxlx(22);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getDzby()).equals("")) {
				addList.addAll(query(crewSchedule, 27, recCrewScheduleObjectId));			
			}
			if((crewSchedule.getDzby()).equals("")){
				crewScheduleObject.setDxlx(27);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getDzy()).equals("")) {
				addList.addAll(query(crewSchedule, 24, recCrewScheduleObjectId));			
			}
			if((crewSchedule.getDzy()).equals("")){
				crewScheduleObject.setDxlx(24);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getDzyby()).equals("")) {
				addList.addAll(query(crewSchedule, 28, recCrewScheduleObjectId));			
			}
			if((crewSchedule.getDzyby()).equals("")){
				crewScheduleObject.setDxlx(28);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (!(crewSchedule.getMccdd()).equals("")) {
				addList.addAll(query(crewSchedule, 30, recCrewScheduleObjectId));
			}
			if((crewSchedule.getMccdd()).equals("")){
				crewScheduleObject.setDxlx(30);
				deleteList.addAll(queryExeitDelete(crewScheduleObject));
			}
			if (addList.size() > 0) {
				commonRecService.write("id", addList, TableEnum.B_S_01001, ThreadVarUtil.getUser().getId(),
						recCrewScheduleObjectId, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, crewSchedule.getId());
			}
			crewScheduleMapper.updateCrewScheduleById(crewSchedule);
			if(deleteList.size()>0){
				commonRecService.write("id", deleteList, TableEnum.B_S_01001, ThreadVarUtil.getUser().getId(),
						recCrewScheduleObjectId, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, crewSchedule.getId());
				for (String id : deleteList) {
					crewScheduleObjectMapper.deleteById(id);
				}
			}
			if (count > 0) {
				
				commonRecService.write(crewSchedule.getId(), TableEnum.B_S_010, user.getId(), recCrewScheduleObjectId,
						LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, crewSchedule.getId());
			} else {
				commonRecService.write(crewSchedule.getId(), TableEnum.B_S_010, user.getId(), recCrewScheduleObjectId,
						LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, crewSchedule.getId());
			}
		}
	}

	/**
	 * 保存飞机排班数据
	 */
	@Override
	public void savePlaneScheduling(List<CrewSchedule> crewScheduleList) {
		String czls = UUID.randomUUID().toString();
		// 取消原有飞机排班数据
		if (!crewScheduleList.isEmpty()) {
			doCancelPlaneScheduling(crewScheduleList.get(0), false);
		}
		for (CrewSchedule crewSchedule : crewScheduleList) {
			// 获取上班时间、下班时间
			Calendar cal = Calendar.getInstance();
			cal.setTime(crewSchedule.getPbrq());
			cal.set(Calendar.HOUR_OF_DAY, crewSchedule.getSbsjXs());
			crewSchedule.setSbsj(cal.getTime());
			cal.set(Calendar.HOUR_OF_DAY, crewSchedule.getXbsjXs());
			crewSchedule.setXbsj(cal.getTime());

			crewSchedule.setId(UUID.randomUUID().toString());
			crewSchedule.setPblx(PBLX_PLANE);
			crewSchedule.setWhrid(ThreadVarUtil.getUser().getId());
			crewSchedule.setWhsj(new Date());
			crewSchedule.setZt(ScheduleStatusEnum.COMMIT.getId());
			// 保存排班数据
			crewScheduleMapper.savePlaneScheduling(crewSchedule);
			// 操作流水
			crewSchedule.setCzls(czls);
			if(crewSchedule.getOperation() == 1){
				commonRecService.write(crewSchedule.getId(), TableEnum.B_S_010, ThreadVarUtil.getUser().getId(), czls,
						LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, crewSchedule.getId());
				crewSchedule.getCrewScheduleObject().setLogOperationEnum(LogOperationEnum.SAVE_WO);
			}else if(crewSchedule.getOperation() == 2){
				commonRecService.write(crewSchedule.getId(), TableEnum.B_S_010, ThreadVarUtil.getUser().getId(), czls,
						LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.UPDATE, crewSchedule.getId());
				crewSchedule.getCrewScheduleObject().setLogOperationEnum(LogOperationEnum.SUBMIT_WO);
			}

			// 保存飞机排班对象
			crewSchedule.getCrewScheduleObject().setMainid(crewSchedule.getId());
			crewSchedule.getCrewScheduleObject().setCzls(czls);
			crewScheduleObjectService.savePlaneScheduling(crewSchedule.getCrewScheduleObject());
		}
	}

	/**
	 * 取消飞机排班
	 */
	@Override
	public void doCancelPlaneScheduling(CrewSchedule crewSchedule, boolean writeRec) {
		// 验证是否有飞机权限
		List<String> list = new ArrayList<String>();
		list.add(crewSchedule.getCrewScheduleObject().getDxid());
		if(!planeModelDataService.existsAircraft(ThreadVarUtil.getUser().getId(), 
				ThreadVarUtil.getUser().getUserType(), ThreadVarUtil.getUser().getJgdm(), list)){
			throw new SystemException("无该飞机权限！");
		}
		if (Utils.Str.isEmpty(crewSchedule.getCzls())) {
			crewSchedule.setCzls(UUID.randomUUID().toString());
			crewSchedule.setLogOperationEnum(LogOperationEnum.ZUOFEI);
		}
		List<String> pbIds = crewScheduleMapper.getCancelPlaneScheduling(crewSchedule);
		List<String> pbObjectIds = crewScheduleObjectService.getCancelPlaneScheduling(crewSchedule);

		// 取消排班数据
		if (pbIds.size() > 0) {
			// 插入日志
			if(writeRec){
				commonRecService.write("id", pbIds, TableEnum.B_S_010, ThreadVarUtil.getUser().getId(),
						crewSchedule.getCzls(), crewSchedule.getLogOperationEnum(), UpdateTypeEnum.DELETE,
						crewSchedule.getId());
			}
			crewScheduleMapper.cancelPlaneScheduling(pbIds);
		}

		// 取消飞机排班对象
		if (pbObjectIds.size() > 0) {
			// 插入日志
			if(writeRec){
				commonRecService.write("id", pbObjectIds, TableEnum.B_S_01001, ThreadVarUtil.getUser().getId(),
						crewSchedule.getCzls(), crewSchedule.getLogOperationEnum(), UpdateTypeEnum.DELETE,
						crewSchedule.getId());
			}
			crewScheduleObjectService.doCancelPlaneScheduling(pbObjectIds);
		}

	}

	/**
	 * 加载飞机排班数据
	 */
	@Override
	public List<CrewSchedule> loadPlaneScheduling(CrewSchedule crewSchedule) {
		return crewScheduleMapper.loadPlaneScheduling(crewSchedule);
	}

	/**
	 * 查询飞机排班数据-飞机视图
	 */
	@Override
	public PlaneData queryPlaneSchedulingInPlaneView(Map<String, Object> paramMap) {
		return crewScheduleMapper.queryPlaneSchedulingInPlaneView(paramMap);
	}

	private List<String> query(CrewSchedule crewSchedule, Integer dxlx, String recCrewScheduleObjectId) {
//		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> resultList=new ArrayList<String>();
		CrewScheduleObject obj = null;
		String id = null;
		List<String> list;
		list = new ArrayList<String>();
		String dx[] = null;
		if (dxlx == 21) {
			dx = crewSchedule.getJxs().split(",");
		}
		if (dxlx == 22) {
			dx = crewSchedule.getDzs().split(",");
		}
		if (dxlx == 23) {
			dx = crewSchedule.getJxy().split(",");
		}
		if (dxlx == 24) {
			dx = crewSchedule.getDzy().split(",");
		}
		if (dxlx == 25) {
			dx = crewSchedule.getJxby().split(",");
		}
		if (dxlx == 26) {
			dx = crewSchedule.getJxyby().split(",");
		}
		if (dxlx == 27) {
			dx = crewSchedule.getDzby().split(",");
		}
		if (dxlx == 28) {
			dx = crewSchedule.getDzyby().split(",");
		}
		if (dxlx == 30) {
			dx = crewSchedule.getMccdd().split(",");
		}
		for (int i = 0; i < dx.length; i++) {
			obj = new CrewScheduleObject();
			obj.setDxid(dx[i]);
			obj.setDxlx(dxlx);
			obj.setMainid(crewSchedule.getId());
			obj.setZt(crewSchedule.getZt());
			id = UUID.randomUUID().toString();
			obj.setId(id);
			int num = crewScheduleObjectService.getIdByMainid(obj);
			if (num == 0) {
				list.add(dx[i]);
			}
			if (num != 0) {
				crewScheduleObjectService.updateZtByCrewScheduleObject(obj);
			}
		}
		obj = new CrewScheduleObject();
		obj.setDxlx(dxlx);
		obj.setMainid(crewSchedule.getId());
		List<String> listId = crewScheduleObjectService.getIdByZt(obj);
		if (listId.size() > 0) {
			commonRecService.write("id", listId, TableEnum.B_S_01001, ThreadVarUtil.getUser().getId(),
					recCrewScheduleObjectId, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, crewSchedule.getId());
			obj = new CrewScheduleObject();
			obj.setDxlx(dxlx);
			obj.setMainid(crewSchedule.getId());
			crewScheduleObjectService.deleteByZt(obj);
		}
		if (list.size() > 0) {
//			List<String> crewScheduleObjectList = new ArrayList<String>();
			for (String string : list) {
				obj = new CrewScheduleObject();
				obj.setDxid(string);
				obj.setDxlx(dxlx);
				obj.setMainid(crewSchedule.getId());
				obj.setZt(crewSchedule.getZt());
				id = UUID.randomUUID().toString();
				obj.setId(id);
				try {
					crewScheduleObjectService.updatecrewshecduleObject(obj);
					resultList.add(id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
//			map.put("add", crewScheduleObjectList);
		}
		obj = new CrewScheduleObject();
		obj.setDxlx(dxlx);
		obj.setMainid(crewSchedule.getId());
		crewScheduleObjectService.updateZt(obj);
		return resultList;
	}
	private List<String> queryExeitDelete(CrewScheduleObject crewScheduleObject) {		
		List<String> idList=crewScheduleObjectMapper.getIdByZt(crewScheduleObject);		
		return idList;
	}
}
