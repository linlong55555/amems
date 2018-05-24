package com.eray.thjw.basic.dao;

import java.util.List;

import com.eray.thjw.basic.po.Propertyright;

public interface PropertyrightMapper {
    int deleteByPrimaryKey(String id);

    int insert(Propertyright record);

    int insertSelective(Propertyright record);

    Propertyright selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Propertyright record);

    int updateByPrimaryKey(Propertyright record);
    
    /**
     * @Description 根据条件查询数据
     * @CreateTime 2018-2-5 上午10:59:26
     * @CreateBy 孙霁
     * @param record
     * @return
     */
    List<Propertyright> queryAll(Propertyright record);
    
    /**
     * 
     * @Description 根据条件查询数量
     * @CreateTime 2018-2-5 下午2:00:50
     * @CreateBy 孙霁
     * @param propertyright
     * @return
     */
    int findByCqbh(Propertyright propertyright);

	List<Propertyright> selectAllByDprtcode(String jgdm);

	Propertyright selectByPrimaryHCMainData(String cqid, String jgdm);
}