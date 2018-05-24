package com.eray.thjw.system.service;

import com.eray.thjw.system.po.Printinfo;
/**
 * 
 * @Description 系统打印
 * @CreateTime 2017年12月27日 上午11:44:57
 * @CreateBy 岳彬彬
 */
public interface PrintinfoService {
	/**
	 * 
	 * @Description 新增打印记录
	 * @CreateTime 2017年12月27日 上午11:52:10
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	int addPrintCount(Printinfo record);
}
