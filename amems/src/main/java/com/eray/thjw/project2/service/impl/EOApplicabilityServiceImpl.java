package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.service.MonitorDataService;
import com.eray.thjw.project2.dao.EOApplicabilityMapper;
import com.eray.thjw.project2.dao.EngineeringOrderMapper;
import com.eray.thjw.project2.po.EOApplicability;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.service.EOApplicabilityService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.produce.WorkorderStatusEnum;
import enu.project2.CompnentTypeEnum;
import enu.project2.EngineeringOrderStatusEnum;

/** 
 * @Description EO适用性设置
 * @CreateTime 2017-8-28 上午10:45:02
 * @CreateBy 雷伟	
 */
@Service
public class EOApplicabilityServiceImpl implements EOApplicabilityService {

	@Resource
	private EOApplicabilityMapper eoApplicabilityMapper;
	@Resource
	private EngineeringOrderMapper engineeringOrderMapper;
	
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private MonitorDataService monitorDataService;
	
	@Resource
	private CommonService commonService;
	
	/**
	 * @Description 新增EO适用性设置
	 * @CreateTime 2017-8-23 上午9:36:02
	 * @CreateBy 雷伟
	 * @param syxszList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 流水号
	 * @param operation 操作类型
	 */
	public void savePulicationAffectedList(List<EOApplicability> syxszList,User user, String eoId, Date currentDate, String czls,LogOperationEnum operation) {
		if(syxszList == null || syxszList.size() <= 0) {
			return;
		}
		
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		for (EOApplicability eoApplicability : syxszList) {
			String id = UUID.randomUUID().toString();
			eoApplicability.setId(id);
			eoApplicability.setMainid(eoId); //业务ID
			eoApplicability.setZt(EffectiveEnum.YES.getId());//状态
			eoApplicability.setWhbmid(user.getBmdm());
			eoApplicability.setWhrid(user.getId());
			eoApplicability.setWhsj(currentDate);
			columnValueList.add(id);
		}
		eoApplicabilityMapper.insert4Batch(syxszList);
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_01001, user.getId(), czls, operation, UpdateTypeEnum.SAVE, eoId);
		}
	}

	@Override
	public void deleteByMainid(EngineeringOrder engineeringOrder,User user, Date currentDate, String czls, LogOperationEnum operation) {
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_01001, user.getId(), czls, operation, UpdateTypeEnum.DELETE, engineeringOrder.getId());
		eoApplicabilityMapper.deleteByMainid(engineeringOrder.getId());
	}
	
	/**
	 * @Description 飞机注册 添加EO执行对象
	 * @CreateTime 2017年10月16日 上午10:57:09
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param xlh 飞机序列号
	 */
	public void addExecObject(String dprtcode, String fjjx, String fjzch, String xlh){
		this.eoApplicabilityMapper.insert4AddPlane(dprtcode, fjjx, fjzch, xlh);
	}
	
	/**
	 * @Description 添加EO执行对象-部件
	 * @CreateTime 2017年10月16日 上午10:57:09
	 * @CreateBy 徐勇
	 * @param dprtcode 飞机所属组织机构
	 * @param fjjx 飞机机型
	 * @param fjzch 飞机注册号
	 * @param xlh 飞机序列号
	 */
	public void addExecObject4Component(String dprtcode, String fjjx, String bjh, String xlh){
		this.eoApplicabilityMapper.insert4AddComponent(dprtcode, fjjx, bjh, xlh);
	}

	@Override
	public List<EOApplicability> getEOApplicabilityByMainId(String id) {
		return eoApplicabilityMapper.getEOApplicabilityByMainId(id);
	}

	@Override
	public EOApplicability selectById(String zxdxId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", zxdxId);
		return eoApplicabilityMapper.selectById(paramMap);
	}

	@Override
	public String doZxdxClose(EOApplicability eoApplicability) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", eoApplicability.getId());
		EOApplicability exeObj =  eoApplicabilityMapper.selectById(paramMap);
		
		/**校验EO状态=7*/
		EngineeringOrder engineeringOrder = engineeringOrderMapper.selectByPrimaryKey(eoApplicability.getMainid()); 
		if(null != engineeringOrder && EngineeringOrderStatusEnum.TAKEEFFECT.getId() != engineeringOrder.getZt()){
			throw new BusinessException("该EO已更新，请刷新后再进行操作!");
		}
		
		/**校验状态,防止重复提交*/
		if(exeObj.getGbzt().longValue()==LogOperationEnum.GUANBI.getId() || 
				exeObj.getGbzt().longValue()==LogOperationEnum.WANCHEN.getId()){
			throw new BusinessException("该执行对象已更新，请刷新后再进行操作!");
		}
		
		/**工单是否都已关闭*/
		List<Map<String, Object>> resourceDatas =  engineeringOrderMapper.getEOExecutionObjs(exeObj.getMainid()); /**有工单的,执行对象*/
		for(Map<String, Object> resourceData : resourceDatas) {
			String zxdxid = formatNull(resourceData.get("ZXDXID"));//执行对象ID
			String gdzt = formatNull(resourceData.get("ZT"));//工单状态
			if(!zxdxid.equals(exeObj.getId())){
				continue;
			}else{
				if(!(gdzt.equals(WorkorderStatusEnum.CLOSETOEND.getId().toString()) || gdzt.equals(WorkorderStatusEnum.CLOSETOFINISH.getId().toString()))){
					throw new BusinessException("该执行对象下的工单没有全部关闭，不能关闭!");
				}
			}
		}
		
		exeObj.setGbzt(eoApplicability.getGbzt()) ;//关闭状态
		exeObj.setGbrid(user.getId()); //关闭人ID
		exeObj.setGbrq(commonService.getSysdate()); //关闭时间
		exeObj.setGbyy(eoApplicability.getGbyy()); //关闭原因
		eoApplicabilityMapper.updateByPrimaryKeySelective(exeObj); //编辑EO
		commonRecService.write(exeObj.getId(), TableEnum.B_G2_01001, user.getId(),  UUID.randomUUID().toString(), LogOperationEnum.Close, UpdateTypeEnum.CLOSE, exeObj.getId());
	
		/**toDo*/
		EngineeringOrder eo = engineeringOrderMapper.selectByPrimaryKey(exeObj.getMainid());
		
		if (CompnentTypeEnum.FUSELAGE.getId() == eo.getSylb().longValue()) {//飞机
			
			monitorDataService.removeByEOExecObject(eo.getId(), exeObj.getBh(), null, null);
			
		} else {//发动机/APU/部件
			
			monitorDataService.removeByEOExecObject(eo.getId(), null, exeObj.getBh(), exeObj.getXlh());
			
		}
		
		return exeObj.getId();
	}

	@Override
	public String doZxdxConfirm(String zxdxId) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", zxdxId);
		EOApplicability exeObj =  eoApplicabilityMapper.selectById(paramMap);
		
		/**校验状态,防止重复提交*/
		if(exeObj.getQrzt().longValue()==WhetherEnum.YES.getId()){
			throw new BusinessException("该执行对象已更新，请刷新后再进行操作!");
		}
		exeObj.setQrzt(WhetherEnum.YES.getId());//确认状态
		eoApplicabilityMapper.updateByPrimaryKeySelective(exeObj);
		commonRecService.write(exeObj.getId(), TableEnum.B_G2_01001, user.getId(),  UUID.randomUUID().toString(), LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, exeObj.getId());
		
		return exeObj.getId();
	}
	
	private String formatNull(Object object) {
		return object==null?"":object.toString();
	}

}
