package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEditable2Init;

/**
 * @Description 装机清单-编辑区-部件初始化mapper
 * @CreateTime 2017年9月28日 上午10:05:05
 * @CreateBy 韩武
 */
public interface InstallationListEditable2InitMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstallationListEditable2Init record);

    int insertSelective(InstallationListEditable2Init record);

    InstallationListEditable2Init selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InstallationListEditable2Init record);

    int updateByPrimaryKey(InstallationListEditable2Init record);
    
    /**
     * @Description 查询飞机的有效的部件初始值
     * @CreateTime 2017年9月23日 下午4:05:28
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    List<InstallationListEditable2Init> queryByAircraft(InstallationListEditable record);
    
    /**
     * @Description 根据mainid删除对应的初始化数据
     * @CreateTime 2017年9月28日 下午4:58:20
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
    
    /**
     * @Description 批量新增
     * @CreateTime 2017年10月17日 上午11:41:54
     * @CreateBy 徐勇
     * @param list
     * @return
     */
    int insert4Batch(@Param("list")List<InstallationListEditable2Init> list);
    
    /**
     * @Description 更新初始值与飞机设置不同的的数据 
     * @CreateTime 2017年10月17日 下午2:19:41
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @return
     */
    List<InstallationListEditable2Init> updateInitByDiffWithPlane(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
    
    /**
     * @Description 从临时区同步装机清单初始化数据
     * @CreateTime 2017年11月17日 下午2:06:46
     * @CreateBy 徐勇
     * @param zjqdid 装机清单ID
     * @return
     */
    int insertFromTemp(@Param("zjqdid")String zjqdid);
    
    /**
     * @Description 从生效区同步装机清单初始化数据
     * @CreateTime 2017年11月25日 下午3:42:17
     * @CreateBy 徐勇
     * @param zjqdid 装机清单id
     */
    int insertFromEffectiveByMainId(@Param("zjqdid")String zjqdid);
    
}