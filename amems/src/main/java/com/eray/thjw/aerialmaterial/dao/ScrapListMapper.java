package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ScrapList;


public interface ScrapListMapper {
    
	List<ScrapList> selectScrapList(ScrapList record);  //分页查询报废清单列表
	
	ScrapList selectByPrimaryKey(String id);           //查询一条报废清单
	
	int updateByPrimaryKeySelective(ScrapList record);   //更新报废清单
	
	
	
	
	int deleteByPrimaryKey(String id);
    int insert(ScrapList record);
    int insertSelective(ScrapList record);
    
    /**
     * 查询-报废申请
     * @param scraplist
     * @return
     */
    List<ScrapList> queryapplypage(ScrapList scraplist);
    
}