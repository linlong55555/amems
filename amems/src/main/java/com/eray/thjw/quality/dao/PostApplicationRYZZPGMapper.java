package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.PostApplicationRYZZPG;

public interface PostApplicationRYZZPGMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostApplicationRYZZPG record);

    int insertSelective(PostApplicationRYZZPG record);

    PostApplicationRYZZPG selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostApplicationRYZZPG record);

    int updateByPrimaryKey(PostApplicationRYZZPG record);

	void deleteByPrimaryMainid(String id);
	
	/**
	 * @Description 根据mainid查询岗位授权-人员资质评估
	 * @CreateTime 2017-11-17 下午3:03:24
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @return List<PostApplicationRYZZPG> 岗位授权-人员资质评估集合
	 */
	List<PostApplicationRYZZPG> queryByMainId(String mainid);
}