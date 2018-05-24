package com.eray.thjw.training.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.service.PlanService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.training.TrainingPlanTypeEnum;

/**
 * 
 * @author 林龙
 * @description 课程培训记录
 */
@Controller
@RequestMapping("/training/course")
public class RecordsController extends BaseController {
	
	@Resource
	private PlanService planService;
	
	@Resource(name="recordsExcelImporter")
	private BaseExcelImporter<MaintenancePersonnel> excelImporter;
	
	/**
	 * 跳转至培训记录管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="training:course:records")
	@RequestMapping(value = "/records", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("trainingPlanTypeEnum", TrainingPlanTypeEnum.enumToListMap());
		return new ModelAndView("/training/course/records_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 培训记录列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "recordsList", method = RequestMethod.POST)
	public Map<String, Object> recordsList(@RequestBody TrainingPlan trainingPlan,HttpServletRequest request,Model model) throws BusinessException{
		String id = trainingPlan.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		trainingPlan.setId(null);
		PageHelper.startPage(trainingPlan.getPagination());
		List<TrainingPlan> list = planService.queryAllPageKcList(trainingPlan);
		
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					TrainingPlan trainingPlan1 = new TrainingPlan();
					trainingPlan1.setId(id);
					List<TrainingPlan> newRecordList = planService.queryAllPageKcList(trainingPlan1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, trainingPlan.getPagination());
		}else{
			List<TrainingPlan> newRecordList = new ArrayList<TrainingPlan>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				TrainingPlan trainingPlan1 = new TrainingPlan();
				trainingPlan1.setId(id);
				newRecordList = planService.queryAllPageKcList(trainingPlan1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, trainingPlan.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, trainingPlan.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 根据培训计划id查询数据
	 * @param request,mainid
	 * @develop date 2016.10.13
	 */
	@ResponseBody
	@RequestMapping(value = "selectByPrimaryKey", method = RequestMethod.POST)
	public TrainingPlan queryDetailListByMainId(String id,HttpServletRequest request)throws BusinessException {
		TrainingPlan trainingPlan = new TrainingPlan();
		try {
			trainingPlan = planService.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询数据失败");
		}
		return trainingPlan;
	}
	
	
	/**
	 * 
	 * @Description 保存
	 * @CreateTime 2017年8月12日 上午10:08:11
	 * @CreateBy 林龙
	 * @param trainingPlan
	 * @throws BusinessException
	 */
	@Privilege(code="training:course:records:01")
	@ResponseBody
	@RequestMapping(value = "recordssave", method = RequestMethod.POST)
	public void save(@RequestBody TrainingPlan trainingPlan) throws BusinessException{
		try {
			planService.save(trainingPlan);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	/**
	 * 
	 * @Description 导出课程培训计划
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "CourseRecords.xls")
	public String export(TrainingPlan trainingPlan,String jhrqBegin,String jhrqEnd,HttpServletRequest request,
			Model model) throws BusinessException {
		String [] ss= (trainingPlan.getZy().substring(0, trainingPlan.getZy().length() - 1)).split(",");
		try {
			trainingPlan.setDprtcode(new String (trainingPlan.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			trainingPlan.setJsxm(new String (trainingPlan.getJsxm().getBytes("iso-8859-1"),"utf-8"));
			
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			trainingPlan.setPagination(p);
			trainingPlan.getParamsMap().put("fxbsList", ss);
			trainingPlan.getParamsMap().put("jhrqBegin", jhrqBegin);
			trainingPlan.getParamsMap().put("jhrqEnd", jhrqEnd);
			List<TrainingPlan> list = planService.queryAllPageKcList(trainingPlan);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "kcpxjl", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
	/**
	 * 
	 * @Description 根据课程编号和人员id查询数据
	 * @CreateTime 2018-3-28 下午4:17:18
	 * @CreateBy 孙霁
	 * @param planPerson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllByBhAndRy", method = RequestMethod.POST)
	public Map<String, Object> queryAllByBhAndRy(@RequestBody PlanPerson planPerson,HttpServletRequest request,Model model) throws BusinessException{
		return planService.queryAllByBhAndRy(planPerson);
	}
	
	
	
	
	/**
	 * @param multipartRequest
	 * @param response
	 * @CreateBy 刘邓
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/excelImports", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put("DATA", ThreadVarUtil.get("returnDatas"));
			result.put(SUCCESS, Boolean.TRUE);			
			result.put(MESSAGE, "培训成绩导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "培训成绩导入失败！");
			e.printStackTrace();
		}
		return result;
	}
}
