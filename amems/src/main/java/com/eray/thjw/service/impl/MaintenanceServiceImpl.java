package com.eray.thjw.service.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.MaintenanceMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.po.FixedCheckItem;
import com.eray.thjw.po.Maintenance;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.service.CheckTaskBillService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixedCheckItemService;
import com.eray.thjw.service.MaintenanceService;
import com.eray.thjw.service.MaintenanceToBookService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.ApproveStatusEnum;
import enu.LatestLogoEnum;
import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 维修方案service,用于业务逻辑处理
 * @develop date 2016.07.25
 */
@Service
public class MaintenanceServiceImpl implements MaintenanceService {
	
	/**
	 * @author liub
	 * @description 维修方案Mapper
	 * @develop date 2016.08.15
	 */
	@Autowired
	private MaintenanceMapper maintenanceMapper;
	
	/**
	 * @author liub
	 * @description 维修方案修订通知书service
	 * @develop date 2016.08.30
	 */
	@Autowired
	private MaintenanceToBookService maintenanceToBookService;
	
	/**
	 * @author liub
	 * @description 定检项目service
	 * @develop date 2016.09.24
	 */
	@Autowired
	private FixedCheckItemService fixedCheckItemService;
	
	/**
	 * @author liub
	 * @description 采番service
	 * @develop date 2016.08.16
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	/**
	 * @author liub
	 * @description 定检任务主表service
	 * @develop date 2016.09.26
	 */
	@Autowired
	private CheckTaskBillService checkTaskBillService;
	
	/**
	 * @author liub
	 * @description 机型数据service
	 */
	@Autowired
	private PlaneModelDataService planeModelDataService;
	
	/**
	 * @author liub
	 * @description 历史数据公共service
	 * @develop date 2016.09.26
	 */
	@Autowired
	private CommonRecService commonRecService;

	/**
	 * @author liub
	 * @description 根据查询条件分页查询待生效或生效维修方案信息
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.09.23
	 */
	@Override
	public List<Maintenance> queryAllPageListEff(Maintenance maintenance){
		return maintenanceMapper.queryAllPageListEff(maintenance);
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询维修方案信息
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	@Override
	public List<Maintenance> queryAllPageList(Maintenance maintenance){
		return maintenanceMapper.queryAllPageList(maintenance);
	}
	
	/**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询维修方案信息,去重
	 * @param dprtcodeList
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	@Override
	public List<Map<String, Object>> selectByDprtcodeList(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		paramMap.put("userId", user.getId());
		paramMap.put("userType", user.getUserType());
		List<Maintenance> maintenanceList = maintenanceMapper.selectByDprtcodeList(paramMap);
		for (Maintenance maintenance : maintenanceList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dprtcode", maintenance.getDprtcode());
			map.put("wxfabh", maintenance.getWxfabh());
			map.put("zwms", maintenance.getZwms());
			list.add(map);
		}
		return list;
	}

	/**
	 * @author liub
	 * @description 保存维修方案信息
	 * @param maintenance
	 * @return String
	 * @throws BusinessException 
	 * @develop date 2016.08.15
	 */
	@Override
	public String saveMaintenance(Maintenance maintenance) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(maintenance.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		maintenance.setDprtcode(user.getJgdm());
		//检查机型是否存在
		checkMaintenance(maintenance);
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String wxfabh = null;
		try {
			wxfabh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.WXFA.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		maintenance.setId(id);
		maintenance.setZt(StatusEnum.NO_EFFECT.getId());
		maintenance.setZxbs(LatestLogoEnum.INITIAL.getId());
		maintenance.setWxfabh(wxfabh);
		maintenance.setZdrid(user.getId());
		maintenance.setZdbmid(user.getBmdm());
		maintenanceMapper.insertSelective(maintenance);
		commonRecService.write(id, TableEnum.B_G_011, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, maintenance.getId());//保存维修方案历史记录信息
		return id;
	}
	
	/**
	 * @author liub
	 * @description 根据主键查询维修方案信息
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	@Override
	public Maintenance getByPrimaryKey(String id){
		return maintenanceMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @author liub
	 * @description 根据主键查询维修方案及用户信息
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	@Override
	public Maintenance selectById(String id){
		return maintenanceMapper.selectById(id);
	}

	/**
	 * @author liub
	 * @description 修改维修方案信息
	 * @param maintenance
	 * @throws BusinessException 
	 * @develop date 2016.08.15
	 */ 
	@Override
	public void edit(Maintenance maintenance) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(maintenance.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		Maintenance oldMaintenance = maintenanceMapper.selectByPrimaryKey(maintenance.getId());
		/* 验证重复提交 begin */
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId()},oldMaintenance.getZt(),"该维修方案已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//检查机型是否存在
		checkMaintenance(maintenance);
		String czls = UUID.randomUUID().toString();//操作流水
		maintenanceMapper.updateByPrimaryKeySelective(maintenance);
		commonRecService.write(maintenance.getId(), TableEnum.B_G_011, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, maintenance.getId());//保存维修方案历史记录信息
	}
	
	/**
	 * @author liub
	 * @description 改版维修方案
	 * @param maintenance,xdtzsidStr
	 * @return String
	 * @develop date 2016.08.17
	 */
	@Override
	public String modifyMaintenance(Maintenance maintenance,String xdtzsidStr) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(maintenance.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		Maintenance oldMt = maintenanceMapper.selectByPrimaryKey(maintenance.getId());
		/* 验证重复提交 begin */
		verification(new Integer[]{StatusEnum.EFFECT.getId()},oldMt.getZt(),"该维修方案已更新，请刷新后再进行操作!");
		BigDecimal maxBb = maintenanceMapper.getMaxBbByWxfabh(oldMt.getWxfabh(),oldMt.getDprtcode());
		BigDecimal bb = oldMt.getBb();
		if(null != maxBb && maxBb.compareTo(bb) != 0){
			throw new BusinessException("该维修方案已更新，请刷新后再进行操作!");
		}
		verification(new Integer[]{LatestLogoEnum.LATEST_VERSION.getId()},oldMt.getZxbs(),"该维修方案已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		UUID uuid = UUID.randomUUID();//生成uuid
		String newId = uuid.toString();
		/* 修改原版本的数据 begin */
		Maintenance oldMaintenance = new Maintenance();
		oldMaintenance.setId(maintenance.getfBbid());
		oldMaintenance.setbBbid(uuid.toString());
		maintenanceMapper.updateByPrimaryKeySelective(oldMaintenance);
		commonRecService.write(oldMaintenance.getId(), TableEnum.B_G_011, user.getId(), czls ,LogOperationEnum.REVISION, UpdateTypeEnum.UPDATE, oldMaintenance.getId());//保存维修方案历史记录信息
		/* 修改原版本的数据 end */
		maintenanceToBookService.saveMaintenanceToBook(oldMaintenance.getId(), xdtzsidStr, czls, oldMaintenance.getId());//保存维修方案修订通知书信息
		/* 保存新版本的数据 begin */
		maintenance.setId(newId);
		maintenance.setZt(StatusEnum.NO_EFFECT.getId());
		maintenance.setZxbs(LatestLogoEnum.INITIAL.getId());
		maintenance.setZdrid(user.getId());
		maintenance.setZdbmid(user.getBmdm());
		maintenanceMapper.insertSelective(maintenance);
		commonRecService.write(newId, TableEnum.B_G_011, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, maintenance.getId());//保存维修方案历史记录信息
		/* 保存新版本的数据 end */
		return newId;
	}
	
	/**
	 * @author liub
	 * @description  提交生产
	 * @param maintenance
	 * @develop date 2016.08.18
	 */
	@Override
	public void updateSbProdMaintenance(Maintenance maintenance) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(maintenance.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		Maintenance oldMaintenance = maintenanceMapper.selectByPrimaryKey(maintenance.getId());
		/* 验证重复提交 begin */
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId()},oldMaintenance.getZt(),"该维修方案已更新，请刷新后再进行操作!");
		List<FixedCheckItem> fList = fixedCheckItemService.queryByWxfabh(maintenance.getWxfabh(),oldMaintenance.getDprtcode());
		if(null == fList || fList.size() == 0){
			throw new BusinessException("该维修方案下定检项目已更新，请刷新后再进行操作!");
		}
		//将未审批且未生效的定检项目放入StringBuffer里面
		for (FixedCheckItem fixedCheckItem : fList) {
			if(ApproveStatusEnum.APPROVED.getId().intValue() != fixedCheckItem.getSpzt().intValue() && StatusEnum.EFFECT.getId().intValue() != fixedCheckItem.getZt().intValue()){
				throw new BusinessException("该维修方案下定检项目已更新，请刷新后再进行操作!");
			}
		}
		/* 验证重复提交 end */
		//提交生产
		String czls = UUID.randomUUID().toString();//操作流水
		List<String> idList = new ArrayList<String>();
		maintenance.setZt(StatusEnum.WAIT_EFFECT.getId());
		maintenanceMapper.updateByPrimaryKeySelective(maintenance);
		commonRecService.write(maintenance.getId(), TableEnum.B_G_011, user.getId(), czls ,LogOperationEnum.SUBMIT_PRODUCTION_CONFIRM, UpdateTypeEnum.UPDATE, maintenance.getId());//保存维修方案历史记录信息
		for (FixedCheckItem fixedCheckItem : fList) {
			if(null != maintenance.getBb() && maintenance.getBb().compareTo(fixedCheckItem.getBb()) == 0){
				idList.add(fixedCheckItem.getId());
			}
		}
		fixedCheckItemService.updateWaitEffective(idList, czls);
	}
	
	/**
	 * @author liub
	 * @description 维修方案生效
	 * @param wxfaId,sxjyrid
	 * @return
	 * @develop date 2016.09.23
	 */
	@Override
	public void updateEffective(String wxfaId,String sxjyrid) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		String wxfabh = "";
		BigDecimal bb = null;
		List<String> ids = null;
		Map<String, Object> paramMap = null;
		User user = ThreadVarUtil.getUser();
		Maintenance maintenance = maintenanceMapper.selectByPrimaryKey(wxfaId);
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(maintenance.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		//验证组织机构
		if(!user.getJgdm().equals(maintenance.getDprtcode())){
			throw new BusinessException("对不起,只有相关机构才有权限操作!");
		}
		/* 验证重复提交 begin */
		verification(new Integer[]{StatusEnum.WAIT_EFFECT.getId()},maintenance.getZt(),"该维修方案已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		Map<String, Object> taskMap = new HashMap<String, Object>();
		List<Integer> stateList = new ArrayList<Integer>();
		stateList.add(1);
		stateList.add(2);
		taskMap.put("dprtcode", maintenance.getDprtcode());
		taskMap.put("wxfabh", maintenance.getWxfabh());
		taskMap.put("stateList", stateList);
		List<CheckTaskBill> checkTaskBillList = checkTaskBillService.checkIsExist(taskMap);
		//检查维修方案下是否存在工卡
		if(0 < checkTaskBillList.size()){
			throw new BusinessException("该维修方案下工卡已更新，请刷新后再进行操作!");
		}
		
		wxfabh = maintenance.getWxfabh();
		bb = maintenance.getBb();
		//当前版本维修方案生效
		maintenance.setZt(StatusEnum.EFFECT.getId());
		maintenance.setZxbs(LatestLogoEnum.LATEST_VERSION.getId());
		maintenance.setSxbmid(user.getBmdm());
		maintenance.setSxrid(user.getId());
		maintenance.setSxjyrid(sxjyrid);
		maintenanceMapper.updateEffective(maintenance);
		commonRecService.write(wxfaId, TableEnum.B_G_011, user.getId(), czls ,LogOperationEnum.COME_INTO_EFFECT, UpdateTypeEnum.UPDATE, maintenance.getId());//保存维修方案历史记录信息
		//小于当前版本维修方案修改为老版本
		ids = new ArrayList<String>();
		List<Maintenance> maintenanceList = maintenanceMapper.queryAllListByWxfabh(wxfabh,maintenance.getDprtcode());
		for (Maintenance maintenance2 : maintenanceList) {
			if(null != bb && bb.compareTo(maintenance2.getBb()) == 1){
				ids.add(maintenance2.getId());
			}
		}
		if(ids.size() > 0){
			paramMap = new HashMap<String, Object>();
			paramMap.put("zxbs", LatestLogoEnum.OLD_VERSION.getId());
			paramMap.put("idList", ids);
			maintenanceMapper.updateBbToOld(paramMap);
		}
		if(ids.size() > 0){
			commonRecService.write("id",ids, TableEnum.B_G_011, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, maintenance.getId());//保存维修方案历史记录信息
		}
		fixedCheckItemService.sbEffective(wxfabh,bb,czls,maintenance.getDprtcode());
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @develop date 2016.08.18
	 */
	@Override
	public void deleteMaintenance(String id) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		Maintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(maintenance.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId(),StatusEnum.WAIT_EFFECT.getId()},maintenance.getZt(),"该维修方案已更新，请刷新后再进行操作!");
		Maintenance sbMt = new Maintenance();
		sbMt.setId(id);
		sbMt.setZt(StatusEnum.CANCEL.getId());
		sbMt.setUser(user);
		maintenanceMapper.updateByPrimaryKeySelective(sbMt);
		commonRecService.write(maintenance.getId(), TableEnum.B_G_011, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, maintenance.getId());//保存维修方案历史记录信息
		fixedCheckItemService.cancelByWxfabhAndBb(maintenance.getWxfabh(), maintenance.getBb(),maintenance.getDprtcode());
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案修改权限
	 * @param id
	 * @develop date 2016.08.17
	 */
	@Override
	public void checkUpdMt(String id) throws BusinessException {
		Maintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		//验证是否生效
		Integer zt = maintenance.getZt();
		if(null == zt || StatusEnum.NO_EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有未生效的维修方案才能修改!");
		}
	}

	/**
	 * @author liub
	 * @description 检查维修方案改版权限
	 * @param id
	 * @develop date 2016.08.17
	 */
	@Override
	public void checkMdfMt(String id) throws BusinessException {
		Maintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		//验证是否生效
		Integer zt = maintenance.getZt();
		if(null == zt || StatusEnum.EFFECT.getId().intValue() != zt.intValue() || LatestLogoEnum.LATEST_VERSION.getId().intValue() != maintenance.getZxbs().intValue()){
			throw new BusinessException("对不起,只有生效的维修方案才能改版!");
		}
		//验证是否最新版本
		BigDecimal maxBb = maintenanceMapper.getMaxBbByWxfabh(maintenance.getWxfabh(),maintenance.getDprtcode());
		BigDecimal bb = maintenance.getBb();
		if(null != maxBb && maxBb.compareTo(bb) != 0){
			throw new BusinessException("对不起,只有最新版本的维修方案才能改版!");
		}
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案提交生产权限
	 * @param id
	 * @develop date 2016.08.18
	 */
	@Override
	public void checkSbProdMt(String id) throws BusinessException {
		StringBuffer message = new StringBuffer();
		Maintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		//验证是否生效
		Integer zt = maintenance.getZt();
		if(null == zt || StatusEnum.NO_EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有未生效的维修方案才能提交生产!");
		}
		List<FixedCheckItem> fList = fixedCheckItemService.queryByWxfabh(maintenance.getWxfabh(),maintenance.getDprtcode());
		if(null == fList || fList.size() == 0){
			throw new BusinessException("对不起,该维修方案下没有定检项目不能提交生产!");
		}
		//将未审批且未生效的定检项目放入StringBuffer里面
		for (FixedCheckItem fixedCheckItem : fList) {
			if(ApproveStatusEnum.APPROVED.getId().intValue() != fixedCheckItem.getSpzt().intValue() && StatusEnum.EFFECT.getId().intValue() != fixedCheckItem.getZt().intValue()){
				message.append(fixedCheckItem.getZwms()).append(" ");
			}
		}
		if(message.length() > 0){
			throw new BusinessException("对不起,该维修方案下定检项目【"+message.toString()+"】未审批,不能提交生产!");
		}
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案生效状态
	 * @param wxfabh,bb,dprtcode
	 * @develop date 2016.08.18
	 */
	@Override
	public void checkEffectiveState(String wxfabh,String bb,String dprtcode) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		Maintenance maintenance = maintenanceMapper.selectByWxfabhBb(wxfabh,bb,dprtcode);
		//验证组织机构
		if(!user.getJgdm().equals(maintenance.getDprtcode())){
			throw new BusinessException("对不起,只有相关机构才有权限操作!");
		}
		//验证是否生效
		Integer zt = maintenance.getZt();
		if(StatusEnum.EFFECT.getId().intValue() == zt.intValue()){
			throw new BusinessException("对不起,生效的维修方案不能添加定检项目!");
		}
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案、版本、机构代码查询维修方案信息
	 * @param wxfabh,bb,dprtcode
	 * @return Maintenance
	 * @develop date 2016.12.12
	 */
	@Override
	public Maintenance selectByWxfabhBb(String wxfabh,String bb,String dprtcode){
		return maintenanceMapper.selectByWxfabhBb(wxfabh,bb,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询版本
	 * @param wxfabh,dprtcode
	 * @return List<BigDecimal>
	 * @develop date 2016.08.24
	 */
	@Override
	public List<BigDecimal> queryBbListByWxfabh(String wxfabh,String dprtcode){
		return maintenanceMapper.queryBbListByWxfabh(wxfabh,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号、机构代码查询机型
	 * @param wxfabh,dprtcode
	 * @return String
	 * @develop date 2016.11.22
	 */
	@Override
	public String getFjjxByWxfabh(String wxfabh,String dprtcode){
		return maintenanceMapper.getFjjxByWxfabh(wxfabh,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询最大版本
	 * @param wxfabh,dprtcode
	 * @return BigDecimal
	 * @develop date 2016.08.26
	 */
	@Override
	public BigDecimal getMaxBbByWxfabh(String wxfabh,String dprtcode){
		return maintenanceMapper.getMaxBbByWxfabh(wxfabh,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询最大版本维修方案信息
	 * @param wxfabh,dprtcode
	 * @return Maintenance
	 * @develop date 2016.12.12
	 */
	@Override
	public Maintenance queryMaxInfoByWxfabh(String wxfabh,String dprtcode){
		return maintenanceMapper.queryMaxInfoByWxfabh(wxfabh,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案改版生效权限
	 * @param id
	 * @develop date 2016.09.23
	 */
	@Override
	public void checkEffective(String id) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		Maintenance maintenance = maintenanceMapper.selectByPrimaryKey(id);
		//验证组织机构
		if(!user.getJgdm().equals(maintenance.getDprtcode())){
			throw new BusinessException("对不起,只有相关机构才有权限操作!");
		}
		//验证生效状态
		Integer zt = maintenance.getZt();
		if(null == zt || StatusEnum.WAIT_EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有待生效的维修方案才能确认生效!");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Integer> stateList = new ArrayList<Integer>();
		stateList.add(StatusEnum.NO_EFFECT.getId());
		stateList.add(StatusEnum.WAIT_EFFECT.getId());
		paramMap.put("dprtcode", maintenance.getDprtcode());
		paramMap.put("wxfabh", maintenance.getWxfabh());
		paramMap.put("stateList", stateList);
		List<CheckTaskBill> checkTaskBillList = checkTaskBillService.checkIsExist(paramMap);
		if(0 < checkTaskBillList.size()){
			throw new BusinessException("对不起,该维修方案存在未完成的工卡!");
		}
	}
	
	/**
	 * @author hanwu
	 * @description 根据飞机机型查询最新的定检项目和监控项目
	 * @param fjjx
	 * @return Maintenance
	 * @develop date 2016.09.22
	 */
	@Override
	public Maintenance selectByFjjx(Map<String, Object> paramMap){
		return maintenanceMapper.selectByFjjx(paramMap);
	}

	/**
	 * @author liub
	 * @description 根据查询条件分页查询维修方案信息（去重）
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	@Override
	public List<Maintenance> queryAllPageDistincList(Maintenance maintenance){
		return maintenanceMapper.queryAllPageDistincList(maintenance);
	}
	
	/**
	 * @author liub
	 * @description 检查维修方案机型是否存在
	 * @param 
	 * @return
	 * @develop date 2016.10.21
	 */
	private void checkMaintenance(Maintenance maintenance) throws BusinessException{
		Maintenance checkParam = new Maintenance();
		checkParam.setJx(maintenance.getJx());
		checkParam.setDprtcode(maintenance.getDprtcode());
		List<Maintenance> maintenanceList = maintenanceMapper.checkMaintenance(checkParam);
		for (Maintenance mt : maintenanceList) {
			if(null == maintenance.getWxfabh() || !maintenance.getWxfabh().equals(mt.getWxfabh()) ){
				throw new BusinessException(new StringBuffer().append("<div>机型[").append(maintenance.getJx()).append("]已被使用!</div>").toString());
			}
		}
	}

	/**
	 * @author hanwu
	 * @description 查询未匹配的定检项目以及其维修方案
	 * @param fjjx
	 * @return Maintenance
	 * @develop date 2016.11.22
	 */
	@Override
	public Maintenance findUnmatchItem(Map<String, Object> plane){
		return maintenanceMapper.findUnmatchItem(plane);
	}
	/**
	 * @author sunji
	 * @description 根据机型查询维修方案
	 * @param maintenance
	 * @return List<Maintenance>
	 * @develop date 2016.08.15
	 */
	@Override
	public Maintenance selectMaintenanceByJx(Maintenance maintenance){
		return maintenanceMapper.selectMaintenanceByJx(maintenance);
	}
	
	/**
	 * @author liub
	 * @description 验证是否存在
	 * @param i,j,message
	 * @develop date 2017.04.15
	 */
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}
}
