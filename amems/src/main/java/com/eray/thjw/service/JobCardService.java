package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.User;

public interface JobCardService {

	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<JobCard> queryAll(JobCard jobCard);
	/**
	 * @author sunji
	 * @description 根据查询总条数
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int queryCount(JobCard jobCard);
	/**
	 * @author sunji
	 * @description  验证根据查询总条数
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int validationQueryCount(JobCard jobCard);
	/**
	 * @author sunji
	 * @description 根据编号查看对象
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public JobCard selectByCode(String gdbh);
	
	/**
	 * @author sunji
	 * @description 添加工卡
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public String insertJobCard(JobCard jobCard)throws BusinessException;
	/**
	 * @author sunji
	 * @description 修改工卡
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void modifyJobCard(JobCard jobCard)throws BusinessException;
	/**
	 * @author sunji
	 * @description 检查定检工卡修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user,String id);
	/**
	 * @author sunji
	 * @description 根据id查询数据
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public JobCard getByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheJobCard(JobCard jobCard)throws BusinessException;
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuJobCard(JobCard jobCard)throws BusinessException;
	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteJobCard (String ids) throws BusinessException;
	/**
	 * @author sunji
	 * @description  关闭（指定结束）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offJobCard (JobCard jobCard) throws BusinessException;
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
	public String updateBatchAudit(List<String> idList,String yj) throws RuntimeException;
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList
	 * @develop date 2016.12.28
	 */
	public String updateBatchApprove(List<String> idList,String yj) throws RuntimeException;
	/**
  	 * @author meizhiliang
  	 * @description 工单复制工卡
  	 * @param JobCard
  	 */
     JobCard getCopyJobCard(JobCard record);
     /**
      * @author sunji
      * @description 根据定检工作内容id查询
      * @param JobCard
      */
     public JobCard selectBydjgznrid(String id);
     /**
      * @author sunji
      * @description 查询列表
      * @param JobCard
      */
     public Map<String , Object> queryAllJobCard(JobCard jobCard) throws BusinessException;
     /**
      * @author sunji
      * @description 根据组织机构查询所有
      * @param JobCard
      */
     public List<JobCard> findByDprtcode(String dprtcode) throws BusinessException;
     
     /**
 	 * @author liub
 	 * @description 根据飞机机型查询工卡(弹窗)
 	 * @param jobCard
 	 * @return List<JobCard> 
 	 */
     public List<JobCard> queryWinByFjjx(JobCard jobCard) throws BusinessException;
}
