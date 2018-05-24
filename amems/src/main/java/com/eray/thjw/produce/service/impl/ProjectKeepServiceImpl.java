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
import com.eray.thjw.produce.dao.ProjectKeepMapper;
import com.eray.thjw.produce.po.FailureKeep;
import com.eray.thjw.produce.po.ProjectKeep;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.service.ProjectKeepService;
import com.eray.thjw.produce.service.WorkOrderNew145Service;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.project2.service.MaterialToolService;
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
import enu.common.WhetherEnum;
import enu.produce.FailureKeepStatusEnum;
import enu.produce.GdblztEnum;
import enu.produce.WorkorderStatusEnum;
import enu.produce.WorkorderTypeEnum;
import enu.project2.ProjectBusinessEnum;
/**
 * 
 * @Description 项目保留serviceimpl 
 * @CreateTime 2017年10月10日 下午2:56:21
 * @CreateBy 林龙
 */
@Service("projectKeepService")
public class ProjectKeepServiceImpl implements ProjectKeepService  {

	@Resource
	private CommonService commonService;
	
	@Resource
	private ProjectKeepMapper projectKeepMapper;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private MaterialToolService materialToolService;
	
	@Resource
	private WorkOrderNewService workOrderNewService;
	
	@Resource
	private WorkOrderNew145Service workOrderNew145Service;
	
	@Resource
	private PlaneModelDataService planeModelDataService;

	/**
	 * @Description 项目保留分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(ProjectKeep projectKeep)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = projectKeep.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		projectKeep.setId(null);
		User user = ThreadVarUtil.getUser();//当前登陆人
		projectKeep.getParamsMap().put("userId", user.getId());
		projectKeep.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(projectKeep.getPagination());
		List<ProjectKeep> list = projectKeepMapper.queryAllPageList(projectKeep);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					ProjectKeep param = new ProjectKeep();
					param.setId(id);
					List<ProjectKeep> newRecordList = projectKeepMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, projectKeep.getPagination());
		}else{
			List<ProjectKeep> newRecordList = new ArrayList<ProjectKeep>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				ProjectKeep param = new ProjectKeep();
				param.setId(id);
				newRecordList = projectKeepMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, projectKeep.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, projectKeep.getPagination());
		}
	}
	
	/**
	 * 
	 * @Description 项目保留监控列表加载
	 * @CreateTime 2017年10月31日 下午1:43:48
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryMonitorAllPageList(ProjectKeep projectKeep)throws BusinessException {
		//用户刚编辑过的记录 ID
				String id = projectKeep.getId();
				//清除查询条件中的ID，保证列表查询结果集的正确性
				projectKeep.setId(null);
				User user = ThreadVarUtil.getUser();//当前登陆人
				projectKeep.getParamsMap().put("userId", user.getId());
				projectKeep.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
				PageHelper.startPage(projectKeep.getPagination());
				List<ProjectKeep> list = projectKeepMapper.queryMonitorAllPageList(projectKeep);
				if(((Page)list).getTotal() > 0){
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						if(!PageUtil.hasRecord(list, id)){
							ProjectKeep param = new ProjectKeep();
							param.setId(id);
							List<ProjectKeep> newRecordList = projectKeepMapper.queryMonitorAllPageList(param);
							if(newRecordList != null && newRecordList.size() == 1){
								list.add(0, newRecordList.get(0));
							}
						}
					}
					return PageUtil.pack4PageHelper(list, projectKeep.getPagination());
				}else{
					List<ProjectKeep> newRecordList = new ArrayList<ProjectKeep>(1);
					if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
						ProjectKeep param = new ProjectKeep();
						param.setId(id);
						newRecordList = projectKeepMapper.queryMonitorAllPageList(param);
						if(newRecordList != null && newRecordList.size() == 1){
							return PageUtil.pack(1, newRecordList, projectKeep.getPagination());
						}
					}
					return PageUtil.pack(0, newRecordList, projectKeep.getPagination());
				}
	}
	
	/**
	 * @Description 保存/提交 项目保留
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 项目保留
	 * @return 项目保留id
	 * @throws BusinessException
	 */
	@Override
	public String save(ProjectKeep projectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//当项目保留为保存提交时验证工单
		this.validationSaveGD(projectKeep);
		
		projectKeep.setGdblzt(GdblztEnum.Bl.getId());
		//新增项目保留 
		insertSelective(projectKeep,uuid,czls,user);
		
		//保存器材/工具
		materialToolService.saveMaterialToolList(projectKeep.getMaterialToolList(), ProjectBusinessEnum.PROJECTEKEEP.getId(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		//新增附件
		attachmentService.eidtAttachment(projectKeep.getAttachmentList(), uuid, czls, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		//当项目保留为提交时更新工单.xmblbs = 1
		this.updateOrderXmblbs(projectKeep,czls,LogOperationEnum.EDIT,commonService.getSysdate(),WhetherEnum.YES);
		
		return uuid;
	}
	
	/**
	 * @Description 当项目保留为提交时更新工单.xmblbs = 1
	 * @CreateTime 2017年10月13日 上午11:44:32
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @throws BusinessException 
	 */
	private void updateOrderXmblbs(ProjectKeep projectKeep,String czls,LogOperationEnum operation,Date currentDate,WhetherEnum ztEnum) throws BusinessException {
		if(projectKeep.getZt() == FailureKeepStatusEnum.ASSESSMENT.getId()){
			if(projectKeep.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = new Workorder145();
				workorder145.setId(projectKeep.getGdid());
				workorder145.setXmblbs(ztEnum.getId());
				workOrderNew145Service.updateWOMain(workorder145, currentDate, czls, operation);
			}else{
				Workorder workorder = new Workorder();
				workorder.setId(projectKeep.getGdid());
				workorder.setXmblbs(ztEnum.getId());
				workOrderNewService.updateWOMain(workorder, currentDate, czls, operation);
			}
		}
	}

	/**
	 * @Description 当项目保留为保存提交时验证工单
	 * @CreateTime 2017年10月13日 上午11:28:07
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @throws BusinessException
	 */
	private void validationSaveGD(ProjectKeep projectKeep) throws BusinessException {
		String zt = null;
		if(projectKeep.getZt() == FailureKeepStatusEnum.SAVE.getId()){
			zt = FailureKeepStatusEnum.SAVE.getName();
		}else{
			zt = FailureKeepStatusEnum.ASSESSMENT.getName();
		}
		if(projectKeep.getGdid() != null){
			if(projectKeep.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = workOrderNew145Service.selectByPrimaryKey(projectKeep.getGdid());
				if(workorder145.getXmblbs()  == WhetherEnum.YES.getId() ){
					throw new BusinessException("当前选择工单已保留，不可"+zt+"项目保留!");
				}else if(workorder145.getZt() != WorkorderStatusEnum.TAKEEFFECT.getId() && workorder145.getZt() != WorkorderStatusEnum.SUBMIT.getId() && workorder145.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
					throw new BusinessException("当前选择工单状态没提交，生效（下发），完成，不可"+zt+"项目保留!");
				}else if(workorder145.getGdlx() != WorkorderTypeEnum.RTN.getId() && workorder145.getGdlx() != WorkorderTypeEnum.EO.getId() 
						&& workorder145.getGdlx() != WorkorderTypeEnum.NRC.getId()&& workorder145.getGdlx() != WorkorderTypeEnum.MOMCC.getId()&& workorder145.getGdlx() != WorkorderTypeEnum.OTHER.getId()){
					throw new BusinessException("当前选择工单类型不为RTN（维修项目），EO，NRC，MO/MCC，其它指令，不可"+zt+"项目保留!");
				}
			}else{
				Workorder workorder = workOrderNewService.selectByPrimaryKey(projectKeep.getGdid());
				if(workorder.getXmblbs() == WhetherEnum.YES.getId()){
					throw new BusinessException("当前选择工单已保留，不可"+zt+"项目保留!");
				} else if(workorder.getZt() != WorkorderStatusEnum.TAKEEFFECT.getId() && workorder.getZt() != WorkorderStatusEnum.SUBMIT.getId() && workorder.getZt() != WorkorderStatusEnum.CLOSETOFINISH.getId()){
					throw new BusinessException("当前选择工单状态没提交，生效（下发），完成，不可"+zt+"项目保留!");
				}else if(workorder.getGdlx() != WorkorderTypeEnum.RTN.getId() && workorder.getGdlx() != WorkorderTypeEnum.EO.getId() && workorder.getGdlx()!= WorkorderTypeEnum.NRC.getId()&& workorder.getGdlx() != WorkorderTypeEnum.MOMCC.getId()&& workorder.getGdlx() != WorkorderTypeEnum.OTHER.getId()){
					throw new BusinessException("当前选择工单类型不为RTN（维修项目），EO，NRC，MO/MCC，其它指令，不可"+zt+"项目保留!");
				}
			}
		}
	}

	/**
	 * @Description 新增项目保留
	 * @CreateTime 2017年9月27日 下午1:43:30
	 * @CreateBy 林龙
	 * @param ProjectKeep
	 * @param uuid 项目保留id
	 * @param czls 流水号
	 * @param user 当前登录人
	 */
	private void insertSelective(ProjectKeep projectKeep, String uuid,String czls,User user)throws BusinessException {
		
		// 验证飞机是否存在
		this.validationFjzch(projectKeep,user);
		
		//当页面没有填写项目保留编号时
		if(projectKeep.getBldh() == null || "".equals(projectKeep.getBldh())){ 
			String bldh=setBldh(projectKeep,user); 		  //根据项目获取最新项目保留单号
			projectKeep.setBldh(bldh);
		}else{
			//验证验证项目保留单编号 唯一
			validationFailureKeep(projectKeep);
		}
		projectKeep.setShr1(projectKeep.getShr());        //审核人
		projectKeep.setDprtcode(user.getJgdm());          //组织机构
		projectKeep.setId(uuid);                          //id
		projectKeep.setWhdwid(user.getBmdm());	          //部门id
		projectKeep.setWhrid(user.getId());		          //用户id
		
		//新增项目保留
		projectKeepMapper.insertSelective(projectKeep);
		//添加新增日志
		commonRecService.write(uuid, TableEnum.B_S2_011,user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid);
		
	}
	
	/**
	 * @Description 验证飞机权限
	 * @CreateTime 2017年9月25日 下午3:04:16
	 * @CreateBy 林龙
	 * @param record
	 * @param user
	 * @throws BusinessException
	 */
	private void validationFjzch(ProjectKeep projectKeep, User user) throws BusinessException {
		List<String> fjzchList = new ArrayList<String>();
		fjzchList.add(projectKeep.getFjzch());
		planeModelDataService.existsAircraft4145Expt(user.getId(), user.getUserType(), projectKeep.getDprtcode(), fjzchList);
	}
	
	/**
	 * @Description 根据项目保留单对象获取最新项目保留单编号
	 * @CreateTime 2017年9月27日 下午1:48:51
	 * @CreateBy 林龙
	 * @param ProjectKeep  项目保留对象
	 * @param user 当前用户
	 * @return
	 * @throws BusinessException 
	 */
	private String setBldh(ProjectKeep projectKeep,User user) throws BusinessException {
		ProjectKeep fai = new ProjectKeep(); 		//new 项目保留
		boolean b = true;
		String bldh = null;
		while(b){
			try {
				bldh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.XMBLD.getName(),projectKeep);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			fai.setBldh(bldh);
			fai.setDprtcode(user.getJgdm());
			//根据技术文件对象查询项目保留数量
			int i = projectKeepMapper.queryCount(fai); 
			if(i <= 0){
				b = false;
			}
		}
		return bldh;
	}

	/**
	 * @Description //验证验证项目保留单编号 唯一
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 */
	private void validationFailureKeep(ProjectKeep projectKeep)throws BusinessException {
		int  iNum = projectKeepMapper.queryCount(projectKeep);
		if (iNum > 0) {
			throw new BusinessException("该项目保留单编号已存在!");
		}
		
	}

	/**
	 * @Description 删除
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 */
	@Override
	public void delete(ProjectKeep projectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		// 验证单据状态是否已变更
		validation4CurrentZt(projectKeep.getId(), Integer.valueOf((String) projectKeep.getParamsMap().get("currentZt")));
		
		//添加新增日志
		commonRecService.write(projectKeep.getId(), TableEnum.B_S2_011,user.getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,projectKeep.getId());
		
		//删除项目保留
		projectKeepMapper.deleteByPrimaryKey(projectKeep.getId());
	}

	/**
	 * @Description 根据项目保留id查询项目保留信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public ProjectKeep getInfoById(ProjectKeep projectKeep)throws BusinessException {
		return projectKeepMapper.getInfoById(projectKeep);
	}
	
	/**
	 * @Description 修改保存项目保留
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @return 项目保留id
	 * @throws BusinessException
	 */
	@Override
	public String update(ProjectKeep projectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString(); //流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		projectKeep.setShr1(projectKeep.getShr());  //审核人
		
		//当项目保留为保存提交时验证工单
		this.validationSaveGD(projectKeep);
		
		//修改项目保留
		updateByPrimaryKeySelective(projectKeep,czls,user);
		
		//修改器材/工具
		materialToolService.updateMaterialToolList(projectKeep.getMaterialToolList(), ProjectBusinessEnum.PROJECTEKEEP.getId(), projectKeep.getId(), czls, projectKeep.getId(), projectKeep.getDprtcode(), LogOperationEnum.EDIT);
		
		//修改附件
		attachmentService.eidtAttachment(projectKeep.getAttachmentList(), projectKeep.getId(), czls, projectKeep.getId(), projectKeep.getDprtcode(), LogOperationEnum.EDIT);
		
		//当项目保留为提交时更新工单.xmblbs = 1
		this.updateOrderXmblbs(projectKeep,czls,LogOperationEnum.EDIT,commonService.getSysdate(),WhetherEnum.YES);
		
		return projectKeep.getId();
	}

	/**
	 * @Description 修改项目保留
	 * @CreateTime 2017年9月27日 下午4:40:24
	 * @CreateBy 林龙
	 * @param ProjectKeep 项目保留
	 * @param czls 流水号
	 */
	private void updateByPrimaryKeySelective(ProjectKeep projectKeep,String czls,User user)throws BusinessException {
		
		// 验证飞机是否存在
		this.validationFjzch(projectKeep,user);
		
		// 验证单据状态是否已变更
		validation4CurrentZt(projectKeep.getId(), Integer.valueOf((String) projectKeep.getParamsMap().get("currentZt")));
		// 验证145是否存在
		this.validation145(projectKeep);
		
		projectKeep.setWhrid(user.getBmdm()); //维护部门
		projectKeep.setWhrid(user.getId());   //维护人
		projectKeepMapper.updateByPrimaryKeySelective(projectKeep);
		//添加修改日志
		commonRecService.write(projectKeep.getId(), TableEnum.B_S2_011,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,projectKeep.getId());
	}
	
	/**
	 * 
	 * @Description 验证145是否存在
	 * @CreateTime 2018年4月8日 上午10:58:45
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	private void validation145(ProjectKeep projectKeep) throws BusinessException{
		ProjectKeep projectKeepnew = projectKeepMapper.selectByPrimaryKey(projectKeep.getId());
		if(projectKeepnew.getBs145() != null){
			if (projectKeepnew.getBs145() == 0 && projectKeep.getBs145() == 1) {
				throw new BusinessException("您没有135工单选择权限!");
			}
			if (projectKeepnew.getBs145() == 1 && projectKeep.getBs145() == 0) {
				throw new BusinessException("您没有145工单选择权限!");
			}
		}
		
	}

	/**
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer zt)throws BusinessException {
		int bzt = projectKeepMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * @Description 完成项目保留
	 * @CreateTime 2017年9月29日 上午10:07:23
	 * @CreateBy 林龙
	 * @param projectKeep 项目保留对象
	 * @throws BusinessException
	 */
	@Override
	public void updateEndModal(ProjectKeep projectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) projectKeep.getParamsMap().get("currentZt"));
		//验证单据状态是否已变更
		validation4CurrentZt(projectKeep.getId(), iCurrentZt);
		
		ProjectKeep projectKeepNew = projectKeepMapper.selectByPrimaryKey(projectKeep.getId());
		if(projectKeepNew.getGdblzt() != GdblztEnum.SF.getId()){//当工单保留状态
			throw new BusinessException("工单保留状态未释放，不可关闭项目保留!");
		}
		if(projectKeepNew.getGdid() != null){//当工单id不为null时判断工单状态是否是已关闭/指定结束
			if(projectKeepNew.getBs145() == WhetherEnum.YES.getId()){ 
				Workorder145 workorder145 = workOrderNew145Service.selectByPrimaryKey(projectKeepNew.getGdid());
				if(workorder145.getZt() != 9&&workorder145.getZt()!=10){
					throw new BusinessException("工单状态未指定结束或者关闭，不可关闭项目保留!");
				}
			}else{
				Workorder workorder = workOrderNewService.selectByPrimaryKey(projectKeepNew.getGdid());
				if(workorder.getZt() != 9&&workorder.getZt() != 10){
					throw new BusinessException("工单状态未指定结束或者关闭，不可关闭项目保留!");
				}
			}
		}
		projectKeep.setZt(FailureKeepStatusEnum.WANCHENG.getId()); //状态为完成
		
		projectKeep.setGbrid(user.getId());
		projectKeep.setGbrbmid(user.getBmdm());
		
		//完成
		projectKeepMapper.updateByPrimaryKeySelective(projectKeep);
		
		//添加新增日志
		commonRecService.write(projectKeep.getId(), TableEnum.B_S2_011,user.getId(), czls, LogOperationEnum.WANCHEN, UpdateTypeEnum.UPDATE,projectKeep.getId());
		
		//新增附件
		attachmentService.eidtAttachment(projectKeep.getAttachmentList(), projectKeep.getId(), czls, projectKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
	}

	/**
	 * @Description 指定结束项目保留
	 * @CreateTime 2017年9月29日 上午11:42:15
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @throws BusinessException
	 */
	@Override
	public void updategConfirm(ProjectKeep projectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//当前状态
		Integer iCurrentZt = Integer.valueOf((String) projectKeep.getParamsMap().get("currentZt"));
		// 验证单据状态是否已变更
		validation4CurrentZt(projectKeep.getId(), iCurrentZt);
		
		ProjectKeep projectKeepNew=projectKeepMapper.selectByPrimaryKey(projectKeep.getId());
		projectKeep.setZt(FailureKeepStatusEnum.CLOSE.getId()); //状态为指定结束
		projectKeep.setGbrid(user.getId());
		projectKeep.setGbrbmid(user.getBmdm());
	
		//指定结束
		projectKeepMapper.updateByPrimaryKeySelective(projectKeep);
		
		//添加新增日志
		commonRecService.write(projectKeep.getId(), TableEnum.B_S2_011,user.getId(), czls, LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE,projectKeep.getId());
		
		//更新工单.xmblbs = 0
		this.updateOrderXmblbs(projectKeepNew,czls,LogOperationEnum.EDIT,commonService.getSysdate(),WhetherEnum.NO);
	}

	/**
	 * @Description 重新执行
	 * @CreateTime 2017年9月28日 下午5:05:44
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Override
	public String updateReExecute(ProjectKeep projectKeep) throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		// 验证单据状态是否已变更
		validation4CurrentZt(projectKeep.getId(), Integer.valueOf((String) projectKeep.getParamsMap().get("currentZt")));
		
		ProjectKeep projectKeepNew=projectKeepMapper.selectByPrimaryKey(projectKeep.getId());
		
		if(projectKeepNew.getGdblzt() != GdblztEnum.Bl.getId()){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		
		//修改项目保留
		projectKeep.setWhrid(user.getBmdm()); 			//维护部门
		projectKeep.setWhrid(user.getId());   			//维护人
		projectKeep.setGdblzt(GdblztEnum.SF.getId());  	//工单保留状态为2
		projectKeepMapper.updateByPrimaryKeySelective(projectKeep);
		
		//添加新增日志
		commonRecService.write(projectKeep.getId(), TableEnum.B_S2_011,user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,projectKeep.getId());
		
		//更新工单.xmblbs = 0
		this.updateOrderXmblbs(projectKeepNew,czls,LogOperationEnum.EDIT,commonService.getSysdate(),WhetherEnum.NO);
		
		return projectKeep.getId();
	}

	/**
	 * @Description 查询工单信息
	 * @CreateTime 2017年10月17日 上午11:24:39
	 * @CreateBy 林龙
	 * @param projectKeep
	 * @return 
	 * @throws BusinessException
	 */
	@Override
	public ProjectKeep selectGetBygdId(ProjectKeep projectKeep)throws BusinessException {
		ProjectKeep projectKeepNew = new ProjectKeep();
		if(projectKeep.getParamsMap().get("gdLx").toString().equals("135")){  //135
			projectKeepNew = projectKeepMapper.selectGetBygdId135(projectKeep);
		}else{//145
			projectKeepNew = projectKeepMapper.selectGetBygdId145(projectKeep);
		}
		return projectKeepNew;
	}

	/**
	 * @Description 附件上传
	 * @CreateTime 2017年10月25日 下午2:56:46
	 * @CreateBy 林龙
	 * @param failureKeep
	 * @throws BusinessException
	 */
	@Override
	public String updatefilesDownSub(ProjectKeep projectKeep)throws BusinessException {
		String czls = UUID.randomUUID().toString();	//流水号
		User user = ThreadVarUtil.getUser();		//当前登陆人
		//新增附件
		attachmentService.eidtAttachment(projectKeep.getAttachmentList(), projectKeep.getId(), czls, projectKeep.getId(), user.getJgdm(), LogOperationEnum.SAVE_WO);
	
		return projectKeep.getId();
	}

	
}