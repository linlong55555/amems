package com.eray.thjw.produce.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.WorkOrderIORecord;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workorder145IORecord;

/**
 * @Description B_S2_00801 135工单拆装记录
 * @CreateTime 2017年10月9日 上午9:44:43
 * @CreateBy 徐勇
 */
public interface WorkOrderIORecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkOrderIORecord record);

    int insertSelective(WorkOrderIORecord record);

    WorkOrderIORecord selectByPrimaryKey(String id);
    
    /**
     * @Description 根据多个id查询
     * @CreateTime 2017年12月12日 下午2:05:08
     * @CreateBy 徐勇
     * @param ioIds 多个拆装记录
     * @return
     */
    List<WorkOrderIORecord> selectByIds(@Param("list")List<String> ioIds);

    int updateByPrimaryKeySelective(WorkOrderIORecord record);

    int updateByPrimaryKey(WorkOrderIORecord record);
    /**
     * 
     * @Description 根据工单id获取数据
     * @CreateTime 2017-10-10 下午3:46:57
     * @CreateBy 孙霁
     * @param mainid
     * @return
     */
    List<WorkOrderIORecord> queryAllByGdid(String mainid);
    
    List<WorkOrderIORecord> queryInfoByMainId(WorkOrderIORecord record);

    /**
     * @Description 获取145工单拆换件
     * @CreateTime 2017-10-24 下午2:09:19
     * @CreateBy 雷伟
     * @param gdid
     * @return
     */
	List<WorkOrderIORecord> queryAllRecord145ByGdid(String gdid);
	
	void deleteNotExist(Workorder record);
	
	/**
	 * @Description 删除flb上工单删除的拆装记录
	 * @CreateTime 2017年11月30日 下午3:24:08
	 * @CreateBy 徐勇
	 * @param record
	 * @return
	 */
	int deleteFlbIOByWONotExist(Workorder record);
	
	void insertByWoIO145(Workorder145IORecord record);
	
	int updateByWoIO145(Workorder145IORecord record);
	
	void deleteNotExistWO135(Workorder145 record);
	
	/**
	 * @Description 删除工单拆装记录
	 * @CreateTime 2017年11月27日 上午10:59:48
	 * @CreateBy 徐勇
	 * @param ioIds 拆装记录id
	 * @return
	 */
	int deleteByIds(@Param("list")List<String> ioIds);
	
	/**
	 * @Description 删除flb中引用了工单拆装记录的的数据
	 * @CreateTime 2017年11月30日 下午3:48:50
	 * @CreateBy 徐勇
	 * @param ioIds 拆装记录id
	 * @return
	 */
	int deleteHandFlbIOByIds(@Param("list")List<String> ioIds);
	
	/**
	 * @Description 根据工单id查询，并附带出装上拆下信息
	 * @CreateTime 2017年11月28日 下午2:03:36
	 * @CreateBy 徐勇
	 * @param mainid 工单id
	 * @return
	 */
	List<WorkOrderIORecord> selectWithCxZsByMainid(@Param("mainid")String mainid);
	
	/**
	 * @Description 根据工单拆装记录更新FLB拆装记录
	 * @CreateTime 2017年11月30日 下午3:18:09
	 * @CreateBy 徐勇
	 * @param gdczjlid 工单拆装记录id
	 * @return
	 */
	int updateFlbIOByWorkorderIO(@Param("gdczjlid")String gdczjlid);
	
	/**
	 * @Description 更新装上库存履历
	 * @CreateTime 2017年12月7日 上午10:41:44
	 * @CreateBy 徐勇
	 * @param id 
	 * @param kcid 装上库存
	 * @param kcllid 装上库存id
	 * @return
	 */
	int updateAzKcId(@Param("id")String id, @Param("kcid")String kcid, @Param("kcllid")String kcllid);
	
	/**
	 * @Description 更新拆下库存履历
	 * @CreateTime 2017年12月7日 上午10:41:44
	 * @CreateBy 徐勇
	 * @param id 
	 * @param kcid 拆下库存
	 * @param kcllid 拆下库存id
	 * @return
	 */
	int updateCxKcId(@Param("id")String id, @Param("kcid")String kcid, @Param("kcllid")String kcllid);
	
	/**
	 * @Description 拆换记录更新
	 * @CreateTime 2018年5月9日 下午4:24:24
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateByWorkOrder(WorkOrderIORecord record);
}