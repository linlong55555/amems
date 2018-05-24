package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.PostApplicationSQJX;

/**
 * @Description 岗位授权-申请机型
 * @CreateTime 2017-11-17 下午4:57:19
 * @CreateBy 刘兵
 */
public interface PostApplicationSQJXService {
	
	/**
	 * @Description 新增岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationSQJXList 岗位授权-申请机型集合
	 * @param mainid 父id
	 */
	public void save(List<PostApplicationSQJX> postApplicationSQJXList, String mainid) throws BusinessException;
	
	/**
	 * @Description 编辑岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationSQJXList 岗位授权-申请机集合
	 * @param mainid 父id
	 */
	public void update(List<PostApplicationSQJX> postApplicationSQJXList, String mainid) throws BusinessException;
	
	/**
	 * @Description 获取岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param mainid 父id
	 */
	public List<PostApplicationSQJX> queryByMainid(String mainid);
	
	/**
	 * @Description 根据mainid集合获取岗位授权-申请机型
	 * @CreateTime 2018-1-31 上午11:00:55
	 * @CreateBy 刘兵
	 * @param mainidList
	 * @return List<PostApplicationSQJX>
	 */
    List<PostApplicationSQJX> queryByMainidList(List<String> mainidList);
}
