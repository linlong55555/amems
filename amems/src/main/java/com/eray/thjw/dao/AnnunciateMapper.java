package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Annunciate;

public interface AnnunciateMapper {
    int deleteByPrimaryKey(String id);

    int insert(Annunciate record);

    int insertSelective(Annunciate record);

    Annunciate selectByPrimaryKey(String id);
    
    Annunciate selectByNum(String num);

    int updateByPrimaryKeySelective(Annunciate record);

    int updateByPrimaryKey(Annunciate record);
    /**
	 * @author sunji
	 * @description 根据查询条件分页查询技术通告
	 * @param Annunciate
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
    List<Annunciate> queryJoinOrderSource(Annunciate record);
   /* //查询数据库时间
    String selectDate();*/
    
    List<Annunciate> queryAll (Map<String, Object> map);
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
	 * @description 通过评估单id查询技术通告
	 * @param pgdid
	 * @develop date 2017.03.14
	*/
     List<Annunciate> queryByPgdId(String pgdid);
     //根据id查询数据
     List<Annunciate> queryByIdList(List<String> idList);
}