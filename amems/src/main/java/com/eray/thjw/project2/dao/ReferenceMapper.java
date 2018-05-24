package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.Reference;

/**
 * @Description 相关参考文件Mapper
 * @CreateTime 2017-8-14 下午4:07:31
 * @CreateBy 刘兵
 */
public interface ReferenceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Reference record);

    int insertSelective(Reference record);

    Reference selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Reference record);
    
    /**
     * @Description 根据业务id删除相关参考文件
     * @CreateTime 2017-8-18 下午8:23:20
     * @CreateBy 刘兵
     * @param ywid 业务id
     * @return int
     */
    int deleteByYwid(String ywid);
    
    /**
     * @Description 批量删除相关参考文件
     * @CreateTime 2017-8-19 下午5:36:22
     * @CreateBy 刘兵
     * @param idList 相关参考文件id集合
     * @return int
     */
    int delete4Batch(List<String> idList);
    
    /**
     * @Description 批量新增相关参考文件
     * @CreateTime 2017-8-19 下午3:02:02
     * @CreateBy 刘兵
     * @param referenceList 相关参考文件集合
     * @return int
     */
    int insert4Batch(List<Reference> referenceList);
    
    /**
	 * @Description 根据条件查询相关参考文件列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param reference 工种工时
	 * @return List<Reference> 工种工时集合
	 */
    List<Reference> queryAllList(Reference reference);
    
    /**
     * @Description 根据业务id、业务类型、机构代码查询相关参考文件列表
     * @CreateTime 2017-8-19 下午4:38:20
     * @CreateBy 刘兵
     * @param ywid 业务ID
     * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
     * @param dprtcode 机构代码
     * @return List<Reference> 相关参考文件集合
     */
    List<Reference> queryByYwidAndYwlxAndDrpt(@Param("ywid")String ywid, @Param("ywlx")int ywlx, @Param("dprtcode")String dprtcode);
    /**
     * 
     * @Description 同步参考文件
     * @CreateTime 2017年10月10日 上午10:58:03
     * @CreateBy 岳彬彬
     * @param reference
     */
    void insertByCopyReference(Reference reference);
    
    /**
     * @Description 同步参考文件
     * @CreateTime 2017-10-17 下午5:50:19
     * @CreateBy 刘兵
     * @param reference 参考文件
     */
    void insertByCopy(Reference reference);
}