package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.PlaneModelData;

/**
 * @author 梅志亮
 * @time 2016-08-15
 * @describe 机型数据2的接口
 */
public interface PlaneModelDataMapper {

	int insert(PlaneModelData record); // 新增机型数据

	int updateByPrimaryKey(PlaneModelData record); // 修改机型数据

	List<PlaneModelData> selectPlaneModelDataList(PlaneModelData record); // 根据条件查询机型数据

	PlaneModelData selectPlaneModelData(PlaneModelData record); // 查询一个机型数据

	int selectCount(PlaneModelData record); // 根据机型查询记录数

	List<PlaneModelData> selectPlanes() throws Exception; // 查询机型集合

	List<PlaneModelData> findType(String dprtcode);

	/**
	 * @author liub
	 * @description 查询所有有效机型
	 * @param
	 * @return List<String>
	 * @develop date 2016.09.05
	 */
	List<String> findAllType(String dprtcode);

	PlaneModelData selectPlane(String fjzch); // 查询一个机型

	PlaneModelData selectPlanes(String fjzch, String dprtcode) throws Exception; // 查询一个机型

	/**
	 * @author liub
	 * @description 根据条件分页查询机型列表
	 * @param planeModelData
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.05
	 */
	public List<Map<String, Object>> queryAllPageList(
			PlaneModelData planeModelData);

	public int selectByFjjxAndDprtcode(PlaneModelData planeModelData);

	List<PlaneModelData> findAllPlaneModelData(PlaneModelData planeModelData1);

	/**
	 * 根据组织机构查询
	 * 
	 * @param dprtcode
	 * @return
	 */
	List<PlaneModelData> findByDprtcode(String dprtcode);

	/**
	 * 查询飞机主数据
	 * 
	 * @param entity
	 * @return
	 */
	List<Map<String, Object>> selectAircraftData(BaseEntity entity);

	List<PlaneModelData> selectUserACTypeLists(PlaneModelData planeModelData);

	/**
	 * 查询用户授权组织机构的所有 机型 和 飞机注册号
	 * 
	 * @param entity
	 * @return
	 */
	List<PlaneModelData> selectAircraftByAuthOrg(BaseEntity entity);

	/**
	 * 根据条件查询飞机和机型
	 * @param entity
	 * @return
	 */
	int selectAircraft(BaseEntity entity);

	List<Map<String, Object>> selectAircraft135145Data(BaseEntity entity);

	int selectAircraft145(BaseEntity entity);

}