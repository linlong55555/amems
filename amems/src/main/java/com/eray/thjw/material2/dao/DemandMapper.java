package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.project2.po.MaterialTool;
/**
 * 
 * @Description 需求mapper
 * @CreateTime 2018年2月26日 上午10:03:44
 * @CreateBy 林龙
 */
public interface DemandMapper {
    int deleteByPrimaryKey(String id);

    int insert(Demand record);

    int insertSelective(Demand record);

    Demand selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);

	List<Demand> queryAllList(Demand demand);
	
	/**
	 * @Description 查询需求提报详情
	 * @CreateTime 2018年2月27日 下午2:25:58
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	Demand queryDetail(String id);
	
	/**
	 * @Description 查询当前人的需求提报列表
	 * @CreateTime 2018年2月28日 下午5:09:25
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	List<Demand> querySelfList(Demand demand);

	int getCurrentZt4Validation(String id);
	
	/**
	 * @Description 需求提报更新数据
	 * @CreateTime 2018年3月1日 上午10:00:32
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateByDemandApply(Demand record);

	List<Demand> queryAllDemandpprotectionPageList(Demand demand);

	List<Demand> queryAllClosePageList(Demand demand);
	
	/**
	 * @Description 查询航材工具需求来源
	 * @CreateTime 2018年4月12日 下午4:00:22
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	List<Demand> queryMaterailToolSource(Demand demand);
}