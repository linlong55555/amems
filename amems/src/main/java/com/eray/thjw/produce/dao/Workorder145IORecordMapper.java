package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workorder145IORecord;

public interface Workorder145IORecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(Workorder145IORecord record);

    int insertSelective(Workorder145IORecord record);

    Workorder145IORecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Workorder145IORecord record);

    int updateByPrimaryKey(Workorder145IORecord record);
    /**
     * 
     * @Description 批量新增工单145拆装记录
     * @CreateTime 2017年10月10日 上午9:56:24
     * @CreateBy 岳彬彬
     * @param list
     */
    void insertWorkorder145IORecordList(Workorder145IORecord record);
    /**
     * 
     * @Description 删除记录
     * @CreateTime 2017年11月1日 上午11:14:55
     * @CreateBy 岳彬彬
     * @param record
     */
    void deleteNotExist(Workorder145 record);
    
    /**
     * @Description 145工单拆换记录更新
     * @CreateTime 2017年12月12日 下午1:45:30
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByWorkOrder145(Workorder145IORecord record);
}