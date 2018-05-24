package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Reference;
import com.eray.thjw.project2.service.ReferenceService;

/**
 * @Description 相关参考文件控制层
 * @CreateTime 2017-8-19 下午3:42:43
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/project2/reference")
public class ReferenceController {

	@Resource
	private ReferenceService referenceService;
	
	/**
	 * @Description 根据条件查询相关参考文件列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param reference 相关参考文件
	 * @return List<Reference> 相关参考文件集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<Reference> queryAllList(@RequestBody Reference reference)throws BusinessException{
		try {
			return referenceService.queryAllList(reference);
		} catch (Exception e) {
			throw new BusinessException("查询相关参考文件失败!",e);
		}
	}
}
