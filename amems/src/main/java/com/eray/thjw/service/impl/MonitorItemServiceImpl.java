package com.eray.thjw.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.MonitorItemMapper;
import com.eray.thjw.po.MonitorItem;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.MonitorItemService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 维修方案service,用于业务逻辑处理
 * @develop date 2016.07.25
 */
@Service
public class MonitorItemServiceImpl implements MonitorItemService {
	
	/**
	 * @author liub
	 * @description 监控项目Mapper
	 * @develop date 2016.08.24
	 */
	@Autowired
	private MonitorItemMapper monitorItemMapper;
	
	/**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	/**
	 * @author liub
	 * @description 根据定检项目id查询监控项目数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<Map<String, Object>> queryListByDjxmid(String djxmid) throws RuntimeException{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<MonitorItem> items = monitorItemMapper.queryListByDjxmid(djxmid);
		for (MonitorItem item : items) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.getId());
			map.put("djxmid", item.getDjxmid());
			map.put("jklbh", item.getJklbh());
			map.put("jkflbh", item.getJkflbh());
			map.put("zqz", item.getZqz());
			map.put("dw", item.getDw());
			map.put("zt", item.getZt());
			list.add(map);
		}
		return list;
	}

	/**
	 * @author liub
	 * @description 根据定检编号删除监控项目
	 * @param 定检编号djbh
	 * @develop date 2016.08.24
	 */
	@Override
	public void deleteByDjxmid(String djxmid) throws RuntimeException{
		monitorItemMapper.deleteByDjxmid(djxmid);
	}
	
	/**
	 * @author liub
	 * @description 新增定检监控项目集合
	 * @param monitorItemList,djxmid
	 * @develop date 2016.08.24
	 */
	@Override
	public void addMonitorItemList(List<MonitorItem> monitorItemList , String djxmid,String czls, String zdid, String dprtcode)throws RuntimeException {
		User user = ThreadVarUtil.getUser();
		List<String> columnValueList = new ArrayList<String>();
		for (MonitorItem monitorItem : monitorItemList) {
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();
			monitorItem.setId(id);
			monitorItem.setDjxmid(djxmid);
			monitorItem.setZt(DelStatus.TAKE_EFFECT.getId());
			monitorItem.setDprtcode(dprtcode);
			monitorItem.setWhrid(user.getId());
			monitorItem.setWhbmid(user.getBmdm());
			monitorItemMapper.insertSelective(monitorItem);
			columnValueList.add(id);
		}
		if(columnValueList.size() > 0){
			commonRecService.write("id", columnValueList, TableEnum.B_G_01201, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE , zdid);//插入定检监控项目日志
		}
	}
	
	/**
	 * @author liub
	 * @description 修改定检监控项目集合
	 * @param monitorItemList,djxmid
	 * @develop date 2016.08.29
	 */
	@Override
	public void editMonitorItemList(List<MonitorItem> monitorItemList , String djxmid,String czls,String zdid, String dprtcode)throws RuntimeException {

		//monitorItemMapper.updateByDjxmid(djxmid);
		List<String> oldIdList = new ArrayList<String>();
		List<String> idList = new ArrayList<String>();
		List<MonitorItem> oldMonitorItemList = monitorItemMapper.queryListByDjxmid(djxmid);
		for (MonitorItem monitorItem : oldMonitorItemList) {
			oldIdList.add(monitorItem.getId());
		}
		User user = ThreadVarUtil.getUser();
		for (MonitorItem monitorItem : monitorItemList) {
			MonitorItem item = monitorItemMapper.selectByDjxmidAndJklAndJkf(djxmid, monitorItem.getJklbh(), monitorItem.getJkflbh());
			if(item != null){
				idList.add(item.getId());
				monitorItem.setId(item.getId());
				monitorItem.setZt(DelStatus.TAKE_EFFECT.getId());
				monitorItem.setDprtcode(dprtcode);
				monitorItem.setWhrid(user.getId());
				monitorItem.setWhbmid(user.getBmdm());
				monitorItemMapper.updateByPrimaryKeySelective(monitorItem);
				if(!oldIdList.contains(item.getId())){
					commonRecService.write(item.getId(), TableEnum.B_G_01201, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//插入定检监控项目日志
				}else{
					commonRecService.write(item.getId(), TableEnum.B_G_01201, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, zdid);//插入定检监控项目日志
				}
			}else{
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				monitorItem.setId(id);
				monitorItem.setDjxmid(djxmid);
				monitorItem.setZt(DelStatus.TAKE_EFFECT.getId());
				monitorItem.setDprtcode(dprtcode);
				monitorItem.setWhrid(user.getId());
				monitorItem.setWhbmid(user.getBmdm());
				
				monitorItemMapper.insertSelective(monitorItem);
				commonRecService.write(id, TableEnum.B_G_01201, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//插入定检监控项目日志
			}
		}
		for(String id : oldIdList){
			if(!idList.contains(id)){
				monitorItemMapper.updateNotEffById(id);
				commonRecService.write(id, TableEnum.B_G_01201, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, zdid);//插入定检监控项目日志
			}
		}
	}
	
}
