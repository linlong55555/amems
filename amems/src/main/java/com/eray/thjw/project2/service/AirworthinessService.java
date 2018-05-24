package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.TEApplicability;
/**
 * 
 * @Description 适航性资料service
 * @CreateTime 2017-8-22 上午10:31:39
 * @CreateBy 孙霁
 */
public interface AirworthinessService {

	/**
	 * 
	 * @Description 根据查询条件查询适航性资料
	 * @CreateTime 2017-8-15 下午6:48:49
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @return
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAll(Airworthiness airworthiness)throws BusinessException ;
	/**
	 * 
	 * @Description 新增适航性资料
	 * @CreateTime 2017-8-15 下午6:49:11
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @throws BusinessException
	 */
	public String insert(Airworthiness airworthiness)throws BusinessException ;
	/**
	 * 
	 * @Description 修改适航性资料
	 * @CreateTime 2017-8-15 下午6:49:24
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @throws BusinessException
	 */
	public String update(Airworthiness airworthiness)throws BusinessException ;
	/**
	 * 
	 * @Description 作废适航性资料
	 * @CreateTime 2017-8-15 下午6:49:35
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Map<String , Object> deleteRecord(String id)throws BusinessException ;
	/**
	 * 
	 * @Description 根据id查询数据（包括机型）
	 * @CreateTime 2017-8-18 下午2:52:53
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Airworthiness selectById(String id)throws BusinessException ;
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年8月21日 下午4:37:31
	 * @CreateBy 林龙
	 * @param airworthiness
	 * @return
	 */
	public Airworthiness getByAirworthinessId(Airworthiness airworthiness);
	/**
	 * 
	 * @Description 适航性资料监控列表
	 * @CreateTime 2017年10月11日 上午10:27:25
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 * @throws BusinessException
	 */
	public Map<String , Object> queryList(BaseEntity baseEntity)throws BusinessException ;
	/**
	 * 
	 * @Description 适航性资料监控关闭
	 * @CreateTime 2017年10月11日 下午3:30:38
	 * @CreateBy 岳彬彬
	 * @param airworthiness
	 * @throws BusinessException
	 */
	public void update4Close(Airworthiness airworthiness) throws BusinessException;
	
	/**
	 * 
	 * @Description 查询导出列表
	 * @CreateTime 2017-12-18 下午2:00:35
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @throws BusinessException
	 */
	public List<Airworthiness> getAirworthinessList(Airworthiness airworthiness)throws BusinessException;
	
	public List<Map<String,Object>> exportCadList(BaseEntity baseEntity) throws BusinessException ;
	/**
	 * 
	 * @Description AD适航性资料监控列表
	 * @CreateTime 2018-4-2 下午2:14:29
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllAd(Airworthiness airworthiness)throws BusinessException;
	
	/**
	 * 
	 * @Description 关闭技术文件监控
	 * @CreateTime 2018-4-3 上午10:36:24
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	public void closeMonitoring(String id, Integer zt)throws BusinessException;
	
	/**
	 * 
	 * @Description 关闭适用性监控
	 * @CreateTime 2018-4-3 上午10:36:29
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	public void closeApplyMonitoring(TEApplicability tEApplicability)throws BusinessException;
	/**
	 * 
	 * @Description 获取Ad导出数据
	 * @CreateTime 2018-4-12 下午2:23:16
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @return
	 * @throws BusinessException
	 */
	public List<Airworthiness> exportAdList(Airworthiness airworthiness)throws BusinessException;
}
