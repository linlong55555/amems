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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Destruction;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.DestructionSerivce;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.ThresholdEnum;

/**
 * 
 * @author sunji
 * @description 销毁控制器
 */
@Controller
@RequestMapping("/aerialmaterial/destruction")
public class DestructionController {
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private DestructionSerivce destructionSerivce;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private MaterialHistoryService materialHistoryService;
	@Resource
	private OrderAttachmentService orderAttachmentService;
	
	/**
	 * 跳转至销毁页面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="aerialmaterial:destruction:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView main(Destruction destruction) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("/material/destruction/destruction_main",model);
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> main(@RequestBody Stock stock,HttpServletRequest request,Model model)throws BusinessException{
		Map<String, Object> resultMap=new HashMap<String, Object>();			
		User user = ThreadVarUtil.getUser();
		//验证机型权限
		stock.getParamsMap().put("userId", user.getId()); 
		List<Stock> list = stockSerivce.queryByCklb(stock);
		resultMap.put("rows", list);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public Map<String, Object> list(@RequestBody Destruction destruction,HttpServletRequest request)throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramsMap.put("userId", user.getId());
		destruction.setParamsMap(paramsMap);
		PageHelper.startPage(destruction.getPagination());
		List<Destruction> list =destructionSerivce.queryDestructionList(destruction);
		return PageUtil.pack4PageHelper(list, destruction.getPagination());
	}
	/**
	 * 跳转至添加销毁页面
	 * @return 页面视图
	 */
	@Privilege(code="aerialmaterial:destruction:main:01")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam List<String> ids) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("stockList", stockSerivce.queryByIds(ids));
		return new ModelAndView("/material/destruction/destruction_add",model);
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Map<String, Object> add(@RequestBody Destruction destruction)throws BusinessException{
		Map<String, Object> resultMap=destructionSerivce.save(destruction);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public Map<String, Object> submit(@RequestBody Destruction destruction)throws BusinessException{
		Map<String, Object> resultMap=destructionSerivce.submit(destruction);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "queryChoStock", method = RequestMethod.POST)
	public Map<String, Object> queryChoStock(@RequestParam String id)throws BusinessException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("rows", materialHistoryService.queryChoStock(id));
		return resultMap;
	}
	/**sunji
	 * 跳转至修改销毁页面
	 * @return 页面视图
	 * @throws BusinessException 
	 */
	@Privilege(code="aerialmaterial:destruction:main:02")
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam String id) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("destruction", destructionSerivce.selectByPrimaryKey(id));
		return new ModelAndView("/material/destruction/destruction_edit",model);
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public Map<String, Object> edit(@RequestBody Destruction destruction)throws BusinessException{
		Map<String, Object> resultMap=destructionSerivce.edit(destruction);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public Map<String, Object> update(@RequestBody Destruction destruction)throws BusinessException{
		Map<String, Object> resultMap=destructionSerivce.update(destruction);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@Privilege(code="aerialmaterial:destruction:main:04")
	@ResponseBody
	@RequestMapping(value = "deleteDestruction", method = RequestMethod.POST)
	public Map<String, Object> deleteDestruction(@RequestParam String id)throws BusinessException{
		Map<String, Object> resultMap=destructionSerivce.deleteDestruction(id);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 */
	@Privilege(code="aerialmaterial:destruction:main:03")
	@ResponseBody
	@RequestMapping(value = "undoDestruction", method = RequestMethod.POST)
	public Map<String, Object> undoDestruction(@RequestParam String id)throws BusinessException{
		Map<String, Object> resultMap=destructionSerivce.updateUndoDestruction(id);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 获取上传附件
	 */
	@ResponseBody
	@RequestMapping(value = "selectedScwjList", method = RequestMethod.POST)
	public Map<String, Object> selectedScwjList(@RequestParam String mainid, Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	    try{
	    	@SuppressWarnings("unused")
	    	User user = ThreadVarUtil.getUser();             // 获取当前登入人对象
	    	List<OrderAttachment> orderAttachmentList=orderAttachmentService.queryAll(mainid);
	    	resultMap.put("rows", orderAttachmentList);
		}catch(Exception e){ 
			throw new BusinessException("上传附件加载失败"); }
		return resultMap;
	}
	/**sunji
	 * 跳转至查看销毁页面
	 * @return 页面视图
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String id) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("destruction", destructionSerivce.selectByPrimaryKey(id));
		return new ModelAndView("/material/destruction/destruction_view",model);
	}

}
