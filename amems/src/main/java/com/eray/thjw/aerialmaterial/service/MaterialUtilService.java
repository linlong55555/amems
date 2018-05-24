package com.eray.thjw.aerialmaterial.service;

/**
 * 航材公共工具服务
 * @author xu.yong
 *
 */
public interface MaterialUtilService {

	/**
	 * 验证序列号唯一性
	 * @param dprtcode
	 * @param bjh
	 * @param sn
	 * @return
	 */
	public boolean validateSnUniqueness (String dprtcode, String bjh, String sn);
}
