package com.eray.thjw.project2.control;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.EOPulicationAffected;
import com.eray.thjw.project2.service.EOPulicationAffectedService;

/** 
 * @Description EO-受影响出版物 控制层
 * @CreateTime 2017-8-24 下午3:21:21
 * @CreateBy 雷伟	
 */
@Controller
@RequestMapping("/project2/pulication")
public class EOPulicationAffectedController extends BaseController {
	
	@Resource
	private EOPulicationAffectedService eoPulicationAffectedService;
	
	/**
	 * @Description 查询EO受影响出版物
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoPulicationAffected EO受影响出版物
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllList", method = RequestMethod.POST)
	public List<EOPulicationAffected> queryAllList(@RequestBody EOPulicationAffected eoPulicationAffected)throws BusinessException{
		try {
			return eoPulicationAffectedService.queryAllList(eoPulicationAffected);
		} catch (Exception e) {
			throw new BusinessException("查询EO受影响出版物失败!",e);
		}
	}
}
