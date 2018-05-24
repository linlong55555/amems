package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.aerialmaterial.service.InspectionService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

import enu.aerialmaterial.MaterialSrouceEnum;

/**
 * @author 裴秀
 * @description 航材工具检验
 */
@Controller
@RequestMapping("material/inspection")
public class QualityTestingController extends BaseController{
	@Resource
	private InspectionService inspectionService;
	
	/**
	 * @Description 航材工具检验
     * @CreateTime 2018年03月21日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:inspection:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("materialSrouceEnum",MaterialSrouceEnum.enumToListMap());
			return new ModelAndView("/quality/testing/testing_main",model);
		} catch (Exception e) {
			 throw new BusinessException("航材工具检验跳转失败!",e);
		}
		
	
	}
	

	/**
	 * 
	 * @Description 航材工具检验列表
	 * @CreateTime 2018年3月26日 上午10:25:22
	 * @CreateBy 林龙
	 * @param inspection
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "inspectionList", method = RequestMethod.POST)
	public Map<String, Object> inspectionList(@RequestBody Inspection inspection,HttpServletRequest request,Model model) throws BusinessException{
		try {
			return inspectionService.queryAllPageList(inspection);
		} catch (Exception e) {
			throw new BusinessException("航材工具检验列表加载失败!",e);
		}
	}
	/**
	 * 
	 * @Description 根据id查询航材工具检验
	 * @CreateTime 2018年3月26日 下午5:09:21
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getByInspectionId", method = RequestMethod.POST)
	public Inspection getByInspectionId(@RequestBody Inspection inspection)throws BusinessException {
		try {
			return inspectionService.getByInspectionId(inspection);
		} catch (Exception e) {
			throw new BusinessException("查询航材工具检验失败!",e);
		}
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018年3月27日 下午1:55:57
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public int update(@RequestBody Inspection inspection)throws BusinessException {
		try {
			return inspectionService.updateByPrimary(inspection);
		} catch (Exception e) {
			throw new BusinessException("保存航材工具检验失败!",e);
		}
	}
	/**
	 * 
	 * @Description 提交
	 * @CreateTime 2018年3月27日 下午1:55:57
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public int submit(@RequestBody Inspection inspection)throws BusinessException {
		try {
			return inspectionService.updateByPrimarySubmit(inspection);
		} catch (Exception e) {
			throw new BusinessException("提交航材工具检验失败!",e);
		}
	}
	
	/**
	 * @Description  航材工具检验查看跳转
	 * @CreateTime 2017年8月14日 上午9:40:19
	 * @CreateBy 林龙
	 * @param id
	 * @param request
	 * @param resp
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("find/{id}")
	public ModelAndView find(@PathVariable("id") String id, HttpServletRequest request,HttpServletResponse resp,Map<String, Object> model) throws Exception {
		try {
			model.put("viewid", id);
			model.put("materialSrouceEnum",MaterialSrouceEnum.enumToListMap());
			return new ModelAndView("/quality/testing/testing_find",model);
			
		} catch (RuntimeException e) {
			throw new BusinessException("航材工具检验查看跳转失败!",e);
		}
	}
}
