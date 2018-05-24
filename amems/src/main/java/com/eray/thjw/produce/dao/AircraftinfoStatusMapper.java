package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.produce.po.FailureKeep;

/**
 * 
 * @Description 飞机状态数据Dao
 * @CreateTime 2017年10月11日 上午10:47:29
 * @CreateBy 朱超
 */
public interface AircraftinfoStatusMapper {
    int deleteByPrimaryKey(String id);

    int insert(AircraftinfoStatus record);

    int insertSelective(AircraftinfoStatus record);

    AircraftinfoStatus selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AircraftinfoStatus record);

    int updateByPrimaryKey(AircraftinfoStatus record);

    /**
     * 
     * @Description 按条件查询飞机状态
     * @CreateTime 2017年10月11日 上午10:50:28
     * @CreateBy 朱超
     * @param aircraftinfoStatus
     * @return
     */
	List<AircraftinfoStatus> selectList4forecast(AircraftinfoStatus aircraftinfoStatus);
	
	/**
	 * @Description 查询飞行记录本需要的数据(初始值)
	 * @CreateTime 2017-11-23 下午5:25:35
	 * @CreateBy 刘兵
	 * @param dprtcode 机构代码
	 * @param fjzch 飞机注册号
	 * @return List<AircraftinfoStatus> 飞机状态数据集合
	 */
	List<AircraftinfoStatus> queryList4FlightBook(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
	
	/**
	 * @Description 飞机编辑时修改初始值，仅更改位置为机身的非日历数据
	 * @CreateTime 2017年10月17日 下午3:14:28
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @return
	 */
	int updateByPlaneEdit(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
	
	/**
	 * @Description 根据飞机和装机位置查询记录数
	 * @CreateTime 2017年10月30日 下午1:54:01
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param wz 装机位置
	 * @param fjzch 飞机注册号
	 * @return
	 */
	int selectCountByFjAndWz(@Param("dprtcode")String dprtcode, @Param("wz")Integer wz, @Param("fjzch")String fjzch);
	
	/**
	 * @Description 批量新增
	 * @CreateTime 2017年10月30日 下午2:02:43
	 * @CreateBy 徐勇
	 * @param list 飞机状态数据
	 * @return
	 */
	int insert4Batch(@Param("list")List<AircraftinfoStatus> list);
	
	/**
	 * @Description 批量修改：根据飞机+装机位置+监控项更新
	 * @CreateTime 2017年10月30日 下午2:02:53
	 * @CreateBy 徐勇
	 * @param list 飞机状态数据
	 * @return
	 */
	int update4Batch(@Param("list")List<AircraftinfoStatus> list);
	
	/**
	 * @Description 从部件使用汇总 更新飞机状态数据累计值（除机身数据）
	 * @CreateTime 2017年10月17日 下午3:14:28
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @return
	 */
	int updateLJFromComponentUseCount(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
	
	/**
	 * @Description 更新飞机状态数据累计值（机身数据）
	 * @CreateTime 2017年10月17日 下午3:14:28
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 * @return
	 */
	int updateBodyLJ(@Param("dprtcode")String dprtcode, @Param("fjzch")String fjzch);
	
	int deleteByZjqdid(@Param("zjqdid")String zjqdid);

	/**
	 * @Description 获取飞行时间、飞行循环默认值
	 * @CreateTime 2018-1-29 下午5:12:11
	 * @CreateBy 雷伟
	 * @param failureKeep
	 * @return
	 */
	List<AircraftinfoStatus> getDefaultFHFC(FailureKeep failureKeep);
	
}