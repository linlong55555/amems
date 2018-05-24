package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialRequisitionDetailWorkOrder;

public interface MaterialRequisitionDetailWorkOrderMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteByMainId(String mainId);
    
    int deleteByMainIds(List<String> mainIdList);
    
    int insert(MaterialRequisitionDetailWorkOrder record);

    int insertSelective(MaterialRequisitionDetailWorkOrder record);
    
    int insertMapSelective(Map map);

    MaterialRequisitionDetailWorkOrder selectByPrimaryKey(String id);
    
    /**
     * 根据领料申请单ID查询所有航材的工单
     * @param id 领料申请单ID
     * @return
     */
    List<MaterialRequisitionDetailWorkOrder> selectByMainMainId(String id);

    int updateByPrimaryKeySelective(MaterialRequisitionDetailWorkOrder record);

    int updateByPrimaryKey(MaterialRequisitionDetailWorkOrder record);
    
    /**
     * 根据mainId和需要保留的ID删除
     * @param mainId
     * @param ids  List<String>
     * @return
     */
    int deleteByMainIdNotIds(Map<String, Object> map);
}