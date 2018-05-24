package com.eray.thjw.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Button;
import com.eray.thjw.po.Menu;
import com.eray.thjw.po.RoleToBtn;
import com.eray.thjw.service.ButtonService;
import com.eray.thjw.service.MenuService;
import com.eray.thjw.service.RoleToBtnService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * @author 梅志亮
 * @description  按钮操作的控制层
 * @develop date 2016.08.05
 */
@Controller
@RequestMapping("/sys/button")
public class ButtonController {
	@Resource
	private ButtonService buttonService;                                               //引入按钮的service
	@Resource
	private MenuService menuService;                                               //引入菜单的service
	@Resource
	private RoleToBtnService roleToBtnService;                                               //引入按钮的service
	/**
	 * 进入菜单列表页面
	 * @return
	 */
	@Privilege(code="sys:button:main")
	@RequestMapping("main")
	public String getIndex(){
		return "sys/button/buttonlist";
	}
	
    /**
     * 请求按钮的列表数据
     * @param button
     * @param request
     * @throws BusinessException
     */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="queryButtonList", method = RequestMethod.POST)
	public Map<String, Object> findButton(@RequestBody Button button)throws BusinessException {
		String id = "";//用户刚编辑过的记录 ID
		if(button.getId() != null){
			id = button.getId().toString();
		}
		//清除查询条件中的ID，保证列表查询结果集的正确性
		button.setId(null);
		
		PageHelper.startPage(button.getPagination());
		List<Button> list = buttonService.selectButtonList(button);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Button button1 = new Button();
					button1.setId(id);
					List<Button> newList = buttonService.selectButtonList(button1);
					if(newList != null && newList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, button.getPagination());
		}else{
			List<Button> newRecordList = new ArrayList<Button>(1);
			if(StringUtils.isNotBlank(String.valueOf(id))){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Button button1 = new Button();
				button1.setId(id);
				newRecordList = buttonService.selectButtonList(button1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, button.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, button.getPagination());
		}
	}
	/**
	 * 增加按钮初始化
	 * @return
	 */
	@RequestMapping("intoAddButton")
	public ModelAndView intoAddMenu() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/sys/button/add_button", model);
	}
	/**
	 * 验证 新增按钮是否重复
	 * @param buttonCode
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getbuttonConut")
	public int getCont(String buttonCode) throws Exception {
		return buttonService.selectButtonCount(buttonCode);
	}
	/**
	 * 执行按钮的新增操作
	 * @param button
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addButton")
	public String addButton(@RequestBody Button button) {
		return buttonService.addButton(button);
	}
	/**
	 * 修改菜单的初始化页面
	 * @param id
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@RequestMapping("{id}/initUpdateButton")
	public ModelAndView intoEditMenu(@PathVariable("id") String id) throws  Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Button button1=new Button();
		button1.setId(String.valueOf(id));
		Button button = buttonService.selectButton(button1);
		model.put("button", button);
		return new ModelAndView("/sys/button/edit_button", model);
	}
	/**
	 * 执行按钮的修改操作
	 * @param button
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("updateButton")
	public int modifyRole(@RequestBody Button button) throws Exception {
		return buttonService.updateButton(button);
	}
	/**
	 * 构建菜单树
	 * @param request
	 * @param model
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="queryButtonTree1", method = RequestMethod.POST)
	public List<Map<String, Object>> queryMenuTree1() throws Exception {
     	List<Menu> menus = menuService.findAll();
    	Map<String, Object> menuMap = new HashMap<String, Object>();
		roleDprtTree("0",menuMap,menus);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> menuList = (List<Map<String, Object>>) menuMap.get("children");
		return menuList;
	}
		
	/**
	 * @author liub
	 * @description 配置菜单
	 * @return List<Map<String, Object>>
	 * @throws Exception 
	 * @develop date 2016.08.10
	 */
	private  void roleDprtTree(String id,Map<String, Object> menuMap,List<Menu> menu) throws Exception{  
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();   
	    for (Menu menus : menu) {  
	        if (null !=menus.getParentId()  &&  id.equals(menus.getParentId())) { 
	        	Map<String, Object> childrenMap = new HashMap<String, Object>();
	        	String name=menus.getMenuName()+"/"+menus.getMenuFname();
	        	
	        	childrenMap.put("id", menus.getId());
	        	childrenMap.put("name", name);
	        	childrenMap.put("state", "open");
	        	childrenMap.put("iconCls", "icon-blank");
	        	roleDprtTree(menus.getId(),childrenMap,menu);  
	        	menuList.add(childrenMap);  
	        }  
	    }  
	    menuMap.put("children", menuList);

    }
	/**
	 * 判断角色按钮是否有关联
	 * @param id
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="isRole_Button/{id}")                
	public int isRole_Button(@PathVariable("id") String id)throws BusinessException {
		int count=0;
		String button_id=String.valueOf(id);
		RoleToBtn rtb=new RoleToBtn();
		rtb.setButtonId(button_id);
		try {
			count= roleToBtnService.queryCount(rtb);
		} catch (Exception e) {
			 throw new BusinessException("查询按钮角色关联失败!");
		}
		return count;
	}
	/**
	 * 执行按钮的删除操作
	 * @param id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="deleteButton/{id}")
	   public int deleteMenu(@PathVariable("id") String id) throws Exception {
			String id2=String.valueOf(id);
			return buttonService.deleteButtonByPrimaryKey(id2);
	}
	
}
