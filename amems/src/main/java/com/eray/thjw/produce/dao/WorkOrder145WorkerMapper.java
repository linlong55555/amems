package com.eray.thjw.produce.dao;

import com.eray.thjw.produce.po.WorkOrder145Worker;
import com.eray.thjw.produce.po.Workorder145;

/**
 * @Description 145工单信息-工作者mapper
 * @CreateTime 2018年1月25日 上午10:14:37
 * @CreateBy 韩武
 */
public interface WorkOrder145WorkerMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkOrder145Worker record);

    int insertSelective(WorkOrder145Worker record);

    WorkOrder145Worker selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkOrder145Worker record);

    int updateByPrimaryKey(WorkOrder145Worker record);
    
    /**
     * @Description 删除工作者
     * @CreateTime 2018年1月24日 上午10:23:12
     * @CreateBy 韩武
     * @param workorder145
     * @return
     */
	int deleteNotExist(Workorder145 workorder145);
}