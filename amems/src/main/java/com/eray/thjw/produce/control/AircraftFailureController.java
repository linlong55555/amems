package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.AircraftFailure;
import com.eray.thjw.produce.service.AircraftFailureService;

/**
 * @Description 飞机故障履历控制器
 * @CreateTime 2018年5月22日 上午9:36:16
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/produce/aircraftfailure")
public class AircraftFailureController extends BaseController{

	@Resource
	private AircraftFailureService aircraftFailureService;
	
	/**
	 * @Description 跳转到飞机故障履历主页面
	 * @CreateTime 2018年5月22日 上午9:37:27
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "produce:aircraftfailure:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/aircraftfailure/aircraftfailure_main", responseParamMap);
	}
	
	/**
	 * @Description 查询飞机故障履历分页列表
	 * @CreateTime 2018年5月22日 上午10:14:42
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/pageablelist", method = RequestMethod.POST)
	public Map<String, Object> queryPageableList(@RequestBody AircraftFailure record)throws BusinessException{
		try {
			return aircraftFailureService.queryPageableList(record);
		} catch (Exception e) {
			throw new BusinessException("查询飞机故障履历列表失败!", e);
		}
	}
	
	/**
	 * @Description 飞机故障履历导出
	 * @CreateTime 2018年5月22日 下午1:58:34
	 * @CreateBy 韩武
	 * @param json
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "aircraftfailure.xls")
	public String export(String json, Model model) throws BusinessException {
		try {
			json = new String(json.getBytes("iso-8859-1"),"utf-8");
			AircraftFailure record = JSON.parseObject(json, AircraftFailure.class);
			List<AircraftFailure> list = aircraftFailureService.queryList(record);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "aircraftfailure", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
