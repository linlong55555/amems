package com.eray.thjw.dao;

import java.util.List;
import com.eray.thjw.po.Menu;

public interface MenuMapper {
	// 增加一个菜单
	public Integer addMenu(Menu menu);
	// 根据查询条件查询菜单信息
	public List<Menu> selectMenuList(Menu menu);
	// 查询 一个菜单信息
	public Menu selectMenu(Menu menu);
	// 修改一个菜单信息
	public int updateMenu(Menu menu);
	// 删除一个菜单信息
	public int deleteMenuByPrimaryKey(String id);
	// 查询总记录数
	public int queryCount(Menu menu);
	
	// 查询所有
	public List<Menu> findAll();
	// 根据条件查询所有
	public List<Menu> findAllparentId(String parentId);
	// 获取用户拥有的权限菜单
	public List<Menu> findMenuByUserId(String userId);

	public int getCount(String id); // 查询菜单下是否有子菜单

	public List<Menu> findAlls(Menu menu);

	public List<Menu> findMenuByUserId4Login(String id, String aPP_NAME);

	public List<Menu> findAllMenuList4Login(String aPP_NAME);

}
