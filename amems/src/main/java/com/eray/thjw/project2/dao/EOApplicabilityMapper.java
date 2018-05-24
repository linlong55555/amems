package com.eray.thjw.project2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.EOApplicability;

/**
 * @Description EO-适用性 Mapper
 * @CreateTime 2017-8-22 下午9:37:43
 * @CreateBy 雷伟
 */
public interface EOApplicabilityMapper {

    /**
	 * @Description 新增 EO-适用性 
	 * @CreateTime 2017-8-22 上午10:12:13
	 * @CreateBy 雷伟
	 * @param eoApplicability EO-适用性 
	 * @return
	 */
	int insert(EOApplicability eoApplicability);
	
	/**
	 * @Description 修改 EO-适用性 
	 * @CreateTime 2017-8-22 上午10:12:17
	 * @CreateBy 雷伟
	 * @param eoApplicability EO-适用性 
	 * @return
	 */
	int updateByPrimaryKeySelective(EOApplicability eoApplicability);

	/**
	 * @Description 批量新增 EO-适用性 
	 * @CreateTime 2017-8-28 上午10:53:59
	 * @CreateBy 雷伟
	 * @param syxszList  EO-适用性,数据集合
	 */
	void insert4Batch(List<EOApplicability> syxszList);

	/**
	 * @Description 更具业务ID,删除EO-适用性 
	 * @CreateTime 2017-8-28 上午10:59:54
	 * @CreateBy 雷伟
	 * @param eoId
	 */
	void deleteByMainid(String eoId);

	/**
	 * @Description 根据EOID查询是否有适用性状态=0的数据,只要有一条，列表就要表黄圈
	 * @CreateTime 2017-9-1 下午2:00:17
	 * @CreateBy 雷伟
	 * @param zlidList
	 * @return
	 */
	List<EOApplicability> getSyxByZlidList(List<String> zlidList);
	
	/**
	 * @Description 飞机注册时新增EO执行对象
	 * @CreateTime 2017年10月16日 上午11:04:29
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param xlh 飞机序列号
	 * @return
	 */
	int insert4AddPlane(@Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx, @Param("fjzch")String fjzch, @Param("xlh")String xlh);
	
	/**
	 * @Description 批量新增EO执行对象-部件
	 * @CreateTime 2017年11月17日 上午10:51:36
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjjx 机型
	 * @param bjh 部件号
	 * @param xlh 序列号
	 * @return
	 */
	int insert4AddComponent(@Param("dprtcode")String dprtcode, @Param("fjjx")String fjjx, @Param("bjh")String bjh, @Param("xlh")String xlh);
	
	/**
	 * @Description 根据EOID获取适用性
	 * @CreateTime 2017-10-19 下午1:36:47
	 * @CreateBy 雷伟
	 * @param id
	 * @return
	 */
	List<EOApplicability> getEOApplicabilityByMainId(String mainid);

	EOApplicability selectById(Map<String, String> paramMap);

	/**
	 * 获取未完成的执行对象数量
	 * @Description 
	 * @CreateTime 2017-11-21 上午11:52:42
	 * @CreateBy 雷伟
	 * @param mainid
	 * @return
	 */
	int getNotCompleteNumsByMainId(String mainid);
}