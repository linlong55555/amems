package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.WorkCard2Related;

/**
 * @Description 工卡-关联工卡Mapper
 * @CreateTime 2017-8-25 下午5:20:46
 * @CreateBy 刘兵
 */
public interface WorkCard2RelatedMapper {
    int deleteByPrimaryKey(String id);

    int insert(WorkCard2Related record);

    int insertSelective(WorkCard2Related record);

    WorkCard2Related selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WorkCard2Related record);
    
    /**
     * @Description 根据mainid删除工卡-关联工卡
     * @CreateTime 2017-9-13 下午5:13:51
     * @CreateBy 刘兵
     * @param mainid 主表id
     * @return int
     */
    int deleteByMainid(String mainid);
    
    /**
    * @Description 批量删除工卡-关联工卡
    * @CreateTime 2017-8-19 下午5:36:22
    * @CreateBy 刘兵
    * @param idList 工卡-关联工卡id集合
    * @return int
    */
   int delete4Batch(List<String> idList);
   
   /**
    * @Description 批量新增工卡-关联工卡
    * @CreateTime 2017-8-25 下午5:48:39
    * @CreateBy 刘兵
    * @param workCard2RelatedList 工卡-关联工卡集合
    * @return int
    */
   int insert4Batch(List<WorkCard2Related> workCard2RelatedList);
   
   /**
	 * @Description 根据条件查询工卡-关联工卡列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workContent 工卡-关联工卡
	 * @return List<WorkContent> 工卡-关联工卡集合
	 */
   List<WorkCard2Related> queryAllList(WorkCard2Related workContent);
   
   /**
    * @Description 根据mainid查询工卡-关联工卡列表  
    * @CreateTime 2017-8-25 下午5:53:24
    * @CreateBy 刘兵
    * @param mainid 父表id
    * @return List<WorkCard2Related> 工卡-关联工卡集合
    */
   List<WorkCard2Related> queryByMainid(String mainid);

}