package com.eray.thjw.aerialmaterial.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.exception.BusinessException;

/**
 * 外派清单业务处理
 * @author xu.yong
 *
 */
public interface ExpatriateService {

	/**
	 * 查询借出未归还的航材统计信息
	 * @param expatriate
	 * @return
	 */
	public Map<String, Object> queryLendStatisticsPage(Expatriate expatriate);
	
	/**
	 * 查询单个借出未归还航材
	 * @param dprtcode 组织机构
	 * @param secondmentId 借调对象ID
	 * @param bjId 部件ID
	 * @return
	 */
	public Expatriate querySingleLend(String dprtcode, String secondmentId, String bjId);
	
	/**
	 * 
	 * 借出 未归还： 给定数量查询借调对象某部件清单
	 * @param dprtcode 组织机构
	 * @param secondmentId 借调对象
	 * @param bjId 部件ID
	 * @param count 归还数量
	 * @return
	 */
	public List<Expatriate> queryListLend(String dprtcode, String secondmentId, String bjId, BigDecimal count);
	
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
	
	 /**
     * 查询借出未归还记录 排序：外派时间
     * @param dprtcode 组织机构
     * @param wpdxid 外派对象
     * @param bjid 部件ID
     * @return
     */
    List<Expatriate> queryLendList(String dprtcode, String wpdxid, String bjid);

	public List<Expatriate> queryLendListcheck(String dprtcode, String jddxid,String bjid,BigDecimal cks)throws RuntimeException;

	public void update(Expatriate expatriate) throws RuntimeException;

	public List<Expatriate> queryLendListchecka(String dprtcode, String jddxid,
			String bjid)throws RuntimeException;

	public Expatriate queryByKey(String wpqdid)throws RuntimeException;

	public  List<Expatriate> queryAllPageListjie(Expatriate expatriate) throws RuntimeException;
	
	public List<Expatriate> queryAllPageListhai(Expatriate expatriate) throws RuntimeException;
	
	/**
	 * 根据归还航材 分配 借出记录
	 * @param secondmentId
	 * @param bjs
	 * @return
	 * @throws BusinessException 
	 */
	public List<Expatriate> getLendByReturn(String dprtcode, String secondmentId, List<Map<String, Object>> bjList) throws BusinessException;
}
