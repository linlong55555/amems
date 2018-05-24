package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project.dao.MelChangeSheetMapper;
import com.eray.thjw.project.po.MelChangeSheet;
import com.eray.thjw.project2.dao.BulletinMapper;
import com.eray.thjw.project2.dao.EngineeringOrderMapper;
import com.eray.thjw.project2.dao.TechnicalInstructionMapper;
import com.eray.thjw.project2.dao.TodoMapper;
import com.eray.thjw.project2.dao.TodorsMapper;
import com.eray.thjw.project2.dao.WorkCardMapper;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.Bulletin;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.ProductionOrder;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.TechnicalInstruction;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.service.GiveInstructionService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.TechnicalService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.quality.po.AnnualPlan;
import com.eray.thjw.quality.po.AuditDiscoveryDetail;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.TaskService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.common.EffectiveEnum;
import enu.produce.NodeEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;

/** 
 * @Description 
 * @CreateTime 2017-8-16 上午10:31:34
 * @CreateBy 孙霁	
 * @UpdateBy 李高升
 */
@Service
public class TodoServiceImpl implements TodoService{
	
	@Resource
	private TodoMapper todoMapper;
	@Resource
	private GiveInstructionService giveInstructionService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private TechnicalService technicalService;
	@Resource
	private TodorsService todorsService;
	@Resource
	private CommonService commonService;
	@Resource
	private TodorsMapper todorsMapper;
	@Resource
	private BulletinMapper bulletinMapper;
	@Resource
	private TechnicalInstructionMapper technicalInstructionMapper;
	@Resource
	private EngineeringOrderMapper engineeringOrderMapper;
	@Resource
	private MelChangeSheetMapper melChangeSheetMapper;
	@Resource
	private WorkCardMapper workCardMapper;
	@Resource
	private InstructionsourceService instructionsourceService;
	@Resource
	private TaskService taskService;
	
	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-8-24 下午6:57:56
	 * @CreateBy 孙霁
	 * @param blrid
	 * @param airworthiness
	 */
	@Override
	public String insert(String blrid, Airworthiness airworthiness, String fjjx ,String technicaId) {
		 Todo todo = new Todo();
		 Date currentDate = commonService.getSysdate();//当前时间
		 String todoid = UUID.randomUUID().toString();
		 todo.setId(todoid);
		 todo.setBlrid(blrid);
		 todo.setLyid(airworthiness.getId());
		 todo.setLybh(airworthiness.getJswjbh());
		 todo.setDbgzlx(ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId());
		 todo.setBlqx(airworthiness.getPgqx());
		 todo.setFjjx(fjjx);
		 todo.setBb(airworthiness.getBb());
		 todo.setJd(NodeEnum.NODE1.getId());
		 todo.setZt(TodoStatusEnum.DCL.getId());
		 todo.setDbywid(technicaId);
		 todo.setDprtcode(airworthiness.getDprtcode());
		 todo.setGnbm("project2:assessment:main:02");
		 todo.setFksj(currentDate);
		 StringBuffer strSm=new StringBuffer();
		 //拼接待办事宜描述
		 strSm.append("针对").append(airworthiness.getJswjbh()).append("_R").append(airworthiness.getBb())
			  .append("_").append(airworthiness.getJswjzt()).append("适航性资料,需要对")
			  .append(fjjx).append("机型进行技术评估.");
		 todo.setSm(strSm.toString());
		 //写入待办事宜表
		 todoMapper.insertSelective(todo);
		// 异步发送邮件
		taskService.doEmailTask(todo);
		 return todoid;
		
	}
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017-8-24 下午7:05:00
	 * @CreateBy 孙霁
	 * @param airworthiness
	 */
	@Override
	public String updateByLyidAndFjjx(Airworthiness airworthiness, String fjjx,String blrid, boolean b) {
		 Todo todo = new Todo();
		 todo.setLyid(airworthiness.getId());
		 todo.setLybh(airworthiness.getJswjbh());
		 todo.setDbgzlx(ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId());
		 todo.setBlqx(airworthiness.getPgqx());
		 todo.setBb(airworthiness.getBb());
		 todo.setZt(TodoStatusEnum.DCL.getId());
		 todo.setFjjx(fjjx);
		 if(b){
			 todo.setBlrid(blrid);
		 }
		 StringBuffer strSm = new StringBuffer();
		 //拼接待办事宜描述
		 strSm.append("针对").append(airworthiness.getJswjbh()).append("_R").append(airworthiness.getBb())
			  .append("_").append(airworthiness.getJswjzt()).append("适航性资料,需要对")
			  .append(fjjx).append("机型进行技术评估.");
		 todo.setSm(strSm.toString());
		 //写入待办事宜表
		 todoMapper.updateByLyidAndFjjx(todo);
		
		 return todo.getId();
	}
	
	/**
	 * 
	 * @Description 根据评估单id批量新增待办事宜
	 * @CreateTime 2017年8月24日 上午9:44:06
	 * @CreateBy 林龙
	 * @param id 评估单id
	 * @throws BusinessException
	 */
	@Override
	public void saveTodoList(String id) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		//根据id查询技术文件评估单信息
		Technical technical = technicalService.selectByPrimaryKey(id);
		if(technical != null){
			GiveInstruction giveInstructionOld = new GiveInstruction();
			giveInstructionOld.setMainid(id);
			//根据评估单id获取下达指令集合数据
			List<GiveInstruction> giveInstructionList = giveInstructionService.selectOrderList(giveInstructionOld);
			
			for (GiveInstruction giveInstruction : giveInstructionList) {
				if(giveInstruction.getZlxl() != 99){//待办事宜不能为其他
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("针对");
					strSm.append(technical.getPgdh());
					strSm.append("R_");
					strSm.append(technical.getBb());
					strSm.append("_");
					strSm.append(technical.getPgdzt());
					strSm.append("技术评估单需要对");
					strSm.append(technical.getJx());
					strSm.append("机型完成");
					strSm.append(SendOrderEnum.getName(giveInstruction.getZlxl()));
					strSm.append("。");
					
				    String uuid = UUID.randomUUID().toString();		  //uuid
				    Todo todo=new Todo();
					todo.setId(uuid);	
					todo.setLyid(technical.getId()); 	//来源id为评估单id
					todo.setLybh(technical.getPgdh());	//来源版本为评估单编号
					todo.setBb(technical.getBb().toString());		//版本为技术评估单版本
					todo.setFjjx(technical.getJx());	//飞机机型为评估单机型
					todo.setDbgzlx(giveInstruction.getZlxl());//待办工作类型为下达指令类型
					todo.setSm(strSm.toString());
					
					todo.setBlqx(technical.getYjbfzlsj()); //办理期限为预计颁发指令期限yjbfzlsj
					todo.setBlrid(giveInstruction.getZdryid());//办理人id为下达指令人
					todo.setYxzt(EffectiveEnum.YES.getId());//有效状态为有效
					todo.setZt(TodoStatusEnum.DCL.getId());//状态为待处理
					todo.setJd(NodeEnum.NODE1.getId());
//					todo.setDbywid(technical.getId());
					todo.setFksj(currentDate);
					todo.setDprtcode(technical.getDprtcode());
					String gnbm="";
					if(giveInstruction.getZlxl()==TodoEnum.EO.getId()){
						gnbm="project2:eo:main:01";
					}else if(giveInstruction.getZlxl()==TodoEnum.TECHNICAL_BULLETIN.getId()){
						gnbm="project2:bulletin:01";
					}else if(giveInstruction.getZlxl()==TodoEnum.TECHNICAL_ORDER.getId()){
						gnbm="project2:instruction:main:01";
					}else if(giveInstruction.getZlxl()==TodoEnum.MP.getId()){
						gnbm="project2:maintenanceproject:main";
					}else if(giveInstruction.getZlxl()==TodoEnum.NRC.getId()){
						gnbm="produce:nrc135:main:01";
					}else if(giveInstruction.getZlxl()==TodoEnum.MEL.getId()){
						gnbm="project:mel:main:01";
					}else if(giveInstruction.getZlxl()==TodoEnum.WORK_CARD.getId()){
						gnbm="project2:workcard:main:01";
					}
					todo.setGnbm(gnbm);
					
					//新增待办事宜
					todoMapper.insertSelective(todo);
					// 异步发送邮件
					taskService.doEmailTask(todo);
					//日志
				}
				
			}
		}
	}

	/**
	 * 
	 * @Description 批量新增待办事宜 
	 * @CreateTime 2017年8月24日 上午11:06:11
	 * @CreateBy 林龙
	 * @param auditIdList
	 * @throws BusinessException
	 */
	@Override
	public void saveBatchTodoList(List<String> approvelIdList) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		User user=ThreadVarUtil.getUser();					//当前登陆人
		for (String id : approvelIdList) {
			//根据id查询技术文件评估单信息
			Technical technical = technicalService.selectByPrimaryKey(id);
			if(technical!=null){
				
				//关闭待办
				Todo tododbyw=new Todo();
				tododbyw.setDbywid(technical.getId());
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(3);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				tododbyw.setZt(TodoStatusEnum.DCL.getId());
				tododbyw.setBlrid(user.getId());
				tododbyw.setFksj(currentDate);
				updateByDbyw(tododbyw);
				
				//根据评估单id获取下达指令集合数据
				GiveInstruction giveInstructionOld = new GiveInstruction();
				giveInstructionOld.setMainid(id);
				List<GiveInstruction> giveInstructionList = giveInstructionService.selectOrderList(giveInstructionOld);
				
				for (GiveInstruction giveInstruction : giveInstructionList) {
					if(giveInstruction.getZlxl() != 99){
						//拼接说明
						StringBuffer strSm = new StringBuffer();
						strSm.append("针对");
						strSm.append(technical.getPgdh());
						strSm.append("_R");
						strSm.append(technical.getBb());
						strSm.append("_");
						strSm.append(technical.getPgdzt());
						strSm.append("技术评估单需要对");
						strSm.append(technical.getJx());
						strSm.append("机型完成");
						strSm.append(SendOrderEnum.getName(giveInstruction.getZlxl()));
						strSm.append("。");
						
					    String uuid = UUID.randomUUID().toString();		  //uuid
					    Todo todo=new Todo();
						todo.setId(uuid);	
						todo.setLyid(technical.getId()); 	//来源id为评估单id
						todo.setLybh(technical.getPgdh());	//来源版本为评估单编号
						todo.setBb(technical.getBb().toString());		//版本为技术评估单版本
						todo.setFjjx(technical.getJx());	//飞机机型为评估单机型
						todo.setDbgzlx(giveInstruction.getZlxl());//待办工作类型为下达指令类型
						todo.setSm(strSm.toString());
						todo.setBlqx(technical.getPgqx());
						todo.setBlrid(giveInstruction.getZdryid());//办理人id为下达指令人
						todo.setYxzt(EffectiveEnum.YES.getId());//有效状态为有效
						todo.setZt(TodoStatusEnum.DCL.getId());//状态为待处理
						todo.setJd(NodeEnum.NODE1.getId());
						todo.setFksj(currentDate);
//						todo.setDbywid(technical.getId());
						todo.setDprtcode(technical.getDprtcode());
						//新增待办事宜
						todoMapper.insertSelective(todo);
						// 异步发送邮件
						taskService.doEmailTask(todo);
					}
				}
			}
		}
		
	}

	
	/**
	 * 
	 * @Description 批量新增待办事宜 -审核
	 * @CreateTime 2017年8月24日 上午11:06:11
	 * @CreateBy 林龙
	 * @param auditIdList
	 * @throws BusinessException
	 */
	@Override
	public void saveBatchTodoAuditList(List<String> auditIdList, Integer type) throws BusinessException {
		User user = ThreadVarUtil.getUser();					//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		for (String id : auditIdList) {
			
			Todo tododbyw=new Todo();
			if(type == TodoEnum.TECHNICAL_ASSESSMENT.getId()){//技术评估单
				//根据id查询技术文件评估单信息
				Technical technical = technicalService.selectByPrimaryKey(id);
				
					if(technical!=null){
						//新增待办事宜
						tododbyw.setDbywid(technical.getId());
						
						//拼接说明
						StringBuffer strSm = new StringBuffer();
						strSm.append("请审批");
						strSm.append(technical.getJx());
						strSm.append("机型的");
						strSm.append(technical.getPgdh());
						strSm.append(" R");
						strSm.append(technical.getBb());
						strSm.append("技术评估单。");
						//新增待办
						insertSelectiveTechnical(technical,strSm.toString(),"project2:assessment:approval:main:01",NodeEnum.NODE3.getId(),null,TodoEnum.TECHNICAL_ASSESSMENT.getId());
					}
			}
			if(type == TodoEnum.TECHNICAL_BULLETIN.getId()){//维护提示
				//根据id查询技术文件评估单信息
				Bulletin bulletin = bulletinMapper.selectByPrimaryKey(id);
				
				if(bulletin!=null){
					//新增待办事宜
					tododbyw.setDbywid(bulletin.getId());
					
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("请审批");
					strSm.append(bulletin.getJx());
					strSm.append("机型的");
					strSm.append(bulletin.getJstgh());
					strSm.append(" R");
					strSm.append(bulletin.getBb());
					strSm.append("维护提示。");
					//新增待办
					insertSelectiveTechnical(bulletin,strSm.toString(),"project2:bulletin:04",NodeEnum.NODE3.getId(),null,TodoEnum.TECHNICAL_BULLETIN.getId());
				}
			}
			if(type == TodoEnum.TECHNICAL_ORDER.getId()){//技术指令
				TechnicalInstruction technicalInstruction = technicalInstructionMapper.selectByPrimaryKey(id);
				
				if(technicalInstruction!=null){
					//新增待办事宜
					tododbyw.setDbywid(technicalInstruction.getId());
					
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("请审批");
					strSm.append(technicalInstruction.getJx());
					strSm.append("机型的");
					strSm.append(technicalInstruction.getJszlh());
					strSm.append(" R");
					strSm.append(technicalInstruction.getBb());
					strSm.append("技术指令。");
					//新增待办
					insertSelectiveTechnical(technicalInstruction,strSm.toString(),"project2:instruction:main:04",NodeEnum.NODE3.getId(),null,TodoEnum.TECHNICAL_ORDER.getId());
				}
				
			}
			if(type == TodoEnum.MP.getId()){//维修方案
		
			}
			if(type == TodoEnum.NRC.getId()){//下达NRC
			
			}
			if(type == TodoEnum.EO.getId()){//EO
		
			}
			if(type == TodoEnum.MEL.getId()){//MEL
				//根据id查询技术文件评估单信息
				MelChangeSheet melChangeSheet = melChangeSheetMapper.selectByPrimaryKey(id);
				
				if(melChangeSheet!=null){
					//新增待办事宜
					tododbyw.setDbywid(melChangeSheet.getId());
					
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("请审批");
					strSm.append(melChangeSheet.getJx());
					strSm.append("机型的");
					strSm.append(melChangeSheet.getGgdbh());
					strSm.append("R");
					strSm.append(melChangeSheet.getXghBb());
					strSm.append("MEL变更。");
					//新增待办
					insertSelectiveTechnical(melChangeSheet,strSm.toString(),"project:mel:main:06",NodeEnum.NODE3.getId(),null,TodoEnum.MEL.getId());
				}
			}
			if(type == TodoEnum.WORK_CARD.getId()){//工卡
				//根据id查询技术文件评估单信息
				WorkCard workCard = workCardMapper.selectByPrimaryKey(id);
				if(workCard!=null){
					//新增待办事宜
					tododbyw.setDbywid(workCard.getId());
					
					//拼接说明
					StringBuffer strSm = new StringBuffer();
					strSm.append("请审批");
					strSm.append(workCard.getJx());
					strSm.append("机型的");
					strSm.append(workCard.getGkh());
					strSm.append(" R");
					strSm.append(workCard.getBb());
					strSm.append("工卡。");
					//新增待办
					insertSelectiveTechnical(workCard,strSm.toString(),"project2:workcard:main:04",NodeEnum.NODE3.getId(),null,TodoEnum.WORK_CARD.getId());
				}
			}
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			//关闭待办
			updateByDbyw(tododbyw);
		}
	}
	
	/**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月24日 上午11:47:15
	 * @CreateBy 林龙
	 * @param todo 待办事宜
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public List<Todo> selectOrderList(Todo todo) throws BusinessException {
		// 产出
		List<Todo> todoList=todoMapper.selectOrderList(todo);
		getOutputs(todoList);
		
		
		return todoList;
	}
	
	/**
	 * 
	 * @Description 产出
	 * @CreateTime 2017年9月1日 上午10:18:36
	 * @CreateBy 林龙
	 * @param todoList
	 * @throws BusinessException 
	 */
	private void getOutputs(List<Todo> todoList) throws BusinessException {
		for (Todo todo : todoList) {
			Instructionsource instructionsourceOld=new Instructionsource();
			instructionsourceOld.setPgdid(todo.getLyid());
			// 获取产出
			List<Instructionsource> isList = instructionsourceService.selectOrderList(instructionsourceOld);
			
			if (isList.size() > 0) {
				List<Instructionsource> list = null;
				for (Todo todo1 : todoList) {
					list = new ArrayList<Instructionsource>();
					for (Instructionsource instructionsource : isList) {
						if (todo1.getDbgzlx()==instructionsource.getZlxl() && todo1.getLyid().equals(instructionsource.getPgdid())) {
							list.add(instructionsource);
						}
					}
					if (list.size() > 0) {
						todo1.setInstructionsourceList(list);;
					}
				}
			}
		}
//		if(todoList.size()>0){
//			
//		}
	
	}
	
	/**
	 * 
	 * @Description 得到未办事项的列表，分页
	 * @CreateTime 2017年8月28日 下午5:04:49
	 * @CreateBy 李高升
	 * @param todo
	 * @return
	 */
	@Override
	public Map<String, Object> getToDOList(Todo todo) {
		PageHelper.startPage(todo.getPagination());
		List<Todo> list = todoMapper.getToDOList(todo);
		list = translateToDOList(list);
		return PageUtil.pack4PageHelper(list, todo.getPagination());
	}
	
	
	/**
	 * 
	 * @Description 将待办工作类型和状态值翻译成中文
	 * @CreateTime 2017年8月29日 上午9:57:52
	 * @CreateBy 李高升
	 * @param list
	 * @return
	 */
	private List<Todo> translateToDOList(List<Todo> list){
		
		for (Todo todo : list) {
			todo.setDbgzlxName(TodoEnum.getName(todo.getDbgzlx()));
			todo.setZtName(TodoStatusEnum.getName(todo.getZt()));
		}
		
		return list;
		
	}
	
	/**
	 * 
	 * @Description 执行反馈操作,更改zt
	 * @CreateTime 2017年8月29日 下午6:56:04
	 * @CreateBy 李高升
	 * @param todo
	 */
	@Override
	public void doFeedBack(Todo todo) {
		User user = ThreadVarUtil.getUser();
		todo.setBlrid(user.getId());
		todo.setZt(TodoStatusEnum.YCL.getId());
		todoMapper.updateZTByPrimaryKeySelective(todo);
		
	}
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2017-8-24 下午6:57:56
	 * @CreateBy 孙霁
	 * @param 
	 * @param 来源id , 飞机机型
	 */
	@Override
	public void deleteByLyidAndFjjx(String lyid, String fjjx) {
		Todo todo = new Todo();
		todo.setLyid(lyid);
		todo.setFjjx(fjjx);
		todoMapper.deleteByLyidAndFjjx(todo);
	}
	
	/**
	 * 
	 * @Description 修改評估人
	 * @CreateTime 2017年11月20日 上午10:25:16
	 * @CreateBy 林龙
	 * @param id
	 * @param pgrid
	 */
	@Override
	public void updatetBypgr(String id, String pgrid,String jx) {
		todoMapper.updatetBypgr(id,pgrid,jx);
	}
	/**
	 * 
	 * @Description 查询待办
	 * @CreateTime 2018年3月22日 下午5:37:48
	 * @CreateBy 林龙
	 * @param todo
	 * @return
	 */
	@Override
	public Todo selecttodo(Todo todo) {
		return todoMapper.selecttodo(todo);
	}
	@Override
	public void updateByPrimaryKeySelective(Todo todoOld) {
		todoMapper.updateByPrimaryKeySelective(todoOld);
		
	}
	@Override
	public List<Todo> selecttodolist(Todo todoInstruc) {
		return todoMapper.selecttodolist(todoInstruc);
	}
	
	/**
	 * 
	 * @Description 生成待办
	 * @CreateTime 2018年4月12日 下午1:56:32
	 * @CreateBy 林龙
	 * @param technical 技术评估
	 * @param strSm 说明
	 * @param dbywid 业务code
	 * @param jd 节点
	 */
	@Override
	public void insertSelectiveTechnical(Object object, String strSm,String gnbm, Integer jd,String blrid,Integer type) {
		 String uuid = UUID.randomUUID().toString();		  //uuid
		 Date currentDate = commonService.getSysdate();//当前时间
		Todo todo=new  Todo();
		todo.setId(uuid);	
		todo.setYxzt(EffectiveEnum.YES.getId());//有效状态为有效
		todo.setZt(TodoStatusEnum.DCL.getId());//状态为待处理
		todo.setDbgzlx(type);//待办工作类型为下达指令类型
		todo.setSm(strSm);
		todo.setJd(jd);
		todo.setFksj(currentDate);
		if(type == TodoEnum.TECHNICAL_ASSESSMENT.getId()){//技术评估单
			Technical technical=new Technical();
			technical=(Technical)object;
			todo.setDprtcode(technical.getDprtcode());
			todo.setLyid(technical.getId()); 	
			todo.setBlqx(technical.getPgqx());
			todo.setLybh(technical.getPgdh());	
			todo.setBb(technical.getBb().toString());		
			todo.setFjjx(technical.getJx());	
			todo.setDbywid(technical.getId());
		}
		if(type == TodoEnum.TECHNICAL_BULLETIN.getId()){//维护提示
			Bulletin bulletin=new Bulletin();
			bulletin=(Bulletin)object;
			todo.setDprtcode(bulletin.getDprtcode());
			todo.setLyid(bulletin.getId()); 	
			todo.setLybh(bulletin.getJstgh());	
			todo.setBb(bulletin.getBb().toString());		
			todo.setFjjx(bulletin.getJx());	
			todo.setDbywid(bulletin.getId());
		}
		if(type == TodoEnum.TECHNICAL_ORDER.getId()){//技术指令
			TechnicalInstruction technicalinstruction=new TechnicalInstruction();
			technicalinstruction=(TechnicalInstruction)object;
			todo.setDprtcode(technicalinstruction.getDprtcode());
			todo.setLyid(technicalinstruction.getId()); 	
			todo.setLybh(technicalinstruction.getJszlh());	
			todo.setBb(technicalinstruction.getBb().toString());		
			todo.setFjjx(technicalinstruction.getJx());	
			todo.setDbywid(technicalinstruction.getId());
		}
		if(type == TodoEnum.MP.getId()){//维修方案
			MaintenanceScheme maintenancescheme=new MaintenanceScheme();
			maintenancescheme=(MaintenanceScheme)object;
			todo.setDprtcode(maintenancescheme.getDprtcode());
			todo.setLyid(maintenancescheme.getId()); 	
			todo.setLybh(maintenancescheme.getWxfabh());	
			todo.setBb(maintenancescheme.getBb().toString());		
			todo.setFjjx(maintenancescheme.getJx());	
			todo.setDbywid(maintenancescheme.getId());
		}
		if(type == TodoEnum.EO.getId()){//eo
			EngineeringOrder engineeringOrder=new EngineeringOrder();
			engineeringOrder=(EngineeringOrder)object;
			todo.setDprtcode(engineeringOrder.getDprtcode());
			todo.setLyid(engineeringOrder.getId()); 	
			todo.setLybh(engineeringOrder.getEobh());	
			todo.setBb(engineeringOrder.getBb().toString());		
			todo.setFjjx(engineeringOrder.getJx());	
			todo.setDbywid(engineeringOrder.getId());
		}
		if(type == TodoEnum.MEL.getId()){//mel
			MelChangeSheet melChangeSheet=new MelChangeSheet();
			melChangeSheet=(MelChangeSheet)object;
			todo.setDprtcode(melChangeSheet.getDprtcode());
			todo.setLyid(melChangeSheet.getId()); 	
			todo.setLybh(melChangeSheet.getGgdbh());	
			todo.setBb(melChangeSheet.getXghBb());		
			todo.setFjjx(melChangeSheet.getJx());	
			todo.setDbywid(melChangeSheet.getId());
		}
		if(type == TodoEnum.WORK_CARD.getId()){//工卡
			WorkCard workCard=new WorkCard();
			workCard=(WorkCard)object;
			todo.setDprtcode(workCard.getDprtcode());
			todo.setLyid(workCard.getId()); 	
			todo.setLybh(workCard.getGkh());	
			todo.setBb(workCard.getBb().toString());		
			todo.setFjjx(workCard.getJx());	
			todo.setDbywid(workCard.getId());
		}
		if(type == TodoEnum.ZJ.getId()){//质检
			Inspection inspection=new Inspection();
			inspection=(Inspection)object;
			todo.setDprtcode(inspection.getDprtcode());
			todo.setLyid(inspection.getId()); 	
			todo.setLybh(inspection.getJydh());	
			todo.setDbywid(inspection.getId());
		}
		if(type == TodoEnum.ZLNSJH.getId()){//质量内审计划
			AnnualPlan annualPlan=new AnnualPlan();
			annualPlan=(AnnualPlan)object;
			todo.setDprtcode(annualPlan.getDprtcode());
			todo.setLyid(annualPlan.getId()); 	
			todo.setLybh(annualPlan.getNf().toString());	
			todo.setBb(annualPlan.getBbh().toString());
			todo.setDbywid(annualPlan.getId());
		}
		if(type == TodoEnum.NSWTZG.getId()){//内审问题整改
			AuditDiscoveryDetail auditDiscoveryDetail=new AuditDiscoveryDetail();
			auditDiscoveryDetail=(AuditDiscoveryDetail)object;
			todo.setDprtcode(auditDiscoveryDetail.getDprtcode());
			todo.setLyid(auditDiscoveryDetail.getId()); 	
			todo.setLybh(auditDiscoveryDetail.getShwtbh());	
			todo.setBlqx(auditDiscoveryDetail.getWtfksj());
			todo.setDbywid(auditDiscoveryDetail.getId());
		}
		if(type == TodoEnum.PRODUCTION_ORDER.getId()){//生产指令
			ProductionOrder productionOrder =(ProductionOrder)object;
			todo.setDprtcode(productionOrder.getDprtcode());
			todo.setLyid(productionOrder.getId()); 	
			todo.setLybh(productionOrder.getZlbh());	
			todo.setBb(productionOrder.getBb().toString());
			todo.setFjjx(productionOrder.getJx());	
			todo.setDbywid(productionOrder.getId());
		}
		
		todo.setGnbm(gnbm);
		todo.setBlrid(blrid);
		todoMapper.insertSelective(todo);
		// 异步发送邮件
		taskService.doEmailTask(todo);
	}
	
	/**
	 * 
	 * @Description 根据待办业务id和节点条件删除
	 * @CreateTime 2018年4月12日 下午2:56:40
	 * @CreateBy 林龙
	 * @param tododbyw
	 * @throws BusinessException
	 */
	@Override
	public void deletedbyw(Todo tododbyw) throws BusinessException {
		todoMapper.deletedbyw(tododbyw);
	}
	/**
	 * 
	 * @Description 根据业务id和节点条件修改
	 * @CreateTime 2018年4月12日 下午3:07:20
	 * @CreateBy 林龙
	 * @param tododbyw
	 * @throws BusinessException
	 */
	@Override
	public void updateByDbyw(Todo tododbyw) throws BusinessException {
		todoMapper.updateByDbyw(tododbyw);
		
	}
	

	/**
	 * @Description 根据id获取数据
	 * @CreateTime 2018-4-23 下午4:49:00
	 * @CreateBy 刘兵
	 * @param id
	 * @return
	 */
	@Override
	public Todo selectByPrimaryKey(String id){
		return todoMapper.selectByPrimaryKey(id);
	}
	
}
