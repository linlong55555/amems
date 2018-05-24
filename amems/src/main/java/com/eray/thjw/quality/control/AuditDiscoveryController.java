package com.eray.thjw.quality.control;

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

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AuditDiscovery;
import com.eray.thjw.quality.service.AuditDiscoveryService;
import com.eray.thjw.util.Utils;

import enu.quality.AuditDiscoverProblemTypeEnum;
import enu.quality.AuditDiscoverTypeEnum;
import enu.quality.AuditnoticeTyepEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/**
 * 
 * @Description 发现问题通知单
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/noticeofdiscovery")
public class AuditDiscoveryController extends BaseController{
	
	@Resource
	private AuditDiscoveryService auditDiscoveryService;

	/**
	 * @Description 跳转到发现问题通知单
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@ResponseBody
	@Privilege(code="quality:noticeofdiscovery:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model, String shxmdid)throws BusinessException {
	 	model = new HashMap<String, Object>();
	 	model.put("auditDiscoverTypeEnum", AuditDiscoverTypeEnum.enumToListMap());//审核问题单类型
	 	model.put("auditDiscoverProblemTypeEnum", AuditDiscoverProblemTypeEnum.enumToListMap());//审核问题单问题类型
	 	model.put("auditnoticeTyepEnum", AuditnoticeTyepEnum.enumToListMap());//审核问题单审核类型
	 	model.put("shxmdid", shxmdid);//审核项目单id
	    return new ModelAndView("/quality/auditdiscovery/audit_discovery",model);
	
	}
	
	/**
	 * 
	 * @Description 问题通知单列表
	 * @CreateTime 2018年1月9日 下午5:37:32
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody AuditDiscovery record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			return auditDiscoveryService.getList(record);
		} catch (Exception e) {
			throw new BusinessException("跳转发现问题通知单页面失败!", e);
		}
	}
	
	
	/**
	 * @Description 保存发现问题通知单
	 * @CreateTime 2018年1月8日 下午1:33:10
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:noticeofdiscovery:main:01,quality:noticeofdiscovery:main:02")
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody AuditDiscovery record) throws BusinessException{
		try {
			return auditDiscoveryService.doSave(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存发现问题通知单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 获取通知单
	 * @CreateTime 2018年1月10日 下午5:01:59
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getRecord", method = RequestMethod.POST)
	public AuditDiscovery getRecord(@RequestBody AuditDiscovery record, HttpServletRequest request) throws BusinessException {
		try {
			return auditDiscoveryService.getRecord(record);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("获取问题通知单失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018年1月10日 下午5:03:20
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:noticeofdiscovery:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String deleteRecord(@RequestBody AuditDiscovery record, HttpServletRequest request) throws BusinessException {
		try {
			auditDiscoveryService.deleteRecord(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除问题通知单失败!", e);
		}
	}
	/**
	 * 
	 * @Description 关闭问题单
	 * @CreateTime 2018年1月11日 上午10:25:41
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:noticeofdiscovery:main:04")
	@ResponseBody
	@RequestMapping(value = "close", method = RequestMethod.POST)
	public String closeRecord(@RequestBody AuditDiscovery record, HttpServletRequest request) throws BusinessException {
		try {
			auditDiscoveryService.update4CloseRecord(record);
			return record.getId();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("关闭问题通知单失败!", e);
		}
	}
	/**
	 * 
	 * @Description 查看
	 * @CreateTime 2018年5月8日 下午2:05:53
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView Looked(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("/quality/auditdiscovery/question_view", model);
	}
	
	@Privilege(code="quality:noticeofdiscovery:main:05")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "NoticeDiscovery.xls")
	public String export(String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			AuditDiscovery record = Utils.Json.toObject(paramjson, AuditDiscovery.class);
			Map<String, Object> resultMap = auditDiscoveryService.getList(record);			
			List<AuditDiscovery> list = (List<AuditDiscovery>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "discovery", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
	}
}
