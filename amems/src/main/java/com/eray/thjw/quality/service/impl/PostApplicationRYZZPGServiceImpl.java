package com.eray.thjw.quality.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.PostApplicationRYZZPGMapper;
import com.eray.thjw.quality.po.PostApplicationRYZZPG;
import com.eray.thjw.quality.service.PostApplicationRYZZPGService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.EffectiveEnum;
/**
 * @Description 岗位授权-人员资质评估
 * @CreateTime 2017-11-17 下午4:57:19
 * @CreateBy 刘兵
 */
@Service
public class PostApplicationRYZZPGServiceImpl implements PostApplicationRYZZPGService  {

	
	@Resource
	private PostApplicationRYZZPGMapper postApplicationRYZZPGMapper;

	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 编辑岗位授权-人员资质评估
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationRYZZPGList 岗位授权-人员资质评估集合
	 * @param mainid 父id
	 */
	@Override
	public void update(List<PostApplicationRYZZPG> postApplicationRYZZPGList, String mainid) throws BusinessException {
		if(null != postApplicationRYZZPGList && postApplicationRYZZPGList.size() > 0){
//			String czls = UUID.randomUUID().toString();	//流水号
			User user = ThreadVarUtil.getUser();//当前登陆人
			Map<String, PostApplicationRYZZPG> map = new HashMap<String, PostApplicationRYZZPG>();
			List<PostApplicationRYZZPG> oldList = postApplicationRYZZPGMapper.queryByMainId(mainid);
			for (PostApplicationRYZZPG postApplicationRYZZPG : oldList) {
				map.put(postApplicationRYZZPG.getGwyqid(), postApplicationRYZZPG);
			}
			for (PostApplicationRYZZPG postApplicationRYZZPG : postApplicationRYZZPGList) {
				if(null != map.get(postApplicationRYZZPG.getGwyqid())){
					postApplicationRYZZPG.setId(map.get(postApplicationRYZZPG.getGwyqid()).getId());
					postApplicationRYZZPGMapper.updateByPrimaryKeySelective(postApplicationRYZZPG);
				}else{
					postApplicationRYZZPG.setId(UUID.randomUUID().toString());
					postApplicationRYZZPG.setMainid(mainid);
					postApplicationRYZZPG.setWhrid(user.getId());
					postApplicationRYZZPG.setWhbmid(user.getJgdm());
					postApplicationRYZZPG.setZt(EffectiveEnum.YES.getId());
					postApplicationRYZZPGMapper.insertSelective(postApplicationRYZZPG);
				}
			}
		}
	}
	
	
	
     
}