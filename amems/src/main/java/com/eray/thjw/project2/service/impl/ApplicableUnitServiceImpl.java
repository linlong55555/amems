package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ApplicableUnitMapper;
import com.eray.thjw.project2.po.ApplicableUnit;
import com.eray.thjw.project2.service.ApplicableUnitService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;


/**
 * @Description 工卡-适用单位service实现类
 * @CreateTime 2017年8月16日 下午1:44:40
 * @CreateBy 韩武
 */
@Service
public class ApplicableUnitServiceImpl implements ApplicableUnitService{
	
	@Resource
	private ApplicableUnitMapper applicableUnitMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存多个工卡-适用单位
	 * @CreateTime 2017-11-8 下午3:16:18
	 * @CreateBy 刘兵
	 * @param ApplicableUnitList 工卡-适用单位集合
	 * @param mainid 父ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	@Override
	public void saveApplicableUnitList(List<ApplicableUnit> applicableUnitList, String mainid, String czls, String zdid, LogOperationEnum logopration){
		if(null != applicableUnitList && applicableUnitList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (ApplicableUnit applicableUnit : applicableUnitList) {
				String id = UUID.randomUUID().toString();
				applicableUnit.setId(id);
				applicableUnit.setWhrid(user.getId());
				applicableUnit.setWhbmid(user.getBmdm());
				applicableUnit.setMainid(mainid);
				applicableUnit.setZt(EffectiveEnum.YES.getId());
				columnValueList.add(id);
			}
			applicableUnitMapper.insert4Batch(applicableUnitList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_01302, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个工卡-适用单位
	 * @CreateTime 2017-11-8 下午3:16:18
	 * @CreateBy 刘兵
	 * @param ApplicableUnitList 工卡-适用单位集合
	 * @param mainid 父ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	@Override
	public void updateApplicableUnitList(List<ApplicableUnit> applicableUnitList, String mainid, String czls, String zdid, LogOperationEnum logopration){
		List<ApplicableUnit> insertApplicableUnitList = null;//工卡-适用单位集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入工卡-适用单位id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工卡-适用单位
		User user = ThreadVarUtil.getUser();
		//根据业务id、业务类型、机构代码获取数据库已经存在的工作内容
		List<ApplicableUnit> existsApplicableUnitList = applicableUnitMapper.queryByMainid(mainid);
		//工卡-适用单位map集合,key:id,ApplicableUnit
		Map<String, ApplicableUnit> applicableUnitMap = new HashMap<String, ApplicableUnit>();
		//将数据库已存在的工卡-适用单位放入applicableUnitMap
		for (ApplicableUnit applicableUnit : existsApplicableUnitList) {
			applicableUnitMap.put(applicableUnit.getSydwid(), applicableUnit);
		}
		if(null != applicableUnitList && applicableUnitList.size() > 0){
			insertApplicableUnitList = new ArrayList<ApplicableUnit>();
			for (ApplicableUnit applicableUnit : applicableUnitList) {
				idMap.put(applicableUnit.getSydwid(), applicableUnit.getSydwid());
				//判断工卡-适用单位id是否为空,是否存在于数据库,不为空且存在:修改工卡-适用单位,反之:新增工卡-适用单位
				ApplicableUnit oldApplicableUnit = applicableUnitMap.get(applicableUnit.getSydwid());
				if(null != applicableUnit.getSydwid() && null != oldApplicableUnit){
					applicableUnit.setId(oldApplicableUnit.getId());
					applicableUnit.setWhrid(user.getId());
					applicableUnit.setWhbmid(user.getBmdm());
					//修改工卡-适用单位
					applicableUnitMapper.updateByPrimaryKeySelective(applicableUnit);
					//保存历史记录信息
					commonRecService.write(applicableUnit.getId(), TableEnum.B_G2_01302, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
				}else{
					insertApplicableUnitList.add(applicableUnit);
				}
			}
			//保存多个工卡-适用单位
			saveApplicableUnitList(insertApplicableUnitList, mainid, czls, zdid, logopration);
		}
		for (ApplicableUnit applicableUnit : existsApplicableUnitList){
			//如果数据库工作内容id不在页面,那么删除
			if(null == idMap.get(applicableUnit.getSydwid())){
				columnValueList.add(applicableUnit.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_01302, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
			//批量删除工作内容
			applicableUnitMapper.delete4Batch(columnValueList);
		}
	}

	/**
	 * @Description 根据mainid删除工卡-适用单位
	 * @CreateTime 2017-11-8 下午3:16:18
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByMainid(String mainid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//mainid集合,用于批量插入日志,删除工卡-适用单位
		User user = ThreadVarUtil.getUser();
		columnValueList.add(mainid);
		// 保存历史记录信息
		commonRecService.write("mainid", columnValueList, TableEnum.B_G2_01302, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除工卡-适用单位
		applicableUnitMapper.deleteByMainid(mainid);
	}
}
