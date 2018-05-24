package com.eray.thjw.aerialmaterial.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eray.thjw.aerialmaterial.dao.StorageMapper;
import com.eray.thjw.aerialmaterial.dao.StoreMapper;
import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.aerialmaterial.service.StoreService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


/**
 * @author liub
 * @description 仓库service,用于业务逻辑处理
 * @develop date 2016.09.09
 */
@Service
@Transactional(readOnly = true)
public class StoreServiceImpl implements StoreService{
	
	/**
	 * @author liub
	 * @description 仓库Mapper
	 * @develop date 2016.09.09
	 */
	@Autowired
	private StoreMapper storeMapper;
	
	/**
	 * @author liub
	 * @description 仓库库位Mapper
	 * @develop date 2016.09.14
	 */
	@Autowired
	private StorageMapper storageMapper;
	
	/**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.09.14
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	/**
	 * @author liub
	 * @description 增加仓库
	 * @param store
	 * @develop date 2016.09.14
	 * @throws BusinessException 
	 */
	@Override
	public String add(Store store) throws BusinessException{
		checkStore(store.getCkh(), store.getCkmc(),null,store.getDprtcode());//检查仓库是否存在
		String czls = UUID.randomUUID().toString();//操作流水
		//新增仓库信息
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		store.setId(id);
		store.setZt(DelStatus.TAKE_EFFECT.getId());
		store.setDprtcode(user.getJgdm());
		store.setCjrid(user.getId());
		store.setBmid(user.getBmdm());
		storeMapper.insertSelective(store);
		commonRecService.write(id, TableEnum.D_009, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);//保存历史记录信息
		//新增库位信息
		List<String> columnValueList = new ArrayList<String>();
		for(Storage storage : store.getStorageList()){
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid2
			String storageId = uuid2.toString();
			storage.setId(storageId);
			storage.setMainid(id);
			storage.setCkh(store.getCkh());
			storage.setCklb(store.getCklb());
			storage.setZt(DelStatus.TAKE_EFFECT.getId());
			storage.setDprtcode(user.getJgdm());
			storage.setCjrid(user.getId());
			storage.setBmid(user.getBmdm());
			columnValueList.add(storageId);
			storageMapper.insertSelective(storage);
		}
		if(columnValueList.size() > 0){
			commonRecService.write("id", columnValueList, TableEnum.D_00901, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);//保存历史记录信息
		}
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改仓库
	 * @param store
	 * @develop date 2016.09.14
	 * @throws BusinessException 
	 */
	@Override
	public void edit(Store store) throws BusinessException{
		checkStore(store.getCkh(), store.getCkmc(),store.getId(),store.getDprtcode());//检查仓库是否存在
		String czls = UUID.randomUUID().toString();//操作流水
		List<String> oldStorageIdList = new ArrayList<String>();
		List<String> newStorageIdList = new ArrayList<String>();
		List<Storage> oldStorageList = storageMapper.queryStorageListByStoreId(store.getId());
		for (Storage storage : oldStorageList) {
			oldStorageIdList.add(storage.getId());
		}
		//修改仓库信息
		User user = ThreadVarUtil.getUser();
		store.setCjrid(user.getId());
		store.setBmid(user.getBmdm());
		storeMapper.updateByPrimaryKeySelective(store);
		commonRecService.write(store.getId(), TableEnum.D_009, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, store.getId());//保存历史记录信息
		//新增或修改库位信息
		for(Storage storage : store.getStorageList()){
			newStorageIdList.add(storage.getId());
			if(null != storage.getId() && !"".equals(storage.getId()) && oldStorageIdList.contains(storage.getId())){
				storage.setCjrid(user.getId());
				storage.setBmid(user.getBmdm());
				storage.setCkh(store.getCkh());
				storage.setCklb(store.getCklb());
				storageMapper.updateByPrimaryKeySelective(storage);
				commonRecService.write(storage.getId(), TableEnum.D_00901, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, store.getId());//保存历史记录信息
			}else{
				UUID uuid2 = UUID.randomUUID();//获取随机的uuid2
				String storageId = uuid2.toString();
				storage.setId(storageId);
				storage.setMainid(store.getId());
				storage.setCkh(store.getCkh());
				storage.setCklb(store.getCklb());
				storage.setZt(DelStatus.TAKE_EFFECT.getId());
				storage.setDprtcode(user.getJgdm());
				storage.setCjrid(user.getId());
				storage.setBmid(user.getBmdm());
				storageMapper.insertSelective(storage);
				commonRecService.write(storageId, TableEnum.D_00901, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, store.getId());//保存历史记录信息
			}
		}
		//删除库位信息
		for (String oldStorageId : oldStorageIdList) {
			if(!newStorageIdList.contains(oldStorageId)){
				Storage storage = new Storage();
				storage.setId(oldStorageId);
				storage.setCjrid(user.getId());
				storage.setBmid(user.getBmdm());
				storage.setZt(DelStatus.LOSE_EFFECT.getId());
				storageMapper.updateByPrimaryKeySelective(storage);
				commonRecService.write(storage.getId(), TableEnum.D_00901, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, store.getId());//保存历史记录信息
			}
		}
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param ids
	 * @return
	 * @develop date 2016.09.18
	 */
	@Override
	public void cancel(String ids) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		String[] idsArr = ids.split(",");
		for(String id : idsArr){
			Store store = new Store();
			store.setId(id);
			store.setZt(DelStatus.LOSE_EFFECT.getId());
			store.setCjrid(user.getId());
			store.setBmid(user.getBmdm());
			storeMapper.updateByPrimaryKeySelective(store);
			commonRecService.write(id, TableEnum.D_009, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, id);//保存历史记录信息
			storageMapper.updateByMainid(id);
			List<String> mainIdList = new ArrayList<String>();
			mainIdList.add(id);
			commonRecService.write("MAINID", mainIdList, TableEnum.D_00901, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, id);//保存历史记录信息
		}
	}
	
	/**
	 * @author liub
	 * @description 根据主键id查询仓库信息
	 * @param store
	 * @return Store
	 * @develop date 2016.09.14
	 */
	public Store selectByPrimaryKey(String id){
		return storeMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @author liub
	 * @description 根据主键id查询仓库及用户信息
	 * @param id
	 * @return Store
	 * @develop date 2016.09.14
	 */
	public Store selectJoinUserById(String id){
		return storeMapper.selectJoinUserById(id);
	}
	
	/**
	 * @author liub
	 * @description 根据仓库id查询仓库库位
	 * @param id
	 * @return List<Storage>
	 * @develop date 2016.09.14
	 */
	public List<Storage> queryStorageListByStoreId(String storeId){
		return storageMapper.queryStorageListByStoreId(storeId);
	}
	
	/**
	 * @author liub
	 * @description 根据仓库号查询仓库库位
	 * @param storeCode
	 * @return List<Storage>
	 * @develop date 2016.09.18
	 */
	public List<Storage> queryStorageListByStoreCode(String storeCode,String dprtcode){
		return storageMapper.queryStorageListByStoreCode(storeCode,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询仓库信息
	 * @param store
	 * @return List<Store>
	 * @develop date 2016.09.12
	 */
	@Override
	public List<Store> queryAllPageList(Store store){
		return storeMapper.queryAllPageList(store);
	}

	/**
	 * @author liub
	 * @description 查询所有的仓库数据,级联查询选中标记
	 * @param roleId，dprtcode
	 * @return List<Map<String, Object>>
	 * @develop date 2016.09.09
	 */
	@Override
	public List<Map<String, Object>> findAllByRoleIdtive(String roleId,String dprtcode){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Store> fList = storeMapper.findAllByRoleIdtive(roleId,dprtcode);
		for (Store store : fList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", store.getId());
			map.put("ckh", store.getCkh());
			map.put("ckmc", store.getCkmc());
			map.put("dprtcode", store.getDprtcode());
			map.put("selectId", store.getSelectId());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据用户查询仓库授权信息
	 * @param userId
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	@Override
	public List<Store> findStoreByUserId(String userId){
		return storeMapper.findStoreByUserId(userId);
	}
	
	/**
	 * @author liub
	 * @description 查询所有的仓库数据
	 * @param
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	@Override
	public List<Store> findAll(){
		return storeMapper.findAll();
	}
	
	/**
	 * @author liub
	 * @description 查询所有有效仓库数据
	 * @param
	 * @return List<Store>
	 * @develop date 2016.09.09
	 */
	@Override
	public List<Store> findAlltive(){
		return storeMapper.findAlltive();
	}
	
	/**
	 * @author liub
	 * @description 根据操作人的机构代码查询有效仓库数据
	 * @param dprtcode
	 * @return List<Store>
	 * @develop date 2016.12.23
	 */
	@Override
	public List<Store> selectByDprtcode(String dprtcode){
		return storeMapper.selectByDprtcode(dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位
	 * @param pdid
	 * @return List<Storage>
	 * @develop date 2016.11.22
	 */
	@Override
	public List<Storage> queryStorageListByPdid(String pdid){
		return storageMapper.queryStorageListByPdid(pdid);
	}
	
	/**
	 * @author liub
	 * @description 根据盘点id查询仓库库位下拉框(新增盘点差异)
	 * @param pdid
	 * @return List<Storage>
	 * @develop date 2016.11.24
	 */
	@Override
	public List<Storage> queryStorageSelectByPdid(String pdid){
		return storageMapper.queryStorageSelectByPdid(pdid);
	}
	
	/**
	 * @author liub
	 * @description 检查仓库是否存在
	 * @param ckh(仓库号),ckmc(仓库名称)
	 * @return
	 * @develop date 2016.09.14
	 */
	@Override
	public void checkStore(String ckh,String ckmc,String id,String dprtcode)throws BusinessException{
		StringBuffer message = new StringBuffer();
		List<Store> storeList = storeMapper.checkStore(ckh, ckmc , dprtcode);
		for (Store store : storeList) {
			if(null != id && id.equals(store.getId())){
				continue;
			}
			if(null != ckh && ckh.equals(store.getCkh())){
				message.append("仓库编号[").append(ckh).append("]已存在!");
			}
			if(null != ckmc && ckmc.equals(store.getCkmc())){
				message.append("仓库名称[").append(ckmc).append("]已存在!");
			}
		}
		if(message.length() > 0){
			throw new BusinessException(message.toString());
		}
	}
	
	@Override
	public List<Store> findAlltives(Store store){
		return storeMapper.findAlltives(store);
	}

	@Override
	public Storage selectByIdWithRel(String kwid){
		return storageMapper.selectByIdWithRel(kwid);
	}
	
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017年12月6日 上午11:06:16
	 * @CreateBy 岳彬彬
	 * @param store
	 * @param whrq
	 * @return
	 */
	@Override
	public List<Store> doExport(Store store, String whrq) {
		if(null != whrq && !"".equals(whrq)){
			store.getParamsMap().put("whrqBegin", whrq.substring(0, 10).concat(" 00:00:00"));
			store.getParamsMap().put("whrqEnd", whrq.substring(13, 23).concat(" 23:59:59"));		
		}
		if(null != store.getKeyword() && !"".equals(store.getKeyword())){
			String keyword = store.getKeyword();
			try {
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			if(keyword.contains("'")){
				keyword=keyword.replace("'", "'|| chr(39) ||'");
			}
			store.setKeyword(keyword);
		}
		List<Store> storeList = storeMapper.queryAllPageList(store);
		if(null != storeList && storeList.size()>0){
			List<String> idList = new ArrayList<String>();
			for (Store s : storeList) {
				idList.add(s.getId());
			}
			List<Storage> storageList = storageMapper.getByMainidList(idList);
			StringBuffer sbf ;
			for (Store s : storeList) {
				sbf = new StringBuffer("");
				for (Storage storage : storageList) {
					if(s.getId().equals(storage.getMainid())){
						sbf.append(storage.getKwh()).append(",");
					}
				}
				if(!"".equals(sbf.toString())){
					s.getParamsMap().put("storage", sbf.subSequence(0, sbf.length()-1));
				}			
			}
		}
		return storeList;
	}
}
