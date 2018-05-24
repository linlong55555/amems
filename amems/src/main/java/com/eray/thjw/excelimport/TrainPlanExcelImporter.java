package com.eray.thjw.excelimport;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.DisassemblingImport;
import com.eray.thjw.project2.dao.ActTypeMapper;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.system.dao.DictItemMapper;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.dao.FacultyMapper;
import com.eray.thjw.training.dao.PlanPersonMapper;
import com.eray.thjw.training.dao.TrainingPlanMapper;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.po.Faculty;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;


@Service("trainplanexcelimporter")
public class TrainPlanExcelImporter extends AbstractExcelImporter<TrainingPlan>{
@Resource
private ActTypeMapper actTypeMapper;
@Resource
private CourseMapper courseMapper;
@Resource
private FacultyMapper facultyMapper;
@Resource
private TrainingPlanMapper trainingPlanMapper;
@Resource
private PlanPersonMapper planPersonMapper;
@Resource
private DictItemMapper dictItemMapper;
@Resource
private MaintenancePersonnelMapper maintenancePersonnelMapper;

	@Override
	public void validateParam(Map<Integer, List<TrainingPlan>> datas)
			throws ExcelImportException {
		  User user=ThreadVarUtil.getUser();
		  //机型
		  List<String> jxList=getJxByDprtcode(user.getJgdm());
		  //讲师
		  List<Faculty> jsList=getJs(user.getJgdm());
		  //培训人员
		  List<MaintenancePersonnel> pxryList=getPxMembersByDprtcode(user.getJgdm());
		  //培训类别
		  List<Map<String,String>> pxlbList = getAllPlanDic(34,user.getJgdm());
		  //培训形式
		  List<Map<String,String>> pxxsList = getAllPlanDic(32,user.getJgdm());
		  //考试形式
		  List<Map<String,String>> ksxsList = getAllPlanDic(33,user.getJgdm());
		  //币种
		  List<Map<String,String>> bzList = getAllPlanDic(3,user.getJgdm());
		  //专业
		  List<Map<String,String>> zyList = getAllPlanDic(4,user.getJgdm());
		  List<TrainingPlan> listPlans;
		  TrainingPlan plan;
		  for(Integer key:datas.keySet()){
			  listPlans=datas.get(key);
			  if(listPlans.isEmpty())
				  continue;
			  for(int i=0;i<listPlans.size();i++){
				   plan=listPlans.get(i);
				  //非空验证
				  if(StringUtils.isBlank(plan.getJhlx().toString()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，计划类型不能为空");
				 
				  if(StringUtils.isBlank(plan.getPxlb()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训类别不能为空");
				  
				  if(StringUtils.isBlank(plan.getParamsMap().get("kcdm").toString()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，课程代码不能为空");
				 
				  if(plan.getFxbs() == null)
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，初/复训标识不能为空");
				  
				  if(StringUtils.isBlank(plan.getPxxs()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训形式不能为空");
				  
				  if(StringUtils.isBlank(plan.getKsxs()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，考试形式不能为空");
				  
				  if(null==plan.getJhKsrq())
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，计划开始日期不能为空");  
				 
//				  if(StringUtils.isBlank(plan.getParamsMap().get("pxrybh").toString()))
//					  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训人员编号不能为空");
				  
				  //中文验证
				  if(!StringUtils.isBlank(plan.getParamsMap().get("kcdm").toString())&&Utils.Str.isChinese(plan.getParamsMap().get("kcdm").toString()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，课程代码不能含有中文");
				  
				  if(!StringUtils.isBlank(plan.getFjjx())&&Utils.Str.isChinese(plan.getFjjx()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，机型不能含有中文");
				  
				  if(!StringUtils.isBlank(plan.getJsid())&&Utils.Str.isChinese(plan.getJsid()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，讲师编号不能含有中文");
				  
				  if(!StringUtils.isBlank(plan.getParamsMap().get("pxrybh").toString())&&Utils.Str.isChinese(plan.getParamsMap().get("pxrybh").toString()))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训人员编号不能含有中文");
			  				  
				  //格式校验	  
				  if(!StringUtils.isBlank(plan.getJhlx().toString())&&(!"1".equals(plan.getJhlx().toString())&&!"2".equals(plan.getJhlx().toString())))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，计划类型只能为1或者2");
				 
				  if(!StringUtils.isBlank(plan.getFxbs().toString())&&(!"1".equals(plan.getFxbs().toString())&&!"2".equals(plan.getFxbs().toString())))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，初/复训标识只能为1或者2");
				 
				  if(!StringUtils.isBlank(plan.getKs())&&!Utils.Str.isDecimalzs(plan.getKs(), 2))
					 addErrorMessage("培训计划信息，第"+(i+3)+"行，课时只能为[0~99999999.99]范围内的两位小数");		  
				  
				  if(null!=plan.getJhKsrq()&&plan.getJhKsrq()==TrainPlanExcelImporter.DATE_TYPE_ERROR)
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，计划开始日期格式不对");
				  
				  if(null!=plan.getJhJsrq()&&plan.getJhJsrq()==TrainPlanExcelImporter.DATE_TYPE_ERROR)
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，计划结束日期格式不对");
				 
				  if(!StringUtils.isBlank(plan.getPxys().toString())&&!Utils.Str.isDecimalzs(plan.getPxys().toString(), 2))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训预算只能为[0~9999999.99]范围内的两位小数");
				 
				  if(!StringUtils.isBlank(plan.getJsBz().toString())&&(!"1".equals(plan.getJsBz().toString())&&!"0".equals(plan.getJsBz().toString())))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，是否颁证只能为0或者1");
				 
				  if(!StringUtils.isBlank(plan.getIsJcff().toString())&&(!"1".equals(plan.getIsJcff().toString())&&!"0".equals(plan.getIsJcff().toString())))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，教材发放只能为0或者1");

//				   if(!StringUtils.isBlank(plan.getParamsMap().get("pxrybh").toString())&&!plan.getParamsMap().get("pxrybh").toString().contains(","))
//					  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训人员编号格式不对");
				 	
				  //时间校验
				  if(plan.getJhJsrq()!=null&&(plan.getJhJsrq().getTime()<plan.getJhKsrq().getTime())){
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，计划结束日期要大于计划开始日期");  
				  }
				  
				    //长度验证
					if(Utils.Str.getLength(plan.getPxlb()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，培训类别的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getParamsMap().get("kcdm").toString()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，课程代码的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getFjjx()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，机型的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getPxxs()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，培训形式的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getKsxs()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，考试形式的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getZrr()) > 15){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，主办的最大长度为15");
					}
					if(Utils.Str.getLength(plan.getPxjg()) > 200){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，培训机构的最大长度为200");
					}
					if(Utils.Str.getLength(plan.getPxdx()) > 1000){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，培训对象的最大长度为1000");
					}
					if(Utils.Str.getLength(plan.getKcdd()) > 300){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，培训地点的最大长度为300");
					}
					if(Utils.Str.getLength(plan.getZy()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，专业的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getJsid()) > 50){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，讲师的最大长度为50");
					}
					if(Utils.Str.getLength(plan.getKcnr()) > 1000){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，课程内容的最大长度为1000");
					}
					if(Utils.Str.getLength(plan.getBz()) > 300){
						addErrorMessage("培训计划信息，第"+(i+3)+"行，备注的最大长度为300");
					}
					
					
				 //数据是否存在	
				   if(!StringUtils.isBlank(plan.getPxlb())&&!pxlbIsExist(pxlbList,plan.getPxlb())){
					   addErrorMessage("培训计划信息，第"+(i+3)+"行，培训类别在数据字典中不存在");
				   }
				   if(!StringUtils.isBlank(plan.getPxxs())&&!pxxsIsExist(pxxsList,plan.getPxxs())){
					   addErrorMessage("培训计划信息，第"+(i+3)+"行，培训形式在数据字典中不存在");
				   }
				   if(!StringUtils.isBlank(plan.getKsxs())&&!ksxsIsExist(ksxsList,plan.getKsxs())){
					   addErrorMessage("培训计划信息，第"+(i+3)+"行，考试形式在数据字典中不存在");
				   }
				   if(!StringUtils.isBlank(plan.getZy())&&!zyIsExist(zyList,plan.getZy())){
					   addErrorMessage("培训计划信息，第"+(i+3)+"行，专业在数据字典中不存在");
				   }
				   if(!StringUtils.isBlank(plan.getPxysBz())&&!bzIsExist(bzList,plan.getBz())){
					   addErrorMessage("培训计划信息，第"+(i+3)+"行，币种在数据字典中不存在");
				   }

				  
				  //数据唯一性验证
				  String isUnique= selectByCodeAndJx(user.getJgdm(),plan.getFjjx(),plan.getParamsMap().get("kcdm").toString());
				      if(StringUtils.isBlank(isUnique))
					  addErrorMessage("培训计划信息，第"+(i+3)+"行，课程代码和机型在机构下不存在");
				   				 				    
				  if(!StringUtils.isBlank(plan.getJsid())){
					  //校验讲师编号是否存在
					  if(!jsBhIsExist(jsList,plan.getJsid())){
						  addErrorMessage("培训计划信息，第"+(i+3)+"行，讲师编号在机构下不存在");
					  }else if(!StringUtils.isBlank(plan.getParamsMap().get("kcdm").toString())){
						  //判断讲师是否有该课程权限
						 String hasAuth= hasCourseAuth(plan.getJsid(), plan.getParamsMap().get("kcdm").toString(), user.getJgdm(),plan.getFjjx());
						  if(StringUtils.isBlank(hasAuth)){
							  addErrorMessage("培训计划信息，第"+(i+3)+"行，该讲师没有此课程权限");
						  }
					  }
				  }  
				  
				  //检验维修人员档案编号是否存在
				  String[] ars=plan.getParamsMap().get("pxrybh").toString().replace("，", ",").split(",");
				    for(String rybh:ars){
				    	if(!StringUtils.isBlank(rybh)){			    		
				    		  if(!ryBhIsExist(pxryList,rybh)){
				    			  addErrorMessage("培训计划信息，第"+(i+3)+"行，培训人员编号"+rybh+"在该机构下不存在");
				    		  }
				    		
				    	}
				    }
			  }
		  }
		
		
	}

	@Override
	public int writeToDB(Map<Integer, List<TrainingPlan>> datas)
			throws ExcelImportException {
		User user=ThreadVarUtil.getUser();
		PlanPerson planPerson=null;
		String[] ars=null;
		String kcid=null;
		Faculty  faculty=null;
		//讲师
		List<Faculty> jsList=getJs(user.getJgdm());
		//培训人员
		List<MaintenancePersonnel> pxryList=getPxMembersByDprtcode(user.getJgdm());
		List<PlanPerson> planPersonList=new ArrayList<PlanPerson>();
		List<TrainingPlan> planList=datas.get(0);
	      if(!planList.isEmpty()){
	    	  for(TrainingPlan plan:planList){;
	    		  String uuid=UUID.randomUUID().toString();
	    		  plan.setId(uuid);
	    		  kcid=selectByCodeAndJx(user.getJgdm(),plan.getFjjx(),plan.getParamsMap().get("kcdm").toString());
	    		  plan.setKcid(kcid);
	    		  faculty=getJsByJsBh(jsList,plan.getJsid());
                  plan.setJsid(faculty.getId());
                  plan.setJsxm(faculty.getXm());
                  if(plan.getJhJsrq()==null){
                	  plan.setXt(1);
                  }else{
                	 int day= (int) ((plan.getJhJsrq().getTime()-plan.getJhKsrq().getTime())/(24*60*60*1000)); 
                	  plan.setXt(day);
                  }
                  ars=plan.getParamsMap().get("pxrybh").toString().split(",");
                  for(int j=0;ars!=null&&j<ars.length;j++){
                      planPerson=new PlanPerson();
                	  planPerson.setId(UUID.randomUUID().toString());
                      planPerson.setDprtcode(user.getJgdm());
                      planPerson.setPxjhid(uuid);
                      planPerson.setWxrydaid(getUserByUserBh(pxryList,ars[j]).getId());
                      planPerson.setXm(getUserByUserBh(pxryList,ars[j]).getXm());
                      planPerson.setFjjx(plan.getFjjx());
                      planPerson.setZy(plan.getZy());
                      planPerson.setKcid(kcid);
                      planPerson.setKcbm(plan.getParamsMap().get("kcdm").toString());
                      planPerson.setKcnr(plan.getKcnr());
                      planPerson.setPxgh(plan.getPxjg());
                      planPerson.setBz(plan.getBz());
                      planPerson.setPxlb(plan.getPxlb());
                      planPerson.setFxbs(plan.getFxbs());
                      planPerson.setPxxs(plan.getPxxs());
                      planPerson.setKsxs(plan.getKsxs());
                      planPerson.setKcdd(plan.getKcdd());
                      planPerson.setJsid(faculty.getId());
                      planPerson.setJsxm(faculty.getXm());
                      planPerson.setZt(1);
                      planPerson.setJszt(0);
                      planPerson.setWhbmid(user.getBmdm());
                      planPerson.setWhrid(user.getId());
                      planPerson.setWhsj(new Date());
                      planPersonList.add(planPerson);
                  }                  
               
	    	  }	    	  
	      }
	      
	      //批量新增培训计划
	      trainingPlanMapper.batchInsert(planList);
	      //批量新增培训课程人员
	      planPersonMapper.batchInsert(planPersonList);
		         return 0;
	}

	@Override
	public Map<Integer, List<TrainingPlan>> convertBean(
			Map<Integer, List<Map<Integer, String>>> mapList)
			throws ExcelImportException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		// 结果集
				Map<Integer, List<TrainingPlan>> resultMap = new TreeMap<Integer, List<TrainingPlan>>();
				// 循环sheet
				for (Integer sheetIndex : mapList.keySet()) {
					// sheet对应装机清单数据
					List<Map<Integer, String>> mapLists = mapList.get(sheetIndex);
					if(mapLists.isEmpty())
						continue;
					ArrayList<TrainingPlan> list = new ArrayList<TrainingPlan>();
					TrainingPlan trainingPlan;
					Map<String,Object> map;
					Map<Integer, String> bean;
					for (int i = 0; i < mapLists.size(); i++) {
						bean = mapLists.get(i);
						trainingPlan = new TrainingPlan();
						map=new HashMap<String, Object>();
						trainingPlan.setParamsMap(map);
						
						/*
						 * 读取excel值
						 */
						trainingPlan.setJhlx(convertToInteger(bean.get(0)==null?"":bean.get(0).trim(),1));          //计划类型
						trainingPlan.setPxlb(bean.get(1)==null?"":bean.get(1).trim());//培训类别
						trainingPlan.getParamsMap().put("kcdm",bean.get(2)==null?"":bean.get(2).trim());//课程代码
						trainingPlan.setFjjx(bean.get(3)==null?"":bean.get(3).trim());//机型
						trainingPlan.setFxbs(convertToInteger(bean.get(4)==null?"":bean.get(4).trim(),1));//初复训标识
						trainingPlan.setKs(bean.get(5)==null?"":bean.get(5).trim());//课时
						trainingPlan.setPxxs(bean.get(6)==null?"":bean.get(6).trim());//培训形式
						trainingPlan.setKsxs(bean.get(7)==null?"":bean.get(7).trim());	// 考试形式
						trainingPlan.setJhKsrq(convertToDate(bean.get(8)==null?"":bean.get(8).trim()));//计划开始日期
						trainingPlan.setJhJsrq(convertToDate(bean.get(9)==null?"":bean.get(9).trim()));//计划结束日期
						trainingPlan.setZrr(bean.get(10)==null?"":bean.get(10).trim());  //主办
						trainingPlan.setPxjg(bean.get(11)==null?"":bean.get(11).trim());  //培训机构
						trainingPlan.setPxdx(bean.get(12)==null?"":bean.get(12).trim());  //培训对象
						trainingPlan.setPxys(convertToBigDecimal(bean.get(13)==null?"":bean.get(13).trim()));  //培训预算
						trainingPlan.setPxysBz(bean.get(14)==null?"":bean.get(14).trim());  //币种
						trainingPlan.setKcdd(bean.get(15)==null?"":bean.get(15).trim());	// 培训地点
						trainingPlan.setZy(bean.get(16)==null?"":bean.get(16).trim());	//专业
						trainingPlan.setJsid(bean.get(17)==null?"":bean.get(17).trim());;	//讲师编号
						trainingPlan.setJsBz(convertToInteger(bean.get(18)==null?"":bean.get(18).trim(),0));;	//是否颁证
						trainingPlan.setIsJcff(convertToInteger(bean.get(19)==null?"":bean.get(19).trim(),0));;	//教材发放
						trainingPlan.getParamsMap().put("pxrybh",bean.get(20)==null?"":bean.get(20).trim());     //维修人员档案编号
						trainingPlan.setKcnr(bean.get(21)==null?"":bean.get(21).trim());  //课程内容
						trainingPlan.setBz(bean.get(22)==null?"":bean.get(22).trim());//课程备注
						trainingPlan.setZt(0);
						trainingPlan.setWhbmid(user.getBmdm());
						trainingPlan.setWhrid(user.getId());
						trainingPlan.setWhsj(new Date());
						trainingPlan.setDprtcode(user.getJgdm());
						list.add(trainingPlan);
						list.trimToSize();
						
					}
					resultMap.put(sheetIndex, list);
				}
				return resultMap;
	}
	
	
	/**
	 * @Description 获取该组织结构下所有机型
	 * @param dprtcode
	 * @return List<String>
	 */
	private List<String> getJxByDprtcode(String dprtcode){
		List<String> resultList=new ArrayList<String>();
		List<ActType> planeDate=actTypeMapper.findByDprtcode(dprtcode);
		for (ActType planeModelData : planeDate) {
			resultList.add(planeModelData.getFjjx());
		}
		return resultList;
	}

	
	
	/**
	 * @Description 获取该组织结构下的字典数据
	 * @param dprtcode
	 * @return
	 */
	private List<Map<String,String>> getAllPlanDic(Integer lxid,String dprtcode){
	    return SysContext.getDicItemsByDicId(lxid, dprtcode);
		
	}
	
	
	/**
	 * 判断培训类别是否存在
	 * @param pxlbList
	 * @param pxlb
	 * @return boolean
	 */
	private boolean pxlbIsExist(List<Map<String,String>> pxlbList,String pxlb){
		boolean flag=false;
		for (Iterator iterator = pxlbList.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			  if(map.get("id").equals(pxlb)){
				  flag=true;
				  break;
			  }
				 
		}
		  return flag;
	}
	
	/**
	 * 判断培训形式是否存在
	 * @param pxlbList
	 * @param pxlb
	 * @return boolean
	 */
	private boolean pxxsIsExist(List<Map<String,String>> pxxsList,String pxxs){
		boolean flag=false;
		for (Iterator iterator = pxxsList.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			  if(map.get("id").equals(pxxs)){
				  flag=true;
				  break;
			  }
				 
		}
		  return flag;
	}
	
	/**
	 * 判断考试形式是否存在
	 * @param pxlbList
	 * @param pxlb
	 * @return boolean
	 */
	private boolean ksxsIsExist(List<Map<String,String>> ksxsList,String ksxs){
		boolean flag=false;
		for (Iterator iterator = ksxsList.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			  if(map.get("id").equals(ksxs)){
				  flag=true;
				  break;
			  }
				 
		}
		  return flag;
	}
	
	/**
	 * 判断币种是否存在
	 * @param pxlbList
	 * @param pxlb
	 * @return boolean
	 */
	private boolean bzIsExist(List<Map<String,String>> bzList,String bz){
		boolean flag=false;
		for (Iterator iterator = bzList.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			  if(map.get("id").equals(bz)){
				  flag=true;
				  break;
			  }
				 
		}
		  return flag;
	}
	
	/**
	 * 判断专业是否存在
	 * @param pxlbList
	 * @param pxlb
	 * @return boolean
	 */
	private boolean zyIsExist(List<Map<String,String>> zyList,String zy){
		boolean flag=false;
		for (Iterator iterator = zyList.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			  if(map.get("id").equals(zy)){
				  flag=true;
				  break;
			  }
				 
		}
		  return flag;
	}
	
	/**
	 * @Description 获取该组织结构下所用讲师
	 * @param dprtcode
	 * @return
	 */
	private List<Faculty> getJs(String dprtcode){	
		List<Faculty> jsBhs=facultyMapper.getJsByDprtcode(dprtcode);
		    return jsBhs;
	}
	
	/**
	 * @Description 获取该组织结构下所用培训人员信息
	 * @param dprtcode
	 * @return
	 */
	private List<MaintenancePersonnel> getPxMembersByDprtcode(String dprtcode){
		return maintenancePersonnelMapper.queryAllJgdm(dprtcode);
	}
			
	/**
	 * @Description 通过课程代码和机型获取课程id
	 * @param dprtcode
	 * @param jx
	 * @param kcbh
	 * @return
	 */
	private String selectByCodeAndJx(String dprtcode,String jx,String kcbh){       
		      return courseMapper.selectByCodeAndJx(dprtcode, jx, kcbh);		
	}
		
	
	/**
	 * @Description 根据讲师编号和课程编号判断讲师是否有课程权限
	 * @param jsbh
	 * @param kcbh
	 * @param dprtcoe
	 * @return
	 */
	private String hasCourseAuth(String jsbh,String kcbh,String dprtcoe,String fjjx){
		
		return facultyMapper.hasCourseAuth(jsbh, kcbh, dprtcoe,fjjx);
	}
	
	
	/**
	 * @Description 判断讲师是否存在
	 * @param list
	 * @param jsbh
	 * @return
	 */
	private boolean jsBhIsExist(List<Faculty> list,String jsbh){
		boolean flag=false;
		if(StringUtils.isBlank(jsbh))
			return flag;
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Faculty faculty = (Faculty) iterator.next();
			if(faculty.getRybh().equals(jsbh)){
				flag=true;
				break;
			}
				
		}
	     return flag;
	}
	
	/**
	 * @Description 判断培训人员是否存在
	 * @param list
	 * @param jsbh
	 * @return
	 */
	private boolean ryBhIsExist(List<MaintenancePersonnel> list,String userBh){
		boolean flag=false;
		if(StringUtils.isBlank(userBh))
			return flag;
	    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
	    	MaintenancePersonnel user = (MaintenancePersonnel) iterator.next();
			if(user.getRybh().equals(userBh)){
				flag=true;
				break;
			}
				
		}
	     return flag;
	}
	
	/**
	 * @Description 通过讲师编号获取讲师信息
	 * @param list
	 * @param jsBh
	 * @return Faculty
	 */
	private Faculty getJsByJsBh(List<Faculty> list,String jsBh){
		Faculty faculty=null;
		if (jsBh == null||list.size()==0)
			return faculty;
        for(Faculty item:list){
        	if(jsBh.equals(item.getRybh())){
        		faculty=item;
        		break;
        	}
        		
        }

		return faculty;
	}
	
	/**
	 * @Description 通过培训成员编号获取培训成员信息
	 * @param list
	 * @param userBh
	 * @return User[]
	 */
	private MaintenancePersonnel getUserByUserBh(List<MaintenancePersonnel> list,String userBh){
		MaintenancePersonnel user=null;
		if (userBh == null||list.size()==0)
			return user;
        for(MaintenancePersonnel item:list){
        	if(userBh.equals(item.getRybh())){
        		user=item;
        		break;
        	}
        		
        }

		return user;
	}
}
