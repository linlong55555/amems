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

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.service.CourseService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.course.CycleEnum;

/**
 * @author liub
 * @description 课程管理控制器
 */
@Controller
@RequestMapping("/training/course")
public class CourseController extends BaseController{
	
	/**
	 * @author liub
	 * @description 课程管理Service
	 */
	@Resource
	private CourseService courseService;
	
	@Resource(name="courseexcelimporter")
	private BaseExcelImporter<BaseEntity> excelImporter;
	
	/**
	 * 跳转至课程管理界面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="training:course:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("cycleEnum", CycleEnum.enumToListMap());
		return new ModelAndView("/training/course/course_main", model);
	}
	
	/**
	 * @author liub
	 * @description 查看课程详情
	 * @param id,model
	 * @return 页面视图
	 * @develop date 2016.09.14
	 *
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,String id) throws BusinessException{
		try {
			model.addAttribute("cycleEnum", CycleEnum.enumToListMap());
			model.addAttribute("course", courseService.selectById(id));
		} catch (Exception e) {
			throw new BusinessException("查看课程详情失败!",e);
		}
		return new ModelAndView("/training/course/course_view");
	}
	
	/**
	 * @author liub
	 * @description 增加课程
	 * @param course
	 * @throws BusinessException 
	 */
	@Privilege(code="training:course:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody Course course) throws BusinessException{
		try {
			return courseService.add(course);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存课程失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 修改课程
	 * @param course
	 * @throws BusinessException 
	 */
	@Privilege(code="training:course:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody Course course) throws BusinessException{
		try {
			courseService.edit(course);
			return course.getId();
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改课程失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 * @throws BusinessException
	 */
	@Privilege(code="training:course:main:03")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(String id) throws BusinessException{
		try {
			courseService.cancel(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			 throw new BusinessException("作废失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询课程信息
	 * @param course
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Course course)throws BusinessException {
		String id = course.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		course.setId(null);
		try {
			PageHelper.startPage(course.getPagination());
			List<Course> list = courseService.queryAllPageList(course);
			if(((Page)list).getTotal() > 0){
				//分页查询
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Course newRecord = new Course();
						newRecord.setId(id);
						List<Course> newRecordList = courseService.queryAllPageList(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, course.getPagination());
			}else{
				List<Course> newRecordList = new ArrayList<Course>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Course newRecord = new Course();
					newRecord.setId(id);
					newRecordList = courseService.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, course.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, course.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据id查询课程及用户信息
	 * @param id
	 * @return Course
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public Course selectById(String id) throws BusinessException {
		Course course = null;
		try {
			course = courseService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询课程失败!",e);
		}
		return course;
	}
	
	/**
	 * 
	 * @Description 根据课程编号查询数据
	 * @CreateTime 2017年12月21日 下午4:51:44
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByKcbm", method = RequestMethod.POST)
	public Course selectByKcbm(@RequestBody Course course)throws BusinessException {
		try {
			return courseService.selectkcbh(course.getKcbh(), course.getDprtcode());
		} catch (Exception e) {
			throw new BusinessException("查询课程信息失败!",e);
		}
	}
	
	/**
	 * @Description 查询课程-培训评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param record 课程
	 * @return List<Course> 课程集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCourseEval",method={RequestMethod.POST,RequestMethod.GET})
	public List<Course> queryCourseEval(@RequestBody Course course) throws BusinessException {
		return courseService.queryCourseEval(course);
	}
	
	@Privilege(code="training:course:main:04")
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
	
	/**
	 * 
	 * @Description 导出课程
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "Course.xls")
	public String export(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
		    Course course = JSON.parseObject(paramjson, Course.class);
			List<Course> list = courseService.queryAllPageList(course);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "kc", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
