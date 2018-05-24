package com.eray.thjw.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

import enu.LoginTypeEnum;
import enu.PrivilegeTypeEnum;

/**
 * @author liub
 * @description session工具类
 * @develop date 2016.08.09
 */
public class SessionUtil {
	
	private static final Logger log = LoggerFactory.getLogger(SessionUtil.class);
	private static HttpSession session;
	public static Map<String, HttpSession> sessionUsers = new HashMap<String, HttpSession>(); //每一个登录用户的Seesion,退出时删除 
	
	/**
	 * @author liub
	 * @description 登录成功后将数据放入session
	 * @param request,需要放入session的数据map
	 * @develop date 2016.08.09
	 */
	public static void initSession(HttpServletRequest request , Map<String, Object> map){
		
		log.debug("参数:{map}", new Object[]{map});
		putToSession(request, "user", map.get("user"));
		putToSession(request, PrivilegeTypeEnum.BUTTON.toString().toLowerCase(), map.get("button"));
		putToSession(request, PrivilegeTypeEnum.MENU.toString().toLowerCase(), map.get("menu"));
		putToSession(request, "accessDepartment", map.get("department"));
		putToSession(request, "loginType", map.get("loginType"));
		putToSession(request, "userStoreList", map.get("userStoreList"));
		putToSession(request, "userMenuList", map.get("userMenuList"));
		//将授权机型，飞机注册号存储到session
		putToSession(request, "userACRegList", map.get("userACRegList"));
		//135 145
		putToSession(request, "userACReg135145List", map.get("userACReg135145List"));
		//组织机构信息表
		putToSession(request, "deptInfo", map.get("deptInfo"));
		
		
		/**Start: 登录退出*/
		String clientIP = new BaseController().getIpAddr(request);
		putToSession(request, "osmsRemoteIP", SysContext.OSMS_URL); //OSMS服务器配置IP
		putToSession(request, "clientIP", clientIP); //客户端IP地址
		putToSession(request, "customer", map.get("customer")); //客户
		putToSession(request, "forTest", map.get("forTest")); //客户
		User user = (User) map.get("user");
		sessionUsers.put(clientIP+(user != null ? user.getUsername() : ""), request.getSession()); //登录用户的请求,退出时删除
		/**End: 登录退出*/
	}

	
	/**
	 * @author liub
	 * @description 将数据放入session
	 * @param request,对象名objStr,对象值obj
	 * @develop date 2016.08.09
	 */
	public static void putToSession(HttpServletRequest request ,String objStr , Object obj){
		
		log.debug("参数名称:{} 参数值:{}", new Object[]{objStr,obj});

		request.getSession().setAttribute(objStr, obj);
	}
	
	/**
	 * @author liub
	 * @description 从session取出菜单id集合
	 * @param request,菜单id
	 * @return Object
	 * @develop date 2016.08.11
	 */
	@SuppressWarnings("unchecked")
	public static List<Integer> getMenuIdsFromSession(HttpServletRequest request){
		
		List<Integer> menuIds = new ArrayList<Integer>();
		
		Object obj = getFromSession(request, "menuModuleList");
		if(null != obj){
			List<Map<String, Object>> menuModuleList = (List<Map<String, Object>>) obj;
			for(Map<String, Object> map : menuModuleList){
				menuIds.add((Integer) map.get("id"));
			}
		}

		return menuIds;
	}
	
	/**
	 * @author liub
	 * @description 从session取出菜单树数据
	 * @param request,菜单id
	 * @return Object
	 * @develop date 2016.08.11
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getMenuTreeFromSession(HttpServletRequest request ,String id){
		
		List<Map<String, Object>> menuTree = new ArrayList<Map<String, Object>>();
		
		Object obj = getFromSession(request, "menuTreeMap");
		if(null != obj){
			Map<String, Object> menuMap = (Map<String, Object>)obj;
			if(null != menuMap){
				menuTree = (List<Map<String, Object>>) menuMap.get(id);
			}
		}

		return menuTree;
	}
	
	/**
	 * @author liub
	 * @description 根据菜单编号或按钮编号从session取出数据
	 * @param request,类型type
	 * @return Object
	 * @develop date 2016.08.11
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getCodeFromSession(HttpServletRequest request ,String type){
		
		List<String> codeList = new ArrayList<String>();
		
		Object obj = SessionUtil.getFromSession(request, type);
		if(obj != null){
			codeList = (List<String>)obj;     
		}

		return codeList;
	}
	
	/**
	 * @author liub
	 * @description 从session取出组织机构代码
	 * @param request
	 * @return List<String>
	 * @develop date 2016.08.23
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getDprtcodeList(HttpServletRequest request){
		
		List<String> deptIds = new ArrayList<String>();
		Object obj = getFromSession(request, "accessDepartment");
		
		if(obj != null){
			List<Department> departmentList = (List<Department>) obj;
			for(Department department : departmentList){
				deptIds.add(department.getId());
			}
		}
		
		return deptIds;
	}
	
	/**
	 * @author liub
	 * @description 从session取出user数据
	 * @param request
	 * @return User
	 * @develop date 2016.08.16
	 */
	public static User getUserFromSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		return (User) session.getAttribute("user");
	}
	
	public static Department getJg(HttpServletRequest request){
		User user = getUserFromSession(request);
		return (user != null) ? user.getDprt_jg() : null;
	}
	
	/**
	 * @author liub
	 * @description 从session取出数据
	 * @param request,参数名objStr
	 * @return Object
	 * @develop date 2016.08.09
	 */
	public static Object getFromSession(HttpServletRequest request ,String objStr){
		HttpSession session = request.getSession();
		log.debug("参数名称:{}", new Object[]{objStr});

		return session.getAttribute(objStr);
	}
	
	/**
	 * @author liub
	 * @description 判断是否具有权限
	 * @param request,权限编码code,权限类型type
	 * @return Object
	 * @develop date 2016.08.09
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasButtonPower(HttpServletRequest request ,String code ,PrivilegeTypeEnum type){
		
		log.debug("权限类型:{} 权限编码:{}", new Object[]{type,code});
		
		List<String> codeList = (List<String>) getFromSession(request,type.toString().toLowerCase());
		return codeList.contains(code);
	}
	
	@SuppressWarnings({ "unchecked"})
	public static boolean isMenu(HttpServletRequest request ,String code){
		session = request.getSession();
		List<String> menus = (List<String>) session.getAttribute(PrivilegeTypeEnum.MENU.toString().toLowerCase());
		for (String menuCode : menus) {
			if(StringUtils.isNotBlank(menuCode) && menuCode.equalsIgnoreCase(code)){
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked"})
	public static boolean hasPermission(HttpServletRequest request ,String code){
		session = request.getSession();
		List<String> buttons = (List<String>) session.getAttribute(PrivilegeTypeEnum.BUTTON.toString().toLowerCase());
		List<String> menus = (List<String>) session.getAttribute(PrivilegeTypeEnum.MENU.toString().toLowerCase());
		return StringUtils.isNotBlank(code)?buttons.contains(code.toLowerCase()) || menus.contains(code.toLowerCase()):false;
	}

	public static boolean isSSOLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String loginType = session.getAttribute("loginType")==null?"":(String)session.getAttribute("loginType");
		return LoginTypeEnum.SSO.getName().equalsIgnoreCase(loginType);
	}
	
	public static void setImageCode(HttpServletRequest request,String imageCode) {
		setAttr(request, "imageCode", imageCode);
	}
	
	public static String getImageCode(HttpServletRequest request) {
		return getAttr(request, "imageCode",String.class);
	}
	
	public static void setAttr(HttpServletRequest request,String attr,Object val){
		HttpSession session = request.getSession();
		session.setAttribute(attr,val);
	}
	
	@SuppressWarnings("unchecked")
	public static <T>T getAttr(HttpServletRequest request,String attr,Class<T>T){
		HttpSession session = request.getSession();
		return (T)session.getAttribute(attr);
	}

}