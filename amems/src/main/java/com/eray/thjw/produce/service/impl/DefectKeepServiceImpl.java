	package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.DefectKeepMapper;
import com.eray.thjw.produce.po.DefectKeep;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.service.DefectKeepService;
import com.eray.thjw.produce.service.WorkOrderNew145Service;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.project2.dao.TodoMapper;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.produce.FailureKeepStatusEnum;
import enu.produce.NodeEnum;
import enu.produce.UndoEnum;
import enu.produce.UndoStatusEnum;
import enu.produce.WorkorderStatusEnum;
import enu.project2.ProjectBusinessEnum;
/**
 * 
 * @Description 缺陷保留serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("defectKeepService")
public class DefectKeepServiceImpl implements DefectKeepService  {

	@Resource
	private DefectKeepMapper defectKeepMapper;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private MaterialToolService materialToolService;
	
	@Resource
	private WorkOrderNewService workOrderNewService;
	
	@Resource
	private WorkOrderNew145Service workOrderNew145Service;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private TodorsService TodorsService;

	/**
	 * @Description 缺陷保留分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(DefectKeep defectKeep)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = defectKeep.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		defectKeep.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		defectKeep.getParamsMap().put("userId", user.getId());
		defectKeep.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(defectKeep.getPagination());
		List<DefectKeep> list = defectKeepMapper.queryAllPageList(defectKeep);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					DefectKeep param = new DefectKeep();
					param.setId(id);
					List<DefectKeep> newRecordList = defectKeepMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, defectKeep.getPagination());
		}else{
			List<DefectKeep> newRecordList = new ArrayList<DefectKeep>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				DefectKeep param = new DefectKeep();
				param.setId(id);
				newRecordList = defectKeepMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, defectKeep.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, defectKeep.getPagination());
		}
	}
	
	/**
	 * 
	 * @Description 缺陷保留监控列表分页数据
	 * @CreateTime 2017年10月31日 下午1:47:05
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryMonitorAllPageList(DefectKeep defectKeep) throws BusinessException {
		//用户刚编辑过的记录 ID
				String id = defectKeep.getId();
				//清除查询条件中的ID，保证列表查询结果集的正确性
				defectKeep.setId(null);
				User user = ThreadVarUtil.getUser();//当前登陆人
				defectKeep.getParamsMap().put("userId", user.getId());
				defectKeep.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
				PageHelper.startPage(defectKeep.getPagination());
				List<DefectKeep> list = defectKeepMapper.queryMonitorAllPageList(defectKeep);
				if(((Page)list).getTotal() > 0){
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						if(!PageUtil.hasRecord(list, id)){
							DefectKeep param = new DefectKeep();
							param.setId(id);
							List<DefectKeep> newRecordList = defectKeepMapper.queryMonitorAllPageList(param);
							if(newRecordList != null && newRecordList.size() == 1){
								list.add(0, newRecordList.get(0));
							}
						}
					}
					return PageUtil.pack4PageHelper(list, defectKeep.getPagination());
				}else{
					List<DefectKeep> newRecordList = new ArrayList<DefectKeep>(1);
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						DefectKeep param = new DefectKeep();
						param.setId(id);
						newRecordList = defectKeepMapper.queryMonitorAllPageList(param);
						if(newRecordList != null && newRecordList.size() == 1){
							return PageUtil.pack(1, newRecordList, defectKeep.getPagination());
						}
					}
					return PageUtil.pack(0, newRecordList, defectKeep.getPagination());
				}
	}
	
	/**
	 * @Description 保存/提交 缺陷保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 缺陷保留
	 * @return 缺陷保留id
	 * @throws BusinessException
	 */
	@Override
	public String save(DefectKeep defectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//新增缺陷保留 
		insertSelective(defectKeep,uuid,czls,user);
		
		//新增附件
		attachmentService.eidtAttachment(defectKeep.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		if(FailureKeepStatusEnum.ASSESSMENT.getId().equals(defectKeep.getZt())){
			//写入待办触发事件
			StringBuilder builder=new StringBuilder();
			builder.append("请审批 ").append(defectKeep.getFjzch()).append("飞机的").append(defectKeep.getBldh()).append("缺陷保留");
			TodorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), defectKeep.getId(), 
					defectKeep.getBldh(), null, defectKeep.getFjzch(), UndoEnum.QXBL.getId(), NodeEnum.NODE3.getId(), 
					builder.toString(), null, null, "produce:reservation:defect:main:04", defectKeep.getId(), null);
			
			TodorsService.updateToDo(defectKeep.getId(), NodeEnum.NODE5.getId(), UndoStatusEnum.DCL.getId(), user.getId());
		}
		

		
		return uuid;
	}
	/**
	 * 
	 * @Description 新增缺陷保留
	 * @CreateTime 2017年9月27日 下午1:43:30
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @param uuid 缺陷保留id
	 * @param czls 流水号
	 * @param user 当前登录人
	 */
	private void insertSelective(DefectKeep defectKeep, String uuid,String czls,User user)throws BusinessException {
		
		// 验证飞机是否存在
		this.validationFjzch(defectKeep,user);
		
		//当页面没有填写缺陷保留编号时
		if(defectKeep.getBldh() == null || "".equals(defectKeep.getBldh())){ 
			String bldh=setBldh(defectKeep,user); //根据缺陷获取最新缺陷保留单号
			defectKeep.setBldh(bldh);
		}else{
			//验证验证缺陷保留单编号 唯一
			validationDefectKeep(defectKeep);
		}
		defectKeep.setDprtcode(user.getJgdm());         //组织机构
		defectKeep.setId(uuid);                         //id
		defectKeep.setWhdwid(user.getBmdm());	        //部门id
		defectKeep.setWhrid(user.getId());		        //用户id
		
		//新增缺陷保留
		defectKeepMapper.insertSelective(defectKeep);
		//添加新增日志
		commonRecService.write(uuid, TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid);
		
	}
	
	/**
	 * 
	 * @Description 验证飞机权限
	 * @CreateTime 2017年9月25日 下午3:04:16
	 * @CreateBy 林龙
	 * @param record
	 * @param user
	 * @throws BusinessException
	 */
	private void validationFjzch(DefectKeep defectKeep, User user) throws BusinessException {
		List<String> fjzchList = new ArrayList<String>();
		fjzchList.add(defectKeep.getFjzch());
		planeModelDataService.existsAircraft4145Expt(user.getId(), user.getUserType(), defectKeep.getDprtcode(), fjzchList);
	}
	/**
	 * 
	 * @Description 根据缺陷保留单对象获取最新缺陷保留单编号
	 * @CreateTime 2017年9月27日 下午1:48:51
	 * @CreateBy 林龙
	 * @param defectKeep  缺陷保留对象
	 * @param user 当前用户
	 * @return
	 * @throws BusinessException 
	 */
	private String setBldh(DefectKeep defectKeep,User user) throws BusinessException {
		DefectKeep fai = new DefectKeep(); 		  	//new 缺陷保留
		boolean b = true;
		String bldh = null;
		while(b){
			try {
				bldh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.QXBLD.getName(),defectKeep);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			fai.setBldh(bldh);
			fai.setDprtcode(user.getJgdm());
			//根据技术文件对象查询缺陷保留数量
			int i = defectKeepMapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return bldh;
	}

	/**
	 * 
	 * @Description //验证验证缺陷保留单编号 唯一
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 */
	private void validationDefectKeep(DefectKeep defectKeep)throws BusinessException {
		int  iNum = defectKeepMapper.queryCount(defectKeep);
		if (iNum > 0) {
			throw new BusinessException("该缺陷保留单编号已存在!");
		}
		
	}

	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 */
	@Override
	public void delete(DefectKeep defectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		// 验证单据状态是否已变更
		validation4CurrentZt(defectKeep.getId(), Integer.valueOf((String) defectKeep.getParamsMap().get("currentZt")));
		
		//添加新增日志
		commonRecService.write(defectKeep.getId(), TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,defectKeep.getId());
		
		//删除缺陷保留
		defectKeepMapper.deleteByPrimaryKey(defectKeep.getId());
		
		//删除待办
		TodorsService.deleteToDo(defectKeep.getId(), null, null);
	}

	/**
	 * 
	 * @Description 根据缺陷保留id查询缺陷保留信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public DefectKeep getInfoById(DefectKeep defectKeep)throws BusinessException {
		return defectKeepMapper.getInfoById(defectKeep);
	}
	
	/**
	 * 
	 * @Description 修改保存缺陷保留
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 缺陷保留id
	 * @throws BusinessException
	 */
	@Override
	public String update(DefectKeep defectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//修改缺陷保留
		updateByPrimaryKeySelective(defectKeep,czls,user);
		
		//修改附件
		attachmentService.eidtAttachment(defectKeep.getAttachmentList(), defectKeep.getId(), czls, defectKeep.getId(), defectKeep.getDprtcode(), LogOperationEnum.EDIT);
		
		
		if(FailureKeepStatusEnum.ASSESSMENT.getId().equals(defectKeep.getZt())){
			//写入待办触发事件
			
			StringBuilder builder=new StringBuilder();
			builder.append("请审批 ").append(defectKeep.getFjzch()).append("飞机的").append(defectKeep.getBldh()).append("缺陷保留");
			TodorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), defectKeep.getId(), 
					defectKeep.getBldh(), null, defectKeep.getFjzch(), UndoEnum.QXBL.getId(), NodeEnum.NODE3.getId(), 
					builder.toString(), null, null, "produce:reservation:defect:main:04", defectKeep.getId(), null);			
			TodorsService.updateToDo(defectKeep.getId(), NodeEnum.NODE5.getId(), UndoStatusEnum.DCL.getId(), user.getId());			
		}
		
		
		return defectKeep.getId();
	}

	/**
	 * 
	 * @Description 修改缺陷保留
	 * @CreateTime 2017年9月27日 下午4:40:24
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @param czls 流水号
	 */
	private void updateByPrimaryKeySelective(DefectKeep defectKeep,String czls,User user)throws BusinessException {
		
		// 验证飞机是否存在
		this.validationFjzch(defectKeep,user);
		// 验证145是否存在
		this.validation145(defectKeep);
		
		// 验证单据状态是否已变更
		validation4CurrentZt(defectKeep.getId(), Integer.valueOf((String) defectKeep.getParamsMap().get("currentZt")));
		
		defectKeep.setWhdwid(user.getBmdm()); //维护部门
		defectKeep.setWhrid(user.getId());   //维护人
		defectKeepMapper.updateByPrimaryKeySelective(defectKeep);
		//添加修改日志
		commonRecService.write(defectKeep.getId(), TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,defectKeep.getId());
	}

	/**
	 * 
	 * @Description 验证145是否存在
	 * @CreateTime 2018年4月8日 上午10:58:45
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	private void validation145(DefectKeep defectKeep) throws BusinessException{
		DefectKeep defectKeepnew = defectKeepMapper.selectByPrimaryKey(defectKeep.getId());
		if(defectKeepnew.getBs145() != null){
			if (defectKeepnew.getBs145() == 0 && defectKeep.getBs145() == 1) {
				throw new BusinessException("您没有135工单选择权限!");
			}
			if (defectKeepnew.getBs145() == 1 && defectKeep.getBs145() == 0) {
				throw new BusinessException("您没有145工单选择权限!");
			}
			
		}
		
	}

	/**
	 * 
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer zt)throws BusinessException {
		int bzt = defectKeepMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}
	
	/**
	 * 
	 * @Description 批准缺陷保留单
	 * @CreateTime 2017年9月28日 上午11:01:01
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String approval(DefectKeep defectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();		   //流水号
		User user = ThreadVarUtil.getUser();			   //当前登陆人
		// 验证飞机是否存在
		this.validationFjzch(defectKeep,user);
		// 验证单据状态是否已变更
		this.validation4CurrentZt(defectKeep.getId(), Integer.valueOf((String) defectKeep.getParamsMap().get("currentZt")));
		
		defectKeep.setPzrbmid(user.getBmdm());			   //批复部门id
		defectKeep.setPzrid(user.getId());				   //批复人id
		defectKeep.setPzsj(commonService.getSysdate());    //批准时间
		
		defectKeepMapper.updateByPrimaryKeySelective(defectKeep);
		
		if(defectKeep.getZt().equals(FailureKeepStatusEnum.APPROVAL.getId())){
			//保存器材/工具
			materialToolService.saveMaterialToolList(defectKeep.getMaterialToolList(), ProjectBusinessEnum.DEFECTEKEEP.getId(), defectKeep.getId(), czls, defectKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
		}
		
		//添加技术评估单已批准日志
		commonRecService.write(defectKeep.getId(), TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE,defectKeep.getId());
		 
			
		 if(FailureKeepStatusEnum.APPROVALDOWN.getId().equals(defectKeep.getZt())){//批准驳回写入待办事宜
			//写入待办触发事件
			 StringBuilder builder=new StringBuilder();
			 builder.append(defectKeep.getFjzch()).append("飞机的").append(defectKeep.getBldh()).append("缺陷保留已经驳回,请重新提交");
			TodorsService.addToDo(user.getJgdm(), EffectiveEnum.YES.getId(), UndoStatusEnum.DCL.getId(), defectKeep.getId(), 
					defectKeep.getBldh(), null, defectKeep.getFjzch(), UndoEnum.QXBL.getId(), NodeEnum.NODE5.getId(), 
					builder.toString(), null, defectKeep.getSqrid(), "produce:reservation:defect:main:02", defectKeep.getId(), null);
			
			TodorsService.updateToDo(defectKeep.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());

		}
		if(FailureKeepStatusEnum.APPROVAL.getId().equals(defectKeep.getZt())){//批准通过写入待办事宜
			TodorsService.updateToDo(defectKeep.getId(), NodeEnum.NODE3.getId(), UndoStatusEnum.DCL.getId(), user.getId());
		}		
		return defectKeep.getId();
	}

	/**
	 * 
	 * @Description  再次保留缺陷保留
	 * @CreateTime 2017年9月28日 下午4:51:25
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留
	 * @return 缺陷保留id
	 */
	@Override
	public String saveagainkeep(DefectKeep defectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		// 验证单据状态是否已变更
		validation4CurrentZt(defectKeep.getId(), Integer.valueOf((String) defectKeep.getParamsMap().get("currentZt")));
		
		defectKeep.setWhrid(user.getBmdm()); 		//维护部门
		defectKeep.setWhrid(user.getId());   		//维护人
		defectKeepMapper.updateByPrimaryKeySelective(defectKeep);
		//添加修改日志
		commonRecService.write(defectKeep.getId(), TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,defectKeep.getId());
		
		return defectKeep.getId();
	}

	/**
	 * 
	 * @Description 完成缺陷保留
	 * @CreateTime 2017年9月29日 上午10:07:23
	 * @CreateBy 林龙
	 * @param defectKeep 缺陷保留对象
	 * @throws BusinessException
	 */
	@Override
	public String updateEndModal(DefectKeep defectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) defectKeep.getParamsMap().get("currentZt"));
		// 验证单据状态是否已变更
		validation4CurrentZt(defectKeep.getId(), iCurrentZt);
		
		DefectKeep defectKeepNew = defectKeepMapper.selectByPrimaryKey(defectKeep.getId());
		if(defectKeepNew.getGdid() != null){//当工单id不为null时判断工单状态是否是已关闭/指定结束
			if(defectKeepNew.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = workOrderNew145Service.selectByPrimaryKey(defectKeepNew.getGdid());
				if(workorder145!=null){
					if(workorder145.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder145.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可关闭缺陷保留!");
					}
				}
			}else{
				Workorder workorder=workOrderNewService.selectByPrimaryKey(defectKeepNew.getGdid());
				if(workorder != null){
					if(workorder.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可关闭缺陷保留!");
					}
				}
			}
		}
		defectKeep.setZt(FailureKeepStatusEnum.WANCHENG.getId()); //状态为指定结束
		
		defectKeep.setGbrid(user.getId());
		defectKeep.setGbrbmid(user.getBmdm());
		defectKeep.setGbsj(commonService.getSysdate());
		//完成
		defectKeepMapper.updateByPrimaryKeySelective(defectKeep);
		
		//添加新增日志
		commonRecService.write(defectKeep.getId(), TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.WANCHEN, UpdateTypeEnum.UPDATE,defectKeep.getId());
	
		//新增附件
		attachmentService.eidtAttachment(defectKeep.getAttachmentList(), defectKeep.getId(), czls, defectKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
	
		return defectKeep.getId();
	}

	/**
	 * 
	 * @Description 指定结束缺陷保留
	 * @CreateTime 2017年9月29日 上午11:42:15
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @throws BusinessException
	 */
	@Override
	public String updategConfirm(DefectKeep defectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) defectKeep.getParamsMap().get("currentZt"));
		// 验证单据状态是否已变更
		validation4CurrentZt(defectKeep.getId(), iCurrentZt);
		
		DefectKeep defectKeepNew = defectKeepMapper.selectByPrimaryKey(defectKeep.getId());
		if(defectKeepNew.getGdid() != null){//当工单id不为null时判断工单状态是否是已关闭/指定结束
			if(defectKeepNew.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = workOrderNew145Service.selectByPrimaryKey(defectKeepNew.getGdid());
				if(workorder145!=null){
					if(workorder145.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder145.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可指定结束缺陷保留!");
					}
				}
			}else{
				Workorder workorder = workOrderNewService.selectByPrimaryKey(defectKeepNew.getGdid());
				if(workorder!=null){
					if(workorder.getZt() != WorkorderStatusEnum.CLOSETOEND.getId() && workorder.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
						throw new BusinessException("下发NRC状态未指定结束或者关闭，不可指定结束缺陷保留!");
					}
				}
			}
		}
		
		defectKeep.setZt(FailureKeepStatusEnum.CLOSE.getId()); //状态为指定结束
		defectKeep.setGbrid(user.getId());
		defectKeep.setGbrbmid(user.getBmdm());
		defectKeep.setGbsj(commonService.getSysdate());
		
		//指定结束
		defectKeepMapper.updateByPrimaryKeySelective(defectKeep);
		
		//添加新增日志
		commonRecService.write(defectKeep.getId(), TableEnum.B_S2_012,user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,defectKeep.getId());
		
		return defectKeep.getId();
	}

	/**
	 * 
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:56:46
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Override
	public String updatefilesDownSub(DefectKeep defectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();//流水号
		User user = ThreadVarUtil.getUser();//当前登陆人
		//新增附件
		attachmentService.eidtAttachment(defectKeep.getAttachmentList(), defectKeep.getId(), czls, defectKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
	
		return defectKeep.getId();
	}



     
}