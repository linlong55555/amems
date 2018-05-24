package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixedCheckWorkContent;


public interface FixedCheckWorkContentService {
	
	/**
	 * @author liub
	 * @description 根据定检项目id工作内容数据
	 * @param djxmid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<Map<String, Object>> queryListByDjxmid(String djxmid) throws RuntimeException;
	
	
	/**
	 * @author liub
	 * @description 新增定检工作内容集合
	 * @param fixedCheckWorkContentList , 定检编号djbh
	 * @develop date 2016.08.24
	 */
	public void addWorkConetenList(List<FixedCheckWorkContent> fixedCheckWorkContentList , String djxmid, String czls, String zdid, String dprtcode) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 修改检工作内容集合
	 * @param fixedCheckWorkContentList , 定检编号djbh
	 * @develop date 2016.08.29
	 */
	public void editWorkConetenList(List<FixedCheckWorkContent> fixedCheckWorkContentList, String djxmid,String czls,String zdid, String dprtcode) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 根据定检编号删除工作内容
	 * @param 定检编号djbh
	 * @develop date 2016.08.24
	 */
	public void deleteByDjxmid(String djxmid) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 根据条件分页查询差异列表
	 * @param baseEntity
	 * @return List<Map<String, Object>> 
	 * @develop date 2017.02.09
	 */
	public List<Map<String, Object>> queryDiffPageList(BaseEntity baseEntity) throws RuntimeException;
	
}
