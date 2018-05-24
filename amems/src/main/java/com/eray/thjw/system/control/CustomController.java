package com.eray.thjw.system.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.control.BaseController;
import com.eray.thjw.po.User;
import com.eray.thjw.system.po.BookMark;
import com.eray.thjw.system.po.CustomShortcut;
import com.eray.thjw.system.service.CustomService;
import com.eray.thjw.util.PageUtil;

/**
 * 
 * 用户首页客制化
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("sys/custom")
public class CustomController extends BaseController{
	
	@Resource
	private CustomService customService;
	
	/**
	 * 查询用户定制数据（隐藏块、自定义快捷方式）
	 */
	@RequestMapping(value="/block/list", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> list(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delPanel", this.customService.queryBlockList());
		map.put("shortcut", this.customService.queryShortcutList());
		map.put("message", this.customService.queryMessageList());
		map.put("msg", this.customService.queryMsg());
		return map;
	}
	
	/**
	 * @author liub
	 * @description 查询用户定制数据（隐藏块、自定义快捷方式）
	 * @develop date 2017.03.07
	 */
	@RequestMapping(value="/block/queryListByUserId", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryListByUserId(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("delPanel", this.customService.queryBlockList());
		map.put("shortcut", this.customService.queryListByUserId());
		map.put("message", this.customService.queryMessageList());
		map.put("msg", this.customService.queryMsg());
		return map;
	}
	
	/**
	 * @author peixiu
	 * @description 站外标签
	 * @develop date 2017.06.07
	 */
	@RequestMapping(value="/mark/queryBookMarkListByUserId", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryBookMarkListByUserId(){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("bookmark", this.customService.queryBookMarkListByUserId());
		return map;
	}
	
	/**
	 * @author peixiu
	 * @description 站外标签
	 * @develop date 2017.06.07
	 */
	@RequestMapping(value="/mark/queryBookMarkByUserId", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> queryBookMarkByUserId(@RequestParam String id, @RequestParam String type){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("bookmark", this.customService.queryBookMarkByUserId(id,type));
		return map;
	}
	
	/**
	 * @author peixiu
	 * @description 站外标签
	 * @develop date 2017.06.07
	 */
	@ResponseBody
	@RequestMapping(value="/mark/updateBookMark", method = RequestMethod.POST)
	public Object updateBookMark(@RequestBody BookMark bookmark, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
		this.customService.updateBookMark(bookmark);
		resultMap.put("status", "success");
		resultMap.put("massage", "修改成功");
	     }catch(Exception e){
		resultMap.put("status", "error");
		resultMap.put("massage", "修改失败");
	    }
	   return resultMap;
	}
	
	/**
	 * 添加一个不需显示的块
	 * @param panelId
	 */
	@RequestMapping(value="/block/add", method = RequestMethod.POST)
	public @ResponseBody void addBlock(String panelId){
		try{
			this.customService.addBlock(panelId);
		}catch(Exception e){
			getLogger().error("添加不需要显示的块失败", e);
		}
	}
	
	/**
	 * 添加多个不需要显示的块
	 * @param panelIds
	 */
	@RequestMapping(value="/block/adds", method = RequestMethod.POST)
	public @ResponseBody void addAllBlock(@RequestParam(required=false,value="panelIds[]")List<String> panelIds){
		try{
			this.customService.addAllBlock(panelIds);
		}catch(Exception e){
			getLogger().error("添加不需要显示的块失败", e);
		}
	}
	
	/**
	 * 添加一个快捷方式
	 * @param panelId
	 */
	@RequestMapping(value="/shortcut/add", method = RequestMethod.POST)
	public @ResponseBody void addShortcut(CustomShortcut customShortcut){
		try{
			this.customService.addShortcut(customShortcut);
		}catch(Exception e){
			getLogger().error("添加快捷菜单失败", e);
		}
	}
	
	/**
	 * @author liub
	 * 添加或修改一个快捷方式
	 * @param customShortcut
	 * @develop date 2017.03.07
	 */
	@RequestMapping(value="/shortcut/addOrUpdate", method = RequestMethod.POST)
	public @ResponseBody void addOrUpdateShortcut(CustomShortcut customShortcut){
		try{
			this.customService.addOrUpdateShortcut(customShortcut);
		}catch(Exception e){
			 getLogger().error("添加或修改快捷菜单失败", e);
		}
	}
	/**
	 * @author 裴秀
	 * 添加站外标签
	 * @param bookmark
	 * @develop date 2017.06.07
	 */
	@RequestMapping(value="/bookmark/addOrUpdateOutSite", method = RequestMethod.POST)
	public @ResponseBody void addOrUpdateOutSite(BookMark bookmark){
		try{
			this.customService.addOrUpdateOutSite(bookmark);
		}catch(Exception e){
			getLogger().error("添加或修改快捷菜单失败", e);
		}
	}
	/**
	 * 添加多个快捷方式
	 * @param panelId
	 */
	@RequestMapping(value="/shortcut/adds", method = RequestMethod.POST)
	public @ResponseBody void addShortcuts(@RequestParam(required=false,value="cdids[]")List<String> cdids){
		try{
			this.customService.addShortcuts(cdids);
		}catch(Exception e){
			getLogger().error("添加快捷菜单失败", e);
		}
	}
	
	/**
	 * 删除一个不需显示的块
	 * @param panelId
	 */
	@RequestMapping(value="/shortcut/remove", method = RequestMethod.POST)
	public @ResponseBody void deleteShortcut(@RequestParam String id, @RequestParam String type){
		try{
			this.customService.deleteShortcut(id, type);
		}catch(Exception e){
			getLogger().error("删除快捷菜单失败", e);
		}
	}
	
	/**
	 * 书签删除
	 * @param panelId
	 */
	@RequestMapping(value="/bookmark/remove", method = RequestMethod.POST)
	public @ResponseBody void deleteBookMark(@RequestParam String id, @RequestParam String type){
		try{
			this.customService.deleteBookMark(id, type);
		}catch(Exception e){
			getLogger().error("删除快捷菜单失败", e);
		}
	}
	
	/**
	 * 查询通知公告和用户留言
	 */
	@RequestMapping(value="/message/list", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> messageList(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", this.customService.queryMessageList());
		map.put("msg", this.customService.queryMsg());
		return map;
	}
	
	/**
	 * 查询用户留言
	 */
	@RequestMapping(value="/msg/list", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> msgList(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", this.customService.queryMsg());
		return map;
	}
	
	/**
	 * 指派给我的技术评估单（未评估）
	 * @return
	 */
	@RequestMapping(value = "/block/block1", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block1List(){
		return PageUtil.pack(this.customService.queryBlock1());
	}
	
	/**
	 * 指派给我的技术通告（未阅）
	 * @return
	 */
	@RequestMapping(value = "/block/block2", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block2List(){
		return PageUtil.pack(this.customService.queryBlock2());
	}
	/**
	 * 指派给我的技术指令（未阅）
	 * @return
	 */
	@RequestMapping(value = "/block/block3", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block1L3st(){
		return PageUtil.pack(this.customService.queryBlock3());
	}
	
	/**
	 * 指派给我的修订通知书（未阅）
	 * @return
	 */
	@RequestMapping(value = "/block/block4", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block4List(){
		return PageUtil.pack(this.customService.queryBlock4());
	}
	/**
	 * 指派给我培训通知
	 * @return
	 */
	@RequestMapping(value = "/block/block5", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block5List(){
		return PageUtil.pack(this.customService.queryBlock5());
	}
	
	/**
	 * 我的提订单
	 * @return
	 */
	@RequestMapping(value = "/block/block6", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block6List(){
		return PageUtil.pack(this.customService.queryBlock6());
	}
	
	/**
	 * MCC 135工单预警（完工未关闭）
	 * @return
	 */
	@RequestMapping(value = "/block/block7", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block7List(){
		return PageUtil.pack(this.customService.queryBlock7());
	}
	
	/**
	 * MCC 145工单预警（完工未关闭）
	 * @return
	 */
	@RequestMapping(value = "/block/block8", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block8List(){
		return PageUtil.pack(this.customService.queryBlock8());
	}
	/**
	 * 
	 * @Description 待办工作
	 * @CreateTime 2017年10月16日 下午3:00:16
	 * @CreateBy 岳彬彬
	 * @return
	 */
	@RequestMapping(value = "/block/block9", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block9List(){
		return PageUtil.pack(this.customService.queryBlock9());
	}
	/**
	 * 
	 * @Description 传阅
	 * @CreateTime 2018年3月22日 下午2:03:42
	 * @CreateBy 林龙
	 * @return
	 */
	@RequestMapping(value = "/block/block10", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block10List(){
		return PageUtil.pack(this.customService.queryBlock10());
	}
	
	/**
	 * @Description 待办工作
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block11", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block11List(){
		return PageUtil.pack(this.customService.queryBlock11());
	}
	
	/**
	 * @Description 飞机监控135
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block12", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block12List(){
		return PageUtil.pack(this.customService.queryBlock12());
	}
	
	/**
	 * @Description 飞机监控145
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block13", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block13List(){
		return PageUtil.pack(this.customService.queryBlock13());
	}
	
	/**
	 * @Description 个人执照/课程到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block14", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block14List(){
		return PageUtil.pack(this.customService.queryBlock14());
	}
	
	/**
	 * @Description 执照到期提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block15", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block15List(){
		return PageUtil.pack(this.customService.queryBlock15());
	}
	
	/**
	 * @Description 工具/设备校验提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block16", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block16List(){
		return PageUtil.pack(this.customService.queryBlock16());
	}
	
	/**
	 * @Description 人员培训提醒
	 * @CreateTime 2018-4-14 上午10:26:51
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block17", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block17List(){
		return PageUtil.pack(this.customService.queryBlock17());
	}
	
	/**
	 * @Description 审核通知单
	 * @CreateTime 2018-4-19 上午10:27:12
	 * @CreateBy 刘兵
	 * @return
	 */
	@RequestMapping(value = "/block/block18", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> block18List(){
		return PageUtil.pack(this.customService.queryBlock18());
	}
	
}
