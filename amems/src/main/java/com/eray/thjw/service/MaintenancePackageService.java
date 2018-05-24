package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.MaintenancePackage;
import com.eray.thjw.po.TaskInfo;


/**
 * @author ll
 * @description 
 * @develop date 2016.07.29
 */
public interface MaintenancePackageService {
	
	/**
	 * 根据条件查询文件数量
	 * @param fileCatalog
	 * @return
	 * @throws RuntimeException
	 */
	public int queryCount(FileCatalog fileCatalog) throws RuntimeException;

	/**
	 * 按条件查询一页计划任务
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaintenancePackage> queryAllPageList(MaintenancePackage maintenancePackage)  throws RuntimeException;
	 
	 /**
	  * 修改文件
	  * @param maintenancePackage
	  * @return
	  * @throws RuntimeException
	  */
	public Map<String, Object> uploadingFile(List<MaintenancePackage> maintenancePackage) throws BusinessException;

	/**
	  * 修改文件
	  * @param maintenancePackage
	  * @return
	  * @throws RuntimeException
	  */
	public Map<String, Object> updateMainid(MaintenancePackage maintenancePackage) throws BusinessException;
	
	
	/**
	 * 修改文件
	 * @param maintenancePackage
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> updtaeuploadingFile(MaintenancePackage maintenancePackage)throws BusinessException;

	/**
	 * 删除文件
	 * @param maintenancePackage
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> deleteuploadingFile(MaintenancePackage maintenancePackage)throws RuntimeException;
}
