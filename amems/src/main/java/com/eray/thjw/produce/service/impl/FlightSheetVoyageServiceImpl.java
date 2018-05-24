package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.FlightSheetVoyageMapper;
import com.eray.thjw.produce.po.FlightSheet;
import com.eray.thjw.produce.po.FlightSheetVoyage;
import com.eray.thjw.produce.service.FlightSheetVoyageService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 飞行记录本 - 航次数据service实现类
 * @CreateTime 2017年10月25日 下午2:35:36
 * @CreateBy 韩武
 */
@Service
public class FlightSheetVoyageServiceImpl implements FlightSheetVoyageService {

	@Resource
	private FlightSheetVoyageMapper flightSheetVoyageMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * 
	 * @Description 发动机监控数据
	 * @CreateTime 2017年10月14日 上午10:40:57
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getMonitorData(BaseEntity baseEntity) {
		int fdjsl =0;
		if(baseEntity.getParamsMap().get("fdjsl")!=null){
			fdjsl= (Integer) baseEntity.getParamsMap().get("fdjsl");
		}
		//组装发动机监控数据
		if(fdjsl>0){
			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
			for (int i = 1; i <= fdjsl; i++) {
				baseEntity.getParamsMap().put("fdj", i);
				resultList.add(doData(baseEntity));	
			}
			return resultList;
		}else{
			return null;
		}
	}
	/**
	 * 
	 * @Description 获取数据
	 * @CreateTime 2017年10月14日 上午10:40:33
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 */
	private Map<String, Object> doData(BaseEntity baseEntity){
		// 获取日期内发动机滑油数据
		List<Map<String, Object>> list1 = flightSheetVoyageMapper.select4MointorDate(baseEntity);
		// 获取飞机下所有1#发动机滑油数据
		List<Map<String, Object>> list2 = flightSheetVoyageMapper.select4MointorFj(baseEntity);	
		Map<String,Object> resultMap = new HashMap<String,Object>();// 返回值
		List<Map<String,Object>> shmap= new ArrayList<Map<String,Object>>();//上行对象集合
		List<Map<String,Object>> xhmap= new ArrayList<Map<String,Object>>();//下行对象集合
		List<Object> xz= new ArrayList<Object>();//X轴
		List<Object> hyList=new ArrayList<Object>();//发动机滑油添加量
		List<Object> flbIdList = new ArrayList<Object>();//飞行记录表id集合
		if (list1 != null && list1.size() > 0) {
			Integer qys = SysContext.QYS;// 滑油监控取样数
			List<Map<String, Object>> qyData = null;
			for (Map<String, Object> map1 : list1) {
				int mark = -1;// 标记匹配位置
				qyData = new ArrayList<Map<String, Object>>();// 取样数据
				for (int i = 0; i < list2.size(); i++) {
					if (mark != -1 && i < (mark + qys) && i != 0 ) {
						qyData.add(list2.get(i));
					}
					if (map1.get("FXLLID").equals(list2.get(i).get("FXLLID"))) {
						qyData.add(list2.get(i));
						mark = i;
					} 
				}
				// 处理取样数
				Map<String,Object> sxObj = null;// 上行图像点
				Map<String,Object> xhObj = null;// 下行图像点
				Double xhllj = 0.000;// 消耗率累计
				Double fxsjlj = 0.000;// 飞行时间累计
				Double hytjllj = 0.000;// 滑油添加量累计
				if (qyData != null && qyData.size() > 0) {
					int sjqys = 0;// 实际取样数
					for (Map<String, Object> map : qyData) {
						xhllj += ((BigDecimal) map.get("HYXHL")).doubleValue();
						fxsjlj += ((BigDecimal) map.get("LJ_FXSJ")).doubleValue();
						hytjllj += ((BigDecimal) map.get("HYTJL")).doubleValue();
						sjqys += 1;
					}
					sxObj =new HashMap<String, Object>();
					xhObj = new HashMap<String, Object>();
					xhllj = sjqys == 0 ? 0 : (xhllj / sjqys);
					DecimalFormat    df   = new DecimalFormat("######0.000");
					sxObj.put("hyxhx", df.format(xhllj));
					sxObj.put("sjqys", sjqys);
					shmap.add(sxObj);
					xhObj.put("hytjllj", df.format(hytjllj/(fxsjlj/60)));						
					xhmap.add(xhObj);
				}
				xz.add(map1.get("FXRQ"));
				hyList.add(map1.get("HYTJL"));
				flbIdList.add(map1.get("FLBID"));
			}
		}
		resultMap.put("sh", shmap);
		resultMap.put("xh", xhmap);
		resultMap.put("xz", xz);
		resultMap.put("hy", hyList);
		resultMap.put("flb", flbIdList);
		return resultMap;
	}
	/**
	 * 
	 * @Description APU监控数据
	 * @CreateTime 2017年10月14日 上午10:41:08
	 * @CreateBy 岳彬彬
	 * @param baseEntity
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getApuData(BaseEntity baseEntity) {
		//组装发动机监控数据
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();			
		baseEntity.getParamsMap().put("fdj", 5);
		resultList.add(doData(baseEntity));			
		return resultList;
	}
	
	/**
	 * @Description 保存飞行记录本 - 航次数据
	 * @CreateTime 2017年10月25日 上午10:57:48
	 * @CreateBy 韩武
	 * @param record
	 */
	@Override
	public void save(FlightSheet record) {
		
		// 删除飞行记录本 - 航次数据
		deleteNotExist(record);
		
		User user = ThreadVarUtil.getUser();
		for (FlightSheetVoyage voyage : record.getFlightSheetVoyageList()) {
			voyage.setMainid(record.getId());
			voyage.setCzls(record.getCzls());
			voyage.setLogOperationEnum(record.getLogOperationEnum());
			voyage.setWhrid(user.getId());
			voyage.setWhsj(new Date());
			voyage.setWhdwid(user.getBmdm());
			voyage.setZt(EffectiveEnum.YES.getId());
			
			if (StringUtils.isBlank(voyage.getId())){	// 新增航次数据
				voyage.setId(UUID.randomUUID().toString());
				flightSheetVoyageMapper.insertSelective(voyage);
				// 保存历史记录信息
				commonRecService.write(voyage.getId(), TableEnum.B_S2_00601, user.getId(), 
						record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.SAVE, record.getId());
				
			} else {	// 修改航次数据
				flightSheetVoyageMapper.updateByPrimaryKey(voyage);
				// 保存历史记录信息
				commonRecService.write(voyage.getId(), TableEnum.B_S2_00601, user.getId(), 
						record.getCzls() , record.getLogOperationEnum(), UpdateTypeEnum.UPDATE, record.getId());
			}
		}
	}
	
	/**
	 * @Description 删除飞行记录本 - 航次数据
	 * @CreateTime 2017年10月25日 上午11:30:26
	 * @CreateBy 韩武
	 * @param record
	 */
	private void deleteNotExist(FlightSheet record){
		
		User user = ThreadVarUtil.getUser();
		
		// 插入日志
		StringBuilder base = new StringBuilder("b.mainid = '"+record.getId()+"' ");
		StringBuilder param = new StringBuilder();
		boolean hasRecord = false;
		if(!record.getFlightSheetVoyageList().isEmpty()){
			for (FlightSheetVoyage voyage : record.getFlightSheetVoyageList()) {
				if(StringUtils.isNotBlank(voyage.getId())){
					param.append("'").append(voyage.getId()).append("',");
					hasRecord = true;
				}
			}
		}
		if(hasRecord){
			param.deleteCharAt(param.lastIndexOf(","));
			param.append(")");
			base.append("and b.id not in (").append(param);
		}
		commonRecService.writeByWhere(base.toString(), TableEnum.B_S2_00601, user.getId(), record.getCzls(),
				record.getLogOperationEnum(), UpdateTypeEnum.DELETE, record.getId());
		// 删除数据
		flightSheetVoyageMapper.deleteNotExist(record);
	}
}