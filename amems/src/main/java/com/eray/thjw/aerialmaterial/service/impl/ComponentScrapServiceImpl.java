package com.eray.thjw.aerialmaterial.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eray.thjw.aerialmaterial.dao.ComponentScrapMapper;
import com.eray.thjw.aerialmaterial.service.ComponentScrapService;


@Service
public class ComponentScrapServiceImpl implements ComponentScrapService {
    @Resource
    private ComponentScrapMapper componentScrapMapper;


	@Override
	public int insertComponentScrap(String bjh, String sn, String dprtcode) {
		return componentScrapMapper.insertComponentScrap(bjh, sn, dprtcode);
	}

	@Override
	public int deleteComponentScrap(String bjh, String sn, String dprtcode) {
		return componentScrapMapper.deleteComponentScrap(bjh, sn, dprtcode);
	}

}
