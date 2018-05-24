package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Secondment;

/**
 * b_h_014 借调对象表
 * @author xu.yong
 *
 */
public interface SecondmentMapper {
    
	List<Secondment> selectSecondmentList(Secondment record);  //借调对象维护列表分页
	
	int selectCount(String jddxbh);     //判断编号是否重复
	
	int insertSelective(Secondment record);   //新增借调对象
	
	Secondment selectById(String id);    //根据id查询一个借调对象
	
	int updateByPrimaryKeySelective(Secondment record); //更新借调对象
	
	int deleteByPrimaryKey(String id);

    int insert(Secondment record);

    int updateByPrimaryKey(Secondment record);
    
    /**
     * 根据组织机构、借调对象类型查询所有有效状态的借调对象
     * @param record
     * @return
     */
    List<Secondment> selectByType(Secondment record);
    
    /**
     * 根据借调对象编号查询
     * @param dprtcode
     * @param jddxbh 借调对象编号
     * @return
     */
    Secondment selectByBH(String dprtcode, String jddxbh);

	Secondment selectByIds(String fxd);
	
	List<Secondment> queryByDprtcode(String dprtcode);

	List<Secondment> queryOtherOrg(String jgdm);
	
	/**
	 * 查询所有的借调对象
	 * @param secondment
	 * @return
	 */
	List<Secondment> queryAll(Secondment secondment);
	
}