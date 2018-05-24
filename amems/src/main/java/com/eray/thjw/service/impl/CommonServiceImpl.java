package com.eray.thjw.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.service.CommonService;

/**
 * 公共业务
 * @author xu.yong
 *
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Resource
	private CommonMapper commonMapper;
	
	/**
	 * 获取数据系统当前时间
	 */
	public Date getSysdate(){
		return this.commonMapper.getSysdate();
	}
	
}
