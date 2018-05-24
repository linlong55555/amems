package com.eray.thjw.flightdata.dao;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.flightdata.po.Attachment;

public interface AttachmentMapper {
	
	int insertSelective(Attachment record);                 //增加航材检验单的附件
	
	Attachment selectByPrimaryKey(String id);
	
	int deleteByPrimaryKey(String id);
    int insert(Attachment record);
    
    
    
    /**
	 * 上传附件
	 * @param attachments
	 */
	void addFile(Attachment attachment);

	/**
	 * 查询附件列表
	 * @param attachment
	 * @return
	 */
	List<Attachment> queryListAttachments(Attachment attachment);
	
	/**
	 * 查询附件列表
	 * @param attachment
	 * @return
	 */
	List<Attachment> queryPlaneDataListAttachmentsByMainIds(List<String> mainidList);

	/**
	 * 逻辑删除附件
	 * @param attachment
	 */
	void delFile(Attachment attachment);
	
	/**
	 * 批量逻辑删除附件
	 * @param attachment
	 */
	void delFiles(Attachment attachment);
	
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
	 * 根据mainid查询附件集合
	 * @param attachment
	 * @return
	 */
	List<Attachment> selectAttachments(String mainid);
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
	 * 文件作废更改对应附件状态
	 * @param mainid
	 */
	void updateByMainid(String mainid);
	
	/**
	 * 获取技术文件附件
	 * @param id
	 * @return
	 */
	Attachment selectTechnicalByPrimaryKey(String id);
	
	/**
	 * 获取工单附件
	 * @param id
	 * @return
	 */
	Attachment selectWOJobEnclosureByPrimaryKey(String id);
	/**
	 * 获取飞机注册附件
	 * @param attachment
	 * @return
	 */
	List<Attachment> queryPlaneDataListAttachments(Attachment attachment);
	/**
	 * 批量修改
	 * @param ids
	 * @return
	 */
	void delFileByids(List<String> ids);
	
	int updateById(Attachment attachment);

	void deleteByMaind(String deleteUploadId);
	
	/**
	 * @Description 批量新增附件
	 * @CreateTime 2017-9-11 上午11:02:02
	 * @CreateBy 刘兵
	 * @param attachmentList 附件集合
	 * @return int
	 */
    int insert4Batch(List<Attachment> attachmentList);
    /**
     * 
     * @Description 复制一个附件
     * @CreateTime 2017年10月10日 下午2:36:28
     * @CreateBy 岳彬彬
     * @param attachment
     */
    void insertAttachemnt4Copy(Attachment attachment);
    
    /**
     * @Description 复制一个附件
     * @CreateTime 2017-10-17 下午5:15:09
     * @CreateBy 刘兵
     * @param newmainid 新的mainid
     * @param mainid 条件mainid
     */
    void insert4Copy(@Param("newmainid")String newmainid, @Param("mainid")String mainid);
    
    /**
     * @Description 查询工程文件管理中某个文件下的所有附件
     * @CreateTime 2018年1月26日 上午11:33:20
     * @CreateBy 韩武
     * @param id
     * @return
     */
    List<Attachment> query4FileCatalog(String id);
}