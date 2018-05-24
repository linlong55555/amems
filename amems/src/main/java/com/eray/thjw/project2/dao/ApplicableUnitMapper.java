package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.ApplicableUnit;

/**
 * @Description 工卡-适用单位Mapper
 * @CreateTime 2017-11-8 下午3:08:10
 * @CreateBy 刘兵
 */
public interface ApplicableUnitMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplicableUnit record);

    int insertSelective(ApplicableUnit record);

    ApplicableUnit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplicableUnit record);
    
    /**
     * @Description 批量新增工卡-适用单位
     * @CreateTime 2017-11-8 下午3:18:35
     * @CreateBy 刘兵
     * @param applicableUnit 工卡-适用单位集合 
     * @return int
     */
    int insert4Batch(List<ApplicableUnit> applicableUnit);
    
    /**
     * @Description 根据业务id删除工卡-适用单位
     * @CreateTime 2017-11-8 下午3:18:35
     * @CreateBy 刘兵
     * @param mainid 父id
     * @return int
     */
    int deleteByMainid(String mainid);
    
    /**
     * @Description 批量删除工卡-适用单位
     * @CreateTime 2017-11-8 下午3:18:35
     * @CreateBy 刘兵
     * @param idList 工卡-适用单位id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 根据父id查询工卡-适用单位列表
     * @CreateTime 2017-11-8 下午3:46:57
     * @CreateBy 刘兵
     * @param mainid 父id
     * @return List<ApplicableUnit> 工卡-适用单位集合
     */
    List<ApplicableUnit> queryByMainid(String mainid);

    /**
     * @Description 批量插入，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param addUnits
     */
	void insert4BatchImpl(List<ApplicableUnit> addUnits);

	/**
     * @Description 批量删除，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param delMainIds
     */
	void delete4BatchImpl(List<String> delMainIds);

}