package com.eray.thjw.training.dao;

import com.eray.thjw.training.po.PersonnelTrainingRecently;

public interface PersonnelTrainingRecentlyMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelTrainingRecently record);

    int insertSelective(PersonnelTrainingRecently record);

    PersonnelTrainingRecently selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelTrainingRecently record);

    int updateByPrimaryKey(PersonnelTrainingRecently record);
}