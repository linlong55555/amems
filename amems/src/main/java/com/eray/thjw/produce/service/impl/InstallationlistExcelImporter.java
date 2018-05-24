package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.produce.dao.InstallationListEditable2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEditableMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.InstallationList;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEditable2Init;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.PartSnValidationEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.SynchronzeStatusEnum;

/**
 * @Description 飞机装机清单导入
 * @CreateTime 2017-12-4 上午10:41:00
 * @CreateBy 雷伟
 */
@Service("installationlistexcelimporter")
public class InstallationlistExcelImporter extends AbstractExcelImporter<InstallationList>{

	protected final static String NOT_FJJD = "NP";//没有父级节点

	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private FixChapterService fixChapterService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private CommonService commonService;
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	@Resource
	private InstallationListEditableMapper installationListEditableMapper;
	@Resource
	private InstallationListEditable2InitMapper installationListEditable2InitMapper; 

	private String dprtcode = "";
	private String fjzch = "";
	private String fjjx = "";
	List<Stock> stockList = new ArrayList<Stock>(); //部件+序列号唯一校验
	SimpleDateFormat yyyyMMddformat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat yyyyMMddGformat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat yyyyMMddHHmmformat = new SimpleDateFormat("yyyyMMdd HH:mm");
    Map<String, InstallationList> levelMap = new LinkedHashMap<String, InstallationList>(); //存入层级数据,遇到1开启新区域范围
    Map<String, Integer> levelWzMap = new LinkedHashMap<String, Integer>(); //21 22  23 24 31 这几种类型的，层级为1的，只能出现0次或1次

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<InstallationList>> sheetList) throws ExcelImportException {
		//根据飞机注册号+机构代码,查找b_s2_004 装机清单-生效区,存在数据不能导入
		Integer existNum = installationListEffectiveMapper.getCountByParam(dprtcode,fjzch);
		if (existNum > 0) {
			throw new ExcelImportException("数据已存在,不能导入！");
		}
		
		//根据机构代码、飞机注册号查询d_007 飞机信息表，取得d_007.发动机数量
		int fadjNum = installationListEffectiveMapper.getCountFdjgsByParam(dprtcode,fjzch);

		// 获取所有章节号
		List<String> fixChapterList = getAllFixChapter();

		//构建件号、序列号Map
		Map<String, List<Integer>> jhxlhMap = getJhxlhMap(sheetList);//文本中,件号序列号,那个几行存在了
		
		//start: 调用岳彬彬，校验方法，分批次查询，提高查询效率
		Map<Integer, List<Stock>> groupMap = new HashMap<Integer, List<Stock>>();//用map存储,分组后的数据
		int groupNum = 1; //分批次查询
		int stepNum = 100; //每批次100条
		for(int startIndex = 0; null != stockList && startIndex < stockList.size();startIndex += stepNum){
			int endIndex = stockList.size() > (startIndex+stepNum)?(startIndex+stepNum):stockList.size();
			List<Stock> newList = stockList.subList(startIndex,endIndex);
			groupMap.put(groupNum, newList);
		    groupNum++;
		}
		
		StringBuffer tipMessage = new StringBuffer("");
		for (Iterator iter = groupMap.entrySet().iterator(); iter.hasNext();)  
		{  
			Entry entry = (Entry) iter.next();  
			List<Stock> subList = (List<Stock>) entry.getValue();  
			try {
				stockSerivce.getCount4ValidationBjAndXlh(subList, PartSnValidationEnum.INSTALL.getId());
			} catch (Exception e) {
				tipMessage.append(e.getMessage());
			}
		}
		
		if(null != tipMessage && tipMessage.toString().length() > 0){
			throw new ExcelImportException(tipMessage.toString());
		}
		//end: 调用岳彬彬，校验方法，分批次查询，提高查询效率

		
		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			//工作表对应的的装机清单数据
			List<InstallationList> datas = sheetList.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//循环校验每一行数据
			InstallationList bean;
			for (int rowIndex = 0; null != datas && rowIndex < datas.size(); rowIndex++) {
				bean = datas.get(rowIndex);
				validateData(sheetIndex,bean,rowIndex,datas,fixChapterList,jhxlhMap,fadjNum);
			}
		}
	}

	/**
	 * @Description 获取件号序列号Map
	 * @CreateTime 2017-11-30 下午2:55:17
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String, List<Integer>> getJhxlhMap(Map<Integer, List<InstallationList>> sheetList) {
		stockList.clear();
		levelWzMap.clear();
		Map<String, List<Integer>> jhxlhMap = new HashMap<String, List<Integer>>();

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			//工作表对应的的装机清单数据
			List<InstallationList> datas = sheetList.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			//循环校验每一行数据
			InstallationList bean;
			for (int rowIndex = 0; null != datas && rowIndex < datas.size(); rowIndex++) {
				bean = datas.get(rowIndex);
				if(!StringUtils.isBlank(bean.getXlh())){
					
					String jh = bean.getJh()==null?"":bean.getJh();
					if(Utils.Str.getLength(bean.getXlh()) > 50 || Utils.Str.getLength(bean.getJh()) > 50){
						continue;
					}
					
					String key = jh+"_"+bean.getXlh();

					List<Integer> list = new ArrayList<Integer>();
					if(jhxlhMap.get(key) != null){
						list = jhxlhMap.get(key);
					}
					list.add(rowIndex+3);
					jhxlhMap.put(key, list);

					Stock stock = new Stock();
					stock.setBjh(bean.getJh()==null?"":bean.getJh());
					stock.setSn(bean.getXlh());
					stock.setDprtcode(dprtcode);
					stockList.add(stock);
				}
				
				String[] wzArr = {"21","22","23","24","31"};
				
				if(bean.getCj() == 1 && !StringUtils.isBlank(bean.getWz()) && Arrays.asList(wzArr).contains(bean.getWz())){
					String key = bean.getCj()+"◎"+bean.getWz();
					Integer num = 1;
					if(levelWzMap.get(key) != null){
						num += levelWzMap.get(key);
					}
					levelWzMap.put(key, num);
				}
			}
		}

		return jhxlhMap;
	}

	/**
	 * @Description 
	 * @CreateTime 2017-11-29 下午3:43:27
	 * @CreateBy 雷伟
	 * @param sheetIndex 当前sheet
	 * @param bean 当前行数据
	 * @param rowIndex 行索引
	 * @param datas 所有行数据
	 * @param fixChapterList 章节号
	 */
	private void validateData(int sheetIndex,InstallationList bean, int rowIndex,List<InstallationList> datas,
			List<String> fixChapterList,Map<String, List<Integer>> jhxlhMap,int fadjNum) {
		//层级
		if (INTEGER_TYPE_ERROR == bean.getCj()) {
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，层级只能为正整数");
		}
		//层级是否找得到上级节点
		String fjdid = findFjdid(bean.getCj(),rowIndex,datas);
		if (NOT_FJJD.equals(fjdid)) {
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，层级找不到上级节点");
		} else {
			bean.setFjdid(fjdid);
			if(!"0".equals(fjdid)){
				bean.setWz(datas.get(rowIndex-1).getWz()); //取父级的分类
			}
		}
		//章节号：不能含有中文、不能超过20个字符
		if(!StringUtils.isBlank(bean.getZjh())){
			if(Utils.Str.isChinese(bean.getZjh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，章节号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getZjh()) > 20){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，章节号不能超过20个字符");
			}
			if(!fixChapterList.contains(bean.getZjh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，章节号不存在");
			}
		}
		//件号：不能为空、不能含有中文、不能超过50个字符
		if(StringUtils.isBlank(bean.getJh())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，件号不能为空");
		}else{
			if(Utils.Str.isChinese(bean.getJh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，件号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getJh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，件号不能超过50个字符");
			}
		}
		//序列号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getXlh())){
			if(Utils.Str.isChinese(bean.getXlh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，序列号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getXlh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，序列号不能超过50个字符");
			}
		}
		//厂家件号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getCjjh())){
			if(Utils.Str.isChinese(bean.getCjjh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，厂家件号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getCjjh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，厂家件号不能超过50个字符");
			}
		}
		//英文名称：不能超过100个字符
		if(!StringUtils.isBlank(bean.getYwmc())){
			if(Utils.Str.getLength(bean.getYwmc()) > 100){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，英文名称不能超过100个字符");
			}
		}
		//中文名称：不能超过100个字符
		if(!StringUtils.isBlank(bean.getZwmc())){
			if(Utils.Str.getLength(bean.getZwmc()) > 100){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，中文名称不能超过100个字符");
			}
		}
		//型号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getXh())){
			if(Utils.Str.isChinese(bean.getXh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，型号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getXh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，型号不能超过50个字符");
			}
		}
		//分类(位置)：11(机身)、21(1#发)、22(2#发)、23(3#发)、24(4#发)、31(APU)。如果不填写默认为11(机身)
		String[] wzArr = {"11","21","22","23","24","31"};
		if(!Arrays.asList(wzArr).contains(bean.getWz())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，分类只能为11(机身)、21(1#发)、22(2#发)、23(3#发)、24(4#发)、31(APU)");
		}else {
			if(!StringUtils.isBlank(""+bean.getCj()) && bean.getCj()==1){
				String key = bean.getCj()+"◎"+bean.getWz();
				if(levelWzMap.get(key) != null && levelWzMap.get(key).intValue() > 1){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，类型为21(1#发)、22(2#发)、23(3#发)、24(4#发)、31(APU)且层级为1的分类只能出现0次或1次");
				}
			}
		}
		
		if(fadjNum == 1){
			String[] errorArr = {"22","23","24"};
			if(Arrays.asList(errorArr).contains(bean.getWz())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，发动机数量为"+fadjNum+",分类不能为22(2#发)、23(3#发)、24(4#发)");
			}
		}else if(fadjNum == 2){
			String[] errorArr = {"23","24"};
			if(Arrays.asList(errorArr).contains(bean.getWz())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，发动机数量为"+fadjNum+",分类不能为23(3#发)、24(4#发)");
			}
		}else if(fadjNum == 3){
			String[] errorArr = {"24"};
			if(Arrays.asList(errorArr).contains(bean.getWz())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，发动机数量为"+fadjNum+",分类不能为24(4#发)");
			}
		}
		
		//批次号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getPch())){
			if(Utils.Str.isChinese(bean.getPch())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，批次号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getPch()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，批次号不能超过50个字符");
			}
		}
		//飞机站位：不能含有中文、不能超过150个字符
		if(!StringUtils.isBlank(bean.getFjzw())){
			if(Utils.Str.isChinese(bean.getFjzw())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，飞机站位不能含有中文");
			}
			if(Utils.Str.getLength(bean.getFjzw()) > 150){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，飞机站位不能超过150个字符");
			}
		}
		
		/*
		 * 如果填序列号，数量必须是1，不填数量就默认为1.
		 * 如果没填序列号，数量必填，如果没填要给提示
		 */
		//装机数量：不能含有中文、不能超过300个字符
		Integer zjsl = convertToInteger(bean.getZjsl(), 1);
		if(StringUtils.isBlank(bean.getXlh()) && StringUtils.isBlank(bean.getZjsl())){
			zjsl = null;
		}
		if(zjsl == null){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，数量不能为空");
		}else if (INTEGER_TYPE_ERROR == zjsl) {
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，数量只能为0~9999999范围内整数");
		} else {
			if (zjsl > 9999999) {
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，数量只能为0~9999999范围内整数");
			}
		}
		//计量单位
		if(StringUtils.isBlank(bean.getJldw())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，计量单位不能为空");
		}else{
			if(Utils.Str.getLength(bean.getJldw()) > 15){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，计量单位不能超过15个字符");
			}
		}
		//装机件类型
		if(!StringUtils.isBlank(bean.getZjjlx())){
			if(Utils.Str.getLength(bean.getZjjlx()) > 15){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，装机件类型不能超过15个字符");
			}
		}
		//部件改装记录
		if(!StringUtils.isBlank(bean.getBjgzjl())){
			if(Utils.Str.getLength(bean.getBjgzjl()) > 600){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，改装记录不能超过600个字符");
			}
		}
		//TSN
		if(!StringUtils.isBlank(bean.getTsn())){
			String tsn = validateTimeFormat(bean.getTsn(),sheetIndex,rowIndex,"TSN",true);
			bean.setTsn(tsn);
		}
		//TSO
		if(!StringUtils.isBlank(bean.getTso())){
			String tso = validateTimeFormat(bean.getTso(),sheetIndex,rowIndex,"TSO",true);
			bean.setTso(tso);
		}
		//CSN
		if(!StringUtils.isBlank(bean.getCsn())){
			if(!validateZsRange(bean.getCsn())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，CSN只能在0~9999999范围内");
			}
		}
		//CSO 
		if(!StringUtils.isBlank(bean.getCso())){
			if(!validateZsRange(bean.getCso())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，CSO只能在0~9999999范围内");
			}
		}
		//备注
		if(!StringUtils.isBlank(bean.getBz())){
			if(Utils.Str.getLength(bean.getBz()) > 300){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，备注不能超过300个字符");
			}
		}
		//出厂日期
		if(!StringUtils.isBlank(bean.getChucrq())){
			String yyyyMMdd = validateDateFormat(bean.getChucrq(),sheetIndex,rowIndex,"出厂日期");
			if(!"".equals(yyyyMMdd)){
				bean.setChucrq(yyyyMMdd);
			}
		}
		//安装日期
		if(StringUtils.isBlank(bean.getAzrq())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，安装日期不能为空");
		}else{
			String yyyyMMdd = validateDateFormat(bean.getAzrq(),sheetIndex,rowIndex,"安装日期");
			if(!"".equals(yyyyMMdd)){
				bean.setAzrq(yyyyMMdd);
			}
		}
		//安装时间
		if(!StringUtils.isBlank(bean.getAzsj())){
			String azsj = validateTimeFormat(bean.getAzsj(),sheetIndex,rowIndex,"安装时间",false);
			bean.setAzsj(azsj);
		}
		//安装记录单号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getAzjldh())){
			if(Utils.Str.isChinese(bean.getAzjldh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，安装记录单号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getAzjldh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，安装记录单号不能超过50个字符");
			}
		}
		//安装人：不能超过15个字符
		if(!StringUtils.isBlank(bean.getAzr())){
			if(Utils.Str.getLength(bean.getAzr()) > 15){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，安装人不能超过15个字符");
			}
		}
		//安装说明：不能超过600个字符
		if(!StringUtils.isBlank(bean.getAzbz())){
			if(Utils.Str.getLength(bean.getAzbz()) > 600){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，安装说明不能超过600个字符");
			}
		}
		//拆除日期
		if(!StringUtils.isBlank(bean.getCcrq())){
			String yyyyMMdd = validateDateFormat(bean.getCcrq(),sheetIndex,rowIndex,"拆除日期");
			if(!"".equals(yyyyMMdd)){
				bean.setCcrq(yyyyMMdd);
			}
		}else{
			if(!StringUtils.isBlank(bean.getCcjldh()) || !StringUtils.isBlank(bean.getCcr()) || !StringUtils.isBlank(bean.getCcbz())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，(填写了拆除记录单号/拆除人/拆除说明)拆除日期不能为空");
			}
		}
		//拆除时间
		if(!StringUtils.isBlank(bean.getCcsj())){
			String ccsj = validateTimeFormat(bean.getCcsj(),sheetIndex,rowIndex,"拆除时间",false);
			bean.setCcsj(ccsj);
		}
		//拆除记录单号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getCcjldh())){
			if(Utils.Str.isChinese(bean.getCcjldh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，拆除记录单号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getCcjldh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，拆除记录单号不能超过50个字符");
			}
		}
		//拆除人：不能超过15个字符
		if(!StringUtils.isBlank(bean.getCcr())){
			if(Utils.Str.getLength(bean.getCcr()) > 15){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，拆除人不能超过15个字符");
			}
		}
		//拆除说明：不能超过600个字符
		if(!StringUtils.isBlank(bean.getCcbz())){
			if(Utils.Str.getLength(bean.getCcbz()) > 600){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，拆除说明不能超过600个字符");
			}
		}
		//拆除时间（拆除日期+拆除时间）大于安装时间（安装日期+安装时间）
		if(!StringUtils.isBlank(bean.getCcrq()) && !StringUtils.isBlank(bean.getAzrq())){
			try {
				Date ccdate = yyyyMMddHHmmformat.parse(bean.getCcrq()+" "+bean.getCcsj());
				Date azdate = yyyyMMddHHmmformat.parse(bean.getAzrq()+" "+bean.getAzsj());
				if(ccdate.getTime() <= azdate.getTime()){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，拆除时间（拆除日期+拆除时间）不能小于等于 安装时间（安装日期+安装时间）");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExcelImportException("日期格式化错误");
			}
		}
		//履历卡类型1无履历卡、2原装履历卡、3自制履历卡
		String[] llklxArr = {"1","2","3"};
		if(!Arrays.asList(llklxArr).contains(bean.getLlklx())){
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，履历卡类型只能为1无履历卡、2原装履历卡、3自制履历卡");
		}
		//履历卡编号：不能含有中文、不能超过50个字符
		if(!StringUtils.isBlank(bean.getLlkbh())){
			if(Utils.Str.isChinese(bean.getLlkbh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，履历卡编号不能含有中文");
			}
			if(Utils.Str.getLength(bean.getLlkbh()) > 50){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，履历卡编号不能超过50个字符");
			}
		}
		//初始日期
		if(!StringUtils.isBlank(bean.getCsrq())){
			String yyyyMMdd = validateDateFormat(bean.getCsrq(),sheetIndex,rowIndex,"初始日期");
			if(!"".equals(yyyyMMdd)){
				bean.setCsrq(yyyyMMdd);
			}
		} else {
			if(!StringUtils.isBlank(bean.getXlh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，填写序列号，初始日期不能为空");
			}
		}
		//飞行 时间
		if(!StringUtils.isBlank(bean.getFxsj())){
			String fxsj = validateTimeFormat(bean.getFxsj(),sheetIndex,rowIndex,"飞行时间",true);
			bean.setFxsj(fxsj);
		}
		//飞行循环
		if(!StringUtils.isBlank(bean.getFxxh())){
			if(!validateZsRange(bean.getFxxh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，飞行循环只能在0~9999999范围内");
			}
		}
		//发动机时间/APU时间
		if(!StringUtils.isBlank(bean.getFdjapusj())){
			String apusj = validateTimeFormat(bean.getFdjapusj(),sheetIndex,rowIndex,"发动机时间/APU时间",true);
			bean.setFdjapusj(apusj);
		}
		//发动机循环/APU循环
		if(!StringUtils.isBlank(bean.getFdjapuxh())){
			if(!validateZsRange(bean.getFdjapuxh())){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，发动机循环/APU循环只能在0~9999999范围内");
			}
		}
		//件号序列号唯一性验证
		if(!StringUtils.isBlank(bean.getXlh())){
			String key = (bean.getJh()==null?"":bean.getJh())+"_"+bean.getXlh();
			if(jhxlhMap.get(key) != null && jhxlhMap.get(key).size() > 1){
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，件号+序列号，在本文档"+jhxlhMap.get(key).toString()+"行已存在");
			}
		}
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
		String dateFormats = "\\d{4}(\\.|-|/|)\\d{2}(\\.|-|/|)\\d{2}"; //支持的格式yyyy.MM.dd、yyyy-MM-dd、yyyy/MM/dd、yyyyMMdd、
		Pattern pattern = Pattern.compile(dateFormats);
		if (!pattern.matcher(chucrq).matches()) {
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，"+columnName+"格式错误");  
		} else {
			chucrq = chucrq.replace(".", "").replace("-", "").replace("/", "").toString();
			yyyyMMdd = chucrq;
		}
		return yyyyMMdd;
	}

	/**
	 * @Description 校验正整数范围 0~9999999
	 * @CreateTime 2017-11-30 上午11:02:55
	 * @CreateBy 雷伟
	 * @param zjsl
	 * @return
	 */
	private boolean validateZsRange(String num) {
		boolean flag = false;
		String check = "[0-9]+"; 
		Pattern regex = Pattern.compile(check); 
		Matcher matcher = regex.matcher(num); 
		if(matcher.matches()){
			try {
				if(Long.valueOf(num) >= 0 && Long.valueOf(num) <= 9999999){
					flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
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
				addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，"+columnName+"分钟数不能超过59");  
			}
			//如果最终结果不转成分钟,那么小时数必须在0~23之间
			if(!coverToMinute){
				if(hrs > 23 || hrs < 0){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，"+columnName+"小时数必须在0~23之间");  
				}
			}
			
		}else{
			addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(rowIndex+3)+"行，"+columnName+"格式错误,小时数:分钟数"); 
		}
		
		return tsn;
	}

	/**
	 * @Description 查找当前层级的父节点
	 * @CreateTime 2017-11-29 下午3:53:15
	 * @CreateBy 雷伟
	 * @param cj 当前层级
	 * @param rowIndex 当前行
	 * @param datas 所有数据
	 * @return
	 */
	private String findFjdid(Integer cj, int rowIndex,List<InstallationList> datas) {
		if (cj==1) {
			levelMap.clear();
			levelMap.put(cj+"", datas.get(rowIndex));
			return "0"; //1节点的父节点为0
		} 
		if (rowIndex-1 < 0) {
			return NOT_FJJD; //没有父节点
		}

		String fjdid = NOT_FJJD; //没有父节点
		InstallationList preBean = datas.get(rowIndex-1);
		if(cj-1==preBean.getCj()) {//上级
			fjdid = preBean.getId();
		}
		if (cj==preBean.getCj()) { //同层级
			fjdid = preBean.getFjdid();
		} 
		
		/*System.out.println(fjdid+"\t"+cj+"\t"+rowIndex);*/

		if(null==fjdid){
			fjdid = NOT_FJJD; //没有父节点
		}
		
		if(fjdid.equals(NOT_FJJD)){
			InstallationList existBean = levelMap.get(""+cj);
			if(null != existBean){
				fjdid = existBean.getFjdid();
			}
		}
		
		levelMap.put(""+cj, datas.get(rowIndex));
		
		return fjdid;
	}

	/**
	 * 保存到数据库
	 * @throws ExcelImportException 
	 */
	@Override
	public int writeToDB(Map<Integer, List<InstallationList>> sheetList) throws ExcelImportException {
		Date implDate = commonService.getSysdate();

		//删除b_s2_00101 装机清单-编辑区-部件初始化
		installationListEditableMapper.deleteRemovedComponentInitImpl(dprtcode, fjzch);
		//删除b_s2_001 装机清单-编辑区
		installationListEditableMapper.deleteRemovedComponentImpl(dprtcode, fjzch);

		//部件号:存在b3.wxxmlx=2的数据，则时控标识=1，否则为0      存在b3.wxxmlx=3的数据，则时寿标识=1，否则为0
		Set<String> skshList = getSkshList();

		List<InstallationListEditable> installEdits = new ArrayList<InstallationListEditable>();
		List<InstallationListEditable2Init> installEditInits = new ArrayList<InstallationListEditable2Init>();
		List<String> insertEditList = new ArrayList<String>();

		//循环sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < sheetList.size(); sheetIndex++) {
			//工作表对应的的装机清单数据
			List<InstallationList> datas = sheetList.get(sheetIndex);
			if(datas.isEmpty()){
				continue;
			}
			//循环校验每一行数据
			for (int rowIndex = 0; null != datas && rowIndex < datas.size(); rowIndex++) {
				InstallationList bean = datas.get(rowIndex);

				InstallationListEditable installEdit = new InstallationListEditable();
				installEdit.setId(bean.getId());
				installEdit.setDprtcode(dprtcode);
				installEdit.setJx(fjjx);
				installEdit.setFjzch(fjzch);
				installEdit.setWz(cover2Num(bean.getWz()));
				installEdit.setCj(bean.getCj());
				installEdit.setFjdid(bean.getFjdid());
				installEdit.setBjh(bean.getJh());
				installEdit.setYwmc(bean.getYwmc());
				installEdit.setZwmc(bean.getZwmc());
				installEdit.setCjjh(bean.getCjjh());
				installEdit.setXh(bean.getXh());
				installEdit.setZjh(bean.getZjh());
				installEdit.setJldw(bean.getJldw());
				installEdit.setXlh(bean.getXlh());
				installEdit.setPch(bean.getPch());
				installEdit.setZjsl(cover2Num(bean.getZjsl())==null?null:new BigDecimal(cover2Num(bean.getZjsl())));
				installEdit.setLlklx(cover2Num(bean.getLlklx()));
				installEdit.setLlkbh(bean.getLlkbh());
				installEdit.setFjzw(bean.getFjzw());
				try {
					installEdit.setChucrq(yyyyMMddformat.parse(bean.getChucrq()));
				} catch (Exception e) {
					installEdit.setChucrq(null);
				}
				if(!StringUtils.isBlank(bean.getTsn())){
					installEdit.setTsn(StringAndDate_Util.convertToMinuteStr(bean.getTsn()));
				}
				if(!StringUtils.isBlank(bean.getTso())){
					installEdit.setTso(StringAndDate_Util.convertToMinuteStr(bean.getTso()));
				}
				installEdit.setCsn(cover2Num(bean.getCsn()));
				installEdit.setCso(cover2Num(bean.getCso()));
				installEdit.setBjgzjl(bean.getBjgzjl());
				installEdit.setBz(bean.getBz());
				installEdit.setSkbs(skshList.contains(bean.getJh()+"2")?1:0);
				installEdit.setSsbs(skshList.contains(bean.getJh()+"3")?1:0);
				try {
					installEdit.setAzsj(yyyyMMddHHmmformat.parse(bean.getAzrq()+" "+bean.getAzsj()));
				} catch (Exception e) {
					installEdit.setAzsj(null);
				}
				installEdit.setAzjldh(bean.getAzjldh());
				installEdit.setAzr(bean.getAzr());
				installEdit.setAzbz(bean.getAzbz());
				try {
					installEdit.setCcsj(yyyyMMddHHmmformat.parse(bean.getCcrq()+" "+bean.getCcsj()));
				} catch (Exception e) {
					installEdit.setCcsj(null);
				}
				installEdit.setCcjldh(bean.getCcjldh());
				installEdit.setCcr(bean.getCcr());
				installEdit.setCcbz(bean.getCcbz());
				if(StringUtils.isBlank(bean.getCcrq())){
					installEdit.setZjzt(1);
				}else{
					installEdit.setZjzt(2);
				}
				installEdit.setZjjlx(bean.getZjjlx());
				installEdit.setTbbs(SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode());
				installEdit.setWhbmid(ThreadVarUtil.getUser().getBmdm());
				installEdit.setWhrid(ThreadVarUtil.getUser().getId());
				installEdit.setWhsj(implDate);
				insertEditList.add(installEdit.getId());
				installEdits.add(installEdit);

				//组建明细
				if(StringUtils.isBlank(bean.getWz()) || InstalledPositionEnum.BODY.getId().toString().equals(bean.getWz())){
					installEditInits.add(getInitDate(bean, implDate));
					installEditInits.add(getFxsj(bean, implDate));
					installEditInits.add(getFxxh(bean, implDate));
				}else if(InstalledPositionEnum.ENG1.getId().toString().equals(bean.getWz()) || 
						InstalledPositionEnum.ENG2.getId().toString().equals(bean.getWz()) || 
						InstalledPositionEnum.ENG3.getId().toString().equals(bean.getWz()) || 
						InstalledPositionEnum.ENG4.getId().toString().equals(bean.getWz())){
					installEditInits.add(getInitDate(bean, implDate));
					installEditInits.add(getFxsj(bean, implDate));
					installEditInits.add(getFxxh(bean, implDate));
					installEditInits.add(getFdjsj(bean, implDate));
					installEditInits.add(getFdjxh(bean, implDate));
				}else if(InstalledPositionEnum.APU.getId().toString().equals(bean.getWz())){
					installEditInits.add(getInitDate(bean, implDate));
					installEditInits.add(getFxsj(bean, implDate));
					installEditInits.add(getFxxh(bean, implDate));
					installEditInits.add(getApusj(bean, implDate));
					installEditInits.add(getApuxh(bean, implDate));
				}
			}
		}

		int stepNum = 300; //每300条,保存一次
		String czls = UUID.randomUUID().toString();
		
		if (installEdits.size() > 0){
			
			for(int startIndex = 0; null != installEdits && startIndex < installEdits.size();startIndex += stepNum){
				int endIndex = installEdits.size() > (startIndex+stepNum)?(startIndex+stepNum):installEdits.size();
				List<InstallationListEditable> saveList = installEdits.subList(startIndex,endIndex);
				installationListEditableMapper.insert4Batch(saveList);
			}
			
			for(int startIndex = 0; null != insertEditList && startIndex < insertEditList.size();startIndex += stepNum){
				int endIndex = insertEditList.size() > (startIndex+stepNum)?(startIndex+stepNum):insertEditList.size();
				List<String> saveList = insertEditList.subList(startIndex,endIndex);
				commonRecService.write("id",saveList, TableEnum.B_S2_001, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
			}
		}
		
		if (installEditInits.size() > 0) {
			
			for(int startIndex = 0; null != installEditInits && startIndex < installEditInits.size();startIndex += stepNum){
				int endIndex = installEditInits.size() > (startIndex+stepNum)?(startIndex+stepNum):installEditInits.size();
				List<InstallationListEditable2Init> saveList = installEditInits.subList(startIndex,endIndex);
				installationListEditable2InitMapper.insert4Batch(saveList);
			}
			
			commonRecService.write("id", TableEnum.B_S2_00101, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}

		return installEdits.size();
	}

	/**
	 * @Description APU循环
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getApuxh(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("3_20_AC");
		initDate.setJkflbh("3C");
		initDate.setCsz(bean.getFdjapuxh());
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	/**
	 * @Description APU时间
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getApusj(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("2_20_AH");
		initDate.setJkflbh("2T");
		initDate.setCsz(StringAndDate_Util.convertToMinuteStr(bean.getFdjapusj()));
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	/**
	 * @Description 发动机循环
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getFdjxh(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("3_30_EC");
		initDate.setJkflbh("3C");
		initDate.setCsz(bean.getFdjapuxh());
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	/**
	 * @Description 发动机时间
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getFdjsj(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("2_30_EH");
		initDate.setJkflbh("2T");
		initDate.setCsz(StringAndDate_Util.convertToMinuteStr(bean.getFdjapusj()));
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	/**
	 * @Description 飞行循环
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getFxxh(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("3_10_FC");
		initDate.setJkflbh("3C");
		initDate.setCsz(bean.getFxxh());
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	/**
	 * @Description 飞行时间
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getFxsj(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("2_10_FH");
		initDate.setJkflbh("2T");
		initDate.setCsz(StringAndDate_Util.convertToMinuteStr(bean.getFxsj()));
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	/**
	 * @Description 获取初始化日期
	 * @CreateTime 2017-12-1 上午10:44:18
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	private InstallationListEditable2Init getInitDate(InstallationList bean,Date implDate) {
		InstallationListEditable2Init initDate = new InstallationListEditable2Init();
		initDate.setId(UUID.randomUUID().toString());
		initDate.setMainid(bean.getId());
		initDate.setJklbh("1_10");
		initDate.setJkflbh("1D");
		try {
			initDate.setCsz(yyyyMMddGformat.format(yyyyMMddformat.parse(bean.getCsrq())));
		} catch (Exception e) {
			initDate.setCsz(null);
		}
		initDate.setWhbmid(ThreadVarUtil.getUser().getBmdm());
		initDate.setWhrid(ThreadVarUtil.getUser().getId());
		initDate.setWhsj(implDate);
		return initDate;
	}

	private Set<String> getSkshList() {
		Set<String> skshSets = new HashSet<String>();

		int stepNum = 900; //每批次900条
		for(int startIndex = 0; null != stockList && startIndex < stockList.size();startIndex += stepNum){
			int endIndex = stockList.size() > (startIndex+stepNum)?(startIndex+stepNum):stockList.size();
			List<Stock> bjhList = stockList.subList(startIndex,endIndex);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("dprtcode", dprtcode);
			param.put("fjjx", fjjx);
			param.put("bjhList", bjhList);
			param.put("fjzch", fjzch);
			List<Map<String, Object>> skshList = null;
			if(null != bjhList && bjhList.size() > 0){
				skshList = installationListEditableMapper.getSkshMapByParam(param);
			}
			
			for (int i = 0; null != skshList && i < skshList.size(); i++) {
				Map<String, Object> map = skshList.get(i);
				String key = map.get("BJH")+""+map.get("WXXMLX");
				skshSets.add(key);
			}
			
		}

		return skshSets;
	}

	/**
	 * @Description 转换成数字
	 * @CreateTime 2017-12-1 上午9:50:27
	 * @CreateBy 雷伟
	 * @param wz
	 * @return
	 */
	private Integer cover2Num(String wz) {
		Integer num = null;
		try {
			if(null != wz){
				num = Integer.parseInt(wz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 转换成对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<InstallationList>> convertBean(Map<Integer, List<Map<Integer, String>>> sheetList) throws ExcelImportException {
		dprtcode = this.getParam("dprtcode")==null?"":this.getParam("dprtcode").toString(); //组织机构码
		fjzch = this.getParam("fjzch")==null?"":this.getParam("fjzch").toString(); //飞机注册号
		fjjx = this.getParam("fjjx")==null?"":this.getParam("fjjx").toString(); //机型
		
		//结果集(按插入的顺序保存)
		Map<Integer, List<InstallationList>> resultMap = new LinkedHashMap<Integer, List<InstallationList>>();

		// 所有航材主数据
		Map<String ,HCMainData> hcMainDatas = getAllHCMainData();
		
		//循环sheet：只解析第一个sheet
		for (int sheetIndex = 0; null != sheetList && sheetIndex < 1; sheetIndex++) {
			List<InstallationList> installationList = new ArrayList<InstallationList>();
			// sheet对应装机清单数据
			List<Map<Integer, String>> rowList = sheetList.get(sheetIndex);
			//循环获取每一行数据
			Map<Integer, String> row;
			InstallationList bean;
			for (int rowIndex = 0; null != rowList && rowIndex < rowList.size(); rowIndex++) {
				row = rowList.get(rowIndex); //行数据
				bean = new InstallationList();//实体bean
				bean = convertRow2Bean(row,bean,hcMainDatas);//将行数据转换成实体bean
				installationList.add(bean);//添加到结果集中
			}
			resultMap.put(sheetIndex, installationList);
		}

		return resultMap;
	}

	/**
	 * @Description 获取所有航材主数据
	 * @CreateTime 2017-11-30 下午1:58:14
	 * @CreateBy 雷伟
	 * @return
	 */
	private Map<String, HCMainData> getAllHCMainData() {
		Map<String ,HCMainData> resultMap = new HashMap<String, HCMainData>();
		List<HCMainData> hcMainDatas = hCMainDataMapper.selectAllByDprtcode(dprtcode);
		for (int i = 0;null != hcMainDatas && i < hcMainDatas.size(); i++) {
			HCMainData hcMainData = hcMainDatas.get(i);
			resultMap.put(hcMainData.getBjh(), hcMainData);
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
	private InstallationList convertRow2Bean(Map<Integer, String> row,InstallationList bean,Map<String ,HCMainData> hcMainDatas) {
		bean.setId(UUID.randomUUID().toString()); //基础id
		bean.setCj(convertToInteger(row.get(0), 1));//层级:层级只能填写正正数。不填写时默认为1
		bean.setZjh(row.get(1));//章节号
		bean.setJh(row.get(2));//件号
		if(StringUtils.isBlank(bean.getJh())){
			bean.setJh("");
		}
		if(StringUtils.isBlank(bean.getZjh())){
			bean.setZjh(hcMainDatas.get(bean.getJh())==null?"":hcMainDatas.get(bean.getJh()).getZjh());//章节号
		}
		bean.setXlh(row.get(3));//序列号
		bean.setCjjh(row.get(4));//厂家件号
		if(StringUtils.isBlank(bean.getCjjh())){
			bean.setCjjh(hcMainDatas.get(bean.getJh())==null?"":hcMainDatas.get(bean.getJh()).getCjjh());//厂家件号
		}
		bean.setYwmc(row.get(5));//英文名称
		if(StringUtils.isBlank(bean.getYwmc())){
			bean.setYwmc(hcMainDatas.get(bean.getJh())==null?"":hcMainDatas.get(bean.getJh()).getYwms());//英文名称
		}
		bean.setZwmc(row.get(6));//中文名称
		if(StringUtils.isBlank(bean.getZwmc())){
			bean.setZwmc(hcMainDatas.get(bean.getJh())==null?"":hcMainDatas.get(bean.getJh()).getZwms());//中文名称
		}
		bean.setXh(row.get(7));//型号
		bean.setWz(row.get(8));//（分类）位置;如果不填写默认为11(机身)
		if(StringUtils.isBlank(bean.getWz())){
			bean.setWz(InstalledPositionEnum.BODY.getId().toString());
		}
		bean.setPch(row.get(9));//批次号
		bean.setFjzw(row.get(10));//飞机站位
		bean.setZjsl(row.get(11));//装机数量
		//填写了序列号的数量必须是1，如果不填写，数量默认为1
		if (!StringUtils.isBlank(bean.getXlh())) {
			bean.setZjsl("1");
		}else{
			if (StringUtils.isBlank(bean.getZjsl())) {
				bean.setZjsl("1");//装机数量
			}
		}
		bean.setJldw(row.get(12));//计量单位
		bean.setZjjlx(row.get(13));//装机件类型
		bean.setBjgzjl(row.get(14));//部件改装记录
		bean.setTsn(row.get(15));//TSN
		bean.setTso(row.get(16));//TSO
		bean.setCsn(row.get(17));//CSN
		bean.setCso(row.get(18));//CSO
		bean.setBz(row.get(19));//备注
		if(StringUtils.isBlank(bean.getBz())){
			bean.setBz(hcMainDatas.get(bean.getJh())==null?"":hcMainDatas.get(bean.getJh()).getBz());//备注
		}
		bean.setChucrq(row.get(20));//出厂日期
		bean.setAzrq(row.get(21));//安装日期
		bean.setAzsj(row.get(22));//安装时间
		if(StringUtils.isBlank(bean.getAzsj())){
			bean.setAzsj("0");//安装时间
		}
		bean.setAzjldh(row.get(23));//安装记录单号
		bean.setAzr(row.get(24));//安装人
		bean.setAzbz(row.get(25));//安装说明
		bean.setCcrq(row.get(26));//拆除日期
		bean.setCcsj(row.get(27));//拆除时间
		if(StringUtils.isBlank(bean.getCcsj())){
			bean.setCcsj("0");//拆除时间
		}
		bean.setCcjldh(row.get(28));//拆除记录单号
		bean.setCcr(row.get(29));//拆除人
		bean.setCcbz(row.get(30));//拆除说明
		bean.setLlklx(row.get(31));//履历卡类型
		if(StringUtils.isBlank(bean.getLlklx())){
			bean.setLlklx("1");//履历卡类型
		}
		bean.setLlkbh(row.get(32));//履历卡编号
		bean.setCsrq(row.get(33));//初始日期
		bean.setFxsj(row.get(34));//飞行时间
		bean.setFxxh(row.get(35));//飞行循环
		bean.setFdjapusj(row.get(36));//发动机时间/APU时间
		bean.setFdjapuxh(row.get(37));//发动机循环/APU循环
		return bean;
	}

	/**
	 * 获取所有章节号
	 * @return
	 */
	private List<String> getAllFixChapter(){
		List<String> resultList = new ArrayList<String>();
		try {
			FixChapter param = new FixChapter();
			param.setDprtcode(dprtcode);
			List<FixChapter> fixChapters = fixChapterService.selectByDprtcode(param);
			for (FixChapter fixChapter : fixChapters) {
				resultList.add(fixChapter.getZjh());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
}