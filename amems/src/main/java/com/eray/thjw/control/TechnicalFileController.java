package com.eray.thjw.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Affected;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.Instruction;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.QualityClose;
import com.eray.thjw.po.RevisionNoticeBook;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.TechnicalUpload;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.AffectedService;
import com.eray.thjw.service.AnnunciateService;
import com.eray.thjw.service.EngineeringService;
import com.eray.thjw.service.InstructionService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.RevisionNoticeBookService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.service.TechnicalUploadService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.OrderZtEnum;
import enu.ThresholdEnum;
import enu.ZtEnum;

/**
 * @author sunji
 * @description 技术文件
 */
@Controller
@RequestMapping("/project/technicalfile")
public class TechnicalFileController extends BaseController {

	/**
	 * @author sunji
	 * @description 技术文件
	 */
	@Resource
	private TechnicalFileService technicalFileService;

	@Resource
	private UserService userService;

	@Resource
	private OrderSourceService orderSourceService; // 评估指令中间service

	@Autowired
	private TechnicalUploadService technicalUploadService;

	@Autowired
	private AffectedService affectedService;// 受影响的列
	@Autowired
	private InstructionService instructionService;
	@Autowired
	private AnnunciateService annunciateService;
	@Autowired
	private RevisionNoticeBookService revisionNoticeBookService;
	@Autowired
	private EngineeringService engineeringService;
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	
	private final static int max = 100;


	/**
	 * 跳转至文件上传
	 * @return 页面视图
	 */
	@Privilege(code = "project:technicalfile:mainupload")
	@RequestMapping(value = "mainupload", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		// 获取该组织机构的所有机型工程师
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.put("users", users);
		return new ModelAndView("/project/technicalfile/technicalfile_upload_list", model);
	}

	/**
	 * @author sunji
	 * @description
	 * @param request,model
	 * @return 文件上传列表
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryTechnicalFileListUpload", method = RequestMethod.POST)
	public Map<String, Object> queryTechnicalFileList(@RequestBody TechnicalFile technicalFile,
			HttpServletRequest request, Model model) throws BusinessException {
		Map<String, Object> resultMap = technicalFileService.queryAllTechnicalfile(technicalFile);
		//获取监控设置的伐值
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.JSWJ.getName(), technicalFile.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
	}
	/**
	 * 初始化文件新增界面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("intoAddTechnicalFileUpload")
	public ModelAndView intoAddRole(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return new ModelAndView("/project/technicalfile/add_technicalfile_upload");
	}

	/**
	 * 增加文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("addTechnicalFileUpload")
	public Map<String, Object> addTechnicalFileUpload(HttpServletRequest request, HttpServletResponse response,
			TechnicalFile technicalFile) throws Exception {

		String newFileName = request.getParameter("newFileName");
		String relativePath = request.getParameter("relativePath");
		String fileName = request.getParameter("fileName");
		String wjdx = request.getParameter("wjdx");
		
		String sm = request.getParameter("sm");
		TechnicalUpload technicalUpload = new TechnicalUpload();
		if(null!=newFileName && !"".equals(newFileName)){
			technicalUpload.setYswjm(fileName);
			technicalUpload.setWbwjm(sm);
			technicalUpload.setNbwjm(newFileName);
			technicalUpload.setCflj(relativePath);
			technicalUpload.setWjdx(Double.valueOf(wjdx));
		
			String hzm=newFileName.substring(newFileName.lastIndexOf(".")+1,newFileName.length());
			technicalUpload.setHzm(hzm);
		}

		return technicalFileService.save(technicalFile, technicalUpload);
	}

	/**
	 * @author liub
	 * @description 检查技术文件上传权限
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request, String id) throws Exception {
		return technicalFileService.checkUpdMt(SessionUtil.getUserFromSession(request), id);
	}

	/**
	 * @author liub
	 * @description 检查技术文件上传修改
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt1")
	public Map<String, Object> checkUpdMt1(HttpServletRequest request, String id) throws Exception {
		return technicalFileService.checkUpdMt1(SessionUtil.getUserFromSession(request), id);
	}

	/**
	 * @author liub
	 * @description 检查技术文件评估权限
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMtAssess")
	public Map<String, Object> checkUpdMtAssess(HttpServletRequest request, String id) throws Exception {
		return technicalFileService.checkUpdMtAssess(SessionUtil.getUserFromSession(request), id);
	}

	/**
	 * @author liub
	 * @description 检查技术文件评估审核
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMtAudit")
	public Map<String, Object> checkUpdMtAudit(HttpServletRequest request, String id) throws Exception {
		return technicalFileService.checkUpdMtAudit(SessionUtil.getUserFromSession(request), id);
	}

	/**
	 * @author liub
	 * @description 检查技术文件评估审批
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMtApproval")
	public Map<String, Object> checkUpdMtApproval(HttpServletRequest request, String id) throws Exception {
		return technicalFileService.checkUpdMtApproval(SessionUtil.getUserFromSession(request), id);
	}

	/**
	 * 初始化修改文件上传
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("initModifyFileUpload")
	public ModelAndView initModifyFileUpload(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id查询数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		// 获取机型工程师
		User user = SessionUtil.getUserFromSession(request);
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.put("users", users);
		//获取上传文件
		TechnicalUpload technicalUpload= technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);

		return new ModelAndView("/project/technicalfile/modify_technicalfile_upload", model);
	}

	/**
	 * 修改文件上传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("modifyModifyFileUpload")
	public Map<String, Object> modifyModifyFileUpload(HttpServletRequest request, HttpServletResponse response,
			TechnicalFile technicalFile) throws Exception {

		String newFileName = request.getParameter("newFileName");
		String relativePath = request.getParameter("relativePath");
		String fileName = request.getParameter("fileName");
		String technicalUploadId = request.getParameter("technicalUploadId");
		String sm = request.getParameter("sm");
		String wjdx = request.getParameter("wjdx");
		TechnicalUpload technicalUpload = new TechnicalUpload();
		technicalUpload.setMainid(technicalFile.getId());
		technicalUpload.setWbwjm(sm);
		if (!newFileName.equals("")) {
			technicalUpload.setId(technicalUploadId);
			technicalUpload.setYswjm(fileName);
			technicalUpload.setNbwjm(newFileName);
			technicalUpload.setCflj(relativePath);
			technicalUpload.setWjdx(Double.valueOf(wjdx));
			String hzm=newFileName.substring(newFileName.lastIndexOf(".")+1,newFileName.length());
			technicalUpload.setHzm(hzm);
		}

		return technicalFileService.modify(technicalFile, technicalUpload);
	}

	/**
	 * 作废技术文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("removeModifyFileUpload/{ids}")
	public Map<String, Object> removeModifyFileUpload(@PathVariable("ids") String items, HttpServletRequest request,
			HttpServletResponse response, TechnicalFile technicalFile) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		String[] item = items.split(",");
		for (int i = 0; i < item.length; i++) {
			technicalFile.setId(item[i]);
			try {
				TechnicalFile technicalFile1 = technicalFileService.queryAll(technicalFile);
				resultMap = validatePri(user, technicalFile1); // 验证
				if (resultMap.get("state").equals("success")) {
					technicalFileService.delete(technicalFile);
					resultMap.put("state", "Success");
				}

			} catch (Exception e) {
				resultMap.put("state", "Failure");
			}
		}

		return resultMap;
	}

	/**
	 * @author liub
	 * @description 验证维修方案操作权限
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePri(User user, TechnicalFile technicalFile) {

		Map<String, Object> returnMap = new HashMap<String, Object>();// 返回数据map

		// 验证技术文件是否存在
		if (null == technicalFile) {
			returnMap.put("state", "error");
			returnMap.put("message", "技术文件不存在!");
			return returnMap;
		}
		// 验证是否生效
		Integer zt = technicalFile.getZt();
		if (ZtEnum.NOT_EVALUATED.getId().intValue() == zt.intValue() || ZtEnum.REVIEW_THE_REJECTED.getId().intValue() == zt.intValue()
				|| ZtEnum.APPROVE_TURN_.getId().intValue() == zt.intValue()) {
			returnMap.put("state", "success");
		} else {
			returnMap.put("state", "error");
			returnMap.put("message", "该技术文件已更新，请刷新后再进行操作");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * 跳转至文件评估
	 * 
	 * @return 页面视图
	 */
	@Privilege(code = "project:technicalfile:mainassess")
	@RequestMapping(value = "mainassess", method = RequestMethod.GET)
	public ModelAndView mainassess(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		// 获取该组织机构的所有机型工程师
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.put("users", users);
		return new ModelAndView("/project/technicalfile/technicalfile_assess_list", model);
	}

	/**
	 * @author 查询受影响的列
	 * @description
	 * @param djbh,model
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByDjxmbhid", method = { RequestMethod.POST, RequestMethod.GET })
	public List<Map<String, Object>> queryListByDjxmbhid(Model model, String djxmid) throws BusinessException {
		List<Map<String, Object>> list = null;
		try {
			list = affectedService.queryListByDjxmbhid(djxmid);
		} catch (Exception e) {
			throw new BusinessException("获取受影响列失败!");
		} finally {
		}

		return list;
	}

	/**
	 * @author
	 * @description
	 * @param request,model
	 * @return 文件评估列表
	 * @throws Exception
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryTechnicalFileListAssess", method = RequestMethod.POST)
	public Map<String, Object> queryTechnicalFileListAssess(@RequestBody TechnicalFile technicalFile,
			HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> resultMap = technicalFileService.queryAllTechnicalfile(technicalFile);
		//获取监控设置的伐值
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.JSWJ.getName(), technicalFile.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
	}

	/**
	 * 初始化技术文件评估
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("initAssessFileUpload")
	public ModelAndView initAssessFileUpload(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/assess_technicalfile", model);
	}

	/**
	 * 初始化技术文件审核
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("initAuditFileUpload")
	public ModelAndView initAuditFileUpload(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/audit_technicalfile", model);
	}

	/**
	 * 技术文件评估-保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileAssess", method = RequestMethod.POST)
	public Map<String, Object> modifyModifyFileAssess(@RequestBody TechnicalFile technicalFile) throws Exception {

		User user = ThreadVarUtil.getUser();
		technicalFile.setPgbmid2(user.getBmdm());
		technicalFile.setPgsj(new Date());
		return technicalFileService.assess(technicalFile);
	}

	/**
	 * 技术文件评估-评估
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileAssesssub", method = RequestMethod.POST)
	public Map<String, Object> modifyModifyFileAssesssub(@RequestBody TechnicalFile technicalFile) throws Exception {

		User user = ThreadVarUtil.getUser();
		technicalFile.setPgbmid2(user.getBmdm());
		technicalFile.setZt(ZtEnum.EVALUATED.getId());// 已评估
		technicalFile.setPgsj(new Date());
		return technicalFileService.assess(technicalFile);
	}

	/**
	 * 跳转至文件审核
	 * 
	 * @return 页面视图
	 */
	@Privilege(code = "project:technicalfile:mainaudit")
	@RequestMapping(value = "mainaudit", method = RequestMethod.GET)
	public ModelAndView mainaudit(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		// 获取该组织机构的所有机型工程师
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.put("users", users);
		return new ModelAndView("/project/technicalfile/technicalfile_audit_list", model);

	}

	/**
	 * @author
	 * @description
	 * @param request,model
	 * @return 文件审核列表
	 * @throws Exception
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryTechnicalFileListAudit", method = RequestMethod.POST)
	public Map<String, Object> queryTechnicalFileListAudit(@RequestBody TechnicalFile technicalFile,
			HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> resultMap = technicalFileService.queryAllTechnicalfile(technicalFile);
		//获取监控设置的伐值
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.JSWJ.getName(), technicalFile.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
	}

	/**
	 * 查看技术文件
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("findTechnicalFileUpload/{id}/{dprtcode}")
	public ModelAndView findTechnicalFileUpload(@PathVariable("id") String id, @PathVariable("dprtcode") String dprtcode, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);

		return new ModelAndView("/project/technicalfile/find_upload_technicalfile", model);
	}

	/**
	 * 查看技术文件评估
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("findAssessFileUpload/{id}/{dprtcode}")
	public ModelAndView findAssessFileUpload(@PathVariable("id") String id,@PathVariable("dprtcode") String dprtcode, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/find_assess_technicalfile", model);
	}

	/**
	 * 技术文件审核-同意
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileAudit", method = { RequestMethod.POST })
	public Map<String, Object> modifyModifyFileAudit(@RequestBody TechnicalFile technicalFile) throws Exception {

		User user = ThreadVarUtil.getUser();
		technicalFile.setShbmid(user.getBmdm());
		technicalFile.setZt(ZtEnum.CHECKED.getId());// 已审核
		technicalFile.setShsj(new Date());
		technicalFile.setShrid(user.getId().toString());
		return technicalFileService.updateShenhe(technicalFile);
	}

	/**
	 * 技术文件审核-驳回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileAuditBh", method = { RequestMethod.POST })
	public Map<String, Object> modifyModifyFileAuditBh(@RequestBody TechnicalFile technicalFile) throws Exception {
		User user = ThreadVarUtil.getUser();
		technicalFile.setShbmid(user.getBmdm());
		technicalFile.setZt(ZtEnum.REVIEW_THE_REJECTED.getId());// 已审核
		technicalFile.setShsj(new Date());
		technicalFile.setShrid(user.getId().toString());
		return technicalFileService.updateShenhe(technicalFile);
	}

	/**
	 * 技术文件审核-指定结束
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileStop", method = { RequestMethod.POST })
	public Map<String, Object> modifyModifyFileStop(@RequestBody TechnicalFile technicalFile) throws Exception {

		User user = ThreadVarUtil.getUser();
		technicalFile.setZt(ZtEnum.CLOSE.getId());// 结束
		technicalFile.setZdjsrq("1");
		technicalFile.setZdjsrid(user.getId().toString());
		return technicalFileService.updateShenhe(technicalFile);
	}

	/**
	 * 跳转至文件审批
	 * 
	 * @return 页面视图
	 */
	@Privilege(code = "project:technicalfile:mainapproval")
	@RequestMapping(value = "mainapproval", method = RequestMethod.GET)
	public ModelAndView mainapproval(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		// 获取该组织机构的所有机型工程师
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.put("users", users);
		return new ModelAndView("/project/technicalfile/technicalfile_approval_list", model);
	}

	/**
	 * @author
	 * @description
	 * @param request,model
	 * @return 文件审批列表
	 * @throws Exception
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryTechnicalFileListApproval", method = RequestMethod.POST)
	public Map<String, Object> queryTechnicalFileListApproval(@RequestBody TechnicalFile technicalFile,
			HttpServletRequest request, Model model) throws Exception {
		Map<String, Object> resultMap = technicalFileService.queryAllTechnicalfile(technicalFile);
		//获取监控设置的伐值
		Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.JSWJ.getName(), technicalFile.getDprtcode());
		resultMap.put("monitorsettings", monitorsettings);
		return resultMap;
	}

	/**
	 * 初始化技术文件审批
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("initApprovalFileUpload")
	public ModelAndView initApprovalFileUpload(@RequestParam String id, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/approval_technicalfile", model);
	}

	/**
	 * 技术文件审批-同意
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileApproval", method = { RequestMethod.POST })
	public Map<String, Object> modifyModifyFileApproval(@RequestBody TechnicalFile technicalFile) throws Exception {
		User user = ThreadVarUtil.getUser();
		technicalFile.setPfbmid(user.getBmdm());
		technicalFile.setZt(ZtEnum.APPROVE.getId());// 已审批
		technicalFile.setPfsj(new Date());
		technicalFile.setPfrid(user.getId().toString());
		return technicalFileService.updateShenhe(technicalFile);
	}

	/**
	 * 技术文件审批-驳回
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileApprovalBh", method = { RequestMethod.POST })
	public Map<String, Object> modifyModifyFileApprovalBh(@RequestBody TechnicalFile technicalFile) throws Exception {


		User user = ThreadVarUtil.getUser();
		technicalFile.setPfbmid(user.getBmdm());
		technicalFile.setZt(ZtEnum.APPROVE_TURN_.getId());// 驳回
		technicalFile.setPfsj(new Date());
		technicalFile.setPfrid(user.getId().toString());
		return technicalFileService.updateShenhe(technicalFile);
	}

	/**
	 * 技术文件审批-中止
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyModifyFileApprovalStop", method = { RequestMethod.POST })
	public Map<String, Object> modifyModifyFileApprovalStop(@RequestBody TechnicalFile technicalFile) throws Exception {


		User user = ThreadVarUtil.getUser();
		technicalFile.setPfbmid(user.getBmdm());
		technicalFile.setZt(ZtEnum.SUSPEND.getId());// 驳回
		technicalFile.setPfsj(new Date());
		technicalFile.setPfrid(user.getId().toString());
		return technicalFileService.updateShenhe(technicalFile);
	}

	/**
	 * 查看技术文件审核
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("findAuditFileUpload/{id}/{dprtcode}")
	public ModelAndView findAuditFileUpload(@PathVariable("id") String id,@PathVariable("dprtcode") String dprtcode, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/find_audit_technicalfile", model);
	}

	/**
	 * 查看技术文件审批
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("findApprovalFileUpload/{id}/{dprtcode}")
	public ModelAndView findApprovalFileUpload(@PathVariable("id") String id, @PathVariable("dprtcode") String dprtcode,HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/find_approval_technicalfile", model);
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
	public Map<String, Object> selectChoosePgd(HttpServletRequest request, String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 根据id获取已选择的评估单
		List<TechnicalFile> tchnicalFileList = technicalFileService.selectChoosePgd(id);

		resultMap.put("tchnicalFileList", tchnicalFileList);
		return resultMap;
	}

	public String getDiretory(String fileType) {

		StringBuffer directory = new StringBuffer();
		Format format = new SimpleDateFormat("yyyyMMdd");
		directory.append(File.separator);
		directory.append(fileType.toLowerCase());
		directory.append(File.separator);
		directory.append(format.format(new Date()));
		return directory.toString();
	}

	protected static final String SUCCESS = "success";
	protected static final String MESSAGE = "message";
	protected static final String ATTACHMENT = "attachments";

	/**
	 * 异步附件上传
	 * 
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ajaxUploadFile")
	public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response,
			@RequestParam(value = "fileType", required = true) String fileType) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String fileName = null;
		String newFileName = null;
		String contentType = null;
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String relativePath = File.separator.concat(getDiretory(fileType));
		String direcory = rootPath.concat(relativePath);

		File rootPathFile = new File(direcory);
		InputStream is = null;
		FileOutputStream fos = null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (!rootPathFile.exists()) {
			rootPathFile.mkdirs();
		}
		try {
			String uuid = null;
			for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile file = multipartRequest.getFile(key);
				if (null!=file&&file.getOriginalFilename().length() > 0) {


					// 截取过长的文件名
					if (file.getOriginalFilename().length() <= max) {
						fileName = file.getOriginalFilename();
					} else {
						String subfix = "";
						if (file.getOriginalFilename().contains(".")) {
							int idx = file.getOriginalFilename().lastIndexOf(".");
							subfix = file.getOriginalFilename().substring(idx);
							fileName = file.getOriginalFilename().substring(0, max - subfix.length()).concat(subfix);
						} else {
							fileName = file.getOriginalFilename().substring(0, 100);
						}
					}

					contentType = file.getContentType();
					if (file != null && !StringUtils.isEmpty(fileName)) {

						newFileName = UUID.randomUUID().toString();
						is = file.getInputStream();
						fos = new FileOutputStream(direcory.concat(File.separator).concat(newFileName));
						FileCopyUtils.copy(is, fos);

						uuid = UUID.randomUUID().toString();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("wjdx", Utils.FileUtil.bytes2unitK(file.getSize()));
						map.put("contentType", contentType);
						map.put("uuid", uuid);
						map.put("newFileName", newFileName);
						map.put("fileName", fileName);
						map.put("relativePath", relativePath);
						listMap.add(map);
					}

				}
			}
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, listMap);
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "附件上传失败");
		}

		return result;
	}

	@ResponseBody
	@RequestMapping("changeContent")
	public Map<String, Object> changeContent(@RequestBody User user) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取该组织机构的所有机型工程师和制单人
		// 获取登入user
		List<User> users = userService.queryUserAll(user);
		result.put("userToRole", users);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "validationWjbh", method = RequestMethod.POST)
	public Object validationWjbh(@RequestParam String shzltgh, @RequestParam String oldshzltgh,@RequestParam String dprtcode,
			HttpServletRequest request) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			TechnicalFile technicalFile = new TechnicalFile();
			technicalFile.setShzltgh(shzltgh);
			technicalFile.setOldshzltgh(oldshzltgh);
			// 获取当前登入人对象
			technicalFile.setDprtcode(dprtcode);
			int i = technicalFileService.validationQueryCount(technicalFile);
			resultMap.put("total", i);
		} catch (Exception e) {
			throw new BusinessException("查询失败");
		}
		return resultMap;
	}

	/**
	 * 查看技术文件审批
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("selectByzlbh")
	public String selectByzlbh(@RequestParam("zlbh") String zlbh,@RequestParam("dprtcode") String dprtcode) throws Exception {
		// 获取当前登入人对象
		OrderSource orderSource = orderSourceService.selectByZlbhKey(zlbh, dprtcode);
		// 指令类型：1技术通告、2技术指令、3修订通知书、4非例行工单、6工程指令EO
		if(orderSource==null){
			throw new BusinessException("查询失败");
		}
		
		if (orderSource.getZlxl() == 1) {

			return "redirect:/project/annunciate/intoViewMainAnnunciate?id=" + orderSource.getZlid()+"&dprtcode="+dprtcode;
		} else if (orderSource.getZlxl() == 2) {

			return "redirect:/project/instruction/intoViewMainInstruction?id=" + orderSource.getZlid()+"&dprtcode="+dprtcode;
		} else if (orderSource.getZlxl() == 3) {

			return "redirect:/project/revisionNoticeBook/intoViewRevisionNoticeBook?id=" + orderSource.getZlid()+"&dprtcode="+dprtcode;
		} else if (orderSource.getZlxl() == 4) {

			return "redirect:/project/workorder/LookedWo?id=" + orderSource.getZlid() + "&gddlx=" + 2;
		} else if (orderSource.getZlxl() == 6) {

			return "redirect:/project/engineering/intoViewMainEngineering?id=" + orderSource.getZlid()+"&dprtcode="+dprtcode;
		}

		return "project/technicalfile/main";
	}
	

	@RequestMapping(value = "/export/{type}/{id}")
	public String export(@PathVariable String id, @PathVariable String type, HttpServletRequest request,Model model) throws BusinessException {

		try {
			TechnicalFile technicalFile = technicalFileService.selectTechnicalFileByPrimaryKey(id);
			model.addAttribute("id", id);
			String path = "http://".concat(request.getServerName()).concat(":")
					.concat(String.valueOf(request.getServerPort())).concat(request.getContextPath())
					.concat("/static/images/report/");
			model.addAttribute("logo", path.concat("south_logo.jpg"));
			model.addAttribute("truepicture", path.concat("checkpicture.jpg"));
			model.addAttribute("lx", technicalFile.getWjlx());
			model.addAttribute("bb", technicalFile.getBb());
			model.addAttribute("fbrq", technicalFile.getFbrq() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, technicalFile.getFbrq()));
			model.addAttribute("zjh", technicalFile.getZjh());
			model.addAttribute("fwxjnr", technicalFile.getFwxjnr());
			model.addAttribute("pgdh", technicalFile.getPgdh());
			model.addAttribute("sxrq", technicalFile.getSxrq() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, technicalFile.getSxrq()));
			model.addAttribute("zdsj", technicalFile.getZdsj() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, technicalFile.getZdsj()));
			model.addAttribute("zt", technicalFile.getZt());
			model.addAttribute("ly", technicalFile.getLy());
			model.addAttribute("sjgz", technicalFile.getSjgz());
			model.addAttribute("wjzt", technicalFile.getWjzt());
			model.addAttribute("wjzy", technicalFile.getWjzy());
			model.addAttribute("xgwj", technicalFile.getShzltgh());
			model.addAttribute("jx", technicalFile.getJx());
			model.addAttribute("fl", technicalFile.getFl());
			model.addAttribute("syx", technicalFile.getSyx());
			model.addAttribute("gczl", technicalFile.getIsGczl());
			model.addAttribute("jstg", technicalFile.getIsJstg());
			model.addAttribute("flxgk", technicalFile.getIsFlxgd());
			model.addAttribute("jszl", technicalFile.getIsJszl());
			model.addAttribute("wxfa", technicalFile.getIsWxfa());
			model.addAttribute("fjgd", technicalFile.getIsFjgd());
			model.addAttribute("zjfx", technicalFile.getIsZjfj());
			model.addAttribute("qt", technicalFile.getIsQt());
			model.addAttribute("qt_ms", technicalFile.getQtMs());
			model.addAttribute("pgr",
					technicalFile.getPgr_user() != null ? technicalFile.getPgr_user().getUsername() : "");
			model.addAttribute("pgrrealname",
					technicalFile.getPgr_user() != null ? technicalFile.getPgr_user().getRealname() : "");
			model.addAttribute("pgrj", technicalFile.getPgyj());
			model.addAttribute("pgsj", technicalFile.getPgsj() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, technicalFile.getPgsj()));
			model.addAttribute("shr",
					technicalFile.getShr_user() != null ? technicalFile.getShr_user().getUsername() : "");
			model.addAttribute("shrrealname",
					technicalFile.getShr_user() != null ? technicalFile.getShr_user().getRealname() : "");
			model.addAttribute("shyj", technicalFile.getShyj());
			model.addAttribute("shsj", technicalFile.getShsj() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, technicalFile.getShsj()));
			model.addAttribute("pfr",
					technicalFile.getPfr_user() != null ? technicalFile.getPfr_user().getUsername() : "");
			model.addAttribute("pfrrealname",
					technicalFile.getPfr_user() != null ? technicalFile.getPfr_user().getRealname() : "");
			model.addAttribute("pfyj", technicalFile.getPfyj());
			model.addAttribute("pfsj", technicalFile.getPfsj() == null ? ""
					: DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, technicalFile.getPfsj()));
//			return "redirect:/report/".concat(type).concat("/").concat(technicalFile.getDprtcode())
//					.concat("/TechnicalFile");
			 return outReport(type, technicalFile.getDprtcode(), "TechnicalFile", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}

	/**
	 * 
	 * @param jx
	 * @param fl
	 * @param zt
	 * @param pgr
	 * @param zdr
	 * @param dprtcode
	 * @param yjts
	 * @param keyword
	 * @param request
	 * @param model
	 * @return 导出技术文件Excel
	 * @throws BusinessException
	 */
	@RequestMapping(value = "technicalFileOutExcel.xls")
	public String export(TechnicalFile  technicalFile, HttpServletRequest request, Model  model) throws BusinessException {
		try{	
			Pagination p=new Pagination();
			p.setSort("auto");
			p.setRows(Integer.MAX_VALUE);
			technicalFile.setPagination(p);
			Map<String, Object> resultMap = technicalFileService.queryAllTechnicalfile(technicalFile);
			//获取监控设置的伐值
			List<TechnicalFile> list=(List<TechnicalFile>) resultMap.get("rows");
				JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
				//Threshold threshold=thresholdService.queryThresholdByKey(ThresholdEnum.JSWJ.toString());
				Monitorsettings monitorsettings =monitorsettingsService.getByKeyDprtcode(ThresholdEnum.TGZL.getName(), technicalFile.getDprtcode());
				model.addAttribute("yjjb1", monitorsettings.getYjtsJb1());
				model.addAttribute("yjjb2", monitorsettings.getYjtsJb2());				
				model.addAttribute("jrMainDataSource", jrDataSource); 
				return outReport("xls", "common", "TechnicalFileDownLoad", model);
			
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
	
	
	
	
	/**
	 * 跳转至文件列表
	 * 
	 * @return 页面视图
	 */
	@Privilege(code = "project:technicalfile:list")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 获取登入user
		User user = SessionUtil.getUserFromSession(request);
		// 获取该组织机构的所有机型工程师
		List<User> users = userService.getByDprtcode(user.getJgdm());
		model.put("users", users);
		return new ModelAndView("/project/technicalfile/technicalfile_searce_list", model);
	}
	/**
	 * 查看技术文件(查询列表)
	 * 
	 * @param request
	 * @param resp
	 * @return @throws Exception @throws
	 */
	@RequestMapping("findTechnicalFileSearce/{id}")
	public ModelAndView findTechnicalFileSearce(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		//根据id获取数据
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		model.put("technicalFile", technicalFile);
		//获取上传文件
		TechnicalUpload technicalUpload = technicalUploadService.selectByManid(id);
		model.put("technicalUpload", technicalUpload);
		return new ModelAndView("/project/technicalfile/find_searce_technicalfile", model);
	}
	
	/**
	 * 跳转至查看评估单
	 * 
	 * @return 页面视图
	 */
	@ResponseBody
	@RequestMapping(value = "selectPgdList", method = RequestMethod.POST)
	public Map<String,Object> selectPgdList(@RequestBody TechnicalFile technicalFile,HttpServletRequest request,Model model){
		if(technicalFile.getPgdsId()!=null && !"".equals(technicalFile.getPgdsId())){
			List <String> list =new ArrayList<String>();
	        String d[] = technicalFile.getPgdsId().split(",");
	        for (int i = 0; i < d.length; i++) {
	        	list.add(d[i]);
	        }
	        technicalFile.setPgdids(list);
		}
		
		PageHelper.startPage(technicalFile.getPagination());
		List<TechnicalFile> list = technicalFileService.queryAllByDocumentsId(technicalFile);
		return PageUtil.pack4PageHelper(list, technicalFile.getPagination());
		
	}
	/**
	 * @author 查询受影响的列
	 * @description
	 * @param djbh,model
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByTechnicalfileids", method = { RequestMethod.POST, RequestMethod.GET })
	public List<Affected> queryListByTechnicalfileids(Model model, @RequestParam("techncialfileIds[]") List<String> techncialfileIds) throws BusinessException {
		List<Affected> list = new ArrayList<Affected>();
		try {
			list = affectedService.queryListByTechnicalfileids(techncialfileIds);
		} catch (Exception e) {
			throw new BusinessException("获取受影响列失败!",e);
		} 
		return list;
	}
	public List<TechnicalFile> acquirePgjg(Map<String, String> map ,List<OrderSource> orderSourcelist, List<TechnicalFile> list){
		for (OrderSource orderSource2 : orderSourcelist) {
			// 判断键是否存在，如果存在，那么拼接
			if (map.containsKey(orderSource2.getPgdid())) {
				map.put(orderSource2.getPgdid(),
						map.get(orderSource2.getPgdid()) + ",&sun1" + orderSource2.getZlbhAndZlzt());
			} else {
				map.put(orderSource2.getPgdid(), orderSource2.getZlbhAndZlzt());
			}
		}
		// 遍历列表数据，拼接评估结果
		for (TechnicalFile technicalFile2 : list) {
			technicalFile2.setZlbh(map.get(technicalFile2.getId()));
		}
		return list;
	}
	public void assembly(List<OrderSource> orderSourcelist,String dprtcode,List<String> ids) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dprtcode", dprtcode);
		paramMap.put("ids", ids);
		// 查询技术指令
		List<Instruction> instructionList = instructionService.queryAll(paramMap);
		Map<String, String> resultInstructionMap = new HashMap<String, String>();
		for (Instruction instruction : instructionList) {
			resultInstructionMap.put(instruction.getJszlh(), OrderZtEnum.getName(instruction.getZt()));
		}

		// 查询技术通告
		List<Annunciate> annunciateList = annunciateService.queryAll(paramMap);
		Map<String, String> resultAnnunciateMap = new HashMap<String, String>();
		for (Annunciate annunciate : annunciateList) {
			resultAnnunciateMap.put(annunciate.getJstgh(), OrderZtEnum.getName(annunciate.getZt()));
		}
		// 修订通知书
		List<RevisionNoticeBook> revisionNoticeBookList = revisionNoticeBookService.queryAll(paramMap);
		Map<String, String> resultRevisionNoticeBookMap = new HashMap<String, String>();
		for (RevisionNoticeBook revisionNoticeBook : revisionNoticeBookList) {
			resultAnnunciateMap.put(revisionNoticeBook.getJszlh(), OrderZtEnum.getName(revisionNoticeBook.getZt()));
		}
		// 工程指令
		List<Engineering> engineeringList = engineeringService.queryAll(paramMap);
		Map<String, String> resultEngineeringMap = new HashMap<String, String>();
		for (Engineering engineering : engineeringList) {
			resultAnnunciateMap.put(engineering.getGczlbh(), OrderZtEnum.getName(engineering.getZt()));
		}
		// 非例行工单
		List<WorkOrder> workOrderList = workOrderService.queryAll(paramMap);
		Map<String, String> resultWorkOrderMap = new HashMap<String, String>();
		for (WorkOrder workOrder : workOrderList) {
			resultAnnunciateMap.put(workOrder.getGdbh(), OrderZtEnum.getName(workOrder.getZt()));
		}

		for (OrderSource orderSource2 : orderSourcelist) {
			// 技术指令
			if (resultInstructionMap.containsKey(orderSource2.getZlbh())) {
				orderSource2.setZlzt(resultInstructionMap.get(orderSource2.getZlbh()));
			}
			// 技术通告
			if (resultAnnunciateMap.containsKey(orderSource2.getZlbh())) {
				orderSource2.setZlzt(resultAnnunciateMap.get(orderSource2.getZlbh()));
			}
			// 修订通知书
			if (resultRevisionNoticeBookMap.containsKey(orderSource2.getZlbh())) {
				orderSource2.setZlzt(resultRevisionNoticeBookMap.get(orderSource2.getZlbh()));
			}
			// 工程指令
			if (resultEngineeringMap.containsKey(orderSource2.getZlbh())) {
				orderSource2.setZlzt(resultEngineeringMap.get(orderSource2.getZlbh()));
			}
			// 非例行工卡
			if (resultWorkOrderMap.containsKey(orderSource2.getZlbh())) {
				orderSource2.setZlzt(resultWorkOrderMap.get(orderSource2.getZlbh()));
			}

		}
	}
	@RequestMapping(value="/downfile/{id}",method={RequestMethod.GET})
	public ResponseEntity<byte[]> downfile(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id) throws BusinessException {
		try {
			 TechnicalUpload technicalUpload=technicalUploadService.selectByPrimaryKey(id);
			String relativePath = technicalUpload.getCflj().concat(File.separator).concat(technicalUpload.getNbwjm());
			String displayName  = technicalUpload.getWbwjm()+"."+technicalUpload.getHzm();
			return download(relativePath, displayName, request, response);
		} catch (Exception e) {
			throw new BusinessException("附件下载失败");
		}
	}
	public ResponseEntity<byte[]> download(String relativePath,String displayName,
			HttpServletRequest res,HttpServletResponse resp) throws IOException{
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String filePath = rootPath.concat(File.separator).concat(relativePath);
		String dfileName = new String(displayName.getBytes("UTF-8"), "iso8859-1"); 
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		headers.setContentDispositionFormData("attachment", dfileName); 
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)), headers, HttpStatus.CREATED); 
	}
	
	/**
	 * @author liub
	 * @description 质量关闭
	 * @param qualityClose
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "engineering:center:cad:01,engineering:center:sb:01")
	@ResponseBody
	@RequestMapping(value = "qualityClose", method = RequestMethod.POST)
	public void qualityClose(@RequestBody QualityClose qualityClose) throws BusinessException{
		getLogger().info("质量关闭  前端参数:id{}", new Object[]{qualityClose});
		try {
			technicalFileService.qualityClose(qualityClose);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("质量关闭失败!",e);
		}
	}
}
