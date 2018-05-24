package com.eray.thjw.control;



import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.service.MonitorItemService;

/**
 * @author liub
 * @description 监控项目控制层
 * @develop date 2016.08.29
 */
@Controller
@RequestMapping("/project/monitoritem")
public class MonitorItemController extends BaseController {
	
	/**
	 * @author liub
	 * @description 监控项目service
	 * @develop date 2016.08.29
	 */
	@Autowired
	private MonitorItemService monitorItemService;
	
	
	private static final Logger log = LoggerFactory.getLogger(MonitorItemController.class);
	
	/**
	 * @author liub
	 * @description 
	 * @param djbh,model
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.25
	 */
	@ResponseBody
	@RequestMapping(value = "queryListByDjxmid",method={RequestMethod.POST,RequestMethod.GET})
	public List<Map<String, Object>> queryListByDjxmid(Model model,String djxmid) throws BusinessException {

		log.info("查询监控项目 前端参数:djxmid:{}", new Object[]{djxmid});
		
		List<Map<String, Object>> list = null;
		
		try {
			list = monitorItemService.queryListByDjxmid(djxmid);
		} catch (Exception e) {
			throw new BusinessException("获取监控项目失败!");
		}finally{}
		
		return list;
	}
		
}
