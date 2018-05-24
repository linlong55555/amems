package com.eray.pbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.GroupDao;
import com.eray.pbs.dao.WorkCenterDao;
import com.eray.pbs.po.Group;
import com.eray.pbs.po.MIConfigBase;
import com.eray.pbs.po.WorkCenter;

@Component
@Transactional(readOnly = true)
public class ReadConfigService {
	@Autowired
	private WorkCenterDao workCenterDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	private Map<String, Group> groupMap = new HashMap<String, Group>(); //key=name
	private Map<String, WorkCenter> workcenterNameMap = new HashMap<String, WorkCenter>(); //key=workCenter
	private Map<Long, WorkCenter> workcenterIdMap = new HashMap<Long, WorkCenter>(); //key=id
	private Map<String, MIConfigBase> miConfigMap = new HashMap<String, MIConfigBase>(); //key=workCenter

	//获取所有的Group以name作为Key
	public Map<String, Group> getGroupMap() {
		List<Group> groupList = groupDao.getAllGroup();
		for (int i = 0; groupList != null && i < groupList.size(); i++) {
			Group group = groupList.get(i);
			if(groupMap.get(group.getName()) == null){
				groupMap.put(group.getName(), group);
			}
		}
		return groupMap;
	}
	
	//获取所有的WorkCenter以name作为Key
	public Map<String, WorkCenter> getWorkcenterNameMap() {
		List<WorkCenter> workcenterList = workCenterDao.getAllWorkcenter();
		for (int i = 0; workcenterList != null && i < workcenterList.size(); i++) {
			WorkCenter workCenter = workcenterList.get(i);
			if(workcenterNameMap.get(workCenter.getWorkCenter()) == null){
				workcenterNameMap.put(workCenter.getWorkCenter(), workCenter);
			}
		}
		return workcenterNameMap;
	}
	
	//获取所有的WorkCenter以Id作为Key
	public Map<Long, WorkCenter> getWorkcenterIdMap() {
		List<WorkCenter> workcenterList = workCenterDao.getAllWorkcenter();
		for (int i = 0; workcenterList != null && i < workcenterList.size(); i++) {
			WorkCenter workCenter = workcenterList.get(i);
			if(workcenterIdMap.get(workCenter.getId()) == null){
				workcenterIdMap.put(workCenter.getId(), workCenter);
			}
		}
		return workcenterIdMap;
	}

	//获取所有的WorkCenter的MI配置
	public Map<String, MIConfigBase> getMiConfigMap() {
		RowMapper<MIConfigBase> rowMapper =  ParameterizedBeanPropertyRowMapper.newInstance(MIConfigBase.class);
		// 刷新所有Stage为空的NRC工单
		String sql = " SELECT " +
					 "		workcenter_ as workcenter , " +
					 "		mechanichours_ as mechanichours, " +
					 "		inspectionhours_ as inspectionhours " +
					 " from pbs_miconfig WHERE status_ = '1' ";
		List<MIConfigBase> miList = jdbcTemplate.query(sql, rowMapper);
		for (int i = 0; miList != null && i < miList.size(); i++) {
			MIConfigBase miConfigBase = miList.get(i);
			if(miConfigMap.get(miConfigBase.getWorkcenter()) == null){
				miConfigMap.put(miConfigBase.getWorkcenter(), miConfigBase);
			}
		}
		
		return miConfigMap;
	}
}
