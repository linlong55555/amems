package com.eray.thjw.basic.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.basic.po.CoverPlateInformation;

public interface CoverPlateInformationMapper {
	int deleteByPrimaryKey(String id);

	int insert(CoverPlateInformation record);

	int insertSelective(CoverPlateInformation record);

	CoverPlateInformation selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CoverPlateInformation record);

	int updateByPrimaryKey(CoverPlateInformation record);

	/**
	 * @author liub
	 * @description 根据飞机机型查询盖板多选下拉框
	 * @param record
	 * @return List<CoverPlateInformation>
	 */
	List<CoverPlateInformation> queryMultiselectByFjjx(CoverPlateInformation record);

	/**
	 * @author liub
	 * @description 根据飞机机型查询盖板(弹窗)
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	List<CoverPlateInformation> queryWinByFjjx(CoverPlateInformation record);

	/**
	 * 
	 * @Description 查询得出盖板列表
	 * @CreateTime 2017年8月31日 上午11:56:05
	 * @CreateBy 李高升
	 * @param coverPlateInformation
	 * @return
	 */
	List<CoverPlateInformation> queryCoverPlateList(CoverPlateInformation coverPlateInformation);

	/**
	 * 
	 * @Description 查询得出盖板列表
	 * @CreateTime 2017年9月15日 上午11:56:05
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @return
	 */
	List<CoverPlateInformation> queryCoverPlateListByKeyword(CoverPlateInformation coverPlateInformation);


	/**
	 * 
	 * @Description 增加时检查盖板编号，保证不重复
	 * @CreateTime 2017年8月31日 下午4:53:20
	 * @CreateBy 李高升
	 * @param coverPlateInformation
	 * @return
	 */
	int getCount(CoverPlateInformation coverPlateInformation);

	int queryCount(CoverPlateInformation coverPlateInformation);

	/**
	 * 
	 * @Description 导入修改
	 * @CreateTime 2017年11月29日 下午3:20:24
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 */
	void updateByPrimaryKeySelectiveImport(CoverPlateInformation coverPlateInformation);

	/**
	 * @Description 根据机构代码、机型、状态=1、值
	 * @CreateTime 2017-12-12 上午10:28:29
	 * @CreateBy 雷伟
	 * @param dprtcode 机构代码
	 * @param jx 机型
	 * @param zt 状态
	 * @param sz 值
	 * @return
	 */
	CoverPlateInformation getCoverPlateByParam(@Param("dprtcode")String dprtcode, @Param("jx")String jx, @Param("sz")String sz);

}