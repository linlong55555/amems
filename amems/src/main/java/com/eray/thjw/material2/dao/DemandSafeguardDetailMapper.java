package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.DemandSafeguardDetail;

/**
 * 
 * @Description 需求保障保障明细
 * @CreateTime 2018-2-26 下午2:57:57
 * @CreateBy 孙霁
 */
public interface DemandSafeguardDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(DemandSafeguardDetail record);

    int insertSelective(DemandSafeguardDetail record);

    DemandSafeguardDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DemandSafeguardDetail record);

    int updateByPrimaryKey(DemandSafeguardDetail record);
    
    List<DemandSafeguardDetail> queryAll(DemandSafeguardDetail record);
    
    /**
	 * @Description 查询需求统计分析
	 * @CreateTime 2018-4-3 下午5:10:16
	 * @CreateBy 刘兵
	 * @param demandSafeguardDetail
	 * @return
	 * @throws BusinessException
	 */
    List<DemandSafeguardDetail> queryAnalysisList(DemandSafeguardDetail record);
    
    void updateBatch(DemandSafeguardDetail record);
}