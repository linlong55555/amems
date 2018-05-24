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

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.basic.dao.CoverPlateInformationMapper;
import com.eray.thjw.basic.dao.StageMapper;
import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.basic.po.Stage;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.dao.ApplicableUnitMapper;
import com.eray.thjw.project2.dao.CoverPlateMapper;
import com.eray.thjw.project2.dao.MaterialToolMapper;
import com.eray.thjw.project2.dao.WorkCardMapper;
import com.eray.thjw.project2.dao.WorkHourMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.project2.po.ApplicableUnit;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.po.WorkCardImplList;
import com.eray.thjw.project2.po.WorkHour;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.system.dao.WorkGroupMapper;
import com.eray.thjw.system.po.WorkGroup;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 工卡信息导入
 * @CreateTime 2017-12-8 上午10:05:11
 * @CreateBy 雷伟	
 */
@Service("workCardExcelImporter")
public class WorkCardExcelImporter extends AbstractExcelImporter<WorkCardImplList>{

	Map<String, Integer> wcUniqueMap = new LinkedHashMap<String, Integer>(); //工卡唯一性：工卡编号、版本、机型
	Map<String, Integer> qcUniqueMap = new LinkedHashMap<String, Integer>(); //器材唯一性：工卡编号、版本、机型、件号
	Map<String, Integer> gjUniqueMap = new LinkedHashMap<String, Integer>(); //工具唯一性：工卡编号、版本、机型、件号
	Map<String, Integer> gsUniqueMap = new LinkedHashMap<String, Integer>(); //工时唯一性：工卡编号、版本、机型、阶段编号、工作组编号
	List<String> fixChapterList = new ArrayList<String>(); //章节号
	List<String> actTypeList = new ArrayList<String>(); //飞机机型
	Map<String, WorkGroup> workGroupMap = new HashMap<String, WorkGroup>(); //工作组
	Map<String, Stage> stageMap = new HashMap<String, Stage>();//阶段
	Map<String, HCMainData> hcMap = new HashMap<String, HCMainData>(); //航材数据
	Map<String, String> onlyInExcelCardMap = new HashMap<String, String>(); //新增的工卡，在excel中有数据库中没有

	@Resource
	private FixChapterService fixChapterService;
	@Resource
	private CommonService commonService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private ActTypeMapper actTypeMapper;
	@Resource
	private WorkGroupMapper workGroupMapper;
	@Resource
	private WorkCardMapper workCardMapper;
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	@Resource
	private StageMapper stageMapper;
	@Resource
	private CoverPlateInformationMapper coverPlateInformationMapper;
	@Resource
	private CoverPlateMapper coverPlateMapper;
	@Resource
	private ApplicableUnitMapper applicableUnitMapper;
	@Resource
	private MaterialToolMapper materialToolMapper;
	@Resource
	private WorkHourMapper workHourMapper;

	protected final static String Z = "Z";//整数
	protected final static String ZZ = "ZZ";//正整数
	protected final static String ZZWQ = "ZZWQ";//正整数,无穷大
	protected final static String XQSL = "XQSL";//需求数量
	private Date implDate = new Date();
	private String czls = "";

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<WorkCardImplList>> sheetList) throws ExcelImportException {
		onlyInExcelCardMap.clear();
		
		List<WorkCardImplList> workCardList = new ArrayList<WorkCardImplList>();//工卡信息
		List<WorkCardImplList> qcList = new ArrayList<WorkCardImplList>();//器材清单
		List<WorkCardImplList> gjList = new ArrayList<WorkCardImplList>();//工具清单
		List<WorkCardImplList> gzList = new ArrayList<WorkCardImplList>();//工种工时

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			if (sheetIndex == 0) {
				workCardList = sheetList.get(sheetIndex);
			}else if (sheetIndex == 1) {
				qcList = sheetList.get(sheetIndex);
			}else if (sheetIndex == 2) {
				gjList = sheetList.get(sheetIndex);
			}else if (sheetIndex == 3) {
				gzList = sheetList.get(sheetIndex);
			}
		}

		// 获取所有章节号
		fixChapterList = getAllFixChapter();
		//飞机机型
		actTypeList = getAllActType();
		//工作组
		workGroupMap = getWorkGroup();
		//航材数据
		hcMap =  getAllHCMainData();
		//阶段
		stageMap = getStages();

		//检验工卡信息
		if (null != workCardList && workCardList.size() > 0) {
			validateWorkcardParam(workCardList);
		}

		//检验器材清单
		if (null != qcList && qcList.size() > 0) {
			validateQcParam(qcList);
		}

		//检验工具清单
		if (null != gjList && gjList.size() > 0) {
			validateGjParam(gjList);
		}

		//检验工种工时清单
		if (null != gzList && gzList.size() > 0) {
			validateGzgsParam(gzList);
		}
		
		//重置数据
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			if (sheetIndex == 0) {
				sheetList.put(sheetIndex, workCardList);
			}else if (sheetIndex == 1) {
				sheetList.put(sheetIndex, qcList);
			}else if (sheetIndex == 2) {
				sheetList.put(sheetIndex, gjList);
			}else if (sheetIndex == 3) {
				sheetList.put(sheetIndex, gzList);
			}
		}
	}

	/**
	 * @Description 校验工种工时清单信息
	 * @CreateTime 2017-12-11 上午11:57:05
	 * @CreateBy 雷伟
	 * @param gzList
	 */
	private void validateGzgsParam(List<WorkCardImplList> gzList) {
		for (int i = 0; null != gzList && i < gzList.size(); i++) {
			WorkCardImplList bean = gzList.get(i);

			boolean gzzbh = true; //工作组编号是否输入正确
			boolean stageFlag = true; //阶段
			boolean bbGkbhJx = true;//版本+工卡编号+机型是否都输入正确
			
			//工卡编号
			if(StringUtils.isBlank(bean.getGsGkh())){
				addErrorMessage("工种工时表，第"+(i+3)+"行，工卡编号不能为空");
				bbGkbhJx = false;
			}else{
				if(Utils.Str.isChinese(bean.getGsGkh())){
					addErrorMessage("工种工时表，第"+(i+3)+"行，工卡编号不能含有中文");
					bbGkbhJx = false;
				}
				if(Utils.Str.getLength(bean.getGsGkh()) > 50){
					addErrorMessage("工种工时表，第"+(i+3)+"行，工卡编号不能超过50个字符");
					bbGkbhJx = false;
				}
			}

			//版本
			if(StringUtils.isBlank(bean.getGsBb())){
				addErrorMessage("工种工时表，第"+(i+3)+"行，版本不能为空");
				bbGkbhJx = false;
			}else{
				if(!Utils.Str.isDecimalzs(bean.getGsBb(), 2)){
					addErrorMessage("工种工时表，第"+(i+3)+"行，版本只能为[0~9999999.99]范围内的两位小数");
					bbGkbhJx = false;
				}
			}

			//机型
			if(StringUtils.isBlank(bean.getGsJx())){
				addErrorMessage("工种工时表，第"+(i+3)+"行，机型不能为空");
				bbGkbhJx = false;
			}else{
				if (Utils.Str.isChinese(bean.getGsJx())) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，机型不能含有中文");
					bbGkbhJx = false;
				}
				if (Utils.Str.getLength(bean.getGsJx()) > 50) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，机型不能超过50个字符");
					bbGkbhJx = false;
				}
				if (!actTypeList.contains(bean.getGsJx())) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，机型不存在");
					bbGkbhJx = false;
				}
			}

			//阶段编号
			if (StringUtils.isBlank(bean.getGsJdbm())) {
				addErrorMessage("工种工时表，第"+(i+3)+"行，阶段编号不能为空");
				stageFlag = false;
			}else{
				if (Utils.Str.getLength(bean.getGsJdbm()) > 50) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，阶段编号不能超过50个字符");
					stageFlag = false;
				}
				if (Utils.Str.isChinese(bean.getGsJdbm())) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，阶段编号不能含有中文");
					stageFlag = false;
				}
			}

			//工作组编号
			if(StringUtils.isBlank(bean.getGsGzzbh())){
				addErrorMessage("工种工时表，第"+(i+3)+"行，工作组编号不能为空");
				gzzbh = false;
			}else{
				if (Utils.Str.isChinese(bean.getGsGzzbh())) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，工作组编号不能含有中文");
					gzzbh = false;
				}
				if (Utils.Str.getLength(bean.getGsGzzbh()) > 50) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，工作组编号不能超过50个字符");
					gzzbh = false;
				}
			}
			
			//工时
			if (StringUtils.isBlank(bean.getGsJhgsxss())) {
				addErrorMessage("工种工时表，第"+(i+3)+"行，工时不能为空");
			}else{
				if(!Utils.Str.isDecimalzs(bean.getGsJhgsxss(), 2)){
					addErrorMessage("工种工时表，第"+(i+3)+"行，工时只能为[0~9999999.99]范围内的两位小数");
				}
			}

			//任务
			if (!StringUtils.isBlank(bean.getGsRw())) {
				if (Utils.Str.getLength(bean.getGsRw()) > 300) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，任务不能超过300个字符");
				}
			}

			//备注
			if (!StringUtils.isBlank(bean.getGsBz())) {
				if (Utils.Str.getLength(bean.getGsBz()) > 300) {
					addErrorMessage("工种工时表，第"+(i+3)+"行，备注不能超过300个字符");
				}
			}
			
			//工卡编号、版本、机型、阶段编号、工作组编号在文本中唯一
			if (!StringUtils.isBlank(bean.getGsGkh()) && !StringUtils.isBlank(bean.getGsBb())
					&& !StringUtils.isBlank(bean.getGsJx()) && !StringUtils.isBlank(bean.getGsJdbm())
					&& !StringUtils.isBlank(bean.getGsGzzbh())) {
				String key = bean.getGsGkh()+"◎"+bean.getGsBb()+"◎"+bean.getGsJx()+"◎"+bean.getGsJdbm()+"◎"+bean.getGsGzzbh();
				if(gsUniqueMap.get(key) != null && gsUniqueMap.get(key).intValue() > 1){
					addErrorMessage("工种工时表，第"+(i+3)+"行，工卡编号+版本+机型+阶段编号+工作组编号在文本中重复");
				}
			}
			
			//验证工作组编号是否存在
			if (!StringUtils.isBlank(bean.getGsGzzbh()) && gzzbh) {
				if(workGroupMap.get(bean.getGsGzzbh()) == null){
					addErrorMessage("工种工时表，第"+(i+3)+"行，工作组编号不存在");
				}else{
					bean.setGsGzzid(workGroupMap.get(bean.getGsGzzbh()).getId());//工种组id
				}
			}
			
			//验证阶段编号是否存在
			if(stageFlag && !StringUtils.isBlank(bean.getGsJdbm())){
				if(stageMap.get(bean.getGsJdbm()) == null){
					addErrorMessage("工种工时表，第"+(i+3)+"行，阶段编号不存在");
				}else{
					bean.setGsJdid(stageMap.get(bean.getGsJdbm()).getId());//阶段id
				}
			}
			
			//验证当前工卡状态:根据机构代码、工卡编号、版本、机型 查询b_g2_013 工卡表
			if(bbGkbhJx){
				WorkCard workCard = workCardMapper.getWorkCardByBbAndjxAndGkbh(bean.getGsBb(), bean.getGsJx(), bean.getGsGkh(), ThreadVarUtil.getUser().getJgdm());
				if (null != workCard && !StringUtils.isBlank(workCard.getId())) {
					bean.setGsWcId(workCard.getId());
					if((workCard.getZt()==null?0:workCard.getZt()) != 1){
						addErrorMessage("工种工时表，第"+(i+3)+"行，数据库已存在，当前工卡处于非保存状态，不能导入");
					}
				}else{
					String key = bean.getGsBb()+"◎"+bean.getGsJx()+"◎"+bean.getGsGkh()+"◎"+ThreadVarUtil.getUser().getJgdm();
					if(onlyInExcelCardMap.get(key) == null){
						addErrorMessage("工种工时表，第"+(i+3)+"行，工卡不存在");
					}else{
						bean.setGsWcId(onlyInExcelCardMap.get(key));
					}
				}
			}
			
		}
	}

	/**
	 * @Description 校验工具清单信息
	 * @CreateTime 2017-12-11 上午11:38:17
	 * @CreateBy 雷伟
	 * @param gjList
	 */
	private void validateGjParam(List<WorkCardImplList> gjList) {
		for (int i = 0; null != gjList && i < gjList.size(); i++) {
			WorkCardImplList bean = gjList.get(i);

			boolean jhFlag = true;
			boolean bbGkbhJx = true;

			//工卡编号
			if(StringUtils.isBlank(bean.getGjGkh())){
				addErrorMessage("工具清单表，第"+(i+3)+"行，工卡编号不能为空");
				bbGkbhJx = false;
			}else{
				if(Utils.Str.isChinese(bean.getGjGkh())){
					addErrorMessage("工具清单表，第"+(i+3)+"行，工卡编号不能含有中文");
					bbGkbhJx = false;
				}
				if(Utils.Str.getLength(bean.getGjGkh()) > 50){
					addErrorMessage("工具清单表，第"+(i+3)+"行，工卡编号不能超过50个字符");
					bbGkbhJx = false;
				}
			}

			//版本
			if(StringUtils.isBlank(bean.getGjBb())){
				addErrorMessage("工具清单表，第"+(i+3)+"行，版本不能为空");
				bbGkbhJx = false;
			}else{
				if(!Utils.Str.isDecimalzs(bean.getGjBb(), 2)){
					addErrorMessage("工具清单表，第"+(i+3)+"行，版本只能为[0~9999999.99]范围内的两位小数");
					bbGkbhJx = false;
				}
			}

			//机型
			if(StringUtils.isBlank(bean.getGjJx())){
				addErrorMessage("工具清单表，第"+(i+3)+"行，机型不能为空");
				bbGkbhJx = false;
			}else{
				if (Utils.Str.isChinese(bean.getGjJx())) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，机型不能含有中文");
					bbGkbhJx = false;
				}
				if (Utils.Str.getLength(bean.getGjJx()) > 50) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，机型不能超过50个字符");
					bbGkbhJx = false;
				}
				if (!actTypeList.contains(bean.getGjJx())) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，机型不存在");
					bbGkbhJx = false;
				}
			}

			//件号
			if (StringUtils.isBlank(bean.getGjJh())) {
				addErrorMessage("工具清单表，第"+(i+3)+"行，件号不能为空");
				jhFlag = false;
			}else{
				if (Utils.Str.getLength(bean.getGjJh()) > 50) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，件号不能超过50个字符");
					jhFlag = false;
				}
				if (Utils.Str.isChinese(bean.getGjJh())) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，件号不能含有中文");
					jhFlag = false;
				}
			}

			//判断件号是否存在
			if(jhFlag && !StringUtils.isBlank(bean.getGjJh())){
				if (hcMap.get(bean.getGjJh()) == null) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，件号不存在");
				}else{
					HCMainData hcObj = hcMap.get(bean.getGjJh());
					if (hcObj.getHclx() == 2) {
						bean.setGjHc(hcObj);
					}else{
						addErrorMessage("工具清单表，第"+(i+3)+"行，件号必须属于工具");
					}
				}
			}

			//需求数量
			if(StringUtils.isBlank(bean.getGjSl())){
				addErrorMessage("工具清单表，第"+(i+3)+"行，需求数量不能为空");
			}else{
				if(!Utils.Str.isDecimal(bean.getGjSl(), 2)){
					if(!validateNumRange(bean.getGjSl(),this.XQSL)){
						addErrorMessage("工具清单表，第"+(i+3)+"行，需求数量只能为[0~999999999]范围内正整数");
					}
				}
			}

			//器材代号
			if (!StringUtils.isBlank(bean.getGjQcdh())) {
				if (Utils.Str.getLength(bean.getGjQcdh()) > 50) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，器材代号不能超过50个字符");
				}
				if (Utils.Str.isChinese(bean.getGjQcdh())) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，器材代号不能含有中文");
				}
			}

			//必要性
			if (!StringUtils.isBlank(bean.getGjBxx())) {
				if (Utils.Str.getLength(bean.getGjBxx()) > 15) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，必要性不能超过15个字符");
				}
			}

			//备注
			if (!StringUtils.isBlank(bean.getGjBz())) {
				if (Utils.Str.getLength(bean.getGjBz()) > 300) {
					addErrorMessage("工具清单表，第"+(i+3)+"行，备注不能超过300个字符");
				}
			}

			//工卡编号、版本、机型、件号在文本中唯一
			if (!StringUtils.isBlank(bean.getGjGkh()) && !StringUtils.isBlank(bean.getGjBb())
					&& !StringUtils.isBlank(bean.getGjJx()) && !StringUtils.isBlank(bean.getGjJh())) {
				String key = bean.getGjGkh()+"◎"+bean.getGjBb()+"◎"+bean.getGjJx()+"◎"+bean.getGjJh();
				if(gjUniqueMap.get(key) != null && gjUniqueMap.get(key).intValue() > 1){
					addErrorMessage("工具清单表，第"+(i+3)+"行，工卡编号+版本+机型+件号在文本中重复");
				}
			}

			//验证当前工卡状态:根据机构代码、工卡编号、版本、机型 查询b_g2_013 工卡表
			if(bbGkbhJx){
				WorkCard workCard = workCardMapper.getWorkCardByBbAndjxAndGkbh(bean.getGjBb(), bean.getGjJx(), bean.getGjGkh(), ThreadVarUtil.getUser().getJgdm());
				if (null != workCard && !StringUtils.isBlank(workCard.getId())) {
					bean.setGjWcId(workCard.getId());
					if((workCard.getZt()==null?0:workCard.getZt()) != 1){
						addErrorMessage("工具清单表，第"+(i+3)+"行，数据库已存在，当前工卡处于非保存状态，不能导入");
					}
				}else{
					String key = bean.getGjBb()+"◎"+bean.getGjJx()+"◎"+bean.getGjGkh()+"◎"+ThreadVarUtil.getUser().getJgdm();
					if(onlyInExcelCardMap.get(key) == null){
						addErrorMessage("工具清单表，第"+(i+3)+"行，工卡不存在");
					}else{
						bean.setGjWcId(onlyInExcelCardMap.get(key));
					}
				}
			}

		}
	}

	/**
	 * @Description 校验器材清单信息
	 * @CreateTime 2017-12-8 下午1:35:14
	 * @CreateBy 雷伟
	 * @param workCardList
	 */
	private void validateQcParam(List<WorkCardImplList> qcList) {
		for (int i = 0; null != qcList && i < qcList.size(); i++) {
			WorkCardImplList bean = qcList.get(i);

			boolean jhFlag = true;
			boolean bbGkbhJx = true;

			//工卡编号
			if(StringUtils.isBlank(bean.getQcGkh())){
				addErrorMessage("器材清单表，第"+(i+3)+"行，工卡编号不能为空");
				bbGkbhJx = false;
			}else{
				if(Utils.Str.isChinese(bean.getQcGkh())){
					addErrorMessage("器材清单表，第"+(i+3)+"行，工卡编号不能含有中文");
					bbGkbhJx = false;
				}
				if(Utils.Str.getLength(bean.getQcGkh()) > 50){
					addErrorMessage("器材清单表，第"+(i+3)+"行，工卡编号不能超过50个字符");
					bbGkbhJx = false;
				}
			}

			//版本
			if(StringUtils.isBlank(bean.getQcBb())){
				addErrorMessage("器材清单表，第"+(i+3)+"行，版本不能为空");
				bbGkbhJx = false;
			}else{
				if(!Utils.Str.isDecimalzs(bean.getQcBb(), 2)){
					addErrorMessage("器材清单表，第"+(i+3)+"行，版本只能为[0~9999999.99]范围内的两位小数");
					bbGkbhJx = false;
				}
			}

			//机型
			if(StringUtils.isBlank(bean.getQcJx())){
				addErrorMessage("器材清单表，第"+(i+3)+"行，机型不能为空");
				bbGkbhJx = false;
			}else{
				if (Utils.Str.isChinese(bean.getQcJx())) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，机型不能含有中文");
					bbGkbhJx = false;
				}
				if (Utils.Str.getLength(bean.getQcJx()) > 50) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，机型不能超过50个字符");
					bbGkbhJx = false;
				}
				if (!actTypeList.contains(bean.getQcJx())) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，机型不存在");
					bbGkbhJx = false;
				}
			}

			//件号
			if (StringUtils.isBlank(bean.getQcJh())) {
				addErrorMessage("器材清单表，第"+(i+3)+"行，件号不能为空");
				jhFlag = false;
			}else{
				if (Utils.Str.getLength(bean.getQcJh()) > 50) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，件号不能超过50个字符");
					jhFlag = false;
				}
				if (Utils.Str.isChinese(bean.getQcJh())) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，件号不能含有中文");
					jhFlag = false;
				}
			}

			//判断件号是否存在
			if(jhFlag && !StringUtils.isBlank(bean.getQcJh())){
				if (hcMap.get(bean.getQcJh()) == null) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，件号不存在");
				}else{
					HCMainData hcObj = hcMap.get(bean.getQcJh());
					if (hcObj.getHclx() == 1 || hcObj.getHclx() == 4) {
						bean.setQcHc(hcObj);
					}else{
						addErrorMessage("器材清单表，第"+(i+3)+"行，件号必须属于器材");
					}
				}
			}

			//需求数量
			if(StringUtils.isBlank(bean.getQcSl())){
				addErrorMessage("器材清单表，第"+(i+3)+"行，需求数量不能为空");
			}else{
				if(!validateNumRange(bean.getQcSl(),this.XQSL)){
					addErrorMessage("器材清单表，第"+(i+3)+"行，需求数量只能为[0~999999999]范围内正整数");
				}
			}

			//器材代号
			if (!StringUtils.isBlank(bean.getQcSl())) {
				if (Utils.Str.getLength(bean.getQcSl()) > 50) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，器材代号不能超过50个字符");
				}
				if (Utils.Str.isChinese(bean.getQcSl())) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，器材代号不能含有中文");
				}
			}

			//必要性
			if (!StringUtils.isBlank(bean.getQcBxx())) {
				if (Utils.Str.getLength(bean.getQcBxx()) > 15) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，必要性不能超过15个字符");
				}
			}

			//备注
			if (!StringUtils.isBlank(bean.getQcBz())) {
				if (Utils.Str.getLength(bean.getQcBz()) > 300) {
					addErrorMessage("器材清单表，第"+(i+3)+"行，备注不能超过300个字符");
				}
			}

			//工卡编号、版本、机型、件号在文本中唯一
			if (!StringUtils.isBlank(bean.getQcGkh()) && !StringUtils.isBlank(bean.getQcBb()) && 
					!StringUtils.isBlank(bean.getQcJx()) && !StringUtils.isBlank(bean.getQcJh())) {
				String key = bean.getQcGkh()+"◎"+bean.getQcBb()+"◎"+bean.getQcJx()+"◎"+bean.getQcJh();
				if(qcUniqueMap.get(key) != null && qcUniqueMap.get(key).intValue() > 1){
					addErrorMessage("器材清单表，第"+(i+3)+"行，工卡编号+版本+机型+件号在文本中重复");
				}
			}

			//验证当前工卡状态:根据机构代码、工卡编号、版本、机型 查询b_g2_013 工卡表
			if(bbGkbhJx){
				WorkCard workCard = workCardMapper.getWorkCardByBbAndjxAndGkbh(bean.getQcBb(), bean.getQcJx(), bean.getQcGkh(), ThreadVarUtil.getUser().getJgdm());
				if (null != workCard && !StringUtils.isBlank(workCard.getId())) {
					bean.setQcWcId(workCard.getId());
					if((workCard.getZt()==null?0:workCard.getZt()) != 1){
						addErrorMessage("器材清单表，第"+(i+3)+"行，数据库已存在，当前工卡处于非保存状态，不能导入");
					}
				}else{
					String key = bean.getQcBb()+"◎"+bean.getQcJx()+"◎"+bean.getQcGkh()+"◎"+ThreadVarUtil.getUser().getJgdm();
					if(onlyInExcelCardMap.get(key) == null){
						addErrorMessage("器材清单表，第"+(i+3)+"行，工卡不存在");
					}else{
						bean.setQcWcId(onlyInExcelCardMap.get(key));
					}
				}
			}
		}
	}

	/**
	 * @Description 校验工卡信息
	 * @CreateTime 2017-12-8 下午1:35:14
	 * @CreateBy 雷伟
	 * @param workCardList
	 */
	private void validateWorkcardParam(List<WorkCardImplList> workCardList) {
		for (int i = 0; null != workCardList && i < workCardList.size(); i++) {
			WorkCardImplList bean = workCardList.get(i);

			boolean bbGkbhJx = true; //版本+工卡编号+机型是否都输入正确
			boolean gzzbh = true; //工作组编号是否输入正确
			boolean accessFlag = true; //盖板是否存在

			//工卡编号
			if(StringUtils.isBlank(bean.getWcGkh())){
				addErrorMessage("工卡信息表，第"+(i+3)+"行，工卡编号不能为空");
				bbGkbhJx = false;
			}else{
				if(Utils.Str.isChinese(bean.getWcGkh())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工卡编号不能含有中文");
					bbGkbhJx = false;
				}
				if(Utils.Str.getLength(bean.getWcGkh()) > 50){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工卡编号不能超过50个字符");
					bbGkbhJx = false;
				}
			}

			//版本
			if(StringUtils.isBlank(bean.getWcBb())){
				addErrorMessage("工卡信息表，第"+(i+3)+"行，版本不能为空");
				bbGkbhJx = false;
			}else{
				if(!Utils.Str.isDecimalzs(bean.getWcBb(), 2)){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，版本只能为[0~9999999.99]范围内的两位小数");
					bbGkbhJx = false;
				}
			}

			//机型
			if(StringUtils.isBlank(bean.getWcJx())){
				addErrorMessage("工卡信息表，第"+(i+3)+"行，机型不能为空");
				bbGkbhJx = false;
			}else{
				if (Utils.Str.isChinese(bean.getWcJx())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，机型不能含有中文");
					bbGkbhJx = false;
				}
				if (Utils.Str.getLength(bean.getWcJx()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，机型不能超过50个字符");
					bbGkbhJx = false;
				}
				if (!actTypeList.contains(bean.getWcJx())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，机型不存在");
					bbGkbhJx = false;
				}
			}

			//RII
			if (StringUtils.isBlank(bean.getWcIsbj())) {
				bean.setWcIsbj("0");
			} else {
				String[] fl = {"1","0"};
				if(!Arrays.asList(fl).contains(bean.getWcIsbj())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，RII范围必须在：1、0");
				}
			}

			//ATA
			if (!StringUtils.isBlank(bean.getWcZjh())) {
				if (Utils.Str.isChinese(bean.getWcZjh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，ATA章节号不能含有中文");
				}
				if (Utils.Str.getLength(bean.getWcZjh()) > 20) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，ATA章节号不能超过20个字符");
				}
				if (!fixChapterList.contains(bean.getWcZjh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，ATA章节号不存在");
				}
			}

			//工卡标题
			if (!StringUtils.isBlank(bean.getWcGzbt())) {
				if (Utils.Str.getLength(bean.getWcGzbt()) > 300) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工卡标题不能超过300个字符");
				}
			}

			//工作概述
			if (!StringUtils.isBlank(bean.getWcGzgs())) {
				if (Utils.Str.getLength(bean.getWcGzgs()) > 1000) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作概述不能超过1000个字符");
				}
			}

			//维修项目编号
			if (!StringUtils.isBlank(bean.getWcWxxmbh())) {
				if (Utils.Str.isChinese(bean.getWcWxxmbh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，维修项目编号不能含有中文");
				}
				if (Utils.Str.getLength(bean.getWcWxxmbh()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，维修项目编号不能超过50个字符");
				}
			}

			//任务单号
			if (!StringUtils.isBlank(bean.getWcRwdh())) {
				if (Utils.Str.isChinese(bean.getWcRwdh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，任务单号不能含有中文");
				}
				if (Utils.Str.getLength(bean.getWcRwdh()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，任务单号不能超过50个字符");
				}
			}

			//工作单号
			if (!StringUtils.isBlank(bean.getWcGzdh())) {
				if (Utils.Str.isChinese(bean.getWcGzdh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作单号不能含有中文");
				}
				if (Utils.Str.getLength(bean.getWcGzdh()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作单号不能超过50个字符");
				}
			}

			//控制号
			if (!StringUtils.isBlank(bean.getWcKzh())) {
				if (Utils.Str.isChinese(bean.getWcKzh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，控制号不能含有中文");
				}
				if (Utils.Str.getLength(bean.getWcKzh()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，控制号不能超过50个字符");
				}
			}

			//CMP号
			if (!StringUtils.isBlank(bean.getWcCmph())) {
				if (Utils.Str.isChinese(bean.getWcCmph())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，CMP号不能含有中文");
				}
				if (Utils.Str.getLength(bean.getWcCmph()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，CMP号不能超过50个字符");
				}
			}

			//工作类别
			if (!StringUtils.isBlank(bean.getWcGzlx())) {
				if (Utils.Str.getLength(bean.getWcGzlx()) > 15) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作类别不能超过15个字符");
				}
			}

			//工卡类型
			if (!StringUtils.isBlank(bean.getWcGklx())) {
				if (Utils.Str.getLength(bean.getWcGklx()) > 15) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工卡类型不能超过15个字符");
				}
			}

			//专业
			if (!StringUtils.isBlank(bean.getWcZy())) {
				if (Utils.Str.getLength(bean.getWcZy()) > 15) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，专业不能超过15个字符");
				}
			}

			//工作组编号
			if (!StringUtils.isBlank(bean.getWcGzzbh())) {
				if (Utils.Str.isChinese(bean.getWcGzzbh())) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作组编号不能含有中文");
					gzzbh = false;
				}
				if (Utils.Str.getLength(bean.getWcGzzbh()) > 50) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作组编号不能超过50个字符");
					gzzbh = false;
				}
			}

			//间隔/重复
			if (!StringUtils.isBlank(bean.getWcJgcf())) {
				if (Utils.Str.getLength(bean.getWcJgcf()) > 300) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，间隔/重复不能超过300个字符");
				}
			}

			//接近
			if (!StringUtils.isBlank(bean.getWcAccess())) {
				if(Utils.Str.isChinese(bean.getWcAccess())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，接近不能为中文");
					accessFlag = false;
				}
				if (Utils.Str.getLength(bean.getWcAccess()) > 1300) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，接近不能超过1300个字符");
					accessFlag = false;
				}
			}
			
			if(accessFlag && !StringUtils.isBlank(bean.getWcAccess())){
				List<String> wcAccessList = new ArrayList<String>();
				String[] accessArr = bean.getWcAccess().replace("，", ",").split(",");
				for (int j = 0 ; accessArr != null && j < accessArr.length; j++) {
					CoverPlateInformation obj = coverPlateInformationMapper.getCoverPlateByParam(ThreadVarUtil.getUser().getJgdm(),bean.getWcJx(),accessArr[j]);
					if(null != obj && !StringUtils.isBlank(obj.getId())){
						wcAccessList.add(obj.getId()+"◎"+accessArr[j]);
					}else{
						addErrorMessage("工卡信息表，第"+(i+3)+"行，接近"+accessArr[j]+"不存在");
						break;
					}
				}
				bean.setWcAccessList(wcAccessList);
			}

			//区域
			if (!StringUtils.isBlank(bean.getWcZone())) {
				if(Utils.Str.isChinese(bean.getWcZone())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，区域不能为中文");
				}
				if (Utils.Str.getLength(bean.getWcZone()) > 1300) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，区域不能超过1300个字符");
				}
			}

			//飞机站位
			if (!StringUtils.isBlank(bean.getWcFjzw())) {
				if(Utils.Str.isChinese(bean.getWcFjzw())){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，飞机站位不能为中文");
				}
				if (Utils.Str.getLength(bean.getWcFjzw()) > 1300) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，飞机站位不能超过1300个字符");
				}
			}
			
			//参考人数
			if (StringUtils.isBlank(bean.getWcJhgsrs())) {
				bean.setWcJhgsrs("1");
			} else {
				if(!validateNumRange(bean.getWcJhgsrs(),this.Z)){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，参考人数只能为[0~9999999]范围内正整数");
				}
			}

			//每人耗时（小时数）
			if (StringUtils.isBlank(bean.getWcJhgsxss())) {
				bean.setWcJhgsxss("1");
			} else {
				if(!Utils.Str.isDecimalzs(bean.getWcJhgsxss(), 2)){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，每人耗时（小时数）只能为[0~9999999.99]范围内的两位小数");
				}
			}

			//适用单位
			if (!StringUtils.isBlank(bean.getWcSydw())) {
				if (Utils.Str.getLength(bean.getWcSydw()) > 15) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，适用单位不能超过15个字符");
				}
			}

			//依据文件/版本
			if (!StringUtils.isBlank(bean.getWcYjwj())) {
				if (Utils.Str.getLength(bean.getWcYjwj()) > 1000) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，依据文件/版本不能超过1000个字符");
				}
			}

			//特别适用性说明
			if (!StringUtils.isBlank(bean.getWcTbsysm())) {
				if (Utils.Str.getLength(bean.getWcTbsysm()) > 1000) {
					addErrorMessage("工卡信息表，第"+(i+3)+"行，特别适用性说明不能超过1000个字符");
				}
			}

			//工卡编号、版本、机型在文本中唯一
			if (!StringUtils.isBlank(bean.getWcGkh()) && !StringUtils.isBlank(bean.getWcBb()) && !StringUtils.isBlank(bean.getWcJx())) {
				String key = bean.getWcGkh()+"◎"+bean.getWcJx();
				if(wcUniqueMap.get(key) != null && wcUniqueMap.get(key).intValue() > 1){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工卡编号+机型在文本中重复");
				}
			}

			//验证当前工卡状态:根据机构代码、工卡编号、版本、机型 查询b_g2_013 工卡表
			if(bbGkbhJx){
				WorkCard workCard = workCardMapper.getWorkCardByBbAndjxAndGkbh(bean.getWcBb(), bean.getWcJx(), bean.getWcGkh(), ThreadVarUtil.getUser().getJgdm());
				if (null != workCard && !StringUtils.isBlank(workCard.getId())) {
					bean.setWcId(workCard.getId());
					if((workCard.getZt()==null?0:workCard.getZt()) != 1){
						addErrorMessage("工卡信息表，第"+(i+3)+"行，当前工卡处于非保存状态，不能导入");
					}
				}else{
					String key = bean.getWcBb()+"◎"+bean.getWcJx()+"◎"+bean.getWcGkh()+"◎"+ThreadVarUtil.getUser().getJgdm();
					bean.setNewWcId(UUID.randomUUID().toString());
					onlyInExcelCardMap.put(key, bean.getNewWcId());
				}
			}

			//验证是否存在同工卡号的工卡:机构代码、工卡编号、机型、排除自身版本号
			if(bbGkbhJx){
				List<WorkCard> workCards = workCardMapper.getWorkCardByNotBbAndjxAndGkbh(bean.getWcBb(), bean.getWcJx(), bean.getWcGkh(), ThreadVarUtil.getUser().getJgdm());
				for (int j = 0; null != workCards && j < workCards.size(); j++) {
					Integer[] arr = {1,2,3,4,5,6};
					if(Arrays.asList(arr).contains(workCards.get(j).getZt())){
						addErrorMessage("工卡信息表，第"+(i+3)+"行，数据库已存在，同工卡号的工卡");
						break;
					}
				}
			}

			//验证工卡版本
			if(bbGkbhJx){
				BigDecimal maxBb = workCardMapper.getMaxBb(bean.getWcJx(), bean.getWcGkh(), ThreadVarUtil.getUser().getJgdm(), bean.getWcId());
				if(null != maxBb && new BigDecimal(bean.getWcBb()).compareTo(maxBb) != 1){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，版本必须大于老版本");
				}
			}

			//验证工作组编号是否存在
			if (!StringUtils.isBlank(bean.getWcGzzbh()) && gzzbh) {
				if(workGroupMap.get(bean.getWcGzzbh()) == null){
					addErrorMessage("工卡信息表，第"+(i+3)+"行，工作组编号不存在");
				}else{
					bean.setWcGzzid(workGroupMap.get(bean.getWcGzzbh()).getId());//工种组id
				}
			}
		}
	}

	/**
	 * 保存到数据库
	 * @throws ExcelImportException 
	 */
	@Override
	public int writeToDB(Map<Integer, List<WorkCardImplList>> sheetList) throws ExcelImportException {
		List<WorkCardImplList> workCardList = new ArrayList<WorkCardImplList>();//工卡信息
		List<WorkCardImplList> qcList = new ArrayList<WorkCardImplList>();//器材清单
		List<WorkCardImplList> gjList = new ArrayList<WorkCardImplList>();//工具清单
		List<WorkCardImplList> gzgsList = new ArrayList<WorkCardImplList>();//工种工时
		implDate = commonService.getSysdate();
		czls = UUID.randomUUID().toString();
		
		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			if (sheetIndex == 0) {
				workCardList = sheetList.get(sheetIndex);
			}else if (sheetIndex == 1) {
				qcList = sheetList.get(sheetIndex);
			}else if (sheetIndex == 2) {
				gjList = sheetList.get(sheetIndex);
			}else if (sheetIndex == 3) {
				gzgsList = sheetList.get(sheetIndex);
			}
		}
		
		//写工卡信息
		if (null != workCardList && workCardList.size() > 0) {
			writeWorkcardToDB(workCardList);
		}

		//写器材清单
		if (null != qcList && qcList.size() > 0) {
			writeQcToDB(qcList);
		}

		//写工具清单
		if (null != gjList && gjList.size() > 0) {
			writeGjToDB(gjList);
		}

		//写工种工时清单
		if (null != gzgsList && gzgsList.size() > 0) {
			writeGzgsToDB(gzgsList);
		}
		
		return 0;
	}

	/**
	 * @Description 写工种工时信息
	 * @CreateTime 2017-12-11 下午2:23:12
	 * @CreateBy 雷伟
	 * @param gzgsList
	 */
	private void writeGzgsToDB(List<WorkCardImplList> gzgsList) {
		List<WorkHour> addWorkHours = new ArrayList<WorkHour>(); //需要新增的工种工时
		List<String> addWorkHourIds = new ArrayList<String>();
		
		BigDecimal rs = new BigDecimal(1);
		for (int i = 0; gzgsList != null && i < gzgsList.size(); i++) {
			WorkCardImplList bean = gzgsList.get(i);
			
			/*工种工时*/
			WorkHour workHour = new WorkHour();
			workHour.setId(UUID.randomUUID().toString());
			workHour.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			workHour.setYwlx(8);
			workHour.setYwid(bean.getGsWcId());
			workHour.setXc(i+1);
			workHour.setRw(bean.getGsRw());
			workHour.setGzzid(bean.getGsGzzid());
			workHour.setGzzbh(bean.getGsGzzbh());
			workHour.setJdid(bean.getGsJdid());
			workHour.setJdbm(bean.getGsJdbm());
			workHour.setJhgsRs(rs);
			if(!StringUtils.isBlank(bean.getGsJhgsxss())){
				workHour.setJhgsXss(new BigDecimal(bean.getGsJhgsxss()));
			}else{
				workHour.setJhgsXss(null);
			}
			workHour.setBz(bean.getGsBz());
			workHour.setZt(1);
			workHour.setWhdwid(ThreadVarUtil.getUser().getBmdm());
			workHour.setWhrid(ThreadVarUtil.getUser().getId());
			workHour.setWhsj(implDate);
			
			addWorkHours.add(workHour);
			addWorkHourIds.add(workHour.getId());
		}
		
		/*新增工种工时:先删除后新增*/
		if (null != addWorkHours && addWorkHours.size() > 0){
			workHourMapper.delete4BatchImpl(addWorkHours);
			workHourMapper.insert4BatchImpl(addWorkHours);
			commonRecService.write("id",addWorkHourIds, TableEnum.B_G2_993, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
	}

	/**
	 * @Description 写工具清单信息
	 * @CreateTime 2017-12-11 下午2:22:55
	 * @CreateBy 雷伟
	 * @param gjList
	 */
	private void writeGjToDB(List<WorkCardImplList> gjList) {
		List<MaterialTool> addMaterialTools = new ArrayList<MaterialTool>(); //需要新增的工具
		List<String> addMaterialToolIds = new ArrayList<String>();
		
		for (int i = 0; gjList != null && i < gjList.size(); i++) {
			WorkCardImplList bean = gjList.get(i);
			
			/*器材*/
			MaterialTool materialTool = new MaterialTool();
			materialTool.setId(UUID.randomUUID().toString());
			materialTool.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			materialTool.setYwlx(8);
			materialTool.setYwid(bean.getGjWcId());
			materialTool.setXc(i+1);
			if(null != bean.getGjHc()){
				materialTool.setBjlx(bean.getGjHc().getHclx());
				materialTool.setBjid(bean.getGjHc().getId());
				materialTool.setJhly(bean.getGjHc().getJhly());
			}else{
				materialTool.setBjlx(null);
				materialTool.setBjid(null);
				materialTool.setJhly(null);
			}
			materialTool.setJh(bean.getGjJh());
			if(!StringUtils.isBlank(bean.getGjSl())){
				materialTool.setSl(new BigDecimal(bean.getGjSl()));
			}else{
				materialTool.setSl(null);
			}
			materialTool.setBxx(bean.getGjBxx());
			materialTool.setQcdh(bean.getGjQcdh());
			materialTool.setBz(bean.getGjBz());
			materialTool.setZt(1);
			materialTool.setWhdwid(ThreadVarUtil.getUser().getBmdm());
			materialTool.setWhrid(ThreadVarUtil.getUser().getId());
			materialTool.setWhsj(implDate);
			
			addMaterialTools.add(materialTool);
			addMaterialToolIds.add(materialTool.getId());
		}
		
		/*新增工具清单:先删除后新增*/
		if (null != addMaterialTools && addMaterialTools.size() > 0){
			materialToolMapper.delete4BatchImpl(addMaterialTools);
			materialToolMapper.insert4BatchImpl(addMaterialTools);
			commonRecService.write("id",addMaterialToolIds, TableEnum.B_G2_997, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
	}

	/**
	 * @Description 写器材清单信息
	 * @CreateTime 2017-12-11 下午2:22:41
	 * @CreateBy 雷伟
	 * @param qcList
	 */
	private void writeQcToDB(List<WorkCardImplList> qcList) {
		List<MaterialTool> addMaterialTools = new ArrayList<MaterialTool>(); //需要新增的器材
		List<String> addMaterialToolIds = new ArrayList<String>();
		
		for (int i = 0; qcList != null && i < qcList.size(); i++) {
			WorkCardImplList bean = qcList.get(i);
			
			/*器材*/
			MaterialTool materialTool = new MaterialTool();
			materialTool.setId(UUID.randomUUID().toString());
			materialTool.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			materialTool.setYwlx(8);
			materialTool.setYwid(bean.getQcWcId());
			materialTool.setXc(i+1);
			if(null != bean.getQcHc()){
				materialTool.setBjlx(bean.getQcHc().getHclx());
				materialTool.setBjid(bean.getQcHc().getId());
				materialTool.setJhly(bean.getQcHc().getJhly());
			}else{
				materialTool.setBjlx(null);
				materialTool.setBjid(null);
				materialTool.setJhly(null);
			}
			materialTool.setJh(bean.getQcJh());
			if(!StringUtils.isBlank(bean.getQcSl())){
				materialTool.setSl(new BigDecimal(bean.getQcSl()));
			}else{
				materialTool.setSl(null);
			}
			materialTool.setBxx(bean.getQcBxx());
			materialTool.setQcdh(bean.getQcQcdh());
			materialTool.setBz(bean.getQcBz());
			materialTool.setZt(1);
			materialTool.setWhdwid(ThreadVarUtil.getUser().getBmdm());
			materialTool.setWhrid(ThreadVarUtil.getUser().getId());
			materialTool.setWhsj(implDate);
			
			addMaterialTools.add(materialTool);
			addMaterialToolIds.add(materialTool.getId());
		}
		
		/*新增器材清单:先删除后新增*/
		if (null != addMaterialTools && addMaterialTools.size() > 0){
			materialToolMapper.delete4BatchImpl(addMaterialTools);
			materialToolMapper.insert4BatchImpl(addMaterialTools);
			commonRecService.write("id",addMaterialToolIds, TableEnum.B_G2_997, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
	}

	/**
	 * @Description 写工卡信息
	 * @CreateTime 2017-12-11 下午2:22:26
	 * @CreateBy 雷伟
	 * @param workCardList
	 */
	private void writeWorkcardToDB(List<WorkCardImplList> workCardList) {
		List<WorkCard> addWockcards = new ArrayList<WorkCard>(); //需要新增的工卡
		List<String> addWockcardIds = new ArrayList<String>();
		
		List<WorkCard> updateWockcards = new ArrayList<WorkCard>(); //需要修改的工卡
		List<String> updateWockcardIds = new ArrayList<String>();
		
		List<WorkCard> preBbWockcards = new ArrayList<WorkCard>(); //有前版本记录的工卡
		List<String> preBbWockcardIds = new ArrayList<String>();
		
		List<ApplicableUnit> addUnits = new ArrayList<ApplicableUnit>();//工卡-适用单位表
		List<String> addUnitIds = new ArrayList<String>();
		
		List<CoverPlate> addCoverPlates = new ArrayList<CoverPlate>();//区域\飞机站位\接近(盖板)
		List<String> addCoverPlateIds = new ArrayList<String>();
		
		for (int i = 0; workCardList != null && i < workCardList.size(); i++) {
			WorkCardImplList bean = workCardList.get(i);
			
			/*工卡*/
			WorkCard workCard = new WorkCard();
			if(!StringUtils.isBlank(bean.getWcId())){
				workCard.setId(bean.getWcId());
			}else if(!StringUtils.isBlank(bean.getNewWcId())){
				workCard.setId(bean.getNewWcId());
			}
			workCard = coverToWorkCard(workCard,bean);//转换成数据库实体
			if(!StringUtils.isBlank(bean.getWcId())){
				updateWockcards.add(workCard);
				updateWockcardIds.add(workCard.getId());
			}else{
				//获取前版本工卡
				WorkCard preWorkcard = workCardMapper.getPreWorkCardByjxAndGkbh(bean.getWcJx(), bean.getWcGkh(), ThreadVarUtil.getUser().getJgdm());
				if(null != preWorkcard && !StringUtils.isBlank(preWorkcard.getId())){
					workCard.setfBbid(preWorkcard.getId()); //当前版本的前版本ID
					preWorkcard.setbBbid(workCard.getId());//前版本的后版本ID
					preBbWockcards.add(preWorkcard);
					preBbWockcardIds.add(preWorkcard.getId());
				}
				addWockcards.add(workCard);
				addWockcardIds.add(workCard.getId());
			}
			
			/*b_g2_01302 工卡-适用单位表*/
			if(!StringUtils.isBlank(bean.getWcSydw())){
				String[] sydws = bean.getWcSydw().replace("，", ",").split(",");
				for (int j = 0 ; sydws != null && j < sydws.length; j++) {
					ApplicableUnit applicableUnit = new ApplicableUnit();
					applicableUnit.setId(UUID.randomUUID().toString());
					applicableUnit.setMainid(workCard.getId());
					applicableUnit.setSydwid(sydws[j]); //合理吗?
					applicableUnit.setSydw(sydws[j]);
					applicableUnit.setZt(1);
					applicableUnit.setWhbmid(ThreadVarUtil.getUser().getBmdm());
					applicableUnit.setWhrid(ThreadVarUtil.getUser().getId());
					applicableUnit.setWhsj(implDate);
					addUnits.add(applicableUnit);
					addUnitIds.add(applicableUnit.getId());
				}
			}
			
			/*b_g2_995区域/站位表/ 接近（盖板）*/
			if(!StringUtils.isBlank(bean.getWcZone())){
				String zoneBh = bean.getWcZone().replace("，", ",");
				CoverPlate coverPlate = getCoverPlate(workCard.getId(),1,1,"",zoneBh);
				addCoverPlates.add(coverPlate);
				addCoverPlateIds.add(coverPlate.getId());
			}
			if(!StringUtils.isBlank(bean.getWcFjzw())){
				String fjzwBh = bean.getWcFjzw().replace("，", ",");
				CoverPlate coverPlate = getCoverPlate(workCard.getId(),3,1,"",fjzwBh);
				addCoverPlates.add(coverPlate);
				addCoverPlateIds.add(coverPlate.getId());
			}
			if(!StringUtils.isBlank(bean.getWcAccess())){
				for (int j = 0 ; bean.getWcAccessList() != null && j < bean.getWcAccessList().size(); j++) {
					String[] accessArr = bean.getWcAccessList().get(j).split("◎");
					CoverPlate coverPlate = getCoverPlate(workCard.getId(),2,j+1,accessArr[0],accessArr[1]);
					addCoverPlates.add(coverPlate);
					addCoverPlateIds.add(coverPlate.getId());
				}
			}
		}
		
		/*保存新增的工卡*/
		if (null != addWockcards && addWockcards.size() > 0){
			workCardMapper.insert4Batch(addWockcards);
			commonRecService.write("id",addWockcardIds, TableEnum.B_G2_013, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		
		/*更新需要修改的工卡*/
		if (null != updateWockcards && updateWockcards.size() > 0){
			workCardMapper.update4Batch(updateWockcards);
			commonRecService.write("id",updateWockcardIds, TableEnum.B_G2_013, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}
		
		/*更新前版本的后版本ID*/
		if (null != preBbWockcards && preBbWockcards.size() > 0){
			workCardMapper.updatePreBb4Batch(preBbWockcards);
			commonRecService.write("id",preBbWockcardIds, TableEnum.B_G2_013, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}
		
		List<String> delMainIds = new ArrayList<String>();
		delMainIds.addAll(addWockcardIds);
		delMainIds.addAll(updateWockcardIds);
		
		/*新增适用单位表:先删除后新增*/
		if (null != delMainIds && delMainIds.size() > 0){
			applicableUnitMapper.delete4BatchImpl(delMainIds);
		}
		if (null != addUnits && addUnits.size() > 0){
			applicableUnitMapper.insert4BatchImpl(addUnits);
			commonRecService.write("id",addUnitIds, TableEnum.B_G2_01302, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		
		/*新增区域\飞机站位\盖板:先删除后新增*/
		if (null != delMainIds && delMainIds.size() > 0){
			coverPlateMapper.delete4BatchImpl(delMainIds);
		}
		if (null != addCoverPlates && addCoverPlates.size() > 0){
			coverPlateMapper.insert4BatchImpl(addCoverPlates);
			commonRecService.write("id",addCoverPlateIds, TableEnum.B_G2_995, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
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
		coverPlate.setYwlx(8);
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

	/**
	 * @Description 转换成工卡bean
	 * @CreateTime 2017-12-11 下午4:17:49
	 * @CreateBy 雷伟
	 * @param workCard
	 * @param bean
	 * @return
	 */
	private WorkCard coverToWorkCard(WorkCard workCard, WorkCardImplList bean) {
		workCard.setDprtcode(ThreadVarUtil.getUser().getJgdm());//机构代码=当前登录人.机构代码
		workCard.setGkh(bean.getWcGkh());//工卡号=文档.工卡编号
		if(!StringUtils.isBlank(bean.getWcBb())){
			workCard.setBb(new BigDecimal(bean.getWcBb()));//版本号=文档.版本
		}
		workCard.setJx(bean.getWcJx());//机型=文档.机型
		if(!StringUtils.isBlank(bean.getWcIsbj())){
			workCard.setIsBj(Integer.valueOf(bean.getWcIsbj()));//必检标识=文档.RII
		}
		workCard.setZjh(bean.getWcZjh());//ATA章节号=文档.ATA章节号
		workCard.setWxxmbh(bean.getWcWxxmbh());//维修项目编号=文档.维修项目编号
		workCard.setRwdh(bean.getWcRwdh());//任务单号=文档.任务单号
		workCard.setKzh(bean.getWcKzh());//控制号=文档.控制号
		workCard.setCmph(bean.getWcCmph());//Cmp No=文档.CMP号
		workCard.setGzdh(bean.getWcGzdh());//工作单号=文档.工作单号
		workCard.setGzbt(bean.getWcGzbt());//工作标题=文档.工卡标题
		workCard.setGklx(bean.getWcGklx());//工卡类型=文档.工卡类别
		workCard.setGzlx(bean.getWcGzlx());//工作类型=文档.工作类型
		workCard.setZy(bean.getWcZy());//专业=文档.专业
		workCard.setGzzid(bean.getWcGzzid());//工作组id
		if(!StringUtils.isBlank(bean.getWcJhgsrs())){
			workCard.setJhgsRs(new BigDecimal(bean.getWcJhgsrs()));//计划工时-人数=文档.参考人数
		}
		if(!StringUtils.isBlank(bean.getWcJhgsxss())){
			workCard.setJhgsXss(new BigDecimal(bean.getWcJhgsxss()));//计划工时-小时数=文档.每人耗时（小时数）
		}
		workCard.setYjwj(bean.getWcYjwj());//依据文件/版本=文档.依据文件/版本
		workCard.setTbsysm(bean.getWcTbsysm());//特别适用性说明=文档.特别适用性说明
		workCard.setGzgs(bean.getWcGzgs());//工作概述=文档.工作概述
		workCard.setBz(bean.getWcJgcf());//备注=文档.间隔/重复
		workCard.setZt(1);//状态=1
		workCard.setZxbs(1);//最新标识=1
		workCard.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		workCard.setWhrid(ThreadVarUtil.getUser().getId());
		workCard.setWhsj(implDate);
		return workCard;
	}

	/**
	 * 转换成对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<WorkCardImplList>> convertBean( Map<Integer, List<Map<Integer, String>>> sheetList) throws ExcelImportException {
		wcUniqueMap.clear();
		qcUniqueMap.clear();
		gjUniqueMap.clear();
		gsUniqueMap.clear();

		//结果集(按插入的顺序保存)
		Map<Integer, List<WorkCardImplList>> resultMap = new LinkedHashMap<Integer, List<WorkCardImplList>>();

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			List<WorkCardImplList> workCardImplLists = new ArrayList<WorkCardImplList>();
			// sheet对应飞行履历数据
			List<Map<Integer, String>> rowList = sheetList.get(sheetIndex);
			//循环获取每一行数据
			Map<Integer, String> row;
			WorkCardImplList bean;
			for (int rowIndex = 0; null != rowList && rowIndex < rowList.size(); rowIndex++) {
				row = rowList.get(rowIndex); //行数据
				bean = new WorkCardImplList();//实体bean
				bean = convertRow2Bean(row,bean,sheetIndex);//将行数据转换成实体bean
				workCardImplLists.add(bean);//添加到结果集中
			}
			resultMap.put(sheetIndex, workCardImplLists);
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
	private WorkCardImplList convertRow2Bean(Map<Integer, String> row,WorkCardImplList bean,int sheetIndex) {

		String key = "";
		Integer num = 1;

		/**1.工卡信息WC**/
		if (sheetIndex == 0) {
			bean.setWcGkh(row.get(0));//工卡编号
			bean.setWcBb(row.get(1));//版本
			bean.setWcJx(row.get(2));//机型
			bean.setWcIsbj(row.get(3));//RII
			bean.setWcZjh(row.get(4));//ATA章节号
			bean.setWcGzbt(row.get(5));//工卡标题
			bean.setWcGzgs(row.get(6));//工作概述
			bean.setWcWxxmbh(row.get(7));//维修项目编号
			bean.setWcRwdh(row.get(8));//任务单号
			bean.setWcGzdh(row.get(9));//工作单号
			bean.setWcKzh(row.get(10));//控制号
			bean.setWcCmph(row.get(11));//CMP号 
			bean.setWcGzlx(row.get(12));//工作类别
			bean.setWcGklx(row.get(13));//工卡类型
			bean.setWcZy(row.get(14));//专业
			bean.setWcGzzbh(row.get(15));//工作组编号
			bean.setWcJgcf(row.get(16));//间隔/重复
			bean.setWcAccess(row.get(17));//接近
			bean.setWcZone(row.get(18));//区域
			bean.setWcFjzw(row.get(19));//飞机站位
			bean.setWcJhgsrs(row.get(20));//参考人数
			bean.setWcJhgsxss(row.get(21));//每人耗时（小时数）
			bean.setWcSydw(row.get(22));//适用单位
			bean.setWcYjwj(row.get(23));//依据文件/版本
			bean.setWcTbsysm(row.get(24));//特别适用性说明

			if (!StringUtils.isBlank(bean.getWcGkh()) && !StringUtils.isBlank(bean.getWcBb()) && !StringUtils.isBlank(bean.getWcJx())) {
				key = bean.getWcGkh()+"◎"+bean.getWcJx();
				if(wcUniqueMap.get(key) != null){
					num += wcUniqueMap.get(key);
				}
				wcUniqueMap.put(key, num);
			}
		}

		else if (sheetIndex == 1) {
			/**2.器材清单QC**/
			bean.setQcGkh(row.get(0));//工卡编号
			bean.setQcBb(row.get(1));//版本
			bean.setQcJx(row.get(2));//机型
			bean.setQcJh(row.get(3));//件号
			bean.setQcSl(row.get(4));//需求数量
			bean.setQcQcdh(row.get(5));//器材代号
			bean.setQcBxx(row.get(6));//必要性
			bean.setQcBz(row.get(7));//备注

			if (!StringUtils.isBlank(bean.getQcGkh()) && !StringUtils.isBlank(bean.getQcBb())
					&& !StringUtils.isBlank(bean.getQcJx()) && !StringUtils.isBlank(bean.getQcJh())) {
				key = bean.getQcGkh()+"◎"+bean.getQcBb()+"◎"+bean.getQcJx()+"◎"+bean.getQcJh();
				if(qcUniqueMap.get(key) != null){
					num += qcUniqueMap.get(key);
				}
				qcUniqueMap.put(key, num);
			}
		}

		else if (sheetIndex == 2) {
			/**3.工具清单GJ**/
			bean.setGjGkh(row.get(0));//工卡编号
			bean.setGjBb(row.get(1));//版本
			bean.setGjJx(row.get(2));//机型
			bean.setGjJh(row.get(3));//件号
			bean.setGjSl(row.get(4));//需求数量
			bean.setGjQcdh(row.get(5));//器材代号
			bean.setGjBxx(row.get(6));//必要性
			bean.setGjBz(row.get(7));//备注

			if (!StringUtils.isBlank(bean.getGjGkh()) && !StringUtils.isBlank(bean.getGjBb())
					&& !StringUtils.isBlank(bean.getGjJx()) && !StringUtils.isBlank(bean.getGjJh())) {
				key = bean.getGjGkh()+"◎"+bean.getGjBb()+"◎"+bean.getGjJx()+"◎"+bean.getGjJh();
				if(gjUniqueMap.get(key) != null){
					num += gjUniqueMap.get(key);
				}
				gjUniqueMap.put(key, num);
			}
		}

		else if (sheetIndex == 3) {
			/**4.工种工时GS**/
			bean.setGsGkh(row.get(0));//工卡编号
			bean.setGsBb(row.get(1));//版本
			bean.setGsJx(row.get(2));//机型
			bean.setGsJdbm(row.get(3));//阶段编号
			bean.setGsGzzbh(row.get(4));//工作组编号
			bean.setGsJhgsxss(row.get(5));//工时
			bean.setGsRw(row.get(6));//任务
			bean.setGsBz(row.get(7));//备注

			if (!StringUtils.isBlank(bean.getGsGkh()) && !StringUtils.isBlank(bean.getGsBb())
					&& !StringUtils.isBlank(bean.getGsJx()) && !StringUtils.isBlank(bean.getGsJdbm())
					&& !StringUtils.isBlank(bean.getGsGzzbh())) {
				key = bean.getGsGkh()+"◎"+bean.getGsBb()+"◎"+bean.getGsJx()+"◎"+bean.getGsJdbm()+"◎"+bean.getGsGzzbh();
				if(gsUniqueMap.get(key) != null){
					num += gsUniqueMap.get(key);
				}
				gsUniqueMap.put(key, num);
			}
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
			param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
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
	 * 获取飞机机型
	 * @return
	 */
	private List<String> getAllActType() {
		List<String> resultList = new ArrayList<String>();
		try {
			ActType param = new ActType();
			param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			param.setZt(1);
			List<ActType> actTypes = actTypeMapper.selectByParam(param);
			if (null == actTypes || actTypes.size() <= 0){
				return resultList;
			}
			for (ActType actType : actTypes) {
				resultList.add(actType.getFjjx());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
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
				else if("XQSL".equals(type)){
					if(Long.valueOf(num) >= 0 && Long.valueOf(num) <= 999999999){
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
	 * @Description 获取所有航材主数据
	 * @CreateTime 2017-11-30 下午1:58:14
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String, HCMainData> getAllHCMainData() {
		Map<String ,HCMainData> resultMap = new HashMap<String, HCMainData>();
		List<HCMainData> hcMainDatas = hCMainDataMapper.selectAllByDprtcode(ThreadVarUtil.getUser().getJgdm());
		for (int i = 0;null != hcMainDatas && i < hcMainDatas.size(); i++) {
			HCMainData hcMainData = hcMainDatas.get(i);
			if(!StringUtils.isBlank(hcMainData.getBjh())){
				resultMap.put(hcMainData.getBjh(), hcMainData);
			}
		}
		return resultMap;
	}
	
	/**
	 * @Description 获取工作组
	 * @CreateTime 2017-12-8 下午2:36:55
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String ,Stage> getStages() {
		Map<String ,Stage> resultMap = new HashMap<String, Stage>();
		Stage param = new Stage();
		param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		param.setZt(1);
		List<Stage> stages = stageMapper.getAllStages(param);
		for (int i = 0;null != stages && i < stages.size(); i++) {
			Stage stage = stages.get(i);
			if(!StringUtils.isBlank(stage.getJdbh())){
				resultMap.put(stage.getJdbh(), stage);
			}
		}
		return resultMap;
	}

	/**
	 * @Description 获取工作组
	 * @CreateTime 2017-12-8 下午2:36:55
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String ,WorkGroup> getWorkGroup() {
		Map<String ,WorkGroup> resultMap = new HashMap<String, WorkGroup>();
		WorkGroup param = new WorkGroup();
		param.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		param.setZt(1);
		List<WorkGroup> workGroups = workGroupMapper.getWorkGroupList(param);
		for (int i = 0;null != workGroups && i < workGroups.size(); i++) {
			WorkGroup workGroup = workGroups.get(i);
			if(!StringUtils.isBlank(workGroup.getGzzdm())){
				resultMap.put(workGroup.getGzzdm(), workGroup);
			}
		}
		return resultMap;
	}
}
