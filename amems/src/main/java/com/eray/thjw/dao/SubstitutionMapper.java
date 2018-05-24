package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.Substitution;

public interface SubstitutionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Substitution record);

    int insertSelective(Substitution record);

    Substitution selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Substitution record);

    int updateByPrimaryKey(Substitution record);
    /**
	 * @author sunji
	 * @description 根据条件查询所有
	 * @param substitution
	 * @return List<Substitution>
	 */
    List<Substitution> queryAll(Substitution substitution);
    
	/**
	 * @author liub
	 * @description 根据部件号集合、机构代码查询等效替代部件
	 * @param map
	 * @return List<Substitution>
	 */
	public List<Substitution> queryByBjhList(Map<String, Object> map);
	
	/**
	 * @author liub
	 * @description 根据id查询等效替代部件及对应的机型适应性、工卡适应性信息
	 * @param id
	 * @return Substitution
	 */
	public Substitution selectById(String id);
	
	/**
	 * @author liub
	 * @description 通过部件号、机构代码获取等效替代部件信息
	 * @param bjh,dprtcode
	 * @return List<Substitution>
	 */
	public List<Substitution> selectSubByBjhAndDprt(@Param("bjh")String bjh,@Param("dprtcode")String dprtcode);
	
	/**
	 * @Description 通过部件号、替代件号、机构代码获取等效替代部件信息
	 * @CreateTime 2017-11-21 下午5:31:32
	 * @CreateBy 刘兵
	 * @param bjh 部件号
	 * @param tdjh 替代件号
	 * @param dprtcode 机构代码
	 * @return Substitution 等效替代
	 */
	public Substitution getSubByBjhAndTdjhAndDprt(@Param("bjh")String bjh, @Param("tdjh")String tdjh, @Param("dprtcode")String dprtcode);
	
    /**
	 * @author sunji
	 * @description 根据组织机构查询所有
	 * @param substitution
	 * @return List<Substitution>
	 */
    List<Substitution> queryAllByDprtcode(Substitution substitution);
    
    
    
    List<Substitution> validateUnique(Substitution substitution);
}