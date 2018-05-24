package com.eray.thjw.control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

/**
 * @author pingxiaojun
 * @description 组织机构 controller类
 * @develop date 2016.08.08
 */
@Controller
@RequestMapping("/sys/department")
public class DepartmentController {

	// 日志记录器
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

	// 组织机构Service
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DeptInfoService deptInfoService;

	/**
	 * @author pingxiaojun
	 * @description 跳转至组织机构界面
	 * @develop date 2016.08.08
	 * @return 跳转路径
	 */
	@Privilege(code = "sys:department:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return "sys/department/departmentMain";
	}

	/**
	 * @author meizhiliang
	 * @description 组织机构列表
	 * @param department
	 *            组织机构列表的参数
	 * @develop date 2016.08.08
	 * @return 组织机构列表的数据
	 */
	@ResponseBody
	@RequestMapping(value = "queryDepartmentList", method = RequestMethod.POST)
	public Map<String, Object> queryDepartmentList(@RequestBody Department department, HttpServletRequest request,
			Model model) {
		User user = ThreadVarUtil.getUser();
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			department.getParamsMap().put("userId", user.getId());
		}
		
		PageHelper.startPage(department.getPagination());
		List<Department> list = departmentService.queryDepartmentListByPage(department);
		return PageUtil.pack4PageHelper(list, department.getPagination());
	}

	/**
	 * @author meizhiliang
	 * @description 初始化新增组织机构界面
	 * @develop date 2016.08.08
	 * @return 新增组织机构界面
	 */
	@Privilege(code="sys:department:main:01")
	@RequestMapping("intoAddDepartment")
	public ModelAndView intoAddDepartment(HttpServletRequest req, HttpServletResponse resp) {

		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("dprtType", GlobalConstants.getList(GlobalConstants.AGENCY_TYPE_KEY));
		map.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("/sys/department/add_department", map);
	}

/*	*//**
	 * 加载组织机构的上级组织机构
	 * 
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping(value = "findAllDepartment", method = RequestMethod.POST)
	public List<Department> findAllDepartment() {
		return departmentService.findAllff();
	}*/

	@ResponseBody
	@RequestMapping(value = "getDepartmentConut", method = RequestMethod.POST) // 判断组织机构是否已经存在
	public Map<String, Object> getDepartmentConut(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws Exception {

		return departmentService.checkUpdMt(department);
	}

	/**
	 * @author meizhiliang
	 * @description 新增组织机构
	 * @param department
	 *            新增组织机构信息的参数
	 * @develop date 2016.08.08
	 * @throws Exception
	 *             新增组织机构信息出现的异常
	 * @return 新增组织机构的处理结果
	 */
	@ResponseBody
	@RequestMapping(value = "addDepartment", method = RequestMethod.POST)
	public String addDepartment(@RequestBody DeptInfo deptInfo, HttpServletRequest request) throws Exception {
		UUID uuid = UUID.randomUUID();//获取随机的uuid	
		String id=uuid.toString();
		deptInfo.setId(id);
		try {
			int count=deptInfoService.insertDeptInfo(deptInfo);
			if(count>0){
				return id;
			}
			// commonRecService.write(id, TableEnum.B_G_007, user.getId(),
			// UpdateTypeEnum.SAVE); //增加操作日志
		} catch (Exception e) {
			throw new BusinessException("增加组织机构失败！");
		}
		return "";
	}

	/**
	 * @author meizhiliang
	 * @description 初始化修改组织机构界面
	 * @param id
	 *            组织机构主键id
	 * @develop date 2016.08.08
	 * @return 修改组织机构的界面
	 */
	@Privilege(code="sys:department:main:02")
	@RequestMapping("intoModifyDepartment/{id}")
	public ModelAndView intoModifyDepartment(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse resp) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Department department1 = new Department();
		department1.setId(id); 
		map.put("dprtType", GlobalConstants.getList(GlobalConstants.AGENCY_TYPE_KEY));
		map.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		DeptInfo deptInfo=deptInfoService.selectById(id);
		String yxqks="";
		String yxqjs="";
		if(deptInfo!=null){
			Date first=deptInfo.getYxqks();
			Date end=deptInfo.getYxqjs();
			if(first!=null){
				yxqks=df.format(first);				
			}
			if(end!=null){
				yxqjs=df.format(end);
			}
		}
		map.put("yxqks", yxqks);
		map.put("yxqjs", yxqjs);
		map.put("deptInfo", deptInfo);
		Department department = departmentService.selectByDepartment(department1);
	
		map.put("department", department);
		return new ModelAndView("/sys/department/modify_department", map);
	}

	/**
	 * @author meizhiliang
	 * @description 修改组织机构信息
	 * @param department
	 *            修改组织机构修改信息的参数
	 * @develop date 2016.08.09
	 * @throws Exception
	 *             修改组织机构信息出现的异常
	 * @return 修改组织机构的处理结果
	 */
	@ResponseBody
	@RequestMapping(value = "modifyDepartment", method = RequestMethod.POST)
	public String modifyDepartment(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws Exception {
		if(department.getId()==null){
			String id=departmentService.insertdepartment(department);//获得返回的id以便在树节点定位
			return id;
		}
		else{
			departmentService.updatedepartment(department);
			return department.getId();
		}
	}
	/**
	 * 只修改
	 * @param request
	 * @param response
	 * @param deptInfo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "modifyDepart", method = RequestMethod.POST)
	public String modifyDepart(HttpServletRequest request, HttpServletResponse response,
			@RequestBody DeptInfo deptInfo) throws Exception {
		deptInfoService.updateById(deptInfo);
		return deptInfo.getId();
	}

	/**
	 * @author pingxiaojun
	 * @description 删除组织机构信息
	 * @param id
	 *            组织机构主键id
	 * @develop date 2016.08.10
	 * @throws Exception
	 *             删除组织机构出现的异常
	 * @return 删除组织机构的处理结果
	 */
	@Privilege(code="sys:department:main:03")
	@ResponseBody
	@RequestMapping(value = "deleteDepartment", method = RequestMethod.POST)
	public void deleteDepartment(String id, String dprttype) throws BusinessException {
		try {
			departmentService.deleteDepartment(id, dprttype);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * @author pingxiaojun
	 * @description 查询组织机构信息
	 * @param id
	 *            组织机构主键id
	 * @develop date 2016.08.11
	 * @return 组织机构信息
	 */
	@ResponseBody
	@RequestMapping(value = "queryDepartment/{id}", method = RequestMethod.POST)
	public Object queryDepartment(@PathVariable("id") String id) {
		StringBuffer sbf = new StringBuffer();
		sbf.append("组织机构主键id：" + id);
		logger.info("前端参数：" + sbf);

		Map<String, Object> map = new HashMap<String, Object>();

		// 查询组织机构信息
		Department department = departmentService.queryByPrimaryKey(id);
		map.put("department", department);

		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "addDepart", method = RequestMethod.POST)
	public String addDepart(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws Exception {
		String id=UUID.randomUUID().toString();
		department.setId(id);
		departmentService.insertSelective(department);
		return id;
	}
	
	@ResponseBody
	@RequestMapping(value="queryRoleMenuTree", method = RequestMethod.POST)
	public List<Map<String, Object>> queryRoleMenuTree(HttpServletRequest request,Model model) throws Exception {
		String roleId=request.getParameter("roleId");
		if(StringUtils.isBlank(roleId)){
			throw new BusinessException("查询出错");
		}
		List<Department> list=departmentService.findAlls();
	    return roleMenuTree("0", list, roleId);
	}
	
	private List<Map<String, Object>> roleMenuTree(String menuParentId, List<Department> list,String id) {  
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> nodeMap = null;
		for (Department menu : list) {
			if(id!=null){
	        if (null != menu.getParentid()&& menuParentId.equals(menu.getParentid())&&id.equals(menu.getId())){ 
	        	//根据父节点组装其子节点
	        	nodeMap = new HashMap<String, Object>();//树节点属性
	        	nodeMap.put("id", menu.getId());
	        	String name	= menu.getDprtcode()+"/"+menu.getDprtname();
	        	nodeMap.put("name",name);
	        	nodeMap.put("state", "open");
	        	nodeMap.put("type", "1");
	        	nodeMap.put("iconCls", "icon-sitemap");
	        	nodeMap.put("order", menu.getPxh());
	        	if(menuParentId == "0"){
	        		nodeMap.put("order", menu.getPxh());
	        	}	        	
	        	List<Map<String, Object>> menuChildrenList = roleMenuTree(menu.getId(), list,null);
	        	        	
	        	nodeMap.put("children", menuChildrenList);
	        	
	        	returnList.add(nodeMap);
	        }
			}else{
				 if (null != menu.getParentid() && menuParentId.equals(menu.getParentid())){ 
			        	//根据父节点组装其子节点
			        	nodeMap = new HashMap<String, Object>();//树节点属性
			        	nodeMap.put("id", menu.getId());
			        	String name	= menu.getDprtcode()+"/"+menu.getDprtname();
			        	nodeMap.put("name",name);
			        	nodeMap.put("state", "open");
			        	nodeMap.put("type", "1");
			        	nodeMap.put("iconCls", "icon-blank");
			        	nodeMap.put("order", menu.getPxh());
			        	if(menuParentId == "0"){
			        		nodeMap.put("order", menu.getPxh());
			        	}	        	
			        	List<Map<String, Object>> menuChildrenList = roleMenuTree(menu.getId(), list,null);
			        	        	
			        	nodeMap.put("children", menuChildrenList);
			        	
			        	returnList.add(nodeMap);
				 }
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
	@ResponseBody
	@RequestMapping(value = "getDprt", method = RequestMethod.POST)
	public Department getDprt(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws Exception {
		
		return departmentService.selectByPrimaryKey(department.getId());
	}
	
	@ResponseBody
	@RequestMapping(value = "getParentDprt", method = RequestMethod.POST)
	public Department getParentDprt(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws Exception {
		
		return departmentService.getParentDepartmentByChildId(department.getId());
	}
	/**
	 * 作废部门
	 * @param request
	 * @param response
	 * @param department
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "InvalidDepartment", method = RequestMethod.POST)
	public void InvalidDepartment(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws BusinessException {
		try {
			departmentService.deleteDepartment(department.getId(), department.getDprttype());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		} finally {
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "EditDepartment", method = RequestMethod.POST)
	public void EditDepartment(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Department department) throws BusinessException {
		try {
			departmentService.updateById(department);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		} finally {
		}
	}
	
	@ResponseBody
	@RequestMapping(value="queryDepartTree", method = RequestMethod.POST)
	public List<Map<String, Object>> queryDepartTree(@RequestBody Department department,HttpServletRequest request,Model model) throws Exception {
		List<Department> list=departmentService.getBm4JdList(department);
		if(department.getJdbs()!=null&&department.getJdbs()==1){
			return roleDepartJdbsTree(list,department.getId());
		}else{
		    return roleDepartTree("0", list, department.getId());
		}		
	}
	
	private List<Map<String, Object>> roleDepartTree(String menuParentId, List<Department> list,String id) {  
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		Map<String, Object> nodeMap = null;
		for (Department menu : list) {
			if(id!=null){
	        if (null != menu.getParentid()&& menuParentId.equals(menu.getParentid())&&id.equals(menu.getId())){ 
	        	//根据父节点组装其子节点
	        	nodeMap = new HashMap<String, Object>();//树节点属性
	        	nodeMap.put("id", menu.getId());
	        	String name	= menu.getDprtcode()+"/"+menu.getDprtname();
	        	nodeMap.put("name",name);
	        	nodeMap.put("state", "open");
	        	nodeMap.put("type", "1");
	        	nodeMap.put("iconCls", "icon-sitemap");
	        	nodeMap.put("order", menu.getPxh());
	        	if(menuParentId == "0"){
	        		nodeMap.put("order", menu.getPxh());
	        	}	        	
	        	List<Map<String, Object>> menuChildrenList = roleDepartTree(menu.getId(), list,null);
	        	        	
	        	nodeMap.put("children", menuChildrenList);
	        	
	        	returnList.add(nodeMap);
	        }
			}else{
				 if (null != menu.getParentid() && menuParentId.equals(menu.getParentid())){ 
			        	//根据父节点组装其子节点
					 	nodeMap = new HashMap<String, Object>();//树节点属性
			        	nodeMap.put("id", menu.getId());
			        	String name	= menu.getDprtcode()+"/"+menu.getDprtname();
			        	nodeMap.put("name",name);
			        	nodeMap.put("state", "open");
			        	nodeMap.put("type", "1");
			        	nodeMap.put("iconCls", "icon-blank");
			        	nodeMap.put("order", menu.getPxh());
			        	if(menuParentId == "0"){
			        		nodeMap.put("order", menu.getPxh());
			        	}	        	
			        	List<Map<String, Object>> menuChildrenList = roleDepartTree(menu.getId(), list,null);
			        	        	
			        	nodeMap.put("children", menuChildrenList);
			        	
			        	returnList.add(nodeMap);
				 }
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
	
	private List<Map<String, Object>> roleDepartJdbsTree(List<Department> list,String id){
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> menuChildrenList = new ArrayList<Map<String, Object>>();
		Map<String, Object> nodeMap = null;
		Map<String,Object> map =new HashMap<String,Object>();
		for (int i = 0; i < list.size(); i++) {
			if(i!=0){
				nodeMap = new HashMap<String, Object>();//树节点属性
	        	nodeMap.put("id", list.get(i).getId());
	        	String name	= list.get(i).getDprtcode()+"/"+list.get(i).getDprtname();
	        	nodeMap.put("name",name);
	        	nodeMap.put("state", "open");
	        	nodeMap.put("type", "1");
	        	nodeMap.put("iconCls", "icon-sitemap");
	        	nodeMap.put("order", list.get(i).getPxh());
	        	menuChildrenList.add(nodeMap);
			}else{
				nodeMap = new HashMap<String, Object>();//树节点属性
	        	map.put("id", list.get(i).getId());
	        	String name	= list.get(i).getDprtcode()+"/"+list.get(i).getDprtname();
	        	map.put("name",name);
	        	map.put("state", "open");
	        	map.put("type", "1");
	        	map.put("iconCls", "icon-sitemap");
	        	map.put("order", list.get(i).getPxh());	        		    	        	
			}
		} 
		
		map.put("children", menuChildrenList);
		returnList.add(map);
		return returnList;
	}
	
	/**
	 * 
	 * @Description  查询基地
	 * @CreateTime 2017-9-20 下午4:19:38
	 * @CreateBy 孙霁
	 * @param department
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDepartmentByJd", method = RequestMethod.POST)
	public List<Department> queryDepartmentByJd(@RequestParam String id)throws BusinessException  {
		try {
			return departmentService.queryJdByid(id);
		} catch (BusinessException e) {
			throw new BusinessException("查询失败！", e);
		}
		
	}
	/**
	 * 
	 * @Description 获取基地标识为1的部门集合
	 * @CreateTime 2017年9月27日 下午2:01:56
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getBmList", method = RequestMethod.POST)
	public List<Department> getBmList(String dprtcode) throws BusinessException {
		try {
			return departmentService.getBmList(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 获取组织机构下所有部门的下拉框
	 * @CreateTime 2017年10月26日 下午2:39:08
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getChildrentList", method = RequestMethod.POST)
	public List<Department> getChildrentList(String dprtcode) throws BusinessException {
		try {
			return departmentService.getAllBmList(dprtcode);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 查询机构列表
	 * @CreateTime 2017年9月26日 上午9:31:59
	 * @CreateBy 朱超
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryOrgs", method = RequestMethod.POST)
	public List<Department> queryOrgs(@RequestBody BaseEntity entity)throws BusinessException  {
		try {
			return departmentService.queryOrgs(entity);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
		
	}
	/**
	 * @Description 获得某个机构下的所有部门，支持分页
	 * @CreateTime 2017-9-29 上午11:15:06
	 * @CreateBy 甘清
	 * @param department 查询参数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value = "queryDept", method = RequestMethod.POST)
	public Map<String, Object> queryDept(@RequestBody Department department) {
		return departmentService.selectDepartmentListByPageNew(department);
	}
	
	/**
	 * 
	 * @Description 获取上级部门是否为基地标识为1的
	 * @CreateTime 2017年10月17日 下午4:33:42
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectDepartmentPid", method = RequestMethod.POST)
	public Department selectDepartmentPid(String dprtcode) {
			List<Department> departmentList=departmentService.selectDepartmentPid(dprtcode);
			for (Department department : departmentList) {
				if(department.getJdbs()==1){
					return department;
				}
			}
			return null;
	}
	
}
