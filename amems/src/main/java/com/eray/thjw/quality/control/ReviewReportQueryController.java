package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.Map;





import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.ThreadVarUtil;
/**
 * 
 * @Description 审核通知单
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/qualityreviewreportquery")
public class ReviewReportQueryController {
	
	

	/**
	 * @Description 审核通知单
	 * @CreateTime 2017年8月14日 上午10:24:08
	 * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:qualityreviewreportquery:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {	
	 	model = new HashMap<String, Object>();
	 	model.put("whbmid", ThreadVarUtil.getUser().getBmdm());
	    return new ModelAndView("/quality/reviewreportquery/review_report_query",model);
	
	}
	
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(String dprtcode,String zlshbh) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("zlshbh",zlshbh);
		model.put("dprtcode",dprtcode);
		return new ModelAndView("/quality/reviewreportquery/review_report_view", model);
	}

}
