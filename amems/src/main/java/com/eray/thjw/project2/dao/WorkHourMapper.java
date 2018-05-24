package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.WorkHour;

/**
 * 
 * @Description 工种工时Mapper
 * @CreateTime 2017-8-14 下午4:08:11
 * @CreateBy 刘兵
 */
public interface WorkHourMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkHour record);

    int insertSelective(WorkHour record);

    WorkHour selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkHour record);
    
    /**
     * @Description 根据业务id删除接近/盖板
     * @CreateTime 2017-8-18 下午8:23:20
     * @CreateBy 刘兵
     * @param ywid 业务id
     * @return int
     */
    int deleteByYwid(String ywid);
    
    /**
     * @Description 批量删除工种工时
     * @CreateTime 2017-8-19 下午5:36:22
     * @CreateBy 刘兵
     * @param idList 工种工时id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 批量新增工种工时
     * @CreateTime 2017-8-19 下午3:02:02
     * @CreateBy 刘兵
     * @param workHourList 工种工时集合
     * @return int
     */
    int insert4Batch(List<WorkHour> workHourList);
    
    /**
	 * @Description 根据条件查询工种工时列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workHour 工种工时
	 * @return List<WorkHour> 工种工时集合
	 */
    List<WorkHour> queryAllList(WorkHour workHour);
    
    /**
     * @Description 根据业务id、业务类型、机构代码查询工种工时列表
     * @CreateTime 2017-8-19 下午4:38:20
     * @CreateBy 刘兵
     * @param ywid 业务ID
     * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
     * @param dprtcode 机构代码
     * @return List<WorkHour> 工种工时集合
     */
    List<WorkHour> queryByYwidAndYwlxAndDrpt(@Param("ywid")String ywid, @Param("ywlx")int ywlx, @Param("dprtcode")String dprtcode);
    /**
     * 
     * @Description 新增145工单工种工时
     * @CreateTime 2017年10月10日 上午10:32:53
     * @CreateBy 岳彬彬
     * @param workHour
     */
    void insertByCopyWorkhours(WorkHour workHour);
    
    /**
     * @Description 同步工种工时
     * @CreateTime 2017-10-17 下午5:50:19
     * @CreateBy 刘兵
     * @param record 工单工种工时
     */
    void insertByCopy(WorkHour record);

    /**
     * @Description 批量删除，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param addUnits
     */
	void delete4BatchImpl(List<WorkHour> addWorkHours);

	 /**
     * @Description 批量新增，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param addUnits
     */
	void insert4BatchImpl(List<WorkHour> addWorkHours);
}