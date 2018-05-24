package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.ComponentCertificateService;
import com.eray.thjw.aerialmaterial.service.StockSerivce;
import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.InstallationListEditable2InitMapper;
import com.eray.thjw.produce.dao.InstallationListEditableMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEditable2Init;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.po.PlanInit;
import com.eray.thjw.produce.service.InstallationListEditable2InitService;
import com.eray.thjw.produce.service.InstallationListEditableService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.PartSnValidationEnum;
import enu.produce.CareerCardTypeEnum;
import enu.produce.InstalledPositionEnum;
import enu.produce.InstalledStatusEnum;
import enu.produce.SynchronzeStatusEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 装机清单-编辑区service实现类
 * @CreateTime 2017年9月22日 下午4:53:30
 * @CreateBy 韩武
 */
@Service
public class InstallationListEditableServiceImpl implements InstallationListEditableService  {

	@Resource
	private InstallationListEditableMapper installationListEditableMapper;
	
	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	
	@Resource
	private InstallationListEditable2InitMapper installationListEditable2InitMapper;
	
	@Resource
	private InstallationListEditable2InitService installationListEditable2InitService;
	
	@Resource
	private ComponentCertificateService componentCertificateService;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private ZoneStationMapper zoneStationMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private StockSerivce stockSerivce;
	
	/** 一级部件 */
	private final Integer INSTALLATIONLIST_LEVEL_ONE = 1;

	/**
	 * @Description 查询装机清单-编辑区的分页数据
	 * @CreateTime 2017年9月23日 上午9:59:30
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryPageableList(
			InstallationListEditable record) {
		
		PageHelper.startPage(record.getPagination());
		
		// 查询装机清单数据
		List<InstallationListEditable> list = installationListEditableMapper.queryTreeList(record);
		// 设置初始化值
		setInitDatas(record, list);
		return PageUtil.pack4PageHelper(list, record.getPagination());
		
	}
	
	/**
	 * @Description 设置初始化值
	 * @CreateTime 2017年9月23日 下午4:07:17
	 * @CreateBy 韩武
	 * @param record
	 */
	private void setInitDatas(InstallationListEditable record, List<InstallationListEditable> list){
		List<InstallationListEditable2Init> initDatas = installationListEditable2InitMapper.queryByAircraft(record);
		if(initDatas.size()>0){
			Map<String, List<InstallationListEditable2Init>> map = new HashMap<String, List<InstallationListEditable2Init>>();
			for (InstallationListEditable2Init i : initDatas) {
				List<InstallationListEditable2Init> resultList = map.get(i.getMainid());
				if(resultList == null){
					resultList = new ArrayList<InstallationListEditable2Init>();
					map.put(i.getMainid(), resultList);
				}
				resultList.add(i);
			}
			for (InstallationListEditable install : list) {
				install.setInitDatas(map.get(install.getId()));
				// 分钟转化为小时
				switchMinuteToHour(install);
			}
		}
	}
	
	/**
	 * @Description 分钟转化为小时
	 * @CreateTime 2017年9月23日 下午4:45:42
	 * @CreateBy 韩武
	 * @param record
	 */
	private void switchMinuteToHour(InstallationListEditable record){
		
		if(record != null){
			List<InstallationListEditable2Init> list = record.getInitDatas();
			if(list != null && !list.isEmpty()){
				for (InstallationListEditable2Init init : list) {
					// 是时间类型的监控项目
					if(MonitorProjectEnum.isTime(init.getJklbh())){
						init.setCsz(StringAndDate_Util.convertToHour(init.getCsz()));
					}
				}
			}
			
			if(StringUtils.isNotBlank(record.getTsn())){
				record.setTsn(StringAndDate_Util.convertToHour(record.getTsn()));
			}
			if(StringUtils.isNotBlank(record.getTso())){
				record.setTso(StringAndDate_Util.convertToHour(record.getTso()));
			}
		}
		
	}
	
	/**
	 * @Description 小时转化为分钟
	 * @CreateTime 2017年10月12日 下午1:35:15
	 * @CreateBy 韩武
	 * @param record
	 */
	private void swtichHourToMinute(InstallationListEditable record){
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
	 * @Description 查询部件集合
	 * @CreateTime 2017年9月25日 下午4:56:40
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<InstallationListEditable> queryList(
			InstallationListEditable record) {
		
		List<InstallationListEditable> list = installationListEditableMapper.queryTreeList(record);
		
		// 设置初始化值
		setInitDatas(record, list);
		
		return list;
	}

	/**
	 * @Description 保存装机清单
	 * @CreateTime 2017年9月28日 下午3:55:21
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public String doSave(InstallationListEditable record)
			throws BusinessException {
		
		// 保存装机清单主数据
		saveInstallation(record);
		
		// 更新子节点
		updateChildren(record);
		
		// 更新航材数据
		updateMaterial(record);
		
		// 保存初部件始化数据
		installationListEditable2InitService.save(record);
		
		// 保存证书
		componentCertificateService.save(record);
		
		return record.getId();
	}
	
	/**
	 * @Description 更新子节点
	 * @CreateTime 2017年10月10日 下午3:27:35
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException 
	 */
	private void updateChildren(InstallationListEditable record) throws BusinessException{
		
		// 所有子节点
		List<InstallationListEditable> children = installationListEditableMapper.findChildren(record);
		
		if(!children.isEmpty()){
			for (InstallationListEditable child : children) {
				// 验证子节点在生效区中存在 && 改变了位置
				if(child.getTbbs().equals(SynchronzeStatusEnum.SYNCHRONZED.getCode()) 
						&& !child.getWz().equals(record.getWz())){
					throw new BusinessException("子节点已生效，无法改变位置！");
				}
				child.setWz(record.getWz());
				
				// 父节点拆下
				if(InstalledStatusEnum.REMOVED.getId().equals(record.getZjzt())){
					child.setZjzt(record.getZjzt());
					child.setCcjldh(record.getCcjldh());
					child.setCcsj(record.getCcsj());
					child.setCcr(record.getCcr());
					child.setCcrid(record.getCcrid());
				}
				child.setTbbs(SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode());
			}
			
			// 设置层级
			setDeep(children, record);
			
			for (InstallationListEditable child : children) {
				installationListEditableMapper.updateByPrimaryKeySelective(child);
			}
		}
		
	}
	
	/**
	 * @Description 设置层级
	 * @CreateTime 2017年10月10日 下午3:46:07
	 * @CreateBy 韩武
	 * @param children
	 * @param parent
	 */
	private void setDeep(List<InstallationListEditable> children, InstallationListEditable parent){
		for (InstallationListEditable child : children) {
			if(child.getFjdid().equals(parent.getId())){
				child.setCj(parent.getCj() + 1);
				setDeep(children, child);
			}
		}
	}
	
	/**
	 * @Description 更新航材数据
	 * @CreateTime 2017年10月10日 上午11:35:15
	 * @CreateBy 韩武
	 * @param record
	 */
	private void updateMaterial(InstallationListEditable record){
		hCMainDataMapper.updateByInstallationList(record);
	}
	
	/**
	 * @Description 保存装机清单主数据
	 * @CreateTime 2017年9月28日 下午4:37:43
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	private String saveInstallation(InstallationListEditable record) throws BusinessException{
		
		// 验证装机清单数据
		record.generateAzsj();
		record.generateCcsj();
		validate(record);
		
		// 小时转化为分钟
		swtichHourToMinute(record);
		User user = ThreadVarUtil.getUser();
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setTbbs(SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode());
		record.setWhbmid(user.getBmdm());
		if(StringUtils.isBlank(record.getCzls())){
			record.setCzls(UUID.randomUUID().toString());//操作流水
		}
		
		if (StringUtils.isBlank(record.getId())){	// 新增维修项目信息
			record.setId(UUID.randomUUID().toString());
			if(record.getLogOperationEnum() == null){
				record.setLogOperationEnum(LogOperationEnum.SAVE_WO);
			}
			installationListEditableMapper.insertSelective(record);
			// 保存历史记录信息
			commonRecService.write(record.getId(), TableEnum.B_S2_001, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getId());
			
		} else {	// 修改维修项目信息
			if(record.getLogOperationEnum() == null){
				record.setLogOperationEnum(LogOperationEnum.EDIT);
			}
			installationListEditableMapper.update(record);
			// 保存历史记录信息
			commonRecService.write(record.getId(), TableEnum.B_S2_001, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.UPDATE, record.getId());
		}
		
		record.setZbid(record.getId());
		return record.getId();
	}
	
	/**
	 * @Description 验证装机清单数据
	 * @CreateTime 2017年9月28日 下午4:38:02
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	private void validate(InstallationListEditable record) throws BusinessException{
		
		// 验证一级部件（除机身）位置唯一
		if(!InstalledPositionEnum.BODY.getId().equals(record.getWz())
				&& INSTALLATIONLIST_LEVEL_ONE.equals(record.getCj())){
			if(installationListEditableMapper.getLevelOneCount(record) > 0){
				throw new BusinessException("位置为" + InstalledPositionEnum.getName(record.getWz())+ 
						"的一级部件最多只能有一个！");
			}
			
		}
		if(null != record.getXlh() && !"".equals(record.getXlh()) 
				&& InstalledStatusEnum.INSTALLED.getId().equals(record.getZjzt())){
			//验证部件号+序列号是否已存在该飞机的编辑区
			List<InstallationListEditable> toBeSyncList = installationListEditableMapper.selectByDprtcode(record.getDprtcode());
			for (InstallationListEditable installationListEditable : toBeSyncList) {
				if(record.getBjh().equals(installationListEditable.getBjh()) && record.getXlh().equals(installationListEditable.getXlh())&&!installationListEditable.getId().equals(record.getId())){
					throw new BusinessException("部件{"+record.getBjh()+"}序列号{"+installationListEditable.getXlh()+"}在"+installationListEditable.getFjzch()+"已存在");
				}
			}
		}	
		//TODO 验证件号+序列号唯一
		if(InstalledStatusEnum.INSTALLED.getId().equals(record.getZjzt())){
			stockSerivce.getCount4ValidationBjAndXlh(record.getId(), record.getBjh(), record.getXlh(), record.getDprtcode(), PartSnValidationEnum.INSTALL.getId());
		}
	}

	/**
	 * @Description 查询装机清单详情
	 * @CreateTime 2017年10月9日 上午11:11:45
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public InstallationListEditable queryDetail(InstallationListEditable record) {
		
		InstallationListEditable detail = installationListEditableMapper.queryDetail(record);
		
		// 分钟转化为小时
		switchMinuteToHour(detail);
		
		return detail;
	}

	/**
	 * @Description 删除装机清单
	 * @CreateTime 2017年10月10日 上午10:03:14
	 * @CreateBy 韩武
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void doDelete(InstallationListEditable record)
			throws BusinessException {
		
		
		// 找到所有子节点
		List<InstallationListEditable> children= installationListEditableMapper.findChildren(record);
		record.setCzls(UUID.randomUUID().toString());
		record.setLogOperationEnum(LogOperationEnum.DELETE);
		
		for (InstallationListEditable child : children) {
			// 验证装机清单id 不存在与生效区、同步标识=1待同步
			User user = ThreadVarUtil.getUser();
			InstallationListEditable editable = installationListEditableMapper.selectByPrimaryKey(child.getId());
			InstallationListEffective effective = installationListEffectiveMapper.selectByPrimaryKey(child.getId());
			if(effective != null || editable.getTbbs() != SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode()){
				throw new BusinessException("部件或其子部件已生效，无法删除！"); 
			}
			
			// 删除主表
			commonRecService.write(child.getId(), TableEnum.B_S2_001, user.getId(), 
					record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getId());
			installationListEditableMapper.deleteByPrimaryKey(child.getId());
			
			// 删除初始化数据表
			child.setCzls(record.getCzls());
			child.setLogOperationEnum(record.getLogOperationEnum());
			installationListEditable2InitService.delete(child);
		}
	}
	
	/**
	 * @Description 飞机注册时 保存装机清单编辑区
	 * @CreateTime 2017年10月16日 上午10:34:42
	 * @CreateBy 徐勇
	 * @param fjjx 机型 
	 * @param fjzch 飞机注册号
	 * @param dprtcode 组织机构
	 * @param planeInitList 飞机初始化信息
	 * @param czls 操作流水号
	 * @throws BusinessException 
	 */
	public void saveInstall4PlaneAdd(String fjjx, String fjzch, String dprtcode, List<PlanInit> planeInitList, String czls) throws BusinessException{
		
		//装机清单map <装机位置,装机清单ID>
		Map<Integer, String> installMap = new HashMap<Integer, String>();
		
		//装机清单初始值map<装机清单ID, 初始值List>
		Map<String, List<InstallationListEditable2Init>> installInitMap = new HashMap<String, List<InstallationListEditable2Init>>();
		
		if(StringUtils.isBlank(czls)){
			czls = UUID.randomUUID().toString();//操作流水号
		}
		
		//装机清单初始值
		List<InstallationListEditable2Init> installInitList = null;
		String zjqdid = null;
		HCMainData hCMainData = null;
		InstallationListEditable2Init installInit = null;
		InstallationListEditable installationListEditable = null;
		User user = ThreadVarUtil.getUser();
		for (PlanInit planInit : planeInitList) {
			//当飞机初始数据的装机位置不为机身，并且不带装机清单时新增装机清单
			if(planInit.getWz().intValue() != InstalledPositionEnum.BODY.getId().intValue()){
				if(installMap.containsKey(planInit.getWz().intValue())){
					zjqdid = installMap.get(planInit.getWz().intValue());
				}else{
					//新增装机清单
					installationListEditable = new InstallationListEditable();
					installationListEditable.setDprtcode(dprtcode);
					installationListEditable.setJx(fjjx);
					installationListEditable.setFjzch(fjzch);
					installationListEditable.setWz(planInit.getWz().intValue());
					installationListEditable.setCj(1);
					installationListEditable.setBjh(planInit.getJh());
					hCMainData = hCMainDataMapper.selectByPrimaryHCMainData(planInit.getJh(), dprtcode);
					if(hCMainData != null){
						installationListEditable.setZwmc(hCMainData.getZwms());
						installationListEditable.setYwmc(hCMainData.getYwms());
						installationListEditable.setCjjh(hCMainData.getCjjh());
						installationListEditable.setXh(hCMainData.getXingh());
						installationListEditable.setZjh(hCMainData.getZjh());
						installationListEditable.setJldw(hCMainData.getJldw());
					}
					installationListEditable.setXlh(planInit.getXlh());
					installationListEditable.setZjsl(BigDecimal.ONE);
					installationListEditable.setLlklx(CareerCardTypeEnum.NONE.getId());
					installationListEditable.setAzsj(this.getAzrq(planeInitList, planInit.getWz().intValue()));
					installationListEditable.setFjdid("0");//一级节点父节点ID直接赋值0 
					installationListEditable.setZjzt(InstalledStatusEnum.INSTALLED.getId());
					installationListEditable.setTbbs(SynchronzeStatusEnum.TO_BE_SYNCHRONZE.getCode());
					installationListEditable.setCzls(czls);
					installationListEditable.setLogOperationEnum(LogOperationEnum.SAVE_WO);
					// 保存装机清单主数据
					zjqdid = this.saveInstallation(installationListEditable);
					installMap.put(planInit.getWz().intValue(), zjqdid);
				}
				installInit = new InstallationListEditable2Init();
				installInit.setId(UUID.randomUUID().toString());
				installInit.setMainid(zjqdid);
				installInit.setJkflbh(planInit.getJkflbh());
				installInit.setJklbh(planInit.getJklbh());
				installInit.setCsz(planInit.getCsz());
				installInit.setWhbmid(user.getBmdm());
				installInit.setWhrid(user.getId());
				planInit.setZjqdid(zjqdid);
				
				if(installInitMap.containsKey(zjqdid)){
					installInitMap.get(zjqdid).add(installInit);
				}else{
					installInitList = new ArrayList<InstallationListEditable2Init>();
					installInitList.add(installInit);
					installInitMap.put(zjqdid, installInitList);
				}
			}
		}
		if(installInitMap.size() > 0){
			Set<String> zjqdSet = installInitMap.keySet();
			for (String id : zjqdSet) {
				// 保存初部件始化数据
				this.installationListEditable2InitService.add4Batch(installInitMap.get(id), czls, LogOperationEnum.SAVE_WO, id);
			}
		}
		//更新时控时寿标识
		this.installationListEditableMapper.updateControlFlag(dprtcode, fjjx, fjzch);
		
	}
	
	/**
	 * @Description 获取安装日期，安装日期为该位置的日历初始值
	 * @CreateTime 2017年11月7日 下午2:10:53
	 * @CreateBy 徐勇
	 * @param planeInitList
	 * @param wz
	 * @return
	 */
	private Date getAzrq(List<PlanInit> planeInitList, Integer wz){
		for (PlanInit planInit : planeInitList) {
			if(MonitorProjectEnum.isCalendar(planInit.getJklbh()) && wz.intValue() == planInit.getWz().intValue()){
				try {
					return DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE, planInit.getCsz());
				} catch (ParseException e) {
					return null;
				}
			}
		}
		return null;
	}
	
	/**
	 * @Description 飞机初始值修改时同步修改装机清单
	 * @CreateTime 2017年10月17日 下午2:54:09
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param fjzch 飞机注册号
	 */
	public void updateInit4PlaneEdit(String dprtcode, String fjzch){
		//将需变更初始数据的装机清单数据改为待同步
		this.installationListEditableMapper.updateSyncFlagByInitDiffWithPlane(dprtcode, fjzch);
		//变更初始值
		this.installationListEditable2InitMapper.updateInitByDiffWithPlane(dprtcode, fjzch);
	}

	/**
	 * @Description 获取导出数据
	 * @CreateTime 2017年12月18日 上午10:47:55
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<InstallationListEditable> getExportList(
			InstallationListEditable record) {
		// 查询装机清单数据
		List<InstallationListEditable> list = installationListEditableMapper.queryTreeList(record);
		// 设置初始化值
		setInitDatas(record, list);
		
		for (InstallationListEditable install : list) {
			// 判断是否维护初始化信息
			List<InstallationListEditable2Init> inits = install.getInitDatas();
			int initFlag = 0;
			if(inits != null && !inits.isEmpty()){
				for (InstallationListEditable2Init init : inits) {
					if(StringUtils.isNotBlank(init.getCsz())){
						initFlag = 1;
					}
				}
			}
			install.getParamsMap().put("initFlag", initFlag);
			
			// 监控类型
			String jklx = "";
			if(install.getSkbs() == null){
				install.setSkbs(0);
			}
			if(install.getSsbs() == null){
				install.setSsbs(0);
			}
			if(install.getSkbs() == 1 && install.getSsbs() == 0){
				jklx = "时控件";
			}
			if(install.getSkbs() == 0 && install.getSsbs() == 1){
				jklx = "时寿件";
			}
			if(install.getSkbs() == 1 && install.getSsbs() == 1){
				jklx = "时控件/时寿件";
			}
			if(jklx.equals("")){
				jklx = "非控制件";
			}
			install.getParamsMap().put("jklxstr", jklx);
			
			// 拼接初始化值
			String calstr = "";
			String timestr = "";
			String cyclestr = "";
			if(inits != null && !inits.isEmpty()){
				for (InstallationListEditable2Init init : inits) {
					if(MonitorProjectEnum.isCalendar(init.getJklbh()) && StringUtils.isNotBlank(init.getCsz())){
						calstr += init.getCsz();
					}
					if(MonitorProjectEnum.isTime(init.getJklbh()) && StringUtils.isNotBlank(init.getCsz())){
						timestr += init.getCsz() + MonitorProjectEnum.getUnit(init.getJklbh()) +  " ";
					}
					if(MonitorProjectEnum.isLoop(init.getJklbh()) && StringUtils.isNotBlank(init.getCsz())){
						cyclestr += init.getCsz() + MonitorProjectEnum.getUnit(init.getJklbh()) + " ";
					}
				}
			}
			install.getParamsMap().put("calstr", calstr);
			install.getParamsMap().put("timestr", timestr);
			install.getParamsMap().put("cyclestr", cyclestr);
		}
		
		return list;
	}
	
}