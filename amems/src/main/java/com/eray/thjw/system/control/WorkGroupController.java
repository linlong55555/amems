package com.eray.thjw.system.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.po.WorkGroup;
import com.eray.thjw.system.service.WorkGroupService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("sys/workgroup")
public class WorkGroupController {
	@Autowired
	private WorkGroupService workGroupService;

	@Privilege(code = "sys:workgroup:main")
	@RequestMapping(value = "main")
	public String manage(Model model) throws BusinessException {

		return "sys/workgroup/WorkGroup";
	}

	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody WorkGroup workGroup, HttpServletRequest request,
			Model model) throws Exception {
		String id = workGroup.getId();// 用户刚编辑过的记录 ID
		// 清除查询条件中的ID，保证列表查询结果集的正确性
		workGroup.setId(null);
		PageHelper.startPage(workGroup.getPagination());
		List<WorkGroup> workGroupList = workGroupService.selectWorkGroupList(workGroup);
		if(((Page)workGroupList).getTotal() > 0){
			if (StringUtils.isNotBlank(id)) {
				if (!PageUtil.hasRecord(workGroupList, id)) {
					WorkGroup workGroup1 = new WorkGroup();
					workGroup1.setId(id);
					List<WorkGroup> newList = workGroupService.selectWorkGroupList(workGroup1);
					if (newList != null && newList.size() == 1) {
						workGroupList.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(workGroupList, workGroup.getPagination());
		} else {
			List<WorkGroup> newRecordList = new ArrayList<WorkGroup>(1);
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				WorkGroup workGroup2 = new WorkGroup();
				workGroup2.setId(id);
				newRecordList = workGroupService.selectWorkGroupList(workGroup2);
				if (newRecordList != null && newRecordList.size() == 1) {
					return PageUtil.pack(1, newRecordList, workGroup.getPagination());
				}				
			}
			return PageUtil.pack(0, newRecordList, workGroup.getPagination());
		}
	}
	
	@Privilege(code = "sys:workgroup:main:01")
	@ResponseBody
	@RequestMapping("/addWorkGroup")
	public String addMaintenanceRecord(@RequestBody WorkGroup workGroup, HttpServletRequest request) throws Exception {	
			if (workGroup.getId().equals("")) {
				String id = UUID.randomUUID().toString();
				workGroup.setId(id);
				workGroupService.insertWorkGroup(workGroup);
			} else {
				workGroupService.updateWorkGroupByPrimaryKey(workGroup);
			}
			return workGroup.getId();
	}
	
	@Privilege(code = "sys:workgroup:main:02")
	@ResponseBody
	@RequestMapping("/edit")
	public WorkGroup edit(@RequestBody WorkGroup workGroup, HttpServletRequest request) throws Exception {
		workGroup=workGroupService.getWorkGroupById(workGroup);
		return workGroup;
	}
	
	@Privilege(code = "sys:workgroup:main:03")
	@ResponseBody
	@RequestMapping("/deleteWorkGroup")
	public String deleteMessage(@RequestBody WorkGroup workGroup, HttpServletRequest request) throws Exception {
		
		workGroupService.updateToInvalid(workGroup);
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/checkWorkGroup")
	public int checkWorkGroup(@RequestBody WorkGroup workGroup, HttpServletRequest request) throws Exception {
		int count = workGroupService.getCountByDprtCode2(workGroup);
		return count;
	}
	//加载工作组
	@ResponseBody
	@RequestMapping("/loadWorkGroup")
	public Map<String, Object> loadWorkGroup(@RequestBody WorkGroup workGroup) throws Exception {
		Map<String, Object> map = new HashMap<String , Object>();
		try{
			map.put("wgList",workGroupService.getWorkGroupList(workGroup));
			map.put("wgMust",SysContext.WORKGROUP_MUST);
		}catch(RuntimeException e){ 
			throw new BusinessException("加载工作组失败",e); 
		}	 
		return map;
	}

}
