package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Substitution;

public interface SubstitutionService {

	
	/**
	 * @author sunji
	 * @description 根据条件查询所有
	 * @param substitution
	 * @return List<Substitution>
	 */
	public Map<String , Object> queryAll(Substitution substitution)throws BusinessException;
	/**
	 * @author sunji
	 * @description 添加
	 * @param substitution
	 * @return id
	 */
	public String save(Substitution substitution)throws BusinessException;
	/**
	 * @author sunji
	 * @description 修改
	 * @param substitution
	 * @return id
	 */
	public String update(Substitution substitution)throws BusinessException;
	/**
	 * @author sunji
	 * @description 作废
	 * @param substitution
	 * @return id
	 */
	public String invalid(Substitution substitution)throws BusinessException;
	
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
	public List<Substitution> selectSubByBjhAndDprt(String bjh,String dprtcode);
	
	/**
	 * @Description 通过部件号、替代件号、机构代码获取等效替代部件信息
	 * @CreateTime 2017-11-21 下午5:31:32
	 * @CreateBy 刘兵
	 * @param bjh 部件号
	 * @param tdjh 替代件号
	 * @param dprtcode 机构代码
	 * @return Substitution 等效替代
	 */
	public Substitution getSubByBjhAndTdjhAndDprt(String bjh, String tdjh, String dprtcode);
	
	/**
	 *@author liud
	 *@description 校验部件号，替代件号唯一性
	 *@param  substitution
	 *@return List<Substitution>
	 */
	public List<Substitution> validateUnique(Substitution substitution);

}
