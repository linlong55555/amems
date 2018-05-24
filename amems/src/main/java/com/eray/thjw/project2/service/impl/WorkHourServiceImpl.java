package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.WorkHourMapper;
import com.eray.thjw.project2.po.WorkHour;
import com.eray.thjw.project2.service.WorkHourService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 工种工时service实现类
 * @CreateTime 2017-8-19 下午2:58:02
 * @CreateBy 刘兵
 */
@Service
public class WorkHourServiceImpl implements WorkHourService{
	
	@Resource
	private WorkHourMapper workHourMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存多个工种工时
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param workHourList 工种工时集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void saveWorkHourList(List<WorkHour> workHourList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		if(null != workHourList && workHourList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (WorkHour workHour : workHourList) {
				String id = UUID.randomUUID().toString();
				workHour.setId(id);
				workHour.setWhrid(user.getId());
				workHour.setWhdwid(user.getBmdm());
				workHour.setYwlx(ywlx);
				workHour.setDprtcode(dprtcode);
				workHour.setYwid(ywid);
				workHour.setZt(EffectiveEnum.YES.getId());
				columnValueList.add(id);
			}
			workHourMapper.insert4Batch(workHourList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_993, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个工种工时
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param workHourList 工种工时集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void updateWorkHourList(List<WorkHour> workHourList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		List<WorkHour> insertWorkHourList = null;//工种工时集合,用于新增
		Map<String, String> gzzidAndJdidMap = new HashMap<String, String>();//记录页面传入工种组id和阶段id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		User user = ThreadVarUtil.getUser();
		//根据业务id、业务类型、机构代码获取数据库已经存在的工种工时
		List<WorkHour> existsWorkHourList = workHourMapper.queryByYwidAndYwlxAndDrpt(ywid, ywlx, dprtcode);
		//工种工时map集合,key:gzzid,value:WorkHour
		Map<String, WorkHour> workHourMap = new HashMap<String, WorkHour>();
		//将数据库已存在的工种工时放入workHourMap
		for (WorkHour workHour : existsWorkHourList) {
			workHourMap.put(new StringBuffer().append(workHour.getJdid()).append(workHour.getGzzid()).toString(), workHour);
		}
		if(null != workHourList && workHourList.size() > 0){
			insertWorkHourList = new ArrayList<WorkHour>();
			for (WorkHour workHour : workHourList) {
				StringBuffer gjid = new StringBuffer();
				gjid.append(workHour.getJdid()).append(workHour.getGzzid());
				gzzidAndJdidMap.put(gjid.toString(), workHour.getGzzid());
				//判断工作组是否存在于数据库,存在:修改工种工时,不存在:新增工种工时
				if(null != workHourMap.get(gjid.toString())){
					workHour.setId(workHourMap.get(gjid.toString()).getId());
					workHour.setWhrid(user.getId());
					workHour.setWhdwid(user.getBmdm());
					//修改工种工时
					workHourMapper.updateByPrimaryKeySelective(workHour);
					//保存历史记录信息
					commonRecService.write(workHour.getId(), TableEnum.B_G2_993, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
				}else{
					insertWorkHourList.add(workHour);
				}
			}
			//保存多个工种工时
			saveWorkHourList(insertWorkHourList, ywlx, ywid, czls, zdid, dprtcode, logopration);
		}
		for (WorkHour workHour : existsWorkHourList){
			//如果数据库工作组id不在页面,那么删除
			if(null == gzzidAndJdidMap.get(new StringBuffer().append(workHour.getJdid()).append(workHour.getGzzid()).toString())){
				columnValueList.add(workHour.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_993, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
			//批量删除工种工时
			workHourMapper.delete4Batch(columnValueList);
		}
	}
	
	/**
	 * @Description 根据ywid删除工种工时
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		User user = ThreadVarUtil.getUser();
		columnValueList.add(ywid);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_993, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除工种工时
		workHourMapper.deleteByYwid(ywid);
	}
	
	/**
	 * @Description 根据条件查询工种工时列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workHour 工种工时
	 * @return List<WorkHour> 工种工时集合
	 */
	@Override
	public List<WorkHour> queryAllList(WorkHour workHour){
		return workHourMapper.queryAllList(workHour);
	}

}
