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
import com.eray.thjw.training.po.Business;
import com.eray.thjw.training.po.BusinessToMaintenancePersonnel;
import com.eray.thjw.training.service.BusinessService;
import com.eray.thjw.training.service.BusinessToMaintenancePersonnelService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Resource
	private BusinessMapper businessMapper;
	@Resource
	private BusinessToMaintenancePersonnelService businessToMaintenancePersonnelService;
	@Resource
	private SaibongUtilService saibongUtilService;
	@Resource
	private CommonRecService commonRecService;
	
	@Override
	public void deleteByPrimaryKey(String id) throws BusinessException{
		 businessMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insertSelective(Business business)throws BusinessException{
		
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		business.setDprtcode(user.getJgdm());
		int i =businessMapper.selectBydgbh(business);
		if(i>0){
			throw new BusinessException("岗位编号已存在");
		}
		
		String id = UUID.randomUUID().toString();//获取随机的uuid
		business.setId(id);
		business.setWhrid(user.getId());
		business.setWhsj(new Date());
		business.setWhbmid(user.getJgdm());
		business.setZt(1);
		businessMapper.insertSelective(business);
		commonRecService.write(id, TableEnum.B_P_001, user.getId(), czls, business.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,business.getId());
	}

	@Override
	public Business selectByPrimaryKey(String id)throws BusinessException {
		return businessMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(Business business) throws BusinessException{
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int i =businessMapper.selectBydgbh(business);
		if(i>0){
			throw new BusinessException("岗位编号已存在");
		}
		business.setWhrid(user.getId());
		business.setWhsj(new Date());
		business.setWhbmid(user.getJgdm());
		 businessMapper.updateByPrimaryKeySelective(business);
		 commonRecService.write(business.getId(), TableEnum.D_017, user.getId(), czls, business.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,business.getId());
	}

	@Override
	public Map<String, Object> queryAllPageList(Business business) throws BusinessException{
		String id = business.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		business.setId(null);
		
		PageHelper.startPage(business.getPagination());
		List<Business> list = businessMapper.queryAllPageList(business);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Business business1 = new Business();
					business1.setId(id);
					//拼接人员
					assembly(list);
					List<Business> newRecordList = businessMapper.queryAllPageList(business1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			//拼接人员
			assembly(list);
			return PageUtil.pack4PageHelper(list, business.getPagination());
		}else{
			List<Business> newRecordList = new ArrayList<Business>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Business business1 = new Business();
				business1.setId(id);
				
				newRecordList = businessMapper.queryAllPageList(business1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, business1.getPagination());
				}
			}
			//拼接人员
			assembly(newRecordList);
			return PageUtil.pack(0, newRecordList, business.getPagination());
		}
	}
	
	/**
	 * @Description 查询课程岗位关系
	 * @CreateTime 2018-2-1 上午10:43:59
	 * @CreateBy 刘兵
	 * @param business 岗位
	 * @return List<Business> 岗位集合
	 */
	@Override
	public List<Business> queryByKc(Business business){
		return businessMapper.queryByKc(business);
	}
	
	public List<Business> assembly(List<Business> list)throws BusinessException{
		List<String> gwids=new ArrayList<String>();
		if(list.size()>0){
			for (Business business : list) {
				gwids.add(business.getId());
			}
			List<BusinessToMaintenancePersonnel> businessToMaintenancePersonnelList=businessToMaintenancePersonnelService.queryByGwids(gwids);
			
			for (Business business : list) {
				StringBuffer str=new StringBuffer();
				for (BusinessToMaintenancePersonnel businessToMaintenancePersonnel : businessToMaintenancePersonnelList) {
					if(business.getId().equals(businessToMaintenancePersonnel.getGwid())){
						
						str.append(businessToMaintenancePersonnel.getMaintenancePersonnel().getXm());
						str.append(",");
						business.getParamsMap().put("xm", str.substring(0,str.length() - 1));
						
						if(business.getBusinessToMaintenancePersonnels()==null){
							List<BusinessToMaintenancePersonnel> btmlist=new ArrayList<BusinessToMaintenancePersonnel>();
							btmlist.add(businessToMaintenancePersonnel);
					
							business.setBusinessToMaintenancePersonnels(btmlist);
						
						}else{
							business.getBusinessToMaintenancePersonnels().add(businessToMaintenancePersonnel);
							
						}
					}
				}
			}
			
			
		}
		
		return list;
	}

	/** sunji
	 * 查询所有状态为有效的数据（不分页）
	 * @return List<Business>
	 * @throws Exception 
	 */
	public List<Business> queryAllBusiness(Business business)
			throws BusinessException {
		return businessMapper.queryAllBusiness(business);
	}
	/**
	 * 
	 * @Description 根据岗位id和维修人员档案id查询数据
	 * @CreateTime 2018-3-27 下午2:16:32
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	public List<Business> queryAllResults(Business business)
			throws BusinessException {
		return businessMapper.queryAllResults(business);
	}

	/**
	 * 作废
	 * @param business
	 * @return
	 */
	public void invalid(Business business) throws BusinessException {
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		business.setZt(0);
		businessMapper.updateByPrimaryKeySelective(business);
		commonRecService.write(business.getId(), TableEnum.D_017, user.getId(), czls, business.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,business.getId());
		
	}



}
