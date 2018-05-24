package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycled;
import com.eray.thjw.po.FileRecycledChildren;

import enu.common.DocumentTypeEnum;

/**
 * @Description 文件目录-回收站service
 * @CreateTime 2018年1月26日 上午10:42:12
 * @CreateBy 韩武
 */
public interface FileRecycledService {
	
	/**
	 * @Description 保存回收站
	 * @CreateTime 2018年1月26日 上午10:43:09
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	FileRecycled save(String id, DocumentTypeEnum documentType) throws BusinessException;
	
	/**
	 * @Description 保存回收站字表
	 * @CreateTime 2018年1月29日 上午11:28:14
	 * @CreateBy 韩武
	 * @param children
	 * @return
	 * @throws BusinessException
	 */
	int saveChildren(List<FileRecycledChildren> children) throws BusinessException;
	
	/**
	 * @Description 查询回收站列表
	 * @CreateTime 2018年1月29日 下午2:07:16
	 * @CreateBy 韩武
	 * @param file
	 * @return
	 */
	List<FileRecycled> queryRecycledList(FileCatalog file);
	
	/**
	 * @Description 回收站还原
	 * @CreateTime 2018年1月29日 下午4:15:13
	 * @CreateBy 韩武
	 * @param list
	 */
	void doResotre(List<FileRecycled> list);
	
	/**
	 * @Description 回收站确认删除
	 * @CreateTime 2018年1月29日 下午4:15:13
	 * @CreateBy 韩武
	 * @param list
	 */
	void doConfirmDelete(List<FileRecycled> list);
	
	/**
	 * @Description 清空回收站
	 * @CreateTime 2018年1月30日 下午1:44:56
	 * @CreateBy 韩武
	 * @param file
	 */
	void doEmpty(FileCatalog file);
}
