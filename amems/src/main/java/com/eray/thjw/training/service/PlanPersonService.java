package com.eray.thjw.training.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.training.po.PlanPerson;


public interface PlanPersonService {
	 
	public List<PlanPerson> selectByPxjhid(String pxjhid);
	/**
	 * @author sunji
	 * @description 查询维修人员培训跟踪
	 * @param PlanPerson
	 * @return List<PlanPerson>
	 */
	public Map<String, Object> qeryAllPageList(PlanPerson planPerson)throws BusinessException;
	
	/**
	 * 培训记录分页
	 * @param planPerson
	 * @return
	 * @throws BusinessException
	 */
	public List<PlanPerson> queryAllPageplanPersonList(PlanPerson planPerson)throws BusinessException;
	
	public void save(PlanPerson planPerson)throws BusinessException;
	
	public void update(PlanPerson planPerson)throws BusinessException;
	
	public void delete(String id);
	
	public PlanPerson selectByPrimaryKey(String id);
	
	/**
	 * 保存-通过维修人员档案
	 * @param personnel
	 * @return
	 */
	void save(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 根据维修人员档案查询对应的培训记录
	 * @param id
	 * @return
	 */
	List<PlanPerson> queryByWxrydaid(String id);
	
	public List<PlanPerson> queryAllPagerecordsList(PlanPerson planPerson);
	
	List<PlanPerson> queryByPxjhscId(String pxjhid);
	public void delete(PlanPerson planPerson);

}
