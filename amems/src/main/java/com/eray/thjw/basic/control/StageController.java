package com.eray.thjw.basic.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.basic.po.Stage;
import com.eray.thjw.basic.service.StageService;
import com.eray.thjw.exception.BusinessException;


/** 
 * @Description 阶段 控制器
 * @CreateTime 2017-9-14 上午11:31:56
 * @CreateBy 甘清	
 */
@Controller
@RequestMapping("/basic/stage")
public class StageController {
	
	@Resource
	private StageService stageService;
	
	@Privilege(code = "basic:stage:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		return new ModelAndView("basic/stage/stage_main");
	}
	
	/**
	 * @Description stage列表信息加载
	 * @CreateTime 2017-9-14 下午5:13:04
	 * @CreateBy 甘清
	 * @param stage
	 * @return Map<String, Object>
	 */
	@Privilege(code = "basic:stage:main")
	@ResponseBody
	@RequestMapping(value = "getstageList", method = RequestMethod.POST)
	public Map<String, Object> getstageList(@RequestBody Stage stage) throws BusinessException {	
		try {
			Map<String, Object> map = stageService.getStages(stage);
			return map;
		} catch (BusinessException e) {
			throw e;
		} catch(Exception e){
			 throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * @Description  新增stage
	 * @CreateTime 2017-9-15 上午11:12:35
	 * @CreateBy 甘清
	 * @param stage 前端stage参数
	 * @return Map<String, Object>
	 */
	@Privilege(code = "basic:stage:main:add")
	@ResponseBody
	@RequestMapping(value = "addstage", method = RequestMethod.POST)
	public Map<String, Object> addStage(@RequestBody Stage stage) throws BusinessException {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Stage resultStage = stageService.addStage(stage);
			map.put("stage", resultStage);
			return map;
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("添加stage失败！", e);
		}
	}
	/**
	 * @Description 根据id & 组织机构码获得阶段信息
	 * @CreateTime 2017-9-15 下午4:08:17
	 * @CreateBy 甘清
	 * @param stage 阶段信息查询
	 * @return map
	 */
	@ResponseBody
	@RequestMapping(value = "getstagebyid", method = RequestMethod.POST)
	public Map<String, Object> getStageById(@RequestBody Stage stage) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stage", stageService.getStageById(stage));
		return map;
	}
	
	/**
	 * @Description  更新stage
	 * @CreateTime 2017-9-15 上午11:12:35
	 * @CreateBy 甘清
	 * @param stage 前端stage参数
	 * @return Map<String, Object>
	 */
	@Privilege(code = "basic:stage:main:edit")
	@ResponseBody
	@RequestMapping(value = "updatestage", method = RequestMethod.POST)
	public void updateStage(@RequestBody Stage stage) throws BusinessException {
		try{
			stageService.updateStage(stage);
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("更新stage失败！", e);
		}
	}
	
	/**
	 * @Description 删除stage（逻辑删除）
	 * @CreateTime 2017-9-24 下午7:39:14
	 * @CreateBy 甘清
	 * @param id stage ID
	 * @throws BusinessException
	 */
	@Privilege(code = "basic:stage:main:del")
	@ResponseBody
	@RequestMapping(value = "del/{id}", method = RequestMethod.POST)
	public void deleteStage(@PathVariable("id")String id) throws BusinessException {
		try{
			stageService.updateStageStatus(id);
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("删除stage失败！", e);
		}
	}
	
	/**
	 * @Description 根据机构代码获取阶段集合
	 * @CreateTime 2017-9-19 下午2:12:39
	 * @CreateBy 刘兵
	 * @param dprtcode 机构代码
	 * @return List<Stage> 阶段集合
	 */
	@ResponseBody
	@RequestMapping(value = "getStageListByDrpt", method = RequestMethod.POST)
	public List<Stage> getStageListByDrpt(String dprtcode){	
		return stageService.getStageListByDrpt(dprtcode);
	}

}
