package com.eray.thjw.quality.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.QualityAuditReport;
import com.eray.thjw.quality.service.AuditQualityReportService;
/**
 * 
 * @Description 质量审核报告
 * @CreateTime 2017年12月26日 
 * @CreateBy 裴秀
 */
@Controller
@RequestMapping("/quality/qualityreviewreport")
public class AuditReportController {

@Autowired	
private AuditQualityReportService auditQualityReportService;

	/**
	 * @Description 质量审核报告
     * @CreateTime 2017年12月26日 
     * @CreateBy 裴秀
	 * @param model
	 * @return 
	 * @throws BusinessException
	 */
	@Privilege(code="quality:qualityreviewreport:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request,Model model)throws BusinessException {
	 	Map<String,Object> map=new HashMap<String, Object>();
	    return new ModelAndView("/quality/auditreport/audit_report",map);
	
	}
	
	@ResponseBody
	@RequestMapping(value = "getAuditReportList", method = RequestMethod.POST)
    public Map<String,Object> getList(HttpServletRequest request,@RequestBody QualityAuditReport auditReport,Model model) throws Exception{
		Map<String,Object> map=new HashMap<String, Object>();
		          try {
		        	  map=auditQualityReportService.getList(auditReport);
				} catch (BusinessException e) {
					throw new BusinessException("获取列表数据异常");
				} catch(Exception e){
					throw new Exception(e.getMessage());
				}
    	         return map;
    }
	//校验质量审核编号唯一性
	@ResponseBody
	@RequestMapping(value = "validatShbhUnique", method = RequestMethod.POST)
	public String validatShbhUnique(HttpServletRequest request,String zlshbh,String dprtcode,String id) throws Exception{
		    String isUnique=null;
		try {
			List<String> list=auditQualityReportService.validatShbhUnique(zlshbh, dprtcode,id);
			 if(list==null||list.size()==0)
				 isUnique="1";
		} catch (BusinessException e) {
			throw new BusinessException("校验质量审核编号唯一性异常");
		} catch(Exception e){
			throw new Exception(e.getMessage());
		}
		     return isUnique;
	}
	@Privilege(code="quality:qualityreviewreport:main:01,quality:qualityreviewreport:main:02,quality:qualityreviewreport:main:04,quality:qualityreviewreport:main:05")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public int save(HttpServletRequest request,@RequestBody QualityAuditReport reoort) throws Exception{
		  try {
			auditQualityReportService.save(reoort);
		} catch (BusinessException e) {
			throw new BusinessException();
		} catch(Exception t){
			throw new Exception(t.getMessage());
		}
		return 0;		
	}
	
	@Privilege(code="quality:qualityreviewreport:main:03")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
    public int delete(HttpServletRequest request,String id) throws Exception{
		try {
			auditQualityReportService.delete(id);
		} catch (BusinessException e) {
			throw new BusinessException("删除失败");
		} catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		    return 0;	
    }
	
	@ResponseBody
	@RequestMapping(value = "selectByKey", method = RequestMethod.POST)
    public QualityAuditReport selectByKey(HttpServletRequest request,String id) throws Exception{
		QualityAuditReport report=null;
		try {
			report=auditQualityReportService.selectBykey(id);
		} catch (BusinessException e) {
			throw new BusinessException("查询失败");
		} catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		    return report;	
    }
	
	@ResponseBody
	@RequestMapping(value = "selectByQualityNum", method = RequestMethod.POST)
    public QualityAuditReport selectByQualityNum(HttpServletRequest request,@RequestBody  QualityAuditReport report) throws Exception{
             return auditQualityReportService.selectByQualityNum(report);	
}
}