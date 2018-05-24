package com.eray.thjw.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Routinginspectionrecord;
import com.eray.thjw.po.RoutinginspectionrecordDetail;
import com.eray.thjw.po.User;
import com.eray.thjw.service.RoutinginspectionrecordDetailService;
import com.eray.thjw.service.RoutinginspectionrecordService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Controller
@RequestMapping("/airportensure/routinginspectionrecord")
public class RoutinginspectionrecordController extends BaseController{

	@Autowired 
	RoutinginspectionrecordService routinginspectionrecordService;
	@Autowired 
	UserService userService;
	@Autowired 
	RoutinginspectionrecordDetailService routinginspectionrecordDetailList;
	/**
	 * 跳转至巡检界面
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="airportensure:routinginspectionrecord:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(Annunciate annunciate) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/airportensure/routinginspectionrecord/routinginspectionrecord_main", model);
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 巡检界面材列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "routinginspectionrecordList", method = RequestMethod.POST)
	public Map<String, Object> routinginspectionrecordList(@RequestBody Routinginspectionrecord routinginspectionrecord,HttpServletRequest request,Model model) throws BusinessException{
		String id=routinginspectionrecord.getId();
		routinginspectionrecord.setId("");
		try {
			PageHelper.startPage(routinginspectionrecord.getPagination());
			List<Routinginspectionrecord> list = this.routinginspectionrecordService.queryAll(routinginspectionrecord);
			if(((Page)list).getTotal() > 0){
				
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Routinginspectionrecord newRecord = new Routinginspectionrecord();
						newRecord.setId(id);
						List<Routinginspectionrecord> newRecordList =this.routinginspectionrecordService.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							//将记录放入结果集第一条
							list.add(0, newRecordList.get(0));
						}
					}
				}
				return PageUtil.pack4PageHelper(list, routinginspectionrecord.getPagination());
				
			}else{
				List<Routinginspectionrecord> newRecordList = new ArrayList<Routinginspectionrecord>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Routinginspectionrecord newRecord = new Routinginspectionrecord();
					newRecord.setId(id);
					newRecordList = this.routinginspectionrecordService.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						return PageUtil.pack(1, newRecordList, routinginspectionrecord.getPagination());
					}
				}
				return PageUtil.pack(0, newRecordList, routinginspectionrecord.getPagination());
			}
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * 初始化增加巡检记录单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Privilege(code="airportensure:routinginspectionrecord:main:01")
	@RequestMapping("add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/airportensure/routinginspectionrecord/routinginspectionrecord_add", model);
	}
	/**
	 * 查询所有本组织机构下的用户
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryUserAll")
	public Map<String, Object> queryUserAll(@RequestBody User user,HttpServletRequest request,HttpServletResponse response)throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<User> users=userService.queryUserAllByDprtcode(user);
			model.put("userList", users);
			return model;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
		
	}
	/**
	 * 保存添加
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody Routinginspectionrecord routinginspectionrecord,HttpServletRequest request)throws BusinessException {
		try {
			String id=routinginspectionrecordService.inster(routinginspectionrecord);
			return id;
		} catch (Exception e) {
			throw new BusinessException("操作失败",e);
		}
		
	}
	/**
	 * 初始化修改巡检记录单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Privilege(code="airportensure:routinginspectionrecord:main:02")
	@RequestMapping("edit")
	public ModelAndView edit(@RequestParam String id,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("routinginspectionrecord", routinginspectionrecordService.selectByPrimaryKey(id));
		return new ModelAndView("/airportensure/routinginspectionrecord/routinginspectionrecord_edit", model);
	}
	/**
	 * 修改保存
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody Routinginspectionrecord routinginspectionrecord,HttpServletRequest request)throws BusinessException {
		try {
			routinginspectionrecordService.update(routinginspectionrecord);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return routinginspectionrecord.getId();
		
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 巡检明细列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "routinginspectionrecordDetailList", method = RequestMethod.POST)
	public Map<String, Object> routinginspectionrecordDetailList(@RequestBody RoutinginspectionrecordDetail routinginspectionrecordDetail,HttpServletRequest request) throws BusinessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("routinginspectionrecordDetailList", routinginspectionrecordDetailList.queryAllByMainid(routinginspectionrecordDetail));
			return map;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:routinginspectionrecord:main:03")
	@ResponseBody
	@RequestMapping(value = "invalidRoutinginspectionrecord", method = RequestMethod.POST)
	public void invalidRoutinginspectionrecord(@RequestBody Routinginspectionrecord routinginspectionrecord) throws BusinessException{
		try {
			routinginspectionrecordService.invalid(routinginspectionrecord);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	
}
