package com.eray.thjw.productionplan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.productionplan.po.LoadingList;

public interface LoadingListMapper {


	List<LoadingList> selectLoadingList(LoadingList ll); // 根据指定条件查询件号和SN序列号


	LoadingList selectConutKey(LoadingList ll);// 查询

	void updatePlaneEditable(LoadingList loadingList) throws RuntimeException; // 修改生效区的飞机装机清单

	/**
	 * 分页查询-生效区
	 * 
	 * @param record
	 * @return
	 */
	List<LoadingList> queryPage(LoadingList record);

	void updatePlaneEditables(LoadingList loadingList);

	/**
	 * 获取所有子节点
	 * 
	 * @param loadingList
	 * @return
	 */
	List<LoadingList> queryList(LoadingList loadingList);
	
	/**
	 * 根据部件拆解记录，判断该部件是否曾经装过飞机
	 * @param usage
	 * @return
	 */
	boolean isEverDisassembled(ComponentUsage usage);
	
	/**
	 * 获取部件累积使用量
	 * @param paramMap
	 * @return
	 */
	ComponentUsage getComponentUsage(LoadingList ll);
	
	List<LoadingList> queryNoChildList(LoadingList ll);
	
	List<LoadingList> getPart(LoadingList ll);
	
	List<LoadingList> getCxj(String fxjlgljlid);
	/**
	 * 
	 * @Description 获取状态为1序列号不为空的数据
	 * @CreateTime 2017年8月24日 上午10:50:46
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param fjjx
	 * @return
	 */
	List<LoadingList> getLoadinglistByfjzch(@Param("dprtcode")String dprtcode,@Param("fjjx")String fjjx);
}