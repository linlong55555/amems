package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Enquiry;
import com.eray.thjw.aerialmaterial.po.ProcurementCatalog;
import com.eray.thjw.exception.BusinessException;


public interface EnquiryService {
	
	
	/**
	 * @author liub
	 * @description 保存询价
	 * @param enquiry
	 * @develop date 2016.10.20
	 * @throws BusinessException 
	 */
	public void save(Enquiry enquiry) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询询价列表信息
	 * @param enquiry
	 * @return Map<String, Object>
	 * @develop date 2016.10.18
	 */
	public List<Map<String, Object>> queryAllPageList(Enquiry enquiry);
	
	/**
	 * @author liub
	 * @description  根据提订详情id查询询价信息
	 * @param mainid
	 * @return List<Enquiry>
	 * @develop date 2016.10.20
	 */
	public List<Enquiry> queryEnquiryListByMainId(String mainid);
	
	/**
	 * @author liub
	 * @description  根据查询条件采购目录信息
	 * @param procurementCatalog
	 * @return List<ProcurementCatalog>
	 * @develop date 2016.11.07
	 */
	public List<ProcurementCatalog> queryProcurementCatalogList(ProcurementCatalog procurementCatalog);
	
}
