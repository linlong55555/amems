package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.WorkCard2Related;
import com.eray.thjw.project2.service.WorkCard2RelatedService;

/**
 * @Description 工卡-关联工卡控制层
 * @CreateTime 2017-8-26 上午9:41:43
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/project2/workcard2related")
public class WorkCard2RelatedController {

	@Resource
	private WorkCard2RelatedService workCard2RelatedService;
	
	/**
	 * @Description 根据条件查询工卡-关联工卡列表 
	 * @CreateTime 2017-8-26 上午9:43:21
	 * @CreateBy 刘兵
	 * @param workCard2Related 工卡-关联工卡
	 * @return List<WorkCard2Related> 工卡-关联工卡集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<WorkCard2Related> queryAllList(@RequestBody WorkCard2Related workCard2Related)throws BusinessException{
		try {
			return workCard2RelatedService.queryAllList(workCard2Related);
		} catch (Exception e) {
			throw new BusinessException("查询工卡-关联工卡失败!",e);
		}
	}
}
