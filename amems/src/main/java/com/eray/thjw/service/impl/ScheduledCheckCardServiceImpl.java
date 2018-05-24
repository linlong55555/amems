package com.eray.thjw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.ScheduledCheckCardMapper;
import com.eray.thjw.po.ScheduledCheckCard;
import com.eray.thjw.service.ScheduledCheckCardService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * 定检工卡服务实现
 * @author zhuchao
 *
 */
@Service
public class ScheduledCheckCardServiceImpl implements ScheduledCheckCardService {

	@Resource
	private ScheduledCheckCardMapper scheduledCheckCardMapper;
	
	@Override
	public Map<String, Object> queryPage(ScheduledCheckCard card) throws RuntimeException {
		PageHelper.startPage(card.getPagination());
		List<ScheduledCheckCard> list = scheduledCheckCardMapper.queryPage(card);
		return PageUtil.pack4PageHelper(list, card.getPagination());

	}

	@Override
	public void edit(ScheduledCheckCard card) throws RuntimeException {
		scheduledCheckCardMapper.updateByPrimaryKeySelective(card);
	}

	@Override
	public void add(ScheduledCheckCard card) throws RuntimeException {
		scheduledCheckCardMapper.insertSelective(card);
	}

	@Override
	public ScheduledCheckCard load(String id) throws RuntimeException {
		return scheduledCheckCardMapper.selectByPrimaryKey(id);
	}

}
