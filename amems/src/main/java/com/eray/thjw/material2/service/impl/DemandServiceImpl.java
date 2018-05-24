package com.eray.thjw.material2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.DemandDetailsMapper;
import com.eray.thjw.material2.dao.DemandMapper;
import com.eray.thjw.material2.dao.DemandProtectionDetailsMapper;
import com.eray.thjw.material2.dao.DemandProtectionMapper;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandDetails;
import com.eray.thjw.material2.po.DemandProtection;
import com.eray.thjw.material2.po.DemandProtectionDetails;
import com.eray.thjw.material2.service.DemandService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.QualityAuditReportMapper;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.material2.DemandDetailsLogoEnum;
import enu.material2.DemandStatusEnum;

/**
 * 
 * @Description 需求impl
 * @CreateTime 2018年2月26日 下午3:25:13
 * @CreateBy 林龙
 */
@Service("demandService")
public class DemandServiceImpl implements DemandService{

	@Resource
	private DemandMapper demandMapper;
	@Resource
	private DemandDetailsMapper demandDetailsMapper;
	@Resource
	private QualityAuditReportMapper qualityAuditReportMapper;
	@Resource
	private DemandProtectionDetailsMapper demandProtectionDetailsMapper;
	@Resource
	private DemandProtectionMapper demandProtectionMapper;
	@Resource
	private CommonService commonService;
	/**
	 * @Description 需求跟踪-需求列表
	 * @CreateTime 2018年2月26日 下午3:27:57
	 * @CreateBy 林龙
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Demand> queryAllList(Demand demand)throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		demand.getParamsMap().put("userId", user.getId());
		demand.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		return demandMapper.queryAllList(demand);
	}
	/**
	 * 
	 * @Description 需求跟踪-已关闭需求列表
	 * @CreateTime 2018年2月28日 上午9:49:14
	 * @CreateBy 林龙
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllClosePageList(Demand demand)throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登陆人
		demand.getParamsMap().put("userId", user.getId());
		demand.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(demand.getPagination());
		List<Demand> list = demandMapper.queryAllClosePageList(demand);
		return PageUtil.pack4PageHelper(list, demand.getPagination());
	}
	
	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2018年2月28日 上午10:04:43
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	@Override
	public void delete(Demand demand) throws BusinessException {
//		User user = ThreadVarUtil.getUser();		//当前登陆人
//		Date currentDate = commonService.getSysdate();//当前时间
		// 验证单据状态是否已变更
		validation4CurrentZt(demand.getId(), Integer.valueOf((String) demand.getParamsMap().get("currentZt")));
		
		//删除需求明细
		demandDetailsMapper.deleteByPrimaryMainid(demand.getId());
		
		//删除需求
		demandMapper.deleteByPrimaryKey(demand.getId());
	}
	
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2018年2月28日 上午10:04:43
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	@Override
	public void updateClose(Demand demand) throws BusinessException {
//		User user = ThreadVarUtil.getUser();		//当前登陆人
//		Date currentDate = commonService.getSysdate();//当前时间
		// 验证单据状态是否已变更
		validation4CurrentZt(demand.getId(), Integer.valueOf((String) demand.getParamsMap().get("currentZt")));
		
		//跟新需求状态=9关闭
		demand.setZt(DemandStatusEnum.COLSE.getId());
		demandMapper.updateByPrimaryKeySelective(demand);
		
		//记录需求单的流程记录
		saveFlowByZt("关闭申请", demand.getId());
		
		List<DemandDetails> demandDetaillist =demandDetailsMapper.selectByPrimaryMainid(demand.getId());
		
		for (DemandDetails demandDetails : demandDetaillist) {
			if(demandDetails.getXqbs() != DemandDetailsLogoEnum.CAN.getId() || demandDetails.getXqbs() != DemandDetailsLogoEnum.COLSE.getId()){
				
				//更新b_h_02401 需求明细-需求标识=9关闭
				demandDetails.setXqbs(DemandDetailsLogoEnum.COLSE.getId());
				demandDetailsMapper.updateByPrimaryKey(demandDetails);
				
				//更新b_h_02801 需求保障明细-需求标识=9关闭,确认标识=0未确认
				DemandProtectionDetails demandProtectionDetails=new DemandProtectionDetails();
				demandProtectionDetails.setId(demandDetails.getId());
				demandProtectionDetails.setXqbs(9);
				demandProtectionDetails.setQrbs(0);
				demandProtectionDetailsMapper.updateByPrimaryKeySelective(demandProtectionDetails);
				
				//记录需求单的流程记录
				saveFlowByZt("关闭需求项目", demandDetails.getId());
			}
		}
	}
	
	
	/**
	 * 
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer zt)throws BusinessException {
		int bzt = demandMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
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
		if(user.getDepartment()!=null){
			record.setBmmc(user.getDepartment().getDprtname());
		}
		record.setCzsj(currentDate);
		record.setCzsm(czsm);
		qualityAuditReportMapper.saveFlowRecord(record);
		
	}
	
	/**
	 * 
	 * @Description 审批通过/审批驳回
	 * @CreateTime 2018年3月2日 上午10:36:45
	 * @CreateBy 林龙
	 * @param demand
	 * @throws BusinessException
	 */
	@Override
	public void updateSubApprove(Demand demand) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证单据状态是否已变更
		validation4CurrentZt(demand.getId(), Integer.valueOf((String) demand.getParamsMap().get("currentZt")));
		
		if(demand.getZt() == DemandStatusEnum.APP.getId()){//当当前状态为已审批
			demand.setJhsj(currentDate);
			
			//修改b_h_024 需求
			demandMapper.updateByPrimaryKeySelective(demand);
			
			//记录需求单的流程记录
			saveFlowByZt("审批通过 "+demand.getJhyj(), demand.getId());
			
			Demand demandNew=demandMapper.selectByPrimaryKey(demand.getId());
			//同步到b_h_028 需求保障主表
			DemandProtection demandProtection=demandProtectionMapper.selectByPrimaryKey(demand.getId());
			if(demandProtection != null){
				demandProtection.setJjcd(demandNew.getJjcd());
				demandProtection.setIsYxfx(demandNew.getIsYxfx());
				demandProtection.setIsFjhtc(demandNew.getIsFjhtc());
				demandProtection.setXqlb(demandNew.getXqlb());
				demandProtection.setJhsyrq(demandNew.getJhsyrq());
				demandProtection.setSjbjh(demandNew.getSjbjh());
				demandProtection.setSjbjmc(demandNew.getSjbjmc());
				demandProtection.setFjzch(demandNew.getFjzch());
				demandProtection.setXlh(demandNew.getXlh());
				demandProtection.setFxsj(demandNew.getFxsj());
				demandProtection.setFxxh(demandNew.getFxxh());
				demandProtection.setXqyy(demandNew.getXqyy());
				demandProtection.setGmjy(demandNew.getGmjy());
				demandProtection.setLylx(demandNew.getLylx());
				demandProtection.setLyid(demandNew.getLyid());
				demandProtection.setLybh(demandNew.getLybh());
				demandProtection.setBs145(demandNew.getBs145());
				demandProtectionMapper.updateByPrimaryKeySelective(demandProtection);
			}else{
				inertDemandProtection(demandNew);
			}
			
			//同步到b_h_02801 需求保障明细
			List<DemandDetails> demandDetaillist =demandDetailsMapper.selectByPrimaryMainid(demand.getId());
			
			for (DemandDetails demandDetails : demandDetaillist) {
				if(demandDetails.getXqbs() != DemandDetailsLogoEnum.COLSE.getId()){//b_h_02401.需求标识！=9关闭
					
					demandDetails.setTbbs(1);//同步标识=1修改待同步
					demandDetailsMapper.updateByPrimaryKeySelective(demandDetails);
					
					DemandProtectionDetails  dDetails =demandProtectionDetailsMapper.selectByPrimaryKey(demandDetails.getId());
					
					if(dDetails != null){
						//更新需求保障明细
						updateDemandProtectionDetails(dDetails,demandDetails);
						
						//记录需求单的流程记录
						saveFlowByZt("审批通过 "+demand.getJhyj(), dDetails.getId());
					}else{
						//新增需求保障明细
						insertDemandProtectionDetails(demandDetails);
						//记录需求单的流程记录
						saveFlowByZt("审批通过 "+demand.getJhyj(), demandDetails.getId());
					}
					
				}
			
			}
		}
		
		if(demand.getZt() == DemandStatusEnum.DOWN.getId()){//当当前状态为审批驳回
			demand.setJhsj(currentDate);
			
			//修改b_h_024 需求
			demandMapper.updateByPrimaryKeySelective(demand);
			
			//记录需求单的流程记录
			saveFlowByZt("审批驳回 "+demand.getJhyj(), demand.getId());
			
			//同步到b_h_02801 需求保障明细
			List<DemandDetails> demandDetaillist =demandDetailsMapper.selectByPrimaryMainid(demand.getId());
			
			for (DemandDetails demandDetails2 : demandDetaillist) {
				if(demandDetails2.getXqbs() != DemandDetailsLogoEnum.COLSE.getId()){//b_h_02401.需求标识！=9关闭
					//记录需求单的流程记录
					saveFlowByZt("审批驳回 "+demand.getJhyj(), demandDetails2.getId());
					
				}
			}
		}
		
	}
	/**
	 * 
	 * @Description 新增需求保障明细
	 * @CreateTime 2018年3月2日 上午11:36:15
	 * @CreateBy 林龙
	 * @param dDetails
	 * @param demandDetails
	 */
	private void insertDemandProtectionDetails( DemandDetails demandDetails) {
		DemandProtectionDetails dDetails =new DemandProtectionDetails();
		dDetails.setId(demandDetails.getId());
		dDetails.setMainid(demandDetails.getMainid());
		dDetails.setWllb(demandDetails.getWllb());
		dDetails.setBjid(demandDetails.getBjid());
		dDetails.setBjh(demandDetails.getBjh());
		dDetails.setBjmc(demandDetails.getBjmc());
		dDetails.setXingh(demandDetails.getXingh());
		dDetails.setGg(demandDetails.getGg());
		dDetails.setJhly(demandDetails.getJhly());
		dDetails.setZjh(demandDetails.getZjh());
		dDetails.setBzjh(demandDetails.getBzjh());
		dDetails.setXqsl(demandDetails.getXqsl());
		dDetails.setDw(demandDetails.getDw());
		dDetails.setXlh(demandDetails.getXlh());
		dDetails.setThj(demandDetails.getThj());
		dDetails.setXqbs(demandDetails.getXqbs());
		dDetails.setQrbs(0);//确认标识=0未确认】
		dDetails.setBzzt(1);//保障状态=1待处理
		demandProtectionDetailsMapper.insertSelective(dDetails);
	}
	/**
	 * 
	 * @Description 更新需求保障明细
	 * @CreateTime 2018年3月2日 上午11:31:31
	 * @CreateBy 林龙
	 * @param dDetails
	 * @param demandDetails
	 */
	private void updateDemandProtectionDetails(DemandProtectionDetails dDetails, DemandDetails demandDetails) {
		dDetails.setWllb(demandDetails.getWllb());
		dDetails.setBjid(demandDetails.getBjid());
		dDetails.setBjh(demandDetails.getBjh());
		dDetails.setBjmc(demandDetails.getBjmc());
		dDetails.setXingh(demandDetails.getXingh());
		dDetails.setGg(demandDetails.getGg());
		dDetails.setJhly(demandDetails.getJhly());
		dDetails.setZjh(demandDetails.getZjh());
		dDetails.setBzjh(demandDetails.getBzjh());
		dDetails.setXqsl(demandDetails.getXqsl());
		dDetails.setDw(demandDetails.getDw());
		dDetails.setXlh(demandDetails.getXlh());
		dDetails.setThj(demandDetails.getThj());
		dDetails.setXqbs(demandDetails.getXqbs());
		dDetails.setQrbs(0);//确认标识=0未确认】
		demandProtectionDetailsMapper.updateByPrimaryKeySelective(dDetails);
		
		
	}
	/**
	 * 
	 * @Description 新增b_h_028 需求保障主表
	 * @CreateTime 2018年3月2日 上午11:10:08
	 * @CreateBy 林龙
	 * @param demand
	 */
	private void inertDemandProtection(Demand demand) {
		DemandProtection demandProtection=new DemandProtection();
		demandProtection.setId(demand.getId());
		demandProtection.setDprtcode(demand.getDprtcode());
		demandProtection.setWhbmid(demand.getWhbmid());
		demandProtection.setWhrid(demand.getWhrid());
		demandProtection.setWhsj(demand.getWhsj());
		demandProtection.setBh(demand.getBh());
		demandProtection.setJjcd(demand.getJjcd());
		demandProtection.setIsYxfx(demand.getIsYxfx());
		demandProtection.setIsFjhtc(demand.getIsFjhtc());
		demandProtection.setXqlb(demand.getXqlb());
		demandProtection.setJhsyrq(demand.getJhsyrq());
		demandProtection.setSjbjh(demand.getSjbjh());
		demandProtection.setSjbjmc(demand.getSjbjmc());
		demandProtection.setFjzch(demand.getFjzch());
		demandProtection.setXlh(demand.getXlh());
		demandProtection.setFxsj(demand.getFxsj());
		demandProtection.setFxxh(demand.getFxxh());
		demandProtection.setXqyy(demand.getXqyy());
		demandProtection.setGmjy(demand.getGmjy());
		demandProtection.setSqrid(demand.getSqrid());
		demandProtection.setSqsj(demand.getSqsj());
		demandProtection.setSqbmid(demand.getSqbmid());
		demandProtection.setJhbmid(demand.getJhbmid());
		demandProtection.setJhrid(demand.getJhrid());
		demandProtection.setJhsj(demand.getJhsj());
		demandProtection.setJhyj(demand.getJhyj());
		demandProtection.setLylx(demand.getLylx());
		demandProtection.setLyid(demand.getLyid());
		demandProtection.setLybh(demand.getLybh());
		demandProtection.setBs145(demand.getBs145());
		demandProtectionMapper.insertSelective(demandProtection);
	}
	@Override
	public Map<String, Object> queryAllDemandpprotectionPageList(Demand demand) {
		User user = ThreadVarUtil.getUser();//当前登陆人
		demand.getParamsMap().put("userId", user.getId());
		demand.getParamsMap().put("userType", user.getUserType());//验证飞机注册号权限权限
		PageHelper.startPage(demand.getPagination());
		List<Demand> list = demandMapper.queryAllDemandpprotectionPageList(demand);
		return PageUtil.pack4PageHelper(list, demand.getPagination());
	}

}
