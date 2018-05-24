package com.eray.thjw.material2.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.DemandDetailsMapper;
import com.eray.thjw.material2.dao.DemandMapper;
import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandDetails;
import com.eray.thjw.material2.service.DemandApplyService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.AircraftinfoStatusMapper;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.AircraftinfoStatus;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.SaiBongEnum;
import enu.material2.DemandDetailsLogoEnum;
import enu.material2.DemandStatusEnum;
import enu.produce.SynchronzeStatusEnum;

/**
 * @Description 需求提报serivce实现类
 * @CreateTime 2018年2月26日 上午11:48:29
 * @CreateBy 韩武
 */
@Service("demandApplyService")
public class DemandApplyServiceImpl implements DemandApplyService {
	
	@Resource
	private DemandMapper demandMapper;
	
	@Resource
	private DemandDetailsMapper demandDetailsMapper;

	@Resource
	private AircraftinfoStatusMapper aircraftinfoStatusMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private ProcessRecordService processRecordService;

	/**
	 * @Description 查询飞机信息
	 * @CreateTime 2018年2月26日 上午11:47:07
	 * @CreateBy 韩武
	 * @return
	 */
	@Override
	public List<AircraftinfoStatus> queryAircraftInfo(Aircraftinfo ac) {
		return aircraftinfoStatusMapper.queryList4FlightBook(ac.getDprtcode(), ac.getFjzch());
	}

	/**
	 * @Description 保存时需求单验证
	 * @CreateTime 2018年2月27日 上午10:42:14
	 * @CreateBy 韩武
	 * @param demand
	 * @throws BusinessException
	 */
	private void validateForSave(Demand demand) throws BusinessException{
		if(demand.getZt() != null && 
				!DemandStatusEnum.SAVE.getId().equals(demand.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
	}
	
	/**
	 * @Description 提交时需求单验证
	 * @CreateTime 2018年2月27日 上午10:42:14
	 * @CreateBy 韩武
	 * @param demand
	 * @throws BusinessException
	 */
	private void validateForSubmit(Demand demand) throws BusinessException{
		if(demand.getZt() != null && 
				!DemandStatusEnum.SAVE.getId().equals(demand.getZt()) && 
				!DemandStatusEnum.SUB.getId().equals(demand.getZt()) && 
				!DemandStatusEnum.APP.getId().equals(demand.getZt()) && 
				!DemandStatusEnum.DOWN.getId().equals(demand.getZt())){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
	}
	
	/**
	 * @Description 保存需求单主单
	 * @CreateTime 2018年2月27日 上午11:02:18
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws com.eray.framework.exception.SaibongException 
	 * @throws SaibongException 
	 */
	private String saveDemand(Demand demand) throws com.eray.framework.exception.SaibongException{
		
		User user = ThreadVarUtil.getUser();
		demand.setWhrid(user.getId());
		demand.setWhsj(new Date());
		demand.setWhbmid(user.getBmdm());
		
		if (StringUtils.isBlank(demand.getId())){	// 新增需求单主单
			demand.setId(UUID.randomUUID().toString());
			// 生成需求单号
			demand.setBh(SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.HC_XQD.getName(), demand));
			demand.setDprtcode(user.getJgdm());
			demand.setSqrid(user.getId());
			demand.setSqsj(new Date());
			demand.setSqbmid(user.getBmdm());
			demandMapper.insertSelective(demand);
			
		} else {	// 修改需求单主单
			demandMapper.updateByDemandApply(demand);
		}
		
		return demand.getId();
	}
	
	/**
	 * @Description 保存需求详情
	 * @CreateTime 2018年2月27日 上午11:27:15
	 * @CreateBy 韩武
	 * @param demand
	 */
	private void saveDemandDetails(Demand demand){
		// 删除不存在的需求明细
		demandDetailsMapper.deleteNotExist(demand);
		
		User user = ThreadVarUtil.getUser();
		for (DemandDetails detail : demand.getDetails()) {
			detail.setMainid(demand.getId());
			detail.setWhrid(user.getId());
			detail.setWhsj(new Date());
			detail.setWhbmid(user.getBmdm());
			detail.setTbbs(SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode());
			if (StringUtils.isBlank(detail.getId())){	// 新增详情数据
				detail.setId(UUID.randomUUID().toString());
				demandDetailsMapper.insertSelective(detail);
			} else {	// 修改详情数据
				demandDetailsMapper.updateByPrimaryKey(detail);
			}
			
			if(DemandStatusEnum.SUB.getId().equals(demand.getZt())){
				// 记录详情新增的流程记录
				processRecordService.addRecord(detail.getId(), demand.getDprtcode(), "新增需求项目");
			}
		}
	}

	/**
	 * @Description 查询需求提报详情
	 * @CreateTime 2018年2月27日 下午2:24:25
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	@Override
	public Demand queryDetail(String id) {
		return demandMapper.queryDetail(id);
	}
	
	/**
	 * @Description 保存需求提报
	 * @CreateTime 2018年2月27日 上午10:35:23
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 * @throws SaibongException 
	 */
	@Override
	public Demand doSave(Demand demand) throws BusinessException, com.eray.framework.exception.SaibongException {
		// 验证状态
		validateForSave(demand);
		// 保存需求单主单
		saveDemand(demand);
		// 保存需求单明细
		saveDemandDetails(demand);
		return demand;
	}

	/**
	 * @Description 提交需求提报
	 * @CreateTime 2018年2月27日 上午10:35:23
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Demand doSubmit(Demand demand) throws BusinessException,
			com.eray.framework.exception.SaibongException {
		// 验证状态
		validateForSubmit(demand);
		// 是否没有提交过
		boolean isNeverSubmit = isNeverSubmit(demand);
		// 保存需求单主单
		saveDemand(demand);
		// 保存主单流程记录
		writeProcessRecord(demand, isNeverSubmit);
		// 保存需求单明细
		saveDemandDetailsForSubmit(demand, isNeverSubmit);
		return demand;
	}
	
	/**
	 * @Description 是否没有提交过
	 * @CreateTime 2018年2月27日 下午4:07:09
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	private boolean isNeverSubmit(Demand demand){
		if(StringUtils.isNotBlank(demand.getId())){
			Demand dbData = demandMapper.selectByPrimaryKey(demand.getId());
			if(!DemandStatusEnum.SAVE.getId().equals(dbData.getZt())){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Description 保存主单流程记录
	 * @CreateTime 2018年2月27日 下午4:04:37
	 * @CreateBy 韩武
	 * @param demand
	 */
	private void writeProcessRecord(Demand demand, boolean isNeverSubmit){
		// 增加d_025流程
		processRecordService.addRecord(demand.getId(), demand.getDprtcode(), isNeverSubmit ? "提交申请" : "修订申请");
	}
	
	/**
	 * @Description 提交时保存需求详情
	 * @CreateTime 2018年2月27日 下午4:12:49
	 * @CreateBy 韩武
	 * @param demand
	 * @param isNeverSubmit
	 */
	private void saveDemandDetailsForSubmit(Demand demand, boolean isNeverSubmit){
		// 如果曾经提交过
		if(isNeverSubmit){
			// 保存需求详情
			saveDemandDetails(demand);
		}else{
			// 对比并保存需求详情
			compareAndSaveDetails(demand);
		}
	}
	
	/**
	 * @Description 对比得到需求标识
	 * @CreateTime 2018年2月27日 下午4:15:22
	 * @CreateBy 韩武
	 * @param demand
	 */
	private void compareAndSaveDetails(Demand demand){
		// 数据库需求主单数据
		Demand dbDemand = demandMapper.selectByPrimaryKey(demand.getId());
		User user = ThreadVarUtil.getUser();
		for (DemandDetails detail : demand.getDetails()) {
			detail.setMainid(demand.getId());
			detail.setWhrid(user.getId());
			detail.setWhsj(new Date());
			detail.setWhbmid(user.getBmdm());
			detail.setTbbs(SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode());
			if(StringUtils.isNotBlank(detail.getId())){
				// 数据库需求详情数据
				DemandDetails dbDetails = demandDetailsMapper.selectByPrimaryKey(detail.getId());
				// 如果需求主单或需求详情与数据库中有一个不同
				if(!demand.equals(dbDemand) || !detail.equals(dbDetails)){
					// 设置需求标识为2修订
					detail.setXqbs(DemandDetailsLogoEnum.UPD.getId());
					// 修改详情数据
					demandDetailsMapper.updateByPrimaryKey(detail);
				}
				// 记录详情修订的流程记录
				processRecordService.addRecord(detail.getId(), demand.getDprtcode(), "修订需求项目");
			}else{
				// 新增详情数据
				detail.setId(UUID.randomUUID().toString());
				demandDetailsMapper.insertSelective(detail);
				// 记录详情新增的流程记录
				processRecordService.addRecord(detail.getId(), demand.getDprtcode(), "新增需求项目");
			}
		}
		
		// 获取删除的id集合
		List<String> deleteList = demandDetailsMapper.getDeleteList(demand);
		// 删除不存在的需求明细
		demandDetailsMapper.deleteNotExist(demand);
		// 记录详情取消的流程记录
		if(deleteList != null && !deleteList.isEmpty()){
			processRecordService.addBatchRecord(deleteList, demand.getDprtcode(), "取消需求项目");
		}
	}

	/**
	 * @Description 查询当前人的需求提报列表
	 * @CreateTime 2018年2月28日 下午5:07:56
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	@Override
	public Map<String, Object> querySelfList(Demand demand) {
		PageHelper.startPage(demand.getPagination());
		List<Demand> list = demandMapper.querySelfList(demand);
		return PageUtil.pack4PageHelper(list, demand.getPagination());
	}

	/**
	 * @Description 根据航材工具清单生成提报单详情
	 * @CreateTime 2018年4月12日 上午9:39:36
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	@Override
	public Demand buildDetailByMaterialTool(Demand demand) throws BusinessException {
		if(demand.getDetails().size() >= 1000){
			throw new BusinessException("一次最多生成1000条物料信息，请修改后重试！");
		}
		
		// 获取部件信息
		List<DemandDetails> details = demandDetailsMapper.queryPartListDetail(demand);
		for (DemandDetails detailDb : details) {
			for (DemandDetails detailPage : demand.getDetails()) {
				if(StringUtils.isNotBlank(detailDb.getBjid()) 
						&& detailDb.getBjid().equals(detailPage.getBjid())){
					detailDb.setXqsl(detailPage.getXqsl());
					detailDb.setThj(detailPage.getThj());
					detailDb.setXqbs(DemandDetailsLogoEnum.SVAE.getId());
				}
			}
		}
		demand.setDetails(details);
		
		// 获取飞机
		User user = ThreadVarUtil.getUser();
		demand.getParamsMap().put("userId", user.getId());
		demand.getParamsMap().put("userType", user.getUserType());
		// 1表示已选中的监控数据,2表示135工包,3表示135工单,4表示145工包
		String type = (String) demand.getParamsMap().get("type");
		outterLoop : for (DemandDetails detail : details) {
			demand.getParamsMap().put("bjh", detail.getBjh());
			List<Demand> demandsDb = demandMapper.queryMaterailToolSource(demand);
			for (Demand demandDb : demandsDb) {
				demand.setFjzch(demandDb.getFjzch());
				demand.setLyid(demandDb.getLyid());
				demand.setLybh(demandDb.getLybh());
				if("2".equals(type)){
					demand.setLylx(1);
					demand.setBs145(0);
				}else if("3".equals(type)){
					demand.setLylx(2);
					demand.setBs145(0);
				}else if("4".equals(type)){
					demand.setLylx(1);
					demand.setBs145(1);
				}else{
					demand.setLylx(demandDb.getLylx());
					demand.setBs145(demandDb.getBs145());
				}
				break outterLoop; 
			}
		}
		return demand;
	}
}