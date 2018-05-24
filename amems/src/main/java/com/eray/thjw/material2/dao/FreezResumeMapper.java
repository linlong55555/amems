package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.FreezResume;

public interface FreezResumeMapper {
    int deleteByPrimaryKey(String id);

    int insert(FreezResume record);

    int insertSelective(FreezResume record);

    FreezResume selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezResume record);

    int updateByPrimaryKey(FreezResume record);
    
    /**
     * 
     * @Description 根据库存kcid查询数据
     * @CreateTime 2018-3-23 下午2:35:34
     * @CreateBy 孙霁
     * @param kcid
     * @return
     */
    List<FreezResume> selectByKcid(String kcid);
}