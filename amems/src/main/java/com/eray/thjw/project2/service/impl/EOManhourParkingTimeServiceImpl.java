package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.EOManhourParkingTimeMapper;
import com.eray.thjw.project2.po.EOManhourParkingTime;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.service.EOManhourParkingTimeService;
import com.eray.thjw.service.CommonRecService;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/** 
 * @Description EO-工时/停场时间 业务实现
 * @CreateTime 2017-8-23 上午10:12:52
 * @CreateBy 雷伟	
 */
@Service
public class EOManhourParkingTimeServiceImpl implements EOManhourParkingTimeService {
	@Resource
	private EOManhourParkingTimeMapper eoManhourParkingTimeMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 新增EO-工时/停场时间
	 * @CreateTime 2017-8-23 上午10:16:03
	 * @CreateBy 雷伟
	 * @param gstcshList 数据集合
	 * @param user 当前用户
	 * @param eoId 业务ID
	 * @param currentDate 当前时间
	 * @param czls 操作流水号
	 * @param operation 操作类型
	 */
	@Override
	public void saveManhourParkingTimeList(List<EOManhourParkingTime> gstcshList, User user, String eoId,Date currentDate, String czls,LogOperationEnum operation) {
		if(gstcshList == null || gstcshList.size() <= 0) {
			return;
		}
		
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		for (EOManhourParkingTime eoManhourParkingTime : gstcshList) {
			String id = UUID.randomUUID().toString();
			eoManhourParkingTime.setId(id);
			eoManhourParkingTime.setMainid(eoId); //业务ID
			eoManhourParkingTime.setZt(EffectiveEnum.YES.getId());//状态
			eoManhourParkingTime.setWhdwid(user.getBmdm());
			eoManhourParkingTime.setWhrid(user.getId());
			eoManhourParkingTime.setWhsj(currentDate);
			columnValueList.add(id);
		}
		eoManhourParkingTimeMapper.insert4Batch(gstcshList);
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_01004, user.getId(), czls, operation, UpdateTypeEnum.SAVE, eoId);
		}
	}

	/**
	 * @Description 查询EO工时/停场时间
	 * @CreateTime 2017-8-24 下午3:25:16
	 * @CreateBy 雷伟
	 * @param eoManhourParkingTime EO工时/停场时间
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<EOManhourParkingTime> queryAllList(EOManhourParkingTime eoManhourParkingTime) {
		return eoManhourParkingTimeMapper.queryAllList(eoManhourParkingTime);
	}

	@Override
	public void deleteByMainid(EngineeringOrder engineeringOrder,User user, Date currentDate, String czls, LogOperationEnum operation) {
		commonRecService.write(engineeringOrder.getId(), TableEnum.B_G2_01004, user.getId(), czls, operation, UpdateTypeEnum.DELETE, engineeringOrder.getId());
		eoManhourParkingTimeMapper.deleteByMainid(engineeringOrder.getId());
	}
}
