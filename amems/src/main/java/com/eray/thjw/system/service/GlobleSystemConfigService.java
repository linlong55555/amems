package com.eray.thjw.system.service;

import java.util.List;

import com.eray.thjw.system.po.GlobleSystemConfig;

public interface GlobleSystemConfigService {
	
	List<GlobleSystemConfig> getBySyscode(String syscode);
	
	void updateBySyscode(List<GlobleSystemConfig> list);
	
	/**
	 * 初始化：加载全局设置信息
	 * @author xu.yong
	 */
	public void initGlobalSettings();
}
