package com.eray.thjw.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.system.dao.GlobleSystemConfigMapper;
import com.eray.thjw.system.po.GlobleSystemConfig;
import com.eray.thjw.system.service.GlobleSystemConfigService;

@Service
public class GlobleSystemConfigServiceImpl implements GlobleSystemConfigService {

	@Resource
	private GlobleSystemConfigMapper globleSystemConfigMapper;

	@Override
	public List<GlobleSystemConfig> getBySyscode(String syscode) {
		
		return globleSystemConfigMapper.getBySyscode(syscode);
	}
	@Override
	public void updateBySyscode(List<GlobleSystemConfig> list) {
		if(list.size()>0){
			GlobleSystemConfig config=new GlobleSystemConfig();
			config.setSyscode(list.get(0).getSyscode());
			config.setPzbm(GlobalConstants.AGENCY_TYPE_KEY);
			config.setPzbm("AGENCY_TYPE");
			globleSystemConfigMapper.deleteJglx(config);//机构类型先删除
		}
		for (GlobleSystemConfig globleSystemConfig : list) {
			if(!GlobalConstants.AGENCY_TYPE_KEY.equals(globleSystemConfig.getPzbm())){
				globleSystemConfigMapper.updateBySyscode(globleSystemConfig);
			}else{
				globleSystemConfigMapper.insertJglx(globleSystemConfig);//在新增进去
			}
		}
	}	
	
	/**
	 * 初始化：加载全局设置信息
	 * @author xu.yong
	 */
	public void initGlobalSettings(){
		List<GlobleSystemConfig> list = this.getBySyscode(SysContext.APP_NAME);
		if(list != null && !list.isEmpty()){
			for (GlobleSystemConfig globleSystemConfig : list) {
				if(GlobalConstants.AGENCY_TYPE_KEY.equals(globleSystemConfig.getPzbm())){
					List<String> agencyTypeList = GlobalConstants.getList(GlobalConstants.AGENCY_TYPE_KEY);
					agencyTypeList.add(globleSystemConfig.getValue1());
					GlobalConstants.VALUE_MAP.put(GlobalConstants.AGENCY_TYPE_KEY, agencyTypeList);
				}else{
					GlobalConstants.VALUE_MAP.put(globleSystemConfig.getPzbm().toUpperCase(), globleSystemConfig.getValue1());
				}
			}
		}
	}
}
