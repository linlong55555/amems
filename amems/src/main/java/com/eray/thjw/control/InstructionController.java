package com.eray.thjw.control;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Instruction;
import com.eray.thjw.po.InstructionContent;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.User;
import com.eray.thjw.service.InstructionContentService;
import com.eray.thjw.service.InstructionService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.SessionUtil;

import enu.JobCardStatusEnum;
import enu.ordersource.OrderSourceEnum;

@Controller
@RequestMapping("/project/instruction")
public class InstructionController extends BaseController{

	@Autowired
	private InstructionService instructionService;
	@Autowired
	private OrderSourceService orderSourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private InstructionContentService instructionContentService;
	
	/**
	 * 跳转至技术指令界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="project:instruction:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String CommonalityPage(Model model) {
		return "project/instruction/instructionMain";
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 页面视图system/user/userMain.jsp
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryInstructionList", method = RequestMethod.POST)
	public Object queryInstructionList(@RequestBody Instruction instruction,HttpServletRequest request,Model model)throws BusinessException{
		return instructionService.queryAllInstruction(instruction);
		
	}
	/**
	 * 初始化增加技术指令
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="project:instruction:main:01")
	@RequestMapping("intoAddInstruction")
	public ModelAndView intoAddInstruction(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		model.put("userList", userService.getByDprtcode(user.getJgdm()));
		return new ModelAndView("project/instruction/add_instruction", model);
	}

	/**
	 * 保存提交技术文件
	 * 
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "saveMainInstruction", method = RequestMethod.POST)
	public String saveMainInstruction(@RequestBody Instruction instruction,HttpServletRequest request)  throws BusinessException{
		try {
			String id=instructionService.insertInstruction(instruction);
			return id;
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(),e);
		}catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 检查技术指令修改权限
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdIt")
	public Map<String, Object> checkUpdIt(HttpServletRequest request,String id) {
		return instructionService.checkUpdIt(SessionUtil.getUserFromSession(request),id);
	}
	
	/**
	 * @author sunji
	 * @description 初始化技术指令（修改）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:instruction:main:02")
	@RequestMapping("intoEditMainInstruction")
	public String intoEditMainInstruction(Model model,@RequestParam String id,@RequestParam String dprtcode) throws BusinessException {
		try {
			Instruction instruction=instructionService.getByPrimaryKey(id);
			if(instruction==null){
				throw new BusinessException("查询失败");
			}
			instruction.setZtText(JobCardStatusEnum.getName(instruction.getZt()));
			model.addAttribute("instruction", instruction);
			model.addAttribute("userList", userService.getByDprtcode(dprtcode));
			
			//判断状态是否是保存或者是提交，分别返回到不同的页面
			if(instruction.getZt()==OrderSourceEnum.EVALUATED.getId() || instruction.getZt()==OrderSourceEnum.CHECKED.getId()
					|| instruction.getZt()==OrderSourceEnum.APPROVE.getId()){
				return "project/instruction/edit_instruction_tj";
			}else{
				return "project/instruction/edit_instruction_bc";
				
			}
		} catch (Exception e) {
			 throw new BusinessException("查询失败!");
		}
		
		
		
		
	}
	/**
	 * @author sunji
	 * @description 删除
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "deleteOrderSource", method = RequestMethod.POST)
	public Object deleteOrderSource(OrderSource orderSource ,HttpServletRequest request){
		Map<String, Object> resultMap=orderSourceService.deleteOrderSource(orderSource);
		return resultMap;
	}
	
	/**
	 * 修改(保存)
	 * 
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "updateMainInstruction", method = RequestMethod.POST)
	public String updateMainInstruction(@RequestBody Instruction instruction,HttpServletRequest request) throws BusinessException{
		try {
			int []i={OrderSourceEnum.NOT_EVALUATED.getId(),OrderSourceEnum.REVIEW_THE_REJECTED.getId(),OrderSourceEnum.APPROVE_TURN_.getId()};
			instructionService.updateInstruction(instruction,i);
			return instruction.getId();
		} catch (BusinessException e) {
			 throw e;
		}
	}
	/**
	 * 修改（提交）
	 * 
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "editMainInstruction", method = RequestMethod.POST)
	public String editMainInstruction(@RequestBody Instruction instruction,HttpServletRequest request) throws BusinessException{
		try {
			int []i={OrderSourceEnum.NOT_EVALUATED.getId(),
					OrderSourceEnum.EVALUATED.getId(),
					OrderSourceEnum.CHECKED.getId(),
					OrderSourceEnum.APPROVE.getId(),
					OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
					OrderSourceEnum.APPROVE_TURN_.getId()};
			instructionService.updateInstruction(instruction,i);
			return instruction.getId();
		} catch (BusinessException e) {
			 throw e;
		}
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令(查询)
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoViewMainInstruction")
	public String intoViewMainInstruction(Model model,@RequestParam String id,@RequestParam String dprtcode) {
		Instruction instruction=instructionService.getByPrimaryKey(id);
		
		if(instruction!=null){
			instruction.setZtText(JobCardStatusEnum.getName(instruction.getZt()));
		}
		model.addAttribute("instruction", instruction);
		model.addAttribute("userList", userService.getByDprtcode(dprtcode));
		return "project/instruction/view_instruction";
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令（审核）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 *
	 */
	@Privilege(code="project:instruction:main:05")
	@RequestMapping("intoShenheMainInstruction")
	public String intoShenheMainInstruction(Model model,@RequestParam String id,@RequestParam String dprtcode
			) throws BusinessException {
		Instruction instruction=instructionService.getByPrimaryKey(id);
		if(instruction==null){
			throw new BusinessException("查询失败");
		}
		model.addAttribute("instruction", instruction);
		model.addAttribute("userList", userService.getByDprtcode(dprtcode));
		//判断状态是否是保存或者是提交，分别返回到不同的页面
		return "project/instruction/shenhe_instruction";
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令（批复）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 *
	 */
	@Privilege(code="project:instruction:main:06")
	@RequestMapping("intoPifuMainInstruction")
	public String intoPifuMainInstruction(Model model,@RequestParam String id,@RequestParam String dprtcode
			) throws BusinessException {
		Instruction instruction=instructionService.getByPrimaryKey(id);
		if(instruction==null){
			throw new BusinessException("查询失败");
		}
		model.addAttribute("instruction", instruction);
		model.addAttribute("userList", userService.getByDprtcode(dprtcode));
		//判断状态是否是保存或者是提交，分别返回到不同的页面
		return "project/instruction/pifu_instruction";
	}
	
	
	/**
	 * @author sunji
	 * @description 关闭
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("offInstruction")
	@Privilege(code="project:instruction:main:04")
	public Map<String, Object> offInstruction(@RequestBody Instruction instruction,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = instructionService.offInstruction(SessionUtil.getUserFromSession(request),instruction);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("deleteInstruction")
	@Privilege(code="project:instruction:main:03")
	public Map<String, Object> deleteInstruction(HttpServletRequest request,String id
			) throws Exception {
		Map<String, Object> resultMap = instructionService.deleteInstruction(SessionUtil.getUserFromSession(request),id);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（审核提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "submitshenheMainInstruction", method = RequestMethod.POST)
	public String submitshenheMainInstruction(@RequestBody Instruction instruction,HttpServletRequest request) throws BusinessException{
		try {
			instructionService.updateshenheInstruction(instruction);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(),e);
		}
		return instruction.getId();
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（批复提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "submitpifuMainInstruction", method = RequestMethod.POST)
	public String submitpifuMainInstruction(@RequestBody Instruction instruction,HttpServletRequest request) throws BusinessException{
		try {
			instructionService.updatepifuInstruction(instruction);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(),e);
		}
		return instruction.getId();
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("obtainDrptment")
	public Map<String, Object> obtainDrptment(HttpServletRequest request,@RequestParam String id
			) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user=userService.selectByPrimaryKey(id);
		resultMap.put("user", user);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 查询该id下的工作内容
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("appendInstructionContent")
	public Map<String, Object> appendInstructionContent(HttpServletRequest request,String id
			) {
		Map <String, Object > resultMap =new HashMap<String, Object>();
		//根据id获取已选择的评估单
		List<InstructionContent> instructionContentList =instructionContentService.selectByPrimaryKeyMainId(id);
		
		resultMap.put("instructionContentList", instructionContentList);
		return resultMap;
	}
	
	/**
	 * 技术质量导出，预览<br>
	 * @todo html 预览少图片，xls导出时虚线。
	 * 预览：html<br>
	 * 预览：pdf<br>
	 * 导出:xls<br>
	 * @param id    (主键)
	 * @param type （pdf,xls,html）
	 * @param request
	 * @param model
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value="/export/{type}/{id}")
	public String export(@PathVariable String id,@PathVariable String type,HttpServletRequest request,RedirectAttributesModelMap  model) throws BusinessException {
		
		try { 
			Instruction instruction = instructionService.getByPrimaryKey(id);
			model.addFlashAttribute("id", instruction.getId());
			String logoPath = "http://".concat(request.getServerName()).concat(":")
					.concat(String.valueOf(request.getServerPort()))
					.concat(request.getContextPath())
					.concat("/static/images/report");
			model.addFlashAttribute("logo_path", logoPath);
		    return "redirect:/report/".concat(type).concat("/").concat(instruction.getDprtcode()).concat("/Instruction");
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
		
	}
	/**
	 * 修改接收状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updateJszt", method = RequestMethod.POST)
	public Map<String, Object> updateJszt(@RequestBody Instruction instruction,HttpServletRequest request) throws Exception{
		Map <String, Object > resultMap =new HashMap<String, Object>();
		try {
			instructionService.updateJszt(instruction);
		} catch (Exception e) {
			throw new BusinessException("数据修改失败",e);
		}
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 批量审核
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 */
	@Privilege(code="project:instruction:main:05")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return instructionService.updateBatchAudit(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核失败",e);
		}
	}
	/**
	 * @author liub
	 * @description 批量批准
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 */
	@Privilege(code="project:instruction:main:06")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		
		try {
			return instructionService.updateBatchApprove(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量批准失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据评估单id查询技术指令数据
	 * @param pgdid
	 * @return List<Instruction>
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPgdId", method = RequestMethod.POST)
	public List<Instruction> queryByPgdId(String pgdid)throws BusinessException {
		List<Instruction> list = null;
		try {
			return  instructionService.queryByPgdId(pgdid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
}
