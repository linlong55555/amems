package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ComponentCertificateMapper;
import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

@Service
public class ComponentCertificateServiceImpl implements ComponentCertificateService {
	
	@Resource
	private ComponentCertificateMapper componentCertificateMapper;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 查询部件证书集合
	 * @CreateTime 2017年9月28日 上午10:01:00
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<ComponentCertificate> queryList(ComponentCertificate record) {
		return componentCertificateMapper.queryList(record);
	}

	/**
	 * @Description 保存部件证书
	 * @CreateTime 2017年9月28日 下午5:03:51
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void save(InstallationListEditable record) {
		User user = ThreadVarUtil.getUser();
		
		// 删除部件证书
		delete(record);		
		
		// 保存部件证书
		if (record.getCertificates() != null 
				&& !record.getCertificates().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (ComponentCertificate cert : record.getCertificates()) {
				
				cert.setId(UUID.randomUUID().toString());
				cert.setJh(record.getBjh());
				cert.setXlh(record.getXlh());
				cert.setPch(record.getPch());
				cert.setZt(EffectiveEnum.YES.getId());
				cert.setWhrid(user.getId());
				cert.setWhsj(new Date());
				cert.setWhbmid(user.getBmdm());
				cert.setDprtcode(record.getDprtcode());
				
				if(StringUtils.isNotBlank(cert.getJh())){
					idList.add(cert.getId());
					componentCertificateMapper.insertSelective(cert);
					
					// 保存附件
					attachmentService.eidtAttachment(cert.getAttachments(), cert.getId(), 
							record.getCzls(), record.getZbid(), record.getDprtcode(), record.getLogOperationEnum());
				}
			}
			
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_H2_024, user.getId(), 
						record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getZbid());
			}
		}
		
	}
	
	/**
	 * @Description 删除部件证书
	 * @CreateTime 2017年9月28日 下午5:04:34
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void delete(InstallationListEditable record) {
		
		if(StringUtils.isNotBlank(record.getXlh())){
			record.setPch("-");
		}else{
			record.setXlh("-");
			if(StringUtils.isBlank(record.getPch())){
				record.setPch("-");
			}
		}
		
		User user = ThreadVarUtil.getUser();
		List<String> delList = componentCertificateMapper.selectByKey(record);
		
		if(!delList.isEmpty()){
			// 删除附件
			for (String mainid : delList) {
				attachmentService.delFiles(mainid, record.getCzls(), record.getZbid(), record.getLogOperationEnum());
			}
			
			// 保存历史记录信息
			commonRecService.write("id", delList, TableEnum.B_H2_024, user.getId(), 
					record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getZbid());
			
			// 删除部件证书
			componentCertificateMapper.deleteByKey(record);
		}
	}

	/**
	 * 
	 * @Descriptiont 添加证书
	 * @CreateTime 2017-11-14 上午9:57:56
	 * @CreateBy 孙霁
	 * @param record
	 */
	@Override
	public void add(InstallationListEditable record) {
		User user = ThreadVarUtil.getUser();
		
		// 删除部件证书
		String czls = UUID.randomUUID().toString();
		deleteById(record,czls);		
		// 保存部件证书	
		if (record.getCertificates() != null 
				&& !record.getCertificates().isEmpty()){
			
			for (ComponentCertificate cert : record.getCertificates()) {
				
				cert.setId(UUID.randomUUID().toString());
				cert.setJh(record.getBjh());
				cert.setXlh(record.getXlh());
				cert.setPch(record.getPch());
				cert.setZt(EffectiveEnum.YES.getId());
				cert.setWhrid(user.getId());
				cert.setWhsj(new Date());
				cert.setWhbmid(user.getBmdm());
				cert.setDprtcode(record.getDprtcode());
				
				if(StringUtils.isNotBlank(cert.getJh())){
					componentCertificateMapper.insertSelective(cert);
					//添加日志
					commonRecService.write(cert.getId(), TableEnum.B_H2_024, user.getId(), czls, LogOperationEnum.UPDATE_UPDATE, UpdateTypeEnum.SAVE, cert.getId());
					
					// 保存附件
					attachmentService.eidtAttachment(cert.getAttachments(), cert.getId(), 
							czls, cert.getId(), record.getDprtcode(), LogOperationEnum.UPDATE_UPDATE);
				}
			}
		}
		
	}
	
	private void deleteById(InstallationListEditable record,String czls){
		if(StringUtils.isNotBlank(record.getXlh())){
			record.setPch("-");
		}else{
			record.setXlh("-");
			if(StringUtils.isBlank(record.getPch())){
				record.setPch("-");
			}
		}
		
		User user = ThreadVarUtil.getUser();
		List<String> delList = componentCertificateMapper.selectByKey(record);
		
		if(!delList.isEmpty()){
			// 删除附件
			for (String mainid : delList) {
				attachmentService.delFiles(mainid, czls, mainid, LogOperationEnum.UPDATE_UPDATE);
			}
			
			for (String delId : delList) {
				commonRecService.write(delId, TableEnum.B_H2_024, user.getId(), czls, LogOperationEnum.UPDATE_UPDATE, UpdateTypeEnum.DELETE, delId);
			}
			/*// 保存历史记录信息
			commonRecService.write("id", delList, TableEnum.B_H2_024, user.getId(), 
					czls, LogOperationEnum.UPDATE_UPDATE, UpdateTypeEnum.DELETE, record.getId());*/
			
			// 删除部件证书
			componentCertificateMapper.deleteByKey(record);
		}
	}

	/**
	 * 
	 * @Description 根据条件查询数据
	 * @CreateTime 2018-3-19 下午4:57:32
	 * @CreateBy 孙霁
	 * @param record
	 */
	@Override
	public List<ComponentCertificate> selectByParams(ComponentCertificate record) {
		return componentCertificateMapper.selectByParams(record);
	}
	
}

