package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.RevisionNoticeBookMapper;
import com.eray.thjw.dao.WorkOrderMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.RevisionNoticeBook;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.EngineeringCenterService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.TechnicalFileService;

import enu.DictateTypeEnum;
import enu.ThresholdEnum;

/**
 * @author liub
 * @description 工程中心service,用于业务逻辑处理
 * @develop date 2017.03.14
 */
@Service
public class EngineeringCenterServiceImpl implements EngineeringCenterService {
    
	/**
	 * @author liub
	 * @description 技术文件service
	 * @develop date 2017.03.14
	 */
	@Resource
	private TechnicalFileService technicalFileService;
	
	/**
	 * @author liub
	 * @description 下达指令service
	 * @develop date 2017.03.14
	 */
	@Resource
	private OrderSourceService orderSourceService;
	
	/**
	 * @author liub
	 * @description 系统阀值设置 service
	 * @develop date 2017.03.15
	 */
	@Resource
	private MonitorsettingsService monitorsettingsService;
	
	/**
	 * @author liub
	 * @description 工程指令 mapper
	 * @develop date 2017.03.17
	 */
	@Resource
	private WorkOrderMapper workOrderMapper; 
	
	/**
	 * @author liub
	 * @description 修订通知书 mapper
	 */
	@Resource
	private RevisionNoticeBookMapper revisionNoticeBookMapper;

	/**
	 * @author liub
	 * @description 根据评估单id查询下达指令数据
	 * @param id
	 * @return List<Map<String, Object>>
	 * @develop date 2017.03.14
	 */
	@Override
	public List<Map<String, Object>> queryByPgdId(String id) throws BusinessException {
		int type = 0;
		Map<String, Object> stateMap = new HashMap<String, Object>();//树形菜单展开或收缩
		stateMap.put("opened", false);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();//返回结果集
		Map<Integer, Integer> typeMap = new HashMap<Integer, Integer>();//指令类型数量
		Map<String, Integer> gczlMap = new HashMap<String, Integer>();//工程指令下EO工单数量集合
		List<String> gczlIdList = new ArrayList<String>();//工程指令id集合
		List<String> flxgdIdList = new ArrayList<String>();//非例行工单id集合
		List<String> xdtzsIdList = new ArrayList<String>();//修订通知书id集合
		TechnicalFile technicalFile = technicalFileService.selectByPrimaryKey(id);
		List<OrderSource> orderSourceList = orderSourceService.queryByPgdId(id);
		//系统阀值设置集合
		Map<Integer, Monitorsettings> thresholdMap = new HashMap<Integer, Monitorsettings>();
		thresholdMap.put(1, monitorsettingsService.getByKeyDprtcode(ThresholdEnum.TGZL.getName(),technicalFile.getJgdm()));
		Monitorsettings monitorsettings = monitorsettingsService.getByKeyDprtcode(ThresholdEnum.GDXD.getName(),technicalFile.getJgdm());
		thresholdMap.put(3, monitorsettings);
		thresholdMap.put(8, monitorsettings);
		thresholdMap.put(9, monitorsettings);
		//添加EO工单
		if(null != technicalFile.getIsGczl() && technicalFile.getIsGczl() == 1){
			for (OrderSource orderSource : orderSourceList) {
				if(DictateTypeEnum.ENGINEERING_ORDER.getId().intValue() == orderSource.getZlxl().intValue()){
					gczlIdList.add(orderSource.getZlid());
				}
			}
			if(gczlIdList.size() > 0){
				type = DictateTypeEnum.ENGINEERING_ORDER.getId();
				List<WorkOrder> WorkOrderList = workOrderMapper.queryByGczlIdList(gczlIdList);
				for (WorkOrder workOrder : WorkOrderList) {
					addOrderSource(resultList, workOrder.getId(), workOrder.getGdbh(), stateMap, workOrder.getGczlid(), getParamMap(thresholdMap.get(type), type, 1));
					if(null == gczlMap.get(workOrder.getGczlid())){
						gczlMap.put(workOrder.getGczlid(), 1);
					}else{
						gczlMap.put(workOrder.getGczlid(), gczlMap.get(workOrder.getGczlid())+1);
					}
				}
			}
		}
		//添加指令
		for (OrderSource orderSource : orderSourceList) {
			//如果指令是工单,放入工单id集合
			if(DictateTypeEnum.ATTACH_WORK_ORDER.getId().intValue() == orderSource.getZlxl().intValue()){
				flxgdIdList.add(orderSource.getZlid());
				continue;
			}
			//如果指令是修订通知书,放入修订通知书id集合
			if(DictateTypeEnum.AMENDMENT_NOTICE.getId().intValue() == orderSource.getZlxl().intValue()){
				xdtzsIdList.add(orderSource.getZlid());
				continue;
			}
			StringBuffer zlbh = new StringBuffer();
			zlbh.append(orderSource.getZlbh());
			if(gczlMap.get(orderSource.getZlid()) != null){
				zlbh.append("(").append(gczlMap.get(orderSource.getZlid())).append(")");
			}
			addOrderSource(resultList,orderSource.getZlid(),zlbh,stateMap,orderSource.getZlxl(),getParamMap(thresholdMap.get(orderSource.getZlxl()),orderSource.getZlxl(),1));
			if(null == typeMap.get(orderSource.getZlxl())){
				typeMap.put(orderSource.getZlxl(), 1);
			}else{
				typeMap.put(orderSource.getZlxl(), typeMap.get(orderSource.getZlxl())+1);
			}
		}
		//添加非例行工单
		if(flxgdIdList.size() > 0){
			List<WorkOrder> flxgdWorkOrderList = workOrderMapper.queryByIdList(flxgdIdList);
			for (WorkOrder workOrder : flxgdWorkOrderList) {
				addOrderSource(resultList, workOrder.getId(), workOrder.getGdbh(), stateMap, workOrder.getGdzlx(), getParamMap(null, workOrder.getGdzlx(), 1));
				if(null == typeMap.get(workOrder.getGdzlx())){
					typeMap.put(workOrder.getGdzlx(), 1);
				}else{
					typeMap.put(workOrder.getGdzlx(), typeMap.get(workOrder.getGdzlx())+1);
				}
			}
		}
		
		//添加修订通知书
		if(xdtzsIdList.size() > 0){
			List<RevisionNoticeBook> revisionNoticeBookList = revisionNoticeBookMapper.queryByXdtzsIdList(xdtzsIdList);
			for (RevisionNoticeBook revisionNoticeBook : revisionNoticeBookList) {
				addOrderSource(resultList, revisionNoticeBook.getId(), revisionNoticeBook.getJszlh(), stateMap, revisionNoticeBook.getTzslx(), getParamMap(null, revisionNoticeBook.getTzslx(), 1));
				if(null == typeMap.get(revisionNoticeBook.getTzslx())){
					typeMap.put(revisionNoticeBook.getTzslx(), 1);
				}else{
					typeMap.put(revisionNoticeBook.getTzslx(), typeMap.get(revisionNoticeBook.getTzslx())+1);
				}
			}
		}
		
		if(null != technicalFile.getIsJstg() && technicalFile.getIsJstg() == 1){
			type = DictateTypeEnum.TECHNICAL_BULLETIN.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsJszl() && technicalFile.getIsJszl() == 1){
			type = DictateTypeEnum.TECHNICAL_ORDER.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsWxfa() && technicalFile.getIsWxfa() == 1){
			type = DictateTypeEnum.AMENDMENT_NOTICE.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsGczl() && technicalFile.getIsGczl() == 1){
			type = DictateTypeEnum.ENGINEERING_ORDER.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsFjgd() && technicalFile.getIsFjgd() == 1){
			type = DictateTypeEnum.ATTACH_WORK_ORDER.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsFlxgd() && technicalFile.getIsFlxgd() == 1){
			type = DictateTypeEnum.TROUBLESHOOTING_WORK_ORDER.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsZjfj() && technicalFile.getIsZjfj() == 1){
			type = DictateTypeEnum.FLIGHT_DEPT.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsXdmel() && technicalFile.getIsXdmel() == 1){
			type = DictateTypeEnum.AMENDMENT_MEL.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsXdgk() && technicalFile.getIsXdgk() == 1){
			type = DictateTypeEnum.AMENDMENT_CARD.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		if(null != technicalFile.getIsQt() && technicalFile.getIsQt() == 1){
			type = DictateTypeEnum.OTHER.getId();
			addOrderSource(resultList, type, getText(type, typeMap), stateMap, "#", getParamMap(thresholdMap.get(type), type, 0));
		}
		return resultList;
	}
	
	/**
	 * @author liub
	 * @description 新增下达指令
	 * @param resultList,typeMap,type
	 * @develop date 2017.03.17
	 */
	private void addOrderSource(List<Map<String, Object>> resultList,Object id,Object text,Object stateMap,Object parentId,Map<String, Object> paramMap){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("id", id);
		resultMap.put("text", text);
		resultMap.put("state", stateMap);
		resultMap.put("parent", parentId);
		resultMap.put("li_attr", paramMap);
		resultList.add(resultMap);
	}
	
	/**
	 * @author liub
	 * @description 获取属性参数
	 * @param resultList,typeMap,type
	 * @develop date 2017.03.14
	 */
	private Map<String, Object> getParamMap(Monitorsettings monitorsettings,int type, int indexnum){
		Map<String, Object> attrMap = new HashMap<String, Object>();
		attrMap.put("type", type);
		attrMap.put("indexnum", indexnum);
		if(null != monitorsettings){
			attrMap.put("yjtsJb1", monitorsettings.getYjtsJb1());
			attrMap.put("yjtsJb2", monitorsettings.getYjtsJb2());
			attrMap.put("yjtsJb3", monitorsettings.getYjtsJb3());
		}
		return attrMap;
	}
	
	/**
	 * @author liub
	 * @description 获取text
	 * @param type,typeMap
	 * @develop date 2017.03.17
	 */
	private StringBuffer getText(int type, Map<Integer, Integer> typeMap){
		StringBuffer text = new StringBuffer();
		text.append(DictateTypeEnum.getName(type));
		text.append("(");
		text.append((typeMap.get(type)!=null?typeMap.get(type):0));
		text.append(")");
		return text;
	}
	
}
