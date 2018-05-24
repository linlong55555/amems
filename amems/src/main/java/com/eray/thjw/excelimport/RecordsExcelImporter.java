package com.eray.thjw.excelimport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.DisassemblingImport;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.ThreadVar;

@Service("recordsExcelImporter")
public class RecordsExcelImporter extends AbstractExcelImporter<MaintenancePersonnel>{

@Resource
private MaintenancePersonnelMapper maintenancePersonnelMapper;	

	@Override
	public void validateParam(Map<Integer, List<MaintenancePersonnel>> datasMap)
			throws ExcelImportException {
		 User user = ThreadVarUtil.getUser();
		 List<MaintenancePersonnel> persons=maintenancePersonnelMapper.queryPersons(user.getJgdm());
		 Map<String,Integer> map=new HashMap<String, Integer>();
		 
			// 循环工作表
			for (Integer sheetIndex : datasMap.keySet()) {
				List<MaintenancePersonnel> datas = datasMap.get(sheetIndex);
				// 该sheet数据为空，则直接读取下个sheet
				if(datas.isEmpty()){
					continue;
				}
				MaintenancePersonnel maintenancePersonnel;
				for (int i = 0; i < datas.size(); i++) {
					maintenancePersonnel = datas.get(i);
					// 非空验证
					String rybh=maintenancePersonnel.getRybh();
					if(StringUtils.isBlank(rybh)){
						addErrorMessage("第"+(i+3)+"行，人员代码不能为空");
					}	
					
					if(map.containsKey(rybh)){
						map.put(rybh,map.get(rybh)+1);
					}else{
						map.put(rybh, 1);
					}
					// 非中文验证
					if(!StringUtils.isBlank(rybh) && Utils.Str.isChinese(rybh)){
						addErrorMessage("第"+(i+3)+"行，人员代码不能含有中文");
					}
					// 长度验证	
					if(Utils.Str.getLength(rybh) > 50){
						addErrorMessage("第"+(i+3)+"行，人员代码最大长度为50");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getXm()) > 100){
						addErrorMessage("第"+(i+3)+"行，姓名最大长度为100");
					}
					if(Utils.Str.getLength(maintenancePersonnel.getParamsMap().get("cj").toString()) >15){
						addErrorMessage("第"+(i+3)+"行，成绩最大长度为15");
					}	
					if(Utils.Str.getLength(maintenancePersonnel.getParamsMap().get("zs").toString()) >100){
						addErrorMessage("第"+(i+3)+"行，证书最大长度为100");
					}					
					if(Utils.Str.getLength(maintenancePersonnel.getParamsMap().get("bz").toString()) >300){
						addErrorMessage("第"+(i+3)+"行，备注最大长度为300");
					}
					
					//数字验证
					if(maintenancePersonnel.getParamsMap().get("cql")!=null&&
					Integer.valueOf(maintenancePersonnel.getParamsMap().get("cql").toString()).equals(INTEGER_TYPE_ERROR)){
						addErrorMessage("第"+(i+3)+"行，出勤率格式不正确");						
					}		
					
					if(maintenancePersonnel.getParamsMap().get("cql")!=null&&
					(Integer.valueOf(maintenancePersonnel.getParamsMap().get("cql").toString())<0||Integer.valueOf(maintenancePersonnel.getParamsMap().get("cql").toString())>100)){
								addErrorMessage("第"+(i+3)+"行，出勤率只能在0-100之间");						
					}	
					//日期验证
					if(maintenancePersonnel.getParamsMap().get("xcpxrq") != null && maintenancePersonnel.getParamsMap().get("xcpxrq")==DATE_TYPE_ERROR ){
						addErrorMessage("第"+(i+3)+"行，下次培训日期格式不正确");
					}
					
					String khjg=maintenancePersonnel.getParamsMap().get("khjg").toString();
					if(StringUtils.isNotBlank(khjg)&&!khjg.equals("0")&&!khjg.equals("1")){
						 addErrorMessage("第"+(i+3)+"行，考核结果只能为0或者1或者不填写");
					}					
					//判断人员是否建档
					boolean flag=true;
			       	for(MaintenancePersonnel person:persons){
			       		String bh=person.getRybh();
			       		String yhbh=person.getParamsMap().get("yhbh")==null?"":person.getParamsMap().get("yhbh").toString();
			       		String drzh=person.getParamsMap().get("drzh")==null?"":person.getParamsMap().get("drzh").toString();
			       		  if(StringUtils.isNotBlank(rybh)&&(rybh.equals(bh)||rybh.equals(yhbh)||rybh.equals(drzh))){
			       			maintenancePersonnel.setId(person.getId());
			       			maintenancePersonnel.setSzdw(person.getSzdw());
			       			maintenancePersonnel.setXm(person.getXm());
			       			    flag=false;
			       			    break;
			       		  }
			       	}
					
					if(flag){
						 addErrorMessage("第"+(i+3)+"行，人员代码"+rybh+"未建档");
					}				
	                 	
				}
				
				//校验人员编号重复性
				for(String str:map.keySet()){
					  if(map.get(str).intValue()!=1){
						  addErrorMessage("人员代码"+str+"重复");
					  }
					
				}
			}
		
	}

	@Override
	public int writeToDB(Map<Integer, List<MaintenancePersonnel>> datas)
			throws ExcelImportException {
		List<MaintenancePersonnel> values=datas.get(0);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		MaintenancePersonnel maintenancePersonnel=null;
		//组装数据
		   for(int i=0;i<values.size();i++){
			   Map<String,Object> map=new HashMap<String, Object>();
			   maintenancePersonnel=values.get(i);
			   map.put("ryid", maintenancePersonnel.getId());//维修人员档案id
			   map.put("rybh", maintenancePersonnel.getRybh());//维修人员档案编号
			   map.put("ryxm", maintenancePersonnel.getXm());//维修人员姓名
			   map.put("szdw", maintenancePersonnel.getSzdw());//工作单位部门
			   map.put("cql",  maintenancePersonnel.getParamsMap().get("cql"));//出勤率
			   map.put("cj",   maintenancePersonnel.getParamsMap().get("cj"));//成绩
			   map.put("khjg", maintenancePersonnel.getParamsMap().get("khjg"));//考核结果
			   map.put("zs",   maintenancePersonnel.getParamsMap().get("zs"));//证书
			   map.put("xcpxrq", maintenancePersonnel.getParamsMap().get("xcpxrq"));//下次培训日期
			   map.put("bz", maintenancePersonnel.getParamsMap().get("bz"));//备注
			      list.add(map);
		   }
		        ThreadVarUtil.set("returnDatas", list);
		                   return 0;
	}

	@Override
	public Map<Integer, List<MaintenancePersonnel>> convertBean(
			Map<Integer, List<Map<Integer, String>>> totalMapList)
			throws ExcelImportException {
		   User user=ThreadVarUtil.getUser();
		Map<Integer, List<MaintenancePersonnel>> resultMap = new TreeMap<Integer, List<MaintenancePersonnel>>();
		for(Integer sheetIndex:totalMapList.keySet()){
			List<Map<Integer, String>> mapList=totalMapList.get(sheetIndex);
			List<MaintenancePersonnel> list = new ArrayList<MaintenancePersonnel>();
			MaintenancePersonnel maintenancePersonnel;
			Map<Integer, String> bean;
			for (int i = 0; i < mapList.size(); i++) {
				bean = mapList.get(i);
				maintenancePersonnel = new MaintenancePersonnel();
				maintenancePersonnel.setParamsMap(new HashMap<String, Object>());
				maintenancePersonnel.setRybh(StringUtils.isBlank(bean.get(0))?"":bean.get(0).trim());	// 人员代码
				maintenancePersonnel.setXm(StringUtils.isBlank(bean.get(1))?"":bean.get(1).trim());	// 姓名
				maintenancePersonnel.getParamsMap().put("cql",convertToInteger(bean.get(2)));// 出勤率
				maintenancePersonnel.getParamsMap().put("cj", StringUtils.isBlank(bean.get(3))?"":bean.get(3).trim());//成绩
				maintenancePersonnel.getParamsMap().put("khjg", StringUtils.isBlank(bean.get(4))?"1":bean.get(4).trim());//考核结果
				maintenancePersonnel.getParamsMap().put("zs",StringUtils.isBlank(bean.get(5))?"":bean.get(5).trim());//证书
				maintenancePersonnel.getParamsMap().put("xcpxrq", convertToDate(bean.get(6)));	//下次培训日期
				maintenancePersonnel.getParamsMap().put("bz",StringUtils.isBlank(bean.get(7))?"":bean.get(7).trim());//备注
				list.add(maintenancePersonnel);
			}
			resultMap.put(sheetIndex, list);
		}

		       return resultMap;
	}

}
