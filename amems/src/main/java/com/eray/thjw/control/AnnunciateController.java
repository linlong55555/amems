package com.eray.thjw.control;

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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.Send;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;
import com.eray.thjw.service.AnnunciateService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.SendService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.JobCardStatusEnum;
import enu.ThresholdEnum;
import enu.ordersource.OrderSourceEnum;

@Controller
@RequestMapping("/project/annunciate")
public class AnnunciateController extends BaseController{

	@Autowired
	private AnnunciateService annunciateService;
	@Autowired
	private TechnicalFileService technicalFileService;
	@Autowired
	private UserService userService;
	@Autowired 
	private SendService sendService;
	@Autowired 
	private OrderSourceService orderSourceServcie;
	@Autowired 
	private OrderAttachmentService orderAttachmentService;
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	
	/**
	 * 跳转至技术通告界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="project:annunciate:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Annunciate annunciate) {
		return new ModelAndView("project/annunciate/annunciateMain");
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 页面视图system/user/userMain.jsp
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryAnnunciateList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody Annunciate annunciate,HttpServletRequest request,Model model)throws BusinessException{
		Map<String, Object> resultMap = annunciateService.queryAllAnnunciate(annunciate);
		//获取监控值
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.TGZL.getName(), annunciate.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
}
	

	/**
	 * 初始化增加技术通告
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Privilege(code="project:annunciate:main:01")
	@RequestMapping("intoAddAnnunciate")
	public ModelAndView intoAddAnnunciate(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		model.put("userList", userService.getByDprtcode(user.getJgdm()));
		return new ModelAndView("project/annunciate/add_annunciate", model);
	}
	/**
	 * 跳转至查看评估单
	 * 
	 * @return 页面视图
	 */
	@RequestMapping(value = "selecPgdAnnunciate", method = RequestMethod.GET)
	public ModelAndView selecPgdAnnunciate(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("project/annunciate/selecPgd_annunciate", model);
	}
	/**
	 * 跳转至查看评估单
	 * 
	 * @return 页面视图
	 */
	@ResponseBody
	@RequestMapping(value = "selectPgdList", method = RequestMethod.POST)
	public Object selectPgdList(@RequestBody TechnicalFile technicalFile,HttpServletRequest request,Model model){
		return technicalFileService.queryAllByDocumentsId(technicalFile);
	}
	/**
	 * 获取所有用户的id和name
	 * 
	 * @return userList
	 */
	@ResponseBody
	@RequestMapping(value = "selectUserMain", method = RequestMethod.POST)
	public Object selectUserMain(HttpServletRequest request,Model model){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		resultMap.put("userList", userService.getByDprtcode(user.getJgdm()));
		return resultMap;
	}
	/**
	 * 获取所有已选和未选用户的id和name
	 * 
	 * @return userList
	 */
	@ResponseBody
	@RequestMapping(value = "selectUserToSend", method = RequestMethod.POST)
	public Object selectUserToSend(String id,HttpServletRequest request,Model model){
		User user=ThreadVarUtil.getUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获取已选择的用户信息
		resultMap.put("userListYes", userService.selectUserToSendYes(id,user.getJgdm()));
		//获取未选择的用户信息
		resultMap.put("userListNo", userService.selectUserToSendNo(id,user.getJgdm()));
		return resultMap;
	}
	/**
	 * 保存提交技术文件
	 * 
	 * @return 
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "saveMainAnnunciate", method = RequestMethod.POST)
	public String saveMainAnnunciate(@RequestBody Annunciate annunciate,HttpServletRequest request) throws BusinessException{
		try {
			String id=annunciateService.insertAnnunciate(annunciate);
			return id;
		} catch (BusinessException e) {
			throw new BusinessException("数据添加失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,@RequestParam String id) {
		return annunciateService.checkUpdMt(SessionUtil.getUserFromSession(request),id);
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 *
	 */
	@ResponseBody
	@RequestMapping("deleteAnnunciate")
	@Privilege(code="project:annunciate:main:03")
	public Map<String, Object> deleteAnnunciate(HttpServletRequest request,String id) throws Exception {
		Map<String, Object> resultMap = annunciateService.deleteAnnunciate(SessionUtil.getUserFromSession(request),id);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 指定结束
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 *
	 */
	@ResponseBody
	@RequestMapping("offAnnunciate")
	@Privilege(code="project:annunciate:main:04")
	public Map<String, Object> offAnnunciate(@RequestBody Annunciate annunciate, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = annunciateService.offAnnunciate(SessionUtil.getUserFromSession(request),annunciate);
		return resultMap;
	}
	
	/**
	 * @author sunji
	 * @description 初始化技术通告（修改）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:annunciate:main:02")
	@RequestMapping("intoEditMainAnnunciate")
	public String intoEditMainAnnunciate(Model model,@RequestParam String id,@RequestParam String dprtcode) throws BusinessException {
		Annunciate annunciate=annunciateService.getByPrimaryKey(id);
		if(annunciate==null){
			throw new BusinessException("维护提示不存在");
		}
		model.addAttribute("annunciate", annunciate);
		//获取已选择的用户信息
		model.addAttribute("userListYes", userService.selectUserToSendYes(annunciate.getId(),annunciate.getDprtcode()));
		//获取未选择的用户信息
		model.addAttribute("userListNo", userService.selectUserToSendNo(annunciate.getId(),annunciate.getDprtcode()));
		//判断状态是否是保存或者是提交，分别返回到不同的页面
		if(annunciate.getZt()==1 || annunciate.getZt()==2 || annunciate.getZt()==3){
			return "project/annunciate/edit_annunciate_tj";
		}else{
			return "project/annunciate/edit_annunciate_bc";
			
		}
	}
	/**
	 * @author sunji
	 * @description 初始化技术通告（审核）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:annunciate:main:05")
	@RequestMapping("intoShenheMainAnnunciate")
	public String intoShenheMainAnnunciate(Model model,@RequestParam String id,@RequestParam  String dprtcode
			) throws BusinessException {
		Annunciate annunciate=annunciateService.getByPrimaryKey(id);
		if(annunciate==null){
			throw new BusinessException("维护提示查询失败");
		}
		annunciate.setZtText(JobCardStatusEnum.getName(annunciate.getZt()));
		model.addAttribute("annunciate", annunciate);
		
		//获取已选择的用户信息
		model.addAttribute("userListYes", userService.selectUserToSendYes(annunciate.getId(),annunciate.getDprtcode()));
		//获取未选择的用户信息
		model.addAttribute("userListNo", userService.selectUserToSendNo(annunciate.getId(),annunciate.getDprtcode()));
			
			return "project/annunciate/shenhe_annunciate";
	}
	/**
	 * @author sunji
	 * @description 初始化技术通告（批复）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:annunciate:main:06")
	@RequestMapping("intoPifuMainAnnunciate")
	//@Privilege(code = "thjw_person_add" , type = PrivilegeTypeEnum.BUTTON)
	public String intoPifuMainAnnunciate(Model model,String id,@RequestParam  String dprtcode
			) throws BusinessException {

		Annunciate annunciate=annunciateService.getByPrimaryKey(id);
		if(annunciate==null){
			throw new BusinessException("维护提示查询失败");
		}
		annunciate.setZtText(JobCardStatusEnum.getName(annunciate.getZt()));
		model.addAttribute("annunciate", annunciate);
		
		//获取已选择的用户信息
		model.addAttribute("userListYes", userService.selectUserToSendYes(annunciate.getId(),annunciate.getDprtcode()));
		//获取未选择的用户信息
		model.addAttribute("userListNo", userService.selectUserToSendNo(annunciate.getId(),annunciate.getDprtcode()));
		
			return "project/annunciate/pifu_annunciate";
	}
	
	/**
	 * @author sunji
	 * @description 初始化技术通告(查询)
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoViewMainAnnunciate")
	public String intoViewMainAnnunciate(Model model,String id
			) throws BusinessException {
		Annunciate annunciate=annunciateService.getByPrimaryKey(id);
		if(annunciate==null){
			throw new BusinessException("维护提示查询失败");
		}
		annunciate.setZtText(JobCardStatusEnum.getName(annunciate.getZt()));
		model.addAttribute("annunciate", annunciate);
		//获取已选择的用户信息
		model.addAttribute("userListYes", userService.selectUserToSendYes(annunciate.getId(),annunciate.getDprtcode()));
		//获取未选择的用户信息
		model.addAttribute("userListNo", userService.selectUserToSendNo(annunciate.getId(),annunciate.getDprtcode()));
		
		return "project/annunciate/view_annunciate";
	}
	/**
	 * @author sunji
	 * @description 查询已选择的评估单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("selectChoosePgd")
	public Map<String, Object> selectChoosePgd(HttpServletRequest request,@RequestParam String id) {
		Map <String, Object > resultMap =new HashMap<String, Object>();
		//根据id获取已选择的评估单
		List<TechnicalFile> tchnicalFileList =annunciateService.selectChoosePgd(id);
		
		resultMap.put("tchnicalFileList", tchnicalFileList);
		return resultMap;
	}
	/**
	 * 保存提交技术文件
	 * 
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "updateAnnunciate", method = RequestMethod.POST)
	public Object updateAnnunciate(@RequestBody Map<String, Object> map,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap=annunciateService.updateAnnunciate(map);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description  查看圈阅情况
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value ="selectSend", method = RequestMethod.POST)
	public Object selectSend(@RequestBody Send send,HttpServletRequest request) throws BusinessException {
		PageHelper.startPage(send.getPagination());
		List<Send> list = sendService.querySendAll(send);
		return PageUtil.pack4PageHelper(list, send.getPagination());
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
		Map<String, Object> resultMap=orderSourceServcie.deleteOrderSource(orderSource);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（保存）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "modifyAnnunciate", method = RequestMethod.POST)
	public String modifyAnnunciate(@RequestBody Annunciate annunciate,HttpServletRequest request) throws Exception{
		try {
			int []i={OrderSourceEnum.NOT_EVALUATED.getId(),
					OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
					OrderSourceEnum.APPROVE_TURN_.getId()};
			annunciateService.modifyAnnunciate(annunciate,i);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败",e);
		}
		return annunciate.getId();
		
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（提交）
	 * @param ids
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "editAnnunciate", method = RequestMethod.POST)
	public String editAnnunciate(@RequestBody Annunciate annunciate,HttpServletRequest request) throws Exception{
		try {
			int []i={OrderSourceEnum.NOT_EVALUATED.getId(),
					OrderSourceEnum.EVALUATED.getId(),
					OrderSourceEnum.CHECKED.getId(),
					OrderSourceEnum.APPROVE.getId(),
					OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
					OrderSourceEnum.APPROVE_TURN_.getId()};
			annunciateService.modifyAnnunciate(annunciate,i);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("操作失败",e);
		}
		return annunciate.getId();
		
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（审核提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value ="submitshenheMainAnnunciate", method = RequestMethod.POST)
	public String submitshenheMainAnnunciate(@RequestBody Annunciate annunciate,HttpServletRequest request) throws BusinessException{
		try {
			annunciateService.updateshenheAnnunciate(annunciate);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(),e);
		}
		return annunciate.getId();
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（批复提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "submitpifuMainAnnunciate", method = RequestMethod.POST)
	public String submitpifuMainAnnunciate(@RequestBody Annunciate annunciate,HttpServletRequest request) throws BusinessException{
		try {
			annunciateService.updatepifuAnnunciate(annunciate);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(),e);
		}
		return annunciate.getId();
	}
	/**
	 * @author sunji
	 * @description  根据编号查询数据
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 */
	@RequestMapping("selectByNum/{jstgh}")
	public ModelAndView selectByNum(@PathVariable("jstgh")String jstgh,HttpServletRequest request,
			HttpServletResponse resp) throws  BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Annunciate annunciate=annunciateService.selectByNum(jstgh);  
			model.put("annunciate", annunciate);
		} catch (Exception e) {
			throw new BusinessException("查询失败");
		}
		return new ModelAndView("/project/annunciate/view_annunciate_tgbh", model);
	}
	
	/**
	 * @author sunji
	 * @description 获取上传附件
	 */
	@ResponseBody
	@RequestMapping(value = "selectedScwjList", method = RequestMethod.POST)
	public Map<String, Object> selectedScwjList(@RequestParam String mainid, Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	    try{
	    	@SuppressWarnings("unused")
	    	User user = ThreadVarUtil.getUser();             // 获取当前登入人对象
	    	List<OrderAttachment> orderAttachmentList=orderAttachmentService.queryAll(mainid);
	    	resultMap.put("rows", orderAttachmentList);
		}catch(Exception e){ 
			throw new BusinessException("上传附件加载失败"); }
		return resultMap;
	}

	@RequestMapping(value="/export/{type}/{id}")
	public String export(@PathVariable String id,@PathVariable String type,HttpServletRequest request,Model model) throws BusinessException {
		try {
			Annunciate annunciate = annunciateService.getByPrimaryKey(id);
			
			model.addAttribute("jstgh", annunciate.getJstgh());
			String zdr = annunciate.getZdr_user()!=null?annunciate.getZdr_user().getRealname():"";
			String shr = annunciate.getShr_user()!=null?annunciate.getShr_user().getRealname():"";
			String pfr = annunciate.getPfr_user()!=null?annunciate.getPfr_user().getRealname():"";
			String fcbm = annunciate.getBm_dprt()!=null?annunciate.getBm_dprt().getDprtname():"";
			
			String nr="(&)nbsp;(&)nbsp;"+annunciate.getNr();
		    model.addAttribute("zdr", zdr);
		    model.addAttribute("shr", shr);
		    model.addAttribute("pfr", pfr);
		    model.addAttribute("fcbm", fcbm);
		    model.addAttribute("ckzl", annunciate.getCkzl());
		    model.addAttribute("zhut", annunciate.getZhut());
		    model.addAttribute("nr", annunciate.getNr());
		    
		    model.addAttribute("sxrq", annunciate.getSxrq()==null?"":DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, annunciate.getSxrq()));
		    model.addAttribute("bb", annunciate.getBb());
		    
		    return outReport(type, annunciate.getDprtcode(), "Annunciate", model);
		} catch (Exception e) {
			e.printStackTrace();
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
	public Map<String, Object> updateJszt(@RequestBody Send send,HttpServletRequest request) throws Exception{
		Map <String, Object > resultMap =new HashMap<String, Object>();
		try {
			sendService.updateJszt(send);
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
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:annunciate:main:05")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		
		try {
			return annunciateService.updateBatchAudit(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 批量批准
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:annunciate:main:06")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return annunciateService.updateBatchApprove(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量批准失败",e);
		}
	}
	
	/**
	 * @author liub 
	 * @description 根据评估单id查询技术通告数据
	 * @param pgdid
	 * @return List<Annunciate>
	 * @develop date 2017.03.14
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPgdId", method = RequestMethod.POST)
	public List<Annunciate> queryByPgdId(String pgdid)throws BusinessException {
		try {
			return annunciateService.queryByPgdId(pgdid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
}
