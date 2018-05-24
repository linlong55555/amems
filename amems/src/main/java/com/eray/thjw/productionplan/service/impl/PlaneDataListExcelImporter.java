package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.dao.PlaneBaseMapper;
import com.eray.thjw.dao.PlaneModelDataMapper;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.PlaneBase;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.productionplan.dao.PlaneInitDataMapper;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.PlaneInitData;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.PlaneDailyUsageService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("planedatalistexcelimporter")
public class PlaneDataListExcelImporter extends AbstractExcelImporter<PlaneData>{
	
	@Resource
	private CommonRecService commonRecService;

	@Resource
	private DeptInfoMapper deptInfoMapper;
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private PlaneBaseMapper planeBaseMapper;
	
	@Resource
	private PlaneDataMapper planeDataMapper;
	
	@Resource
	private PlaneInitDataMapper planeInitDataMapper;
	
	@Resource
	private PlaneModelDataMapper planeModelDataMapper;
	@Resource
	private PlaneDailyUsageService planeDailyUsageService;
	@Resource
	private LoadingListService loadingListService;
	/**
	 * 获取所有基地
	 * @return
	 */
	private List<String> getAllPlaneBase(){
		try {
			List<String> resultList = new ArrayList<String>();
			User user=ThreadVarUtil.getUser();
			PlaneBase planeBase1=new PlaneBase();
			planeBase1.setDprtcode(user.getJgdm());
			List<PlaneBase> planeBases = planeBaseMapper.findAllBase(planeBase1);
			for (PlaneBase planeBase : planeBases) {
				resultList.add(planeBase.getJdms());
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取所有飞机机型数据
	 * @return
	 */
	private List<String> getAuthorityPlaneData(){
		List<String> resultMap = new ArrayList<String>();
		User user=ThreadVarUtil.getUser();
		PlaneModelData planeModelData1=new PlaneModelData();
		BaseEntity entity = new BaseEntity();
		entity.getParamsMap().put("userId", user.getId());
		List<Map<String,Object>>aircraftDatas = planeModelDataMapper.selectAircraftData(entity);
		
		for (Map<String, Object> map : aircraftDatas) {
			resultMap.add(map.get("FJJX").toString());
		}
		
//		for (PlaneModelData planeModelData : aircraftDatas) {
//			resultMap.add(planeModelData.getFjjx());
//		}
		return resultMap;
	}
	
	/**
	 * 获取所有机型
	 * @return
	 */
	private List<String> getAllPlaneModelData(){
		try {
			List<String> resultList = new ArrayList<String>();
			User user=ThreadVarUtil.getUser();
			PlaneModelData planeModelData1=new PlaneModelData();
			planeModelData1.setDprtcode(user.getJgdm());
			List<PlaneModelData> planeModelDatas = planeModelDataMapper.findAllPlaneModelData(planeModelData1);
			for (PlaneModelData planeModelData : planeModelDatas) {
				resultList.add(planeModelData.getFjjx());
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
	private List< String> getAllPlaneData(){
		List<String> resultList = new ArrayList<String>();
		PlaneData pd = new PlaneData();
		pd.setZt(1);
		List<PlaneData> planeDatas = planeDataService.findAllWithFjjx(pd);
		for (PlaneData planeData : planeDatas) {
			resultList.add(planeData.getFjzch());
		}
		return resultList;
	}
	
	/**
	 * 判断机型是否更新
	 * @return
	 */
	private Boolean getModelState(String fjzch,String fjjx){
		Boolean flag=false;
		PlaneData pd = new PlaneData();
		pd.setFjzch(fjzch);
		pd.setFjjx(fjjx);
		pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		PlaneData planeDatas = planeDataService.selectByPrimaryKeys(pd);
		if(planeDatas==null){
			flag=true;
		}
		return flag;
	}

	
	
	@Override
	public void validateParam(Map<Integer, List<PlaneData>> datasMap)
			throws ExcelImportException {
		// 所有基地
		List<String> planeBaseList = getAllPlaneBase();
		
		// 所有飞机机型
		List<String> planeModelList = getAllPlaneModelData();
		
		// 有权限的飞机机型
		List<String> authorityList = getAuthorityPlaneData();
		
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<PlaneData> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			
			// 飞机机型验证
			if(!datas.isEmpty()  && !StringUtils.isBlank(datas.get(0).getFjzch())){
				if(planeModelList.contains(datas.get(0).getFjjx())){
					// 飞机权限验证
					if(!authorityList.contains(datas.get(0).getFjjx())){
						throw new ExcelImportException("无权添加该机型下的飞机注册号");
					}
				}else{
					throw new ExcelImportException("飞机机型不存在");
				}
			}
			
			PlaneData ll;
		  String temp = "";
		  String tempjx = "";
			for (int i = 0; i < datas.size(); i++) {
				ll = datas.get(i);
				
				temp = ll.getFjzch();
				tempjx = ll.getFjjx();
				
				if(StringUtils.isBlank(ll.getFjjx())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机机型不能为空");
				}
				
				if(StringUtils.isBlank(ll.getFjzch())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机注册号不能为空");
				}
				
			   for (int j = i + 1; j < datas.size(); j++)
	            {
				   if (temp.equals(datas.get(j).getFjzch()))
	                {
					   addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机注册号重复");
	                }
	            }
				
			    
				if(StringUtils.isBlank(ll.getXlh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号不能为空");
				}
				if(StringUtils.isBlank(ll.getJd())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，基地不能为空");
				}
				
				if(!StringUtils.isBlank(ll.getGjdjzh())){
					if(ll.getGjdjzjkrq()==null){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，当国籍登记证号为空时，国籍登记证日期不能为空");
					}
				}
				
				if(ll.getGjdjzjkrq()!=null){
					if(StringUtils.isBlank(ll.getGjdjzh())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，当国籍登记证日期为空时，国籍登记证号不能为空");
					}
				}
				
				if(!StringUtils.isBlank(ll.getShzh())){
					if(ll.getShzjkrq()==null){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，当适航证号为空时，适航证号日期不能为空");
					}
				}
				
				if(ll.getShzjkrq()!=null){
					if(StringUtils.isBlank(ll.getShzh())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，当适航证号日期为空时，适航证号不能为空");
					}
				}
				
				
				if(!StringUtils.isBlank(ll.getWxdtxkzh())){
					if(ll.getDtzzjkrq()==null){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，当无线电台许可证号为空时，电台执照日期不能为空");
					}
				}
				
				if(ll.getDtzzjkrq()!=null){
					if(StringUtils.isBlank(ll.getWxdtxkzh())){
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，当电台执照日期为空时，无线电台许可证号不能为空");
					}
				}
				
				
				
				// 非中文验证
				if(!StringUtils.isBlank(ll.getFjzch()) && Utils.Str.isChinese(ll.getFjzch())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机注册号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getFjjx()) && Utils.Str.isChinese(ll.getFjjx())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，机型不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getXlh()) && Utils.Str.isChinese(ll.getXlh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getGjdjzh()) && Utils.Str.isChinese(ll.getGjdjzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，国际登记证号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getShzh()) && Utils.Str.isChinese(ll.getShzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getWxdtxkzh()) && Utils.Str.isChinese(ll.getWxdtxkzh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，无线电台许可证号不能含有中文");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getFjzch()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞机注册号的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getFjjx()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，机型的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getXlh()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，序列号的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getBzm()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，备注名的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getJd()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，基地的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getJsgzjl()) > 150){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，机身改装记录的最大长度为150");
				}
				if(Utils.Str.getLength(ll.getGjdjzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，国际登记证号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getShzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，适航证号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getWxdtxkzh()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，无线电台许可证号的最大长度为50");
				}
				// 基地存在验证
				if(!StringUtils.isBlank(ll.getJd()) && !planeBaseList.contains(ll.getJd())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，基地不存在");
				}
				// 日期类型验证
				if(ll.getCcrq() != null && ll.getCcrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，出厂日期格式不正确");
				}
				// 日期类型验证
				if(ll.getDtzzjkrq() != null && ll.getDtzzjkrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，出厂日期格式不正确");
				}
				// 日期类型验证
				if(ll.getFxrq() != null && ll.getFxrq().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞行日期不能为空");
				}
				
				//验证时间
				String regEx = "^[0-9]+((:|.)[0-5]?[0-9])?$"; 
				Pattern pat = Pattern.compile(regEx); 
				if(!StringUtils.isBlank(ll.getFxsj())){
					boolean fxsj = pat.matcher(ll.getFxsj()).matches(); 
					if(fxsj==true){
					}else{
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，飞行时间格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getSsdsj())){
					boolean fxsj = pat.matcher(ll.getSsdsj()).matches(); 
					if(fxsj==true){
					}else{
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，搜索灯时间格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getJcsj())){
					boolean fxsj = pat.matcher(ll.getJcsj()).matches(); 
					if(fxsj==true){
					}else{
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，绞车时间格式不正确");
						
					}
				}
				if(!StringUtils.isBlank(ll.getQlxh())){
					try {
						BigDecimal bd=new BigDecimal(ll.getQlxh());
				        BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，起落循环的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，起落循环格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getJcxh())){
					try {
						 BigDecimal bd=new BigDecimal(ll.getJcxh());
						 BigDecimal bd1=new BigDecimal("10000000");
							//类型验证
							if(bd.compareTo(bd1)==1){
								addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，绞车循环的最大值不能超过99999999.99");
							}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，绞车循环格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getWdgxh())){
					try {
						BigDecimal bd=new BigDecimal(ll.getWdgxh());
				        BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，外吊挂循环的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，外吊挂循环格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getzN1())){
					try {
						  BigDecimal bd=new BigDecimal(ll.getzN1());
					        BigDecimal bd1=new BigDecimal("100000000");
							//类型验证
							if(bd.compareTo(bd1)==1){
								addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N1的最大值不能超过99999999.99");
							}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N1格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getzN2())){
					try {
						BigDecimal bd=new BigDecimal(ll.getzN2());
				        BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N2的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N2格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getzN3())){
					try {
						BigDecimal bd=new BigDecimal(ll.getzN3());
						BigDecimal bd1=new BigDecimal("100000000");
						
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N3的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N3格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getzN4())){
					try {
						BigDecimal bd=new BigDecimal(ll.getzN4());
						BigDecimal bd1=new BigDecimal("100000000");
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N4的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N4格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getzN5())){
					try {
						BigDecimal bd=new BigDecimal(ll.getzN5());
						BigDecimal bd1=new BigDecimal("100000000");
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N5的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N5格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getzN6())){
					try {
						BigDecimal bd=new BigDecimal(ll.getzN6());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N6的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，左发-N6格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getyN1())){
					try {
						BigDecimal bd=new BigDecimal(ll.getyN1());
						BigDecimal bd1=new BigDecimal("100000000");
						
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N1的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N1格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getyN2())){
					try {
						BigDecimal bd=new BigDecimal(ll.getyN2());
						BigDecimal bd1=new BigDecimal("100000000");
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N2的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N2格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getyN3())){
					try {
						BigDecimal bd=new BigDecimal(ll.getyN3());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N3的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N3格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getyN4())){
					try {
						BigDecimal bd=new BigDecimal(ll.getyN4());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N4的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N4格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getyN5())){
					try {
						BigDecimal bd=new BigDecimal(ll.getyN5());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N5的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N5格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getyN6())){
					try {
						BigDecimal bd=new BigDecimal(ll.getyN6());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N6的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，右发-N6格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getTS1())){
					try {
						BigDecimal bd=new BigDecimal(ll.getTS1());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，TS1的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，TS1格式不正确");
					}
				}
				if(!StringUtils.isBlank(ll.getTS2())){
					try {
						BigDecimal bd=new BigDecimal(ll.getTS2());
						BigDecimal bd1=new BigDecimal("100000000");
							
						//类型验证
						if(bd.compareTo(bd1)==1){
							addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，TS2的最大值不能超过99999999.99");
						}
					} catch (Exception e) {
						addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，TS2格式不正确");
					}
				}
			}
		}
	}

	
	public static String formateRate(String rateStr){  
        if(rateStr.indexOf(".") != -1){  
            //获取小数点的位置  
            int num = 0;  
            num = rateStr.indexOf(".");  
              
            //获取小数点后面的数字 是否有两位 不足两位补足两位  
            String dianAfter = rateStr.substring(0,num+1);  
            String afterData = rateStr.replace(dianAfter, "");  
            if(afterData.length() < 2){  
               afterData = afterData + "0" ;  
            }else{  
                afterData = afterData;  
            }  
            return rateStr.substring(0,num) + "." + afterData.substring(0,2);  
         }else{  
           return rateStr;  
         }  
   }  
	
	@Override
	public int writeToDB(Map<Integer, List<PlaneData>> datasMap)
			throws ExcelImportException {
		try {
			this.save(datasMap);
		} catch (BusinessException e) {
			throw new ExcelImportException(e.getMessage(), e);
		}
		return 1;
	}

	public void save(Map<Integer, List<PlaneData>> datasMap) throws BusinessException{
		// 汇总所有sheet的装机清单数据
				List<PlaneData> datas = new ArrayList<PlaneData>();
				for (Integer sheetIndex : datasMap.keySet()) {
					datas.addAll(datasMap.get(sheetIndex));
				}
				for (PlaneData planeData : datas) {
					
					// 写入日志
					String czls = UUID.randomUUID().toString();
					PlaneData planeData1=new PlaneData();
					planeData1.setFjzch(planeData.getFjzch());
					planeData1.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					int num=planeDataMapper.queryCount(planeData1);
					PlaneBase planeBase1=new PlaneBase();
					planeBase1.setJdms(planeData.getJd());
					planeBase1.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					PlaneBase planeBase=planeBaseMapper.selectByPrimary1(planeBase1);
					planeData.setJd(planeBase.getId());
					planeData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					if(num>0){
						
						  if(getModelState(planeData.getFjzch(),planeData.getFjjx())){
							  throw new BusinessException("飞机机型不能更新");
			           		}
						
						LogOperationEnum logOperationEnum = LogOperationEnum.EDIT;
						planeDataMapper.updateByPrimaryKeySelective(planeData);
						commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_007, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
					
						// 4.修改飞机日使用量监控指标
						planeDailyUsageService.edit(planeData, czls, logOperationEnum);
						// 5.修改飞机装机清单
						loadingListService.editPlaneEditable(planeData, czls, logOperationEnum);
					}else{
						
						//验证账号注册数量
						int count1 = this.planeDataMapper.queryCountfj(ThreadVarUtil.getUser().getJgdm());
						//查询账号注册最大数量
						DeptInfo accounts = deptInfoMapper.selectCounts(ThreadVarUtil.getUser().getJgdm());
						
						if(accounts!=null){
							if(accounts.getZcfj_max()!=null&&count1 >= accounts.getZcfj_max()&&accounts.getZcfj_max()!=0){
 								throw new BusinessException("飞机注册号不能超过组织机构最大注册数量");
							}
						}
						
						planeDataMapper.insertSelective(planeData);
						commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_007, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						
						LogOperationEnum logOperationEnum = LogOperationEnum.SAVE_WO;
						// 3.新增飞机日使用量监控指标
						planeDailyUsageService.add(planeData, czls, logOperationEnum);
						// 4.新增飞机装机清单
						loadingListService.insertPlaneEditable(planeData, czls, logOperationEnum);
					
					
					}
					if(planeData.getFxrq()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_date_rq");
						planeInitData.setInitXmms("系统初始化日期");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
					    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
					    String str = sdf.format(planeData.getFxrq());
						planeInitData.setInitValue(str);
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getFxsj()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_time_jsfx");
						planeInitData.setInitXmms("系统初始化机身飞行时间");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitValue(planeData.getFxsj().toString());
						planeInitData.setJsbs(0);
						
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getSsdsj()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_time_ssd");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化搜索灯时间");
						planeInitData.setInitValue(planeData.getSsdsj().toString());
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getJcsj()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_time_jc");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化绞车时间");
						planeInitData.setInitValue(planeData.getJcsj().toString());
						planeInitData.setJsbs(0);
						
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getQlxh()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_qlj");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化起落架循环");
						planeInitData.setInitValue(formateRate(planeData.getQlxh().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getJcxh()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_jc");
						planeInitData.setInitXmms("系统初始化绞车循环");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitValue(formateRate(planeData.getJcxh().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getWdgxh()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_wdg");
						planeInitData.setInitXmms("系统初始化外吊挂循环");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitValue(formateRate(planeData.getWdgxh().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getzN1()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_l_n1");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化发动机左发N1循环");
						planeInitData.setInitValue(formateRate(planeData.getzN1().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getzN2()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_l_n2");
						planeInitData.setInitXmms("系统初始化发动机左发N2循环");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitValue(formateRate(planeData.getzN2().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getzN3()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmbh("init_loop_l_n3");
						planeInitData.setInitXmms("系统初始化发动机左发N3循环");
						planeInitData.setInitValue(formateRate(planeData.getzN3().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getzN4()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmbh("init_loop_l_n4");
						planeInitData.setInitXmms("系统初始化发动机左发N4循环");
						planeInitData.setInitValue(formateRate(planeData.getzN4().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getzN5()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_l_n5");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化发动机左发N5循环");
						planeInitData.setInitValue(formateRate(planeData.getzN5().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getzN6()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_l_n6");
						planeInitData.setInitXmms("系统初始化发动机左发N6循环");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitValue(formateRate(planeData.getzN6().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getyN1()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_r_n1");
						planeInitData.setInitXmms("系统初始化发动机右发N1循环");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitValue(formateRate(planeData.getyN1().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getyN2()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmbh("init_loop_r_n2");
						planeInitData.setInitXmms("系统初始化发动机右发N2循环");
						planeInitData.setInitValue(formateRate(planeData.getyN2().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getyN3()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_r_n3");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化发动机右发N3循环");
						planeInitData.setInitValue(formateRate(planeData.getyN3().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getyN4()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_r_n4");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化发动机右发N4循环");
						planeInitData.setInitValue(formateRate(planeData.getyN4().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getyN5()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmbh("init_loop_r_n5");
						planeInitData.setInitXmms("系统初始化发动机右发N5循环");
						planeInitData.setInitValue(formateRate(planeData.getyN5().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getyN6()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmbh("init_loop_r_n6");
						planeInitData.setInitXmms("系统初始化发动机右发N6循环");
						planeInitData.setInitValue(formateRate(planeData.getyN6().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getTS1()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmbh("init_loop_ts1");
						planeInitData.setInitXmms("系统初始化TS1");
						planeInitData.setInitValue(formateRate(planeData.getTS1().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
					if(planeData.getTS2()!=null){
						PlaneInitData planeInitData =new PlaneInitData();
						planeInitData.setFjzch(planeData.getFjzch());
						planeInitData.setInitXmbh("init_loop_ts2");
						planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
						planeInitData.setInitXmms("系统初始化TS2");
						planeInitData.setInitValue(formateRate(planeData.getTS2().toString()));
						planeInitData.setJsbs(0);
						int num1=planeInitDataMapper.queryCount(planeInitData);
						if(num1>0){
							planeInitDataMapper.updateByPrimaryKeySelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}else{
							planeInitDataMapper.insertSelective(planeInitData);
							commonRecService.writeByWhere("b.fjzch = '"+planeData.getFjzch().replaceAll("'", "''")+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'", TableEnum.D_00701, ThreadVarUtil.getUser().getId(),czls,LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"b.fjzch = '"+planeData.getFjzch()+"' and b.dprtCode = '"+ThreadVarUtil.getUser().getJgdm()+"'");
						}
					}
				}
	}
	
	
	@Override
	public Map<Integer, List<PlaneData>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		// 结果集
				Map<Integer, List<PlaneData>> resultMap = new TreeMap<Integer, List<PlaneData>>();
				// 循环sheet
				for (Integer sheetIndex : totalMapList.keySet()) {
					// sheet对应装机清单数据
					List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
					List<PlaneData> list = new ArrayList<PlaneData>();
					PlaneData ll;
					Map<Integer, String> bean;
					for (int i = 0; i < mapList.size(); i++) {
						bean = mapList.get(i);
						ll = new PlaneData();
						/*
						 * 读取excel值
						 */
						ll.setFjzch(bean.get(0));	// 供应商编号
						ll.setFjjx(bean.get(1));	// 供应商名称
						ll.setXlh(bean.get(2));	// 	联系人
						ll.setBzm(bean.get(3));	// 联系电话
						ll.setCcrq(convertToDate(bean.get(4)));	// 地址
						ll.setJd(bean.get(5));	// 地址
						ll.setJsgzjl(bean.get(6));	// 地址
						ll.setGjdjzh(bean.get(7));	// 地址
						ll.setGjdjzhrq(convertToDate(bean.get(8)));
						ll.setShzh(bean.get(9));	// 地址
						ll.setShzjkrq(convertToDate(bean.get(10)));
						ll.setWxdtxkzh(bean.get(11));	// 地址
						ll.setWxdtxkzhrq(convertToDate(bean.get(12)));
						ll.setDtzzjkrq(convertToDate(bean.get(13)));	// 地址
						ll.setZt(1);	// 有效
						ll.setCjrid(ThreadVarUtil.getUser().getId());//创建人
						if(ThreadVarUtil.getUser().getBmdm()==null){
							ll.setBmid("");//部门
						}else{
							ll.setBmid(ThreadVarUtil.getUser().getBmdm());//部门
						}
						ll.setCjsj( new Date());//创建时间
						ll.setFxrq(convertToDate(bean.get(14)));
						ll.setFxsj(bean.get(15));
						ll.setQlxh(bean.get(16));
						ll.setSsdsj(bean.get(17));
						ll.setJcsj(bean.get(18));
						ll.setJcxh(bean.get(19));
						ll.setWdgxh(bean.get(20));
						ll.setzN1(bean.get(21));
						ll.setzN2(bean.get(22));
						ll.setzN3(bean.get(23));
						ll.setzN4(bean.get(24));
						ll.setzN5(bean.get(25));
						ll.setzN6(bean.get(26));
						ll.setyN1(bean.get(27));
						ll.setyN2(bean.get(28));
						ll.setyN3(bean.get(29));
						ll.setyN4(bean.get(30));
						ll.setyN5(bean.get(31));
						ll.setyN6(bean.get(32));
						ll.setTS1(bean.get(33));
						ll.setTS2(bean.get(34));
						list.add(ll);
					}
					resultMap.put(sheetIndex, list);
				}
				return resultMap;
	}

}
