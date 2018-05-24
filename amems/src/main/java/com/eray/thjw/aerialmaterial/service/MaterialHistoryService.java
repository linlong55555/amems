package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.MaterialHistory;
public interface MaterialHistoryService {

	void insert(MaterialHistory materialHistory);
	
	List<MaterialHistory> selectKey(String  kcid, String dprt);

	
	/**
	 * @author liub
	 * @description 根据id,kcid修改部件履历表
	 * @param id,kcid
	 * @return int
	 * @develop date 2016.11.01
	 */
	public int updateByStock(String id,String kcid) throws RuntimeException;

	/**
	 * @author meizhiliang
	 * @description 增加移库的部件履历
	 * @develop date 2016.11.01
	 */
	int insertSelective(MaterialHistory materialHistoryList);
	/**
	 * @author meizhiliang
	 * @description 查询一个部件履历
	 * @develop date 2016.11.01
	 */
	MaterialHistory selectByPrimaryKey(String id)throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 修改部件履历
	 * @param materialHistory
	 * @return int
	 * @develop date 2016.11.25
	 */
	int updateByPrimaryKeySelective(MaterialHistory materialHistory);
	/**
	 * @author sunji
	 * @description 根据销毁id获取数据
	 * @param materialHistory
	 * @return list
	 */
	List<MaterialHistory> queryChoStock(String id);

	/**
	 * 
	 * @Description 库存履历列表
	 * @CreateTime 2018年3月21日 下午3:50:37
	 * @CreateBy 林龙
	 * @param materialHistory
	 * @return
	 * @throws RuntimeException
	 */
	Map<String, Object> queryAllPageList(MaterialHistory materialHistory)throws RuntimeException;
}
