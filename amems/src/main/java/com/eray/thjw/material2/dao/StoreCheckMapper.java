package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.StoreCheck;

/**
 * @Description 盘点记录mapper
 * @CreateTime 2018-3-20 下午2:05:50
 * @CreateBy 刘兵
 */
public interface StoreCheckMapper {
    int deleteByPrimaryKey(String id);

    int insert(StoreCheck record);

    int insertSelective(StoreCheck record);

    StoreCheck selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(StoreCheck record);
    
    /**
	 * @Description 盈亏历史分页列表查询
	 * @CreateTime 2018-3-22 上午11:55:12
	 * @CreateBy 刘兵
	 * @param storeCheck 盘点记录
	 * @return List<StoreCheck> 盘点记录集合
	 */
	List<StoreCheck> queryAllPageList(StoreCheck storeCheck);

}