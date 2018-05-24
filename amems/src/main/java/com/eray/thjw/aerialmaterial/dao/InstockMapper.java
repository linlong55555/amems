package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Instock;
import com.eray.thjw.po.BaseEntity;

/**
 * 入库单
 * @author xu.yong
 *
 */
public interface InstockMapper {
    int deleteByPrimaryKey(String id);

    int insert(Instock record);

    int insertSelective(Instock record);

    Instock selectByPrimaryKey(String id);
    
    /**
     * 根据ID查询非作废 ，返回结果带入库人、申请人、借调对象
     * @param id
     * @return
     */
    Instock selectById(String id);
    
    /**
     * 根据相关单据ID查询非作废 ，返回结果带入库人、申请人、借调对象
     * @param xgdjid 相关单据ID
     * @return
     */
    Instock selectByXgdjId(String xgdjid);

    int updateByPrimaryKeySelective(Instock record);

    int updateByPrimaryKey(Instock record);
    
    /**
     * 根据入库航材ID更新入库单状态
     * @param detailId 入库航材ID
     * @param oldState 旧状态
     * @param newState 新状态
     * @return
     */
    int updateStateByDetailId(String detailId, Integer oldState, Integer newState);
    
    List<Instock> selectPage(BaseEntity entity);
    
    /**
     * 根据相关单据ID（外飞行队借入归还出库单ID）查询归还入库单
     * @param map {dprtcode:, idList:}
     * @return
     */
    List<Instock> selectLendReturnByXgdjid(Map<String, Object> map);
}