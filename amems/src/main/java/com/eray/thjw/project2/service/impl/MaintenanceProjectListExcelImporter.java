package com.eray.thjw.project2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.CoverPlateInformationMapper;
import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.produce.dao.AircraftinfoMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.project2.dao.CoverPlateMapper;
import com.eray.thjw.project2.dao.MaintenanceClassMapper;
import com.eray.thjw.project2.dao.MaintenanceProjectMapper;
import com.eray.thjw.project2.dao.MaintenanceSchemeMapper;
import com.eray.thjw.project2.dao.ProjectModelMapper;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaintenanceClass;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.po.MaintenanceProjectList;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.ProjectModel;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 维修项目导入
 * @CreateTime 2017-12-13 下午1:59:27
 * @CreateBy 雷伟	
 */
@Service("maintenanceprojectlistexcelimporter")
public class MaintenanceProjectListExcelImporter extends AbstractExcelImporter<MaintenanceProjectList>{

	protected final static String Z = "Z";//整数
	protected final static String ZZ = "ZZ";//正整数


	private Date implDate = new Date();
	private String czls = "";
	private String wxfaId = ""; //维修方案ID
	private MaintenanceScheme wxfa; //维修方案

	List<String> fixChapterList = new ArrayList<String>(); //章节号
	Map<String,Integer> rwhMap = new HashMap<String, Integer>(); //任务号在文本中唯一
	Map<String, MaintenanceClass> wxdlMap = new HashMap<String, MaintenanceClass>();//维修大类 key=维修大类编号
	Map<String, Aircraftinfo> fjzchMap = new HashMap<String, Aircraftinfo>();//维修大类 key=适用性,飞机注册号

	@Resource
	private FixChapterService fixChapterService;
	@Resource
	private MaintenanceSchemeMapper maintenanceSchemeMapper;
	@Resource
	private MaintenanceClassMapper maintenanceClassMapper;
	@Resource
	private CoverPlateInformationMapper coverPlateInformationMapper;
	@Resource
	private AircraftinfoMapper aircraftinfoMapper;
	@Resource
	private MaintenanceProjectMapper maintenanceProjectMapper;
	@Resource
	private CoverPlateMapper coverPlateMapper;
	@Resource
	private ProjectModelMapper projectModelMapper;

	@Resource
	private CommonService commonService;
	@Resource
	private CommonRecService commonRecService;

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<MaintenanceProjectList>> sheetList) throws ExcelImportException {
		List<MaintenanceProjectList> wxxmList = new ArrayList<MaintenanceProjectList>();//维修项目信息
		
		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			if (sheetIndex == 0) {
				wxxmList = sheetList.get(sheetIndex);
			}
		}

		if(sheetList == null || sheetList.size() <= 0){
			throw new ExcelImportException("excel内容不能 为空！");
		}

		if(StringUtils.isBlank(wxfaId)){
			throw new ExcelImportException("选择的维修方案不存在！");
		}

		wxfa = maintenanceSchemeMapper.selectByPrimaryKey(wxfaId);
		if(wxfa != null && !StringUtils.isBlank(wxfa.getId())){
			if((wxfa.getZt()==null?0:wxfa.getZt()) != 1){
				throw new ExcelImportException("维修方案状态不等于保存状态，不能进行导入操作！");
			}
		}else{
			throw new ExcelImportException("选择的维修方案不存在！");
		}

		// 获取所有章节号
		fixChapterList = getAllFixChapter();
		// 获取所有维修项目大类
		wxdlMap = getWxdlMap();
		// 获取所有飞机注册号
		fjzchMap= getAllFjzch();

		for (int i = 0; null != wxxmList && i < wxxmList.size(); i++) {
			MaintenanceProjectList bean = wxxmList.get(i);

			//任务号
			if(StringUtils.isBlank(bean.getRwh())){
				addErrorMessage("第1个工作表，第"+(i+3)+"行，任务号不能为空");
			}else{
				if(Utils.Str.isChinese(bean.getRwh())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，任务号不能含有中文");
				}
				if(Utils.Str.getLength(bean.getRwh()) > 50){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，任务号不能超过50个字符");
				}
				if(null != rwhMap && null != rwhMap.get(bean.getRwh()) && rwhMap.get(bean.getRwh()) > 1){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，任务号在文本重复");
				}
			}

			//任务描述
			if(StringUtils.isBlank(bean.getRwms())){
				addErrorMessage("第1个工作表，第"+(i+3)+"行，任务描述不能为空");
			}else{
				if(Utils.Str.getLength(bean.getRwms()) > 4000){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，任务描述不能超过4000个字符");
				}
			}

			//维修项目大类编号
			if(StringUtils.isBlank(bean.getWxxmdlbh())){
				addErrorMessage("第1个工作表，第"+(i+3)+"行，维修项目大类编号不能为空");
			}else{
				if(Utils.Str.isChinese(bean.getWxxmdlbh())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，维修项目大类编号不能含有中文");
				}
				if(Utils.Str.getLength(bean.getWxxmdlbh()) > 50){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，维修项目大类编号不能超过50个字符");
				}
				if(null != wxdlMap && wxdlMap.get(bean.getWxxmdlbh()) == null){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，维修项目大类编号不存在");
				}else{
					bean.setWxxmdlid(wxdlMap.get(bean.getWxxmdlbh()).getId());
				}
			}

			//分类
			if(StringUtils.isBlank(bean.getWxxmlx())){
				addErrorMessage("第1个工作表，第"+(i+3)+"行，分类不能为空");
			}else{
				String[] fl = {"1","2","3"};
				if(!Arrays.asList(fl).contains(bean.getWxxmlx())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，分类范围必须为：1（一般项目）、2（时控项目）、3（时寿项目）");
				}
			}

			//ATA章节号
			if(StringUtils.isBlank(bean.getZjh())){
				addErrorMessage("第1个工作表，第"+(i+3)+"行，ATA章节号不能为空");
			}else{
				if(Utils.Str.isChinese(bean.getZjh())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，ATA章节号不能含有中文");
				}
				if(Utils.Str.getLength(bean.getZjh()) > 20){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，ATA章节号不能超过20个字符");
				}
				if(null != fixChapterList && !fixChapterList.contains(bean.getZjh())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，ATA章节号不存在");
				}
			}

			//CMP/CAMP号
			if(!StringUtils.isBlank(bean.getCmph())){
				if(Utils.Str.isChinese(bean.getCmph())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，CMP/CAMP号不能含有中文");
				}
				if(Utils.Str.getLength(bean.getCmph()) > 50){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，CMP/CAMP号不能超过50个字符");
				}
			}

			//参考号
			if(!StringUtils.isBlank(bean.getCkh())){
				if(Utils.Str.getLength(bean.getCkh()) > 300){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，参考号不能超过300个字符");
				}
			}

			//参考文件
			if(!StringUtils.isBlank(bean.getCkwj())){
				if(Utils.Str.getLength(bean.getCkwj()) > 300){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，参考文件不能超过300个字符");
				}
			}

			//工作类别
			if(!StringUtils.isBlank(bean.getGzlx())){
				if(Utils.Str.getLength(bean.getGzlx()) > 15){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，工作类别不能超过15个字符");
				}
			}

			//项目类型
			if(!StringUtils.isBlank(bean.getXmlx())){
				if(Utils.Str.getLength(bean.getXmlx()) > 15){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，项目类型不能超过15个字符");
				}
			}

			//RII
			if (StringUtils.isBlank(bean.getIsbj())) {
				bean.setIsbj("0");
			} else {
				String[] fl = {"1","0"};
				if(!Arrays.asList(fl).contains(bean.getIsbj())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，RII范围必须为：1(是)、0(否)");
				}
			}

			//ALI
			if (StringUtils.isBlank(bean.getAli())) {
				bean.setAli("0");
			} else {
				String[] fl = {"1","0"};
				if(!Arrays.asList(fl).contains(bean.getAli())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，ALI范围必须为：1(是)、0(否)");
				}
			}

			//适用性
			if(!StringUtils.isBlank(bean.getSyx())){
				if(!bean.getSyx().toLowerCase().trim().equals("all")){
					String[] syxArr = bean.getSyx().replace("，", ",").split(",");
					for (int j = 0 ; syxArr != null && j < syxArr.length; j++) {
						if(null != fjzchMap && fjzchMap.get(syxArr[j]) == null){
							addErrorMessage("第1个工作表，第"+(i+3)+"行，适用性列,飞机注册号"+syxArr[j]+"不存在");
							break;
						}
					}
				}
			}

			//接近
			boolean accessFlag = true;
			if (!StringUtils.isBlank(bean.getAccess())) {
				if(Utils.Str.isChinese(bean.getAccess())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，接近不能为中文");
					accessFlag = false;
				}
				if (Utils.Str.getLength(bean.getAccess()) > 1300) {
					addErrorMessage("第1个工作表，第"+(i+3)+"行，接近不能超过1300个字符");
					accessFlag = false;
				}
			}
			if(accessFlag && !StringUtils.isBlank(bean.getAccess())){
				List<String> wcAccessList = new ArrayList<String>();
				String[] accessArr = bean.getAccess().replace("，", ",").split(",");
				for (int j = 0 ; accessArr != null && j < accessArr.length; j++) {
					CoverPlateInformation obj = coverPlateInformationMapper.getCoverPlateByParam(wxfa.getDprtcode(),wxfa.getJx(),accessArr[j]);
					if(null != obj && !StringUtils.isBlank(obj.getId())){
						wcAccessList.add(obj.getId()+"◎"+accessArr[j]);
					}else{
						addErrorMessage("第1个工作表，第"+(i+3)+"行，接近"+accessArr[j]+"不存在");
						break;
					}
				}
				bean.setAccessList(wcAccessList);
			}

			//区域
			if (!StringUtils.isBlank(bean.getZone())) {
				if(Utils.Str.isChinese(bean.getZone())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，区域不能为中文");
				}
				if (Utils.Str.getLength(bean.getZone()) > 1300) {
					addErrorMessage("第1个工作表，第"+(i+3)+"行，区域不能超过1300个字符");
				}
			}

			//飞机站位
			if (!StringUtils.isBlank(bean.getFjzw())) {
				if(Utils.Str.isChinese(bean.getFjzw())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，飞机站位不能为中文");
				}
				if (Utils.Str.getLength(bean.getFjzw()) > 1300) {
					addErrorMessage("第1个工作表，第"+(i+3)+"行，飞机站位不能超过1300个字符");

				}
			}

			//计划人数
			if (StringUtils.isBlank(bean.getJhgsrs())) {
				bean.setJhgsrs("1");
			} else {
				if(!validateNumRange(bean.getJhgsrs(),this.Z)){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，计划人数只能为[0~9999999]范围内正整数");
				}
			}

			//每人耗时（小时数）
			if (StringUtils.isBlank(bean.getJhgsxss())) {
				bean.setJhgsrs("1");
			} else {
				if(!Utils.Str.isDecimalzs(bean.getJhgsxss(), 2)){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，每人耗时（小时数）只能为[0~9999999.99]范围内的两位小数");
				}
			}

			//计算公式
			if (StringUtils.isBlank(bean.getJsgs())) {
				bean.setJsgs("3");
			} else {
				String[] fl = {"1","2","3"};
				if(!Arrays.asList(fl).contains(bean.getJsgs())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，计算公式范围必须为：1（计划与实际取小再加上周期）、2（实际加上周期）、3（计划加上周期）");
				}
			}

			//后到为准
			if (StringUtils.isBlank(bean.getIshdwz())) {
				bean.setIshdwz("0");
			} else {
				String[] fl = {"1","0"};
				if(!Arrays.asList(fl).contains(bean.getIshdwz())){
					addErrorMessage("第1个工作表，第"+(i+3)+"行，后到为准范围必须为：1(是)、0(否)");
				}
			}

			//备注
			if (!StringUtils.isBlank(bean.getBz())) {
				if (Utils.Str.getLength(bean.getBz()) > 300) {
					addErrorMessage("第1个工作表，第"+(i+3)+"行，备注不能超过300个字符");

				}
			}
		}
	}

	/**
	 * 保存到数据库
	 * @throws ExcelImportException 
	 */
	@Override
	public int writeToDB(Map<Integer, List<MaintenanceProjectList>> sheetList) throws ExcelImportException {
		implDate = commonService.getSysdate();
		czls = UUID.randomUUID().toString();

		List<MaintenanceProjectList> wxxmList = new ArrayList<MaintenanceProjectList>();//维修项目信息

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			if (sheetIndex == 0) {
				wxxmList = sheetList.get(sheetIndex);
			}
		}

		List<MaintenanceProject> addWxxms = new ArrayList<MaintenanceProject>(); //需要新增的维修项目
		List<String> addWxxmIds = new ArrayList<String>();

		List<MaintenanceProject> updateWxxms = new ArrayList<MaintenanceProject>(); //需要修改的维修项目
		List<String> updateWxxmIds = new ArrayList<String>();

		List<MaintenanceProject> preBbWxxms = new ArrayList<MaintenanceProject>(); //有前版本记录的维修项目
		List<String> preBbWxxmIds = new ArrayList<String>();

		List<ProjectModel> addModels = new ArrayList<ProjectModel>();//维修项目对应飞机关系表
		List<String> addModelIds = new ArrayList<String>();
		List<String> delModelIds = new ArrayList<String>();


		List<CoverPlate> addCoverPlates = new ArrayList<CoverPlate>();//区域\飞机站位\接近(盖板)
		List<String> addCoverPlateIds = new ArrayList<String>();

		for (int i = 0; null != wxxmList && i < wxxmList.size(); i++) {
			MaintenanceProjectList bean = wxxmList.get(i);

			/*维修项目*/
			MaintenanceProject wxxm = coverToWxxm(bean);

			MaintenanceProject obj = maintenanceProjectMapper.getWxxmByParam(wxfa.getDprtcode(),wxfa.getWxfabh(),bean.getRwh(),wxfa.getBb()+"");
			//获取前版本工卡
			MaintenanceProject preWxxm = maintenanceProjectMapper.getPreWxxmByParam(wxfa.getDprtcode(),wxfa.getWxfabh(),bean.getRwh());
			
			if(obj != null && !StringUtils.isBlank(obj.getId())){
				wxxm.setId(obj.getId());
				if(null != preWxxm && !StringUtils.isBlank(preWxxm.getId())){ //由于改版维修项目不可编辑：因此更新时，如果有前版本，那么不更新维修项目类型
					wxxm.setWxxmlx(null);
				}
				updateWxxms.add(wxxm);
				updateWxxmIds.add(wxxm.getId());
			}else{
				wxxm.setId(UUID.randomUUID().toString());
				if(null != preWxxm && !StringUtils.isBlank(preWxxm.getId())){
					wxxm.setfBbid(preWxxm.getId()); //当前版本的前版本ID
					preWxxm.setbBbid(wxxm.getId());//前版本的后版本ID
					preBbWxxms.add(preWxxm);
					preBbWxxmIds.add(preWxxm.getId());
				}
				addWxxms.add(wxxm);
				addWxxmIds.add(wxxm.getId());
			}

			/*b_g2_01203 维修项目对应飞机关系表*/
			delModelIds.add(wxxm.getId());
			if(!StringUtils.isBlank(bean.getSyx()) && !bean.getSyx().toLowerCase().equals("all")){
				String[] syxArr = bean.getSyx().replace("，", ",").split(",");
				for (int j = 0 ; syxArr != null && j < syxArr.length; j++) {
					ProjectModel model = new ProjectModel();
					model.setId(UUID.randomUUID().toString());
					model.setMainid(wxxm.getId());
					model.setXc(j+1);
					model.setFjzch(syxArr[j]);
					model.setWhdwid(ThreadVarUtil.getUser().getBmdm());
					model.setWhrid(ThreadVarUtil.getUser().getId());
					model.setWhsj(implDate);
					addModels.add(model);
					addModelIds.add(model.getId());
				}
			}

			/*b_g2_995区域/站位表/ 接近（盖板）*/
			if(!StringUtils.isBlank(bean.getZone())){
				String zoneBh = bean.getZone().replace("，", ",");
				CoverPlate coverPlate = getCoverPlate(wxxm.getId(),1,1,"",zoneBh);
				addCoverPlates.add(coverPlate);
				addCoverPlateIds.add(coverPlate.getId());
			}
			if(!StringUtils.isBlank(bean.getFjzw())){
				String fjzwBh = bean.getFjzw().replace("，", ",");
				CoverPlate coverPlate = getCoverPlate(wxxm.getId(),3,1,"",fjzwBh);
				addCoverPlates.add(coverPlate);
				addCoverPlateIds.add(coverPlate.getId());
			}
			if(!StringUtils.isBlank(bean.getAccess())){
				for (int j = 0 ; bean.getAccessList() != null && j < bean.getAccessList().size(); j++) {
					String[] accessArr = bean.getAccessList().get(j).split("◎");
					CoverPlate coverPlate = getCoverPlate(wxxm.getId(),2,j+1,accessArr[0],accessArr[1]);
					addCoverPlates.add(coverPlate);
					addCoverPlateIds.add(coverPlate.getId());
				}
			}
		}

		/*保存新增的维修项目*/
		if (null != addWxxms && addWxxms.size() > 0){
			maintenanceProjectMapper.insert4Batch(addWxxms);
			commonRecService.write("id",addWxxmIds, TableEnum.B_G2_012, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}

		/*更新需要修改的维修项目*/
		if (null != updateWxxms && updateWxxms.size() > 0){
			maintenanceProjectMapper.update4Batch(updateWxxms);
			commonRecService.write("id",updateWxxmIds, TableEnum.B_G2_012, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}

		/*更新前版本的后版本ID*/
		if (null != preBbWxxms && preBbWxxms.size() > 0){
			maintenanceProjectMapper.updatePreBb4Batch(preBbWxxms);
			commonRecService.write("id",preBbWxxmIds, TableEnum.B_G2_012, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}

		/*根据MainId，删除b_g2_01203 维修项目对应飞机关系表*/
		if(null != delModelIds && delModelIds.size() > 0){
			projectModelMapper.delete4BatchByMainids(delModelIds);
			commonRecService.write("id",delModelIds, TableEnum.B_G2_01203, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.DELETE, "");
		}

		/*新增b_g2_01203 维修项目对应飞机关系表*/
		if(null != addModels && addModels.size() > 0){
			projectModelMapper.insert4BatchImpl(addModels);
			commonRecService.write("id",addModelIds, TableEnum.B_G2_01203, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		
		/*新增区域\飞机站位\盖板:先删除后新增*/
		if (null != delModelIds && delModelIds.size() > 0){
			coverPlateMapper.delete4BatchImpl(delModelIds);
		}
		if (null != addCoverPlates && addCoverPlates.size() > 0){
			coverPlateMapper.insert4BatchImpl(addCoverPlates);
			commonRecService.write("id",addCoverPlateIds, TableEnum.B_G2_995, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}

		return 0;
	}

	private MaintenanceProject coverToWxxm(MaintenanceProjectList bean) {
		MaintenanceProject wxxm = new MaintenanceProject();

		wxxm.setDprtcode(wxfa.getDprtcode());//机构代码=来源于维修方案
		wxxm.setJx(wxfa.getJx());//机型=来源于维修方案
		wxxm.setWxfabh(wxfa.getWxfabh());//维修方案编号=来源于维修方案
		wxxm.setWxxmdlid(bean.getWxxmdlid());//维修项目大类=文档.维修项目大类编号对应的维修项目大类id
		if(!StringUtils.isBlank(bean.getWxxmlx())){
			wxxm.setWxxmlx(Integer.valueOf(bean.getWxxmlx()));//维修项目类型=文档.分类
		}else{
			wxxm.setWxxmlx(null);//维修项目类型=文档.分类
		}
		wxxm.setZjh(bean.getZjh());//ATA章节号=文档.ATA章节号
		wxxm.setRwh(bean.getRwh());//任务号=文档.任务号
		wxxm.setBb(wxfa.getBb());//版本号=来源于维修方案
		wxxm.setCkh(bean.getCkh());//参考号=文档.参考号
		wxxm.setCmph(bean.getCmph());//Cmp No=文档.CMP/CAMP号
		wxxm.setRwms(bean.getRwms());//任务描述=文档.任务描述
		if(!StringUtils.isBlank(bean.getSyx())){
			if(bean.getSyx().toLowerCase().trim().equals("all")){
				wxxm.setSyx("00000");//适用性=见特殊字段说明
			}else{
				wxxm.setSyx("-");//适用性=见特殊字段说明
			}
		}
		wxxm.setCkwj(bean.getCkwj());//参考文件=文档.参考文件
		wxxm.setGzlx(bean.getGzlx());//工作类型=文档.工作类别
		wxxm.setXmlx(bean.getXmlx());//项目类型=文档.项目类型
		if(!StringUtils.isBlank(bean.getIsbj())){
			wxxm.setIsBj(Integer.valueOf(bean.getIsbj()));//必检标识=文档.RII
		}else{
			wxxm.setIsBj(null);//必检标识=文档.RII
		}
		if(!StringUtils.isBlank(bean.getIsbj())){
			wxxm.setAli(Integer.valueOf(bean.getAli()));//ALI=文档.ALI
		}else{
			wxxm.setAli(null);//ALI=文档.ALI	
		}
		if(!StringUtils.isBlank(bean.getJhgsrs())){
			wxxm.setJhgsRs(new BigDecimal(bean.getJhgsrs()));//计划工时-人数=文档.计划人数
		}else{
			wxxm.setJhgsRs(null);//计划工时-人数=文档.计划人数
		}
		if(!StringUtils.isBlank(bean.getJhgsxss())){
			wxxm.setJhgsXss(new BigDecimal(bean.getJhgsxss()));//计划工时-小时数=文档.每人耗时（小时数）
		}else{
			wxxm.setJhgsXss(null);//计划工时-小时数=文档.每人耗时（小时数）
		}
		if(!StringUtils.isBlank(bean.getJsgs())){
			wxxm.setJsgs(Integer.valueOf(bean.getJsgs()));//计算公式=文档.计算公式
		}else{
			wxxm.setJsgs(null);//计算公式=文档.计算公式
		}
		if(!StringUtils.isBlank(bean.getIshdwz())){
			wxxm.setIsHdwz(Integer.valueOf(bean.getIshdwz()));//后到为准=文档.后到为准
		}else{
			wxxm.setIsHdwz(null);//后到为准=文档.后到为准
		}
		wxxm.setBz(bean.getBz());//备注=文档.备注
		wxxm.setZt(1);//状态=1
		wxxm.setYxbs(1);//有效标识=1
		wxxm.setZdbmid(ThreadVarUtil.getUser().getBmdm());
		wxxm.setZdrid(ThreadVarUtil.getUser().getId());
		wxxm.setZdsj(implDate);

		return wxxm;
	}

	/**
	 * 转换成对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<MaintenanceProjectList>> convertBean(Map<Integer, List<Map<Integer, String>>> sheetList) throws ExcelImportException {
		rwhMap.clear();
		wxfaId = this.getParam("wxfaId")==null?"":this.getParam("wxfaId").toString(); //维修方案ID

		//结果集(按插入的顺序保存)
		Map<Integer, List<MaintenanceProjectList>> resultMap = new LinkedHashMap<Integer, List<MaintenanceProjectList>>();

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			List<MaintenanceProjectList> wxxmLists = new ArrayList<MaintenanceProjectList>();
			// sheet对应飞行履历数据
			List<Map<Integer, String>> rowList = sheetList.get(sheetIndex);
			//循环获取每一行数据
			Map<Integer, String> row;
			MaintenanceProjectList bean;
			for (int rowIndex = 0; null != rowList && rowIndex < rowList.size(); rowIndex++) {
				row = rowList.get(rowIndex); //行数据
				bean = new MaintenanceProjectList();//实体bean
				bean = convertRow2Bean(row,bean);//将行数据转换成实体bean
				wxxmLists.add(bean);//添加到结果集中
			}
			resultMap.put(sheetIndex, wxxmLists);
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
	private MaintenanceProjectList convertRow2Bean(Map<Integer, String> row,MaintenanceProjectList bean) {
		Integer num = 1;

		bean.setRwh(row.get(0));//任务号
		bean.setRwms(row.get(1));//任务描述
		bean.setWxxmdlbh(row.get(2));//维修项目大类编号
		bean.setWxxmlx(row.get(3));//分类
		bean.setZjh(row.get(4));//ATA章节号
		bean.setCmph(row.get(5));//CMP/CAMP号
		bean.setCkh(row.get(6));//参考号
		bean.setCkwj(row.get(7));//参考文件
		bean.setGzlx(row.get(8));//工作类别
		bean.setXmlx(row.get(9));//项目类型
		bean.setIsbj(row.get(10));//RII
		bean.setAli(row.get(11));//ALI
		bean.setSyx(row.get(12));//适用性
		bean.setAccess(row.get(13));//接近
		bean.setZone(row.get(14));//区域
		bean.setFjzw(row.get(15));//飞机站位
		bean.setJhgsrs(row.get(16));//计划人数
		bean.setJhgsxss(row.get(17));//每人耗时（小时数）
		bean.setJsgs(row.get(18));//计算公式
		bean.setIshdwz(row.get(19));//后到为准
		bean.setBz(row.get(20));//备注

		//任务号在文本中唯一
		if(!StringUtils.isBlank(bean.getRwh())){
			if(rwhMap.get(bean.getRwh()) != null){
				num += rwhMap.get(bean.getRwh());
			}
			rwhMap.put(bean.getRwh(), num);
		}

		return bean;
	}

	/**
	 * 获取所有章节号
	 * @return
	 */
	private List<String> getAllFixChapter(){
		try {
			List<String> resultList = new ArrayList<String>();
			FixChapter param = new FixChapter();
			param.setDprtcode(wxfa.getDprtcode());
			List<FixChapter> fixChapters = fixChapterService.selectByDprtcode(param);
			if (null == fixChapters || fixChapters.size() <= 0){
				return null;
			}
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
	 * @Description 获取维修项目大类
	 * @CreateTime 2017-12-15 上午10:23:42
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String, MaintenanceClass> getWxdlMap() {
		Map<String ,MaintenanceClass> resultMap = new HashMap<String, MaintenanceClass>();
		List<MaintenanceClass> wxdlDatas = maintenanceClassMapper.getWxdlByParam(wxfa.getDprtcode(),wxfa.getJx());
		for (int i = 0;null != wxdlDatas && i < wxdlDatas.size(); i++) {
			MaintenanceClass wxdl = wxdlDatas.get(i);
			if(!StringUtils.isBlank(wxdl.getDlbh())){
				resultMap.put(wxdl.getDlbh(), wxdl);
			}
		}
		return resultMap;
	}

	/**
	 * @Description 获取所有飞机注册号
	 * @CreateTime 2017-12-15 上午10:24:03
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String, Aircraftinfo> getAllFjzch() {
		Map<String ,Aircraftinfo> resultMap = new HashMap<String, Aircraftinfo>();
		List<Aircraftinfo> fjzchDatas = aircraftinfoMapper.getAllFjzchByParam(wxfa.getDprtcode(),wxfa.getJx());
		for (int i = 0;null != fjzchDatas && i < fjzchDatas.size(); i++) {
			Aircraftinfo aircraftinfo = fjzchDatas.get(i);
			if(!StringUtils.isBlank(aircraftinfo.getFjzch())){
				resultMap.put(aircraftinfo.getFjzch(), aircraftinfo);
			}
		}
		return resultMap;
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
	 * @Description 获取b_g2_995 接近（盖板）/区域/站位表信息
	 * @CreateTime 2017-12-12 上午10:13:34
	 * @CreateBy 雷伟
	 * @param ywid  业务ID
	 * @param lx 类型
	 * @param xc 项次
	 * @param gbid 盖板ID
	 * @param gbbh 盖板编号
	 * @return
	 */
	private CoverPlate getCoverPlate(String ywid, int lx, int xc, String gbid,String gbbh) {
		CoverPlate coverPlate = new CoverPlate();
		coverPlate.setId(UUID.randomUUID().toString());
		coverPlate.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		coverPlate.setYwlx(3);
		coverPlate.setYwid(ywid);
		coverPlate.setLx(lx);
		coverPlate.setXc(xc);
		coverPlate.setGbid(gbid);
		coverPlate.setGbh(gbbh);
		coverPlate.setWhdwid(ThreadVarUtil.getUser().getBmdm());
		coverPlate.setWhrid(ThreadVarUtil.getUser().getId());
		coverPlate.setWhsj(implDate);
		return coverPlate;
	}
}
