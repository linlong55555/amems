package com.eray.thjw.material2.control;


import java.util.List;
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
import com.eray.thjw.material2.service.DemandApplyService;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;


/**
 * @author 裴秀
 * @description 需求提报
 */
@Controller
@RequestMapping("/material/demand")
public class DemandApplyController extends BaseController {
	
	@Resource
	private DemandApplyService demandApplyService;
	
	/**
	 * @Description 需求提报
     * @CreateTime 2018年01月19日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:demand:apply")
	@RequestMapping(value = "apply", method = { RequestMethod.GET,RequestMethod.POST })
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/demand/apply/apply_main",model);
	}

	/**
	 * @Description 查询飞机信息
	 * @CreateTime 2018年2月26日 上午11:50:13
	 * @CreateBy 韩武
	 * @param ac
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/acinfo", method = RequestMethod.POST)
	public List<AircraftinfoStatus> queryAircraftInfo(@RequestBody Aircraftinfo ac) throws BusinessException{
		try {
			return demandApplyService.queryAircraftInfo(ac);
		} catch (Exception e) {
			throw new BusinessException("查询飞机信息失败!",e);
		}
	}
	
	/**
	 * @Description 查询需求提报详情
	 * @CreateTime 2018年2月27日 下午2:23:32
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Demand queryDetail(String id) throws BusinessException{
		try {
			return demandApplyService.queryDetail(id);
		} catch (Exception e) {
			throw new BusinessException("查询需求提报详情失败!",e);
		}
	}
	
	/**
	 * @Description 保存需求提报
	 * @CreateTime 2018年2月27日 上午10:33:56
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Demand save(@RequestBody Demand demand) throws BusinessException{
		try {
			return demandApplyService.doSave(demand);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存需求提报失败!",e);
		}
	}
	
	/**
	 * @Description 提交需求提报
	 * @CreateTime 2018年2月27日 上午10:33:56
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public Demand submit(@RequestBody Demand demand) throws BusinessException{
		try {
			return demandApplyService.doSubmit(demand);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("提交需求提报失败!",e);
		}
	}
	
	/**
	 * @Description 查询当前人的需求提报列表
	 * @CreateTime 2018年2月28日 下午5:06:14
	 * @CreateBy 韩武
	 * @param ac
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/selflist", method = RequestMethod.POST)
	public Map<String, Object> querySelfList(@RequestBody Demand demand) throws BusinessException{
		try {
			return demandApplyService.querySelfList(demand);
		} catch (Exception e) {
			throw new BusinessException("查询当前人的需求提报列表失败!",e);
		}
	}
	
	/**
	 * @Description 需求提报查看
     * @CreateTime 2018年01月19日 
     * @CreateBy 林龙
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "find", method = RequestMethod.GET)
	public ModelAndView find(Map<String, Object> model)throws BusinessException {
	    return new ModelAndView("/material2/demand/apply/apply_find",model);
	}
	
	
	/**
	 * @Description 根据航材工具清单生成提报单详情
	 * @CreateTime 2018年4月12日 上午9:38:10
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/builddetailbymaterialtool", method = RequestMethod.POST)
	public Demand buildDetailByMaterialTool(@RequestBody Demand demand) throws BusinessException{
		try {
			return demandApplyService.buildDetailByMaterialTool(demand);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("根据航材工具清单生成提报单详情失败!",e);
		}
	}
}
