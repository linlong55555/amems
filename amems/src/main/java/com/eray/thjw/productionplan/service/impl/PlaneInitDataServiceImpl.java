package com.eray.thjw.productionplan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.productionplan.dao.PlaneInitDataMapper;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.po.PlaneInitData;
import com.eray.thjw.productionplan.service.PlaneInitDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.PlaneInitDataEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class PlaneInitDataServiceImpl implements PlaneInitDataService {

	@Resource
	private PlaneInitDataMapper planeInitDataMapper;

	@Resource
	private CommonRecService commonRecService;

	/**
	 * 新增飞机初始化数据
	 */
	@Override
	public void add(List<PlaneInitData> initDatas, String fjzch, String dprtcode, UpdateTypeEnum type, String czls,
			LogOperationEnum logOperationEnum) throws RuntimeException {
		if (initDatas != null && !initDatas.isEmpty()) {
			// 根据项目编号填充飞机初始化项目描述
			this.fillXmms(initDatas, dprtcode);
			// 新增飞机初始化数据
			planeInitDataMapper.batchInsert(initDatas);
		}
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+fjzch.replaceAll("'", "''")+"' and b.dprtcode = '"+dprtcode+"'"
				, TableEnum.D_00701, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, type
				, fjzch.concat(",").concat(dprtcode));
	}

	/**
	 * 根据项目编号填充项目描述
	 * 
	 * @param initDatas
	 */
	private void fillXmms(List<PlaneInitData> initDatas, String dprtcode) {
		for (PlaneInitData planeInitData : initDatas) {
			if (dprtcode == null || dprtcode.equals("")) {
				planeInitData.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			} else {
				planeInitData.setDprtcode(dprtcode);
			}
			planeInitData.setInitXmms(PlaneInitDataEnum.getDescription(planeInitData.getInitXmbh()));
		}
	}

	/**
	 * 删除飞机初始化数据
	 */
	@Override
	public void deleteByFjzch(String fjzch, String dprtcode, String czls, LogOperationEnum logOperationEnum)
			throws RuntimeException {
		PlaneInitData pd = new PlaneInitData();
		pd.setFjzch(fjzch);
		pd.setDprtcode(dprtcode);
		planeInitDataMapper.deleteByFjzch(pd);
		// 插入日志
		commonRecService.writeByWhere(" b.fjzch = '"+fjzch.replaceAll("'", "''")+"' and b.dprtcode = '"+dprtcode+"'"
				, TableEnum.D_00701, ThreadVarUtil.getUser().getId(), czls, logOperationEnum, UpdateTypeEnum.DELETE
				, fjzch.concat(",").concat(dprtcode));
	}

	@Override
	public PlaneInitData selectByKey(PlaneInitData pd) throws Exception {
		return planeInitDataMapper.selectByKey(pd);
	}

	/**
	 * 根据飞机注册号查询对应的初始化数据
	 */
	@Override
	public Map<String, Object> findByFjzch(String  fjzch, String dprtcode) {
		return planeInitDataMapper.findByFjzch(new PlaneData(fjzch, dprtcode));
	}

}
