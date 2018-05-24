package com.eray.thjw.system.control;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.po.Printinfo;
import com.eray.thjw.system.service.PrintinfoService;
/**
 * 
 * @Description 打印
 * @CreateTime 2017年12月27日 上午11:42:17
 * @CreateBy 岳彬彬
 */
@Controller
@RequestMapping("/sys/print")
public class PrintinfoController  extends BaseController{
	
	@Resource
	private PrintinfoService printinfoService;

	/**
	 * 
	 * @Description 新增打印记录
	 * @CreateTime 2017年12月27日 上午11:53:45
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void save(@RequestBody Printinfo record) throws BusinessException{
		try {		
			printinfoService.addPrintCount(record);
		} catch (Exception e) {
			 throw new BusinessException();
		}
	}
	
}
