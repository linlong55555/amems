package com.eray.thjw.project2.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.TechnicalfileOrder;

import enu.LogOperationEnum;

/**
 * @Description 技术文件评估单Service
 * @CreateTime 2017年8月16日 上午10:12:57
 * @CreateBy 林龙
 */
public interface TechnicalService {

	/**
	 *  添加技术文件评估（初始）
	 * @Description 
	 * @CreateTime 2017-8-16 上午10:16:16
	 * @CreateBy 孙霁
	 * @param technical
	 * @param airworthiness
	 * @param czls
	 * @param zdid
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	public String insert(TechnicalfileOrder teco,Airworthiness airworthiness,String czls,String zdid, User user, LogOperationEnum cz)throws BusinessException ;
	/**
	 * 
	 * @Description 修改评估单评估期限和zjh
	 * @CreateTime 2017-8-16 下午2:41:13
	 * @CreateBy 孙霁
	 * @param pgqx
	 * @param zjh
	 * @return
	 * @throws BusinessException
	 */
	public void updateTechnical(String id,Date pgqx,String zjh,String czls, String zdid,User user, LogOperationEnum cz, TechnicalfileOrder teco, boolean b)throws BusinessException ;
	
	/**
	 * 
	 * @Description 物理删除评估单评估
	 * @CreateTime 2017-8-16 下午2:50:03
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	public void deleteTechnical(String id)throws BusinessException ;
	/**
	 * @Description 技术文件评估单分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(Technical technical)throws BusinessException ;
	
	/**
	 * @Description 保存技术评估单
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 技术文件评估单id
	 * @throws BusinessException
	 */
	public String save(Technical technical)throws BusinessException;
	
	/**
	 * @Description 技术文件评估单删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 技术文件评估单id
	 * @throws BusinessException
	 */
	public void delete(Technical technical)throws BusinessException;
	/**
	 * 
	 * @Description 技术评估单弹出列表
	 * @CreateTime 2017年8月16日 上午11:00:11
	 * @CreateBy 岳彬彬
	 * @param technical
	 * @return
	 */
	Map<String, Object> selectPgdByZlidAndDprtcode(Technical technical);
	/**
	 * 
	 * @Description  根据适航性资料查询数据
	 * @CreateTime 2017-8-16 上午10:45:54
	 * @CreateBy 孙霁
	 * @param jswjid
	 * @return
	 */
	public List<Technical>  selectByjswjid(String jswjid);
	/**
	 * @Description 根据适航性资料id查询评估单
	 * @CreateTime 2017年8月15日 下午8:17:29
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return List<Technical> 技术文件评估单List集合
	 */
	public Map<String, Object> queryGljspgdList(Technical technical);
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-8-18 下午4:51:54
	 * @CreateBy 孙霁
	 * @param jswjid
	 * @return
	 */
	public Map<String, Object> selectTechnicalfileByJswjid(String jswjid) throws BusinessException;
	
	/**
	 * 
	 * @Description 新增技术文件评估单
	 * @CreateTime 2017年8月18日 下午10:17:58
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @param uuid 主表id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	public void insertSelective(Technical technical, String uuid, String czls)throws BusinessException;
	
	/**
	 * @Description 根据技术文件评估单id查询技术评估单信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	public Technical getById(Technical technical);
	
	/**
	 * @Description 修改技术评估单
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 技术文件评估单id
	 * @throws BusinessException
	 */
	public String update(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 新增提交技术评估单
	 * @CreateTime 2017年8月22日 上午11:47:32
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	public String saveSubmit(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 修改提交技术评估单
	 * @CreateTime 2017年8月22日 上午11:55:10
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单id
	 * @return
	 * @throws BusinessException
	 */
	public String updateSubmit(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 审核技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	public String passed(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 审核驳回技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	public String turnDown(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 批准技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	public String approvalPassed(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 批准驳回技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	public String approvalTurnDown(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月23日 上午9:29:21
	 * @CreateBy 林龙
	 * @param idList idlist
	 * @param yj 意见
	 * @return
	 * @throws BusinessException
	 */
	public String updateBatchAudit(List<String> idList, String yj)throws BusinessException;
	
	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年8月23日 上午9:29:21
	 * @CreateBy 林龙
	 * @param idList idlist
	 * @param yj 意见
	 * @return
	 * @throws BusinessException
	 */
	public String updateBatchApprovel(List<String> idList, String yj)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据id查询技术文件评估单
	 * @CreateTime 2017年8月24日 上午10:20:23
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Technical selectByPrimaryKey(String id)throws BusinessException;
	
	/**
	 * 
	 * @Description 关闭技术文件评估单
	 * @CreateTime 2017年8月24日 下午6:08:06
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @throws BusinessException
	 */
	public void updateEndModal(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 初始化数据 
	 * @CreateTime 2017年8月24日 下午9:47:25
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 */
	public Map<String, Object> initData();
	
	/**
	 * 
	 * @Description 改版保存技术评估单
	 * @CreateTime 2017年8月26日 上午10:04:11
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	public String insertRevisionSave(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 改版提交技术评估单
	 * @CreateTime 2017年8月26日 上午10:04:04
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	public String updateRevisionSubmit(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据技术评估单查询最大版本
	 * @CreateTime 2017年8月28日 下午3:57:59
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	public Technical selectMaxBb(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据id查询当前评估单的所有版本
	 * @CreateTime 2017年8月29日 上午10:50:57
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @param dprtcode 
	 * @return
	 */
	public List<Technical> queryHistoryListById(String id, String dprtcode)throws BusinessException;
	
	/**
	 * 
	 * @Description 根据arids获取评估单数据
	 * @CreateTime 2017-9-1 下午3:59:57
	 * @CreateBy 孙霁
	 * @param arids
	 * @throws BusinessException
	 * @return
	 */
	public List<Technical> queryAllByGlxswjids(List<String> arids)throws BusinessException;
	
	/**
	 * 
	 * @Description 修改评估人
	 * @CreateTime 2017年11月17日 下午5:08:36
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	public String updateAssessor(Technical technical)throws BusinessException;
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2018年1月3日 下午2:12:06
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> queryAlldaochuList(Technical technical)
			throws BusinessException;
	public Map<String, Object> getgljspgdListold(Technical technical);
	public List<Technical> getgljspgdListoldwu(Technical technical);
	
}
