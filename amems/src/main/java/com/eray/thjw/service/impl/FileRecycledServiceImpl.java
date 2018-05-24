package com.eray.thjw.service.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.FileCatalogMapper;
import com.eray.thjw.dao.FileRecycledChildrenMapper;
import com.eray.thjw.dao.FileRecycledMapper;
import com.eray.thjw.dao.MaintenancePackageMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycled;
import com.eray.thjw.po.FileRecycledChildren;
import com.eray.thjw.po.MaintenancePackage;
import com.eray.thjw.po.User;
import com.eray.thjw.service.FileRecycledService;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.DocumentTypeEnum;

/**
 * @Description 文件目录-回收站service实现类
 * @CreateTime 2018年1月26日 上午10:47:42
 * @CreateBy 韩武
 */
@Service
public class FileRecycledServiceImpl implements FileRecycledService {
	
	@Resource
	private FileRecycledMapper fileRecycledMapper;
	
	@Resource
	private FileRecycledChildrenMapper fileRecycledChildrenMapper;
	
	@Resource
	private FileCatalogMapper fileCatalogMapper;
	
	@Resource
	private MaintenancePackageMapper maintenancePackageMapper;

	/**
	 * @Description 保存回收站
	 * @CreateTime 2018年1月26日 上午10:43:09
	 * @CreateBy 韩武
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public FileRecycled save(String id, DocumentTypeEnum documentType)
			throws BusinessException {
		
		User user = ThreadVarUtil.getUser();
		FileRecycled recycled = new FileRecycled();
		recycled.setCzsj(new Date());
		recycled.setCzrid(user.getId());
		recycled.setCzbmid(user.getBmdm());
		if(DocumentTypeEnum.FOLDER.equals(documentType)){	// 文件夹
			
			FileCatalog folder = fileCatalogMapper.selectByPrimaryKey(id);
			recycled.setId(UUID.randomUUID().toString());
			recycled.setMkdm(folder.getMkdm());
			recycled.setDprtcode(folder.getDprtcode());
			recycled.setWjid(folder.getId());
			recycled.setWjlx(documentType.getCode());
			recycled.setWjmc(folder.getMlmc());
			
		} else if (DocumentTypeEnum.FILE.equals(documentType)){	// 文件
			
			MaintenancePackage file = maintenancePackageMapper.selectByPrimaryKey(id);
			recycled.setId(UUID.randomUUID().toString());
			recycled.setMkdm(file.getFolder().getMkdm());
			recycled.setDprtcode(file.getDprtcode());
			recycled.setWjid(file.getId());
			recycled.setWjlx(documentType.getCode());
			recycled.setWjmc(file.getYswjm());
			recycled.setHzm(file.getHzm());
		}
		
		// 保存至回收站
		fileRecycledMapper.insertSelective(recycled);
		
		return recycled;
	}

	/**
	 * @Description 保存回收站字表
	 * @CreateTime 2018年1月29日 上午11:28:14
	 * @CreateBy 韩武
	 * @param children
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public int saveChildren(List<FileRecycledChildren> children)
			throws BusinessException {
		return fileRecycledChildrenMapper.batchInsert(children);
	}

	/**
	 * @Description 查询回收站列表
	 * @CreateTime 2018年1月29日 下午2:07:16
	 * @CreateBy 韩武
	 * @param file
	 * @return
	 */
	@Override
	public List<FileRecycled> queryRecycledList(FileCatalog file) {
		return fileRecycledMapper.queryRecycledList(file);
	}

	/**
	 * @Description 回收站还原
	 * @CreateTime 2018年1月29日 下午4:15:13
	 * @CreateBy 韩武
	 * @param list
	 */
	@Override
	public void doResotre(List<FileRecycled> list) {
		for (FileRecycled recycled : list) {
			
			// 1.还原节点上至根节点的目录数据到有效状态
			restoreParentNodes(recycled);
			
			// 2.根据回收站子表的内容还原附件和目录
			restoreChildrenNodes(recycled);
			
			// 3.删除回收站数据（主表和子表）
			deleteRecycledData(recycled.getId());
		}
	}
	
	/**
	 * @Description 删除回收站数据
	 * @CreateTime 2018年1月30日 上午11:18:17
	 * @CreateBy 韩武
	 * @param id
	 */
	private void deleteRecycledData(String id){
		// 删除回收站数据
		fileRecycledMapper.deleteByPrimaryKey(id);
		// 删除回收站子表数据
		fileRecycledChildrenMapper.deleteByMainid(id);
	}
	
	/**
	 * @Description 还原节点上至根节点的目录数据到有效状态
	 * @CreateTime 2018年1月29日 下午4:26:07
	 * @CreateBy 韩武
	 * @param FileRecycled
	 */
	private void restoreParentNodes(FileRecycled recycled){
		
		recycled = fileRecycledMapper.selectByPrimaryKey(recycled.getId());
		
		if(DocumentTypeEnum.FOLDER.getCode().equals(recycled.getWjlx())){	// 文件夹
			
			// 还原节点上至根节点的目录数据到有效状态
			fileCatalogMapper.restoreParentNodes(recycled.getWjid());
			
		} else if (DocumentTypeEnum.FILE.getCode().equals(recycled.getWjlx())){	// 文件
			
			// 获取文件对应的文件夹
			MaintenancePackage file = maintenancePackageMapper.selectByPrimaryKey(recycled.getWjid());
			// 还原节点上至根节点的目录数据到有效状态
			fileCatalogMapper.restoreParentNodes(file.getFolder().getId());
		}
	}
	
	/**
	 * @Description 根据回收站子表的内容还原附件和目录
	 * @CreateTime 2018年1月29日 下午4:42:54
	 * @CreateBy 韩武
	 * @param fileRecycled
	 */
	private void restoreChildrenNodes(FileRecycled recycled){
		
		// 还原子节点文件夹
		fileCatalogMapper.restoreChildrenNodes(recycled.getId());
		// 还原子节点文件
		maintenancePackageMapper.restoreChildrenNodes(recycled.getId());
	}

	/**
	 * @Description 回收站确认删除
	 * @CreateTime 2018年1月29日 下午4:15:13
	 * @CreateBy 韩武
	 * @param list
	 */
	@Override
	public void doConfirmDelete(List<FileRecycled> list) {
		for (FileRecycled recycled : list) {
			
			// 1.获取需要删除的节点（根据mainid查询回收站子表且在文件和目录表的状态=0，且过滤回收站其他的数据的父节点集合和子节点集合）
			List<FileRecycledChildren> children = fileRecycledChildrenMapper.findInvalidDataByMainid(recycled.getId());
			
			// 2.物理删除（物理删除目录表和附件表，以及附件磁盘文件）
			physicalDeleting(children);
			
			// 3.删除回收站和回收站子表数据
			deleteRecycledData(recycled.getId());
		}
	}
	
	/**
	 * @Description 物理删除
	 * @CreateTime 2018年1月30日 上午10:59:49
	 * @CreateBy 韩武
	 * @param list
	 */
	private void physicalDeleting(List<FileRecycledChildren> list){
		
		for (FileRecycledChildren child : list) {
			
			if(DocumentTypeEnum.FOLDER.getCode().equals(child.getWjlx())){	// 文件夹
				
				// 物理删除目录表
				fileCatalogMapper.physicalDeleteById(child.getWjid());
				
			} else if (DocumentTypeEnum.FILE.getCode().equals(child.getWjlx())){	// 文件
				
				// 获取附件对象
				MaintenancePackage file = maintenancePackageMapper.selectByPrimaryKey(child.getWjid());
				// 删除磁盘文件
				File_Util.delFile(file.getCflj(), file.getNbwjm());
				// 物理删除附件表
				maintenancePackageMapper.physicalDeleteById(child.getWjid());
			}
		}
	}

	/**
	 * @Description 清空回收站
	 * @CreateTime 2018年1月30日 下午1:44:56
	 * @CreateBy 韩武
	 * @param file
	 */
	@Override
	public void doEmpty(FileCatalog file) {
		
		// 1.获取需要删除的节点（根据模块代码、组织机构查询回收站子表且在文件和目录表的状态=0）
		List<FileRecycledChildren> children = fileRecycledChildrenMapper.findInvalidDataByMkdm(file);
		
		// 2.物理删除（物理删除目录表和附件表，以及附件磁盘文件）
		physicalDeleting(children);
		
		// 3.清空回收站、回收站子表数据
		truncateRecycled(file);
	}
	
	/**
	 * @Description 清空回收站
	 * @CreateTime 2018年1月30日 下午2:02:08
	 * @CreateBy 韩武
	 * @param file
	 */
	private void truncateRecycled(FileCatalog file){
		// 清空回收站
		fileRecycledMapper.deleteByMkdm(file);
		// 清空回收站子表
		fileRecycledChildrenMapper.deleteByMkdm(file);
	}

}
