package com.eray.thjw.service;

/**
 * @author 梅志亮
 * @time 2016-08-15
 * @describe 机型数据2的Service 用于业务逻辑处理
 */

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.PlaneModelData;

public interface PlaneModelDataService {

	public String insert(PlaneModelData record) throws Exception; // 新增机型数据

	public int updateByPrimaryKey(PlaneModelData record) throws Exception; // 修改机型数据

	public List<PlaneModelData> queryList(PlaneModelData record)
			throws Exception; // 根据条件查询机型数据

	public int selectCount(PlaneModelData record) throws Exception; // 根据机型查询记录数

	PlaneModelData selectPlaneModelData(PlaneModelData record) throws Exception; // 查询一个机型数据

	PlaneModelData selectPlane(String fjzch) throws Exception; // 查询一个机型

	PlaneModelData selectPlanes(String fjzch, String string) throws Exception; // 查询一个机型

	List<PlaneModelData> selectPlanes() throws Exception; // 查询机型集合

	public List<PlaneModelData> findType() throws RuntimeException;

	/**
	 * @author liub
	 * @description 根据机构代码查询所有有效机型
	 * @param
	 * @return List<String>
	 * @develop date 2016.09.05
	 */
	public List<String> findAllType(String dprtcode) throws RuntimeException;

	/**
	 * @author liub
	 * @description 根据条件分页查询机型列表
	 * @param planeModelData
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.05
	 */
	public List<Map<String, Object>> queryAllPageList(
			PlaneModelData planeModelData) throws RuntimeException;

	/**
	 * 根据组织机构查询机型
	 * 
	 * @param dprtcode
	 * @return
	 */
	public List<String> findByDprtcode(String dprtcode);

	/**
	 * 查询飞机主数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<Map<String, Object>> selectAircraftData(BaseEntity entity);

	public List<PlaneModelData> selectUserACTypeLists(
			PlaneModelData planeModelData);

	/**
	 * 查询用户授权组织机构的所有 机型 和 飞机注册号
	 * 
	 * @author xu.yong
	 * @return
	 */
	public List<PlaneModelData> queryAircraftByAuthOrgList();
	
	/**
	 * 检查飞机是否存在
	 * @param uesrId
	 * @param dprtCode
	 * @param fjzch
	 * @return
	 */
	public  Boolean existsAircraft(String uesrId,String usertype,String dprtCode,List<String> fjzch);
	
	/**
	 * 检查飞机是否存在
	 * @param uesrId
	 * @param dprtCode
	 * @param fjzch
	 * @return
	 */
	public  void existsAircraft4Expt(String uesrId,String usertype,String dprtCode,List<String> fjzch) throws BusinessException;

	
	/**
	 * 检查机型是否存在
	 * @param uesrId
	 * @param dprtCode
	 * @param jx
	 * @return
	 */
	public  Boolean existsModel(String uesrId,String usertype,String dprtCode,List<String> jx);
	
	/**
	 * 检查机型是否存在
	 * @param uesrId
	 * @param dprtCode
	 * @param jx
	 * @return
	 */
	public  void existsModel4Expt(String uesrId,String usertype,String dprtCode,List<String> jx) throws BusinessException;

	public void existsAircraft4145Expt(String id, String userType,String dprtcode, List<String> fjzchList)throws BusinessException;

	Boolean existsAircraft145(String uesrId, String usertype, String dprtCode,
			List<String> fjzch);

}
