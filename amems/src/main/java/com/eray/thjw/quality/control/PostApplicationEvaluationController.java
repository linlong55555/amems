package com.eray.thjw.quality.control;

import java.util.HashMap;
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
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.quality.po.PostApplicationPXPG;
import com.eray.thjw.quality.service.PostApplicationService;

import enu.quality.PostStatusEnum;

/**
 * 
 * @Description 岗位申请评估
 * @CreateTime 2017年11月9日 下午3:22:08
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/quality/post/evaluation")
public class PostApplicationEvaluationController {
	
	@Resource
	private PostApplicationService postApplicationService;
	
	/**
	 * @Description 岗位申请评估列表跳转
	 * @CreateTime 2017年8月14日 上午10:24:08
	 * @CreateBy 林龙
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:evaluation:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
		try {
			model.put("postStatusEnum", PostStatusEnum.enumToListMap()); //岗位状态枚举
			return new ModelAndView("quality/postapplicationevaluation/postapplicationevaluation_main",model);
		} catch (Exception e) {
			throw new BusinessException("岗位申请评估列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 根据mainid查询岗位授权-培训评估
	 * @CreateTime 2017-11-17 下午3:03:24
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @return 岗位授权-培训评估集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryPostApplicationPXPGByMainId", method = RequestMethod.POST)
	public List<PostApplicationPXPG> queryPostApplicationPXPGByMainId(String mainid) throws BusinessException{
		return postApplicationService.queryPostApplicationPXPGByMainId(mainid);
	}
	
	/**
	 * @Description 查询人员资质评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param workRequire 岗位要求
	 * @return List<WorkRequire> 岗位要求集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWorkRequireEval",method={RequestMethod.POST,RequestMethod.GET})
	public List<WorkRequire> queryWorkRequireEval(@RequestBody WorkRequire workRequire) throws BusinessException {
		return postApplicationService.queryWorkRequireEval(workRequire);
	}
	
	/**
	 * @Description 岗位评估
	 * @CreateTime 2017-11-16 下午6:03:54
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:evaluation:main:01")
	@ResponseBody
	@RequestMapping(value = "updateEvaluation", method = RequestMethod.POST)
	public String updateEvaluation(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.updateEvaluation(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("岗位评估失败!",e);
		}
	}
	
	/**
	 * @Description 设置有效期
	 * @CreateTime 2017-11-17 上午11:18:56
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:evaluation:main:02")
	@ResponseBody
	@RequestMapping(value = "updateEffectDate", method = RequestMethod.POST)
	public String updateEffectDate(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.updateEffectDate(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("设置有效期失败!",e);
		}
	}
	
	/**
	 * @Description 上传授权书
	 * @CreateTime 2017-11-16 下午6:03:54
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:evaluation:main:03")
	@ResponseBody
	@RequestMapping(value = "updateUpload", method = RequestMethod.POST)
	public String updateUpload(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.updateUpload(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("上传授权书失败!",e);
		}
	}


}
