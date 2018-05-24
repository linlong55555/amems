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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.quality.po.AuditDiscoveryDetail;
import com.eray.thjw.quality.service.AuditDiscoveryDetailService;

import enu.ThresholdEnum;
import enu.quality.AuditDiscoverProblemTypeEnum;
import enu.quality.AuditDiscoverTypeEnum;
import enu.quality.AuditnoticeTyepEnum;
/**
 * 
 * @Description 审核问题整改措施
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/correctivemeasures")
public class ReviewreFormMeasuresController {
	
	@Resource
	private AuditDiscoveryDetailService auditDiscoveryDetailService;
	
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	/**
	 * @Description 措施
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:correctivemeasures:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
	 	model.put("auditDiscoverTypeEnum", AuditDiscoverTypeEnum.enumToListMap());//审核问题单类型
	 	model.put("auditDiscoverProblemTypeEnum", AuditDiscoverProblemTypeEnum.enumToListMap());//审核问题单问题类型
	 	model.put("auditnoticeTyepEnum", AuditnoticeTyepEnum.enumToListMap());//审核问题单审核类型
	    return new ModelAndView("/quality/reviewreformmeasures/review_reform_measures",model);
	
	}
	
	/**
	 * 
	 * @Description 问题通知单列表
	 * @CreateTime 2018年1月9日 下午5:37:32
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody AuditDiscoveryDetail record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Map<String, Object> resultMap = auditDiscoveryDetailService.getList(record);
			// 获取监控值
			Monitorsettings monitorsettings = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.ZLWTFK.getName(),
					record.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("跳转问题整改措施页面失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取数据
	 * @CreateTime 2018年1月12日 上午10:24:03
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getRecord", method = RequestMethod.POST)
	public AuditDiscoveryDetail getRecord(@RequestBody AuditDiscoveryDetail record, HttpServletRequest request) throws BusinessException {
		try {
			return auditDiscoveryDetailService.getRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("获取问题清单失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 保存、提交、指定执行人
	 * @CreateTime 2018年1月15日 上午10:36:55
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:correctivemeasures:main:01,quality:correctivemeasures:main:02,quality:correctivemeasures:main:03")
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody AuditDiscoveryDetail record) throws BusinessException{
		try {
			return auditDiscoveryDetailService.updateRecord(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存问题清单失败!",e);
		}
	}
	/**
	 * 
	 * @Description 反馈
	 * @CreateTime 2018年1月15日 上午10:37:38
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:correctivemeasures:main:04")
	@ResponseBody
	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public void feedback(@RequestBody AuditDiscoveryDetail record) throws BusinessException{
		try {
			auditDiscoveryDetailService.update4Feedback(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("反馈问题清单失败!",e);
		}
	}
	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView Looked(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("/quality/reviewreformmeasures/correctivemeasures_view", model);
	}
}
