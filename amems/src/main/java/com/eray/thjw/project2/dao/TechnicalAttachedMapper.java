package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.TechnicalAttached;

public interface TechnicalAttachedMapper {
    int deleteByPrimaryKey(String id);

    int insert(TechnicalAttached record);

    int insertSelective(TechnicalAttached record);

    TechnicalAttached selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TechnicalAttached record);

    int updateByPrimaryKey(TechnicalAttached record);

    /**
	 * @Description  根据mainid 技术评估单id查询 技术评估单附表数量
	 * @CreateTime 2017年8月22日 上午9:57:30
	 * @CreateBy 林龙
	 * @param mainid 技术评估单id
	 * @return 数量
	 * @throws BusinessException
	 */
	int selectByMainidCount(String mainid);

	/**
	 * 
	 * @Description 根据mainid 技术评估单id查询 技术评估单附表
	 * @CreateTime 2017年8月24日 下午3:29:01
	 * @CreateBy 林龙
	 * @param mainid
	 * @return
	 */
	TechnicalAttached selectByMainid(String mainid);
	
	
}