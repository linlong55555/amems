package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.po.Login;

public interface LoginMapper {
    int deleteByPrimaryKey(String id);

    int insert(Login record);

    int insertSelective(Login record);

    /**
     * 分页查询账号
     * @param entity
     * @return
     */
    List<Login> selectPage(BaseEntity entity);
    
    /**
     * 分页查询未绑定账号
     * @param entity
     * @return
     */
    List<Login> selectUnboundPage(BaseEntity entity);
    
    /**
     * 按账号名count
     * @param userName
     * @return
     */
    int selectCountByUserName(String userName);
    
    Login selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);

	int selectCounts(String jgdm);

	Login seelctById(String drzhid);

	Login selectByPrimaryName(String accountName);

	int queryCountByJgdm(String jgdm);

	List<Login> findAllLogin();
}