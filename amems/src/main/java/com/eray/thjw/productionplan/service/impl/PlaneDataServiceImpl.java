package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.LoadingListEditableMapper;
import com.eray.thjw.productionplan.dao.PlaneDataMapper;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.PlaneInitData;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.PlaneDailyUsageService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.productionplan.service.PlaneInitDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.impl.PlaneModelDataServiceImpl;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.OperateEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * 飞机基本数据service
 * 
 * @author hanwu
 *
 */
@Service
public class PlaneDataServiceImpl implements PlaneDataService {

	private static final Logger logger = LoggerFactory.getLogger(PlaneModelDataServiceImpl.class);

	@Resource
	private PlaneDataMapper planeDataMapper;
	@Resource
	private DeptInfoMapper deptInfoMapper;

	@Resource
	private PlaneInitDataService planeInitDataService;

	@Resource
	private PlaneDailyUsageService planeDailyUsageService;

	@Resource
	private LoadingListService loadingListService;

	@Resource
	private CommonRecService commonRecService;

	@Resource
	private AttachmentMapper attachmentMapper;

	@Resource
	private LoadingListEditableMapper loadingListEditableMapper;

	@Resource
	private PlaneModelDataService planeModelDataService;

	@Override
	public PlaneData selectByPrimaryKey(PlaneData pd) throws RuntimeException {

		return planeDataMapper.selectByPrimaryKey(pd);
	}

	/**
	 * 飞机基本信息分页查询
	 * 
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryByPage(PlaneData pd) throws BusinessException {
		// 用户刚编辑过的记录 ID
		String fjzch = pd.getFjzch();
		String dprtcode = pd.getDprtcode();
		// 清除查询条件中的ID，保证列表查询结果集的正确性
		pd.setFjzch(null);
		pd.getParamsMap().put("userId", ThreadVarUtil.getUser().getId());
		PageHelper.startPage(pd.getPagination());
		List<Map<String, Object>> list = planeDataMapper.queryPage(pd);
		// 查询总记录数
		if (((Page) list).getTotal() > 0) {
			if (StringUtils.isNotBlank(fjzch)) { // 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (!hasRecordMap(list, fjzch)) {
					PlaneData param = new PlaneData();
					param.setFjzch(fjzch);
					param.setDprtcode(dprtcode);
					List<Map<String, Object>> newRecordList = planeDataMapper.queryPage(param);
					if (newRecordList != null && newRecordList.size() == 1) {
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, pd.getPagination());
		} else {
			List<Map<String, Object>> newRecordList = new ArrayList<Map<String, Object>>(1);
			if (StringUtils.isNotBlank(fjzch)) { // 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				PlaneData param = new PlaneData();
				param.setFjzch(fjzch);
				param.setDprtcode(dprtcode);
				param.setPagination(new Pagination());
				if (newRecordList != null && newRecordList.size() == 1) {
					return PageUtil.pack(1, newRecordList, pd.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, pd.getPagination());
		}
	}

	/**
	 * 新增飞机基本信息和初始化数据以及其他表的数据
	 * 
	 * @throws BusinessException
	 */
	@Override
	public void add(PlaneData pd) throws RuntimeException, BusinessException {
		pd.setGjdjzfjid(UUID.randomUUID().toString());
		pd.setShzfjid(UUID.randomUUID().toString());
		pd.setWxdtzzfjid(UUID.randomUUID().toString());

		// 验证账号注册数量
		int count1 = this.planeDataMapper.queryCountfj(ThreadVarUtil.getUser().getJgdm());

		// 查询账号注册最大数量
		DeptInfo accounts = deptInfoMapper.selectCounts(ThreadVarUtil.getUser().getJgdm());
		if (accounts != null) {
			if (accounts.getZcfj_max() != null && count1 >= accounts.getZcfj_max() && accounts.getZcfj_max() != 0) {
				throw new BusinessException("飞机注册号不能超过组织机构的最大飞机注册数量");
			}
		}

		// 验证是否拥有机型权限
		List<String> list = new ArrayList<String>();
		list.add(pd.getFjjx());
		planeModelDataService.existsModel4Expt(ThreadVarUtil.getUser().getId(), ThreadVarUtil.getUser().getUserType(),
				ThreadVarUtil.getUser().getJgdm(), list);

		String czls = UUID.randomUUID().toString();
		LogOperationEnum logOperationEnum = LogOperationEnum.SAVE_WO;
		// 1.新增飞机基本信息
		this.addPlane(pd, czls, logOperationEnum);
		// 2.新增飞机初始化数据
		planeInitDataService.add(pd.getInitDatas(), pd.getFjzch(), ThreadVarUtil.getUser().getJgdm(),
				UpdateTypeEnum.SAVE, czls, logOperationEnum);
		// 3.新增飞机日使用量监控指标
		planeDailyUsageService.add(pd, czls, logOperationEnum);
		// 4.新增飞机装机清单
		loadingListService.insertPlaneEditable(pd, czls, logOperationEnum);
		// 5.保存电台执照附件
		this.addDtzzfjs(pd.getDtzzfjs(), pd.getFjzch(), czls, logOperationEnum, pd);
	}

	/**
	 * 修改飞机基本信息和初始化数据以及其他表的数据
	 * 
	 * @throws BusinessException
	 */
	@Override
	public void edit(PlaneData pd) throws RuntimeException, BusinessException {
		/*
		 * if(null==pd.getGjdjzfjid() || "".equals(pd.getGjdjzfjid())){
		 * pd.setGjdjzfjid(UUID.randomUUID().toString()); }
		 * if(null==pd.getShzfjid() || "".equals(pd.getShzfjid())){
		 * pd.setShzfjid(UUID.randomUUID().toString()); }
		 * if(null==pd.getWxdtzzfjid() || "".equals(pd.getWxdtzzfjid())){
		 * pd.setWxdtzzfjid(UUID.randomUUID().toString()); }
		 */
		// 验证是否拥有机型权限
		List<String> list = new ArrayList<String>();
		list.add(pd.getFjjx());
		planeModelDataService.existsModel4Expt(ThreadVarUtil.getUser().getId(), ThreadVarUtil.getUser().getUserType(),
				ThreadVarUtil.getUser().getJgdm(), list);
		String czls = UUID.randomUUID().toString();
		LogOperationEnum logOperationEnum = LogOperationEnum.EDIT;
		// 1.修改飞机基本信息
		pd.setCcrq(Utils.FormatUtil.parseDateTime(Utils.FormatUtil.formatDate(pd.getCcrq())));
		planeDataMapper.updateByPrimaryKeySelective(pd);
		// 插入日志
		commonRecService.writeByWhere(
				" b.fjzch = '" + pd.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '" + pd.getDprtcode() + "'",
				TableEnum.D_007, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, UpdateTypeEnum.UPDATE,
				pd.getFjzch().concat(",").concat(pd.getDprtcode()));
		// 2.删除对应的所有飞机初始化信息
		planeInitDataService.deleteByFjzch(pd.getFjzch(), pd.getDprtcode(), czls, logOperationEnum);
		// 3.新增飞机初始化数据
		planeInitDataService.add(pd.getInitDatas(), pd.getFjzch(), pd.getDprtcode(), UpdateTypeEnum.UPDATE, czls,
				logOperationEnum);
		// 4.修改飞机日使用量监控指标
		planeDailyUsageService.edit(pd, czls, logOperationEnum);
		// 5.修改飞机装机清单
		loadingListService.editPlaneEditable(pd, czls, logOperationEnum);
		// 6.修改电台执照附件
		this.editDtzzfjs(pd.getDtzzfjs(), pd.getFjzch(), pd.getDprtcode(), czls, logOperationEnum, pd);
	}

	/**
	 * 只新增飞机基本信息
	 * 
	 * @param pd
	 * @throws RuntimeException
	 * @throws BusinessException
	 */
	private void addPlane(PlaneData pd, String czls, LogOperationEnum logOperationEnum)
			throws RuntimeException, BusinessException {
		// 1.验证飞机
		this.validatePlane(pd);
		// 2.新增飞机基本信息
		User user = ThreadVarUtil.getUser();
		pd.setCcrq(Utils.FormatUtil.parseDateTime(Utils.FormatUtil.formatDate(pd.getCcrq())));
		pd.setDprtcode(user.getJgdm());
		if (user.getBmdm() == null) {
			pd.setBmid("");
		} else {
			pd.setBmid(user.getBmdm());
		}
		pd.setCjrid(user.getId());
		pd.setCjsj(new Date());
		pd.setZt(DelStatus.TAKE_EFFECT.getId());
		planeDataMapper.insert(pd);
		// 3.插入日志
		commonRecService.writeByWhere(
				" b.fjzch = '" + pd.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '" + user.getJgdm() + "'",
				TableEnum.D_007, user.getId(), czls, logOperationEnum, UpdateTypeEnum.SAVE,
				pd.getFjzch().concat(",").concat(user.getJgdm()));
	}

	/**
	 * 新增电台执照附件
	 * 
	 * @param dtzzfjs
	 * @param fjzch
	 * @param czls
	 * @param logOperationEnum
	 */
	private void addDtzzfjs(List<Attachment> dtzzfjs, String fjzch, String czls, LogOperationEnum logOperationEnum,
			PlaneData pd) {
		if (dtzzfjs != null && !dtzzfjs.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : dtzzfjs) {
				if (attachment.getType() == 1) {
					attachment.setMainid(pd.getGjdjzfjid());
				} else if (attachment.getType() == 2) {
					attachment.setMainid(pd.getShzfjid());
				} else {
					attachment.setMainid(pd.getWxdtzzfjid());
				}

				/* attachment.setMainid(fjzch); */
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				if (user.getBmdm() == null) {
					attachment.setCzbmid("");
				} else {
					attachment.setCzbmid(user.getBmdm());
				}
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, fjzch.concat(",").concat(user.getJgdm()));
		}
	}

	/**
	 * 修改电台执照附件
	 * 
	 * @param dtzzfjs
	 * @param fjzch
	 * @param czls
	 * @param logOperationEnum
	 */
	private void editDtzzfjs(List<Attachment> dtzzfjs, String fjzch, String dprtcode, String czls,
			LogOperationEnum logOperationEnum, PlaneData pd) {
		if (dtzzfjs != null && !dtzzfjs.isEmpty()) {
			User user = ThreadVarUtil.getUser();
			for (Attachment attachment : dtzzfjs) {
				if (attachment.getOperate() == null || attachment.getOperate().equals(OperateEnum.ADD)) {
					if (attachment.getType() == 1) {
						attachment.setMainid(pd.getGjdjzfjid());
					} else if (attachment.getType() == 2) {
						attachment.setMainid(pd.getShzfjid());
					} else {
						attachment.setMainid(pd.getWxdtzzfjid());
					}
					/* attachment.setMainid(fjzch); */
					attachment.setDprtcode(dprtcode);
					attachment.setId(UUID.randomUUID().toString());
					attachment.setCzrid(user.getId());
					if (user.getBmdm() == null) {
						attachment.setCzbmid("");
					} else {
						attachment.setCzbmid(user.getBmdm());
					}
					attachmentMapper.addFile(attachment);
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.SAVE, fjzch.concat(",").concat(dprtcode));
				} else if (attachment.getOperate().equals(OperateEnum.DEL)) {
					attachmentMapper.updateByPrimaryKey(attachment.getId());
					commonRecService.write(attachment.getId(), TableEnum.D_011, user.getId(), czls,
							LogOperationEnum.EDIT, UpdateTypeEnum.DELETE, fjzch.concat(",").concat(dprtcode));
				}
			}
		}
	}

	/**
	 * 验证飞机注册号是否存在
	 * 
	 * @param pd
	 * @throws BusinessException
	 */
	private void validatePlane(PlaneData pd) throws BusinessException {
		pd.setDprtcode(ThreadVarUtil.getUser().getJgdm());
		if (planeDataMapper.queryCountByFjzch(pd) >= 1) {
			throw new BusinessException("飞机注册号已存在！");
		}
		if (planeDataMapper.queryCountByXlh(pd) >= 1) {
			throw new BusinessException("序列号已存在！");
		}
	}

	/**
	 * 根据飞机注册号查询单条飞机信息
	 */
	@Override
	public Map<String, Object> queryByFjzch(PlaneData pd) throws RuntimeException {
		Map<String, Object> result = new HashMap<String, Object>();
		pd = planeDataMapper.queryByFjzch(pd);
		result.put("fjzch", pd.getFjzch());
		result.put("dprtCode", pd.getDprtcode());
		result.put("fjjx", pd.getFjjx());
		result.put("xlh", pd.getXlh());
		result.put("bzm", pd.getBzm());
		result.put("ccrq", pd.getCcrq());
		result.put("jd", pd.getJd());
		result.put("jsgzjl", pd.getJsgzjl());
		result.put("bz", pd.getBz());
		result.put("bmid", pd.getBmid());
		result.put("cjrid", pd.getCjrid());
		result.put("cjsj", pd.getCjsj());
		result.put("zt", pd.getZt());
		result.put("gjdjzh", pd.getGjdjzh());
		result.put("shzh", pd.getShzh());
		result.put("wxdtxkzh", pd.getWxdtxkzh());

		result.put("gjdjzjkrq", pd.getGjdjzjkrq());
		result.put("shzjkrq", pd.getShzjkrq());
		result.put("dtzzjkrq", pd.getDtzzjkrq());

		result.put("gjdjzfjid", pd.getGjdjzfjid());
		result.put("shzfjid", pd.getShzfjid());
		result.put("wxdtzzfjid", pd.getWxdtzzfjid());

		for (PlaneInitData initData : pd.getInitDatas()) {
			result.put(initData.getInitXmbh(), initData.getInitValue());
		}
		return result;
	}

	@Override
	public List<PlaneData> queryList(PlaneData pd) {
		if (pd == null) {
			throw new RuntimeException("参数不能为空...");
		}
		try {
			return planeDataMapper.queryList(pd);
		} catch (Exception e) {
			throw new RuntimeException("查询机型数据：", e);
		}
	}

	/**
	 * 查询所有飞机数据并关联机型
	 */
	@Override
	public List<PlaneData> findAllWithFjjx(PlaneData pd) {
		return planeDataMapper.findAllWithFjjx(pd);
	}

	/**
	 * 查询所有飞机数据并关联机型
	 */
	@Override
	public List<PlaneData> findAllWithFjjxAuthority(PlaneData pd) {
		pd.getParamsMap().put("userId", ThreadVarUtil.getUser().getId());
		return planeDataMapper.findAllWithFjjx(pd);
	}

	@Override
	public List<PlaneData> queryPlanes(PlaneData record) throws RuntimeException {
		return planeDataMapper.queryPlanes(record);
	}

	/**
	 * @author liub
	 * @description 根据角色拥有的机构代码查询飞机基本信息
	 * @param dprtcodeList
	 * @return List<PlaneData>
	 * @develop date 2016.12.06
	 */
	@Override
	public List<Map<String, Object>> selectByDprtcodeList(List<String> dprtcodeList) throws RuntimeException {
		if (dprtcodeList == null || dprtcodeList.isEmpty()) {
			return null;
		}
		List<PlaneData> planeDataList = planeDataMapper.selectByDprtcodeList(dprtcodeList);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (PlaneData planeData : planeDataList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("fjzch", planeData.getFjzch());
			resultMap.put("dprtcode", planeData.getDprtcode());
			resultList.add(resultMap);
		}
		return resultList;
	}

	/**
	 * 查询所有飞机数据并关联机型
	 */
	@Override
	public List<Map<String, Object>> findAllWithFjjxInJson(PlaneData pd) {
		pd.getParamsMap().put("userId", ThreadVarUtil.getUser().getId());
		List<PlaneData> planeDataList = planeDataMapper.findAllWithFjjx(pd);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (PlaneData planeData : planeDataList) {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("fjzch", planeData.getFjzch());
			resultMap.put("dprtcode", planeData.getDprtcode());
			resultMap.put("zt", planeData.getZt());
			resultMap.put("fjjx", planeData.getFjjx());
			resultMap.put("isTsqk", planeData.getIsTsqk());
			resultMap.put("xlh", planeData.getXlh());
			resultMap.put("isSync", planeData.getIsSync());
			resultList.add(resultMap);
		}
		return resultList;
	}

	@Override
	public List<PlaneData> queryAllPageList(PlaneData planeData) throws RuntimeException {
		return planeDataMapper.queryAllPageList(planeData);
	}

	@Override
	public void update(PlaneData planeData) throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		LogOperationEnum logOperationEnum = LogOperationEnum.EDIT;
		String czls = UUID.randomUUID().toString();
		if (null == planeData.getGjdjzfjid() || "".equals(planeData.getGjdjzfjid())) {
			planeData.setGjdjzfjid(UUID.randomUUID().toString());
		}
		if (null == planeData.getShzfjid() || "".equals(planeData.getShzfjid())) {
			planeData.setShzfjid(UUID.randomUUID().toString());
		}
		if (null == planeData.getWxdtzzfjid() || "".equals(planeData.getWxdtzzfjid())) {
			planeData.setWxdtzzfjid(UUID.randomUUID().toString());
		}

		this.editDtzzfjs(planeData.getDtzzfjs(), planeData.getFjzch(), planeData.getDprtcode(), czls, logOperationEnum,
				planeData);
		planeDataMapper.updateByPrimaryKeySelective(planeData);
		// 插入日志
		commonRecService.writeByWhere(
				" b.fjzch = '" + planeData.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '" + user.getJgdm()
						+ "'",
				TableEnum.D_007, user.getId(), czls, logOperationEnum, UpdateTypeEnum.UPDATE,
				planeData.getFjzch().concat(",").concat(user.getJgdm()));

		/*
		 * List<Attachment> list = planeData.getAttachments(); List<String>
		 * list1 = new ArrayList<String>(); //修改之前分配的菜单id List<String>
		 * columnValueList = new ArrayList<String>(); if (!list.isEmpty()) {
		 * 
		 * for (Attachment attachment : list) {
		 * if(attachment.getBiaoshi().equals("2")){
		 * attachment.setMainid(planeData.getFjzch());
		 * attachment.setDprtcode(user.getJgdm());
		 * attachment.setId(UUID.randomUUID().toString());
		 * attachment.setCzrid(user.getId()); if(user.getBmdm()==null){
		 * attachment.setCzbmid(""); }else{
		 * attachment.setCzbmid(user.getBmdm()); }
		 * attachmentMapper.addFile(attachment);
		 * columnValueList.add(attachment.getId()); commonRecService.write("id",
		 * columnValueList, TableEnum.D_011, user.getId(), czls,
		 * LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,
		 * planeData.getFjzch().concat(",").concat(user.getJgdm())); }
		 * list1.add(attachment.getId()); } } List<String> list3 = new
		 * ArrayList<String>(); //修改之后的菜单id Attachment attachment= new
		 * Attachment(); attachment.setMainid(planeData.getFjzch());
		 * List<Attachment> list2 =
		 * attachmentMapper.queryListAttachments(attachment); for (Attachment
		 * attachment2 : list2) { list3.add(attachment2.getId()); }
		 * 
		 * //删除 for(String newMenuId : list3){ if(!list1.contains(newMenuId)){
		 * attachmentMapper.updateByPrimaryKey(newMenuId);
		 * columnValueList.add(newMenuId); commonRecService.write("id",
		 * columnValueList, TableEnum.D_011, user.getId(), czls,
		 * LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,
		 * planeData.getFjzch().concat(",").concat(user.getJgdm())); } }
		 */

	}

	/**
	 * @author liub
	 * @description 根据条件分页查询飞机注册号列表
	 * @param planeData
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.05
	 */
	@Override
	public List<Map<String, Object>> queryAllPageWinList(PlaneData planeData) throws RuntimeException {
		return planeDataMapper.queryAllPageWinList(planeData);
	}

	private boolean hasRecordMap(List<Map<String, Object>> rowsList, String id) {
		if (StringUtils.isBlank(id)) {
			return true;
		}
		if (rowsList != null && !rowsList.isEmpty()) {
			// 移除刚编辑的记录
			int i = 0;
			for (i = 0; i < rowsList.size(); i++) {
				if (id.equals(rowsList.get(i).get("FJZCH"))) {
					if (i != 0) {
						Map<String, Object> t = rowsList.get(i);
						rowsList.remove(i);
						rowsList.add(0, t);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<PlaneData> selectUserACFjzchLists(PlaneData planeData) {

		return planeDataMapper.selectUserACFjzchLists(planeData);
	}

	@Override
	public PlaneData selectByPrimaryKeys(PlaneData pd) {
		return planeDataMapper.selectByPrimaryKeys(pd);
	}

	/**
	 * 删除飞机基本信息
	 * 
	 * @throws BusinessException
	 */
	@Override
	public void delete(PlaneData pd) throws BusinessException {
		// 验证未添加装机清单数据
		LoadingList param = new LoadingList();
		param.setFjzch(pd.getFjzch());
		param.setDprtcode(pd.getDprtcode());
		if (loadingListEditableMapper.queryCount(param) > 1) {
			throw new BusinessException("该飞机已添加装机清单数据，无法删除！");
		}
		String czls = UUID.randomUUID().toString();
		LogOperationEnum logOperationEnum = LogOperationEnum.ZUOFEI;
		// 1.删除飞机基本信息
		commonRecService.writeByWhere(
				" b.fjzch = '" + pd.getFjzch().replaceAll("'", "''") + "' and b.dprtcode = '" + pd.getDprtcode() + "'",
				TableEnum.D_007, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, UpdateTypeEnum.DELETE,
				pd.getFjzch().concat(",").concat(pd.getDprtcode()));
		planeDataMapper.deleteByPrimaryKey(pd);
		// 2.删除对应的所有飞机初始化信息
		planeInitDataService.deleteByFjzch(pd.getFjzch(), pd.getDprtcode(), czls, logOperationEnum);
		// 3.删除飞机日使用量监控指标
		planeDailyUsageService.delete(pd, czls, logOperationEnum);
		// 4.删除飞机装机清单
		loadingListService.deletePlaneEditable(pd, czls, logOperationEnum);

	}
	/**
	 * @Description EO适用性获取飞机
	 * @CreateBy 岳彬彬
	 * @CreateTime 2017年8月24日 下午4:44:26
	 * @param pd
	 */
	@Override
	public Map<String, Object> getplaneList(PlaneData pd) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageHelper.startPage(pd.getPagination());
		List<PlaneData> list = planeDataMapper.getPlaneList(pd);
		resultMap = PageUtil.pack4PageHelper(list, pd.getPagination());
		return resultMap;
	}

}
