package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.DistributionDepartment;


/**
 * 
 * @Description 分发部门或用户Service
 * @CreateTime 2017年8月21日 上午11:02:42
 * @CreateBy 林龙
 */
public interface DistributionDepartmentService {

	/**
	 * 
	 * @Description 新增分发部门或用户
	 * @CreateTime 2017年8月21日 上午11:03:21
	 * @CreateBy 林龙
	 * @param distributionDepartment 分发部门或用户对象
	 * @param mainid 评估单id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	public void insertDistributionDepartment(List<DistributionDepartment> distributionDepartment, String mainid,String czls)throws BusinessException;

	/**
	 * 
	 * @Description 根据评估单id查询涉及部门信息
	 * @CreateTime 2017年8月21日 下午9:34:23
	 * @CreateBy 林龙
	 * @param distributionDepartment 涉及部门
	 * @return
	 */
	public List<DistributionDepartment> selectDepartmentList(DistributionDepartment distributionDepartment)throws BusinessException;

	/**
	 * 
	 * @Description 编辑涉及部门
	 * @CreateTime 2017年8月22日 上午10:38:18
	 * @CreateBy 林龙
	 * @param distributionDepartmentList
	 * @param id 评估单id
	 * @param czls 流水号
	 */
	public void updateDistributionDepartment(List<DistributionDepartment> distributionDepartmentList, String id,String czls)throws BusinessException;

	/**
	 * 
	 * @Description 根据id 技术评估单id删除 涉及部门集合
	 * @CreateTime 2017年8月24日 下午3:35:10
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @param czls 流水号
	 */
	public void deleteDistributionDepartment(String id, String czls);



}
