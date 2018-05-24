package com.eray.thjw.material2.control;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.service.DemandService;


/**
 * @author 裴秀
 * @description 需求校核
 */
@Controller
@RequestMapping("/material/demand")
public class DemandApproveController extends BaseController {
	
	@Resource
	private DemandService demandService;
	
	/**
	 * @Description 需求校核
     * @CreateTime 2018年01月19日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:demand:approve")
	@RequestMapping(value = "approve", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/demand/approve/approve_main",model);
	
	}
	
	/**
	 * 
	 * @Description 审批通过/审批驳回
	 * @CreateTime 2018年3月2日 上午10:35:45
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "subApprove", method = RequestMethod.POST)
	public void subApprove(@RequestBody Demand demand) throws BusinessException{
		try {
			demandService.updateSubApprove(demand);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("提交失败!",e);
		}
	}

}
