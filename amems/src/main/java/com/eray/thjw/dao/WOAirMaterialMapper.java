package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.WOAirMaterial;

public interface WOAirMaterialMapper {
   
	int deleteByPrimaryKey(String id);
//
//    int insert(WOAirMaterial record);

        int insertSelective(WOAirMaterial record);                  // 对工单航材耗材表执行增加操作

       List<WOAirMaterial> selectWOAirMaterialList(WOAirMaterial record);        //查询工单的航材和耗材
//
       int updateByPrimaryKeySelective(WOAirMaterial record);
//
    int updateByPrimaryKey(WOAirMaterial record);
        
        void save(WOAirMaterial record) throws Exception;	// 新增对工单航材耗材表执行增加操作
        
       List<WOAirMaterial> selectedHcList(WOAirMaterial wOAirMaterial);
       
       int doByAirMaterial(WOAirMaterial wOAirMaterial);
       
       int deleteById(String id);
       
       int updateByAirMaterial(WOAirMaterial wOAirMaterial);
}