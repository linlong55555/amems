package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.training.po.Business;

public interface BusinessMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(Business record);

    Business selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Business record);

	List<Business> queryAllPageList(Business business);
	
	List<Business> queryAllBusiness(Business business);
	
	//验证岗位编号是否唯一
	int selectBydgbh(Business business);
	
	/**
	 * @author liub
	 * @description 根据机构代码查询岗位
	 * @param dprtcode
	 * @return List<Business>
	 */
	List<Business> queryBusinessByDprtcode(String dprtcode);

	List<Business> queryList4mpids(List<String> ids);
	
	int updateById(Business business);
	
	/**
	 * @Description 查询课程岗位关系
	 * @CreateTime 2018-2-1 上午10:43:59
	 * @CreateBy 刘兵
	 * @param business 岗位
	 * @return List<Business> 岗位集合
	 */
	List<Business> queryByKc(Business business);
	
	/**
	 * 
	 * @Description 根据岗位id和维修人员档案id查询数据
	 * @CreateTime 2018-3-27 下午2:15:10
	 * @CreateBy 孙霁
	 * @param business
	 * @return List<Business>
	 */
	List<Business> queryAllResults(Business business);

	/**
	 * 
	 * @Description 查询列表
	 * @CreateTime 2018-3-28 上午10:22:52
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	List<Business> queryAll(Business business);
	
}