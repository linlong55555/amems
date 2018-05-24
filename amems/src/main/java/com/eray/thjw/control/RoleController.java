package com.eray.thjw.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Button;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.Menu;
import com.eray.thjw.po.ModelRole;
import com.eray.thjw.po.Role;
import com.eray.thjw.po.RoleToBtn;
import com.eray.thjw.po.RoleToDprt;
import com.eray.thjw.po.RoleToMenu;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WarehouseRole;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.ButtonService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.MenuService;
import com.eray.thjw.service.ModelRoleService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.RoleService;
import com.eray.thjw.service.RoleToBtnService;
import com.eray.thjw.service.RoleToDprtService;
import com.eray.thjw.service.RoleToMenuService;
import com.eray.thjw.service.RoleToModelService;
import com.eray.thjw.service.RoleToStoreService;
import com.eray.thjw.service.WarehouseRoleService;
import com.eray.thjw.system.po.GlobleSystemInfo;
import com.eray.thjw.system.service.GlobleSystemInfoService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author ll
 * @description 角色管理控制器
 * @develop date 2016.07.25
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController {
	
	@Resource
	private MenuService menuService; //菜单service
	
	@Resource
	private PlaneDataService planeDataService; //飞机注册号service
	
	@Resource
	private PlaneModelDataService planeModelDataService; //机型service
	
	@Resource
	private PlaneDataMapper planeDataMapper; //菜单service
	
	@Autowired
	private RoleService roleService; //角色service
	
	@Autowired
	private RoleToModelService roleToModelService; //角色service
	
	@Autowired
	private WarehouseRoleService warehouseRoleService; //库房角色service
	
	@Autowired
	private ModelRoleService modelRoleService; //机型角色service
	
	@Resource
	private ButtonService buttonService; //按钮service
	
	@Resource
	private DepartmentService departmentService; //组织结构service
	
	@Resource
	private RoleToMenuService roleToMenuService; //角色to菜单service
	
	@Resource
	private RoleToBtnService roleToBtnService; //角色to按钮service
	
	@Resource
	private RoleToDprtService roleToDprtService; //角色to组织结构service
	
	@Resource
	private StoreService storeService; //仓库service
	
	@Resource
	private RoleToStoreService roleToStoreService; //角色to仓库service
	
	@Resource
	private GlobleSystemInfoService globleSystemInfoService;
	
	/**
	 * 初始化角色界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="sys:role:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("/sys/role/role_list", model);
	}
	
	/**
	 * 功能角色查询
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryRoleList", method = RequestMethod.POST)
	public Map<String, Object> queryRoleList(@RequestBody Role role,HttpServletRequest request,Model model) throws Exception {
	
		User sessionUser = ThreadVarUtil.getUser();
		
		//非超级管理所拥有的权限
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(sessionUser.getOrgcode())){
			role.getParamsMap().put("userId", sessionUser.getId());
		}
		
		String id =role.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		role.setId(null);
		PageHelper.startPage(role.getPagination());
		List<Role> list = roleService.queryAllPageList(role);
		if(((Page)list).getTotal() > 0){
			
			if(id!=null){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id.toString())){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Role role1 = new Role();
					role1.setId(String.valueOf(id));
					List<Role> newRecordList = roleService.queryAllPageList(role1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, role.getPagination());
		}else{
			List<Role> newRecordList = new ArrayList<Role>(1);
			if(id!=null){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Role role1 = new Role();
				role1.setId(String.valueOf(id));
				newRecordList = roleService.queryAllPageList(role1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, role.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, role.getPagination());
		}
		
	}
	
	/**
	 * 机型角色查询
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "queryModelRoleList", method = RequestMethod.POST)
	public Map<String, Object> queryModelRoleList(@RequestBody ModelRole modelRole,HttpServletRequest request,Model model) throws Exception {
	
		User sessionUser = ThreadVarUtil.getUser();
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(sessionUser.getOrgcode())){
			modelRole.getParamsMap().put("userId", sessionUser.getId());
		}
		
		String id =modelRole.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		modelRole.setId(null);
		PageHelper.startPage(modelRole.getPagination());
		List<ModelRole> list = modelRoleService.queryAllPageList(modelRole);
		if(((Page)list).getTotal() > 0){
			
			if(id!=null){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id.toString())){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ModelRole role1 = new ModelRole();
					role1.setId(String.valueOf(id));
					List<ModelRole> newRecordList = modelRoleService.queryAllPageList(role1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, modelRole.getPagination());
		}else{
			List<ModelRole> newRecordList = new ArrayList<ModelRole>(1);
			if(id!=null){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ModelRole role1 = new ModelRole();
				role1.setId(String.valueOf(id));
				newRecordList = modelRoleService.queryAllPageList(role1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, modelRole.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, modelRole.getPagination());
		}
		
	}
	
	/**
	 * 库房角色查询
	 * @param role
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "querywarehouseRoleList", method = RequestMethod.POST)
	public Map<String, Object> querywarehouseRoleList(@RequestBody WarehouseRole warehouseRole,HttpServletRequest request,Model model) throws Exception {
	
		User sessionUser = ThreadVarUtil.getUser();
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(sessionUser.getOrgcode())){
			warehouseRole.getParamsMap().put("userId", sessionUser.getId());
		}
		
		String id =warehouseRole.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		warehouseRole.setId(null);
		PageHelper.startPage(warehouseRole.getPagination());
		List<WarehouseRole> list = warehouseRoleService.queryAllPageList(warehouseRole);
		if(((Page)list).getTotal() > 0){
			if(id!=null){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id.toString())){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					WarehouseRole role1 = new WarehouseRole();
					role1.setId(String.valueOf(id));
					List<WarehouseRole> newRecordList = warehouseRoleService.queryAllPageList(role1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, warehouseRole.getPagination());
		}else{
			List<WarehouseRole> newRecordList = new ArrayList<WarehouseRole>(1);
			if(id!=null){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				WarehouseRole role1 = new WarehouseRole();
				role1.setId(String.valueOf(id));
				newRecordList = warehouseRoleService.queryAllPageList(role1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, warehouseRole.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, warehouseRole.getPagination());
		}
		
	}
	
	/**
	 * 根据组织机构查询功能角色
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/list/dprtcode/{dprtcode}", method = RequestMethod.POST)
	public Map<String, Object> queryRoleListByDprtcode(@PathVariable String dprtcode) throws Exception {
		Role role = new Role();
		role.setDprtId(dprtcode);
		return PageUtil.pack(this.roleService.queryAllRole(role));
	}
	
	/**
	 * 根据组织机构查询机型角色
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/list/modeldprtcode/{dprtcode}", method = RequestMethod.POST)
	public Map<String, Object> modeldprtcode(@PathVariable String dprtcode) throws Exception {
		ModelRole role = new ModelRole();
		role.setDprtId(dprtcode);
		return PageUtil.pack(this.modelRoleService.queryAllModelRole(role));
	}
	/**
	 * 根据组织机构查询库房角色
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/list/warehousedprtcode/{dprtcode}", method = RequestMethod.POST)
	public Map<String, Object> warehousedprtcode(@PathVariable String dprtcode) throws Exception {
		WarehouseRole role = new WarehouseRole();
		role.setDprtId(dprtcode);
		return PageUtil.pack(this.warehouseRoleService.queryAllWarehouseRole(role));
	}
	
	/**
	 * 初始化增加功能角色
	 * @param req
	 * @param resp
	 * @return
	 */
	@Privilege(code="sys:role:main:01")
	@RequestMapping("intoAddRole")
	public ModelAndView intoAddRole(HttpServletRequest req,HttpServletResponse resp) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		return new ModelAndView("/sys/role/role_add", model);
	}
	
	/**
	 * 初始化增加机型角色
	 * @param req
	 * @param resp
	 * @return
	 */
	@Privilege(code="sys:role:main:06")
	@RequestMapping("intoAddModelRole")
	public ModelAndView intoAddModelRole(HttpServletRequest req,
			HttpServletResponse resp) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
			//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		return new ModelAndView("/sys/role/model_role_add", model);
	}
	
	/**
	 * 初始化增加库房角色
	 * @param req
	 * @param resp
	 * @return
	 */
	@Privilege(code="sys:role:main:09")
	@RequestMapping("intoAddWarehouseRole")
	public ModelAndView intoAddWarehouseRole(HttpServletRequest req,
			HttpServletResponse resp) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
			//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("/sys/role/warehouse_role_add", model);
	}
	
	
	
	/**
	 * 增加机型角色
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "addmodelRole", method = RequestMethod.POST)
	public Map<String, Object> addmodelRole(HttpServletRequest request,HttpServletResponse response,@RequestBody ModelRole modelRole) throws Exception {
		return modelRoleService.save(modelRole);
	}
	
	/**
	 * 增加库房角色
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addwarehouseRole")
	public Map<String, Object> addwarehouseRole(HttpServletRequest request,HttpServletResponse response,WarehouseRole warehouseRole) throws Exception {
		
		return warehouseRoleService.save(warehouseRole);
	}
	
	/**
	 * 增加功能角色
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addRole")
	public Map<String, Object> addRole(HttpServletRequest request,HttpServletResponse response,Role role) throws Exception {
		
		return roleService.save(role);
	}
	
	/**@author ll
	 * @description 检查功能角色是否重复
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,String roleCode,String dprtId) throws Exception {

		return roleService.checkUpdMt(roleCode,dprtId);
	}
	
	/** @author ll
	 * @description 检查机型角色是否重复
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/ModelcheckUpdMt", method = RequestMethod.POST)
	public Map<String, Object> ModelcheckUpdMt(HttpServletRequest request, String modelRoleCode,String dprtId) throws Exception {
		
		return modelRoleService.checkUpdMt(modelRoleCode,dprtId);
	}
	
	
	/** @author ll
	 * @description 检查库房角色是否重复
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/warehousecheckUpdMt", method = RequestMethod.POST)
	public Map<String, Object> warehousecheckUpdMt(HttpServletRequest request, String warehouseRoleCode,String dprtId) throws Exception {
		
		return warehouseRoleService.checkUpdMt(warehouseRoleCode,dprtId);
	}
	
	
	/**
	 * 初始化修改机型角色
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	@Privilege(code="sys:role:main:07")
	@RequestMapping("initModifyModelRole/{id}")
	public ModelAndView initModifyModelRole(@PathVariable("id") String id,  HttpServletRequest request,HttpServletResponse resp) throws  Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		ModelRole role = modelRoleService.findById(id);
		model.put("role", role);
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("/sys/role/model_role_modify", model);
	}
	
	/**
	 * 新增机型角色界面初始化数据
	 * @return
	 */
	@RequestMapping("/init/modelrole/add")
	public @ResponseBody Map<String, Object> initAddModelRole(){
		Map<String, Object> map = new HashMap<String, Object>();
			
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			map.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			map.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		map.put("planeModelList", this.planeModelDataService.queryAircraftByAuthOrgList());
		return map;
	}
	
	/**
	 * 修改机型角色界面初始化数据
	 * @return
	 */
	@RequestMapping("/init/modelrole/modify")
	public @ResponseBody Map<String, Object> initModifyModelRole(@RequestParam String roleId){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			map.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			map.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		map.put("planeModelList", this.planeModelDataService.queryAircraftByAuthOrgList());
		map.put("rolePlaneModelList",this.roleToModelService.queryByRoleId(roleId));
		return map;
	}
	
	
	/**
	 * 初始化修改库房角色
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	@Privilege(code="sys:role:main:10")
	@RequestMapping("initModifyWarehouseRole/{id}")
	public ModelAndView initModifyWarehouseRole(@PathVariable("id") String id,  HttpServletRequest request,HttpServletResponse resp) throws  Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		WarehouseRole role1=new WarehouseRole();
		role1.setId(String.valueOf(id));
		WarehouseRole role = warehouseRoleService.queryAll(role1);
		model.put("role", role);
		
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("/sys/role/warehouse_role_modify", model);
	}
	
	/**
	 * 初始化修改角色
	 * 
	 * @param request
	 * @param resp
	 * @return
	 * @throws Exception 
	 * @throws  
	 */
	@Privilege(code="sys:role:main:02")
	@RequestMapping("initModifyRole/{id}")
	public ModelAndView initModifyRole(@PathVariable("id") String id,  HttpServletRequest request,HttpServletResponse resp) throws  Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Role role1=new Role();
		role1.setId(String.valueOf(id));
		Role role = roleService.queryAll(role1);
		model.put("role", role);
		
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
			//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		
		return new ModelAndView("/sys/role/role_modify", model);
	}
	
	
	/**
	 * 修改角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("modifyRole")
	public Map<String, Object> modifyRole(HttpServletRequest request,HttpServletResponse response,Role role) throws Exception {
		
		return roleService.modify(role);
	}
	
	/**
	 * 修改库房角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("modifywarehouseRole")
	public Map<String, Object> modifywarehouseRole(HttpServletRequest request,HttpServletResponse response,WarehouseRole warehouseRole) throws Exception {
		
		return warehouseRoleService.modify(warehouseRole);
	}
	
	/**
	 * 修改机型角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "modifymodelRole", method = RequestMethod.POST)
	public Map<String, Object> modifymodelRole(HttpServletRequest request,HttpServletResponse response,@RequestBody ModelRole modelRole) throws Exception {
		
		return modelRoleService.modify(modelRole);
	}

	
	@ResponseBody
	@RequestMapping("checkUpdRole")
	public Map<String, Object> checkUpdRole(HttpServletRequest request,String roleCode) throws Exception {

		return roleService.checkUpdRole(roleCode);
	}
	

	
	/**
	 * 初始化角色菜单按钮界面
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@Privilege(code="sys:role:main:03")
	@RequestMapping("intoRoleMenu/{id}")
	public ModelAndView intoRoleMenu(@PathVariable("id") String id,HttpServletRequest req,HttpServletResponse resp) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("/sys/role/role_tree_menu", model);
	}
	
	
	
	/**
	 * @author linlong
	 * @description 配置菜单树
	 * @param 
	 * @return List<Map<String, Object>>
	 * @throws Exception 
	 * @develop date 2016.08.10
	 */
	private List<Map<String, Object>> roleMenuTree(String menuParentId, List<Menu> menuList, List<Button> buttonList,Map<String, Object> roleMenuMap, Map<String, Object> roleBtnMap) {  
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> nodeMap = null;
		for (Menu menu : menuList) {  
	        if (null != menu.getParentId() && menuParentId.equals(menu.getParentId())){ 
	        	//根据父节点组装其子节点
	        	nodeMap = new HashMap<String, Object>();//树节点属性
	        	nodeMap.put("id", menu.getId());
	        	String name	= menu.getMenuName()+"/"+menu.getMenuFname();
	        	nodeMap.put("name",name);
	        	nodeMap.put("state", "open");
	        	nodeMap.put("type", "1");
	        	nodeMap.put("iconCls", "icon-blank");
	        	nodeMap.put("order", menu.getMenuOrder());
	        	if("0".equals(menuParentId)){
	        		nodeMap.put("order", menu.getFullOrder());
	        	}
	        	
	        	if(roleMenuMap.containsKey(menu.getId())){
	        		nodeMap.put("checked", "true");
	        	}
	        	nodeMap.put("app", menu.getXtlx());
	        	
	        	List<Map<String, Object>> menuChildrenList = roleMenuTree(menu.getId(), menuList, buttonList, roleMenuMap, roleBtnMap);
	        	
	        	List<Map<String, Object>> btnChildrenList = roleMenuBottonTree(menu.getId(), buttonList, roleBtnMap);
	        	
	        	menuChildrenList.addAll(btnChildrenList);
	        	
	        	nodeMap.put("children", menuChildrenList);
	        	
	        	returnList.add(nodeMap);
	        }  
	    }
		Collections.sort(returnList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return Integer.valueOf(o1.get("order")==null?"0":o1.get("order").toString()).compareTo(Integer.valueOf(o2.get("order")==null?"0":o2.get("order").toString()));
			}
		});
		
		return returnList;
    }  
	
	private List<Map<String, Object>> roleMenuBottonTree(String menuId, List<Button> buttonList, Map<String, Object> roleBtnMap){
		
		List<Map<String, Object>> btnChilrenList = new ArrayList<Map<String, Object>>();
		Map<String, Object> nodeMap = null;//树节点属性
		for (Button button : buttonList) {
			if(button.getMenuId().equals(menuId)){
				nodeMap = new HashMap<String, Object>();//树节点属性
				nodeMap.put("id", button.getId());
	        	String name	= button.getButtonName()+"/"+button.getButtonCode();
	        	nodeMap.put("name",name);
	        	nodeMap.put("state", "open");
	        	nodeMap.put("type", "2");
	        	nodeMap.put("iconCls", "icon-blank");
	        	
	        	if(roleBtnMap.containsKey(button.getId())){
	        		nodeMap.put("checked", "true");
	        	}
	        	
	        	btnChilrenList.add(nodeMap);
			}
		}
		return btnChilrenList;
	}
	
	/**
	 * 
	 * @Description 获取角色 菜单权限树  登陆人的所有权限 以及所操作角色已具有的权限
	 * @UpdateTime 2017年8月19日 下午2:48:18
	 * @UpdateBy 徐勇
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="queryRoleMenuTree", method = RequestMethod.POST)
	public List<Map<String, Object>> queryRoleMenuTree(HttpServletRequest request,Model model) throws Exception {
		String roleId=request.getParameter("roleId");
		if(StringUtils.isBlank(roleId)){
			throw new BusinessException("查询角色菜单出错");
		}
		
		List<Menu> menuList = null;//所有菜单
		List<Button> buttonList = null;//按钮集合
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 获取所有菜单及按钮
			//所有菜单
			menuList = menuService.findAll();
			//获取按钮集合
			buttonList = buttonService.findAllButtonList();
		}else{//非超级机构获取当前用户的所有菜单，按钮
			menuList = menuService.findMenuByUserId(user.getId());//获取菜单数据
			buttonList = buttonService.findButtonByUserId(user.getId());//获取按钮集合
		}
		  Collections.sort(buttonList, new Comparator<Button>(){			  
			  @Override
			public int compare(Button o1, Button o2) {
				// TODO Auto-generated method stub
				return o1.getButtonCode().compareToIgnoreCase(o2.getButtonCode());
			}
			  
		  });
	   	//角色已选的菜单
	   	List<RoleToMenu> roleMenuList = roleToMenuService.queryByRoleId(roleId);
	   	Map<String, Object> roleMenuMap = new HashMap<String, Object>(roleMenuList.size()); 
	   	for (RoleToMenu roleToMenu : roleMenuList) {
	   		roleMenuMap.put(roleToMenu.getMenuId(), null);
		}
	   	
	   	//角色已选的按钮
	   	List<RoleToBtn> roleBtnList = roleToBtnService.queryByRoleId(roleId);
	   	Map<String, Object> roleBtnMap = new HashMap<String, Object>(roleMenuList.size()); 
	   	for (RoleToBtn roleToBtn : roleBtnList) {
	   		roleBtnMap.put(roleToBtn.getButtonId(), null);
		}
	   	
	   	//组装菜单树
	   	List<Map<String, Object>> menuTreeList = roleMenuTree("0", menuList, buttonList, roleMenuMap, roleBtnMap);
	   	//获取系统 信息
	  	List<GlobleSystemInfo> sysInfoList = globleSystemInfoService.getAll();
	  	//将菜单树按应用分类
	  	List<Map<String, Object>> returnTreeList = new ArrayList<Map<String, Object>>();//最终的树
	  	Map<String, Object> nodeMap = null;
	  	List<Map<String, Object>> childrenList = null;
	  	for (GlobleSystemInfo globleSystemInfo : sysInfoList) {
	  		nodeMap = new HashMap<String, Object>();//树节点属性
	  		nodeMap.put("id", globleSystemInfo.getSyscode());
	  		String name	= globleSystemInfo.getMs()+"/"+globleSystemInfo.getSyscode();
	  		nodeMap.put("name",name);
	  		nodeMap.put("state", "open");
	  		nodeMap.put("type", "app");
	  		nodeMap.put("nocheck", true);
	  		nodeMap.put("open", true);
	  		
	  		childrenList = new ArrayList<Map<String, Object>>();//系统 子节点
	  		//将属于该系统的功能 放入该系统子节点中
			for (Map<String, Object> tempNodeMap : menuTreeList) {
		    	if(globleSystemInfo.getSyscode().equals(tempNodeMap.get("app"))){
		    		childrenList.add(tempNodeMap);
		    	}
			}
			nodeMap.put("children", childrenList);
			returnTreeList.add(nodeMap);
		}
	  	//将不属于任何系统的菜单直接放入根节点下
	    return returnTreeList;

	}
	
	/**
	 * 分配菜单
	 * @param request
	 * @param response
	 * @return
	 * @author xu.yong
	 */
	@ResponseBody
	@RequestMapping("roleMenu/save")
	public String saveRoleMenu(@RequestParam String roleId, @RequestParam(value="addMenus[]", required=false) List<String> addMenus,
			@RequestParam(value="delMenus[]", required=false) List<String> delMenus,
			@RequestParam(value="addBtns[]", required=false) List<String> addBtns, 
			@RequestParam(value="delBtns[]", required=false) List<String> delBtns) throws BusinessException {
		
		this.roleService.saveRole2Menu(roleId, addMenus, delMenus, addBtns, delBtns);
		return roleId.toString();
	}
	
		
	/**
	 * 分配菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addRoleMenu")
	public Map<String, Object> addRoleMenu(HttpServletRequest request,
			HttpServletResponse response,RoleToMenu roleToMenu,String nodes,String menus) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list1 = new ArrayList<String>(); //修改之前分配的菜单id
		List<String> list2 = new ArrayList<String>(); //修改之后的菜单id
		String[] menu=new String[0];
		String[] index =new String[0];
		
		if(!menus.equals("")){
			 menu = menus.split(",");//修改之前分配的菜单id
		}
		if(!nodes.equals("")){
			index = nodes.split(",");//修改之后的菜单id
		}
		
		for (int i = 0; i < index.length; i++) {
			list2.add(index[i]);
		}
		for (int i = 0; i < menu.length; i++) {
			list1.add(menu[i]);
		}
		try {
			//删除
			for(String newMenuId : list1){
				System.out.println("list1="+list1);
				if(!list2.contains(newMenuId)){
					roleToMenu.setMenuId(String.valueOf(newMenuId));
					roleToMenuService.delete(roleToMenu);
					
					RoleToBtn	roleToBtn=new RoleToBtn();
					roleToBtn.setRoleId(roleToMenu.getRoleId());//删除取消勾选后的按钮
					roleToBtnService.delete(roleToBtn);
				}
			}
			
			//新增
			for(String oldMenuId : list2){
				if(!list1.contains(oldMenuId)){
					roleToMenu.setMenuId(String.valueOf(oldMenuId));
					roleToMenuService.save(roleToMenu);
				}
			resultMap.put("state", "Success");
			resultMap.put("message", "分配成功");
			}
		} catch (Exception e) {
			throw new BusinessException("分配失败");
		}
		return resultMap;
	}
		
		
	/**
	 * 分配按钮
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addRoleBtn")
	public Map<String, Object> addRoleBtn(HttpServletRequest request,HttpServletResponse response,RoleToBtn roleToBtn,String nodes,String btns) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<String> list1 = new ArrayList<String>(); //修改之前分配的菜单id
		List<String> list2 = new ArrayList<String>(); //修改之后的菜单id
		String[] btn=new String[0];
		String[] index =new String[0];
		
		if(!btns.equals("")){
			btn = btns.split(",");//修改之前分配的菜单id
		}
		
		if(!nodes.equals("")){
			index = nodes.split(",");//修改之后的菜单id
		}
		
		for (int i = 0; i < index.length; i++) {
			list2.add(index[i]);
		}
		for (int i = 0; i < btn.length; i++) {
			list1.add(btn[i]);
		}
		try {
			//删除
			for(String newButtonId : list1){
				if(!list2.contains(newButtonId)){
					roleToBtn.setButtonId(String.valueOf(newButtonId));
					
					roleToBtnService.delete(roleToBtn);
				}
			}
			//新增
			for(String oldButtonId : list2){
				if(!list1.contains(oldButtonId)){
					roleToBtn.setButtonId(String.valueOf(oldButtonId));
					roleToBtnService.save(roleToBtn);
				}
			}
			
			resultMap.put("state", "Success");
			resultMap.put("message", "分配成功");
		} catch (Exception e) {
			throw new BusinessException("分配失败",e);
		}
		
		return resultMap;
	}
		
			
	/**
	 * @author linlong
	 * @description 配置组织机构树
	 * @param 
	 * @return List<Map<String, Object>>
	 * @throws Exception 
	 * @develop date 2016.08.10
	 */
	private  void roleDprtTree(String roleId,String id,Map<String, Object> menuMap,List<Department> department) throws Exception{  
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();   
		
		Role role1=roleService.findOneByRoleID(roleId);
		
	    for (Department departments : department) {  
	        if (null != departments.getParentid() && id.equals(departments.getParentid())) { 
	        	RoleToDprt roleToDprt=new RoleToDprt();
	        	
	        	roleToDprt.setRoleId(String.valueOf(roleId));
	        	roleToDprt.setDprtId(departments.getId());
	        	
	        	int num=roleToDprtService.queryCount(roleToDprt);
	        	
	        	Map<String, Object> childrenMap = new HashMap<String, Object>();
        		if(num>0){
        			childrenMap.put("checked", "true");
        		}
        		
        		if(role1.getDprtId().equals(departments.getId())){
        			childrenMap.put("chkDisabled", "true");
        		}
	        	childrenMap.put("id", departments.getId());
	        	childrenMap.put("name", departments.getDprtname());
	        	childrenMap.put("state", "open");
	        	childrenMap.put("iconCls", "icon-blank");
	        	
	        	roleDprtTree(roleId,departments.getId(),childrenMap,department);  
	        	 menuList.add(childrenMap);  
	        }  
	    }  
	    menuMap.put("children", menuList);

    }  
		
	// 查询组织机构树
	@Privilege(code="sys:role:main:04")
	@ResponseBody
	@RequestMapping(value="queryRoleDprtTree", method = RequestMethod.POST)
	public List<Map<String, Object>> queryRoleDprtTree(HttpServletRequest request,Model model) throws Exception {
		String roleId=request.getParameter("roleId");
		if(roleId==null){
			roleId="0";
		}
		
		List<Department> dprts = null;
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//超级机构用户 拥有的组织机构
			dprts = departmentService.queryOrg();
		}else{
			//非超级机构获取当前用户机构代码
			dprts = departmentService.findDepartmentByUserId(user.getId());
		}
		Map<String, Object> menuMap = new HashMap<String, Object>();
	
		roleDprtTree(roleId,"0",menuMap,dprts);
		
		List<Map<String, Object>> menuList = (List<Map<String, Object>>) menuMap.get("children");
		return menuList;
	}
		
	/**
	 * 分配组织机构
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("addRoleDprt")
	public Map<String, Object> addRoleDprt(HttpServletRequest request,HttpServletResponse response,RoleToDprt roleToDprt,String nodes,String dprts) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> list1 = new ArrayList<String>(); //修改之前分配的组织机构
		List<String> list2 = new ArrayList<String>(); //修改之后的组织机构
		String[] menu=new String[0];
		String[] index =new String[0];
		
		if(!dprts.equals("")){
			 menu = dprts.split(",");//修改之前分配的组织机构id
		}
		if(!nodes.equals("")){
			index = nodes.split(",");//修改之后的组织机构id
		}
		
		for (int i = 0; i < index.length; i++) {
			list2.add(index[i]);
		}
		for (int i = 0; i < menu.length; i++) {
			list1.add(menu[i]);
		}
		
		try {
			//删除
			for(String newMenuId : list1){
				if(!list2.contains(newMenuId)){
					roleToDprt.setDprtId(newMenuId);
					roleToDprtService.delete(roleToDprt);
				}
			}
			
			//新增
			for(String oldMenuId : list2){
				if(!list1.contains(oldMenuId)){
					roleToDprt.setDprtId(oldMenuId);
					roleToDprtService.save(roleToDprt);
				}
			}
			
			resultMap.put("state", "Success");
		} catch (Exception e) {
			resultMap.put("state", e);
			throw new BusinessException("分配失败",e);
		}
		return resultMap;
	}
		
	/**
	 * 删除角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Privilege(code="sys:role:main:05")
	@ResponseBody
	@RequestMapping("removeRole/{ids}")
	public Map<String, Object> removeRole(@PathVariable("ids") String ids, HttpServletRequest request,HttpServletResponse response) throws  Exception {
		
		return roleService.delete(ids);
	}
		
	/**
	 * 删除机型角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Privilege(code="sys:role:main:08")
	@ResponseBody
	@RequestMapping("removeModelRole/{ids}")
	public Map<String, Object> removeModelRole(@PathVariable("ids") String ids, HttpServletRequest request,HttpServletResponse response) throws  Exception {
		
		return modelRoleService.delete(ids);
	}
		
	/**
	 * 删除库房角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	@Privilege(code="sys:role:main:12")
	@ResponseBody
	@RequestMapping("removeWarehouseRole/{ids}")
	public Map<String, Object> removeWarehouseRole(@PathVariable("ids") String ids, HttpServletRequest request,HttpServletResponse response) throws  Exception {
		
		return warehouseRoleService.delete(ids);
	}
		
	/**
	 * @author liub
	 * @description 查询所有的仓库数据,级联查询选中标记
	 * @param request,model
	 * @return
	 * @develop date 2016.09.09
	 */
	@Privilege(code="sys:role:main:11")
	@ResponseBody
	@RequestMapping(value = "findAllByRoleIdtive", method = RequestMethod.POST)
	public List<Map<String, Object>> findAllByRoleIdtive(Model model,String id,String dprtcode) throws BusinessException {

		List<Map<String, Object>> storeList = null;
		try {
			storeList = storeService.findAllByRoleIdtive(id,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询所有的仓库数据,级联查询选中标记失败!",e);
		}finally{}
		
		return storeList;
	}
	
	/**
	 * 角色仓库授权
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("saveRoleToStore")
	public void saveRoleToStore(HttpServletRequest request,HttpServletResponse response,String roleId,String oldStoreIds,String newStoreIds) throws BusinessException {
		
		try {
			roleToStoreService.saveRoleToStore(roleId, oldStoreIds, newStoreIds);
		} catch (Exception e) {
			 throw new BusinessException("仓库授权失败!",e);
		}
	}
}
