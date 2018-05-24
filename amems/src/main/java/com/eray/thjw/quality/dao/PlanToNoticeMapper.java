package com.eray.thjw.quality.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.quality.po.PlanToNotice;

public interface PlanToNoticeMapper {
    int insert(PlanToNotice record);

    int insertSelective(PlanToNotice record);
    
    int delete(String shtzdid);
    
    /**
     * @Description 根据年度审核计划id删除
     * @CreateTime 2018-1-19 上午9:53:20
     * @CreateBy 刘兵
     * @param ndshjhid
     * @return
     */
    int deleteByNdshjhid(String ndshjhid);
    
    /**
     * @Description 复制年度审核计划与审核通知单关系
     * @CreateTime 2018-01-20 下午5:15:09
     * @CreateBy 刘兵
     * @param newndshjhid 新的newndshjhid
     * @param ndshjhid 条件ndshjhid
     */
    void insert4Copy(@Param("newndshjhid")String newndshjhid, @Param("ndshjhid")String ndshjhid);
}