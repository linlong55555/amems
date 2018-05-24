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
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.service.FailureKeepService;

import enu.ThresholdEnum;
/**
 * 
 * @Description 故障保留监控控制层
 * @CreateTime 2017年10月11日 下午4:25:24
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/quality/faultmonitoring")
public class FaultMonitorController {
	
	@Resource
	private FailureKeepService failureKeepService;
	@Resource
	private MonitorsettingsService monitorsettingsService;

	/**
	 * 
	 * @Description 故障保留监控页面
	 * @CreateTime 2017年10月11日 上午10:38:59
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Privilege(code="quality:faultmonitoring:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView monitor(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("quality/faultmonitor/faultmonitor_main",model);
		} catch (Exception e) {
			throw new BusinessException("故障保留监控列表跳转失败!",e);
		}
	}
	/**
	 * 
	 * @Description 故障保留监控列表
	 * @CreateTime 2017年11月28日 上午11:13:03
	 * @CreateBy 岳彬彬
	 * @param failureKeep
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitorAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryMonitorAllPageList(@RequestBody FailureKeep failureKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = failureKeepService.queryMonitorAllPageList(failureKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("故障保留监控列表加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 再次保留
	 * @CreateTime 2017年11月28日 上午11:40:35
	 * @CreateBy 岳彬彬
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:faultmonitoring:main:01")
	@ResponseBody
	@RequestMapping(value = "saveagainkeep", method = RequestMethod.POST)
	public String saveagainkeep(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			return failureKeepService.saveagainkeep(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("再次保留故障保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2017年11月28日 上午11:39:54
	 * @CreateBy 岳彬彬
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="quality:faultmonitoring:main:02")
	@ResponseBody
	@RequestMapping(value = "endModal", method = RequestMethod.POST)
	public void endModal(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeepService.updateEndModal(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭故障保留失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年11月28日 上午11:40:10
	 * @CreateBy 岳彬彬
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Privilege(code="quality:faultmonitoring:main:03")
	@ResponseBody
	@RequestMapping(value = "gConfirm", method = RequestMethod.POST)
	public void gConfirm(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			failureKeepService.updategConfirm(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("指定结束故障保留失败!",e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "getTechnicalThreshold", method = RequestMethod.POST)
	public Map<String, Object> getTechnicalThreshold(String dprtcode)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("monitorsettings", monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GZBLD.getName(),dprtcode));
			return model;
		} catch (Exception e) {
			throw new BusinessException("获取故障保留阀值失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 附件上传
	 * @CreateTime 2017年11月28日 下午1:45:48
	 * @CreateBy 岳彬彬
	 * @param failureKeep
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:faultmonitoring:main:04")
	@ResponseBody
	@RequestMapping(value = "filesDownSub", method = RequestMethod.POST)
	public String filesDownSub(@RequestBody FailureKeep failureKeep) throws BusinessException{
		try {
			return failureKeepService.updatefilesDownSub(failureKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("附件上传失败!",e);
		}
	}
}
