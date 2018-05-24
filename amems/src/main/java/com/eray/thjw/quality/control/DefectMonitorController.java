package com.eray.thjw.quality.control;

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
import com.eray.thjw.produce.po.DefectKeep;
import com.eray.thjw.produce.service.DefectKeepService;
/**
 * 
 * @Description 缺陷保留监控控制层
 * @CreateTime 2017年10月11日 下午4:25:24
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/quality/defectmonitoring")
public class DefectMonitorController {
	
	@Resource
	private DefectKeepService defectKeepService;
	/**
	 * 
	 * @Description 缺陷保留监控页面
	 * @CreateTime 2017年10月11日 上午10:38:59
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Privilege(code="quality:defectmonitoring:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView monitoring() {
		return new ModelAndView("quality/defectmonitor/defectmonitor_main");
	}
	/**
	 * 
	 * @Description 缺陷保留监控列表
	 * @CreateTime 2017年11月28日 上午11:13:03
	 * @CreateBy 岳彬彬
	 * @param defectKeep
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryMonitorAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody DefectKeep defectKeep,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = defectKeepService.queryMonitorAllPageList(defectKeep);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("缺陷保留监控列表加载失败!",e);
		}
	}
	/**
	 * 
	 * @Description 附件上传
	 * @CreateTime 2017年11月28日 下午1:44:18
	 * @CreateBy 岳彬彬
	 * @param defectKeep
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "filesDownSub", method = RequestMethod.POST)
	public String filesDownSub(@RequestBody DefectKeep defectKeep) throws BusinessException{
		try {
			return defectKeepService.updatefilesDownSub(defectKeep);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("附件上传失败!",e);
		}
	}
	
}
