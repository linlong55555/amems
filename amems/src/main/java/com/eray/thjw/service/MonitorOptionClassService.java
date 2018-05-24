package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.MonitorOptionClass;
import com.eray.thjw.po.MonitorOptionItem;

/**
 * 监控分类服务
 * @author zhuchao
 *
 */
public interface MonitorOptionClassService {

	 
	public List<MonitorOptionClass> queryAll();
	
	public List<MonitorOptionItem> findOptionAll();
}
