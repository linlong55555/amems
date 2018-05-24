package com.eray.thjw.productionplan.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;

/**
 * 装机清单列表-编辑区
 * @author hanwu
 *
 */
public interface LoadingListEditableMapper {
    int deleteByPrimaryKey(String id);

    int insert(LoadingList record);

    int insertSelective(LoadingList record);

    LoadingList selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoadingList record);

    int updateByPrimaryKey(LoadingList record);
    
    LoadingList selectByPlane(LoadingList record);
    
    List<LoadingList> select(LoadingList record);
    
    List<LoadingList> selectTimeMonitor(LoadingList record);
    
    List<LoadingList> selectFixMonitor(LoadingList record);
    
    List<LoadingList> queryPage(LoadingList record);
    
    int queryCount(LoadingList record);
    
    List<LoadingList> selectEditableInTree(LoadingList record);
    
    List<LoadingList> queryNoChildList(LoadingList record);
    
    int batchUpdate(List<LoadingList> list);
    
    int insertOrUpdateEditable(LoadingList record);
    
    int cascadeScrapEditable(LoadingList record);
    
    List<String> getChildrenId(LoadingList record);
    
    List<LoadingList> getChildren(LoadingList record);
    
    int updateChildenWz(LoadingList record);
    
    List<TimeMonitorSetting> getTcAndTv(String zjqdid);
    
    int updateChildrenFjdid(LoadingList record);
    
    List<LoadingList> selectEffectiveInTree(LoadingList record);
    
    int batchMerge(List<LoadingList> list);
    
    int writeToLoadingListHistory(String zjqdid, String xgid);
    
    int deleteLoadingListHistory(Map<String, Object> paramMap);
    
    int updateTimeMonitorSetting(Map<String, Object> paramMap);
    
    int updateFixedMonitorSetting(Map<String, Object> paramMap);
    
    int updateFixedMonitorDetail(Map<String, Object> paramMap);
    
    int writeToLoadingListHistoryByBean(LoadingList record);
    
    List<LoadingList> queryEditableParentTree(LoadingList record);
    
    /**
     * 查询根节点（飞机本身的装机清单数据）
     * @param pd
     * @return
     */
    LoadingList selectRootNode(PlaneData pd);
    
}