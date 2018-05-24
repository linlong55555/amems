package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaterialTool;

/**
 * 
 * @Description 器材/工具Mapper
 * @CreateTime 2017-8-14 下午4:07:31
 * @CreateBy 刘兵
 */
public interface MaterialToolMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaterialTool record);

    int insertSelective(MaterialTool record);

    MaterialTool selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaterialTool record);
    
    /**
     * @Description 根据业务id删除器材/工具
     * @CreateTime 2017-8-18 下午8:23:20
     * @CreateBy 刘兵
     * @param ywid 业务id
     * @return int
     */
    int deleteByYwid(String ywid);
    
    /**
     * @Description 批量删除器材/工具
     * @CreateTime 2017-8-19 下午5:36:22
     * @CreateBy 刘兵
     * @param idList 器材/工具id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 批量新增器材/工具
     * @CreateTime 2017-8-19 下午3:02:02
     * @CreateBy 刘兵
     * @param materialToolList 器材/工具集合
     * @return int
     */
    int insert4Batch(List<MaterialTool> materialToolList);
    
    /**
	 * @Description 根据条件查询器材/工具列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
    List<MaterialTool> queryAllList(MaterialTool materialTool);
    
    /**
	 * @Description 根据条件查询器材清单列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
    List<MaterialTool> queryEquipmentList(MaterialTool materialTool);
    
    /**
     * @Description 根据业务id、业务类型、机构代码查询器材/工具列表
     * @CreateTime 2017-8-19 下午4:38:20
     * @CreateBy 刘兵
     * @param ywid 业务ID
     * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
     * @param dprtcode 机构代码
     * @return List<MaterialTool> 器材/工具集合
     */
    List<MaterialTool> queryByYwidAndYwlxAndDrpt(@Param("ywid")String ywid, @Param("ywlx")int ywlx, @Param("dprtcode")String dprtcode);
    /**
     * 
     * @Description 同步145工单器材工具
     * @CreateTime 2017年10月10日 上午10:50:35
     * @CreateBy 岳彬彬
     * @param record
     */
    void insertByCopyMaterialTool(MaterialTool record);
    
    /**
     * @Description 同步器材工具
     * @CreateTime 2017-10-17 下午5:50:19
     * @CreateBy 刘兵
     * @param record 器材工具
     */
    void insertByCopy(MaterialTool record);
    
    /**
	 * @Description 根据条件查询航材工具需求清单(维修项目)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 */
	List<MaterialTool> queryToolList4Maintenance(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(EO)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 */
	List<MaterialTool> queryToolList4EO(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工单)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材工具
	 * @return List<MaterialTool> 器材工具集合
	 */
	List<MaterialTool> queryToolList4WorkOrder(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工包)
	 * @CreateTime 2017-10-28 下午1:11:56
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryToolList4Package(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询航材工具需求清单(选中的)
	 * @CreateTime 2017-10-27 上午10:41:26
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryAllToolList(MaterialTool materialTool);
	/**
	 * 
	 * @Description 根据条件查询航材工具需求清单(145工包)
	 * @CreateTime 2017年10月28日 下午4:05:43
	 * @CreateBy 岳彬彬
	 * @param materialTool
	 * @return
	 */
	List<MaterialTool> queryToolList4WP145(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(选中的)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工包)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList4Package(MaterialTool materialTool);
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工单)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	List<MaterialTool> queryTaskInfoList4WorkOrder(MaterialTool materialTool);
	/**
	 * 
	 * @Description 根据条件查询器材/工具任务信息列表(145工包)
	 * @CreateTime 2017年11月3日 上午9:59:18
	 * @CreateBy 岳彬彬
	 * @param materialTool
	 * @return
	 */
	List<MaterialTool> queryTaskInfoList4WP145(MaterialTool materialTool);

	 /**
     * @Description 批量删除，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param addUnits
     */
	void delete4BatchImpl(List<MaterialTool> addMaterialTools);

	 /**
     * @Description 批量新增，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param addUnits
     */
	void insert4BatchImpl(List<MaterialTool> addMaterialTools);
	
}