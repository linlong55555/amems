package com.eray.thjw.quality.control;

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
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.quality.po.PersonnelToAuthorizationRecord;
import com.eray.thjw.quality.service.PersonnelToAuthorizationRecordService;

import enu.ThresholdEnum;

/**
 * 
 * @Description 档案-授权记录
 * @CreateTime 2017年9月19日 下午1:57:08
 * @CreateBy 朱超
 */
@Controller
@RequestMapping("/quality/authorizationmonitor")
public class QualityAuthorizationmonitorController extends BaseController {
	
	@Resource 
	private PersonnelToAuthorizationRecordService personnelToAuthorizationRecordService;
	
	@Resource 
	private MonitorsettingsService monitorsettingsService;
	
	/**
	 *  
	 * @Description 跳转至档案-授权记录管理页面
	 * @CreateTime 2017年9月19日 下午2:05:07
	 * @CreateBy 朱超
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "quality:authorizationmonitor:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView manage() throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/quality/authorizationmonitor/authorizationmonitor_main", model);
	}
	
	/**
	 *  
	 * @Description 跳转至档案-授权记录管理页面
	 * @CreateTime 2017年9月19日 下午2:05:07
	 * @CreateBy 朱超
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "quality:authorizationmonitor:list")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/quality/authorizationmonitor/authorizationmonitor_main", model);
	}
	
	/**
	 * 
	 * @Description 档案-授权记录分页查询
	 * @CreateTime 2017年9月19日 下午2:04:48
	 * @CreateBy 朱超
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public Map<String, Object> queryPage(@RequestBody PersonnelToAuthorizationRecord record) throws BusinessException{
		try {
			Map<String, Object> resultMap = personnelToAuthorizationRecordService.page(record);
			Monitorsettings monitorsettings = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.SQZZ.getName(), record.getDprtcode());
			resultMap.put("monitorsettings", monitorsettings);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败!", e);
		}
	}
	 
	/**
	 * 
	 * @Description 导出执照有效期记录
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "LicenseValidMonitor.xls")
	public String export(PersonnelToAuthorizationRecord personnelToAuthorizationRecord, String dqrqStart,String dqrqEnd,HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			personnelToAuthorizationRecord.setPagination(p);
			personnelToAuthorizationRecord.setDprtcode(new String (personnelToAuthorizationRecord.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			personnelToAuthorizationRecord.getParamsMap().put("dprtcode", personnelToAuthorizationRecord.getDprtcode());
			personnelToAuthorizationRecord.getParamsMap().put("dqrqStart", dqrqStart);
			personnelToAuthorizationRecord.getParamsMap().put("dqrqEnd", dqrqEnd);
			Map<String, Object> resultMap = personnelToAuthorizationRecordService.page(personnelToAuthorizationRecord);
			List<PersonnelToAuthorizationRecord> list = (List<PersonnelToAuthorizationRecord>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "zzyxqjk", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
	
}
