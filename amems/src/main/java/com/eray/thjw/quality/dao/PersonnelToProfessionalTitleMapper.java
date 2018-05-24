package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.po.PersonnelToProfessionalTitle;

public interface PersonnelToProfessionalTitleMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelToProfessionalTitle record);

    int insertSelective(PersonnelToProfessionalTitle record);

    PersonnelToProfessionalTitle selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelToProfessionalTitle record);

    int updateByPrimaryKey(PersonnelToProfessionalTitle record);
    
    /**
     * 删除列表中不存在的数据
     * @param personnel
     * @return
     */
    int deleteNotExist(MaintenancePersonnel personnel);
    
    /**
     * 查询
     * @param id
     * @return
     */
    List<PersonnelToProfessionalTitle> query(String id);
    
    /**
     * @Description 根据组织机构查询
     * @CreateTime 2018年1月12日 上午11:13:29
     * @CreateBy 韩武
     * @param dprtcode
     * @return
     */
    List<PersonnelToProfessionalTitle> queryByDprtcode(String dprtcode);
}