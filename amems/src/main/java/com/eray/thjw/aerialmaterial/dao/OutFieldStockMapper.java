package com.eray.thjw.aerialmaterial.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.OutFieldStock;

public interface OutFieldStockMapper {

	OutFieldStock queryById(String id); // 查询场外库存记录

	int updateByPrimaryKeySelective(OutFieldStock record); // 更新场外记录

	int insertSelective(OutFieldStock record); // 增加外场库存记录

	int deleteByPrimaryKey(String id);

	int insert(OutFieldStock record);

	OutFieldStock selectByPrimaryKey(String id);

	int updateByPrimaryKey(OutFieldStock record);

	List<OutFieldStock> queryByParam(OutFieldStock record);

	public OutFieldStock queryByKey(String id) throws RuntimeException;

	OutFieldStock queryByParamKey(OutFieldStock outFieldStock) throws RuntimeException;

	public List<OutFieldStock> queryAllPageList(OutFieldStock outFieldStock) throws RuntimeException;

	void delete(OutFieldStock outFieldStock1) throws RuntimeException;

	void update(OutFieldStock outFieldStock1) throws RuntimeException;

	void updateZTById(String id);

	void deleteOutFieldStockById(String id);

	void insertOutFieldStockFromMaterialHistory(String whrid, String id, String kcid);
	/**
	 * 
	 * @Description 根据部件id和组织机构获取序列号不为空的外场数据
	 * @CreateTime 2017年8月24日 上午10:48:09
	 * @CreateBy 岳彬彬
	 * @param bjid
	 * @param dprtcode
	 * @return
	 */
	List<OutFieldStock> getOutFieldStockList(String bjid, String dprtcode);
	
	/**
	 * @Description 查询某一序列号、批次号件的可用库存情况
	 * @CreateTime 2017年10月31日 下午3:57:47
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param bjh 部件号
	 * @param xlh 序列号
	 * @param pch 批次号
	 * @return
	 */
	List<OutFieldStock> selectStockByComponent(@Param("dprtcode")String dprtcode, @Param("bjh")String bjh, @Param("xlh")String xlh, @Param("pch")String pch);

	/**
	 * @Description 根据ID更新库存数量
	 * @CreateTime 2017年10月31日 下午4:30:18
	 * @CreateBy 徐勇
	 * @param id 外场库存ID
	 * @param kcsl 库存数量
	 * @return
	 */
	int updateKcslById(@Param("id")String id, @Param("kcsl")BigDecimal kcsl);
	
	/**
	 * @Description 根据增加库存数量
	 * @CreateTime 2017年10月31日 下午4:30:18
	 * @CreateBy 徐勇
	 * @param id 外场库存ID
	 * @param kcsl 库存数量
	 * @return
	 */
	int updateKcslAddById(@Param("id")String id, @Param("kcsl")BigDecimal kcsl);
	
	int insertFromHistory(@Param("id")String id, @Param("kcsl")BigDecimal kcsl, @Param("userId") String userId);
	
	/**
	 * 
	 * @Description 获取库外列表
	 * @CreateTime 2018-3-12 下午4:21:24
	 * @CreateBy 孙霁
	 * @param outFieldStock
	 * @return
	 */
	public List<OutFieldStock> queryAll(OutFieldStock outFieldStock);
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-3-19 下午4:04:14
	 * @CreateBy 孙霁
	 * @param id
	 * @return OutFieldStoc
	 */
	public OutFieldStock selectById(String id);

	OutFieldStock queryByKeywc(String kcid);

	List<OutFieldStock> queryjydid(String id);
	
}