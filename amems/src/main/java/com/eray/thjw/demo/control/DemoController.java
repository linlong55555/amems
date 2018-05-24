package com.eray.thjw.demo.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;


//TODO 样例开发
@RequestMapping(value="/demo")
@Controller(value="demoController1")
public class DemoController extends BaseController{
 
	/**
	 * @author liub
	 * @description 厂房设施清单
	 * @return 页面视图
	 */
	@Privilege(code="demo:housingfacilities:list")
	@RequestMapping(value="/housingfacilities/list",method={RequestMethod.POST,RequestMethod.GET})
	public String housingfacilitiesList(){		  
		return "/demo/housingfacilities_list";
	}
	
	/**
	 * @author liub
	 * @description 厂房设施维护监控
	 * @return 页面视图
	 */
	@Privilege(code="demo:housingfacilities:monitor")
	@RequestMapping(value="/housingfacilities/monitor",method={RequestMethod.POST,RequestMethod.GET})
	public String housingfacilitiesMonitor(){		  
		return "/demo/housingfacilities_monitor";
	}
	
	/**
	 * @author liub
	 * @description 质量安全审核发现问题汇总单
	 * @return 页面视图
	 */
	@Privilege(code="demo:security:summary")
	@RequestMapping(value="/security/summary",method={RequestMethod.POST,RequestMethod.GET})
	public String securitySummary(){		  
		return "/demo/security/summary";
	}
	
	/**
	 * @author liub
	 * @description 审核报告
	 * @return 页面视图
	 */
	@Privilege(code="demo:security:report")
	@RequestMapping(value="/security/report",method={RequestMethod.POST,RequestMethod.GET})
	public String securityReport(){		  
		return "/demo/security/report";
	}

	/**
	 * @author 裴秀
	 * @description 岗位授权
	 * @return 页面视图
	 */
	@RequestMapping(value="/eoAdd",method={RequestMethod.POST,RequestMethod.GET})
	public String eoAdd(){;
		return "/project2/EO/eo_add";
	}
	
	/**
	 * @author 裴秀
	 * @description EO的添加界面
	 * @return 页面视图
	 */
	@RequestMapping(value="/auth/applyjob",method={RequestMethod.POST,RequestMethod.GET})
	public String authApplyjob(){;
		return "/demo/authorization/apply";
	}
	
	/**
	 * @author 裴秀
	 * @description 岗位授权
	 * @return 页面视图
	 */
	@Privilege(code="demo:auth:station")
	@RequestMapping(value="/auth/station",method={RequestMethod.POST,RequestMethod.GET})
	public String authStation(){	
		return "/demo/authorization/jobs";
	}
	
	/**
	 * @author 裴秀
	 * @description 授权评估
	 * @return 页面视图
	 */
	@Privilege(code="demo:auth:assessment")
	@RequestMapping(value="/auth/assessment",method={RequestMethod.POST,RequestMethod.GET})
	public String authAssessment(){		  
		return "/demo/authorization/evaluate";
	}
	
	/**
	 * @author 裴秀
	 * @description 资质审核
	 * @return 页面视图
	 */
	@Privilege(code="demo:auth:audit")
	@RequestMapping(value="/auth/audit",method={RequestMethod.POST,RequestMethod.GET})
	public String authAudit(){		  
		return "/demo/authorization/qualification";
	}
	
	/**
	 * @author 裴秀
	 * @description 授权
	 * @return 页面视图
	 */
	@Privilege(code="demo:auth:main")
	@RequestMapping(value="/auth/main",method={RequestMethod.POST,RequestMethod.GET})
	public String authAain(){		  
		return "/demo/authorization/authorized";
	}	
	/**
	 * @author meizhiliang
	 * @description 年度质量安全审核计划
	 * @return 页面视图
	 */
	@Privilege(code="demo:security:item")
	@RequestMapping(value="/security/item",method={RequestMethod.POST,RequestMethod.GET})
	public String auditPlan(){		  
		return "/demo/security/auditplan";
	}
	/**
	 * @author meizhiliang
	 * @description 质量安全审核项目单
	 * @return 页面视图
	 */
	@Privilege(code="demo:security:audit")
	@RequestMapping(value="/security/audit",method={RequestMethod.POST,RequestMethod.GET})
	public String safetyAduit(){		  
		return "/demo/security/safetyaudit";
	}
	/**
	 * @author meizhiliang
	 * @description 质量安全审核项目单
	 * @return 页面视图
	 */
	@Privilege(code="demo:project2:test1")
	@RequestMapping(value="/project2/test1",method={RequestMethod.POST,RequestMethod.GET})
	public String test1(){		  
		return "/demo/project2/demo_main";
	}
}
