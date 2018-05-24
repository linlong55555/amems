package com.eray.thjw.quality.service.impl;

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
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.quality.dao.AuditnoticeMapper;
import com.eray.thjw.quality.dao.NoticeToItemlistqueryMapper;
import com.eray.thjw.quality.dao.PlanToNoticeMapper;
import com.eray.thjw.quality.po.Auditnotice;
import com.eray.thjw.quality.po.PlanToNotice;
import com.eray.thjw.quality.service.AuditMembersService;
import com.eray.thjw.quality.service.AuditNoticeService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.project2.AirworthinessStatusEnum;
import enu.quality.AuditItemStatusEnum;
import enu.quality.AuditnoticeStatusEnum;
import enu.quality.BusinessTypeEnum;

/** 
 * @Description 审核通知单
 * @CreateTime 2018-1-8 上午10:06:37
 * @CreateBy 孙霁	
 */
@Service
public class AuditNoticeServiceImpl implements AuditNoticeService{

	@Resource
	private AuditnoticeMapper auditNoticeMapper;
	
	@Resource
	private AuditMembersService auditMembersService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private SaibongUtilService saibongUtilService;

	@Resource
	private PlanToNoticeMapper planToNoticeMapper;
	
	@Resource
	private NoticeToItemlistqueryMapper noticeToItemlistqueryMapper;
	
	@Override
	public Map<String, Object> queryAll(Auditnotice auditNotice)
			throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=auditNotice.getId();
		auditNotice.setId("");
		try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(auditNotice.getPagination());
			List<Auditnotice> list=auditNoticeMapper.queryAll(auditNotice);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Auditnotice newRecord = new Auditnotice();
						newRecord.setId(id);
						List<Auditnotice> newRecordList = auditNoticeMapper.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				//this.gettechnicalfileorder(list);
				resultMap=PageUtil.pack4PageHelper(list, auditNotice.getPagination());
				return resultMap;
				
			}else{
				List<Auditnotice> newRecordList = new ArrayList<Auditnotice>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Auditnotice newRecord = new Auditnotice();
					newRecord.setId(id);
					newRecordList = auditNoticeMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, auditNotice.getPagination());
						return resultMap;
					}
					
				}
				//this.gettechnicalfileorder(newRecordList);
				resultMap= PageUtil.pack(0, newRecordList, auditNotice.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2017-9-21 上午11:09:46
	 * @CreateBy 孙霁
	 * @param aircraftinfo
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insert(Auditnotice auditnotice) throws BusinessException {
		//添加主表数据
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		auditnotice.setDprtcode(user.getJgdm());
		//验证编号是否唯一
		if(auditnotice.getJcdbh() != null && auditnotice.getJcdbh() != ""){
			if(verifyBh(auditnotice)){
				throw new BusinessException("检查单号已存在，请重新输入！");
			}
		}else{
			boolean b=true;
			Auditnotice an = new Auditnotice();
			while(b){
				String gczlbh;
				try {
					gczlbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JCDBH.getName());
				} catch (SaibongException e) {
					throw new RuntimeException(e);
				}
				auditnotice.setJcdbh(gczlbh);
				an.setJcdbh(gczlbh);
				an.setDprtcode(user.getJgdm());
				if(!verifyBh(an)){
					b=false;
				}
			}
		}
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.SAVE_WO;
		auditnotice.setDprtcode(user.getJgdm());
		auditnotice.setId(UUID.randomUUID().toString());
		auditnotice.setYdzt(0);
		auditnotice.setWhbmid(user.getBmdm());
		auditnotice.setWhrid(user.getId());
		auditnotice.setWhsj(new Date());
		auditNoticeMapper.insertSelective(auditnotice);
		
		//判断是否添加年度计划中间表
		if(auditnotice.getNdshjhid() != null && !"".equals(auditnotice.getNdshjhid())){
			PlanToNotice planToNotice =new PlanToNotice();
			planToNotice.setNdshjhid(auditnotice.getNdshjhid());
			planToNotice.setShtzdid(auditnotice.getId());
			planToNoticeMapper.insertSelective(planToNotice);
		}
		
		//添加审核成员
		if(auditnotice.getShdxList() != null && auditnotice.getShdxList().size() > 0){
			auditMembersService.insert(auditnotice.getShdxList(), auditnotice.getId(), BusinessTypeEnum.SHTZD.getId(), user);
		}
		//添加附件
		attachmentService.eidtAttachment(auditnotice.getAttachmentList(), auditnotice.getId(), czls, auditnotice.getId(), auditnotice.getDprtcode(), LogOperationEnum.SAVE_WO);
		
		
		return auditnotice.getId();
	}
	/**
	 * 
	 * @Description 验证编号
	 * @CreateTime 2018-1-10 下午3:31:40
	 * @CreateBy 孙霁
	 * @return
	 */
	private Boolean verifyBh(Auditnotice auditnotice){
		int i = auditNoticeMapper.queryCount(auditnotice);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 
	 * @Description 根据id查询数据
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Auditnotice selectById(String id) throws BusinessException {
		return auditNoticeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditnotice
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(Auditnotice auditnotice) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditnoticeStatusEnum.SAVE.getId()
				};
		boolean blAbnormal = this.verification(auditnotice.getId(), iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核通知单已更新，请刷新后再进行操作！");
		}
		//修改主表数据
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.EDIT;
		auditnotice.setWhbmid(user.getBmdm());
		auditnotice.setWhrid(user.getId());
		auditnotice.setWhsj(new Date());
		auditNoticeMapper.updateByPrimaryKeySelective(auditnotice);
		//编辑审核成员表
		//1.删除主表id的所有审核成员
		auditMembersService.delete(auditnotice.getId());
		//2.添加
		if(auditnotice.getShdxList() != null && auditnotice.getShdxList().size() > 0){
			auditMembersService.insert(auditnotice.getShdxList(), auditnotice.getId(), BusinessTypeEnum.SHTZD.getId(), user);
		}
		//编辑附件信息
		attachmentService.eidtAttachment(auditnotice.getAttachmentList(), auditnotice.getId(), czls, auditnotice.getId(), auditnotice.getDprtcode(), LogOperationEnum.EDIT);
		
		return auditnotice.getId();
	}
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void delete(String id) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditnoticeStatusEnum.SAVE.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核通知单已更新，请刷新后再进行操作！");
		}
		//1.删除主表
		auditNoticeMapper.deleteByPrimaryKey(id);
		//2.删除审核人员数据
		auditMembersService.delete(id);
		//3.删除b_z_008中间表
		planToNoticeMapper.delete(id);
		//4.删除b_z_010中间表
		noticeToItemlistqueryMapper.delete(id);
		
	}
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateIssue(String id) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditnoticeStatusEnum.SAVE.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核通知单已更新，请刷新后再进行操作！");
		}
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		Auditnotice auditnotice = new Auditnotice();
		auditnotice.setId(id);
		auditnotice.setZt(AuditnoticeStatusEnum.XIAFA.getId());
		auditnotice.setWhbmid(user.getBmdm());
		auditnotice.setWhrid(user.getId());
		auditnotice.setWhsj(new Date());
		auditNoticeMapper.updateByPrimaryKeySelective(auditnotice);
	}
	/**
	 * 
	 * @Description 接收
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateAccept(String id) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditnoticeStatusEnum.XIAFA.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核通知单已更新，请刷新后再进行操作！");
		}
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		Auditnotice auditnotice = new Auditnotice();
		auditnotice.setId(id);
		auditnotice.setZt(AuditnoticeStatusEnum.JIESHOU.getId());
		auditnotice.setQrrbh(user.getUsername());
		auditnotice.setQrrid(user.getId());
		auditnotice.setQrrmc(user.getRealname());
		auditnotice.setQrsj(new Date());
		auditNoticeMapper.updateByPrimaryKeySelective(auditnotice);
	}
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018-1-11 上午10:12:17
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateClose(String id) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditnoticeStatusEnum.SAVE.getId(),AuditItemStatusEnum.XIAFA.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核通知单已更新，请刷新后再进行操作！");
		}
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		Auditnotice auditnotice = new Auditnotice();
		auditnotice.setId(id);
		auditnotice.setZt(AuditnoticeStatusEnum.CLOSE.getId());
		auditnotice.setQrrbh(user.getUsername());
		auditnotice.setQrrid(user.getId());
		auditnotice.setQrrmc(user.getRealname());
		auditnotice.setQrsj(new Date());
		auditNoticeMapper.updateByPrimaryKeySelective(auditnotice);
		
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
		Auditnotice auditNotice=auditNoticeMapper.selectByPrimaryKey(id);
		for (int i = 0; i < iStatus.length; i++) {
			if(auditNotice != null && auditNotice.getZt() == iStatus[i]){
				blAbnormal = false;
			}
		}
		
		return blAbnormal;
	}

	/**
	 * 
	 * @Description 接收审核项目单
	 * @CreateTime 2018-1-11 上午10:10:53
	 * @CreateBy 孙霁
	 * @param request
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public void updateacceptAuditNotice(String id) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditnoticeStatusEnum.XIAFA.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核评估单已更新，请刷新后再进行操作！");
		}
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		Auditnotice auditnotice = new Auditnotice();
		auditnotice.setId(id);
		auditnotice.setZt(AuditnoticeStatusEnum.JIESHOU.getId());
		auditnotice.setQrrbh(user.getUsername());
		auditnotice.setQrrid(user.getId());
		auditnotice.setQrrmc(user.getRealname());
		auditnotice.setQrsj(new Date());
		auditNoticeMapper.updateByPrimaryKeySelective(auditnotice);
	}

}
