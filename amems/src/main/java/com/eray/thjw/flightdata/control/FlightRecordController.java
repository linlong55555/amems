package com.eray.thjw.flightdata.control;

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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.service.FlightRecordService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

/**
 * 
 * @author 林龙
 * @description 飞行履历控制器
 */
@Controller
@RequestMapping("/flightdata/flightrecord")
public class FlightRecordController extends BaseController {

	@Resource
	private FlightRecordService flightRecordService;

	@Resource
	private PlaneDataService planeDataService;

	/**
	 * 跳转至飞行履历管理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "flightdata:flightrecord:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(HttpServletRequest request) throws BusinessException {

		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		return new ModelAndView("/flightdata/flightrecord/flightrRcord_main", model);
	}

	/**
	 * @author liub
	 * @description
	 * @param request,model
	 * @return 飞行履历列表
	 * @throws Exception
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "flightrecordList", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody FlightRecord flightRecord, HttpServletRequest request,
			Model model) throws BusinessException {
		flightRecord.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		flightRecord.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		PageHelper.startPage(flightRecord.getPagination());
		List<FlightRecord> list = this.flightRecordService.queryAllPageList(flightRecord);
		return PageUtil.pack4PageHelper(list, flightRecord.getPagination());
	}

	/**
	 * 
	 * @param fjzch
	 * @param flightDate
	 * @param dprtcode
	 * @param keyword
	 * @param zt
	 * @param request
	 * @param model
	 * @return 飞行履历导出Excel
	 * @throws BusinessException
	 */

	@RequestMapping(value = "FlightRecordList.xls")
	public String export(FlightRecord flightRecord,
			HttpServletRequest request, Model model) throws BusinessException {
		try {		
			List<FlightRecord>list=flightRecordService.queryAllPageList(flightRecord);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			model.addAttribute("fjzch", flightRecord.getFjzch());
			return outReport("xls", "common", "FlightRecord", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败",e);
		}

	}

}
