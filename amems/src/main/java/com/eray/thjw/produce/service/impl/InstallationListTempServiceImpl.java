package com.eray.thjw.produce.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.InstallationListEditableMapper;
import com.eray.thjw.produce.dao.InstallationListTempMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListTemp;
import com.eray.thjw.produce.service.AircraftinfoService;
import com.eray.thjw.produce.service.InstallationListTemp2InitService;
import com.eray.thjw.produce.service.InstallationListTempService;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.service.MaintenanceProjectService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.PartSnValidationEnum;
import enu.project2.MaintenanceProjectTypeEnum;

/**
 * @Description 装机清单-临时区service实现类
 * @CreateTime 2017年10月26日 下午2:46:27
 * @CreateBy 韩武
 */
@Service
public class InstallationListTempServiceImpl implements InstallationListTempService{
	
	@Resource
	private InstallationListTempMapper installationListTempMapper;
	
	@Resource
	private InstallationListEditableMapper installationListEditableMapper;
	
	@Resource
	private InstallationListTemp2InitService installationListTemp2InitService;
	
	@Resource
	private ComponentCertificateService componentCertificateService;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private StockSerivce stockSerivce;
	
	@Resource
	private AircraftinfoService aircraftinfoService;
	
	@Resource
	private MaintenanceProjectService maintenanceProjectService;
	
	/** 一级部件 */
	private final Integer INSTALLATIONLIST_LEVEL_ONE = 1;

	/**
	 * @Description 保存装机清单-临时区
	 * @CreateTime 2017年10月26日 下午2:45:06
	 * @CreateBy 韩武
	 * @param temp
	 * @param id 装机清单id，可传null
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public String save(InstallationListTemp record, String id) throws BusinessException {
		
		// 保存装机清单主数据
		saveInstallation(record, id);
		
		// 更新航材数据
		updateMaterial(record);
		
		// 保存证书
		saveComponentCertificate(record);
		
		// 保存初部件始化数据
		installationListTemp2InitService.save(record);
		
		return record.getId();
	}
	
	/**
	 * @Description 保存装机清单主数据
	 * @CreateTime 2017年9月28日 下午4:37:43
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	private String saveInstallation(InstallationListTemp record, String id) throws BusinessException{
		
		// 验证装机清单数据
		//validate(record);
		
		// 判断时控件/定检件
		judgeMonitorType(record);
		
		// 小时转化为分钟
		swtichHourToMinute(record);
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setWhbmid(user.getBmdm());
		if(StringUtils.isBlank(record.getCzls())){
			record.setCzls(UUID.randomUUID().toString());//操作流水
		}
		
		if (StringUtils.isBlank(record.getId())){	// 新增装机清单数据
			if(StringUtils.isNotBlank(id)){
				record.setId(id);
			}else{
				record.setId(UUID.randomUUID().toString());
			}
			record.setXgzjqdid(record.getId());
			if(record.getLogOperationEnum() == null){
				record.setLogOperationEnum(LogOperationEnum.SAVE_WO);
			}
			installationListTempMapper.insertSelective(record);
			// 保存历史记录信息
			commonRecService.write(record.getId(), TableEnum.B_S2_002, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getFxjldid());
			
		} else {	// 修改装机清单数据
			if(record.getLogOperationEnum() == null){
				record.setLogOperationEnum(LogOperationEnum.EDIT);
			}
			installationListTempMapper.updateByFLB(record);
			// 保存历史记录信息
			commonRecService.write(record.getId(), TableEnum.B_S2_002, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.UPDATE, record.getFxjldid());
		}
		
		return record.getId();
	}
	
	/**
	 * @Description 判断监控类型
	 * @CreateTime 2017年12月5日 上午9:46:08
	 * @CreateBy 韩武
	 * @param zsj
	 * @throws BusinessException 
	 */
	private void judgeMonitorType(InstallationListTemp zsj) throws BusinessException{
		Aircraftinfo info = new Aircraftinfo();
		info.setFjzch(zsj.getFjzch());
		info.setDprtcode(zsj.getDprtcode());
		info = aircraftinfoService.selectByfjzchAndDprtcode(info);
		
		if(info != null){
			InstallationListEditable record = new InstallationListEditable();
			record.setFjzch(info.getFjzch());
			record.setDprtcode(zsj.getDprtcode());
			record.setJx(info.getFjjx());
			record.setBjh(zsj.getBjh());
			List<MaintenanceProject> list = maintenanceProjectService.queryApplyList(record);
			
			Integer skbs = 0;
			Integer ssbs = 0;
			for (MaintenanceProject project : list) {
				if(MaintenanceProjectTypeEnum.TIMECONTROL.getId().equals(project.getWxxmlx())){
					skbs = 1;
				}
				if(MaintenanceProjectTypeEnum.WHENLIFE.getId().equals(project.getWxxmlx())){
					ssbs = 1;
				}
			}
			zsj.setSkbs(skbs);
			zsj.setSsbs(ssbs);
		}
	}
	
	/**
	 * @Description 小时转化为分钟
	 * @CreateTime 2017年10月12日 下午1:35:15
	 * @CreateBy 韩武
	 * @param record
	 */
	private void swtichHourToMinute(InstallationListTemp record){
		if(record != null){
			if(StringUtils.isNotBlank(record.getTsn())){
				record.setTsn(StringAndDate_Util.convertToMinuteStr(record.getTsn()));
			}
			if(StringUtils.isNotBlank(record.getTso())){
				record.setTso(StringAndDate_Util.convertToMinuteStr(record.getTso()));
			}
		}
	}
	
	/**
	 * @Description 验证装机清单数据
	 * @CreateTime 2017年9月28日 下午4:38:02
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	private void validate(InstallationListTemp record) throws BusinessException{
		
		// 验证一级部件（除机身）位置唯一
//		if(!InstalledPositionEnum.BODY.getId().equals(record.getWz())
//				&& INSTALLATIONLIST_LEVEL_ONE.equals(record.getCj())){
//			
//			InstallationListEditable param = new InstallationListEditable(record);
//			if(installationListEditableMapper.getLevelOneCount(param) > 0){
//				throw new BusinessException("位置为" + InstalledPositionEnum.getName(record.getWz())+ 
//						"的一级部件最多只能有一个！");
//			}
//			
//		}
		
		//验证件号+序列号唯一
		if(StringUtils.isNotBlank(record.getXlh())){
			stockSerivce.getCount4ValidationBjAndXlh(record.getId(), record.getBjh(), record.getXlh(), record.getDprtcode(), PartSnValidationEnum.INSTALL.getId());
		}
	}
	
	/**
	 * @Description 更新航材数据
	 * @CreateTime 2017年10月10日 上午11:35:15
	 * @CreateBy 韩武
	 * @param record
	 */
	private void updateMaterial(InstallationListTemp record){
		InstallationListEditable param = new InstallationListEditable(record);
		hCMainDataMapper.updateByInstallationList(param);
	}
	
	/**
	 * @Description 保存证书
	 * @CreateTime 2017年10月26日 下午3:34:25
	 * @CreateBy 韩武
	 * @param record
	 */
	private void saveComponentCertificate(InstallationListTemp record){
		InstallationListEditable param = new InstallationListEditable(record);
		param.setCzls(record.getCzls());
		param.setLogOperationEnum(record.getLogOperationEnum());
		param.setCertificates(record.getCertificates());
		componentCertificateService.save(param);
	}
	/**
	 * 
	 * @Description 删除装机清单-临时区
	 * @CreateTime 2017年11月25日 上午10:40:29
	 * @CreateBy 岳彬彬
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void delete(String id) throws BusinessException {
		
		installationListTempMapper.deleteByPrimaryKey(id);
	}

	
}
