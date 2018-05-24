package com.eray.thjw.project2.service;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.TEApplicability;

/**
 * 
 * @Description 技术评估单-适用性service
 * @CreateTime 2018年3月29日 下午3:08:15
 * @CreateBy 林龙
 */
public interface TEApplicabilityService {
	/**
	 * 
	 * @Description 新增或编辑
	 * @CreateTime 2018年4月2日 上午10:52:45
	 * @CreateBy 林龙
	 * @param syxszList
	 * @param user
	 * @param eoId
	 * @param currentDate
	 * @param czls
	 */
	void savePulicationAffectedList(List<TEApplicability> syxszList,User user, String eoId,Date currentDate,String czls);
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018年4月2日 上午10:52:38
	 * @CreateBy 林龙
	 * @param id
	 * @param czls 
	 * @param user 
	 */
	void deleteByMainid(String id, User user, String czls);
	


}
