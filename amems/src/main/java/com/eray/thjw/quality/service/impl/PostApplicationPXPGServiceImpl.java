package com.eray.thjw.quality.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.PostApplicationMapper;
import com.eray.thjw.quality.dao.PostApplicationPXPGMapper;
import com.eray.thjw.quality.dao.PostApplicationRYZZPGMapper;
import com.eray.thjw.quality.po.PersonnelToPost;
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.quality.po.PostApplicationPXPG;
import com.eray.thjw.quality.service.PersonnelToPostService;
import com.eray.thjw.quality.service.PostApplicationPXPGService;
import com.eray.thjw.quality.service.PostApplicationService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.ConclusionEnum;
import enu.common.EffectiveEnum;
import enu.quality.PostStatusEnum;
/**
 * @Description 岗位授权-培训评估
 * @CreateTime 2017-11-17 下午4:57:19
 * @CreateBy 刘兵
 */
@Service
public class PostApplicationPXPGServiceImpl implements PostApplicationPXPGService  {

	
	@Resource
	private PostApplicationPXPGMapper postApplicationPXPGMapper;

	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 编辑岗位授权-培训评估
	 * @CreateTime 2017-11-17 下午5:00:13
	 * @CreateBy 刘兵
	 * @param postApplicationPXPGList 岗位授权-培训评估集合
	 * @param mainid 父id
	 */
	@Override
	public void update(List<PostApplicationPXPG> postApplicationPXPGList, String mainid) throws BusinessException {
		if(null != postApplicationPXPGList && postApplicationPXPGList.size() > 0){
//			String czls = UUID.randomUUID().toString();	//流水号
			User user = ThreadVarUtil.getUser();//当前登陆人
			Map<String, PostApplicationPXPG> map = new HashMap<String, PostApplicationPXPG>();
			List<PostApplicationPXPG> oldList = postApplicationPXPGMapper.queryByMainId(mainid);
			for (PostApplicationPXPG postApplicationPXPG : oldList) {
				map.put(postApplicationPXPG.getKcid(), postApplicationPXPG);
			}
			for (PostApplicationPXPG postApplicationPXPG : postApplicationPXPGList) {
				if(null != map.get(postApplicationPXPG.getKcid())){
					postApplicationPXPG.setId(map.get(postApplicationPXPG.getKcid()).getId());
					postApplicationPXPGMapper.updateByPrimaryKeySelective(postApplicationPXPG);
				}else{
					postApplicationPXPG.setId(UUID.randomUUID().toString());
					postApplicationPXPG.setMainid(mainid);
					postApplicationPXPG.setWhrid(user.getId());
					postApplicationPXPG.setWhbmid(user.getJgdm());
					postApplicationPXPG.setZt(EffectiveEnum.YES.getId());
					postApplicationPXPGMapper.insertSelective(postApplicationPXPG);
				}
			}
		}
	}
	
	
	
     
}