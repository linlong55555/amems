package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.BackRegister;
import com.eray.thjw.material2.po.MeasurementToolsDetails;

/**
 * 
 * @Description 退料登记mapper
 * @CreateTime 2018年3月5日 上午9:45:47
 * @CreateBy 林龙
 */
public interface BackRegisterMapper {
    int deleteByPrimaryKey(String id);

    int insert(BackRegister record);

    int insertSelective(BackRegister record);

    BackRegister selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BackRegister record);

    int updateByPrimaryKey(BackRegister record);

	List<BackRegister> queryAllPageList(BackRegister backRegister);

	BackRegister getInfoById(BackRegister backRegister);
}