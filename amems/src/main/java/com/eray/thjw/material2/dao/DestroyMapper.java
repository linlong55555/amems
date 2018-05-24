package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.Destroy;
/**
 * 
 * @Description 销毁
 * @CreateTime 2018年3月27日 下午3:41:17
 * @CreateBy 岳彬彬
 */
public interface DestroyMapper {
    int deleteByPrimaryKey(String id);

    int insert(Destroy record);

    int insertSelective(Destroy record);

    Destroy selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Destroy record);

    int updateByPrimaryKey(Destroy record);
    /**
     * 
     * @Description 待销毁列表
     * @CreateTime 2018年3月27日 下午4:22:50
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<Destroy> getAllPageList(Destroy record);
    /**
     * 
     * @Description 验证不存在与b_h2_021 销毁（销毁状态=1）的数据
     * @CreateTime 2018年3月27日 下午5:37:44
     * @CreateBy 岳彬彬
     * @param list
     * @return
     */
    int getCount4ValidDestroy(List<String> list);
    /**
     * 
     * @Description 已销毁列表
     * @CreateTime 2018年3月28日 下午2:11:35
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<Destroy> getAllDestroyList(Destroy record);
    /**
     * 
     * @Description 撤销验证不存在已撤销的数据
     * @CreateTime 2018年3月28日 下午3:17:33
     * @CreateBy 岳彬彬
     * @param list
     * @return
     */
    int getCount4ValidRevoke(List<String> list);
    /**
     * 
     * @Description 根据报废明细id做撤销
     * @CreateTime 2018年3月28日 下午3:28:09
     * @CreateBy 岳彬彬
     * @param record
     */
    void update4Revoke(Destroy record);
    /**
     * 
     * @Description 根据报废明细id获取销毁数据
     * @CreateTime 2018年3月28日 下午4:25:03
     * @CreateBy 岳彬彬
     * @param bfmxid
     * @return
     */
    Destroy selectByBfmxid(String bfmxid);
}