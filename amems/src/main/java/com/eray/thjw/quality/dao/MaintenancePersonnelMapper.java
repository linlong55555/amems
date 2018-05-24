package com.eray.thjw.quality.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.quality.po.MaintenancePersonnel;

public interface MaintenancePersonnelMapper {
	int deleteByPrimaryKey(String id);

    int insert(MaintenancePersonnel record);

    int insertSelective(MaintenancePersonnel record);

    MaintenancePersonnel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenancePersonnel record);

    int updateByPrimaryKey(MaintenancePersonnel record);
	/**
	 * @author sunji
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<MaintenancePersonnel>
	 */
	List<MaintenancePersonnel> queryAllBygwid(String gwid);
	
	/**
	 * @author liub
	 * @description 根据机构代码查询维修人员档案树(按部门)
	 * @param dprtcode
	 * @param keyword 
	 * @return List<MaintenancePersonnel>
	 */
	List<MaintenancePersonnel> queryDrptTreeByDprtcode(String dprtcode, String keyword);
	
	/**
	 * @author liub
	 * @description 根据机构代码查询维修人员档案树(按岗位)
	 * @param dprtcode
	 * @param str 
	 * @return List<MaintenancePersonnel>
	 */
	List<MaintenancePersonnel> queryBusinessTreeByDprtcode(String dprtcode, String str);
	
	/**
	 * 维修人员档案分页查询
	 * @param personnel
	 * @return
	 */
	List<MaintenancePersonnel> queryPage(MaintenancePersonnel personnel);
	
	/**
	 * 维修人员档案查询带岗位
	 * @param personnel
	 * @return
	 */
	List<MaintenancePersonnel> queryWithPost(MaintenancePersonnel personnel);
	
	/**
	 * 验证同一组织机构下人员编号是否重复
	 * @param rybh
	 * @param dprtcode
	 * @return
	 */
	int selectCount4VlidationRybh(String rybh,String dprtcode);
	/**
	 * 验证同一组织机构下是否已经录入
	 * @param rybh
	 * @param dprtcode
	 * @return
	 */
	int validtion4RyidExist(MaintenancePersonnel personnel);

	MaintenancePersonnel queryAllRybh(MaintenancePersonnel maintenancePersonnel);

	int queryCount(MaintenancePersonnel maintenancePersonnel);

	List<MaintenancePersonnel> queryAllJgdm(String jgdm);

	void updateByPrimaryKeySelectiveStr(
			MaintenancePersonnel maintenancePersonnel);
	
	/**
	 * 维修人员档案页面更新数据专用
	 * @Description 
	 * @CreateTime 2017年8月14日 上午10:21:56
	 * @CreateBy 韩武
	 * @return
	 */
	int updateByMaintenance(MaintenancePersonnel personnel);

	MaintenancePersonnel findRyidExist(MaintenancePersonnel personnel);
	
	/**
	 * 
	 * @Description 查询所有岗位监控
	 * @CreateTime 2017-11-16 上午10:03:54
	 * @CreateBy 孙霁
	 * @param personnel
	 * @return
	 */
	List<MaintenancePersonnel> queryAllMonitor(MaintenancePersonnel personnel);

	List<Map<String, Object>> loadTrainingcourse(MaintenancePersonnel personnel);
	
	/**
	 * 
	 * @Description 根据维修人员id查询数据
	 * @CreateTime 2017-11-16 上午10:03:54
	 * @CreateBy 孙霁
	 * @param personnel
	 * @return
	 */
	MaintenancePersonnel selectByWxryid(String id);

	List<Map<String, Object>> loadTrainingcourseFjjx(
			MaintenancePersonnel personnel);
	
	
	/**
	 * @Description 根据组织机构获取验证人员数据
	 * @param dprtcode
	 * @CreateBy 刘邓
	 * @return
	 */
	List<MaintenancePersonnel> queryPersons(String dprtcode);
}