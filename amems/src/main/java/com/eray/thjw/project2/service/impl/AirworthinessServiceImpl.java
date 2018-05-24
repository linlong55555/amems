package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.monitorsettings.dao.MonitorsettingsMapper;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.project2.dao.AirworthinessMapper;
import com.eray.thjw.project2.dao.TEApplicabilityMapper;
import com.eray.thjw.project2.dao.TechnicalfileOrderMapper;
import com.eray.thjw.project2.dao.TodorsMapper;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.TEApplicability;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.TechnicalfileOrder;
import com.eray.thjw.project2.service.AirworthinessService;
import com.eray.thjw.project2.service.TechnicalService;
import com.eray.thjw.project2.service.TechnicalfileOrderService;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.ThresholdEnum;
import enu.UpdateTypeEnum;
import enu.common.WhetherEnum;
import enu.project2.AirworthinessIspgEnum;
import enu.project2.AirworthinessMonitorStatusEnum;
import enu.project2.AirworthinessOperationEnum;
import enu.project2.AirworthinessStatusEnum;
import enu.project2.MonitorProjectEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TechnicalEnum;
import enu.project2.TechnicalInstructionStatusEnum;
import enu.project2.TechnicalStatusEnum;
/**
 * 
 * @Description 适航性资料service实现
 * @CreateTime 2017-8-22 上午10:31:11
 * @CreateBy 孙霁
 */
@Service
public class AirworthinessServiceImpl implements AirworthinessService{

	@Resource
	private AirworthinessMapper airworthinessMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private TechnicalfileOrderService technicalfileOrderService;
	@Resource
	private TechnicalService technicalService;
	@Resource
	private TodoService todoService;
	@Resource
	private TodorsMapper todorsMapper;
	@Resource
	private UserService userService;
	@Resource
	private TodorsService todorsService;
	@Resource
	private TechnicalfileOrderMapper technicalfileOrderMapper;
	@Resource
	private TEApplicabilityMapper tEApplicabilityMapper;
	@Autowired
	private MonitorsettingsMapper monitorsettingsMapper;
	
	/**
	 * 
	 * @Description 根据查询条件查询适航性资料
	 * @CreateTime 2017-8-15 下午6:48:49
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @return
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAll(Airworthiness airworthiness) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=airworthiness.getId();
		airworthiness.setId("");
		try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(airworthiness.getPagination());
			List<Airworthiness> list=airworthinessMapper.queryAll(airworthiness);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Airworthiness newRecord = new Airworthiness();
						newRecord.setId(id);
						List<Airworthiness> newRecordList = airworthinessMapper.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				this.gettechnicalfileorder(list);
				this.getWarningVal(list);
				resultMap=PageUtil.pack4PageHelper(list, airworthiness.getPagination());
				return resultMap;
				
			}else{
				List<Airworthiness> newRecordList = new ArrayList<Airworthiness>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Airworthiness newRecord = new Airworthiness();
					newRecord.setId(id);
					newRecordList = airworthinessMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, airworthiness.getPagination());
						return resultMap;
					}
					
				}
				this.gettechnicalfileorder(newRecordList);
				this.getWarningVal(newRecordList);
				resultMap= PageUtil.pack(0, newRecordList, airworthiness.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}
	
	//获取预警阀值: 查询优先级 1.先子类 2.再主类  3.最后-1
	private void getWarningVal(List<Airworthiness> newRecordList) {
		for (Airworthiness obj : newRecordList) {
			
			Monitorsettings monitorsettings=new Monitorsettings();
			monitorsettings.setDprtcode(obj.getDprtcode());
			monitorsettings.setKey(ThresholdEnum.JSWJ.getName()+"_"+obj.getJswjlx());//子类型
			Monitorsettings existObj = monitorsettingsMapper.selectThresholdByKeyandDprtcode(monitorsettings);
			
			if(null == existObj){
				monitorsettings.setKey(ThresholdEnum.JSWJ.getName());//主类型
				existObj = monitorsettingsMapper.selectThresholdByKeyandDprtcode(monitorsettings);
				if(null == existObj){
					monitorsettings.setDprtcode("-1"); //-1
					monitorsettings.setKey(ThresholdEnum.JSWJ.getName());//主类型
					existObj = monitorsettingsMapper.selectThresholdByKeyandDprtcode(monitorsettings);
				}
			}
			
			if(null != existObj){
				obj.setYjtsJb1(existObj.getYjtsJb1()==null?0:existObj.getYjtsJb1());
				obj.setYjtsJb2(existObj.getYjtsJb2()==null?0:existObj.getYjtsJb2());
				obj.setYjtsJb3(existObj.getYjtsJb3()==null?0:existObj.getYjtsJb3());
			}else{
				obj.setYjtsJb1(0);
				obj.setYjtsJb2(0);
				obj.setYjtsJb3(0);
			}
			
		}
	}
	
	/**
	 * 
	 * @Description 组装技术指令列表
	 * @CreateTime 2017-8-15 下午6:50:37
	 * @CreateBy 孙霁
	 * @param airworthinessList
	 * @return
	 * @throws BusinessException 
	 */
	private List<Airworthiness> gettechnicalfileorder(List<Airworthiness> airworthinessList) throws BusinessException{
		if(airworthinessList!= null && airworthinessList.size()>0){
			List<String > arids=new ArrayList<String>();
			//循环主表数据，把id放入idList集合
			for (Airworthiness airworthiness : airworthinessList) {
				arids.add(airworthiness.getId());
			}
			//根据idList查询从表数据
			List<TechnicalfileOrder> technicalfileOrderList= new ArrayList<TechnicalfileOrder>();
			
			for (int i = 0; i < Math.ceil((double) arids.size() / 1000); i++) {
				technicalfileOrderList.addAll(technicalfileOrderService.queryAllByMainids(arids.subList(i * 1000,arids.size() < ((i + 1) * 1000) ? arids.size() : ((i + 1) * 1000))));
			}
			
			//循环主表数据根据 主表id 对比 从表mainid,把数据组织到主表List中
			for (Airworthiness airworthiness : airworthinessList) {
				for (TechnicalfileOrder technicalfileOrder : technicalfileOrderList) {
					//对比id是否相同，如果相同，添加
					if(airworthiness.getId().equals(technicalfileOrder.getMainid())){
						//判断List是否为空，如果为空，创建一个list,如果不为空，直接add
						if(airworthiness.getTechnicalfileOrderList() != null && airworthiness.getTechnicalfileOrderList().size()>0){
							airworthiness.getTechnicalfileOrderList().add(technicalfileOrder);
						}else{ 
							List<TechnicalfileOrder> toList=new ArrayList<TechnicalfileOrder>();
							toList.add(technicalfileOrder);
							airworthiness.setTechnicalfileOrderList(toList);
						}
					}
					
				}
			}
			
			//查询评估单状态是否都是完成或指定结束或批准
			//1.获取arids的评估单
			List<Technical> technicalList= new ArrayList<Technical>();
			for (int i = 0; i < Math.ceil((double) arids.size() / 1000); i++) {
				technicalList.addAll(technicalService.queryAllByGlxswjids(arids.subList(i * 1000,arids.size() < ((i + 1) * 1000) ? arids.size() : ((i + 1) * 1000))));
			}
			//循环主表数据根据 主表id 对比 从表mainid,把数据组织到主表List中
			for (Airworthiness airworthiness : airworthinessList) {
				for (Technical technical : technicalList) {
					//对比id是否相同，如果相同，添加
					if(airworthiness.getId().equals(technical.getJswjid())){
						//判断List是否为空，如果为空，创建一个list,如果不为空，直接add
						if(airworthiness.getTechnicalList() != null && airworthiness.getTechnicalList().size()>0){
							airworthiness.getTechnicalList().add(technical);
						}else{
							List<Technical> toList=new ArrayList<Technical>();
							toList.add(technical);
							airworthiness.setTechnicalList(toList);
						}
					}
					
				}
			}
			
			for (Airworthiness airworthiness : airworthinessList) {
				airworthiness.setYjzt(WhetherEnum.NO.getId());
				if(airworthiness.getTechnicalList() !=null && airworthiness.getTechnicalList().size() > 0){
					List<Technical> techList = airworthiness.getTechnicalList();
					boolean blZt = false;
					for (Technical technical : techList) {
						if(technical.getZt() == TechnicalEnum.CHUSHI.getId() ||
						   technical.getZt() == TechnicalEnum.NOT_EVALUATED.getId() ||
						   technical.getZt() == TechnicalEnum.XIAFA.getId() ||
						   technical.getZt() == TechnicalEnum.SHENHE.getId() ||
						   technical.getZt() == TechnicalEnum.SHENHEBOHUI.getId() ||
						   technical.getZt() == TechnicalEnum.PIZHUNBOHUI.getId()){
							blZt = true;
						}
					}
					if(blZt){
						airworthiness.setYjzt(WhetherEnum.YES.getId());
					}
				}
			}
			
			
			
		}
		
		return airworthinessList;
	}
	/**
	 * 
	 * @Description 新增适航性资料
	 * @CreateTime 2017-8-15 下午6:49:11
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @throws BusinessException
	 */
	public String insert(Airworthiness airworthiness) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		String airworthinessId = UUID.randomUUID().toString();
		String zdid = airworthinessId;
		//操作指令
		LogOperationEnum cz = LogOperationEnum.SAVE_WO;
		airworthiness.setDprtcode(user.getJgdm());
		//验证唯一
		int count=airworthinessMapper.findByjswjbh(airworthiness);
		if(count > 0){
			throw new BusinessException("当前版本下编号出现重复,请修改编号或者版本后再进行操作！");
		}
		//1.添加主表信息
		airworthiness.setId(airworthinessId);
		airworthiness.setZdrid(user.getId());
		airworthiness.setZdbmid(user.getBmdm());
		airworthiness.setZdsj(new Date());
		
		//添加主表信息
		airworthinessMapper.insertSelective(airworthiness);
		//添加历史数据表
		 commonRecService.write(zdid, TableEnum.B_G2_000, user.getId(), czls, cz, UpdateTypeEnum.SAVE,airworthinessId);
		//2.添加附件从表
		if(airworthiness.getScfj() !=null){
			attachmentService.addAttachment(airworthiness.getScfj(), airworthinessId, czls, zdid, user.getJgdm(), airworthiness.getLogOperationEnum().SAVE_WO);
		}
		//判断是否需要评估，如果需要 那么添加从表数据
		if(airworthiness.getXpgbs() == AirworthinessIspgEnum.SUBMIT.getId()){
			//判断状态为保存 还是 提交
			if(airworthiness.getZt() == AirworthinessStatusEnum.NOT_EVALUATED.getId()){
				//保存状态 添加技术指令 评估指令表
				technicalfileOrderService.updateTechnicalfileOrder(airworthiness, czls, zdid, user, cz);
			}else{
				//提交状态 添加技术指令 评估指令表 评估单和写入待办事宜表
				if(airworthiness.getTechnicalfileOrderList() != null && airworthiness.getTechnicalfileOrderList().size()>0){
					this.saveApplyActype(airworthiness.getTechnicalfileOrderList(), airworthiness, czls, zdid, user, cz);
				}
			}
		}
		return airworthinessId;
	}
	
	/**
	 * 
	 * @Description 修改适航性资料
	 * @CreateTime 2017-8-15 下午6:49:24
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @throws BusinessException
	 */
	public String update(Airworthiness airworthiness) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		String airworthinessId = airworthiness.getId();
		String zdid = airworthinessId;
		List<TechnicalfileOrder> technicalfileOrderList=airworthiness.getTechnicalfileOrderList();
		//操作标识
		LogOperationEnum cz = LogOperationEnum.EDIT;
		
		//验证唯一
		int count = airworthinessMapper.findByjswjbh(airworthiness);
		if(count > 0){
			throw new BusinessException("当前版本下编号出现重复,请修改编号或者版本后再进行操作！");
		}
		//验证状态是否发生改变
		int [] iStatus = {AirworthinessStatusEnum.NOT_EVALUATED.getId()
				   	  ,AirworthinessStatusEnum.SUBMIT.getId()
				};
		boolean blAbnormal = this.verification(airworthinessId, iStatus);
		if(blAbnormal){
			throw new BusinessException("该适航性文件已更新，请刷新后再进行操作！");
		}
		//1.修改主表
		airworthiness.setZdrid(user.getId());
		airworthiness.setZdbmid(user.getBmdm());
		airworthinessMapper.updateByPrimaryKeySelective(airworthiness);
		commonRecService.write(zdid, TableEnum.B_G2_000, user.getId(), czls, airworthiness.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,airworthinessId);
		//2.修改附件上传
		//1) 判断是否有删除附件id
		if(airworthiness.getDeleteUploadId() != null && !"".equals(airworthiness.getDeleteUploadId())){
			attachmentService.deleteById(airworthiness.getDeleteUploadId(), czls, zdid, cz);
		}
		//2) 判断是否有附件id
		Attachment attachment = airworthiness.getScfj();
		if(attachment != null && attachment.getBiaoshi() != null && "edit".equals(attachment.getBiaoshi()) ){
			List<Attachment> attachmentList = attachmentService.selectListAttachmentsByPlane(airworthinessId);
			if(attachmentList != null && attachmentList.size() > 0 ){
				attachment.setId(attachmentList.get(0).getId());
				attachmentService.editAttachment(attachment, airworthinessId, czls, zdid, user.getJgdm(), cz);
			}else{
				attachmentService.addAttachment(attachment, airworthinessId, czls, zdid, user.getJgdm(), cz);
			}
		}
		//保存状态 修改技术指令 评估指令表
		if(airworthiness.getZt() == AirworthinessStatusEnum.NOT_EVALUATED.getId()){
			//判断是否需要评估，如果不评估 直接删除所有评估指令表
			if(airworthiness.getXpgbs() == AirworthinessIspgEnum.NOT_EVALUATED.getId()){
				technicalfileOrderService.deleteByMainid(airworthiness.getId(), czls, zdid, user, cz);
			}else{
				technicalfileOrderService.updateTechnicalfileOrder(airworthiness, czls, zdid, user, cz);
			}
			List<String> arids = new ArrayList<String>(); 
			arids.add(airworthiness.getId());
			List<TechnicalfileOrder> teorList=technicalfileOrderService.queryAllByMainids(arids);
			
		}else{
			//判断是否需要评估，如果不评估 直接删除所有评估指令表(s1)
			if(airworthiness.getXpgbs() == AirworthinessIspgEnum.NOT_EVALUATED.getId()){
				technicalfileOrderService.deleteByMainid(airworthiness.getId(), czls, zdid, user, cz);
			}else{
				//提交状态 修改技术指令 评估指令表，待办事宜表，待办事宜明细表
				this.saveApplyActype(technicalfileOrderList, airworthiness, czls, zdid, user, cz);
			}
		}
		return airworthiness.getId();
	}
	/**
	 * 
	 * @Description 添加适用性机型
	 * @CreateTime 2017-8-16 上午9:51:54
	 * @CreateBy 孙霁
	 * @param technicalfileOrderList
	 * @param airworthiness
	 * @param czls
	 * @param zdid
	 * @param user
	 * @return	jx
	 * @throws BusinessException
	 */
	private void saveApplyActype(List<TechnicalfileOrder> technicalfileOrderList,
			Airworthiness airworthiness, String czls, String zdid, User user, LogOperationEnum cz) throws BusinessException{
		//根据airworthinessId查询技术评估单
		List<Technical> technicalList = technicalService.selectByjswjid(airworthiness.getId());
		//根据airworthinessId查询技术评估单
		List<TechnicalfileOrder> oldTechnicalfileOrderList = technicalfileOrderMapper.queryAllByMainid(airworthiness.getId());	

		for (TechnicalfileOrder technicalfileOrder : technicalfileOrderList) {
			if(technicalfileOrder.getZt() == AirworthinessOperationEnum.ADD.getId()){
				boolean bOption = true;
				//判断是否机型已存在
				for (TechnicalfileOrder oldTechnicalfileOrder : oldTechnicalfileOrderList) {
					if(oldTechnicalfileOrder.getFjjx().equals(technicalfileOrder.getFjjx())){
						bOption = false;
					}
				}
				if(bOption){
					//1.添加适用性机型
					TechnicalfileOrder teco=technicalfileOrderService.insert(technicalfileOrder, airworthiness.getId(), czls, zdid, user, cz);
					//2.添加技术评估单
					String technicaId=technicalService.insert(teco, airworthiness, czls, zdid, user, cz);
					//3.添加待办事宜表
					String todoId = todoService.insert(technicalfileOrder.getPgrid(), airworthiness, technicalfileOrder.getFjjx(),technicaId);
					//4.写入待办事宜明细表
					todorsService.insert(todoId,technicaId);
				}
					
				
			}else if(technicalfileOrder.getZt() == AirworthinessOperationEnum.UPDATE.getId()){
				//对指令表进行修改操作
				technicalfileOrderService.update(technicalfileOrder, airworthiness.getId(), czls, zdid, user, cz);
				
				if(technicalList != null && technicalList.size()>0){
					//创建一个评估单id List（用于删除待办事宜明细）
					List<String> pgdIds = new ArrayList<String>();
					for (Technical technical : technicalList) {
						//当评估单状态不为初始状态时，修改评估期限和ata
						if(technicalfileOrder.getFjjx().equals(technical.getJx()) && technical.getZt() == TechnicalStatusEnum.INITIAL.getId()){
							//判断评估人是否相同,如果相同 修改，如果不同 删除
							if(technicalfileOrder.getPgrid().equals(technical.getPgrid())){
								//修改评估单
								technicalService.updateTechnical(technical.getId(), airworthiness.getPgqx(), airworthiness.getAta(), czls, zdid, user, cz,null,false);
							}else{
								//删除评估单
								technicalService.deleteTechnical(technical.getId());
								//删除待办事宜
								todoService.deleteByLyidAndFjjx(airworthiness.getId(), technical.getJx());
								//添加到删除list
								pgdIds.add(technical.getId());
								//添加新的评估单
								String technicaId=technicalService.insert(technicalfileOrder, airworthiness, czls, zdid, user, cz);
								//2.添加待办事宜表
								String todoId = todoService.insert(technicalfileOrder.getPgrid(), airworthiness, technicalfileOrder.getFjjx(),technicaId);
								//写入待办事宜明细表
								todorsService.insert(todoId,technicaId);
								
							}
							//当评估单状态不为初始状态时，只修改评估期限
						}else if(technicalfileOrder.getFjjx().equals(technical.getJx()) && technical.getZt() != TechnicalStatusEnum.INITIAL.getId()){
							//1.修改评估单
							if(airworthiness.getPgqx() != null){
								technicalService.updateTechnical(technical.getId(), airworthiness.getPgqx(), null, czls, zdid, user, cz, technicalfileOrder, true);
							}
							//2.修改待办事宜表
							todoService.updateByLyidAndFjjx(airworthiness, technical.getJx(),technicalfileOrder.getPgrid(),true);	
						}
					}
					//删除待办事宜明细
					if(pgdIds != null && pgdIds.size() > 0){
						todorsService.deleteYwIds(pgdIds);
					}
				}else{
					//1.添加新的评估单
					String technicaId=technicalService.insert(technicalfileOrder, airworthiness, czls, zdid, user, cz);
					//2.添加待办事宜表
					String todoId = todoService.insert(technicalfileOrder.getPgrid(), airworthiness, technicalfileOrder.getFjjx(),technicaId);
					//3.写入待办事宜明细表
					todorsService.insert(todoId,technicaId);
					
				}
				
			}
		}
	}

	/**
	 * @author sunji
	 * @description 删除
	 */
	public Map<String, Object> deleteRecord(String id) throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		
		LogOperationEnum cz = LogOperationEnum.DELETE;
		Map<String, Object> returnMap =  new HashMap<String, Object>();//返回数据map
		String czls=UUID.randomUUID().toString();
		int []iStatus = {AirworthinessStatusEnum.NOT_EVALUATED.getId()
				,AirworthinessStatusEnum.CLOSE.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			returnMap.put("state", "error");
			returnMap.put("message", "该数据已更新，请刷新后再进行操作！");
			return returnMap;
		}
		//添加历史数据表
		commonRecService.write(id, TableEnum.B_G2_000, user.getId(), czls, cz,UpdateTypeEnum.DELETE,id);
		int count = airworthinessMapper.deleteByPrimaryKey(id);
		if(count > 0){
			returnMap.put("state", "success");
			returnMap.put("message", "删除成功！");
		}
		//删除从表数据
		technicalfileOrderService.deleteByMainid(id, czls, id, user, cz);
		return returnMap;
	}
	/**
	 * 
	 * @Description 根据id查询数据（包括机型）
	 * @CreateTime 2017-8-18 下午2:52:53
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public Airworthiness selectById(String id) throws BusinessException {
		Airworthiness airworthiness = airworthinessMapper.selectById(id);
		if(airworthiness != null && null != airworthiness.getScfj()){
			airworthiness.getScfj().setHzm("."+airworthiness.getScfj().getHzm());
		}
		return airworthiness;
	}
	/**
	 * 
	 * @Description 验证状态是否改变
	 * @CreateTime 2017-8-17 下午2:07:46
	 * @CreateBy 孙霁
	 * @param id
	 * @param iStatus
	 * @return
	 */
	private boolean verification(String id, int [] iStatus){
		boolean blAbnormal=true;
		Airworthiness airworthiness=airworthinessMapper.selectByPrimaryKey(id);
		for (int i = 0; i < iStatus.length; i++) {
			if(airworthiness != null && airworthiness.getZt() == iStatus[i]){
				blAbnormal = false;
			}
		}
		
		return blAbnormal;
	}
	
	/**
	 * 
	 * @Description 根据适航性资料id查询适航性资料数据 
	 * @CreateTime 2017年8月21日 下午4:38:49
	 * @CreateBy 林龙
	 * @param airworthiness 适航性资料
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Airworthiness getByAirworthinessId(Airworthiness airworthiness) {
		return airworthinessMapper.selectByPrimaryKey(airworthiness.getId());
	}
	/**
	 * 
	 * @Description 适航性资料监控列表
	 * @CreateTime 2017年10月11日 上午10:27:25
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryList(BaseEntity baseEntity) throws BusinessException {
		PageHelper.startPage(baseEntity.getPagination());
		List<Map<String, Object>> list = airworthinessMapper.selectAll(baseEntity);
		return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
	}
	/**
	 * 
	 * @Description 适航性资料监控关闭
	 * @CreateTime 2017年10月11日 下午3:30:38
	 * @CreateBy 岳彬彬
	 * @param airworthiness
	 * @throws BusinessException
	 */
	@Override
	public void update4Close(Airworthiness airworthiness) throws BusinessException {
		//验证适航性资料监控状态是否为0
		validationMonitor(airworthiness.getId());
		User user = ThreadVarUtil.getUser();
		airworthiness.setGbrid(user.getId());
		String czls = UUID.randomUUID().toString();
		airworthinessMapper.update4Close(airworthiness);
		commonRecService.write(airworthiness.getId(), TableEnum.B_G2_000, user.getId(), czls,  LogOperationEnum.WANCHEN,UpdateTypeEnum.UPDATE, airworthiness.getId());
	}
	/**
	 * 
	 * @Description 验证适航性资料监控状态是否为0未关闭
	 * @CreateTime 2017年10月11日 下午3:30:54
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	private void validationMonitor(String id) throws BusinessException{
		Airworthiness air = airworthinessMapper.selectByPrimaryKey(id);
		if(air == null || air.getJkzt() != AirworthinessMonitorStatusEnum.Not.getId()){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}
	@Override
	public List<Airworthiness> getAirworthinessList(Airworthiness airworthiness)
			throws BusinessException {
		if(airworthiness.getWjlxList() != null && airworthiness.getWjlxList().size() > 0 && airworthiness.getWjlxList().get(0) != "" ){
			airworthiness.getParamsMap().put("wjlxList", airworthiness.getWjlxList());
		}
		if(airworthiness.getXpgbsList() != null && airworthiness.getXpgbsList().size() > 0 && airworthiness.getXpgbsList().get(0) != "" ){
			airworthiness.getParamsMap().put("xpgbsList", airworthiness.getXpgbsList());
		}
		
		List<Airworthiness> list=airworthinessMapper.queryAll(airworthiness);
		this.gettechnicalfileorder(list);
		if(list != null && list.size() > 0 ){
			StringBuffer str;
			for (Airworthiness air : list) {
				if(air.getTechnicalfileOrderList() != null && air.getTechnicalfileOrderList().size() > 0){
					str = new StringBuffer();
					for (TechnicalfileOrder technicalfileOrder : air.getTechnicalfileOrderList()) {
						str.append(technicalfileOrder.getFjjx()).append(",");
					}
					if(str != null ){
						air.getParamsMap().put("jxList", str.toString().substring(0,str.toString().length() - 1));
					}
				}
				air.getParamsMap().put("ztText", AirworthinessStatusEnum.getName(air.getZt()));
				
			}
			
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> exportCadList(BaseEntity baseEntity)
			throws BusinessException {
		Pagination  page=new Pagination();
		page.setEnd(0);
		page.setOrder("desc");
		page.setPage(1);
		page.setRows(100000);
		page.setSort("auto");
		page.setStart(0);
		baseEntity.setPagination(page);
		PageHelper.startPage(baseEntity.getPagination());
		List<Map<String, Object>> list = airworthinessMapper.selectAll(baseEntity);
        if(list!=null&&list.size()>0){
     	     for(Map<String, Object> map:list){
     			StringBuilder  scValues=new StringBuilder();
     			StringBuilder  zqValues=new StringBuilder();
     			StringBuilder  sczxzValues=new StringBuilder();
     			StringBuilder  xcjhzValues=new StringBuilder(); 
     	    	 String lx=null;
     	    	 String scValue=null;
     	    	 String sc_dw=null;
     	    	 String zqValue=null;
     	    	 String xcjhzValue=null;
     	    	 String sczxzValue=null;
     	    	    for(String key:map.keySet()){
     	    	    	if("JHSJSJ".equals(key)){
     	    	    		String values=(String) map.get(key);
     	    	    		if(values!=null){
     	    	    			String [] ars=values.split(",");
     	    	    			for(String atr:ars){
     	    	    				String [] strs=atr.split("#_#",-1);
     	    	    				lx=strs[0];
     	    	    				scValue=strs[2];
     	    	    				sc_dw=strs[3];
     	    	    				zqValue=strs[4];
     	    	    				xcjhzValue=strs[1];
     	    	    				if(isTime(lx)){
     	    	    					String scTimeValue=convertToHour(scValue,":");
     	    	    					String zqTimeValue=convertToHour(zqValue,":");
     	    	    					String xcjhzTimeValue=convertToHour(xcjhzValue,":");
     	    	    					scValue=(StringUtils.isBlank(scValue)?"":((scTimeValue==null||"0".equals(scTimeValue.trim())||"".equals(scTimeValue.trim()))?"":scTimeValue+getUnit(lx)));
     	    	    					zqValue=(StringUtils.isBlank(zqValue)?"":((zqTimeValue==null||"0".equals(zqTimeValue.trim())||"".equals(zqTimeValue.trim()))?"":zqTimeValue+getUnit(lx)));
     	    	    					xcjhzValue=(StringUtils.isBlank(xcjhzValue)?"":((xcjhzTimeValue==null||"0".equals(xcjhzTimeValue.trim())||"".equals(xcjhzTimeValue.trim()))?"":xcjhzTimeValue+getUnit(lx)));
     	    	    				}else if(isCal(lx)){
     	    	    					scValue=(StringUtils.isBlank(scValue)?"":scValue+getSubitem(lx,sc_dw));
     	    	    					zqValue=((StringUtils.isBlank(zqValue))?"":zqValue+getSubitem(lx,sc_dw));
     	    	    					xcjhzValue=(StringUtils.isBlank(xcjhzValue)?"":xcjhzValue+getSubitem(lx,sc_dw));
     	    	    				}else if(isLoop(lx)){
     	    	    				   scValue=(StringUtils.isBlank(scValue)?"":scValue+getUnit(lx));
     	    	    				   zqValue=(StringUtils.isBlank(zqValue)?"":zqValue+getUnit(lx));
     	    	    				  xcjhzValue=(StringUtils.isBlank(xcjhzValue)?"":xcjhzValue+getUnit(lx));
     	    	    				}
     	    	    			    scValues.append(scValue).append(" ");
     	    	     	    	    zqValues.append(zqValue).append(" ");
     	    	     	    	    xcjhzValues.append(xcjhzValue).append(" ");
     	    	     	    	         	    
     	    	    			}
    	    	    		
     	    	    		}
     	    	    		
     	    	    	}
					if ("SCSJ".equals(key)) {
						String values = (String) map.get(key);
						if (values != null) {
							String[] ars = values.split(",");
							for (String str : ars) {
								String[] strs = str.split("#_#",-1);
								String lxs = strs[0];
								String sc_value = strs[1];
								if (isTime(lxs)) {
									String scTimeValue=convertToHour(sc_value, ":");
									sc_value = (sc_value == null ? ""
											: (scTimeValue==null||"0".equals(scTimeValue.trim())||"".equals(scTimeValue.trim()))?"":
												scTimeValue+ getUnit(lxs));
								} else if (isCal(lxs)) {
									sc_value = (sc_value == null ? ""
											: sc_value + getSubitem(lxs, sc_dw));
								} else if (isLoop(lxs)) {
									sc_value = (sc_value == null ? ""
											: sc_value + getUnit(lxs));
								}
								sczxzValues.append(sc_value).append(" ");

							}

						}

					}

     	    	    }	     	    	   
	     	    	   map.put("scValues", scValues.toString());
	     	    	   map.put("zqValues",zqValues.toString());
	     	    	   map.put("xcjhzValues", xcjhzValues.toString());
	     	    	   map.put("sczxzValues", sczxzValues);
     	     }
     	   
     	     
        }
		return list;
	} 
	
	private Boolean isTime(String jklbh) {
		return "2_20_AH".equals(jklbh) || "2_10_FH".equals(jklbh)
				|| "2_30_EH".equals(jklbh);
	}

	private Boolean isLoop(String jklbh) {
		return "3_10_FC".equals(jklbh) || "3_20_AC".equals(jklbh)
				|| "3_30_EC".equals(jklbh);
	}

	private Boolean isCal(String jklbh) {
		return "1_10".equals(jklbh);
	}
	private String convertToHour(String minutes,String separtor){
		 if(!StringUtils.isNumeric(minutes)||minutes.length()==0)
			  return null;
		 Integer minute=Integer.valueOf(minutes);
		if (minute == null ||minute<= 0)
			return null;
		int hour = minute / 60;
		int min = minute % 60;
		if (min == 0)
			return  String.valueOf(hour);
		return hour + separtor + min;		
	}
	private String getUnit(String jklbh){
		if ("1_10".equals(jklbh))
			return "";
		if ("2_10_FH".equals(jklbh))
			return "FH";
		if ("2_20_AH".equals(jklbh))
			return "APUH";
		if ("2_30_EH".equals(jklbh))
			return "EH";
		if ("3_10_FC".equals(jklbh))
			return "FC";
		if ("3_20_AC".equals(jklbh))
			return "APUC";
		if ("3_30_EC".equals(jklbh))
			return "EC";
		return null;
	}
	
	private String getSubitem(String lx,String sc_dw){
		String value="";
		Map<String,String> map=new HashMap<String, String>();
		map.put("11","D");
		map.put("12","M");
		
		  if("1_10".equals(lx)){
		      value=(map.get(sc_dw)==null?"":map.get(sc_dw));		  
		  }
		return value;
	}
	/**
	 * 
	 * @Description AD适航性资料监控列表
	 * @CreateTime 2018-4-2 下午2:14:29
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllAd(Airworthiness airworthiness)
			throws BusinessException {
		PageHelper.startPage(airworthiness.getPagination());
		List<Airworthiness> list = airworthinessMapper.queryAllAd(airworthiness);
		return PageUtil.pack4PageHelper(list, airworthiness.getPagination());
	}
	/**
	 * 
	 * @Description 关闭技术文件监控
	 * @CreateTime 2018-4-3 上午10:36:24
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void closeMonitoring(String id, Integer zt) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		//验证
		//技术文件下所有的评估单中的b_g2_00102表中监控状态全部为9（包含可见/不可见）
		if(zt == 9){
			int i = airworthinessMapper.selectByBg200102Count(id);
			if(i > 0){
				throw new BusinessException("该评估单下还有未关闭的监控数据，请确保全部关闭后再进行操作!");
			}
		}
		Airworthiness airworthiness = new Airworthiness();
		airworthiness.setId(id);
		airworthiness.setJkzt(zt);
		airworthiness.setGbrid(user.getId());
		airworthiness.setGbrq(new Date());
		airworthinessMapper.updateByPrimaryKeySelective(airworthiness);
		commonRecService.write(id, TableEnum.B_G2_000, user.getId(), czls, airworthiness.getLogOperationEnum().Close, UpdateTypeEnum.UPDATE,id);
	}
	/**
	 * 
	 * @Description 关闭适用性监控
	 * @CreateTime 2018-4-3 上午10:36:29
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void closeApplyMonitoring(TEApplicability tEApplicability) throws BusinessException {
		//标记成不关闭     此条评估单对应的技术文件的监控状态=0初始，才可以，否则不容许这样操作
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		int i = 0;
		if(tEApplicability.getJkzt() == 0 ){	
			 i = airworthinessMapper.selectByJkeq0(tEApplicability.getParamsMap().get("id").toString());
		}
		if(i > 0){
			throw new BusinessException("技术文件状态为关闭，不可开启！");
		}
		if(tEApplicability.getId() != null && !"".equals(tEApplicability.getId())){
			TEApplicability te =new TEApplicability();
			te.setId(tEApplicability.getId());
			te.setZt(tEApplicability.getZt());
			te.setJkbz(tEApplicability.getJkbz());
			te.setJkzt(tEApplicability.getJkzt());
			tEApplicabilityMapper.updateByPrimaryKeySelective(te);
			commonRecService.write(tEApplicability.getId(), TableEnum.B_G2_000, user.getId(), czls, LogOperationEnum.Close, UpdateTypeEnum.UPDATE,tEApplicability.getId());
		}else{
			String id = UUID.randomUUID().toString();
			tEApplicability.setId(id);
			tEApplicability.setMainid(tEApplicability.getParamsMap().get("pgdid").toString());
			tEApplicability.setZt(1);
			tEApplicability.setWhdwid(user.getBmdm());
			tEApplicability.setWhrid(user.getId());
			tEApplicability.setWhsj(new Date());
			tEApplicability.setKjbs(0);
			tEApplicabilityMapper.insertSelective(tEApplicability);
			commonRecService.write(id, TableEnum.B_G2_000, user.getId(), czls, LogOperationEnum.Close, UpdateTypeEnum.UPDATE,id);
			
		}
		
		
	}
	/**
	 * 
	 * @Description 获取Ad导出数据
	 * @CreateTime 2018-4-12 下午2:23:16
	 * @CreateBy 孙霁
	 * @param airworthiness
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Airworthiness> exportAdList(Airworthiness airworthiness)
			throws BusinessException {
		Pagination  page=new Pagination();
		page.setEnd(0);
		page.setOrder("desc");
		page.setPage(1);
		page.setRows(100000);
		page.setSort("auto");
		page.setStart(0);
		airworthiness.setPagination(page);
		PageHelper.startPage(airworthiness.getPagination());
		//获取数据
		List<Airworthiness> list = airworthinessMapper.queryAllAd(airworthiness);
		//用于拼接字符串
		StringBuffer str = new StringBuffer();
		for (Airworthiness air : list) {
			//技术文件关闭状态
			if(Integer.valueOf(air.getParamsMap().get("jswjjkzt").toString()) == 9){
				air.getParamsMap().put("jswjgbztText","关闭");
			}else{
				air.getParamsMap().put("jswjgbztText","开启");
			}
			//关闭状态
			if(air.getParamsMap().get("fjbj") != null && !" ".equals(air.getParamsMap().get("fjbj").toString())){
				if(air.getParamsMap().get("syxjkzt") != null && Integer.valueOf(air.getParamsMap().get("syxjkzt").toString()) == 9){
					air.getParamsMap().put("gbztText","关闭");
				}else{
					air.getParamsMap().put("gbztText","开启");
				}
			}
			//关联文件
			str.setLength(0);
			if(air.getParamsMap().get("gl_jswjlx") != null && !"".equals(air.getParamsMap().get("gl_jswjlx").toString()) ){
				str.append(air.getParamsMap().get("gl_jswjlx"));
			}
			if(air.getParamsMap().get("gl_jswjbh") != null && !"".equals(air.getParamsMap().get("gl_jswjbh").toString()) ){
				str.append("/");
				str.append(air.getParamsMap().get("gl_jswjbh"));
			}
			if(air.getParamsMap().get("gl_xzah") != null && !"".equals(air.getParamsMap().get("gl_xzah").toString()) ){
				str.append("/");
				str.append(air.getParamsMap().get("gl_xzah"));
			}
			if(air.getParamsMap().get("gl_jswjzt") != null && !"".equals(air.getParamsMap().get("gl_jswjzt").toString()) ){
				str.append("/");
				str.append(air.getParamsMap().get("gl_jswjzt"));
			}
			if(air.getParamsMap().get("gl_sxrq") != null && !"".equals(air.getParamsMap().get("gl_sxrq").toString()) ){
				str.append("/");
				str.append(air.getParamsMap().get("gl_sxrq").toString().substring(0,10));
			}
			air.getParamsMap().put("glwjText", str.toString());
			//下达指令类型
			str.setLength(0);
			if(air.getParamsMap().get("zllx") != null && !"".equals( air.getParamsMap().get("zllx"))){
				String[] zllxList = air.getParamsMap().get("zllx").toString().split(",");
				for (String zlxl : zllxList) {
					str.append(SendOrderEnum.getName(Integer.valueOf(zlxl))).append(",");
				}
				air.getParamsMap().put("xdzllxText",str.substring(0, str.length()-1));
			}else{
				air.getParamsMap().put("xdzllxText","");
			}
			//上传执行
			if(air.getParamsMap().get("sczx") != null && !"".equals(air.getParamsMap().get("sczx"))){
				air.getParamsMap().put("sczxText",formatLastData(air.getParamsMap().get("sczx").toString()));
			}
			//下次到期
			if(air.getParamsMap().get("xczx") != null && !"".equals(air.getParamsMap().get("xczx"))){
				air.getParamsMap().put("xczxText",formatLastData(air.getParamsMap().get("xczx").toString()));
			}
			//执行记录
			str.setLength(0);
			if(air.getParamsMap().get("zxjl") != null && !"".equals( air.getParamsMap().get("zxjl"))){
				String[] arr1 = air.getParamsMap().get("zxjl").toString().split(",");
				for (String zxjl : arr1) {
					String[] arr2 = zxjl.split("#_#");
					str.append(arr2[1]).append("\n");
				}
				air.getParamsMap().put("zxjlText",str.toString());
			}else{
				air.getParamsMap().put("zxjlText","");
			}
			
		}
		return list;
	}
	/**
	 * @Description 格式化上次数据
	 * @CreateTime 2017-12-19 下午4:13:28
	 * @CreateBy 刘兵
	 * @param monitoringCurrent 监控数据
	 * @throws BusinessException
	 */
	private String formatLastData(String data){
			StringBuffer str = new StringBuffer();
			String[] arr1 = data.split(",");
			for (int i  = 0 ; i < arr1.length ; i++) {
				String[] arr2 = arr1[i].split("#_#", -1);
				str.append(formatJkz(arr2[0], arr2[1], " ", "", true));
				if(i != arr1.length - 1){
					str.append("\n");
				}
			}
			return str.toString();
	}
	
	/**
	 * @Description 格式化监控值
	 * @CreateTime 2017-12-20 上午9:50:09
	 * @CreateBy 刘兵
	 * @param jklbh 监控类编号
	 * @param value 监控值
	 * @param backupValue 备用值
	 * @param dw 单位
	 * @param addDw 是否增加单位
	 * @return 监控值
	 */
	private String formatJkz(String jklbh, String value, String backupValue, String dw, boolean addDw){
		StringBuffer jkz = new StringBuffer();
		if(StringUtils.isNotBlank(value)){
			//判断是否是时间格式
			if(MonitorProjectEnum.isTime(jklbh)){
				value = StringAndDate_Util.convertToHour(value);
			}
		}else{
			value = backupValue;
		}
		jkz.append(value);
		if(addDw && !" ".equals(value)){
			jkz.append(MonitorProjectEnum.getUnit(jklbh, dw));
		}
		return jkz.toString();
	}
}
