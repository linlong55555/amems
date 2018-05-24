package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.system.po.GlobleSystemConfig;

public interface GlobleSystemConfigMapper {
	
	List<GlobleSystemConfig> getBySyscode(String syscode);
	
	void updateBySyscode(GlobleSystemConfig config);
	
	void deleteJglx(GlobleSystemConfig config);
	
	void insertJglx(GlobleSystemConfig config);
	
}