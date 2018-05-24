package com.eray.thjw.aerialmaterial.control;



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

import com.eray.thjw.aerialmaterial.po.BaseMaintenance;
import com.eray.thjw.aerialmaterial.service.BaseMaintenanceService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author 
 * @description 基地维护
 * @develop 
 */
@Controller
@RequestMapping("/sys/base")
public class BaseMaintenanceController extends BaseController {
	@Autowired
	private BaseMaintenanceService baseMaintenanceService;
	
	@Privilege(code="sys:base:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) throws BusinessException {
	
		return "sys/base/baseMaintenance";
	}
	/**
	 * 根据条件查询基地
	 * @param baseMaintenance
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> flightrecordList(@RequestBody BaseMaintenance baseMaintenance,
			HttpServletRequest request, Model model) throws Exception {
		String id = baseMaintenance.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		baseMaintenance.setId(null);
		PageHelper.startPage(baseMaintenance.getPagination());
		List<BaseMaintenance> baseMaintenanceList = baseMaintenanceService.selectBaseMaintenanceList(baseMaintenance);
		if(((Page)baseMaintenanceList).getTotal() > 0){
			//分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(baseMaintenanceList, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BaseMaintenance baseMaintenance1 = new BaseMaintenance();
					baseMaintenance1.setId(id);
					List<BaseMaintenance> newRecordList = baseMaintenanceService.selectBaseMaintenanceList(baseMaintenance1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						baseMaintenanceList.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(baseMaintenanceList, baseMaintenance.getPagination());		
		}
		else{
			List<BaseMaintenance> newRecordList = new ArrayList<BaseMaintenance>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BaseMaintenance baseMaintenance2 = new BaseMaintenance();
				baseMaintenance2.setId(id);
				newRecordList = baseMaintenanceService.selectBaseMaintenanceList(baseMaintenance2);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, baseMaintenance.getPagination());					
				}
			}
			return PageUtil.pack(0, newRecordList, baseMaintenance.getPagination());
		}

	}
	/**
	 * 添加或修改
	 * @param baseMaintenance
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addBaseMaintenance", method = RequestMethod.POST)
	public String addBaseMaintenance(@RequestBody BaseMaintenance baseMaintenance,
			HttpServletRequest request, Model model) throws Exception {
		int count=baseMaintenanceService.selectByJdms(baseMaintenance);
		if(count==0&&baseMaintenance.getId().equals("")){
			String id = UUID.randomUUID().toString();
			baseMaintenance.setId(id);
			baseMaintenanceService.insertBaseMaintenance(baseMaintenance);
			return id;
		}
		else if(count==0&&!baseMaintenance.getId().equals("")){
			baseMaintenanceService.updateBaseMaintenanceById(baseMaintenance);
		return baseMaintenance.getId();
		}else{
		return "";
		}
		}
	/**
	 * 作废
	 * @param baseMaintenance
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBaseMaintenance", method = RequestMethod.POST)
	public String deleteBaseMaintenance(@RequestBody BaseMaintenance baseMaintenance,
			HttpServletRequest request, Model model) throws Exception {
		baseMaintenanceService.deleteBaseMaintenanceById(baseMaintenance.getId());
		return "";
	}
	/**
	 * 获取该id对应的基地信息
	 * @param baseMaintenance
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public BaseMaintenance editBaseMaintenance(@RequestBody BaseMaintenance baseMaintenance,
			HttpServletRequest request, Model model) throws Exception {
		baseMaintenance=baseMaintenanceService.selectBaseMaintenanceById(baseMaintenance.getId());
		return baseMaintenance;
	}

}
