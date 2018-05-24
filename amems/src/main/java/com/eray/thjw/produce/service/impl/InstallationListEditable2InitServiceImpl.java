package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.InstallationListEditable2InitMapper;
import com.eray.thjw.produce.po.InstallationListEditable;
import com.eray.thjw.produce.po.InstallationListEditable2Init;
import com.eray.thjw.produce.service.InstallationListEditable2InitService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 装机清单-编辑区-部件初始化service实现类
 * @CreateTime 2017年9月28日 下午4:30:19
 * @CreateBy 韩武
 */
@Service()
public class InstallationListEditable2InitServiceImpl implements InstallationListEditable2InitService{

	@Resource
	private InstallationListEditable2InitMapper installationListEditable2InitMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存装机清单-编辑区-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:30:39
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void save(InstallationListEditable record) {
		
		User user = ThreadVarUtil.getUser();
		
		// 删除装机清单-编辑区-部件初始化数据
		delete(record);		
		
		// 保存装机清单-编辑区-部件初始化数据
		if (record.getInitDatas() != null 
				&& !record.getInitDatas().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (InstallationListEditable2Init init : record.getInitDatas()) {
				
				// 时间类型，小时转化为分钟
				if(MonitorProjectEnum.isTime(init.getJklbh())){
					init.setCsz(StringAndDate_Util.convertToMinuteStr(init.getCsz()));
				}
				init.setId(UUID.randomUUID().toString());
				init.setMainid(record.getId());
				init.setWhrid(user.getId());
				init.setWhsj(new Date());
				init.setWhbmid(user.getBmdm());
				idList.add(init.getId());
				installationListEditable2InitMapper.insertSelective(init);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_S2_00101, user.getId(), 
						record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getId());
			}
		}
		
	}
	
	/**
	 * @Description 批量新增装机清单-初始值 
	 * @CreateTime 2017年10月17日 上午11:43:58
	 * @CreateBy 徐勇
	 * @param list 装机清单-初始值
	 * @param czls 操作流水
	 * @param logOperationEnum 操作
	 * @param zbid 主表id,即装机清单
	 */
	public void add4Batch(List<InstallationListEditable2Init> list, String czls, LogOperationEnum logOperationEnum, String zbid) {
		for (int i = 0; i <= list.size()/SysContext.BATCH_PROCESS_SIZE; i++) {
			if(i*SysContext.BATCH_PROCESS_SIZE < list.size()){
				this.installationListEditable2InitMapper.insert4Batch(list.subList(i*SysContext.BATCH_PROCESS_SIZE, 
						((i+1) * SysContext.BATCH_PROCESS_SIZE > list.size() ? list.size():(i+1) * SysContext.BATCH_PROCESS_SIZE)));
			}
		}
		// 保存历史记录信息
		List<String> zjqdList = new ArrayList<String>(1);
		zjqdList.add(zbid);
		commonRecService.write("mainid", zjqdList, TableEnum.B_S2_00101, ThreadVarUtil.getUser().getId(), 
				czls, logOperationEnum, UpdateTypeEnum.SAVE, zbid);
	}
	
	
	/**
	 * @Description 删除装机清单-编辑区-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:54:19
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void delete(InstallationListEditable record){
		
		User user = ThreadVarUtil.getUser();
		List<String> delList = new ArrayList<String>();
		delList.add(record.getId());
		
		// 保存历史记录信息
		commonRecService.write("mainid", delList, TableEnum.B_S2_00101, user.getId(), 
				record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getId());
		
		// 删除装机清单-编辑区-部件初始化数据
		installationListEditable2InitMapper.deleteByMainid(record.getId());
	}
	

}
