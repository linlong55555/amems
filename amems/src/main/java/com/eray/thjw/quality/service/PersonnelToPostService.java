package com.eray.thjw.quality.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToPost;

public interface PersonnelToPostService {

	/**
	 * 保存
	 * @param personnel
	 * @return
	 */
	void save(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	List<PersonnelToPost> query(String id);

	List<PersonnelToPost> selectMainidList(List<String> pgdidList);
}
