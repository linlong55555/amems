package com.eray.thjw.project2.control;

import java.util.HashMap;
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
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.ActType;
import com.eray.thjw.project2.service.ActTypeService;

import enu.project2.TechnicalStatusEnum;

/**
 * @Description 机型控制器
 * @CreateTime 2017年8月14日 上午9:54:50
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/project2/actype")
public class AcTypeController {
	
	@Resource
	private ActTypeService actTypeService;

	/**
	 * @Description 机型列表跳转
	 * @CreateTime 2017年8月14日 上午10:24:08
	 * @CreateBy 林龙
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="project2:actype:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
		try {
			model.put("technicalStatusEnum", TechnicalStatusEnum.enumToListMap());
			return new ModelAndView("project2/actype/actype_main",model);
		} catch (Exception e) {
			throw new BusinessException("机型列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 机型列表加载
	 * @CreateTime 2017年8月14日 上午10:23:48
	 * @CreateBy 林龙
	 * @param actType
	 * @param request
	 * @param model
	 * @param resultMap
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody ActType actType,HttpServletRequest request,Model model,Map<String, Object> resultMap)throws BusinessException{
		try {
			resultMap = actTypeService.queryAllPageList(actType);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("机型列表加载失败!",e);
		}	
	}
	
	/**
	 * 
	 * @Description 保存机型
	 * @CreateTime 2017年8月14日 上午10:23:35
	 * @CreateBy 林龙
	 * @param actType 飞机机型
	 * @return String
	 * @throws BusinessException
	 */
	@Privilege(code="project2:actype:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody ActType actType) throws BusinessException{
		try {
			return actTypeService.save(actType);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存机型失败!",e);
		}
	}
	
	/**
	 * @Description 修改机型
	 * @CreateTime 2017年8月14日 上午10:23:00
	 * @CreateBy 林龙
	 * @param actType
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:actype:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody ActType actType) throws BusinessException{
		try {
			return actTypeService.update(actType);
		} catch (BusinessException e){
			throw e;
		}  catch (Exception e) {
			throw new BusinessException("修改机型失败!",e);
		}
	}
	
	/**
	 * @Description 注销机型
	 * @CreateTime 2017年8月14日 下午6:13:35
	 * @CreateBy 林龙
	 * @param actType
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:actype:main:04")
	@ResponseBody
	@RequestMapping(value = "note", method = RequestMethod.POST)
	public String note(@RequestBody ActType actType) throws BusinessException{
		try {
			return actTypeService.updatenote(actType);
		} catch (BusinessException e){
			throw e;
		}  catch (Exception e) {
			throw new BusinessException("注销机型失败!",e);
		}
	}
	
	/**
	 * @Description 启用机型
	 * @CreateTime 2017年8月14日 下午6:13:35
	 * @CreateBy 林龙
	 * @param actType
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project2:actype:main:03")
	@ResponseBody
	@RequestMapping(value = "enabled", method = RequestMethod.POST)
	public String enabled(@RequestBody ActType actType) throws BusinessException{
		try {
			return actTypeService.updateEnabled(actType);
		} catch (BusinessException e){
			throw e;
		}  catch (Exception e) {
			throw new BusinessException("启用机型失败!",e);
		}
	}
	/**
	 * 
	 * @Description 根据组织机构查询机型
	 * @CreateTime 2018-1-31 下午6:56:02
	 * @CreateBy 孙霁
	 * @param actType
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "findByDprtcode", method = RequestMethod.POST)
	public List<ActType> enabled(String dprtcode) throws BusinessException{
		try {
			return actTypeService.findByDprtcode(dprtcode);
		}  catch (Exception e) {
			throw new BusinessException("查询失败!",e);
		}
	}
}
