package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.DistributionDepartment;

/**
 * @Description 分发部门
 * @author 岳彬彬
 */
public interface DistributionDepartmentMapper {
	/**
	 * @description 根据id删除记录
	 * @author 岳彬彬
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	int insert(DistributionDepartment record);

	/**
	 * @Description 新增涉及部门
	 * @CreateTime 2017年8月21日 上午11:19:00
	 * @CreateBy 林龙
	 * @param distributionDepartment 涉及部门对象
	 * @return
	 */
	int insertSelective(DistributionDepartment distributionDepartment);

	DistributionDepartment selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(DistributionDepartment record);

	int updateByPrimaryKey(DistributionDepartment record);

	/**
	 * @description 批量新增分发部门数据
	 * @author 岳彬彬
	 * @param list
	 * @return
	 */
	int insertDistributionDepartment(List<DistributionDepartment> list);

	/**
	 * @description 根据业务id查询分发部门数据
	 * @author 岳彬彬
	 * @param 业务id
	 * @param 对象id
	 * @return
	 */
	List<DistributionDepartment> getDepartmentByYwid(String ywid);

	/**
	 * @description 根据id批量更新状态
	 * @author 岳彬彬
	 * @param list
	 * @return
	 */
	void updateZtByIds(List<String> list);

	/**
	 * @description 根据业务id逻辑作废
	 * @author 岳彬彬
	 * @param ywid
	 */
	void updateZtByYwid(String ywid);

	/**
	 * @description 根据id标识该通告已阅
	 * @author 岳彬彬
	 * @param record
	 */
	void updateisJsByid(DistributionDepartment record);
	/**
	 * 
	 * @Description 根据业务id和对象id查询分发部门
	 * @CreateTime 2017年8月15日 上午9:40:57
	 * @CreateBy 岳彬彬
	 * @param ywid 业务id
	 * @param dxid 对象id
	 * @return 分发部门
	 */
	DistributionDepartment getDepartmentByYwidAndDxid4Circulation(String ywid, String dxid);
		
	
	/**
	 * @Description  根据业务id和对象id查询分发部门(EO)
	 * @CreateTime  2018年2月28日 上午14:27:57
	 * @CreateBy   刘邓
	 * @param ywid  业务id
	 * @param dxid  对象id
	 * @return     分发部门
	 */
	DistributionDepartment getDepartmentByYwidAndDxid4Eo(String ywid, String dxid);
	
	
	
	
	/**
	 * 
	 * @Description 根据业务id集合查询分发部门
	 * @CreateTime 2017年8月18日 下午4:56:07
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	List<DistributionDepartment> getDepartmentByYwidList(List<String> list);

	void delete4Batch(List<String> columnValueList);

	/**
	 * @Description 根据业务ID删数据
	 * @CreateTime 2017-8-25 下午6:09:14
	 * @CreateBy 雷伟
	 * @param eoId
	 */
	void deleteByYwid(String eoId);

	/**
	 * @Description 查询EO分发部门
	 * @CreateTime 2018-2-26 下午4:33:15
	 * @CreateBy 雷伟
	 * @param id
	 * @param bmdm
	 * @return
	 */
	DistributionDepartment getDepartmentByYwidAndEODxid4Circulation(String id,String bmdm);

}