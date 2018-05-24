package com.eray.thjw.training.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.quality.po.Auditnotice;
import com.eray.thjw.training.po.BusinessToCourse;

public interface BusinessToCourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusinessToCourse record);

    int insertSelective(BusinessToCourse record);

    BusinessToCourse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusinessToCourse record);

    int updateByPrimaryKey(BusinessToCourse record);
    
    List<BusinessToCourse> queryAllBygwid(@Param("gwid")String gwid ,@Param("dprtcode")String dprtcode);
    
    /**
     * 
     * @Description 根据条件查询数量
     * @CreateTime 2018-1-10 下午3:42:26
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    int queryCount(BusinessToCourse record);
}