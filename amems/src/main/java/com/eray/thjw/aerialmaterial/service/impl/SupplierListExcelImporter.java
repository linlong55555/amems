package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.google.common.base.Supplier;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("supplierlistexcelimporter")
public class SupplierListExcelImporter/* extends AbstractExcelImporter<Supplier>*/{
	
	/*@Resource
	private CommonRecService commonRecService;

	@Resource
	private SupplierMapper supplierMapper;

	@Override
	public void validateParam(Map<Integer, List<Supplier>> datasMap)
			throws ExcelImportException {
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<Supplier> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			Supplier ll;
			String temp = "";
			for (int i = 0; i < datas.size(); i++) {
				ll = datas.get(i);
				
				temp = ll.getGysbm();
			   for (int j = i + 1; j < datas.size(); j++)
	            {
				   if (temp.equals(datas.get(j).getGysbm()))
	                {
					   addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商编号重复");
	                }
	            }
				
				if(StringUtils.isBlank(ll.getGysbm())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商编号不能为空");
				}
				if(StringUtils.isBlank(ll.getGysmc())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商名称不能为空");
				}
				if(StringUtils.isBlank(ll.getLxr())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，联系人不能为空");
				}
				if(StringUtils.isBlank(ll.getGyslb().toString())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商类型不能为空");
				}
				if(StringUtils.isBlank(ll.getHzzk().toString())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商核准状况不能为空");
				}
				if(StringUtils.isBlank(ll.getGysdj())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商等级不能为空");
				}
				if(!StringUtils.isBlank(ll.getHzzk().toString()) &&ll.getHzzk()!=1 ){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商核准状况只能为1");
				}
				if(!StringUtils.isBlank(ll.getGyslb().toString()) &&ll.getGyslb() != 1 && ll.getGyslb() != 2){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商类型只能为1、2");
				}
				if(!StringUtils.isBlank(ll.getGysdj()) &&!ll.getGysdj().equals("1") &&!ll.getGysdj().equals("2")&&!ll.getGysdj().equals("3")){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商等级只能为1、2、3");
				}
				// 非中文验证
				if(!StringUtils.isBlank(ll.getGysbm()) && Utils.Str.isChinese(ll.getGysbm())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商编号不能含有中文");
				}
				if(!StringUtils.isBlank(ll.getLxdh()) && Utils.Str.isChinese(ll.getLxdh())){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，联系电话不能含有中文");
				}
				// 长度验证
				if(Utils.Str.getLength(ll.getGysbm()) > 50){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商编号的最大长度为50");
				}
				if(Utils.Str.getLength(ll.getGysmc()) > 300){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商名称的最大长度为300");
				}
				if(Utils.Str.getLength(ll.getLxr()) > 30){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，联系人的最大长度为30");
				}
				if(Utils.Str.getLength(ll.getLxdh()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，联系电话的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getDz()) > 100){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，地址的最大长度为100");
				}
				if(Utils.Str.getLength(ll.getGysdj()) > 20){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，供应商等级的最大长度为20");
				}
				if(Utils.Str.getLength(ll.getBz()) > 300){
					addErrorMessage("第"+(sheetIndex+1)+"个工作表，第"+(i+3)+"行，备注的最大长度为300");
				}
				
			}
		}
	}


	@Override
	public int writeToDB(Map<Integer, List<Supplier>> datasMap)
			throws ExcelImportException {
		// 汇总所有sheet的装机清单数据
		List<Supplier> datas = new ArrayList<Supplier>();
		for (Integer sheetIndex : datasMap.keySet()) {
			datas.addAll(datasMap.get(sheetIndex));
		}
		int count = 0;
		List<Supplier> temp = new ArrayList<Supplier>();
		for (int i = 0; i < datas.size(); i++) {
			temp.add(datas.get(i));
			if(temp.size() >= 500 || i == datas.size() - 1){
				count += supplierMapper.batchMerge(temp);
				temp.clear();
			}
		}
		
		// 写入日志
		String czls = UUID.randomUUID().toString();
		List<String> insertList = new ArrayList<String>();
		List<String> updateList = new ArrayList<String>();
		for (Supplier ll : datas) {
			if("1".equals(String.valueOf(ll.getParamsMap().get("isNew")))){
				insertList.add(ll.getId());
			}else {
				updateList.add(ll.getId());
			}
		}
		if(!insertList.isEmpty()){
			commonRecService.write("id", insertList, TableEnum.D_010, ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE,"id");
		}
		if(!updateList.isEmpty()){
			commonRecService.write("id", updateList, TableEnum.D_010, ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE,"id");
		}
		return count;
	}


	@Override
	public Map<Integer, List<Supplier>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		// 结果集
		Map<Integer, List<Supplier>> resultMap = new TreeMap<Integer, List<Supplier>>();
		
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应装机清单数据
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			//查询所有供应商
			//Map<String, Supplier> gyslist = getgysMap(ThreadVarUtil.getUser().getJgdm());
			List<Supplier> list = new ArrayList<Supplier>();
			Supplier ll;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				ll = new Supplier();
				
				 * 读取excel值
				 
				String uuid = UUID.randomUUID().toString();
				ll.setId(uuid);
				ll.setGysbm(bean.get(0));	// 供应商编号
				ll.setGysmc(bean.get(1));	// 供应商名称
				ll.setLxr(bean.get(2));	// 	联系人
				ll.setLxdh(bean.get(3));	// 联系电话
				ll.setDz(bean.get(4));	// 地址
				if(StringUtils.isBlank(bean.get(5))){
					ll.setGyslb(1);
				}else{
					ll.setGyslb(Integer.valueOf(bean.get(5)));	// 供应商类别
				}
				ll.setHzzk(Integer.valueOf(bean.get(6)));
				ll.setGysdj(bean.get(7));	// 供应商等级
				ll.setBz(bean.get(8));	// 备注
				ll.setZt(1);	// 有效
				ll.setDprtcode(ThreadVarUtil.getUser().getJgdm());//机构代码
				ll.setCjrid(ThreadVarUtil.getUser().getId());//创建人
				if(ThreadVarUtil.getUser().getBmdm()==null){
					ll.setBmid("");//部门
				}else{
					ll.setBmid(ThreadVarUtil.getUser().getBmdm());//部门
				}
				ll.setCjsj( new Date());//创建时间
				
				list.add(ll);
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}
*/
}
