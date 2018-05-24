package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Engineering;

public interface EngineeringMapper {
    int deleteByPrimaryKey(String id);

    int insert(Engineering record);

    int insertSelective(Engineering record);

    Engineering selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Engineering record);

    int updateByPrimaryKey(Engineering record);
    
    int queryCount(Engineering record);
    
    List<Engineering> queryEngineeringAll(Engineering record);
    
    List<Engineering> queryEngineeringPageList(Engineering record);                //查询工单需要的工程指令
    
    List<Engineering> queryAll (Map<String, Object> map);
    
    int updateBatchApprove (Map<String, Object>map);
    
    List<Integer> getEOStatusList(String engineeringId);
    
    Engineering findByEOId(String eoId);
    
    int queryCountbyEoId(String id);
    
    /**
	 * @author liub
	 * @description 通过评估单id查询EO指令
	 * @param pgdid
	 * @develop date 2017.03.16
	*/
     List<Engineering> queryByPgdId(String pgdid);
     
     int validationQueryCount(Engineering record);
     //根据id查询数据
     List<Engineering> queryByIdList(List<String> idList);
}