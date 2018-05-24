package com.eray.thjw.material2.control;


import java.util.List;
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
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandDetails;
import com.eray.thjw.material2.service.DemandDetailsService;
import com.eray.thjw.material2.service.DemandService;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.quality.service.ProcessRecordService;


/**
 * @author 裴秀
 * @description 需求跟踪Controller
 */
@Controller
@RequestMapping("/material/demand")
public class DemandTrackingController extends BaseController {
	
	@Resource
	private DemandService demandService;
	@Resource
	private DemandDetailsService demandDetailsService;
	@Resource
	private ProcessRecordService processRecordService;
	/**
	 * @Description 需求跟踪
     * @CreateTime 2018年01月31日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:demand:tracking")
	@RequestMapping(value = "tracking", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/demand/tracking/tracking_main",model);
	
	}
	
	/**
	 * 
	 * @Description 需求跟踪需求列表
	 * @CreateTime 2018年2月26日 下午3:22:13
	 * @CreateBy 林龙
	 * @param demand
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<Demand> queryAllList(@RequestBody Demand demand,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return demandService.queryAllList(demand);
		} catch (Exception e) {
			throw new BusinessException("需求跟踪需求列表加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 需求跟踪-已关闭需求列表
	 * @CreateTime 2018年2月26日 下午3:22:13
	 * @CreateBy 林龙
	 * @param demand
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllClosePageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllClosePageList(@RequestBody Demand demand,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return demandService.queryAllClosePageList(demand);
		} catch (Exception e) {
			throw new BusinessException("需求跟踪-已关闭需求列表加载失败!",e);
		}
	}
	/**
	 * 
	 * @Description 需求跟踪-查询需求信息列表
	 * @CreateTime 2018年2月26日 下午3:22:13
	 * @CreateBy 林龙
	 * @param demand
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllDemandInfoList", method = RequestMethod.POST)
	public Map<String, Object> queryAllDemandInfoList(@RequestBody DemandDetails demandDetails,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return demandDetailsService.queryAllPageList(demandDetails);
		} catch (Exception e) {
			throw new BusinessException("需求跟踪-查询需求信息列表加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
//	@Privilege(code="produce:reservation:defect:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(@RequestBody Demand demand) throws BusinessException{
		try {
			demandService.delete(demand);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 主表关闭
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
//	@Privilege(code="produce:reservation:defect:main:03")
	@ResponseBody
	@RequestMapping(value = "closebatch", method = RequestMethod.POST)
	public void closebatch(@RequestBody Demand demand) throws BusinessException{
		try {
			demandService.updateClose(demand);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 明细表关闭
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
//	@Privilege(code="produce:reservation:defect:main:03")
	@ResponseBody
	@RequestMapping(value = "close", method = RequestMethod.POST)
	public void close(@RequestBody DemandDetails demandDetail) throws BusinessException{
		try {
			demandDetailsService.updateClose(demandDetail);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭失败!",e);
		}
	}
	
	@RequestMapping(value = "tracking/view", method = RequestMethod.GET)
	public ModelAndView view(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/demand/tracking/closed_demand",model);
	
	}
	
	/**
	 * 
	 * @Description 查询流程记录
	 * @CreateTime 2018年2月28日 下午2:07:55
	 * @CreateBy 林龙
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public List<ProcessRecord> queryList(@RequestBody ProcessRecord record)throws BusinessException{
		try {
			return processRecordService.queryList(record);
		} catch (Exception e) {
			throw new BusinessException("流程记录查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 查询需求信息明细
	 * @CreateTime 2018年3月13日 下午2:13:23
	 * @CreateBy 林龙
	 * @param demandDetails
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryDemandInfoList", method = RequestMethod.POST)
	public List<DemandDetails> queryDemandInfoList(@RequestBody DemandDetails demandDetails,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return demandDetailsService.queryDemandInfoList(demandDetails);
		} catch (Exception e) {
			throw new BusinessException("查询需求信息明细加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 出库-需求保障信息
	 * @CreateTime 2018年2月26日 下午3:22:13
	 * @CreateBy 林龙
	 * @param demand
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllDemandpprotectionPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllDemandpprotectionPageList(@RequestBody Demand demand,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return demandService.queryAllDemandpprotectionPageList(demand);
		} catch (Exception e) {
			throw new BusinessException("出库-需求保障信息加载失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 查询需求保障明细
	 * @CreateTime 2018年3月13日 下午2:13:23
	 * @CreateBy 林龙
	 * @param demandDetails
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryDemandProtectionInfoList", method = RequestMethod.POST)
	public List<DemandDetails> queryDemandProtectionInfoList(@RequestBody DemandDetails demandDetails,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			return demandDetailsService.queryDemandProtectionInfoList(demandDetails);
		} catch (Exception e) {
			throw new BusinessException("查询需求保障明细加载失败!",e);
		}
	}
}
