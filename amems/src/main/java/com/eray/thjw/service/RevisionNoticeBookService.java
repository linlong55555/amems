package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.RevisionNoticeBook;
import com.eray.thjw.po.User;

/**
 * @author pingxiaojun
 * @description 修订通知书 Service类
 * @develop date 2016.08.15
 */
public interface RevisionNoticeBookService {

	/**
	 * @author pingxiaojun
	 * @description 查询修订通知书列表并且分页
	 * @param requestParamMap
	 *            请求参数Map
	 * @develop date 2016.08.15
	 * @return List<Map<String, Object>> 修订通知书列表
	 */
	public List<Map<String, Object>> queryRevisionNoticeBookListByPage(Map<String, Object> requestParamMap);

	/**
	 * @author pingxiaojun
	 * @description 保存修订通知书信息
	 * @param requestParamMap
	 *            请求参数Map
	 * @develop date 2016.08.17
	 * @throws Exception
	 *             新增修订通知书信息出现的异常
	 * @return Map<String, Object> 新增修订通知书的处理结果
	 */
	public Map<String, Object> saveRevisionNoticeBook(Map<String, Object> requestParamMap) throws Exception;

	/**
	 * @author pingxiaojun
	 * @description 根据主键id查询修订通知书信息
	 * @param id
	 *            主键id
	 * @develop date 2016.08.18
	 * @return WorkRevisionNotice 修订通知书信息
	 */
	public RevisionNoticeBook queryByPrimaryKey(String id) throws RuntimeException;

	/**
	 * @author pingxiaojun
	 * @description 修改修订通知书信息
	 * @param requestParamMap
	 *            请求参数Map
	 * @develop date 2016.08.18
	 * @throws Exception
	 *             修改修订通知书信息出现的异常
	 * @return Map<String, Object> 修改修订通知书信息的处理结果
	 */
	public Map<String, Object> modifyRevisionNoticeBook(Map<String, Object> requestParamMap, int [] i) throws Exception;

	/**
	 * @author pingxiaojun
	 * @description 删除修订通知书
	 * @param revisionNoticeBook
	 *            请求参数
	 * @develop date 2016.08.22
	 * @throws Exception
	 *            删除修订通知书出现的异常
	 * @return Map<String, Object> 删除修订通知书的处理结果
	 */
	public Map<String, Object> deleteRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook) throws Exception;

	/**
	 * @author pingxiaojun
	 * @description 指定结束修订通知书
	 * @param revisionNoticeBook
	 *            请求参数
	 * @develop date 2016.08.23
	 * @throws Exception
	 *             指定结束修订通知书出现的异常
	 * @return Map<String, Object> 指定结束修订通知书的处理结果
	 */
	public Map<String, Object> endRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook) throws BusinessException;
	
	/**
	 * @author pingxiaojun
	 * @description 指定结束修订通知书
	 * @param revisionNoticeBook
	 *            请求参数
	 * @develop date 2016.08.23
	 * @throws Exception
	 *             指定结束修订通知书出现的异常
	 * @return Map<String, Object> 指定结束修订通知书的处理结果
	 */
	public Map<String, Object> closeRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook) throws BusinessException;

	/**
	 * @author liub
	 * @description 查询所有修订通知书
	 * @param revisionNoticeBook
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.23
	 */
	public List<Map<String, Object>> findAll(RevisionNoticeBook revisionNoticeBook) throws RuntimeException;

	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user, String id);

	/**
	 * @author sunji
	 * @description 审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook) throws BusinessException;

	/**
	 * @author sunji
	 * @description 批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuRevisionNoticeBook(RevisionNoticeBook revisionNoticeBook) throws BusinessException;

	/**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<RevisionNoticeBook> queryAll(Map<String, Object> map);
	/**
	 * 通过id查询RevisionNoticeBook里面的数据
	 * @param id
	 * @return
	 */
	public RevisionNoticeBook selectByPrimaryKey(String id);
	/**
	 * 修改接收状态
	 * @param id
	 * @return
	 */
	public void updateJszt(RevisionNoticeBook revisionNoticeBook);
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
	public String updateBatchAudit(List<String> idList,String yj);
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList
	 * @develop date 2016.12.28
	 */
	public String updateBatchApprove(List<String> idList,String yj);
	
	/**
	 * @author liub
	 * @description 通过评估单id、通知书类型查询修订通知书
	 * @param pgdid,tzslx
	 * @return List<Map<String, Object>> 修订通知书列表
	 * @develop date 2017.03.15
	*/
	public List<Map<String, Object>> queryByPgdId(String pgdid,Integer tzslx);
	/**
	 * sunji
	 * 查询列表
	 * @param annunciate
	 * @return map
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAllRevisionNoticeBook( Map<String, Object> requestParamMap,Pagination pagination)throws BusinessException;
}
