package com.eray.thjw.service;

import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.TechnicalUpload;


/**
 * @author liub
 * @description 角色service,用于业务逻辑处理
 * @develop date 2016.07.29
 */
public interface TechnicalUploadService {

	 
	 public Map<String, Object> save(TechnicalUpload technicalUpload) throws BusinessException;
	 
	 public TechnicalUpload selectByManid(String mainid) throws BusinessException;
	
	 public Map<String, Object> modify(TechnicalUpload technicalUpload) throws BusinessException;
	 
	 public TechnicalUpload selectByPrimaryKey(String id) throws BusinessException;

}
