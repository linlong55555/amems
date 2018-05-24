package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.quality.po.PostApplicationPXPG;
import com.eray.thjw.quality.po.PostApplicationSQJX;

/**
 * 
 * @Description 岗位授权service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface PostApplicationService {
	
	/**
	 * @Description 岗位授权分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(PostApplication postApplication)throws BusinessException ;
	
	/**
	 * @Description 保存岗位授权
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return 岗位授权id
	 * @throws BusinessException
	 */
	public String save(PostApplication postApplication)throws BusinessException;
	
	/**
	 * @Description 岗位授权删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return 岗位授权id
	 * @throws BusinessException
	 */
	public void delete(PostApplication postApplication)throws BusinessException;

	/**
	 * @Description 根据岗位授权id查询岗位授权信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return
	 * @throws BusinessException
	 */
	public PostApplication getInfoById(PostApplication postApplication)throws BusinessException;

	/**
	 * @Description 修改保存岗位授权
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return 岗位授权id
	 * @throws BusinessException
	 */
	public String update(PostApplication postApplication)throws BusinessException;

	/**
	 * @Description 修改审核人
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param postApplication 岗位授权
	 * @return
	 * @throws BusinessException
	 */
	public String updateShr(PostApplication postApplication)throws BusinessException;

	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年11月14日 下午1:57:13
	 * @CreateBy 林龙
	 * @param postApplication
	 * @throws BusinessException
	 */
	public void updategConfirm(PostApplication postApplication)throws BusinessException;

	/**
	 * 
	 * @Description 审核通过
	 * @CreateTime 2017年11月14日 下午3:57:07
	 * @CreateBy 林龙
	 * @param postApplication
	 * @return
	 * @throws BusinessException
	 */
	public String updatePassed(PostApplication postApplication)throws BusinessException;

	/**
	 * 
	 * @Description 审核驳回
	 * @CreateTime 2017年11月14日 下午3:57:19
	 * @CreateBy 林龙
	 * @param postApplication
	 * @return
	 * @throws BusinessException
	 */
	public String updateTurnDown(PostApplication postApplication)throws BusinessException;
	
	/**
	 * @Description 岗位评估
	 * @CreateTime 2017-11-16 下午6:06:21
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	public String updateEvaluation(PostApplication postApplication)throws BusinessException;
	
	/**
	 * @Description 设置有效期
	 * @CreateTime 2017-11-17 上午11:18:56
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	public String updateEffectDate(PostApplication postApplication)throws BusinessException;
	
	/**
	 * @Description 上传授权书
	 * @CreateTime 2017-11-16 下午6:03:54
	 * @CreateBy 刘兵
	 * @param postApplication 岗位授权
	 * @return String
	 * @throws BusinessException
	 */
	public String updateUpload(PostApplication postApplication)throws BusinessException;
	
	/**
	 * @Description 根据mainid查询岗位授权-培训评估
	 * @CreateTime 2017-11-17 下午3:03:24
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @return 岗位授权-培训评估集合
	 */
	public List<PostApplicationPXPG> queryPostApplicationPXPGByMainId(String mainid);
	
	/**
	 * @Description 查询人员资质评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param workRequire 岗位要求
	 * @return List<WorkRequire> 岗位要求集合
	 */
	public List<WorkRequire> queryWorkRequireEval(WorkRequire workRequire);
	
	/**
	 * @Description 获取岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param mainid
	 */
	public List<PostApplicationSQJX> querySQJXByMainid(String mainid);
	
}
