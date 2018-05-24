package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.MaintenancePackage;

public interface MaintenancePackageMapper {
	public int queryCount(FileCatalog fileCatalog) throws RuntimeException;
	
	/**
	 * 按条件查询一页计划任务
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaintenancePackage> queryAllPageList(MaintenancePackage maintenancePackage)  throws RuntimeException;
	 
	 List<MaintenancePackage> queryListByMW(MaintenancePackage maintenancePackage);
	 
	 List<MaintenancePackage> queryListByMWList(MaintenancePackage maintenancePackage);
	 
	 public void saveMaintenancePackage(MaintenancePackage maintenancePackage)throws RuntimeException;
	 
	 public void updateMaintenancePackage(MaintenancePackage maintenancePackage)throws RuntimeException;
	 
	 public void deleteMaintenancePackage(MaintenancePackage maintenancePackage)throws RuntimeException;
	 
	 /**
	  * @Description 根据id删除
	  * @CreateTime 2018年1月29日 上午11:23:38
	  * @CreateBy 韩武
	  * @param id
	  * @return
	  */
	 int deleteById(String id);
	 
	 /**
	  * @Description 根据id查询
	  * @CreateTime 2018年1月29日 上午11:40:29
	  * @CreateBy 韩武
	  * @param id
	  * @return
	  */
	 MaintenancePackage selectByPrimaryKey(String id);
	 
	 /**
	  * @Description 根据回收站子表的内容还原附件
	  * @CreateTime 2018年1月29日 下午4:52:06
	  * @CreateBy 韩武
	  * @param id
	  * @return
	  */
	 int restoreChildrenNodes(String id);
	 
	 /**
	  * @Description 根据id物理删除
	  * @CreateTime 2018年1月30日 上午11:05:48
	  * @CreateBy 韩武
	  * @param id
	  * @return
	  */
	 int physicalDeleteById(String id);
	 
	 int updateMainid(MaintenancePackage maintenancePackage);
}