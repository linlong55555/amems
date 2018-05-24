package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.material2.po.OutinReceiptSource;

/**
 * 收货单mapper
 * @Description 
 * @CreateTime 2018年3月12日 下午2:50:41
 * @CreateBy 韩武
 */
public interface OutinReceiptMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutinReceipt record);

    int insertSelective(OutinReceipt record);

    OutinReceipt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutinReceipt record);

    int updateByPrimaryKey(OutinReceipt record);
    
    /**
     * @Description 查询合同明细
     * @CreateTime 2018年3月7日 上午10:12:29
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<OutinReceiptSource> queryContractDetailList(OutinReceipt record);
    
    /**
     * @Description 查询退料明细
     * @CreateTime 2018年3月7日 上午10:14:08
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<OutinReceiptSource> queryReturnMaterialList(OutinReceipt record);
    
    /**
     * @Description 收货更新数据
     * @CreateTime 2018年3月9日 下午4:50:02
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByReceipt(OutinReceipt record);
    
    /**
     * @Description 查询收货单详情
     * @CreateTime 2018年3月12日 下午3:02:20
     * @CreateBy 韩武
     * @param id
     * @return
     */
    OutinReceipt queryDetail(String id);
    
    /**
     * @Description 查询当前人的收货单列表
     * @CreateTime 2018年3月19日 下午2:48:25
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<OutinReceipt> querySelfList(OutinReceipt record);
}