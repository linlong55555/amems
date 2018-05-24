package com.eray.thjw.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.NoAuthException;
import com.eray.thjw.po.Menu;
import com.eray.thjw.util.SessionUtil;

/**
 * @author liub
 * @description 权限拦截器
 * @develop date 2016.08.09
 */
public class PrivilegeInterceptor extends HandlerInterceptorAdapter {
	 public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {  
         
	 }  
	  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView view) throws Exception {  
        String contextPath = request.getContextPath(); 
        if (view != null) {  
            request.setAttribute("ctx", contextPath);  
        }  
    }  
    
	/**
	 * @author liub
	 * @description 验证是否具有权限
	 * @param request,response,handler
	 * @return boolean
	 * @develop date 2016.08.09
	 */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
          
        //处理Privilege Annotation，实现方法级权限控制  
        HandlerMethod method = (HandlerMethod)handler;  
        Privilege privilege = method.getMethodAnnotation(Privilege.class);  
        
        //如果为空在表示该方法不需要进行权限验证  
        if (privilege == null) {  
            return true;  
        }
         
        //支持非菜单入口 的方法多权限编码配置，多权限以,分隔
        String[] codes = privilege.code().split(",");
        
        //TODO 权限跳转页面待处理
        if (!SessionUtil.getUserFromSession(request).getUsername().equalsIgnoreCase("admin")) {
        	
        	//支持非菜单入口 的方法多权限编码配置，多权限以,分隔
        	boolean flag = false;
        	for (String code : codes) {
        		if (SessionUtil.hasPermission(request, code)) {
        			flag = true;
        		} 
			}
        	if(!flag){
        		throw new NoAuthException("noauth");
        	}
		}
        //注意，菜单入口的方法只应配置一个权限代码，以免前端当前功能菜单选中状态异常
        for (String code : codes) {
        	if(SessionUtil.isMenu(request, code)){
        		SessionUtil.putToSession(request, "menuCodeHigh", buildMenuPath(request, privilege.code()));
        		break;
        	}
        }
        return true;   
    }  
    
    /**
     * 构造当前菜单的ID全路径
     * @param request
     * @param menuCode
     * @return
     */
    private String buildMenuPath(HttpServletRequest request, String menuCode){
    	List<Menu> menuList = (List<Menu>) SessionUtil.getFromSession(request, "userMenuList");
    	return buildMenuPath(menuList, menuCode);
    }
    
    /**
     * @Description 构建当前访问菜单的路径
     * @CreateTime 2017年8月18日 上午11:35:26
     * @CreateBy 徐勇
     * @param menuList 树状结构的用户菜单权限
     * @param menuCode 当前访问菜单的code，对应的是controller中priv注解中定义的code
     * @return 菜单路径 以','分隔
     */
    private String buildMenuPath(List<Menu> menuList, String menuCode){
    	for (Menu menu : menuList) {
    		if(menuCode.equalsIgnoreCase(menu.getMenuCode())){
    			return menu.getId();
    		}
    		String menuId = buildMenuPath(menu.getChildren(), menuCode);
    		if(StringUtils.isNotBlank(menuId)){
    			return new StringBuilder(menu.getId()).append(",").append(menuId).toString();
    		}
		}
    	return "";
    }
}
