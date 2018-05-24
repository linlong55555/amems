package com.eray.thjw.project2.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.DistributionDepartment;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.Todors;
import com.eray.thjw.project2.service.AirworthinessService;
import com.eray.thjw.project2.service.DistributionDepartmentService;
import com.eray.thjw.project2.service.GiveInstructionService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.TechnicalService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.Utils;

import enu.ThresholdEnum;
import enu.project2.TechnicalStatusEnum;
import enu.project2.TechnicalSysEnum;

/**
 * @Description 技术评估单评估
 * @CreateTime 2017年8月15日 下午2:27:47
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/project2/assessment")
public class AssessmentController extends BaseController{

	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	@Resource
	private TechnicalService technicalService;
	
	@Resource
	private GiveInstructionService giveInstructionService;
	
	@Resource
	private InstructionsourceService instructionsourceService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private AirworthinessService airworthinessService;
	
	@Resource
	private DistributionDepartmentService distributionDepartmentService;
	
	@Resource
	private TodoService todoService;
	
	@Resource
	private TodorsService todorsService;
	
	/**
	 * @Description 技术评估列表跳转 
	 * @CreateTime 2017年8月14日 上午9:37:54
	 * @CreateBy 林龙
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//初始化数据
			model = technicalService.initData();
			
			String url="";
			if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A01.getCode())){
				url="project2/assessment/assessment_main";
			}else if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A02.getCode())){
				url="project2/assessmenttwo/assessment_main_two";
			}
			return new ModelAndView(url,model);
		} catch (Exception e) {
			 throw new BusinessException("技术文件评估列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description  获取当前组织机构的技术文件阀值和评估人
	 * @CreateTime 2017年8月14日 上午9:39:15
	 * @CreateBy 林龙
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getTechnicalThreshold", method = RequestMethod.POST)
	public Map<String, Object> getTechnicalThreshold(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.JSWJ.getName(),dprtcode));
			return model;
		} catch (Exception e) {
			throw new BusinessException("获取技术评估单阀值失败!",e);
		}
	}
	
	/**
	 * @Description 技术评估单列表分页数据 
	 * @CreateTime 2017年8月14日 上午9:39:57
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody Technical technical,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = technicalService.queryAllPageList(technical);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("技术文件评估列表加载失败!",e);
		}
	}
	
	/**
	 * @Description  技术文件查看跳转
	 * @CreateTime 2017年8月14日 上午9:40:19
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("find/{id}")
	public ModelAndView find(@PathVariable("id") String id, HttpServletRequest request,HttpServletResponse resp,Map<String, Object> model) throws Exception {
		try {
			
			String url="";
			if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A01.getCode())){
				url="/project2/assessment/assessment_open_find";
			}else if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A02.getCode())){
				url="/project2/assessmenttwo/assessment_open_find_two";
			}
			return new ModelAndView(url,model);
			
		} catch (RuntimeException e) {
			throw new BusinessException("技术评估单查看跳转失败!",e);
		}
	}

	/**
	 * @Description 保存技术评估单
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.save(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 修改技术文件
	 * @CreateTime 2017年8月14日 上午9:42:40
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody Technical technical) throws BusinessException{
		try {
			technical.setZt(TechnicalStatusEnum.SAVE.getId());//状态为保存
			return technicalService.update(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 新增提交技术评估单
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:01")
	@ResponseBody
	@RequestMapping(value = "saveSubmit", method = RequestMethod.POST)
	public String saveSubmit(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.saveSubmit(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 修改提交技术评估单
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:02")
	@ResponseBody
	@RequestMapping(value = "updateSubmit", method = RequestMethod.POST)
	public String updateSubmit(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.updateSubmit(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 删除技术文件
	 * @CreateTime 2017年8月14日 上午9:43:17
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Technical technical) throws BusinessException{
		try {
			 technicalService.delete(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 关闭技术评估单
	 * @CreateTime 2017年8月14日 上午9:43:17
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:05")
	@ResponseBody
	@RequestMapping(value = "endModal", method = RequestMethod.POST)
	public void endModal(@RequestBody Technical technical) throws BusinessException{
		try {
			 technicalService.updateEndModal(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭技术评估单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 选择技术评估单列表
	 * @CreateTime 2017年8月16日 上午10:57:57
	 * @CreateBy 岳彬彬
	 * @param technical
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="getTechnical", method = RequestMethod.POST)
	public Map<String, Object> getTechnical(@RequestBody Technical technical, HttpServletRequest request,
			HttpServletResponse resp) throws BusinessException {
		try {
			return technicalService.selectPgdByZlidAndDprtcode(technical);
		}catch (Exception e) {
			 throw new BusinessException("获取技术评估单列表失败!",e);
		}
	}	
	
	/**
	 * @Description 根据技术文件id查询所有评估单
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getgljspgdList", method = RequestMethod.POST)
	public Map<String, Object> getgljspgdList(@RequestBody Technical technical)throws BusinessException {
		try {
			return technicalService.queryGljspgdList(technical);
		} catch (Exception e) {
			throw new BusinessException("查询技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 根据技术文件id查询所有评估单
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getgljspgdListold", method = RequestMethod.POST)
	public Map<String, Object> getgljspgdListold(@RequestBody Technical technical)throws BusinessException {
		try {
			return technicalService.getgljspgdListold(technical);
		} catch (Exception e) {
			throw new BusinessException("查询技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 根据技术文件id查询所有评估单
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getgljspgdListoldwu", method = RequestMethod.POST)
	public List<Technical> getgljspgdListoldwu(@RequestBody Technical technical)throws BusinessException {
		try {
			return technicalService.getgljspgdListoldwu(technical);
		} catch (Exception e) {
			throw new BusinessException("查询技术评估单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据适航性资料id查询适航性资料数据 
	 * @CreateTime 2017年8月21日 下午4:38:49
	 * @CreateBy 林龙
	 * @param airworthiness 适航性资料
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByAirworthinessId", method = RequestMethod.POST)
	public Airworthiness getByAirworthinessId(@RequestBody Airworthiness airworthiness)throws BusinessException {
		try {
			return airworthinessService.getByAirworthinessId(airworthiness);
		} catch (Exception e) {
			throw new BusinessException("查询适航性资料失败!",e);
		}
	}
	
	/**
	 * @Description 根据技术评估单id查询技术评估单信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByTechnicalId", method = RequestMethod.POST)
	public Technical getByTechnicalId(@RequestBody Technical technical)throws BusinessException {
		try {
			return technicalService.getById(technical);
		} catch (Exception e) {
			throw new BusinessException("查询技术评估单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据评估单id查询技术评估单-下达指令List集合信息
	 * @CreateTime 2017年8月19日 上午10:08:14
	 * @CreateBy 林龙
	 * @param giveInstruction 技术评估单-下达指令
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectOrderList", method = RequestMethod.POST)
	public List<GiveInstruction> selectOrderList(@RequestBody GiveInstruction giveInstruction)throws BusinessException {
		try {
			return giveInstructionService.selectOrderList(giveInstruction);
		} catch (Exception e) {
			throw new BusinessException("查询下达指令信息失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据评估单id查询涉及部门信息
	 * @CreateTime 2017年8月21日 下午9:33:48
	 * @CreateBy 林龙
	 * @param distributionDepartment 涉及部门
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectDepartmentList", method = RequestMethod.POST)
	public List<DistributionDepartment> selectDepartmentList(@RequestBody DistributionDepartment distributionDepartment)throws BusinessException {
		try {
			return distributionDepartmentService.selectDepartmentList(distributionDepartment);
		} catch (Exception e) {
			throw new BusinessException("查询涉及部门信息失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月19日 上午10:08:14
	 * @CreateBy 林龙
	 * @param giveInstruction 下达指令来源
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllOrderList", method = RequestMethod.POST)
	public List<Todo> queryAllOrderList(@RequestBody Todo todo)throws BusinessException {
		try {
			return todoService.selectOrderList(todo);
		} catch (Exception e) {
			throw new BusinessException("查询下达指令来源信息失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据评估单id查询产出
	 * @CreateTime 2017年8月19日 上午10:08:14
	 * @CreateBy 林龙
	 * @param todors 待办事宜-处理结果
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectOuputList", method = RequestMethod.POST)
	public List<GiveInstruction> selectOuputList(@RequestBody Todors todors)throws BusinessException {
		try {
			return todorsService.selectOuputList(todors);
		} catch (Exception e) {
			throw new BusinessException("查询下达指令信息失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据适航性资料获取数据和待办事宜数据
	 * @CreateTime 2017-8-18 下午4:48:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("selectTechnicalfileByJswjid")
	public Map<String, Object> selectTechnicalfileByJswjid(HttpServletRequest request,String jswjid) throws Exception {
		Map<String, Object> resultMap = technicalService.selectTechnicalfileByJswjid(jswjid);
		return resultMap;
	}
	
	/**
	 * @Description 改版保存技术评估单
	 * @CreateTime 2017年8月14日 上午9:42:40
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:04")
	@ResponseBody
	@RequestMapping(value = "revisionSave", method = RequestMethod.POST)
	public String revisionSave(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.insertRevisionSave(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("保存改版技术评估单失败!",e);
		}
	}
	
	/**
	 * @Description 改版提交技术评估单
	 * @CreateTime 2017年8月14日 上午9:42:40
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:04")
	@ResponseBody
	@RequestMapping(value = "updateRevisionSubmit", method = RequestMethod.POST)
	public String updateRevisionSubmit(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.updateRevisionSubmit(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("提交改版技术评估单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 查看技术评估单
	 * @CreateTime 2017年8月26日 下午2:59:26
	 * @CreateBy 林龙
	 * @param model
	 * @param id 
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(Model model,@RequestParam String id)throws BusinessException {
		try {
			model.addAttribute("viewid", id);
			
			String url="";
			if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A01.getCode())){
				url="project2/assessment/assessment_open_find";
			}else if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A02.getCode())){
				url="project2/assessmenttwo/assessment_open_find_two";
			}
			return new ModelAndView(url);
			
		} catch (Exception e) {
			throw new BusinessException("查看技术评估单失败！", e);
		}
	}
	
	/**
	 * @Description 根据技术评估单id获取最大版本
	 * @CreateTime 2017年8月14日 上午9:42:40
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectMaxBb", method = RequestMethod.POST)
	public Technical selectMaxBb(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.selectMaxBb(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("查询技术评估单最大版本失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 根据id查询当前评估单的所有版本
	 * @CreateTime 2017年8月29日 上午10:49:45
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @return 
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryHistoryListById",method={RequestMethod.POST,RequestMethod.GET})
	public List<Technical> queryHistoryListById(String id,String dprtcode) throws BusinessException {
		try {
			return technicalService.queryHistoryListById(id,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("根据技术评估单id查询当前技术评估单的历史版本集合失败!",e);
		}
	}
	
	/**
	 * @Description 修改评估人
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:main:06")
	@ResponseBody
	@RequestMapping(value = "updateAssessor", method = RequestMethod.POST)
	public String updateAssessor(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.updateAssessor(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("修改评估人失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 技术评估导出
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "TechnicalAssessment.xls")
	public String export(String paramjson,HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson=new String (paramjson.getBytes("iso-8859-1"),"utf-8");
			Technical technical = Utils.Json.toObject(paramjson, Technical.class);
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			technical.setPagination(p);
			Map<String, Object> resultMap = technicalService.queryAlldaochuList(technical);
			List<Technical> list = (List<Technical>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "jspg", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}
