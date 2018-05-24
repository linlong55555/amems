package com.eray.thjw.produce.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.service.FlightSheetService;

/**
 * @Description 飞行记录本Controller
 * @CreateTime 2017年9月15日 下午4:16:15
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/produce/flb2")
public class FlightLogBook2Controller extends BaseController {
	
	@Resource
	private FlightSheetService flightSheetService;
	
	/**
	 * @Description 跳转到飞行记录本主页面
	 * @CreateTime 2018年1月22日 下午1:57:41
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "produce:flb2:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/flb2/flightlogbook_main", responseParamMap);
	}
	
	/**
	 * @Description 跳转到飞行记录本查看页面
	 * @CreateTime 2017年9月20日 下午2:08:48
	 * @CreateBy 韩武
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(HttpServletRequest request, Model model) {
		Map<String, Object> responseParamMap = new HashMap<String, Object>();
		return new ModelAndView("produce/flb2/flightlogbook_view", responseParamMap);
	}
	
	/**
	 * 
	 * @Description 飞机记录本列表加载
	 * @CreateTime 2017年10月9日 下午4:44:14
	 * @CreateBy 林龙
	 * @param flightSheet
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public Map<String, Object> queryAllList(@RequestBody FlightSheet flightSheet,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = flightSheetService.queryAllList(flightSheet);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("飞机记录本列表加载失败!",e);
		}
	}
	
	/**
	 * @Description 保存飞行记录本
	 * @CreateTime 2017年10月24日 下午3:50:46
	 * @CreateBy 韩武
	 * @param flightSheet
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flb2:main:01,produce:flb2:main:02")
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody FlightSheet flightSheet) throws BusinessException{
		try {
			return flightSheetService.doSave(flightSheet);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存飞行记录本失败!",e);
		}
	}
	
	/**
	 * @Description 提交飞行记录本
	 * @CreateTime 2017年11月14日 上午10:03:43
	 * @CreateBy 徐勇
	 * @param flightSheet
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flb2:main:03")
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(@RequestBody FlightSheet flightSheet) throws BusinessException{
		try {
			flightSheetService.doSubmit(flightSheet);
			return flightSheet.getId();
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("提交飞行记录本失败!",e);
		}
	}
	
	/**
	 * @Description 修订飞行记录本
	 * @CreateTime 2017年11月14日 上午10:03:43
	 * @CreateBy 徐勇
	 * @param flightSheet
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flb2:main:03")
	@ResponseBody
	@RequestMapping(value = "/revise", method = RequestMethod.POST)
	public String revise(@RequestBody FlightSheet flightSheet) throws BusinessException{
		try {
			flightSheetService.doRevise(flightSheet);
			return flightSheet.getId();
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("修订飞行记录本失败!",e);
		}
	}
	
	/**
	 * @Description 作废飞行记录本
	 * @CreateTime 2017年11月14日 上午10:03:43
	 * @CreateBy 徐勇
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flb2:main:05")
	@ResponseBody
	@RequestMapping(value = "/discard/{id}", method = RequestMethod.POST)
	public void discard(@PathVariable("id") String id) throws BusinessException{
		try {
			flightSheetService.doDiscard(id);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("作废飞行记录本失败!",e);
		}
	}
	
	/**
	 * @Description 查询飞行记录本详情
	 * @CreateTime 2017年10月26日 下午5:06:23
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public FlightSheet queryDetail(@RequestBody FlightSheet record) throws BusinessException{
		try {
			return flightSheetService.queryDetailWithPage(record);
		} catch (Exception e) {
			throw new BusinessException("查询查询飞行记录本详情失败!",e);
		}
	}
	
	/**
	 * @Description 加载飞行前数据
	 * @CreateTime 2017年10月28日 下午12:50:07
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/loadPreflightData", method = RequestMethod.POST)
	public FlightSheet loadPreflightData(@RequestBody FlightSheet record) throws BusinessException{
		try {
			return flightSheetService.loadPreflightData(record);
		} catch (Exception e) {
			throw new BusinessException("加载飞行前数据失败!",e);
		}
	}
	
	/**
	 * @Description 删除飞行记录本数据
	 * @CreateTime 2017年10月28日 下午4:50:42
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	@Privilege(code = "produce:flb2:main:04")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestBody FlightSheet record) throws BusinessException{
		try {
			flightSheetService.doDelete(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("删除飞行记录本数据失败!",e);
		}
	}
}
