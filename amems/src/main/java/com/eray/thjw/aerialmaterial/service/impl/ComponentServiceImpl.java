package com.eray.thjw.aerialmaterial.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ComponentMapper;
import com.eray.thjw.aerialmaterial.po.Component;
import com.eray.thjw.aerialmaterial.service.ComponentService;


/**
 * @Description 部件service实现类
 * @CreateTime 2017年10月9日 下午4:54:12
 * @CreateBy 韩武
 */
@Service
public class ComponentServiceImpl implements ComponentService {
	
	@Resource
	private ComponentMapper componentMapper;

	/**
	 * @Description 根据件号和序列号查找
	 * @CreateTime 2017年10月9日 下午4:53:12
	 * @CreateBy 韩武
	 * @return
	 */
	@Override
	public Component findByJhAndXlh(Component component) {
		return componentMapper.findByJhAndXlh(component);
	}

}
