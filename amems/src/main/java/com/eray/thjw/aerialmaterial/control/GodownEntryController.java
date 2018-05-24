 package com.eray.thjw.aerialmaterial.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.GodownEntry;
import com.eray.thjw.aerialmaterial.service.GodownEntryDetailService;
import com.eray.thjw.aerialmaterial.service.GodownEntryService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.ReserveStatusEnum;
import enu.aerialmaterial.MaterialSrouceEnum;

/**
 * 
 * 入库控制器 
 * @author ll
 */
@Controller
@RequestMapping("/aerialmaterial/godown")
public class GodownEntryController {
	
	@Resource
	private GodownEntryService godownEntryService;
	
	@Resource
	private GodownEntryDetailService godownEntryDetailService;
	/**
	 * 跳转至入库管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="aerialmaterial:godown:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		return new ModelAndView("/material/godown/godown_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 入库列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "godownEntryList", method = RequestMethod.POST)
	public Map<String, Object> godown(@RequestBody GodownEntry godownEntry,HttpServletRequest request,Model model) throws BusinessException{
		String id = godownEntry.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		godownEntry.setId(null);
		
		godownEntry.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		godownEntry.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		
		PageHelper.startPage(godownEntry.getPagination());
		List<GodownEntry> list = godownEntryService.queryAllPageList(godownEntry);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					GodownEntry godownEntry1 = new GodownEntry();
					godownEntry1.setId(id);
					List<GodownEntry> newRecordList = godownEntryService.queryAllPageList(godownEntry1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, godownEntry.getPagination());
		}else{
			List<GodownEntry> newRecordList = new ArrayList<GodownEntry>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				GodownEntry godownEntry1 = new GodownEntry();
				godownEntry1.setId(id);
				newRecordList = godownEntryService.queryAllPageList(godownEntry1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, godownEntry.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, godownEntry.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 初始化入库单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.10.13
	 *
	 */
	@Privilege(code="aerialmaterial:godown:main:01")
	@RequestMapping(value = "stoctIn", method = RequestMethod.GET)
	public ModelAndView stoctIn(String id,HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("godownEntry", godownEntryService.queryGetByid(id));//基本信息
			
			model.put("materialSrouceEnum",MaterialSrouceEnum.enumToListMap());//部件来源枚举
			
			model.put("GodownEntryDetails", godownEntryDetailService.queryGetreceiptSheetDetails(id));//入库信息
			
		} catch (Exception e) {
			throw new BusinessException("初始化入库单失败!");
		}finally{}
		return new ModelAndView("material/godown/godown_stoctin", model);
	}

	/**
	 * @author ll
	 * @description 编辑后保存入库单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "editSave", method = RequestMethod.POST)
	public String editSave(@RequestBody GodownEntry godownEntry) throws BusinessException{
		try {
			return godownEntryService.editSave(godownEntry);	
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 编辑后提交入库单
	 * @param reserve
	 * @develop date 2016.10.14
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "editSubmit", method = RequestMethod.POST)
	public String editSubmit(@RequestBody GodownEntry godownEntry) throws BusinessException{
		try {
			godownEntry.setZt(ReserveStatusEnum.SUBMIT.getId());
			godownEntryService.editSubmit(godownEntry);
			return godownEntry.getId();
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 撤销
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="aerialmaterial:godown:main:02")
	@ResponseBody
	@RequestMapping(value = "cancel", method = RequestMethod.POST)
	public void cancel(HttpServletRequest request,String id) throws BusinessException{
		try {
			godownEntryService.cancel(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 初始化入库单查看
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.10.13
	 *
	 */
	@Privilege(code="aerialmaterial:godown:main")
	@RequestMapping(value = "view", method = RequestMethod.GET)
	public ModelAndView view(String id,HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("godownEntry", godownEntryService.queryGetByid(id));//基本信息
			model.put("materialSrouceEnum",MaterialSrouceEnum.enumToListMap());//部件来源枚举
			model.put("GodownEntryDetails", godownEntryDetailService.queryGetreceiptSheetDetails(id));//入库信息
		} catch (Exception e) {
			throw new BusinessException("初始化入库单查看界面失败!");
		}finally{}
		return new ModelAndView("material/godown/godown_vew", model);
	}
}
