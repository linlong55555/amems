package com.eray.thjw.monitorsettings.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.dao.MonitorsettingsMapper;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
/**
 * @author sunji
 * @description 系统阀值设置 ServiceImpl类
 * @develop date 2017.04.14
 */
@Service
public class MonitorsettingsServiceImpl implements MonitorsettingsService{

	
	@Autowired
	private MonitorsettingsMapper monitorsettingsMapper;
	/**
	 * 根据组织机构查询系统阀值设置信息的实现方法
	 */
	@Override
	public List<Monitorsettings> queryThresholdByDprtcode(String dprtcode)
			throws BusinessException {
		List<Monitorsettings> monitorsettingsList = monitorsettingsMapper.selectThresholdByDprtcode(dprtcode);
		return monitorsettingsList;
	}
	/**
	 * 系统阀值设置修改信息的实现方法
	 */
	public void update(Monitorsettings monitorsettings)throws BusinessException {
		List<Monitorsettings> monitorsettingsListNew=monitorsettings.getMonitorsettingsList();
		if("-1".equals(monitorsettings.getDprtcode())){
			for (Monitorsettings monitorsettingsNew : monitorsettingsListNew) {
				monitorsettingsMapper.updateByKeyandDprtcode(monitorsettingsNew);
			}
		}else{
			List<Monitorsettings> monitorsettingsListOld = monitorsettingsMapper.selectThresholdByDprtcode(monitorsettings.getDprtcode());
		
			for (Monitorsettings monitorsettingsOld : monitorsettingsListOld) {
				if("-1".equals(monitorsettingsOld.getDprtcode())){
					for (Monitorsettings monitorsettingsNew : monitorsettingsListNew) {
						if(monitorsettingsOld.getKey().equals(monitorsettingsNew.getKey())){
//							if("GDGS".equals(monitorsettingsOld.getKey()) || "YXX_YPJH_YXQ".equals(monitorsettingsOld.getKey())){
//								
//								if(monitorsettingsOld.getYjtsJb1().intValue()!=monitorsettingsNew.getYjtsJb1().intValue()
//										){
//									monitorsettingsMapper.insertSelective(monitorsettingsNew);
//								}
//							}else 
							if("PXTX".equals(monitorsettingsOld.getKey())){
								if(monitorsettingsOld.getYjtsJb1().intValue()!=monitorsettingsNew.getYjtsJb1().intValue()
										|| monitorsettingsOld.getYjtsJb2().intValue()!=monitorsettingsNew.getYjtsJb2().intValue()
										|| monitorsettingsOld.getYjtsJb3().intValue()!=monitorsettingsNew.getYjtsJb3().intValue()){
									monitorsettingsMapper.insertSelective(monitorsettingsNew);
								}
							}else{
								if(monitorsettingsOld.getYjtsJb1().intValue()!=monitorsettingsNew.getYjtsJb1().intValue()
										|| monitorsettingsOld.getYjtsJb2().intValue()!=monitorsettingsNew.getYjtsJb2().intValue()
										){
									monitorsettingsMapper.insertSelective(monitorsettingsNew);
								}
							}
							
							
	
							break;
						}
					}
				}else{
					for (Monitorsettings monitorsettingsNew : monitorsettingsListNew) {
						if(monitorsettingsOld.getDprtcode().equals(monitorsettingsNew.getDprtcode()) && monitorsettingsNew.getKey().equals(monitorsettingsOld.getKey())){
							monitorsettingsMapper.updateByKeyandDprtcode(monitorsettingsNew);
							break;
						}
					}
				}
				
				
			}
		}
		
		//更新保存,子类型
		this.updateSubTypes(monitorsettings);
		
	}
	
	/**
	 * 更新保存,子类型
	 * @Description 
	 * @CreateTime 2018-4-13 上午10:27:18
	 * @CreateBy 雷伟
	 * @param monitorsettings
	 */
	private void updateSubTypes(Monitorsettings monitorsettings) {
		
		List<Monitorsettings> delSubTypes = new ArrayList<Monitorsettings>(); //需要删除的，如果两个值都为空
		List<Monitorsettings> addSubTypes = new ArrayList<Monitorsettings>(); //需要新增的，在t_threshold表不存在
		List<Monitorsettings> updateSubTypes = new ArrayList<Monitorsettings>(); //需要更新的，在t_threshold表存在
		
		List<Monitorsettings> subTypes = monitorsettings.getSubTypeList();
		for (int i = 0; null != subTypes && i < subTypes.size(); i++) {
			Monitorsettings obj = subTypes.get(i);
			if (null == obj.getYjtsJb1() && null == obj.getYjtsJb2()) {
				delSubTypes.add(obj);
			} else {
				Monitorsettings existObj = monitorsettingsMapper.selectThresholdByKeyandDprtcode(obj);//判断是否存在
				if (null != existObj && null != existObj.getDprtcode()) {
					updateSubTypes.add(obj);
				} else {
					addSubTypes.add(obj);
				}
			}
		}
		
		//删除
		if(null != delSubTypes && delSubTypes.size() > 0){
			monitorsettingsMapper.delete4Batch(delSubTypes);
		}

		//新增
		if(null != addSubTypes && addSubTypes.size() > 0){
			monitorsettingsMapper.insert4Batch(addSubTypes);
		}

	    //更新
		if(null != updateSubTypes && updateSubTypes.size() > 0){
			monitorsettingsMapper.update4Batch(updateSubTypes);
		}
		
	}
	
	/**
	 * 获取监控预警阈值
	 * @param key 预警编码
	 * @param dprtcode 组织机构
	 */
	public Monitorsettings getByKeyDprtcode(String key, String dprtcode) throws BusinessException {
		Monitorsettings monitorsettings=new Monitorsettings();
		monitorsettings.setKey(key);
		monitorsettings.setDprtcode(dprtcode);
		
		Monitorsettings mtss = monitorsettingsMapper.selectThresholdByKeyandDprtcode(monitorsettings);
		if(mtss==null){
			monitorsettings.setDprtcode("-1");
			Monitorsettings mts = monitorsettingsMapper.selectThresholdByKeyandDprtcode(monitorsettings);
			return mts;
		}
		
		return mtss;
	}
	@Override
	public Map<String,Monitorsettings> getByKeyInDprtcodes(String key, List<String> dprtcodes) {
		Map<String,Monitorsettings> map = new HashMap<String,Monitorsettings>();
		Monitorsettings monitorsettings=new Monitorsettings();
		monitorsettings.setKey(key);
		monitorsettings.getParamsMap().put("dprtcodes", dprtcodes);
		List<Monitorsettings> list = monitorsettingsMapper.selectThresholdByKeyandDprtcodes(monitorsettings);
		
		if(list==null || list.isEmpty()){
			monitorsettings.setDprtcode("-1");
			Monitorsettings mts = monitorsettingsMapper.selectThresholdByKeyandDprtcode(monitorsettings);
			if (mts!=null) {
				for (String dprtcode : dprtcodes) {
					map.put(dprtcode, mts);
				}
			}
		}
		else {
			for (Monitorsettings item : list) {
				map.put(item.getDprtcode(), item);
			}
		}
		
		return map;
	}
	
	/**
	 * 获取适航性资料子类型
	 */
	@Override
	public List<Monitorsettings> queryShxzlSubTypeByDprtcode(String dprtcode) throws BusinessException {
		List<Monitorsettings> subTypeList = monitorsettingsMapper.selectShxzlSubTypeByDprtcode(dprtcode);
		return subTypeList;
	}
	
}
