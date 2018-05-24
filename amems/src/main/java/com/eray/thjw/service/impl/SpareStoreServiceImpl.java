package com.eray.thjw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.SpareStoreMapper;
import com.eray.thjw.po.SpareStore;
import com.eray.thjw.service.SpareStoreService;

@Service
public class SpareStoreServiceImpl implements SpareStoreService {
   
	@Resource
	private SpareStoreMapper spareStoreMapper;
	
	@Override
	public List<SpareStore> selectSpareStoreList(SpareStore ss)throws RuntimeException {
		return spareStoreMapper.selectSpareStoreList(ss);
	}
}
