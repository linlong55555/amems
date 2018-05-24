package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.Todo;


/** 
 * @Description 待办事宜service
 * @CreateTime 2017-8-16 上午10:28:41
 * @CreateBy 孙霁	
 */
public interface TodoService {

	/**
	 * 
	 * @Description 根据评估单id批量新增待办事宜
	 * @CreateTime 2017年8月24日 上午9:44:06
	 * @CreateBy 林龙
	 * @param id 评估单id
	 * @throws BusinessException
	 */
	public void saveTodoList(String id)throws BusinessException;

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-8-24 下午6:57:56
	 * @CreateBy 孙霁
	 * @param blrid
	 * @param airworthiness
	 */
	public String insert(String blrid, Airworthiness airworthiness, String fjjx,String technicaId);
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2017-8-24 下午6:57:56
	 * @CreateBy 孙霁
	 * @param 
	 * @param airworthiness
	 */
	public void deleteByLyidAndFjjx(String lyid, String fjjx);
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-8-24 下午7:05:00
	 * @CreateBy 孙霁
	 * @param airworthiness
	 */
	public String updateByLyidAndFjjx(Airworthiness airworthiness, String fjjx,String blrid, boolean b);
	/**
	 * 
	 * @Description 批量新增待办事宜 
	 * @CreateTime 2017年8月24日 上午11:06:11
	 * @CreateBy 林龙
	 * @param auditIdList
	 * @throws BusinessException
	 */
	public void saveBatchTodoList(List<String> approvelIdList)throws BusinessException;

	/**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月24日 上午11:47:15
	 * @CreateBy 林龙
	 * @param todo 待办事宜
	 * @return
	 */
	public List<Todo> selectOrderList(Todo todo)throws BusinessException;

	/**
	 * 
	 * @Description 得到未办事项的列表，分页
	 * @CreateTime 2017年8月28日 下午5:04:49
	 * @CreateBy 李高升
	 * @param todo
	 * @return
	 */
	public Map<String, Object> getToDOList(Todo todo);
	
	/**
	 * 
	 * @Description 执行反馈操作
	 * @CreateTime 2017年8月29日 下午6:56:04
	 * @CreateBy 李高升
	 * @param todo
	 */
	public void doFeedBack(Todo todo);

		/**
	 * 
	 * @Description 修改評估人
	 * @CreateTime 2017年11月20日 上午10:25:16
	 * @CreateBy 林龙
	 * @param id
	 * @param pgrid
	 */
	public void updatetBypgr(String id, String pgrid,String jx);

	/**
	 * 
	 * @Description 查询待办
	 * @CreateTime 2018年3月22日 下午5:37:48
	 * @CreateBy 林龙
	 * @param todo
	 * @return
	 */
	public Todo selecttodo(Todo todo);

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018年3月22日 下午5:41:12
	 * @CreateBy 林龙
	 * @param todoOld
	 */
	public void updateByPrimaryKeySelective(Todo todoOld);

	/**
	 * 
	 * @Description 查询多条todo
	 * @CreateTime 2018年3月23日 上午11:02:06
	 * @CreateBy 林龙
	 * @param todoInstruc
	 * @return
	 */
	public List<Todo> selecttodolist(Todo todoInstruc);

	/**
	 * 
	 * @Description 评估单生成待办
	 * @CreateTime 2018年4月12日 下午1:56:32
	 * @CreateBy 林龙
	 * @param technical 技术评估
	 * @param strSm 说明
	 * @param dbywid 业务code
	 * @param jd 节点
	 * @param type 
	 */
	public void insertSelectiveTechnical(Object object, String strSm,String dbywid, Integer jd,String blrid, Integer type);

	public void saveBatchTodoAuditList(List<String> auditIdList, Integer type)throws BusinessException ;

	/**
	 * 
	 * @Description 根据待办业务id和节点条件删除
	 * @CreateTime 2018年4月12日 下午2:56:40
	 * @CreateBy 林龙
	 * @param tododbyw
	 * @throws BusinessException
	 */
	public void deletedbyw(Todo tododbyw)throws BusinessException ;

	/**
	 * 
	 * @Description 根据业务id和节点条件修改
	 * @CreateTime 2018年4月12日 下午3:07:20
	 * @CreateBy 林龙
	 * @param tododbyw
	 * @throws BusinessException
	 */
	public void updateByDbyw(Todo tododbyw)throws BusinessException;


	/**
	 * @Description 根据id获取数据
	 * @CreateTime 2018-4-23 下午4:49:00
	 * @CreateBy 刘兵
	 * @param id
	 * @return
	 */
	public Todo selectByPrimaryKey(String id);
	
}
