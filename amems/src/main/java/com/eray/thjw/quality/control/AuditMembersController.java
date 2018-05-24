package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.quality.po.Auditnotice;
import com.eray.thjw.quality.service.AuditMembersService;
import com.eray.thjw.quality.service.AuditNoticeService;

import enu.ThresholdEnum;
/**
 * 
 * @Description 审核成员
 * @CreateTime 2018-1-11 上午11:15:02
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/quality/auditMembers")
public class AuditMembersController {
	
	@Resource
	private AuditMembersService auditMembersService;

	/**
	 * 
	 * @Description 根据mainid查询审核成员
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectByYwid")
	public Map<String, Object> selectByYwid(HttpServletRequest request, String ywid) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("rows",  auditMembersService.selectByYwid(ywid));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	

}
