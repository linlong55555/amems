package com.eray.thjw.material2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.material2.po.OutinReceiptInfo;
import com.eray.thjw.material2.po.OutinReceiptShelves;

/**
 * @Description 收货单-上架mapper
 * @CreateTime 2018年3月12日 下午2:50:06
 * @CreateBy 韩武
 */
public interface OutinReceiptShelvesMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutinReceiptShelves record);

    int insertSelective(OutinReceiptShelves record);

    OutinReceiptShelves selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutinReceiptShelves record);

    int updateByPrimaryKey(OutinReceiptShelves record);
    
    /**
     * @Description 根据收货单详情id删除收货单-上架信息
     * @CreateTime 2018年3月12日 上午11:21:57
     * @CreateBy 韩武
     * @param mainid
     * @param shelves
     * @return
     */
    int deleteNotExistByMainid(@Param("mainid")String mainid, @Param("shelves")List<OutinReceiptShelves> shelves);
    
    /**
     * @Description 收货单保存时更新收货单-上架数据
     * @CreateTime 2018年3月12日 上午11:23:17
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updateByReceiptShelves(OutinReceiptShelves record);
    
    /**
     * @Description 根据收货单id删除收货单-上架信息
     * @CreateTime 2018年3月12日 上午11:31:00
     * @CreateBy 韩武
     * @param shdid
     * @param details
     * @return
     */
    int deleteNotExistByShdid(@Param("shdid")String shdid, @Param("details")List<OutinReceiptInfo> details);
    
    List<OutinReceiptShelves> selectReceiptShelvesList(List<String> shidList);
    
    /**
     * @Description 根据收货单id删除
     * @CreateTime 2018年3月20日 上午9:50:09
     * @CreateBy 韩武
     * @param shdid
     * @return
     */
    int deleteByShdid(String shdid);
    
    /**
     * @Description 验证收货数量和库存数量是否相同
     * @CreateTime 2018年3月20日 下午1:48:50
     * @CreateBy 韩武
     * @param shdid
     * @return
     */
    int validateStockCountByShdid(String shdid);
}