package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.MaterialRequisition;

public interface MaterialRequisitionMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaterialRequisition record);

    int insertSelective(MaterialRequisition record);

    MaterialRequisition selectByPrimaryKey(String id);
    
    /**
     * 查询用户保存状态的航材领用单
     * @param userId 用户ID
     * @return
     */
    List<MaterialRequisition> selectUserSaved(String userId);

    int updateByPrimaryKeySelective(MaterialRequisition record);

    int updateByPrimaryKey(MaterialRequisition record);
    
    /**
     * 分页查询领用申请单
     * @param materialRequisition
     * @return
     */
    List<MaterialRequisition> queryPage(MaterialRequisition materialRequisition);
    
    /**
	 * 按条件查询一页库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<MaterialRequisition> queryAllPageList(MaterialRequisition materialRequisition)  throws RuntimeException;
	
}