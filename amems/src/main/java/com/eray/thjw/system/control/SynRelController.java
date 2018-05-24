package com.eray.thjw.system.control;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.system.po.SynRel;
import com.eray.thjw.system.service.SynRelService;
import com.eray.thjw.util.SessionUtil;

/**
 * 
 * @Description 系统同步关系
 * @CreateTime 2017年9月25日 上午11:26:37
 * @CreateBy 朱超
 */
@Controller
@RequestMapping("/sys/synrel")
public class SynRelController extends BaseController {

	@Resource
	private SynRelService synRelService;
	
	@Resource
	private DeptInfoService deptInfoService;
	
	 
	/**
	 * 
	 * @Description 系统同步关系列表界面
	 * @CreateTime 2017年9月25日 上午11:36:58
	 * @CreateBy 朱超
	 * @param req
	 * @param resp
	 * @return
	 */
	@Privilege(code="sys:synrel:main")
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp){
		Map<String, Object> model = new HashMap<String, Object>();
		Department department =  SessionUtil.getJg(req);
		DeptInfo deptInfo = deptInfoService.selectById(department.getId());
		model.put("deptType", deptInfo.getDeptType());
		return new ModelAndView("sys/synrel/synrel_main", model);
	}
	
	/**
	 *  
	 * @Description 查询一页系统同步关
	 * @CreateTime 2017年9月25日 上午11:31:43
	 * @CreateBy 朱超
	 * @param entity
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/page",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> page(@RequestBody SynRel entity) throws BusinessException{
		return this.synRelService.queryPage(entity);
	}
	
	/**
	 * 
	 * @Description 保存系统同步关系
	 * @CreateTime 2017年9月25日 下午5:11:08
	 * @CreateBy 朱超
	 * @param entity
	 * @throws BusinessException
	 */
	@Privilege(code="sys:synrel:add,sys:synrel:edit")
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public @ResponseBody void save(HttpServletRequest request , @RequestBody SynRel entity) throws BusinessException{
		Department jg = SessionUtil.getJg(request);
		entity.setDprtcode(jg.getId());
		this.synRelService.save(entity);
	}
	
	/**
	 * 
	 * @Description 加载一个同步关系
	 * @CreateTime 2017年9月25日 下午5:11:05
	 * @CreateBy 朱超
	 * @param entity
	 * @throws BusinessException
	 */
	@RequestMapping(value="/load",method=RequestMethod.POST)
	public @ResponseBody SynRel load(@RequestBody SynRel entity) throws BusinessException{
		return this.synRelService.load(entity.getId());
	}
	
	 /**
	  * 
	  * @Description 批量删除同步关系
	  * @CreateTime 2017年9月25日 下午5:10:49
	  * @CreateBy 朱超
	  * @param baseEntity
	  */
	@Privilege(code="sys:synrel:delete")
	@RequestMapping(value="/dels",method=RequestMethod.POST)
	public @ResponseBody void enable(@RequestBody BaseEntity baseEntity){
		this.synRelService.dels(baseEntity);
	}
}
