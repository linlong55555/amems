package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Expatriate;

/**
 * b_h_012 外派清单
 * @author xu.yong
 *
 */
public interface ExpatriateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Expatriate record);

    int insertSelective(Expatriate record);

    Expatriate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Expatriate record);

    int updateByPrimaryKey(Expatriate record);
    
    public Expatriate queryByKey(String wpqdid) throws RuntimeException;
    
    /**
     * 分页查询借出 待归还 统计数据
     * @param expatriate
     * @return
     */
    List<Expatriate> queryLendStatisticsPage(Expatriate expatriate);
   
    /**
     * 查询借出 待归还 统计数据
     * @param expatriate
     * @return
     */
    List<Expatriate> queryLendStatistics(Expatriate expatriate);
    
    /**
     * 查询借出未归还记录 排序：外派时间
     * @param dprtcode 组织机构
     * @param wpdxid 外派对象
     * @param bjid 部件ID
     * @return
     */
    List<Expatriate> queryLendList(String dprtcode, String wpdxid, String bjid);
    
    /**
	 * 按条件查询一页在途
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<Expatriate> queryAllPageList(Expatriate expatriate)  throws RuntimeException;
	
	 /**
	 * 根据借调对象和部件id查询当前的待归还数量
	 * @param param
	 * @param pagination
	 * @return
	 */
	 Expatriate querySelectCount(Expatriate expatriate)  throws RuntimeException;
	 
	 public List<Expatriate> queryLendListcheck(String dprtcode, String jddxid,String bjid)throws RuntimeException;
	 
	 public  List<Expatriate> queryAllPageListjie(Expatriate expatriate) throws RuntimeException;
		
		 
	public  List<Expatriate> queryAllPageListhai(Expatriate expatriate) throws RuntimeException;
	
	 /**
	  * 调整归还数量，在原归还数量基础上增加
	  * @param expatriate
	  * @return
	  */
	 int updateGhslAdd(Expatriate expatriate);
	 
	 /**
	  * 调整归还数量，在原归还数量基础上减少
	  * @param expatriate
	  * @return
	  */
	 int updateGhslSub(Expatriate expatriate);
	 
	 /**
	  * 根据bjId或bjh查询某借调对象待归还航材
	  * @param map
	  * @return
	  */
	 List<Expatriate> queryLendByBj(Map<String, Object> map);
	 
}