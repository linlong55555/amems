package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.PostApplicationPXPG;

/**
 * @Description 岗位授权-培训评估
 * @CreateTime 2017-11-17 下午4:57:19
 * @CreateBy 刘兵
 */
public interface PostApplicationPXPGService {
	
	/**
	 * @Description 编辑岗位授权-培训评估
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationPXPGList 岗位授权-培训评估集合
	 * @param mainid 父id
	 */
	public void update(List<PostApplicationPXPG> postApplicationPXPGList, String mainid) throws BusinessException;
	
}
