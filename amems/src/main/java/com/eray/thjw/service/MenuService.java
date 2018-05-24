package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.Menu;

/**
 * 菜单操作的接口
 * @author meizhiliang
 */
public interface MenuService {
	/**
	 * @author meizhiliang
	 * 新增按钮操作
	 * @param menu
	 * @return String
	 */
	public String addMenu(Menu menu);
	/**
	 * @author meizhiliang
	 * 新增按钮操作
	 * @param menu
	 * @return List<Menu>
	 */
	public List<Menu> selectMenuList(Menu menu);
	/**
	 * @author meizhiliang
	 * 查询一个菜单
	 * @param menu
	 * @return Menu
	 */
	public Menu selectMenu(Menu menu);
	/**
	 * @author meizhiliang
	 * 修改菜单的信息
	 * @param menu
	 * @return int
	 */
	public int updateMenu(Menu menu);
	/**
	 * @author meizhiliang
	 * 删除 一个菜单
	 * @param menu
	 * @return int
	 */
	public int deleteMenuByPrimaryKey(String id);

	// 查询总记录数
	public int queryC(Menu menu);

	// 查询所有
	public List<Menu> findAll();

	// 根据条件查询所有
	public List<Menu> findAllparentId(String parentId);

	// 获取用户拥有的权限菜单
	public List<Menu> findMenuByUserId(String userId);

	public int getCount(String id); // 查询角色菜单关联表的记录

	public List<Menu> findAlls(Menu menu);

	public List<Menu> findMenuByUserId4Login(String id);

	public List<Menu> findAllMenuList4Login();

}
