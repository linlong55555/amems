package com.eray.thjw.material2.dao;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.ws.rs.PathParam;

import com.eray.thjw.material2.po.OutboundRelations;
/**
 * 
 * @Description 出库关系mapper
 * @CreateTime 2018年3月15日 下午3:12:49
 * @CreateBy 林龙
 */
public interface OutboundRelationsMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutboundRelations record);

    int insertSelective(OutboundRelations record);

    OutboundRelations selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutboundRelations record);

    int updateByPrimaryKey(OutboundRelations record);

	void deleteByCkid(String id);

	void delete4Batch(@PathParam("list")List<String> list);

	void updateByPrimaryKeySelectiveCk(OutboundRelations outboundRelations);
}