package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.WorkContent;

/**
 * 
 * @Description 工作内容Mapper
 * @CreateTime 2017-8-14 下午4:08:11
 * @CreateBy 刘兵
 */
public interface WorkContentMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkContent record);

    int insertSelective(WorkContent record);

    WorkContent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkContent record);
    
    /**
     * @Description 根据业务id删除工作内容
     * @CreateTime 2017-8-18 下午8:23:20
     * @CreateBy 刘兵
     * @param ywid 业务id
     * @return int
     */
    int deleteByYwid(String ywid);
    
    /**
     * @Description 批量删除工作内容
     * @CreateTime 2017-8-19 下午5:36:22
     * @CreateBy 刘兵
     * @param idList 工作内容id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 批量新增工作内容
     * @CreateTime 2017-8-19 下午3:02:02
     * @CreateBy 刘兵
     * @param workContentList 工种工时集合
     * @return int
     */
    int insert4Batch(List<WorkContent> workContentList);
    
    /**
	 * @Description 根据条件查询工作内容列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workContent 工作内容
	 * @return List<WorkContent> 工作内容集合
	 */
    List<WorkContent> queryAllList(WorkContent workContent);
    
    /**
     * @Description 根据业务id、业务类型、机构代码查询工作内容列表
     * @CreateTime 2017-8-19 下午4:38:20
     * @CreateBy 刘兵
     * @param ywid 业务ID
     * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
     * @param dprtcode 机构代码
     * @return List<WorkContent> 工作内容集合
     */
    List<WorkContent> queryByYwidAndYwlxAndDrpt(@Param("ywid")String ywid, @Param("ywlx")int ywlx, @Param("dprtcode")String dprtcode);
    /**
     * 
     * @Description 同步工作内容
     * @CreateTime 2017年10月10日 上午11:08:26
     * @CreateBy 岳彬彬
     * @param workContent
     */
    void insertByCopyWorkContent(WorkContent workContent);
    
    /**
     * @Description 同步工作内容
     * @CreateTime 2017-10-17 下午5:50:19
     * @CreateBy 刘兵
     * @param record 工作内容
     */
    void insertByCopy(WorkContent record);
}