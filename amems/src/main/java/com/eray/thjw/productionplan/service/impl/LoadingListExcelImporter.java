package com.eray.thjw.productionplan.service.impl;

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

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.flightdata.dao.FlightRecordSheetMapper;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.productionplan.dao.LoadingListEditableMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.ComponentTypeEnum;
import enu.LogOperationEnum;
import enu.PartsPositionEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("loadinglistexcelimporter")
public class LoadingListExcelImporter extends AbstractExcelImporter<LoadingList>{
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private FixChapterService fixChapterService;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private LoadingListEditableMapper loadingListEditableMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private FlightRecordSheetMapper flightRecordSheetMapper;
	

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<LoadingList>> datasMap) throws ExcelImportException {
		
		// 所有飞机注册号
		Map<String, String> fjzchList = getAllPlaneData();
		// 有权限的飞机
		Map<String, String> authorityList = getAuthorityPlaneData();
		
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<LoadingList> datas = datasMap.get(sheetIndex);
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
				if(flightRecordSheetMapper.getCountByLoadingListImport(planeData) > 0){
					throw new ExcelImportException("请撤销飞机"+fjzch+"之前的飞行记录本后再导入！");
				}
			}
			
			// 获取所有章节号
			List<String> fixChapterList = getAllFixChapter();
			// 获取该飞机下一级部件 的位置
			Map<Integer, LoadingList> wzMap = getWzMap(fjzch,fjzchList.get(fjzch));
			// 获取该组织机构下的所有件号序列号
			Map<String, LoadingList> xlhMap = getJhAndXlhMap(fjzchList.get(fjzch));
			// 获取该组织机构下的所有件号批次号内部识别码
			Map<String, LoadingList> nbsbmMap = getNbsbmMap(fjzchList.get(fjzch));
			LoadingList ll;
			for (int i = 0; i < datas.size(); i++) {
				ll = datas.get(i);
				
				// 非空验证
				if(StringUtils.isBlank(ll.getFjzch())){
					addErrorMessage("飞机注册号不能为空");
				}
				if(StringUtils.isBlank(ll.getJh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，件号不能为空");
				}
				if(StringUtils.isBlank(ll.getZjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，章节号不能为空");
				}
				
				// 可选值范围验证
				if(ll.getWz() != 0 && ll.getWz() != 1 && ll.getWz() != 2 
						&& ll.getWz() != 3 && ll.getWz() != 4 && ll.getWz() != 5){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，位置只能为0、1、2、3、4、5");
				}
				if(ll.getKzlx() != 1 && ll.getKzlx() != 2 && ll.getKzlx() != 3){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，控制类型只能为1、2、3");
				}
				if(ll.getLlklx() != 1 && ll.getLlklx() != 2 && ll.getLlklx() != 3){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，履历卡类型只能为0、1、2");
				}
				if(ll.getIsDj() != 0 && ll.getIsDj() != 1){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，是否定检只能为0、1");
				}
				if(ll.getCj() != 1 && ll.getCj() != 2){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，层级只能为1、2");
				}
				if(ll.getZt() != 1 && ll.getZt() != 2){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，状态只能为1、2");
				}
				
				// 非中文验证
				if(!StringUtils.isBlank(ll.getJh()) && Utils.Str.isChinese(ll.getJh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，件号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getCjjh()) &&Utils.Str.isChinese(ll.getCjjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，厂家件号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getNbsbm()) &&Utils.Str.isChinese(ll.getNbsbm())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，内部识别码不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getXlh()) &&Utils.Str.isChinese(ll.getXlh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getXkzh()) &&Utils.Str.isChinese(ll.getXkzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，许可证号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getShzh()) &&Utils.Str.isChinese(ll.getShzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getPch()) &&Utils.Str.isChinese(ll.getPch())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，批次号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getLlkbh()) &&Utils.Str.isChinese(ll.getLlkbh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，履历卡编号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getAzjldh()) &&Utils.Str.isChinese(ll.getAzjldh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，安装记录单号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getLlkbh()) &&Utils.Str.isChinese(ll.getLlkbh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，拆除记录单号不能含有中文");
				}
				
				// 类型验证
				if(ll.getScrq() != null && ll.getScrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，生产日期格式不正确");
				}
				if(ll.getAzrq() != null && ll.getAzrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，安装日期格式不正确");
				}
				if(ll.getCcrq() != null && ll.getCcrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，拆除日期格式不正确");
				}
				if(ll.getZjsl() != null && INTEGER_TYPE_ERROR.equals(ll.getZjsl())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，装机数量格式不正确");
				}
				
				// 长度验证
				if(Utils.Str.getLength(ll.getJh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，件号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getXlh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getCjjh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，厂家件号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getYwmc()) > 300){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，英文名称的最大长度为300");
				}
				if(Utils.Str.getLength(ll.getZwmc()) > 100){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，中文名称的最大长度为100");
				}
				if(Utils.Str.getLength(ll.getNbsbm()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，内部识别码的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getXkzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，许可证号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getShzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getPch()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，批次号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getBjgzjl()) > 500){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，部件改装记录的最大长度为500");
				}
				if(Utils.Str.getLength(ll.getBz()) > 300){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，备注的最大长度为300");
				}
				if(Utils.Str.getLength(ll.getAzjldh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，安装记录单号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getCcjldh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，拆除记录单号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getLlkbh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，履历卡编号的最大长度为50");
				}
				
				
				// 装机数量验证
				if(ll.getZjsl() != null && (ll.getZjsl() <= 0 || ll.getZjsl() >= 1000000)){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，装机数量必须在1~999999之间");
				}
				
				// 序列号验证
				if(StringUtils.isBlank(ll.getXlh()) 
						&& (ll.getKzlx() == 1 || ll.getKzlx() == 2 || ll.getIsDj() == 1)){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，时控件和定检件的序列号不能为空");
				}
				
				// 章节号存在验证
				if(!StringUtils.isBlank(ll.getZjh()) && !fixChapterList.contains(ll.getZjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，章节号不存在");
				}
				
				// 一级部件唯一验证
				if(ll.getCj() == 1 && wzMap.containsKey(ll.getWz())){
					LoadingList dbData = wzMap.get(ll.getWz());
					if(!dbData.getJh().equals(ll.getJh()) || !dbData.getXlh().equals(ll.getXlh())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，已存在位置为"
								+ PartsPositionEnum.getName(ll.getWz()) + "的一级部件");
					}
				}
				
				// 件号、序列号重复验证（飞机注册号不同）
				if(!StringUtils.isBlank(ll.getXlh())){
					String key = ll.getJh() + "_" + ll.getXlh();
					if(xlhMap.containsKey(key) && !xlhMap.get(key).getFjzch().equals(ll.getFjzch())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，件号为" + ll.getJh() + 
								"，序列号为" + ll.getXlh() + "的部件已存在");
					}
				}
				// 件号、批次号、内部识别码重复验证（飞机注册号不同）
				else if(!StringUtils.isBlank(ll.getNbsbm())){
					String key = ll.getJh() + "_" + ll.getPch() + "_" + ll.getNbsbm();
					if(nbsbmMap.containsKey(key) && !nbsbmMap.get(key).getFjzch().equals(ll.getFjzch())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，件号为" + ll.getJh() + 
								(StringUtils.isBlank(ll.getPch())?"": ("，批次号为：" + ll.getPch()))
								+ "，内部识别码为：" + ll.getNbsbm() + "的部件已存在");
					}
				}
				
				// 层级关系验证
				if(i == 0 && ll.getCj() != 1){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，层级关系不正确（第三行的层级必须为1）");
				}
			}
			
			// 验证excel中一级部件的数量
			Map<Integer, Integer> cjMap = getExcelCjCount(datas);
			for (Integer  key: cjMap.keySet()) {
				if(cjMap.get(key) > 1){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，存在多个位置为"
							+ PartsPositionEnum.getName(key) + "的一级部件");
				}
			}
			
		}

		
		// 汇总所有sheet的装机清单数据
		List<LoadingList> all = new ArrayList<LoadingList>();
		for (Integer sheetIndex : datasMap.keySet()) {
			all.addAll(datasMap.get(sheetIndex));
		}
		// 获取excel中相同件号序列号的数量
		Map<String, Integer> xlhCountMap = getExcelXlhCount(all);
		for (String key : xlhCountMap.keySet()) {
			if(xlhCountMap.get(key) > 1){
				String jh = key.split("_")[0];
				String xlh = key.split("_")[1];
				addErrorMessage("件号为"+jh+"，序列号为"+xlh+"在excel中存在多个");
			}
		}
		// 获取excel中相同件号序列号的数量
		Map<String, Integer> nbsbmCountMap = getExcelNbsbmCount(all);
		for (String key : nbsbmCountMap.keySet()) {
			if(nbsbmCountMap.get(key) > 1){
				String jh = key.split("_")[0];
				String pch = key.split("_")[1];
				String nbsbm = key.split("_")[2];
				addErrorMessage("件号为"+jh+(StringUtils.isBlank(pch)?"":"，批次号为"+pch)+"，内部识别码为"+nbsbm+"在excel中存在多个");
			}
		}
	}
	
	/**
	 * 获取所有章节号
	 * @return
	 */
	private List<String> getAllFixChapter(){
		try {
			List<String> resultList = new ArrayList<String>();
			FixChapter param = new FixChapter();
			param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			List<FixChapter> fixChapters = fixChapterService.selectByDprtcode(param);
			for (FixChapter fixChapter : fixChapters) {
				resultList.add(fixChapter.getZjh());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有飞机数据
	 * @return
	 */
	private Map<String, String> getAllPlaneData(){
		Map<String, String> resultMap = new HashMap<String, String>();
		PlaneData pd = new PlaneData();
		pd.setZt(1);
		List<PlaneData> planeDatas = planeDataService.findAllWithFjjx(pd);
		for (PlaneData planeData : planeDatas) {
			resultMap.put(planeData.getFjzch(), planeData.getDprtcode());
		}
		return resultMap;
	}
	
	/**
	 * 获取所有飞机数据
	 * @return
	 */
	private Map<String, String> getAuthorityPlaneData(){
		Map<String, String> resultMap = new HashMap<String, String>();
		PlaneData pd = new PlaneData();
		pd.setZt(1);
		List<PlaneData> planeDatas = planeDataService.findAllWithFjjxAuthority(pd);
		for (PlaneData planeData : planeDatas) {
			resultMap.put(planeData.getFjzch(), planeData.getDprtcode());
		}
		return resultMap;
	}
	
	/**
	 * 获取所有航材主数据
	 * @return
	 */
	private Map<String, HCMainData> getAllHCMainData(){
		Map<String ,HCMainData> resultMap = new HashMap<String, HCMainData>();
		List<HCMainData> hcMainDatas = hCMainDataMapper.selectAllByDprtcode(ThreadVarUtil.getUser().getJgdm());
		for (HCMainData hcMainData : hcMainDatas) {
			resultMap.put(hcMainData.getBjh()+"_"+hcMainData.getDprtcode(), hcMainData);
		}
		return resultMap;
	}
	
	
	/**
	 * 获取该飞机下所有1级部件的位置
	 * @return
	 * @throws ExcelImportException 
	 */
	private Map<Integer, LoadingList> getWzMap(String fjzch, String dprtcode) throws ExcelImportException{
		Map<Integer, LoadingList> resultMap = new HashMap<Integer, LoadingList>();
		LoadingList param = new LoadingList();
		param.setFjzch(fjzch);
		param.setDprtcode(dprtcode);
		param.setZt(1);
		param.setCj(1);
		List<LoadingList> list = loadingListEditableMapper.select(param);
		for (LoadingList loadingList : list) {
			if( !PartsPositionEnum.PLANE_BODY.getId().equals(loadingList.getWz())){	// 非机身
				if(!resultMap.containsKey(loadingList.getWz())){
					resultMap.put(loadingList.getWz(), loadingList);
				}else{
					throw new ExcelImportException("该飞机下已存在多个位置为" 
							+ PartsPositionEnum.getName(loadingList.getWz()) + "的一级部件");
				}
			}
		}
		return resultMap;
		
	}
	
	/**
	 * 获取该组织机构下的所有件号序列号
	 * @param list
	 * @return
	 */
	private Map<String, LoadingList> getJhAndXlhMap(String dprtcode){
		
		// 获取db中装上的装机清单数据
		LoadingList param = new LoadingList();
		param.setDprtcode(dprtcode);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("noXlh", "noXlh");
		param.setParamsMap(paramsMap);
		param.setZt(1);
		List<LoadingList> dbList = loadingListEditableMapper.select(param);
		
		Map<String, LoadingList> resultMap = new HashMap<String, LoadingList>();
		for (LoadingList ll : dbList) {
			if(!StringUtils.isBlank(ll.getXlh())){
				resultMap.put(ll.getJh() + "_" + ll.getXlh(), ll);
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取该组织机构下的所有件号批次号内部识别码
	 * @param list
	 * @return
	 */
	private Map<String, LoadingList> getNbsbmMap(String dprtcode){
		
		// 获取db中装上的装机清单数据
		LoadingList param = new LoadingList();
		param.setDprtcode(dprtcode);
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("noNbsbm", "noNbsbm");
		param.setParamsMap(paramsMap);
		param.setZt(1);
		List<LoadingList> dbList = loadingListEditableMapper.select(param);
		
		Map<String, LoadingList> resultMap = new HashMap<String, LoadingList>();
		for (LoadingList ll : dbList) {
			if(!StringUtils.isBlank(ll.getNbsbm())){
				resultMap.put(ll.getJh() + "_" + ll.getPch() + "_" + ll.getNbsbm(), ll);
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取excel中相同件号序列号的数量
	 * @param list
	 * @return
	 */
	private Map<String , Integer> getExcelXlhCount(List<LoadingList> list){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if(list != null && !list.isEmpty()){
			for (LoadingList ll : list) {
				if(!StringUtils.isBlank(ll.getXlh()) && ll.getZt() == 1){
					String key = ll.getJh() + "_" + ll.getXlh();
					if(!resultMap.containsKey(key)){
						resultMap.put(key , 1);
					}else{
						resultMap.put(key, resultMap.get(key)+1);
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取excel中相同件号序列号的数量
	 * @param list
	 * @return
	 */
	private Map<Integer , Integer> getExcelCjCount(List<LoadingList> list){
		Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		if(list != null && !list.isEmpty()){
			for (LoadingList ll : list) {
				if(ll.getCj() == 1 && ll.getWz() != 0 && ll.getZt() == 1){
					Integer key = ll.getWz();
					if(!resultMap.containsKey(key)){
						resultMap.put(key , 1);
					}else{
						resultMap.put(key, resultMap.get(key)+1);
					}
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * 获取excel中相同件号批次号内部识别码的数量
	 * @param list
	 * @return
	 */
	private Map<String , Integer> getExcelNbsbmCount(List<LoadingList> list){
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		if(list != null && !list.isEmpty()){
			for (LoadingList ll : list) {
				if(!StringUtils.isBlank(ll.getNbsbm()) && ll.getZt() == 1){
					String key = ll.getJh() + "_" + ll.getPch() + "_" + ll.getNbsbm();
					if(!resultMap.containsKey(key)){
						resultMap.put(key , 1);
					}else{
						resultMap.put(key, resultMap.get(key)+1);
					}
				}
			}
		}
		return resultMap;
	}
	
	@Override
	public int writeToDB(Map<Integer, List<LoadingList>> datasMap) {
		
		// 汇总所有sheet的装机清单数据
		List<LoadingList> datas = new ArrayList<LoadingList>();
		for (Integer sheetIndex : datasMap.keySet()) {
			datas.addAll(datasMap.get(sheetIndex));
		}
		
		// 批量插入装机清单数据
		int count = 0;
		List<LoadingList> temp = new ArrayList<LoadingList>();
		for (int i = 0; i < datas.size(); i++) {
			temp.add(datas.get(i));
			if(temp.size() >= 500 || i == datas.size() - 1){
				count += loadingListEditableMapper.batchMerge(temp);
				temp.clear();
			}
		}
		
		// 写入日志
		String czls = UUID.randomUUID().toString();
		List<String> insertList = new ArrayList<String>();
		List<String> updateList = new ArrayList<String>();
		for (LoadingList ll : datas) {
			if("2".equals(String.valueOf(ll.getParamsMap().get("isNew")))){
				insertList.add(ll.getId());
			}else{
				updateList.add(ll.getId());
			}
		}
		if(!insertList.isEmpty()){
			commonRecService.write("id", insertList, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		
		if(!updateList.isEmpty()){
			commonRecService.write("id", updateList, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}
		//TODO 如果将控制类型改为非控制件，定检件改为非定检件，则删除相应的时控件数据和定检件数据
		return count;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<LoadingList>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList) throws ExcelImportException {
		// 结果集
		Map<Integer, List<LoadingList>> resultMap = new TreeMap<Integer, List<LoadingList>>();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应装机清单数据
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			// 所有航材主数据
			Map<String ,HCMainData> hcMainDatas = getAllHCMainData();
			// 获取该组织机构下的所有件号序列号
			Map<String, LoadingList> xlhMap = getJhAndXlhMap(ThreadVarUtil.getUser().getJgdm());
			// 获取该组织机构下的所有件号批次号内部识别码
			Map<String, LoadingList> nbsbmMap = getNbsbmMap(ThreadVarUtil.getUser().getJgdm());
			List<LoadingList> list = new ArrayList<LoadingList>();
			LoadingList ll;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				ll = new LoadingList();
				
				/*
				 * 读取excel值
				 */
				ll.setFjzch(String.valueOf((this.getParam("fjzch"))));	// 飞机注册号
				ll.setCj(convertToInteger(bean.get(0), 1));	// 层级
				ll.setZjh(bean.get(1));	// 章节号
				ll.setJh(bean.get(2));	// 件号
				ll.setXlh(bean.get(3));	// 序列号
				ll.setCjjh(bean.get(4));	// 厂家件号
				ll.setYwmc(bean.get(5));	// 英文名称
				ll.setZwmc(bean.get(6));	// 中文名称
				ll.setNbsbm(bean.get(7));	// 内部识别码
				ll.setWz(convertToInteger(bean.get(8), 0));	// 位置
				ll.setXkzh(bean.get(9));	// 许可证号
				ll.setShzh(bean.get(10));	// 适航证号
				ll.setPch(bean.get(11));	// 批次号
				ll.setZjsl(convertToInteger(bean.get(12), 1));	// 装机数量
				ll.setBjgzjl(bean.get(13));	// 改装记录
				ll.setBz(bean.get(14));	// 备注
				ll.setScrq(convertToDate(bean.get(15)));	// 生产日期
				ll.setAzrq(convertToDate(bean.get(16)));	// 安装日期
				ll.setAzjldh(bean.get(17));	// 安装记录单号	
				ll.setCcrq(convertToDate(bean.get(18)));	// 拆除日期
				ll.setCcjldh(bean.get(19));	// 拆除记录单号
				ll.setKzlx(convertToInteger(bean.get(20), 3));	// 控制类型
				ll.setLlklx(convertToInteger(bean.get(21), 0));	// 履历卡类型
				// 履历卡导入类型0、1、2与数据库类型1、2、3对应
				if(ll.getLlklx() != INTEGER_TYPE_ERROR){
					ll.setLlklx(ll.getLlklx() + 1);
				}
				ll.setLlkbh(bean.get(22));	// 履历卡编号
				ll.setIsDj(convertToInteger(bean.get(23), 0));	// 是否定检
				ll.setZt(convertToInteger(bean.get(24), 0));	// 状态
				
				/*
				 * 设置默认值
				 */
				ll.setBjlx(ComponentTypeEnum.COMPONENT.getCode());	// 部件
				ll.setSxzt(0);	// 未生效
				ll.setTbbs(SynchronzeEnum.TO_DO.getCode());	// 待同步
				ll.setWhrid(ThreadVarUtil.getUser().getId());
				ll.setWhdwid(ThreadVarUtil.getUser().getBmdm());
				ll.setWhsj(new Date());
				ll.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				// 序列号管理件的装机数量只能为1
				if(StringUtils.isNotBlank(ll.getXlh()) && !INTEGER_TYPE_ERROR.equals(ll.getZjsl())){
					ll.setZjsl(1);
				}
				
				/*
				 * 继承航材主数据
				 */
				if(hcMainDatas.containsKey(ll.getJh()+"_"+ll.getDprtcode())){
					HCMainData hcMainData = hcMainDatas.get(ll.getJh()+"_"+ll.getDprtcode());
					if(StringUtils.isBlank(ll.getZwmc())){	// 中文名称
						ll.setZwmc(hcMainData.getZwms());
					}
					if(StringUtils.isBlank(ll.getYwmc())){	// 英文名称
						ll.setYwmc(hcMainData.getYwms());
					}
					if(StringUtils.isBlank(ll.getCjjh())){	// 厂家件号
						ll.setCjjh(hcMainData.getCjjh());
					}
					if(StringUtils.isBlank(ll.getZjh())){	// 章节号
						ll.setZjh(hcMainData.getZjh());
					}
					if(StringUtils.isBlank(ll.getBz())){	// 备注
						ll.setBz(hcMainData.getBz());
					}
				}
				
				/*
				 * 如果已存在相同件号、序列号的拆下或作废的部件，则用原来的部件
				 */
				String xlhKey = ll.getJh() + "_" + ll.getXlh();
				String nbsbmKey = ll.getJh() + "_" + ll.getPch() + "_" + ll.getNbsbm();
				if(ll.getZt() == 1 && !StringUtils.isBlank(ll.getXlh()) && xlhMap.containsKey(xlhKey)){
					ll.setId(xlhMap.get(xlhKey).getId());
					ll.getParamsMap().put("isNew", "0");
				}
				
				if(ll.getZt() == 1 && StringUtils.isBlank(ll.getXlh()) 
						&& !StringUtils.isBlank(ll.getNbsbm()) && nbsbmMap.containsKey(nbsbmKey)){
					ll.setId(nbsbmMap.get(nbsbmKey).getId());
					ll.getParamsMap().put("isNew", "1");
				}
				
				if(ll.getParamsMap().get("isNew") == null){
					ll.setId(UUID.randomUUID().toString());
					ll.getParamsMap().put("isNew", "2");
				}
				list.add(ll);
			}
			
			
			// 设置父节点id
			if(!list.isEmpty()){
				LoadingList param = new LoadingList();
				param.setFjzch(list.get(0).getFjzch());
				param.setBjlx(ComponentTypeEnum.WHOLE.getCode());
				param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				LoadingList plane = loadingListEditableMapper.selectByPlane(param);
				if(plane != null){
					String planeid = plane.getId();
					LoadingList fjd = null;
					for (int j = 0; j < list.size(); j++) {
						ll = list.get(j);
						if(ll.getCj() == 1){
							fjd = ll;
							ll.setFjdid(planeid);
						}else if(fjd != null && ll.getCj() == 2){
							ll.setFjdid(fjd.getId());
							ll.setWz(fjd.getWz());
						}
					}
				} 
			}
			
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}

//	@Override
//	protected void initConfig() {
//		configureAttr(0, "cj", "cj", "层级", AttrType.INTEGER)
//			.setNotNull(true);
//		configureAttr(1, "zjh", "zjh", "章节号", AttrType.STRING);
//		configureAttr(2, "jh", "jh", "件号", AttrType.STRING);
//		configureAttr(3, "xlh", "xlh", "序列号", AttrType.STRING);
//		configureAttr(4, "cjjh", "cjjh", "厂家件号", AttrType.STRING);
//		configureAttr(5, "ywmc", "ywmc", "英文名称", AttrType.STRING);
//		configureAttr(6, "zwmc", "zwmc", "中文名称", AttrType.STRING);
//		configureAttr(7, "nbsbm", "nbsbm", "内部识别码", AttrType.STRING);
//		configureAttr(8, "wz", "wz", "位置", AttrType.INTEGER);
//		configureAttr(9, "xkzh", "xkzh", "许可证号", AttrType.STRING);
//		configureAttr(10, "shzh", "shzh", "适航证号", AttrType.STRING);
//		configureAttr(11, "pch", "pch", "批次号", AttrType.STRING);
//		configureAttr(12, "zjsl", "zjsl", "数量", AttrType.INTEGER);
//		configureAttr(13, "bjgzjl", "bjgzjl", "改装记录", AttrType.STRING);
//		configureAttr(14, "bz", "bz", "备注", AttrType.STRING);
//		configureAttr(15, "scrq", "scrq", "生产日期", AttrType.DATE);
//		configureAttr(16, "azrq", "azrq", "安装日期", AttrType.DATE);
//		configureAttr(17, "azjldh", "azjldh", "安装记录单号", AttrType.STRING);
//		configureAttr(18, "ccrq", "ccrq", "拆除日期", AttrType.DATE);
//		configureAttr(19, "ccjldh", "ccjldh", "拆除记录单号", AttrType.STRING);
//		configureAttr(20, "kzlx", "kzlx", "控制类型", AttrType.INTEGER);
//		configureAttr(21, "llklx", "llklx", "履历卡类型", AttrType.INTEGER);
//		configureAttr(22, "llkbh", "llkbh", "履历卡编号", AttrType.STRING);
//		configureAttr(23, "isDj", "is_dj", "是否定检", AttrType.INTEGER);
//	}
	


}
