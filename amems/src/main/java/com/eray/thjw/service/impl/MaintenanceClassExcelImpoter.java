package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.dao.MaintenanceClassMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.project2.po.MaintenanceClass;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("maintenanceclassExcelimporter")
public class MaintenanceClassExcelImpoter extends AbstractExcelImporter<MaintenanceClass>{
	
@Resource
private ActTypeMapper actTypeMapper;	


@Resource
private MaintenanceClassMapper maintenanceClassMapper;

@Resource
private CommonRecService commonRecService;

	@Override
	public void validateParam(Map<Integer, List<MaintenanceClass>> datas)
			throws ExcelImportException {
		List<MaintenanceClass> list=null;	
		//获取所有机型
	    List<String> jxList=getJxByDprtcode(this.getParam("dprtcode").toString());
	    if(datas.size()==0){
			addErrorMessage("导入数据模板内容为空");
	    }else{	
	    	//校验大类编号在文本的唯一性
	    	  validatUnique(datas);
	    	//数据格式的基础验证
	    	for(Integer sheet:datas.keySet()){
	    		list=datas.get(sheet);
	    		if(list.size()==0)continue;
	    		String dlbh=null;
	    		String jx=null;
	    		String ywms=null;
	    		String zwms=null;
	    		for(int i=0;i<list.size();i++){
	    			dlbh=list.get(i).getDlbh();
	    			jx=list.get(i).getJx();
	    			ywms=list.get(i).getDlywms();
	    			zwms=list.get(i).getDlzwms();    
	    			if(StringUtils.isBlank(dlbh))
	  				  addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，维修项目大类编号不能为空");
	    				
	  				if(!StringUtils.isBlank(dlbh) && Utils.Str.isChinese(dlbh))
	  					addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，维修项目大类编号不能含有中文");	  				
	  				   
	  				if(Utils.Str.getLength(dlbh) > 50)
	  					addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，维修项目大类编号最大长度为50");
	  				 
	  				if(StringUtils.isBlank(jx))
		  			  addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，机型不能为空");
	  				
	  				if(!StringUtils.isBlank(jx) && Utils.Str.isChinese(jx))
	  				  addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，机型不能含有中文");	
	  				
	  				if(Utils.Str.getLength(jx) > 50)
	  				  addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，机型最大长度为50");
                   
	  				 //校验机型是否存在
                    if(!jxList.contains(jx)&&!StringUtils.isBlank(jx)){
    	  			  addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，机型在系统中不存在");   
                    }
                    
                    if(Utils.Str.getLength(zwms) > 100){
                    	addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，中文描述最大长度为100");
                    }
                    
                    if(Utils.Str.getLength(ywms) > 100){
                    	addErrorMessage("第"+(sheet+1)+"个工作表，第"+(i+3)+"行，英文描述最大长度为100");
                    }
	    			 
	    		}
	    	}
	    }
		
	    
	     
		
	}
   //校验大类编号唯一性
	private void validatUnique(Map<Integer, List<MaintenanceClass>> datas) {
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		List<MaintenanceClass> ds = null;
		List<Integer> li = null;
		String dlbh = null;
		for (Integer sheet : datas.keySet()) {
			ds = datas.get(sheet);
			for (int i = 0; i < ds.size(); i++) {
				dlbh = ds.get(i).getDlbh();
				if (map.containsKey(dlbh)) {
					li = map.get(dlbh);
					li.add(i + 3);
					map.put(dlbh, li);
				} else {
					li = new ArrayList<Integer>();
					li.add(i + 3);
					map.put(dlbh, li);
				}
			}

			StringBuilder builder = new StringBuilder("");
			for (String key : map.keySet()) {
				li = map.get(key);
				if (li.size() == 1)
					continue;
				for (Integer num : li) {
					builder.append(num).append(",");
				}
				addErrorMessage("第"
						+ (sheet + 1)
						+ "个工作表，第"
						+ builder.toString().substring(0,
								builder.toString().length() - 1)
						+ "行，维修项目大类编号重复");
				builder = new StringBuilder("");
			}

		}

	}



	@Override
	public int writeToDB(Map<Integer, List<MaintenanceClass>> datas)
			throws ExcelImportException {
		String dprtcode = this.getParam("dprtcode").toString();
		
		String czls = UUID.randomUUID().toString();
		
		List<String> adds = new ArrayList<String>();
		
		List<String> updates = new ArrayList<String>();
		
		MaintenanceClass main = null;

		// 获取该机构下所有的维修方案大类
		List<MaintenanceClass> classes = maintenanceClassMapper
				.queryByDprtcode(dprtcode);

		List<MaintenanceClass> list = datas.get(0);

		// 获取该机构下的最大项次
		int maxCount = maintenanceClassMapper.findMaxXc(dprtcode);

		// 给list集合中的每个对象项次
		giveXcToObject(list, maxCount);

		// 数据批量写入数据库
		maintenanceClassMapper.batchMerge(list);

		for (int i = 0; i < classes.size(); i++) {
			main = classes.get(i);
			for (MaintenanceClass clz : list) {
				if (main.getDlbh().equals(clz.getDlbh())
						&& main.getJx().equals(clz.getJx()))
					updates.add(clz.getId());
				else
					adds.add(clz.getId());
			}

		}

		// 写日志(insert)
		if (!adds.isEmpty()) {
			commonRecService.write("id", adds, TableEnum.B_G2_01101,
					ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
		}
		// 写日志(update)
		if (!updates.isEmpty()) {
			commonRecService.write("id", updates, TableEnum.B_G2_01101,
					ThreadVarUtil.getUser().getId(), czls,
					LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
		}

		return 0;
	}

	private void giveXcToObject(List<MaintenanceClass> list,int count) {
		   for(MaintenanceClass maintenanceClass:list){
			   maintenanceClass.setXc(Integer.valueOf(++count));			   
		   }
		
	}
	@Override
	public Map<Integer, List<MaintenanceClass>> convertBean(
			Map<Integer, List<Map<Integer, String>>> mapList)
			throws ExcelImportException {
		User user = ThreadVarUtil.getUser();
		MaintenanceClass maintenanceClass = null;
		List<MaintenanceClass> list = new ArrayList<MaintenanceClass>();
		Map<Integer, List<MaintenanceClass>> map = new HashMap<Integer, List<MaintenanceClass>>();
		if (mapList == null)
			return null;
		// 把mapList数据前后空格去除
		removeSpace(mapList);
		List<Map<Integer, String>> ls = mapList.get(0);
		for (Map<Integer, String> mp : ls) {
			maintenanceClass = new MaintenanceClass();
			maintenanceClass.setId(UUID.randomUUID().toString());
			// 维修项目大类编号
			maintenanceClass.setDlbh(mp.get(0));
			// 机型
			maintenanceClass.setJx(mp.get(1));
			// 中文描述
			maintenanceClass.setDlzwms(mp.get(2));
			// 英文描述
			maintenanceClass.setDlywms(mp.get(3));
			// 状态
			maintenanceClass.setZt(1);
			// 维护单位
			maintenanceClass.setWhdwid(user.getBmdm());
			// 维护人id
			maintenanceClass.setWhrid(user.getId());
			// 维护时间
			maintenanceClass.setWhsj(new Date());
			//部门机构
			maintenanceClass.setDprtcode(this.getParam("dprtcode").toString());
			
			list.add(maintenanceClass);
		}
		map.put(Integer.valueOf(0), list);
		return map;

	}
	
    //去除空格
	private void removeSpace(Map<Integer, List<Map<Integer, String>>> mapList) {
		List<Map<Integer, String>> list = null;
		Map<Integer, String> map = null;
		for (Integer key : mapList.keySet()) {
			list = mapList.get(key);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					map = list.get(i);
					if (map != null) {
						for (Integer no : map.keySet()) {
							String value = map.get(no);
							if (!StringUtils.isEmpty(value)) {
								map.put(no, value.trim());
							}
						}

					}

				}
			}
		}
		
	}	
	
	/**
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
