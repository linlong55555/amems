package com.eray.thjw.service.impl;

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.dao.ButtonMapper;
import com.eray.thjw.po.Button;
import com.eray.thjw.service.ButtonService;
/**
 * 按钮操作的接口实现类
 * @author meizhiliang
 */
@Service
public class ButtonServiceImpl implements ButtonService {

	@Resource
	private ButtonMapper buttonMapper;

	public String addButton(Button button) {
		String id = UUID.randomUUID().toString();
		button.setId(id);
		buttonMapper.addButton(button);
		return id;
	}

	public List<Button> selectButtonList(Button button) {
		return buttonMapper.selectButtonList(button);
	}

	public int updateButton(Button button) {
		return buttonMapper.updateButton(button);
	}

	public int deleteButtonByPrimaryKey(String id) {
		return buttonMapper.deleteButtonByPrimaryKey(id);
	}

	// 获取用户拥有的按钮权限
	@Override
	public List<Button> findButtonByUserId(String userId) {
		return buttonMapper.findButtonByUserId(userId);
	}

	// 获取所有按钮权限
	@Override
	public List<Button> findAllButtonList() {
		return buttonMapper.findAllButtonList();
	}

	@Override
	public Button selectButton(Button button) {
		return buttonMapper.selectButton(button);
	}

	@Override
	public int selectButtonCount(String buttonCode) {
		return buttonMapper.selectButtonCount(buttonCode);
	}

	@Override
	public List<Button> findButtonByUserId4Login(String id) {
		return buttonMapper.findButtonByUserId4Login(id, SysContext.APP_NAME);
	}

	@Override
	public List<Button> findAllButtonList4Login() {
		return buttonMapper.findAllButtonList4Login(SysContext.APP_NAME);
	}

}
