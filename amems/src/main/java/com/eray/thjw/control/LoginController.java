package com.eray.thjw.control;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eray.common.util.TokenUtil;
import com.eray.common.util.UserUtil;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.NoAuthException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.AuthService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.system.po.SysLog;
import com.eray.thjw.system.service.SystemLogService;
import com.eray.thjw.util.CreateImageCodeUtil;
import com.eray.thjw.util.RSAUtils;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

/**
 * @author liub
 * @description 用户控制层
 * @develop date 2016.08.05
 */
@Controller
public class LoginController extends BaseController {

	/**
	 * @author liub
	 * @description 用户service
	 * @develop date 2016.08.15
	 */
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authUserService;
	
	@Resource
	private SystemLogService systemLogService;
	
	
	@RequestMapping(value = "/homepage/generateCkCode")
	public ModelAndView generateCkCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CreateImageCodeUtil createImageCode = new CreateImageCodeUtil(114,38);
			SessionUtil.setImageCode(request,createImageCode.getCode());
			createImageCode.write(response.getOutputStream());
			
		} catch (Exception e) {
			throw new BusinessException("登录失败", e);
		}
		return null;
	}
	
	/**
	 * @Description 获取公钥以及将私钥存入session
	 * @CreateTime 2017年8月16日 上午11:24:49
	 * @CreateBy 徐勇
	 */
	@ResponseBody
	@RequestMapping(value = "/homepage/publickey")
	public Map<String, String> getPublickey(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>(2);
		//获取公钥返回给前台
		RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
		map.put("modulus", new String(Hex.encodeHex(publicKey.getModulus().toByteArray())));
		map.put("exponent", new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));
		//将私钥放入session
		SessionUtil.setAttr(request, "PRIVATE_KEY", RSAUtils.getDefaultPrivateKey());
		return map;
	}
	
	/**
	 * @Description 跳转到登陆页
	 * @UpdateBy 徐勇
	 * @UpdateTime 2017年8月16日 上午11:26:33
	 * @return
	 */
	@RequestMapping(value="/login",method={RequestMethod.GET})
	public ModelAndView login(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		SessionUtil.setAttr(request, "GETIP_STYLE", SysContext.GETIP_STYLE);
		return new ModelAndView("web/public/login", model);
	}
	

	
	@RequestMapping(value="/main",method={RequestMethod.GET})
	public String main(Model model) {
		return "main";
	}
	
	@Privilege(code="index")
	@RequestMapping(value="/index",method={RequestMethod.GET})
	public ModelAndView index(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
	
	
		return new ModelAndView("index", model);
	}
	
	@ResponseBody
	@RequestMapping(value="/login",method={RequestMethod.POST})
	public Map<String, Object> login(HttpServletRequest request,
			@RequestParam(value = "username",required = true) String username,
			@RequestParam(value = "password",required = true) String password1,
			@RequestParam(value = "imageCode",required = true) String imageCode,
			@RequestParam(value = "mac",required = false) String mac,
			@RequestParam(value = "lastip",required = false) String lastip,
			@RequestParam(value = "customer",required = false) String customer,
			@RequestParam(value = "forTest",required = false) String forTest,
			RedirectAttributes attr,HttpServletResponse response) throws NoAuthException {
		
		//解密
		String password=RSAUtils.decryptStringByJs(password1, SessionUtil.getAttr(request, "PRIVATE_KEY", RSAPrivateKey.class));
		
		String imageCodeInCache = SessionUtil.getImageCode(request);
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return getErrorMsg("账号，密码不能为空");
		}
		//生产模式
		if (SysContext.PRODUCT_MODEL) {
			if (StringUtils.isEmpty(imageCodeInCache)) {
				return getErrorMsg("验证码已过期");
			}
			else if (!imageCodeInCache.equalsIgnoreCase(imageCode)) {
				return getErrorMsg("验证码不正确");
			}
		}
		 
			String passwordNew = UserUtil.MD5(password);
			String ip = (enabledClientIp() && Utils.IP.isLegitimate(lastip))?lastip:getIpAddr(request);
			getLogger().debug("当前登陆人IP===="+ip);
			
			SysLog sysLog = new SysLog();
			sysLog.setCzip(ip);
			sysLog.setCzsj(username);
			sysLog.setCzmc("LOGIN");
			
			Map<String, Object> resultMap = userService.login(username,passwordNew, request, mac);
			if ("true".equals(resultMap.get("islegal"))) {	
				sysLog.setRzlx(1);
				if(StringUtils.isBlank(customer)){
					customer = "common";
				}
				resultMap.put("customer", customer);
				resultMap.put("forTest", forTest==null?"":forTest);
				SessionUtil.initSession(request, resultMap);
				if(resultMap.get("id")!=null &&!"".equals(resultMap.get("id"))){
					
					User user=new User();
					user.setId((String)resultMap.get("id"));
					user.setLastip(ip);
					user.setLastvisit(new Date());
					userService.updateByPrimaryKeySelective(user);
					User users=	userService.selectByPrimaryKey((String)resultMap.get("id"));
					SessionUtil.getUserFromSession(request).setLastip(ip);
					sysLog.setCzrid(user.getId());
					sysLog.setCzsj(users.getUsername()+" "+users.getRealname());
					sysLog.setDprtcode(users.getJgdm());
				}
				
				Cookie cookie = new Cookie("uname", username);  
	            cookie.setMaxAge(30 * 60);// 设置为30min  
	            cookie.setPath("/");  
	            response.addCookie(cookie); 
	            
	            try{
					this.systemLogService.saveLog(sysLog);    
				}catch(Exception e){
					getLogger().error("记录登陆日志失败:"+username);
				} 
			}else{
				sysLog.setRzlx(2);
				
				if(resultMap.containsKey("message")){
					try{
						sysLog.setYcnr(resultMap.get("message").toString());
						this.systemLogService.saveLog(sysLog);    
					}catch(Exception e){
						getLogger().error("记录登陆日志失败:"+username);
					}
					return getErrorMsg((String)resultMap.get("message")); 
				}else{
					return getErrorMsg("用户名或密码不正确"); 
				}
			}
		
		return getSuccessMsg("登陆成功");
		 
	}

	/**
	 * 演示登录入口
	 * @param request
	 * @param username
	 * @param password
	 * @param attr
	 * @param response
	 * @return
	 * @throws NoAuthException
	 * @throws IOException 
	 */
	@ResponseBody  
	@RequestMapping(value="/login4demo" )
	public JSONPObject login4demo(HttpServletRequest request,String username,
			String password,String callbackparam) throws BusinessException {
		
		Map<String, Object> result = null;
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			 result = getErrorMsg("账号，密码不能为空");
		}
		else {
			String passwordNew = UserUtil.MD5(password);
			String ip = getIpAddr(request);
			SysLog sysLog = new SysLog();
			sysLog.setCzip(ip);
			sysLog.setCzmc("LOGIN");
			
			Map<String, Object> resultMap = userService.login4demo(username,passwordNew, request);
			if ("true".equals(resultMap.get("islegal"))) {	
				sysLog.setRzlx(1);
				resultMap.put("customer", "common");
				resultMap.put("forTest", "");

				SessionUtil.initSession(request, resultMap);
				if(resultMap.get("id")!=null &&!"".equals(resultMap.get("id"))){
					
					User user=new User();
					user.setId((String)resultMap.get("id"));
					user.setLastip(ip);
					user.setLastvisit(new Date());
					userService.updateByPrimaryKeySelective(user);
					User users=	userService.selectByPrimaryKey((String)resultMap.get("id"));
					SessionUtil.getUserFromSession(request).setLastip(ip);
					sysLog.setCzrid(user.getId());
					sysLog.setCzsj(users.getUsername()+" "+users.getRealname());
					sysLog.setDprtcode(users.getJgdm());
				}
				
	            try{
					this.systemLogService.saveLog(sysLog);    
				}catch(Exception e){
					getLogger().error("记录登陆日志失败:"+username);
				} 
	            result =  getSuccessMsg("登陆成功");
			}else{
				sysLog.setRzlx(2);
				if(resultMap.containsKey("message")){
					try{
						this.systemLogService.saveLog(sysLog);    
					}catch(Exception e){
						getLogger().error("记录登陆日志失败:"+username);
					}
					sysLog.setYcnr((String)resultMap.get("message"));
					result = getErrorMsg((String)resultMap.get("message")); 
					  
				}else{
					sysLog.setYcnr("账号或密码不正确");
					result =  getErrorMsg("账号或密码不正确"); 
				}
			}
		}
		
		return new JSONPObject(callbackparam, result);
		 
	}
	
	
	
	
	/**
	 * 开启客户端提取IP
	 * @return
	 */
	public boolean enabledClientIp() {
		return SysContext.GETIP_STYLE!=null 
				&& SysContext.GETIP_STYLE.equals("CLIENT");
	}
	
	/**
	 * 单点登录
	 * 
	 * @param request
	 * @param token
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value="/ssoLogin",method={RequestMethod.POST,RequestMethod.GET})
	public String ssoLogin(HttpServletRequest request,String token,Model model) {
		
		String loginFail = GlobalConstants.getString(GlobalConstants.LOGIN_ERROR_KEY);
		try {
			//将token解码
			getLogger().debug("encode token====="+token);
			String str= TokenUtil.getJWTMessage(token);
			getLogger().debug("decode token====="+str);
			String username = str.split("_")[0];
			String password = str.split("_")[1];
//			String username = "TEST";
//			String password = "qcQCD6D8icMzr+j6kSKNKA==";
			
			String ip = getIpAddr(request);
			SysLog sysLog = new SysLog();
			sysLog.setCzip(ip);
			sysLog.setCzmc("LOGIN");
			//sysLog.setCzsj(username);
			
			//验证指定顶层机构下，用户账号，密码是否存在
			AuthUser authUser = authUserService.exists(username, password);
			if (authUser!=null) {
				authUserService.synAuthInfoByUser(authUser);
				Map<String, Object> result = userService.ssoLogin(username, password);
				//存在就加载权限，否则登陆失败
				if ("true".equals(result.get("islegal"))) {	
					result.put("loginType","sso");
					SessionUtil.initSession(request, result);
					
//					User user=ThreadVarUtil.getUser();
					User user=SessionUtil.getUserFromSession(request);
					sysLog.setRzlx(1);
					sysLog.setDprtcode(user.getJgdm());
					sysLog.setCzrid(user.getId());
					sysLog.setCzsj(user.getUsername()+" "+user.getRealname());
					try{
						this.systemLogService.saveLog(sysLog);    
					}catch(Exception e){
						getLogger().error("记录单点登陆日志失败:"+username);
					}
					
					return "redirect:/main";
				}else{
					sysLog.setRzlx(2);
					sysLog.setYcnr("系统账号不存在");
					
					try{
						this.systemLogService.saveLog(sysLog);    
					}catch(Exception e){
						getLogger().error("记录单点登陆日志失败:"+username);
					}
					
					model.addAttribute("sso_login_error_url", loginFail);
					model.addAllAttributes(result);
					getLogger().error("系统账号不存在");
					return "/sso_login_error";
				}
				
				
			}else {
				sysLog.setRzlx(2);
				sysLog.setYcnr("统一登陆平台用户不存在");
				
				try{
					this.systemLogService.saveLog(sysLog);    
				}catch(Exception e){
					getLogger().error("记录单点登陆日志失败:"+username);
				}
				
				model.addAttribute("sso_login_error_url", loginFail);
				model.addAttribute("message", "统一登陆平台用户不存在");
				getLogger().error("统一登陆平台用户不存在");
				return "/sso_login_error";
			}
			
		} catch (Exception e) {
			model.addAttribute("sso_login_error_url", loginFail);
			model.addAttribute("message", "登陆系统出现异常");
			getLogger().error("单点登陆异常", e);
			return "/sso_login_error";
		}
	
		
	}*/
	
	/**
	 * @author liub
	 * @description 退出登录
	 * @param request
	 * @return 重定向到指定页面
	 * @develop date 2016.08.10
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		User user = null;
		try{
			user = ThreadVarUtil.getUser();
			String ip = user.getLastip();
			SysLog sysLog = new SysLog();
			sysLog.setDprtcode(user.getJgdm());
			sysLog.setCzrid(user.getId());
			sysLog.setCzip(ip);
			sysLog.setCzmc("LOGOUT");
			sysLog.setCzsj(user.getUsername()+" "+user.getRealname());
			sysLog.setRzlx(1);
		    try{
				this.systemLogService.saveLog(sysLog);    
			}catch(Exception e){
				getLogger().error("记录登出日志失败:"+user.getUsername()+" "+user.getRealname());
			} 
		}catch(Exception e){
			
		}
		if (SessionUtil.isSSOLogin(request)) {
			request.getSession().invalidate();
			return "redirect:"+GlobalConstants.getString(GlobalConstants.LOGIN_OUT_KEY);
		}
		else{
			request.getSession().invalidate();
			return "redirect:/login";
		}
	}
	
	/**
	 * @author liub
	 * @description 登录超时
	 * @param request
	 * @return 重定向到指定页面
	 * @develop date 2016.08.12
	 */
	@RequestMapping(value = "/logTimeOut", method = RequestMethod.GET)
	public String logTimeOut(HttpServletRequest request){
		if (SessionUtil.isSSOLogin(request)) {
			request.getSession().invalidate();
			return "redirect:"+GlobalConstants.getString(GlobalConstants.SESSION_LOSE_KEY);
		}
		else{
			request.getSession().invalidate();
			return "redirect:/login";
		}
		
	}
	
	
	/**
	 * osms单点登录amems
	 * 
	 * @param request
	 * @param token
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/ssoLogin",method={RequestMethod.POST,RequestMethod.GET})
	public String ssoLogin(HttpServletRequest request,String token,Model model) {
		
		String loginFail = GlobalConstants.getString(GlobalConstants.LOGIN_ERROR_KEY);
		try {
			//将token解码
			getLogger().debug("encode token====="+token);
			String str= TokenUtil.getJWTMessage(token);
			getLogger().debug("decode token====="+str);
			String username = str.split("_")[0];
			String password = str.split("_")[1];
			
			String ip = getIpAddr(request);
			SysLog sysLog = new SysLog();
			sysLog.setCzip(ip);
			sysLog.setCzmc("LOGIN");
			
			//验证指定顶层机构下，用户账号，密码是否存在
			/*AuthUser authUser = authUserService.exists(username, password);*/
			/*if (authUser!=null) {*/
				/*authUserService.synAuthInfoByUser(authUser);*/
				Map<String, Object> result = userService.ssoLogin(username, password);
				//存在就加载权限，否则登陆失败
				if ("true".equals(result.get("islegal"))) {	
					// 清空当前登录用户session
	        		request.getSession().invalidate();
	        		//清空登录用户的请求
	        		SessionUtil.sessionUsers.clear();
	        		
					result.put("loginType","sso");
					SessionUtil.initSession(request, result);
					
					User user=SessionUtil.getUserFromSession(request);
					sysLog.setRzlx(1);
					sysLog.setDprtcode(user.getJgdm());
					sysLog.setCzrid(user.getId());
					sysLog.setCzsj(user.getUsername()+" "+user.getRealname());
					try{
						this.systemLogService.saveLog(sysLog);    
					}catch(Exception e){
						getLogger().error("记录单点登陆日志失败:"+username);
					}
					
					/*Start:跳转到对应模块*/
					String module = request.getParameter("module");
					String mainid = request.getParameter("mainid");
					if(null != module && !"".equals(module)){
						return "redirect:/"+module+"?mainidParam="+mainid;
					}
					/*End:跳转到对应模块*/
					
					return "redirect:/main";
				}else{
					sysLog.setRzlx(2);
					sysLog.setYcnr("系统账号不存在");
					
					try{
						this.systemLogService.saveLog(sysLog);    
					}catch(Exception e){
						getLogger().error("记录单点登陆日志失败:"+username);
					}
					
					model.addAttribute("sso_login_error_url", loginFail);
					model.addAllAttributes(result);
					getLogger().error("系统账号不存在");
					return "/sso_login_error";
				}
				
				
			/*}else {
				sysLog.setRzlx(2);
				sysLog.setYcnr("统一登陆平台用户不存在");
				
				try{
					this.systemLogService.saveLog(sysLog);    
				}catch(Exception e){
					getLogger().error("记录单点登陆日志失败:"+username);
				}
				
				model.addAttribute("sso_login_error_url", loginFail);
				model.addAttribute("message", "统一登陆平台用户不存在");
				getLogger().error("统一登陆平台用户不存在");
				return "/sso_login_error";
			}*/
			
		} catch (Exception e) {
			model.addAttribute("sso_login_error_url", loginFail);
			model.addAttribute("message", "登陆系统出现异常");
			getLogger().error("单点登陆异常", e);
			return "/sso_login_error";
		}
	}
	
	/**
	 * 单点退出：其他系统退出,当前系统退出
	 * 
	 * @param userAuthentication
	 * @return
	 * @throws ServletException 
	 */
	@ResponseBody
	@RequestMapping(value = "ssoLogout", method = {RequestMethod.POST,RequestMethod.GET})
	public void ssoLogout(HttpServletRequest request,String tokenkey) {
		if(SessionUtil.sessionUsers.get(tokenkey) != null){
			//清空当前登录用户session
			SessionUtil.sessionUsers.get(tokenkey).invalidate();
			//清空登录用户的session
			SessionUtil.sessionUsers.remove(tokenkey);
		}
	}
		
}
