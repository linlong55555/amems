package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.PostApplicationPXPG;

public interface PostApplicationPXPGMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostApplicationPXPG record);

    int insertSelective(PostApplicationPXPG record);

    PostApplicationPXPG selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostApplicationPXPG record);

    int updateByPrimaryKey(PostApplicationPXPG record);

	void deleteByPrimaryMainid(String id);
	
	/**
	 * @Description 根据mainid查询岗位授权-培训评估
	 * @CreateTime 2017-11-17 下午3:03:24
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @return 岗位授权-培训评估集合
	 */
	List<PostApplicationPXPG> queryByMainId(String mainid);
}