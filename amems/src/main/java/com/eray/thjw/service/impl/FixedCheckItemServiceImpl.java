package com.eray.thjw.service.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.FixedCheckItemMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FixedCheckItem;
import com.eray.thjw.po.Maintenance;
import com.eray.thjw.po.MonitorItem;
import com.eray.thjw.po.MonitorOptionClass;
import com.eray.thjw.po.MonitorOptionItem;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.ScheduledCheckItemEditableMapper;
import com.eray.thjw.productionplan.dao.ScheduledCheckMonitorItemEditableMapper;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixedCheckItemService;
import com.eray.thjw.service.FixedCheckWorkContentService;
import com.eray.thjw.service.MaintenanceService;
import com.eray.thjw.service.MonitorItemService;
import com.eray.thjw.service.MonitorOptionClassService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.ApproveStatusEnum;
import enu.LatestLogoEnum;
import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.UnitEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 定检项目service,用于业务逻辑处理
 * @develop date 2016.07.25
 */
@Service
public class FixedCheckItemServiceImpl implements FixedCheckItemService {
	
	/**
	 * @author liub
	 * @description 定检项目Mapper
	 * @develop date 2016.08.20
	 */
	@Autowired
	private FixedCheckItemMapper fixedCheckItemMapper;
	
	/**
	 * @author liub
	 * @description 维修方案service
	 * @develop date 2016.08.26
	 */
	@Autowired
	private MaintenanceService maintenanceService;
	
	/**
	 * @author liub
	 * @description 监控项目service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private MonitorItemService monitorItemService;
	
	/**
	 * @author liub
	 * @description 监控大类service
	 * @develop date 2016.08.22
	 */
	@Autowired
	private MonitorOptionClassService monitorClassService;
	
	/**
	 * @author liub
	 * @description 定检工作内容service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private FixedCheckWorkContentService fixedCheckWorkContentService;
	
	/**
	 * @author liub
	 * @description 采番service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	/**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	
	/**
	 * @author hanwu
	 * @description 定检项目mapper
	 * @develop date 2017.03.02
	 */
	@Autowired
	private ScheduledCheckItemEditableMapper scheduledCheckItemEditableMapper;
	
	/**
	 * @author hanwu
	 * @description 定检监控项目mapper
	 * @develop date 2017.03.02
	 */
	@Autowired
	private ScheduledCheckMonitorItemEditableMapper scheduledCheckMonitorItemEditableMapper;
	
	/**
	 * @author liub
	 * @description 根据定检项目排序数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.25
	 */
	@Override
	public List<Map<String, Object>> queryOrderList(String wxfabh,String dprtcode){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<FixedCheckItem> fList = fixedCheckItemMapper.queryOrderList(wxfabh,dprtcode);
		for (FixedCheckItem fixedCheckItem : fList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", fixedCheckItem.getId());
			map.put("pxh", fixedCheckItem.getPxh());
			map.put("zwms", fixedCheckItem.getZwms());
			map.put("djbh", fixedCheckItem.getDjbh());
			list.add(map);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询定检项目信息
	 * @param fixedCheckItem
	 * @return Map<String, Object>
	 * @develop date 2016.08.20
	 */
	@Override
	public Map<String, Object> queryAllPageList(FixedCheckItem fixedCheckItem){
		String id = fixedCheckItem.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		fixedCheckItem.setId(null);
		PageHelper.startPage(fixedCheckItem.getPagination());
		List<FixedCheckItem> list = fixedCheckItemMapper.queryAllPageList(fixedCheckItem);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					FixedCheckItem newRecord = new FixedCheckItem();
					newRecord.setId(id);
					newRecord.setWxfabh(fixedCheckItem.getWxfabh());
					newRecord.setBb(fixedCheckItem.getBb());
					Pagination page = new Pagination();
					newRecord.setPagination(page);
					List<FixedCheckItem> newRecordList = fixedCheckItemMapper.queryAllPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			formatMonitor(list);//格式化监控条件
			return PageUtil.pack4PageHelper(list, fixedCheckItem.getPagination());
		}else{
			List<FixedCheckItem> newRecordList = new ArrayList<FixedCheckItem>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				FixedCheckItem newRecord = new FixedCheckItem();
				newRecord.setId(id);
				newRecord.setWxfabh(fixedCheckItem.getWxfabh());
				newRecord.setBb(fixedCheckItem.getBb());
				newRecord.setPagination(fixedCheckItem.getPagination());
				newRecord.getPagination().setPage(1);
				newRecordList = fixedCheckItemMapper.queryAllPageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					formatMonitor(list);//格式化监控条件
					return PageUtil.pack(1, newRecordList, fixedCheckItem.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, fixedCheckItem.getPagination());
		}
	}
	
	/**
	 * @author liub
	 * @description 获取最大排序号
	 * @return int
	 * @develop date 2016.08.25
	 */
	@Override
	public int getMaxPxh(){
		int pxh = 0;
		BigDecimal maxPxh = fixedCheckItemMapper.getMaxPxh();
		if(null != maxPxh){
			pxh = maxPxh.intValue() + 1;
		}
		return pxh;
	}

	/**
	 * @author liub
	 * @description 根据id查询定检项目
	 * @param id
	 * @return FixedCheckItem
	 * @develop date 2016.08.24
	 */
	@Override
	public FixedCheckItem selectByPrimaryKey(String id){
		return fixedCheckItemMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @author liub
	 * @description 根据id查询定检项目,包含制单人,审核人,批准人
	 * @param id
	 * @return FixedCheckItem
	 * @develop date 2016.09.29
	 */
	public FixedCheckItem selectById(String id){
		return fixedCheckItemMapper.selectById(id);
	}
	
	/**
	 * @author liub
	 * @description 根据维修方案编号查询定检项目信息,字段排序F_BBID desc,ZWMS desc
	 * @param wxfabh,dprtcode
	 * @return List<FixedCheckItem>
	 * @develop date 2016.09.28
	 */
	public List<FixedCheckItem> queryByWxfabh(String wxfabh,String dprtcode){
		return fixedCheckItemMapper.queryByWxfabh(wxfabh,dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 根据条件查询有差异维修方案的定检项目信息,字段排序djbh desc,ZWMS desc
	 * @param map
	 * @return List<Map<String, Object>>
	 * @develop date 2016.09.28
	 */
	public List<FixedCheckItem> queryByMap(Map<String, Object> map){
		//查询当前版本定检项目集合
		List<FixedCheckItem> fixedCheckItemList = fixedCheckItemMapper.queryByMap(map);
		List<String> djbhList = new ArrayList<String>();
		map.put("bb",map.get("fbb"));
		//查询前版本定检项目集合
		List<FixedCheckItem> fixedCheckItemOldList = fixedCheckItemMapper.queryByMap(map);
		for(FixedCheckItem fixedCheckItemOld : fixedCheckItemOldList){
			djbhList.add(fixedCheckItemOld.getDjbh());
		}
		for(FixedCheckItem fixedCheckItem : fixedCheckItemList){
			if(!djbhList.contains(fixedCheckItem.getDjbh())){
				fixedCheckItem.setfBbid(null);
				fixedCheckItem.setIsModify("true");
				continue;
			}
			for(FixedCheckItem fixedCheckItemOld : fixedCheckItemOldList){
				if(fixedCheckItem.getDjbh().equals(fixedCheckItemOld.getDjbh())){
					if(fixedCheckItem.getBb().compareTo(fixedCheckItemOld.getBb()) == 1){
						fixedCheckItem.setfBbid(fixedCheckItemOld.getId());
						fixedCheckItem.setIsModify("true");
					}else{
						fixedCheckItem.setIsModify("");
					}
				}
			}
		}
		Collections.sort(fixedCheckItemList, new Comparator<FixedCheckItem>() {
	        public int compare(FixedCheckItem o1, FixedCheckItem o2) {
	        	String isModify1 = o1.getIsModify();
	        	String djbh1 = o1.getDjbh();
	        	String isModify2 = o2.getIsModify();
	        	String djbh2 = o2.getDjbh();
	        	if(isModify1.equals(isModify2)){
	    			return djbh2.compareTo(djbh1);
	    		}else{
	    			return isModify2.compareTo(isModify1);
	    		}
	        }
	    });
		return fixedCheckItemList;
	}
	
	/**
	 * @author liub
	 * @description 根据条件新增定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	@Override
	public String add(FixedCheckItem fixedCheckItem) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String djbh;
		try {
			djbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.DJXM.getName());
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		fixedCheckItem.setId(id);
		fixedCheckItem.setDjbh(djbh);
		fixedCheckItem.setZxbs(LatestLogoEnum.INITIAL.getId());
		fixedCheckItem.setZt(StatusEnum.NO_EFFECT.getId());
		fixedCheckItem.setSpzt(ApproveStatusEnum.NOT_AUDITED.getId());
		fixedCheckItem.setDprtcode(user.getJgdm());
		fixedCheckItem.setZdrid(user.getId());
		fixedCheckItem.setZdbmid(user.getBmdm());
		fixedCheckItemMapper.insert(fixedCheckItem);
		commonRecService.write(id, TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, fixedCheckItem.getId());//插入日志
		fixedCheckWorkContentService.addWorkConetenList(fixedCheckItem.getFixedCheckWorkContentList(),id,czls, fixedCheckItem.getId(),user.getJgdm());
		monitorItemService.addMonitorItemList(fixedCheckItem.getMonitorItemList(),id,czls, fixedCheckItem.getId(),user.getJgdm());	
		return id;
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目修改权限
	 * @param id
	 * @develop date 2016.08.31
	 */
	@Override
	public void checkEdit(String id) throws BusinessException{
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(id);
		Integer zt = fItem.getZt();
		if(null == zt || StatusEnum.NO_EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有未生效的定检项目才能修改!");
		}
		Integer spzt = fItem.getSpzt();
		if(!(null == spzt || ApproveStatusEnum.NOT_AUDITED.getId().intValue() == spzt.intValue() || ApproveStatusEnum.AUDIT_DISMISSED.getId().intValue() == spzt.intValue() || ApproveStatusEnum.APPROVED_DISMISSED.getId().intValue() == spzt.intValue())){
			throw new BusinessException("对不起,只有未审核、审核驳回、批准驳回的定检项目才能修改!");
		}
	}
	
	/**
	 * @author liub
	 * @description 修改定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.24
	 */
	@Override
	public void edit(FixedCheckItem fixedCheckItem) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(fixedCheckItem.getId());
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId()},fItem.getZt(),"该定检项目已更新，请刷新后再进行操作!");
		verification(new Integer[]{
				ApproveStatusEnum.NOT_AUDITED.getId(),
				ApproveStatusEnum.AUDIT_DISMISSED.getId(),
				ApproveStatusEnum.APPROVED_DISMISSED.getId()
				},fItem.getSpzt(),"该定检项目已更新，请刷新后再进行操作!");
		fixedCheckItemMapper.updateByPrimaryKeySelective(fixedCheckItem);
		commonRecService.write(fixedCheckItem.getId(), TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, fixedCheckItem.getId());//插入日志
		fixedCheckWorkContentService.editWorkConetenList(fixedCheckItem.getFixedCheckWorkContentList() , fixedCheckItem.getId(),czls , fixedCheckItem.getId(),fixedCheckItem.getDprtcode());
		monitorItemService.editMonitorItemList(fixedCheckItem.getMonitorItemList(), fixedCheckItem.getId(),czls, fixedCheckItem.getId(),fixedCheckItem.getDprtcode());	
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目改版权限
	 * @param id
	 * @develop date 2016.08.26
	 */
	@Override
	public void checkModify(String id) throws BusinessException{
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(id);
		Maintenance maintenance = maintenanceService.queryMaxInfoByWxfabh(fItem.getWxfabh(),fItem.getDprtcode());
		BigDecimal bb = fItem.getBb();
		if(null == maintenance.getBb() || maintenance.getBb().compareTo(bb) != 1){
			throw new BusinessException("对不起,已经是最大版本,不能改版!");
		}
		//验证是否生效
		Integer zt = fItem.getZt();
		if(null == zt || StatusEnum.EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有生效的定检项目才能改版!!");
		}
		//如果维修方案是生效状态不能改版
		if(StatusEnum.EFFECT.getId().intValue() == maintenance.getZt().intValue()){
			throw new BusinessException("对不起,请先改版该定检项目对应的维修方案!");
		}
	}
	
	/**
	 * @author liub
	 * @description 改版定检项目
	 * @param fixedCheckItem
	 * @develop date 2016.08.26
	 */
	@Override
	public String modify(FixedCheckItem fixedCheckItem) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(fixedCheckItem.getId());
		Maintenance maintenance = maintenanceService.queryMaxInfoByWxfabh(fItem.getWxfabh(),fItem.getDprtcode());
		BigDecimal bb = fItem.getBb();
		Integer zt = fItem.getZt();
		if(null == maintenance.getBb() || maintenance.getBb().compareTo(bb) != 1 || null == zt || StatusEnum.EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("该定检项目已更新，请刷新后再进行操作!");
		}
		//如果维修方案是生效状态不能改版
		if(StatusEnum.EFFECT.getId().intValue() == maintenance.getZt().intValue()){
			throw new BusinessException("该定检项目对应的维修方案已更新，请刷新后再进行操作!");
		}
		String newId = UUID.randomUUID().toString();
		/* 修改原版本的数据 begin */
		FixedCheckItem oldFItem = new FixedCheckItem();
		oldFItem.setId(fixedCheckItem.getfBbid());
		oldFItem.setbBbid(newId);
		fixedCheckItemMapper.updateByPrimaryKeySelective(oldFItem);
		commonRecService.write(oldFItem.getId(), TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.REVISION, UpdateTypeEnum.UPDATE, oldFItem.getId());//插入日志
		/* 修改原版本的数据 end */
		/* 保存新版本的数据 begin */
		fixedCheckItem.setId(newId);
		fixedCheckItem.setZt(StatusEnum.NO_EFFECT.getId());
		fixedCheckItem.setZxbs(LatestLogoEnum.INITIAL.getId());
		fixedCheckItem.setSpzt(ApproveStatusEnum.NOT_AUDITED.getId());
		fixedCheckItem.setZdrid(user.getId());
		fixedCheckItem.setZdbmid(user.getBmdm());
		fixedCheckItemMapper.insert(fixedCheckItem);
		commonRecService.write(newId, TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, fixedCheckItem.getId());//插入日志
		/* 保存新版本的数据 end */
		fixedCheckWorkContentService.addWorkConetenList(fixedCheckItem.getFixedCheckWorkContentList() , fixedCheckItem.getId(),czls, fixedCheckItem.getId(),fixedCheckItem.getDprtcode());
		monitorItemService.addMonitorItemList(fixedCheckItem.getMonitorItemList(), fixedCheckItem.getId(),czls, fixedCheckItem.getId(),fixedCheckItem.getDprtcode());	
		return newId;
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目审核权限
	 * @param id
	 * @develop date 2016.09.28
	 */
	@Override
	public void checkAudit(String id) throws BusinessException{
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(id);
		//验证是否生效状态及审核状态
		Integer zt = fItem.getZt();
		if(null == zt || StatusEnum.NO_EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有未生效的定检项目才能审核!");
		}
		Integer spzt = fItem.getSpzt();
		if(!(null == spzt || ApproveStatusEnum.NOT_AUDITED.getId().intValue() == spzt.intValue())){
			throw new BusinessException("对不起,只有未审核的定检项目才能审核!");
		}
	}
	
	/**
	 * @author liub
	 * @description 审核
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	@Override
	public void audit(FixedCheckItem fixedCheckItem) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(fixedCheckItem.getId());
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId()},fItem.getZt(),"该定检项目已更新，请刷新后再进行操作!");
		verification(new Integer[]{ApproveStatusEnum.NOT_AUDITED.getId()},fItem.getSpzt(),"该定检项目已更新，请刷新后再进行操作!");
		fixedCheckItem.setShrid(user.getId());
		fixedCheckItem.setShbmid(user.getBmdm());
		fixedCheckItemMapper.updateByPrimaryKeySelective(fixedCheckItem);
		if(ApproveStatusEnum.AUDIT_DISMISSED.getId().intValue() == fixedCheckItem.getSpzt().intValue()){
			commonRecService.write(fixedCheckItem.getId(), TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE, fixedCheckItem.getId());//插入日志
		}else{
			commonRecService.write(fixedCheckItem.getId(), TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, fixedCheckItem.getId());//插入日志
		}
	}
	
	/**
	 * @author liub
	 * @description 检查定检项目批准权限
	 * @param id
	 * @develop date 2016.09.28
	 */
	@Override
	public void checkApprove(String id) throws BusinessException{
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(id);
		Integer spzt = fItem.getSpzt();
		if(null == spzt || ApproveStatusEnum.AUDITED.getId().intValue() != spzt.intValue()){
			throw new BusinessException("对不起,只有已审核的定检项目才能批准!");
		}
		//验证是否生效状态及审核状态
		Integer zt = fItem.getZt();
		if(null == zt || StatusEnum.NO_EFFECT.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有未生效的定检项目才能批准!");
		}
	}
	
	/**
	 * @author liub
	 * @description 批准
	 * @param fixedCheckItem
	 * @develop date 2016.09.29
	 */
	public void approve(FixedCheckItem fixedCheckItem) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(fixedCheckItem.getId());
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId()},fItem.getZt(),"该定检项目已更新，请刷新后再进行操作!");
		verification(new Integer[]{ApproveStatusEnum.AUDITED.getId()},fItem.getSpzt(),"该定检项目已更新，请刷新后再进行操作!");
		fixedCheckItem.setPfrid(user.getId());
		fixedCheckItem.setPfbmid(user.getBmdm());
		fixedCheckItemMapper.updateByPrimaryKeySelective(fixedCheckItem);
		if(ApproveStatusEnum.APPROVED_DISMISSED.getId().intValue() == fixedCheckItem.getSpzt().intValue()){
			commonRecService.write(fixedCheckItem.getId(), TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE, fixedCheckItem.getId());//插入日志
		}else{
			commonRecService.write(fixedCheckItem.getId(), TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, fixedCheckItem.getId());//插入日志
		}
	}
	
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList,yj
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchAudit(List<String> idList,String yj) throws BusinessException{
		List<String> auditIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<FixedCheckItem> fItemList = fixedCheckItemMapper.queryByIdList(idList);
		for (FixedCheckItem item : fItemList) {
			if(StatusEnum.NO_EFFECT.getId().intValue() == item.getZt().intValue() && ApproveStatusEnum.NOT_AUDITED.getId().intValue() == item.getSpzt().intValue()){
				auditIdList.add(item.getId());
				buffer.append("定检项目【").append(item.getDjbh()).append("】审核通过!<br>");
			}else{
				buffer.append("定检项目【").append(item.getDjbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			User user = ThreadVarUtil.getUser();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			fixedCheckItemMapper.updateBatchAudit(paramMap);
			commonRecService.write("id",auditIdList, TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, null);//插入日志
		}
		return buffer.toString();
	}
	

	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList,yj
	 * @develop date 2016.12.28
	 */
	@Override
	public String updateBatchApprove(List<String> idList,String yj) throws BusinessException{
		List<String> approveIdList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		List<FixedCheckItem> fItemList = fixedCheckItemMapper.queryByIdList(idList);
		for (FixedCheckItem item : fItemList) {
			if(StatusEnum.NO_EFFECT.getId().intValue() == item.getZt().intValue() && ApproveStatusEnum.AUDITED.getId().intValue() == item.getSpzt().intValue()){
				approveIdList.add(item.getId());
				buffer.append("定检项目【").append(item.getDjbh()).append("】批准通过!<br>");
			}else{
				buffer.append("定检项目【").append(item.getDjbh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pfrid", user.getId());
		paramMap.put("pfbmid", user.getBmdm());
		paramMap.put("idList", approveIdList);
		paramMap.put("pfyj", yj);
		if(null != approveIdList && approveIdList.size() > 0){
			fixedCheckItemMapper.updateBatchApprove(paramMap);
			commonRecService.write("id",approveIdList, TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, null);//插入日志
		}
		return buffer.toString();
	}
	
	/**
	 * @author liub
	 * @description 调整排序
	 * @param fixedCheckItemList
	 * @develop date 2016.08.25
	 */
	@Override
	public void order(List<FixedCheckItem> fixedCheckItemList){
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		List<String> columnValueList = new ArrayList<String>();
		for (FixedCheckItem fixedCheckItem : fixedCheckItemList) {
			fixedCheckItemMapper.updateByPrimaryKeySelective(fixedCheckItem);
			columnValueList.add(fixedCheckItem.getId());
		}
		if(columnValueList.size() > 0){
			commonRecService.write("id", columnValueList, TableEnum.B_G_012, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);
		}
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.25
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		FixedCheckItem fItem = fixedCheckItemMapper.selectByPrimaryKey(id);
		verification(new Integer[]{StatusEnum.NO_EFFECT.getId(),StatusEnum.WAIT_EFFECT.getId()},fItem.getZt(),"该定检项目已更新，请刷新后再进行操作!");
		FixedCheckItem cancelFItem = new FixedCheckItem();
		cancelFItem.setId(id);
		cancelFItem.setZt(StatusEnum.CANCEL.getId());
		fixedCheckItemMapper.updateByPrimaryKeySelective(cancelFItem);
		commonRecService.write(id, TableEnum.B_G_012, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, cancelFItem.getId());//插入日志
	}
	
	/**
	 * @author liub
	 * @description  根据维修方案编号、版本作废
	 * @param wxfabh维修方案编号,wxfabb维修方案版本
	 * @return
	 * @develop date 2016.12.12
	 */
	@Override
	public void cancelByWxfabhAndBb(String wxfabh,BigDecimal wxfabb,String dprtcode){
		fixedCheckItemMapper.cancelByWxfabhAndBb(wxfabh, wxfabb.toString(),dprtcode);
	}
	
	/**
	 * @author liub
	 * @description  将定检项目修改成待生效
	 * @param paramMap
	 * @develop date 2016.09.29
	 */
	@Override
	public void updateWaitEffective(List<String> idList,String czls){
		Map<String, Object> paramMap = null;
		User user = ThreadVarUtil.getUser();
		if(null != idList && 0 < idList.size()){
			paramMap = new HashMap<String, Object>();
			paramMap.put("zt", StatusEnum.WAIT_EFFECT.getId());
			paramMap.put("idList", idList);
			fixedCheckItemMapper.updateWaitEffective(paramMap);
			commonRecService.write("id",idList, TableEnum.B_G_012, user.getId(),czls,LogOperationEnum.SUBMIT_PRODUCTION_CONFIRM, UpdateTypeEnum.UPDATE, null);//保存定检项目历史记录信息
		}
	}
	
	/**
	 * @author liub
	 * @description 生效定检项目
	 * @param wxfabh维修方案编号,wxfabb维修方案版本
	 * @develop date 2016.09.24
	 */
	@Override
	public void sbEffective(String wxfabh,BigDecimal wxfabb,String czls,String dprtcode) throws BusinessException{
		//验证版本号是否为空
		if(null == wxfabb){
			throw new BusinessException("对不起,维修方案版本不能为空!");
		}
		List<String> nids = null;//记录需要生成最新版本的id集合
		List<String> oids = null;//记录需要生成老版本的id集合
		List<String> fCodes = null;//记录需要生成最新版本的定检编号集合
		List<String> cCodes = null;//记录发生改版的定检编号集合
		Map<String, Integer> countMap = null;//记录定检编号个数
		Map<String, Object> paramMap = null;
		User user = ThreadVarUtil.getUser();
		List<FixedCheckItem> fList = fixedCheckItemMapper.queryByWxfabh(wxfabh,dprtcode);
		//将需要生成最新版本的id放入集合
		nids = new ArrayList<String>();
		fCodes = new ArrayList<String>();
		cCodes = new ArrayList<String>();
		countMap = new HashMap<String, Integer>();
		for (FixedCheckItem f : fList) {
			if(null == countMap.get(f.getDjbh())){
				countMap.put(f.getDjbh(), 1);
			}else{
				countMap.put(f.getDjbh(), countMap.get(f.getDjbh())+1);
			}
		}
		for (FixedCheckItem f : fList) {
			if((wxfabb.compareTo(f.getBb()) == 0 || countMap.get(f.getDjbh()) == 1) && f.getDprtcode().equals(user.getJgdm())){
				nids.add(f.getId());
				fCodes.add(f.getDjbh());
			}
		}
		for (FixedCheckItem f : fList) {
			if(wxfabb.compareTo(f.getBb()) == 0 && f.getDprtcode().equals(user.getJgdm())){
				cCodes.add(f.getDjbh());
			}
		}
		if(nids.size() > 0){
			paramMap = new HashMap<String, Object>();
			paramMap.put("zt", StatusEnum.EFFECT.getId());
			paramMap.put("zxbs", LatestLogoEnum.LATEST_VERSION.getId());
			paramMap.put("idList", nids);
			fixedCheckItemMapper.updateEffective(paramMap);
			//将需要生成老版本的id放入集合
			oids = new ArrayList<String>();
			for (FixedCheckItem f : fList) {
				if(wxfabb.compareTo(f.getBb()) == 1 && fCodes.contains(f.getDjbh()) && countMap.get(f.getDjbh()) > 1 && f.getDprtcode().equals(user.getJgdm())){
					oids.add(f.getId());
					nids.add(f.getId());
				}
			}
			if(oids.size() > 0){
				paramMap = new HashMap<String, Object>();
				paramMap.put("zxbs", LatestLogoEnum.OLD_VERSION.getId());
				paramMap.put("idList", oids);
				fixedCheckItemMapper.updateBbToOld(paramMap);
			}
			commonRecService.write("id",nids, TableEnum.B_G_012, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);//保存定检项目历史记录信息
		}
		
		/*
		 * 升版后将维修方案对应装机清单的定检项目的同步标识改为待同步
		 */
		if(!cCodes.isEmpty()){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("wxfabh", wxfabh);
			param.put("djbhs", cCodes);
			param.put("wxfabb", wxfabb);
			// 查询所有受影响的定检项目
			List<ScheduledCheckItem> items = scheduledCheckItemEditableMapper.selectInfluencedByWxfaData(param);
			if(!items.isEmpty()){
				StringBuilder itemIds = new StringBuilder();
				for (ScheduledCheckItem item : items) {
					item.setWhrid(user.getId());
					item.setWhdwid(user.getBmdm());
					item.setWhsj(new Date());
					itemIds.append("'").append(item.getId()).append("'").append(",");
				}
				itemIds.deleteCharAt(itemIds.lastIndexOf(","));
				param.put("items", items);
				param.put("whrid", user.getId());
				param.put("whdwid", user.getBmdm());
				param.put("whsj", new Date());
				// 更新定检项目同步标识
				scheduledCheckItemEditableMapper.updateTbbs(param);
				// 更新定检监控项目同步标识
				scheduledCheckMonitorItemEditableMapper.updateTbbs(param);
				// 记录日志
				if(itemIds.length() > 0){
					commonRecService.writeByWhere("b.mainid in ("+itemIds+") and b.zt = 1", 
							TableEnum.B_S_0010301, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);
				}
			}
		}
	}
	
	/**
	 * @author sunji
	 * @description  根据编号和最新标识查询数据
	 * @param idList
	 * @develop date 2016.09.29
	 */
	public FixedCheckItem selectByZxbsAndBh(FixedCheckItem fixedCheckItem){
		return fixedCheckItemMapper.selectByZxbsAndBh(fixedCheckItem);
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
	
	/**
	 * @author liub
	 * @description 格式化监控条件
	 * @param list
	 * @return
	 * @develop date 2016.08.20
	 */
	private void formatMonitor(List<FixedCheckItem> list){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> idList = new ArrayList<String>();//定检项目id集合
		for (FixedCheckItem fItem : list) {
			idList.add(fItem.getId());
		}
		paramMap.put("idList", idList);
		//设置监控条件
		if(idList.size() > 0){
			List<FixedCheckItem> fItemList = fixedCheckItemMapper.findJoinMonitorByIds(paramMap);
			Map<String, String> monitorMap = getAllMonitorMap();//监控map
			for (FixedCheckItem fItem : list) {
				StringBuffer jktj = new StringBuffer();
				for (FixedCheckItem fItem2 : fItemList) {
					if(fItem.getId().equals(fItem2.getId())){
						for (MonitorItem monitorItem : fItem2.getMonitorItemList()) {
							jktj.append(monitorMap.get(monitorItem.getJklbh()))
								.append("：").append(monitorItem.getZqz()+UnitEnum.getName(monitorItem.getDw()))
								.append(" / ");
						}
						if(jktj.length() > 0){
							jktj.deleteCharAt(jktj.length() - 2);
						}
					}
				}
				fItem.setJktj(jktj.toString());
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 获取监控信息map,用于翻译
	 * @param 
	 * @return Map<String, Object>
	 * @develop date 2016.08.20
	 */
	private Map<String, String> getAllMonitorMap(){
		Map<String, String> monitorMap = new HashMap<String, String>();//监控设置项Map
		//将监控设置项放入Map
		List<MonitorOptionClass> MonitorOptionClassList = monitorClassService.queryAll();
		for (MonitorOptionClass monitorOptionClass : MonitorOptionClassList) {
			monitorMap.put(monitorOptionClass.getJkflbh(), monitorOptionClass.getMs());
			for(MonitorOptionItem monitorOptionItem : monitorOptionClass.getItemList()){
				monitorMap.put(monitorOptionItem.getJklbh(), monitorOptionItem.getMs());
			}
		}
		return monitorMap;
	}
	
}
