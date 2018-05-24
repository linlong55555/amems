package com.eray.thjw.control;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.common.util.UserUtil;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.ModelRole;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.Role;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WarehouseRole;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.ModelRoleService;
import com.eray.thjw.service.RoleService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.service.WarehouseRoleService;
import com.eray.thjw.system.service.AccountService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.RSAUtils;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;

/**
 * @author ll
 * @description 用户控制层
 * @develop date 2016.08.05
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

	/**
	 * @author ll
	 * @description user service
	 * @develop date 2016.07.25
	 */
	@Autowired
	private UserService userService;

	/**
	 * 组织机构service
	 */
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 角色service
	 */
	@Autowired
	private RoleService roleservice;

	/**
	 * 机型service
	 */
	@Autowired
	private ModelRoleService modelRoleService;
	
	/**
	 * 库房serice
	 */
	@Autowired
	private WarehouseRoleService warehouseRoleService;
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource(name="userExcelImporter")
	private BaseExcelImporter<Storage> excelImporter;
	
	/**
	 * 修改密码获取密匙key
	 * @param secondment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "publicKey", method = RequestMethod.POST)
	public Map<String, Object> publicKey(String dprtcode)throws BusinessException {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
			model.put("modulus", new String(Hex.encodeHex(publicKey.getModulus().toByteArray())));
			model.put("exponent", new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));
			return model;
		} catch (Exception e) {
			throw new BusinessException("查询密匙失败!",e);
		}
	}
	
	/**
	 * 跳转至用户管理界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code = "sys:user:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest req, HttpServletResponse resp) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
			//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("sys/user/userMain", model);
	}

	/**
	 * @author sunji
	 * @description
	 * @param request,model
	 * @return 页面视图system/user/userMain.jsp
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryUserList", method = RequestMethod.POST)
	public Map<String, Object> queryUserList(@RequestBody User user, HttpServletRequest request, Model model) throws BusinessException{
		try {
			return userService.SelectByUser(user);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author meizhiliang
	 * @description
	 * @param request,model
	 * @return 用户和维修人员联合查询
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryUserAndAttendantor", method = RequestMethod.POST)
	public Object queryUserAndAttendantor(@RequestBody User user, HttpServletRequest request, Model model) {
		user.setKeyword(user.getRealname());
		PageHelper.startPage(user.getPagination());
		List<User> list = userService.queryUserAndAttendantor(user);
		return PageUtil.pack4PageHelper(list, user.getPagination());
	}
	
	/**
	 * 初始化增加用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@Privilege(code="sys:user:main:01")
	@RequestMapping("intoAddUser")
	public ModelAndView intoAddUser(HttpServletRequest req, HttpServletResponse resp){
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
			//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		return new ModelAndView("/sys/user/add_user", model);
	}

	/**
	 * @author sunji
	 * @description 密码修改
	 * @param request,model
	 * @return success/error
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserPassword", method = RequestMethod.POST)
	public Object updateUserPassword(@RequestBody User user, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User sessionUser = ThreadVarUtil.getUser();
		
		if(!sessionUser.getPassword().equals(UserUtil.MD5(user.getPassword()))){
			resultMap.put("status", "error");
			resultMap.put("massage", "原密码输入有误");
			return resultMap;
		}
		try{
			String newpassword = UserUtil.MD5(user.getNewpassword());
			this.accountService.updateAccountPassword(sessionUser.getDrzhid(), newpassword);
			sessionUser.setPassword(newpassword);
			resultMap.put("status", "success");
			resultMap.put("massage", "修改成功");
		}catch(Exception e){
			resultMap.put("status", "error");
			resultMap.put("massage", "修改失败");
		}
		return resultMap;
	}
	
	/**
	 * @author peixiu
	 * @description 修改用户信息
	 * @param request,model
	 * @return success/error
	 * @develop date 2017.03.30
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
	public Object updateUserInfo(@RequestBody User user, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User sessionUser = ThreadVarUtil.getUser();
		//解密
		String passw=RSAUtils.decryptStringByJs(user.getPassword());
		String newpassw=RSAUtils.decryptStringByJs(user.getNewpassword());
		user.setPassword(passw);
		user.setNewpassword(newpassw);
		String czls =UUID.randomUUID().toString();
		try{
			if(null!=user.getPassword()&&!"".equals(user.getPassword())){
				if(!sessionUser.getPassword().equals(UserUtil.MD5(user.getPassword()))){
					resultMap.put("status", "error");
					resultMap.put("massage", "原密码输入有误");
					return resultMap;
				}else{
					String newpassword = UserUtil.MD5(user.getNewpassword());
					this.accountService.updateAccountPassword(sessionUser.getDrzhid(), newpassword);
					sessionUser.setPassword(newpassword);
				}
			}
			if(user.getAttId().equals("2")){
				//删除原有的电子签名
				attachmentService.deleteByMainId(user.getId(), czls, user.getId(), LogOperationEnum.DELETE);
			}
			if(user.getAttachment() != null){
				//删除原有的电子签名
				attachmentService.deleteByMainId(user.getId(), czls, user.getId(), LogOperationEnum.DELETE);
				Attachment attachment = user.getAttachment();
				String attId = attachmentService.addAttachment(attachment, user.getId(), czls, user.getId(), user.getJgdm(), LogOperationEnum.EDIT);
				sessionUser.setAttId(attId);
			}
			
			sessionUser.setPhone(user.getPhone());
			sessionUser.setRealname(user.getRealname());
			sessionUser.setCellphone(user.getCellphone());
			sessionUser.setSex(user.getSex());
			userService.updateUserInfo(user);
//			SessionUtil.setAttr(request, "realname", user.getRealname());
//			SessionUtil.setAttr(request, "password", user.getPassword());
//			SessionUtil.setAttr(request, "cellphone", user.getCellphone());
//			SessionUtil.setAttr(request, "sex", user.getSex());
//			SessionUtil.setAttr(request, "phone", user.getPhone());
			resultMap.put("status", "success");
			resultMap.put("massage", "修改成功");
		}catch(Exception e){
			resultMap.put("status", "error");
			resultMap.put("massage", "修改失败");
		}
		return resultMap;

	}
	
	/**
	 * @author sunji
	 * @description 状态修改
	 * @param request,model
	 * @return success/error
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@Privilege(code="sys:user:main:03")
	@RequestMapping(value = "updateUserState", method = RequestMethod.POST)
	public Object updateUserState(@RequestBody User user, HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("text", userService.updateUserState(user));
		return resultMap;
	}

	// 查询菜单树
	@ResponseBody
	@RequestMapping(value = "queryDepartmentTree", method = RequestMethod.POST)
	public String queryDepartmentTree(HttpServletRequest request, Model model) {
		String parentId = request.getParameter("parentId");
		String resultStr = "";
		resultStr = "";
		resultStr += "[";
		List<Department> department1 = departmentService.findAll(parentId);

		for (Department department2 : department1) {
			resultStr += "{";
			resultStr += String.format(
					"\"id\": \"" + department2.getId() + "\", \"text\": \"" + department2.getDprtname()
							+ "\", \"iconCls\": \"icon-blank\", \"state\": \"closed\"",
					department2.getId().toString(), department2.getDprtname());
			resultStr += "},";
		}
		resultStr = resultStr.substring(0, resultStr.length() - 1);
		resultStr += "]";

		return resultStr;
	}

	// 查询角色名称和id@ResponseBody
	@ResponseBody
	@RequestMapping(value = "queryRoleList", method = RequestMethod.POST)
	public Object queryRoleList(HttpServletRequest request, Model model) throws Exception {
		List<Role> roles = roleservice.queryAllRole(new Role());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("RoleList", roles);
		
		return resultMap;
	}

	// 保存提交用户
	@ResponseBody
	@RequestMapping(value = "saveUser", method = RequestMethod.POST)
	public Map<String, Object> saveUser(@RequestBody User user, HttpServletRequest request, Model model)throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("id", userService.insertUser(user));
		return map;

	}

	// 修改提示
	@Privilege(code="sys:user:main:02")
	@RequestMapping("updateUser")
	public ModelAndView updateUser(@RequestParam String userId, HttpServletRequest request, HttpServletResponse resp){
		Map<String, Object> model = new HashMap<String, Object>();
		
		// 获取数据的基本信息
		User user = userService.selectByPrimaryKey(userId);
		
		User users = ThreadVarUtil.getUser();
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(users.getOrgcode())){//超级机构用户 拥有的组织机构
			//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(users.getId()));
		}
		
		// 获取该数据已功能角色
		List<Role> userToRoleList = roleservice.queryRole(userId);
		
		StringBuffer userToRoleIds = new StringBuffer();
		for (Role role : userToRoleList) {
			userToRoleIds.append(",").append(role.getId());
		}
		if(userToRoleIds.length() > 0){
			userToRoleIds = userToRoleIds.deleteCharAt(0);
		}
		
		// 获取该数据已选机型角色
		List<ModelRole> userToModelRoleList = modelRoleService.queryRole(userId);
		StringBuffer userToModelRoleIds = new StringBuffer();
		for (ModelRole role : userToModelRoleList) {
			userToModelRoleIds.append(",").append(role.getId());
		}
		if(userToModelRoleIds.length() > 0){
			userToModelRoleIds = userToModelRoleIds.deleteCharAt(0);
		}
		
		// 获取该数据已选库房角色
		List<WarehouseRole> userToWarehouseRoleList = warehouseRoleService.queryRole(userId);
		StringBuffer userToWarehouseRoleIds = new StringBuffer();
		for (WarehouseRole role : userToWarehouseRoleList) {
			userToWarehouseRoleIds.append(",").append(role.getId());
		}
		if(userToWarehouseRoleIds.length() > 0){
			userToWarehouseRoleIds = userToWarehouseRoleIds.deleteCharAt(0);
		}
		
		model.put("user", user);
		model.put("userToRoleIds", userToRoleIds);
		model.put("userToModelRoleIds", userToModelRoleIds);
		model.put("userToWarehouseRoleIds", userToWarehouseRoleIds);
		return new ModelAndView("/sys/user/modify_user", model);
	}

	// 查询已选角色和未选角色名称，id
	@ResponseBody
	@RequestMapping(value = "queryRoleGroupList", method = RequestMethod.POST)
	public Object queryRoleGroupList(@RequestBody String userId, HttpServletRequest request, Model model)throws Exception {
		List<Role> userToRoleList = roleservice.queryRole(userId);
		List<Role> notUserToRoleList = roleservice.queryUserNotRole(userId);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("userToRoleList", userToRoleList);
		resultMap.put("notUserToRoleList", notUserToRoleList);
		return resultMap;
	}

	// 查询组织机构树
	@ResponseBody
	@RequestMapping(value = "queryDprtTrees/{dprtcode}", method = RequestMethod.POST)
	public List<Map<String, Object>> queryRoleDprtTree(@PathVariable String dprtcode,HttpServletRequest request,Model model) throws Exception {
		
		List<Department> dprts = departmentService.findAllff();
		Map<String, Object> menuMap = new HashMap<String, Object>();
		
		queryDprtTree(dprtcode,menuMap,dprts);
			
		List<Map<String, Object>> menuList = (List<Map<String, Object>>) menuMap.get("children");
		return menuList;
	}
	
	// 查询组织机构树2
	public void queryDprtTree(String id, Map<String, Object> menuMap, List<Department> department) throws Exception {
		
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		
		for (Department depart : department) {
		      if (null != depart.getParentid() && id.equals(depart.getParentid())) { 
				Map<String, Object> childrenMap = new HashMap<String, Object>();
				childrenMap.put("id", depart.getId());
				childrenMap.put("name", depart.getDprtname());
				childrenMap.put("state", "open");
				childrenMap.put("iconCls", "icon-blank");
				queryDprtTree(depart.getId(), childrenMap, department);
				menuList.add(childrenMap);
		      }
		}
		menuMap.put("children", menuList);
	}

	/**
	 * @author liub
	 * @description 配置菜单
	 * @param
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 * @develop date 2016.08.10
	 */
	private void roleDprtTree(String id, Map<String, Object> menuMap, List<Department> department) throws Exception {

		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
		for (Department departments : department) {

			if (id.equalsIgnoreCase(departments.getParentid())) {

				Map<String, Object> childrenMap = new HashMap<String, Object>();
				childrenMap.put("id", departments.getId());
				childrenMap.put("name", departments.getDprtname());
				childrenMap.put("state", "open");
				childrenMap.put("iconCls", "icon-blank");
				roleDprtTree(departments.getId(), childrenMap, department);
				menuList.add(childrenMap);
			}
		}
		menuMap.put("children", menuList);

	}

	/**
	 * @author sunji
	 * @description 用户修改
	 * @param
	 * @return List<Map<String, Object>>
	 * @throws BusinessException 
	 * @throws Exception
	 * @develop date 2016.08.10
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserMessage", method = RequestMethod.POST)
	public Object updateUserMessage(@RequestBody User user, HttpServletRequest request, Model model) throws BusinessException{

		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", userService.updateUser(user));
		map.put("status", "success");
		return map;
	}

	/**
	 * 在线预览帮助手册PDF
	 * 
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/UserHelperPdf")
	public void findPdf(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setContentType("application/pdf");
		String path = request.getSession().getServletContext().getRealPath("/WEB-INF/help/Help.pdf");
		File file = new File(path);
		response.setContentLength((int) file.length());
		response.addHeader("Content-Disposition", "attachment; filename=" + "Help.pdf");
		OutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(FileUtils.readFileToByteArray(file));  
	}
	
	@ResponseBody
	@RequestMapping(value = "queryList", method = RequestMethod.POST)
	public List<User> queryList(@RequestBody User user, HttpServletRequest request, Model model) throws Exception {
		List<User> list = null;
		list = userService.queryUserAll(user);
		return list;

	}
	
	/**
	 * 解绑用户账号关联
	 */

	@Privilege(code="sys:user:main:05")
	@RequestMapping(value="/unbound/account/{id}",method=RequestMethod.POST)
	public @ResponseBody void enable(@PathVariable String id){
		this.userService.updateUserUnboundAccount(id);
	}
	
	// 根据机型检索用户
	@ResponseBody
	@RequestMapping(value = "queryAllByjx", method = RequestMethod.POST)
	public List<User> queryAllByjx(@RequestBody User user, HttpServletRequest request, Model model)throws Exception {
		List <User> list=userService.queryAllByjx(user);
		return list;
	}
	

	/**
	 * @author sunji
	 * @description 根据机构代码查询人员树(按部门)
	 * @param dprtcode
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "queryDrptTreeByDprtcode", method = RequestMethod.POST)
	public Map<String, Object> queryDrptTreeByDprtcode(String dprtcode, String bmdm, String str)throws BusinessException {
		try {
			return userService.queryDrptTreeByDprtcode(dprtcode, bmdm, str);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	
	/**
	 * 用户信息导入
	 * ll
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code = "sys:user:main:06")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,String dprtcode,HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.setParam("dprtcode", dprtcode);
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "用户信息导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "用户信息导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @Description excel导出
	 * @CreateTime 2017年12月4日 上午11:24:24
	 * @CreateBy 岳彬彬
	 * @param user
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="sys:user:main:07")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "UserManagement.xls")
	public String export(User user, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			user.setJgdm(new String (user.getJgdm().getBytes("iso8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			user.setPagination(p);
			if(null != user.getKeyword() && !"".equals(user.getKeyword())){
				String keyword = user.getKeyword();
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				user.setKeyword(keyword);
			}
			Map<String, Object> resultMap = userService.SelectByUser(user);
			List<User> list = (List<User>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "userManagement", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
