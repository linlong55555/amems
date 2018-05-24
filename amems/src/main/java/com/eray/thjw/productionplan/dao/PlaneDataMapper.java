package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneData;

public interface PlaneDataMapper {
    int deleteByPrimaryKey(PlaneData pd);

    int insert(PlaneData pd);

    int insertSelective(PlaneData pd);

    List<PlaneData> queryList(PlaneData pd);       //  根据给出条件查询飞机数据
    
    /**
     * 查询飞机数据-适航指令使用
     * @param pd
     * @return
     */
    List<PlaneData> queryPlanes(PlaneData pd);      

    PlaneData selectByPrimaryKey(PlaneData pd); 
    
    int updateByPrimaryKeySelective(PlaneData record);

    int updateByPrimaryKey(PlaneData record);
    
    int queryCount(PlaneData pd) throws RuntimeException;	// 查询所有飞机数量
    
    int queryCountByFjzch(PlaneData pd) throws RuntimeException;	// 查询指定飞机注册号的飞机数量
    
    int queryCountByXlh(PlaneData pd) throws RuntimeException;	// 查询指定序列号的飞机数量

	List<Map<String, Object>> queryPage(PlaneData pd) throws RuntimeException;	// 分页查询飞机基本信息
	
	PlaneData queryByFjzch(PlaneData pd) throws RuntimeException;	// 根据飞机注册号查询
	
	PlaneData selectByKey(PlaneData pd)throws Exception;       //  根据给出条件查询飞机数据
	
	List<PlaneData> findAllWithFjjx(PlaneData pd);	// 查询所有飞机数据并关联机型 
	/**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询飞机基本信息
	 * @param dprtcodeList
	 * @return List<PlaneData>
	 * @develop date 2016.12.06
	 */
	public List<PlaneData> selectByDprtcodeList(List<String> dprtcodeList);
	

	List<PlaneData> queryAllPageList(PlaneData planeData)throws RuntimeException;

	/**
	 * @author liub
	 * @description  根据条件分页查询飞机注册号列表
	 * @param planeData
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.05
	 */
	public List<Map<String, Object>> queryAllPageWinList(PlaneData planeData);
	
	int queryCountfj(String pd);

	public List<PlaneData> selectUserACFjzchLists(PlaneData planeData);

	PlaneData selectByPrimaryKeys(PlaneData pd);
	/**
	 * 
	 * @Description EO适用性根据组织机构和飞机机型查询状态为1的飞机
	 * @CreateTime 2017年8月24日 上午11:56:59
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param fjjx
	 * @return
	 */
	List<PlaneData> getPlaneList(PlaneData pd);
}