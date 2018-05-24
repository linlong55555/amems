package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.PostApplicationRYZZPG;

/**
 * @Description 岗位授权-人员资质评估
 * @CreateTime 2017-11-17 下午4:57:19
 * @CreateBy 刘兵
 */
public interface PostApplicationRYZZPGService {
	
	/**
	 * @Description 编辑岗位授权-人员资质评估
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationRYZZPGList 岗位授权-人员资质评估集合
	 * @param mainid 父id
	 */
	public void update(List<PostApplicationRYZZPG> postApplicationRYZZPGList, String mainid) throws BusinessException;
	
}
