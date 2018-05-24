package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.thjw.aerialmaterial.po.HasScrappedList;
import com.eray.thjw.aerialmaterial.po.ScrapList;
import com.eray.thjw.exception.BusinessException;


public interface ScrapListService {

	void save(ScrapList scrapList) throws BusinessException;
	/**
	 * @author meizhiliang
	 * @param record
	 * 分页查询报废清单列表
	 */
	List<ScrapList> selectScrapList(ScrapList record);  
	/**
	 * @author meizhiliang
	 * @param record
	 * 查询一条报废清单
	 */
	ScrapList selectByPrimaryKey(String id);   
	/**
	 * @author meizhiliang
	 * @param record
	 * 更新报废清单
	 * @throws Exception 
	 */
	void updateByPrimaryKeySelective(ScrapList record) throws Exception;
	
	/**
	 * 查询-报废申请
	 * @param scraplist
	 * @return
	 */
	Map<String, Object> queryapplypage(ScrapList scraplist);
	
	/**
	 * 查询报废单详情
	 * @param scraplist
	 * @return
	 */
	List<HasScrappedList> queryscrapdetail(ScrapList scraplist);
	
	/**
	 * 保存报废单
	 * @param scrapList
	 * @return
	 */
	String doSave(ScrapList scrapList) throws SaibongException;
	
	/**
	 * 提交报废单
	 * @param scrapList
	 * @return
	 * @throws SaibongException
	 */
	String doSubmit(ScrapList scrapList) throws SaibongException, BusinessException;
	
	/**
	 * 作废报废单
	 * @param scrapList
	 */
	void doScrap(ScrapList scrapList);
	
	/**
	 * 指定结束报废单
	 * @param scrapList
	 */
	void doClose(ScrapList scrapList) throws BusinessException;
	
	/**
	 * 撤销报废单
	 * @param scrapList
	 */
	void doRevoke(ScrapList scrapList) throws BusinessException;
	
	/**
	 * 审核通过报废单
	 * @param scrapList
	 * @return
	 */
	String doAuditApprove(ScrapList scrapList) throws BusinessException;
	
	/**
	 * 审核拒绝报废单
	 * @param scrapList
	 * @return
	 */
	String doAuditReject(ScrapList scrapList) throws BusinessException;
}
