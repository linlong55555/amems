package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.InstallationListTemp2InitMapper;
import com.eray.thjw.produce.po.InstallationListTemp;
import com.eray.thjw.produce.po.InstallationListTemp2Init;
import com.eray.thjw.produce.service.InstallationListTemp2InitService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.project2.MonitorProjectEnum;

/**
 * @Description 装机清单-临时区-部件初始化service实现类
 * @CreateTime 2017年9月28日 下午4:30:19
 * @CreateBy 韩武
 */
@Service()
public class InstallationListTemp2InitServiceImpl implements InstallationListTemp2InitService{

	@Resource
	private InstallationListTemp2InitMapper installationListTemp2InitMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存装机清单-临时去-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:30:39
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void save(InstallationListTemp record) {
		
		User user = ThreadVarUtil.getUser();
		
		// 删除装机清单-临时去-部件初始化数据
		delete(record);		
		
		// 保存装机清单-临时去-部件初始化数据
		if (record.getInitDatas() != null 
				&& !record.getInitDatas().isEmpty()){
			
			List<String> idList = new ArrayList<String>();
			for (InstallationListTemp2Init init : record.getInitDatas()) {
				
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
				installationListTemp2InitMapper.insertSelective(init);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_S2_00201, user.getId(), 
						record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getFxjldid());
			}
		}
		
	}
	
	
	/**
	 * @Description 删除装机清单-临时去-部件初始化数据
	 * @CreateTime 2017年9月28日 下午4:54:19
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void delete(InstallationListTemp record){
		
		User user = ThreadVarUtil.getUser();
		List<String> delList = new ArrayList<String>();
		delList.add(record.getId());
		
		// 保存历史记录信息
		commonRecService.write("mainid", delList, TableEnum.B_S2_00201, user.getId(), 
				record.getCzls(), record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getFxjldid());
		
		// 删除装机清单-临时去-部件初始化数据
		installationListTemp2InitMapper.deleteByMainid(record.getId());
	}
	

}
