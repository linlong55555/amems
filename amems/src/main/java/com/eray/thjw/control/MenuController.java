package com.eray.thjw.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.eray.thjw.po.Menu;
import com.eray.thjw.service.MenuService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author 梅志亮
 * @description  菜单管理的控制层
 * @develop date 2016.08.05
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController {
	@Resource
	private MenuService menuService;    
	/**
	 * 进入菜单列表页 
	 * @return String
	 */
	@Privilege(code="sys:menu:main")
	@RequestMapping("main")
	public String getIndex(){
		return "/sys/menu/menulist";
	}
    /**
     * 请求菜单列表数据
     * @param menu
     * @throws BusinessException
     */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="queryMenuList", method = RequestMethod.POST)
	public Map<String, Object> findDicList(@RequestBody Menu menu)throws BusinessException {
		String id = "";//用户刚编辑过的记录 ID
		if(menu.getId() != null){
			id = menu.getId().toString();
		}
		//清除查询条件中的ID，保证列表查询结果集的正确性
		menu.setId(null);
		PageHelper.startPage(menu.getPagination());
		List<Menu> list = menuService.selectMenuList(menu);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Menu menu1 = new Menu();
					menu1.setId(id);
					List<Menu> newList = menuService.selectMenuList(menu1);
					if(newList != null && newList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, menu.getPagination());
		}else{
			List<Menu> newRecordList = new ArrayList<Menu>(1);
			if(StringUtils.isNotBlank(String.valueOf(id))){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Menu menu1 = new Menu();
				menu1.setId(String.valueOf(id));
				newRecordList = menuService.selectMenuList(menu1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, menu.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, menu.getPagination());
		}
	}
	/**
	 * 新增菜单初始化
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("intoAddMenu")
	public ModelAndView intoAddMenu() {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/sys/menu/add_menu", model);
	}
	/**
	 * 判断菜单是否已经存在
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("getMenuConut")                      
	public int getMenuConut(@RequestBody Menu menu) throws Exception {
		return menuService.queryC(menu);
	}
	/**
	 * 执行菜单的新增
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("addMenu")
	public String addMemu(@RequestBody Menu menu) throws Exception {
		return menuService.addMenu(menu);
	}
	/**
	 * 修改菜单初始化
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("{id}/intoEditMenu")
	public ModelAndView intoEditMenu(@PathVariable("id") String id) throws  Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		Menu menu1=new Menu();
		menu1.setId(String.valueOf(id));
		Menu menu = menuService.selectMenu(menu1);
		model.put("menu", menu);
		return new ModelAndView("/sys/menu/edit_menu", model);
	}
	/**
	 * 执行菜单的修改操作
	 * @param id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("updateMenu")
	public int modifyRole(@RequestBody Menu menu) throws Exception {
		return menuService.updateMenu(menu);
	}
	/**
	 * 加载菜单树
	 * @param menu
	 * @param request
	 * @param model
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="queryMenuTree1", method = RequestMethod.POST)
	public List<Map<String, Object>> queryMenuTree1(@RequestBody Menu menu) throws Exception {
		List<Menu> menus = menuService.findAlls(menu);
		Map<String, Object> menuMap = new HashMap<String, Object>();
		roleDprtTree("0",menuMap,menus);
		List<Map<String, Object>> menuList = (List<Map<String, Object>>) menuMap.get("children");
		return menuList;
	}
	
	/**
	 * @author liub
	 * @description 配置菜单
	 * @param 
	 * @return List<Map<String, Object>>
	 * @throws Exception 
	 * @develop date 2016.08.10
	 */
	private  void roleDprtTree(String menuParentId,Map<String, Object> menuMap,List<Menu> menu) throws Exception{  
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();   
	    for (Menu menus : menu) {  
	        if (null !=menus.getParentId()  &&  menuParentId.equals(menus.getParentId())) { 
	        	Map<String, Object> childrenMap = new HashMap<String, Object>();
	        	String name=menus.getMenuName()+"/"+menus.getMenuFname();
	        	
	        	childrenMap.put("id", menus.getId());
	        	childrenMap.put("name", name);
	        	childrenMap.put("state", "open");
	        	childrenMap.put("iconCls", "icon-blank");
	        	childrenMap.put("type", "1");
	        	childrenMap.put("order", menus.getMenuOrder());
	        	if(menus.getParentId().equals("0")){
	        		childrenMap.put("order", menus.getFullOrder());
	        	}
	        	if(childrenMap.containsKey(menus.getId())){
	        		childrenMap.put("checked", "true");
	        	}
	        	roleDprtTree(menus.getId(),childrenMap,menu);  
	        	menuList.add(childrenMap);  
	        }  
	    }  
	    Collections.sort(menuList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return Integer.valueOf(o1.get("order")==null?"0":o1.get("order").toString()).compareTo(Integer.valueOf(o2.get("order")==null?"0":o2.get("order").toString()));
			}
		});
	    menuMap.put("children", menuList);

    }
	/**
	 * 判断菜单是否存在子菜单
	 * @param id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="getCountMenu/{id}")                
	public int getCountMenu(@PathVariable("id") String id) throws Exception {
		return menuService.getCount(id);
	}
	/**
	 * 删除菜单
	 * @param id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="deleteMenu/{id}")
    public int deleteMenu(@PathVariable("id") String id) throws Exception {
		String id2=String.valueOf(id);
		int num = 0;
		num=menuService.deleteMenuByPrimaryKey(id2);
		return num;
	}
}
