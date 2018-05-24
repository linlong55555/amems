package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;

/**
 * @Description 需求提报serivce
 * @CreateTime 2018年2月26日 上午11:45:34
 * @CreateBy 韩武
 */
public interface DemandApplyService {
	
	/**
	 * @Description 查询飞机信息
	 * @CreateTime 2018年2月26日 上午11:47:07
	 * @CreateBy 韩武
	 * @return
	 */
	List<AircraftinfoStatus> queryAircraftInfo(Aircraftinfo ac);
	
	/**
	 * @Description 保存需求提报
	 * @CreateTime 2018年2月27日 上午10:35:23
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	Demand doSave(Demand demand) throws BusinessException, com.eray.framework.exception.SaibongException;
	
	/**
	 * @Description 查询需求提报详情
	 * @CreateTime 2018年2月27日 下午2:24:25
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	Demand queryDetail(String id);

	/**
	 * @Description 提交需求提报
	 * @CreateTime 2018年2月27日 上午10:35:23
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	Demand doSubmit(Demand demand) throws BusinessException, com.eray.framework.exception.SaibongException;
	
	/**
	 * @Description 查询当前人的需求提报列表
	 * @CreateTime 2018年2月28日 下午5:07:56
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	Map<String, Object> querySelfList(Demand demand);
	
	/**
	 * @Description 根据航材工具清单生成提报单详情
	 * @CreateTime 2018年4月12日 上午9:39:36
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	Demand buildDetailByMaterialTool(Demand demand) throws BusinessException;
}
