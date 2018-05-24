package com.eray.thjw.service;

import java.util.List;
import com.eray.thjw.po.Button;

/**
 * 按钮操作接口类
 * @author meizhiliang
 */
public interface ButtonService {
	/**
	 * @author meizhiliang
	 * 新增按钮操作
	 * @param button
	 * @return String
	 */
	public String addButton(Button button);
	/**
	 * @author meizhiliang
	 * 查询按钮集合
	 * @param List<Button>
	 * @return String
	 */
	public List<Button> selectButtonList(Button button);
	/**
	 * @author meizhiliang
	 * 查询一个按钮
	 * @param button
	 * @return button
	 */
	public Button selectButton(Button button);
	/**
	 * @author meizhiliang
	 * 修改按钮信息
	 * @param button
	 * @return int
	 */
	public int updateButton(Button button);
	/**
	 * @author meizhiliang
	 * 删除一个按钮信息
	 * @param button
	 * @return int
	 */
	public int deleteButtonByPrimaryKey(String id);

	
	// 获取用户拥有的按钮权限
	public List<Button> findButtonByUserId(String userId);

	// 获取所有按钮权限
	public List<Button> findAllButtonList();

	// 验证按钮是否重复
	int selectButtonCount(String buttonCode);

	public List<Button> findButtonByUserId4Login(String id);

	public List<Button> findAllButtonList4Login();
}
