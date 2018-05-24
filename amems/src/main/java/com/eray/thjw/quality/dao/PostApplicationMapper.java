package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.PostApplication;

public interface PostApplicationMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostApplication record);

    int insertSelective(PostApplication record);

    PostApplication selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostApplication record);

    int updateByPrimaryKey(PostApplication record);

	List<PostApplication> queryAllPageList(PostApplication postApplication);

	int queryCount(PostApplication fai);

	PostApplication getInfoById(PostApplication postApplication);

	int getCurrentZt4Validation(String id);
	
	/**
	 * @Description 岗位评估
	 * @CreateTime 2017-11-16 下午6:06:21
	 * @CreateBy 刘兵
	 * @param record 岗位授权
	 * @return int
	 */
	int updateEvaluation(PostApplication record);

	int selectByPostApplication(PostApplication postApplication);
	
	List<String> selectFjjxByPostApplication(PostApplication postApplication);

}