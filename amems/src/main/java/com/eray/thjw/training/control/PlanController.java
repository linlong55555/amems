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
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.po.TrainingPlanExportExcel;
import com.eray.thjw.training.service.PlanService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.course.CycleEnum;
import enu.training.TrainingPlanStatusEnum;
import enu.training.TrainingPlanTypeEnum;

/**
 * @author liub
 * @description 培训计划控制器
 */
@Controller
@RequestMapping("/training/plan")
public class PlanController extends BaseController{
	
	/**
	 * @author liub
	 * @description 培训计划Service
	 */
	@Resource
	private PlanService planService;
	
	@Resource(name="trainplanexcelimporter")
	private BaseExcelImporter<BaseEntity> excelImporter;
	
	
	/**
	 * 跳转至培训计划界面
	 * @return 
	 */
	@Privilege(code="training:plan:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("trainingPlanTypeEnum", TrainingPlanTypeEnum.enumToListMap());
		model.put("trainingPlanStatusEnum", TrainingPlanStatusEnum.enumToListMap());
		return new ModelAndView("/training/plan/plan_main", model);
	}
	
	/**
	 * @author liub
	 * @description 增加培训计划
	 * @param trainingPlan
	 * @throws BusinessException 
	 */
	@Privilege(code="training:plan:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody TrainingPlan trainingPlan) throws BusinessException{
		try {
			return planService.add(trainingPlan);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存培训计划失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改培训计划
	 * @param TrainingPlan
	 * @throws BusinessException 
	 */
	@Privilege(code="training:plan:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody TrainingPlan trainingPlan) throws BusinessException{
		try {
			planService.edit(trainingPlan);
			return trainingPlan.getId();
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改培训计划失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code="training:plan:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(String id) throws BusinessException{
		try {
			planService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 下发
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code="training:plan:main:04")
	@ResponseBody
	@RequestMapping(value = "updateIssued", method = RequestMethod.POST)
	public void updateIssued(String id) throws BusinessException{
		try {
			planService.updateIssued(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("下发失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param trainingPlan
	 * @throws BusinessException
	 */
	@Privilege(code="training:plan:main:05")
	@ResponseBody
	@RequestMapping(value = "shutDown", method = RequestMethod.POST)
	public void shutDown(@RequestBody TrainingPlan trainingPlan) throws BusinessException{
		getLogger().info("指定结束  前端参数:id{}", new Object[]{trainingPlan});
		try {
			planService.updateShutDown(trainingPlan);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("指定结束失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询培训计划信息
	 * @param trainingPlan
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody TrainingPlan trainingPlan)throws BusinessException {
		String id = trainingPlan.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		trainingPlan.setId(null);
		try {
			PageHelper.startPage(trainingPlan.getPagination());
			List<TrainingPlan> list = planService.queryAllPageList(trainingPlan);
			if(((Page)list).getTotal() > 0){
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						TrainingPlan newRecord = new TrainingPlan();
						newRecord.setId(id);
						List<TrainingPlan> newRecordList = planService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, trainingPlan.getPagination());
			}else{
				List<TrainingPlan> newRecordList = new ArrayList<TrainingPlan>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					TrainingPlan newRecord = new TrainingPlan();
					newRecord.setId(id);
					newRecordList = planService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, trainingPlan.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, trainingPlan.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询年度视图信息
	 * @param trainingPlan
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryYearPageList", method = RequestMethod.POST)
	public Map<String, Object> queryYearPageList(@RequestBody TrainingPlan trainingPlan)throws BusinessException {
		try {
			return planService.queryYearPageList(trainingPlan);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据id查询培训计划、课程及用户信息
	 * @param id
	 * @return TrainingPlan
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public TrainingPlan selectById(String id) throws BusinessException {
		try {
			return planService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询失败!",e);
		}
	}
	/**
	 * @author liub
	 * @description 根据查询条件分页查询培训计划信息
	 * @param trainingPlan
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "plan.xls")
	public String planExcel(TrainingPlanExportExcel trainingPlanExportExcel,Model model)throws BusinessException {
		try {
			TrainingPlan trainingPlan=new TrainingPlan();
			trainingPlan.setDprtcode(trainingPlanExportExcel.getDprtcode());
			trainingPlan.setJhlx(trainingPlanExportExcel.getJhlx());
			trainingPlan.setKeyword(trainingPlanExportExcel.getKeyword());
			trainingPlan.setZt(trainingPlanExportExcel.getZt());
			
			Pagination pagination=new Pagination();
			pagination.setOrder(trainingPlanExportExcel.getOrder());
			pagination.setPage(trainingPlanExportExcel.getPage());
			pagination.setRows(trainingPlanExportExcel.getRows());
			pagination.setSort(trainingPlanExportExcel.getSort());
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			if(trainingPlanExportExcel.getFxbsList()!=null ){
				paramsMap.put("fxbsList", trainingPlanExportExcel.getFxbsList());
			}
			paramsMap.put("jhrqBegin", trainingPlanExportExcel.getJhrqBegin());
			paramsMap.put("jhrqEnd", trainingPlanExportExcel.getJhrqEnd());
			paramsMap.put("year", trainingPlanExportExcel.getYear());
			trainingPlan.setPagination(pagination);
			trainingPlan.setParamsMap(paramsMap);
			//TrainingPlan trainingPlan=new TrainingPlan();
			List<TrainingPlan> list =new ArrayList<TrainingPlan>();
			String wjmc="";
			if(trainingPlanExportExcel.getZtList()!=null ){
				paramsMap.put("ztList", trainingPlanExportExcel.getZtList());
				list= planService.queryAllTrainingPlan(trainingPlan);
				wjmc="PlanYear";
			}else{
				list= planService.queryAllPageListToPerson(trainingPlan);
				wjmc="Plan";
			}
			for (TrainingPlan plan : list) {
				if(plan.getZt()!=null ){
					plan.getParamsMap().put("ztText", TrainingPlanStatusEnum.getName(plan.getZt()));
				}
				if(plan.getJhlx()!=null){
					plan.getParamsMap().put("jhlxText",TrainingPlanTypeEnum.getName( plan.getJhlx()));
				}
				if(plan.getCourse() !=null && plan.getCourse().getZqdw()!=null){
					plan.getParamsMap().put("zqdwText",CycleEnum.getName(plan.getCourse().getZqdw()));
				}
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "课程信息导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "课程信息导入失败！");
			e.printStackTrace();
		}
		return result;
	}
}
