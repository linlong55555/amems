package com.eray.thjw.quality.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.quality.po.AnnualPlan;

/**
 * 
 * @Description 年度计划service
 * @CreateTime 2017年9月25日 下午1:52:48
 * @CreateBy 林龙
 */
public interface AnnualPlanService {
	
	/**
	 * @Description 岗位授权分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param annualPlan 岗位授权
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(AnnualPlan annualPlan)throws BusinessException ;
	
	/**
	 * 
	 * @Description 保存年度计划
	 * @CreateTime 2018年1月8日 上午10:06:42
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	public String save(AnnualPlan annualPlan)throws BusinessException;
	
	/**
	 * @Description 岗位授权删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param annualPlan 岗位授权
	 * @return 岗位授权id
	 * @throws BusinessException
	 */
	public void delete(AnnualPlan annualPlan)throws BusinessException;

	/**
	 * @Description  根据年度计划id查询年度计划
	 * @CreateTime 2018-4-27 上午11:07:48
	 * @CreateBy 刘兵
	 * @param id
	 * @return
	 */
	public AnnualPlan getInfoById(String id);

	/**
	 * 
	 * @Description 修改年度计划信息
	 * @CreateTime 2018年1月8日 上午11:06:12
	 * @CreateBy 林龙
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	public String update(AnnualPlan annualPlan)throws BusinessException;

	/**
	 * 
	 * @Description 获取最新版本相关数据
	 * @CreateTime 2018年1月4日 下午2:30:24
	 * @CreateBy 林龙
	 * @param nf
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	public List<AnnualPlan> getLatestVersionList(String nf, String dprtcode)throws BusinessException;

	/**
	 * 
	 * @Description 获取最新版本相关数据
	 * @CreateTime 2018年1月4日 下午2:30:24
	 * @CreateBy 林龙
	 * @param nf
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	public AnnualPlan getLatestVersion(String nf, String dprtcode,String bbh)throws BusinessException;
	
	/**
	 * @Description 查询变更记录
	 * @CreateTime 2018年1月11日 下午3:08:52
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	List<AnnualPlan> queryChangeRecord(AnnualPlan record);
	
	/**
	 * @Description 根据状态，组织机构获取数据
	 * @CreateTime 2018年1月11日 下午3:08:52
	 * @CreateBy sunji
	 * @param record
	 * @return
	 */
	List<AnnualPlan> queryAllByZt(AnnualPlan record);
	
	/**
	 * @Description 提交年度计划信息
	 * @CreateTime 2018-1-19 上午11:00:21
	 * @CreateBy 刘兵
	 * @param annualPlan
	 * @return
	 * @throws BusinessException
	 */
	public String doSubmit(AnnualPlan annualPlan)throws BusinessException;
	
	/**
	 * @Description 升级版本
	 * @CreateTime 2018-1-19 上午11:00:21
	 * @CreateBy 刘兵
	 * @param annualPlan
	 * @return String新版本的年度计划id
	 * @throws BusinessException
	 */
	public String doModify(AnnualPlan annualPlan)throws BusinessException;
	
	/**
	 * 
	 * @Description 批准驳回
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	void updateApprovalAndAudit(AnnualPlan annualPlan,Integer zt,Integer yzzt)throws BusinessException;

}
