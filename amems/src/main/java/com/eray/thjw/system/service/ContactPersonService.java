package com.eray.thjw.system.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.BaseMaintenance;
import com.eray.thjw.system.po.ContactPerson;

public interface ContactPersonService {
	/**
	 * 添加联系人
	 * @param baseMaintenance
	 * @throws Exception
	 */
	void insertBaseMaintenance(ContactPerson contactPerson)throws Exception;
	/**
	 * 查询联系人
	 * @param baseMaintenance
	 * @return
	 * @throws Exception
	 */
	List<ContactPerson> selectBaseMaintenanceList(ContactPerson contactPerson)throws Exception;

	/**
	 * 根据id获取联系人信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ContactPerson selectBaseMaintenanceById(String id)throws Exception;
	/**
	 * 修改联系人信息
	 * @param baseMaintenance
	 * @throws Exception
	 */
	void updateBaseMaintenanceById(ContactPerson contactPerson)throws Exception;
	/**
	 * 修改联系人状态
	 * @param id
	 * @throws Exception
	 */
	void deleteBaseMaintenanceById(String id)throws Exception;
	/**
	 * 查询联系法人是否存在
	 */
	int selectByJdms(ContactPerson contactPerson)throws Exception;
	
	void insertContactPerson(ContactPerson contactPerson) throws Exception;
	
	void save(ContactPerson contactPerson);
	void update(ContactPerson contactPerson);
	void cancel(ContactPerson contactPerson);
	ContactPerson selectContactPersonById(String id);
	
	/**
	 * @author liub
	 * @description 根据csid获取联系人信息
	 * @param csid
	 * @return List<ContactPerson>
	 */
	public List<ContactPerson> queryListByCsId(String csid);
	Map<String, Object> selectPageList(ContactPerson contactPerson);
}
