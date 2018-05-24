package com.eray.thjw.flightdata.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;

import enu.LogOperationEnum;

public interface AttachmentService {
    
	/**
	 * @author meizhiliang
	 * @description 增加航材检验单附件
	 * @develop date 2016.11.2
    */
	int insertSelective(List<Attachment> attachment,String mainid, String czls,LogOperationEnum logopration,String id) ;
	
	/**
	 * @author liub
	 * @description 增加附件
	 * @param attachment,mainid
	 * @develop date 2016.11.16
	 * @throws BusinessException 
	 */
	public void addAttachment(List<Attachment> attachmentList,String mainid) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description 修改附件
	 * @param attachment,mainid,dprtcode
	 * @develop date 2016.11.16
	 * @throws BusinessException 
	 */
	public void eidtAttachment(List<Attachment> attachmentList,String mainid, String dprtcode) throws RuntimeException;
	
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
	public void eidtAttachment(List<Attachment> attachmentList,String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * 查询附件列表
	 * @param attachment
	 * @return
	 */
	List<Attachment> queryListAttachments(Attachment attachment);
	
	/**
	 * 查一个附件
	 * @param id
	 * @return
	 */
	Attachment selectByPrimaryKey(String id);
	/**
	 * 培训课程附件集合
	 * @param attachment
	 * @return
	 */
	List<Attachment> selectListAttachments(String mainid);
	/**
	 * 培训课程附件集合
	 * @param attachment
	 * @return
	 */
	List<Attachment> selectListAttachmentsByPlane(String mainid);
	/**
	 * 逻辑删除培训课程附件
	 * @param attachment
	 * @return
	 */
	int updateByPrimaryKey(String id);
	/**
	 * copy原课程的附件  与新课程绑定关系
	 * @param attachment
	 * @return
	 */
	int updateByPrimaryKeySelective(Attachment attachment);
	/**
	 * 根据mainid查询附件集合
	 * @param attachment
	 * @return
	 */
	List<Attachment> selectAttachments(String mainid);
	
	/**
	 * 根据id和类型读取附件
	 * @param id
	 * @param type
	 * @return
	 */
	Attachment loadAttachmentByIdAndType(String id, Integer type);

	/**
	 * 飞机注册获取附件
	 * @param attachment
	 * @return
	 */
	List<Attachment> queryPlaneDataListAttachments(Attachment attachment);
	
	public List<Attachment> queryPlaneDataListAttachmentsByMainids(List<String> mainidList);
	
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
	public String addAttachment(Attachment attachment,String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration);
	
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
	public void editAttachment(Attachment attachment,String mainid,String czls,String zdid, String dprtcode, LogOperationEnum logopration);
	
	/**
	 * @Description 删除附件
	 * @CreateTime 2017-8-23 下午6:47:04
	 * @CreateBy 刘兵
	 * @param mainid 附件mainid
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	public void delFiles(String mainid, String czls, String zdid, LogOperationEnum logopration);

	/**
	 * 
	 * @Description //根据评估单id删除附件
	 * @CreateTime 2017年8月24日 下午3:50:07
	 * @CreateBy 林龙
	 * @param id 评估单id
	 * @param czls 流水
	 */
	void deleteAttachment(String id, String czls);

	/**
	 * @Description 删除附件
	 * @CreateTime 2017-8-23 下午6:47:04
	 * @CreateBy 孙霁
	 * @param attachment 附件
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	public void deleteById(String id,String czls,String zdid, LogOperationEnum logopration);

	/**
	 * 
	 * @Description 新增结论附件
	 * @CreateTime 2017年8月27日 下午9:37:31
	 * @CreateBy 林龙
	 * @param assessmentAttachment
	 * @param uuid
	 * @param czls
	 * @param czls2
	 * @param jgdm
	 * @param saveWo
	 */
	public String addAssessmentAttachment(Attachment assessmentAttachment,String czls, String czls2, String jgdm, LogOperationEnum saveWo);

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
	public void deleteByMainId(String deleteUploadId, String czls, String id,LogOperationEnum delete);
	
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
	public void saveAttachment4Modify(List<Attachment> attachmentList, String fid, String mainid, String czls, String zdid, String dprtcode, LogOperationEnum logopration);
}
