package com.eray.thjw.quality.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.PostApplicationSQJXMapper;
import com.eray.thjw.quality.po.PostApplicationSQJX;
import com.eray.thjw.quality.service.PostApplicationSQJXService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.EffectiveEnum;
/**
 * @Description 岗位授权-培训评估
 * @CreateTime 2017-11-17 下午4:57:19
 * @CreateBy 刘兵
 */
@Service
public class PostApplicationSQJXServiceImpl implements PostApplicationSQJXService  {

	
	@Resource
	private PostApplicationSQJXMapper postApplicationSQJXMapper;

	@Resource
	private CommonRecService commonRecService;
	
	
	/**
	 * @Description 新增岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationSQJXList 岗位授权-申请机型集合
	 * @param mainid 父id
	 */
	@Override
	public void save(List<PostApplicationSQJX> postApplicationSQJXList, String mainid) throws BusinessException {
		if(null != postApplicationSQJXList && postApplicationSQJXList.size() > 0){
//			String czls = UUID.randomUUID().toString();	//流水号
			User user = ThreadVarUtil.getUser();//当前登陆人
			for (PostApplicationSQJX postApplicationSQJX : postApplicationSQJXList) {
				postApplicationSQJX.setId(UUID.randomUUID().toString());
				postApplicationSQJX.setMainid(mainid);
				postApplicationSQJX.setWhrid(user.getId());
				postApplicationSQJX.setWhbmid(user.getBmdm());
				postApplicationSQJX.setZt(EffectiveEnum.YES.getId());
				postApplicationSQJXMapper.insertSelective(postApplicationSQJX);
			}
		}
	}
	
	/**
	 * @Description 编辑岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationSQJXList 岗位授权-申请机集合
	 * @param mainid 父id
	 */
	@Override
	public void update(List<PostApplicationSQJX> postApplicationSQJXList, String mainid) throws BusinessException {
		if(null != postApplicationSQJXList && postApplicationSQJXList.size() > 0){
//			String czls = UUID.randomUUID().toString();	//流水号
			User user = ThreadVarUtil.getUser();//当前登陆人
			Map<String, PostApplicationSQJX> map = new HashMap<String, PostApplicationSQJX>();
			Map<String, String> jxMap = new HashMap<String, String>();
			List<PostApplicationSQJX> oldList = postApplicationSQJXMapper.queryByMainid(mainid);
			for (PostApplicationSQJX postApplicationSQJX : oldList) {
				map.put(postApplicationSQJX.getFjjx() == null?"-":postApplicationSQJX.getFjjx(), postApplicationSQJX);
			}
			for (PostApplicationSQJX postApplicationSQJX : postApplicationSQJXList) {
				if(null != map.get(postApplicationSQJX.getFjjx())){
					jxMap.put(postApplicationSQJX.getFjjx() == null?"-":postApplicationSQJX.getFjjx(), postApplicationSQJX.getFjjx());
					postApplicationSQJX.setId(map.get(postApplicationSQJX.getFjjx()).getId());
					postApplicationSQJXMapper.updateByPrimaryKeySelective(postApplicationSQJX);
				}else{
					postApplicationSQJX.setId(UUID.randomUUID().toString());
					postApplicationSQJX.setMainid(mainid);
					postApplicationSQJX.setWhrid(user.getId());
					postApplicationSQJX.setWhbmid(user.getBmdm());
					postApplicationSQJX.setZt(EffectiveEnum.YES.getId());
					postApplicationSQJXMapper.insertSelective(postApplicationSQJX);
				}
			}
			for (PostApplicationSQJX postApplicationSQJX : oldList){
				if(null == jxMap.get(postApplicationSQJX.getFjjx() == null?"-":postApplicationSQJX.getFjjx())){
					postApplicationSQJXMapper.deleteByPrimaryKey(postApplicationSQJX.getId());
				}
			}
		}
	}
	
	/**
	 * @Description 获取岗位授权-申请机型
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param mainid 父id
	 */
	@Override
	public List<PostApplicationSQJX> queryByMainid(String mainid) {
		return postApplicationSQJXMapper.queryByMainid(mainid);
	}

	/**
	 * @Description 根据mainid集合获取岗位授权-申请机型
	 * @CreateTime 2018-1-31 上午11:00:55
	 * @CreateBy 刘兵
	 * @param mainidList
	 * @return List<PostApplicationSQJX>
	 */
	public List<PostApplicationSQJX> queryByMainidList(List<String> mainidList) {
		return postApplicationSQJXMapper.queryByMainidList(mainidList);
	}
}