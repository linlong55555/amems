package com.eray.thjw.project2.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.AirworthinessMapper;
import com.eray.thjw.project2.dao.TechnicalMapper;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.TechnicalfileOrder;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.DistributionDepartmentService;
import com.eray.thjw.project2.service.GiveInstructionService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.ReferenceService;
import com.eray.thjw.project2.service.TEApplicabilityService;
import com.eray.thjw.project2.service.TechnicalAttachedService;
import com.eray.thjw.project2.service.TechnicalService;
import com.eray.thjw.project2.service.TechnicalfileOrderService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.ConclusionEnum;
import enu.common.RevMarkEnum;
import enu.produce.NodeEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TechnicalSourseEnum;
import enu.project2.TechnicalStatusEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;

/**
 * @Description 技术评估单Impl
 * @CreateTime 2017年8月16日 上午10:22:32
 * @CreateBy 林龙
 */
@Service("technicalService")
public class TechnicalServiceImpl implements TechnicalService{

	@Resource
	private TechnicalMapper technicalMapper;
	@Resource
	private AirworthinessMapper airworthinessMapper;
	@Resource
	private TEApplicabilityService teapplicabilityservice;
	@Resource
	private CommonService commonService;
	@Resource
	private TechnicalfileOrderService technicalfileOrderService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private TechnicalAttachedService technicalAttachedService;
	
	@Resource
	private TodorsService todorsService;
	
	@Resource
	private GiveInstructionService giveInstructionService;
	
	@Resource
	private ReferenceService referenceService;
	
	@Resource
	private DistributionDepartmentService distributionDepartmentService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private TodoService todoService;
	
	@Resource
	private InstructionsourceService instructionsourceService;
	
	/**
	 *  添加技术文件评估（初始）
	 * @Description 
	 * @CreateTime 2017-8-16 上午10:16:16
	 * @CreateBy 孙霁
	 * @param technical
	 * @param airworthiness
	 * @param czls
	 * @param zdid
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insert(TechnicalfileOrder teco,Airworthiness airworthiness, String czls,
			String zdid, User user,  LogOperationEnum cz) throws BusinessException {
		
		Technical technica = new Technical();
		technica.setId(UUID.randomUUID().toString());
		technica.setJx(teco.getFjjx());
		technica.setPgrid(teco.getPgrid());
		technica.setPgbmid(teco.getDprtmentId());
		technica.setJswjid(airworthiness.getId());
		technica.setPgqx(airworthiness.getPgqx());
		technica.setZjh(airworthiness.getAta());
		technica.setLylx(TechnicalSourseEnum.SHXZL.getId());
		technica.setZt(TechnicalStatusEnum.INITIAL.getId());
		technica.setZxbs(RevMarkEnum.INITIAL.getId());
		technica.setPgdzt(airworthiness.getJswjzt());
		technica.setDprtcode(airworthiness.getDprtcode());
		technicalMapper.insertSelective(technica);
		commonRecService.write(technica.getId(), TableEnum.B_G2_001, user.getId(),czls, cz, UpdateTypeEnum.SAVE, zdid);
		return technica.getId();
	}
	/**
	 * @Description 技术评估单分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(Technical technical)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = technical.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		technical.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		technical.getParamsMap().put("userId", user.getId());
		technical.getParamsMap().put("userType", user.getUserType());
		PageHelper.startPage(technical.getPagination());
		List<Technical> list = technicalMapper.queryAllPageList(technical);
		if(((Page)list).getTotal() > 0){
			// 获取下达指令
			getOrders(list);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					Technical param = new Technical();
					param.setId(id);
					List<Technical> newRecordList = technicalMapper.queryAllPageList(param);
					// 获取下达指令
					getOrders(list);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, technical.getPagination());
		}else{
			List<Technical> newRecordList = new ArrayList<Technical>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				Technical param = new Technical();
				param.setId(id);
				newRecordList = technicalMapper.queryAllPageList(param);
				// 获取下达指令
				getOrders(list);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, technical.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, technical.getPagination());
		}
	}
	
	/**
	 * @Description 技术评估单导出
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAlldaochuList(Technical technical)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = technical.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		technical.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		technical.getParamsMap().put("userId", user.getId());
		technical.getParamsMap().put("userType", user.getUserType());
		PageHelper.startPage(technical.getPagination());
		List<Technical> list = technicalMapper.queryAllPageList(technical);
		if(((Page)list).getTotal() > 0){
			getOrders(list);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					Technical param = new Technical();
					param.setId(id);
					List<Technical> newRecordList = technicalMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, technical.getPagination());
		}else{
			List<Technical> newRecordList = new ArrayList<Technical>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				Technical param = new Technical();
				param.setId(id);
				newRecordList = technicalMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, technical.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, technical.getPagination());
		}
	}
	
	/**
	 * 
	 * @Description 获取下达指令
	 * @CreateTime 2017年9月1日 上午9:26:50
	 * @CreateBy 林龙
	 * @param bulletinList
	 */
	private void getOrders(List<Technical> technicalList) {
		List<String> pgdidList = new ArrayList<String>();// 评估单id集合
		for (Technical technical : technicalList) {
			pgdidList.add(technical.getId());
		}
		if (pgdidList.size() > 0) {
			// 获取达指令集合
			List<GiveInstruction> isList = giveInstructionService.selectOrdersList(pgdidList);
			if (isList.size() > 0) {
				List<GiveInstruction> list = null;
				for (Technical technical : technicalList) {
					StringBuffer str=new StringBuffer();
					list = new ArrayList<GiveInstruction>();
					for (GiveInstruction giveInstruction : isList) {
						if (technical.getId().equals(giveInstruction.getMainid())) {
							str.append(SendOrderEnum.getName(giveInstruction.getZlxl()));
							str.append(",");
							list.add(giveInstruction);
						}
					}
					
					if (list.size() > 0) {
						technical.getParamsMap().put("zlxl", str.substring(0,str.length() - 1));
						technical.setGiveInstructionList(list);
					}
				}
			}
		}
	}
	
	/**
	 * @Description 保存技术评估单
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public String save(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		String uuid = UUID.randomUUID().toString();//uuid-主单id
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		//新增技术评估单 
		technical.setZt(TechnicalStatusEnum.SAVE.getId());//状态为保存
		this.insertSelective(technical,uuid,czls);
		
		//新增技术评估单-附加信息 
		technicalAttachedService.insertTechnicalAttached(technical,uuid,czls);
		
		//新增参考文件 
		referenceService.insertReference(technical.getReferenceList(),uuid,czls);
		
		//新增技术评估单-适用性
		teapplicabilityservice.savePulicationAffectedList(technical.getSyxszList(), user, uuid, currentDate, czls);
		
		//新增技术评估单-下达指令List集合 
		giveInstructionService.insertGiveInstruction(technical.getGiveInstructionList(),uuid,czls);
		
		//新增涉及部门
		distributionDepartmentService.insertDistributionDepartment(technical.getDistributionDepartmentList(),uuid,czls);
		
		//新增附件
		attachmentService.eidtAttachment(technical.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		
		
		
		return uuid;
	}
	
	/**
	 * @Description 修改技术评估单
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public String update(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		
		//评估单id查询技术评估单-附表数量
		int iNum = technicalAttachedService.selectByMainidCount(technical.getId());
		if(iNum == 0){
			if(technical.getPgdh() == null || "".equals(technical.getPgdh())){ 
				String pgdh=this.setPgdbh(technical); //根据评估单对象获取最新评估单编号
				technical.setPgdh(pgdh);
			}else{
				//验证验证技术评估单编号+版本 唯一
				this.validationTechnical(technical);
			}
			//修改技术评估单 
			this.updateByPrimaryKeySelective(technical,czls);
			//新增技术评估单-附加信息 
			technicalAttachedService.insertTechnicalAttached(technical,technical.getId(),czls);
		}else{
			//修改技术评估单 
			this.updateByPrimaryKeySelective(technical,czls);
			//修改技术评估单-附加信息 
			technicalAttachedService.updateTechnicalAttached(technical,technical.getId(),czls);
		}
		
		//编辑参考文件
		referenceService.updateReferenceList(technical.getReferenceList(),ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId(),technical.getId(),czls,technical.getId(),technical.getDprtcode(), LogOperationEnum.EDIT);
		
		//新增技术评估单-适用性
		//适用列表:先删再保存
		teapplicabilityservice.deleteByMainid(technical.getId(),user,czls);
		teapplicabilityservice.savePulicationAffectedList(technical.getSyxszList(), user, technical.getId(), currentDate, czls);
		
		//编辑技术评估单-下达指令List集合 
		giveInstructionService.updateGiveInstruction(technical.getGiveInstructionList(),technical.getId(),czls);
		
		//编辑涉及部门
		distributionDepartmentService.updateDistributionDepartment(technical.getDistributionDepartmentList(),technical.getId(),czls);
		
		//新增附件
		attachmentService.eidtAttachment(technical.getAttachmentList(), technical.getId(), czls, technical.getId(), technical.getDprtcode(), LogOperationEnum.EDIT);
		
		
		todorsService.updatetsByYwid(technical.getPgdh(),technical.getBb(), technical.getPgdzt(),technical.getZt(), technical.getId());//修改
		
		return technical.getId();
	}
	
	/**
	 * 
	 * @Description 新增提交技术评估单
	 * @CreateTime 2017年8月22日 上午11:47:32
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String saveSubmit(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		String uuid = UUID.randomUUID().toString();//uuid-主单id
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间		
		//新增技术评估单 
		technical.setZt(TechnicalStatusEnum.ASSESSMENT.getId());//状态为已评估
		this.insertSelective(technical,uuid,czls);
		
		//新增技术评估单-附加信息 
		technicalAttachedService.insertTechnicalAttached(technical,uuid,czls);
		
		//新增参考文件 
		referenceService.insertReference(technical.getReferenceList(),uuid,czls);
		
		//新增技术评估单-适用性
		teapplicabilityservice.savePulicationAffectedList(technical.getSyxszList(), user, uuid, currentDate, czls);
		
		//新增技术评估单-下达指令List集合 
		giveInstructionService.insertGiveInstruction(technical.getGiveInstructionList(),uuid,czls);
		
		//新增涉及部门
		distributionDepartmentService.insertDistributionDepartment(technical.getDistributionDepartmentList(),uuid,czls);
		
		//新增附件
		attachmentService.eidtAttachment(technical.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SUBMIT_WO);
		
		//新增待办事宜
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(1);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append("请审核");
		strSm.append(technical.getJx());
		strSm.append("机型的");
		strSm.append(technical.getPgdh());
		strSm.append(" R");
		strSm.append(technical.getBb());
		strSm.append("技术评估单。");
		todoService.insertSelectiveTechnical(technical,strSm.toString(),"project2:assessment:audit:main:01",NodeEnum.NODE2.getId(),null,TodoEnum.TECHNICAL_ASSESSMENT.getId());
		
		return uuid;
	}
	

	/**
	 * 
	 * @Description 修改提交技术评估单
	 * @CreateTime 2017年8月22日 上午11:55:10
	 * @CreateBy 林龙
	 * @param technical 技术评估单id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateSubmit(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间		
		//评估单id查询技术评估单-附表数量
		int iNum = technicalAttachedService.selectByMainidCount(technical.getId());
		if(iNum == 0){
			if(technical.getPgdh() == null || "".equals(technical.getPgdh())){ 
				String pgdh=this.setPgdbh(technical); //根据评估单对象获取最新评估单编号
				technical.setPgdh(pgdh);
			}else{
				//验证验证技术评估单编号+版本 唯一
				this.validationTechnical(technical);
			}
			//修改技术评估单 
			technical.setZt(TechnicalStatusEnum.ASSESSMENT.getId());//状态为已评估
			this.updateByPrimaryKeySelective(technical,czls);
			//新增技术评估单-附加信息 
			technicalAttachedService.insertTechnicalAttached(technical,technical.getId(),czls);
		}else{
			//修改技术评估单 
			technical.setZt(TechnicalStatusEnum.ASSESSMENT.getId());//状态为已评估
			this.updateByPrimaryKeySelective(technical,czls);
			//修改技术评估单-附加信息 
			technicalAttachedService.updateTechnicalAttached(technical,technical.getId(),czls);
		}
		
		//编辑参考文件
		referenceService.updateReferenceList(technical.getReferenceList(),ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId(),technical.getId(),czls,technical.getId(),technical.getDprtcode(), LogOperationEnum.SUBMIT_WO);
		
		//新增技术评估单-适用性
		//适用列表:先删再保存
		teapplicabilityservice.deleteByMainid(technical.getId(),user,czls);
		teapplicabilityservice.savePulicationAffectedList(technical.getSyxszList(), user, technical.getId(), currentDate, czls);
		
		//编辑技术评估单-下达指令List集合 
		giveInstructionService.updateGiveInstruction(technical.getGiveInstructionList(),technical.getId(),czls);
		
		//编辑涉及部门
		distributionDepartmentService.updateDistributionDepartment(technical.getDistributionDepartmentList(),technical.getId(),czls);
		
		//新增附件
		attachmentService.eidtAttachment(technical.getAttachmentList(), technical.getId(), czls, technical.getId(), technical.getDprtcode(), LogOperationEnum.SUBMIT_WO);
		
		
		/**新增待办事宜*/
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(1);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append("请审核");
		strSm.append(technical.getJx());
		strSm.append("机型的");
		strSm.append(technical.getPgdh());
		strSm.append(" R");
		strSm.append(technical.getBb());
		strSm.append("技术评估单。");
		//新增待办
		todoService.insertSelectiveTechnical(technical,strSm.toString(),"project2:assessment:audit:main:01",NodeEnum.NODE2.getId(),null,TodoEnum.TECHNICAL_ASSESSMENT.getId());
		
		
		return technical.getId();
	}
	
	/**
	 * 
	 * @Description 修改技术评估单
	 * @CreateTime 2017年8月22日 上午9:19:49
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @param czls 流水号
	 */
	private void updateByPrimaryKeySelective(Technical technical, String czls) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		// 验证机型是否存在
		this.validation4Jx(technical);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		technical.setPgbmid(user.getBmdm());	          //部门id
		technical.setPgrid(user.getId());		          //用户id
		
		//修改技术评估单
		technicalMapper.updateByPrimaryKeySelective(technical);
		
		//添加修改日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,technical.getId());
	}
	
	/**
	 * @Description 保存技术评估单
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public void insertSelective(Technical technical,String uuid, String czls) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		
		// 验证机型是否存在
		this.validation4Jx(technical);
		
		//当页面没有填写评估单编号时
		if(technical.getPgdh() == null || "".equals(technical.getPgdh())){ 
			String pgdh=this.setPgdbh(technical); //根据评估单对象获取最新评估单编号
			technical.setPgdh(pgdh);
		}else{
			//验证验证技术评估单编号+版本 唯一
			this.validationTechnical(technical);
		}
		technical.setDprtcode(user.getJgdm());
		technical.setId(uuid);                            //id
		technical.setZxbs(RevMarkEnum.INITIAL.getId());	  //最新标识为初始
		technical.setPgbmid(user.getBmdm());	          //部门id
		technical.setPgrid(user.getId());		          //用户id
		
		//新增技术评估单
		technicalMapper.insertSelective(technical);
		//添加新增日志
		commonRecService.write(uuid, TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid);
	}
	
	/**
	 * 
	 * @Description 验证当前单据的状态和当前操作的单据状态一致
	 * @CreateTime 2017年8月23日 上午10:56:21
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @param zt 当前状态
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(String id, int zt) throws BusinessException {
		int bzt = technicalMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 验证机型 
	 * @CreateTime 2017年8月23日 上午10:44:03
	 * @CreateBy 林龙
	 * @param technical
	 * @throws BusinessException 
	 */
	private void validation4Jx(Technical technical) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		
		List<String> jxList = new ArrayList<String>();
		if (technical.getJx() != null && StringUtils.isNotBlank(technical.getJx())) {
			jxList.add(technical.getJx());
		} else {
			Technical newRecord = technicalMapper.selectByPrimaryKey(technical.getId());// 审核批准前端没有传机型，需要后端查询
			jxList.add(newRecord.getJx());
		}
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), technical.getDprtcode(), jxList);
	}
	
	/**
	 * 
	 * @Description  根据技术文件单号+版本查询技术评估单验证唯一
	 * @CreateTime 2017年8月17日 下午5:17:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 */
	private void validationTechnical(Technical technical) throws BusinessException {
		
		int  iNum = technicalMapper.getCount4Validation(technical);
		if (iNum > 0) {
			throw new BusinessException("该技术评估单编号+版本已存在!");
		}
	}
	
	/**
	 * 
	 * @Description  根据评估单对象获取最新评估单编号
	 * @CreateTime 2017年8月17日 下午4:32:44
	 * @CreateBy 林龙
	 * @param technical
	 * @return pgdbhNew 评估单编号
	 */
	private String setPgdbh(Technical technical) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		StringBuffer pgdbhNew = new StringBuffer();//评估单编号
		Technical tec = new Technical(); //new 技术文件对象
		boolean b = true;
		while(b){
			String pgdh;
			try {
				//采番获得技术文件编号-参数组织机构，代码：JSPG  例：技术评估单号-PG-2016-流水号（0000）
				pgdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JSPG.getName()); 
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			pgdbhNew.append(pgdh);
			tec.setPgdh(pgdh);
			tec.setDprtcode(user.getJgdm());
			//根据技术文件对象查询技术文件数量
			int i = technicalMapper.queryCount(tec); 
			if(i <= 0){
				b = false;
			}
		}
		return pgdbhNew.toString();
	}
	/**
	 * @Description 技术评估单删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public void delete(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		
		Technical technicalNew =technicalMapper.getById(technical);
		if(technicalNew.getfBbid()!=null){
			//去掉前版本评估单的后版本id
			Technical technicalOld=technicalMapper.selectByPrimaryKey(technicalNew.getfBbid());
			technicalOld.setbBbid("");
			technicalMapper.updateByPrimaryKeySelective(technicalOld);
		}
		//添加新增日志
		commonRecService.write(technical.getfBbid(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,technical.getId());
		
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		//添加删除日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,technical.getId());
		
		//删除技术评估单-附加信息 
		technicalAttachedService.deleteTechnicalAttached(technical.getId(),czls);
		
		//删除参考文件集合
		referenceService.deleteReference(technical.getId(),czls);
		
		//删除技术评估单-下达指令List集合 
		giveInstructionService.deleteGiveInstruction(technical.getId(),czls);
		
		if(technicalNew.getTechnicalAttached()!=null){
			//删除结论附件
			attachmentService.deleteById(technicalNew.getTechnicalAttached().getJlfjid(), czls, technical.getId(), LogOperationEnum.DELETE);
		}
		
		//删除涉及部门
		distributionDepartmentService.deleteDistributionDepartment(technical.getId(),czls);
		
		//删除附件
		attachmentService.deleteAttachment(technical.getId(), czls);
		
		//删除技术评估单
		technicalMapper.deleteByPrimaryKey(technical.getId());
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
		
	}

	/**
	 * 
	 * @Description 技术评估单弹出列表
	 * @CreateTime 2017年8月16日 上午11:00:11
	 * @CreateBy 岳彬彬
	 * @param technical
	 * @return
	 */
	@Override
	public Map<String, Object> selectPgdByZlidAndDprtcode(Technical technical) {
		//用户刚编辑过的记录 ID
				String id = technical.getId();
				//清除查询条件中的ID，保证列表查询结果集的正确性
				technical.setId(null);
				PageHelper.startPage(technical.getPagination());
				List<Technical> list = technicalMapper.selectPgdByZlidAndDprtcode(technical);
				if(((Page)list).getTotal() > 0){
					attachment(list);
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						if(!PageUtil.hasRecord(list, id)){
							Technical param = new Technical();
							param.setId(id);
							List<Technical> newRecordList = technicalMapper.selectPgdByZlidAndDprtcode(param);
							attachment(newRecordList);
							if(newRecordList != null && newRecordList.size() == 1){
								list.add(0, newRecordList.get(0));
							}
						}
					}
					return PageUtil.pack4PageHelper(list, technical.getPagination());
				}else{
					List<Technical> newRecordList = new ArrayList<Technical>(1);
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						Technical param = new Technical();
						param.setId(id);
						newRecordList = technicalMapper.selectPgdByZlidAndDprtcode(param);
						attachment(newRecordList);
						if(newRecordList != null && newRecordList.size() == 1){
							return PageUtil.pack(1, newRecordList, technical.getPagination());
						}
					}
					return PageUtil.pack(0, newRecordList, technical.getPagination());
				}
	}
	/**
	 * 
	 * @Description 获取适航性资料附件
	 * @CreateTime 2017年8月22日 下午12:03:14
	 * @CreateBy 岳彬彬
	 * @param list
	 */
	private void attachment(List<Technical> list){
		
		List<String> ids=new ArrayList<String>();
		for (Technical Technical : list) {
			if(Technical.getJswjid() != null&&!"".equals(Technical.getJswjid())){
				ids.add(Technical.getJswjid());
			}			
		}
		if(ids.size()>0){
			List<Attachment> attList=attachmentMapper.queryPlaneDataListAttachmentsByMainIds(ids);
			if(attList.size()>0){
				for (Technical Technical : list) {
					for (Attachment att : attList) {
						if(att.getMainid().equals(Technical.getJswjid())){
							Technical.getAirworthiness().setScfj(att);
						}
					}
					
				}
			}
		}		
	}
	
	/**
	 * @Description 根据适航性资料id查询评估单
	 * @CreateTime 2017年8月15日 下午8:17:29
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return List<Technical> 技术评估单List集合
	 */
	@Override
	public Map<String, Object> queryGljspgdList(Technical technical) {
		PageHelper.startPage(technical.getPagination());
		List<Technical> list=technicalMapper.queryGljspgdList(technical);
		return PageUtil.pack4PageHelper(list, technical.getPagination());
	}
	
	/**
	 * 
	 * @Description  根据适航性资料查询数据
	 * @CreateTime 2017-8-16 上午10:45:54
	 * @CreateBy 孙霁
	 * @param jswjid
	 * @return
	 */
	@Override
	public List<Technical> selectByjswjid(String jswjid) {
		return technicalMapper.selectByjswjid(jswjid);
	}
	
	/**
	 * 
	 * @Description 修改评估单评估期限和zjh
	 * @CreateTime 2017-8-16 下午2:41:13
	 * @CreateBy 孙霁
	 * @param pgqx
	 * @param zjh
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateTechnical(String id, Date pgqx, String zjh,String czls, String zdid,User user, LogOperationEnum cz, TechnicalfileOrder teco, boolean b)
			throws BusinessException {
		Technical technical = new Technical(); 
		technical.setPgqx(pgqx);
		technical.setZjh(zjh);
		technical.setId(id);
		if(b){
			technical.setPgrid(teco.getPgrid());	
			technical.setPgbmid(teco.getDprtmentId());	
		}
		technicalMapper.updateByPrimaryKeySelective(technical);
		commonRecService.write(id, TableEnum.B_G2_001, user.getId(), czls, cz, UpdateTypeEnum.UPDATE, zdid);
	}
	/**
	 * 
	 * @Description 物理删除评估单评估
	 * @CreateTime 2017-8-16 下午2:50:03
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void deleteTechnical(String id) throws BusinessException {
		technicalMapper.deleteByPrimaryKey(id);
	}
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017-8-18 下午4:51:54
	 * @CreateBy 孙霁
	 * @param jswjid
	 * @return
	 */
	@Override
	public Map<String, Object> selectTechnicalfileByJswjid(String jswjid) throws BusinessException {
		//创建一个返回值map
		Map<String, Object> map = new HashMap<String, Object>();
		
		//查询评估单数据
		List <Technical> technicalList = technicalMapper.selectTechnicalfileByJswjid(jswjid);
		map.put("technicalList", technicalList);
		
		//判断是否有数据，如果有数据，取第一条的id获取下达指令数据
		if(technicalList != null && technicalList.size() > 0){
			Todo todo = new Todo();
			todo.setLyid(technicalList.get(0).getId());
			//根据第一条数据id 获取待办事宜数据
			List<Todo> todoList = todoService.selectOrderList(todo);
			map.put("todoList",todoList);
		}
		
		return map;
	}
	
	/**
	 * @Description 根据技术评估单id查询技术评估单信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Technical getById(Technical technical) {
		return technicalMapper.getById(technical);
	}
	
	/**
	 * 
	 * @Description 审核技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public String passed(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		Date currentDate = commonService.getSysdate();//当前时间
		User user=ThreadVarUtil.getUser();					//当前登陆人
		// 验证机型是否存在
		this.validation4Jx(technical);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		technical.setZt(TechnicalStatusEnum.AUDIT.getId()); //状态为已审核
		technical.setShbmid(user.getBmdm());				//审核部门id
		technical.setShrid(user.getId());					//审核人id
		technical.setShjl(ConclusionEnum.AUDITED.getId());  //结论：已审核
		
		//修改技术评估单
		technicalMapper.updateByPrimaryKeySelective(technical);
		
		//添加技术评估单已审核日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE,technical.getId());
		
		//新增待办事宜
		Technical technical1=technicalMapper.selectByPrimaryKey(technical.getId());
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical1.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append("请审批");
		strSm.append(technical1.getJx());
		strSm.append("机型的");
		strSm.append(technical1.getPgdh());
		strSm.append(" R");
		strSm.append(technical1.getBb());
		strSm.append("技术评估单。");
		todoService.insertSelectiveTechnical(technical1,strSm.toString(),"project2:assessment:approval:main:01",NodeEnum.NODE3.getId(),null,TodoEnum.TECHNICAL_ASSESSMENT.getId());
		
		return technical.getId();
	}
	
	/**
	 * 
	 * @Description 审核驳回技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public String turnDown(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();				//流水号
		User user = ThreadVarUtil.getUser();					//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证机型是否存在
		this.validation4Jx(technical);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		technical.setZt(TechnicalStatusEnum.AUDITDOWN.getId()); //状态为审核驳回
		technical.setShbmid(user.getBmdm());					//审核部门id
		technical.setShrid(user.getId());						//审核人id
		technical.setShjl(ConclusionEnum.AUDITEDREJECTED.getId());  //结论：审核驳回
		
		//修改技术评估单
		technicalMapper.updateByPrimaryKeySelective(technical);
		
		//添加技术评估单审核驳回日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,technical.getId());
		
		Technical technical1=technicalMapper.selectByPrimaryKey(technical.getId());
		
		//新增待办事宜
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical1.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append(technical1.getJx());
		strSm.append("机型的");
		strSm.append(technical1.getPgdh());
		strSm.append(" R");
		strSm.append(technical1.getBb());
		strSm.append("技术评估单已经驳回，请重新提交。");
		todoService.insertSelectiveTechnical(technical1,strSm.toString(),"project2:assessment:main",NodeEnum.NODE5.getId(),technical1.getPgrid(),TodoEnum.TECHNICAL_ASSESSMENT.getId());
		
		
		return technical.getId();
	}
	
	/**
	 * 
	 * @Description 批准技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public String approvalPassed(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		Date currentDate = commonService.getSysdate();//当前时间
		User user = ThreadVarUtil.getUser();				   //当前登陆人
		// 验证机型是否存在
		this.validation4Jx(technical);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		technical.setZt(TechnicalStatusEnum.APPROVAL.getId()); //状态为已批准
		technical.setPfbmid(user.getBmdm());				   //批复部门id
		technical.setPfrid(user.getId());					   //批复人id
		technical.setPfjl(ConclusionEnum.APPROVED.getId());    //结论：已批准
		
		//将该评估单的前版本ID的数据最新标识 = 3老版本，该评估单最新标识=2新版本
		technical.setZxbs(RevMarkEnum.LATEST_VERSION.getId()); //最新标识为最新版本 2
		
		//修改技术评估单
		technicalMapper.updateByPrimaryKeySelective(technical);
		
		//添加技术评估单已批准日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,technical.getId());
		
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(3);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//新增待办事宜
		todoService.saveTodoList(technical.getId());
		
		//上个版本最新标识改为3
		if(technical.getfBbid()!=null){//前版本id不为null
			
			Technical technicalOld=new Technical();
			technicalOld.setId(technical.getfBbid());
			technicalOld.setZxbs(RevMarkEnum.OLD_VERSION.getId());//最新标识为老版本 3
			technicalMapper.updateByPrimaryKeySelective(technicalOld);
			
			//添加技术评估单已批准日志
			commonRecService.write(technical.getfBbid(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,technical.getId());
		}
		
		return technical.getId();
	}
	
	/**
	 * 
	 * @Description 批准驳回技术评估单
	 * @CreateTime 2017年8月22日 下午8:43:13
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return 技术评估单id
	 * @throws BusinessException
	 */
	@Override
	public String approvalTurnDown(Technical technical)throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();					       //当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证机型是否存在
		this.validation4Jx(technical);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		technical.setZt(TechnicalStatusEnum.APPROVALDOWN.getId()); //状态为批准驳回
		technical.setPfbmid(user.getBmdm());				       //批复部门id
		technical.setPfrid(user.getId());					       //批复人id
		technical.setPfjl(ConclusionEnum.APPROVEDREJECTED.getId());//结论：批准驳回
		
		//修改技术评估单
		technicalMapper.updateByPrimaryKeySelective(technical);
		
		//添加技术评估单审核驳回日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,technical.getId());
		
		Technical technical1 =technicalMapper.selectByPrimaryKey(technical.getId());
		//新增待办事宜
		//关闭待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(3);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		tododbyw.setBlrid(user.getId());
		tododbyw.setFksj(currentDate);
		todoService.updateByDbyw(tododbyw);
		
		//拼接说明
		StringBuffer strSm = new StringBuffer();
		strSm.append(technical1.getJx());
		strSm.append("机型的");
		strSm.append(technical1.getPgdh());
		strSm.append(" R");
		strSm.append(technical1.getBb());
		strSm.append("技术评估单已经驳回，请重新提交。");
		todoService.insertSelectiveTechnical(technical1,strSm.toString(),"project2:assessment:main",NodeEnum.NODE5.getId(),technical1.getPgrid(),TodoEnum.TECHNICAL_ASSESSMENT.getId());
		
		return technical.getId();
	}
	
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月23日 上午9:29:21
	 * @CreateBy 林龙
	 * @param idList idlist
	 * @param yj 意见
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateBatchAudit(List<String> idList, String yj) throws BusinessException {
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<Technical> fItemList = technicalMapper.queryByIdList(idList);
		for (Technical item : fItemList) {
			if (2 == item.getZt()) {
				auditIdList.add(item.getId());
				buffer.append("技术评估单【").append(item.getPgdh()).append("】审核通过!<br>");
			} else {
				buffer.append("技术评估单【").append(item.getPgdh()).append("】数据已更新,未能审核成功!<br>");
			}
		}

		if (null != auditIdList && auditIdList.size() > 0) {
			String czls = UUID.randomUUID().toString();// 操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("zt", TechnicalStatusEnum.AUDIT.getId());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			technicalMapper.updateBatchAudit(paramMap);
			commonRecService.write("id", auditIdList, TableEnum.B_G2_001, user.getId(), czls,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, null);// 插入日志
		
			//批量新增待办事宜
			todoService.saveBatchTodoAuditList(auditIdList,TodoEnum.TECHNICAL_ASSESSMENT.getId());
		
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年8月23日 上午9:29:21
	 * @CreateBy 林龙
	 * @param idList idlist
	 * @param yj 意见
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateBatchApprovel(List<String> idList, String yj) throws BusinessException {
		String czls = UUID.randomUUID().toString();// 操作流水
		User user = ThreadVarUtil.getUser();
		List<String> approvelIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<Technical> fItemList = technicalMapper.queryByIdList(idList);
		
		for (Technical item : fItemList) {
			if (3 == item.getZt()) {
				approvelIdList.add(item.getId());
				buffer.append("技术评估单【").append(item.getPgdh()).append("】批准通过!<br>");
			} else {
				buffer.append("技术评估单【").append(item.getPgdh()).append("】数据已更新,未能批准成功!<br>");
			}
			
			//上个版本最新标识改为3
			if(item.getfBbid()!=null){//前版本id不为null
				
				Technical technicalOld=new Technical();
				technicalOld.setId(item.getfBbid());
				technicalOld.setZxbs(RevMarkEnum.OLD_VERSION.getId());//最新标识为老版本 3
				technicalMapper.updateByPrimaryKeySelective(technicalOld);
				
				//添加技术评估单已批准日志
				commonRecService.write(item.getfBbid(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,item.getId());
			}
		}

		if (null != approvelIdList && approvelIdList.size() > 0) {
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pfrid", user.getId());
			paramMap.put("pfbmid", user.getBmdm());
			paramMap.put("zt", TechnicalStatusEnum.APPROVAL.getId());
			paramMap.put("zxbs", RevMarkEnum.LATEST_VERSION.getId());//最新标识为最新版本 2
			paramMap.put("idList", approvelIdList);
			paramMap.put("pfyj", yj);
			technicalMapper.updateBatchApprovel(paramMap);
			commonRecService.write("id", approvelIdList, TableEnum.B_G2_001, user.getId(), czls,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, null);// 插入日志
		
			//批量新增待办事宜
			todoService.saveBatchTodoList(approvelIdList);
			
		}
		return buffer.toString();
	}
	
	/**
	 * 
	 * @Description 根据id查询技术评估单
	 * @CreateTime 2017年8月24日 上午10:20:23
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Technical selectByPrimaryKey(String id) throws BusinessException {
		return technicalMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 
	 * @Description 关闭技术评估单
	 * @CreateTime 2017年8月24日 下午6:08:06
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @throws BusinessException
	 */
	@Override
	public void updateEndModal(Technical technical) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) technical.getParamsMap().get("currentZt"));
		
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), iCurrentZt);
		
		if(iCurrentZt == TechnicalStatusEnum.ASSESSMENT.getId() || iCurrentZt == TechnicalStatusEnum.AUDIT.getId() || iCurrentZt == TechnicalStatusEnum.WANCHENG.getId()){//当当前状态为已评估和已审核，已完成
			
			technical.setGbrid(user.getId());
			technicalMapper.updateByPrimaryKeySelective(technical);
			
			//添加新增日志
			commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,technical.getId());
		
			Technical technicalNew =technicalMapper.selectByPrimaryKey(technical.getId());
			
			//去掉前版本评估单的后版本id
			Technical technicalOld=technicalMapper.selectByPrimaryKey(technicalNew.getfBbid());
			if(technicalOld!=null){
				technicalMapper.updateByPrimaryKeySelective(technicalOld);
				//添加新增日志
				commonRecService.write(technical.getfBbid(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,technical.getId());
			}
			
		}else{//当当前状态为已批准
			
			//根据评估单id验证 下达的指令（除技术通告） 必须EO关闭（指定结束/完成）维修方案批准、MEL更改单批准、工卡生效/失效、 技术指令关闭 才能关闭
			instructionsourceService.validation4Colse(technical.getId());
			
			technical.setGbrid(user.getId());
			technicalMapper.updateByPrimaryKeySelective(technical);
			
			LogOperationEnum logOperationEnum=null;
			if(technical.getZt() == TechnicalStatusEnum.CLOSE.getId()){
				logOperationEnum = LogOperationEnum.GUANBI; //状态为指定结束
			}else{
				logOperationEnum = LogOperationEnum.WANCHEN;//状态为完成
			}
			
			//添加新增日志
			commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, logOperationEnum, UpdateTypeEnum.UPDATE,technical.getId());
		}
		
		//处理技术文件下发评估单的待办
		Technical technicalNews =technicalMapper.selectByPrimaryKey(technical.getId());
		if(technicalNews.getJswjid() !=null){
			Todo todo=new Todo();
			todo.setLyid(technicalNews.getJswjid());
			todo.setFjjx(technicalNews.getJx());
			todo.setZt(1);
			Todo todoOld=todoService.selecttodo(todo); //查询待办
			if(todoOld !=null ){
				todoOld.setZt(2);
				todoOld.setFkyj(technical.getGbyy());
				todoOld.setFksj(currentDate);
				todoService.updateByPrimaryKeySelective(todoOld);//修改待办
			}
		}
		
		//处理评估单下发指令的待办
		Todo todoInstruc=new Todo();
		todoInstruc.setLyid(technical.getId());
		todoInstruc.setFjjx(technicalNews.getJx());
		todoInstruc.setZt(1);
		List<Todo> todoInstrucOld=todoService.selecttodolist(todoInstruc); //查询待办
		for (Todo todo : todoInstrucOld) {
			todo.setZt(2);
			todo.setFkyj(technical.getGbyy());
			todo.setFksj(currentDate);
			todoService.updateByPrimaryKeySelective(todo);//修改待办
			
		}
		
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
		
	}
	
	/**
	 * 
	 * @Description 初始化数据 
	 * @CreateTime 2017年8月24日 下午9:47:25
	 * @CreateBy 林龙
	 * @param request
	 * @return
	 */
	@Override
	public Map<String, Object> initData() {
		
		Map<String, Object> model  = new HashMap<String, Object>();
	
		model.put("technicalStatusEnum", TechnicalStatusEnum.enumToListMap());
		model.put("sendOrderEnum", SendOrderEnum.enumToListMap());
		return model;
	}
	
	/**
	 * 
	 * @Description 改版保存技术评估单
	 * @CreateTime 2017年8月26日 上午10:04:11
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insertRevisionSave(Technical technical)throws BusinessException {
		
		String czls = UUID.randomUUID().toString();//流水号
		String uuid = UUID.randomUUID().toString();//uuid-主单id
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间	
		//新增改版的技术评估单 
		technical.setZt(TechnicalStatusEnum.SAVE.getId());//状态为保存
		this.insertRevision(technical,uuid,czls);
		
		//新增技术评估单-附加信息 
		technicalAttachedService.insertTechnicalAttached(technical,uuid,czls);
		
		//新增参考文件 
		referenceService.insertReference(technical.getReferenceList(),uuid,czls);
		
		teapplicabilityservice.savePulicationAffectedList(technical.getSyxszList(), user, technical.getId(), currentDate, czls);
		
		//新增技术评估单-下达指令List集合 
		giveInstructionService.insertGiveInstruction(technical.getGiveInstructionList(),uuid,czls);
		
		//新增涉及部门
		distributionDepartmentService.insertDistributionDepartment(technical.getDistributionDepartmentList(),uuid,czls);
		
		//新增附件
		attachmentService.eidtAttachment(technical.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		return uuid;
	}
	
	/**
	 * 
	 * @Description 新增改版的技术评估单 
	 * @CreateTime 2017年8月26日 上午10:20:29
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @param uuid 新id
	 * @param czls 流水号
	 */
	private void insertRevision(Technical technical, String uuid, String czls)throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		
		// 验证机型是否存在
		this.validation4Jx(technical);
		String oldId=technical.getId();//
		//验证上一个版本
		
		technical.setfBbid(technical.getId());//前版本id
		technical.setZxbs(RevMarkEnum.INITIAL.getId());	  //最新标识为初始
		technical.setDprtcode(user.getJgdm());
		technical.setId(uuid);                            //id
		technical.setPgbmid(user.getBmdm());	          //部门id
		technical.setPgrid(user.getId());		          //用户id
		
		//新增技术评估单
		technicalMapper.insertSelective(technical);
		
		//添加新增日志
		commonRecService.write(uuid, TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid);

		//上一个版本，同时后版本id为当前页面的id
		Technical technicalOld=new Technical();
		technicalOld.setId(oldId);//上个版本id
		technicalOld.setbBbid(uuid);//后版本id为当前最新id
		technicalMapper.updateByPrimaryKeySelective(technicalOld);
		
	}
	/**
	 * 
	 * @Description 改版提交技术评估单
	 * @CreateTime 2017年8月26日 上午10:04:04
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateRevisionSubmit(Technical technical)throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		String uuid = UUID.randomUUID().toString();//uuid-主单id
		User user = ThreadVarUtil.getUser();//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间	
		
		//新增改版的技术评估单 
		technical.setZt(TechnicalStatusEnum.ASSESSMENT.getId());//状态为保存
		this.insertRevision(technical,uuid,czls);
		
		//新增技术评估单-附加信息 
		technicalAttachedService.insertTechnicalAttached(technical,uuid,czls);
		
		//新增参考文件 
		referenceService.insertReference(technical.getReferenceList(),uuid,czls);
		
		teapplicabilityservice.savePulicationAffectedList(technical.getSyxszList(), user, technical.getId(), currentDate, czls);
		
		//新增技术评估单-下达指令List集合 
		giveInstructionService.insertGiveInstruction(technical.getGiveInstructionList(),uuid,czls);
		
		//新增涉及部门
		distributionDepartmentService.insertDistributionDepartment(technical.getDistributionDepartmentList(),uuid,czls);
		
		//新增附件
		attachmentService.eidtAttachment(technical.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		return uuid;
	}
	
	/**
	 * 
	 * @Description 根据技术评估单查询最大版本
	 * @CreateTime 2017年8月28日 下午3:57:59
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Technical selectMaxBb(Technical technical) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		technical.setDprtcode(user.getJgdm());
		return technicalMapper.selectMaxBb(technical);
	}
	
	/**
	 * 
	 * @Description 根据id查询当前评估单的所有版本
	 * @CreateTime 2017年8月29日 上午10:50:57
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @return
	 */
	@Override
	public List<Technical> queryHistoryListById(String id, String dprtcode)throws BusinessException {
		
		return technicalMapper.queryHistoryListById(id,dprtcode);
	}
	/**
	 * 
	 * @Description 根据arids获取评估单数据
	 * @CreateTime 2017-9-1 下午3:59:57
	 * @CreateBy 孙霁
	 * @param arids
	 * @throws BusinessException
	 * @return
	 */
	public List<Technical> queryAllByGlxswjids(List<String> arids)
			throws BusinessException {
		return technicalMapper.queryAllByGlxswjids(arids);
	}
	/**
	 * 
	 * @Description 修改评估人
	 * @CreateTime 2017年11月17日 下午5:08:36
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String updateAssessor(Technical technical) throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		// 验证机型是否存在
		this.validation4Jx(technical);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(technical.getId(), Integer.valueOf((String) technical.getParamsMap().get("currentZt")));
		
		//修改技术评估单
		technicalMapper.updateByPrimaryKeySelective(technical);
		
		//添加修改日志
		commonRecService.write(technical.getId(), TableEnum.B_G2_001,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,technical.getId());
		
		if(!technical.getJswjid().equals("")){
			//当技术文件id存在时，更新评估人
			technicalfileOrderService.updatetBypgr(technical.getJswjid(), technical.getJx(),technical.getPgrid());
			//修改 待办事宜办理人
			todoService.updatetBypgr(technical.getJswjid(), technical.getPgrid(),technical.getJx());
		}
		
		//删除1，5待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(technical.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(1);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		tododbyw.setZt(TodoStatusEnum.DCL.getId());
		todoService.deletedbyw(tododbyw);
		
		//根据状态来新增待办
		Technical technicalNews =technicalMapper.selectByPrimaryKey(technical.getId());
		if(technicalNews.getZt().equals(TechnicalStatusEnum.AUDITDOWN.getId()) || technicalNews.getZt().equals(TechnicalStatusEnum.APPROVALDOWN.getId())){
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(technicalNews.getJx());
			strSm.append("机型的");
			strSm.append(technicalNews.getPgdh());
			strSm.append(" R");
			strSm.append(technicalNews.getBb());
			strSm.append("技术评估单已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(technicalNews,strSm.toString(),"project2:assessment:main",NodeEnum.NODE5.getId(),technicalNews.getPgrid(),TodoEnum.TECHNICAL_ASSESSMENT.getId());
		}else{
			if(technicalNews.getJswjid() != null){
				Airworthiness airworthiness=airworthinessMapper.selectByPrimaryKey(technicalNews.getJswjid());
				todoService.insert(technical.getPgrid(), airworthiness, technical.getJx(),technical.getId());
			}
		}
		
		return technical.getId();
	}
	
	/**
	 * @Description 根据适航性资料id查询评估单
	 * @CreateTime 2017年8月15日 下午8:17:29
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return List<Technical> 技术评估单List集合
	 */
	@Override
	public Map<String, Object> getgljspgdListold(Technical technical) {
		PageHelper.startPage(technical.getPagination());
		List<Technical> list=technicalMapper.getgljspgdListold(technical);
		return PageUtil.pack4PageHelper(list, technical.getPagination());
	}
	/**
	 * @Description 根据适航性资料id查询评估单
	 * @CreateTime 2017年8月15日 下午8:17:29
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return List<Technical> 技术评估单List集合
	 */
	@Override
	public List<Technical> getgljspgdListoldwu(Technical technical) {
		return technicalMapper.getgljspgdListold(technical);
	}
	
}
