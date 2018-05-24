package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.AircraftStatusService;

import enu.produce.InstalledPositionEnum;
import enu.project2.AirworthinessStatusEnum;
import enu.project2.EngineeringOrderStatusEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.TechnicalStatusEnum;
import enu.project2.WorkCardStatusEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-23 下午2:07:03
 * @CreateBy 孙霁	
 */
@Service
public class AircraftStatusServiceImpl implements AircraftStatusService{

	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;
	@Resource
	private WorkorderMapper workorderMapper;
	/**
	 * 
	 * @Description 查询飞机状态
	 * @CreateTime 2017-10-23 下午2:12:04
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 */
	@Override
	public Aircraftinfo selectBystatus(Aircraftinfo aircraftinfo) {
		return aircraftinfoMapper.selectBystatus(aircraftinfo);
	}
	
	/**
	 * 
	 * @Description 查询飞机状态主数据
	 * @CreateTime 2017-10-23 下午2:23:14
	 * @CreateBy 孙霁
	 * @param aircraftinfoStatus
	 * @return
	 */
	@Override
	public List<AircraftinfoStatus> queryAll(
			AircraftinfoStatus aircraftinfoStatus) {
		//查询所有数据
		List<AircraftinfoStatus> aircraftinfoStatusList = aircraftinfoStatusMapper.selectList4forecast(aircraftinfoStatus);
		//用于返回的数据
		List<AircraftinfoStatus> resultList = new ArrayList<AircraftinfoStatus>();
		//创建一个字符串 用于拼接
		StringBuffer str = new StringBuffer();
		//创建一个boolean用于判断
		boolean b;
		//循环查询的数据
		for (AircraftinfoStatus air : aircraftinfoStatusList) {
			//默认boolean
			b = true;
			//循环返回数据
			for (AircraftinfoStatus result : resultList) {
				//判断返回数的监控值是否为空，如果为空那么拼接自己的监控项
				if(result.getParamsMap().get("jkz") == null || "".equals(result.getParamsMap().get("jkz"))){
					//清空str字符串
					str.delete(0, str.length());
					str.append(result.getJkflbh()).append("#_#").append(result.getJklbh()).append("#_#").append(result.getLjz()==null?0:result.getLjz());
					result.getParamsMap().put("jkz",str.toString());
				} 
				//判断返回数据是否出现重复位置，如果有，那么拼接监控项
				if((result.getWz() == InstalledPositionEnum.BODY.getId() && result.getWz() == air.getWz()) ||
						(result.getWz() == air.getWz() && air.getJh().equals(result.getJh()) && air.getXlh().equals(result.getXlh()))){
					//清空str字符串
					str.delete(0, str.length());
					str.append(result.getParamsMap().get("jkz")).append(",").append(air.getJkflbh()).append("#_#").append(air.getJklbh()).append("#_#").append(air.getLjz()==null?0:air.getLjz());
					result.getParamsMap().put("jkz",str.toString());
					b = false;
				}
				
			}
			//判断是否添加数据到返回数据里
			if(b){
				resultList.add(air);
			}
		}
		if(aircraftinfoStatusList.size() <= 1){
			for (AircraftinfoStatus result : resultList) {
				//判断返回数的监控值是否为空，如果为空那么拼接自己的监控项
				if(result.getParamsMap().get("jkz") == null || "".equals(result.getParamsMap().get("jkz"))){
					//清空str字符串
					str.delete(0, str.length());
					str.append(result.getJkflbh()).append("#_#").append(result.getJklbh()).append("#_#").append(result.getLjz()==null?0:result.getLjz());
					result.getParamsMap().put("jkz",str.toString());
				} 
			}
		}
		return resultList;
	}

	/**
	 * 
	 * @Description 查询未关闭的适航性资料
	 * @CreateTime 2017-10-23 下午2:23:14
	 * @CreateBy 孙霁
	 * @param aircraftinfoStatus
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryAllAir(Aircraftinfo aircraftinfo) {
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		//查询数据
		List<Aircraftinfo> airList = aircraftinfoMapper.queryAllAir(aircraftinfo);
		//子类状态map
		Map<String, Object> childrenStatusMap = new HashMap<String, Object>();
		//是否选中
		childrenStatusMap.put("selected", false);
		//是否打开
		childrenStatusMap.put("opened", true);
		//用于拼接数量
		List<String> countList = new ArrayList<String>();
		//用于拼接
		StringBuffer str = new StringBuffer();
		//标记
		boolean b = false;
		//获取技术类型数据
		this.getMap("jswjlx", "", "", "", "", airList, b, menuList, countList, childrenStatusMap, null);
		//适航性资料
		this.getMap("jswjid", "jswjlx", "jswjbh", "jswjbb", "jswjzt", airList, b, menuList, countList, childrenStatusMap, ProjectBusinessEnum.SHIHA.getId());
		//评估单
		this.getMap("pgdid", "jswjid", "pgdbh", "pgdbb", "pgdzt", airList, b, menuList, countList, childrenStatusMap, ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId());
		//EO
		this.getMap("eoid", "pgdid", "eobh", "eobb", "eozt", airList, b, menuList, countList, childrenStatusMap, ProjectBusinessEnum.EO.getId());
		//工单
		this.getMap("gdid", "eoid", "gdbh", "", "gdzt", airList, b, menuList, countList, childrenStatusMap, ProjectBusinessEnum.WORKORDER.getId());
		//工卡
		this.getMap("gdid", "pgdid", "gdbh", "", "gdzt", airList, b, menuList, countList, childrenStatusMap, ProjectBusinessEnum.WORK_CARD.getId());
		
		for (Map<String, Object> map : menuList) {
			str.delete(0, str.length());
			//不同的类型 输出不同的text
			if(map.get("menu_Enumtype") == null){//适航性资料状态
				str.append(map.get("id")).append(":");
				map.put("text", str.toString());
				map.put("icon", false);
			}else if(map.get("menu_Enumtype") == ProjectBusinessEnum.SHIHA.getId()){//适航性资料
				str.append(ProjectBusinessEnum.getName(Integer.valueOf(map.get("menu_Enumtype").toString()))).append(" (")
				   .append(map.get("menu_bh")).append(" R").append(map.get("menu_bb")).append(" ")
				   .append(AirworthinessStatusEnum.getName(Integer.valueOf(map.get("menu_zt").toString())))
				   .append(")");
				map.put("text", str.toString());
				map.put("icon", false);
				
			}else if(map.get("menu_Enumtype") == ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId()){//评估单
				str.append(ProjectBusinessEnum.getName(Integer.valueOf(map.get("menu_Enumtype").toString()))).append(" (")
				   .append(map.get("menu_bh")).append(" R").append(map.get("menu_bb")).append(" ")
				   .append(TechnicalStatusEnum.getName(Integer.valueOf(map.get("menu_zt").toString())))
				   .append(")");
				map.put("text", str.toString());
				map.put("icon", false);
				
			}else if(map.get("menu_Enumtype") == ProjectBusinessEnum.WORK_CARD.getId()){//工卡
				str.append(ProjectBusinessEnum.getName(Integer.valueOf(map.get("menu_Enumtype").toString()))).append(" (")
				   .append(map.get("menu_bh")).append(" R").append(map.get("menu_bb")).append(" ")
				   .append(WorkCardStatusEnum.getName(Integer.valueOf(map.get("menu_zt").toString())))
				   .append(")");
				map.put("text", str.toString());
				map.put("icon", "icon-file-alt");
				
			}else if(map.get("menu_Enumtype") == ProjectBusinessEnum.EO.getId()){//EO
				str.append(ProjectBusinessEnum.getName(Integer.valueOf(map.get("menu_Enumtype").toString()))).append(" (")
				   .append(map.get("menu_bh")).append(" R").append(map.get("menu_bb")).append(" ")
				   .append(EngineeringOrderStatusEnum.getName(Integer.valueOf(map.get("menu_zt").toString())))
				   .append(")");
				map.put("text", str.toString());
				map.put("icon",false);
				
			}else if(map.get("menu_Enumtype") == ProjectBusinessEnum.WORKORDER.getId()){//工单
				str.append(ProjectBusinessEnum.getName(Integer.valueOf(map.get("menu_Enumtype").toString()))).append(" (")
				   .append(map.get("menu_bh")).append(map.get("menu_bjh")==null?"":" {"+map.get("menu_bjh")+" "+(map.get("menu_xlh")==null?"}":map.get("menu_xlh"))+"}")
				   .append(" ").append(EngineeringOrderStatusEnum.getName(Integer.valueOf(map.get("menu_zt").toString())))
				   .append(")");
				map.put("text", str.toString());
				map.put("icon", "icon-file-alt");
				
			}
			//拼接数量
			for (String count : countList) {
				if(count != null && count.equals(map.get("id"))){
					map.put("count",map.get("count") == null?1:(Integer.valueOf(map.get("count").toString())+1));
				}
			}
			str.delete(0, str.length());
			str.append(map.get("text").toString()).append(" ").append(map.get("count") != null ? map.get("count") : "");
			map.put("text",str.toString());
		}
		return menuList;
	}
	/**
	 * 
	 * @Description 获取数据
	 * @CreateTime 2017-10-26 下午4:18:43
	 * @CreateBy 孙霁
	 * @param id
	 * @param parentId
	 * @param bh
	 * @param bb
	 * @param zt
	 * @param airList
	 * @param b
	 * @param menuList
	 * @param countList
	 * @param childrenStatusMap
	 * @param type
	 */
	private void getMap(String id, String parentId, String bh, String bb,String zt, List<Aircraftinfo> airList, boolean b,
			List<Map<String, Object>> menuList, List<String> countList,
			Map<String, Object> childrenStatusMap, Integer type){
				for (Aircraftinfo air : airList) {
					b = false;
					if(air.getParamsMap().get(id) != null && !"".equals(air.getParamsMap().get(id))){
						for (Map<String, Object> map : menuList) {
							if(map.get("id").equals(air.getParamsMap().get(id))){
								b = true;
							}
						}
					}else{
						b = true;
					}
					if(!b){
						Map<String, Object> childrenMap = new HashMap<String, Object>();
						childrenMap.put("id", air.getParamsMap().get(id));
						childrenMap.put("menu_bh", air.getParamsMap().get(bh));
						childrenMap.put("menu_bb", air.getParamsMap().get(bb));
						childrenMap.put("menu_zt", air.getParamsMap().get(zt));
						childrenMap.put("parent", air.getParamsMap().get(parentId) == null ? "#":air.getParamsMap().get(parentId));
						childrenMap.put("menu_Enumtype", type);
						childrenMap.put("menu_bjh", air.getParamsMap().get("bjh"));
						childrenMap.put("menu_xlh", air.getParamsMap().get("xlh"));
						childrenMap.put("state", childrenStatusMap);
						countList.add((String)air.getParamsMap().get(parentId));
						menuList.add(childrenMap);
					}
				}
	}

	/**
	 * 
	 * @Description 加载执行历史
	 * @CreateTime 2017-10-27 下午2:55:08
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return
	 */
	@Override
	public List<Workorder> queryAllPerformHistory(Workorder workorder) {
		return workorderMapper.queryAllPerformHistory(workorder);
	}
	
	/**
	 * @Description 装机清单生效时保存飞机状态数据，调用该方法前需要验证是否为一级部件
	 * @CreateTime 2017年10月30日 下午1:46:53
	 * @CreateBy 徐勇
	 * @param list 飞机状态数据
	 */
	public void save4installComponent(List<AircraftinfoStatus> list){
		if(list == null || list.size() == 0){
			return;
		}
		//判断是否有该装机位置的飞机状态数据
		AircraftinfoStatus aircraftinfoStatus = list.get(0);
		int count = this.aircraftinfoStatusMapper.selectCountByFjAndWz(aircraftinfoStatus.getDprtcode(), aircraftinfoStatus.getWz(), aircraftinfoStatus.getFjzch());
		if(count == 0){
			//执行批量新增
			this.aircraftinfoStatusMapper.insert4Batch(list);
		}else{
			//执行批量修改
			this.aircraftinfoStatusMapper.update4Batch(list);
		}
	}
	
	public void removeByZjqdid(String zjqdid){
		this.aircraftinfoStatusMapper.deleteByZjqdid(zjqdid);
	}
	
}
