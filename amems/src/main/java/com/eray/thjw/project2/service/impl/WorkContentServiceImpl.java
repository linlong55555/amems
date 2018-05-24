package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.WorkContentMapper;
import com.eray.thjw.project2.po.WorkContent;
import com.eray.thjw.project2.service.WorkContentService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 工作内容service实现类
 * @CreateTime 2017-8-19 下午2:58:02
 * @CreateBy 刘兵
 */
@Service
public class WorkContentServiceImpl implements WorkContentService{
	
	@Resource
	private WorkContentMapper workContentMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存多个工作内容
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param workContentList 工作内容集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void saveWorkContentList(List<WorkContent> workContentList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		if(null != workContentList && workContentList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (WorkContent workContent : workContentList) {
				String id = UUID.randomUUID().toString();
				workContent.setId(id);
				workContent.setWhrid(user.getId());
				workContent.setWhdwid(user.getBmdm());
				workContent.setYwlx(ywlx);
				workContent.setDprtcode(dprtcode);
				workContent.setYwid(ywid);
				workContent.setZt(EffectiveEnum.YES.getId());
				columnValueList.add(id);
			}
			workContentMapper.insert4Batch(workContentList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_996, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个工作内容
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param workContentList 工作内容集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void updateWorkContentList(List<WorkContent> workContentList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		List<WorkContent> insertWorkContenList = null;//工作内容集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入工作内容id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		User user = ThreadVarUtil.getUser();
		//根据业务id、业务类型、机构代码获取数据库已经存在的工作内容
		List<WorkContent> existsWorkContentList = workContentMapper.queryByYwidAndYwlxAndDrpt(ywid, ywlx, dprtcode);
		//工作内容map集合,key:id,WorkContent
		Map<String, WorkContent> workContentMap = new HashMap<String, WorkContent>();
		//将数据库已存在的工作内容放入workContentMap
		for (WorkContent workContent : existsWorkContentList) {
			workContentMap.put(workContent.getId(), workContent);
		}
		if(null != workContentList && workContentList.size() > 0){
			insertWorkContenList = new ArrayList<WorkContent>();
			for (WorkContent workContent : workContentList) {
				//判断工作内容id是否为空,是否存在于数据库,不为空且存在:修改工作内容,反之:新增工作内容
				if(null != workContent.getId() && null != workContentMap.get(workContent.getId())){
					workContent.setWhrid(user.getId());
					workContent.setWhdwid(user.getBmdm());
					idMap.put(workContent.getId(), workContent.getId());
					//修改工作内容
					workContentMapper.updateByPrimaryKeySelective(workContent);
					//保存历史记录信息
					commonRecService.write(workContent.getId(), TableEnum.B_G2_996, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
				}else{
					insertWorkContenList.add(workContent);
				}
			}
			//保存多个工作内容
			saveWorkContentList(insertWorkContenList, ywlx, ywid, czls, zdid, dprtcode, logopration);
		}
		for (WorkContent workContent : existsWorkContentList){
			//如果数据库工作内容id不在页面,那么删除
			if(null == idMap.get(workContent.getId())){
				columnValueList.add(workContent.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_996, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
			//批量删除工作内容
			workContentMapper.delete4Batch(columnValueList);
		}
	}
	
	/**
	 * @Description 根据ywid删除工作内容
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工作内容
		User user = ThreadVarUtil.getUser();
		columnValueList.add(ywid);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_996, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除工作内容
		workContentMapper.deleteByYwid(ywid);
	}
	
	/**
	 * @Description 根据条件查询工作内容列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param workContent 工作内容
	 * @return List<WorkContent> 工作内容集合
	 */
	@Override
	public List<WorkContent> queryAllList(WorkContent workContent){
		return workContentMapper.queryAllList(workContent);
	}


}
