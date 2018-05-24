package com.eray.thjw.componentchangerecord.control;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.componentchangerecord.service.ComponentchangerecordService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.PartsDisassemblyRecord;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * 
 * 部件拆换记录
 * @author sunji
 *
 */
@Controller
@RequestMapping("flightdata/componentchangerecord/")
public class ComponentchangerecordController extends BaseController{

	@Autowired
	private ComponentchangerecordService componentchangerecordService;
	/**
	 * 跳转到部件拆换记录页面
	 * @return
	 */
	@Privilege(code="flightdata:componentchangerecord:main")
	@RequestMapping("main")
	public String main(Model model){
		//List<Department> departments = departmentService.queryOrg();
		//model.addAttribute("departments", departments);
		return "flightdata/componentchangerecord/componentchangerecord_main";
	}
	
	/**
	 * 部件拆换记录列表
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public  Map<String, Object> list(@RequestBody PartsDisassemblyRecord partsDisassemblyRecord,HttpServletRequest request,HttpServletResponse response)throws BusinessException {
		
		try {
			PageHelper.startPage(partsDisassemblyRecord.getPagination());
			List<PartsDisassemblyRecord> list = this.componentchangerecordService.queryPartsDisassemblyRecordList(partsDisassemblyRecord);
			return PageUtil.pack4PageHelper(list, partsDisassemblyRecord.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
		
		
	}
}
