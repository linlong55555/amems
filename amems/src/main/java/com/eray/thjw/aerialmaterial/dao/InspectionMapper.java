package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.material2.po.OutboundOrder;

public interface InspectionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Inspection record);

    int insertSelective(Inspection record);

    Inspection selectByPrimaryKey(String id);
    
    Inspection getById(String id);

    int updateByPrimaryKeySelective(Inspection record);

    int updateByPrimaryKey(Inspection record);
    
    List<Inspection> selectInspectionList(Inspection record);
    
    /**
	 * @author liub
	 * @description 根据入库单id集合查询检验单
	 * @param map
	 * @return List<Inspection>
	 */
	public List<Inspection> queryByIdList(Map<String, Object> map);
	
	int getCount4validation(String id);

	List<String> selectByKeyInspection(Inspection record);

	void deleteByKeyInspection(Inspection record);

	List<Inspection> queryAllPageList(Inspection inspection);

	Inspection getByInspectionId(Inspection inspection);
	
	/**
	 * @Description 根据收货单id删除
	 * @CreateTime 2018年3月29日 下午3:28:39
	 * @CreateBy 韩武
	 * @param shdid
	 * @return
	 */
	int deleteByShdid(String shdid);

	int queryCount(Inspection tec);

	/**
	 * 
	 * @Description 根据收货单id查询质检单
	 * @CreateTime 2018年4月16日 上午10:39:40
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 */
	List<Inspection> selectByKeyByShdid(String id);

}