package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.PostApplicationSQJX;

/**
 * @Description 岗位授权-申请机型Mapper
 * @CreateTime 2018-1-25 下午3:30:27
 * @CreateBy 刘兵
 */
public interface PostApplicationSQJXMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostApplicationSQJX record);

    int insertSelective(PostApplicationSQJX record);

    PostApplicationSQJX selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostApplicationSQJX record);

    int updateByPrimaryKey(PostApplicationSQJX record);
    
    List<PostApplicationSQJX> queryByMainid(String mainid);
    
    /**
	 * @Description 根据mainid集合获取岗位授权-申请机型
	 * @CreateTime 2018-1-31 上午11:00:55
	 * @CreateBy 刘兵
	 * @param mainidList
	 * @return List<PostApplicationSQJX>
	 */
    List<PostApplicationSQJX> queryByMainidList(List<String> mainidList);
    
}