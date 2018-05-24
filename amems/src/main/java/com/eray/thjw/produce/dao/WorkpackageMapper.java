package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.Workpackage;

/**
 * 
 * @Description 工包Mapper
 * @CreateTime 2017年9月25日 下午3:58:24
 * @CreateBy 岳彬彬
 */
public interface WorkpackageMapper {
	int deleteByPrimaryKey(String id);

	int insert(Workpackage record);

	int insertSelective(Workpackage record);

	Workpackage selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Workpackage record);

	int updateByPrimaryKey(Workpackage record);
	
	/**
	 * @Description 修改工包待提标识
	 * @CreateTime 2017-10-16 下午3:49:15
	 * @CreateBy 刘兵
	 * @param record 工包
	 * @return int
	 */
	int updateDtbsById(Workpackage record);
	
	/**
	 * @Description 修改工包待提标识及状态
	 * @CreateTime 2017-10-17 下午3:27:28
	 * @CreateBy 刘兵
	 * @param record 工包
	 * @return int
	 */
	int updateDtbsAndZtById(Workpackage record);

	/**
	 * 
	 * @Description 验证工包编号在组织机构下是否唯一
	 * @CreateTime 2017年9月25日 下午3:02:48
	 * @CreateBy 岳彬彬
	 * @param gbbh
	 * @param dprtcode
	 * @return
	 */
	Integer getCountByGbbhAndDprtcode(String gbbh, String dprtcode);

	/**
     * @Description 获取预组包数量
     * @CreateTime 2017-10-16 下午2:52:40
     * @CreateBy 刘兵
     * @param fjzch 飞机注册号
     * @param dprtcode 机构代码
     * @return int 预组包数量
     */
    int getBurstificationCount(@Param("fjzch")String fjzch, @Param("dprtcode")String dprtcode);
	
	/**
	 * 
	 * @Description 获取工包列表
	 * @CreateTime 2017年9月25日 下午3:58:43
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	List<Workpackage> getWorkpackageList(Workpackage record);
	/**
	 * 
	 * @Description 获取工包当前状态
	 * @CreateTime 2017年9月27日 下午3:36:30
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	Integer getCurrentById(String id);
	/**
	 * 
	 * @Description 指定结束
	 * @CreateTime 2017年9月27日 下午6:27:36
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4EndClose(Workpackage record);
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年9月28日 上午10:52:56
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4Issued(Workpackage record);
	/**
	 * 
	 * @Description 完工反馈
	 * @CreateTime 2017年9月28日 下午12:04:29
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4Feedback(Workpackage record);
	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年9月28日 下午2:01:06
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4Close(Workpackage record);
	
	/**
	 * 飞行记录本查询工包数据
	 * @Description 
	 * @CreateTime 2017年10月17日 下午2:49:22
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<Workpackage> query4FLB(Workpackage record);
	
	/**
	 * @Description 根据条件查询工包信息
	 * @CreateTime 2017-10-19 下午2:37:19
	 * @CreateBy 刘兵
	 * @param record 工包
	 * @return List<Workpackage>工包集合
	 */
	List<Workpackage> queryWorkpackageList(Workpackage record);
	/**
	 * 
	 * @Description 获取下发状态下的所有工包
	 * @CreateTime 2017年12月12日 下午4:54:36
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	List<Workpackage> getIssuedWp(Workpackage record);

	int queryCount(Workpackage tec);
}