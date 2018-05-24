package com.eray.thjw.project2.dao;

import com.eray.thjw.project2.po.PersonnelReceipt;

public interface PersonnelReceiptMapper {
    int deleteByPrimaryKey(String id);

    int insert(PersonnelReceipt record);

    int insertSelective(PersonnelReceipt record);

    PersonnelReceipt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PersonnelReceipt record);

    int updateByPrimaryKey(PersonnelReceipt record);
    /**
     * 
     * @Description 根据业务id和接收人id查询数据
     * @CreateTime 2017年8月19日 上午11:49:17
     * @CreateBy 岳彬彬
     * @param ywid
     * @param jsrid
     * @return
     */
    PersonnelReceipt getPersonnelReceipt4Validation(String ywid,String jsrid);
}