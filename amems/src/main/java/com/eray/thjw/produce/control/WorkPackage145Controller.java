package com.eray.thjw.produce.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.baseinfo.servcice.CustomerService;
import com.eray.thjw.baseinfo.servcice.ProjectService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.produce.po.Workpackage145;
import com.eray.thjw.produce.service.Workpackage145Service;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.produce.SourceTypeEnum;
import enu.produce.Workpackage145StatusEnum;
import enu.produce.WorkpackageStatusEnum;
/**
 * 
 * @Description 工包控制器145
 * @CreateTime 2017年9月18日 下午5:56:41
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/produce/workpackage145")
public class WorkPackage145Controller extends BaseController{
	@Resource
	private Workpackage145Service workpackage145Service;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private CustomerService  customerService;;
	@Resource
	private ProjectService projectService;
	/**
	 * 
	 * @Description 工包视图
	 * @CreateTime 2017年9月18日 下午5:56:54
	 * @CreateBy 岳彬彬
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("sourceTypeEnum", SourceTypeEnum.enumToListMap());
			model.put("departmentList", departmentService.getBmList(ThreadVarUtil.getUser().getJgdm()));
			model.put("xfdepartmentList", departmentService.getAllBmList(ThreadVarUtil.getUser().getJgdm()));
			model.put("xmList", projectService.getProjectByDprt(ThreadVarUtil.getUser().getJgdm()));
			model.put("workpackageStatusEnum", Workpackage145StatusEnum.enumToListMap());
			return new ModelAndView("produce/workpackage/145/workpackage_main", model);
		} catch (Exception e) {
			throw new BusinessException("工包145管理列表跳转失败!",e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "getWorkpackage145List", method = RequestMethod.POST)
	public Map<String, Object> getWorkpackage145List(@RequestBody Workpackage145 record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workpackage145Service.getWorkpackageList(record);
		} catch (Exception e) {
			throw new BusinessException("获取工包145列表失败!", e);
		}
	}
	
	/**
	 * @Description 查询航材工具需求清单工包信息 
	 * @CreateTime 2018-4-12 下午3:23:52
	 * @CreateBy 刘兵
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMToolDetail145List", method = RequestMethod.POST)
	public Map<String, Object> queryMToolDetail145List(@RequestBody Workpackage145 record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return workpackage145Service.queryMToolDetail145List(record);
		} catch (Exception e) {
			throw new BusinessException("获取工包145列表失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 工包查看
	 * @CreateTime 2017年9月18日 下午5:57:09
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView Looked(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("produce/workpackage/145/workpackage_view", model);
	}
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2017年10月19日 上午9:41:31
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			return workpackage145Service.addRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增工包失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取145工包
	 * @CreateTime 2017年10月18日 下午4:20:09
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getRecord", method = RequestMethod.POST)
	public Workpackage145 getRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			return workpackage145Service.getRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("获取工包失败!", e);
		}
	}
	/**
	 * 
	 * @Description 更新工包
	 * @CreateTime 2017年10月18日 下午5:08:36
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.updateRecord(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("更新工包失败!", e);
		}
	}
	/**
	 * 
	 * @Description 删除工包
	 * @CreateTime 2017年10月18日 下午5:08:58
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.deleteRecord(record);;
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除工包失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年10月19日 上午11:53:39
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:04")
	@ResponseBody
	@RequestMapping(value = "issued", method = RequestMethod.POST)
	public String issuedRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.update4Issued(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("下发工包失败!", e);
		}
	}
	/**
	 * 
	 * @Description 完工反馈
	 * @CreateTime 2017年10月19日 上午11:53:39
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:05")
	@ResponseBody
	@RequestMapping(value = "feedback", method = RequestMethod.POST)
	public String feedbackRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.update4Feedback(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("完工反馈失败!", e);
		}
	}
	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年10月19日 上午11:53:50
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:06")
	@ResponseBody
	@RequestMapping(value = "close", method = RequestMethod.POST)
	public String closeRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.update4Close(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("更新工包失败!", e);
		}
	}
	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年10月19日 下午2:04:04
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="produce:workpackage145:main:07")
	@ResponseBody
	@RequestMapping(value = "end", method = RequestMethod.POST)
	public String endRecord(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.update4End(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("更新工包失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取工包明细
	 * @CreateTime 2017年10月20日 上午9:53:57
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getWorpackageDetailList", method = RequestMethod.POST)
	public Map<String, Object> getWorpackageDetailList(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			return workpackage145Service.getWorkpackageDetail(record);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 工包修订
	 * @CreateTime 2017年11月27日 上午10:58:32
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doRevision", method = RequestMethod.POST)
	public String doRevision(@RequestBody Workpackage145 record, HttpServletRequest request) throws BusinessException {
		try {
			workpackage145Service.doXDClose(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("修订失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 工包145导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:workpackage145:main:11")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "WorkPackage145.xls")
	public String export(Workpackage145 record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			record.setPagination(p);
			Map<String, Object> resultMap = workpackage145Service.getWorkpackageList(record);
			List<Workpackage145> list = (List<Workpackage145>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "gb145", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	
	@Privilege(code = "produce:workpackage145:main:12")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "workpackageDetail145.xls" ,method={RequestMethod.GET})
	public String exportExcel(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Workpackage145 workpackage = Utils.Json.toObject(paramjson, Workpackage145.class);
			Workpackage145 wp = new Workpackage145();
			wp.setId((String) workpackage.getParamsMap().get("gbid"));
			wp = workpackage145Service.getRecord(wp);
			Map<String, Object> resultMap = workpackage145Service.getWorkpackageDetail(workpackage);
			List<Map<String, Object>> workorderList = (List<Map<String, Object>>) resultMap.get("workorderList");
			wp.getParamsMap().put("workorderDetail", workorderList);
			List<Workpackage145> list = new ArrayList<Workpackage145>();
			list.add(wp);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "workpackageDetail145", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
