package com.eray.thjw.airportensure.service.impl;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.airportensure.dao.FuelupMapper;
import com.eray.thjw.airportensure.po.Fuelup;
import com.eray.thjw.airportensure.service.FuelupService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class FuelupServiceImpl implements FuelupService {
	
	@Resource
	private FuelupMapper fuelupMapper;

	@Resource
	private SaibongUtilService saibongUtilService;     
	
	@Resource
	private PlaneModelDataService planeModelDataService;   
	
	@Resource
	private PlaneDataService planeDataService;
	
	@Resource
	private CommonRecService commonRecService;
	@Override
	public List<Fuelup> queryAllPageList(Fuelup fuelup) throws RuntimeException {
		return fuelupMapper.queryAllPageList(fuelup);
	}

	@Override
	public void save(Fuelup fuelup) throws RuntimeException, BusinessException {
		
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		fuelup.setId(uuid.toString());
		String jydjbh ;	
		try {
			jydjbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JCBZJY.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		
		if(fuelup.getFjzch()==null){
			fuelup.setFjjx("");
		}else{
			PlaneData planeData=new PlaneData();
			planeData.setFjzch(fuelup.getFjzch());
			planeData.setDprtcode(user.getJgdm());
			
			List<String> list = new ArrayList<String>();
			list.add(fuelup.getFjzch());
			planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), user.getJgdm(), list);
			
			planeData=planeDataService.selectByPrimaryKey(planeData);
			fuelup.setFjjx(planeData.getFjjx());
		}

		fuelup.setFjjydbh(jydjbh);
		fuelup.setDprtcode(user.getJgdm());
		fuelup.setWhsj(new Date());
		fuelup.setWhrid(user.getId());
		fuelup.setWhbmid(user.getBmdm());
		fuelup.setZt(1);
		fuelupMapper.insertSelective(fuelup);
		String czls = UUID.randomUUID().toString();
		commonRecService.write(uuid.toString(), TableEnum.B_J_003, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,uuid.toString());
	}

	@Override
	public void update(Fuelup fuelup) throws RuntimeException, BusinessException {
		User user = ThreadVarUtil.getUser();
		PlaneData planeData=new PlaneData();
		planeData.setFjzch(fuelup.getFjzch());
		planeData.setDprtcode(user.getJgdm());
		List<String> list = new ArrayList<String>();
		list.add(fuelup.getFjzch());
		planeModelDataService.existsAircraft4Expt(user.getId(), user.getUserType(), user.getJgdm(), list);
		planeData=planeDataService.selectByPrimaryKey(planeData);
		fuelup.setFjjx(planeData.getFjjx());
		fuelupMapper.updateByPrimaryKeySelective(fuelup);
		
		String czls = UUID.randomUUID().toString();
		commonRecService.write(fuelup.getId(), TableEnum.B_J_003, user.getId(),czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,fuelup.getId());
	}

	@Override
	public void cancel(String id) throws RuntimeException {
		fuelupMapper.delete(id);
		
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		commonRecService.write(id, TableEnum.B_J_003, user.getId(),czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,id);
	}

	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 飞机加油单明细列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	public Map<String, Object> queryfuelupDetailList(Fuelup fuelup)
			throws RuntimeException {
		Map <String, Object>map=new TreeMap<String, Object>();
		//查询飞机注册号
		List<String> fjzchList=fuelupMapper.queryAllFjzch(fuelup);
		
		//查询所有加油单
		List<Fuelup> fuelupDetailList=fuelupMapper.queryfuelupDetailList(fuelup);
		
		Map<String,List> mp=new TreeMap<String, List>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (Fuelup flp : fuelupDetailList) {
			String date=formatter.format(flp.getJyrq());
			if(mp.containsKey(date)){
				List<Map> zchlist=mp.get(date);
				boolean b=true;
				for (Map zchmap : zchlist) {
					if(!zchmap.containsKey(flp.getFjzch())){
						if(zchmap.get("code").equals(flp.getJd())){
							zchmap.put(flp.getFjzch(), flp.getSjjysl());
							b=false;
							break;
						}
					}
				}
				if(b){
					Map<String, String> zcmap=new HashMap<String, String>();
					zcmap.put(flp.getFjzch(), String.valueOf(flp.getSjjysl()));
					zcmap.put("code", flp.getJd());
					zcmap.put("jdms", flp.getJdms());
					zcmap.put("date", date);
					zchlist.add(zcmap);
					
				}
				
			}else{
				List<Map> zchlist=new ArrayList<Map>();
				Map<String, String> zcmap=new HashMap<String, String>();
				zcmap.put(flp.getFjzch(), String.valueOf(flp.getSjjysl()));
				zcmap.put("code", flp.getJd());
				zcmap.put("jdms", flp.getJdms());
				zcmap.put("date", date);
				zchlist.add(zcmap);
				mp.put(date, zchlist);
			}
		}
		map.put("value", mp);
		map.put("title", fjzchList);
		/*//查询总条数
		int total=fuelupMapper.queryfuelupDetailListCount(fuelup);
		
		String[][] arr = new String[fuelupDetailList.size()+1][fjzchList.size()+1];
		for(int i = 0 ; i < fuelupDetailList.size() ; i++){
			if(i == 0){
				for(int j = 0 ; j < fjzchList.size() ; j++){
					if(j==0){
						arr[i][j] = "日期/飞机注册号";
					}
						arr[i][j+1] = fjzchList.get(j);
				}
			}
				for(int j = 0 ; j < fjzchList.size() ; j++){
					if(j==0){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						arr[i+1][j] = sdf.format(fuelupDetailList.get(i).getJyrq());
					}
					System.out.println(arr[0][j+1] +"     "+fuelupDetailList.get(i).getFjzch());
					if(arr[0][j+1].equals( fuelupDetailList.get(i).getFjzch())){
						
						arr[i+1][j+1] = String.valueOf(""+fuelupDetailList.get(i).getSjjysl());
					}else{
						arr[i+1][j+1] = "";	
					}
					
				}
		}
		map.put("fuelupDetailList", arr);
		map.put("total", total);
		map.put("pageable", fuelup.getPagination());*/
		
		
		return map;
	}

	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 发油人加油单明细列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	public Map<String, Object> queryfuelupFyrDetailList(Fuelup fuelup)
			throws RuntimeException {
		Map <String, Object>map=new TreeMap<String, Object>();
		//查询飞机加油人
		List<String> fjzchList=fuelupMapper.queryAllFyr(fuelup);
		
		//查询所有加油单
		List<Fuelup> fuelupDetailList=fuelupMapper.queryfuelupDetailList(fuelup);
		
		Map<String,List> mp=new TreeMap<String, List>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		for (Fuelup flp : fuelupDetailList) {
			String date=formatter.format(flp.getJyrq());
			if(mp.containsKey(date)){
				List<Map> zchlist=mp.get(date);
				boolean b=true;
				for (Map zchmap : zchlist) {
					if(!zchmap.containsKey(flp.getFyr())){
						if(zchmap.get("code").equals(flp.getJd())){
							zchmap.put(flp.getFyr(), flp.getSjjysl());
							b=false;
							break;
						}
					}
				}
				if(b){
					Map<String, String> zcmap=new HashMap<String, String>();
					zcmap.put(flp.getFyr(), String.valueOf(flp.getSjjysl()));
					zcmap.put("code", flp.getJd());
					zcmap.put("jdms", flp.getJdms());
					zcmap.put("date", date);
					zchlist.add(zcmap);
					
				}
				
			}else{
				List<Map> zchlist=new ArrayList<Map>();
				Map<String, String> zcmap=new HashMap<String, String>();
				zcmap.put(flp.getFyr(), String.valueOf(flp.getSjjysl()));
				zcmap.put("code", flp.getJd());
				zcmap.put("jdms", flp.getJdms());
				zcmap.put("date", date);
				zchlist.add(zcmap);
				mp.put(date, zchlist);
			}
		}
		map.put("value", mp);
		map.put("title", fjzchList);
		
		
		return map;
		
		
		
		
		
		
		
		
		
		
			/*	Map <String, Object>map=new HashMap<String, Object>();
				//查询飞机注册号
				List<String> fjzchList=fuelupMapper.queryAllFyr(fuelup.getDprtcode());
				
				//查询所有加油单
				List<Fuelup> fuelupDetailList=fuelupMapper.queryfuelupDetailList(fuelup);
				
				//查询总条数
				int total=fuelupMapper.queryfuelupDetailListCount(fuelup);
				
				String[][] arr = new String[fuelupDetailList.size()+1][fjzchList.size()+1];
				for(int i = 0 ; i < fuelupDetailList.size() ; i++){
					if(i == 0){
						for(int j = 0 ; j < fjzchList.size() ; j++){
							if(j==0){
								arr[i][j] = "日期/发油人";
							}
								arr[i][j+1] = fjzchList.get(j);
						}
					}
						for(int j = 0 ; j < fjzchList.size() ; j++){
							if(j==0){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								arr[i+1][j] = sdf.format(fuelupDetailList.get(i).getJyrq());
							}
							System.out.println(arr[0][j+1] +"     "+fuelupDetailList.get(i).getFyr());
							if(fuelupDetailList.get(i).getFyr().equals(arr[0][j+1])){
								
								arr[i+1][j+1] = String.valueOf(""+fuelupDetailList.get(i).getSjjysl());
							}else{
								arr[i+1][j+1] = "";	
							}
							
						}
				}
				map.put("fuelupDetailList", arr);
				map.put("total", total);
				map.put("pageable", fuelup.getPagination());
		return map;*/
	}
	public Map<String, Object> queryfuelupSummaryList(Fuelup fuelup){
		Map <String, Object>map=new TreeMap<String, Object>();
		//查询飞机注册号
		List<String> fjzchList=fuelupMapper.queryAllFjzch(fuelup);
		
		//查询所有加油单
		List<Fuelup> fuelupSummaryList=fuelupMapper.queryfuelupFjSummaryList(fuelup);
		
		//查询所有加油单基地
		List<Map<String,String>> fuelupJdList=fuelupMapper.queryfuelupJdList(fuelup);
		for (Map<String, String> fue : fuelupJdList) {
			fue.put("zjia", "0");
			for (String fjzch : fjzchList) {
				fue.put(fjzch,"0");
			}
			
		}
		
		Map<String,List> mp=new TreeMap<String, List>();
		for (Fuelup flp : fuelupSummaryList) {
			if(mp.containsKey(flp.getJyyf())){
				List<Map> zchlist=mp.get(flp.getJyyf());
				boolean b=true;
				for (Map zchmap : zchlist) {
					if(!zchmap.containsKey(flp.getFjzch())){
						if(zchmap.get("code").equals(flp.getJd())){
							zchmap.put(flp.getFjzch(), flp.getSjjysl());
							double ypf=Double.valueOf(String.valueOf(zchmap.get("ypfy")));
							double newypfy=BigDecimal.valueOf(ypf).add(BigDecimal.valueOf(flp.getYpfy())).doubleValue();
							zchmap.put("ypfy",newypfy);
							
							double ypz=Double.valueOf(String.valueOf(zchmap.get("ypzl")));
							double newypzl=BigDecimal.valueOf(ypz).add(BigDecimal.valueOf(flp.getYpzl())).doubleValue();
							zchmap.put("ypzl",newypzl);
							
							//zchmap.put("ypzl",Double.valueOf((String) zchmap.get("ypzl"))+Double.valueOf(flp.getYpzl()));
							b=false;
							break;
						}
					}
				}
				if(b){
					Map<String, String> zcmap=new HashMap<String, String>();
					zcmap.put(flp.getFjzch(), String.valueOf(flp.getSjjysl()));
					zcmap.put("code", flp.getJd());
					zcmap.put("jdms", flp.getJdms());
					zcmap.put("date", flp.getJyyf());
					if(zcmap.containsKey("ypfy")){
						Double fy=Double.valueOf(zcmap.get("ypfy"));
						zcmap.put("ypfy", String.valueOf(fy));
					}else{
						zcmap.put("ypfy", String.valueOf(flp.getYpfy()));
					}
					zcmap.put("ypzl", String.valueOf(flp.getYpzl()));
					zchlist.add(zcmap);
					
				}
				
			}else{
				List<Map> zchlist=new ArrayList<Map>();
				Map<String, String> zcmap=new HashMap<String, String>();
				zcmap.put(flp.getFjzch(), String.valueOf(flp.getSjjysl()));
				zcmap.put("code", flp.getJd());
				zcmap.put("jdms", flp.getJdms());
				zcmap.put("date", flp.getJyyf());
				if(zcmap.containsKey("ypfy")){
					Double fy=Double.valueOf(zcmap.get("ypfy"));
					zcmap.put("ypfy", String.valueOf(fy));
				}else{
					zcmap.put("ypfy", String.valueOf(flp.getYpfy()));
				}
				
				
				zcmap.put("ypzl", String.valueOf(flp.getYpzl()));
				zchlist.add(zcmap);
				mp.put(flp.getJyyf(), zchlist);
			}
		}
		map.put("value", mp);
		map.put("title", fjzchList);
		map.put("jd", fuelupJdList);
		
		
		return map;
	}
	//发油人汇总
	@Override
	public Map<String, Object> fuelupFyrSummaryList(Fuelup fuelup)
			throws RuntimeException {
		Map <String, Object>map=new TreeMap<String, Object>();
		//查询飞机加油人
		List<String> fjzchList=fuelupMapper.queryAllFyr(fuelup);
		
		//查询所有加油单
		List<Fuelup> fuelupSummaryList=fuelupMapper.queryfuelupFyrSummaryList(fuelup);
		
		//查询所有加油单基地
		List<Map<String,String>> fuelupJdList=fuelupMapper.queryfuelupJdList(fuelup);
		for (Map<String, String> fue : fuelupJdList) {
			fue.put("zjia", "0");
			for (String fyr : fjzchList) {
				fue.put(fyr,"0");
			}
			
		}
		
		Map<String,List> mp=new TreeMap<String, List>();
		for (Fuelup flp : fuelupSummaryList) {
			if(mp.containsKey(flp.getJyyf())){
				List<Map> zchlist=mp.get(flp.getJyyf());
				boolean b=true;
				for (Map zchmap : zchlist) {
					if(!zchmap.containsKey(flp.getFyr())){
						if(zchmap.get("code").equals(flp.getJd())){
							zchmap.put(flp.getFyr(), flp.getSjjysl());
							double ypf=Double.valueOf(String.valueOf(zchmap.get("ypfy")));
							double newypfy=BigDecimal.valueOf(ypf).add(BigDecimal.valueOf(flp.getYpfy())).doubleValue();
							zchmap.put("ypfy",newypfy);
							
							double ypz=Double.valueOf(String.valueOf(zchmap.get("ypzl")));
							double newypzl=BigDecimal.valueOf(ypz).add(BigDecimal.valueOf(flp.getYpzl())).doubleValue();
							zchmap.put("ypzl",newypzl);
							b=false;
							break;
						}
					}
				}
				if(b){
					Map<String, String> zcmap=new HashMap<String, String>();
					zcmap.put(flp.getFyr(), String.valueOf(flp.getSjjysl()));
					zcmap.put("code", flp.getJd());
					zcmap.put("jdms", flp.getJdms());
					zcmap.put("date", flp.getJyyf());
					if(zcmap.get("ypfy")!=null && !"".equals(zcmap.get("ypfy"))){
						Double fy=Double.valueOf(zcmap.get("ypfy"));
						zcmap.put("ypfy", String.valueOf(fy));
					}else{
						zcmap.put("ypfy", String.valueOf(flp.getYpfy()));
					}
					zcmap.put("ypzl", String.valueOf(flp.getYpzl()));
					zchlist.add(zcmap);
					
				}
				
			}else{
				List<Map> zchlist=new ArrayList<Map>();
				Map<String, String> zcmap=new HashMap<String, String>();
				zcmap.put(flp.getFyr(), String.valueOf(flp.getSjjysl()));
				zcmap.put("code", flp.getJd());
				zcmap.put("jdms", flp.getJdms());
				zcmap.put("date", flp.getJyyf());
				if(zcmap.get("ypfy")!=null && !"".equals(zcmap.get("ypfy"))){
					Double fy=Double.valueOf(zcmap.get("ypfy"));
					zcmap.put("ypfy", String.valueOf(fy));
				}else{
					zcmap.put("ypfy", String.valueOf(flp.getYpfy()));
				}
				zcmap.put("ypzl", String.valueOf(flp.getYpzl()));
				zchlist.add(zcmap);
				mp.put(flp.getJyyf(), zchlist);
			}
		}
		map.put("value", mp);
		map.put("title", fjzchList);
		map.put("jd", fuelupJdList);
		
		
		return map;
	}
	
	
	

}
