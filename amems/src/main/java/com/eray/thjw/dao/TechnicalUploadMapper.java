package com.eray.thjw.dao;


import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.TechnicalUpload;

public interface TechnicalUploadMapper {
	 
	 public void save(TechnicalUpload technicalUpload) throws BusinessException;
	 
	 public TechnicalUpload selectByManid(String mainid) throws BusinessException;

	 public Map<String, Object> modify(TechnicalUpload technicalUpload) throws BusinessException;
	 
	 public TechnicalUpload selectByPrimaryKey(String id) throws BusinessException;
}