package com.eray.thjw.project2.service.impl;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.TechnicalAttachedMapper;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.TechnicalAttached;
import com.eray.thjw.project2.service.TechnicalAttachedService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @Description 技术评估单-附加信息Impl
 * @CreateTime 2017年8月16日 上午10:22:32
 * @CreateBy 林龙
 */
@Service("technicalAttachedService")
public class TechnicalAttachedServiceImpl implements TechnicalAttachedService{

	@Resource
	private TechnicalAttachedMapper technicalAttachedMapper;
	
	@Resource
	private TodorsService todorsService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * 
	 * @Description 新增技术文件技术评估单-附加信息
	 * @CreateTime 2017年8月17日 下午5:31:40
	 * @CreateBy 林龙
	 * @param technicalAttached
	 * @param mainid 评估单主表id
	 * @param czls 评估单流水号
	 * @throws BusinessException
	 */
	@Override
	public void insertTechnicalAttached(Technical technical,String mainid, String czls) throws BusinessException {
		TechnicalAttached technicalAttached=new TechnicalAttached();
		technicalAttached=technical.getTechnicalAttached();
		String uuid = UUID.randomUUID().toString();//uuid
		User user = ThreadVarUtil.getUser();//当前登陆人
		technicalAttached.setId(uuid);
		technicalAttached.setMainid(mainid);//评估单主表id
		String jlfjid=null;
		if(technical.getAssessmentAttachment()!=null){
			 jlfjid=attachmentService.addAssessmentAttachment(technical.getAssessmentAttachment(),czls, czls, user.getJgdm(), LogOperationEnum.SAVE_WO);
		}
		//新增结论附件
		
		technicalAttached.setJlfjid(jlfjid);
		//新增技术评估单-附加信息
		technicalAttachedMapper.insertSelective(technicalAttached);
		
		//添加新增技术评估单-附加信息日志
		commonRecService.write(uuid, TableEnum.B_G2_00100, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,mainid);
	
		todorsService.updatetByYwid(technical.getId(), technical.getBb(),technical.getPgdh());// 更新待办事宜状态
	}

	/**
	 * 
	 * @Description 修改技术文件技术评估单-附加信息
	 * @CreateTime 2017年8月17日 下午5:31:40
	 * @CreateBy 林龙
	 * @param technicalAttached
	 * @param mainid 评估单主表id
	 * @param czls 评估单流水号
	 * @throws BusinessException
	 */
	@Override
	public void updateTechnicalAttached(Technical technical,String mainid, String czls) throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		TechnicalAttached technicalAttached=new TechnicalAttached();
		technicalAttached=technical.getTechnicalAttached();
		
		//是否删除附件
		if(technical.getDeleteUploadId() != null && !"".equals(technical.getDeleteUploadId())){
			attachmentService.deleteByMainId(technical.getDeleteUploadId(), czls, technicalAttached.getId(), LogOperationEnum.DELETE);
			technicalAttached.setJlfjid("");
		}
		
		//2) 判断是否有附件id
		Attachment attachment = technical.getAssessmentAttachment();
		if(attachment != null && attachment.getBiaoshi() != null && "edit".equals(attachment.getBiaoshi()) ){
			List<Attachment> attachmentList = attachmentService.selectListAttachmentsByPlane(technicalAttached.getId());
			if(attachmentList != null && attachmentList.size() > 0 ){
				//不变
				attachment.setId(attachmentList.get(0).getId());
				attachmentService.editAttachment(attachment, technicalAttached.getId(), czls, technicalAttached.getId(), user.getJgdm(), LogOperationEnum.EDIT);
			}else{
				//新增结论附件
				String jlfjid=attachmentService.addAssessmentAttachment(attachment,czls, czls, user.getJgdm(), LogOperationEnum.SAVE_WO);
				
				technicalAttached.setJlfjid(jlfjid);
			}
		}
		
		//修改技术评估单-附加信息
		technicalAttachedMapper.updateByPrimaryKeySelective(technicalAttached);
		
		//添加修改技术评估单-附加信息日志
		commonRecService.write(technicalAttached.getId(), TableEnum.B_G2_00100, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,mainid);
	
	}

	/**
	 * 
	 * @Description  根据mainid 技术评估单id查询 技术评估单附表
	 * @CreateTime 2017年8月22日 上午9:57:30
	 * @CreateBy 林龙
	 * @param mainid 技术评估单id
	 * @return 数量
	 * @throws BusinessException
	 */
	@Override
	public int selectByMainidCount(String mainid) throws BusinessException {
		
		return technicalAttachedMapper.selectByMainidCount(mainid);
	}

	/**
	 * 
	 * @Description 根据mainid 技术评估单id删除 技术评估单附表
	 * @CreateTime 2017年8月24日 下午3:23:44
	 * @CreateBy 林龙
	 * @param mainid  技术文件评估单id
	 * @param czls 	流水号
	 */
	@Override
	public void deleteTechnicalAttached(String mainid, String czls) {
		
		//根据mainid 技术评估单id查询 技术评估单附表集合
		TechnicalAttached technicalAttached = technicalAttachedMapper.selectByMainid(mainid);
		
		if(technicalAttached != null){
			
			//添加删除技术评估单-附加信息日志
			commonRecService.write(technicalAttached.getId(), TableEnum.B_G2_00100, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE,mainid);
			
			//删除技术评估单-附加信息日志
			technicalAttachedMapper.deleteByPrimaryKey(technicalAttached.getId());	
		}
	}

	

}
