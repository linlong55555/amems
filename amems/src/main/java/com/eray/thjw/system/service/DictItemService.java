
package com.eray.thjw.system.service;

import java.util.List;

import com.eray.thjw.system.po.DictItem;
public interface DictItemService {
	/**
	 * 根据字典类型名称查询字典明细
	 * @param DictItem
	 * @return List<DictItem>
	 * @author Meizhiliang
	 */
	public List<DictItem> getDictItemList(DictItem  dictItem);      
	/**
	 * 增加字典项明细
	 * @param DictItem
	 * @return int
	 * @author Meizhiliang
	 */
	public String insertSelective(DictItem  dictItem);
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年8月17日 下午10:07:24
	 * @CreateBy Meizhiliang
	 * @UpdateBy 李高升
	 * @param dictItem
	 * @return
	 */
	public String updateByPrimaryKeySelective(DictItem  dictItem);
	/**
	 * 删除字典项明细
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public String deleteByDictItem(DictItem  dictItem);
	
	/**
	 * 同步字典
	 * @param DictItem
	 * @author Meizhiliang
	 */
	public String doSynch(Integer lxid,String dprtcode);
	
}
