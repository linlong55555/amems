package com.eray.thjw.project2.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.project2.po.Airworthiness;

public interface AirworthinessMapper {
	int deleteByPrimaryKey(String id);

	int insert(Airworthiness record);

	int insertSelective(Airworthiness record);

	Airworthiness selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Airworthiness record);

	int updateByPrimaryKey(Airworthiness record);

	/**
	 * 
	 * @Description 条件查询
	 * @CreateTime 2017-8-17 上午11:45:34
	 * @CreateBy 孙霁
	 * @param record
	 * @return
	 */
	List<Airworthiness> queryAll(Airworthiness record);

	/**
	 * 
	 * @Description 根据编号查询 数据条数
	 * @CreateTime 2017-8-17 上午11:46:09
	 * @CreateBy 孙霁
	 * @param record
	 * @return
	 */
	int findByjswjbh(Airworthiness record);

	/**
	 * 
	 * @Description 根据id查询数据（包括机型）
	 * @CreateTime 2017-8-18 下午2:54:50
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 */
	Airworthiness selectById(String id);

	/**
	 * 
	 * @Description 适航性资料监控列表
	 * @CreateTime 2017年10月11日 上午10:37:46
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 */
	List<Map<String, Object>> selectAll(BaseEntity baseEntity);
	/**
	 * 
	 * @Description 适航性资料监控关闭
	 * @CreateTime 2017年10月11日 下午3:28:27
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4Close(Airworthiness record);
	/**
	 * 
	 * @Description AD适航性资料监控
	 * @CreateTime 2018-4-2 下午2:17:25
	 * @CreateBy 孙霁
	 * @param record
	 * @return
	 */
	List<Airworthiness> queryAllAd(Airworthiness record);
	/**
	 * 
	 * @Description 技术文件下所有的评估单中的b_g2_00102表中监控状态全部为9（包含可见/不可见）
	 * @CreateTime 2018-4-3 上午10:40:44
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 */
	int selectByBg200102Count(String id);
	/**
	 * 
	 * @Description 此条评估单对应的技术文件的监控状态=0初始，才可以，否则不容许这样操作
	 * @CreateTime 2018-4-3 上午10:40:44
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 */
	int selectByJkeq0(String id);
}