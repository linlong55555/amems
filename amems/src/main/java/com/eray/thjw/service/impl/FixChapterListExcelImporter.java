package com.eray.thjw.service.impl;

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

import com.eray.thjw.dao.FixChapterMapper;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("fixchapterlistexcelimporter")
public class FixChapterListExcelImporter extends AbstractExcelImporter<FixChapter>{
	
	@Resource
	private CommonRecService commonRecService;

	@Resource
	private FixChapterMapper fixChapterMapper;

	@Override
	public void validateParam(Map<Integer, List<FixChapter>> datasMap)
			throws ExcelImportException {
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<FixChapter> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			FixChapter ll;
			  String temp = "";
			for (int i = 0; i < datas.size(); i++) {
				ll = datas.get(i);
				
				temp = ll.getZjh();
			   
				if(StringUtils.isBlank(ll.getZjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，章节号不能为空");
				}
				if(StringUtils.isBlank(ll.getYwms())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，英文名称不能为空");
				}
				// 非中文验证
				if(!StringUtils.isBlank(ll.getZjh()) && Utils.Str.isChinese(ll.getZjh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，章节号不能含有中文");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getZjh()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，章节号的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getYwms()) > 200){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，英文名称的最大长度为200");
				}
				if(Utils.Str.getLength(ll.getZwms()) > 200){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，中文名称的最大长度为200");
				}
				
				if (temp != null) {
					for (int j = i + 1; j < datas.size(); j++) {
						if (temp.equals(datas.get(j).getZjh())) {
							addErrorMessage("第" + (sheetIndex + 1) + "个工作表，第"
									+ (i + 3) +","+(j+3)+"行，章节号重复");
						}
					}

				}
			
			}
		}
	}

	@Override
	public int writeToDB(Map<Integer, List<FixChapter>> datasMap)
			throws ExcelImportException {
		// 汇总所有sheet的装机清单数据
		List<FixChapter> datas = new ArrayList<FixChapter>();
		for (Integer sheetIndex : datasMap.keySet()) {
			datas.addAll(datasMap.get(sheetIndex));
		}
		// 批量插入ATA章节号数据
		
		
		int count = 0;
		List<FixChapter> temp = new ArrayList<FixChapter>();
		for (int i = 0; i < datas.size(); i++) {
			temp.add(datas.get(i));
			if(temp.size() >= 500 || i == datas.size() - 1){
				count += fixChapterMapper.batchMerge(temp);
				temp.clear();
			}
		}
		
		// 写入日志
		String czls = UUID.randomUUID().toString();
		List<String> insertList = new ArrayList<String>();
		List<String> updateList = new ArrayList<String>();
		
		for (FixChapter ll : datas) {
			if("1".equals(String.valueOf(ll.getParamsMap().get("isNew")))){
				insertList.add(ll.getZjh());
			}else {
				updateList.add(ll.getZjh());
			}
		}
		
		if(!insertList.isEmpty()){
			commonRecService.write("zjh", insertList, TableEnum.D_005, ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"zjh");
		}
		if(!updateList.isEmpty()){
			commonRecService.write("zjh", updateList, TableEnum.D_005, ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"zjh");
		}
		return count;
	}

	/**
	 * 获取该组织机构下的所有zjh
	 * @param list
	 * @return
	 */
	private Map<String, FixChapter> getzjhMap(String dprtcode){
		
		FixChapter param = new FixChapter();
		param.setDprtcode(dprtcode);
		param.setZt(1);
		List<FixChapter> dbList = fixChapterMapper.selectFixChapterList(param);
		
		Map<String, FixChapter> resultMap = new HashMap<String, FixChapter>();
		
		for (FixChapter ll : dbList) {
			if(!StringUtils.isBlank(ll.getZjh())){
				resultMap.put(ll.getZjh(), ll);
			}
		}
		return resultMap;
	}
	
	@Override
	public Map<Integer, List<FixChapter>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		// 结果集
		Map<Integer, List<FixChapter>> resultMap = new TreeMap<Integer, List<FixChapter>>();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			
			Map<String, FixChapter> zjhlist = getzjhMap(ThreadVarUtil.getUser().getJgdm());
			
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			List<FixChapter> list = new ArrayList<FixChapter>();
			FixChapter ll;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				ll = new FixChapter();
				/*
				 * 读取excel值
				 */
				ll.setZjh(bean.get(0));	// 章节号
				ll.setYwms(bean.get(1));	// 英文名称
				ll.setZwms(bean.get(2));	// 中文名称
				ll.setZt(1);	// 装上
				
				if(StringUtils.isBlank(ll.getZjh())  && zjhlist.containsKey(ll.getZjh())){
					ll.getParamsMap().put("isNew", "1");
				}
				
				ll.setDprtcode(ThreadVarUtil.getUser().getJgdm());
				ll.setCjrid(ThreadVarUtil.getUser().getId());
				if(ThreadVarUtil.getUser().getBmdm()==null){
					ll.setBmid("");
				}else{
					ll.setBmid(ThreadVarUtil.getUser().getBmdm());
				}
				ll.setCjsj( new Date());
				list.add(ll);
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}

}
