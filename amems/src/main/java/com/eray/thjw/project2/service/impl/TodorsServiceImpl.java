package com.eray.thjw.project2.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.dao.TodoMapper;
import com.eray.thjw.project2.dao.TodorsMapper;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.Todors;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.TaskService;


/**
 * @Description
 * @CreateTime 2017-8-16 上午10:32:24
 * @CreateBy 孙霁
 */
@Service
public class TodorsServiceImpl implements TodorsService {

	@Resource
	private TodorsMapper todorsMapper;
	@Resource
	private TodoMapper todoMapper;
	@Resource
	private TodoService todoService;
	@Resource
	private TaskService taskService;

	/**
	 * 
	 * @Description 添加 待办事宜-处理结果
	 * @CreateTime 2017-8-16 上午10:35:34
	 * @CreateBy 孙霁
	 * @param todoId
	 * @param technicaId
	 * @throws BusinessException
	 */
	@Override
	public void insert(String todoId, String technicaId) throws BusinessException {

		Todors todors = new Todors();
		todors.setId(UUID.randomUUID().toString());
		todors.setMainid(todoId);
		todors.setYwid(technicaId);
		todorsMapper.insertSelective(todors);
	}

	/**
	 * 
	 * @Description 根据待办事宜id 查询待办事宜明细
	 * @CreateTime 2017-8-16 上午10:55:49
	 * @CreateBy 孙霁
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Todors> selectByMainid(String mainid) throws BusinessException {
		return todorsMapper.selectByMainid(mainid);
	}

	/**
	 * 
	 * @Description 根据业务id删除待办事宜明细
	 * @CreateTime 2017-8-16 下午3:31:48
	 * @CreateBy 孙霁
	 * @param ywids
	 */
	@Override
	public void deleteYwIds(List<String> ywids) {
		todorsMapper.deleteYwIds(ywids);
	}

	/**
	 * @Description 新增待办事宜处理结果
	 * @CreateTime 2017年8月17日 下午5:43:18
	 * @CreateBy 岳彬彬
	 * @param fjjx
	 *            飞机机型
	 * @param lyidList
	 *            评估单id集合
	 * @param dbgzlx
	 *            指令类型
	 * @param userId
	 *            办理人id
	 * @param ywid
	 *            业务id
	 * @param ywbh
	 *            业务编号
	 * @param ywbb
	 *            业务版本
	 * @param ywzt
	 *            业务主题
	 * @param yedjzt
	 *            业务单据状态
	 */
	@Override
	public void insertTodorsList(String fjjx, List<String> lyidList, int dbgzlx, String userId, String ywid,
			String ywbh, String ywbb, String ywzt, Integer ywdjzt) {
		Todo record = new Todo();
		record.setFjjx(fjjx);
		record.setDbgzlx(dbgzlx);
		record.setBlrid(userId);
		record.getParamsMap().put("list", lyidList);
		if (lyidList != null && !lyidList.isEmpty()) {
			List<Todo> tList = todoMapper.getTodoByParams(record);
			if (tList.size() > 0) {
				List<Todors> todorsList = new ArrayList<Todors>();
				Todors todors;
				for (Todo todo : tList) {
					
					
					todors = new Todors();
					todors.setId(UUID.randomUUID().toString());
					todors.setMainid(todo.getId());
					todors.setYwbb(ywbb);
					todors.setYwbh(ywbh);
					todors.setYwid(ywid);
					todors.setYwzt(ywzt);
					todors.setYwdjzt(ywdjzt);
					todorsList.add(todors);
				}
				if (todorsList.size() > 0) {
					todorsMapper.insertTodors(todorsList);
				}
			}
		}
	}

	/**
	 * 
	 * @Description 根据业务id删除待办事宜处理结果
	 * @CreateTime 2017年8月17日 上午9:48:24
	 * @CreateBy 岳彬彬
	 * @param 业务id
	 */
	@Override
	public void deleteTodorsByYwid(String ywid) {
		todorsMapper.deleteTodorsByYwid(ywid);
	}

	/**
	 * 
	 * @Description 根据业务id更新待办事宜处理结果
	 * @CreateTime 2017年8月17日 上午9:49:34
	 * @CreateBy 岳彬彬
	 * @param ywbh 业务编号
	 * @param ywbb 业务版本
	 * @param ywzt 业务主题
	 * @param ywid 业务id
	 * @param ywdjid 业务单据状态
	 */
	@Override
	public void updateTodorsByYwid(String ywbh, String ywbb, String ywzt, String ywid, Integer ywdjzt) {
		Todors todors = new Todors();
		todors.setYwbb(ywbb);
		todors.setYwbh(ywbh);
		todors.setYwzt(ywzt);
		todors.setYwid(ywid);
		todors.setYwdjzt(ywdjzt);
		todorsMapper.updateByYwid(todors);
	}

	/**
	 * 
	 * @Description 根据来源id获取明细数据
	 * @CreateTime 2017-8-18 下午7:12:06
	 * @CreateBy 孙霁
	 * @param lyid
	 * @return
	 */
	@Override
	public List<Todo> selectTodorsBylyid(String lyid) throws BusinessException {
		return todorsMapper.selectTodorsBylyid(lyid);
	}

	/**
	 * 
	 * @Description 根据评估单id查询产出
	 * @CreateTime 2017年8月24日 下午2:02:48
	 * @CreateBy 林龙
	 * @param todors
	 * @return
	 */
	@Override
	public List<GiveInstruction> selectOuputList(Todors todors) {
		return todorsMapper.selectOuputList(todors);
	}

	@Override
	public List<Todors> selectOuputsList(List<String> todoIdList) {
		return todorsMapper.selectOuputsList(todoIdList);
	}
	
	/**
	 * 
	 * @Description 根据业务id更新状态
	 * @CreateTime 2017年9月1日 上午11:49:14
	 * @CreateBy 岳彬彬
	 * @param ywdjzt 业务单据状态
	 * @param ywid 业务id
	 */
	@Override
	public void updateYwdjztByYwid(Integer ywdjzt,String ywid) {
		todorsMapper.updateYwdjztByYwid(ywdjzt,ywid);

	}
	/**
	 * 
	 * @Description 根据业务id集合批量更新状态
	 * @CreateTime 2017年9月1日 下午12:02:34
	 * @CreateBy 岳彬彬
	 * @param yedjzt 业务单据状态
	 * @param list 业务id集合
	 */
	@Override
	public void updateZtByYwidList(Integer ywdjzt,List<String> list) {
		
		todorsMapper.updateZtByYwidList(ywdjzt,list);
	}

	/**
	 * 
	 * @Description  更新待办事宜编号和版本
	 * @CreateTime 2017年9月13日 下午4:50:00
	 * @CreateBy 林龙
	 * @param id
	 * @param bb
	 * @param pgdh
	 */
	@Override
	public void updatetByYwid(String id, BigDecimal bb, String pgdh) {
		
		todorsMapper.updatetByYwid(id,bb,pgdh);
	}
	
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
	@Override
	public void addToDo(String dprtcode, Integer yxzt, Integer zt, String lyid,
			String lybh, String bb, String fjjx, Integer dbgzlx, Integer jd,
			String sm, Date blqx, String blrid, String gnbm, String dbywid,String fkyj)
			throws BusinessException {
	     Todo todo=new Todo();
		 todo.setId(UUID.randomUUID().toString());
		 todo.setDprtcode(dprtcode);
		 todo.setYxzt(yxzt);
		 todo.setZt(zt);
		 todo.setLyid(lyid);
		 todo.setLybh(lybh);
		 todo.setBb(bb);
		 todo.setFjjx(fjjx);
		 todo.setDbgzlx(dbgzlx);
		 todo.setJd(jd);
		 todo.setSm(sm);
		 todo.setBlqx(blqx);
		 todo.setBlrid(blrid);
		 todo.setGnbm(gnbm);
		 todo.setDbywid(dbywid);
		 todo.setFkyj(fkyj);
		 todo.setFksj(new Date());
		 todoMapper.insertSelective(todo);
		 // 异步发送邮件
		 taskService.doEmailTask(todo);
	}
	
	/**
	 * @Description  根据待办业务id,节点修改状态
	 * @CreateBy 刘邓
	 * @param dbywid 待办业务id
	 * @param jd     节点
	 * @param zt     状态
	 * @param blrid  办理人id
	 * @throws BusinessException
	 */
	@Override
	public void updateToDo(String dbywid, Integer jd, Integer zt, String blrid)
			throws BusinessException {
		   Todo todo=new Todo();
		   todo.setDbywid(dbywid);
		   todo.setJd(jd);
		   todo.setZt(zt);
		   todo.setBlrid(blrid);
		   todoMapper.updateByDbId(todo);
	}
	/**
     * @Description  根据id,节点,状态删除数据
     * @CreateBy 刘邓
     * @param id
     * @param zt
     * @param jdlist
     * @throws BusinessException
     */
	@Override
	public void deleteToDo(String id, Integer zt,List<Integer> jdlist) throws BusinessException {
		 Todo todo=new Todo();
		 todo.getParamsMap().put("jdlist", jdlist);
		 todo.setZt(zt);
		 todo.setDbywid(id);
		todoMapper.delById(todo);
		
	}

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
	@Override
	public void updatetsByYwid(String pgdh, BigDecimal bb, String pgdzt,
			Integer zt, String id) {
		todorsMapper.updatetsByYwid(pgdh,bb,pgdzt,zt,id);
		
	}

	@Override
	public void updateBlrByDbid(String dbywid, Integer jd, Integer zt,String blrid) {
		   Todo todo=new Todo();
		   todo.setDbywid(dbywid);
		   todo.setBlrid(blrid);
		   //todo.setJd(jd);
		   todo.setZt(zt);
		   todoMapper.updateBlrByYwid(todo);
		
	}
	

	
}
