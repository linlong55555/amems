package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.Todo;

public interface TodoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Todo record);

    int insertSelective(Todo record);

    Todo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Todo record);

    int updateByPrimaryKey(Todo record);

    /**
     * 
     * @Description 根据飞机机型,来源ID集合, 工作类型、userId批量获取待办事项数据
     * @CreateTime 2017年8月16日 下午4:26:45
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<Todo> getTodoByParams(Todo record);
    /**
     * 
     * @Description 根据来源id获取数据
     * @CreateTime 2017-8-15 下午5:05:01
     * @CreateBy 孙霁
     * @param id
     * @return
     */
    Todo selectByLyid(String lyid);

    /**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月24日 上午11:47:15
	 * @CreateBy 林龙
	 * @param todo 待办事宜
	 * @return
	 */
	List<Todo> selectOrderList(Todo todo);

	/**
	 * 
	 * @Description 根据来源id查询todolist
	 * @CreateTime 2017年8月28日 下午7:33:14
	 * @CreateBy 李高升
	 * @param todo
	 * @return
	 */
	List<Todo> getToDOList(Todo todo);
    
	/**
	 * 
	 * @Description 根据来源id更新ZT值
	 * @CreateTime 2017年8月29日 下午7:33:52
	 * @CreateBy 李高升
	 * @param todo
	 */
	void updateZTByPrimaryKeySelective(Todo todo);
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2017-8-24 下午6:57:56
	 * @CreateBy 孙霁
	 * @param 
	 * @param todo
	 */
	void deleteByLyidAndFjjx(Todo todo);
	/**
	 * 
	 * @Description 根据来源id 和 飞机机型修改数据
	 * @CreateTime 2017-8-24 下午6:57:56
	 * @CreateBy 孙霁
	 * @param 
	 * @param todo
	 */
	void updateByLyidAndFjjx(Todo todo);
	void updatetBypgr(String id, String pgrid,String jx);

	Todo selecttodo(Todo todo);

	List<Todo> selecttodolist(Todo todoInstruc);
	
	/**
	 * @Description 根据待办业务id修改状态
	 * @CreateBy 刘邓
	 * @param 
	 * @param todo
	 */
	void updateByDbId(Todo todo);

	void deletedbyw(Todo tododbyw);

	void updateByDbyw(Todo tododbyw);
	
	/**
	 * @Description 根据待办业务id删除待办
	 * @CreateBy 刘邓
	 * @param 
	 * @param todo
	 */
	void delById(Todo todo);
	
	/**
	 * @Description 根据待办业务修改办理人
	 * @CreateBy 刘邓
	 * @param 
	 * @param todo
	 */
	void updateBlrByYwid(Todo todo);
}