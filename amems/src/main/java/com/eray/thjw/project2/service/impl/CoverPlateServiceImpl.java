package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.CoverPlateMapper;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaintenanceProject;
import com.eray.thjw.project2.service.CoverPlateService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.project2.MaintenanceProjectTypeEnum;
import enu.project2.ProjectBusinessEnum;


/**
 * @Description 接近/盖板service实现类
 * @CreateTime 2017年8月16日 下午1:44:40
 * @CreateBy 韩武
 */
@Service
public class CoverPlateServiceImpl implements CoverPlateService{
	
	@Resource
	private CoverPlateMapper coverPlateMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存接近/盖板-维修方案
	 * @CreateTime 2017年8月16日 上午10:42:25
	 * @CreateBy 韩武
	 * @param project 维修项目
	 * @throws BusinessException
	 */
	@Override
	public void save(MaintenanceProject project) throws BusinessException {
		
		if (project.getWxxmlx() != MaintenanceProjectTypeEnum.FIXEDPACKAGE.getId()){	// 不是定检包
			
			if(LogOperationEnum.SAVE_WO.getId().equals(project.getLogOperationEnum().getId())
					|| LogOperationEnum.REVISION.getId().equals(project.getLogOperationEnum().getId())){	// 新增/改版
				saveCoverPlateList(project.getCoverPlateList(), ProjectBusinessEnum.MP.getId(), 
						project.getId(), project.getCzls(), project.getId(), project.getDprtcode(), project.getLogOperationEnum());
			}else{	// 修改
				updateCoverPlateList(project.getCoverPlateList(), ProjectBusinessEnum.MP.getId(), 
						project.getId(), project.getCzls(), project.getId(), project.getDprtcode(), project.getLogOperationEnum());
			}
		}
	}
	
	/**
	 * @Description 保存多个接近/盖板
	 * @CreateTime 2017-8-18 下午7:05:37
	 * @CreateBy 刘兵
	 * @param coverPlateList 接近/盖板集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void saveCoverPlateList(List<CoverPlate> coverPlateList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		if(null != coverPlateList && coverPlateList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (CoverPlate coverPlate : coverPlateList) {
				String id = UUID.randomUUID().toString();
				coverPlate.setId(id);
				coverPlate.setWhrid(user.getId());
				coverPlate.setWhdwid(user.getBmdm());
				coverPlate.setYwlx(ywlx);
				coverPlate.setDprtcode(dprtcode);
				coverPlate.setYwid(ywid);
				columnValueList.add(id);
			}
			coverPlateMapper.insert4Batch(coverPlateList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_995, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个接近/盖板
	 * @CreateTime 2017-8-18 下午7:05:37
	 * @CreateBy 刘兵
	 * @param coverPlateList 接近/盖板集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void updateCoverPlateList(List<CoverPlate> coverPlateList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		User user = ThreadVarUtil.getUser();
		List<String> columnValueList = new ArrayList<String>();//ywid集合,用于批量插入日志
		columnValueList.add(ywid);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_995, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除接近/盖板
		coverPlateMapper.deleteByYwid(ywid);
		//保存多个接近/盖板
		saveCoverPlateList(coverPlateList, ywlx, ywid, czls, zdid, dprtcode, logopration);
	}

	/**
	 * @Description 根据ywid删除接近/盖板
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//ywid集合,用于批量插入日志,删除接近/盖板
		User user = ThreadVarUtil.getUser();
		columnValueList.add(ywid);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_995, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除接近/盖板
		coverPlateMapper.deleteByYwid(ywid);
	}
}
