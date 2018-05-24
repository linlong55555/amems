package com.eray.thjw.flightdata.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;


@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Resource
    private AttachmentMapper attachmentMapper;
    @Resource
 	private CommonRecService commonRecService;
	@Override
	public int insertSelective(List<Attachment> attachment,String mainid,String czls,LogOperationEnum logopration,String id) {
		int count=0;
		if (attachment!=null && !attachment.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment att : attachment) {
					UUID uuid = UUID.randomUUID();
					String id2 = uuid.toString();
					att.setId(id2);
					att.setMainid(mainid);
					att.setCzbmid(user.getBmdm());;
					att.setCzrid(user.getId());
					att.setDprtcode(user.getJgdm());
					count=attachmentMapper.insertSelective(att);
					commonRecService.write(id2, TableEnum.D_011, user.getId(),czls ,logopration, UpdateTypeEnum.SAVE,id);
			}
		}
		return count;
	}
	
	/**
	 * @author liub
	 * @description 增加附件
	 * @param attachment,mainid
	 * @develop date 2016.11.16
	 * @throws BusinessException 
	 */
	@Override
	public void addAttachment(List<Attachment> attachmentList,String mainid) throws RuntimeException{
		if (attachmentList!= null && !attachmentList.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachmentList) {
				attachment.setMainid(mainid);
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 修改附件
	 * @param attachment,mainid
	 * @develop date 2016.11.16
	 * @throws BusinessException 
	 */
	@Override
	public void eidtAttachment(List<Attachment> attachmentList,String mainid, String dprtcode) throws RuntimeException{
		if (attachmentList!=null && !attachmentList.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachmentList) {
				if (attachment.getOperate()==null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(mainid);
					attachment.setDprtcode(dprtcode);
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
				}
				else if(attachment.getOperate().equals(OperateEnum.DEL)){
					attachmentMapper.delFile(attachment);
				}
				else if(attachment.getOperate().equals(OperateEnum.EDIT)){
					
				}
			}
		}
	}
	
	/**
	 * @Description 修改附件
	 * @CreateTime 2017-8-23 下午6:16:44
	 * @CreateBy 刘兵
	 * @param attachmentList 附件集合
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param dprtcode 机构代码
	 * @param logopration 操作
	 */
	@Override
	public void eidtAttachment(List<Attachment> attachmentList,String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration){
		if (attachmentList!=null && !attachmentList.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : attachmentList) {
				// 后缀名截取10位
				if(StringUtils.isNotBlank(attachment.getHzm()) && attachment.getHzm().length() > 10){
					attachment.setHzm(attachment.getHzm().substring(0, 10));
				}
				if (attachment.getOperate()==null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(mainid);
					attachment.setDprtcode(dprtcode);
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
				}
				else if(attachment.getOperate().equals(OperateEnum.DEL)){
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
					attachmentMapper.deleteByPrimaryKey(attachment.getId());
				}
				else if(attachment.getOperate().equals(OperateEnum.EDIT)){
					
				}
			}
		}
	}
	
	@Override
	public List<Attachment> queryListAttachments(Attachment attachment) {
		return attachmentMapper.queryListAttachments(attachment);
	}
	
	@Override
	public Attachment selectByPrimaryKey(String id) {
		return attachmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Attachment> selectListAttachments(String mainid) {
		return attachmentMapper.selectListAttachments(mainid);
	}

	@Override
	public int updateByPrimaryKey(String id) {
		return attachmentMapper.updateByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Attachment attachment) {
		return attachmentMapper.updateByPrimaryKeySelective(attachment);
	}

	@Override
	public List<Attachment> selectListAttachmentsByPlane(String mainid) {
		return attachmentMapper.selectListAttachmentsByPlane(mainid);
	}

	@Override
	public List<Attachment> selectAttachments(String mainid) {
		return attachmentMapper.selectAttachments(mainid);
	}

	@Override
	public Attachment loadAttachmentByIdAndType(String id, Integer type) {
		Attachment attachment;
		// 根据不同类型获取附件
		if(type == 1){	// D_011
			attachment = attachmentMapper.selectByPrimaryKey(id);
		} else if(type == 2){	// B_G_00101
			attachment = attachmentMapper.selectTechnicalByPrimaryKey(id);
			if(StringUtils.isNotBlank(attachment.getHzm())){
				attachment.setWbwjm(attachment.getWbwjm()+"."+attachment.getHzm());
			}
		} else if(type == 3){	// B_G_00603
			attachment = attachmentMapper.selectWOJobEnclosureByPrimaryKey(id);
		} else{
			attachment = new Attachment();
		}
		attachment.setType(type);
		return attachment;
	}
	@Override
	public List<Attachment> queryPlaneDataListAttachments(Attachment attachment) {
	
		return attachmentMapper.queryPlaneDataListAttachments(attachment);
	}
	
	public List<Attachment> queryPlaneDataListAttachmentsByMainids(List<String> mainidList) {
		
		return attachmentMapper.queryPlaneDataListAttachmentsByMainIds(mainidList);
	}
	
	/**
	 * @Description 新增附件
	 * @CreateTime 2017-8-23 下午6:16:44
	 * @CreateBy 刘兵
	 * @param attachment 附件
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param dprtcode 机构代码
	 * @param logopration 操作
	 */
	public String addAttachment(Attachment attachment,String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration){
		String id = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		attachment.setMainid(mainid);
		attachment.setDprtcode(dprtcode);
		attachment.setId(id);
		attachment.setCzrid(user.getId());
		attachment.setCzbmid(user.getBmdm());
		attachmentMapper.addFile(attachment);
		commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.SAVE,zdid);
		return id;
	}
	
	/**
	 * @Description 编辑附件
	 * @CreateTime 2017-8-23 下午6:46:22
	 * @CreateBy 刘兵
	 * @param attachment 附件
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param dprtcode 机构代码
	 * @param logopration 操作
	 */
	public void editAttachment(Attachment attachment,String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration){
		User user = ThreadVarUtil.getUser();
		attachment.setCzrid(user.getId());
		attachment.setCzbmid(user.getBmdm());
		attachmentMapper.updateById(attachment);
		commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
	}
	
	/**
	 * @Description 删除附件
	 * @CreateTime 2017-8-23 下午6:47:04
	 * @CreateBy 刘兵
	 * @param mainid 附件mainid
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void delFiles(String mainid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除附件
		User user = ThreadVarUtil.getUser();
		columnValueList.add(mainid);
		commonRecService.write("mainid", columnValueList, TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		attachmentMapper.deleteByMaind(mainid);
	}

	/**
	 * 
	 * @Description //根据评估单id删除附件
	 * @CreateTime 2017年8月24日 下午3:50:07
	 * @CreateBy 林龙
	 * @param id 评估单id
	 * @param czls 流水
	 */
	@Override
	public void deleteAttachment(String id, String czls) {
		User user = ThreadVarUtil.getUser();
		
		//根据评估单id查询附件集合
		List<Attachment> AttachmentList=attachmentMapper.selectAttachments(id);
		
		for (Attachment attachment : AttachmentList) {
			commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, id);
			attachmentMapper.delFiles(attachment);
		}
	}
	
	/**
	 * @Description 删除附件
	 * @CreateTime 2017-8-23 下午6:47:04
	 * @CreateBy 孙霁
	 * @param id 附件id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	public void deleteById(String id,String czls,String zdid, LogOperationEnum logopration){
		User user = ThreadVarUtil.getUser();
		commonRecService.write(id, TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		attachmentMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 
	 * @Description 新增结论附件
	 * @CreateTime 2017年8月27日 下午9:35:53
	 * @CreateBy 林龙
	 * @param attachment
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param dprtcode
	 * @param logopration
	 */
	public String addAssessmentAttachment(Attachment attachment,String czls,String zdid, String dprtcode, LogOperationEnum logopration){
		User user = ThreadVarUtil.getUser();
		String uuid=UUID.randomUUID().toString();
		String mainid=UUID.randomUUID().toString();
		attachment.setMainid(mainid);
		attachment.setDprtcode(dprtcode);
		attachment.setId(uuid);
		attachment.setCzrid(user.getId());
		attachment.setCzbmid(user.getBmdm());
		attachmentMapper.addFile(attachment);
		commonRecService.write(uuid, TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.SAVE,zdid);
		
		return mainid;
	}

	/**
	 * 
	 * @Description 删除结论附件
	 * @CreateTime 2017年8月27日 下午9:37:31
	 * @CreateBy 林龙
	 * @param assessmentAttachment
	 * @param uuid
	 * @param czls
	 * @param czls2
	 * @param jgdm
	 * @param saveWo
	 */
	@Override
	public void deleteByMainId(String deleteUploadId, String czls, String id,LogOperationEnum logopration) {
		User user = ThreadVarUtil.getUser();
		attachmentMapper.deleteByMaind(deleteUploadId);
		commonRecService.write(deleteUploadId, TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, id);
	}
	
	/**
	 * @Description 改版时新增附件
	 * @CreateTime 2017-8-23 下午6:16:44
	 * @CreateBy 刘兵
	 * @param attachmentList 附件集合
	 * @param fid 上个版本mainid
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param dprtcode 机构代码
	 * @param logopration 操作
	 */
	@Override
	public void saveAttachment4Modify(List<Attachment> attachmentList, String fid, String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration){
		List<Attachment> batchAttachmentList = new ArrayList<Attachment>();//批量插入的附件集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		Map<String, String> idMap = new HashMap<String, String>();//用于存放页面删除的附件
		User user = ThreadVarUtil.getUser();
		if(attachmentList!=null && !attachmentList.isEmpty()){
			for (Attachment attachment : attachmentList) {
				if (attachment.getOperate()==null || attachment.getOperate().equals(OperateEnum.ADD)) {
					attachment.setMainid(mainid);
					attachment.setDprtcode(dprtcode);
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					attachment.setCzbmid(user.getBmdm());
					columnValueList.add(attachment.getId());
					batchAttachmentList.add(attachment);
				}
				else if(attachment.getOperate().equals(OperateEnum.DEL)){
					idMap.put(attachment.getId(), attachment.getId());
				}
			}
		}
		//将上个版本的附件放入批量插入集合
		for(Attachment attachment : attachmentMapper.selectAttachments(fid)){
			if(null == idMap.get(attachment.getId())){
				attachment.setMainid(mainid);
				attachment.setDprtcode(dprtcode);
				attachment.setId(UUID.randomUUID().toString());
				columnValueList.add(attachment.getId());
				batchAttachmentList.add(attachment);
			}
		}
		//批量插入附件
		if(batchAttachmentList.size() > 0){
			attachmentMapper.insert4Batch(batchAttachmentList);
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
		}
	}
	
}
