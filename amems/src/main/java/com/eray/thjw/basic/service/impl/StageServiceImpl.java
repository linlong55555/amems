package com.eray.thjw.basic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.StageMapper;
import com.eray.thjw.basic.po.Stage;
import com.eray.thjw.basic.service.StageService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/** 
 * @Description stage service实现类
 * @CreateTime 2017-9-14 上午11:49:32
 * @CreateBy 甘清	
 */
@Service
public class StageServiceImpl implements StageService {
	
	@Resource
	private  StageMapper stageMapper;   //stage Mapper
	
	@Resource
	private CommonRecService commonRecService;

	@Override
	public Map<String, Object> getStages(Stage stage) throws BusinessException {
		String id  = stage.getId();
		stage.setId("");
		PageHelper.startPage(stage.getPagination());
		try {
			List<Stage> list = stageMapper.getStageList(stage);
			if(((Page)list).getTotal() > 0) {
				if(StringUtils.isNotBlank(id)) {
					if (!PageUtil.hasRecord(list, id)) {
						Stage s = new Stage();
						s.setId(id);
						Stage firtStage = stageMapper.getStageById(s);
						if (firtStage != null) {
							list.add(0, firtStage);
						}
					}
				}
				return PageUtil.pack4PageHelper(list, stage.getPagination());
			} else {
				List<Stage> stagelist = new ArrayList<Stage>();
				if(StringUtils.isNotBlank(id)) {
					Stage s = new Stage();
					s.setId(id);
					Stage firtStage = stageMapper.getStageById(s);
					if (firtStage != null) {
						stagelist.add(firtStage);
					}
				}
				return PageUtil.pack(1, stagelist, stage.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
	}
    
	/**
	 * @Description 保存阶段,并check是否重名
	 * @CreateTime 2017-9-19 下午6:34:43
	 * @CreateBy 甘清
	 * @param stage 阶段参数
	 * @return
	 */
	@Override
	public Stage addStage(Stage stage)  throws BusinessException  {
		User user = ThreadVarUtil.getUser();
		Stage checkStage = new Stage();
		checkStage.setDprtcode(user.getJgdm()); //组织机构码
		checkStage.setJdbh(stage.getJdbh());   //阶段编号
		List<Stage> list = stageMapper.checkStage(checkStage);
		if (list == null || list.size() < 1) {
			//再次验证项次是否重复出现
			String id = UUID.randomUUID().toString();
			String czls = UUID.randomUUID().toString();
			stage.setId(id);
			stage.setWhsj(new Date());
			stage.setWhrid(user.getId());
			stage.setWhbmid(user.getBmdm());
			stage.setDprtcode(user.getJgdm());
			stage.setZt(EffectiveEnum.YES.getId());
			stageMapper.addStage(stage);
			commonRecService.write(stage.getId(), TableEnum.D_024, user.getId(), czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, stage.getId());
			return stage;
		} else {
			throw new BusinessException("阶段编号:" + stage.getJdbh() + "已存在!");
		}
	}
    /**
     * @Description 更新阶段名称,并check是否重名
     * @CreateTime 2017-9-19 下午6:40:47
     * @CreateBy 甘清
     * @param stage 阶段参数
     * @return boolean
     */
	@Override
	public void updateStage(Stage stage) throws BusinessException {
		Stage oldStage = stageMapper.getStageById(stage);
		if (oldStage == null) {
			throw new BusinessException("该阶段信息已删除，请刷新后再进行操作!");
		}
		User user = ThreadVarUtil.getUser();
		Stage checkStage = new Stage();
		checkStage.setDprtcode(oldStage.getDprtcode()); //取老的组织机构码
		checkStage.setJdbh(stage.getJdbh());   //阶段编号
		List<Stage> list = stageMapper.checkStage(checkStage);
		if (list != null && list.size() > 0) {
			int size = list.size();
			if (size > 1) { //重新重复的stage
				throw new BusinessException("阶段编号:" + stage.getJdbh() + "已存在!");
			}
			if (oldStage.getId().equals(stage.getId())) { //前后的编号一致，说明stage不存在重名
				stage.setDprtcode(oldStage.getDprtcode());
				stage.setWhsj(new Date());
				stage.setWhrid(user.getId());
				stage.setDprtcode(oldStage.getDprtcode());
				String czls = UUID.randomUUID().toString();
				stageMapper.updateStage(stage);
				commonRecService.write(stage.getId(), TableEnum.D_024, user.getId(), czls, LogOperationEnum.EDIT,
						UpdateTypeEnum.UPDATE, stage.getId());
				} else {
					throw new BusinessException("阶段编号:" + stage.getJdbh() + "已存在!");
				}
			} else { //数据被删
				throw new BusinessException("该阶段信息已删除，请刷新后再进行操作!");
			}
	}

	@Override
	public Stage getStageById(Stage stage) {
		return stageMapper.getStageById(stage);
	}
	
	/**
	 * @Description 根据机构代码获取阶段集合
	 * @CreateTime 2017-9-19 下午2:11:08
	 * @CreateBy 刘兵
	 * @param dprtcode 机构代码
	 * @return List<Stage> 阶段集合
	 */
	@Override
	public List<Stage> getStageListByDrpt(String dprtcode) {
		return stageMapper.getStageListByDrpt(dprtcode);
	}

	/**
	 * 删除操作
	 */
	@Override
	public void updateStageStatus(String id) throws BusinessException {
		Stage stage = new Stage();
		User user = ThreadVarUtil.getUser();
		stage.setId(id);
		String czls = UUID.randomUUID().toString();
		stage.setZt(EffectiveEnum.NO.getId());
		commonRecService.write(stage.getId(), TableEnum.D_024, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, stage.getId());
		stageMapper.deleteStage(stage);
	}
}
