package com.eray.thjw.service;

import java.util.Map;

import com.eray.thjw.po.ScheduledCheckCard;

/**
 * 定检工卡服务接口
 * @author zhuchao
 *
 */
public interface ScheduledCheckCardService {
	
	/**
	 * 查询一页定检工卡
	 * @param card
	 * @return
	 */
	public Map<String, Object> queryPage(ScheduledCheckCard card) throws RuntimeException;

	/**
	 * 修改定检工卡
	 * @param card
	 */
	public void edit(ScheduledCheckCard card) throws RuntimeException;

	/**
	 * 添加定检工卡
	 * @param card
	 */
	public void add(ScheduledCheckCard card) throws RuntimeException;

	/**
	 * 加载一个定检工卡
	 * @param id
	 * @return ScheduledCheckCard
	 */
	public ScheduledCheckCard load(String id) throws RuntimeException;

}
