package com.eray.thjw.training.dao;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.training.po.YearPlan;

public interface YearPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(YearPlan record);

    int insertSelective(YearPlan record);

    YearPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(YearPlan record);

    int updateByPrimaryKey(YearPlan record);
    
    /**
	 * @author liub
	 * @description 根据年度、机构代码检查年度计划是否存在
	 * @param record
	 * @return YearPlan
	 */
    YearPlan checkExistsByNdAndDprt(YearPlan record);
    
    /**
	 * @author liub
	 * @description 根据年度、机构代码查询年度计划、附件数
	 * @param nd、dprtcode
	 * @return YearPlan
	 */
    YearPlan selectByNdAndDprt(@Param("nd")Integer nd,@Param("dprtcode")String dprtcode);
}