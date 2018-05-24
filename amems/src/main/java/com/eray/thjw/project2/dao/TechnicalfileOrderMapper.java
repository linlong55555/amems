package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.TechnicalfileOrder;


public interface TechnicalfileOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(TechnicalfileOrder record);

    int insertSelective(TechnicalfileOrder record);

    TechnicalfileOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TechnicalfileOrder record);

    int updateByPrimaryKey(TechnicalfileOrder record);
    
    /**
     * 
     * @Description 根据适航指令ids获取数据
     * @CreateTime 2017-8-19 上午10:54:45
     * @CreateBy 孙霁
     * @param arids
     * @return
     */
    List<TechnicalfileOrder> queryAllByMainids(List<String> arids);
    
    /**
     * 
     * @Description 根据适航指令mainnid获取数据
     * @CreateTime 2017-8-19 上午10:55:18
     * @CreateBy 孙霁
     * @param mainid
     * @return
     */
    List<TechnicalfileOrder> queryAllByMainid(String mainid);
    /**
	 * 
	 * @Description 根据组适航性资料id删除数据
	 * @CreateTime 2017-8-12 下午2:43:31
	 * @CreateBy 孙霁
	 * @param technicalfileOrderId
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param dprtcode
	 * @throws BusinessException
	 */
    int deleteByMainid(String mainid);

    /**
	 * 
	 * @Description 修改评估人
	 * @CreateTime 2017年11月20日 上午10:30:37
	 * @CreateBy 林龙
	 * @param jswjid
	 * @param jx
	 * @param pgrid
	 */
	void updatetBypgr(String jswjid, String jx, String pgrid);
}