package com.eray.thjw.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.eray.thjw.system.dao.DictMapper;
import com.eray.thjw.system.po.Dict;
import com.eray.thjw.system.service.DictService;

@Service
public class DictServiceImpl implements DictService {
    @Resource
    private DictMapper newDicItemMapper;
	@Override
	public List<Dict> getNewDicListByDprtcode(Dict newdic) {
		return newDicItemMapper.getNewDicListByDprtcode(newdic);
	}
}
