package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.system.po.Dict;
import com.eray.thjw.system.po.DictItem;

public interface DictMapper {
	/**
	 * 根据组织机构查询字典项
	 * @param newdic
	 * @return List<NewDic>
	 * @author Meizhiliang
	 */
	public List<Dict> getNewDicListByDprtcode(Dict  newdic);  
	/**
	 * 根据类型id和组织机构查询字典
	 * @param newdic
	 * @return Dict
	 * @author Meizhiliang
	 */
	public Dict selectDict(Integer lxid,String dprtcode);
	/**
	 * 增加字典项
	 * @param DictItem
	 * @return int
	 * @author Meizhiliang
	 */
	public void insertSelective(DictItem dictitem); 
	
	/**
	 * 查询全部系统字典
	 * @return
	 */
	public List<Dict> selectDicAndItems();
	/**
	 * 查询全部系统字典
	 * @return
	 */
	public void deleteByPrimaryKey(Integer lxid,String dprtcode);

}