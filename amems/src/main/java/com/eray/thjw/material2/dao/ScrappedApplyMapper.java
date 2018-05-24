package com.eray.thjw.material2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.material2.po.ScrappedApply;
/**
 * 
 * @Description 报废申请Mapper
 * @CreateTime 2018年3月22日 上午11:30:20
 * @CreateBy 岳彬彬
 */
public interface ScrappedApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(ScrappedApply record);

    int insertSelective(ScrappedApply record);

    ScrappedApply selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ScrappedApply record);

    int updateByPrimaryKey(ScrappedApply record);
    /**
     * 
     * @Description 报废申请列表查询
     * @CreateTime 2018年3月22日 上午11:30:29
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<ScrappedApply> getAllList(ScrappedApply record);
    /**
     * 
     * @Description 报废申请详细信息
     * @CreateTime 2018年3月22日 下午2:27:06
     * @CreateBy 岳彬彬
     * @param id
     * @return
     */
    List<Map<String,Object>> getListById(ScrappedApply record);
    /**
     * 
     * @Description 同组织机构下报废单号唯一
     * @CreateTime 2018年3月22日 下午5:26:06
     * @CreateBy 岳彬彬
     * @param bfdh
     * @param dprtcode
     * @return
     */
    int getCount4CheckExist(@Param("bfdh")String bfdh, @Param("dprtcode")String dprtcode);
    /**
     * 
     * @Description 根据id获取数据
     * @CreateTime 2018年3月23日 下午3:59:09
     * @CreateBy 岳彬彬
     * @param id
     * @return
     */
    ScrappedApply getRecord(String id);
    /**
     * 
     * @Description 获取报废审核列表
     * @CreateTime 2018年3月26日 下午2:13:40
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<ScrappedApply> getAuditList(ScrappedApply record);
}