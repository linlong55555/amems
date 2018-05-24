package com.eray.thjw.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.CheckTaskBill;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.CheckingMonitoring;
import com.eray.thjw.productionplan.service.CheckTaskBillService;
import com.eray.thjw.productionplan.service.CheckingContentSerivce;
import com.eray.thjw.productionplan.service.CheckingMonitoringSerivce;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import enu.WorkOrderStateEnum;

@Controller
@RequestMapping("/project/checklist")
public  class CheckListController {
	@Resource
	private CheckTaskBillService checkTaskBillService;
	@Resource
	private UserService userService;
	@Resource
	private CheckingMonitoringSerivce checkingMonitoringSerivce;
	@Resource
	private CheckingContentSerivce checkingContentSerivce;
	
	/**
	 * @author 梅志亮  定检任务单列表初始化页面
	 * @time 2016-10-31
	 */
	@Privilege(code = "project:checklist:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String getIndex(Model model) {
		
		model.addAttribute("workOrderStateEnum", WorkOrderStateEnum.enumToListMap());
		//获取登入user
		User user1=ThreadVarUtil.getUser();
		User user=new User();
		user.setJgdm(user1.getJgdm());
		List<User> users=userService.queryUserAll(user);
		model.addAttribute("userToRole", users);
		return "project/checklist/checklist";
	}
	/**
	 * @author 梅志亮  定检任务单列表
	 * @time 2016-10-31
	 */
	@Privilege(code = "project:checklist:main")
	@ResponseBody
	@RequestMapping(value = "queryCheckList", method = RequestMethod.POST)
	public Map<String, Object> queryCheckList(
			@RequestBody CheckTaskBill checktaskbill, HttpServletRequest request,
			Model model) throws BusinessException{
		String id = checktaskbill.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		checktaskbill.setId(null);
		PageHelper.startPage(checktaskbill.getPagination());
		List<CheckTaskBill> list = checkTaskBillService.selectCheckTaskBill(checktaskbill);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			//分页查询
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					CheckTaskBill ctb = new CheckTaskBill();
					ctb.setId(id);
					List<CheckTaskBill> newRecordList = checkTaskBillService.selectCheckTaskBill(ctb);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, checktaskbill.getPagination());
		}else{
			List<CheckTaskBill> newRecordList = new ArrayList<CheckTaskBill>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				CheckTaskBill ctb = new CheckTaskBill();
				ctb.setId(id);
				newRecordList =  checkTaskBillService.selectCheckTaskBill(ctb);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, checktaskbill.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, checktaskbill.getPagination());
		}
	}
	
	/**
	 * @author 梅志亮
	 * @description 查看定检任务单
	 * @develop date 2016.11.2
	 * @throws BusinessException
	 */
	@RequestMapping(value = "Looked")
	public ModelAndView Looked(@Param (value="id") String id) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		CheckTaskBill  ct = checkTaskBillService.selectByPrimaryKey(id);
		CheckingMonitoring  cm = checkingMonitoringSerivce.selectByPrimaryKey(id);
		model.put("checklist",ct );
		model.put("checkingmonitoring",cm );
		return new ModelAndView("project/checklist/view_checklist", model);
	}
	
	/**
	 * @author 梅志亮  定检任务单列表
	 * @time 2016-10-31
	 */
	@ResponseBody
	@RequestMapping(value = "queryCheckingContentList", method = RequestMethod.POST)
	public Map<String, Object> queryCheckingContentList(@Param (value="id") String id  ,HttpServletRequest request,Model model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("rows",checkingContentSerivce.selectByPrimaryKey(id));
		return resultMap;
	}
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 定检任务单的指定结束
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	@Privilege(code = "project:checklist:main:01")
	@ResponseBody
	@RequestMapping("Over")
	public void Over(HttpServletRequest request,HttpServletResponse response,@RequestBody CheckTaskBill checkTaskBill) throws Exception {
		checkTaskBillService.doEnd(checkTaskBill);
	}
}
