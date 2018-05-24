package com.eray.thjw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.eray.thjw.exception.TimeOutException;
import com.eray.thjw.po.User;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThreadVar;

/**
 * @author liub
 * @description 登录拦截器
 * @develop date 2016.08.09
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	public void afterCompletion(HttpServletRequest request, HttpServletResponse arg1, Object handler, Exception arg3)
			throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView view)
			throws Exception {

	}

	/**
	 * @author liub
	 * @description 检查用户是否登录,未登录跳转到登录页面
	 * @param request,response,handler
	 * @return boolean
	 * @develop date 2016.08.09
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		User user = SessionUtil.getUserFromSession(request);
		String url = request.getRequestURI();
		/*//登陆页面的帮助按钮查看帮助手册PDF在未登陆状态下放行
		if (!url.contains("UserHelperPdf")) {
			if (null == user) {
				throw new TimeOutException("timeout");
			}
			ThreadVarUtil.setUser(user);
			ThreadVarUtil.set(ThreadVar.CLIENT_IP.name(), user.getLastip());
		}*/
		//登陆页面的帮助按钮查看帮助手册PDF在未登陆状态下放行
		if (null == user) {
			throw new TimeOutException("timeout");
		}
		ThreadVarUtil.setUser(user);
		ThreadVarUtil.set(ThreadVar.CLIENT_IP.name(), user.getLastip());
		return super.preHandle(request, response, handler);
	}

}
