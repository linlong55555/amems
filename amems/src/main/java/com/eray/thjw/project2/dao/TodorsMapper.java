package com.eray.thjw.project2.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.Todors;

public interface TodorsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Todors record);

    int insertSelective(Todors record);

    Todors selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Todors record);

    int updateByPrimaryKey(Todors record);
    /**
	 * 
	 * @Description 根据待办事宜id 查询待办事宜明细
	 * @CreateTime 2017-8-16 上午10:55:49
	 * @CreateBy 孙霁
	 * @return
	 * @throws BusinessException
	 */
    List<Todors> selectByMainid(String mainid);
    
    /**
     * 
     * @Description 通过多个mainid查询待办处理结果
     * @CreateTime 2017年9月6日 上午11:04:25
     * @CreateBy 朱超
     * @param mainid
     * @return
     */
    List<Todors> selectByMainids(List<String> mainids);
	/**
	 * 
	 * @Description 根据业务id删除待办事宜明细
	 * @CreateTime 2017-8-16 下午3:31:48
	 * @CreateBy 孙霁
	 * @param ywids
	 */
    void deleteYwIds(List<String> ywids);
    /**
	 * 
	 * @Description 批量新增数据
	 * @CreateTime 2017年8月16日 下午6:56:30
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	void insertTodors(List<Todors> list);
	/**
	 * 
	 * @Description 根据业务id删除数据
	 * @CreateTime 2017年8月16日 下午6:55:56
	 * @CreateBy 岳彬彬
	 * @param ywid
	 */
	void deleteTodorsByYwid(String ywid);
	/**
	 * 
	 * @Description 根据业务id更新数据
	 * @CreateTime 2017年8月17日 下午5:46:26
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updateByYwid(Todors record);
    /**
	 * 
	 * @Description 根据来源id获取明细数据
	 * @CreateTime 2017-8-18 下午7:12:06
	 * @CreateBy 孙霁
	 * @param lyid
	 * @return
	 */
    List<Todo> selectTodorsBylyid(String lyid);

    /**
	 * 
	 * @Description 根据评估单id查询产出
	 * @CreateTime 2017年8月24日 下午2:02:48
	 * @CreateBy 林龙
	 * @param todors
	 * @return
	 */
	List<GiveInstruction> selectOuputList(Todors todors);

	/**
	 * 
	 * @Description 根据评估单id集合查询产出
	 * @CreateTime 2017年8月24日 下午2:02:48
	 * @CreateBy 林龙
	 * @param todors
	 * @return
	 */
	List<Todors> selectOuputsList(List<String> todoIdList);
	/**
	 * 
	 * @Description 根据业务id更新状态
	 * @CreateTime 2017年9月1日 上午11:48:11
	 * @CreateBy 岳彬彬
	 * @param ywdjzt
	 */
	void updateYwdjztByYwid(@Param("ywdjzt")Integer ywdjzt,@Param("ywid")String ywid);
	/**
	 * 
	 * @Description 根据业务id集合批量更新状态
	 * @CreateTime 2017年9月1日 下午12:02:34
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	void updateZtByYwidList(@Param("ywdjzt")Integer ywdjzt,@Param("list")List<String> list);

	/**
	 * 
	 * @Description  更新待办事宜编号和版本
	 * @CreateTime 2017年9月13日 下午4:50:00
	 * @CreateBy 林龙
	 * @param id
	 * @param bb
	 * @param pgdh
	 */
	void updatetByYwid(String id, BigDecimal bb, String pgdh);

	void updatetsByYwid(String pgdh, BigDecimal bb, String pgdzt, Integer zt,
			String id);

	
}