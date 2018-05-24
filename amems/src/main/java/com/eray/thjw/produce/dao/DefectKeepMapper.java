package com.eray.thjw.produce.dao;

import java.util.List;

import com.eray.thjw.produce.po.DefectKeep;

/**
 * 
 * @Description 缺陷保留mapper
 * @CreateTime 2017年10月14日 下午3:36:01
 * @CreateBy 林龙
 */
public interface DefectKeepMapper {
    int deleteByPrimaryKey(String id);

    int insert(DefectKeep record);

    int insertSelective(DefectKeep record);

    DefectKeep selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DefectKeep record);

    int updateByPrimaryKey(DefectKeep record);

	List<DefectKeep> queryAllPageList(DefectKeep failureKeep);
	/**
	 * 
	 * @Description 根据技术文件对象查询缺陷保留数量
	 * @CreateTime 2017年10月14日 下午3:44:01
	 * @CreateBy 林龙
	 * @return
	 */
	int queryCount(DefectKeep fai);

	/**
	 * 
	 * @Description 根据缺陷保留查询缺陷保留信息
	 * @CreateTime 2017年10月14日 下午3:44:01
	 * @CreateBy 林龙
	 * @return
	 */
	DefectKeep getInfoById(DefectKeep failureKeep);

	/**
	 * 
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年10月14日 下午3:44:01
	 * @CreateBy 林龙
	 * @return
	 */
	int getCurrentZt4Validation(String id);

	List<DefectKeep> queryMonitorAllPageList(DefectKeep param);
}