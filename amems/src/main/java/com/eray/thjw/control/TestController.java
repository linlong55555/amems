package com.eray.thjw.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能测试用
 * @author xu.yong
 *
 */
@Controller
public class TestController {

	@RequestMapping("/demo/testEcharts")
	public String testEcharts(){
		return "echarts_demo/demo";
	} 
	
}
