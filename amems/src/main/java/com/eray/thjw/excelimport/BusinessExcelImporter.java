package com.eray.thjw.excelimport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.WorkRequireMapper;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.BusinessMapper;
import com.eray.thjw.training.dao.BusinessToMaintenancePersonnelMapper;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-14 下午12:01:31
 * @CreateBy 孙霁	
 */
@Service("businessExcelImporter")
public class BusinessExcelImporter extends AbstractExcelImporter<Object>{

	@Resource
	private ActTypeMapper actTypeMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private BusinessMapper businessMapper;
	@Resource
	private MaintenancePersonnelMapper maintenancePersonnelMapper;
	@Resource
	private WorkRequireMapper workRequireMapper;
	@Resource
	private BusinessToMaintenancePersonnelMapper businessToMaintenancePersonnelMapper;
	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<Object>> datasMap)
			throws ExcelImportException {
		// TODO Auto-generated method stub
		//获取登入user
		User user = ThreadVarUtil.getUser();
		List<Object> businessList = new ArrayList<Object>();
		List<Object> workRequireList = new ArrayList<Object>();
		List<Object> businessToMaintenancePersonnelList = new ArrayList<Object>();
		List<String> jxList = getJxByDprtcode(user.getJgdm());
		boolean b;
			// 工作表对应的的飞行记录本数据
			businessList = datasMap.get(0);
			if(businessList !=null){
				Business business;
				for (int i = 0; i < businessList.size(); i++) {
					business = (Business) businessList.get(i);
					// 非空验证
					if(StringUtils.isBlank(business.getDgbh())){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，岗位编号不能为空");
					}
					if(StringUtils.isBlank(business.getDgmc()) ){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，岗位名称不能为空");
					}
					// 非中文验证
					if(!StringUtils.isBlank(business.getDgbh()) && Utils.Str.isChinese(business.getDgbh())){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，岗位编号不能含有中文");
					}
					// 长度验证
					if(Utils.Str.getLength(business.getDgbh()) > 50){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，岗位编号最大长度为50");
					}
					if(Utils.Str.getLength(business.getDgmc()) > 60){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，岗位名称最大长度为60");
					}
					if(Utils.Str.getLength(business.getBz()) > 300){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，岗位编号最大长度为300");
					}
				}
				Business business1;
				Business business2;
				//冒泡对比是否含有重复数据
				for (int i = 0; i < businessList.size()-1; i++) {
					b = false;
					for (int a = i+1; a < businessList.size(); a++) {
						business1 =(Business) businessList.get(i);
						business2 =(Business)businessList.get(a);
						if(business1.getDgbh() != null && business2.getDgbh() != null){
							if(business1.getDgbh().equals(business2.getDgbh())){
								b = true;
							}
						}
					}
					if(b){
						addErrorMessage("第1个工作表，第"+(i+3)+"行，请确保岗位编号数据唯一");
					}
				}
				
				
			}
			// 工作表对应的的飞行记录本数据
			workRequireList = datasMap.get(1);
			if(workRequireList !=null){
				WorkRequire workRequire;
				for (int i = 0; i < workRequireList.size(); i++) {
					workRequire = (WorkRequire) workRequireList.get(i);
					// 非空验证
					if(StringUtils.isBlank(workRequire.getGwbh())){
						addErrorMessage("第2个工作表，第"+(i+3)+"行，岗位编号不能为空");
					}
					if(StringUtils.isBlank(workRequire.getGwyq()) ){
						addErrorMessage("第2个工作表，第"+(i+3)+"行，岗位要求不能为空");
					}
					// 非中文验证
					if(!StringUtils.isBlank(workRequire.getGwbh()) && Utils.Str.isChinese(workRequire.getGwbh())){
						addErrorMessage("第2个工作表，第"+(i+3)+"行，岗位编号不能含有中文");
					}
					// 长度验证
					if(Utils.Str.getLength(workRequire.getGwbh()) > 50){
						addErrorMessage("第2个工作表，第"+(i+3)+"行，岗位编号最大长度为50");
					}
					if(Utils.Str.getLength(workRequire.getGwyq()) > 1000){
						addErrorMessage("第2个工作表，第"+(i+3)+"行，岗位名称最大长度为1000");
					}
				}
			}
			// 工作表对应的的飞行记录本数据
			businessToMaintenancePersonnelList = datasMap.get(2);
			if(businessToMaintenancePersonnelList !=null){
				BusinessToMaintenancePersonnel businessToMaintenancePersonnel;
				for (int i = 0; i < businessToMaintenancePersonnelList.size(); i++) {
					businessToMaintenancePersonnel = (BusinessToMaintenancePersonnel) businessToMaintenancePersonnelList.get(i);
					// 非空验证
					if(StringUtils.isBlank(businessToMaintenancePersonnel.getGwbh())){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，岗位编号不能为空");
					}
					if(StringUtils.isBlank(businessToMaintenancePersonnel.getRydabh()) ){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，人员档案编号不能为空");
					}
					// 非中文验证
					if(!StringUtils.isBlank(businessToMaintenancePersonnel.getGwbh()) && Utils.Str.isChinese(businessToMaintenancePersonnel.getGwbh())){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，岗位编号不能含有中文");
					}
					if(!StringUtils.isBlank(businessToMaintenancePersonnel.getRydabh()) && Utils.Str.isChinese(businessToMaintenancePersonnel.getRydabh())){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，人员档案编号不能含有中文");
					}
					// 长度验证
					if(Utils.Str.getLength(businessToMaintenancePersonnel.getGwbh()) > 50){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，岗位编号最大长度为50");
					}
					if(Utils.Str.getLength(businessToMaintenancePersonnel.getRydabh()) > 50){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，人员档案编号最大长度为50");
					}
					
					if(!StringUtils.isBlank(businessToMaintenancePersonnel.getFjjx()) && Utils.Str.isChinese(businessToMaintenancePersonnel.getFjjx())){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，机型不能含有中文");
					}
					
					if(!StringUtils.isBlank(businessToMaintenancePersonnel.getFjjx()) && Utils.Str.getLength(businessToMaintenancePersonnel.getFjjx()) > 50){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，机型最大长度为50");
					}
					
					if(!StringUtils.isBlank(businessToMaintenancePersonnel.getFjjx())&&!jxList.contains(businessToMaintenancePersonnel.getFjjx())){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，机型不存在");
					}
					
					if(!StringUtils.isBlank(businessToMaintenancePersonnel.getLb()) && Utils.Str.getLength(businessToMaintenancePersonnel.getLb()) > 100){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，类别最大长度为100");
					}
					
					// 日期验证
					if(businessToMaintenancePersonnel.getKsrq() != null && businessToMaintenancePersonnel.getKsrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，开始日期格式不正确");
					}
					if(businessToMaintenancePersonnel.getJzrq() != null && businessToMaintenancePersonnel.getJzrq().compareTo(DATE_TYPE_ERROR) == 0){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，截止日期格式不正确");
					}
					if(businessToMaintenancePersonnel.getKsrq() != null && businessToMaintenancePersonnel.getJzrq() != null 
							&& businessToMaintenancePersonnel.getKsrq().compareTo(DATE_TYPE_ERROR) != 0 && businessToMaintenancePersonnel.getJzrq().compareTo(DATE_TYPE_ERROR) != 0 ){
						if(businessToMaintenancePersonnel.getJzrq().before(businessToMaintenancePersonnel.getKsrq())){
							addErrorMessage("第3个工作表，第"+(i+3)+"行，开始日期不能小于截止日期");
						}
					}
				}
				BusinessToMaintenancePersonnel businessToMaintenancePersonnel1;
				BusinessToMaintenancePersonnel businessToMaintenancePersonnel2;
				//冒泡对比是否含有重复数据
				for (int i = 0; i < businessToMaintenancePersonnelList.size()-1; i++) {
					b = false;
					for (int a = i+1; a < businessToMaintenancePersonnelList.size(); a++) {
						businessToMaintenancePersonnel1 =(BusinessToMaintenancePersonnel) businessToMaintenancePersonnelList.get(i);
						businessToMaintenancePersonnel2 =(BusinessToMaintenancePersonnel)businessToMaintenancePersonnelList.get(a);
						StringBuffer sb1 = new StringBuffer();
						StringBuffer sb2 = new StringBuffer();
						sb1.append(businessToMaintenancePersonnel1.getRydabh()).append(businessToMaintenancePersonnel1.getGwbh());
						sb1.append(businessToMaintenancePersonnel1.getFjjx() == null?"-":businessToMaintenancePersonnel1.getFjjx());
						sb2.append(businessToMaintenancePersonnel2.getRydabh()).append(businessToMaintenancePersonnel2.getGwbh());
						sb2.append(businessToMaintenancePersonnel2.getFjjx() == null?"-":businessToMaintenancePersonnel2.getFjjx());
						if(sb1.toString().equals(sb2.toString())){
							b = true;
						}
					}
					if(b){
						addErrorMessage("第3个工作表，第"+(i+3)+"行，请确保岗位编号和人员档案编号及机型数据唯一");
					}
				}
				
			}
			Business business = new Business();
			business.setDprtcode(user.getJgdm());
			//获取数据库岗位编号
			List<Business> businessOldList = businessMapper.queryAllBusiness(business);
			//获取数据库人员档案编号
			List<MaintenancePersonnel> maintenancePersonnelList = maintenancePersonnelMapper.queryAllJgdm(user.getJgdm());
			//验证岗位要求 编号是否存在数据库 和 新的岗位信息
			WorkRequire workRequire;
			for (Object object : workRequireList) {
				b = true;
				workRequire = (WorkRequire)object;
				//遍历编号是否存在数据库
				for (Business OldBusiness : businessOldList) {
					if(OldBusiness.getDgbh().equals(workRequire.getGwbh())){
						b = false;
					}
				}
				//遍历编号是否存在新的岗位信息
				Business newBusiness;
				for (Object obj : businessList) {
					newBusiness = (Business)obj;
					if(newBusiness.getDgbh().equals(workRequire.getGwbh())){
						b = false;
					}
				}
				if(b){
					addErrorMessage("第2个工作表，"+workRequire.getGwbh()+"岗位编号不存在");
				}
			}
			BusinessToMaintenancePersonnel businessToMaintenancePersonnel;
			boolean bGw;
			boolean bRy;
			//验证岗位对应人员表 的岗位编号是否存在数据库 和 新的岗位信息
			for (Object object : businessToMaintenancePersonnelList) {
				bGw = true;
				bRy = true;
				businessToMaintenancePersonnel = (BusinessToMaintenancePersonnel)object;
				
				if(businessToMaintenancePersonnel.getRydabh() == null || businessToMaintenancePersonnel.getRydabh() == null){
					continue;
				}
				//遍历编号是否存在数据库
				for (Business OldBusiness : businessOldList) {
					if(businessToMaintenancePersonnel.getGwbh().equals(OldBusiness.getDgbh())){
						bGw = false;
					}
				}
				//遍历编号是否存在新的岗位信息
				Business newBusiness;
				for (Object obj : businessList) {
					newBusiness = (Business)obj;
					if(businessToMaintenancePersonnel.getGwbh().equals(newBusiness.getDgbh())){
						bGw = false;
					}
				}
				//遍历人员编号是否存在
				for (MaintenancePersonnel maintenancePersonnel : maintenancePersonnelList) {
					if(businessToMaintenancePersonnel.getRydabh().equals(maintenancePersonnel.getRybh()) ){
						bRy = false;
					}
				}
				
				if(bGw){
					addErrorMessage("第3个工作表，"+businessToMaintenancePersonnel.getGwbh()+"岗位编号不存在");
				}
				if(bRy){
					addErrorMessage("第3个工作表，"+businessToMaintenancePersonnel.getRydabh()+"人员编号不存在");
				}
			}
			
			
	}

	@Override
	public int writeToDB(Map<Integer, List<Object>> datas)
			throws ExcelImportException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		List<Object> businessList = new ArrayList<Object>();
		List<Object> workRequireList = new ArrayList<Object>();
		List<Object> businessToMaintenancePersonnelList = new ArrayList<Object>();
		// 工作表对应的岗位信息数据
		businessList = datas.get(0);
		// 工作表对应的岗位要求
		workRequireList = datas.get(1);
		// 工作表对应的岗位人员
		businessToMaintenancePersonnelList = datas.get(2);
		//操作指令
		LogOperationEnum cz = LogOperationEnum.IMPORT;
		//操作流水id
		String czls = UUID.randomUUID().toString();
		boolean b;
		//创建一个list 用于存储岗位编号和岗位id
		List<Business> newBusinessList = new ArrayList<Business>();
		//获取数据库岗位信息
		Business bus = new Business();
		bus.setDprtcode(user.getJgdm());
		List<Business> oldBusinessList = businessMapper.queryAllBusiness(bus);
		//添加岗位表
		if(businessList !=null && businessList.size() > 0){
			//获取数据库岗位人员关系表数据
			List<BusinessToMaintenancePersonnel> oldBusinessToMaintenancePersonnelList = new ArrayList<BusinessToMaintenancePersonnel>();
			Business business;
			
			for (Object object : businessList) {
				b = false;
				business = (Business) object;	
				for (Business oldBusiness : oldBusinessList) {
					if(oldBusiness.getDgbh().equals(business.getDgbh())){
						b = true;
						business.setId(oldBusiness.getId());
					}
				}
				if(b){
					businessMapper.updateById(business);
					commonRecService.write(business.getId(), TableEnum.B_P_001, user.getId(), czls, cz, UpdateTypeEnum.UPDATE,business.getId());
				}else{
					business.setId(UUID.randomUUID().toString());
					businessMapper.insertSelective(business);
					commonRecService.write(business.getId(), TableEnum.B_P_001, user.getId(), czls, cz, UpdateTypeEnum.SAVE,business.getId());
					newBusinessList.add(business);
				}
			}
				
		}
		if(oldBusinessList != null && oldBusinessList.size() >0){
			newBusinessList.addAll(oldBusinessList);
		}
		//添加岗位要求表
		if(workRequireList !=null && workRequireList.size() > 0){
			WorkRequire workRequire;
			for (Object object : workRequireList) {
				workRequire = (WorkRequire)object;
				workRequire.setId(UUID.randomUUID().toString());
				for (Business business : newBusinessList) {
					if(business.getDgbh().equals(workRequire.getGwbh())){
						workRequire.setMainid(business.getId());
					}
				}
				if(workRequire.getMainid() != null ){
					workRequireMapper.insertSelective(workRequire);
					commonRecService.write(workRequire.getId(), TableEnum.B_P_00101, user.getId(), czls, cz, UpdateTypeEnum.SAVE,workRequire.getId());
				}
			}
		}
		
		//添加岗位-人员关系表
		if(businessToMaintenancePersonnelList !=null && businessToMaintenancePersonnelList.size() > 0){
			//获取所有岗位 - 人员关系表
			List<BusinessToMaintenancePersonnel> oldBusinessToMaintenancePersonnelList =businessToMaintenancePersonnelMapper.queryAllPageList(new BusinessToMaintenancePersonnel());
			//获取数据库人员档案编号
			List<MaintenancePersonnel> maintenancePersonnelList = maintenancePersonnelMapper.queryAllJgdm(user.getJgdm());
			BusinessToMaintenancePersonnel businessToMaintenancePersonnel;
			for (Object object : businessToMaintenancePersonnelList) {
				b = true;
				businessToMaintenancePersonnel =(BusinessToMaintenancePersonnel)object;
				for (MaintenancePersonnel maintenancePersonnel : maintenancePersonnelList) {
					if(businessToMaintenancePersonnel.getRydabh().equals(maintenancePersonnel.getRybh()) ){
						businessToMaintenancePersonnel.setWxrydaid(maintenancePersonnel.getId());
					}
				}
				
				for (Business business : newBusinessList) {
					if(business.getDgbh().equals(businessToMaintenancePersonnel.getGwbh())){
						businessToMaintenancePersonnel.setGwid(business.getId());
					}
				}
				//验证维修人员档案id 盒岗位id 是否已存在
				for (BusinessToMaintenancePersonnel oldBtm : oldBusinessToMaintenancePersonnelList) {
					StringBuffer sb1 = new StringBuffer();
					StringBuffer sb2 = new StringBuffer();
					sb1.append(oldBtm.getWxrydaid()).append(oldBtm.getGwid());
					sb1.append(oldBtm.getFjjx() == null?"-":oldBtm.getFjjx());
					sb2.append(businessToMaintenancePersonnel.getWxrydaid()).append(businessToMaintenancePersonnel.getGwid());
					sb2.append(businessToMaintenancePersonnel.getFjjx() == null?"-":businessToMaintenancePersonnel.getFjjx());
					if(sb1.toString().equals(sb2.toString())){
						b = false;
					}
				}
				if(b){
					businessToMaintenancePersonnel.setId(UUID.randomUUID().toString());
					businessToMaintenancePersonnelMapper.insertSelective(businessToMaintenancePersonnel);
					commonRecService.write(businessToMaintenancePersonnel.getId(), TableEnum.B_P_006, user.getId(), czls, cz, UpdateTypeEnum.SAVE,businessToMaintenancePersonnel.getId());
				}
				
			}
		}
		
		return 0;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<Object>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 结果集
		Map<Integer, List<Object>> resultMap = new HashMap<Integer, List<Object>>();
			// sheet对应装机清单数据
		List<Map<Integer, String>> sheetlist1 = totalMapList.get(0);
		List<Map<Integer, String>> sheetlist2 = totalMapList.get(1);
		List<Map<Integer, String>> sheetlist3 = totalMapList.get(2);
		
		List<Object> obj1 = new ArrayList<Object>();
		List<Object> obj2 = new ArrayList<Object>();
		List<Object> obj3 = new ArrayList<Object>();
		Business business;	//b_001 岗位实体
		WorkRequire workRequire;	//b_00101 岗位要求表
		BusinessToMaintenancePersonnel businessToMaintenancePersonnel;		//b_005 岗位 人员关系表
		Map<Integer, String> bean;
		for (int i = 0; i < sheetlist1.size(); i++) {
			bean = sheetlist1.get(i);
			business = new Business();	//b_001 岗位实体
			String id = UUID.randomUUID().toString();
			
			business.setId(id);// id
			business.setDgbh(bean.get(0));// 岗位编号				
			business.setDgmc(bean.get(1));// 岗位名称
			business.setBz(bean.get(2));// 备注
			business.setWhrid(user.getId());
			business.setWhbmid(user.getBmdm());
			business.setWhsj(new Date());
			business.setZt(1);
			business.setDprtcode(user.getJgdm());
			obj1.add(business);
		}
		resultMap.put(0,obj1);
		for (int i = 0; i < sheetlist2.size(); i++) {
			bean = sheetlist2.get(i);
			workRequire = new WorkRequire();//b_00101 岗位要求表
			String id = UUID.randomUUID().toString();
			
			workRequire.setId(id);// id
			workRequire.setGwbh(bean.get(0));// 岗位编号				
			workRequire.setGwyq(bean.get(1));// 岗位要求
			workRequire.setWhrid(user.getId());
			workRequire.setWhbmid(user.getBmdm());
			workRequire.setWhsj(new Date());
			workRequire.setZt(1);
			obj2.add(workRequire);
		}
		resultMap.put(1,obj2);
		for (int i = 0; i < sheetlist3.size(); i++) {
			bean = sheetlist3.get(i);
			businessToMaintenancePersonnel = new BusinessToMaintenancePersonnel();// b_005 岗位 人员关系表
			String id = UUID.randomUUID().toString();
			
			businessToMaintenancePersonnel.setId(id);// id
			businessToMaintenancePersonnel.setGwbh(bean.get(0));// 岗位编号				
			businessToMaintenancePersonnel.setRydabh(bean.get(1));// 维修人员档案编号
			businessToMaintenancePersonnel.setFjjx(bean.get(2));
			businessToMaintenancePersonnel.setLb(bean.get(3));
			businessToMaintenancePersonnel.setKsrq(convertToDate(bean.get(4)));//开始日期
			businessToMaintenancePersonnel.setJzrq(convertToDate(bean.get(5)));//截止日期
			businessToMaintenancePersonnel.setWhrid(user.getId());
			businessToMaintenancePersonnel.setWhbmid(user.getBmdm());
			businessToMaintenancePersonnel.setWhsj(new Date());
			businessToMaintenancePersonnel.setZt(1);
			obj3.add(businessToMaintenancePersonnel);
		}
		resultMap.put(2,obj3);
		
		return resultMap;
	}
	
	/**
	 * 
	 * 获取该组织结构下所有机型
	 * @param dprtcode
	 * @return
	 */
	private List<String> getJxByDprtcode(String dprtcode){
		List<String> resultList=new ArrayList<String>();
		List<ActType> planeDate=actTypeMapper.findByDprtcode(dprtcode);
		for (ActType planeModelData : planeDate) {
			resultList.add(planeModelData.getFjjx());
		}
		return resultList;
	}
}
