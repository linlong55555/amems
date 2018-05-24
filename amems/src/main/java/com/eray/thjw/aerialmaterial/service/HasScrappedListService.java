package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.exception.BusinessException;


public interface HasScrappedListService {

	void save(HasScrappedList hasScrappedList) throws BusinessException;
	/**
	 * @author meizhiliang
	 * @param mainid
	 * 查询所有的报废航材
	 */
	List<HasScrappedList> selectHasScrappedList(String mainid);
	
	/**
	 * @author sunji
	 * @description  根据条件分页报废记录清单
	 * @param hasScrappedList
	 * @return  Map<String, Object>
	 */
	public  Map<String, Object> queryAll(HasScrappedList hasScrappedList);
	/**
	 * @author sunji
	 * @description  根据条件不分页报废记录清单
	 * @param hasScrappedList
	 * @return  Map<String, Object>
	 */
	public List<HasScrappedList> queryAllPageList(HasScrappedList hasScrappedList) throws BusinessException;
}
