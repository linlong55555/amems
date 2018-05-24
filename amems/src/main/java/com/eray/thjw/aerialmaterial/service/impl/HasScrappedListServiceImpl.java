
package com.eray.thjw.aerialmaterial.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HasScrappedListMapper;
import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.service.ComponentScrapService;
import com.eray.thjw.aerialmaterial.service.ComponentUsageService;
import com.eray.thjw.aerialmaterial.service.HasScrappedListService;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.aerialmaterial.service.MaterialRecService;
import com.eray.thjw.aerialmaterial.service.OutFieldStockService;
import com.eray.thjw.aerialmaterial.service.ScrapListService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

@Service
public class HasScrappedListServiceImpl implements HasScrappedListService {
	
	@Resource
	private HasScrappedListMapper hasScrappedListMapper;
	@Resource
	private ScrapListService scrapListService;
	@Resource
	private MaterialHistoryService materialHistoryService;
	@Resource
	private StockSerivce stockSerivce;
	@Resource
	private MaterialRecService materialRecService;   //航材的REC
	@Resource
	private OutFieldStockService outFieldStockService;
	@Resource
	private ComponentScrapService componentScrapService;    //报废情况使用情况
	@Resource
	private ComponentUsageService componentUsageService;    //部件情况使用情况
	
	
	@Override
	public void save(HasScrappedList hasScrappedList) throws BusinessException {
		hasScrappedListMapper.insertSelective(hasScrappedList);
	}

	@Override
	public List<HasScrappedList> selectHasScrappedList(String mainid) {
		return hasScrappedListMapper.selectHasScrappedList(mainid);
	}

	/**
	 * @author sunji
	 * @description  根据条件分页报废记录清单
	 * @param hasScrappedList
	 * @return  Map<String, Object>
	 */ 
	public Map<String, Object> queryAll(HasScrappedList hasScrappedList) {
		PageHelper.startPage(hasScrappedList.getPagination());
		List<HasScrappedList> list = this.hasScrappedListMapper.queryAll(hasScrappedList);
		return PageUtil.pack4PageHelper(list, hasScrappedList.getPagination());
	}

	/**
	 * @author sunji
	 * @description  根据条件不分页报废记录清单
	 * @param hasScrappedList
	 * @return  Map<String, Object>
	 */
	public List<HasScrappedList> queryAllPageList (
			HasScrappedList hasScrappedList) throws BusinessException{
		return this.hasScrappedListMapper.queryAllPageList(hasScrappedList);
	}
}
