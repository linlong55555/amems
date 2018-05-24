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
import com.eray.thjw.quality.dao.AuditItemMapper;
import com.eray.thjw.quality.dao.AuditNoticeToAuditItemMapper;
import com.eray.thjw.quality.dao.ProjectAndProblemMapper;
import com.eray.thjw.quality.po.AuditItem;
import com.eray.thjw.quality.po.AuditNoticeToAuditItem;
import com.eray.thjw.quality.service.AuditItemListService;
import com.eray.thjw.quality.service.AuditMembersService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.quality.AuditItemStatusEnum;
import enu.quality.BusinessTypeEnum;

/** 
 * @Description  审核项目单
 * @CreateTime 2018-1-16 下午2:57:51
 * @CreateBy 孙霁	
 */
@Service("auditItemListService")
public class AuditItemListServiceImpl implements AuditItemListService{

	@Resource
	private AuditItemMapper auditItemMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private AuditNoticeToAuditItemMapper auditNoticeToAuditItemMapper;
	
	@Resource
	private AuditMembersService auditMembersService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private ProjectAndProblemMapper projectAndProblemMapper;
	
	/**
	 * 
	 * @Description 根据条件查询列表数据
	 * @CreateTime 2018-1-8 上午10:05:30
	 * @CreateBy 孙霁
	 * @param auditItem
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAll(AuditItem auditItem)
			throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=auditItem.getId();
		auditItem.setId("");
		try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(auditItem.getPagination());
			List<AuditItem> list=auditItemMapper.queryAll(auditItem);
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						AuditItem newRecord = new AuditItem();
						newRecord.setId(id);
						List<AuditItem> newRecordList = auditItemMapper.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				//this.gettechnicalfileorder(list);
				resultMap=PageUtil.pack4PageHelper(list, auditItem.getPagination());
				return resultMap;
				
			}else{
				List<AuditItem> newRecordList = new ArrayList<AuditItem>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					AuditItem newRecord = new AuditItem();
					newRecord.setId(id);
					newRecordList = auditItemMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, auditItem.getPagination());
						return resultMap;
					}
					
				}
				//this.gettechnicalfileorder(newRecordList);
				resultMap= PageUtil.pack(0, newRecordList, auditItem.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}

	/**
	 * 
	 * @Description 添加
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditItem
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insert(AuditItem auditItem) throws BusinessException {
		//添加主表数据
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		auditItem.setDprtcode(user.getJgdm());
		//验证编号是否唯一
		if(auditItem.getShxmdbh() != null && auditItem.getShxmdbh() != ""){
			if(verifyBh(auditItem)){
				throw new BusinessException("检查单号已存在，请重新输入！");
			}
		}else{
			boolean b=true;
			AuditItem an = new AuditItem();
			while(b){
				String gczlbh;
				try {
					gczlbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.SHXMDBH.getName());
				} catch (SaibongException e) {
					throw new RuntimeException(e);
				}
				auditItem.setShxmdbh(gczlbh);
				an.setShxmdbh(gczlbh);
				an.setDprtcode(user.getJgdm());
				if(!verifyBh(an)){
					b=false;
				}
			}
		}
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.SAVE_WO;
		auditItem.setDprtcode(user.getJgdm());
		auditItem.setId(UUID.randomUUID().toString());
		auditItem.setWhbmid(user.getBmdm());
		auditItem.setWhrid(user.getId());
		auditItem.setWhsj(new Date());
		auditItemMapper.insertSelective(auditItem);
		
		//判断是否添加审核通知单中间表
		if(auditItem.getShtzdid() != null && !"".equals(auditItem.getShtzdid())){
			AuditNoticeToAuditItem auditNoticeToAuditItem =new AuditNoticeToAuditItem();
			auditNoticeToAuditItem.setShtzdid(auditItem.getShtzdid());
			auditNoticeToAuditItem.setShtzdid(auditItem.getId());
			auditNoticeToAuditItemMapper.insertSelective(auditNoticeToAuditItem);
		}
		
		//添加审核成员
		if(auditItem.getShdxList() != null && auditItem.getShdxList().size() > 0){
			auditMembersService.insert(auditItem.getShdxList(), auditItem.getId(), BusinessTypeEnum.SHXMD.getId(), user);
		}
		//添加附件
		attachmentService.eidtAttachment(auditItem.getAttachmentList(), auditItem.getId(), czls, auditItem.getId(), auditItem.getDprtcode(), LogOperationEnum.SAVE_WO);
		
		
		return auditItem.getId();
	}

	/**
	 * 
	 * @Description 验证编号
	 * @CreateTime 2018-1-10 下午3:31:40
	 * @CreateBy 孙霁
	 * @return
	 */
	private Boolean verifyBh(AuditItem auditItem){
		int i = auditItemMapper.queryCount(auditItem);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2018-1-10 下午2:22:20
	 * @CreateBy 孙霁
	 * @param auditItem
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(AuditItem auditItem) throws BusinessException {
		//验证状态是否发生改变
		int [] iStatus = {AuditItemStatusEnum.SAVE.getId()
				};
		boolean blAbnormal = this.verification(auditItem.getId(), iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核评估单已更新，请刷新后再进行操作！");
		}
		//修改主表数据
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 操作指令
		LogOperationEnum cz = LogOperationEnum.EDIT;
		auditItem.setWhbmid(user.getBmdm());
		auditItem.setWhrid(user.getId());
		auditItem.setWhsj(new Date());
		auditItemMapper.updateByPrimaryKeySelective(auditItem);
		//编辑审核成员表
		//1.删除主表id的所有审核成员
		auditMembersService.delete(auditItem.getId());
		//2.添加
		if(auditItem.getShdxList() != null && auditItem.getShdxList().size() > 0){
			auditMembersService.insert(auditItem.getShdxList(), auditItem.getId(), BusinessTypeEnum.SHXMD.getId(), user);
		}
		//编辑附件信息
		attachmentService.eidtAttachment(auditItem.getAttachmentList(), auditItem.getId(), czls, auditItem.getId(), auditItem.getDprtcode(), LogOperationEnum.EDIT);
		
		return auditItem.getId();
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
	public AuditItem selectById(String id) throws BusinessException {
		return auditItemMapper.selectByPrimaryKey(id);
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
		int [] iStatus = {AuditItemStatusEnum.SAVE.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核项目单已更新，请刷新后再进行操作！");
		}
		//1.删除主表
		auditItemMapper.deleteByPrimaryKey(id);
		//2.删除审核人员数据
		auditMembersService.delete(id);
		//3.删除b_z_008中间表
		auditNoticeToAuditItemMapper.deleteByShxmdid(id);
		//4.删除b_z_010中间表
		projectAndProblemMapper.deleteByShxmdid(id);
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
		int [] iStatus = {AuditItemStatusEnum.SAVE.getId(),AuditItemStatusEnum.XIAFA.getId()
				};
		boolean blAbnormal = this.verification(id, iStatus);
		if(blAbnormal){
			throw new BusinessException("该审核项目单已更新，请刷新后再进行操作！");
		}
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		AuditItem auditItem = new AuditItem();
		auditItem.setId(id);
		auditItem.setZt(AuditItemStatusEnum.CLOSE.getId());
		auditItem.setWhrid(user.getId());
		auditItem.setWhsj(new Date());
		auditItemMapper.updateByPrimaryKeySelective(auditItem);
		
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
		AuditItem auditItem=auditItemMapper.selectByPrimaryKey(id);
		for (int i = 0; i < iStatus.length; i++) {
			if(auditItem != null && auditItem.getZt() == iStatus[i]){
				blAbnormal = false;
			}
		}
		
		return blAbnormal;
	}

}
