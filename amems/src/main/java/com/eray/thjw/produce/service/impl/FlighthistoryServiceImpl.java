package com.eray.thjw.produce.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.produce.dao.FlightSheetMapper;
import com.eray.thjw.produce.dao.FlightSheetVoyageMapper;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.FlighthistoryService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.github.pagehelper.PageHelper;

import enu.produce.FlbStatusEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-12 下午2:30:30
 * @CreateBy 孙霁	
 */
@Service
public class FlighthistoryServiceImpl implements FlighthistoryService{

	@Resource
	private FlightSheetVoyageMapper flightSheetVoyageMapper;
	/**
	 * 
	 * @Description 条件查询飞行履历主列表
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAll(FlightSheetVoyage flightSheetVoyage) {
		PageHelper.startPage(flightSheetVoyage.getPagination());
		//查询数据
		List<FlightSheetVoyage> list = flightSheetVoyageMapper.queryAllFlighthistory(flightSheetVoyage);
		Map<String, Object> map= PageUtil.pack4PageHelper(list, flightSheetVoyage.getPagination());
		//判断是否有数据
		if(list != null && list.size()>0){
			//如果有数据，查询小计
			FlightSheetVoyage subtotal = flightSheetVoyageMapper.selectSubtotalByFlighthistory(flightSheetVoyage);
			//如果有数据，查询任务类型小计
			List<FlightSheetVoyage> rwlxList = flightSheetVoyageMapper.selectRwlxByFlighthistory(flightSheetVoyage);
			map.put("subtotal", subtotal);
			map.put("rwlxList", rwlxList);
		}
		return map;
	}
	/**
	 * 
	 * @Description 获取导出数据
	 * @CreateTime 2017-10-9 上午11:32:59
	 * @CreateBy 孙霁
	 * @param workorder
	 * @return Map<String, Object>
	 */
	@Override
	public List<FlightSheetVoyage> getList(FlightSheetVoyage record) {
		//查询数据
		List<FlightSheetVoyage> list = flightSheetVoyageMapper.queryAllFlighthistory(record);
		//发动机数量
		int fdjsl = Integer.valueOf(record.getParamsMap().get("fdjsl").toString());
		StringBuffer str = new StringBuffer();
		for (FlightSheetVoyage flightSheetVoyage : list){
			//状态翻译
			flightSheetVoyage.getParamsMap().put("ztText",FlbStatusEnum.getName(flightSheetVoyage.getFlightSheet().getZt()));
			
			str.delete(0,str.length());
			//飞行时间本次使用
			if(flightSheetVoyage.getSysjFz() != null){
				flightSheetVoyage.getParamsMap().put("sysjFz", StringAndDate_Util.convertToHour((flightSheetVoyage.getSysjFz())+""));
			}
			//飞行时间本次/累计 FH
			str.append(StringAndDate_Util.convertToHour((flightSheetVoyage.getFxsjFz()==null?0:flightSheetVoyage.getFxsjFz())+""))
			   .append("/")
			   .append(StringAndDate_Util.convertToHour(appendLj(flightSheetVoyage.getParamsMap().get("fxjslj").toString(),0)));
			flightSheetVoyage.getParamsMap().put("fxsjFz", str.toString());
			
			str.delete(0,str.length());
			//本次/累计 FC
			if(flightSheetVoyage.getFxxh()== null){
				flightSheetVoyage.setFxxh(0);
			}
			str.append(flightSheetVoyage.getFxxh())
			   .append("/")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("fxjslj").toString(),1));
			flightSheetVoyage.getParamsMap().put("bcfc", str.toString());
			
			
			/* 1#发动机 */
			//部件
			str.delete(0,str.length());
			str.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_1").toString(),0))
			   .append(" ")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_1").toString(),1));
			flightSheetVoyage.getParamsMap().put("bj1f", str.toString());
			
			//本次/累计 EH
			str.delete(0,str.length());
			str.append(StringAndDate_Util.convertToHour((flightSheetVoyage.getF1SjFz()==null?0:flightSheetVoyage.getF1SjFz())+""))
			   .append("/")
			   .append(StringAndDate_Util.convertToHour(appendLj(flightSheetVoyage.getParamsMap().get("fdj_1").toString(),2)));
			flightSheetVoyage.getParamsMap().put("eh1f", str.toString());
			
			//本次 /累计 EC
			str.delete(0,str.length());
			str.append((flightSheetVoyage.getF1Xh()==null?0:flightSheetVoyage.getF1Xh()))
			   .append("/")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_1").toString(),3));
			flightSheetVoyage.getParamsMap().put("ec1f", str.toString());
			
			/* 2#发动机 */
			//部件
			str.delete(0,str.length());
			str.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_2").toString(),0))
			   .append(" ")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_2").toString(),1));
			flightSheetVoyage.getParamsMap().put("bj2f", str.toString());
			
			//本次/累计 EH
			str.delete(0,str.length());
			str.append(StringAndDate_Util.convertToHour((flightSheetVoyage.getF2SjFz()==null?0:flightSheetVoyage.getF2SjFz())+""))
			   .append("/")
			   .append(StringAndDate_Util.convertToHour(appendLj(flightSheetVoyage.getParamsMap().get("fdj_2").toString(),2)));
			flightSheetVoyage.getParamsMap().put("eh2f", str.toString());
			
			//本次 /累计 EC
			str.delete(0,str.length());
			str.append((flightSheetVoyage.getF2Xh()==null?0:flightSheetVoyage.getF2Xh()))
			   .append("/")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_2").toString(),3));
			flightSheetVoyage.getParamsMap().put("ec2f", str.toString());
			
			/* 3#发动机 */
			//部件
			if(fdjsl >= 3){
				str.delete(0,str.length());
				str.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_3").toString(),0))
				.append(" ")
				.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_3").toString(),1));
				flightSheetVoyage.getParamsMap().put("bj3f", str.toString());
				
				//本次/累计 EH
				str.delete(0,str.length());
				str.append(StringAndDate_Util.convertToHour((flightSheetVoyage.getF3SjFz()==null?0:flightSheetVoyage.getF3SjFz())+""))
				.append("/")
				.append(StringAndDate_Util.convertToHour(appendLj(flightSheetVoyage.getParamsMap().get("fdj_3").toString(),2)));
				flightSheetVoyage.getParamsMap().put("eh3f", str.toString());
				
				//本次 /累计 EC
				str.delete(0,str.length());
				str.append((flightSheetVoyage.getF3Xh()==null?0:flightSheetVoyage.getF3Xh()))
				.append("/")
				.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_3").toString(),3));
				flightSheetVoyage.getParamsMap().put("ec3f", str.toString());
			}else{
				flightSheetVoyage.getParamsMap().put("bj3f", "");
				flightSheetVoyage.getParamsMap().put("eh3f", "");
				flightSheetVoyage.getParamsMap().put("ec3f", "");
				flightSheetVoyage.setF3Hytjl(null);
			}
			
			/* 4#发动机 */
			//部件
			if(fdjsl >= 4){
				str.delete(0,str.length());
				str.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_4").toString(),0))
				.append(" ")
				.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_4").toString(),1));
				flightSheetVoyage.getParamsMap().put("bj4f", str.toString());
				
				//本次/累计 EH
				str.delete(0,str.length());
				str.append(StringAndDate_Util.convertToHour((flightSheetVoyage.getF4SjFz()==null?0:flightSheetVoyage.getF4SjFz())+""))
				.append("/")
				.append(StringAndDate_Util.convertToHour(appendLj(flightSheetVoyage.getParamsMap().get("fdj_4").toString(),2)));
				flightSheetVoyage.getParamsMap().put("eh4f", str.toString());
				
				//本次 /累计 EC
				str.delete(0,str.length());
				str.append((flightSheetVoyage.getF4Xh()==null?0:flightSheetVoyage.getF4Xh()))
				.append("/")
				.append(appendLj(flightSheetVoyage.getParamsMap().get("fdj_4").toString(),3));
				flightSheetVoyage.getParamsMap().put("ec4f", str.toString());
			}else{
				flightSheetVoyage.getParamsMap().put("bj4f", "");
				flightSheetVoyage.getParamsMap().put("eh4f", "");
				flightSheetVoyage.getParamsMap().put("ec4f", "");
				flightSheetVoyage.setF4Hytjl(null);
			}
			
			/* apu */
			//部件
			str.delete(0,str.length());
			str.append(appendLj(flightSheetVoyage.getParamsMap().get("apu").toString(),0))
			   .append(" ")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("apu").toString(),1));
			flightSheetVoyage.getParamsMap().put("bjapu", str.toString());
			
			//本次/累计 EH
			str.delete(0,str.length());
			str.append(StringAndDate_Util.convertToHour((flightSheetVoyage.getApuSjFz()==null?0:flightSheetVoyage.getApuSjFz())+""))
			   .append("/")
			   .append(StringAndDate_Util.convertToHour(appendLj(flightSheetVoyage.getParamsMap().get("apu").toString(),2)));
			flightSheetVoyage.getParamsMap().put("ehapu", str.toString());
			
			//本次 /累计 EC
			str.delete(0,str.length());
			str.append((flightSheetVoyage.getApuXh()==null?0:flightSheetVoyage.getApuXh()))
			   .append("/")
			   .append(appendLj(flightSheetVoyage.getParamsMap().get("apu").toString(),3));
			flightSheetVoyage.getParamsMap().put("ecapu", str.toString());
		}
		
		//判断是否有数据
		str.delete(0,str.length());
		if(list != null && list.size()>0){
			//如果有数据，查询小计
			FlightSheetVoyage subtotal = flightSheetVoyageMapper.selectSubtotalByFlighthistory(record);
			//如果有数据，查询任务类型小计
			List<FlightSheetVoyage> rwlxList = flightSheetVoyageMapper.selectRwlxByFlighthistory(record);
			for (FlightSheetVoyage fsv : rwlxList) {
				if(fsv.getFxrwlx() != null && !"".equals(fsv.getFxrwlx())){
					str.append(fsv.getFxrwlx()).append(" : ").append(StringAndDate_Util.convertToHour(fsv.getParamsMap().get("lj_fxsj")+""));
				}
			}
			if(subtotal != null){
				//任务类型
				subtotal.setFxrwlx(str.toString());
				//飞行时间
				subtotal.getParamsMap().put("lj_sysj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_sysj")+""));
				subtotal.getParamsMap().put("lj_fxsj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_fxsj")+""));
				//1发
				subtotal.getParamsMap().put("lj_fdj1_sj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_fdj1_sj")+""));
				//2发
				subtotal.getParamsMap().put("lj_fdj2_sj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_fdj2_sj")+""));
				//3发
				if(fdjsl >= 3){
					subtotal.getParamsMap().put("lj_fdj3_sj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_fdj3_sj")+""));
				}else{
					subtotal.getParamsMap().put("lj_fdj3_sj", "");
				}
				//4发
				if(fdjsl >= 4){
					subtotal.getParamsMap().put("lj_fdj4_sj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_fdj4_sj")+""));
				}else{
					subtotal.getParamsMap().put("lj_fdj4_sj", "");
				}
				//apu
				subtotal.getParamsMap().put("lj_apu_sj", StringAndDate_Util.convertToHour(subtotal.getParamsMap().get("lj_apu_sj")+""));
				
				
				
				list.get(0).setFlightSheetVoyage(subtotal);
			}
		}
		return list;
	}
	/**
	 * 
	 * @Description 获取累计值
	 * @CreateTime 2017-12-22 下午2:20:46
	 * @CreateBy 孙霁
	 * @param obj
	 * @param index
	 * @return
	 */
	private String appendLj(String obj,int index){
		StringBuffer str = new StringBuffer();
		if(null != obj && !"".equals(obj)){
				String[] arr1 = obj.split("#_#", -1);
				str.append(arr1[index]);
		}
		return str.toString();
	}

}
