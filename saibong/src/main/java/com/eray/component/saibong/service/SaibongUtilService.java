package com.eray.component.saibong.service;

import com.eray.component.saibong.exception.SaibongException;

public interface SaibongUtilService{
	/**
	 * 生成采蕃号
	 * @param dprtcode 组织机构代码
	 * @param key 采蕃Key
	 * @return
	 * @throws SaibongException 
	 */
	public String generate(String dprtcode, String key) throws SaibongException;

	/**
	 * 生成采蕃号（带加权值）
	 * @param dprtcode 组织机构代码
	 * @param key 采蕃Key
	 * @param weight 加权值
	 * @return
	 * @throws SaibongException 
	 */
	public String generate(String dprtcode, String key, String weight) throws SaibongException;
}