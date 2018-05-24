package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.dao.UpMapper;


public interface OtherHCMainDataMapper extends UpMapper {
	
	public List<HCMainData> selectByIds();
	
}