package com.eray.thjw.quality.control;

import java.util.HashMap;
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
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.quality.service.PostApplicationService;

import enu.quality.PostStatusEnum;

/**
 * 
 * @Description 岗位申请审核
 * @CreateTime 2017年11月9日 下午3:22:08
 * @CreateBy 林龙
 */
@Controller
@RequestMapping("/quality/post/review")
public class PostApplicationReviewController {
	
	@Resource
	private PostApplicationService postApplicationService;

	/**
	 * @Description 岗位申请审核列表跳转
	 * @CreateTime 2017年8月14日 上午10:24:08
	 * @CreateBy 林龙
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:review:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
		try {
			model.put("postStatusEnum", PostStatusEnum.enumToListMap()); //岗位状态枚举
			return new ModelAndView("quality/postapplicationreview/postapplicationreview_main",model);
		} catch (Exception e) {
			throw new BusinessException("岗位申请审核列表跳转失败!",e);
		}
	}
	
	/**
	 * @Description 审核通过
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:review:main:01")
	@ResponseBody
	@RequestMapping(value = "passed", method = RequestMethod.POST)
	public String passed(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.updatePassed(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核通过失败!",e);
		}
	}
	
	/**
	 * @Description 审核驳回
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:post:review:main:01")
	@ResponseBody
	@RequestMapping(value = "turnDown", method = RequestMethod.POST)
	public String turnDown(@RequestBody PostApplication postApplication) throws BusinessException{
		try {
			return postApplicationService.updateTurnDown(postApplication);
		} catch (BusinessException e){
			throw e;
		} catch (Exception e) {
			 throw new BusinessException("审核驳回失败!",e);
		}
	}

}
