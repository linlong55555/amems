package com.eray.thjw.basic.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.CoverPlateInformationMapper;
import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("coverPlateInformationExcelImporter")
public class CoverPlateInformationExcelImporter extends AbstractExcelImporter<CoverPlateInformation> {
	
	@Autowired
	private CoverPlateInformationMapper coverPlateInformationMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private CommonService commonService;

	@Resource
	private ActTypeMapper actTypeMapper;
	
	@Resource
	private ZoneStationMapper zoneStationMapper;
	
	/**
	 * 当前用户获取所有机型
	 * @return
	 */
	private List<String> getAllPlaneModelData(){
		try {
			List<String> resultList = new ArrayList<String>();
			User user=ThreadVarUtil.getUser();
			ActType actType1=new ActType();
			actType1.setDprtcode(user.getJgdm());
			actType1.getParamsMap().put("userId", user.getId());
			actType1.getParamsMap().put("userType", user.getUserType());
			List<ActType> actTypelist = actTypeMapper.findAllPlaneModelData(actType1);
			for (ActType actType : actTypelist) {
				resultList.add(actType.getFjjx());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param fjjx 
	 * @Description 当前用户所有区域
	 * @CreateTime 2017年11月29日 下午2:58:39
	 * @CreateBy 林龙
	 * @return
	 */
	private List<String> getAllQY(String fjjx) {
		try {
			List<String> resultList = new ArrayList<String>();
			User user=ThreadVarUtil.getUser();
			ZoneStation zoneStation1=new ZoneStation();
			zoneStation1.setDprtcode(user.getJgdm());
			zoneStation1.setJx(fjjx);
			zoneStation1.setLx(1); //区域
			List<ZoneStation> zoneStationlist = zoneStationMapper.selectByJx(zoneStation1);
			for (ZoneStation zoneStation: zoneStationlist) {
				resultList.add(zoneStation.getSz());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void validateParam(Map<Integer, List<CoverPlateInformation>> datasMap) throws ExcelImportException {
		// 所有飞机机型
		List<String> planeModelList = getAllPlaneModelData();
		
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的盖板数据
			List<CoverPlateInformation> datas = datasMap.get(sheetIndex);
		
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
		
			// 所在机型区域
		
			
			CoverPlateInformation cpi ;
			 String tempjx = "";
			 String tempbh = "";
			for (int i = 0; i < datas.size(); i++) {
				cpi = datas.get(i);
				
				tempjx = cpi.getFjjx();  //机型
				tempbh = cpi.getGbbh();  //编号
				List<String> qyList = getAllQY(tempjx);
				if(StringUtils.isBlank(cpi.getFjjx())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机机型不能为空!");
				}
				if(StringUtils.isBlank(cpi.getGbbh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，盖板编号不能为空!");
				}
				
				// 长度验证
				if(Utils.Str.getLength(cpi.getFjjx()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机机型的最大长度为50!");
				}
				if(Utils.Str.getLength(cpi.getGbbh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，盖板编号的最大长度为50!");
				}
				if(Utils.Str.getLength(cpi.getGbmc()) > 100){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，盖板名称的最大长度为100!");
				}
				if(Utils.Str.getLength(cpi.getSzqywz()) > 600){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，所在位置的最大长度为600!");
				}
				BigDecimal bd1=new BigDecimal("100000000");
				if(cpi.getKggs() != null){
					if( cpi.getKggs().compareTo(BIGDECIMAL_TYPE_ERROR) == 0){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，开盖工时格式不正确!");
					}else if(cpi.getKggs().compareTo(bd1)==1){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，开盖工时的最大值不能超过99999999.99!");
					}
				}
				if(cpi.getHggs() != null){
					if( cpi.getHggs().compareTo(BIGDECIMAL_TYPE_ERROR) == 0){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，合盖工时格式不正确!");
					}else if(cpi.getHggs().compareTo(bd1)==1){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，合盖工时的最大值不能超过99999999.99!");
					}
				}
				if(Utils.Str.getLength(cpi.getRlgbbs()) > 100){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，燃料盖板标识的最大长度为100!");
				}
				
				// 格式验证
				if(!StringUtils.isBlank(cpi.getFjjx()) && Utils.Str.isChinese(cpi.getFjjx())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机机型不能含有中文!");
				}
				if(!StringUtils.isBlank(cpi.getGbbh()) && Utils.Str.isChinese(cpi.getGbbh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，盖板编号不能含有中文!");
				}
				if(!StringUtils.isBlank(cpi.getQy()) && Utils.Str.isChinese(cpi.getQy())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，区域不能含有中文!");
				}
				
				// 区域验证
				if(!StringUtils.isBlank(cpi.getQy()) && !qyList.contains(cpi.getQy())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，区域不存在!");
				}
				
				// 飞机机型验证
				if(!planeModelList.contains(cpi.getFjjx())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机机型不存在或者无权添加该机型下的盖板数据!");
				}
				
			    for (int j = i + 1; j < datas.size(); j++){
				   CoverPlateInformation cpis =(CoverPlateInformation) datas.get(j);
					if(cpis != null && tempbh != null){
						if (tempbh.equals(cpis.getGbbh()) && tempjx.equals(cpis.getFjjx())){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机机型和盖板编号验证唯一!");
						}
					}
	            }
			}
			
		}
	}
	
	@Override
	public int writeToDB(Map<Integer, List<CoverPlateInformation>> datas) throws ExcelImportException {
		List<CoverPlateInformation> list = new ArrayList<CoverPlateInformation>();
		list.addAll(datas.get(0));
		String czls = UUID.randomUUID().toString();
		User user=ThreadVarUtil.getUser();
		Date currentDate = commonService.getSysdate();//当前时间
		for (CoverPlateInformation coverPlateInformation : list) {
			
			coverPlateInformation.setZt(1);
			coverPlateInformation.setDprtcode(user.getJgdm());
			coverPlateInformation.setWhbmid(user.getBmdm());
			coverPlateInformation.setWhrid(user.getId());
			coverPlateInformation.setWhsj(currentDate);
			
			int num=coverPlateInformationMapper.queryCount(coverPlateInformation);
			if(num>0){
				coverPlateInformationMapper.updateByPrimaryKeySelectiveImport(coverPlateInformation);
				commonRecService.write(coverPlateInformation.getId(), TableEnum.D_021, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,coverPlateInformation.getId());
			}else{
				String uuid = UUID.randomUUID().toString();
				coverPlateInformation.setId(uuid);
				coverPlateInformationMapper.insertSelective(coverPlateInformation);
				commonRecService.write(coverPlateInformation.getId(), TableEnum.D_021, ThreadVarUtil.getUser().getId(),czls ,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,coverPlateInformation.getId());
			}
		}
		
		
		
		return 1;
	}

	@Override
	public Map<Integer, List<CoverPlateInformation>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList) throws ExcelImportException {
		// 结果集
		Map<Integer, List<CoverPlateInformation>> resultMap = new TreeMap<Integer, List<CoverPlateInformation>>();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应库存
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			List<CoverPlateInformation> list = new ArrayList<CoverPlateInformation>();
			CoverPlateInformation cpi;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				cpi = new CoverPlateInformation();
				/*
				 * 读取excel值
				 */
				cpi.setFjjx(bean.get(0)); 	//机型
				cpi.setGbbh(bean.get(1));	//盖板编号
				cpi.setGbmc(bean.get(2));	//盖板描述
				cpi.setSzqywz(bean.get(3));	//所在位置
				cpi.setQy(bean.get(4));		//区域
				if (bean.get(5) != null ) {
					cpi.setKggs(convertToBigDecimal(bean.get(5)));	//开盖工时
				}
				if (bean.get(6) != null ) {
					cpi.setHggs(convertToBigDecimal(bean.get(6)));	//盒盖工时
				}
				cpi.setRlgbbs(bean.get(7));	//燃料盖板标识
				list.add(cpi);
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}

}
