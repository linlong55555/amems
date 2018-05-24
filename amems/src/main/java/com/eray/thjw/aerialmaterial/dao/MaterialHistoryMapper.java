package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;

public interface MaterialHistoryMapper {
	
	/**
	 * @author liub
	 * @description 根据id,kcid修改部件履历表
	 * @param id,kcid
	 * @return int
	 * @develop date 2016.11.01
	 */
	public int updateByStock(@Param("id")String id,@Param("kcid")String kcid);
	/**
	 * @author meizhiliang
	 * @description 新增移库记录
	 * @param id,kcid
	 * @return int
	 * @develop date 2016.11.01
	 */
	int insertSelective(MaterialHistory record);
	/**
	 * @author meizhiliang
	 * @description 查询一条履历信息
	 * @return int
	 * @develop date 2016.11.01
	 */
	MaterialHistory selectByPrimaryKey(String id);
	
	
	
	
	
	
	
	List<MaterialHistory> selectKey(String  kcid, String dprt);

	int deleteByPrimaryKey(String id);

    int insert(MaterialHistory record);

    int updateByPrimaryKeySelective(MaterialHistory record);
	/**
	 * @author meizhiliang
	 * @description 添加数据对象为stock
	 * @return int
	 */
    int insertBystock(Stock stock);
    /**
     * @author sunji
     * @description 根据销毁单id获取数据
     * @return int
     */
    List<MaterialHistory> queryChoStock(String mainid);
	/**
	 * 根据idList物理删除数据
	 * @param stock
	 * @return
	 */
	public int deleteids(List<String> idList);
	/**
	 * 根据idList查询数据
	 * @param stock
	 * @return
	 */
	public List<MaterialHistory> queryByIds(List<String> idList);
	public void delete4Batch(List<String>list);
	public List<MaterialHistory> queryAllPageList(MaterialHistory materialHistory);
	/**
	 * 
	 * @Description 批量新增部件履历
	 * @CreateTime 2018年3月23日 上午9:46:52
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	void insertBatchMaterialHistory(List<MaterialHistory> list);
	/**
	 * 
	 * @Description 根据部件履历id获取数据
	 * @CreateTime 2018年3月28日 下午3:41:43
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	MaterialHistory selectById(String id);
}