package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialRequisition;

/**
 * 
 * 航材领用业务接口
 * @author xu.yong
 *
 */
public interface MaterialRequisitionService {

	/**
	 * 查询用户保存状态的领用申请单 
	 * @return
	 */
	public MaterialRequisition query();
	
	/**
	 * 根据ID查询领用申请单
	 * @return
	 */
	public MaterialRequisition queryById(String id);
	
	/**
	 * 判断用户是否有保存状态的领用申请单
	 * @return
	 */
	public boolean hasSaved();
	
	/**
	 * 添加领用航材
	 * @param kcid 界面领用的航材库存ID
	 */
	public void addMaterial(String kcid);
	
	/**
	 * 修改领用申请单
	 * @param materialRequisition 领用申请单信息
	 * @param modifyList 修改的航材清单
	 * @param idList 删除清单
	 */
	public void save(MaterialRequisition materialRequisition, List<Map> modifyList, List<String> idList);
	
	/**
	 * 提交领用申请单
	 * @param id 领用申请单ID
	 */
	public void saveSubmit(String id);
	
	/**
	 * 更新
	 * @param id 
	 * @param zt
	 */
	public void updateState(String id, Integer zt);
	
	/**
	 * 分页查询领用申请单
	 * @param materialRequisition
	 * @return
	 */
	public List<MaterialRequisition> queryPage(MaterialRequisition materialRequisition);
	
	/**
	 * 按条件查询一页库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaterialRequisition> queryAllPageList(MaterialRequisition materialRequisition)  throws RuntimeException;
	
	public MaterialRequisition selectByPrimaryKey(String xgdjid);

	public void updateByPrimaryKeySelective(MaterialRequisition materialRequisition1);
	
	public void saveClose(String id, String zdjsyy);
	
}
