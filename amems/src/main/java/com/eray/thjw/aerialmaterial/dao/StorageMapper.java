package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Storage;

public interface StorageMapper {

	/**
	 * @author liub
	 * @description 根据仓库id查询仓库库位
	 * @param id
	 * @return List<Storage>
	 * @develop date 2016.09.14
	 */
	List<Storage> queryStorageListByStoreId(String storeId);

	/**
	 * 根据库位ID查询 (带关系：仓库)
	 * 
	 * @param id
	 * @return
	 */
	Storage selectByIdWithRel(String id);

	/**
	 * @author liub
	 * @description 根据mainid修改仓库库位状态
	 * @param mainid
	 * @return int
	 * @develop date 2016.11.02
	 */
	int updateByMainid(String mainid);

	/**
	 * @author liub
	 * @param string 
	 * @description 根据仓库号查询仓库库位
	 * @param id
	 * @return List<Storage>
	 * @develop date 2016.09.18
	 */
	List<Storage> queryStorageListByStoreCode(String storeCode, String string);

	int insertSelective(Storage record);

	int updateByPrimaryKeySelective(Storage record);

	/**
	 * @author meizhiliang
	 * @description 根据库位号集合查询
	 * @param id
	 * @return List<Storage>
	 * @develop date 2016.11.19
	 */
	List<Storage> queryStorageList(List<String> kwids);

	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位
	 * @param pdid
	 * @return List<Storage>
	 * @develop date 2016.11.22
	 */
	List<Storage> queryStorageListByPdid(String pdid);

	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位下拉框(新增盘点差异)
	 * @param pdid
	 * @return List<Storage>
	 * @develop date 2016.11.24
	 */
	List<Storage> queryStorageSelectByPdid(String pdid);

	int batchMerge(List<Storage> list);

	Storage selectByPrimarykwh(String ckh, String kwh, String string);
	
	String getId(String mainid,String dprtcode,String kwh);
	
	String selectByMainidAndKwh(Storage record);
	/**
	 * 
	 * @Description 根据仓库集合获取下面所有的库位
	 * @CreateTime 2017年12月5日 下午2:04:16
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	List<Storage> getByMainidList(List<String> list);
}