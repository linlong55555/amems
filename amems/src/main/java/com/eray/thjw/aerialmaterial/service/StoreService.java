package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.exception.BusinessException;



public interface StoreService {
	
	/**
	 * @author liub
	 * @description 增加仓库
	 * @param store
	 * @develop date 2016.09.14
	 * @throws BusinessException 
	 */
	public String add(Store store) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改仓库
	 * @param store
	 * @develop date 2016.09.14
	 * @throws BusinessException 
	 */
	public void edit(Store store) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  作废
	 * @param ids
	 * @return
	 * @develop date 2016.09.18
	 */
	public void cancel(String ids) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据主键id查询仓库信息
	 * @param store
	 * @return Store
	 * @develop date 2016.09.14
	 */
	public Store selectByPrimaryKey(String id);
	
	/**
	 * @author liub
	 * @description 根据主键id查询仓库及用户信息
	 * @param id
	 * @return Store
	 * @develop date 2016.09.14
	 */
	public Store selectJoinUserById(String id);
	
	/**
	 * @author liub
	 * @description 根据仓库id查询仓库库位
	 * @param id
	 * @return List<Storage>
	 * @develop date 2016.09.14
	 */
	public List<Storage> queryStorageListByStoreId(String storeId);
	
	/**
	 * @author liub
	 * @description 根据仓库号查询仓库库位
	 * @param storeCode
	 * @return List<Storage>
	 * @develop date 2016.09.18
	 */
	public List<Storage> queryStorageListByStoreCode(String storeCode,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询仓库信息
	 * @param store
	 * @return List<Store>
	 * @develop date 2016.09.12
	 */
	public List<Store> queryAllPageList(Store store);
	
	/**
	 * @author liub
	 * @description 查询所有的仓库数据,级联查询选中标记
	 * @param roleId,dprtcode
	 * @return List<Map<String, Object>>
	 * @develop date 2016.09.09
	 */
	public List<Map<String, Object>> findAllByRoleIdtive(String roleId,String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据用户查询仓库授权信息
	 * @param userId
	 * @return List<Map<String, Object>>
	 * @develop date 2016.09.09
	 */
	public List<Store> findStoreByUserId(String userId);
	
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
	List<Store> selectByDprtcode(String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据条件查询所有有效仓库数据
	 * @param
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	List<Store> findAlltives(Store store);
	
	/**
	 * @author liub
	 * @description 检查仓库是否存在
	 * @param ckh(仓库号),ckmc(仓库名称)
	 * @return>
	 * @develop date 2016.09.14
	 */
	public void checkStore(String ckh,String ckmc,String id,String dprtcode) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位
	 * @param pdid
	 * @return List<Storage>
	 * @develop date 2016.11.22
	 */
	public List<Storage> queryStorageListByPdid(String pdid);

	public Storage selectByIdWithRel(String kwid);
	
	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位下拉框(新增盘点差异)
	 * @param pdid
	 * @return List<Storage>
	 * @develop date 2016.11.24
	 */
	public List<Storage> queryStorageSelectByPdid(String pdid);
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017年12月6日 上午11:06:16
	 * @CreateBy 岳彬彬
	 * @param store
	 * @param whrq
	 * @return
	 */
	List<Store> doExport(Store store,String whrq);
}
