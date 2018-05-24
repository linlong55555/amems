package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.dao.FlightSheetMapper;
import com.eray.thjw.produce.dao.FlightSheetVoyageMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.FilgthResumeList;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 飞行履历导入
 * @CreateTime 2017-12-4 上午10:40:31
 * @CreateBy 雷伟	
 */
@Service("flightResumeExcelImporter")
public class FlightResumeExcelImporter extends AbstractExcelImporter<FilgthResumeList>{

	private String dprtcode = "";
	private String fjzch = "";
	private String fjjx = "";

	protected final static String Z = "Z";//整数
	protected final static String ZZ = "ZZ";//正整数
	protected final static String ZZWQ = "ZZWQ";//正整数,无穷大

	protected final static String FXRQYMHBH = "fxrqYmHbh";
	protected final static String FXRQKC = "fxrqKc";
	protected final static String FXRQYM = "fxrqYm";

	SimpleDateFormat yyyyMMddformat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat yyyyMMddHHmmformat = new SimpleDateFormat("yyyyMMdd HH:mm");
	Map<String, Map<String, List<FilgthResumeList>>> mergeMap;

	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	@Resource
	private FlightSheetMapper flightSheetMapper;
	@Resource
	private FlightSheetVoyageMapper flightSheetVoyageMapper;
	@Resource
	private SaibongUtilService saibongUtilService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private CommonService commonService;

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<FilgthResumeList>> sheetList) throws ExcelImportException {
		String sysrq = yyyyMMddformat.format(new Date()); //系统日期

		//检查飞机注册号是否存在
		Aircraftinfo aircraftinfo = new Aircraftinfo();
		aircraftinfo.setDprtcode(dprtcode);
		aircraftinfo.setFjzch(fjzch);
		int countFjzch = aircraftinfoMapper.findByFjzch(aircraftinfo);
		if (countFjzch <= 0) {
			throw new ExcelImportException("飞机注册号不存在，不能导入！");
		}

		//内存分组excel数据,用于后面校验
		mergeMap = getMergeMap(sheetList);

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			//工作表对应的的装机清单数据
			List<FilgthResumeList> datas = sheetList.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//循环校验每一行数据
			FilgthResumeList bean;
			for (int rowIndex = 0; null != datas && rowIndex < datas.size(); rowIndex++) {
				bean = datas.get(rowIndex);
				validateData(sheetIndex,bean,rowIndex,datas,mergeMap,sysrq);
			}
		}
	}

	/**
	 * 保存到数据库
	 * @throws ExcelImportException 
	 */
	@Override
	public int writeToDB(Map<Integer, List<FilgthResumeList>> sheetList) throws ExcelImportException {
		
		List<FlightSheet> addMainDatas = new ArrayList<FlightSheet>();//(新增)主表数据
		List<String> mainTabIds = new ArrayList<String>();//主表数据ID
		List<String> addMainIds = new ArrayList<String>();
		
		List<FlightSheetVoyage> addDetailDatas = new ArrayList<FlightSheetVoyage>();//(新增)明细表数据
		List<String> addSubIds = new ArrayList<String>();
		
		List<FlightSheetVoyage> updateDetailDatas = new ArrayList<FlightSheetVoyage>();//(修改)明细表数据
		List<String> updateSubIds = new ArrayList<String>();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		Date sysDate = commonService.getSysdate();
		Map<String, List<FilgthResumeList>>  sheetDatas = mergeMap.get(FXRQYM); 
		for (Iterator iter = sheetDatas.entrySet().iterator(); iter.hasNext();)  
		{  
			Entry entry = (Entry) iter.next();  
			
			//主表关键字段
			String mainKey = (String) entry.getKey();
			String fxrq = mainKey.split("◎")[0];//飞行日期
			fxrq = fxrq.replace(".", "").replace("-", "").toString();
			String jlbym = mainKey.split("◎")[1];//飞行本页码
			
			paramMap.clear();
			paramMap.put("dprtcode", dprtcode);
			paramMap.put("fjzch", fjzch);
			paramMap.put("fxrq", fxrq);
			paramMap.put("jlbym", jlbym);
			paramMap.put("zt", "99");
			
			FlightSheet flightSheet = flightSheetMapper.findFlightSheetByParam(paramMap);
			if(null == flightSheet || null == flightSheet.getId()){
				flightSheet = new FlightSheet();
				flightSheet.setId(UUID.randomUUID().toString());
				try {
					flightSheet.setFxrq(yyyyMMddformat.parse(fxrq));
				} catch (Exception e) {
					flightSheet.setFxrq(null);
				}
				flightSheet.setJlbym(jlbym);
				flightSheet.setDprtcode(dprtcode);
				flightSheet.setFjzch(fjzch);
				flightSheet.setZt(99);
				flightSheet.setIsXdtx(0);
				try {
					String last4 = fjzch.substring(fjzch.length() - 4 <= 0 ? 0 : fjzch.length()-4, fjzch.length());
					flightSheet.setFxjlbh(saibongUtilService.generate(ThreadVarUtil.getUser().getJgdm(), SaiBongEnum.FXJL.getName(), last4));
				} catch (Exception e) {
					e.printStackTrace();
				}
				flightSheet.setZddwid(ThreadVarUtil.getUser().getBmdm());
				flightSheet.setZdrid(ThreadVarUtil.getUser().getId());
				flightSheet.setZdsj(sysDate);
				addMainDatas.add(flightSheet);
				addMainIds.add(flightSheet.getId());
			}
			mainTabIds.add(flightSheet.getId()); //主表数据ID
			
			//从表数据
			List<FilgthResumeList> subDatas = (List<FilgthResumeList>) entry.getValue();
			for (int k = 0; null != subDatas && k < subDatas.size(); k++) {
				FilgthResumeList bean = subDatas.get(k);
				
				paramMap.clear();
				paramMap.put("mainid", flightSheet.getId());
				paramMap.put("hbh", bean.getHbh());
				FlightSheetVoyage sheetVoyage = flightSheetVoyageMapper.findFlightVoyageByParam(paramMap);
				
				boolean addSheetVoyage = false;
				if(null == sheetVoyage || null == sheetVoyage.getId()){
					sheetVoyage = new FlightSheetVoyage();
					sheetVoyage.setId(UUID.randomUUID().toString());
					addSheetVoyage = true;
				}
				sheetVoyage.setMainid(flightSheet.getId());
				sheetVoyage.setZt(1);
				sheetVoyage.setWhdwid(ThreadVarUtil.getUser().getBmdm());
				sheetVoyage.setWhrid(ThreadVarUtil.getUser().getId());
				sheetVoyage.setWhsj(sysDate);
				sheetVoyage.setHbh(bean.getHbh());
				sheetVoyage.setQfz(bean.getQfz());
				sheetVoyage.setZlz(bean.getZlz());
				try {
					sheetVoyage.setKcsj(yyyyMMddHHmmformat.parse(fxrq+" "+bean.getKcsj()));
				} catch (Exception e) {
					sheetVoyage.setKcsj(null);
				}
				try {
					sheetVoyage.setQfsj(getQfLdTcSj(bean,fxrq,"qf",sheetVoyage));
				} catch (Exception e) {
					sheetVoyage.setQfsj(null);
				}
				try {
					sheetVoyage.setLdsj(getQfLdTcSj(bean,fxrq,"ld",sheetVoyage));
				} catch (Exception e) {
					sheetVoyage.setLdsj(null);
				}
				try {
					sheetVoyage.setTcsj(getQfLdTcSj(bean,fxrq,"tc",sheetVoyage));
				} catch (Exception e) {
					sheetVoyage.setTcsj(null);
				}
				sheetVoyage.setSysjFz(convertToMinuteStr(bean.getSysjFz()));
				sheetVoyage.setFxsjFz(convertToMinuteStr(bean.getFxsjFz()));
				sheetVoyage.setF1SjFz(convertToMinuteStr(bean.getF1SjFz()));
				if(null != bean.getF1Xh()){
					sheetVoyage.setF1Xh(Integer.valueOf(bean.getF1Xh()));
				}else{
					sheetVoyage.setF1Xh(null);
				}
				if(null != bean.getF1Hytjl()){
					sheetVoyage.setF1Hytjl(new BigDecimal(bean.getF1Hytjl()));
				}else{
					sheetVoyage.setF1Hytjl(null);
				}
				sheetVoyage.setF2SjFz(convertToMinuteStr(bean.getF2SjFz()));
				if(null != bean.getF2Xh()){
					sheetVoyage.setF2Xh(Integer.valueOf(bean.getF2Xh()));
				}else{
					sheetVoyage.setF2Xh(null);
				}
				if(null != bean.getF2Hytjl()){
					sheetVoyage.setF2Hytjl(new BigDecimal(bean.getF2Hytjl()));
				}else{
					sheetVoyage.setF2Hytjl(null);
				}
				sheetVoyage.setF3SjFz(convertToMinuteStr(bean.getF3SjFz()));
				if(null != bean.getF3Xh()){
					sheetVoyage.setF3Xh(Integer.valueOf(bean.getF3Xh()));
				}else{
					sheetVoyage.setF3Xh(null);
				}
				if(null != bean.getF3Hytjl()){
					sheetVoyage.setF3Hytjl(new BigDecimal(bean.getF3Hytjl()));
				}else{
					sheetVoyage.setF3Hytjl(null);
				}
				sheetVoyage.setF4SjFz(convertToMinuteStr(bean.getF4SjFz()));
				if(null != bean.getF4Xh()){
					sheetVoyage.setF4Xh(Integer.valueOf(bean.getF4Xh()));
				}else{
					sheetVoyage.setF4Xh(null);
				}
				if(null != bean.getF4Hytjl()){
					sheetVoyage.setF4Hytjl(new BigDecimal(bean.getF4Hytjl()));
				}else{
					sheetVoyage.setApuSjFz(null);
				}
				sheetVoyage.setApuSjFz(convertToMinuteStr(bean.getApuSjFz()));
				if(null != bean.getApuXh()){
					sheetVoyage.setApuXh(Integer.valueOf(bean.getApuXh()));
				}else{
					sheetVoyage.setApuXh(null);
				}
				if(null != bean.getApuHytjl()){
					sheetVoyage.setApuHytjl(new BigDecimal(bean.getApuHytjl()));
				}else{
					sheetVoyage.setApuHytjl(null);
				}
				if(null != bean.getFxxh()){
					sheetVoyage.setFxxh(Integer.valueOf(bean.getFxxh()));
				}else{
					sheetVoyage.setFxxh(null);
				}
				if(null != bean.getLxqlcs()){
					sheetVoyage.setLxqlcs(Integer.valueOf(bean.getLxqlcs()));
				}else{
					sheetVoyage.setLxqlcs(null);
				}
				sheetVoyage.setIdgtksj(bean.getIdgtksj());
				if(null != bean.getYyy()){
					sheetVoyage.setYyy(new BigDecimal(bean.getYyy()));
				}else{
					sheetVoyage.setYyy(null);
				}
				if(null != bean.getRyCyl()){
					sheetVoyage.setRyCyl(new BigDecimal(bean.getRyCyl()));
				}else{
					sheetVoyage.setRyCyl(null);
				}
				if(null != bean.getRyJyl()){
					sheetVoyage.setRyJyl(new BigDecimal(bean.getRyJyl()));
				}else{
					sheetVoyage.setRyJyl(null);	
				}
				if(null != bean.getRySyyl()){
					sheetVoyage.setRySyyl(new BigDecimal(bean.getRySyyl()));
				}else{
					sheetVoyage.setRySyyl(null);
				}
				sheetVoyage.setJz(bean.getJz());
				sheetVoyage.setFxrwlx(bean.getFxrwlx());
				
				if(addSheetVoyage){
					addDetailDatas.add(sheetVoyage);
					addSubIds.add(sheetVoyage.getId());
				}else{
					updateDetailDatas.add(sheetVoyage);
					updateSubIds.add(sheetVoyage.getId());
				}
			}
		} 
		
		String czls = UUID.randomUUID().toString();
		//新增主表数据
		if (null != addMainDatas && addMainDatas.size() > 0){
			flightSheetMapper.insert4Batch(addMainDatas);
			commonRecService.write("id",addMainIds, TableEnum.B_S2_006, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		//新增从表数据
		if (null != addDetailDatas && addDetailDatas.size() > 0){
			flightSheetVoyageMapper.insert4Batch(addDetailDatas);
			commonRecService.write("id",addSubIds, TableEnum.B_S2_00601, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		//更新从表数据
		if (null != updateDetailDatas && updateDetailDatas.size() > 0){
			flightSheetVoyageMapper.update4Batch(updateDetailDatas);
			commonRecService.write("id",updateSubIds, TableEnum.B_S2_00601, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}
		
		//根据主表ID,更新主表数据的开车时间
		if (null != mainTabIds && mainTabIds.size() > 0){
			flightSheetMapper.updateKcsj4Batch(mainTabIds);
			commonRecService.write("id",mainTabIds, TableEnum.B_S2_006, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}
		
		//根据mianid+hbh,更新航次、航次描述
		List<FlightSheetVoyage> allDetailDatas = new ArrayList<FlightSheetVoyage>();
		allDetailDatas.addAll(addDetailDatas);
		allDetailDatas.addAll(updateDetailDatas);
		List<String> allSubIds = new ArrayList<String>();
		allSubIds.addAll(addSubIds);
		allSubIds.addAll(updateSubIds);
		if(null != allDetailDatas && allDetailDatas.size() > 0){
			flightSheetVoyageMapper.updateHcHcms4Batch(allDetailDatas);
			commonRecService.write("id",allSubIds, TableEnum.B_S2_00601, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}

		return 0;
	}

	/**
	 * 转换成对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<FilgthResumeList>> convertBean(Map<Integer, List<Map<Integer, String>>> sheetList) throws ExcelImportException {
		//结果集(按插入的顺序保存)
		Map<Integer, List<FilgthResumeList>> resultMap = new LinkedHashMap<Integer, List<FilgthResumeList>>();

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < 1; sheetIndex++) {
			List<FilgthResumeList> filgthResumeList = new ArrayList<FilgthResumeList>();
			// sheet对应飞行履历数据
			List<Map<Integer, String>> rowList = sheetList.get(sheetIndex);
			//循环获取每一行数据
			Map<Integer, String> row;
			FilgthResumeList bean;
			for (int rowIndex = 0; null != rowList && rowIndex < rowList.size(); rowIndex++) {
				row = rowList.get(rowIndex); //行数据
				bean = new FilgthResumeList();//实体bean
				bean = convertRow2Bean(row,bean);//将行数据转换成实体bean
				filgthResumeList.add(bean);//添加到结果集中
			}
			resultMap.put(sheetIndex, filgthResumeList);
		}

		return resultMap;
	}

	/**
	 * @Description 将Excel行数据转换成实体bean
	 * @CreateTime 2017-11-29 下午2:16:11
	 * @CreateBy 雷伟
	 * @param row 行数据
	 * @param bean 实体bean
	 * @return
	 */
	private FilgthResumeList convertRow2Bean(Map<Integer, String> row, FilgthResumeList bean) {
		dprtcode = this.getParam("dprtcode")==null?"":this.getParam("dprtcode").toString(); //组织机构码
		fjzch = this.getParam("fjzch")==null?"":this.getParam("fjzch").toString(); //飞机注册号
		fjjx = this.getParam("fjjx")==null?"":this.getParam("fjjx").toString(); //机型

		bean.setFjzch(fjzch);//飞机注册号
		bean.setFxrq(row.get(0)); //飞行日期
		bean.setJlbym(row.get(1)); //飞行（记录）本页码
		bean.setHbh(row.get(2)); //航班号
		bean.setFxrwlx(row.get(3)); //(飞行)任务类型
		bean.setQfz(row.get(4)); //起飞站
		bean.setZlz(row.get(5)); //着陆站
		bean.setKcsj(row.get(6)); //开车
		bean.setQfsj(row.get(7)); //起飞
		bean.setLdsj(row.get(8)); //落地
		bean.setTcsj(row.get(9)); //停车
		bean.setSysjFz(row.get(10)); //使用
		bean.setFxsjFz(row.get(11)); //FH 飞行时间
		bean.setFxxh(row.get(12)); //FC 飞行循环
		bean.setLxqlcs(row.get(13)); //连续起落次数
		bean.setF1SjFz(row.get(14)); //1#发时间-分钟
		bean.setF1Xh(row.get(15)); //1#发循环
		bean.setF1Hytjl(row.get(16)); //1#发滑油添加量
		bean.setF2SjFz(row.get(17)); //2#发时间-分钟
		bean.setF2Xh(row.get(18)); //2#发循环
		bean.setF2Hytjl(row.get(19)); //2#发滑油添加量
		bean.setF3SjFz(row.get(20)); //3#发时间-分钟
		bean.setF3Xh(row.get(21)); //3#发循环
		bean.setF3Hytjl(row.get(22)); //3#发滑油添加量
		bean.setF4SjFz(row.get(23)); //4#发时间-分钟
		bean.setF4Xh(row.get(24)); //4#发循环
		bean.setF4Hytjl(row.get(25)); //4#发滑油添加量
		bean.setApuSjFz(row.get(26)); //APU时间-分钟
		bean.setApuXh(row.get(27)); //APU循环
		bean.setApuHytjl(row.get(28)); //APU滑油添加量
		bean.setIdgtksj(row.get(29)); //IDG脱开时间
		bean.setYyy(row.get(30)); //液压油
		bean.setRyCyl(row.get(31)); //燃油存油量
		bean.setRyJyl(row.get(32)); //燃油加油量
		bean.setRySyyl(row.get(33)); //燃油剩余油量
		bean.setJz(row.get(34)); //机长

		return bean;
	}

	/**
	 * @Description 日期格式校验
	 * @CreateTime 2017-11-30 上午11:18:28
	 * @CreateBy 雷伟
	 * @param chucrq 值
	 * @param sheetIndex 当前sheet索引
	 * @param rowIndex 当前行索引
	 * @param columnName 列名
	 */
	private String validateDateFormat(String chucrq, int sheetIndex,int rowIndex, String columnName) {
		String yyyyMMdd = "";
		String dateFormats = "\\d{4}(\\.|-|/|)\\d{2}(\\.|-|)\\d{2}"; //支持的格式yyyy.MM.dd、yyyy-MM-dd、yyyyMMdd、
		Pattern pattern = Pattern.compile(dateFormats);
		if (!pattern.matcher(chucrq).matches()) {
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，"+columnName+"格式错误");  
		} else {
			try {
				chucrq = chucrq.replace(".", "").replace("-", "").toString();
				yyyyMMdd = chucrq;
			} catch (Exception e) {
				
			}
		}
		return yyyyMMdd;
	}

	/**
	 * @Description 校验,小时数:分钟数 
	 *              分钟数不能超过59
	 * @CreateTime 2017-11-30 上午10:24:23
	 * @CreateBy 雷伟
	 * @param tsn
	 */
	private String validateTimeFormat(String tsn,int sheetIndex,int rowIndex,String columnName,boolean coverToMinute) {
		boolean isNum = tsn.matches("[0-9]+");
		boolean isHHMM = tsn.matches("([0-9]{1,}):[0-9]{1,2}");
		
		//只要符合是HH:SS格式 或 是整数就通过
		if(isNum || isHHMM){
			//如果是纯整数，自动补齐格式
			if(isNum){
				tsn = tsn + ":00";
			}
			
			String[] timeArr = tsn.split(":");
			int hrs = Integer.valueOf(timeArr[0]);
			int min = Integer.valueOf(timeArr[1]);
			if (min > 59) {
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，"+columnName+"分钟数不能超过59");  
			}
			//如果最终结果不转成分钟,那么小时数必须在0~23之间
			if(!coverToMinute){
				if(hrs > 23 || hrs < 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，"+columnName+"小时数必须在0~23之间");  
				}
			}
			
		}else{
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，"+columnName+"格式错误,小时数:分钟数"); 
		}
		
		return tsn;
	}

	/**
	 * @Description 校验整数范围 0~9999999
	 * @CreateTime 2017-11-30 上午11:02:55
	 * @CreateBy 雷伟
	 * @param zjsl
	 * @param flag Z整数  ZZ正整数  ZS正数
	 * @return
	 */
	private boolean validateNumRange(String num,String type) {
		boolean flag = false;
		String check = "\\d+"; 
		Pattern regex = Pattern.compile(check); 
		Matcher matcher = regex.matcher(num); 
		if(matcher.matches()){
			try {
				if("Z".equals(type)){
					if(Long.valueOf(num) >= 0 && Long.valueOf(num) <= 9999999){
						flag = true;
					}
				}
				else if("ZZ".equals(type)){
					if(Long.valueOf(num) > 0 && Long.valueOf(num) <= 9999999){
						flag = true;
					}
				}
				else if("ZZWQ".equals(type)){
					if(Long.valueOf(num) > 0 && Long.valueOf(num) <= 999999999){
						flag = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @Description 
	 * @CreateTime 2017-11-29 下午3:43:27
	 * @CreateBy 雷伟
	 * @param sheetIndex 当前sheet
	 * @param bean 当前行数据
	 * @param rowIndex 行索引
	 * @param datas 所有行数据
	 * @param mergeMap 文本分组后数据集合
	 * @param sysrq 系统日期
	 */
	private void validateData(int sheetIndex,FilgthResumeList bean, int rowIndex,
			List<FilgthResumeList> datas,Map<String, Map<String, List<FilgthResumeList>>> mergeMap,String sysrq) {

		//飞行日期：不能为空、要符合日期格式
		boolean fxrqFlag = true;
		if(StringUtils.isBlank(bean.getFxrq())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行日期不能为空");
		}else{
			String yyyyMMdd = validateDateFormat(bean.getFxrq(),sheetIndex,rowIndex,"飞行日期");
			if(!"".equals(yyyyMMdd)){
				bean.setFxrq(yyyyMMdd);
			}else{
				fxrqFlag = false;
			}
		}

		//飞行本页码：不能为空、不能含有中文、不能超过20个字符
		if(StringUtils.isBlank(bean.getJlbym())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行本页码不能为空");
		}else{
			if(Utils.Str.isChinese(bean.getJlbym())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行本页码不能含有中文");
			}
			if(Utils.Str.getLength(bean.getJlbym()) > 20){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行本页码不能超过20个字符");
			}
		}

		//航班号：不能为空、不能含有中文、不能超过50个字符
		if(StringUtils.isBlank(bean.getHbh())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，航班号不能为空");
		}else{
			if(Utils.Str.isChinese(bean.getHbh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，航班号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getHbh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，航班号不能超过50个字符");
			}
		}

		//任务类型：不能超过15个字符
		if(!StringUtils.isBlank(bean.getFxrwlx())){
			if(Utils.Str.getLength(bean.getFxrwlx()) > 15){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，任务类型不能超过15个字符");
			}
		}

		//起飞站：不能为空、不能超过100个字符
		if(StringUtils.isBlank(bean.getQfz())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，起飞站不能为空");
		}else{
			if(Utils.Str.getLength(bean.getQfz()) > 100){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，起飞站不能超过100个字符");
			}
		}

		//着陆站：不能为空、不能超过100个字符
		if(StringUtils.isBlank(bean.getZlz())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，着陆站不能为空");
		}else{
			if(Utils.Str.getLength(bean.getZlz()) > 100){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，着陆站不能超过100个字符");
			}
		}

		//开车：不能为空、符合小时:分钟格式
		if(StringUtils.isBlank(bean.getKcsj())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，开车不能为空");
		}else{
			String kcsj = validateTimeFormat(bean.getKcsj(),sheetIndex,rowIndex,"开车",false);
			bean.setKcsj(kcsj);
		}

		//起飞：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getQfsj())){
			String qfsj = validateTimeFormat(bean.getQfsj(),sheetIndex,rowIndex,"起飞",false);
			bean.setQfsj(qfsj);
		}

		//落地：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getLdsj())){
			String ldsj = validateTimeFormat(bean.getLdsj(),sheetIndex,rowIndex,"落地",false);
			bean.setLdsj(ldsj);
		}

		//停车：不能为空、符合小时:分钟格式
		if(StringUtils.isBlank(bean.getTcsj())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，停车不能为空");
		}else{
			String tcsj = validateTimeFormat(bean.getTcsj(),sheetIndex,rowIndex,"停车",false);
			bean.setTcsj(tcsj);
		}

		//使用：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getSysjFz())){
			String sysjFz = validateTimeFormat(bean.getSysjFz(),sheetIndex,rowIndex,"使用",true);
			bean.setSysjFz(sysjFz);
		}

		//FH：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getFxsjFz())){
			String fxsjFz = validateTimeFormat(bean.getFxsjFz(),sheetIndex,rowIndex,"FH",true);
			bean.setFxsjFz(fxsjFz);
		}

		//FC：整数，范围：0~9999999
		if(!StringUtils.isBlank(bean.getFxxh())){
			if(!validateNumRange(bean.getFxxh(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，FC只能在0~9999999范围内");
			}
		}

		//连续起落次数：整数，范围：0~9999999
		if(!StringUtils.isBlank(bean.getLxqlcs())){
			if(!validateNumRange(bean.getLxqlcs(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，连续起落次数只能能[0~9999999]范围内整数");
			}
		}

		//1#发时间-分钟：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getF1SjFz())){
			String f1SjFz = validateTimeFormat(bean.getF1SjFz(),sheetIndex,rowIndex,"EH",true);
			bean.setF1SjFz(f1SjFz);
		}

		//1#发循环：正整数范围[0~9999999]
		if(!StringUtils.isBlank(bean.getF1Xh())){
			if(!validateNumRange(bean.getF1Xh(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，1#发EC只能为[0~9999999]范围内正整数");
			}
		}

		//1#滑油添加量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getF1Hytjl())){
			if(!Utils.Str.isDecimalzs(bean.getF1Hytjl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，1#发滑油添加量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//2#发时间-分钟：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getF2SjFz())){
			String f2SjFz = validateTimeFormat(bean.getF2SjFz(),sheetIndex,rowIndex,"EH",true);
			bean.setF2SjFz(f2SjFz);
		}

		//2#发循环：正整数范围[1~9999999]
		if(!StringUtils.isBlank(bean.getF2Xh())){
			if(!validateNumRange(bean.getF2Xh(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，2#发EC只能在[0~9999999]范围内正整数");
			}
		}

		//2#滑油添加量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getF2Hytjl())){
			if(!Utils.Str.isDecimalzs(bean.getF2Hytjl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，2#发滑油添加量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//3#发时间-分钟：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getF3SjFz())){
			String f3SjFz = validateTimeFormat(bean.getF3SjFz(),sheetIndex,rowIndex,"EH",true);
			bean.setF3SjFz(f3SjFz);
		}

		//3#发循环：正整数范围[1~9999999]
		if(!StringUtils.isBlank(bean.getF3Xh())){
			if(!validateNumRange(bean.getF3Xh(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，3#发EC只能在[0~9999999]范围内正整数");
			}
		}

		//3#滑油添加量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getF3Hytjl())){
			if(!Utils.Str.isDecimalzs(bean.getF3Hytjl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，3#发滑油添加量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//4#发时间-分钟：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getF4SjFz())){
			String f4SjFz = validateTimeFormat(bean.getF4SjFz(),sheetIndex,rowIndex,"EH",true);
			bean.setF4SjFz(f4SjFz);
		}

		//4#发循环：正整数范围[1~9999999]
		if(!StringUtils.isBlank(bean.getF4Xh())){
			if(!validateNumRange(bean.getF4Xh(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，4#发EC只能在[0~9999999]范围内正整数");
			}
		}

		//4#滑油添加量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getF4Hytjl())){
			if(!Utils.Str.isDecimalzs(bean.getF4Hytjl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，4#发滑油添加量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//APU时间-分钟：符合小时:分钟格式
		if(!StringUtils.isBlank(bean.getApuSjFz())){
			String apuSjFz = validateTimeFormat(bean.getApuSjFz(),sheetIndex,rowIndex,"APU.H",true);
			bean.setApuSjFz(apuSjFz);
		}

		//APU循环：正整数范围[1~9999999]
		if(!StringUtils.isBlank(bean.getApuXh())){
			if(!validateNumRange(bean.getApuXh(),this.Z)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，APU.C只能在[0~9999999]范围内正整数");
			}
		}

		//APU滑油添加量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getApuHytjl())){
			if(!Utils.Str.isDecimalzs(bean.getApuHytjl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，APU滑油添加量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//IDG脱开时间：不能含有中文、不能超过20个字符
		if(!StringUtils.isBlank(bean.getIdgtksj())){
			if(Utils.Str.isChinese(bean.getIdgtksj())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，IDG脱开时间不能含有中文");
			}
			if(Utils.Str.getLength(bean.getIdgtksj()) > 20){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，IDG脱开时间不能超过20个字符");
			}
		}

		//液压油：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getYyy())){
			if(!Utils.Str.isDecimalzs(bean.getYyy(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，液压油只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//燃油存油量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getRyCyl())){
			if(!Utils.Str.isDecimalzs(bean.getRyCyl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，燃油存油量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//燃油加油量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getRyJyl())){
			if(!Utils.Str.isDecimalzs(bean.getRyJyl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，燃油加油量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//燃油剩余油量：正数范围[0~9999999.99]
		if(!StringUtils.isBlank(bean.getRySyyl())){
			if(!Utils.Str.isDecimalzs(bean.getRySyyl(), 2)){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，燃油剩余油量只能为[0~9999999.99]范围内的两位小数");
			}
		}

		//机长：不能超过100个字符
		if(!StringUtils.isBlank(bean.getJz())){
			if(Utils.Str.getLength(bean.getJz()) > 100){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，机长不能超过100个字符");
			}
		}

		//飞行日期+飞行本页码+航班号在文本中唯一
		if(!StringUtils.isBlank(bean.getFxrq()) && !StringUtils.isBlank(bean.getJlbym()) && !StringUtils.isBlank(bean.getHbh())){
			String fxrqYmHbhKey = bean.getFxrq()+"◎"+bean.getJlbym()+"◎"+bean.getHbh();
			if(mergeMap.get(FXRQYMHBH) != null && mergeMap.get(FXRQYMHBH).get(fxrqYmHbhKey) != null && mergeMap.get(FXRQYMHBH).get(fxrqYmHbhKey).size() > 1){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行日期+飞行本页码+航班号在文本中已存在");
			}
		}

		//飞行日期+开车在本文中唯一
		if(!StringUtils.isBlank(bean.getFxrq()) && !StringUtils.isBlank(bean.getKcsj())){
			String fxrqKcKey = bean.getFxrq()+"◎"+bean.getKcsj();
			if(mergeMap.get(FXRQKC) != null && mergeMap.get(FXRQKC).get(fxrqKcKey) != null && mergeMap.get(FXRQKC).get(fxrqKcKey).size() > 1){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行日期+开车在文本中已存在");
			}
		}

		//飞行日期+飞行本页码相同的数据行不能超过49条
		if(!StringUtils.isBlank(bean.getFxrq()) && !StringUtils.isBlank(bean.getKcsj())){
			String fxrqYmKey = bean.getFxrq()+"◎"+bean.getJlbym();
			if(mergeMap.get(FXRQYM) != null && mergeMap.get(FXRQYM).get(fxrqYmKey) != null  && mergeMap.get(FXRQYM).get(fxrqYmKey).size() > 49){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行日期+飞行本页码相同的数据行不能超过49条");
			}
		}

		//飞行日期不能大于系统日期
		if(!StringUtils.isBlank(bean.getFxrq()) && fxrqFlag){
			if(validateNumRange(bean.getFxrq(),this.ZZWQ)){
				if(Long.valueOf(bean.getFxrq()) > Long.valueOf(sysrq)){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+4)+"行，飞行日期不能大于系统日期");
				}
			}
		}
	}

	/**
	 * @Description 获得excel合并数据
	 * @CreateTime 2017-12-4 下午5:01:34
	 * @CreateBy 雷伟
	 * @param sheetList
	 * @return
	 */
	private Map<String, Map<String, List<FilgthResumeList>>> getMergeMap(Map<Integer, List<FilgthResumeList>> sheetList) {
		Map<String, Map<String, List<FilgthResumeList>>> mergeMap = new HashMap<String, Map<String, List<FilgthResumeList>>>();
		
		Map<String, List<FilgthResumeList>> fxrqYmHbhMap = new HashMap<String, List<FilgthResumeList>>();
		Map<String, List<FilgthResumeList>> fxrqKcMap = new HashMap<String, List<FilgthResumeList>>();
		Map<String, List<FilgthResumeList>> fxrqYmMap = new HashMap<String, List<FilgthResumeList>>();


		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			//工作表对应的的装机清单数据
			List<FilgthResumeList> datas = sheetList.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//循环校验每一行数据
			FilgthResumeList bean;
			String fxrqYmHbhKey = "";//飞行日期+飞行本页码+航班号
			String fxrqKcKey = "";//飞行日期+开车
			String fxrqYmKey = "";//飞行日期+飞行本页码
			for (int rowIndex = 0; null != datas && rowIndex < datas.size(); rowIndex++) {
				bean = datas.get(rowIndex);
				List<FilgthResumeList> resumeList = new ArrayList<FilgthResumeList>();

				if(!StringUtils.isBlank(bean.getFxrq()) && !StringUtils.isBlank(bean.getJlbym()) && !StringUtils.isBlank(bean.getHbh())){
					fxrqYmHbhKey = bean.getFxrq()+"◎"+bean.getJlbym()+"◎"+bean.getHbh();
					if(fxrqYmHbhMap.get(fxrqYmHbhKey) != null){
						resumeList = fxrqYmHbhMap.get(fxrqYmHbhKey);
					}else{
						resumeList = new ArrayList<FilgthResumeList>();
					}
					resumeList.add(bean);
					fxrqYmHbhMap.put(fxrqYmHbhKey, resumeList);
				}

				if(!StringUtils.isBlank(bean.getFxrq()) && !StringUtils.isBlank(bean.getKcsj())){
					fxrqKcKey = bean.getFxrq()+"◎"+bean.getKcsj();
					if(fxrqKcMap.get(fxrqKcKey) != null){
						resumeList = fxrqKcMap.get(fxrqKcKey);
					}else{
						resumeList = new ArrayList<FilgthResumeList>();
					}
					resumeList.add(bean);
					fxrqKcMap.put(fxrqKcKey, resumeList);
				}

				if(!StringUtils.isBlank(bean.getFxrq()) && !StringUtils.isBlank(bean.getKcsj())){
					fxrqYmKey = bean.getFxrq()+"◎"+bean.getJlbym();
					if(fxrqYmMap.get(fxrqYmKey) != null){
						resumeList = fxrqYmMap.get(fxrqYmKey);
					}else{
						resumeList = new ArrayList<FilgthResumeList>();
					}
					resumeList.add(bean);
					fxrqYmMap.put(fxrqYmKey, resumeList);
				}

			}
		}

		mergeMap.put(FXRQYMHBH, fxrqYmHbhMap);
		mergeMap.put(FXRQKC, fxrqKcMap);
		mergeMap.put(FXRQYM, fxrqYmMap);

		return mergeMap;
	}
	
	/**
	 * @Description 获取起飞、落地、停车时间
	 * @CreateTime 2017-12-5 下午4:01:51
	 * @CreateBy 雷伟
	 * @param bean
	 * @param flag
	 * @return
	 */
	private Date getQfLdTcSj(FilgthResumeList bean,String fxrq, String flag,FlightSheetVoyage sheetVoyage) {
		Date fxDate = new Date();
		
		String kcsjStr = StringAndDate_Util.convertToMinuteStr(bean.getKcsj()); //开车时间
		String qfsjStr = StringAndDate_Util.convertToMinuteStr(bean.getQfsj()); //起飞时间
		String ldsjStr = StringAndDate_Util.convertToMinuteStr(bean.getLdsj()); //落地时间
		String tcsjStr = StringAndDate_Util.convertToMinuteStr(bean.getTcsj()); //停车时间
		
		try {
			if("qf".equals(flag)){
				
				if(kcsjStr==null || qfsjStr==null){
					return null;
				}
				
				int qfsj = (qfsjStr==null)?0:Integer.parseInt(qfsjStr); //起飞时间
				int kcsj = (kcsjStr==null)?0:Integer.parseInt(kcsjStr); //开车时间
				if (qfsj >= kcsj) {
					fxDate = yyyyMMddHHmmformat.parse(fxrq+" "+ bean.getQfsj());
				} else {
					fxDate = yyyyMMddHHmmformat.parse((Integer.parseInt(fxrq)+1)+" "+ bean.getQfsj());
				}
			}else if("ld".equals(flag)){
				int ldsj = (ldsjStr==null)?0:Integer.parseInt(ldsjStr); //落地时间
				
				//确定比较时间日期
				String bjsjStr = ""; //比较时间
				String bjYYYYMMDD = ""; //比较日期
				if (qfsjStr != null) {
					bjsjStr = qfsjStr;
					bjYYYYMMDD = yyyyMMddformat.format(sheetVoyage.getQfsj());
				} else {
					bjsjStr = kcsjStr;
					bjYYYYMMDD = yyyyMMddformat.format(sheetVoyage.getKcsj());
				}
				
				//比较的时间为空，直接跳过
				if(bjsjStr==null || bean.getLdsj() == null){
					return null;
				}
				
				int bjsj = (bjsjStr==null)?0:Integer.parseInt(bjsjStr);
				
				//比较
				if (ldsj >= bjsj) {
					fxDate = yyyyMMddHHmmformat.parse(bjYYYYMMDD+" "+ bean.getLdsj());
				} else {
					fxDate = yyyyMMddHHmmformat.parse((Integer.parseInt(bjYYYYMMDD)+1)+" "+ bean.getLdsj());
				}
				
			}else if("tc".equals(flag)){
				int tcsj = (tcsjStr==null)?0:Integer.parseInt(tcsjStr); //停车时间
				
				//确定比较时间日期
				String bjsjStr = ""; //比较时间
				String bjYYYYMMDD = ""; //比较日期
				if (ldsjStr != null) {
					bjsjStr = ldsjStr;
					bjYYYYMMDD = yyyyMMddformat.format(sheetVoyage.getLdsj());
				} else if (qfsjStr != null) {
					bjsjStr = qfsjStr;
					bjYYYYMMDD = yyyyMMddformat.format(sheetVoyage.getQfsj());
				} else {
					bjsjStr = kcsjStr;
					bjYYYYMMDD = yyyyMMddformat.format(sheetVoyage.getKcsj());
				}
				
				//比较的时间为空，直接跳过
				if(bjsjStr==null || bean.getTcsj() == null){
					return null;
				}
				
				int bjsj = (bjsjStr==null)?0:Integer.parseInt(bjsjStr);
				
				//比较
				if (tcsj >= bjsj) {
					fxDate = yyyyMMddHHmmformat.parse(bjYYYYMMDD+" "+ bean.getTcsj());
				} else {
					fxDate = yyyyMMddHHmmformat.parse((Integer.parseInt(bjYYYYMMDD)+1)+" "+ bean.getTcsj());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fxDate;
	}
	
	/**
	 * @Description 转换成分钟
	 * @CreateTime 2017-12-5 下午4:41:39
	 * @CreateBy 雷伟
	 * @param sysjFz
	 * @return
	 */
	private Integer convertToMinuteStr(String fz) {
		Integer result = null;
		String coverStr = StringAndDate_Util.convertToMinuteStr(fz);
		if(null != coverStr){
			result = Integer.parseInt(coverStr);
		}
		return result;
	}
}
