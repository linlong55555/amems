package com.eray.thjw.excelimport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.DisassemblingImportMapper;
import com.eray.thjw.produce.po.DisassemblingImport;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;

/** 
 * @Description 
 * @CreateTime 2017-10-14 下午12:01:31
 * @CreateBy 孙霁	
 */
@Service("componentiohistoryExcelImporter")
public class ComponentiohistoryExcelImporter extends AbstractExcelImporter<DisassemblingImport>{

	@Resource
	private DisassemblingImportMapper disassemblingImportMapper;
	@Resource
	private CommonRecService commonRecService;
	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<DisassemblingImport>> datasMap)
			throws ExcelImportException {
		// TODO Auto-generated method stub
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的飞行记录本数据
			List<DisassemblingImport> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			DisassemblingImport disassemblingImport;
			for (int i = 0; i < datas.size(); i++) {
				disassemblingImport = datas.get(i);
				// 非空验证
				if(StringUtils.isBlank(disassemblingImport.getBjh())){
					addErrorMessage("第"+(i+3)+"行，件号不能为空");
				}
				if(StringUtils.isBlank(disassemblingImport.getFjzch())){
					addErrorMessage("第"+(i+3)+"行，飞机注册号不能为空");
				}
				// 非中文验证
				if(!StringUtils.isBlank(disassemblingImport.getBjh()) && Utils.Str.isChinese(disassemblingImport.getBjh())){
					addErrorMessage("第"+(i+3)+"行，件号不能含有中文");
				}
				if(!StringUtils.isBlank(disassemblingImport.getXlh()) && Utils.Str.isChinese(disassemblingImport.getXlh())){
					addErrorMessage("第"+(i+3)+"行，序列号不能含有中文");
				}
				if(!StringUtils.isBlank(disassemblingImport.getPch()) && Utils.Str.isChinese(disassemblingImport.getPch())){
					addErrorMessage("第"+(i+3)+"行，批次号不能含有中文");
				}
				if(!StringUtils.isBlank(disassemblingImport.getFjzch()) && Utils.Str.isChinese(disassemblingImport.getFjzch())){
					addErrorMessage("第"+(i+3)+"行，飞机注册号不能含有中文");
				}
				if(!StringUtils.isBlank(disassemblingImport.getAzjldh()) && Utils.Str.isChinese(disassemblingImport.getAzjldh())){
					addErrorMessage("第"+(i+3)+"行，安装记录单号不能含有中文");
				}
				if(!StringUtils.isBlank(disassemblingImport.getCcjldh()) && Utils.Str.isChinese(disassemblingImport.getCcjldh())){
					addErrorMessage("第"+(i+3)+"行，拆下记录单号不能含有中文");
				}
				// 长度验证
				
				if(Utils.Str.getLength(disassemblingImport.getBjh()) > 50){
					addErrorMessage("第"+(i+3)+"行，部件号最大长度为50");
				}
				if(Utils.Str.getLength(disassemblingImport.getXlh()) > 50){
					addErrorMessage("第"+(i+3)+"行，序列号最大长度为50");
				}
				if(Utils.Str.getLength(disassemblingImport.getPch()) > 50){
					addErrorMessage("第"+(i+3)+"行，批次号最大长度为50");
				}
				if(Utils.Str.getLength(disassemblingImport.getBjmc()) > 300){
					addErrorMessage("第"+(i+3)+"行，名称最大长度为300");
				}
				if(Utils.Str.getLength(disassemblingImport.getZjh()) > 100){
					addErrorMessage("第"+(i+3)+"行，章节号最大长度为100");
				}
				if(Utils.Str.getLength(disassemblingImport.getCjjh()) > 100){
					addErrorMessage("第"+(i+3)+"行，厂家件号最大长度为100");
				}
				if(Utils.Str.getLength(disassemblingImport.getFjzch()) > 20){
					addErrorMessage("第"+(i+3)+"行，飞机注册号最大长度为20");
				}
				if(Utils.Str.getLength(disassemblingImport.getAzjldh()) > 50){
					addErrorMessage("第"+(i+3)+"行，安装记录单号最大长度为50");
				}
				if(Utils.Str.getLength(disassemblingImport.getAzr()) > 100){
					addErrorMessage("第"+(i+3)+"行，装上工作者最大长度为100");
				}
				if(Utils.Str.getLength(disassemblingImport.getJldw()) > 15){
					addErrorMessage("第"+(i+3)+"行，单位最大长度为15");
				}
				if(Utils.Str.getLength(disassemblingImport.getFjzw()) > 150){
					addErrorMessage("第"+(i+3)+"行，飞机站位最大长度为150");
				}
				if(Utils.Str.getLength(disassemblingImport.getZjjlx()) > 15){
					addErrorMessage("第"+(i+3)+"行，装机件类型最大长度为15");
				}
				if(Utils.Str.getLength(disassemblingImport.getAzbz()) > 300){
					addErrorMessage("第"+(i+3)+"行，装机备注最大长度为300");
				}
				if(Utils.Str.getLength(disassemblingImport.getCcjldh()) > 50){
					addErrorMessage("第"+(i+3)+"行，拆下记录单号最大长度为50");
				}
				if(Utils.Str.getLength(disassemblingImport.getCcr()) > 100){
					addErrorMessage("第"+(i+3)+"行，拆下工作者最大长度为100");
				}
				if(Utils.Str.getLength(disassemblingImport.getCcbz()) > 300){
					addErrorMessage("第"+(i+3)+"行，拆机说明最大长度为300");
				}
				if(Utils.Str.getLength(disassemblingImport.getCjZsjj()) > 300){
					addErrorMessage("第"+(i+3)+"行，对应装上件最大长度为300");
				}
				// 装机数量验证
				if(disassemblingImport.getZjsl() != null && INTEGER_TYPE_ERROR.equals(disassemblingImport.getZjsl())){
					addErrorMessage("第"+(i+3)+"行，装机数量格式不正确");
				}
				
				if(disassemblingImport.getZjsl() != null && (disassemblingImport.getZjsl() <= 0 || disassemblingImport.getZjsl() >= 10000000)){
					addErrorMessage("第"+(i+3)+"行，装机数量必须在1~9999999之间");
				}
				//日期验证
				if(disassemblingImport.getAzsj() != null && disassemblingImport.getAzsj().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(i+3)+"行，安装日期格式不正确");
				}
				if(disassemblingImport.getCcsj() != null && disassemblingImport.getCcsj().compareTo(DATE_TYPE_ERROR) == 0){
					addErrorMessage("第"+(i+3)+"行，拆下日期格式不正确");
				}
			}
		}
	}

	@Override
	public int writeToDB(Map<Integer, List<DisassemblingImport>> datas)
			throws ExcelImportException {
		
		//获取登入user
		User user = ThreadVarUtil.getUser();
		List<DisassemblingImport> list = new ArrayList<DisassemblingImport>();
		list.addAll(datas.get(0));
		//操作指令
		LogOperationEnum cz = LogOperationEnum.IMPORT;
		//操作流水id
		String czls = UUID.randomUUID().toString();
		for (DisassemblingImport disassemblingImport : list) {
				disassemblingImport.setId(UUID.randomUUID().toString());
				disassemblingImportMapper.insertSelective(disassemblingImport);
		}
		return 0;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<DisassemblingImport>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 结果集
				Map<Integer, List<DisassemblingImport>> resultMap = new TreeMap<Integer, List<DisassemblingImport>>();
				// 循环sheet
				for (Integer sheetIndex : totalMapList.keySet()) {
					// sheet对应装机清单数据
					List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
					List<DisassemblingImport> list = new ArrayList<DisassemblingImport>();
					
					DisassemblingImport disassemblingImport;
					Map<Integer, String> bean;
					for (int i = 1; i < mapList.size(); i++) {
						bean = mapList.get(i);
						disassemblingImport = new DisassemblingImport();
						/*
						 * 读取excel值
						 */
						disassemblingImport.setBjh(bean.get(0));	// 部件号
						disassemblingImport.setXlh(bean.get(1));	// 序列号
						disassemblingImport.setPch(bean.get(2));	// 批次号
						disassemblingImport.setBjmc(bean.get(3));	// 部件名称
						disassemblingImport.setZjh(bean.get(4));	//章节号
						disassemblingImport.setCjjh(bean.get(5));	//厂家件号
						disassemblingImport.setFjzch(bean.get(6));	//飞机注册号
						disassemblingImport.setAzjldh(bean.get(7));	//安装记录单号
						disassemblingImport.setAzsj(convertToDate(bean.get(8)));	//安装日期
						disassemblingImport.setAzr(bean.get(9));	//安装人
						disassemblingImport.setZjsl(convertToInteger(bean.get(10), 1));	//装机数量
						disassemblingImport.setJldw(bean.get(11));	//单位
						disassemblingImport.setFjzw(bean.get(12));	//飞机站位
						disassemblingImport.setZjjlx(bean.get(13));	//类型
						disassemblingImport.setAzbz(bean.get(14));	//安装备注
						disassemblingImport.setCcjldh(bean.get(15));	//拆下记录单号
						disassemblingImport.setCcsj(convertToDate(bean.get(16)));	//拆下时间
						disassemblingImport.setCcr(bean.get(17));	//拆下人
						disassemblingImport.setCcbz(bean.get(18));	//拆下备注
						disassemblingImport.setCjZsjj(bean.get(19));	//对应装上件
						disassemblingImport.setWhbmid(user.getBmdm());
						disassemblingImport.setWhrid(user.getId());
						disassemblingImport.setWhsj(new Date());
						disassemblingImport.setDprtcode(user.getJgdm());
						list.add(disassemblingImport);
					}
					resultMap.put(sheetIndex, list);
				}
				return resultMap;
	}
}
