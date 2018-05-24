package com.eray.thjw.system.control;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.aware.SpringContextHolder;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.system.po.Dict;
import com.eray.thjw.system.po.DictItem;
import com.eray.thjw.system.service.AccountService;
import com.eray.thjw.system.service.DictItemService;
import com.eray.thjw.system.service.DictService;
import com.eray.thjw.util.ThreadVarUtil;

/**
 * @author 梅志亮
 * @description  数据字典的控制层
 * @develop date 2016.08.05
 */
@Controller
@RequestMapping("/sys/dict")
public class DictController extends BaseController {
    
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DictService dictService;
	@Autowired
	private DictItemService dictItemService;
	
	@Resource
	private AccountService accountService;
	/**
	 * 进入字典管理界面
	 * @author Meizhiliang
	 * @return ModelAndView
	 * @param HttpServletRequest req, HttpServletResponse resp
	 */
	@Privilege(code="sys:dict:main")
	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse resp){
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		//超级机构用户 拥有的组织机构
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
	     //非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		return new ModelAndView("sys/dic_item/dictlist", model);
	}
	/**
	 * 查询字典类型列表操作
	 * @author Meizhiliang
	 * @return Map<String, Object>
	 * @param Dict
	 */
	@ResponseBody
	@RequestMapping(value="getNewDicListByDprtcode", method = RequestMethod.POST)
	public Map<String, Object> getNewDicListByDprtcode(@RequestBody Dict dict) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", dictService.getNewDicListByDprtcode(dict));
		return resultMap;
	}
	/**
	 * 查询字典明细列表操作
	 * @author Meizhiliang
	 * @return Map<String, Object>
	 * @param DictItem
	 */
	@ResponseBody
	@RequestMapping(value="getDictItemList", method = RequestMethod.POST)
	public Map<String, Object> getDictItemList(@RequestBody DictItem dictItem) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", dictItemService.getDictItemList(dictItem));
		return resultMap;
	}
	/**
	 * 对字典明细进行新增操作
	 * @author Meizhiliang
	 * @return String
	 * @param DictItem
	 */
	@Privilege(code = "sys:dict:main:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String addDicItem(@RequestBody DictItem dictItem) throws Exception {
		String massge="";
		try{
		     massge=dictItemService.insertSelective(dictItem);
		     SpringContextHolder.initDic();
		}catch(Exception e){
			 throw new BusinessException("增加字典明细失败！",e);
		}
		return massge;
	}
	/**
	 * 对字典明细进行修改操作
	 * @author Meizhiliang
	 * @return String
	 * @param DictItem
	 */
	@Privilege(code = "sys:dict:main:02")
	@ResponseBody
	@RequestMapping(value = "Edit", method = RequestMethod.POST)
	public String editDicItem(@RequestBody DictItem dictItem) throws Exception {
		String massge="";
		try{
		     massge=dictItemService.updateByPrimaryKeySelective(dictItem);
		     SpringContextHolder.initDic();
		}catch(Exception e){
			 throw new BusinessException("修改字典明细失败！",e);
		}
		return massge;
	}
	/**
	 * 对字典明细进行删除操作
	 * @author Meizhiliang
	 * @return String
	 * @param String
	 */
	@Privilege(code = "sys:dict:main:03")
	@ResponseBody
	@RequestMapping(value = "Delete", method = RequestMethod.POST)
	public String deleteDicItem(@RequestBody DictItem dictItem) throws Exception {
		String massge="";
		try{
		     massge=dictItemService.deleteByDictItem(dictItem);
		     SpringContextHolder.initDic();
		}catch(Exception e){
			 throw new BusinessException("删除字典明细失败！",e);
		}
		return massge;
	}
	/**
	 * 对字典和字典明细进行同步操作
	 * @author Meizhiliang
	 * @return String
	 * @param String
	 */
	@Privilege(code = "sys:dict:main:04")
	@ResponseBody
	@RequestMapping(value = "Synch", method = RequestMethod.POST)
	public String Synch(@Param(value="lxid")Integer lxid,@Param (value="dprtcode") String dprtcode) throws Exception {
		String massge="";
		try{
		     massge=dictItemService.doSynch(lxid, dprtcode);
		     SpringContextHolder.initDic();
		}catch(Exception e){
			 throw new BusinessException("同步字典失败！",e);
		}
		return massge;
	}
}
