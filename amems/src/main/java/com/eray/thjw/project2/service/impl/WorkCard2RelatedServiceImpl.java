package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.WorkCard2RelatedMapper;
import com.eray.thjw.project2.po.WorkCard2Related;
import com.eray.thjw.project2.service.WorkCard2RelatedService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;


/**
 * @Description 工卡-关联工卡service实现类 
 * @CreateTime 2017-8-25 下午5:34:05
 * @CreateBy 刘兵
 */
@Service
public class WorkCard2RelatedServiceImpl implements WorkCard2RelatedService{
	
	@Resource
	private WorkCard2RelatedMapper workCard2RelatedMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存多个工卡-关联工卡
	 * @CreateTime 2017-8-25 下午5:41:24
	 * @CreateBy 刘兵
	 * @param workCard2RelatedList 工卡-关联工卡集合
	 * @param mainid 父表单id
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	@Override
	public void saveWorkCard2RelatedList(List<WorkCard2Related> workCard2RelatedList, String mainid, String czls, String zdid, LogOperationEnum logopration){
		if(null != workCard2RelatedList && workCard2RelatedList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (WorkCard2Related workCard2Related : workCard2RelatedList) {
				String id = UUID.randomUUID().toString();
				workCard2Related.setId(id);
				workCard2Related.setWhrid(user.getId());
				workCard2Related.setWhbmid(user.getBmdm());
				workCard2Related.setMainid(mainid);
				workCard2Related.setZt(EffectiveEnum.YES.getId());
				columnValueList.add(id);
			}
			workCard2RelatedMapper.insert4Batch(workCard2RelatedList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_01301, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个工卡-关联工卡
	 * @CreateTime 2017-8-25 下午5:41:24
	 * @CreateBy 刘兵
	 * @param workCard2RelatedList 工卡-关联工卡集合
	 * @param mainid 父表单id
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param logopration 操作类型
	 */
	@Override
	public void updateWorkCard2RelatedList(List<WorkCard2Related> workCard2RelatedList, String mainid, String czls, String zdid, LogOperationEnum logopration){
		List<WorkCard2Related> insertWorkCard2RelatedList = null;//工卡-关联工卡集合,用于新增
		Map<String, String> gkhMap = new HashMap<String, String>();//记录页面传入工卡-关联工卡工卡号集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		User user = ThreadVarUtil.getUser();
		//根据业务id、业务类型、机构代码获取数据库已经存在的工卡-关联工卡
		List<WorkCard2Related> existsWorkCard2RelatedList = workCard2RelatedMapper.queryByMainid(mainid);
		//工卡-关联工卡map集合,key:id,WorkCard2Related
		Map<String, WorkCard2Related> workCard2RelatedMap = new HashMap<String, WorkCard2Related>();
		//将数据库已存在的工卡-关联工卡放入workCard2RelatedMap
		for (WorkCard2Related workCard2Related : existsWorkCard2RelatedList) {
			workCard2RelatedMap.put(workCard2Related.getGkh(), workCard2Related);
		}
		if(null != workCard2RelatedList && workCard2RelatedList.size() > 0){
			insertWorkCard2RelatedList = new ArrayList<WorkCard2Related>();
			for (WorkCard2Related workCard2Related : workCard2RelatedList) {
				gkhMap.put(workCard2Related.getGkh(), workCard2Related.getGkh());
				//判断工卡-关联工卡id是否为空,是否存在于数据库,不为空且存在:修改工卡-关联工卡,反之:新增工卡-关联工卡
				if(null != workCard2RelatedMap.get(workCard2Related.getGkh())){
					workCard2Related.setId(workCard2RelatedMap.get(workCard2Related.getGkh()).getId());
					workCard2Related.setWhrid(user.getId());
					workCard2Related.setWhbmid(user.getBmdm());
					//修改工卡-关联工卡
					workCard2RelatedMapper.updateByPrimaryKeySelective(workCard2Related);
					//保存历史记录信息
					commonRecService.write(workCard2Related.getId(), TableEnum.B_G2_01301, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
				}else{
					insertWorkCard2RelatedList.add(workCard2Related);
				}
			}
			//保存多个工卡-关联工卡
			saveWorkCard2RelatedList(insertWorkCard2RelatedList, mainid, czls, zdid, logopration);
		}
		for (WorkCard2Related workCard2Related : existsWorkCard2RelatedList){
			//如果数据库工卡-关联工卡gkh不在页面,那么删除
			if(null == gkhMap.get(workCard2Related.getGkh())){
				columnValueList.add(workCard2Related.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_01301, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
			//批量删除工卡-关联工卡
			workCard2RelatedMapper.delete4Batch(columnValueList);
		}
	}
	
	/**
	 * @Description 根据mainid删除工卡-关联工卡
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByMainid(String mainid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工卡-关联工卡
		User user = ThreadVarUtil.getUser();
		columnValueList.add(mainid);
		commonRecService.write("mainid", columnValueList, TableEnum.B_G2_01301, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		workCard2RelatedMapper.deleteByMainid(mainid);
	}
	
	/**
	 * @Description 根据条件查询工卡-关联工卡列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workCard2Related 工卡-关联工卡
	 * @return List<WorkCard2Related> 工卡-关联工卡集合
	 */
	@Override
	public List<WorkCard2Related> queryAllList(WorkCard2Related workCard2Related){
		return workCard2RelatedMapper.queryAllList(workCard2Related);
	}


}
