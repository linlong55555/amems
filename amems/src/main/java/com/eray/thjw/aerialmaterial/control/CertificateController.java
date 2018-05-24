package com.eray.thjw.aerialmaterial.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.AircraftStatusService;
import com.eray.thjw.produce.service.ComponenthistoryService;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ThresholdEnum;

/**
 * 
 * @Description 
 * @CreateTime 2017-11-9 下午1:59:09
 * @CreateBy 孙霁
 */
@Controller
@RequestMapping("/aerialmaterial/certificate")
public class CertificateController extends BaseController {
	
	@Resource
	private DepartmentService departmentService;
	@Resource
	private HCMainDataService hCMainDataService;
	@Resource
	private ComponenthistoryService componenthistoryService;
	@Resource
	private ComponentCertificateService componentCertificateService;
	
	/**
	 * 
	 * @Description 跳转到主列表页面
	 * @CreateTime 2017-11-9 下午2:01:21
	 * @CreateBy 孙霁
	 * @param request
	 * @param model
	 * @return
	 */
	@Privilege(code = "aerialmaterial:certificate:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request, Model model) {
		return new ModelAndView("demo/certificate/certificate_main");
	}
	
	/**
	 * 
	 * @Description 查询所有数据
	 * @CreateTime 2017-10-23 下午2:14:04
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAll", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody HCMainData hCMainData,HttpServletRequest request,Model model)throws BusinessException{
		try {
			Map<String, Object> resultMap = hCMainDataService.queryAllCertificate(hCMainData);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 根据部件号和序列号和批次号和组织机构查询航材数据和证书数据
	 * @CreateTime 2017-11-13 下午2:47:30
	 * @CreateBy 孙霁
	 * @param request
	 * @param hCMainData
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping("selectByBjhAndXlh")
	public Map<String, Object> selectByBjhAndXlh(HttpServletRequest request,@RequestBody HCMainData hCMainData) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("row",  hCMainDataService.selectByBjhAndXlh(hCMainData));
			return resultMap;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询失败！",e);
		}
		
	}
	
	/**
	 * 
	 * @Description 修改证书
	 * @CreateTime 2017-9-23 下午4:21:50
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "updateCertificate", method = RequestMethod.POST)
	@Privilege(code="aerialmaterial:certificate:main:01")
	public void updateCertificate(@RequestBody InstallationListEditable record,HttpServletRequest request) throws BusinessException{
		try {
			// 保存证书
			componentCertificateService.add(record);
		} catch (Exception e) {
			throw new BusinessException("数据添加失败！",e);
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-19 下午4:50:06
	 * @CreateBy 孙霁
	 * @param hCMainData
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByParams", method = RequestMethod.POST)
	public List<ComponentCertificate> selectByParams(@RequestBody ComponentCertificate componentCertificate,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return componentCertificateService.selectByParams(componentCertificate);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}
	
}
