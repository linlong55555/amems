package com.eray.thjw.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.BaseMaintenance;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.ContactPersonMapper;
import com.eray.thjw.system.po.ContactPerson;
import com.eray.thjw.system.service.ContactPersonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


@Service
public class ContactPersonServiceImpl implements ContactPersonService {
	@Autowired
	private ContactPersonMapper contactPersonMapper;
	
	@Resource
	private CommonRecService commonRecService;
	


	@Override
	public void insertBaseMaintenance(ContactPerson contactPerson)
			throws Exception {
		contactPersonMapper.insertSelective(contactPerson);
	}

	@Override
	public List<ContactPerson> selectBaseMaintenanceList(
			ContactPerson contactPerson) throws Exception {
		return contactPersonMapper.selectBaseMaintenanceList(contactPerson);
	}

	@Override
	public ContactPerson selectBaseMaintenanceById(String id) throws Exception {
		return contactPersonMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateBaseMaintenanceById(ContactPerson contactPerson)
			throws Exception {
		contactPersonMapper.updateByPrimaryKeySelective(contactPerson);
	}

	@Override
	public void deleteBaseMaintenanceById(String id) throws Exception {
		contactPersonMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int selectByJdms(ContactPerson contactPerson) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertContactPerson(ContactPerson contactPerson)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(ContactPerson contactPerson) {
		User user=ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		UUID czls = UUID.randomUUID();
		contactPerson.setId(uuid.toString());
		contactPerson.setZt(1);
		contactPerson.setWhsj(new Date());
		contactPerson.setWhrid(user.getId());
		contactPerson.setDprtcode(user.getJgdm());
		contactPersonMapper.insertSelective(contactPerson);
		
		commonRecService.write(uuid.toString(), TableEnum.D_016, user.getId(),czls.toString(),LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid.toString());
	}

	@Override
	public void update(ContactPerson contactPerson) {
		contactPersonMapper.updateByPrimaryKeySelective(contactPerson);
		UUID czls = UUID.randomUUID();
		User user=ThreadVarUtil.getUser();
		commonRecService.write(contactPerson.getId(), TableEnum.D_016, user.getId(),czls.toString(),LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,contactPerson.getId());
	}

	@Override
	public void cancel(ContactPerson contactPerson) {
		contactPerson.setZt(0);
		contactPersonMapper.updateByPrimaryKeySelective(contactPerson);
		UUID czls = UUID.randomUUID();
		User user=ThreadVarUtil.getUser();
		commonRecService.write(contactPerson.getId(), TableEnum.D_016, user.getId(),czls.toString(),LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,contactPerson.getId());
		
	}

	@Override
	public ContactPerson selectContactPersonById(String id) {
		return contactPersonMapper.selectByPrimaryKey(id);
	}

	/**
	 * @author liub
	 * @description 根据csid获取联系人信息
	 * @param csid
	 * @return List<ContactPerson>
	 */
	@Override
	public List<ContactPerson> queryListByCsId(String csid){
		return contactPersonMapper.queryListByCsId(csid);
	}

	@Override
	public Map<String, Object> selectPageList(ContactPerson contactPerson) {
		String id = contactPerson.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		contactPerson.setId(null);
		PageHelper.startPage(contactPerson.getPagination());
		List<ContactPerson> baseMaintenanceList = contactPersonMapper.selectBaseMaintenanceList(contactPerson);
		if(((Page)baseMaintenanceList).getTotal() > 0){
			//分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(baseMaintenanceList, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					ContactPerson baseMaintenance1 = new ContactPerson();
					baseMaintenance1.setId(id);
					List<ContactPerson> newRecordList = contactPersonMapper.selectBaseMaintenanceList(baseMaintenance1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						baseMaintenanceList.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(baseMaintenanceList, contactPerson.getPagination());		
		}
		else{
			List<ContactPerson> newRecordList = new ArrayList<ContactPerson>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				ContactPerson baseMaintenance2 = new ContactPerson();
				baseMaintenance2.setId(id);
				newRecordList = contactPersonMapper.selectBaseMaintenanceList(baseMaintenance2);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, contactPerson.getPagination());					
				}
			}
			return PageUtil.pack(0, newRecordList, contactPerson.getPagination());
		}
	}

}
