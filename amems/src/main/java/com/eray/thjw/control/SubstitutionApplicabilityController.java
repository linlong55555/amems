package com.eray.thjw.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.service.SubstitutionApplicabilityService;

/**
 * @author sunji
 * @description 等效替代适用性
 */
@Controller
@RequestMapping("/basic/substitutionApplicability")
public class SubstitutionApplicabilityController {

	@Autowired
	private SubstitutionApplicabilityService substitutionApplicabilityservice;
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping("queryAllByMainid")
	public Map<String, Object> queryAllByMainid(@RequestParam String mainid) throws BusinessException{
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("bsList", substitutionApplicabilityservice.queryAllByMainid(mainid));
		return map;
	}
}
