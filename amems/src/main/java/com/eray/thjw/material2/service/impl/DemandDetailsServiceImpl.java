package com.eray.thjw.material2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.DemandDetailsMapper;
import com.eray.thjw.material2.dao.DemandProtectionDetailsMapper;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandDetails;
import com.eray.thjw.material2.po.DemandProtectionDetails;
import com.eray.thjw.material2.service.DemandDetailsService;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.quality.dao.QualityAuditReportMapper;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.material2.DemandDetailsLogoEnum;

/**
 * 
 * @Description 需求明细impl
 * @CreateTime 2018年2月26日 下午3:25:13
 * @CreateBy 林龙
 */
@Service
public class DemandDetailsServiceImpl implements DemandDetailsService{
	@Resource
	private DemandDetailsMapper demandDetailsMapper;
	@Resource
	private DemandProtectionDetailsMapper demandProtectionDetailsMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private QualityAuditReportMapper qualityAuditReportMapper;
	/**
	 * 
	 * @Description 需求跟踪-查询需求信息
	 * @CreateTime 2018年2月27日 下午2:37:00
	 * @CreateBy 林龙
	 * @param demandDetails
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(DemandDetails demandDetails)throws BusinessException {
		
		PageHelper.startPage(demandDetails.getPagination());
		List<DemandDetails> list = demandDetailsMapper.queryAllPageList(demandDetails);
		return PageUtil.pack4PageHelper(list, demandDetails.getPagination());
	}
	
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年2月28日 上午11:04:11
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	@Override
	public void updateClose(DemandDetails demandDetails) throws BusinessException {
		validation4CurrentZt(demandDetails.getId(), Integer.valueOf((String) demandDetails.getParamsMap().get("currentXqbs")));
		
		//更新b_h_02401 需求明细-需求标识=9关闭
		demandDetails.setXqbs(DemandDetailsLogoEnum.COLSE.getId());
		demandDetailsMapper.updateByPrimaryKeySelective(demandDetails);
		
		//更新b_h_02801 需求保障明细-需求标识=9关闭,确认标识=0未确认
		DemandProtectionDetails demandProtectionDetails=new DemandProtectionDetails();
		demandProtectionDetails.setId(demandDetails.getId());
		demandProtectionDetails.setXqbs(9);
		demandProtectionDetails.setQrbs(0);
		demandProtectionDetailsMapper.updateByPrimaryKeySelective(demandProtectionDetails);
		
		//记录需求单的流程记录
		saveFlowByZt("关闭需求项目", demandDetails.getId());
	}
	/**
	 * 
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer xqbs)throws BusinessException {
		int bzt = demandDetailsMapper.getCurrentZt4Validation(id);
		if (bzt != xqbs) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}
	
	/**
	 * 
	 * @Description 记录需求单的流程记录
	 * @CreateTime 2018年2月28日 上午10:32:04
	 * @CreateBy 林龙
	 * @param status
	 * @param mainId
	 */
	private void saveFlowByZt(String czsm,String mainId) {
		Date currentDate = commonService.getSysdate();//当前时间
		User user = ThreadVarUtil.getUser();
		ProcessRecord record=new ProcessRecord();		
		record.setId(UUID.randomUUID().toString());
		record.setMainid(mainId);
		record.setDprtcode(user.getJgdm());
		record.setCzrmc(user.getRealname());
		record.setBmmc(user.getDepartment().getDprtname());
		record.setCzsj(currentDate);
		record.setCzsm(czsm);
		qualityAuditReportMapper.saveFlowRecord(record);
		
	}

	@Override
	public List<DemandDetails> queryDemandInfoList(DemandDetails demandDetails)throws BusinessException {
		return demandDetailsMapper.queryAllPageList(demandDetails);
	}

	@Override
	public List<DemandDetails> queryDemandProtectionInfoList(DemandDetails demandDetails) throws BusinessException {
		
		return demandDetailsMapper.queryDemandProtectionInfoList(demandDetails);
	}
}
