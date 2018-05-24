package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.baseinfo.po.DirectoryStructure;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FileCatalog;

/**
 * 文档管理service
 * @author ll
 * @description 
 * @develop date 2016.07.29
 */
public interface FileCatalogService {
	
	/**
	 * 根据条件查询所有目录
	 * @param fileCatalog1 
	 * @return
	 * @throws RuntimeException
	 */
	public List<FileCatalog> findAlls(FileCatalog fileCatalog1)throws RuntimeException; 
	
	/**
	 * 根据条件查询所有目录
	 * @param fileCatalog1 
	 * @return
	 * @throws RuntimeException
	 */
	public List<FileCatalog> queryAlls(FileCatalog fileCatalog)throws RuntimeException;  
	
	/**
	 * 查询目录数量
	 * @param fileCatalog
	 * @return
	 * @throws RuntimeException
	 */
	public int queryCount(FileCatalog fileCatalog) throws RuntimeException;
	
	/**
	 * 添加目录
	 * @param fileCatalog
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> saveFile(FileCatalog fileCatalog) throws BusinessException;
	
	/**
	 * 添加导入目录
	 * @param fileCatalog
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> saveImportFile(List<DirectoryStructure> dirStructures,String upZipPath,String rootPath,Map<String, Object> resultMap,String mkdm) throws BusinessException;
	
	/**
	 * 修改目录
	 * @param fileCatalog
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> updateFile(FileCatalog fileCatalog)throws RuntimeException;
	
	/**
	 * @Description 修改父目录
	 * @CreateTime 2018-3-16 下午1:32:24
	 * @CreateBy 刘兵
	 * @param fileCatalog
	 */
	public Map<String, Object> updateFmlid(FileCatalog fileCatalog);
	
	/**
	 * 删除目录
	 * @param fileCatalog
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> deleteFolder(FileCatalog fileCatalog)throws RuntimeException;
	
	/**
	 * 验证目录唯一
	 * @param mkdm
	 * @param id
	 * @param mkdm2 
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> checkUpdMt(FileCatalog fileCatalog) throws RuntimeException;
	
	/**
	 * 验证文件夹唯一
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public Map<String, Object> checkdel(String id) throws RuntimeException;
	
	/**
	 * @Description 检查文件夹是否存在
	 * @CreateTime 2017-9-15 上午9:27:27
	 * @CreateBy 刘兵
	 * @param id 文件夹id
	 * @throws BusinessException
	 */
	public void checkExist(String id) throws BusinessException;
	/**
	 * 
	 * @Description 获取导出列表
	 * @CreateTime 2018年4月12日 下午3:42:02
	 * @CreateBy 岳彬彬
	 * @param fileCatalog
	 * @return
	 */
	List<Map<String, Object>> exportList(FileCatalog fileCatalog);
}
