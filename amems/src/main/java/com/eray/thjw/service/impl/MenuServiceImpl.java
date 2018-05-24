package com.eray.thjw.service.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.dao.MenuMapper;
import com.eray.thjw.po.Menu;
import com.eray.thjw.service.MenuService;

import javax.annotation.Resource;
/**
 * 菜单操作实现
 * @author meizhiliang
 */
@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuMapper manuMapper;
    /**
     * 菜单新增
     */
	public String addMenu(Menu menu) {
		String parentid = menu.getParentId();
		if (parentid == null || "".equals(parentid)) {
			menu.setParentId("0");
		}
		String id = UUID.randomUUID().toString();
		menu.setId(id);
		manuMapper.addMenu(menu); 
		return id;
	}
    /**
     * 根据条件查询菜单 没有条件表示查询所有
     */
	public List<Menu> selectMenuList(Menu menu) {
		return manuMapper.selectMenuList(menu); 
	}
    /**
     * 修改菜单信息
     */
	public int updateMenu(Menu menu) {
		return manuMapper.updateMenu(menu); 
	}
    /**
     * 删除一个菜单信息
     */
	public int deleteMenuByPrimaryKey(String id) { 
		return manuMapper.deleteMenuByPrimaryKey(id);
	}
    /**
     * 查询菜单记录数
     */
	@Override
	public int queryC(Menu menu) {
		return manuMapper.queryCount(menu);
	}
    /**
    * 查询所有菜单
    */
	@Override
	public List<Menu> findAll() {
		return manuMapper.findAll();
	}
   /**
    * 查询某一个菜单
    */
	@Override
	public Menu selectMenu(Menu menu) {
		return manuMapper.selectMenu(menu);
	}

	/**
	 *  获取用户拥有的权限菜单
	 */
	@Override
	public List<Menu> findMenuByUserId(String userId) {
		return manuMapper.findMenuByUserId(userId);
	}

	@Override
	public int getCount(String id) {
		return manuMapper.getCount(id);
	}

	@Override
	public List<Menu> findAllparentId(String parentId) {
		return manuMapper.findAllparentId(parentId);
	}

	@Override
	public List<Menu> findAlls(Menu menu) {
		return manuMapper.findAlls(menu);
	}

	@Override
	public List<Menu> findMenuByUserId4Login(String id) {

		return manuMapper.findMenuByUserId4Login(id, SysContext.APP_NAME);
	}

	@Override
	public List<Menu> findAllMenuList4Login() {
		return manuMapper.findAllMenuList4Login(SysContext.APP_NAME);
	}

}
