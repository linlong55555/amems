package com.eray.thjw.service;



public interface MaintenanceToBookService {
	
	
	/**
	 * @author liub
	 * @description 保存维修方案修订通知书信息
	 * @param maintenance
	 * @return 
	 * @develop date 2016.08.30
	 */
	public void saveMaintenanceToBook(String wxfaid ,String xdtzsidStr,String czls, String zdid) throws RuntimeException;

}
