package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.RevisionNoticeBook;
import com.eray.thjw.po.WorkOrder;

public interface RevisionNoticeBookMapper {
    int deleteByPrimaryKey(String id);

    int insert(RevisionNoticeBook record);

    int insertSelective(RevisionNoticeBook record);

    RevisionNoticeBook selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RevisionNoticeBook record);

    int updateByPrimaryKey(RevisionNoticeBook record);
    
    List<Map<String, Object>> selectRevisionNoticeBookListByPage(Map<String, Object> paramMap);
    
    List<RevisionNoticeBook> findAll(RevisionNoticeBook revisionNoticeBook);
    
    List<RevisionNoticeBook> queryAll (Map<String, Object> map);
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
       
       
     /**
   	 * @author liub
   	 * @description 根据修订通知书id集合查询修订通知书数据
   	 * @param xdtzsIdList
   	 */
   	List<RevisionNoticeBook> queryByXdtzsIdList(List<String> xdtzsIdList);
       
       /**
   	 * @author liub
   	 * @description 通过评估单id、通知书类型查询修订通知书
   	 * @param pgdid,tzslx
   	 * @return List<Map<String, Object>> 修订通知书列表
   	 * @develop date 2017.03.15
   	*/
    List<Map<String, Object>> queryByPgdId(@Param("pgdid")String pgdid,@Param("tzslx")Integer tzslx);
    //根据id查询数据
    List<RevisionNoticeBook> queryByIdList(List<String> idList);
    
}