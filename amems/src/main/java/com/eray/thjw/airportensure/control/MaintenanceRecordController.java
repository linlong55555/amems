package com.eray.thjw.airportensure.control;

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

import com.eray.thjw.airportensure.po.MaintenanceRecord;
import com.eray.thjw.airportensure.service.MaintenanceRecordService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/airportensure/maintenancerecord")
public class MaintenanceRecordController {
	@Autowired
	private MaintenanceRecordService maintenanceRecordService;

	@Privilege(code = "airportensure:maintenancerecord:main")
	@RequestMapping(value = "main")
	public String manage(Model model)
			throws BusinessException {
		model.addAttribute("superdprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return "airportensure/maintenancerecord/MaintenanceRecord";
	}

	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody MaintenanceRecord maintenanceRecord,
			HttpServletRequest request, Model model) throws Exception {
		String id = maintenanceRecord.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		maintenanceRecord.setId(null);
		PageHelper.startPage(maintenanceRecord.getPagination());
		List<MaintenanceRecord> maintenanceRecordList = maintenanceRecordService.selectMaintenanceRecordList(maintenanceRecord);	
		if(((Page)maintenanceRecordList).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){
				if(!PageUtil.hasRecord(maintenanceRecordList, id)){
					MaintenanceRecord maintenanceRecord1=new MaintenanceRecord();
					maintenanceRecord1.setId(id);
					List<MaintenanceRecord> newList = maintenanceRecordService.selectMaintenanceRecordList(maintenanceRecord1);
					if(newList != null && newList.size() == 1){
						maintenanceRecordList.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(maintenanceRecordList, maintenanceRecord.getPagination());
		}else{
			List<MaintenanceRecord> newRecordList = new ArrayList<MaintenanceRecord>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				MaintenanceRecord maintenanceRecord2=new MaintenanceRecord();
				maintenanceRecord2.setId(id);
				newRecordList = maintenanceRecordService.selectMaintenanceRecordList(maintenanceRecord2);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, maintenanceRecord.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, maintenanceRecord.getPagination());
		}
	}

	@Privilege(code = "airportensure:maintenancerecord:main:01")
	@ResponseBody
	@RequestMapping("/addMaintenanceRecord")
	public String addMaintenanceRecord(@RequestBody MaintenanceRecord maintenanceRecord, HttpServletRequest request)
			throws Exception {
		if (maintenanceRecord.getId().equals("")) {
			String id = UUID.randomUUID().toString();
			maintenanceRecord.setId(id);
			maintenanceRecordService.insertMaintenanceRecord(maintenanceRecord);
		} else {
			maintenanceRecordService.updateMaintenanceRecordById(maintenanceRecord);
		}
		return maintenanceRecord.getId();
	}

	@Privilege(code = "airportensure:maintenancerecord:main:03")
	@ResponseBody
	@RequestMapping("/deleteRecord")
	public String deleteRecord(@RequestBody MaintenanceRecord maintenanceRecord, HttpServletRequest request)
			throws Exception {
		maintenanceRecordService.updateZtById(maintenanceRecord.getId());
		return "";
	}

	@Privilege(code = "airportensure:maintenancerecord:main:02")
	@ResponseBody
	@RequestMapping("/edit")
	public MaintenanceRecord edit(@RequestBody MaintenanceRecord maintenanceRecord, HttpServletRequest request)
			throws Exception {
		maintenanceRecord = maintenanceRecordService.getMaintenanceRecordById(maintenanceRecord);
		return maintenanceRecord;
	}
}
