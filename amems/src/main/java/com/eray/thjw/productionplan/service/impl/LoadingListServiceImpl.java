package com.eray.thjw.productionplan.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.ComponentUsage;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.exception.SystemException;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.LoadingListEditableMapper;
import com.eray.thjw.productionplan.dao.LoadingListMapper;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.productionplan.po.TimeMonitorSetting;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.LoadingListToSpecialConditionService;
import com.eray.thjw.productionplan.service.ScheduledCheckItemService;
import com.eray.thjw.productionplan.service.ScheduledCheckMonitorItemService;
import com.eray.thjw.productionplan.service.TimeMonitorSettingService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.PageHelper;

import enu.BjEnum;
import enu.ComponentOperationEnum;
import enu.ComponentTypeEnum;
import enu.ControlTypeEnum;
import enu.LogOperationEnum;
import enu.MonitorItemEnum;
import enu.NodeLevelEnum;
import enu.PlaneComponentPositionEnum;
import enu.SynchronzeEnum;
import enu.TableEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;
import enu.UpdateTypeEnum;

@Service
public class LoadingListServiceImpl implements LoadingListService {

	@Resource
	private LoadingListMapper loadingListMapper;

	@Resource
	private LoadingListEditableMapper loadingListEditableMapper;

	@Resource
	private TimeMonitorSettingService timeMonitorSettingService;

	@Resource
	private LoadingListToSpecialConditionService loadingListToSpecialConditionService;

	@Resource
	private CommonRecService commonRecService;

	@Resource
	private ScheduledCheckItemService scheduledCheckItemService;

	@Resource
	private ScheduledCheckMonitorItemService scheduledCheckMonitorItemService;
	
	@Resource
	private PlaneDataMapper planeDataMapper;
	
	@Resource
	private HCMainDataService hcMainDataService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;

	@Override
	public List<LoadingList> selectLoadingList(LoadingList ll) throws RuntimeException {
		return loadingListMapper.selectLoadingList(ll);
	}

	/**
	 * 根据主键查询-编辑区
	 */
	@Override
	public LoadingList selectEditableByPrimaryKey(LoadingList ll) throws RuntimeException {
		return loadingListEditableMapper.selectByPrimaryKey(ll.getId());
	}

	/**
	 * 新增飞机装机清单-编辑区
	 */
	@Override
	public void insertPlaneEditable(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		LoadingList ll = new LoadingList();
		ll.setId(UUID.randomUUID().toString());
		ll.setFjzch(pd.getFjzch());
		ll.setBjlx(ComponentTypeEnum.WHOLE.getCode());
		// 默认装机数量为1
		ll.setZjsl(1);
		ll.setWz(PlaneComponentPositionEnum.JS.getCode());
		ll.setZt(ComponentOperationEnum.INSTALLED.getCode());
		ll.setScrq(pd.getCcrq());
		ll.setBz(pd.getBz());
		ll.setZwmc(pd.getFjzch());
		ll.setYwmc(pd.getFjzch());
		ll.setXlh(pd.getXlh());
		ll.setJh(pd.getFjzch());
		ll.setShzh(pd.getShzh());
		ll.setKzlx(ControlTypeEnum.NON_CONTROL.getCode());
		ll.setIsDj(BjEnum.YES_BJ.getId());
		// 机身的层级为0，是父节点
		ll.setCj(NodeLevelEnum.LEVEL_ROOT.getCode());
		ll.setKbs("0");
		ll.setBjgzjl(pd.getJsgzjl());
		ll.setLlklx(2);
		// 设置默认值
		setDefaultValue(ll);
		ll.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		loadingListEditableMapper.insertSelective(ll);
		// 插入日志
		commonRecService.write(ll.getId(), TableEnum.B_S_001, user.getId(), 
				czls, logOperationEnum, UpdateTypeEnum.SAVE, ll.getId());
	}

	/**
	 * 修改飞机装机清单-编辑区
	 */
	@Override
	public void editPlaneEditable(PlaneData pd, String czls, LogOperationEnum logOperationEnum) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		LoadingList ll = new LoadingList();
		ll.setFjzch(pd.getFjzch());
		ll.setBjlx(ComponentTypeEnum.WHOLE.getCode());
		ll.setDprtcode(pd.getDprtcode());
		ll = loadingListEditableMapper.selectByPlane(ll);
		ll.setScrq(pd.getCcrq());
		ll.setBz(pd.getBz());
		ll.setShzh(pd.getShzh());
		// 设置默认值
		setDefaultValue(ll);
		loadingListEditableMapper.updateByPrimaryKeySelective(ll);
		// 插入日志
		commonRecService.write(ll.getId(), TableEnum.B_S_001, user.getId(), 
				czls, logOperationEnum, UpdateTypeEnum.UPDATE, ll.getId());
	}

	/**
	 * 查询装机清单-编辑区
	 */
	@Override
	public List<LoadingList> selectEditableInTree(LoadingList param) throws RuntimeException {
		List<LoadingList> list = loadingListEditableMapper.selectEditableInTree(param);
		// 设置虚拟父节点id
		setVirtualParentId(list, true);
		return list;
	}
	
	/**
	 * 设置虚拟父节点id
	 * @param list
	 */
	private void setVirtualParentId (List<LoadingList> list, boolean hasUnRelationNode){
		
		// 设置pid
		for (LoadingList ll : list) {
			if(ll.getCj() == 1){
				if(ll.getWz() == 0){
					ll.setpId("js");
				}else if(ll.getWz() == 1){
					ll.setpId("zf");
				}else if(ll.getWz() == 2){
					ll.setpId("yf");
				}else if(ll.getWz() == 3){
					ll.setpId("jc");
				}else if(ll.getWz() == 4){
					ll.setpId("ssd");
				}else if(ll.getWz() == 5){
					ll.setpId("wdg");
				}
			}else{
				ll.setpId(ll.getFjdid());
			}
		}
		
		// 获取飞机id
		String rootId = getRootId(list);
		
		// 机身
		LoadingList js = new LoadingList();
		js.setId("js");
		js.setWz(0);
		js.setCj(0);
		js.setJh("机身");
		js.setFjdid(rootId);
		js.setpId(rootId);
		list.add(js);
		
		// 左发
		LoadingList zf = new LoadingList();
		zf.setId("zf");
		zf.setWz(1);
		zf.setCj(0);
		zf.setJh("1#左发");
		zf.setFjdid(rootId);
		zf.setpId(rootId);
		list.add(zf);
		
		// 右发
		LoadingList yf = new LoadingList();
		yf.setId("yf");
		yf.setWz(2);
		yf.setCj(0);
		yf.setJh("2#右发");
		yf.setFjdid(rootId);
		yf.setpId(rootId);
		list.add(yf);
		
		// 外吊挂
		LoadingList wdg = new LoadingList();
		wdg.setId("wdg");
		wdg.setWz(5);
		wdg.setCj(0);
		wdg.setJh("外吊挂");
		wdg.setFjdid(rootId);
		wdg.setpId(rootId);
		list.add(wdg);
		
		// 搜索灯
		LoadingList ssd = new LoadingList();
		ssd.setId("ssd");
		ssd.setWz(4);
		ssd.setCj(0);
		ssd.setJh("搜索灯");
		ssd.setFjdid(rootId);
		ssd.setpId(rootId);
		list.add(ssd);
		
		// 绞车
		LoadingList jc = new LoadingList();
		jc.setId("jc");
		jc.setWz(3);
		jc.setCj(0);
		jc.setJh("绞车");
		jc.setFjdid(rootId);
		jc.setpId(rootId);
		list.add(jc);
		
		// 未关联部件
		if(hasUnRelationNode){
			LoadingList wgl = new LoadingList();
			wgl.setId("0");
			wgl.setWz(0);
			wgl.setCj(0);
			wgl.setJh("未关联部件");
			wgl.setFjdid(rootId);
			wgl.setpId(rootId);
			list.add(wgl);
		}
		
	}
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	private String getRootId(List<LoadingList> list){
		for (LoadingList ll : list) {
			if(ll.getBjlx() == 2){
				return ll.getId();
			}
		}
		return "";
	}
	
	/**
	 * 查询装机清单-生效区
	 */
	@Override
	public List<LoadingList> selectEffectiveInTree(LoadingList ll) throws RuntimeException {
		
		List<LoadingList> list = loadingListEditableMapper.selectEffectiveInTree(ll);
		// 设置虚拟父节点id
		setVirtualParentId(list, false);
		return list;
	}

	/**
	 * 查询指定飞机无且无子部件的零件清单
	 */
	@Override
	public Map<String, Object> queryNoChildList(LoadingList ll) throws RuntimeException {
		PageHelper.startPage(ll.getPagination());
		List<LoadingList> list = loadingListEditableMapper.queryNoChildList(ll);
		return PageUtil.pack4PageHelper(list, ll.getPagination());
	}

	/**
	 * 改变父节点（维护子部件关系）-编辑区
	 */
	@Override
	public void doChangeParent(List<LoadingList> list) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		for (LoadingList ll : list) {
			// validateLoadingList(ll);
			// 设置块标识
			setKbs(ll);
			// 设置默认值
			setDefaultValue(ll);
		}
		loadingListEditableMapper.batchUpdate(list);
		// 插入日志
		logRecList(list, czls, UpdateTypeEnum.UPDATE);
	}

	/**
	 * 新增或修改编辑区的装机清单-编辑区
	 */
	@Override
	public String doInsertOrUpdateEditable(LoadingList ll) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		// 当前操作是否为插入
		UpdateTypeEnum type = UpdateTypeEnum.UPDATE;
		LogOperationEnum logOperationEnum = LogOperationEnum.EDIT;
		// 验证装机清单数据
		validateLoadingList(ll);
		// 设置默认值
		setDefaultValue(ll);
		// 新增时生成uuid并生成块标识
		if (Utils.Str.isEmpty(ll.getId())) {
			ll.setId(UUID.randomUUID().toString());
			type = UpdateTypeEnum.SAVE;
			logOperationEnum = LogOperationEnum.SAVE_WO;
		} else {
			if (ll.getCj() == 1) {
				// 同步子节点的位置
				synchronizeWz(ll, czls);
			}
			// 如果将控制类型改为非控制件，定检件改为非定检件，则删除相应的时控件数据和定检件数据
			updateTimeMonitorAndFixedMonitor(ll, czls, LogOperationEnum.EDIT);
		}
		// 设置块标识
		setKbs(ll);
		// 新增或修改
		loadingListEditableMapper.insertOrUpdateEditable(ll);
		// 插入日志
		logRec(ll, czls, type, logOperationEnum);
		// 修改装机清单时，同步航材主数据
		hcMainDataService.updateByLoadingList(ll, czls);
		return ll.getId();
	}

	/**
	 * 验证装机清单数据
	 * 
	 * @param ll
	 */
	private void validateLoadingList(LoadingList ll) {
		// 验证是否有飞机权限
		List<String> list = new ArrayList<String>();
		list.add(ll.getFjzch());
		if(!planeModelDataService.existsAircraft(ThreadVarUtil.getUser().getId(), 
				ThreadVarUtil.getUser().getUserType(), ThreadVarUtil.getUser().getJgdm(), list)){
			throw new SystemException("无该飞机权限！");
		}
		
		// 一级节点的同一位置的的部件只能存在一个（除了机身外）
		if (NodeLevelEnum.LEVEL_1.getCode().equals(ll.getCj()) && !PlaneComponentPositionEnum.JS.getCode().equals(ll.getWz())) {
			LoadingList param = new LoadingList();
			param.setFjzch(ll.getFjzch());
			param.setWz(ll.getWz());
			param.setCj(ll.getCj());
			param.setZt(1);
			param.setDprtcode(ll.getDprtcode());
			if (ll.getId() != null) {
				param.setNotId(ll.getId());
			}
			if (loadingListEditableMapper.queryCount(param) >= 1) {
				throw new SystemException("位置为：" + PlaneComponentPositionEnum.getName(ll.getWz()) + "的一级部件已存在！");
			}
		}
		
		if (!Utils.Str.isEmpty(ll.getXlh())) {
			LoadingList param2 = new LoadingList();
			param2.setJh(ll.getJh());
			param2.setXlh(ll.getXlh());
			param2.setZt(1);
			param2.setDprtcode(ll.getDprtcode());
			if (ll.getId() != null) {
				param2.setNotId(ll.getId());
			}
			if (loadingListEditableMapper.queryCount(param2) >= 1) {
				throw new SystemException("件号为：" + ll.getJh() + "，序列号为：" + ll.getXlh() + "，状态为：装上的部件，已存在！");
			}
		} else if(!Utils.Str.isEmpty(ll.getNbsbm())){
			LoadingList param3 = new LoadingList();
			param3.setJh(ll.getJh());
			param3.setPch(ll.getPch());
			param3.setNbsbm(ll.getNbsbm());
			param3.setDprtcode(ll.getDprtcode());
			param3.setZt(1);
			if (ll.getId() != null) {
				param3.setNotId(ll.getId());
			}
			if (loadingListEditableMapper.queryCount(param3) >= 1) {
				throw new SystemException("件号为：" + ll.getJh() + (StringUtils.isBlank(ll.getPch())?"": ("，批次号为：" + ll.getPch()))
						+ "，内部识别码为：" + ll.getNbsbm() + "，状态为：装上的部件，已存在！");
			}
		}
	}

	/**
	 * 设置块标识
	 * 
	 * @param ll
	 */
	private void setKbs(LoadingList ll) {
		if (ll.getCj() == 1) {
			ll.setKbs(ll.getId());
		} else if (ll.getCj() == 2) {
			ll.setKbs(ll.getFjdid());
		}
	}

	/**
	 * 作废部件-编辑区
	 */
	@Override
	public void doScrapEditable(LoadingList ll) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		ll.setZt(ComponentOperationEnum.SCRAP.getCode());
		// 设置默认值
		setDefaultValue(ll);
		// 作废部件
		loadingListEditableMapper.updateByPrimaryKeySelective(ll);
		// 记录日志
		logRec(ll, czls, UpdateTypeEnum.DELETE, LogOperationEnum.ZUOFEI);
		// 写入装机清单拆下历史
		writeToLoadingListHistory(ll.getId(), ll.getId());
		// 更新对应子部件的父节点id为0
		loadingListEditableMapper.updateChildrenFjdid(ll);
		// 记录日志
		List<String> fjdid = new ArrayList<String>();
		fjdid.add(ll.getId());
		commonRecService.write("fjdid", fjdid, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), 
				czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);
		// 删除时控件数据
		timeMonitorSettingService.deleteEditable(ll.getId(), czls, LogOperationEnum.EDIT);
		// 删除定检项目
		ScheduledCheckItem param = new ScheduledCheckItem();
		param.setZjqdid(ll.getId());
		scheduledCheckItemService.deleteByZjqdid(param, czls, LogOperationEnum.EDIT);
		// 删除监控项目
		scheduledCheckMonitorItemService.deleteByZjqdid(ll.getId(), czls, LogOperationEnum.EDIT);
	}

	/**
	 * 飞机装机清单查询-表状-编辑区
	 */
	@Override
	public Map<String, Object> selectEditableInTable(LoadingList ll) throws RuntimeException {
		PageHelper.startPage(ll.getPagination());
		List<LoadingList> list = loadingListEditableMapper.queryPage(ll);
		return PageUtil.pack4PageHelper(list, ll.getPagination());
	}

	/**
	 * 飞机装机清单查询-表状-生效区
	 */
	@Override
	public Map<String, Object> selectInTable(LoadingList ll) throws RuntimeException {
		PageHelper.startPage(ll.getPagination());
		List<LoadingList> list = loadingListMapper.queryPage(ll);
		return PageUtil.pack4PageHelper(list, ll.getPagination());
	}

	/**
	 * 级联作废部件-编辑区
	 */
	@Override
	public void doCascadeScrapEditable(LoadingList ll) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		// 状态=报废
		ll.setZt(ComponentOperationEnum.SCRAP.getCode());
		// 设置默认值
		setDefaultValue(ll);
		loadingListEditableMapper.cascadeScrapEditable(ll);
		// 插入日志
		List<String> childrenIds = loadingListEditableMapper.getChildrenId(ll);
		commonRecService.write("id", childrenIds, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(),
				czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, null);
		// 写入装机清单拆下历史
		writeToLoadingListHistory(loadingListEditableMapper.getChildren(ll));
		List<String> fjzchs = new ArrayList<String>();
		fjzchs.add(ll.getFjzch());
		// 删除时控件数据
		timeMonitorSettingService.cascadeDeleteEditable(ll.getId(), czls, LogOperationEnum.EDIT);
		// 删除定检项目
		ScheduledCheckItem param = new ScheduledCheckItem();
		param.setZjqdid(ll.getId());
		scheduledCheckItemService.cascadeDeleteByZjqdid(param, czls, LogOperationEnum.EDIT);
		// 删除监控项目
		scheduledCheckMonitorItemService.cascadeDeleteByZjqdid(ll.getId(), czls, LogOperationEnum.EDIT);
	}

	/**
	 * 同步子节点的位置-编辑区
	 * 
	 * @param ll
	 */
	private void synchronizeWz(LoadingList form_data, String czls) {
		LoadingList db_data = loadingListEditableMapper.selectByPrimaryKey(form_data.getId());
		// 父节点位置发生了改变
		if (!form_data.getWz().equals(db_data.getWz())) {
			// 更新子节点位置
			loadingListEditableMapper.updateChildenWz(form_data);
			List<String> list = new ArrayList<String>();
			list.add(form_data.getId());
			commonRecService.write("fjdid", list, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(),
					czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, null);
		}
	}

	@Override
	public LoadingList selectConutKey(LoadingList ll) {

		return loadingListMapper.selectConutKey(ll);
	}

	/**
	 * 查询时控件-编辑区
	 */
	@Override
	public List<LoadingList> queryEditableTimeMonitor(LoadingList ll) throws RuntimeException {
		return loadingListEditableMapper.selectTimeMonitor(ll);
	}

	/**
	 * 修改时控件设置
	 */
	@Override
	public void modifyTimeMonitorEditable(LoadingList ll) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		// 更新装机清单数据
		setDefaultValue(ll);
		loadingListEditableMapper.updateByPrimaryKeySelective(ll);
		// 记录日志
		logRec(ll, czls, UpdateTypeEnum.UPDATE, LogOperationEnum.EDIT);
		// 更新时控件设置
		timeMonitorSettingService.saveEditable(ll.getSettings(), ll.getId(), czls, LogOperationEnum.EDIT);
		// 更新时控件特殊飞行情况
		loadingListToSpecialConditionService.saveEditable(ll.getConditions(), ll.getId(), czls, LogOperationEnum.EDIT);
	}

	/**
	 * 设置默认值
	 * 
	 * @param ll
	 */
	private void setDefaultValue(LoadingList ll) {
		User user = ThreadVarUtil.getUser();
		// 未生效
		ll.setSxzt(0);
		// 待同步
		ll.setTbbs(SynchronzeEnum.TO_DO.getCode());
		ll.setWhrid(user.getId());
		ll.setWhdwid(user.getBmdm());
		ll.setWhsj(new Date());
	}

	/**
	 * 查询定检件-编辑区
	 */
	@Override
	public List<LoadingList> queryEditableFixedMonitor(LoadingList ll) throws RuntimeException {
		return loadingListEditableMapper.selectFixMonitor(ll);
	}

	/**
	 * 根据时控件表和定检件表查询tc和tv
	 */
	@Override
	public List<TimeMonitorSetting> getTcAndTv(LoadingList ll) throws RuntimeException {
		return loadingListEditableMapper.getTcAndTv(ll.getId());
	}

	/**
	 * 保存定检监控项目
	 */
	@Override
	public void saveFixMonitorEditable(ScheduledCheckItem item) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		// 记录日志
		logRec(new LoadingList(item.getZjqdid(), item.getFjzch()), czls, UpdateTypeEnum.UPDATE, LogOperationEnum.EDIT);
		// 保存定检项目
		String mainid = scheduledCheckItemService.saveEditable(item, czls, LogOperationEnum.EDIT);
		// 保存监控项目
		scheduledCheckMonitorItemService.saveEditable(item.getMonitorItemList(), mainid, item.getZjqdid(), czls, LogOperationEnum.EDIT);
	}

	/**
	 * 删除定检监控项目
	 */
	@Override
	public void deleteFixMonitorEditable(ScheduledCheckItem item) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		// 记录日志
		logRec(new LoadingList(item.getZjqdid(), item.getFjzch()), czls, UpdateTypeEnum.UPDATE, LogOperationEnum.EDIT);
		// 删除定检项目
		scheduledCheckItemService.deleteEditable(item, czls, LogOperationEnum.EDIT);
		// 删除监控项目
		scheduledCheckMonitorItemService.deleteEditable(item.getId(), czls, LogOperationEnum.EDIT, item.getZjqdid(), true);
	}

	/**
	 * 修改定检监控项目
	 */
	@Override
	public void updateFixMonitorEditable(ScheduledCheckItem item) throws RuntimeException {
		String czls = UUID.randomUUID().toString();
		// 记录日志
		logRec(new LoadingList(item.getZjqdid(), item.getFjzch()), czls, UpdateTypeEnum.UPDATE, LogOperationEnum.EDIT);
		// 更新定检项目
		scheduledCheckItemService.updateEditable(item, czls, LogOperationEnum.EDIT);
		// 删除监控项目
		scheduledCheckMonitorItemService.deleteEditable(item.getId(), czls, LogOperationEnum.EDIT, item.getZjqdid(), false);
		// 保存监控项目
		scheduledCheckMonitorItemService.saveEditable(item.getMonitorItemList(), item.getId(), item.getZjqdid(), czls, LogOperationEnum.EDIT);
	}

	/**
	 * 根据件号和序列号判断部件是否存在
	 */
	@Override
	public List<LoadingList> findByJhAndXlh(LoadingList ll) throws RuntimeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoadingList param = new LoadingList();
		param.setJh(ll.getJh());
		param.setXlh(ll.getXlh());
		param.setDprtcode(ll.getDprtcode());
		param.setZt(1);
		List<LoadingList> list = loadingListEditableMapper.select(param);
		if (list.isEmpty()) {
			resultMap.put("isExist", false);
		} else if (list.size() == 1) {
			resultMap.put("isExist", true);
			resultMap.put("loadingList", list.get(0));
		} else {
			throw new SystemException("同一件号、序列号的装上部件存在多个！");
		}
		return list;
	}

	@Override
	public void updatePlaneEditable(LoadingList loadingList) throws RuntimeException {
		loadingListMapper.updatePlaneEditable(loadingList);

	}

	/**
	 * 如果将控制类型改为非控制件，定检件改为非定检件，则删除相应的时控件数据和定检件数据
	 * 
	 * @param ll
	 *            表单数据
	 */
	private void updateTimeMonitorAndFixedMonitor(LoadingList formData, String czls, LogOperationEnum logOperationEnum) {
		// 数据库数据
		LoadingList dbData = loadingListEditableMapper.selectByPrimaryKey(formData.getId());
		// 控制类型发生了改变，且现在的控制类型为非控制件
		if (!dbData.getKzlx().equals(formData.getKzlx()) && ControlTypeEnum.NON_CONTROL.getCode().equals(formData.getKzlx())) {
			// 删除时控件数据s
			timeMonitorSettingService.deleteEditable(formData.getId(), czls, logOperationEnum);
		}
		// 是否定检发生了改变，且现在的为非定检件
		if (dbData.getIsDj() == 1 && formData.getIsDj() == 0) {
			// 删除定检项目
			ScheduledCheckItem param = new ScheduledCheckItem();
			param.setZjqdid(formData.getId());
			scheduledCheckItemService.deleteByZjqdid(param, czls, logOperationEnum);
			// 删除监控项目
			scheduledCheckMonitorItemService.deleteByZjqdid(formData.getId(), czls, logOperationEnum);
		}
		// 序列号管理件且件号或序列号发生改变
		if(formData.getXlh() != null && 
				(!formData.getXlh().equals(dbData.getXlh()) || !formData.getJh().equals(dbData.getJh()))){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("zjqdid", formData.getId());
			paramMap.put("jh", formData.getJh());
			paramMap.put("xlh", formData.getXlh());
			// 更新b_s_00101件号、序列号
			loadingListEditableMapper.updateTimeMonitorSetting(paramMap);
			// 更新b_s_00103件号、序列号
			loadingListEditableMapper.updateFixedMonitorSetting(paramMap);
			// 更新b_s_0010301件号、序列号
			loadingListEditableMapper.updateFixedMonitorDetail(paramMap);
		}
	}

	@Override
	public void updatePlaneEditables(LoadingList loadingList) throws RuntimeException {
		loadingListMapper.updatePlaneEditables(loadingList);

	}

	/**
	 * 根据条件查询
	 */
	@Override
	public List<LoadingList> queryList(LoadingList loadingList) {
		return loadingListMapper.queryList(loadingList);
	}

	/**
	 * 如果曾经拆下则汇总部件使用情况
	 */
	@Override
	public Map<String, Object> sumComponentUsageIfEverDisassembled(LoadingList loadingList) {
		// 该部件的累积部件使用情况
		ComponentUsage usage = loadingListMapper.getComponentUsage(loadingList);
		Map<String, Object> bean = new HashMap<String, Object>();
		// 该部件曾经拆下
		if(isEverDisassembled(usage)){
			if(isNotZero(usage.getFxsjFz()) || isNotZero(usage.getFxsjXs())){	// 飞行时间
				bean.put(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.name().toLowerCase(), carry(usage.getFxsjXs(), usage.getFxsjFz()));
			}
			if(isNotZero(usage.getQljxh())){ // 起落架循环
				bean.put(MonitorItemEnum.LANDING_GEAR_CYCLE.name().toLowerCase(), usage.getQljxh());
			} 
			if(isNotZero(usage.getTs1())){	// 特殊1
				bean.put(MonitorItemEnum.SPECIAL_FIRST.name().toLowerCase(), usage.getTs1());
			} 
			if(isNotZero(usage.getTs2())){	// 特殊2
				bean.put(MonitorItemEnum.SPECIAL_SECOND.name().toLowerCase(), usage.getTs2());
			} 
			if(isNotZero(usage.getFdjN1())){	// N1
				bean.put(MonitorItemEnum.N1.name(), usage.getFdjN1());
			} 
			if(isNotZero(usage.getFdjN2())){	// N2
				bean.put(MonitorItemEnum.N2.name(), usage.getFdjN2());
			} 
			if(isNotZero(usage.getFdjN3())){	// N3
				bean.put(MonitorItemEnum.N3.name(), usage.getFdjN3());
			} 
			if(isNotZero(usage.getFdjN4())){	// N4
				bean.put(MonitorItemEnum.N4.name(), usage.getFdjN4());
			} 
			if(isNotZero(usage.getFdjN5())){	// N5
				bean.put(MonitorItemEnum.N5.name(), usage.getFdjN5());
			} 
			if(isNotZero(usage.getFdjN6())){	// N6
				bean.put(MonitorItemEnum.N6.name(), usage.getFdjN6());
			} 
			if(isNotZero(usage.getJcxh())){	// 绞车循环
				bean.put(MonitorItemEnum.WINCH_CYCLE.name().toLowerCase(), usage.getJcxh());
			} 
			if(isNotZero(usage.getJcsjFz()) || isNotZero(usage.getJcsjXs())){	// 绞车时间
				bean.put(MonitorItemEnum.WINCH_TIME.name().toLowerCase(), carry(usage.getJcsjXs(), usage.getJcsjFz()));
			} 
			if(isNotZero(usage.getSsdsjFz()) || isNotZero(usage.getSsdsjXs())){	// 搜索灯时间
				bean.put(MonitorItemEnum.SEARCH_LIGHT_TIME.name().toLowerCase(), carry(usage.getSsdsjXs(), usage.getSsdsjFz()));
			} 
			if(isNotZero(usage.getDgxh())){	// 外吊挂循环
				bean.put(MonitorItemEnum.EXT_SUSPENSION_LOOP.name().toLowerCase(), usage.getDgxh());
			}
		}
		return bean;
	}
	
	/**
	 * 根据部件拆解记录，判断该部件是否曾经装过飞机
	 * @param usage
	 * @return
	 */
	private boolean isEverDisassembled(ComponentUsage usage){
		return loadingListMapper.isEverDisassembled(usage);
	}
	
	/**
	 * 判断数字不为空
	 * @param num
	 * @return
	 */
	private boolean isNotZero(BigDecimal num){
		return num != null && num.compareTo(BigDecimal.ZERO) > 0;
	}
	
	/**
	 * 判断数字不为空
	 * @param num
	 * @return
	 */
	private boolean isNotZero(Integer num){
		return num != null && num > 0;
	}
	
	/**
	 * 进位
	 * @param hour
	 * @param minute
	 * @return
	 */
	private String carry(Integer hour, Integer minute){
		String hourStr = hour == null ? "0" : String.valueOf(hour);
		String minuteStr = minute == null ? "0" : String.valueOf(minute);
		String time =  hourStr + ":" + minuteStr;
		time = StringAndDate_Util.operateTime(time, "0", TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);
		return time;
	}

	/**
	 * 查询指定飞机无且无子部件的零件清单-生效区
	 */
	@Override
	public Map<String, Object> queryEffectiveNoChildList(LoadingList ll)
			throws RuntimeException {
		PageHelper.startPage(ll.getPagination());
		List<LoadingList> list = loadingListMapper.queryNoChildList(ll);
		return PageUtil.pack4PageHelper(list, ll.getPagination());
	}

	/**
	 * 装机清单记录rec
	 * @param ll
	 * @param czls
	 * @param type
	 */
	private void logRec(LoadingList ll, String czls, UpdateTypeEnum type, LogOperationEnum logOperationEnum){
		if(ll != null){
			commonRecService.write(ll.getId(), TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), 
					czls, logOperationEnum, type,  ll.getId());
		}
	}
	
	/**
	 * 装机清单集合记录rec
	 * @param list
	 * @param czls
	 * @param type
	 */
	private void logRecList(List<LoadingList> list, String czls, UpdateTypeEnum type){
		if(list != null && !list.isEmpty()){
			List<String> keys = new ArrayList<String>();
			List<String> fjzchs = new ArrayList<String>();
			for (LoadingList ll : list) {
				keys.add(ll.getId());
				fjzchs.add(ll.getFjzch());
			}
			// 插入日志
			commonRecService.write("id", keys, TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), 
					czls, LogOperationEnum.EDIT, type, null);
		}
	}
	
	/**
	 * 写入装机清单拆下历史
	 * @param zjqdid
	 */
	public void writeToLoadingListHistory(String zjqdid, String xgid){
		loadingListEditableMapper.writeToLoadingListHistory(zjqdid, xgid);
	}
	
	/**
	 * 写入装机清单拆下历史
	 * @param zjqdids
	 */
	public void writeToLoadingListHistory(List<LoadingList> list){
		
		String fjdid = UUID.randomUUID().toString();
		for (LoadingList ll : list) {
			ll.setXgid(ll.getId());
			if(ll.getCj() == 1){
				ll.setId(fjdid);
			}else{
				ll.setId(UUID.randomUUID().toString());
				ll.setFjdid(fjdid);
			}
			loadingListEditableMapper.writeToLoadingListHistoryByBean(ll);
		}
	}

	/**
	 * 根据条件查询
	 */
	@Override
	public List<LoadingList> queryByParam(LoadingList loadingList) {
		return loadingListEditableMapper.select(loadingList);
	}

	/**
	 * 飞机装机清单父节点查询-树状
	 */
	@Override
	public List<LoadingList> queryEditableParentTree(LoadingList ll) {
		List<LoadingList> list = loadingListEditableMapper.queryEditableParentTree(ll);
		// 设置虚拟父节点id
		setVirtualParentId(list, false);
		return list;
	}

	/**
	 * 删除飞机插入的装机清单数据
	 */
	@Override
	public void deletePlaneEditable(PlaneData pd, String czls, LogOperationEnum logOperationEnum) {
		// 飞机对应的装机清单数据
		LoadingList ll = loadingListEditableMapper.selectRootNode(pd);
		// 插入日志
		commonRecService.write(ll.getId(), TableEnum.B_S_001, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.DELETE, ll.getId());
		// 删除装机清单数据
		loadingListEditableMapper.deleteByPrimaryKey(ll.getId());
	}

	@Override
	public List<LoadingList> getPart(LoadingList ll) {
		
		return loadingListMapper.getPart(ll);
	}
}
