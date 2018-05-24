package com.eray.thjw.produce.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.InstallationListEffective2Init;

/**
 * @Description 装机清单-生效区-部件初始化mapper
 * @CreateTime 2017年9月28日 上午10:05:55
 * @CreateBy 韩武
 */
public interface InstallationListEffective2InitMapper {
    int deleteByPrimaryKey(String id);

    int insert(InstallationListEffective2Init record);

    int insertSelective(InstallationListEffective2Init record);

    InstallationListEffective2Init selectByPrimaryKey(String id);
    
    List<InstallationListEffective2Init> selectByMainid(String mainid);
    
    /**
     * @Description 根据位置和时间查询 当时的一级节点
     * @CreateTime 2017年11月20日 下午4:21:35
     * @CreateBy 徐勇
     * @param dprtcode 组织机构
     * @param fjzch 飞机注册号
     * @param wz 位置
     * @param jssj 计算时间
     * @return
     */
    List<InstallationListEffective2Init> selectTopByWzAndSj(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch, @Param("wz")Integer wz, @Param("jssj")Date jssj);

    int updateByPrimaryKeySelective(InstallationListEffective2Init record);

    int updateByPrimaryKey(InstallationListEffective2Init record);
    
    /**
     * @Description 查询飞机的有效的部件初始值
     * @CreateTime 2017年9月23日 下午4:05:28
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    List<InstallationListEffective2Init> queryByAircraft(InstallationListEffective record);
    
    /**
     * @Description 批量保存装机清单生效区初始化数据
     * @CreateTime 2017年10月25日 下午2:21:02
     * @CreateBy 徐勇
     * @param list 装机清单-生效区初始化数据
     * @return
     */
    int insert4Batch(@Param("list")List<InstallationListEffective2Init> list);
    
    /**
     * @Description 根据装机清单ID删除
     * @CreateTime 2017年10月25日 下午4:22:34
     * @CreateBy 徐勇
     * @param mainid 装机清单ID
     * @return
     */
    int deleteByMainid(@Param("mainid")String mainid);
    
    /**
     * @Description 清除装机清单生效区初始化数据中在机使用量和拆下时已用
     * @CreateTime 2017年11月25日 下午2:40:30
     * @CreateBy 徐勇
     * @param mainid
     * @return
     */
    int update4ClearRemoveDataByMainid(@Param("mainId")String mainId);
    
    /**
     * @Description 查询初始值集合
     * @CreateTime 2017-11-24 下午2:55:38
     * @CreateBy 刘兵
     * @param mainidList 父id集合
     * @return List<InstallationListEffective2Init> 装机清单-生效区-部件初始化集合
     */
    List<InstallationListEffective2Init> queryByMainidList(List<String> mainidList);
}