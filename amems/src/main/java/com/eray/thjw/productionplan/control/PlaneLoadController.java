

package com.eray.thjw.productionplan.control;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.DetailEngineering;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneLoad;
import com.eray.thjw.productionplan.po.PlaneLoadInfo;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.PlaneLoadInfoService;
import com.eray.thjw.productionplan.service.PlaneLoadService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


@Controller
@RequestMapping("/productionplan/planeload")
public class PlaneLoadController extends BaseController {
	@Autowired
	private PlaneLoadService planeLoadService;
	@Autowired
	private PlaneLoadInfoService planeLoadInfoService;
	@Autowired
	private LoadingListService loadingListService;
	@Autowired
	private OrderAttachmentService orderAttachmentService;
	
	/**
	 * 跳转至计划任务管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="productionplan:planeload:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView manage() throws BusinessException {
		return new ModelAndView("productionplan/planeload/planeload_main");
	}
	
	@ResponseBody
	@RequestMapping(value = "getListPage", method = RequestMethod.POST)
	public Map<String, Object> planeLoadList(@RequestBody PlaneLoad record,
			HttpServletRequest request, Model model) throws Exception {
		String id = record.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		record.setId(null);
		PageHelper.startPage(record.getPagination());
		List<PlaneLoad> planeLoadList = planeLoadService.getPlaneLoadList(record);
		if(((Page)planeLoadList).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){
				if(!PageUtil.hasRecord(planeLoadList, id)){
					PlaneLoad record1=new PlaneLoad();
					record1.setId(id);
					List<PlaneLoad> newList =  planeLoadService.getPlaneLoadList(record1);
					if(newList != null && newList.size() == 1){
						planeLoadList.add(0, newList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(planeLoadList, record.getPagination());
		}else{
			List<PlaneLoad> newRecordList = new ArrayList<PlaneLoad>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				PlaneLoad record2=new PlaneLoad();
				record2.setId(id);
				newRecordList = planeLoadService.getPlaneLoadList(record2);;
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, record.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, record.getPagination());
		}
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("productionplan/planeload/planeload_add", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至飞机载重新增页面失败!",e);
		}
	}
	
	@ResponseBody
	@RequestMapping("/queryPartList")
	public Map<String, Object> queryPartList(@RequestBody LoadingList ll, HttpServletRequest request)
			throws Exception {
		PageHelper.startPage(ll.getPagination());
		List<LoadingList> partList=loadingListService.getPart(ll);
		return PageUtil.pack4PageHelper(partList, ll.getPagination());
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public String add(@RequestBody PlaneLoad record, HttpServletRequest request)
			throws Exception {
		String id=UUID.randomUUID().toString();
		record.setId(id);
		planeLoadService.insertPlaneLoad(record);
		return id;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("planeLoad", planeLoadService.selectByPrimaryKey(id));
			return new ModelAndView("productionplan/planeload/planeload_edit", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至飞机载重修改页面失败!",e);
		}
	}
	@ResponseBody
	@RequestMapping("/edit")
	public String edit(@RequestBody PlaneLoad record, HttpServletRequest request)
			throws Exception {
		planeLoadService.updatePlaneLoad(record);
		return record.getId();
	}
	/**
	 * @author 查询部件震动的列
	 * @description 
	 * @param mainid,model
	 * @return List<PlaneLoadInfo>
	 */
	@ResponseBody
	@RequestMapping(value = "queryListBymainid",method={RequestMethod.POST,RequestMethod.GET})
	public List<PlaneLoadInfo> queryListBymainid(String mainid) throws BusinessException {
		List<PlaneLoadInfo> list = null;
		try {
			list= planeLoadInfoService.getInfoByMainid(mainid);
		} catch (Exception e) {
			throw new BusinessException("部件震动列失败!",e);
		}
		return list;
	}
	/**
	 * @author sunji
	 * @description 获取上传附件
	 */
	@ResponseBody
	@RequestMapping(value = "selectedScwjList", method = RequestMethod.POST)
	public Map<String, Object> selectedScwjList(@RequestParam String mainid, Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	    try{
	    	@SuppressWarnings("unused")
	    	User user = ThreadVarUtil.getUser();             // 获取当前登入人对象
	    	List<OrderAttachment> orderAttachmentList=orderAttachmentService.queryAll(mainid);
	    	resultMap.put("rows", orderAttachmentList);
		}catch(Exception e){ 
			throw new BusinessException("上传附件加载失败"); }
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 删除
	 * @param model,id
	 * @throws Exception 
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public Map<String, Object> delete(@RequestParam String id) throws Exception {
		Map<String, Object> resultMap = planeLoadService.deletePlaneLoad(id);
		return resultMap;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("planeLoad", planeLoadService.selectByPrimaryKey(id));
			return new ModelAndView("productionplan/planeload/planeload_view", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至飞机载重查看页面失败!",e);
		}
	}
}
