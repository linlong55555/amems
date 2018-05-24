package com.eray.thjw.quality.dao;

import java.util.List;

import com.eray.thjw.quality.po.AuditDiscoveryDetail;

/**
 * @Description 审核问题清单Mapper
 * @CreateTime 2018年1月8日 下午1:46:06
 * @CreateBy 韩武
 */
public interface AuditDiscoveryDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditDiscoveryDetail record);

    int insertSelective(AuditDiscoveryDetail record);

    AuditDiscoveryDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditDiscoveryDetail record);

    int updateByPrimaryKey(AuditDiscoveryDetail record);
    /**
     * 
     * @Description 批量新增
     * @CreateTime 2018年1月9日 下午4:19:56
     * @CreateBy 岳彬彬
     * @param list
     */
    void batchInsert(List<AuditDiscoveryDetail> list);
    /**
     * 
     * @Description 批量修改
     * @CreateTime 2018年1月9日 下午4:20:34
     * @CreateBy 岳彬彬
     * @param list
     */
    void batchUpdate(List<AuditDiscoveryDetail> list);
    /**
     * 
     * @Description 验证审核问题编号唯一性
     * @CreateTime 2018年1月9日 下午4:21:01
     * @CreateBy 岳彬彬
     * @param shwtbh
     * @return
     */
    int getCount(String shwtbh,String dprtcode);
    /**
     * 
     * @Description 根据审核问题单id获取审核问题清单
     * @CreateTime 2018年1月10日 上午11:07:56
     * @CreateBy 岳彬彬
     * @param shwtdid
     * @return
     */
    List<AuditDiscoveryDetail> getByShwtdid(String shwtdid);
    /**
     * 
     * @Description 批量删除
     * @CreateTime 2018年1月10日 下午4:38:17
     * @CreateBy 岳彬彬
     * @param list
     */
    void batchDel(List<String> list);
    /**
     * 
     * @Description 根据审核问题单id删除审核问题清单
     * @CreateTime 2018年1月10日 下午5:10:27
     * @CreateBy 岳彬彬
     * @param shwtdid
     */
    void deleteByShwtdid(String shwtdid);
    /**
     * 
     * @Description 批量关闭
     * @CreateTime 2018年1月11日 上午9:52:01
     * @CreateBy 岳彬彬
     * @param record
     */
    void batchClose(AuditDiscoveryDetail record);
    /**
     * 
     * @Description 问题整改措施列表
     * @CreateTime 2018年1月11日 下午3:53:34
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<AuditDiscoveryDetail> queryList(AuditDiscoveryDetail record);
    /**
     * 
     * @Description 根据id获取数据
     * @CreateTime 2018年1月12日 上午10:22:54
     * @CreateBy 岳彬彬
     * @param id
     * @return
     */
    AuditDiscoveryDetail getById(String id);
    /**
     * 
     * @Description 根据id集合获取问题清单
     * @CreateTime 2018年1月15日 上午10:49:09
     * @CreateBy 岳彬彬
     * @param list
     * @return
     */
    List<AuditDiscoveryDetail> getByIdList(List<String> list);
    /**
     * 
     * @Description 批量反馈
     * @CreateTime 2018年1月15日 上午11:17:02
     * @CreateBy 岳彬彬
     * @param list
     * @return
     */
   void batchFeedback(List<String> list);
   /**
    * 
    * @Description 整改措施列表
    * @CreateTime 2018年1月17日 下午4:36:32
    * @CreateBy 岳彬彬
    * @param record
    * @return
    */
   List<AuditDiscoveryDetail> queryReticList(AuditDiscoveryDetail record);
}