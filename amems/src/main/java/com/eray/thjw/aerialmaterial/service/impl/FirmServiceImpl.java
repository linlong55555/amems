package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.FirmMapper;
import com.eray.thjw.aerialmaterial.po.Firm;
import com.eray.thjw.aerialmaterial.service.FirmService;
import com.eray.thjw.dao.CommonMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.system.dao.ContactPersonMapper;
import com.eray.thjw.system.po.ContactPerson;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 厂商service,用于业务逻辑处理
 */
@Service
public class FirmServiceImpl implements FirmService {

	/**
	 * @author liub
	 * @description 厂商Mapper
	 */
	@Resource
	private FirmMapper firmMapper;
	
	@Resource
	private CommonMapper commonMapper;

	/**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.09.20
	 */
	@Autowired
	private CommonRecService commonRecService;

	/**
	 * @author liub
	 * @description 附件service
	 */
	@Resource
	private AttachmentService attachmentService;

	/**
	 * @author liub
	 * @description 联系人Mapper
	 */
	@Autowired
	private ContactPersonMapper contactPersonMapper;

	/**
	 * @author liub
	 * @description 增加厂商
	 * @param firm
	 * @throws BusinessException
	 */
	@Override
	public String add(Firm firm) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		firm.setDprtcode(user.getJgdm());
		checkExist(firm);// 检查是否存在
		String czls = UUID.randomUUID().toString();// 操作流水
		// 新增厂商信息
		UUID uuid = UUID.randomUUID();// 获取随机的uuid
		String id = uuid.toString();
		firm.setId(id);
		firm.setZt(DelStatus.TAKE_EFFECT.getId());
		firm.setCjrid(user.getId());
		firmMapper.insertSelective(firm);
		commonRecService.write(id, TableEnum.D_015, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,
				id);// 保存历史记录信息
		attachmentService.eidtAttachment(firm.getAttachments(), firm.getId(), czls, firm.getId(), firm.getDprtcode(),
				LogOperationEnum.SAVE_WO);
		// 新增联系人信息
		if (null != firm.getContactPersonList() && firm.getContactPersonList().size() > 0) {
			List<String> columnValueList = new ArrayList<String>();
			for (ContactPerson contactPerson : firm.getContactPersonList()) {
				String personId = UUID.randomUUID().toString();// 获取随机的uuid
				contactPerson.setId(personId);
				contactPerson.setCsid(firm.getId());
				contactPerson.setGysmc(firm.getGysmc());
				contactPerson.setZt(DelStatus.TAKE_EFFECT.getId());
				contactPerson.setWhrid(user.getId());
				contactPerson.setDprtcode(user.getJgdm());
				contactPersonMapper.insertSelective(contactPerson);
				columnValueList.add(personId);
			}
			if (columnValueList.size() > 0) {
				commonRecService.write("id", columnValueList, TableEnum.D_016, user.getId(), czls,
						LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, firm.getId());// 插入日志
			}
		}
		return id;
	}

	/**
	 * @author liub
	 * @description 修改厂商
	 * @param firm
	 * @throws BusinessException
	 */
	@Override
	public void edit(Firm firm) throws BusinessException {
		checkExist(firm);// 检查是否存在
		String czls = UUID.randomUUID().toString();// 操作流水
		User user = ThreadVarUtil.getUser();
		firm.setCjrid(user.getId());
		firmMapper.updateByPrimaryKeySelective(firm);
		commonRecService.write(firm.getId(), TableEnum.D_015, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE, firm.getId());// 保存历史记录信息
		attachmentService.eidtAttachment(firm.getAttachments(), firm.getId(), czls, firm.getId(), firm.getDprtcode(),
				LogOperationEnum.EDIT);
		// 编辑联系人信息
		if (null != firm.getContactPersonList() && firm.getContactPersonList().size() > 0) {
			List<String> addColumnValueList = new ArrayList<String>();// 用于新增联系人id集合
			List<String> editColumnValueList = new ArrayList<String>();// 用于编辑联系人id集合
			for (ContactPerson contactPerson : firm.getContactPersonList()) {
				if (null == contactPerson.getId() || "".equals(contactPerson.getId())) {
					String personId = UUID.randomUUID().toString();// 获取随机的uuid
					contactPerson.setId(personId);
					contactPerson.setCsid(firm.getId());
					contactPerson.setGysmc(firm.getGysmc());
					contactPerson.setZt(DelStatus.TAKE_EFFECT.getId());
					contactPerson.setWhrid(user.getId());
					contactPerson.setDprtcode(firm.getDprtcode());
					contactPersonMapper.insertSelective(contactPerson);
					addColumnValueList.add(personId);
				} else {
					contactPerson.setWhrid(user.getId());
					contactPersonMapper.updateByPrimaryKeySelective(contactPerson);
					editColumnValueList.add(contactPerson.getId());
				}
			}
			if (addColumnValueList.size() > 0) {
				commonRecService.write("id", addColumnValueList, TableEnum.D_016, user.getId(), czls,
						LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, firm.getId());// 插入日志
			}
			if (editColumnValueList.size() > 0) {
				commonRecService.write("id", editColumnValueList, TableEnum.D_016, user.getId(), czls,
						LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, firm.getId());// 插入日志
			}
			if (null != firm.getDelIds() && firm.getDelIds().size() > 0) {
				for (String delId : firm.getDelIds()) {
					contactPersonMapper.cancel(delId, user.getId());
				}
				commonRecService.write("id", firm.getDelIds(), TableEnum.D_016, user.getId(), czls,
						LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, firm.getId());// 插入日志
			}
		}
	}

	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	@Override
	public void cancel(String id) {
		String czls = UUID.randomUUID().toString();// 操作流水
		User user = ThreadVarUtil.getUser();
		Firm firm = new Firm();
		firm.setId(id);
		firm.setZt(DelStatus.LOSE_EFFECT.getId());
		firm.setCjrid(user.getId());
		firmMapper.cancel(firm);
		commonRecService.write(id, TableEnum.D_015, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,
				id);// 保存历史记录信息
	}

	/**
	 * @author liub
	 * @description 根据查询条件分页查询供应商信息
	 * @param firm
	 * @return List<Firm>
	 * @develop date 2016.09.20
	 */
	@Override
	public List<Firm> queryAllPageList(Firm firm) {
		return firmMapper.queryAllPageList(firm);
	}

	/**
	 * @author liub
	 * @description 根据查询条件分页查询供应商信息(弹窗)
	 * @param record
	 * @return List<Map<String, Object>>
	 */
	@Override
	public List<Map<String, Object>> queryWinAllPageList(Firm firm) {
		return firmMapper.queryWinAllPageList(firm);
	}

	/**
	 * @author liub
	 * @description 根据主键id查询厂商信息
	 * @param id
	 * @return Firm
	 */
	@Override
	public Firm selectByPrimaryKey(String id) {
		return firmMapper.selectByPrimaryKey(id);
	}

	/**
	 * @author liub
	 * @description 根据查询条件查询供应商信息
	 * @param firm
	 * @return List<Firm>
	 */
	public List<Firm> queryFirmList(Firm firm) {
		return firmMapper.queryFirmList(firm);
	}

	/**
	 * @author liub
	 * @description 检查航材厂商是否存在
	 * @param firm
	 */
	public void checkExist(Firm firm) throws BusinessException {
		StringBuffer message = new StringBuffer();
		List<Firm> firmList = firmMapper.checkFirm(firm);
		for (Firm oldObj : firmList) {
			if (null != oldObj.getId() && oldObj.getId().equals(firm.getId())) {
				continue;
			}
			if (null != oldObj.getGysbm() && oldObj.getGysbm().equals(firm.getGysbm())) {
				message.append("供应商编号[").append(firm.getGysbm()).append("]已存在!");
			}
			if (null != oldObj.getGysmc() && oldObj.getGysmc().equals(firm.getGysmc())) {
				message.append("供应商名称[").append(firm.getGysmc()).append("]已存在!");
			}
		}
		if (message.length() > 0) {
			throw new BusinessException(message.toString());
		}
	}

	/**
	 * 
	 * @Description 获取组织机构下的有效的外委供应商
	 * @CreateTime 2017年9月27日 上午11:24:47
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	@Override
	public List<Firm> getwwFirmList(String dprtcode) {

		return firmMapper.getwwFirmList(dprtcode);
	}

	@Override
	public List<Firm> queryPageListPop(Firm firm) {
		Date currentDate = commonMapper.getSysdate();
		PageHelper.startPage(firm.getPagination());
		String currentDateStr = DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE,currentDate);
		firm.getParamsMap().put("currentDate", currentDateStr.concat(" 08:00:00"));
		List<Firm> list =  firmMapper.queryPageListPop(firm);
		return list;
	}

}
