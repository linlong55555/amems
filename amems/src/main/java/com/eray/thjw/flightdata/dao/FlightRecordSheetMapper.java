package com.eray.thjw.flightdata.dao;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;

public interface FlightRecordSheetMapper {
    int deleteByPrimaryKey(String id);

    int insert(FlightRecordSheet record);

    int insertSelective(FlightRecordSheet record);

    FlightRecordSheet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FlightRecordSheet record);

    int updateByPrimaryKey(FlightRecordSheet record);
    
    /**
     * 飞行记录单分页查询
     * @param record
     * @return
     */
    List<FlightRecordSheet> queryByPage(FlightRecordSheet record);
    
    /**
     * 根据飞机注册号统计使用量
     * @param fjzch
     * @return
     */
    FlightRecord countUsageByFjzch(FlightRecordSheet sheet);
    
    /**
     * 根据条件查询飞行记录单
     * @param sheet
     * @return
     */
    List<FlightRecordSheet> queryList(FlightRecordSheet sheet);
    
    /**
     * 根据飞机注册号查询记录本页码
     * @param fjzch
     * @return
     */
    List<String> queryRecordNumByFjzch(String fjzch);
    
    /**
     * 获取飞行数据详细
     * @param id
     * @return
     */
    FlightRecordSheet getFlightDataDetail(String id);
    
    /**
     * 获取最大的飞行日期
     * @param sheet
     * @return
     */
    Date findMaxFxrq(FlightRecordSheet sheet);
    
    /**
     * 获取部件在库存中的数量
     * @return
     */
    int getCountInStock(LoadingList ll);
    
    /**
     * 获取部件在外场库存中的数量
     * @return
     */
    int getCountInOutStock(LoadingList ll);
    
    /**
     * 获取下一页
     * @param id
     * @return
     */
    String getNextPage(FlightRecordSheet sheet);
    
    /**
     * 获取上一页
     * @param id
     * @return
     */
    String getPreviousPage(FlightRecordSheet sheet);
    
    /**
     * 装机清单导入获取飞行记录单数据
     * @param sheet
     * @return
     */
    int getCountByLoadingListImport(PlaneData planeData);
    
    /**
     * 获取前一条飞行记录单
     * @param fxjldid
     * @return
     */
    FlightRecordSheet getPreviousRecord(String fxjldid);
    
    /**
     * 更新后面的飞行记录单的提醒标识
     * @param fxjldid
     * @return
     */
    int updateAfterNoticeFlag(String fxjldid);
    
    List<FlightRecordSheet> getHbhFxrqFlightRecord(FlightRecordSheet sheet);
    
    /**
     * 
     * @Description 获取航班号
     * @CreateTime 2017年9月29日 下午3:24:50
     * @CreateBy 胡才秋
     * @param sheet
     * @return
     */
    List<FlightRecordSheet> queryFlightRecordList(FlightRecordSheet sheet);
}