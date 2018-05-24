package com.eray.thjw.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.TechnicalUploadMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.TechnicalUpload;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.TechnicalUploadService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class TechnicalUploadServiceImpl implements TechnicalUploadService {

	@Autowired
	private TechnicalUploadMapper technicalUploadMapper;
	
	private static final Logger logger = LoggerFactory.getLogger(TechnicalUploadServiceImpl.class);
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Autowired
	private CommonRecService commonRecService;
	
	@Override
	public Map<String, Object> save(TechnicalUpload technicalUpload)
			throws BusinessException {
		if (technicalUpload == null) {
			throw new BusinessException("操作失败");
		}
		
		try {
			User user = ThreadVarUtil.getUser();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			technicalUpload.setId(uuid.toString());
			technicalUploadMapper.save(technicalUpload);
			commonRecService.write(uuid.toString(), TableEnum.B_G_00101, user.getId(), technicalUpload.getCzls(), technicalUpload.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,technicalUpload.getTechnicalfileId());
			
			resultMap.put("state", "Success");
		} catch (BusinessException e) {
			resultMap.put("state", "Failure");
			throw new BusinessException("操作失败",e);
		}
		finally{
		}
		return resultMap;
	}

	@Override
	public TechnicalUpload selectByManid(String mainid) throws BusinessException {
		try {
			return technicalUploadMapper.selectByManid(mainid);
			
		} catch (BusinessException e) {
			throw new BusinessException("操作失败");
		}
	}

	@Override
	public Map<String, Object> modify(TechnicalUpload technicalUpload)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		try {
			 Map<String, Object> map=technicalUploadMapper.modify(technicalUpload);
			commonRecService.write(technicalUpload.getId(), TableEnum.B_G_00101, user.getId(), technicalUpload.getCzls(), technicalUpload.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,technicalUpload.getTechnicalfileId());
			return map;
			
		} catch (BusinessException e) {
			throw new BusinessException("操作失败",e);
		}
	}

	@Override
	public TechnicalUpload selectByPrimaryKey(String id) throws BusinessException {
		return technicalUploadMapper.selectByPrimaryKey(id);
	}
	

}
