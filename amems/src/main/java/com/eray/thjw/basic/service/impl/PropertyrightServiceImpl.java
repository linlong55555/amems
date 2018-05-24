package com.eray.thjw.basic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.PropertyrightMapper;
import com.eray.thjw.basic.dao.StageMapper;
import com.eray.thjw.basic.po.Propertyright;
import com.eray.thjw.basic.po.Stage;
import com.eray.thjw.basic.service.PropertyrightService;
import com.eray.thjw.basic.service.StageService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/** 
 * @Description 产权数据管理业务逻辑接口
 * @CreateTime 2018-2-5 上午10:56:14
 * @CreateBy 孙霁
 */
@Service
public class PropertyrightServiceImpl implements  PropertyrightService {
	
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private PropertyrightMapper propertyrightMapper;

	@Override
	public Map<String, Object> queryAll(Propertyright propertyright)
			throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=propertyright.getId();
		propertyright.setId("");
		try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(propertyright.getPagination());
			List<Propertyright> list=propertyrightMapper.queryAll(propertyright);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Propertyright newRecord = new Propertyright();
						newRecord.setId(id);
						List<Propertyright> newRecordList = propertyrightMapper.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				resultMap=PageUtil.pack4PageHelper(list, propertyright.getPagination());
				return resultMap;
				
			}else{
				List<Propertyright> newRecordList = new ArrayList<Propertyright>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Propertyright newRecord = new Propertyright();
					newRecord.setId(id);
					newRecordList = propertyrightMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, propertyright.getPagination());
						return resultMap;
					}
					
				}
				resultMap= PageUtil.pack(0, newRecordList, propertyright.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}

	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018-2-5 上午11:33:36
	 * @CreateBy 孙霁
	 * @param propertyright
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insert(Propertyright propertyright) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		String id = UUID.randomUUID().toString();
		//操作指令
		LogOperationEnum cz = LogOperationEnum.SAVE_WO;
		propertyright.setDprtcode(user.getJgdm());
		//验证唯一
		int count=propertyrightMapper.findByCqbh(propertyright);
		if(count > 0){
			throw new BusinessException("产权编号出现重复,请修改产权编号后再进行操作！");
		}
		//1.添加表信息
		propertyright.setId(id);
		propertyright.setWhrid(user.getId());
		propertyright.setWhbmid(user.getBmdm());
		propertyright.setWhsj(new Date());
		propertyright.setZt(EffectiveEnum.YES.getId());
		
		//添加主表信息
		propertyrightMapper.insertSelective(propertyright);
		//添加历史数据表
		 commonRecService.write(id, TableEnum.D_026, user.getId(), czls, cz, UpdateTypeEnum.SAVE,id);
		return id;
	}

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-2-5 上午11:34:01
	 * @CreateBy 孙霁
	 * @param propertyright
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(Propertyright propertyright) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		//操作标识
		LogOperationEnum cz = LogOperationEnum.EDIT;
		//验证唯一
		int count = propertyrightMapper.findByCqbh(propertyright);
		if(count > 0){
			throw new BusinessException("产权编号出现重复,请修改产权编号后再进行操作！");
		}
		//验证数据状态是否为有效状态
		Propertyright oldPropertyright = propertyrightMapper.selectByPrimaryKey(propertyright.getId());
		if(oldPropertyright.getZt() == EffectiveEnum.NO.getId()){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		//修改主表
		propertyright.setZdrid(user.getId());
		propertyright.setZdbmid(user.getBmdm());
		propertyrightMapper.updateByPrimaryKeySelective(propertyright);
		commonRecService.write(propertyright.getId(), TableEnum.D_026, user.getId(), czls, propertyright.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,propertyright.getId());
		return propertyright.getId();
	}

	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018-2-5 上午11:34:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String delete(String id) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		//操作标识
		LogOperationEnum cz = LogOperationEnum.DELETE;
		Propertyright propertyright = new Propertyright();
		propertyright.setId(id);
		propertyright.setZt(EffectiveEnum.NO.getId());
		propertyrightMapper.updateByPrimaryKeySelective(propertyright);
		commonRecService.write(propertyright.getId(), TableEnum.D_026, user.getId(), czls, cz, UpdateTypeEnum.DELETE,propertyright.getId());
		return id;
	}

	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-2-5 下午4:39:25
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Propertyright selectById(String id) throws BusinessException {
		return propertyrightMapper.selectByPrimaryKey(id);
	}


}
