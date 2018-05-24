package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.MaintenanceClassMapper;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.MaintenanceClass;
import com.eray.thjw.project2.service.MaintenanceClassService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.ordersource.OrderSourceEnum;
/**
 * 
 * @Description 
 * @CreateTime 2017-8-17 上午11:24:24
 * @CreateBy 孙霁
 */
@Service
public class MaintenanceClassServiceImpl implements MaintenanceClassService {

	@Resource
	private MaintenanceClassMapper maintenanceClassMapper;
	@Resource
	private CommonRecService commonRecService;

	/**
	 * 
	 * @Description 根据飞机机型查询维修方案大类(弹窗)
	 * @CreateTime 2017-8-17 上午11:22:44
	 * @CreateBy 韩武
	 * @param maintenanceClass
	 * @return
	 */
	@Override
	public List<MaintenanceClass> queryWinByFjjx(MaintenanceClass maintenanceClass) {
		return maintenanceClassMapper.queryWinByFjjx(maintenanceClass);
	}

	/**
	 * 
	 * @Description 根据飞机机型查询维修方案大类(分页)
	 * @CreateTime 2017-8-17 上午11:22:56
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAll(MaintenanceClass maintenanceClass)throws BusinessException{
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String id=maintenanceClass.getId();
			maintenanceClass.setId("");
			try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(maintenanceClass.getPagination());
			List<MaintenanceClass> list=maintenanceClassMapper.queryAll(maintenanceClass);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						MaintenanceClass newRecord = new MaintenanceClass();
						newRecord.setId(id);
						List<MaintenanceClass> newRecordList = maintenanceClassMapper.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				resultMap=PageUtil.pack4PageHelper(list, maintenanceClass.getPagination());
				return resultMap;
				
			}else{
				List<MaintenanceClass> newRecordList = new ArrayList<MaintenanceClass>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					MaintenanceClass newRecord = new MaintenanceClass();
					newRecord.setId(id);
					newRecordList = maintenanceClassMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, maintenanceClass.getPagination());
						return resultMap;
					}
					
				}
				resultMap= PageUtil.pack(0, newRecordList, maintenanceClass.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-8-17 上午11:23:00
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @throws BusinessException
	 */
	public void insert(MaintenanceClass maintenanceClass)
			throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		String maintenanceClassId = UUID.randomUUID().toString();
		maintenanceClass.setId(maintenanceClassId);
		maintenanceClass.setDprtcode(user.getJgdm());
		maintenanceClass.setWhdwid(user.getBmdm());
		maintenanceClass.setWhsj(new Date());
		maintenanceClass.setWhrid(user.getId());
		maintenanceClass.setZt(EffectiveEnum.YES.getId());
		//验证唯一
		int count=maintenanceClassMapper.findBydlbh(maintenanceClass);
		if(count > 0){
			throw new BusinessException("编号在该机型中已存在！");
		}
		maintenanceClassMapper.insertSelective(maintenanceClass);
		//添加历史数据表
		 commonRecService.write(maintenanceClassId, TableEnum.B_G2_01101, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,maintenanceClassId);
	}

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-8-17 上午11:23:08
	 * @CreateBy 孙霁
	 * @param maintenanceClass
	 * @throws BusinessException
	 */
	public void update(MaintenanceClass maintenanceClass)
			throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		maintenanceClass.setWhdwid(user.getBmdm());
		maintenanceClass.setWhsj(new Date());
		maintenanceClass.setWhrid(user.getId());
		//验证状态是否发生改变
		int [] iStatus = {EffectiveEnum.YES.getId()};
		boolean b = this.verification(maintenanceClass.getId(), iStatus);
		if(b){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		//验证唯一
		int count = maintenanceClassMapper.findBydlbh(maintenanceClass);
		if(count > 0){
			throw new BusinessException("编号在该机型中已存在！");
		}
		maintenanceClassMapper.updateByPrimaryKeySelective(maintenanceClass);
		//添加历史数据表
		commonRecService.write(maintenanceClass.getId(), TableEnum.B_G2_01101, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,maintenanceClass.getId());
	}

	/**
	 * 
	 * @Description 作废
	 * @CreateTime 2017-8-17 上午11:23:14
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	public void delete(String id) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		MaintenanceClass maintenanceClass = new MaintenanceClass ();
		maintenanceClass.setId(id);
		maintenanceClass.setZt(EffectiveEnum.NO.getId());
		maintenanceClassMapper.updateByPrimaryKeySelective(maintenanceClass);
		//添加历史数据表
		commonRecService.write(id, TableEnum.B_G2_01101, user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,id);
	}
	/**
	 * 
	 * @Description 验证状态是否改变
	 * @CreateTime 2017-8-17 下午2:07:46
	 * @CreateBy 孙霁
	 * @param id
	 * @param iStatus
	 * @return
	 */
	public boolean verification(String id, int [] iStatus){
		boolean b=true;
		MaintenanceClass maintenanceClass=maintenanceClassMapper.selectByPrimaryKey(id);
		for (int a = 0; a < iStatus.length; a++) {
			if(maintenanceClass.getZt() == iStatus[a]){
				b = false;
			}
		}
		
		return b;
	}
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-9-14 下午5:55:33
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public MaintenanceClass selectById(String id) throws BusinessException {
		return maintenanceClassMapper.selectByPrimaryKey(id);
	}
}
