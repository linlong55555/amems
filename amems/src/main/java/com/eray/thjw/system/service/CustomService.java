package com.eray.thjw.system.service;

import java.util.List;
import java.util.Map;


import com.eray.thjw.po.User;
import com.eray.thjw.system.po.BookMark;
import com.eray.thjw.system.po.CustomBlock;
import com.eray.thjw.system.po.CustomShortcut;
import com.eray.thjw.system.po.Message;

/**
 * 用户首页客制化
 * @author xu.yong
 *
 */
public interface CustomService {

	/**
	 * 查询用户首页不显示的块
	 * @return
	 */
	public List<CustomBlock> queryBlockList();
	
	/**
	 * 添加用户首页不显示的块
	 * @return
	 */
	public void addBlock(String panelId);
	
	/**
	 * 添加用户首页不显示的块
	 * @return
	 */
	public void addAllBlock(List<String> panelIdList);
	
	/**
	 * @author liub
	 * @description  查询用户快捷菜单
	 * @develop date 2017.03.07
	 */
	public List<CustomShortcut> queryListByUserId();
	
	/**
	 * @author peixiu
	 * @description  站外标签查询
	 * @develop date 2017.06.07
	 */
	public List<BookMark> queryBookMarkListByUserId();
	/**
	 * @author peixiu
	 * @description  站外标签查询
	 * @develop date 2017.06.07
	 */
	public List<BookMark> queryBookMarkByUserId(String id, String type);
	/**
	 * @author peixiu
	 * @description  站外标签查询
	 * @develop date 2017.06.07
	 */
	public String updateBookMark(BookMark bookmark);
	
	/**
	 * 查询用户快捷菜单
	 * @return
	 */
	public List<CustomShortcut> queryShortcutList();
	
	/**
	 * 添加用户快捷菜单
	 * @return
	 */
	public void addShortcut(CustomShortcut customShortcut);
	
	/**
	 * @author liub
	 * @description  添加或修改用户快捷菜单
	 * @param customShortcut
	 * @develop date 2017.03.07
	 */
	public void addOrUpdateShortcut(CustomShortcut customShortcut);
	/**
	 * @author peixiu
	 * @description  添加或修改站外书签
	 * @param customShortcut
	 * @develop date 2017.03.07
	 */
	public void addOrUpdateOutSite(BookMark bookmark);
	
	/**
	 * 添加用户快捷菜单
	 * @return
	 */
	public void addShortcuts(List<String> cdids);
	
	/**
	 * 删除用户快捷菜单
	 * @return
	 */
	public void deleteShortcut(String id, String type);
	
	/**
	 * 删除书签
	 * @return
	 */
	public void deleteBookMark(String id, String type);
	
	/**
	 * 查询有效期的公告
	 * @return
	 */
	public List<Message> queryMessageList();
	
	/**
	 * 查询留言
	 * @return
	 */
	public List<Map<String, Object>> queryMsg();
	
	/**
	 * 指派给我的技术评估单（未评估）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock1();
	
	/**
	 * 指派给我的技术通告（未阅）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock2();
	
	/**
	 * 指派给我的技术指令（未阅）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock3();
	
	/**
	 * 指派给我的修订通知书（未阅）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock4();
	
	/**
	 * 指派给我培训计划
	 * @return
	 */
	public List<Map<String, Object>> queryBlock5();
	
	/**
	 * 我的提订单
	 * @return
	 */
	public List<Map<String, Object>> queryBlock6();
	
	/**
	 * MCC 135工单预警（完工未关闭）
	 * @return
	 */
	public List<Map<String, Object>> queryBlock7();
	/**
	 * 
	 * @Description 145工单预警
	 * @CreateTime 2017年10月16日 下午1:49:58
	 * @CreateBy 岳彬彬
	 * @return
	 */
	public List<Map<String, Object>> queryBlock8();
	/**
	 * 
	 * @Description 待办工作
	 * @CreateTime 2017年10月16日 下午2:58:53
	 * @CreateBy 岳彬彬
	 * @return
	 */
	public List<Map<String, Object>> queryBlock9();

	/**
	 * 
	 * @Description 传阅
	 * @CreateTime 2018年3月22日 下午2:03:58
	 * @CreateBy 林龙
	 * @return
	 */
	public List<Map<String, Object>> queryBlock10();
	
	/**
	 * @Description 待办工作
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock11();
	
	/**
	 * @Description 飞机监控135
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock12();
	
	/**
	 * @Description 飞机监控145
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock13();
	
	/**
	 * @Description 个人执照/课程到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock14();
	
	/**
	 * @Description 执照到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock15();
	
	/**
	 * @Description 工具/设备校验提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock16();
	
	/**
	 * @Description 人员培训提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock17();
	
	/**
	 * @Description 审核通知单
	 * @CreateTime 2018-4-19 上午10:27:12
	 * @CreateBy 刘兵
	 * @return
	 */
	public List<Map<String, Object>> queryBlock18();
	
}
