package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.po.ScrapList;


public interface HasScrappedListMapper {
    
	List<HasScrappedList> selectHasScrappedList(String mainid); //查询所有的报废航材
	
	
	int updateByPrimaryKeySelective(HasScrappedList record);   //更新报废单的报废航材
	
	
	
	
	int deleteByPrimaryKey(String id);

    int insert(HasScrappedList record);

    int insertSelective(HasScrappedList record);

    HasScrappedList selectByPrimaryKey(String id);

    int updateByPrimaryKey(HasScrappedList record);
    
    List<HasScrappedList> selectHasScrappedList4Revoke(String mainid);
    
    public List<HasScrappedList> queryAll(HasScrappedList hasScrappedList);
    
    List<HasScrappedList> queryAllPageList(HasScrappedList hasScrappedList);
    
    
    /**
     * 查询报废单详情
     * @param scraplist
     * @return
     */
    List<HasScrappedList> queryscrapdetail(String id);
    
    /**
     * 删除报废单详情
     * @param scrapList
     * @return
     */
    int deleteNotExist(ScrapList scrapList);
    
    /**
     * 查询所有报废单详情
     * @param scraplist
     * @return
     */
    List<HasScrappedList> queryAllData(String id);
    
    /**
     * 删除无效的数据（zt=0）
     * @param id
     * @return
     */
    int deleteInvalidData(String id);
}