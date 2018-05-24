package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.exception.BusinessException;

public interface InspectionService {
	
	 int deleteByPrimaryKey(String id);

	 int insert(Inspection record);

	 int insertSelective(Inspection record);

	 Inspection selectByPrimaryKey(String id);
	 
	 Inspection getById(String id);

	 int updateByPrimaryKeySelective(Inspection record) throws Exception;

	 int updateByPrimaryKey(Inspection record);
	 
	 List<Inspection> selectInspectionList(Inspection record);

	 /**
	  * 
	  * @Description 航材工具检验列表
	  * @CreateTime 2018年3月26日 上午10:26:50
	  * @CreateBy 林龙
	  * @param inspection
	  * @return
	  * @throws Exception
	  */
	 Map<String, Object> queryAllPageList(Inspection inspection)throws Exception;

	 /**
	  * 
	  * @Description 查询航材工具检验
	  * @CreateTime 2018年3月26日 下午5:10:19
	  * @CreateBy 林龙
	  * @param inspection
	  * @return
	  * @throws Exception
	  */
	Inspection getByInspectionId(Inspection inspection)throws Exception;

	/**
	 * 
	 * @Description 保存航材工具检验
	 * @CreateTime 2018年3月27日 下午1:57:15
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	int updateByPrimary(Inspection inspection)throws BusinessException;

	/**
	 * 
	 * @Description 提交航材工具检验
	 * @CreateTime 2018年3月27日 下午2:11:54
	 * @CreateBy 林龙
	 * @param inspection
	 * @return
	 * @throws BusinessException
	 */
	int updateByPrimarySubmit(Inspection inspection)throws BusinessException;
	
}
