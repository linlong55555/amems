package com.eray.thjw.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eray.common.util.TokenUtil;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.util.ThreadVarUtil;

import enu.VerifyMechanismEnum;


@Controller
@RequestMapping("/remotesystem")
public class RemoteSystemController extends BaseController{
	
	/** osms系统单点登录地址 */
	private final static String OSMS_SSO_LOGIN_PATH = "/user/authen/ssoLogin";
	
	/** schedule系统单点登录地址 */
	private final static String SCHEDULE_SSO_LOGIN_PATH = "/homepage/ssoLogin";
	
	/** sms内部报告内部审核地址 */
	private final static String SMS_VERIFYMECHANISM = "/verifyMechanismInterface";
	
	
	/**
	 * 跳转至pbs系统，免密登录
	 * @return
	 */
	@Privilege(code="remotesystem:pbs")
	@RequestMapping("pbs")
	public ModelAndView toPbs(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", getToken());
		map.put("url", SysContext.OSMS_URL + OSMS_SSO_LOGIN_PATH);
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 跳转至schedule系统，免密登录
	 * @return
	 */
	@Privilege(code="remotesystem:schedule")
	@RequestMapping("schedule")
	public ModelAndView toSchedule(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", getToken());
		map.put("url", SysContext.SCHEDULE_URL + SCHEDULE_SSO_LOGIN_PATH);
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 跳转至SMS的新建机务内部报告页面
	 * @return
	 */
	@Privilege(code="remotesystem:sms:newmechanismreport")
	@RequestMapping("sms/newmechanismreport")
	public ModelAndView toNewMechanismReport(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", TokenUtil.getMechanismJWTString(ThreadVarUtil.getUser().getDrzhid()));
		map.put("url", SysContext.SMS_URL + SMS_VERIFYMECHANISM);
		map.put("mark", VerifyMechanismEnum.NEWMECHANISMREPORT.getMark());
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 跳转至SMS的查询机务内部报告
	 * @return
	 */
	@Privilege(code="remotesystem:sms:findmechanismreport")
	@RequestMapping("sms/findmechanismreport")
	public ModelAndView toFindMechanismReport(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", TokenUtil.getMechanismJWTString(ThreadVarUtil.getUser().getDrzhid()));
		map.put("url", SysContext.SMS_URL + SMS_VERIFYMECHANISM);
		map.put("mark", VerifyMechanismEnum.FINDMECHANISMREPORT.getMark());
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 跳转至SMS的新建机务内部审核计划
	 * @return
	 */
	@Privilege(code="remotesystem:sms:newmechanismaudit")
	@RequestMapping("sms/newmechanismaudit")
	public ModelAndView toNewMechanismAudit(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", TokenUtil.getMechanismJWTString(ThreadVarUtil.getUser().getDrzhid()));
		map.put("url", SysContext.SMS_URL + SMS_VERIFYMECHANISM);
		map.put("mark", VerifyMechanismEnum.NEWMECHANISMAUDIT.getMark());
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 跳转至SMS的查询机务内部审核计划
	 * @return
	 */
	@Privilege(code="remotesystem:sms:findmechanismaudit")
	@RequestMapping("sms/findmechanismaudit")
	public ModelAndView toFindMechanismAudit(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", TokenUtil.getMechanismJWTString(ThreadVarUtil.getUser().getDrzhid()));
		map.put("url", SysContext.SMS_URL + SMS_VERIFYMECHANISM);
		map.put("mark", VerifyMechanismEnum.FINDMECHANISMAUDIT.getMark());
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 跳转至SMS的查询机务内部审核检查单
	 * @return
	 */
	@Privilege(code="remotesystem:sms:findmechanismcheckreport")
	@RequestMapping("sms/findmechanismcheckreport")
	public ModelAndView toFindMechanismCheckReport(){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("token", TokenUtil.getMechanismJWTString(ThreadVarUtil.getUser().getDrzhid()));
		map.put("url", SysContext.SMS_URL + SMS_VERIFYMECHANISM);
		map.put("mark", VerifyMechanismEnum.FINDMECHANISMCHECKREPORT.getMark());
		return new ModelAndView("/web/public/webpage_redirect", map);
	}
	
	/**
	 * 获取token
	 * @return
	 */
	private String getToken(){
		String userid = ThreadVarUtil.getUser().getId();
		long timestamp = System.currentTimeMillis();
		String token = userid + "_" + timestamp;
		return Base64.encodeBase64String(token.getBytes());
	}
	
}
