package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ProcurementCatalog;

public interface ProcurementCatalogMapper {
    int deleteByPrimaryKey(String id);

    int insert(ProcurementCatalog record);

    int insertSelective(ProcurementCatalog record);

    ProcurementCatalog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProcurementCatalog record);

    int updateByPrimaryKey(ProcurementCatalog record);
    
    /**
	 * @author liub
	 * @description  根据条件修改采购合同状态
	 * @param record
	 * @return int
	 * @develop date 2016.10.20
	 */
	int updateStatus(ProcurementCatalog record);
	
	/**
	 * @author liub
	 * @description  根据条件修改采购合同有效期结束
	 * @param record
	 * @return int
	 * @develop date 2016.10.20
	 */
	int updateYxqJs(ProcurementCatalog record);
	
	/**
	 * @author liub
	 * @description  根据查询条件采购目录信息
	 * @param procurementCatalog
	 * @return List<ProcurementCatalog>
	 * @develop date 2016.11.07
	 */
	List<ProcurementCatalog> queryProcurementCatalogList(ProcurementCatalog record);
	
	
}