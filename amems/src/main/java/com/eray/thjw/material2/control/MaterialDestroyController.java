package com.eray.thjw.material2.control;

import java.util.HashMap;
import java.util.List;
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
import com.eray.thjw.material2.po.Destroy;
import com.eray.thjw.material2.service.MaterialSDestroyService;
import com.eray.thjw.util.Utils;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author 裴秀
 * @description 销毁
 */
@Controller
@RequestMapping("material/destroy/airmaterial")
public class MaterialDestroyController extends BaseController{
	@Resource
	private MaterialSDestroyService materialSDestroyService;
	/**
	 * @Description 销毁
     * @CreateTime 2018年03月22日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="material:destroy:airmaterial:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Model model)throws BusinessException {
	    return new ModelAndView("/material2/destory/airmaterial/airmaterial_main");
	
	}
	/**
	 * 
	 * @Description 待销毁主列表
	 * @CreateTime 2018年3月27日 下午4:22:02
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getToDestroyList", method = RequestMethod.POST)
	public Map<String, Object> getToDestroyList(@RequestBody Destroy record ) throws BusinessException {
		try {
			return materialSDestroyService.getToDestroyList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转待销毁页面失败!", e);
		}
	}
	/**
	 * 
	 * @Description 销毁
	 * @CreateTime 2018年3月28日 下午2:06:15
	 * @CreateBy 岳彬彬
	 * @param list
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "destroy", method = RequestMethod.POST)
	public void destroy(@RequestBody List<String> list, HttpServletRequest request) throws BusinessException {
		try {
			materialSDestroyService.update4DestroyRecrod(list);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增报废申请失败!", e);
		}
	}
	/**
	 * 
	 * @Description 已销毁列表
	 * @CreateTime 2018年3月28日 下午2:06:27
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getDestroyList", method = RequestMethod.POST)
	public Map<String, Object> getDestroyList(@RequestBody Destroy record ) throws BusinessException {
		try {
			return materialSDestroyService.getDestroyList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转待销毁页面失败!", e);
		}
	}
	/**
	 * 
	 * @Description 撤销
	 * @CreateTime 2018年3月28日 下午3:10:55
	 * @CreateBy 岳彬彬
	 * @param list
	 * @param request
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "revoke", method = RequestMethod.POST)
	public void revoke(@RequestBody List<String> list, HttpServletRequest request) throws BusinessException {
		try {
			materialSDestroyService.update4RevokeRecrod(list);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("新增报废申请失败!", e);
		}
	}
	/**
	 * 
	 * @Description 导出xls
	 * @CreateTime 2018年3月29日 下午3:15:05
	 * @CreateBy 岳彬彬
	 * @param type
	 * @param name
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export/{type}/{name}")
	public String export(@PathVariable String type,@PathVariable String name,String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			Destroy record = Utils.Json.toObject(paramjson, Destroy.class);
			Map<String, Object> resultMap = new HashMap<String,Object>();
			if(type.equals("1")){ //销毁
				resultMap = materialSDestroyService.getToDestroyList(record);
			}else{//撤销
				resultMap = materialSDestroyService.getDestroyList(record);
			}
			List<Destroy> list = (List<Destroy>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			if(type.equals("1")){ //销毁
				return outReport("xls", "common", "destroy", model);
			}else{//撤销
				return outReport("xls", "common", "revoke", model);
			}
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
	}
}
