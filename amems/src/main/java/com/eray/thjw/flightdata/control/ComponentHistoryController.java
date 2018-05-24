package com.eray.thjw.flightdata.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.PartsDisassemblyRecordService;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.util.SessionUtil;
import com.google.gson.Gson;

import enu.PartsPositionEnum;
import enu.PartsStatusEnum;

/**
 * 
 * @author zhuchao
 * @description 部件履历控制器
 */
@Controller				
@RequestMapping("/flightdata/componenthistory")
public class ComponentHistoryController extends BaseController {

	@Resource
	private PartsDisassemblyRecordService partsDisassemblyRecordService;

	@Resource
	private PlaneDataService planeDataService;

	/**
	 * 跳转至部件履历管理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "flightdata:componenthistory:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(HttpServletRequest request, Model model) throws BusinessException {
		List<PlaneData> planes = planeDataService.queryList(new PlaneData());
		model.addAttribute("planes", planes);
		model.addAttribute("planeData",
				new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));

		model.addAttribute("partsStatusEnum", PartsStatusEnum.enumToListMap());
		model.addAttribute("partsPositionEnum", PartsPositionEnum.enumToListMap());
		return new ModelAndView("flightdata/componenthistory/componenthistory_manage");
	}

	@Privilege(code = "flightdata:componenthistory:manage")
	@ResponseBody
	@RequestMapping(value = "/queryList", method = { RequestMethod.POST })
	public Map<String, Object> queryList(HttpServletRequest request, @RequestParam Map<String, Object> param,
			Pagination pagination) throws BusinessException {
		try {
			// 没有选择机构，可查当前用户授权的所有机构
			if (null == param.get("dprtcode") || StringUtils.isEmpty(param.get("dprtcode").toString())) {
				List<String> dprtcodes = SessionUtil.getDprtcodeList(request);
				param.put("dprtcodes", dprtcodes);
			}
			param.put("userId", SessionUtil.getUserFromSession(request).getId());
			return partsDisassemblyRecordService.queryPartsInfoPage(param, pagination);
		} catch (RuntimeException e) {
			throw new BusinessException("查询部件履历失败", e);
		}
	}

	/**
	 * 跳转至部件履历管理页面
	 * 
	 * @param jh
	 *            件号
	 * @param xlh
	 *            序列号
	 * @return
	 * @throws Exception
	 */
	// @Privilege(code="flightdata:componenthistory:view")
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String jh, @RequestParam String xlh, @RequestParam String dprtcode)
			throws BusinessException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("jh", jh);
		params.put("xlh", xlh);
		params.put("dprtcode", dprtcode);
		return new ModelAndView("flightdata/componenthistory/componenthistory_view", params);
	}

	@ResponseBody
	@RequestMapping(value = "/viewData", method = RequestMethod.GET)
	public Map<String, Object> viewData(@RequestParam String jh, @RequestParam String xlh,
			@RequestParam String dprtcode, HttpServletRequest request, Model model) throws BusinessException {
		try {
			Map<String, Object> result = partsDisassemblyRecordService.queryPartsInfo(jh, xlh, dprtcode);
			return result;
		} catch (BusinessException e) {
			throw e;
		} catch (RuntimeException e) {
			throw new BusinessException("查看部件拆装记录失败", e);
		}

	}

	/**
	 * 
	 * @param jh
	 * @param xlh
	 * @param fjzch
	 * @param dprtcode
	 * @param request
	 * @param model
	 * @return 导出EXCEL
	 * @throws BusinessException
	 */
	@RequestMapping(value = "componenthistoryOutExcel")
	public String export(String jh, String xlh, String fjzch, String dprtcode, HttpServletRequest request,
			RedirectAttributesModelMap model) throws BusinessException {
		try {
			model.addFlashAttribute("jh", jh);
			model.addFlashAttribute("xlh", xlh);
			model.addFlashAttribute("fjzch", fjzch);
			model.addFlashAttribute("dprtCode", dprtcode);
			Map<String, Object> result = partsDisassemblyRecordService.queryPartsInfo(jh, xlh, dprtcode);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("partsDisassemblyRecords");
			List<Object> clist = new ArrayList<Object>();
			for (Map<String, Object> map : list) {
				 clist.add( map.get("FJZCH"));
				 clist.add( map.get("AZ_ZXRQ"));
				 clist.add( map.get("AZ_JLD"));
				 clist.add( map.get("AZ_GZZXM"));
				 clist.add( map.get("GDSX"));
				 clist.add( map.get("BJYY"));
				 clist.add( map.get("ZSSY"));
				 clist.add( map.get("AZ_BZ"));
				 clist.add( map.get("CJ_ZXRQ"));
				 clist.add( map.get("CJ_JLD"));
				 clist.add( map.get("CJ_GZZXM"));
				 clist.add( map.get("CXYY"));
				 clist.add( map.get("CXSY"));
				 clist.add( map.get("CJ_BZ"));
				 clist.add( map.get("CJ_ZSJJH"));
				 clist.add( map.get("CJ_ZSJXLH"));
				 clist.add( map.get("ZJSY"));
			}
			model.addFlashAttribute("list", clist);
			model.addFlashAttribute("size", clist.size());
			return "redirect:/report/".concat("xls").concat("/").concat(dprtcode).concat("/ComponetDownLoad.xls");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
	}

}
