package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToAchievement;

public interface PersonnelToAchievementService {

	/**
	 * 保存学术成就
	 * @param personnel
	 * @return
	 */
	void saveScholarship(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 保存嘉奖记录
	 * @param personnel
	 * @return
	 */
	void saveCitation(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 查询学术成就
	 * @param id
	 * @return
	 */
	List<PersonnelToAchievement> queryScholarships(String id);
	
	/**
	 * 查询嘉奖记录
	 * @param id
	 * @return
	 */
	List<PersonnelToAchievement> queryCitations(String id);
}
