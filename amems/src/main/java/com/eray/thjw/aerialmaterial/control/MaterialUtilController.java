 package com.eray.thjw.aerialmaterial.control;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.aerialmaterial.service.MaterialUtilService;

/**
 * 
 * 航材公共方法
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/aerialmaterial/util")
public class MaterialUtilController {
	
	@Resource
	private MaterialUtilService materialUtilService;
	
	/**
	 * 验证序列号唯一性
	 * @param dprtcode
	 * @param bjh
	 * @param sn
	 * @return
	 */
	@RequestMapping("validateSnUniqueness")
	private @ResponseBody Boolean validateSnUniqueness(@RequestParam String dprtcode, @RequestParam String bjh, @RequestParam String sn){
		return this.materialUtilService.validateSnUniqueness(dprtcode, bjh, sn);
	}
}
