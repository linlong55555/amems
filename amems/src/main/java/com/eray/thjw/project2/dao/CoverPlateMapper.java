package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.CoverPlate;

/**
 * 
 * @Description 接近/盖板Mapper
 * @CreateTime 2017-8-14 下午3:56:02
 * @CreateBy 刘兵
 */
public interface CoverPlateMapper {
	
	int deleteByPrimaryKey(String id);

    int insert(CoverPlate record);

    int insertSelective(CoverPlate record);

    CoverPlate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CoverPlate record);
    
    /**
     * @Description 批量新增接近/盖板
     * @CreateTime 2017-8-19 上午11:31:07
     * @CreateBy 刘兵
     * @param coverPlateList 接近/盖板集合
     * @return int
     */
    int insert4Batch(List<CoverPlate> coverPlateList);
    
    /**
     * @Description 根据业务id删除接近/盖板
     * @CreateTime 2017-8-18 下午8:23:20
     * @CreateBy 刘兵
     * @param ywid 业务id
     * @return int
     */
    int deleteByYwid(String ywid);
    
    /**
	 * @Description 根据业务id集合获取接近/盖板
	 * @CreateTime 2017-8-23 上午10:01:59
	 * @CreateBy 刘兵
	 * @param ywidList 业务id集合
	 * @return List<CoverPlate> 工卡集合
	 */
	List<CoverPlate> queryByYwidList(List<String> ywidList);
	/**
	 * 
	 * @Description 同步接近/盖板
	 * @CreateTime 2017年10月10日 上午10:39:13
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void insertByCopyCoverPlate(CoverPlate record);
	
	 /**
     * @Description 同步接近/盖板
     * @CreateTime 2017-10-17 下午5:50:19
     * @CreateBy 刘兵
     * @param record 接近/盖板
     */
    void insertByCopy(CoverPlate record);
    /**
     * 
     * @Description 根据业务id集合获取接近/盖板
     * @CreateTime 2017年10月31日 下午3:00:21
     * @CreateBy 岳彬彬
     * @param ywidList
     * @return
     */
    List<CoverPlate> queryBylist(List<String> ywidList);
    
    /**
     * @Description 批量删除，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param delMainIds
     */
	void delete4BatchImpl(List<String> delMainIds);

	/**
     * @Description 批量插入，导入专用
     * @CreateTime 2017-12-12 上午11:40:17
     * @CreateBy 雷伟
     * @param addUnits
     */
	void insert4BatchImpl(List<CoverPlate> addCoverPlates);
}