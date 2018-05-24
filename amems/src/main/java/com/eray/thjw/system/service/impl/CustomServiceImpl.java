package com.eray.thjw.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.po.User;
import com.eray.thjw.system.dao.BookMarkMapper;
import com.eray.thjw.system.dao.CustomBlockMapper;
import com.eray.thjw.system.dao.CustomShortcutMapper;
import com.eray.thjw.system.dao.PortalBlockMapper;
import com.eray.thjw.system.dao.SystemMessageMapper;
import com.eray.thjw.system.po.BookMark;
import com.eray.thjw.system.po.CustomBlock;
import com.eray.thjw.system.po.CustomShortcut;
import com.eray.thjw.system.po.Message;
import com.eray.thjw.system.service.CustomService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.EnquiryStatusEnum;
import enu.MaterialProgressEnum;
import enu.ReserveStatusEnum;
import enu.produce.InstalledPositionEnum;
import enu.project2.MonitorProjectEnum;

/**
 * 用户首页客制化
 * @author xu.yong
 *
 */
@Service("customService")
public class CustomServiceImpl implements CustomService {

	@Resource
	private CustomBlockMapper customBlockMapper;
	
	@Resource
	private CustomShortcutMapper customShortcutMapper;
	
	@Resource
	private PortalBlockMapper portalBlockMapper;
	
	@Resource
	private SystemMessageMapper systemMessageMapper;
	
	@Resource
	private BookMarkMapper bookMarkMapper;
	
	/**
	 * 查询用户首页不显示的块
	 * @return
	 */
	public List<CustomBlock> queryBlockList(){
		//获取登入user
		User user=ThreadVarUtil.getUser();
		return this.customBlockMapper.selectByMainId(user.getId());
	}
	
	/**
	 * 添加用户首页不显示的块
	 * @return
	 */
	public void addBlock(String panelId){
		User user = ThreadVarUtil.getUser();
		this.addBlock(user.getId(), panelId);
	}
	
	/**
	 * 添加用户首页不显示的块
	 * @return
	 */
	public void addAllBlock(List<String> panelIdList){
		User user = ThreadVarUtil.getUser();
		String userId = user.getId();
		this.customBlockMapper.deleteByMainId(userId);
		
		if(panelIdList != null && !panelIdList.isEmpty()){
			for (String panelId : panelIdList) {
				this.addBlock(userId, panelId);
			}
		}
	}
	
	private void addBlock(String userId, String panelId){
		CustomBlock customBlock = new CustomBlock();
		customBlock.setId(UUID.randomUUID().toString());
		customBlock.setMainid(userId);
		customBlock.setKbm(panelId);
		this.customBlockMapper.insertSelective(customBlock);
	}
	
	/**
	 * 查询用户快捷菜单
	 * @return
	 */
	public List<CustomShortcut> queryShortcutList(){
		User user = ThreadVarUtil.getUser();
		String userType = "2";
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			userType = "1";
		}
		return this.customShortcutMapper.selectByMainId(user.getId(), userType);
	}
	
	/**
	 * @author liub
	 * @description  查询用户快捷菜单
	 * @develop date 2017.03.07
	 */
	public List<CustomShortcut> queryListByUserId(){
		User user = ThreadVarUtil.getUser();
		String userType = "2";
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			userType = "1";
		}
		return this.customShortcutMapper.selectByUserId(user.getId(), userType);
	}
	
	/**
	 * @author peixiu
	 * @description  站外标签查询
	 * @develop date 2017.06.07
	 */
	public List<BookMark> queryBookMarkListByUserId(){
		User user = ThreadVarUtil.getUser();
		return this.bookMarkMapper.selectBookMarkByUserId(user.getId(),user.getJgdm());
	}
	/**
	 * @author peixiu
	 * @description  站外标签查询
	 * @develop date 2017.06.07
	 */
	@Override
	public String updateBookMark(BookMark bookmark){
			this.bookMarkMapper.updateBookMark(bookmark);
		return "success";
	}
	
	/**
	 * @author peixiu
	 * @description  站外标签查询
	 * @develop date 2017.06.07
	 */
	public List<BookMark> queryBookMarkByUserId(String id, String type){
		return this.bookMarkMapper.selectBookMarkByPrimaryKey(id);
	}
	
	
	
	/**
	 * 添加用户快捷菜单
	 * @return
	 */
	public void addShortcut(CustomShortcut customShortcut){
		User user = ThreadVarUtil.getUser();
		customShortcut.setId(UUID.randomUUID().toString());
		customShortcut.setYhid(user.getId());
		this.customShortcutMapper.insertSelective(customShortcut);
	}

	/**
	 * @author liub
	 * @description  添加或修改用户快捷菜单
	 * @param customShortcut
	 * @develop date 2017.03.07
	 */
	public void addOrUpdateShortcut(CustomShortcut customShortcut){
		User user = ThreadVarUtil.getUser();
		customShortcut.setYhid(user.getId());
		CustomShortcut oldObj = this.customShortcutMapper.selectByUserMenuId(customShortcut.getYhid(), customShortcut.getCdid());
		if(null == oldObj){
			customShortcut.setId(UUID.randomUUID().toString());
			this.customShortcutMapper.insertSelective(customShortcut);
		}else{
			this.customShortcutMapper.updateByUserMenuId(customShortcut);
		}
	}
	
	/**
	 * @author peixiu
	 * @description  添加或修改站外标签
	 * @param bookmark
	 * @develop date 2017.06.07
	 */
	public void addOrUpdateOutSite(BookMark bookmark){
		User user = ThreadVarUtil.getUser();
		bookmark.setId(UUID.randomUUID().toString());
		bookmark.setYhid(user.getId());
		bookmark.setJgdm(user.getJgdm());
		bookmark.setWhrid(user.getId());
		bookmark.setZt(1);
		bookmark.setWhsj(new Date());
		this.bookMarkMapper.insertSelective(bookmark);
	
	}
	
	/**
	 * 添加用户快捷菜单
	 * @return
	 */
	public void addShortcuts(List<String> cdids){
		User user = ThreadVarUtil.getUser();
		String userId = user.getId();
		this.customShortcutMapper.deleteByMainId(userId);
		
		if(cdids != null && !cdids.isEmpty()){
			for (String cdid : cdids) {
				this.addShortcut(userId, cdid);
			}
		}
	}
	
	/**
	 * 添加用户快捷菜单
	 * @return
	 */
	private void addShortcut(String userId, String cdid){
		CustomShortcut customShortcut = new CustomShortcut();
		customShortcut.setId(UUID.randomUUID().toString());
		customShortcut.setCdid(cdid);
		customShortcut.setYhid(userId);
		this.customShortcutMapper.insertSelective(customShortcut);
	}
	
	
	/**
	 * 删除用户快捷菜单
	 * @return
	 */
	public void deleteShortcut(String id, String type){
		if("1".equals(type)){
			this.customShortcutMapper.deleteByPrimaryKey(id);
		}else{
			User user = ThreadVarUtil.getUser();
			this.customShortcutMapper.deleteByUserMenuId(user.getId(), id);
		}
	}
	
	/**
	 * 删除书签
	 * @return
	 */
	public void deleteBookMark(String id, String type){
		if("0".equals(type)){
			this.bookMarkMapper.deleteByPrimaryKey(id);
		}else if("1".equals(type)){
			this.customShortcutMapper.deleteByPrimaryKey(id);
		}else{
			User user = ThreadVarUtil.getUser();
			this.customShortcutMapper.deleteByUserMenuId(user.getId(), id);
		}
	}
	
	/**
	 * 查询有效期的公告
	 * @return
	 */
	public List<Message> queryMessageList(){
		User user = ThreadVarUtil.getUser();
		return this.systemMessageMapper.selectEffective(user.getJgdm());
	}
	
	/**
	 * 查询留言
	 * @return
	 */
	public List<Map<String, Object>> queryMsg(){
		User user = ThreadVarUtil.getUser();
		return this.portalBlockMapper.selectMsg(user.getId());
	}
	
	
	/**
	 * 指派给我的技术评估单（未评估）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock1(){
		User user = ThreadVarUtil.getUser(); 
		return this.portalBlockMapper.selectBlock1(user.getId());
	}
	
	/**
	 * 指派给我的技术通告（未阅）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock2(){
		User user = ThreadVarUtil.getUser();
		return this.portalBlockMapper.selectBlock2(user.getId(),user.getBmdm());
	}
	
	/**
	 * 指派给我的技术指令（未阅）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock3(){
		User user = ThreadVarUtil.getUser();
		return this.portalBlockMapper.selectBlock3(user.getId());
	}
	/**
	 * 指派给我的修订通知书（未阅）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock4(){
		User user = ThreadVarUtil.getUser();
		return this.portalBlockMapper.selectBlock4(user.getId());
	}
	/**
	 * 指派给我培训计划
	 * @return
	 */
	public List<Map<String, Object>> queryBlock5(){
		User user = ThreadVarUtil.getUser();
		return this.portalBlockMapper.selectBlock5(user.getId(),user.getJgdm());
	}
	/**
	 * 我的提订单
	 * @return
	 */
	public List<Map<String, Object>> queryBlock6(){
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = formatContractMap(this.portalBlockMapper.selectBlock6(user.getId()));
		setMaterialProgress(list);
		return list;
	}
	
	/**
	 * MCC工单预警（完工未关闭）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock7(){
		User user = ThreadVarUtil.getUser();
		user.getParamsMap().put("userType", user.getUserType());
		user.getParamsMap().put("userId", user.getId());
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock7(user);
		return list;
	}
	
	
	/**
	 * @author liub
	 * @description 设置合同信息
	 * @param reserveDetailList
	 */
	private List<Map<String, Object>> formatContractMap(List<Map<String, Object>> reserveDetailList){
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();//页面结果集
		Map<Object, Map<String, Object>> tempMap = new HashMap<Object, Map<String, Object>>();//临时map集合key:id,value:map
		for(Map<String, Object> reserveDetailMap : reserveDetailList){
			//将合同id、合同流水号放入集合
			Map<String, Object> contractMap = new HashMap<String, Object>();
			contractMap.put("HTID", reserveDetailMap.get("HTID"));
			contractMap.put("HTLSH", reserveDetailMap.get("HTLSH"));
			Map<String, Object> resultMap = tempMap.get(reserveDetailMap.get("ID"));//从临时map集合中取出
			//如果临时map集合中没有存放则存放
			if(null == resultMap){
				tempMap.put(reserveDetailMap.get("ID"), reserveDetailMap);//id对应的数据存放临时map集合
				List<Map<String, Object>> contractMapList = new ArrayList<Map<String,Object>>();
				contractMapList.add(contractMap);
				reserveDetailMap.put("contractList", contractMapList);
				resultList.add(reserveDetailMap);
			}else{
				resultMap.put("HTSL", formatBigDecimal((BigDecimal) resultMap.get("HTSL")).add(formatBigDecimal((BigDecimal) reserveDetailMap.get("HTSL"))));
				resultMap.put("DHSL", formatBigDecimal((BigDecimal) resultMap.get("DHSL")).add(formatBigDecimal((BigDecimal) reserveDetailMap.get("DHSL"))));
				resultMap.put("RKSL", formatBigDecimal((BigDecimal) resultMap.get("RKSL")).add(formatBigDecimal((BigDecimal) reserveDetailMap.get("RKSL"))));
				List<Map<String, Object>> contractMapList = (List<Map<String, Object>>) resultMap.get("contractList");
				contractMapList.add(contractMap);
			}
		}
		return resultList;
	}
	
	/**
	 * @author liub
	 * @description 如果是空返回0
	 * @param v
	 */
	private BigDecimal formatBigDecimal(BigDecimal v){
		return v == null?BigDecimal.ZERO:v;
	}
	
	/**
	 * @author liub
	 * @description 设置航材提订进度
	 * @param reserveDetailList
	 * @return 
	 * @develop date 2016.10.17
	 */
	private void setMaterialProgress(List<Map<String, Object>> reserveDetailList) throws RuntimeException{
		for(Map<String, Object> map : reserveDetailList){
			String jdzt = null;
			Integer ttzt = ((BigDecimal) map.get("RESERVEZT")).intValue();
			Integer xjzt = ((BigDecimal) map.get("XJZT")).intValue();
			if(ttzt.intValue() == ReserveStatusEnum.SAVE.getId().intValue()){
				jdzt = MaterialProgressEnum.SAVE.getName();
			}else if(ttzt.intValue() == ReserveStatusEnum.SUBMIT.getId().intValue()){
				jdzt = MaterialProgressEnum.SUBMIT.getName();
				if(null != map.get("SHSL") || xjzt.intValue() != EnquiryStatusEnum.NO_ENQUIRY.getId().intValue()){
					jdzt = MaterialProgressEnum.AUDITING.getName();
				}
			}else if(ttzt.intValue() == ReserveStatusEnum.AUDIT_PASS.getId().intValue() || ttzt.intValue() == ReserveStatusEnum.AUDIT_NOT_PASS.getId().intValue()){
				jdzt = MaterialProgressEnum.AUDITED.getName();
			}
			if(null != map.get("HTH")){
				jdzt = MaterialProgressEnum.CONTRACTED.getName();
			}
			if(null != map.get("DHSL") && ((BigDecimal) map.get("DHSL")).intValue() > 0 && null != map.get("DHSL") && (null == map.get("RKSL") || ((BigDecimal) map.get("RKSL")).intValue() == 0)){
				jdzt = MaterialProgressEnum.ARRIVED_NO_STORAGE.getName();
			}
			if(null != map.get("RKSL") && ((BigDecimal) map.get("RKSL")).intValue() > 0 && null != map.get("HTSL") && ((BigDecimal) map.get("HTSL")).compareTo(((BigDecimal) map.get("RKSL"))) == 1 ){
				jdzt = MaterialProgressEnum.PART_STORAGED.getName();
			}
			if(null != map.get("RKSL") && null != map.get("HTSL") && ((BigDecimal) map.get("HTSL")).compareTo(((BigDecimal) map.get("RKSL"))) == 0){
				jdzt = MaterialProgressEnum.ALL_STORAGED.getName();
			}
			if(ttzt.intValue() == ReserveStatusEnum.CLOSE.getId().intValue()){
				jdzt = MaterialProgressEnum.STOP.getName();
			}
			map.put("JDZT",jdzt);
		}
	}
	/**
	 * 
	 * @Description 145工单预警
	 * @CreateTime 2017年10月16日 下午1:49:58
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock8() {
		User user = ThreadVarUtil.getUser();
		user.getParamsMap().put("userType", user.getUserType());
		user.getParamsMap().put("userId", user.getId());
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock8(user);
		return list;
	}
	/**
	 * 
	 * @Description 待办工作
	 * @CreateTime 2017年10月16日 下午2:58:53
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock9() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock9(user.getId());
		return list;
	}

	/**
	 * 
	 * @Description 传阅
	 * @CreateTime 2018年3月22日 下午2:03:58
	 * @CreateBy 林龙
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock10() {
		User user = ThreadVarUtil.getUser();
		if(StringUtils.isBlank(user.getBmdm())){
			return new ArrayList<Map<String,Object>>(0);
		}
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock10(user.getId(),user.getBmdm());
		return list;
	}

	/**
	 * @Description 待办工作
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock11() {
		User user = ThreadVarUtil.getUser();
		return this.portalBlockMapper.selectBlock11(user.getId(), user.getJgdm());
	}

	/**
	 * @Description 飞机监控135
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock12() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock12(user.getId(), user.getJgdm());
		formatJksj(list);
		return list;
	}
	
	/**
	 * @Description 格式化监控数据
	 * @CreateTime 2018-4-16 下午1:46:12
	 * @CreateBy 刘兵
	 * @param list
	 */
	private void formatJksj(List<Map<String, Object>> list){
		for (Map<String, Object> map : list) {
			if(null != map.get("FJSJ")){
				String[] fjsjArr = String.valueOf(map.get("FJSJ")).split(",");
				List<Map<String, Object>> jksjList = new ArrayList<Map<String,Object>>();
				Map<String, List<String>> idMap = putJkzToMap(fjsjArr);
				List<String> existsList = new ArrayList<String>();
				for(String fjsjStr : fjsjArr){
					Map<String, Object> resultMap = new HashMap<String, Object>();
					String[] arr = fjsjStr.split("#_#", -1);
					int wz = StringUtils.isNotBlank(arr[0])?Integer.parseInt(arr[0]):11;
					String wzStr = new StringBuffer().append(arr[0]).append(arr[1]).append(arr[2]).toString();
					String value = formatJkz(arr[3], arr[4], " ", "", true);
					if(wz == InstalledPositionEnum.BODY.getId()){
						if(MonitorProjectEnum.isTime(arr[3])){
							map.put("FXXS", value);
						}else{
							map.put("FXXH", value);
						}
					}else{
						resultMap.put("WZ", wz);
						resultMap.put("BJH", arr[1]);
						resultMap.put("XLH", arr[2]);
						resultMap.put("JKZ", idMap.get(wzStr));
						if(!existsList.contains(wzStr)){
							jksjList.add(resultMap);
						}
					}
					existsList.add(wzStr);
				}
				map.put("JKSJ", jksjList);
			}
		}
		
	}
	
	/**
	 * @Description 将监控值放入map监控值
	 * @CreateTime 2018-4-16 下午1:46:12
	 * @CreateBy 刘兵
	 * @param map
	 */
	private Map<String, List<String>> putJkzToMap(String[] fjsjArr){
		Map<String, List<String>> idMap = new HashMap<String, List<String>>();
		for(String fjsjStr : fjsjArr){
			String[] arr = fjsjStr.split("#_#", -1);
			String id = new StringBuffer().append(arr[0]).append(arr[1]).append(arr[2]).toString();
			String value = formatJkz(arr[3], arr[4], " ", "", true);
			List<String> jkzList = idMap.get(id);
			if(null == jkzList){
				jkzList = new ArrayList<String>();
				idMap.put(id, jkzList);
			}
			jkzList.add(value);
		}
		return idMap;
	}
	
	/**
	 * @Description 格式化监控值
	 * @CreateTime 2017-12-20 上午9:50:09
	 * @CreateBy 刘兵
	 * @param jklbh 监控类编号
	 * @param value 监控值
	 * @param backupValue 备用值
	 * @param dw 单位
	 * @param addDw 是否增加单位
	 * @return 监控值
	 */
	private String formatJkz(String jklbh, String value, String backupValue, String dw, boolean addDw){
		StringBuffer jkz = new StringBuffer();
		if(StringUtils.isNotBlank(value)){
			//判断是否是时间格式
			if(MonitorProjectEnum.isTime(jklbh)){
				value = StringAndDate_Util.convertToHour(value);
			}
		}else{
			value = backupValue;
		}
		jkz.append(value);
		if(addDw && !" ".equals(value)){
			jkz.append(" ").append(MonitorProjectEnum.getUnit(jklbh, dw));
		}
		return jkz.toString();
	}
	
	
	/**
	 * @Description 飞机监控145
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock13() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock13(user.getId(), user.getJgdm());
		return list;
	}
	/**
	 * @Description 个人执照/课程到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock14() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock14(user.getId(), user.getJgdm());
		return list;
	}
	/**
	 * @Description 执照到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock15() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock15(user.getJgdm());
		return list;
	}
	/**
	 * @Description 工具/设备校验提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock16() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock16(user.getJgdm());
		return list;
	}
	/**
	 * @Description 人员培训提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock17() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock17(user.getJgdm());
		return list;
	}
	
	/**
	 * @Description 审核通知单
	 * @CreateTime 2018-4-19 上午10:27:12
	 * @CreateBy 刘兵
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryBlock18() {
		User user = ThreadVarUtil.getUser();
		List<Map<String, Object>> list = this.portalBlockMapper.selectBlock18(user.getBmdm(), user.getJgdm());
		return list;
	}

}
