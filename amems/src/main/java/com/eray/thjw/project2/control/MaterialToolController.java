package com.eray.thjw.project2.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.util.Utils;

/**
 * @Description 器材/工具控制层
 * @CreateTime 2017-8-19 下午3:42:43
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/project2/materialtool")
public class MaterialToolController extends BaseController {

	@Resource
	private MaterialToolService materialToolService;
	
	/**
	 * @Description 根据条件查询器材/工具列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<MaterialTool> queryAllList(@RequestBody MaterialTool materialTool)throws BusinessException{
		try {
			return materialToolService.queryAllList(materialTool);
		} catch (Exception e) {
			throw new BusinessException("查询器材/工具失败!",e);
		}
	}
	
	/**
	 * @Description 根据条件查询器材清单列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryEquipmentList", method = RequestMethod.POST)
	public List<MaterialTool> queryEquipmentList(@RequestBody MaterialTool materialTool)throws BusinessException{
		try {
			return materialToolService.queryEquipmentList(materialTool);
		} catch (Exception e) {
			throw new BusinessException("查询器材清单失败!",e);
		}
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(维修项目)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryToolList4Maintenance", method = RequestMethod.POST)
	public List<MaterialTool> queryToolList4Maintenance(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
			return materialToolService.queryToolList4Maintenance(materialTool);
		}catch (RuntimeException e) {
			throw new BusinessException("查询航材工具需求清单失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(EO)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryToolList4EO", method = RequestMethod.POST)
	public List<MaterialTool> queryToolList4EO(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
			return materialToolService.queryToolList4EO(materialTool);
		}catch (RuntimeException e) {
			throw new BusinessException("查询航材工具需求清单失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工单)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryToolList4WorkOrder", method = RequestMethod.POST)
	public List<Map<String, Object>> queryToolList4WorkOrder(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
	    	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	    	List<MaterialTool> materialToolList = materialToolService.queryToolList4WorkOrder(materialTool);
	    	for (MaterialTool mt : materialToolList) {
	    		Map<String, Object> resultMap = new HashMap<String, Object>();
	    		resultMap.put("jh", mt.getJh());
	    		resultMap.put("jhly", mt.getJhly());
	    		resultMap.put("bjlx", mt.getBjlx());
	    		resultMap.put("bjid", mt.getBjid());
	    		resultMap.put("dprtcode", mt.getDprtcode());
	    		resultMap.put("paramsMap", mt.getParamsMap());
	    		resultList.add(resultMap);
			}
			return resultList;
		}catch (RuntimeException e) {
			throw new BusinessException("查询航材工具需求清单失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工包)
	 * @CreateTime 2017-10-26 下午2:06:39
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryToolList4Package", method = RequestMethod.POST)
	public List<Map<String, Object>> queryToolList4Package(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
	    	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	    	List<MaterialTool> materialToolList = materialToolService.queryToolList4Package(materialTool);
	    	for (MaterialTool mt : materialToolList) {
	    		Map<String, Object> resultMap = new HashMap<String, Object>();
	    		resultMap.put("jh", mt.getJh());
	    		resultMap.put("jhly", mt.getJhly());
	    		resultMap.put("bjlx", mt.getBjlx());
	    		resultMap.put("bjid", mt.getBjid());
	    		resultMap.put("dprtcode", mt.getDprtcode());
	    		resultMap.put("paramsMap", mt.getParamsMap());
	    		resultList.add(resultMap);
			}
			return resultList;
		}catch (RuntimeException e) {
			throw new BusinessException("查询航材工具需求清单失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(选中的)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllToolList", method = RequestMethod.POST)
	public List<Map<String, Object>> queryAllToolList(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
	    	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	    	List<MaterialTool> materialToolList = materialToolService.queryAllToolList(materialTool);
	    	for (MaterialTool mt : materialToolList) {
	    		Map<String, Object> resultMap = new HashMap<String, Object>();
	    		resultMap.put("jh", mt.getJh());
	    		resultMap.put("jhly", mt.getJhly());
	    		resultMap.put("bjlx", mt.getBjlx());
	    		resultMap.put("bjid", mt.getBjid());
	    		resultMap.put("dprtcode", mt.getDprtcode());
	    		resultMap.put("paramsMap", mt.getParamsMap());
	    		resultList.add(resultMap);
			}
			return resultList;
		}catch (RuntimeException e) {
			throw new BusinessException("查询航材工具需求清单失败",e);
		}
	}
	/**
	 * 
	 * @Description 根据条件查询航材工具需求清单(145工包)
	 * @CreateTime 2017年10月28日 下午4:09:13
	 * @CreateBy 岳彬彬
	 * @param materialTool
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryToolList4WP145", method = RequestMethod.POST)
	public List<Map<String, Object>> queryToolList4WP145(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
	    	List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	    	List<MaterialTool> materialToolList = materialToolService.queryToolList4WP145(materialTool);
	    	for (MaterialTool mt : materialToolList) {
	    		Map<String, Object> resultMap = new HashMap<String, Object>();
	    		resultMap.put("jh", mt.getJh());
	    		resultMap.put("jhly", mt.getJhly());
	    		resultMap.put("bjlx", mt.getBjlx());
	    		resultMap.put("bjid", mt.getBjid());
	    		resultMap.put("dprtcode", mt.getDprtcode());
	    		resultMap.put("paramsMap", mt.getParamsMap());
	    		resultList.add(resultMap);
			}
			return resultList;
		}catch (RuntimeException e) {
			throw new BusinessException("查询航材工具需求清单失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(选中的)
	 * @CreateTime 2017-11-1 上午11:08:23
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryTaskInfoList", method = RequestMethod.POST)
	public List<MaterialTool> queryTaskInfoList(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
			return materialToolService.queryTaskInfoList(materialTool);
		}catch (RuntimeException e) {
			throw new BusinessException("查询任务信息列表失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工包)
	 * @CreateTime 2017-11-1 上午11:08:23
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryTaskInfoList4Package", method = RequestMethod.POST)
	public List<MaterialTool> queryTaskInfoList4Package(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
			return materialToolService.queryTaskInfoList4Package(materialTool);
		}catch (RuntimeException e) {
			throw new BusinessException("查询任务信息列表失败",e);
		}
	}
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工单)
	 * @CreateTime 2017-11-1 上午11:08:23
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryTaskInfoList4WorkOrder", method = RequestMethod.POST)
	public List<MaterialTool> queryTaskInfoList4WorkOrder(@RequestBody MaterialTool materialTool) throws BusinessException {
	    try{
			return materialToolService.queryTaskInfoList4WorkOrder(materialTool);
		}catch (RuntimeException e) {
			throw new BusinessException("查询任务信息列表失败",e);
		}
	}
	/**
	 * 
	 * @Description 根据条件查询器材/工具任务信息列表(145工包)
	 * @CreateTime 2017年11月3日 上午9:48:24
	 * @CreateBy 岳彬彬
	 * @param materialTool
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryTaskInfoList4WP145", method = RequestMethod.POST)
	public List<MaterialTool> queryTaskInfoList4WP145(@RequestBody MaterialTool materialTool) throws BusinessException {
		try{
			return materialToolService.queryTaskInfoList4WP145(materialTool);
		}catch (RuntimeException e) {
			throw new BusinessException("查询任务信息列表失败",e);
		}
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年12月22日 上午11:02:34
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param dprtcode
	 * @param hclx
	 * @param hclx_ej
	 * @param lyrq
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "toolDetail")
	public String export(String paramjson, HttpServletRequest request, RedirectAttributesModelMap model) throws BusinessException {
		try {		
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			MaterialTool materialTool = Utils.Json.toObject(paramjson, MaterialTool.class);
			List<MaterialTool> list;
			if("1".equals(materialTool.getParamsMap().get("type"))){
				list = materialToolService.queryAllToolList(materialTool);
			}else if("2".equals(materialTool.getParamsMap().get("type"))){
				list = materialToolService.queryToolList4Package(materialTool);
			}else if("3".equals(materialTool.getParamsMap().get("type"))){
				list = materialToolService.queryToolList4WorkOrder(materialTool);
			}else if("4".equals(materialTool.getParamsMap().get("type"))){
				list = materialToolService.queryToolList4WP145(materialTool);
			}else if("12".equals(materialTool.getParamsMap().get("type"))){
				list = materialToolService.queryToolList4Package(materialTool);
			}else{
				list = materialToolService.queryAllToolList(materialTool);
			}
			List<MaterialTool> qcList = new ArrayList<MaterialTool>();
			List<MaterialTool> gjList = new ArrayList<MaterialTool>();
			if(list.size()>0){
				for (MaterialTool materialTool2 : list) {
					if(1 == materialTool2.getBjlx() || 4 == materialTool2.getBjlx()){
						String tdjxx = (String) materialTool2.getParamsMap().get("tdjxx");
						doTdjxx(tdjxx,materialTool2,qcList);
					}
					if(2 == materialTool2.getBjlx() || 3 == materialTool2.getBjlx()){
						String tdjxx = (String) materialTool2.getParamsMap().get("tdjxx");
						doTdjxx(tdjxx,materialTool2,gjList);
					}
				}
			}
			model.addFlashAttribute("qcList",  qcList);
			model.addFlashAttribute("gjList",  gjList);
			return "redirect:/report/".concat("xls").concat("/").concat(materialTool.getDprtcode()).concat("/toolDetail.xls");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
	}
	/**
	 * 
	 * @Description 处理导出中拆换件
	 * @CreateTime 2017年12月22日 下午3:26:54
	 * @CreateBy 岳彬彬
	 * @param tdjxx
	 * @param materialTool
	 * @param list
	 */
	private void doTdjxx(String tdjxx,MaterialTool materialTool,List<MaterialTool> list){
		if(null != tdjxx && !"".equals(tdjxx)){
			StringBuffer sbf =new StringBuffer("");
			String[] tdj = tdjxx.split(",");
			for (String str : tdj) {
				String[] s = str.split("#_#");
				for (int i = 0; i < s.length; i++) {
					sbf.append(s[0]).append(" : ");
					if(i == 1){
						sbf.append(s[1]).append(",");
					}
					if(i == 3){
						sbf.append(s[3]).append("\n");
					}
				}
				if(s.length == 1){
					sbf.append("0").append(",");
				}
			}
			materialTool.getParamsMap().put("tdjxx", sbf.toString());
		}
		list.add(materialTool);
	}
}
