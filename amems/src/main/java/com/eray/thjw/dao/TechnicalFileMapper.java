package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;

public interface TechnicalFileMapper {

	List<TechnicalFile> queryAllPageList(TechnicalFile technicalFile) throws BusinessException;

	int validationQueryCount(TechnicalFile technicalFile) throws BusinessException;

	List<TechnicalFile> queryAllByDocumentsId(TechnicalFile technicalFile);

	public void save(TechnicalFile technicalFile) throws BusinessException;

	public TechnicalFile queryAll(TechnicalFile technicalFile) throws BusinessException;

	public int modify(TechnicalFile technicalFile) throws BusinessException;

	public int assess(TechnicalFile technicalFile) throws BusinessException;

	public int delete(TechnicalFile technicalFile) throws BusinessException;

	public Map<String, Object> checkUpdMt(User user, String id) throws BusinessException;

	public Map<String, Object> checkUpdMtAssess(User user, String id) throws BusinessException;// 技术文件评估

	public Map<String, Object> checkUpdMtAudit(User user, String id) throws BusinessException;// 技术文件审核

	public Map<String, Object> checkUpdMtApproval(User user, String id) throws BusinessException;// 技术文件审批

	public List<TechnicalFile> selectChoosePgd(String id);

	TechnicalFile selectTechnicalFileByPrimaryKey(String id) throws BusinessException;
	
	TechnicalFile selectByPrimaryKey(String id);

}