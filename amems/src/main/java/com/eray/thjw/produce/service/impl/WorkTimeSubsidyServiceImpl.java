package com.eray.thjw.produce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.basic.dao.HourCostMapper;
import com.eray.thjw.basic.dao.PriceLadderMapper;
import com.eray.thjw.basic.po.HourCost;
import com.eray.thjw.basic.po.PriceLadder;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.WorkorderMapper;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.WorkTimeSubsidyService;
import com.eray.thjw.util.ThreadVarUtil;
/**
 * 
 * @Description 工时统计service的实现类
 * @CreateTime 2018年4月4日 上午9:29:14
 * @CreateBy 岳彬彬
 */
@Service
public class WorkTimeSubsidyServiceImpl implements WorkTimeSubsidyService {

	@Resource
	private WorkorderMapper workorderMapper;
	
	@Resource
	private HourCostMapper hourCostMapper;
	
	@Resource
	private PriceLadderMapper priceLadderMapper;

	
	/**
	 * 
	 * @Description 工时补贴统计页面数据
	 * @CreateTime 2018年4月2日 上午10:40:53
	 * @CreateBy 岳彬彬
	 * @param paramObj
	 * @return
	 */
	@Override
	public Map<String, Object> getWorkTimeSubsidy(Workorder record) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object>> wxgsList = workorderMapper.selectWxGs(record);//维修工时
		List<Map<String,Object>> qjgsList = workorderMapper.selectQjgs(record);//清洁工时
		List<HourCost> hourCostList = hourCostMapper.getRecord();//工时单价
		List<PriceLadder> priceLadderList = priceLadderMapper.getRecord();//阶梯价格
		HourCost wx = new  HourCost() ;//维修
		HourCost qj = new  HourCost() ;//清洁
		for (HourCost hourCost2 : hourCostList) {
			resultMap.put("biz", hourCost2.getBiz());
			if("1" .equals(hourCost2.getLx())){//维修工时		
				wx = hourCost2;
				if(null == wx.getDj()){
					resultMap.put("wxgsdj", doGsdj(hourCost2,priceLadderList));//维修工时单价（阶梯价）
				}else{
					resultMap.put("wxgsdj", "("+hourCost2.getBiz()+"/小时):".concat(wx.getDj().toString()));//维修工时单价（单价）
				}
			}else if("2".equals(hourCost2.getLx())){//清洁工时			
				qj = hourCost2;
				if(null == qj.getDj()){
					resultMap.put("qjgsdj", doGsdj(hourCost2,priceLadderList));//清洁工时单价（阶梯价）
				}else{//单价
					resultMap.put("qjgsdj", "("+hourCost2.getBiz()+"/小时):".concat(qj.getDj().toString()));//清洁工时单价（单价）
				}
			}
		}
		doCal(wx,wxgsList,resultMap,"1");//维修
		doCal(qj,qjgsList,resultMap,"2");//清洁
		// 人员补贴汇总数据列表
		doPersonSum(resultMap,priceLadderList,wx,qj);
		resultMap.put("wxgsList", wxgsList);//维修工时明细列表
		resultMap.put("qjgsList", qjgsList);//清洁工时明细列表
		return resultMap;
	}
	/**
	 * 
	 * @Description 处理列表的工时单价
	 * @CreateTime 2018年4月2日 下午3:18:18
	 * @CreateBy 岳彬彬
	 * @param hourCost2
	 * @param priceLadderList
	 * @return
	 */
	private String doGsdj(HourCost hourCost2,List<PriceLadder> priceLadderList){
		StringBuffer sbf = new StringBuffer("("+hourCost2.getBiz()+"/小时):");
			for (PriceLadder priceLadder2 : priceLadderList) {
				if (priceLadder2.getMainid().equals(hourCost2.getId())) {
					if(null != priceLadder2.getJzgs()){
						sbf.append(priceLadder2.getQsgs()).append("-").append(priceLadder2.getJzgs()).append("工时：").append(priceLadder2.getGsdj()).append(",");
					}else{
						sbf.append(priceLadder2.getQsgs()).append("工时").append("以上：").append(priceLadder2.getGsdj());
					}
				}
			}
		return sbf.toString();
	}
	
	
	/**
	 * 
	 * @Description 处理维修和清洁的数据，累计工时
	 * @CreateTime 2018年4月2日 下午12:00:11
	 * @CreateBy 岳彬彬
	 * @param cost
	 * @param list
	 * @param resultMap
	 * @param priceLadderList
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	private void doCal(HourCost cost,List<Map<String,Object>> list,Map<String,Object> resultMap,String type){
		BigDecimal zgs = new BigDecimal(0);//总工时
		BigDecimal zje = new BigDecimal(0);//总金额
		Map<String,Object> sumMap = new LinkedHashMap<String,Object>();//人员汇总集合list
		Map<String,Object> bean;
		for (Map<String, Object> map : list) {
			BigDecimal gs = (BigDecimal) (map.get("GS") == null? new BigDecimal(0) :map.get("GS"));
			zgs = zgs.add(gs);//累计总工时
			if(null != cost && null != cost.getDj()){//工时为单价
				zje = zje.add(cost.getDj().multiply(gs));//价格为单价是累计总金额
			}
			if(!sumMap.isEmpty()){
				if(sumMap.containsKey(map.get("USERID"))){
					bean = (Map<String, Object>) sumMap.get(map.get("USERID"));
					bean.put("gs", ((BigDecimal) bean.get("gs")).add(gs));
				}else{
					bean = new HashMap<String,Object>();
					bean.put("name", map.get("USERNAME"));
					bean.put("id", map.get("USERID"));
					bean.put("zh", map.get("USERNO"));
					bean.put("gs", (BigDecimal) (map.get("GS") == null? new BigDecimal(0) :map.get("GS")));
					bean.put("type", type);
					sumMap.put((String) map.get("USERID"), bean);
				}		
			}else{
				bean = new HashMap<String,Object>();
				bean.put("name", map.get("USERNAME"));
				bean.put("zh", map.get("USERNO"));
				bean.put("id", map.get("USERID"));
				bean.put("gs", (BigDecimal) (map.get("GS") == null? new BigDecimal(0) :map.get("GS")));
				bean.put("type", type);
				sumMap.put((String) map.get("USERID"),bean);
			}
		}
		if("1".equals(type)){
			resultMap.put("wxgsZgs", zgs);//维修工时总工时
			if(null != cost && null != cost.getDj()){
				resultMap.put("wxgsZje",zje);
			}else{//阶梯价不显示总金额
				resultMap.put("wxgsZje","-");	
			}
			resultMap.put("wxBeanList", new ArrayList<Object>(sumMap.values()));//维修的所有数据进行汇总处理（去重数据并累计了工时）
		}else{
			resultMap.put("qjgsZgs", zgs);//清洁工时总工时
			if(null != cost && null != cost.getDj()){
				resultMap.put("qjgsZje",zje);
			}else{//阶梯价不显示总金额
				resultMap.put("qjgsZje","-");	
			}
			resultMap.put("qjBeanList", new ArrayList<Object>(sumMap.values()));//清洁的所有数据进行汇总处理（去重数据并累计了工时）
		}
	}
	/**
	 * 
	 * @Description 处理人员补贴汇总数据列表
	 * @CreateTime 2018年4月8日 下午2:51:29
	 * @CreateBy 岳彬彬
	 * @param resultMap
	 * @param priceLadderList
	 * @param wxjg
	 * @param qjjg
	 */
	@SuppressWarnings("unchecked")
	private void doPersonSum(Map<String, Object> resultMap, List<PriceLadder> priceLadderList, HourCost wxjg,
			HourCost qjjg) {
		List<Object> wxList = (List<Object>) resultMap.get("wxBeanList");// 维修人员汇总集合list（去重数据并累计了工时）
		List<Object> qjList = (List<Object>) resultMap.get("qjBeanList");// 清洁人员汇总集合list（去重数据并累计了工时）
		List<Map<String, Object>> totalList = new ArrayList<Map<String,Object>>();// 人员补贴list
		Map<String, Object> bean;
		if(null != wxList && wxList.size()>0){
			for (Object obj : wxList) {
				Map<String, Object> wx = (Map<String, Object>) obj;
				if(null != qjList &&  qjList.size()>0){
					for (int i = 0; i < qjList.size(); i++) {
						Map<String, Object> qj = (Map<String, Object>) qjList.get(i);
						if (wx.get("id").equals(qj.get("id"))) {
							bean = new HashMap<String, Object>();
							bean.put("name", wx.get("name"));
							bean.put("zh", wx.get("zh"));
							bean.put("id", wx.get("id"));
							BigDecimal wxgs = (BigDecimal) (wx.get("gs") == null ? new BigDecimal(0) : wx.get("gs"));// 维修工时
							BigDecimal qjgs = (BigDecimal) (qj.get("gs") == null ? new BigDecimal(0) : qj.get("gs"));// 清洁工时
							BigDecimal wxzje = new BigDecimal(0);// 维修总价格
							BigDecimal qjzje = new BigDecimal(0);// 清洁总价格
							wxzje = doZje(wxgs, priceLadderList, wxjg);
							qjzje = doZje(qjgs, priceLadderList, qjjg);
							bean.put("zje", wxzje.add(qjzje));
							totalList.add(bean);
							break;
						} else {
							if (i == qjList.size() - 1) {
								bean = new HashMap<String, Object>();
								bean.put("name", wx.get("name"));
								bean.put("zh", wx.get("zh"));
								bean.put("id", wx.get("id"));
								BigDecimal wxgs = (BigDecimal) (wx.get("gs") == null ? new BigDecimal(0) : wx.get("gs"));// 维修工时
								BigDecimal wxzje = new BigDecimal(0);// 维修总价格
								wxzje = doZje(wxgs, priceLadderList, wxjg);
								bean.put("zje", wxzje);
								totalList.add(bean);
							}
						}
					}
				}else{
					bean = new HashMap<String, Object>();
					bean.put("name", wx.get("name"));
					bean.put("zh", wx.get("zh"));
					bean.put("id", wx.get("id"));
					BigDecimal wxgs = (BigDecimal) (wx.get("gs") == null ? new BigDecimal(0) : wx.get("gs"));// 维修工时
					BigDecimal wxzje = new BigDecimal(0);// 维修总价格
					wxzje = doZje(wxgs, priceLadderList, wxjg);
					bean.put("zje", wxzje);
					totalList.add(bean);
				}
			}
		}else{
			if(null != qjList &&  qjList.size()>0){
				for (int i = 0; i < qjList.size(); i++) {
					Map<String, Object> qj = (Map<String, Object>) qjList.get(i);
					bean = new HashMap<String, Object>();
					bean.put("name", qj.get("name"));
					bean.put("zh", qj.get("zh"));
					bean.put("id", qj.get("id"));
					BigDecimal qjgs = (BigDecimal) (qj.get("gs") == null ? new BigDecimal(0) : qj.get("gs"));// 清洁工时
					BigDecimal qjzje = new BigDecimal(0);// 清洁总价格
					qjzje = doZje(qjgs, priceLadderList, wxjg);
					bean.put("zje", qjzje);
					totalList.add(bean);
				}
			}
		}
		//处理防止存在于清洁但不存在于维修的数据
		if(null != wxList && wxList.size()>0 && null != qjList &&  qjList.size()>0){
			List<Map<String, Object>> temp = new ArrayList<Map<String,Object>>();// 人员补贴list
			for (Object obj : qjList) {
				Map<String, Object> qj = (Map<String, Object>) obj;
				for (int i = 0; i < totalList.size(); i++) {
					if(!qj.get("id").equals(totalList.get(i).get("id")) ){
						if(i == totalList.size()-1){
							bean = new HashMap<String, Object>();
							bean.put("name", qj.get("name"));
							bean.put("zh", qj.get("zh"));
							bean.put("id", qj.get("id"));
							BigDecimal qjgs = (BigDecimal) (qj.get("gs") == null ? new BigDecimal(0) : qj.get("gs"));// 清洁工时
							BigDecimal qjzje = new BigDecimal(0);// 清洁总价格
							qjzje = doZje(qjgs, priceLadderList, qjjg);
							bean.put("zje", qjzje);
							temp.add(bean);
						}
					}else{
						break;
					}
				}
			}
			totalList.addAll(temp);
		}
		resultMap.put("totalList", totalList);
	}
	/**
	 * 
	 * @Description 计算总金额
	 * @CreateTime 2018年4月8日 下午2:52:18
	 * @CreateBy 岳彬彬
	 * @param zgs
	 * @param priceLadderList
	 * @param cost
	 * @return
	 */
	private BigDecimal doZje(BigDecimal zgs, List<PriceLadder> priceLadderList, HourCost cost) {
		BigDecimal zje = new BigDecimal(0);// 总价格
		if (null != cost && null != cost.getDj()) {// 单价
			zje = zgs.multiply(cost.getDj());
		} else {// 阶梯价
			PriceLadder record = new PriceLadder();
			for (PriceLadder priceLadder : priceLadderList) {
				if (priceLadder.getMainid().equals(cost.getId()) && null != priceLadder.getJzgs()
						&& !(priceLadder.getJzgs().compareTo(zgs) == -1)) {
					record = priceLadder;
					break;
				} else if (priceLadder.getMainid().equals(cost.getId()) && null == priceLadder.getJzgs()) {
					record = priceLadder;
					break;
				}
			}
			BigDecimal qdje =record.getQdje() == null ? new BigDecimal(0) : record.getQdje();
			BigDecimal gsdj =record.getGsdj() == null ? new BigDecimal(0) : record.getGsdj();
			BigDecimal qsgs =record.getQsgs() == null ? new BigDecimal(0) : record.getQsgs();
			zje = qdje.add((zgs.subtract(qsgs)).multiply(gsdj));
		}
		return zje;
	}
	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年4月3日 下午6:36:01
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	@Override
	public void addRecord(List<HourCost> record) {
		//先删除 
		hourCostMapper.deleteAll();
		priceLadderMapper.deleteAll();
		//后新增
		User user = ThreadVarUtil.getUser();
		for (HourCost hourCost : record) {
			String id = UUID.randomUUID().toString();
			hourCost.setId(id);
			hourCost.setWhrid(user.getId());
			hourCost.setWhsj(new Date());
			hourCost.setDprtcode(user.getJgdm());
			hourCost.setWhbmid(user.getBmdm());
			hourCostMapper.insertSelective(hourCost);
			 List<PriceLadder> ladderList = hourCost.getLadderList();
			 if(null != ladderList && ladderList.size()>0){
				 for (PriceLadder priceLadder : ladderList) {
					 priceLadder.setId(UUID.randomUUID().toString());
					 priceLadder.setMainid(id);
					 priceLadderMapper.insertSelective(priceLadder);
				}
			 }
			 
		}
	}
	/**
	 * 
	 * @Description 获取
	 * @CreateTime 2018年4月4日 上午9:28:28
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Override
	public List<HourCost> getRecord() {
		List<HourCost> hourCostList = hourCostMapper.getRecord();//工时单价
		List<PriceLadder> priceLadderList = priceLadderMapper.getRecord();//阶梯价格
		if(null != hourCostList && hourCostList.size() > 0){
			for (HourCost hourCost : hourCostList) {
				if(null != priceLadderList && priceLadderList.size()>0){
					List<PriceLadder> list = new ArrayList<PriceLadder>();
					for (PriceLadder priceLadder : priceLadderList) {
						if(hourCost.getId().equals(priceLadder.getMainid())){
							list.add(priceLadder);
						}
					}
					hourCost.setLadderList(list);
				}
			}
			return hourCostList;
		}else{
			return null;
		}
	}
}
