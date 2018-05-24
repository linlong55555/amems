package com.eray.thjw.training.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.service.PlanPersonService;

/**
 * @author liub
 * @description 控制器
 */
@Controller
@RequestMapping("/training/planperson")
public class PlanPersonController extends BaseController{
	
	@Resource
	private PlanPersonService planPersonService;
	
	@ResponseBody
	@RequestMapping(value = "selectByPxjhid",method={RequestMethod.POST,RequestMethod.GET})
	public List<PlanPerson> selectByPxjhid(String pxjhid) throws BusinessException {
		try {
			return planPersonService.selectByPxjhid(pxjhid);
		} catch (Exception e) {
			throw new BusinessException("查询失败!",e);
		}
	}
	
	/**
	 * 实参人数查询
	 * @param pxjhid
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPxjhscId",method={RequestMethod.POST,RequestMethod.GET})
	public List<PlanPerson> queryByPxjhscId(String pxjhid) throws BusinessException {
		try {
			return planPersonService.queryByPxjhscId(pxjhid);
		} catch (Exception e) {
			throw new BusinessException("查询失败!",e);
		}
	}
	
}
