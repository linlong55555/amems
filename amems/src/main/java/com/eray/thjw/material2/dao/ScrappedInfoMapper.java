package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.ScrappedInfo;

public interface ScrappedInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ScrappedInfo record);

    int insertSelective(ScrappedInfo record);

    ScrappedInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ScrappedInfo record);

    int updateByPrimaryKey(ScrappedInfo record);
    /**
     * 
     * @Description 批量新增
     * @CreateTime 2018年3月23日 上午9:51:30
     * @CreateBy 岳彬彬
     * @param list
     */
    void insertBachInfo(List<ScrappedInfo> list);
    /**
     * 
     * @Description 根据mainid删除数据
     * @CreateTime 2018年3月23日 下午5:30:17
     * @CreateBy 岳彬彬
     * @param mainid
     */
    void deleteByMainid(String mainid);
    /**
     * 
     * @Description 在报废明细中没有一个已经销毁才能关闭
     * @CreateTime 2018年3月26日 上午11:51:55
     * @CreateBy 岳彬彬
     * @param mainid
     * @return
     */
    int doValidation4Close(String mainid);
    /**
     * 
     * @Description 根据mainid获取所有的报废明细
     * @CreateTime 2018年3月26日 下午1:36:54
     * @CreateBy 岳彬彬
     * @param mainid
     * @return
     */
    List<ScrappedInfo> selectByMainid(String mainid);
    /**
     * 
     * @Description 根据id集合获取报废明细数据
     * @CreateTime 2018年3月27日 下午5:42:07
     * @CreateBy 岳彬彬
     * @param list
     * @return
     */
    List<ScrappedInfo> getListByIdList(List<String> list);
}