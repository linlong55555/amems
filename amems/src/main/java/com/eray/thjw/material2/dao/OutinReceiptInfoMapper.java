package com.eray.thjw.material2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.material2.po.OutinReceiptInfo;

/**
 * @Description 收货单-信息mapper
 * @CreateTime 2018年3月12日 下午2:49:51
 * @CreateBy 韩武
 */
public interface OutinReceiptInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutinReceiptInfo record);

    int insertSelective(OutinReceiptInfo record);

    OutinReceiptInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutinReceiptInfo record);

    int updateByPrimaryKey(OutinReceiptInfo record);
    
    /**
     * @Description 根据收货单id删除收货单明细
     * @CreateTime 2018年3月12日 上午10:11:33
     * @CreateBy 韩武
     * @param shdid
     * @return
     */
    int deleteNotExistByShdid(@Param("shdid")String shdid, @Param("details")List<OutinReceiptInfo> details);
    
    /**
	 * @Description 收货单保存时更新收货单明细数据
	 * @CreateTime 2018年3月12日 上午10:44:13
	 * @CreateBy 韩武
	 * @param detail
	 * @return
	 */
	int updateByOutinReceiptInfo(OutinReceiptInfo detail);
	
	/**
	 * @Description 根据收货单id删除
	 * @CreateTime 2018年3月20日 上午9:47:53
	 * @CreateBy 韩武
	 * @param shdid
	 * @return
	 */
	int deleteByShdid(String shdid);
}