package com.eray.thjw.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Substitution;
import com.eray.thjw.project2.service.WorkCardService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.SubstitutionService;

/**
 * @author sunji
 * @description 等效替代
 */
@Controller
@RequestMapping("/basic/substitution")
public class SubstitutionController {
	
	@Autowired
	private SubstitutionService substitutionService;
	@Autowired
	private PlaneModelDataService planeModelDataService;
	@Autowired
	private WorkCardService workCardService;
	
	/**
	 * 跳转至等效替代界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="basic:substitution:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Annunciate annunciate) {
		return new ModelAndView("basic/substitution/substitution_main");
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> queryAnnunciateList(@RequestBody Substitution substitution,HttpServletRequest request,Model model)throws BusinessException{
		Map<String, Object> resultMap = substitutionService.queryAll(substitution);
		return resultMap;
}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping("queryFjzchAndGk")
	public Map<String, Object> queryFjzchAndGk(@RequestParam String dprtcode) throws BusinessException{
		Map<String, Object> map =new HashMap<String, Object>();
		//查询该组织机构下的所有机型
		map.put("jxList", planeModelDataService.findByDprtcode(dprtcode));
		//查询该组织机构下的所有工卡
		map.put("gkList", workCardService.findByDprtcode(dprtcode));
		return map;
	}
	/**
	 * 保存替换
	 * @return 
	 * @throws BusinessException 
	 * @throws Exception 
	 */
	//@Privilege(code="basic:substitution:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody Substitution substitution) throws BusinessException{
		try {
			String id=substitutionService.save(substitution);
			return id;
		} catch (BusinessException  e) {
			throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			throw new BusinessException("数据添加失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	//@Privilege(code="airportensure:oilprice:main:02")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@RequestBody Substitution substitution) throws BusinessException{
		try {
			String id=substitutionService.update(substitution);
			return id;
		} catch (BusinessException  e) {
			throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			throw new BusinessException("数据修改失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	//@Privilege(code="airportensure:oilprice:main:03")
	@ResponseBody
	@RequestMapping(value = "invalid", method = RequestMethod.POST)
	public String invalid(@RequestBody Substitution substitution) throws BusinessException{
		try {
			String id=substitutionService.invalid(substitution);
			return id;
		} catch (BusinessException e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据id查询等效替代部件及对应的机型适应性、工卡适应性信息
	 * @param id
	 * @return HCMainData
	 * @develop date 2016.12.14
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public Substitution selectById(String id) throws BusinessException {
		Substitution substitution = null;
		try {
			substitution = substitutionService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询等效替代部件失败!",e);
		}
		return substitution;
	}
	
	/**
	 * @author liub
	 * @description 通过部件号、机构代码获取等效替代部件信息
	 * @param bjh,dprtcode
	 * @return List<Substitution>
	 */
	@ResponseBody
	@RequestMapping(value = "selectSubByBjhAndDprt",method={RequestMethod.POST,RequestMethod.GET})
	public List<Substitution> selectSubByBjhAndDprt(String bjh,String dprtcode) throws BusinessException {
		List<Substitution> list = null;
		try {
			list = substitutionService.selectSubByBjhAndDprt(bjh,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询等效替代部件失败!",e);
		}
		return list;
	}
	
	/**
	 * @Description 通过部件号、替代件号、机构代码获取等效替代部件信息
	 * @CreateTime 2017-11-21 下午5:30:01
	 * @CreateBy 刘兵
	 * @param bjh 部件号
	 * @param tdjh 替代件号
	 * @param dprtcode 机构代码
	 * @return Substitution 等效替代
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getSubByBjhAndTdjhAndDprt",method={RequestMethod.POST,RequestMethod.GET})
	public Substitution getSubByBjhAndTdjhAndDprt(String bjh, String tdjh, String dprtcode) throws BusinessException {
		try {
			return substitutionService.getSubByBjhAndTdjhAndDprt(bjh, tdjh, dprtcode);
		} catch (Exception e) {
			throw new BusinessException("查询等效替代部件失败!",e);
		}
	}
	

	@RequestMapping(value = "substitution_view",method={RequestMethod.GET})
	public ModelAndView substitution_view(String id) throws BusinessException {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("basic/substitution/substitution_view");
		modelAndView.addObject("id", id);
		return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "validateUnique",method={RequestMethod.POST})
	public boolean validateUnique(@RequestBody Substitution substitution) throws BusinessException {
		boolean isUnique=false;	      
	     
		List<Substitution>  list=substitutionService.validateUnique(substitution);
	      
	      if(list!=null&&list.size()>0)isUnique=true;
	      
	        return isUnique;
	      
	}
	
	
	
	

	
}
