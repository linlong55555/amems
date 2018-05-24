package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.PlanInit;

public interface AircraftinfoMapper {
    int insertSelective(Aircraftinfo aircraftinfo);

    PlanInit selectByPrimaryKey(String fjzch);

    int updateByPrimaryKeySelective(Aircraftinfo aircraftinfo);

    /**
	 * @Description  飞机基本信息分页
	 * @CreateTime 2017年9月19日 上午11:17:10
	 * @CreateBy 林龙
	 * @param aircraftinfo  飞机基本信息
	 * @return  飞机基本信息map集合
	 */
	List<Aircraftinfo> queryAllPageList(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 验证飞机注册号唯一
	 * @CreateTime 2017-9-21 上午11:37:54
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return int
	 */
	int findByFjzch(Aircraftinfo aircraftinfo);
	/**
	 * 
	 * @Description 验证MSN号唯一
	 * @CreateTime 2017-9-21 上午11:38:10
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return int
	 */
	int findByXlh(Aircraftinfo aircraftinfo);
	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2017-9-22 上午10:05:44
	 * @CreateBy 孙霁
	 * @param id
	 * @return Aircraftinfo
	 * @throws BusinessException
	 */
	Aircraftinfo selectByfjzchAndDprtcode(Aircraftinfo aircraftinfo);
	
	/**
	 * @Description 根据机型查询飞机信息
	 * @CreateTime 2017年9月27日 上午10:59:28
	 * @CreateBy 韩武
	 * @param aircraftinfo
	 * @return
	 */
	List<Aircraftinfo> selectByFjjx(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 根据飞机注册号和组织机构获取飞机信息
	 * @CreateTime 2017年9月25日 上午10:21:16
	 * @CreateBy 岳彬彬
	 * @param fjzch
	 * @param dprtcode
	 * @return
	 */
	Aircraftinfo getFjByFjzchAndDprtcode(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 根据组织机构查询飞机注册号数量
	 * @CreateTime 2017-10-10 下午2:12:24
	 * @CreateBy 孙霁
	 * @param dprtcode
	 * @return
	 */
	int queryCountfj(String dprtcode);
	
	/**
	 * 
	 * @Description 查询组织机构下所有飞机数据
	 * @CreateTime 2017-10-14 下午2:08:03
	 * @CreateBy 孙霁
	 * @param dprtcode
	 */
	List<Aircraftinfo> queryAllBydprtcode(String dprtcode);
	/**
	 * 
	 * @Description 获取组织机构下所有有效的飞机
	 * @CreateTime 2017年10月14日 上午10:37:54
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
	List<Aircraftinfo> queryszAllPageList(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 查询飞机状态
	 * @CreateTime 2017-10-23 下午2:11:21
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 */
	Aircraftinfo selectBystatus(Aircraftinfo aircraftinfo);
	
	/**
	 * 
	 * @Description 查询未关闭的适航性资料
	 * @CreateTime 2017-10-25 下午2:46:43
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 */
	List<Aircraftinfo> queryAllAir(Aircraftinfo aircraftinfo);

	/**
	 * @Description 根据组织机构、状态=1 ：导入专用
	 * @CreateTime 2017-12-15 上午10:34:42
	 * @CreateBy 雷伟
	 * @param dprtcode
	 * @param jx
	 * @return
	 */
	List<Aircraftinfo> getAllFjzchByParam(@Param("dprtcode")String dprtcode,@Param("jx")String jx);
	/**
	 * 
	 * @Description 根据飞机注册号和组织机构验证该飞机注册号是否存在于d_007
	 * @CreateTime 2018年4月19日 下午4:13:19
	 * @CreateBy 岳彬彬
	 * @param fjzch
	 * @param dprtcode
	 * @return
	 */
	int doValidation4FjzchExist(@Param("fjzch")String fjzch,@Param("dprtcode")String dprtcode);
}