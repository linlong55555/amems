package com.eray.thjw.training.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.BusinessMapper;
import com.eray.thjw.training.dao.BusinessToMaintenancePersonnelMapper;
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.training.po.PlanPerson;
import com.eray.thjw.training.service.BusinessService;
import com.eray.thjw.training.service.BusinessToMaintenancePersonnelService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("businessToMaintenancePersonnelService")
public class BusinessToMaintenancePersonnelServiceImpl implements BusinessToMaintenancePersonnelService {

	@Resource
	private BusinessToMaintenancePersonnelMapper businessToMaintenancePersonnelMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	@Resource
	private CommonRecService commonRecService;
	@Override
	public void deleteByPrimaryKey(String id) throws BusinessException{
		businessToMaintenancePersonnelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(BusinessToMaintenancePersonnel businessPer)throws BusinessException{
		
		
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		businessPer.setId(uuid.toString());
		businessPer.setWhrid(user.getId());
		businessPer.setWhsj(new Date());
		businessPer.setWhbmid(user.getJgdm());
		businessPer.setDprtcode(user.getJgdm());
		businessPer.setZt(1);
		businessToMaintenancePersonnelMapper.insertSelective(businessPer);
	}

	@Override
	public BusinessToMaintenancePersonnel selectByPrimaryKey(String id)throws BusinessException {
		return businessToMaintenancePersonnelMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(BusinessToMaintenancePersonnel businessPer) throws BusinessException{
		businessToMaintenancePersonnelMapper.updateByPrimaryKeySelective(businessPer);
	}

	@Override
	public Map<String, Object> queryAllPageList(BusinessToMaintenancePersonnel businessPer) throws BusinessException{
		String id = businessPer.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		businessPer.setId(null);
		
		PageHelper.startPage(businessPer.getPagination());
		List<BusinessToMaintenancePersonnel> list = businessToMaintenancePersonnelMapper.queryAllPageList(businessPer);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BusinessToMaintenancePersonnel businessPer1 = new BusinessToMaintenancePersonnel();
					businessPer1.setId(id);
					List<BusinessToMaintenancePersonnel> newRecordList = businessToMaintenancePersonnelMapper.queryAllPageList(businessPer1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, businessPer.getPagination());
		}else{
			List<BusinessToMaintenancePersonnel> newRecordList = new ArrayList<BusinessToMaintenancePersonnel>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BusinessToMaintenancePersonnel businessPer1 = new BusinessToMaintenancePersonnel();
				businessPer1.setId(id);
				newRecordList = businessToMaintenancePersonnelMapper.queryAllPageList(businessPer1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, businessPer1.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, businessPer.getPagination());
		}
	}

	@Override
	public List<BusinessToMaintenancePersonnel> queryByGwids(List<String> gwids)
			throws BusinessException {
		return businessToMaintenancePersonnelMapper.queryByGwids(gwids);
	}

	/**
	 * 根据维修档案id查询数据
	 * @param wxrydaid
	 * @return
	 */
	public List<BusinessToMaintenancePersonnel> queryAllBywxrydaid(
			String wxrydaid) throws BusinessException {
		return businessToMaintenancePersonnelMapper.queryAllBywxrydaid(wxrydaid);
	}

	/**
	 * 根据ids进行批量添加
	 * @param businessToMaintenancePersonnel
	 * @return
	 */
	public void batchInsert(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		//需要添加的UserIdList 
		List<String> ids=businessToMaintenancePersonnel.getIds();
		BusinessToMaintenancePersonnel businessPer = new BusinessToMaintenancePersonnel();
		businessPer.setGwid(businessToMaintenancePersonnel.getGwid());
		//根据岗位id查询旧数据
		List<BusinessToMaintenancePersonnel> oldList = businessToMaintenancePersonnelMapper.queryAllPageList(businessPer);
		boolean b;
		for (String wxrydaid : ids) {
			b = false;
			businessToMaintenancePersonnel.setWxrydaid(wxrydaid);
			businessToMaintenancePersonnel.setWhbmid(user.getBmdm());
			businessToMaintenancePersonnel.setWhrid(user.getId());
			businessToMaintenancePersonnel.setWhsj(new Date());
			businessToMaintenancePersonnel.setZt(1);
			//循环遍历 旧数据是否有新数据 并判断机型是否相同，如果相同 进行修改操作，如果不同 新增
			for (BusinessToMaintenancePersonnel bp : oldList) {
				if(("".equals(businessToMaintenancePersonnel.getFjjx()) &&
				   bp.getFjjx() == null &&	bp.getWxrydaid().equals( businessToMaintenancePersonnel.getWxrydaid())) ||
				   (businessToMaintenancePersonnel.getFjjx().equals(bp.getFjjx()) && bp.getWxrydaid().equals(businessToMaintenancePersonnel.getWxrydaid()))){
					businessToMaintenancePersonnel.setId(bp.getId());
					b = true;
				}
			}
			if(b){
				businessToMaintenancePersonnelMapper.updateByPrimaryKeySelective(businessToMaintenancePersonnel);
				commonRecService.write(businessToMaintenancePersonnel.getId(), TableEnum.B_P_006, user.getId(), czls, businessToMaintenancePersonnel.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,businessToMaintenancePersonnel.getId());
			}else{
				String id = UUID.randomUUID().toString();//获取随机的uuid
				businessToMaintenancePersonnel.setId(id);
				businessToMaintenancePersonnelMapper.insertSelective(businessToMaintenancePersonnel);
				commonRecService.write(id, TableEnum.B_P_006, user.getId(), czls, businessToMaintenancePersonnel.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,id);
			}
	
		}
		
		
	}
	/**
	 * 作废
	 * @param id
	 * @return
	 */
	public void deleteInfo(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		commonRecService.write(id, TableEnum.B_P_006, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
		businessToMaintenancePersonnelMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int selectByPrimarygwwxId(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)throws BusinessException {
		return businessToMaintenancePersonnelMapper.selectByPrimarygwwxId(businessToMaintenancePersonnel);
	}

	@Override
	public void deleteByPrimaryBt(BusinessToMaintenancePersonnel businessToMaintenancePersonnel) {
		businessToMaintenancePersonnelMapper.deleteByPrimaryBt(businessToMaintenancePersonnel);
	}

	@Override
	public List<BusinessToMaintenancePersonnel> queryAllPage(
			BusinessToMaintenancePersonnel businessPer)
			throws BusinessException {
		return businessToMaintenancePersonnelMapper.queryAllPageList(businessPer);
	}

	/**
	 * 
	 * @Description 根据条件查询岗位人员课程跟踪
	 * @CreateTime 2018-2-1 下午3:59:46
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAll(BusinessToMaintenancePersonnel businessToMaintenancePersonnel)
			throws BusinessException {
		PageHelper.startPage(businessToMaintenancePersonnel.getPagination());
		List<BusinessToMaintenancePersonnel> list = businessToMaintenancePersonnelMapper.queryAll(businessToMaintenancePersonnel);
		return PageUtil.pack4PageHelper(list, businessToMaintenancePersonnel.getPagination());
	}
	/**
	 * 
	 * @Description 根据条件查询岗位人员课程跟踪(不分页)
	 * @CreateTime 2018-2-1 下午3:59:46
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<BusinessToMaintenancePersonnel> queryAllList(
			BusinessToMaintenancePersonnel businessToMaintenancePersonnel)
			throws BusinessException {
		return businessToMaintenancePersonnelMapper.queryAll(businessToMaintenancePersonnel);
	}


}
