package com.eray.thjw.flightdata.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.FlightRecord;
import com.eray.thjw.flightdata.po.FlightRecordSheet;
import com.eray.thjw.productionplan.po.LoadingList;

/**
 * b_s_006 飞行记录单服务接口
 * @author hanwu
 *
 */
public interface FlightRecordSheetService {
	
	/**
	 * 飞行记录单分页查询
	 * @param sheet
	 * @return
	 */
	public Map<String, Object> queryByPage(FlightRecordSheet sheet) throws BusinessException;
	
	/**
	 * 加载飞行前数据
	 * @param fjzch
	 * @param fxjldh
	 * @return
	 */
	public List<FlightRecord> loadPreflightData(FlightRecordSheet sheet);
	
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
     * 保存飞行记录单数据
     * @param data
     */
    String save(FlightRecordSheet data) throws BusinessException;
    
    /**
     * 获取所有飞行记录单相关数据
     * @param id
     * @return
     */
    FlightRecordSheet getAllData(String id);
    
    /**
     * 根据id查找飞行记录单
     * @param id
     * @return
     */
    FlightRecordSheet findById(String id);
    
    /**
     * 验证记录本页码和飞行日期
     * @param flightRecordSheet
     * @return
     */
    Map<String, Object> validate(FlightRecordSheet flightRecordSheet);
    
    /**
     * 加载部件实际使用值
     * @param sheet
     * @return
     */
    Map<String, Object> loadActuallyUsed(ComponentUsage data);
    
    /**
     * 判断部件是否在库存中
     * @param ll
     * @return
     */
    boolean isExistInStock(LoadingList ll);
    
    /**
     * 判断部件是否在外场库存中
     * @param ll
     * @return
     */
    boolean isExistInOutStock(LoadingList ll);
    
    /**
     * 获取飞行记录单的上一页或下一页
     * @param id
     * @param pageFlag
     * @return
     */
    String getPageId(String id, Integer pageFlag);
    
    List<FlightRecordSheet> getHbhFxrqFlightRecord(FlightRecordSheet sheet);

	public List<com.eray.thjw.produce.po.FlightSheet> queryFxrqFlightRecordList(com.eray.thjw.produce.po.FlightSheet sheet);
    
}
