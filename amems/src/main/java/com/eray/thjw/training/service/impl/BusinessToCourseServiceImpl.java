package com.eray.thjw.training.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.BusinessToCourseMapper;
import com.eray.thjw.training.po.BusinessToCourse;
import com.eray.thjw.training.service.BusinessToCourseService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
/**
 * @author sunji
 * @description 岗位To课程service,用于业务逻辑处理
 */
@Service
public class BusinessToCourseServiceImpl implements BusinessToCourseService{

	@Autowired
	private BusinessToCourseMapper businessToCourseMapper;
	@Autowired
	private CommonRecService commonRecService;
	
	/**
	 * @author sunji
	 * @description 增加岗位To课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void insert(BusinessToCourse businessToCourse) throws BusinessException{
		//验证该岗位下是否存在重复课程编号
		int count = businessToCourseMapper.queryCount(businessToCourse);
		if(count > 0){
			throw new BusinessException("该课程编号已存在，请修改后再进行操作！");
		}
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		String id=UUID.randomUUID().toString();
		businessToCourse.setId(id);
		businessToCourse.setZt(1);
		businessToCourse.setWhbmid(user.getBmdm());
		businessToCourse.setWhrid(user.getId());
		businessToCourse.setWhsj(new Date());
		businessToCourseMapper.insertSelective(businessToCourse);
		commonRecService.write(id, TableEnum.B_P_005, user.getId(), czls, businessToCourse.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,id);
	}
	/**
	 * @author sunji
	 * @description  修改岗位To课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void update(BusinessToCourse businessToCourse)throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		businessToCourseMapper.updateByPrimaryKeySelective(businessToCourse);
		commonRecService.write(businessToCourse.getId(), TableEnum.B_P_005, user.getId(), czls, businessToCourse.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,businessToCourse.getId());
	}

	/**
	 * @author sunji
	 * @description  删除岗位To课程
	 * @param course
	 * @throws BusinessException 
	 */
	public void delete(String id) throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		commonRecService.write(id, TableEnum.B_P_005, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
		businessToCourseMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 
	 * @Description 根据岗位id查询数据
	 * @CreateTime 2018-1-22 下午3:03:13
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<BusinessToCourse> queryAllBygwid(String gwid,String dprtcode)
			throws BusinessException {
		return businessToCourseMapper.queryAllBygwid(gwid,dprtcode);
	}
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-1-22 下午3:07:17
	 * @CreateBy 孙霁
	 * @param gwid
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public BusinessToCourse selectByPrimaryKey(String id)
			throws BusinessException {
		BusinessToCourse businessToCourse = businessToCourseMapper.selectByPrimaryKey(id);
		if(businessToCourse == null){
			throw new BusinessException("该数据不存在，请刷新后再进行操作！");
		}
		return businessToCourse;
	}
	/**
	 * 
	 * @Description 添加多个
	 * @CreateTime 2018-1-23 下午2:29:07
	 * @CreateBy 孙霁
	 * @param businessToCourseList
	 * @throws BusinessException
	 */
	@Override
	public void insertAll(List<BusinessToCourse> businessToCourseList)
			throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		for (BusinessToCourse businessToCourse : businessToCourseList) {
			String czls=UUID.randomUUID().toString();
			String id=UUID.randomUUID().toString();
			businessToCourse.setId(id);
			businessToCourse.setZt(1);
			businessToCourse.setWhbmid(user.getBmdm());
			businessToCourse.setWhrid(user.getId());
			businessToCourse.setWhsj(new Date());
			businessToCourseMapper.insertSelective(businessToCourse);
			commonRecService.write(id, TableEnum.B_P_005, user.getId(), czls, businessToCourse.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,id);
		}
	}

}
