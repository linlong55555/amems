package com.eray.thjw.system.dao;

import java.util.List;
import com.eray.thjw.system.po.DictItem;

public interface DictItemMapper {
	/**
	 * 根据字典类型名称查询字典明细
	 * @param DictItem
	 * @return List<DictItem>
	 * @author Meizhiliang
	 */
	public List<DictItem> getDictItemList(DictItem  dictItem);  
	/**
	 * 验证字典项是否能新增
	 * @param DictItem
	 * @return List<DictItem>
	 * @author Meizhiliang
	 */
	public int getDictItemCount(DictItem  dictItem); 
	/**
	 * 增加字典项明细
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public void insertSelective(DictItem  dictItem); 
	/**
	 * copy字典项明细
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public void insertDictItem(DictItem  dictItem); 
	/**
	 * 修改字典项明细
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public void updateByPrimaryKeySelective(DictItem  dictItem); 
	/**
	 * 删除字典项明细
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public void delete(DictItem  dictItem);
	/**
	 * 同步批量删除字典项明细
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public void deleteByKey(Integer lxid,String dprtcode);
	/**
	 * 
	 * @Description 根据类型id和组织机构获取数据字典
	 * @CreateTime 2017年12月1日 上午11:00:05
	 * @CreateBy 岳彬彬
	 * @param lxid
	 * @param dprtcode
	 * @return
	 */
	List<DictItem> selectByLxidAndDprtcode(String lxid,String dprtcode);
}