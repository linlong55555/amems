package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.Store;


public interface StoreMapper {
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询仓库信息
	 * @param record
	 * @return List<Store>
	 * @develop date 2016.09.12
	 */
	public List<Store> queryAllPageList(Store record);
	
	/**
	 * @author liub
	 * @description 查询所有的仓库数据,级联查询选中标记
	 * @param roleId
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	List<Store> findAllByRoleIdtive(@Param("roleId")String roleId,@Param("dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据用户查询仓库授权信息
	 * @param userId
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	List<Store> findStoreByUserId(String userId);
	
	/**
	 * @author liub
	 * @description 查询所有的仓库数据
	 * @param
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	List<Store> findAll();
	
	/**
	 * @author liub
	 * @description 查询所有有效仓库数据
	 * @param
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	List<Store> findAlltive();
	
	/**
	 * @author liub
	 * @description 根据操作人的机构代码查询有效仓库数据
	 * @param dprtcode
	 * @return List<Store>
	 * @develop date 2016.12.23
	 */
	public List<Store> selectByDprtcode(String dprtcode);
	
	/**
	 * @author liub
	 * @description 检查仓库是否存在
	 * @param ckh(仓库号),ckmc(仓库名称),dprtcode(机构代码)
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	List<Store> checkStore(@Param(value = "ckh")String ckh ,@Param(value = "ckmc")String ckmc ,@Param(value = "dprtcode")String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据id查询仓库及用户信息
	 * @param id
	 * @return Store
	 * @develop date 2016.09.14
	 */
	Store selectJoinUserById(String id);
	
	
    int deleteByPrimaryKey(String id);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

	public List<Store> findAlltives(Store store);
	
	/**
	 * 根据用户ID查询用户角色用户拥有的仓库
	 * @param userId
	 * @return
	 */
	List<Store> selectByUserId(String userId);
	/**
	 * 根据用户ID查询用户角色用户拥有的仓库
	 * @return
	 */
	List<Store> selectAll();

	public Store selectByPrimaryCkh(String ckh, String jgdm);


	public List<Store> selectByUserIdJgdm(String jgdm, String id);

}