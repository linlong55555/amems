package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.productionplan.po.PlaneData;

public interface PlaneDataService {
	
	PlaneData selectByPrimaryKey(PlaneData pd)throws RuntimeException;       //  根据给出条件查询飞机数据
	
	Map<String, Object> queryByPage(PlaneData pd) throws BusinessException;	// 飞机基本信息分页查询
	
	void add(PlaneData pd) throws RuntimeException, BusinessException;	// 新增飞机基本信息和初始化数据
	
	void edit(PlaneData pd) throws RuntimeException, BusinessException;	// 修改飞机基本信息和初始化数据
	
	Map<String, Object> queryByFjzch(PlaneData pd) throws RuntimeException;	// 飞机注册号查询

	List<PlaneData> queryList(PlaneData planeData);
	
	List<PlaneData> findAllWithFjjx(PlaneData pd);	// 查询所有飞机数据并关联机型
	
	List<PlaneData> findAllWithFjjxAuthority(PlaneData pd);	// 查询授权的飞机数据并关联机型
	
	/**
     * 查询飞机数据-适航指令使用
     * @param pd
     * @return
     */
    List<PlaneData> queryPlanes(PlaneData pd) throws RuntimeException;
    
    /**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询飞机基本信息
	 * @param dprtcodeList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.06
	 */
	public List<Map<String, Object>> selectByDprtcodeList(List<String> dprtcodeList) throws RuntimeException;
	
	/**
	 * 查询所有飞机数据并关联机型
	 * @param pd
	 * @return
	 */
	List<Map<String, Object>> findAllWithFjjxInJson(PlaneData pd);

	List<PlaneData> queryAllPageList(PlaneData planeData)throws RuntimeException;

	void update(PlaneData planeData)throws RuntimeException,BusinessException;
	
	/**
	 * @author liub
	 * @description  根据条件分页查询飞机注册号列表
	 * @param planeData
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.05
	 */
	public List<Map<String, Object>> queryAllPageWinList(PlaneData planeData) throws RuntimeException;
	
	public List<PlaneData> selectUserACFjzchLists(PlaneData planeData);

	PlaneData selectByPrimaryKeys(PlaneData pd);
	
	/**
	 * 删除飞机基本信息
	 * @param pd
	 */
	void delete(PlaneData pd) throws BusinessException;
	/**
	 * 
	 * @Description EO选择飞机
	 * @CreateTime 2017年8月24日 下午4:43:29
	 * @CreateBy 岳彬彬
	 * @param pd
	 * @return
	 */
	Map<String,Object> getplaneList(PlaneData pd);
}
