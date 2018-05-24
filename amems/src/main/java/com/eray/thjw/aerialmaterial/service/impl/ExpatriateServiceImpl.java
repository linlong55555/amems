package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.ExpatriateMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * 外派清单业务处理
 * @author xu.yong
 *
 */
@Service("expatriateService")
public class ExpatriateServiceImpl implements ExpatriateService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ExpatriateMapper expatriateMapper;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	/**
	 * 查询借出未归还的航材统计信息
	 * @param expatriate
	 * @return
	 */
	public Map<String, Object> queryLendStatisticsPage(Expatriate expatriate){
		PageHelper.startPage(expatriate.getPagination());
		List<Expatriate> list = this.expatriateMapper.queryLendStatisticsPage(expatriate);
		return PageUtil.pack4PageHelper(list, expatriate.getPagination());
	}

	@Override
	public List<Expatriate> queryAllPageList(Expatriate expatriate)
			throws RuntimeException {
		return expatriateMapper.queryAllPageList(expatriate);
	}

	@Override
	public Expatriate querySelectCount(Expatriate expatriate)
			throws RuntimeException {
		
		return expatriateMapper.querySelectCount(expatriate);
	}
	
	/**
	 * 查询单个借出未归还航材
	 * @param dprtcode 组织机构
	 * @param secondmentId 借调对象ID
	 * @param bjId 部件ID
	 * @return
	 */
	public Expatriate querySingleLend(String dprtcode, String secondmentId, String bjId){
		
		Expatriate expatriate = new Expatriate();
		expatriate.setDprtcode(dprtcode);
		expatriate.setWpdxid(secondmentId);
		expatriate.setBjid(bjId);
		
		List<Expatriate> list = expatriateMapper.queryLendStatistics(expatriate);
		if(null != list && !list.isEmpty()){
			if(list.size() == 1){
				return list.get(0);
			}else{
				logger.error("借出未归还航材记录查询错误");
				throw new RuntimeException("借出未归还航材记录查询错误");
			}
		}else{
			logger.error("没有该借出未归还航材记录");
			throw new RuntimeException("没有该借出未归还航材记录");
		}
	}
	
	/**
	 * 
	 * 借出 未归还： 给定数量查询借调对象某部件清单
	 * @param dprtcode 组织机构
	 * @param secondmentId 借调对象
	 * @param bjId 部件ID
	 * @param count 归还数量
	 * @return
	 */
	public List<Expatriate> queryListLend(String dprtcode, String secondmentId, String bjId, BigDecimal count){
		List<Expatriate> returnList = new ArrayList<Expatriate>();
		List<Expatriate> list = this.expatriateMapper.queryLendList(dprtcode, secondmentId, bjId);
		
		BigDecimal tempCount = new BigDecimal(0).setScale(2);
		if(null != list && !list.isEmpty()){
			for (Expatriate expatriate : list) {
				tempCount = tempCount.add(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
				if (tempCount.compareTo(count) == 1) {
					expatriate.setDghsl(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)).subtract(tempCount.subtract(count.setScale(2))));
					returnList.add(expatriate);
					break;
				}else if(tempCount.compareTo(count) == 0) {
					expatriate.setDghsl(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
					returnList.add(expatriate);
					break;
				}else{
					expatriate.setDghsl(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
					returnList.add(expatriate);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<Expatriate> queryLendList(String dprtcode, String wpdxid,
			String bjid) {
		return expatriateMapper.queryLendList(dprtcode, wpdxid, bjid);
	}

	@Override
	public List<Expatriate> queryLendListcheck(String dprtcode, String jddxid,
			String bjid,BigDecimal cks) throws RuntimeException {
		List<Expatriate> returnList = new ArrayList<Expatriate>();

		List<Expatriate> list=expatriateMapper.queryLendListcheck(dprtcode, jddxid, bjid);
		
		if(null != list && !list.isEmpty()){
			for (Expatriate expatriate : list) {
				BigDecimal tempCount = new BigDecimal(0).setScale(2);
				tempCount = tempCount.add(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
				
				if (tempCount.compareTo(cks) == 1) {
					expatriate.setDghsl(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)).subtract(tempCount.subtract(cks.setScale(2))));
					returnList.add(expatriate);

					break;
				}else if(tempCount.compareTo(cks) == 0) {
					expatriate.setDghsl(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
					returnList.add(expatriate);

					break;
				}else{
					expatriate.setDghsl(expatriate.getKcsl().setScale(2).subtract(expatriate.getGhsl().setScale(2)));
					returnList.add(expatriate);

				}
			}
		}
		return returnList;
	}

	@Override
	public void update(Expatriate expatriate) throws RuntimeException {
		
		expatriateMapper.updateByPrimaryKeySelective(expatriate);
	}

	@Override
	public List<Expatriate> queryLendListchecka(String dprtcode, String jddxid,
			String bjid) throws RuntimeException {
		return expatriateMapper.queryLendListcheck(dprtcode, jddxid, bjid);
	}

	@Override
	public Expatriate queryByKey(String wpqdid) throws RuntimeException {
		
		return expatriateMapper.queryByKey(wpqdid);
	}

	@Override
	public List<Expatriate> queryAllPageListjie(Expatriate expatriate)
			throws RuntimeException {
		return expatriateMapper.queryAllPageListjie(expatriate);
	}

	@Override
	public List<Expatriate> queryAllPageListhai(Expatriate expatriate)
			throws RuntimeException {
		return expatriateMapper.queryAllPageListhai(expatriate);
	}

	/**
	 * 根据归还航材 分配 借出记录
	 * @param secondmentId
	 * @param bjs
	 * @return
	 * @throws BusinessException 
	 */
	public List<Expatriate> getLendByReturn(String dprtcode, String secondmentId, List<Map<String, Object>> bjList) throws BusinessException{
		Map<String, Object> paramsMap = new HashMap<String, Object>();

		List<String> bjIdList = new ArrayList<String>();
		List<String> bjhList = new ArrayList<String>();
		for (Map<String, Object> map : bjList) {
			if(map.containsKey("bjid")){
				bjIdList.add((String)map.get("bjid"));
			}else if(map.containsKey("bjh")){
				bjhList.add((String)map.get("bjh"));
			}
		}
		if(bjIdList.isEmpty() && bjhList.isEmpty()){
			throw new BusinessException("部件不存在");
		}
		if(!bjIdList.isEmpty()){
			paramsMap.put("bjIdList", bjIdList);
		}
		if(!bjhList.isEmpty()){
			paramsMap.put("bjhList", bjhList);
		}
		paramsMap.put("dprtcode", dprtcode);

		List<HCMainData> hCMainDataList = hCMainDataMapper.selectBybjs(paramsMap);
		
		if(hCMainDataList == null || hCMainDataList.isEmpty()){
			throw new BusinessException("部件不存在，请选择借出航材");
		}
		
		Map<String, String> bjhBjidMap = new HashMap<String, String>(hCMainDataList.size());
		bjIdList = new ArrayList<String>();
		for (HCMainData hcMainData : hCMainDataList) {
			bjIdList.add(hcMainData.getId());
			bjhBjidMap.put(hcMainData.getBjh(), hcMainData.getId());
		}
		
		//计算各部件的数量
		Map<String, BigDecimal> bjSlMap = new HashMap<String, BigDecimal>();
		for (Map<String, Object> map : bjList) {
			if(map.containsKey("bjid")){
				String bjid = (String)map.get("bjid");
				if(bjSlMap.containsKey(bjid)){
					bjSlMap.put(bjid, bjSlMap.get(bjid).add(new BigDecimal((String)map.get("kcsl"))).setScale(2));
				}else{
					bjSlMap.put(bjid, new BigDecimal((String)map.get("kcsl")).setScale(2));
				}
			}else if(map.containsKey("bjh")){
				if(bjhBjidMap.containsKey((String)map.get("bjh"))){
					String bjid = bjhBjidMap.get((String)map.get("bjh"));
					if(bjSlMap.containsKey(bjid)){
						bjSlMap.put(bjid, bjSlMap.get(bjid).add(new BigDecimal((String)map.get("kcsl"))).setScale(2));
					}else{
						bjSlMap.put(bjid, new BigDecimal((String)map.get("kcsl")).setScale(2));
					}
				}else{
					throw new BusinessException("部件"+map.get("bjh")+"不存在，请选择借出航材");
				}
			}
		}
		
		
		
		paramsMap = new HashMap<String, Object>();
		paramsMap.put("dprtcode", dprtcode);
		paramsMap.put("bjIdList", bjIdList);
		paramsMap.put("wpdxid", secondmentId);
		
		List<Expatriate> list = expatriateMapper.queryLendByBj(paramsMap);
		if(null == list || list.isEmpty()){
			throw new BusinessException("没有借出未归还航材");
		}
		
		List<Expatriate> returnList = new ArrayList<Expatriate>();
		Set<String> bjidSet = bjSlMap.keySet(); 
		for (String bjid : bjidSet) {
			Expatriate expatriateStatics = this.querySingleLend(dprtcode, secondmentId, bjid);
			if(null == expatriateStatics || null == expatriateStatics.getDghsl() 
					|| expatriateStatics.getDghsl().compareTo(bjSlMap.get(bjid)) < 0){
				throw new BusinessException("借出未归还航材数量少于入库数量");
			}
			returnList.addAll(this.queryListLend(dprtcode, secondmentId, bjid, bjSlMap.get(bjid)));
		}
		return returnList;
	}
	
}
