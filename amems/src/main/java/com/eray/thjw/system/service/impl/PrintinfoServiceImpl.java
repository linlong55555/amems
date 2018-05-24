package com.eray.thjw.system.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.system.dao.PrintinfoMapper;
import com.eray.thjw.system.po.Printinfo;
import com.eray.thjw.system.service.PrintinfoService;
import com.eray.thjw.util.ThreadVarUtil;


/**
 * 
 * @Description 系统打印
 * @CreateTime 2017年12月27日 上午11:47:02
 * @CreateBy 岳彬彬
 */
@Service(value="printinfoService")
public class PrintinfoServiceImpl implements PrintinfoService{
	
	@Resource
	private PrintinfoMapper printinfoMapper;
	
	/**
	 * 
	 * @Description 新增打印记录
	 * @CreateTime 2017年12月27日 上午11:52:10
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	@Override
	public int addPrintCount(Printinfo record) {
		User user = ThreadVarUtil.getUser();
		record.setId(UUID.randomUUID().toString());
		record.setCzrid(user.getId());
		record.setCzip("0:0:0:0:0:0:0:1".equals(ThreadVarUtil.getClientIp())?"127.0.0.1":ThreadVarUtil.getClientIp());
		record.setCasj(new Date());
		return printinfoMapper.insertSelective(record);
	}
	
	
}
