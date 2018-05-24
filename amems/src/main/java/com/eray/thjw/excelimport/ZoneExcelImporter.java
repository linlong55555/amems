package com.eray.thjw.excelimport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.ComponentTypeEnum;
import enu.LogOperationEnum;
import enu.PartsPositionEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-14 下午12:01:31
 * @CreateBy 孙霁	
 */
@Service("zoneExcelImporter")
public class ZoneExcelImporter extends AbstractExcelImporter<ZoneStation>{

	@Resource
	private ActTypeMapper actTypeMapper;
	@Resource
	private ZoneStationMapper zoneStationMapper;
	@Resource
	private CommonRecService commonRecService;
	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<ZoneStation>> datasMap)
			throws ExcelImportException {
		// TODO Auto-generated method stub
		//获取登入user
		User user = ThreadVarUtil.getUser();
		//验证机型数据表 是否拥有机型
		List<ActType> actTypeList = actTypeMapper.findByDprtcode(user.getJgdm());
		List<ZoneStation> zoneStationList = new ArrayList<ZoneStation>();
		boolean b;
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的飞行记录本数据
			List<ZoneStation> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			ZoneStation zoneSztation;
			for (int i = 0; i < datas.size(); i++) {
				b = true;
				zoneSztation = datas.get(i);
				// 非空验证
				if(StringUtils.isBlank(zoneSztation.getJx())){
					addErrorMessage("第"+(i+3)+"行，机型不能为空");
				}
				if(zoneSztation.getSz() == null ){
					addErrorMessage("第"+(i+3)+"行，区域不能为空");
				}
				// 非中文验证
				if(!StringUtils.isBlank(zoneSztation.getJx()) && Utils.Str.isChinese(zoneSztation.getJx())){
					addErrorMessage("第"+(i+3)+"行，机型不能含有中文");
				}
				if(!StringUtils.isBlank(zoneSztation.getSz()) && Utils.Str.isChinese(zoneSztation.getSz())){
					addErrorMessage("第"+(i+3)+"行，区域不能含有中文");
				}
				// 长度验证
				if(Utils.Str.getLength(zoneSztation.getJx()) > 50){
					addErrorMessage("第"+(i+3)+"行，机型最大长度为50");
				}
				if(Utils.Str.getLength(zoneSztation.getSz()) > 50){
					addErrorMessage("第"+(i+3)+"行，区域最大长度为50");
				}
				if(Utils.Str.getLength(zoneSztation.getMs()) > 100){
					addErrorMessage("第"+(i+3)+"行，描述最大长度为100");
				}
				for (ActType actType : actTypeList) {
					if(zoneSztation.getJx() != null && zoneSztation.getJx().equals(actType.getFjjx())){
						b = false;
					}
				}
				if(zoneSztation.getJx() != null && b){
					addErrorMessage("第"+(i+3)+"行，"+zoneSztation.getJx()+"机型不存在");
				}
			}
			ZoneStation zoneSztation1;
			ZoneStation zoneSztation2;
			//冒泡对比是否含有重复数据
			for (int i = 0; i < datas.size()-1; i++) {
				b = false;
				for (int a = i+1; a < datas.size(); a++) {
					zoneSztation1 = datas.get(i);
					zoneSztation2 = datas.get(a);
					if(zoneSztation1.getJx() != null && zoneSztation2.getJx() != null &&
							zoneSztation1.getSz() != null && zoneSztation2.getSz() != null){
						if(zoneSztation1.getJx().equals(zoneSztation2.getJx()) && zoneSztation1.getSz().equals(zoneSztation2.getSz())){
							b = true;
						}
					}
				}
				if(b){
					addErrorMessage("第"+(i+3)+"行，出现重复数据，请确保机型和区域数据唯一");
				}
			}
		}
	}

	@Override
	public int writeToDB(Map<Integer, List<ZoneStation>> datas)
			throws ExcelImportException {
		
		//获取登入user
		User user = ThreadVarUtil.getUser();
		List<ZoneStation> list = new ArrayList<ZoneStation>();
		list.addAll(datas.get(0));
		ZoneStation zoneStation = new ZoneStation();
		zoneStation.setDprtcode(user.getJgdm());
		zoneStation.setLx(1);
		//操作指令
		LogOperationEnum cz = LogOperationEnum.IMPORT;
		//操作流水id
		String czls = UUID.randomUUID().toString();
		//获取当前组织机构下的原有数据
		List<ZoneStation> oldList = zoneStationMapper.selectByJx(zoneStation);
		boolean b = false;
		for (ZoneStation newZoneStation : list) {
			b = true;
			for (ZoneStation oldZoneStation : oldList) {
				if(newZoneStation.getJx().equals(oldZoneStation.getJx()) && newZoneStation.getSz().equals(oldZoneStation.getSz())){
					newZoneStation.setId(oldZoneStation.getId());
					b = false;
				}
			}
			if(b){
				newZoneStation.setId(UUID.randomUUID().toString());
				zoneStationMapper.insertSelective(newZoneStation);
				commonRecService.write(newZoneStation.getId(), TableEnum.D_023, user.getId(), czls, cz, UpdateTypeEnum.SAVE,newZoneStation.getId());
			}else{
				zoneStationMapper.updateById(newZoneStation);
				commonRecService.write(newZoneStation.getId(), TableEnum.D_023, user.getId(), czls, cz, UpdateTypeEnum.UPDATE,newZoneStation.getId());
			}
		}
		
		
		
		return 0;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<ZoneStation>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 结果集
				Map<Integer, List<ZoneStation>> resultMap = new TreeMap<Integer, List<ZoneStation>>();
				// 循环sheet
				for (Integer sheetIndex : totalMapList.keySet()) {
					// sheet对应装机清单数据
					List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
					List<ZoneStation> list = new ArrayList<ZoneStation>();
					
					ZoneStation zoneStation;
					Map<Integer, String> bean;
					for (int i = 0; i < mapList.size(); i++) {
						bean = mapList.get(i);
						zoneStation = new ZoneStation();
						/*
						 * 读取excel值
						 */
						zoneStation.setJx(bean.get(0));	// 机型
						zoneStation.setSz(bean.get(1));	// 区域
						zoneStation.setMs(bean.get(2));	// 描述
						zoneStation.setLx(1);	// 类型默认为区域
						zoneStation.setZt(1);	// 状态
						zoneStation.setWhdwid(user.getBmdm());
						zoneStation.setWhrid(user.getId());
						zoneStation.setWhsj(new Date());
						zoneStation.setDprtcode(user.getJgdm());
						list.add(zoneStation);
					}
					resultMap.put(sheetIndex, list);
				}
				return resultMap;
	}
}
