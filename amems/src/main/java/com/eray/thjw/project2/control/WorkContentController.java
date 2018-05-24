package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.WorkContent;
import com.eray.thjw.project2.service.WorkContentService;

/**
 * @Description 工作内容控制层
 * @CreateTime 2017-8-19 下午3:42:43
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/project2/workcontent")
public class WorkContentController {

	@Resource
	private WorkContentService workContentService;
	
	/**
	 * @Description 根据条件查询工作内容列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param workContent 工作内容
	 * @return List<WorkContent> 工作内容集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<WorkContent> queryAllList(@RequestBody WorkContent workContent)throws BusinessException{
		try {
			return workContentService.queryAllList(workContent);
		} catch (Exception e) {
			throw new BusinessException("查询工作内容失败!",e);
		}
	}
}
