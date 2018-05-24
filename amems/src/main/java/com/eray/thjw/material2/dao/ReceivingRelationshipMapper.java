package com.eray.thjw.material2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.material2.po.OutinReceiptInfo;
import com.eray.thjw.material2.po.ReceivingRelationship;

public interface ReceivingRelationshipMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReceivingRelationship record);

    int insertSelective(ReceivingRelationship record);

    ReceivingRelationship selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceivingRelationship record);

    int updateByPrimaryKey(ReceivingRelationship record);
    
    /**
     * @Description 根据收货单id删除收货关系
     * @CreateTime 2018年3月12日 上午10:11:33
     * @CreateBy 韩武
     * @param shdid
     * @return
     */
    int deleteNotExistByShdid(@Param("shdid")String shdid, @Param("details")List<OutinReceiptInfo> details);
    
    /**
     * @Description 根据收货单明细删除
     * @CreateTime 2018年3月12日 下午12:21:46
     * @CreateBy 韩武
     * @param shmxid
     * @return
     */
    int deleteByShmxid(String shmxid);
    
    /**
     * @Description 根据收货单id删除
     * @CreateTime 2018年3月20日 上午9:53:57
     * @CreateBy 韩武
     * @param shdid
     * @return
     */
    int deleteByShdid(String shdid);
    
    /**
     * @Description 根据收货单id无效
     * @CreateTime 2018年3月20日 下午2:59:33
     * @CreateBy 韩武
     * @param shdid
     * @return
     */
    int invalidateByshdid(String shdid);
}