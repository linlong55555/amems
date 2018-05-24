package com.eray.thjw.material2.control;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.DemandSafeguardDetail;
import com.eray.thjw.material2.service.DemandSafeguardDetailService;
import com.eray.thjw.material2.service.DemandSafeguardService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;


/**
 * @author 裴秀
 * @description 工具需求清单
 */
@Controller
@RequestMapping("/material/demand")
public class DemandToolListController extends BaseController {
	
	

}
