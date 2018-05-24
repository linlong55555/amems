package com.eray.thjw.system.control;

import java.util.ArrayList;
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
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Department;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.system.po.Message;
import com.eray.thjw.system.service.SystemMessageService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("sys/message")
public class SystemMessageController {
	@Autowired
	private SystemMessageService systemMessageService;
	@Autowired
	private DepartmentService departmentService;

	@Privilege(code = "sys:message:main")
	@RequestMapping(value = "main")
	public String manage(Model model) throws BusinessException {

		return "sys/message/messageTable";
	}

	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody Message message, HttpServletRequest request, Model model)
			throws Exception {
		String id = message.getId();// 用户刚编辑过的记录 ID
		// 清除查询条件中的ID，保证列表查询结果集的正确性
		message.setId(null);
		PageHelper.startPage(message.getPagination());
		List<Message> messageList = systemMessageService.selectMessageList(message);
		if(((Page)messageList).getTotal() > 0){
			if (StringUtils.isNotBlank(id)) {
				if (!PageUtil.hasRecord(messageList, id)) {
					Message message1 = new Message();
					message1.setId(id);
					List<Message> newList = systemMessageService.selectMessageList(message1);
					if (newList != null && newList.size() == 1) {
						messageList.add(0, newList.get(0));						
					}
				}
			}
			return PageUtil.pack4PageHelper(messageList, message.getPagination());
		} else {
			List<Message> newRecordList = new ArrayList<Message>(1);
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				Message message2 = new Message();
				message2.setId(id);
				newRecordList = systemMessageService.selectMessageList(message2);
				if (newRecordList != null && newRecordList.size() == 1) {
					return PageUtil.pack(1, newRecordList, message.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, message.getPagination());
		}
	}
	
	@Privilege(code = "sys:message:main:01")
	@ResponseBody
	@RequestMapping("/addMessage")
	public String addMaintenanceRecord(@RequestBody Message message, HttpServletRequest request) throws Exception {
		if (message.getId().equals("")) {
			String id = UUID.randomUUID().toString();
			message.setId(id);
			systemMessageService.insertMessage(message);
		} else {
			systemMessageService.updateMessageByPrimaryKey(message);
		}
		return message.getId();
	}
	
	@Privilege(code = "sys:message:main:02")
	@ResponseBody
	@RequestMapping("/edit")
	public Message edit(@RequestBody Message message, HttpServletRequest request) throws Exception {
		message = systemMessageService.selectByPrimaryKey(message.getId());
		return message;
	}
	
	@Privilege(code = "sys:message:main:04")
	@ResponseBody
	@RequestMapping("/deleteMessage")
	public String deleteMessage(@RequestBody Message message, HttpServletRequest request) throws Exception {
		systemMessageService.updateToClose(message);
		return message.getId();
	}
	
	@Privilege(code = "sys:message:main:03")
	@ResponseBody
	@RequestMapping("/replyMessage")
	public String replyMessage(@RequestBody Message message, HttpServletRequest request) throws Exception {
		systemMessageService.updateZtByPrimaryKey(message);
		return message.getId();
	}

	@ResponseBody
	@RequestMapping("/querydprtnameList")
	public Map<String, Object> querydprtnameList(@RequestBody Department department, HttpServletRequest request)
			throws Exception {
		List<Department> departmentList = childBm(department);
		return PageUtil.pack(departmentList.size(), departmentList, department.getPagination());
	}
	
	private List<Department> childBm(Department department)throws Exception {
		String keyword=department.getKeyword();
		List<Department> list=new ArrayList<Department>();
		List<Department> childList=departmentService.selectChild(department);
		department.setKeyword("");
		List<Department> sencondList=departmentService.selectChild(department);
		if(childList.size()>0){
			list.addAll(childList);
			for (Department department1 : childList) {
				department1.setKeyword(keyword);
				List<Department> nextList=childBm(department1);
				list.addAll(nextList);
			}
		}if(sencondList.size()>childList.size()){
			for (Department department2 : sencondList) {
				for (Department department3 : childList) {
					if(department3.getId().equals(department2.getId())){
						sencondList.remove(department3);
					}
				}
			}
			if(sencondList.size()>0){
				for (Department department1 : sencondList) {
					department1.setKeyword(keyword);
					List<Department> nextList=childBm(department1);
					list.addAll(nextList);
				}
			}
		}
		return list;
	}
}
