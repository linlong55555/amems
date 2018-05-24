package com.eray.thjw.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.dao.SynRelMapper;
import com.eray.thjw.system.po.SynRel;
import com.eray.thjw.system.service.SynRelService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/** 
 * @Description 系统同步关系服务实现
 * @CreateTime 2017年9月25日 上午11:27:53
 * @CreateBy 朱超	
 */
public @Service class SynRelServiceImpl implements SynRelService {

	@Resource private SynRelMapper synRelMapper;
	
	@Override
	public Map<String, Object> queryPage(SynRel record) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageHelper.startPage(record.getPagination());
		List<SynRel> list = synRelMapper.selectList(record);
		resultMap = PageUtil.pack4PageHelper(list, record.getPagination());
		return resultMap;
	}

	@Override
	public void save(SynRel record) {
		//有ID更新，无ID新建一条数据
		if (StringUtils.isNotBlank(record.getId())) {
			synRelMapper.updateByPrimaryKeySelective(record);
		}
		else {
			String id = UUID.randomUUID().toString();
			record.setId(id);
			synRelMapper.insert(record);
		}
	}

	@Override
	public SynRel load(String id) {
		SynRel bean = synRelMapper.selectByPrimaryKey(id);
		return bean;
	}

	@Override
	public void dels(BaseEntity baseEntity) {
		Object idsStr = baseEntity.getParamsMap().get("ids");
		List<String>ids = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(idsStr.toString(), ",");  
		while(st.hasMoreElements()){  
			ids.add(st.nextToken());
		}  
		baseEntity.getParamsMap().put("ids", ids);
		synRelMapper.deletes(baseEntity);
	}

}
