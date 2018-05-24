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
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.ComponentTypeEnum;
import enu.PartsPositionEnum;
import enu.SynchronzeEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-14 下午12:01:31
 * @CreateBy 孙霁	
 */
@Service("flighthistoryExcelImporter")
public class FlighthistoryExcelImporter extends AbstractExcelImporter<FlightSheet>{

	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<FlightSheet>> datasMap)
			throws ExcelImportException {
		// TODO Auto-generated method stub
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 所有飞机注册号
		Map<String, String> fjzchList = getAllPlaneData(user.getJddm());
		// 有权限的飞机
		Map<String, String> authorityList = getAuthorityPlaneData();
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的飞行记录本数据
			List<FlightSheet> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			String fjzch = String.valueOf(this.getParam("fjzch")) ;
			if(!datas.isEmpty()  && !StringUtils.isBlank(fjzch)){
				// 飞机注册号验证
				if(fjzchList.containsKey(fjzch)){
					// 飞机权限验证
					if(!authorityList.containsKey(fjzch)){
						throw new ExcelImportException("无权添加该飞机下的装机清单数据！");
					}
				}else{
					throw new ExcelImportException("飞机注册号不存在！");
				}
				
				PlaneData planeData = new PlaneData(fjzch, ThreadVarUtil.getUser().getJgdm());
				// 飞行记录单验证
			/*	if(flightRecordSheetMapper.getCountByLoadingListImport(planeData) > 0){
					throw new ExcelImportException("请撤销飞机"+fjzch+"之前的飞行记录本后再导入！");
				}*/
				FlightSheet flightSheet;
				for (int i = 0; i < datas.size(); i++) {
					flightSheet = datas.get(i);
					//验证主单数据
					List<FlightSheetVoyage> flightSheetVoyageList = flightSheet.getFlightSheetVoyageList();
					// 非空验证
					if(StringUtils.isBlank(flightSheet.getFjzch())){
						addErrorMessage("第"+(i+3)+"行，飞机注册号不能为空");
					}
					if(flightSheet.getFxrq() == null ){
						addErrorMessage("第"+(i+3)+"行，飞行日期不能为空");
					}
					if(StringUtils.isBlank(flightSheet.getFxjlbh())){
						addErrorMessage("第"+(i+3)+"行，飞行记录本号不能为空");
					}
					if(StringUtils.isBlank(flightSheet.getJlbym())){
						addErrorMessage("第"+(i+3)+"行，飞行本页码不能为空");
					}
					// 非中文验证
					if(!StringUtils.isBlank(flightSheet.getFjzch()) && Utils.Str.isChinese(flightSheet.getFjzch())){
						addErrorMessage("第"+(i+3)+"行，飞机注册号不能含有中文");
					}
					if(!StringUtils.isBlank(flightSheet.getFxjlbh()) && Utils.Str.isChinese(flightSheet.getFxjlbh())){
						addErrorMessage("第"+(i+3)+"行，飞行记录本不能含有中文");
					}
					if(!StringUtils.isBlank(flightSheet.getJlbym()) && Utils.Str.isChinese(flightSheet.getJlbym())){
						addErrorMessage("第"+(i+3)+"行，飞行记本页码不能含有中文");
					}
					// 日期验证
					if(flightSheet.getFxrq() != null && flightSheet.getFxrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("第"+(i+3)+"行，飞行日期格式不正确");
					}
					// 长度验证
					if(Utils.Str.getLength(flightSheet.getFjzch()) > 20){
						addErrorMessage("第"+(i+3)+"行，飞机注册号最大长度为20");
					}
					if(Utils.Str.getLength(flightSheet.getFxjlbh()) > 20){
						addErrorMessage("第"+(i+3)+"行，飞行记录本最大长度为20");
					}
					if(Utils.Str.getLength(flightSheet.getFxjlbh()) > 20){
						addErrorMessage("第"+(i+3)+"行，飞行本页码最大长度为20");
					}
					for(int a=0;a<flightSheetVoyageList.size();a++){
						// 非空验证
						if(StringUtils.isBlank(flightSheetVoyageList.get(a).getHbh())){
							addErrorMessage("第"+(i+3+a)+"行，航班号不能为空");
						}
						// 非中文验证
						if(!StringUtils.isBlank(flightSheet.getFjzch()) && Utils.Str.isChinese(flightSheet.getFjzch())){
							addErrorMessage("第"+(i+3)+"行，飞机注册号不能含有中文");
						}
						
						
						
					}
					
				}
			}
		}
	}

	@Override
	public int writeToDB(Map<Integer, List<FlightSheet>> datas)
			throws ExcelImportException {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<FlightSheet>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 结果集
				Map<Integer, List<FlightSheet>> resultMap = new TreeMap<Integer, List<FlightSheet>>();
				// 循环sheet
				for (Integer sheetIndex : totalMapList.keySet()) {
					// sheet对应装机清单数据
					List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
					List<FlightSheet> list = new ArrayList<FlightSheet>();
					
					FlightSheet flightSheet;
					Map<Integer, String> bean;
					for (int i = 0; i < mapList.size(); i++) {
						bean = mapList.get(i);
						flightSheet = new FlightSheet();
						FlightSheetVoyage flightSheetVoyage = new FlightSheetVoyage();
						/*
						 * 读取excel值
						 */
						flightSheet.setFjzch(bean.get(1));	// 飞机注册号
						flightSheet.setJlbym(bean.get(4));	// 飞行本页码
						flightSheet.setFxrq(convertToDate(bean.get(2)));	// 飞行日期
						flightSheet.setFxjlbh(bean.get(3));	// 飞行记录本号
						//添加默认值
						flightSheet.setZt(99);	// 状态默认为历史
						flightSheet.setZdrid(user.getId());
						flightSheet.setZdbmid(user.getBmdm());
						flightSheet.setZdsj(new Date());
						flightSheet.setZt(1);
						
						flightSheetVoyage.setHbh(bean.get(5));	//航班号
						flightSheetVoyage.setQfz(bean.get(6));	//起飞站
						flightSheetVoyage.setZlz(bean.get(7));	//着落站
						flightSheetVoyage.setTcsj(convertToDate(bean.get(8)));	//停车时间
						flightSheetVoyage.setLdsj(convertToDate(bean.get(9)));	//落地时间
						flightSheetVoyage.setKcsj(convertToDate(bean.get(10)));	//开车时间
						flightSheetVoyage.setQfsj(convertToDate(bean.get(11)));	//起飞时间
						flightSheetVoyage.setSysjFz(convertToMoutin(bean.get(12), 1));	//使用时间-分钟
						flightSheetVoyage.setFxsjFz(convertToMoutin(bean.get(13), 1));	//飞行时间-分钟
						flightSheetVoyage.setFxxh(convertToInteger(bean.get(14), 1));	//起落循环
						flightSheetVoyage.setLxqlcs(convertToInteger(bean.get(15), 1));	//连续起落次数
						flightSheetVoyage.setF1SjFz(convertToMoutin(bean.get(16), 1));	//1#发时间-分钟
						flightSheetVoyage.setF1Xh(convertToInteger(bean.get(17), 1));	//1#发循环
						flightSheetVoyage.setF1Hytjl(convertToBigDecimal(bean.get(18)));	//1#发滑油添加量
						flightSheetVoyage.setF2SjFz(convertToMoutin(bean.get(19), 1));	//2#发时间-分钟
						flightSheetVoyage.setF2Xh(convertToInteger(bean.get(20), 1));	//2#发循环
						flightSheetVoyage.setF2Hytjl(convertToBigDecimal(bean.get(21)));	//2#发滑油添加量
						flightSheetVoyage.setF3SjFz(convertToMoutin(bean.get(22), 1));	//3#发时间-分钟
						flightSheetVoyage.setF3Xh(convertToInteger(bean.get(23), 1));	//3#发循环
						flightSheetVoyage.setF3Hytjl(convertToBigDecimal(bean.get(24)));	//3#发滑油添加量
						flightSheetVoyage.setF4SjFz(convertToMoutin(bean.get(25), 1));	//4#发时间-分钟
						flightSheetVoyage.setF4Xh(convertToInteger(bean.get(26), 1));	//4#发循环
						flightSheetVoyage.setF4Hytjl(convertToBigDecimal(bean.get(27)));	//4#发滑油添加量
						flightSheetVoyage.setApuSjFz(convertToMoutin(bean.get(28), 1));	//spu发时间-分钟
						flightSheetVoyage.setApuXh(convertToInteger(bean.get(29), 1));	//spu发循环
						flightSheetVoyage.setApuHytjl(convertToBigDecimal(bean.get(30)));	//spu发滑油添加量
						flightSheetVoyage.setIdgtksj(bean.get(31));	//IDG脱开时间
						flightSheetVoyage.setYyy(convertToBigDecimal(bean.get(32)));	//液压油
						flightSheetVoyage.setRyCyl(convertToBigDecimal(bean.get(33)));	//飞行前存油量
						flightSheetVoyage.setRyJyl(convertToBigDecimal(bean.get(34)));	//加油量
						flightSheetVoyage.setRySyyl(convertToBigDecimal(bean.get(35)));	//飞行后剩余油量
						flightSheetVoyage.setFxr(bean.get(36));	//放行人
						flightSheetVoyage.setJz(bean.get(37));	//机长
						/*
						 * 设置默认值
						 */
						flightSheetVoyage.setWhrid(user.getId());
						flightSheetVoyage.setWhdwid(user.getBmdm());
						flightSheetVoyage.setWhsj(new Date());
						flightSheetVoyage.setZt(1);
						
						//判断list是否有飞机注册号和页码相同的情况
						boolean  b = true;
						if(list != null && list.size() > 0){
							for (FlightSheet fs : list) {
								if(flightSheet.getFjzch().equals(fs.getFjzch()) && flightSheet.getJlbym().equals(fs.getJlbym())){
									fs.getFlightSheetVoyageList().add(flightSheetVoyage);
									b = false;
								}
							}
						}
						if(b){
							List<FlightSheetVoyage> flightSheetVoyageList = new ArrayList<FlightSheetVoyage>();
							flightSheetVoyageList.add(flightSheetVoyage);
							flightSheet.setFlightSheetVoyageList(flightSheetVoyageList);
							list.add(flightSheet);
						}
						
					}
					resultMap.put(sheetIndex, list);
				}
				return resultMap;
	}
	/**
	 * 获取所有飞机数据
	 * @return
	 */
	private Map<String, String> getAllPlaneData(String dprtcode){
		
		Map<String, String> resultMap = new HashMap<String, String>();
		List<Aircraftinfo> aircraftinfoList = aircraftinfoMapper.queryAllBydprtcode(dprtcode);
		for (Aircraftinfo aircraftinfo : aircraftinfoList) {
			resultMap.put(aircraftinfo.getFjzch(), aircraftinfo.getDprtcode());
		}
		return resultMap;
	}
	/**
	 * 获取所有飞机数据
	 * @return
	 */
	private Map<String, String> getAuthorityPlaneData(){
		Map<String, String> resultMap = new HashMap<String, String>();
		/*PlaneData pd = new PlaneData();
		pd.setZt(1);
		List<PlaneData> planeDatas = planeDataService.findAllWithFjjxAuthority(pd);
		for (PlaneData planeData : planeDatas) {
			resultMap.put(planeData.getFjzch(), planeData.getDprtcode());
		}*/
		return resultMap;
	}
}
