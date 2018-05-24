package com.eray.thjw.project2.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.service.AirworthinessService;
import com.eray.thjw.project2.service.DistributionDepartmentService;
import com.eray.thjw.project2.service.GiveInstructionService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.TechnicalService;
import com.eray.thjw.service.UserService;

import enu.project2.TechnicalSysEnum;

/**
 * 
 * @Description 技术评估单审核
 * @CreateTime 2017年8月22日 下午2:39:45
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/project2/assessment/audit")
public class AssessmentAuditController {

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
	
	/**
	 * @Description 技术评估审核列表跳转 
	 * @CreateTime 2017年8月14日 上午9:37:54
	 * @CreateBy 林龙
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:audit:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			//初始化数据
			model = technicalService.initData();
			
			String url="";
			if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A01.getCode())){
				url="project2/assessment/audit/assessment_audit_main";
			}else if(SysContext.ASSESSMENT_TYPE.equals(TechnicalSysEnum.A02.getCode())){
				url="project2/assessmenttwo/audit/assessment_audit_main_two";
			}
			return new ModelAndView(url,model);
			
		} catch (Exception e) {
			 throw new BusinessException("技术文件评估审核列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 技术文件评估单审核列表分页数据 
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
			throw new BusinessException("技术文件评估审核列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 审核通过
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:audit:main:01")
	@ResponseBody
	@RequestMapping(value = "passed", method = RequestMethod.POST)
	public String passed(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.passed(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核技术文件评估单失败!",e);
		}
	}
	
	/**
	 * @Description 审核驳回
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:audit:main:01")
	@ResponseBody
	@RequestMapping(value = "turnDown", method = RequestMethod.POST)
	public String turnDown(@RequestBody Technical technical) throws BusinessException{
		try {
			return technicalService.turnDown(technical);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核驳回技术文件评估单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月23日 上午9:25:36
	 * @CreateBy 林龙
	 * @param idList id list集合
	 * @param yj 意见
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:assessment:audit:main:02")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]") List<String> idList, String yj) throws BusinessException {
		try {
			return technicalService.updateBatchAudit(idList, yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核文件评估单失败文件", e);
		}
	}
}
