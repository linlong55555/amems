package com.eray.thjw.training.service.impl;

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

import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.DictItemMapper;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
/**
 * 
 * @Description 课程信息导入
 * @CreateTime 2017年12月1日 上午9:33:53
 * @CreateBy 岳彬彬
 */
@Service("courseexcelimporter")
public class Courseexcelimporter extends AbstractExcelImporter<Course>{
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
    private CourseMapper courseMapper;
	
	@Resource
	private DictItemMapper dictItemMapper;
	
	@Resource
	private ActTypeMapper actTypeMapper;

	/**
	 * 参数验证
	 * @throws ExcelImportException 
	 */
	@Override
	public void validateParam(Map<Integer, List<Course>> datasMap) throws ExcelImportException {
		      User user= ThreadVarUtil.getUser();
		// 循环工作表
		for (Integer sheetIndex : datasMap.keySet()) {
			// 工作表对应的的装机清单数据
			List<Course> datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			// sheetName
			String sheetName = String.valueOf(datas.get(0).getParamsMap().get("sheetName"));
			Course course;
			//考试形式
			List<String> ksxsList = getAllCourseDic("33");
			//培训形式
			List<String> pxxsList = getAllCourseDic("32");
			//课程类别
			List<String> kclbList = getAllCourseDic("31");
			//课程代码
			List<String> kcbhList = new ArrayList<String>() ;
			List<String> jxList=getJxByDprtcode(user.getJgdm());
			if(sheetName.equals("课程信息")){
				for (int i = 0; i < datas.size(); i++) {
					course = datas.get(i);
					
					// 非空验证
					if(StringUtils.isBlank(course.getKcbh())){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程代码不能为空");
					}
					if(StringUtils.isBlank(course.getKcmc())){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程名称不能为空");
					}
					if(StringUtils.isBlank(course.getKclb())){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程类别不能为空");
					}
					if(StringUtils.isBlank(course.getKcnr())){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程内容不能为空");
					}
					if(StringUtils.isBlank(course.getPxmb())){
						addErrorMessage("课程信息，第"+(i+3)+"行，培训目标不能为空");
					}
					if(StringUtils.isBlank(course.getKs())){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训学时不能为空");
					}
					if(StringUtils.isBlank(course.getPxxs())){
						addErrorMessage("课程信息，第"+(i+3)+"行，培训形式不能为空");
					}
					if(course.getIsFx() !=0 && course.getIsFx() !=1 ){
						addErrorMessage("课程信息，第"+(i+3)+"行，是否复训请填写0或1或不填写");
					}
					if(course.getIsFx() == 1){
						if(StringUtils.isBlank(course.getFxks())){
							addErrorMessage("课程信息，第"+(i+3)+"行，是复训的情况下，复训课时不能为空");
						}
						if(StringUtils.isBlank(course.getFxpxxs())){
							addErrorMessage("课程信息，第"+(i+3)+"行，是复训的情况下，复训形式不能为空");
						}
						if(StringUtils.isBlank(course.getFxksxs())){
							addErrorMessage("课程信息，第"+(i+3)+"行，是复训的情况下，复训考试形式不能为空");
						}
						if(null == course.getZqz()){
							addErrorMessage("课程信息，第"+(i+3)+"行，是复训的情况下，复训间隔不能为空");
						}
						if(!INTEGER_TYPE_ERROR.equals(course.getZqdw()) && course.getZqdw() != 1 && course.getZqdw() != 2 && course.getZqdw() != 3){
							addErrorMessage("课程信息，第"+(i+3)+"行，间隔单位请填写1或2或3或不填写");
						}
						if(Utils.Str.getLength(course.getFxks()) > 10){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训学时的最大长度为10");
						}
						if(Utils.Str.getLength(course.getFxpxxs()) > 15){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训形式的最大长度为15");
						}
						if(Utils.Str.getLength(course.getFxksxs()) > 15){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训学时的最大长度为15");
						}
						if(null != course.getZqz() && !INTEGER_TYPE_ERROR.equals(course.getZqz()) && course.getZqz() > 99999999){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训间隔的最大为99999999");
						}
						if(!pxxsList.contains(course.getFxpxxs())){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训形式在数据字典中不存在");
						}
						if(!ksxsList.contains(course.getFxksxs())){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训考试形式在数据字典中不存在");
						}
						if(!Utils.Str.isDecimal(course.getFxks(),2)){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训学时格式不对");
						}
						if(null != course.getIsFx() && INTEGER_TYPE_ERROR.equals(course.getIsFx())){
							addErrorMessage("课程信息，第"+(i+3)+"行，是否复训格式不对");
						}
						if(null != course.getZqz() && INTEGER_TYPE_ERROR.equals(course.getZqz())){
							addErrorMessage("课程信息，第"+(i+3)+"行，复训间隔格式不对");
						}
						if(null != course.getZqdw() && INTEGER_TYPE_ERROR.equals(course.getZqdw())){
							addErrorMessage("课程信息，第"+(i+3)+"行，间隔单位格式不对");
						}
					}
					//中文验证
					if(!StringUtils.isBlank(course.getKcbh()) && Utils.Str.isChinese(course.getKcbh())){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程代码不能含有中文");
					}
					if(!StringUtils.isBlank(course.getFjjx()) && Utils.Str.isChinese(course.getFjjx())){
						addErrorMessage("课程信息，第"+(i+3)+"行，机型不能含有中文");
					}
					if(!StringUtils.isBlank(course.getFjjx())&&!jxList.contains(course.getFjjx())){
						addErrorMessage("课程信息，第"+(i+3)+"行，机型不存在");
					}
//					if (!StringUtils.isBlank(course.getFjjx())
//							&& !StringUtils
//									.isBlank(courseMapper.selectByCodeAndJx(
//											user.getJgdm(), course.getFjjx(),
//											course.getKcbh()))) {
//						addErrorMessage("课程信息，第"+(i+3)+"行，机型和课程代码在系统中已存在!");
//					}

					// 长度验证
					if(Utils.Str.getLength(course.getKcbh()) > 50){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程代码的最大长度为50");
					}
					if(Utils.Str.getLength(course.getKcmc()) > 100){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程名称的最大长度为100");
					}
					if(Utils.Str.getLength(course.getKclb()) > 15){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程类别的最大长度为15");
					}
					if(Utils.Str.getLength(course.getKcnr()) > 1000){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程内容的最大长度为1000");
					}
					if(Utils.Str.getLength(course.getPxmb()) > 600){
						addErrorMessage("课程信息，第"+(i+3)+"行，培训目标的最大长度为600");
					}
					if(Utils.Str.getLength(course.getFjjx()) > 50){
						addErrorMessage("课程信息，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(course.getKs()) > 10){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训学时的最大长度为10");
					}				
					if(Utils.Str.getLength(course.getPxxs()) > 15){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训形式的最大长度为15");
					}
					if(Utils.Str.getLength(course.getKsxs()) > 15){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训考试形式的最大长度为15");
					}
					if(Utils.Str.getLength(course.getBz())>300){
						addErrorMessage("课程信息，第"+(i+3)+"行，备注的最大长度为300");
					}
					if(!kclbList.contains(course.getKclb())){
						addErrorMessage("课程信息，第"+(i+3)+"行，课程类别在数据字典中不存在");
					}
					if(!pxxsList.contains(course.getPxxs())){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训形式在数据字典中不存在");
					}
					if(!ksxsList.contains(course.getKsxs())){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训考试形式在数据字典中不存在");
					}
					if(!Utils.Str.isDecimal(course.getKs(),2)){
						addErrorMessage("课程信息，第"+(i+3)+"行，初训学时格式不对");
					}
					if(kcbhList.size() > 0) {
						if(kcbhList.contains(course.getKcbh()+"&"+course.getFjjx())) {
							addErrorMessage("课程信息，第" + (i + 3) + "行，课程代码和机型重复");
						}else {
							kcbhList.add(course.getKcbh()+"&"+course.getFjjx());
						}
					}else{
						kcbhList.add(course.getKcbh()+"&"+course.getFjjx());
					}
				}
			}
		}
	}
	
	@Override
	public int writeToDB(Map<Integer, List<Course>> datasMap) {
		// 批量插入装机清单数据
		int count = 0;
		// 汇总所有sheet的装机清单数据
		List<Course> datas = new ArrayList<Course>();
		for (Integer sheetIndex : datasMap.keySet()) {
			datas = datasMap.get(sheetIndex);
			// 该sheet数据为空，则直接读取下个sheet
			if(datas.isEmpty()){
				continue;
			}
			// sheetName
			String sheetName = String.valueOf(datas.get(0).getParamsMap().get("sheetName"));
			if(sheetName.equals("课程信息")){
				// 获取所有课程信息
				Map<String, Course> courses = getAllCourse();
				// 写入日志
				String czls = UUID.randomUUID().toString();
				List<Course> insertList = new ArrayList<Course>();
				List<Course> updateList = new ArrayList<Course>();
				for (Course course : datas) {//sheet数据
					if(courses.containsKey(course.getKcbh()+"&"+course.getFjjx())){
						course.setId(courses.get(course.getKcbh()+"&"+course.getFjjx()).getId());
						updateList.add(course);
					}else{
						course.setId(UUID.randomUUID().toString());
						insertList.add(course);
					}
				}
				// 批量新增课程信息
				count += batchInsert(insertList);
				// 批量修改课程信息
				count += batchUpdate(updateList);
				if(!insertList.isEmpty()){
					commonRecService.write("id", getIds(insertList), TableEnum.B_P_003, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.IMPORT, UpdateTypeEnum.SAVE, "");
				}
				
				if(!updateList.isEmpty()){
					commonRecService.write("id", getIds(updateList), TableEnum.B_P_003, ThreadVarUtil.getUser().getId(), czls,
							LogOperationEnum.IMPORT, UpdateTypeEnum.UPDATE, "");
				}
			}
		}
		return count;
	}
	
	/**
	 * 获取对象id集合
	 * @param list
	 * @return
	 */
	private List<String> getIds(List<Course> list){
		List<String> resultList = new ArrayList<String>();
		for (Course course : list) {
			resultList.add(course.getId());
		}
		return resultList;
	}
	
	/**
	 * 批量新增课程信息
	 * @param insertList
	 * @return
	 */
	private int batchInsert(List<Course> insertList){
		int count = 0;
		if (insertList.isEmpty())
			return count;
		int num = 0;
		List<Course> temp = new ArrayList<Course>();
		for (int i = 0; i < insertList.size(); i++) {
			temp.add(insertList.get(i));
			num++;
			if (num > 499) {
				count += courseMapper.batchInsert(temp);
				temp.clear();
				num = 0;
			}
		}
		count += courseMapper.batchInsert(temp);
		return count;
	}
	
	/**
	 * 批量修改课程信息
	 * @param updateList
	 * @return
	 */
	private int batchUpdate(List<Course> updateList){
		int count = 0;
		if (updateList.isEmpty())
			return count;
		int num = 0;
		List<Course> temp = new ArrayList<Course>();
		for (int i = 0; i < updateList.size(); i++) {
			temp.add(updateList.get(i));
			num++;
			if (num > 499) {
				count += courseMapper.batchUpdate(temp);
				temp.clear();
				num = 0;
			}
		}
		count += courseMapper.batchUpdate(temp);
              return count;
	}

	/**
	 * 转化为对应实体类
	 * @throws ExcelImportException 
	 */
	@Override
	public Map<Integer, List<Course>> convertBean(Map<Integer, List<Map<Integer, String>>> totalMapList) throws ExcelImportException {
		// 结果集
		Map<Integer, List<Course>> resultMap = new TreeMap<Integer, List<Course>>();
		User user = ThreadVarUtil.getUser();
		// 循环sheet
		for (Integer sheetIndex : totalMapList.keySet()) {
			// sheet对应装机清单数据
			List<Map<Integer, String>> mapList = totalMapList.get(sheetIndex);
			if(mapList.isEmpty()){
				continue;
			}
			// sheetName
			String sheetName = mapList.get(0).get(-1);
			List<Course> list = new ArrayList<Course>();
			if(sheetName.equals("课程信息")){
				Course course;
				Map<Integer, String> bean;
				list.clear();
				for (int i = 0; i < mapList.size(); i++) {
					bean = mapList.get(i);
					course = new Course();
					/*
					 * 读取excel值
					 */
					course.setKcbh(bean.get(0));	// 课程编号
					course.setKcmc(bean.get(1));	// 课程名称
					course.setKclb(bean.get(2));	// 课程类别
					course.setKcnr(bean.get(3));	// 课程内容
					course.setPxmb(bean.get(4));	// 培训目标
					course.setFjjx(bean.get(5));   //机型
					course.setKs(bean.get(6));	// 初训学时
					course.setPxxs(bean.get(7));	// 初训形式
					course.setKsxs(bean.get(8));  //初训考试形式
					course.setIsFx(convertToInteger(bean.get(9),0));	// 是否复训
					if(null != course.getIsFx() && !INTEGER_TYPE_ERROR.equals(course.getIsFx()) && 1== course.getIsFx()){
						course.setFxks(bean.get(10));	// 复训学时
						course.setFxpxxs(bean.get(11));	// 复训形式
						course.setFxksxs(bean.get(12));	// 复训考试形式
						course.setZqz(convertToInteger(bean.get(13)));	// 复训间隔
						course.setZqdw(convertToInteger(bean.get(14), 2));	// 间隔单位
					}
					course.setBz(bean.get(15));;	// 备注
					course.setZt(1);
					course.setDprtcode(user.getJgdm());
					course.setWhrid(user.getId());
					course.setWhsj(new Date());
					course.setWhbmid(user.getBmdm());
					course.getParamsMap().put("sheetName", sheetName);
					list.add(course);
				}
			}
			resultMap.put(sheetIndex, list);
		}
		return resultMap;
	}
	
	/**
	 * 
	 * @Description 获取所有课程信息
	 * @CreateTime 2017年12月1日 上午9:41:13
	 * @CreateBy 岳彬彬
	 * @return
	 */
	private Map<String, Course> getAllCourse(){
		Map<String, Course> resultMap = new HashMap<String, Course>();
		List<Course> courses= courseMapper.selectALLCourse(ThreadVarUtil.getUser().getJgdm());
		for (Course course : courses) {
			resultMap.put(course.getKcbh()+"&"+course.getFjjx(), course);
		}
		return resultMap;
	}
	
	private List<String> getAllCourseDic(String lxid){
		List<String> resultList = new ArrayList<String>();
		List<DictItem> dictList = dictItemMapper.selectByLxidAndDprtcode(lxid, ThreadVarUtil.getUser().getJgdm());
		for (DictItem dictItem : dictList) {
			resultList.add(dictItem.getSz());
		}
		return resultList;
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
