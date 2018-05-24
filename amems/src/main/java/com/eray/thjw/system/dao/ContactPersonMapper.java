package com.eray.thjw.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.system.po.ContactPerson;

public interface ContactPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactPerson record);

    int insertSelective(ContactPerson record);

    ContactPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactPerson record);

    int updateByPrimaryKey(ContactPerson record);

	List<ContactPerson> selectBaseMaintenanceList(ContactPerson contactPerson);

	List<ContactPerson> selectContactPersonList(ContactPerson baseMaintenance);
	
	/**
	 * @author liub
	 * @description 根据csid获取联系人信息
	 * @param csid
	 * @return List<ContactPerson>
	 */
	List<ContactPerson> queryListByCsId(String csid);
	
	/**
	 * @author liub
	 * @description 作废联系人信息
	 * @param id,whrid
	 */
	void cancel(@Param("id")String id,@Param("whrid")String whrid);
	
	/**
	 * 根据组织机构查询所有联系人
	 * @param dprtcode
	 * @return
	 */
	List<ContactPerson> queryByDprtcode(String dprtcode);
	
	/**
	 * 批量插入联系人
	 * @return
	 */
	int batchInsert(List<ContactPerson> persons);
	
	/**
	 * 批量修改联系人
	 * @return
	 */
	int batchUpdate(List<ContactPerson> persons);

}