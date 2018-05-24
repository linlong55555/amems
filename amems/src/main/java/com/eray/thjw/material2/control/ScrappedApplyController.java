package com.eray.thjw.material2.control;

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
import com.eray.thjw.material2.po.ScrappedApply;
import com.eray.thjw.material2.service.ScrappedApplyService;

/**
 * @author 裴秀
 * @description 报废申请
 */
@Controller
@RequestMapping("material/scrapped/apply")
public class ScrappedApplyController {
	
	@Resource
	private ScrappedApplyService scrappedApplyService;
	/**
	 * @Description 报废申请
     * @CreateTime 2018年03月22日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:scrapped:apply:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView list(Model model)throws BusinessException {
	    return new ModelAndView("/material2/scrapped/apply/apply_main");
	
	}
	
	/**
	 * 
	 * @Description 获取报废申请主页面列表数据
	 * @CreateTime 2018年3月22日 上午11:36:24
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getScrappedApplyList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody ScrappedApply record ) throws BusinessException {
		try {
			return scrappedApplyService.getScrappedApplyList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转报废申请管理页面失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取报废申请明细列表数据
	 * @CreateTime 2018年3月22日 下午2:33:44
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getScrappedDetailList", method = RequestMethod.POST)
	public Map<String, Object> getScrappedDetailList(@RequestBody ScrappedApply record, HttpServletRequest request) throws BusinessException {
		try {
			return scrappedApplyService.getListById(record);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年3月26日 下午1:58:46
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addRecord(@RequestBody ScrappedApply record, HttpServletRequest request) throws BusinessException {
		try {
			return scrappedApplyService.addRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增报废申请失败!", e);
		}
	}
	/**
	 * 
	 * @Description 获取数据
	 * @CreateTime 2018年3月26日 下午1:58:36
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getRecord", method = RequestMethod.POST)
	public ScrappedApply getRecord(@RequestBody ScrappedApply record, HttpServletRequest request) throws BusinessException {
		try {
			return scrappedApplyService.getRecord(record.getId());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("获取报废登记失败!", e);
		}
	}
	/**
	 * 
	 * @Description 编辑
	 * @CreateTime 2018年3月26日 下午1:58:28
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String updateRecord(@RequestBody ScrappedApply record, HttpServletRequest request) throws BusinessException {
		try {
			return scrappedApplyService.updateRecrod(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增报废申请失败!", e);
		}
	}
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018年3月26日 下午1:58:20
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void deleteRecord(@RequestBody ScrappedApply record, HttpServletRequest request) throws BusinessException {
		try {
			scrappedApplyService.deleteRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!", e);
		}
	}
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年3月26日 下午1:58:13
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doClose", method = RequestMethod.POST)
	public String updateRecord4Close(@RequestBody ScrappedApply record, HttpServletRequest request) throws BusinessException {
		try {
			scrappedApplyService.updateRecord4Close(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!", e);
		}
	}
	/**
	 * 
	 * @Description 查看
	 * @CreateTime 2018年3月27日 下午2:59:01
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView Looked(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("/material2/scrapped/apply/apply_view", model);
	}
}
