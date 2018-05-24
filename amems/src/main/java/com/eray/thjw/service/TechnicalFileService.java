package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.QualityClose;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.TechnicalUpload;
import com.eray.thjw.po.User;

/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface TechnicalFileService {
	
	TechnicalFile selectByPrimaryKey(String id) throws BusinessException;

	List<TechnicalFile> queryAllPageList(TechnicalFile technicalFile) throws BusinessException;

	int validationQueryCount(TechnicalFile technicalFile) throws BusinessException;

	List<TechnicalFile> queryAllByDocumentsId(TechnicalFile technicalFile);

	public Map<String, Object> save(TechnicalFile technicalFile, TechnicalUpload technicalUpload) throws BusinessException;

	public TechnicalFile queryAll(TechnicalFile technicalFile) throws BusinessException;

	public Map<String, Object> modify(TechnicalFile technicalFile, TechnicalUpload technicalUpload) throws BusinessException;

	public Map<String, Object> assess(TechnicalFile technicalFile) throws BusinessException;

	public int delete(TechnicalFile technicalFile) throws Exception;

	public Map<String, Object> updateShenhe(TechnicalFile technicalFile) throws BusinessException;

	List<TechnicalFile> selectChoosePgd(String id);

	/**
	 * @author liub
	 * @description 检查维修方案修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user, String id) throws Exception;// 技术文件上传的验证

	public Map<String, Object> checkUpdMt1(User user, String id) throws Exception;// 技术文件上传的修改验证

	public Map<String, Object> checkUpdMtAssess(User user, String id) throws Exception;// 技术文件评估

	public Map<String, Object> checkUpdMtAudit(User user, String id) throws Exception;// 技术文件审核

	public Map<String, Object> checkUpdMtApproval(User user, String id) throws Exception;// 技术文件审批

	/**
	 * 
	 * 用id查询评估单需要打印数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TechnicalFile selectTechnicalFileByPrimaryKey(String id) throws Exception;
	/**
	 * sunji
	 * 查询列表
	 * @param technicalFile
	 * @return map
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAllTechnicalfile(TechnicalFile technicalFile)throws BusinessException;
	
	/**
	 * @author liub
	 * @description 质量关闭
	 * @param qualityClose
	 */
	public void qualityClose(QualityClose qualityClose) throws BusinessException;


}
