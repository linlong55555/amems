package com.eray.thjw.dao;

import java.util.List;
import com.eray.thjw.po.Button;

public interface ButtonMapper {
	// 增加一个按钮
	public Integer addButton(Button button);
	// 查询按钮集合
	public List<Button> selectButtonList(Button button);
	// 查询 一个按钮信息
	public Button selectButton(Button button);
	// 修改一个按钮信息
	public int updateButton(Button button);
	// 删除一个按钮信息
	public int deleteButtonByPrimaryKey(String id);

	
	// 获取用户拥有的按钮权限
	public List<Button> findButtonByUserId(String userId);

	// 获取所有按钮权限
	public List<Button> findAllButtonList();

	// 验证按钮是否重复
	int selectButtonCount(String buttonCode);

	public List<Button> findButtonByUserId4Login(String id, String aPP_NAME);

	public List<Button> findAllButtonList4Login(String aPP_NAME);

}
