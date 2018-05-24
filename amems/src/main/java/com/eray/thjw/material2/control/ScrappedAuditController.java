package com.eray.thjw.material2.control;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ScrappedApply;
import com.eray.thjw.material2.service.ScrappedApplyService;

/**
 * @author 裴秀
 * @description 报废审核
 */
@Controller
@RequestMapping("material/scrapped/audit")
public class ScrappedAuditController {
	@Resource
	private ScrappedApplyService scrappedApplyService;
	/**
	 * @Description 报废审核
     * @CreateTime 2018年03月22日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:scrapped:audit:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView list(Model model)throws BusinessException {
	    return new ModelAndView("/material2/scrapped/audit/audit_main");
	
	}
	/**
	 * 
	 * @Description 获取列表
	 * @CreateTime 2018年3月26日 下午2:06:53
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getScrappedApplyList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody ScrappedApply record ) throws BusinessException {
		try {
			return scrappedApplyService.getAuditList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转报废申请管理页面失败!", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public String audit(@RequestBody ScrappedApply record ) throws BusinessException {
		try {
			return scrappedApplyService.update4Audit(record);
		} catch (Exception e) {
			throw new BusinessException("报废审核失败!", e);
		}
	}
}
