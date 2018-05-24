package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.system.po.CustomBlock;

public interface CustomBlockMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomBlock record);

    int insertSelective(CustomBlock record);

    CustomBlock selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomBlock record);

    int updateByPrimaryKey(CustomBlock record);
    
    /**
     * 根据mainid（用户ID） 查询 
     * @param mainid 用户ID
     * @return
     */
    List<CustomBlock> selectByMainId(String mainId);
    
    /**
     * 根据mainid（用户ID） 查询 删除
     * @param mainid 用户ID
     * @return
     */
    int deleteByMainId(String mainId);
    
}