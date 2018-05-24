package com.eray.thjw.project2.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.Todors;

/**
 * @Description 待办事宜-处理结果service
 * @CreateTime 2017-8-16 上午10:29:56
 * @CreateBy 孙霁
 */
public interface TodorsService {

	/**
	 * 
	 * @Description 添加 待办事宜-处理结果
	 * @CreateTime 2017-8-16 上午10:35:34
	 * @CreateBy 孙霁
	 * @param todoId
	 * @param technicaId
	 * @throws BusinessException
	 */
	public void insert(String todoId, String technicaId) throws BusinessException;

	/**
	 * 
	 * @Description 根据待办事宜id 查询待办事宜明细
	 * @CreateTime 2017-8-16 上午10:55:49
	 * @CreateBy 孙霁
	 * @return
	 * @throws BusinessException
	 */
	public List<Todors> selectByMainid(String mainid) throws BusinessException;

	/**
	 * 
	 * @Description 根据业务id删除待办事宜明细
	 * @CreateTime 2017-8-16 下午3:31:48
	 * @CreateBy 孙霁
	 * @param ywids
	 */
	public void deleteYwIds(List<String> ywids);

	/**
	 * @Description 新增待办事宜处理结果
	 * @CreateTime 2017年8月17日 下午5:43:18
	 * @CreateBy 岳彬彬
	 * @param fjjx 飞机机型
	 * @param lyidList 评估单id集合
	 * @param dbgzlx 指令类型
	 * @param userId 办理人id
	 * @param ywid 业务id
	 * @param ywbh 业务编号
	 * @param ywbb 业务版本
	 * @param ywzt 业务主题
	 * @param yedjzt 业务单据状态
	 */
	void insertTodorsList(String fjjx, List<String> lyidList, int dbgzlx, String userId, String ywid, String ywbh,
			String ywbb, String ywzt, Integer ywdjzt);

	/**
	 * 
	 * @Description 根据业务id删除待办事宜处理结果
	 * @CreateTime 2017年8月17日 下午5:43:23
	 * @CreateBy 岳彬彬
	 * @param 业务id
	 */
	void deleteTodorsByYwid(String ywid);

	/**
	 * 
	 * @Description 根据业务id更新待办事宜处理结果
	 * @CreateTime 2017年8月17日 下午5:43:33
	 * @CreateBy 岳彬彬
	 * @param ywbh 业务编号
	 * @param ywbb 业务版本
	 * @param ywzt 业务主题
	 * @param ywid 业务id
	 * @param yedjzt 业务单据状态
	 */
	void updateTodorsByYwid(String ywbh, String ywbb, String ywzt, String ywid, Integer ywdjzt);

	/**
	 * 
	 * @Description 根据来源id获取明细数据
	 * @CreateTime 2017-8-18 下午7:12:06
	 * @CreateBy 孙霁
	 * @param lyid
	 * @return
	 */
	public List<Todo> selectTodorsBylyid(String lyid) throws BusinessException;

	/**
	 * 
	 * @Description 根据评估单id查询产出
	 * @CreateTime 2017年8月24日 下午2:02:48
	 * @CreateBy 林龙
	 * @param todors
	 * @return
	 */
	public List<GiveInstruction> selectOuputList(Todors todors);

	/**
	 * 
	 * @Description 根据评估单id集合查询产出
	 * @CreateTime 2017年8月24日 下午2:02:48
	 * @CreateBy 林龙
	 * @param todors
	 * @return
	 */
	public List<Todors> selectOuputsList(List<String> todoIdList);

	/**
	 * 
	 * @Description 根据业务id更新状态
	 * @CreateTime 2017年9月1日 上午11:49:14
	 * @CreateBy 岳彬彬
	 * @param ywdjzt 业务单据状态
	 * @param ywid 业务id
	 */
	void updateYwdjztByYwid(Integer ywdjzt, String ywid);

	/**
	 * 
	 * @Description 根据业务id集合批量更新状态
	 * @CreateTime 2017年9月1日 下午12:02:34
	 * @CreateBy 岳彬彬
	 * @param yedjzt 业务单据状态
	 * @param list 业务id集合
	 */
	void updateZtByYwidList(Integer ywdjzt, List<String> list);

	/**
	 * 
	 * @Description  更新待办事宜编号和版本
	 * @CreateTime 2017年9月13日 下午4:50:00
	 * @CreateBy 林龙
	 * @param id
	 * @param bb
	 * @param pgdh
	 */
	public void updatetByYwid(String id, BigDecimal bb, String pgdh);
	
	

	/**
	 * @Description  新增待办事宜
	 * @CreateBy 刘邓
	 * @param dprtcode  组织机构
	 * @param yxzt      有效状态
	 * @param zt        状态
	 * @param lyid      来源id
	 * @param lybh      来源编号
	 * @param bb        版本
	 * @param fjjx      飞机机型
	 * @param dbgzlx    待办工作类型
	 * @param jd        节点
	 * @param sm        说明
	 * @param blqx      办理期限
	 * @param blrid     办理人id
	 * @param gnbm      功能编码
	 * @param dbywid    待办业务id
	 * @param fkyj      反馈意见
	 * @throws BusinessException
	 */
	public void addToDo(String dprtcode,Integer yxzt,Integer zt,String lyid,String lybh,String bb,String fjjx,Integer dbgzlx,Integer jd,String sm,Date blqx,String blrid,String gnbm,String dbywid,String fkyj) throws BusinessException;
	
	
	/**
	 * @Description  根据待办业务id,节点修改状态
	 * @CreateBy 刘邓
	 * @param dbywid 待办业务id
	 * @param jd     节点
	 * @param zt     状态
	 * @param blrid  办理人id
	 * @throws BusinessException
	 */
	public void updateToDo(String dbywid,Integer jd,Integer zt,String blrid) throws BusinessException;
	

    /**
     * @Description  根据id,节点,状态删除数据
     * @CreateBy 刘邓
     * @param id
     * @param zt
     * @throws BusinessException
     */
    public void deleteToDo(String id,Integer zt,List<Integer> jdlist) throws BusinessException;

    /**
     * 
     * @Description 更新待办
     * @CreateTime 2018年4月23日 下午2:49:02
     * @CreateBy 林龙
     * @param pgdh
     * @param bb
     * @param pgdzt
     * @param zt
     * @param id
     */
	public void updatetsByYwid(String pgdh, BigDecimal bb, String pgdzt,
			Integer zt, String id);
	
	
	 /**
     * 
     * @Description 根据待办业务id修改待办办理人id
     * @CreateTime 2018年4月26日 下午2:49:02
     * @CreateBy 刘邓
     * @param dbywid
     * @param jd
     * @param pgdzt
     * @param zt
     */
	public void updateBlrByDbid(String dbywid,Integer jd,Integer zt,String blrid); 
	
}