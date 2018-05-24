package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.MaintenancePersonnel;

public interface MaintenancePersonnelService {

	/**
	 * @author sunji
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<MaintenancePersonnel>
	 */
	List<MaintenancePersonnel> queryAllBygwid(String id);
	
	/**
	 * @author liub
	 * @param str 
	 * @description 根据机构代码查询维修人员档案树(按部门)
	 * @param id
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryDrptTreeByDprtcode(String dprtcode, String str);
	
	/**
	 * @author liub
	 * @param str 
	 * @description 根据机构代码查询维修人员档案树(按岗位)
	 * @param id
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryBusinessTreeByDprtcode(String dprtcode, String str);
	
	/**
	 * 保存维修人员档案
	 * @param personnel
	 * @return
	 */
	String save(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 维修人员档案分页查询
	 * @param personnel
	 * @return
	 */
	Map<String, Object> queryPage(MaintenancePersonnel personnel);
	
	/**
	 * 删除维修人员档案
	 * @param personnel
	 * @throws BusinessException
	 */
	void delete(MaintenancePersonnel personnel) throws BusinessException;
	
	/**
	 * 加载维修人员档案详情
	 * @param personnel
	 * @return
	 */
	MaintenancePersonnel loadDetail(MaintenancePersonnel personnel);
	
	/**
	 * id查询
	 * @param id
	 * @return
	 */
	MaintenancePersonnel selectByPrimaryKey(String id);
	
	/**
	 * wxryid查询
	 * @param id
	 * @return
	 */
	MaintenancePersonnel selectByWxryid(String wxryid);
	
	/**
	 * 验证同一组织机构下是否已经录入
	 * @param rybh
	 * @param dprtcode
	 * @return
	 */
	int validtion4RyidExist(MaintenancePersonnel personnel);

	MaintenancePersonnel findRyidExist(MaintenancePersonnel personnel);
	
	/**
	 * 
	 * @Description 查询所有岗位监控
	 * @CreateTime 2017-11-16 上午10:01:45
	 * @CreateBy 孙霁
	 * @param maintenancePersonnel
	 */
	Map<String, Object> queryAllMonitor(MaintenancePersonnel maintenancePersonnel);

	List<Map<String, Object>> loadTrainingcourse(MaintenancePersonnel personnel);
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-1-29 下午5:28:03
	 * @CreateBy 刘兵
	 * @param maintenancePersonnel 维修人员档案
	 * @return List<MaintenancePersonnel
	 */
	List<MaintenancePersonnel> doExportExcel(MaintenancePersonnel maintenancePersonnel);

	List<Map<String, Object>> loadTrainingcourseFjjx(
			MaintenancePersonnel personnel);
}
