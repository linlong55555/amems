package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.JobCard;

public interface JobCardMapper {
    int deleteByPrimaryKey(String id);

    int insert(JobCard record);

    int insertSelective(JobCard record);

    JobCard selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JobCard record);

    int updateByPrimaryKey(JobCard record);
    
    /**
	 * @author ll
     * @param string 
	 * @description 根据定检工作内容id定检工卡
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<JobCard> queryListByDjgznrid(String djbh,String nbid, String string);
    
    List<JobCard> queryAll(JobCard record);
    
    int queryCount(JobCard record);
    
    int validationQueryCount(JobCard record);
    
    JobCard selectByCode(String gdbh);
    /**
   	 * @author liub
   	 * @description 批量审核
   	 * @param idList
   	 * @develop date 2016.12.28
   	 */
       int updateBatchAudit(Map<String, Object> map);
       
       /**
   	 * @author liub
   	 * @description 批量批准
   	 * @param idList
   	 * @develop date 2016.12.28
   	 */
       int updateBatchApprove(Map<String, Object> map);
       
       JobCard selectBydjgznrid(String id);
       //根据id查询数据
       List<JobCard> queryByIdList(List<String> idList);
       
       //根据组织机构获取工卡
       List<JobCard> findByDprtcode(String dprtcode);
       
       /**
    	 * @author liub
    	 * @description 根据飞机机型查询工卡(弹窗)
    	 * @param jobCard
    	 * @return List<JobCard> 
    	 */
       List<JobCard> queryWinByFjjx(JobCard jobCard);
}