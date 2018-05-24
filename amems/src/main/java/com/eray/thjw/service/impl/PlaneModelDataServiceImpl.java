package com.eray.thjw.service.impl;

/**
 * @author 梅志亮
 * @time 2016-08-15
 * @describe 机型数据2Serrvice业务逻辑的实现类
 */
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.PlaneModelDataMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.PlaneModelData;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.JX_BJService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PlaneModelDataServiceImpl implements PlaneModelDataService {

	@Resource
	private PlaneModelDataMapper planeModelDataMapper;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private JX_BJService jX_BJService;

	@Override
	public String insert(PlaneModelData record) throws Exception {
		String czls = UUID.randomUUID().toString(); // 日志的操作流水
		User user = ThreadVarUtil.getUser();
		record.setDprtcode(user.getJgdm());
		if (user.getBmdm() == null) {
			record.setBmid("");
		} else {
			record.setBmid(user.getBmdm());
		}
		record.setCjrid(user.getId());
		record.setCzls(czls);
		record.setLogOperationEnum(LogOperationEnum.SAVE_WO);
		record.setZbid(user.getJgdm() + "," + record.getFjjx());
		planeModelDataMapper.insert(record);

		commonRecService.writeByWhere(
				"fjjx='" + record.getFjjx().replaceAll("'", "''") + "' and b.dprtcode = '" + user.getJgdm() + "'",
				TableEnum.D_004, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, record.getZbid());
		// 插入历史记录
		jX_BJService.insertSelective(record);
		return record.getFjjx();
	}

	@Override
	public int updateByPrimaryKey(PlaneModelData record) throws Exception {
		String czls = UUID.randomUUID().toString(); // 日志的操作流水
		record.setCzls(czls);
		record.setLogOperationEnum(LogOperationEnum.EDIT);
		User user = ThreadVarUtil.getUser();
		record.setZbid(record.getDprtcode() + "," + record.getFjjx());
		int count = planeModelDataMapper.updateByPrimaryKey(record);
		commonRecService.writeByWhere(
				"fjjx='" + record.getFjjx().replaceAll("'", "''") + "' and b.dprtcode = '" + record.getDprtcode() + "'",
				TableEnum.D_004, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, record.getZbid());

		commonRecService.writeByWhere(
				"fjjx='" + record.getFjjx().replaceAll("'", "''") + "' and b.dprtcode = '" + record.getDprtcode() + "'",
				TableEnum.D_00401, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.DELETE, record.getZbid());
		jX_BJService.deleteJX_BJ(record.getFjjx()); // 首先将该机型下的关联部件数据都删除

		count += jX_BJService.insertSelective(record); // 对关联部件表的修改操作先删掉所有机型的数据再增加
		return count;
	}

	@Override
	public List<PlaneModelData> queryList(PlaneModelData record) throws Exception {
		try {
			return planeModelDataMapper.selectPlaneModelDataList(record);
		} catch (Exception e) {
			throw new Exception("机型列表查询失败", e);
		}
	}

	@Override
	public int selectCount(PlaneModelData record) throws Exception {
		try {
			return planeModelDataMapper.selectCount(record);
		} catch (Exception e) {
			throw new Exception("查询失败：", e);
		}
	}

	@Override
	public PlaneModelData selectPlaneModelData(PlaneModelData record) throws Exception {
		return planeModelDataMapper.selectPlaneModelData(record);
	}

	/**
	 * @author liub
	 * @description 查询所有有效机型
	 * @param
	 * @return List<String>
	 * @develop date 2016.09.05
	 */
	@Override
	public List<String> findAllType(String dprtcode) throws RuntimeException {
		return planeModelDataMapper.findAllType(dprtcode);
	}

	@Override
	public PlaneModelData selectPlane(String fjzch) throws Exception {
		return planeModelDataMapper.selectPlane(fjzch);
	}

	@Override
	public PlaneModelData selectPlanes(String fjzch, String dprtcode) throws Exception {
		return planeModelDataMapper.selectPlanes(fjzch, dprtcode);
	}

	@Override
	public List<PlaneModelData> selectPlanes() throws Exception {
		return planeModelDataMapper.selectPlanes();
	}

	@Override
	public List<PlaneModelData> findType() throws RuntimeException {
		return planeModelDataMapper.findType(ThreadVarUtil.getUser().getJgdm());
	}

	/**
	 * @author liub
	 * @description 根据条件分页查询机型列表
	 * @param planeModelData
	 * @return List<Map<String, Object>>
	 * @develop date 2017.01.05
	 */
	@Override
	public List<Map<String, Object>> queryAllPageList(PlaneModelData planeModelData) throws RuntimeException {
		return planeModelDataMapper.queryAllPageList(planeModelData);
	}

	@Override
	public List<String> findByDprtcode(String dprtcode) {
		return planeModelDataMapper.findAllType(dprtcode);
	}

	/**
	 * 查询飞机主数据
	 * 
	 * @param entity
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectAircraftData(BaseEntity entity) {
		return planeModelDataMapper.selectAircraftData(entity);
	}

	@Override
	public List<PlaneModelData> selectUserACTypeLists(PlaneModelData planeModelData) {
		return planeModelDataMapper.selectUserACTypeLists(planeModelData);
	}

	/**
	 * 查询用户授权组织机构的所有 机型 和 飞机注册号
	 * 
	 * @author xu.yong
	 * @return
	 */
	public List<PlaneModelData> queryAircraftByAuthOrgList() {
		User user = ThreadVarUtil.getUser();
		BaseEntity entity = new BaseEntity();
		entity.getParamsMap().put("usertype", user.getUserType());
		entity.getParamsMap().put("userId", user.getId());
		return this.planeModelDataMapper.selectAircraftByAuthOrg(entity);
	}

	
	@Override
	public void existsAircraft4Expt(String uesrId, String usertype, String dprtCode, List<String> fjzch)
			throws BusinessException {
		Boolean result = existsAircraft(uesrId, usertype, dprtCode, fjzch);
		if (!result) {
			throw new BusinessException("指定飞机不存在或没有该飞机权限");
		}
	}
	
	@Override
	public Boolean existsAircraft(String uesrId, String usertype, String dprtCode, List<String> fjzch) {
		if (usertype != null && usertype == "admin") {
			return Boolean.TRUE;
		} else {
			Set<String> fjzchSet = new HashSet<String>();
			fjzchSet.addAll(fjzch);
			BaseEntity entity = new BaseEntity();
			entity.getParamsMap().put("userId", uesrId);
			entity.getParamsMap().put("dprtcode", dprtCode);
			entity.getParamsMap().put("fjzch", fjzchSet);
			int count = this.planeModelDataMapper.selectAircraft(entity);

			return fjzchSet.size() <= count;
		}
	}



	
	@Override
	public void existsModel4Expt(String uesrId, String usertype, String dprtCode, List<String> jx)
			throws BusinessException {
		Boolean result = existsModel(uesrId, usertype, dprtCode, jx);
		if (!result) {
			throw new BusinessException("指定飞机机型不存在或没有该机型权限");
		}

	}
	
	@Override
	public Boolean existsModel(String uesrId, String usertype, String dprtCode, List<String> jx) {
		if (usertype != null && usertype == "admin") {
			return Boolean.TRUE;
		} else {
			Set<String> jsSet = new HashSet<String>();
			jsSet.addAll(jx);
			BaseEntity entity = new BaseEntity();
			entity.getParamsMap().put("userId", uesrId);
			entity.getParamsMap().put("dprtcode", dprtCode);
			entity.getParamsMap().put("jx", jsSet);
			int count = this.planeModelDataMapper.selectAircraft(entity);
			return jsSet.size() <= count;
		}

	}

	@Override
	public void existsAircraft4145Expt(String uesrId, String usertype, String dprtCode, List<String> fjzch)
			throws BusinessException {
		Boolean result = existsAircraft145(uesrId, usertype, dprtCode, fjzch);
		if (!result) {
			throw new BusinessException("指定飞机不存在或没有该飞机权限");
		}
	}

	@Override
	public Boolean existsAircraft145(String uesrId, String usertype, String dprtCode, List<String> fjzch) {
		if (usertype != null && usertype == "admin") {
			return Boolean.TRUE;
		} else {
			Set<String> fjzchSet = new HashSet<String>();
			fjzchSet.addAll(fjzch);
			BaseEntity entity = new BaseEntity();
			entity.getParamsMap().put("userId", uesrId);
			entity.getParamsMap().put("dprtcode", dprtCode);
			entity.getParamsMap().put("fjzch", fjzchSet);
			int count = this.planeModelDataMapper.selectAircraft145(entity);
			
			return fjzchSet.size() <= count;
		}
	}
	
}
