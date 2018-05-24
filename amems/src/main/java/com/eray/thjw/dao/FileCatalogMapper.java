package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.FileCatalog;

public interface FileCatalogMapper {
	
	/**
	 * 查询所有目录
	 * @param fileCatalog1 
	 * @return
	 * @throws RuntimeException
	 */
	public List<FileCatalog> findAlls(FileCatalog fileCatalog1)throws RuntimeException; 
	
	/**
	 * 查询所有目录
	 * @param fileCatalog1 
	 * @return
	 * @throws RuntimeException
	 */
	public List<FileCatalog> queryAlls(FileCatalog fileCatalog)throws RuntimeException; 
	
	public int queryCount(FileCatalog fileCatalog) throws RuntimeException;
	
	public void saveFileCatalog(FileCatalog fileCatalog);
	
	public void updateFileCatalog(FileCatalog fileCatalog);
	
	public void deleteFileCatalog(FileCatalog fileCatalog);
	
	/**
	 * @Description 修改父目录
	 * @CreateTime 2018-3-16 下午1:32:24
	 * @CreateBy 刘兵
	 * @param fileCatalog
	 */
	public void updateFmlid(FileCatalog fileCatalog);
	
	
	/**
	 * @Description 根据主键查询目录
	 * @CreateTime 2017-9-15 上午9:19:37
	 * @CreateBy 刘兵
	 * @param id 主键id
	 * @return
	 */
	FileCatalog selectByPrimaryKey(String id);
	
	/**
	 * @Description 获取某个目录下的所有子目录
	 * @CreateTime 2018年1月26日 上午11:20:09
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	List<FileCatalog> getChildren(String id);
	
	/**
	 * @Description 根据id删除
	 * @CreateTime 2018年1月29日 上午11:19:11
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	int deleteById(String id);
	
	/**
	 * @Description 还原节点上至根节点的目录数据到有效状态
	 * @CreateTime 2018年1月29日 下午4:30:39
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	int restoreParentNodes(String id);
	
	/**
	 * @Description 根据回收站子表的内容还原目录
	 * @CreateTime 2018年1月29日 下午4:45:22
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	int restoreChildrenNodes(String id);
	
	/**
	 * @Description 根据id物理删除
	 * @CreateTime 2018年1月30日 上午11:02:38
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	int physicalDeleteById(String id);
	
	int vilide4FolderExixts(FileCatalog fileCatalog);
}