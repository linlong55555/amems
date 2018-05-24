package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.produce.dao.InstallationListEffective2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.InstallationListEffective2Init;
import com.eray.thjw.produce.service.InstallationListEffectiveService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.github.pagehelper.PageHelper;

import enu.common.HasOrNoEnum;
import enu.project2.MonitorProjectEnum;

/** 
 * @Description 装机清单-生效区service实现类
 * @CreateTime 2017年10月11日 下午2:23:52
 * @CreateBy 韩武	
 */
@Service
public class InstallationListEffectiveServiceImpl implements InstallationListEffectiveService{
	
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	
	@Resource
	private InstallationListEffective2InitMapper installationListEffective2InitMapper;
	
	@Resource
	private ZoneStationMapper zoneStationMapper;

	/**
	 * @Description 查询装机清单-生效区的分页数据
	 * @CreateTime 2017年10月11日 下午2:22:35
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryPageableList(InstallationListEffective record) {
		PageHelper.startPage(record.getPagination());
		
		// 查询装机清单数据
		List<InstallationListEffective> list = installationListEffectiveMapper.queryList(record);
		// 设置初始化值
		setInitDatas(record, list);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}
	
	/**
	 * @Description 设置初始化值
	 * @CreateTime 2017年10月11日 下午2:33:52
	 * @CreateBy 韩武
	 * @param record
	 * @param list
	 */
	private void setInitDatas(InstallationListEffective record, List<InstallationListEffective> list){
		List<InstallationListEffective2Init> initDatas = installationListEffective2InitMapper.queryByAircraft(record);
		if(initDatas.size()>0){
			Map<String, List<InstallationListEffective2Init>> map = new HashMap<String, List<InstallationListEffective2Init>>();
			for (InstallationListEffective2Init i : initDatas) {
				List<InstallationListEffective2Init> resultList = map.get(i.getMainid());
				if(resultList == null){
					resultList = new ArrayList<InstallationListEffective2Init>();
					map.put(i.getMainid(), resultList);
				}
				resultList.add(i);
			}
			for (InstallationListEffective install : list) {
				install.setInitDatas(map.get(install.getId()));
				// 分钟转化为小时
				switchMinuteToHour(install);
			}
		}
	}
	
	/**
	 * @Description 分钟转化为小时
	 * @CreateTime 2017年9月23日 下午4:45:42
	 * @CreateBy 韩武
	 * @param record
	 */
	private void switchMinuteToHour(InstallationListEffective record){
		
		if(record != null){
			List<InstallationListEffective2Init> list = record.getInitDatas();
			if(list != null && !list.isEmpty()){
				for (InstallationListEffective2Init init : list) {
					// 是时间类型的监控项目
					if(MonitorProjectEnum.isTime(init.getJklbh())){
						init.setZssyy(StringAndDate_Util.convertToHour(init.getZssyy()));
					}
				}
			}
			
			if(StringUtils.isNotBlank(record.getTsn())){
				record.setTsn(StringAndDate_Util.convertToHour(record.getTsn()));
			}
			if(StringUtils.isNotBlank(record.getTso())){
				record.setTso(StringAndDate_Util.convertToHour(record.getTso()));
			}
		}
		
	}

	/**
	 * @Description 查询装机清单-生效区的数据集合
	 * @CreateTime 2017年10月27日 上午10:44:51
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<InstallationListEffective> queryList(
			InstallationListEffective record) {
		
		// 查询装机清单数据
		List<InstallationListEffective> list = installationListEffectiveMapper.queryList(record);
		// 设置初始化值
		setInitDatas(record, list);
		
		return list;
	}

	/**
	 * @Description 查询装机清单详情
	 * @CreateTime 2017年11月27日 下午5:09:11
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public InstallationListEffective queryDetail(
			InstallationListEffective record) {
		
		InstallationListEffective detail = installationListEffectiveMapper.queryDetail(record);
		
		// 分钟转化为小时
		switchMinuteToHour(detail);
		
		return detail;
	}
	
	/**
	 * @Description 获取导出数据
	 * @CreateTime 2017年12月18日 上午11:06:03
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<InstallationListEditable> getExportList(InstallationListEditable record){
		InstallationListEffective param = new InstallationListEffective();
		param.setFjzch(record.getFjzch());
		param.setDprtcode(record.getDprtcode());
		param.setWz(record.getWz());
		param.setZjjlx(record.getZjjlx());
		param.setParamsMap(record.getParamsMap());
		
		// 查询装机清单数据
		List<InstallationListEffective> list = installationListEffectiveMapper.queryList(param);
		// 设置初始化值
		setInitDatas(param, list);
		
		List<InstallationListEditable> resultList = new ArrayList<InstallationListEditable>();
		for (InstallationListEffective install : list) {
			
			// 判断是否维护初始化信息
			List<InstallationListEffective2Init> inits = install.getInitDatas();
			
			int initFlag = 0;
			if(inits != null && !inits.isEmpty()){
				for (InstallationListEffective2Init init : inits) {
					if(StringUtils.isNotBlank(init.getZssyy())){
						initFlag = 1;
					}
				}
			}
			
			install.getParamsMap().put("initFlag", initFlag);
			
			// 监控类型
			String jklx = "";
			if(install.getSkbs() == null){
				install.setSkbs(0);
			}
			if(install.getSsbs() == null){
				install.setSsbs(0);
			}
			if(install.getSkbs() == 1 && install.getSsbs() == 0){
				jklx = "时控件";
			}
			if(install.getSkbs() == 0 && install.getSsbs() == 1){
				jklx = "时寿件";
			}
			if(install.getSkbs() == 1 && install.getSsbs() == 1){
				jklx = "时控件/时寿件";
			}
			if(jklx.equals("")){
				jklx = "非控制件";
			}
			install.getParamsMap().put("jklxstr", jklx);
			
			// 拼接初始化值
			String calstr = "";
			String timestr = "";
			String cyclestr = "";
			if(inits != null && !inits.isEmpty()){
				for (InstallationListEffective2Init init : inits) {
					if(MonitorProjectEnum.isCalendar(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
						calstr += init.getZssyy();
					}
					if(MonitorProjectEnum.isTime(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
						timestr += init.getZssyy() + MonitorProjectEnum.getUnit(init.getJklbh()) +  " ";
					}
					if(MonitorProjectEnum.isLoop(init.getJklbh()) && StringUtils.isNotBlank(init.getZssyy())){
						cyclestr += init.getZssyy() + MonitorProjectEnum.getUnit(init.getJklbh()) + " ";
					}
				}
			}
			install.getParamsMap().put("calstr", calstr);
			install.getParamsMap().put("timestr", timestr);
			install.getParamsMap().put("cyclestr", cyclestr);
			InstallationListEditable edit = new InstallationListEditable(install);
			edit.setTbbs(HasOrNoEnum.NO.getId());
			resultList.add(edit);
		}
		
		return resultList;
	}

}
