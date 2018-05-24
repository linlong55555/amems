package com.eray.thjw.aerialmaterial.control;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.aerialmaterial.service.ComponentService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;

/**
 * @Description 部件controller
 * @CreateTime 2017年10月9日 下午4:55:36
 * @CreateBy 韩武
 */
@Controller
@RequestMapping("/aerialmaterial/component")
public class ComponentController extends BaseController {
	
	@Resource
	private ComponentService componentService;
	
	/**
	 * @Description 根据件号和序列号查找部件
	 * @CreateTime 2017年10月9日 下午4:57:02
	 * @CreateBy 韩武
	 * @param component
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "findByJhAndXlh", method = RequestMethod.POST)
	public Component findByJhAndXlh(@RequestBody Component component) throws BusinessException {
		try {
			return componentService.findByJhAndXlh(component);
		} catch (Exception e) {
			throw new BusinessException("根据件号和序列号查找部件失败", e);
		}
	}
}
