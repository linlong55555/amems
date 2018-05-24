package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.QualityCheck;

public interface QualityCheckService {

	/**
	 * @author meizhiliang
	 * @description 分页查询航材检验单
	 * @develop date 2016.11.2
    */
	List<QualityCheck>  selectQualityCheckList(QualityCheck record);    
	       
	/**
	 * @author meizhiliang
	 * @description 查询某个航材检验单
	 * @develop date 2016.11.2
    */
	QualityCheck selectByPrimaryKey(String id);   
	/**
	 * @author meizhiliang
	 * @description 检验航材操作
	 * @develop date 2016.11.9
    */
	int updateByPrimaryKeySelective(QualityCheck record);                   //执行航材检验操作
	
	void save(QualityCheck qualityCheck);
	
}
