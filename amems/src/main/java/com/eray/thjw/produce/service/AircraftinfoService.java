package com.eray.thjw.produce.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Aircraftinfo;



/**
 * 
 * @Description 飞机基本信息service
 * @CreateTime 2017年9月19日 上午11:14:35
 * @CreateBy 林龙
 */
public interface AircraftinfoService {

	/**
	 * 
	 * @Description  飞机基本信息分页
	 * @CreateTime 2017年9月19日 上午11:17:10
	 * @CreateBy 林龙
	 * @param aircraftinfo  飞机基本信息
	 * @return  飞机基本信息map集合
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllPageList(Aircraftinfo aircraftinfo)throws BusinessException ;
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	public String insert(Aircraftinfo aircraftinfo)throws BusinessException ;

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	public String update(Aircraftinfo aircraftinfo)throws BusinessException ;
	
	/**
	 * 
	 * @Description 修改状态
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	public void updateByPrimaryKeySelective(Aircraftinfo aircraftinfo)throws BusinessException ;
	
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-9-22 上午10:05:44
	 * @CreateBy 孙霁
	 * @param id
	 * @return Aircraftinfo
	 * @throws BusinessException
	 */
	public Aircraftinfo selectByfjzchAndDprtcode(Aircraftinfo aircraftinfo)throws BusinessException ;
	
	/**
	 * @Description 根据机型查询飞机信息
	 * @CreateTime 2017年9月27日 上午10:49:09
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @return
	 */
	List<Aircraftinfo> selectByFjjx(Aircraftinfo aircraftinfo);
	/**
	 * 
	 * @Description 获取飞机信息
	 * @CreateTime 2017年9月27日 下午2:32:03
	 * @CreateBy 岳彬彬
	 * @param aircraftinfo
	 * @return
	 */
	Aircraftinfo getAircraftinfo(Aircraftinfo aircraftinfo);
	/**
	 * 
	 * @Description 获取组织机构下所有有效飞机
	 * @CreateTime 2017年10月14日 上午10:39:10
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<Aircraftinfo> getFjByDprtcode(String dprtcode);

	/**
	 * 
	 * @Description 飞机三证监控分页查询
	 * @CreateTime 2017年10月20日 上午10:09:54
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	public Map<String, Object> queryszAllPageList(Aircraftinfo aircraftinfo)throws BusinessException;

	/**
	 * 
	 * @Description 修改飞机三证
	 * @CreateTime 2017年11月22日 上午10:58:30
	 * @CreateBy 林龙
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	String updatesz(Aircraftinfo aircraftinfo) throws BusinessException;
}
