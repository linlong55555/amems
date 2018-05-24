package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AuditDiscoveryDetail;
import com.eray.thjw.quality.service.AuditDiscoveryDetailService;
import com.eray.thjw.util.Utils;

import enu.quality.AuditDiscoverProblemTypeEnum;
import enu.quality.AuditDiscoverTypeEnum;
import enu.quality.AuditnoticeTyepEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @Description 整改措施评估
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/rectifymeasuresfollow")
public class RectifyMeasuresFollowController extends BaseController{
	
	@Resource
	private AuditDiscoveryDetailService auditDiscoveryDetailService;
	/**
	 * @Description 审核通知单
	 * @CreateTime 2017年8月14日 上午10:24:08
	 * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:rectifymeasuresfollow:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Map<String, Object> model)throws BusinessException {
	 	model = new HashMap<String, Object>();
	 	model.put("auditDiscoverTypeEnum", AuditDiscoverTypeEnum.enumToListMap());//审核问题单类型
	 	model.put("auditDiscoverProblemTypeEnum", AuditDiscoverProblemTypeEnum.enumToListMap());//审核问题单问题类型
	 	model.put("auditnoticeTyepEnum", AuditnoticeTyepEnum.enumToListMap());//审核问题单审核类型
	    return new ModelAndView("/quality/rectifymeasuresfollow/rectify_measures_follow",model);
	
	}
	/**
	 * 
	 * @Description 评估
	 * @CreateTime 2018年1月17日 上午11:50:05
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:rectifymeasuresfollow:main:01")
	@ResponseBody
	@RequestMapping(value = "/approve", method = RequestMethod.POST)
	public String approve(@RequestBody AuditDiscoveryDetail record) throws BusinessException{
		try {
			return auditDiscoveryDetailService.update4Approve(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存问题清单失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 整改措施评估
	 * @CreateTime 2018年1月17日 下午4:39:51
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryList", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody AuditDiscoveryDetail record, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			Map<String, Object> resultMap = auditDiscoveryDetailService.queryReticList(record);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("跳转问题整改措施页面失败!", e);
		}
	}
	/**
	 * 
	 * @Description 验证
	 * @CreateTime 2018年5月8日 下午1:52:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:rectifymeasuresfollow:main:02")
	@ResponseBody
	@RequestMapping(value = "/valid", method = RequestMethod.POST)
	public String valid(@RequestBody AuditDiscoveryDetail record) throws BusinessException{
		try {
			return auditDiscoveryDetailService.update4Valid(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存问题清单失败!",e);
		}
	}
	/**
	 * 
	 * @Description 关闭、驳回
	 * @CreateTime 2018年5月8日 下午1:52:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:rectifymeasuresfollow:main:03,quality:rectifymeasuresfollow:main:04")
	@ResponseBody
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public String close(@RequestBody AuditDiscoveryDetail record) throws BusinessException{
		try {
			return auditDiscoveryDetailService.update4Close(record);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存问题清单失败!",e);
		}
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2018年5月8日 下午4:50:30
	 * @CreateBy 岳彬彬
	 * @param type
	 * @param name
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="quality:correctivemeasures:main:04,quality:rectifymeasuresfollow:main:05")
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export/{type}/{name}")
	public String export(@PathVariable String type,@PathVariable String name,String paramjson, HttpServletRequest request,Model model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			AuditDiscoveryDetail record = Utils.Json.toObject(paramjson, AuditDiscoveryDetail.class);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if("3".equals(type)){
				resultMap = auditDiscoveryDetailService.queryReticList(record);			
			}else {
				resultMap = auditDiscoveryDetailService.getList(record);
			}
			List<AuditDiscoveryDetail> list = (List<AuditDiscoveryDetail>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "measures", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
	}
}
