package com.eray.thjw.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.common.util.UserUtil;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.dao.PlaneModelDataMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Button;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.Menu;
import com.eray.thjw.po.User;
import com.eray.thjw.po.UserToModelRole;
import com.eray.thjw.po.UserToRole;
import com.eray.thjw.po.UserToWarehouseRole;
import com.eray.thjw.service.ButtonService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.service.MenuService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.service.UserToModelRoleService;
import com.eray.thjw.service.UserToRoleService;
import com.eray.thjw.service.UserToWarehouseRoleService;
import com.eray.thjw.system.po.Login;
import com.eray.thjw.system.service.AccountService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.DelStatus;
import enu.LogOperationEnum;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper usermapper;

	@Autowired
	private CommonService commonService;
	@Autowired
	private DeptInfoMapper deptInfoMapper;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserToRoleService userToRoleService;
	@Autowired
	private UserToModelRoleService userToModelRoleService;
	@Autowired
	private UserToWarehouseRoleService userToWarehouseRoleService;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private DeptInfoService deptInfoService;
	
	@Resource
	private StoreMapper storeMapper;
	
	@Resource
	private AccountService accountService;
	
	private String parentIcon = "icon-folder-open-alt";
	private String childrenIcon = "icon-user";

	
	/**
	 * @author liub
	 * @description 菜单service
	 * @develop date 2016.08.10
	 */
	@Autowired
	private MenuService menuService;
	
	/**
	 * @author liub
	 * @description 按钮service
	 * @develop date 2016.08.10
	 */
	@Autowired
	private ButtonService buttonService;
	
	@Autowired
	private PlaneModelDataMapper planeModelDataMapper;
	
	@Autowired
	private AttachmentService attachmentService;
	

	/**
	 * @author sunji
	 * @description 根据id修改数据
	 * @develop date 2016.08.5
	 */
	@Override
	public void updateByPrimaryKeySelective(User user) {
		usermapper.updateByPrimaryKeySelective(user);
		
	}
	/**
	 * @author sunji
	 * @description 用户service,用于查询所有数据及所在组织机构
	 * @develop date 2016.08.5
	 */
	@Override
	public Map<String, Object> SelectByUser(User user) {
		if(StringUtils.isBlank(user.getJgdm())){
			User sessionUser = ThreadVarUtil.getUser();
			if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(sessionUser.getOrgcode())){
				user.getParamsMap().put("userId", sessionUser.getId());
			}
		}
		String id=user.getId();
		user.setId("");
		PageHelper.startPage(user.getPagination());
		List<User> list=usermapper.queryUserFetchDepartment(user);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					User newRecord = new User();
					newRecord.setId(id);
					List<User> newRecordList = usermapper.queryUserFetchDepartment(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, user.getPagination());
			
		}else{
			List<User> newRecordList = new ArrayList<User>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				User newRecord = new User();
				newRecord.setId(id);
				newRecordList = usermapper.queryUserFetchDepartment(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, user.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, user.getPagination());
		}
		
		
		
	}
	
	
	/**
	 * @author sunji
	 * @throws BusinessException 
	 * @description 用户service,添加用户
	 * @develop date 2016.08.5
	 */
	@Override
	public String insertUser(User user) throws BusinessException {
		
		User user1=new User();
		user1.setUsername(user.getUsername());
		user1.setJgdm(user.getJgdm());
		List<User> usernew=usermapper.queryjgdm(user1);
		if(usernew!=null&& !usernew.isEmpty()){
			throw new BusinessException("用户代码重复，请重新填写");
		}
		String czls = UUID.randomUUID().toString();
		if(StringUtils.isNotBlank(user.getDrzhid())){
			
			Login login=accountService.selectByPrimaryKey(user.getDrzhid());
			if(login != null){
				//如果是选择的账号
				//判断账号是否被绑定了
				List<User> list = this.usermapper.selectByDrzhid(login.getId());
				if(list != null && !list.isEmpty()){//如果账号已被占用，提示用户
					User tempUser = list.get(0);
					throw new BusinessException("该账号已与用户["+tempUser.getRealname()+"]绑定，请先解除绑定关系");
				}
				
				if(StringUtils.isBlank(login.getJgdm())){
					//创建账号
					login.setJgdm(user.getJgdm());
					this.accountService.updateByPrimaryKey(login);
				}else{
					if(!user.getJgdm().equals(login.getJgdm())){
						throw new BusinessException("该用户没有此账号组织机构权限，请重新选择");
					}
				}
			}else{
				throw new BusinessException("该账号不存在，请重新选择");
			}
			
		}else if(StringUtils.isNotBlank(user.getAccountName())){ //手动输入的用户名
			//创建账号
			
			
			if(!user.getAccountName().equals("")){
				Login login1=accountService.selectByPrimaryName(user.getAccountName());
				if(login1 != null){
					List<User> list = this.usermapper.selectByDrzhid(login1.getId());
					if(list != null && !list.isEmpty()){//如果账号已被占用，提示用户
						User tempUser = list.get(0);
						throw new BusinessException("该账号已与用户["+tempUser.getRealname()+"]绑定，请先解除绑定关系");
					}
					
					if(StringUtils.isBlank(login1.getJgdm())){
						//创建账号
						login1.setJgdm(user.getJgdm());
						this.accountService.updateByPrimaryKey(login1);
					}else{
						if(!user.getJgdm().equals(login1.getJgdm())){
							throw new BusinessException("该用户没有此账号组织机构权限，请重新选择");
						}
					}
					
					user.setDrzhid(login1.getId());
				}else{
					Login login = new Login();
					login.setUsername(user.getAccountName());
					login.setJgdm(user.getJgdm());
					this.accountService.saveAccount(login);
					user.setDrzhid(login.getId());
				}
			}else{
				user.setDrzhid("");
			}
		
		}
		
		user.setState(1);
		user.setId(UUID.randomUUID().toString());
		user.setBmdm(user.getDepartmentId());
		
		//根据当前选择的部门id查询从当前部门往上的所有父数据（包含自己）
		if(user.getDepartmentId()!=null&&!user.getDepartmentId().equals("")){
			List<Department> departmentList=departmentService.selectDepartmentPid(user.getDepartmentId());
			for (Department department : departmentList) {
				if(department.getJdbs()==1){
					user.setJddm(department.getId());//用户选择部门后往部门上级递归查找基地标识为是的 部门ID存入该字段
					break;
				}
			}
		}
		
		//4.添加用户
		usermapper.insertSelective(user);
		String id=user.getId();
		
		//5.遍历功能角色id如果有，进行添加
		List <String>roleIds=user.getRolesId();
		for (String roleId : roleIds) {
			//添加角色
			UserToRole userToRole=new UserToRole();
			UUID uuid = UUID.randomUUID();
			String urId=uuid.toString();
			userToRole.setUrId(urId);
			userToRole.setRoleId(roleId);
			userToRole.setUserId(id);
			userToRoleService.insert(userToRole);
		}
		
		//6.遍历机型角色id如果有，进行添加
		List<String> modelRolesIds=user.getModelRolesId();
		for (String roleId : modelRolesIds) {
			//添加角色
			UserToModelRole userToModelRole=new UserToModelRole();
			UUID uuid = UUID.randomUUID();
			String urId=uuid.toString();
			userToModelRole.setUrId(urId);
			userToModelRole.setRoleId(roleId);
			userToModelRole.setUserId(id);
			userToModelRoleService.insert(userToModelRole);
		}
		
		//7.遍历库房角色id如果有，进行添加
		List<String> warehouseRolesIds=user.getWarehouseRolesId();
		for (String roleId : warehouseRolesIds) {
			//添加角色
			UserToWarehouseRole userToWarehouseRole=new UserToWarehouseRole();
			UUID uuid = UUID.randomUUID();
			String urId=uuid.toString();
			userToWarehouseRole.setUrId(urId);
			userToWarehouseRole.setRoleId(roleId);
			userToWarehouseRole.setUserId(id);
			userToWarehouseRoleService.insert(userToWarehouseRole);
		}
		
		//8.对电子签名进行添加
		if(user.getAttachment() != null){
			Attachment attachment = user.getAttachment();
			attachment.setId(UUID.randomUUID().toString());
			attachment.setMainid(user.getId());
			attachmentService.addAttachment(attachment, user.getId(), czls, user.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
		}
		return user.getId();
	}
	
	

	
	
	/**
	 * @author sunji
	 * @description 用户service,删除用户
	 * @develop date 2016.08.5
	 */
	@Override
	public String deleteUser(String userId) {
		if(usermapper.deleteByPrimaryKey(userId)==0){
			return "success";
		}else{
			return "error";
		}
	}
	/**
	 * @author sunji
	 * @description 用户service,修改密码
	 * @develop date 2016.08.5
	 */
	@Override
	
	public String updateUserPassword(User user) {
		user.setPassword(UserUtil.MD5(user.getNewpassword()));
		usermapper.updateUserPassword(user);
		return "success";
	}
	
	/**
	 * @author peixiu
	 * @description 用户service,修改用户信息
	 * @develop date 2017.03.5
	 */
	
	@Override
	public String updateUserInfo(User user) {
		
		
		usermapper.updateUserInfo(user);
		return "success";
	}
	/**
	 * @author sunji
	 * @description 用户service,修改状态
	 * @develop date 2016.08.5
	 */
	@Override
	public String updateUserState(User user) throws Exception {
		
		if(usermapper.updateUserState(user)==1){
			return "success";
		}else{
			return "error";
		}
	}
	/**
	 * @author liub
	 * @description 用户登录验证
	 * @param username,password
	 * @return Map<String, Object>
	 * @develop date 2016.08.08
	 */
	@Override
	public Map<String, Object> login(String username,String password, HttpServletRequest request, String ip) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回页面数据

		//判断用户名是否存在
		User user = usermapper.getByUserNameAndPassword(username,password);
		if(null == user){
			returnMap.put("islegal", "flase");
			returnMap.put("username", username);
			returnMap.put("password", password);
			returnMap.put("message", "账号或密码不正确");
			return returnMap;
		}else if(DelStatus.LOSE_EFFECT.getId().intValue() == user.getState().intValue()){
			returnMap.put("islegal", "flase");
			returnMap.put("username", username);
			returnMap.put("password", password);
			returnMap.put("message", "账号已被停用");
			return returnMap;
		}
		
		//是否通过IP/MAC地址限制登陆
		if(GlobalConstants.getBoolean(GlobalConstants.MAC_LIMIT_KEY)){
			if(StringUtils.isBlank(ip)){
				returnMap.put("islegal", "flase");
				returnMap.put("username", username);
				returnMap.put("password", password);
				returnMap.put("message", "未获取到客户端编号");
				return returnMap;
			}
			String mac = "";
			int limit = this.usermapper.selectLimitByIpOrMac(user.getJgdm(),ip, mac);
			if(limit == 0){
				returnMap.put("islegal", "flase");
				returnMap.put("username", username);
				returnMap.put("password", password);
				returnMap.put("message", "当前网络或客户端被限制登录");
				return returnMap;
			}
		}
		
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//查有效期
			DeptInfo accounts = deptInfoMapper.selectCounts(user.getJgdm());
			
			SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
			if(accounts!=null){
				if(accounts.getYxqjs()==null&&accounts.getYxqks()==null){
					
				}else{
						try {
							if(accounts.getYxqjs()==null&&formatter.parse(formatter.format(commonService.getSysdate())).getTime()<formatter.parse(formatter.format(accounts.getYxqks())).getTime()){
								//当前日期小于开始日期 结束日期为空
								returnMap.put("islegal", "flase");
								returnMap.put("username", username);
								returnMap.put("password", password);
								returnMap.put("message", "账号不在有效期内，请联系管理员");
								return returnMap;
							}else if(accounts.getYxqks()==null&&formatter.parse(formatter.format(commonService.getSysdate())).getTime()>formatter.parse(formatter.format(accounts.getYxqjs())).getTime()){
								//当前时间大于结束日期 开始日期为空
								returnMap.put("islegal", "flase");
								returnMap.put("username", username);
								returnMap.put("password", password);
								returnMap.put("message", "账号不在有效期内，请联系管理员");
								return returnMap;
							}else if(accounts.getYxqjs()!=null&&accounts.getYxqks()!=null&&(formatter.parse(formatter.format(commonService.getSysdate())).getTime()<formatter.parse(formatter.format(accounts.getYxqks())).getTime()
									||formatter.parse(formatter.format(commonService.getSysdate())).getTime()>formatter.parse(formatter.format(accounts.getYxqjs())).getTime())){
								//当前时间小于开始日期大于结束日期
								returnMap.put("islegal", "flase");
								returnMap.put("username", username);
								returnMap.put("password", password);
								returnMap.put("message", "账号不在有效期内，请联系管理员");
								return returnMap;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
				}
					
			}
		}
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			initAdminMap(returnMap, user);//初始化超级管理员数据
		}else{
			initUserMap(returnMap, user);//初始化普通管理员数据
		}
		returnMap.put("id", user.getId());
		returnMap.put("loginType", "sysLoginPage");
		return returnMap;
	}
	
	@Override
	public Map<String, Object> login4demo(String username,String password, HttpServletRequest request) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回页面数据

		//判断用户名是否存在
		User user = usermapper.getByUserNameAndPassword(username,password);
		if(null == user){
			returnMap.put("islegal", "flase");
			returnMap.put("username", username);
			returnMap.put("password", password);
			returnMap.put("message", "账号或密码不正确");
			return returnMap;
		}else if(DelStatus.LOSE_EFFECT.getId().intValue() == user.getState().intValue()){
			returnMap.put("islegal", "flase");
			returnMap.put("username", username);
			returnMap.put("password", password);
			returnMap.put("message", "账号已被停用");
			return returnMap;
		}
		
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			//查有效期
			DeptInfo accounts = deptInfoMapper.selectCounts(user.getJgdm());
			SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DEFAULT_FORMAT_DATE);
			if(accounts!=null){
				if(accounts.getYxqjs()==null&&accounts.getYxqks()==null){
					
				}else{
						try {
							if(accounts.getYxqjs()==null&&formatter.parse(formatter.format(commonService.getSysdate())).getTime()<formatter.parse(formatter.format(accounts.getYxqks())).getTime()){
								//当前日期小于开始日期 结束日期为空
								returnMap.put("islegal", "flase");
								returnMap.put("username", username);
								returnMap.put("password", password);
								returnMap.put("message", "账号不在有效期内，请联系管理员");
								return returnMap;
							}else if(accounts.getYxqks()==null&&formatter.parse(formatter.format(commonService.getSysdate())).getTime()>formatter.parse(formatter.format(accounts.getYxqjs())).getTime()){
								//当前时间大于结束日期 开始日期为空
								returnMap.put("islegal", "flase");
								returnMap.put("username", username);
								returnMap.put("password", password);
								returnMap.put("message", "账号不在有效期内，请联系管理员");
								return returnMap;
							}else if(formatter.parse(formatter.format(commonService.getSysdate())).getTime()<formatter.parse(formatter.format(accounts.getYxqks())).getTime()
									||formatter.parse(formatter.format(commonService.getSysdate())).getTime()>formatter.parse(formatter.format(accounts.getYxqjs())).getTime()){
								//当前时间小于开始日期大于结束日期
								returnMap.put("islegal", "flase");
								returnMap.put("username", username);
								returnMap.put("password", password);
								returnMap.put("message", "账号不在有效期内，请联系管理员");
								return returnMap;
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
				}
					
			}
		}
		
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			initAdminMap(returnMap, user);//初始化超级管理员数据
		}else{
			initUserMap(returnMap, user);//初始化普通管理员数据
		}
		returnMap.put("id", user.getId());
		returnMap.put("loginType", "sysLoginPage");
		return returnMap;
	}
	
	public Map<String, Object> ssoLogin(String username, String password)throws RuntimeException {

		Map<String, Object> result = new HashMap<String, Object>();
		User param = new User();
		param.setUsername(username);
		
		//找不到用户就登陆失败，否则登陆成功，加载权限
		User user = usermapper.getByUserNameAndPassword(username, password);
		if (null == user) {
			result.put("islegal", "false");
			result.put("message", "您无此系统权限!");
		}else {
			if(DelStatus.LOSE_EFFECT.getId().intValue() == user.getState().intValue()){
				result.put("islegal", "flase");
				result.put("username", username);
				result.put("message", "您的账号已被停用!");
			}else{
				result.put("islegal", "true");
				//根据用户账号是否是admin加载不同的权限
				if ("admin".equals(username)) {
					initAdminMap(result, user);
				} else {
					initUserMap(result, user);
				}
			}
		}
		result.put("loginType", "sso");
		return result;
	}
	
	/**
	 * @author liub
	 * @description 如果是admin用户登录,执行此方法
	 * @develop date 2016.08.11
	 */
	private void initAdminMap(Map<String, Object> returnMap , User user){
		
		List<Menu> menuList = menuService.findAllMenuList4Login();//获取菜单数据
		List<Button> buttonList = buttonService.findAllButtonList4Login();//获取按钮集合
	
		List<Menu> userMenuTreeList = buildUserMenuTree("0", menuList);
		List<String> menuCodeList = getMenuCodeList(menuList);//获取菜单编码集合
		List<String> buttonCodeList = getButtonCodeList(buttonList);//获取按钮编码集合
		List<Department> department = departmentService.queryOrg();//获取组织结构集合
		List<Store> storeList = this.storeMapper.selectAll();
		BaseEntity entity = new BaseEntity();
		entity.getParamsMap().put("usertype", "admin");
		List<Map<String,Object>> aircraftDatas = planeModelDataMapper.selectAircraftData(entity );
		List<Map<String,Object>> aircraftDatas135145 = planeModelDataMapper.selectAircraft135145Data(entity );
		
		DeptInfo deptInfo=deptInfoService.selectById(user.getJgdm());
		returnMap.put("userMenuList", userMenuTreeList);
		returnMap.put("menu", menuCodeList);
		returnMap.put("button", buttonCodeList);
		returnMap.put("department", department);
		returnMap.put("islegal", "true");
		user.setUserType("admin");
		returnMap.put("user", user);
		returnMap.put("userStoreList", storeList);
		//存储授权机型和飞机注册号
		returnMap.put("userACRegList", aircraftDatas);
		returnMap.put("userACReg135145List", aircraftDatas135145);
		//组织机构信息表
		returnMap.put("deptInfo", deptInfo);
	}
	
	/**
	 * @Description 将用户所具有菜单权限构造成树结构（自递归）
	 * @CreateTime 2017年8月17日 下午3:52:13
	 * @CreateBy 徐勇
	 * @param menuParentId 父节点ID，外部调用传入顶级节点ID（"0"）
	 * @param menuList 用户具有菜单权限集合
	 * @return 经过组装的具有上下结构的菜单
	 */
	private List<Menu> buildUserMenuTree(String menuParentId, List<Menu> menuList) {  
		List<Menu> returnList = new ArrayList<Menu>();
		for (Menu menu : menuList) {  
	        if (null != menu.getParentId() && menuParentId.equals(menu.getParentId())){ 
	        	if(menu.getMenuOrder() == null){
	        		menu.setMenuOrder(Integer.valueOf(StringUtils.isNotBlank(menu.getFullOrder())?menu.getFullOrder():"0"));
	        	}
	        	//递归调用，构造子节点菜单
	        	menu.setChildren(buildUserMenuTree(menu.getId(), menuList));
	        	returnList.add(menu);
	        }  
	    }
		Collections.sort(returnList, new Comparator<Menu>() {
			@Override
			public int compare(Menu o1, Menu o2) {
				
				return (o1.getMenuOrder()==null?new Integer(0):o1.getMenuOrder()).compareTo(o2.getMenuOrder());
			}
		});
		return returnList;
    }  

	 //**********************
	/**
	 * @author liub
	 * @description 如果是普通管理员登录,执行此方法
	 * @develop date 2016.08.11
	 * @UpdateBy 徐勇
	 * @UpdateTime 2017年8月17日 下午4:24:52
	 */
	private void initUserMap(Map<String, Object> returnMap , User user){

		List<Menu> menuList = menuService.findMenuByUserId4Login(user.getId());//获取菜单数据
		List<Button> buttonList = buttonService.findButtonByUserId4Login(user.getId());//获取按钮集合
		
		//构造菜单树结构
		List<Menu> userMenuTreeList = buildUserMenuTree("0", menuList);
		List<String> menuCodeList = getMenuCodeList(menuList);//获取菜单编码集合
		List<String> buttonCodeList = getButtonCodeList(buttonList);//获取按钮编码集合
		List<Department> department = departmentService.findDepartmentByUserId(user.getId());//获取组织结构集合
		List<Store> storeList = this.storeMapper.selectByUserId(user.getId());
		BaseEntity entity = new BaseEntity();
		entity.getParamsMap().put("userId", user.getId());
		List<Map<String,Object>>aircraftDatas=  planeModelDataMapper.selectAircraftData(entity );
		
		entity.getParamsMap().put("dprtcode", user.getJgdm());
		List<Map<String,Object>>aircraftDatas135145=  planeModelDataMapper.selectAircraft135145Data(entity );

		DeptInfo deptInfo=deptInfoService.selectById(user.getJgdm());
			
		returnMap.put("userMenuList", userMenuTreeList);
		returnMap.put("menu", menuCodeList);
		returnMap.put("button", buttonCodeList);
		returnMap.put("department", department);
		returnMap.put("islegal", "true");
		returnMap.put("user", user);
		returnMap.put("userStoreList", storeList);
		//存储授权机型和飞机注册号
		returnMap.put("userACRegList", aircraftDatas);
		returnMap.put("userACReg135145List", aircraftDatas135145);
		//组织机构信息表
		returnMap.put("deptInfo", deptInfo);
		
	}
	
	/**
	 * @author liub
	 * @description 提供菜单编码集合
	 * @param 
	 * @return List<String>
	 * @develop date 2016.08.10
	 */
	public List<String> getMenuCodeList(List<Menu> menus){  

		List<String> menuCodeList = new ArrayList<String>(menus.size());
		
		for(Menu menu : menus){
			menuCodeList.add(menu.getMenuCode());
		}
		return menuCodeList;
    } 
	
	/**
	 * @author liub
	 * @description 提供按钮编码集合
	 * @param 
	 * @return List<String>
	 * @develop date 2016.08.10
	 */
	public List<String> getButtonCodeList(List<Button> buttons){  

		List<String> buttonCodeList = new ArrayList<String>(buttons.size());
		
		for(Button button : buttons){
			buttonCodeList.add(button.getButtonCode());
		}
		return buttonCodeList;
    }  
	
	/**
	 * @author liub
	 * @description 提供菜单树数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.10
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMenuTree(List<Menu> menus){  
		
		List<Map<String, Object>> menuModuleList = new ArrayList<Map<String, Object>>();//菜单模块集合
		Map<String, Object> menuTreeMap = new HashMap<String, Object>();//菜单树map集合
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回值map集合
		
		Map<String, Object> menuMap = new HashMap<String, Object>();
		buildMenuTree("0",menuMap,menus);
		List<Map<String, Object>> menuTree = (List<Map<String, Object>>) menuMap.get("children");

		for(Map<String, Object> map : menuTree){
			Map<String, Object> moduleMap = new HashMap<String, Object>();
			moduleMap.put("id", map.get("id"));
			moduleMap.put("menuName", map.get("text"));
			moduleMap.put("fullOrder", map.get("fullOrder"));
			menuModuleList.add(moduleMap);
			menuTreeMap.put(String.valueOf(map.get("id")), map.get("children"));
		}
		
		Collections.sort(menuModuleList, new FullOrderCompare());//排序调用方法
		returnMap.put("menuModuleList", menuModuleList);
		returnMap.put("menuTreeMap", menuTreeMap);
		return returnMap;	

    }  
	
	/**
	 * @author liub
	 * @description 配置菜单数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.10
	 */
	private void buildMenuTree(String id,Map<String, Object> menuMap,List<Menu> menus){  
 
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();   
	    for (Menu menu : menus) {
	    	
	        if (null != menu.getParentId() && id.equals( menu.getParentId())) { 
	        	Map<String, Object> childrenMap = new HashMap<String, Object>();
	        	childrenMap.put("id", menu.getId());
	        	childrenMap.put("text", menu.getMenuName());
	        	childrenMap.put("menuOrder", menu.getMenuOrder());
	        	childrenMap.put("fullOrder", menu.getFullOrder());
	        	Map<String, Object> attrivuteMap = new HashMap<String, Object>();
	        	attrivuteMap.put("url", menu.getPath());
	        	attrivuteMap.put("menuCode", menu.getMenuCode());
	        	attrivuteMap.put("parentId", menu.getParentId());
	        	attrivuteMap.put("menuType", menu.getMenuType());
	        	childrenMap.put("attributes", attrivuteMap); 
	        	buildMenuTree(menu.getId(),childrenMap,menus);  
	        	menuList.add(childrenMap);  
	        }  
	    }  
	    Collections.sort(menuList, new MenuOrderCompare());//排序调用方法
	    menuMap.put("children", menuList);

    }  
	
	/**
	 * 查询全部未选择组织机构的用户的实现方法
	 */
	@Override
	public List<User> queryAllNotChooseDepartmentTheUser(String realName) {
		return usermapper.selectAllNotChooseDepartmentTheUser(realName);
	}
	
	/**
	 * 查询全部已选择组织机构的用户的实现方法
	 */
	@Override
	public List<User> queryAllAlreadyChooseDepartmentTheUser(String realName, String jgdm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("realName", realName);
		map.put("jgdm", jgdm);
		return usermapper.selectAllAlreadyChooseDepartmentTheUser(map);
	}
	
	/**
	 * 根据用户主键id修改用户的组织机构的实现方法
	 */
	@Override
	public int updateUserTheDepartmentById(String id, String bmdm, String jgdm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("bmdm", bmdm);
		map.put("jgdm", jgdm);
		return usermapper.updateUserTheDepartmentById(map);
	}
	
	/**
	 * 根据部门代码查询该组织机构下的用户的条数的实现方法
	 */
	@Override
	public int queryUserCountByBmdm(String bmdm) {
		return usermapper.selectUserCountByBmdm(bmdm);
	}/**
	 * 条件查询
	 */
	@Override
	public User selectByPrimaryKey(String userId) {
		return usermapper.selectByPrimaryKey(userId);
	}
	
	/**
	 * 根据原始的部门代码修改用户的组织机构的实现方法
	 */
	@Override
	public int updateUserTheDepartmentByOrgnBmdm(String bmdm, String jgdm, String orgnBmdm) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bmdm", bmdm);
		map.put("jgdm", jgdm);
		map.put("orgnBmdm", orgnBmdm);
		return usermapper.updateUserTheDepartmentByOrgnBmdm(map);
	}
	
	@Override
	public String updateUser(User user) throws BusinessException {
		
		User user1=new User();
		user1.setUsername(user.getUsername());
		user1.setJgdm(user.getJgdm());
		List<User> usersnew=usermapper.queryjgdm(user1);
		User oldUser = usermapper.selectByPrimaryKey(user.getId());
		String czls = UUID.randomUUID().toString();
		if(usersnew!=null&&!usersnew.isEmpty()){
			if(usersnew.size()!=1&&usersnew.size()!=0){
					throw new BusinessException("用户代码重复，请重新填写");
			}else{
				User tempUser = usersnew.get(0);
				if(!tempUser.getId().equals(oldUser.getId())){
					throw new BusinessException("用户代码重复，请重新填写");
				}
			}
		}
		
		if(StringUtils.isNotBlank(user.getDrzhid())){
			if(!user.getDrzhid().equals(oldUser.getDrzhid())){
				Login login=accountService.selectByPrimaryKey(user.getDrzhid());
				if(login != null){
					//如果是选择的账号
					//判断账号是否被绑定了
					List<User> list = this.usermapper.selectByDrzhid(login.getId());
					if(list != null && !list.isEmpty()){//如果账号已被占用，提示用户
						User tempUser = list.get(0);
						if(!tempUser.getId().equals(oldUser.getId())){
							throw new BusinessException("该账号已与用户["+tempUser.getRealname()+"]绑定，请先解除绑定关系");
						}
					}
					
					if(StringUtils.isBlank(login.getJgdm())){
						login.setJgdm(user.getJgdm());
						this.accountService.updateByPrimaryKey(login);
					}else{
						if(!user.getJgdm().equals(login.getJgdm())){
							throw new BusinessException("该用户没有此账号组织机构权限，请重新选择");
						}
					}
				}else{
					throw new BusinessException("该账号不存在，请重新选择");
				}
			}
			
		}else{
			if(!user.getAccountName().equals("")){
				Login login1=accountService.selectByPrimaryName(user.getAccountName());
				if(login1 != null){
					List<User> list = this.usermapper.selectByDrzhid(login1.getId());
					if(list != null && !list.isEmpty()){//如果账号已被占用，提示用户
						User tempUser = list.get(0);
						if(!tempUser.getId().equals(oldUser.getId())){
							throw new BusinessException("该账号已与用户["+tempUser.getRealname()+"]绑定，请先解除绑定关系");
						}
					}
					
					if(StringUtils.isBlank(login1.getJgdm())){
						//创建账号
						login1.setJgdm(user.getJgdm());
						this.accountService.updateByPrimaryKey(login1);
					}else{
						if(!user.getJgdm().equals(login1.getJgdm())){
							throw new BusinessException("该用户没有此账号组织机构权限，请重新选择");
						}
					}
					
					user.setDrzhid(login1.getId());
				}else{
					Login login = new Login();
					login.setUsername(user.getAccountName());
					login.setJgdm(user.getJgdm());
					this.accountService.saveAccount(login);
					user.setDrzhid(login.getId());
				}
			}else{
				user.setDrzhid("");
			}

		}
		
		//对角色的变化进行操作
		List<String> listOld=user.getOldRolesId();//旧数据
		List<String> listNew=user.getRolesId();//新数据
		
		//删除
		for(String newRoleId : listOld){
			 
			if(!listNew.contains(newRoleId)){
				UserToRole userToRole=new UserToRole();
				userToRole.setRoleId(newRoleId);
				userToRole.setUserId(user.getId());
				userToRoleService.delete(userToRole);
			}
		}
		
		//新增
		for(String oldRoleId : listNew){
			if(!listOld.contains(oldRoleId)){
				String urid=UUID.randomUUID().toString();
				UserToRole userToRole=new UserToRole();
				userToRole.setRoleId(oldRoleId);
				userToRole.setUserId(user.getId());
				userToRole.setUrId(urid);
				userToRoleService.insert(userToRole);
			}
		}
		
		//对机型角色的变化进行操作
		List<String> listModelOld=user.getOldModelRolesId();//旧数据
		List<String> listModelNew=user.getModelRolesId();//新数据
		//删除
		for(String newModelRoleId : listModelOld){
			 
			if(!listModelNew.contains(newModelRoleId)){
				UserToModelRole userToModelRole=new UserToModelRole();
				userToModelRole.setRoleId(newModelRoleId);
				userToModelRole.setUserId(user.getId());
				userToModelRoleService.delete(userToModelRole);
			}
		}
		//新增
		for(String oldModelRoleId : listModelNew){
			if(!listModelOld.contains(oldModelRoleId)){
				String urid=UUID.randomUUID().toString();
				UserToModelRole userToModelRole=new UserToModelRole();
				userToModelRole.setRoleId(oldModelRoleId);
				userToModelRole.setUserId(user.getId());
				userToModelRole.setUrId(urid);
				userToModelRoleService.insert(userToModelRole);
			}
		}
				
		//对库房角色的变化进行操作
		List<String> listWarehouseOld=user.getOldWarehouseRolesId();//旧数据
		List<String> listWarehouseNew=user.getWarehouseRolesId();//新数据
		
		//删除
		for(String newWarehouseRoleId : listWarehouseOld){
			 
			if(!listWarehouseNew.contains(newWarehouseRoleId)){
				UserToWarehouseRole userToWarehouseRole=new UserToWarehouseRole();
				userToWarehouseRole.setRoleId(newWarehouseRoleId);
				userToWarehouseRole.setUserId(user.getId());
				userToWarehouseRoleService.delete(userToWarehouseRole);
			}
		}
		
		//新增
		for(String oldWarehouseRoleId : listWarehouseNew){
			if(!listWarehouseOld.contains(oldWarehouseRoleId)){
				String urid=UUID.randomUUID().toString();
				UserToWarehouseRole userToWarehouseRole=new UserToWarehouseRole();
				userToWarehouseRole.setRoleId(oldWarehouseRoleId);
				userToWarehouseRole.setUserId(user.getId());
				userToWarehouseRole.setUrId(urid);
				userToWarehouseRoleService.insert(userToWarehouseRole);
			}
		}
		
	/*	//1.根据departmentId查询组织代码
		Department department=departmentService.findById(user.getDepartmentId());
		
		//3.把map的信息赋给user对象
		user.setBmdm(department.getId());*/
		//4.添加用户并返回用户id
		
		//根据当前选择的部门id查询从当前部门往上的所有父数据（包含自己）
		if(user.getBmdm()!=null&&!user.getBmdm().equals("")){
			List<Department> departmentList=departmentService.selectDepartmentPid(user.getBmdm());
			for (Department department : departmentList) {
				if(department.getJdbs()==1){
					user.setJddm(department.getId());//用户选择部门后往部门上级递归查找基地标识为是的 部门ID存入该字段
					break;
				}
			}
		}
		if(user.getAttId().equals("2")){
			//删除原有的电子签名
			attachmentService.deleteByMainId(user.getId(), czls, user.getId(), LogOperationEnum.DELETE);
		}
		//8.对电子签名进行添加
		if(user.getAttachment() != null){
			//删除原有的电子签名
			attachmentService.deleteByMainId(user.getId(), czls, user.getId(), LogOperationEnum.DELETE);
			Attachment attachment = user.getAttachment();
			attachment.setId(UUID.randomUUID().toString());
			attachment.setMainid(user.getId());
			attachmentService.addAttachment(attachment, user.getId(), czls, user.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
		}
		usermapper.updateByPrimaryKeySelective(user);
		return user.getId();
	}
	/**
	 * @author sunji
	 * @description 查询所有用户的id和name
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> queryUserAll(User user) {
		return usermapper.queryUserAll(user);
	}
	public List<User> queryUserAllByDprtcode(User user) {
		return usermapper.queryUserAllByDprtcode(user);
	}
	@Override
	public List<User> selectUserToSendYes(String id,String dprtcode) {
		return usermapper.selectUserToSendYes(id,dprtcode);
	}
	@Override
	public List<User> selectUserToSendNo(String id,String dprtcode) {
		return usermapper.selectUserToSendNo(id,dprtcode);
	}
	/**
	 * 根据条件查询条数
	 * @param user
	 * @return int
	 */
	public int queryCountByName(User user) {
		return usermapper.queryCountByName(user);
	}
	@Override
	public List<User> queryUserAndAttendantor(User user) {
		return usermapper.queryUserAndAttendantor(user);
	}
	
	/**
	 * 解绑用户账号
	 * @param id
	 */
	public void updateUserUnboundAccount(String id){
		this.usermapper.updateUserUnboundAccount(id);
	}
	@Override
	public List<User> queryAllByjx(User user) {
		return usermapper.queryAllByjx(user);
	}
	/**
	 * @author sunji
	 * @description 根据组织机构查询所有用户的id和name
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> getByDprtcode(String dprtcode) {
		return usermapper.getByDprtcode(dprtcode);
	}
	
	/**
	 * @author sunji
	 * @description 根据机构代码查询人员树(按部门)
	 * @param dprtcode
	 * @return List<Map<String, Object>>
	 */
	public Map<String, Object> queryDrptTreeByDprtcode(String dprtcode, String bmdm, String str) {
		Map<String, Object> map = new HashMap<String, Object>();//返回值
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dprtcode", dprtcode);
		param.put("bmdm", bmdm);
		List<Department> departments = departmentMapper.getChildrentList(param);
		Map<String, Object> stateMap = new HashMap<String, Object>();//树形菜单展开或收缩
		stateMap.put("opened", true);
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		Map<String, Department> drptMap = new HashMap<String, Department>();//部门map
		for (Department department : departments) {
			String parentId = department.getParentid();
			if(department.getId().equals(dprtcode)){
				parentId = "#";
			}
			addDrptTree(treeList, department.getId(), department.getDprtname(), stateMap, this.parentIcon, parentId, getParamMap(null, 0));
			drptMap.put(department.getId(), department);
		}
		List<User> userList = usermapper.getByDprtcodetree(dprtcode, bmdm, str);
		for (User user : userList) {
			StringBuffer s = new StringBuffer();
			String parentId = dprtcode;
			if(user != null && drptMap.containsKey(user.getBmdm())){
				parentId = user.getBmdm();
			}
			addDrptTree(treeList, user.getId(), s.append(user.getDisplayName()), stateMap, this.childrenIcon, parentId, getParamMap(user, 1));
		}
		map.put("treeList", treeList);
		map.put("userList", userList);
		return map;
	}
	
/**
 * @author liub
 * @description 新增部门树
 * @param resultList,typeMap,type
 * @develop date 2017.03.17
 */
private void addDrptTree(List<Map<String, Object>> resultList,Object id,Object text,Object stateMap,String icon , Object parentId,Map<String, Object> paramMap){
	Map<String, Object> resultMap = new HashMap<String, Object>();
	resultMap.put("id", id);
	resultMap.put("text", text);
	resultMap.put("state", stateMap);
	resultMap.put("icon", icon);
	resultMap.put("parent", parentId);
	resultMap.put("li_attr", paramMap);
	resultList.add(resultMap);
}

/**
 * @author liub
 * @description 获取属性参数
 * @param resultList,type
 */
private Map<String, Object> getParamMap(User user,int type){
	Map<String, Object> attrMap = new HashMap<String, Object>();
	attrMap.put("type", type);
	if(null != user){
		StringBuffer s = new StringBuffer();
		attrMap.put("id", user.getId());
		attrMap.put("bmdm", user.getBmdm());
		attrMap.put("dprtname", user.getParamsMap().get("dprtname"));
		attrMap.put("dprtbh", user.getParamsMap().get("dprtcode"));
		attrMap.put("username", user.getUsername());
		attrMap.put("realname", user.getRealname());
		attrMap.put("sex", user.getSex());
		attrMap.put("displayName", s.append(user.getDisplayName()));
	}
	return attrMap;
}

/**
 * @author liub
 * @description  同一父节点下菜单排序
 * @develop date 2016.08.11
 */
class MenuOrderCompare implements Comparator<Map<String, Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		if(null == o1.get("menuOrder") || null == o2.get("menuOrder")){
			return 0;
		}
		int v1 = (Integer) o1.get("menuOrder");
		int v2 = (Integer) o2.get("menuOrder");
		return v1 - v2;
	}
}
/**
 * @author liub
 * @description  整个菜单排序
 * @develop date 2016.08.11
 */
class FullOrderCompare implements Comparator<Map<String, Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		if(null == o1.get("fullOrder") || null == o2.get("fullOrder")){
			return 0;
		}
		int v1 = Integer.parseInt((String) o1.get("fullOrder"));
		int v2 = Integer.parseInt((String) o2.get("fullOrder"));
		return v1 - v2;
	}
}

}