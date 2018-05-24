package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.QualityCheck;
public interface QualityCheckMapper {
    
	
	List<QualityCheck>  selectQualityCheckList(QualityCheck record);    //分页查询航材检验单
	
	QualityCheck selectByPrimaryKey(String id);                                     //查询某个航材检验单
	
	int updateByPrimaryKeySelective(QualityCheck record);                   //执行航材检验操作
	
	/**
	 * 更新合同提定关系数量
	 * @author meizhiliang
	 * @description 
	 * @develop date 2016.11.2
    */
	int updateHtNum(QualityCheck record);   
	
	
	int deleteByPrimaryKey(String id);
    int insert(QualityCheck record);
    int insertSelective(QualityCheck record);
    int updateByPrimaryKey(QualityCheck record);
    
    /**
     * 根据入库单ID作废检验单
     * @param instockId
     * @return
     */
    int discardByInstockId(String instockId);
}