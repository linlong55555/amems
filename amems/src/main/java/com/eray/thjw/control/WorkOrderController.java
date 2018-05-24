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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.HCMainDataService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.DetailEngineering;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.NonWOCard;
import com.eray.thjw.po.Nonroutine;
import com.eray.thjw.po.SpareStore;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOAirMaterial;
import com.eray.thjw.po.WOJobContent;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.LoadingListService;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.DetailEngineeringService;
import com.eray.thjw.service.EngineeringService;
import com.eray.thjw.service.JobCardService;
import com.eray.thjw.service.NonWOCardService;
import com.eray.thjw.service.NonroutineService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.SpareStoreService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.service.WOActionObjService;
import com.eray.thjw.service.WOAirMaterialService;
import com.eray.thjw.service.WOBaseService;
import com.eray.thjw.service.WOJobContentService;
import com.eray.thjw.service.WOJobEnclosureService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.WorkOrderStateEnum;
import enu.WorkOrderTypeEnum2;
import enu.workorder.WorkOrderEnum;


/**
 * @author 梅志亮
 * @description 工单管理的控制层
 * @develop date 2016.08.23
 */

@Controller
@RequestMapping("/project/workorder")
public class WorkOrderController extends BaseController {

	@Resource
	private NonroutineService nonroutineService;
	@Resource
	private TechnicalFileService technicalFileService; // 技术文件的Service
	@Resource
	private PlaneDataService planeDataService; // 飞机主数据的Service
	@Resource
	private LoadingListService loadingListService; // 飞机装机清单生效区的Service
	@Resource
	private SpareStoreService spareStoreService; // 航材库工具的Service
	@Resource
	private WOBaseService wOBaseService;
	@Resource
	private HCMainDataService hcMainDataService;
	@Resource
	private WOActionObjService  wOActionObjService;
	@Resource
	private OrderSourceService orderSourceService;
	@Resource
	private WOAirMaterialService wOAirMaterialService;
	@Resource
	private WOJobContentService  wOJobContentService;
	@Resource
	private NonWOCardService  nonWOCardService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private PlaneBaseService planeBaseService;
	@Resource
	private UserService  userService;
	@Resource
	private WOJobEnclosureService wOJobEnclosureService;
	@Resource
	private CommonRecService commonRecService;                   //REC表服务
	@Resource
	private EngineeringService engineeringService;           
	@Resource
	private DetailEngineeringService detailEngineeringService;           
	@Resource
	private JobCardService jobCardService;           
	
	
    /**
     * 在工程技术菜单下面进入工单列表页面
     * @param model
     * @return
     */
	@Privilege(code = "project:workorder:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String getIndex(Model model) {
		model.addAttribute("workOrderStateEnum", WorkOrderStateEnum.enumToListMap());
		return "project/workorder/workorderlist";
	}
	/**
     * 在生产计划菜单下面进入工单列表页面
     * @param model
     * @return
     */
	@Privilege(code = "project:workorder:main1")
	@RequestMapping(value = "/main1", method = RequestMethod.GET)
	public String getIndex1(Model model) {
		model.addAttribute("workOrderStateEnum", WorkOrderStateEnum.enumToListMap());
		return "project/workorder/workorderlist";
	}
	/**
	 * 请求工单列表数据
	 * @param workorder
	 * @param request
	 * @param model
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "queryWorkOrderList", method = RequestMethod.POST)
	public Map<String, Object> queryWorkOrderList(@RequestBody WorkOrder workorder, HttpServletRequest request,
			Model model) throws BusinessException{
		String id = workorder.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		workorder.setId(null);
		workorder.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		workorder.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		PageHelper.startPage(workorder.getPagination());
		List<WorkOrder> list = workOrderService.selectWorkOrderList(workorder);
		if(((Page)list).getTotal() > 0){//当总记录数大于0执行分页查询
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					WorkOrder workorder1 = new WorkOrder();
					workorder1.setId(id);
					List<WorkOrder> newRecordList = workOrderService.selectWorkOrderList(workorder1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, workorder.getPagination());
		}else{
			List<WorkOrder> newRecordList = new ArrayList<WorkOrder>(1);
			if(StringUtils.isNotBlank(id)){   //判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				WorkOrder workorder1 = new WorkOrder();
				workorder1.setId(id);
				newRecordList = workOrderService.selectWorkOrderList(workorder1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, workorder.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, workorder.getPagination());
		}
	}
	
	/**
	 * @author 梅志亮  
     * @disable  非例行工单新增初始化
	 * @time 2016-8-22
	 * @return ModelAndView
	 */
	@Privilege(code = "project:workorder:main:01,project:workorder:main1:01")
	@RequestMapping("intoNonRoutine")
	public String intoNonRoutine(HttpServletRequest req,Model model) {
		String gkid=req.getParameter("id");
		if(null!=gkid&&null!=gkid){
			JobCard jobcard=jobCardService.getByPrimaryKey(gkid);
			model.addAttribute("jobcard", jobcard);
		}
		User user=ThreadVarUtil.getUser();
		model.addAttribute("workOrderStateEnum", WorkOrderStateEnum.enumToListMap());
		model.addAttribute("baseList", planeBaseService.findBaseByDprtcode(user.getJgdm()));
		return "project/workorder/add_non";
	}
	
	/**
	 * @author 梅志亮
	 * @description 根据当前用户和技术文件取得工单的评估单号
	 */
	@ResponseBody
	@RequestMapping(value = "selectPgdList", method = RequestMethod.POST)
	public Object selectPgdList(TechnicalFile technicalFile) {
		return technicalFileService.queryAllByDocumentsId(technicalFile);
	}

	/**
	 * @author 梅志亮
	 * @description 根据当前用户和航材库信息取得工单的耗材
	 */
	@ResponseBody
	@RequestMapping(value = "selectHcxxList", method = RequestMethod.POST)
	public Map<String, Object> selectHcxxList(@RequestBody HCMainData hcMainData) throws BusinessException {
	    try{
			return hcMainDataService.selectHCList(hcMainData);
		}catch (RuntimeException e) {
			throw new BusinessException("航材工具信息加载失败",e);
		}
	}

	/**
	 * @author 梅志亮
	 * @description 加载工单的关联工单
	 */
	@ResponseBody
	@RequestMapping(value = "workCardList", method = RequestMethod.POST)
	public Map<String, Object> workCardList(@RequestBody WorkOrder workorder) throws BusinessException {
		try{
			return workOrderService.selectAllWorkOrder(workorder);
		}catch(RuntimeException e){ 
			 throw new BusinessException("查询工单信息失败",e); 
		}	 
	}
	
	/**
	 * @author 梅志亮
	 * @description 加载所有的工单 工卡的复制
	 * 用于工单的复制
	 */
	@ResponseBody
	@RequestMapping(value = "loadCopy", method = RequestMethod.POST)
	public Map<String, Object> loadCopy(@RequestBody WorkOrder workorder) throws BusinessException {
		try{
			PageHelper.startPage(workorder.getPagination());
			List<WorkOrder> list = workOrderService.selectCopyList(workorder);
			return PageUtil.pack4PageHelper(list, workorder.getPagination());
		}catch(RuntimeException e){ 
			 throw new BusinessException("查询工单信息失败",e); 
		}	 
	}
	/**
	 * @author 梅志亮
	 * @description 复制选中的工单
	 */
	@ResponseBody
	@RequestMapping(value = "loadCopyWo", method = RequestMethod.POST)
	public WorkOrder loadCopyWo(@RequestBody WorkOrder workorder) throws BusinessException {
		try{
			return workOrderService.selectByCopy(workorder);
		}catch(RuntimeException e){ 
			 throw new BusinessException("查询工单信息失败",e); 
		}	 
	}
	/**
	 * @author 梅志亮
	 * @description 复制选中的工卡
	 */
	@ResponseBody
	@RequestMapping(value = "loadCopyJobCard", method = RequestMethod.POST)
	public JobCard loadCopyJobCard(@RequestBody JobCard jc) throws BusinessException {
		try{
			return jobCardService.getCopyJobCard(jc);
		}catch(RuntimeException e){ 
			 throw new BusinessException("查询工卡信息失败",e); 
		}	 
	}

	/**
	 * @author 梅志亮
	 * @description 根据当前登陆用户取得飞机注册号
	 */
	@ResponseBody
	@RequestMapping("getPlaneDatas")             //查询d_007表
	public List<PlaneData> getPlaneDatas(PlaneData planeData,@Param (value="fjjx") String fjjx,@Param (value="dprtcode") String dprtcode,HttpServletRequest request)
			throws Exception {
		List<PlaneData> list = null;
		try {
			planeData.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
			planeData.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
			planeData.setDprtcode(dprtcode);
			planeData.setFjjx(fjjx);
			list = planeDataService.queryList(planeData);
		} catch (RuntimeException e) {
			throw new BusinessException("飞机注册号加载失败",e);
		}
		return list;
	}

	/**
	 * @author 梅志亮
	 * @description 选择非装机件根绝当前登陆用户取得飞机件号和序列号
	 */

	@ResponseBody
	@RequestMapping("getSpareStore")   //关联B_H_001 和 B_H_003
	public  Map<String, Object> getgetSpareStore( @RequestBody SpareStore ss)throws Exception {
		try{
			PageHelper.startPage(ss.getPagination());
			List<SpareStore> list = spareStoreService.selectSpareStoreList(ss);
			return PageUtil.pack4PageHelper(list, ss.getPagination());
		}catch(RuntimeException e){ 
			 throw new BusinessException("非装机件部件号加载失败",e); 
		}	 
	}

	/**
	 * @author 梅志亮
	 * @description 根据飞机注册号取得件号和序列号
	 */
	@ResponseBody
	@RequestMapping("getLoadingList")     //关联B_S_003
	public Map<String, Object> getLoadingList(@RequestBody LoadingList ll) throws BusinessException {
		try{
			PageHelper.startPage(ll.getPagination()); 
			List<LoadingList> list = loadingListService.selectLoadingList(ll);
			return PageUtil.pack4PageHelper(list, ll.getPagination());
		}catch(RuntimeException e){ 
			 throw new BusinessException("加载飞机部件号和序列号失败",e); 
		}	 
	}

	/**
	 * @author 梅志亮
	 * @description 增加工单操作
	 * @develop date 2016.08.24
	 * @throws BusinessException
	 */
	@Privilege(code = "project:workorder:main:01,project:workorder:main1:01")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@RequestBody WorkOrder workorder) throws BusinessException {
		String str = null;
		try {
			str = workOrderService.insertSelective(workorder);
		} catch (RuntimeException  e) {
			throw new BusinessException("工单保存失败",e);
		}
		return str;
	}  
	
	/**
	 * 新增EO工单工单初始化
	 * @author 梅志亮
	 * @time 2016-8-22
	 */
	@Privilege(code = "project:workorder:main:08,project:workorder:main1:08")
	@RequestMapping("intoEO")
	public String intoEO(HttpServletRequest req,HttpServletResponse resp,Model model) {
		String gczlid=req.getParameter("gczlid");
		String gczlzxdxid=req.getParameter("gczlzxdxid");
		if(null!=gczlid&&null!=gczlzxdxid){
			Engineering engineering=engineeringService.getByPrimaryKey(gczlid);
			DetailEngineering detailEngineering =detailEngineeringService.selectByPrimaryKey(gczlzxdxid);
			model.addAttribute("engineering", engineering);
			model.addAttribute("detailEngineering", detailEngineering);
		}
		User user=ThreadVarUtil.getUser();
		model.addAttribute("workOrderStateEnum", WorkOrderStateEnum.enumToListMap());
		model.addAttribute("baseList", planeBaseService.findBaseByDprtcode(user.getJgdm())); 
		return "project/workorder/add_eo";
	}
	/**
	 * @author 梅志亮
	 * @description 复制工单初始化
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	@Privilege(code = "project:workorder:main:09,project:workorder:main1:09")
	@RequestMapping(value = "intoCopy")
	public ModelAndView intoCopy(@Param (value="id") String id,@Param (value="gddlx") Integer gddlx) throws BusinessException {
		ModelAndView view = null;
		Map<String, Object> model = workOrderService.initializtionWo(id, gddlx);
		if(!WorkOrderEnum.EO_TYPE.getId().equals(gddlx)){              //定检和非例行复制出的工单类型是非例行工单
			view=new ModelAndView("project/workorder/copy_non", model);
		}else{                      //EO工单复制出的工单类型是EO工单
			view=new ModelAndView("project/workorder/copy_eo", model);
		}
		return view;
	}
	
	
	
	/**
	 * @author 梅志亮
	 * @description 编辑工单初始化页面
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	@Privilege(code = "project:workorder:main:02,project:workorder:main1:02")
	@RequestMapping(value = "intoEdit")
	public ModelAndView intoEdit(@Param (value="id") String id,@Param (value="gddlx") Integer gddlx) throws BusinessException {
		return workOrderService.intoEditByWorkOrder(id, gddlx);
	}
	
	/**
	 * @author 梅志亮
	 * @description 修改操作
	 * @develop date 2016.08.24
	 */
	@Privilege(code = "project:workorder:main:02,project:workorder:main1:02")
	@ResponseBody
	@RequestMapping(value = "Edit", method = RequestMethod.POST)
	public int Edit(@RequestBody WorkOrder workorder) throws BusinessException {
		int  count = 0;
		try {
			count = workOrderService.updateByPrimaryKeySelective(workorder);
		} catch (RuntimeException e) {
			throw new BusinessException("工单保存失败",e);
		}
		return count;
	}  
	
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 查看工单
	 * @develop date 2016.10.24 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "LookedWo")
	public ModelAndView LookedWo(@Param (value="id") String id,@Param (value="gddlx") Integer gddlx) throws BusinessException {
		Map<String, Object> model = workOrderService.initializtionWo(id,gddlx);
		return new ModelAndView("project/workorder/view_wo", model);
	}
	
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 查看工单 外部调用
	 * @develop date 2016.10.24 
	 * @throws BusinessException
	 */
	@RequestMapping(value = "Looked")
	public ModelAndView Looked(@Param (value="id") String id,@Param (value="gddlx") Integer gddlx) throws BusinessException {
		ModelAndView view = null;
		String dprtcode="";
		if(WorkOrderEnum.DJRW_TYPE.getId().equals(gddlx)){        //当查看类型为定检任务单时
			view=new ModelAndView("redirect:/project/checklist/Looked?id="+id); //重定向到定检任务单的查看页面
		}else if(WorkOrderEnum.DJ_TYPE.getId().equals(gddlx)||WorkOrderEnum.NON_TYPE.getId().equals(gddlx)||WorkOrderEnum.EO_TYPE.getId().equals(gddlx)){              //当查看的类型为工单时
			Map<String, Object> model = workOrderService.initializtionWo(id,gddlx);
			WorkOrder wo = (WorkOrder)model.get("woResult") ;
			//通过判断工单的状态是否为可修改状态，进入相应的页面
			if(WorkOrderStateEnum.SAVE_WO.getId().equals(wo.getZt())||WorkOrderStateEnum.SHENHEBOHUI_WO.getId().equals(wo.getZt())||WorkOrderStateEnum.SHENPIBOHUI_WO.getId().equals(wo.getZt())){
				view=new ModelAndView("redirect:/project/workorder/intoEdit?id="+id+"&gddlx="+gddlx);
			}else{
				view=new ModelAndView("project/workorder/view_wo", model);
			}
		}else{         //当查看类型为空或者不是规定的数值时  默认为工卡
			view=new ModelAndView("redirect:/project/jobCard/intoViewMainJobCard?id="+id+"&dprtCode="+dprtcode);  //重定向到定检工卡查看页面
		}
		return view;
	}
	

	/**
	 * @author 梅志亮
	 * @return 
	 * @description 审核工单初始化页面
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	@Privilege(code = "project:workorder:main:05,project:workorder:main1:05")
	@RequestMapping(value = "auditWo")
	public ModelAndView auditWo(@Param (value="id") String id,@Param (value="gddlx") Integer gddlx) throws BusinessException {
		return new ModelAndView("project/workorder/audit_wo",workOrderService.initializtionWo(id,gddlx));
	}
	@Privilege(code = "project:workorder:main:05")
	@ResponseBody
	@RequestMapping("Audit")
	public int updatePlaneData(HttpServletRequest request,
			HttpServletResponse response,@RequestBody WorkOrder workorder) throws Exception {
		int count=0;
		try{
			count =workOrderService.updateAudit(workorder);
        }catch(RuntimeException e){
			throw new BusinessException("工单审核失败",e);
		}
		return count;
	}
	
	/**
	 * @author meizhiliang
	 * @description 批量审核
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.12.20
	 */
	@Privilege(code = "project:workorder:main:05,project:workorder:main1:05")
	@ResponseBody
	@RequestMapping("batchReview")
	public String batchReview(@RequestBody  WorkOrder workorder, HttpServletRequest request) throws Exception {
		try{
			return workOrderService.doUpdateBatchReview(workorder);
		}catch(RuntimeException e){
			throw new BusinessException("工单批量审核失败",e);
		}
	}
	/**
	 * @author meizhiliang
	 * @description 批量批准
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code = "project:workorder:main:06,project:workorder:main1:06")
	@ResponseBody
	@RequestMapping("batchApprove")
	public String batchApprove(@RequestBody WorkOrder workorder, HttpServletRequest request) throws Exception {
		try{
			return workOrderService.doUpdateBatchApprove(workorder);
		}catch(RuntimeException e){
			throw new BusinessException("工单批量批准失败",e);
		}
	}
	
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 批准工单初始化页面
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	@Privilege(code = "project:workorder:main:06,project:workorder:main1:06")
	@RequestMapping(value = "replyWo")
	public ModelAndView replyWo(@Param (value="id") String id,@Param (value="gddlx") Integer gddlx) throws BusinessException {
		return new ModelAndView("project/workorder/reply_wo",workOrderService.initializtionWo(id,gddlx));
	}
	@Privilege(code = "project:workorder:main:06")
	@ResponseBody
	@RequestMapping("Reply")
	public int Reply(HttpServletRequest request,
			HttpServletResponse response,@RequestBody WorkOrder workorder) throws Exception {
		int count=0;
		try{
			count =workOrderService.updateReply(workorder);
        }catch(RuntimeException e){
			throw new BusinessException("工单批准失败",e);
		}
		return count;
	}
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 工单的指定结束
	 * @develop date 2016.09.24 
	 * @throws BusinessException
	 */
	@Privilege(code = "project:workorder:main:04,project:workorder:main1:04")
	@ResponseBody
	@RequestMapping("Over")
	public int Over(HttpServletRequest request,
			HttpServletResponse response,@RequestBody WorkOrder workorder) throws Exception {
		int count=0;
		try{
			count=workOrderService.updateOver(workorder);
        }catch(RuntimeException e){
			throw new BusinessException("工单指定结束失败",e);
		}
		return count;
	}
	/**
	 * @author 梅志亮
	 * @return 
	 * @description 作废工单
	 * @develop date 2016.09.24 
	 */
	@Privilege(code = "project:workorder:main:03,project:workorder:main1:03")
	@ResponseBody
	@RequestMapping("Cancel")
	public int Cancel(HttpServletRequest request,
			HttpServletResponse response,@RequestBody WorkOrder workorder) throws Exception {
		int count=0;
		try{
			count =workOrderService.updateCancel(workorder); 
        }catch(Exception e){
			throw new BusinessException("工单作废失败",e);
		}
		return count;
	}
	
	/**
	 * @author 梅志亮
	 * @description 根据工程指令id查机型
	 */
	@ResponseBody
	@RequestMapping("getJx")
	public Engineering getJx(@Param (value="gczlid") String gczlid ) throws BusinessException {
		return engineeringService.getByPrimaryKey(gczlid);
	}
	
	
	
	/**
	 * 查看所有工单和工卡
	 * @return 页面视图
	 */
	@ResponseBody
	@RequestMapping(value = "queryRelatedJobCardAll", method = RequestMethod.POST)
	public Object queryRelatedJobCardAll(@RequestBody WorkOrder workOrder,HttpServletRequest request,Model model){
		
		if(workOrder.getIds().size()<=0){
			workOrder.setIds(null);
		}
		
		PageHelper.startPage(workOrder.getPagination());
		List <WorkOrder> list=workOrderService.queryRelatedJobCardAll(workOrder);
		return PageUtil.pack4PageHelper(list, workOrder.getPagination());

	}
	/**
	 * @author sunji
	 * @description 获取已选的航材
	 */
	@ResponseBody
	@RequestMapping(value = "selectedHcList", method = RequestMethod.POST)
	public Map<String, Object> selectedHcList(@RequestBody WOAirMaterial wOAirMaterial,Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		User user = ThreadVarUtil.getUser();             // 获取当前登入人对象
		List<WOAirMaterial> list=wOAirMaterialService.selectedHcList(wOAirMaterial);
	    try{
				resultMap.put("rows", list);
		}catch(Exception e){ 
			throw new BusinessException("航材工具信息加载失败"); }
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 获取工卡的工作内容
	 */
	@ResponseBody
	@RequestMapping(value = "selectedGznrList", method = RequestMethod.POST)
	public Map<String, Object> selectedGznrList(@RequestBody WOJobContent wOJobContent, Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<WOJobContent> list=wOJobContentService.selectedGznrList(wOJobContent);
	    try{
			resultMap.put("rows", list);
		}catch(Exception e){ 
			throw new BusinessException("工作内容加载失败",e); 
		}
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 获取工卡的工作内容
	 */
	@ResponseBody
	@RequestMapping(value = "selectedXggkList", method = RequestMethod.POST)
	public Map<String, Object> selectedXggkList(@RequestParam String mainid,@RequestParam String jx, Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		NonWOCard nonWOCard= new NonWOCard();
		nonWOCard.setMainid(mainid);
		//根据mainid查询相关工卡
		List<NonWOCard> nonWOCardList=nonWOCardService.selectedXggkList(nonWOCard);
		WorkOrder wod=new WorkOrder();
		wod.setJx(jx);
		//wod.setDprtcode(user.getJgdm());
		
		List <WorkOrder> workOrderList=workOrderService.selectRealtedJobCardByjx(wod);
		//查询所有相关工卡
		for (WorkOrder workOrder : workOrderList) {
			for (NonWOCard nonWO : nonWOCardList) {
				if(workOrder.getId().equals(nonWO.getXggdid())){
					nonWO.setZy(workOrder.getZy());
					nonWO.setZyName(workOrder.getZyName());
					nonWO.setZhut(workOrder.getZhut());
					nonWO.setGdbh(workOrder.getGdbh());
				}
			}
			
		}
		System.out.println(nonWOCardList);
		resultMap.put("rows", nonWOCardList);
	    try{
				/*resultMap.put("rows", list);*/
		}catch(Exception e){ 
			throw new BusinessException("相关工单（卡）加载失败"); }
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 获取工卡的工作内容
	 */
	@ResponseBody
	@RequestMapping(value = "selectedScwjList", method = RequestMethod.POST)
	public Map<String, Object> selectedScwjList(@RequestParam String mainid, Model model) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		@SuppressWarnings("unused")
		User user = ThreadVarUtil.getUser();             // 获取当前登入人对象
		
		WOJobEnclosure wOJobEnclosure=new WOJobEnclosure();                                                                                           //工单的附件
		wOJobEnclosure.setMainid(mainid);
		List<WOJobEnclosure> woJobEnclosure=wOJobEnclosureService.selectedFjList(wOJobEnclosure);
		
		resultMap.put("rows", woJobEnclosure);
	    try{
				/*resultMap.put("rows", list);*/
		}catch(Exception e){ 
			throw new BusinessException("上传附件加载失败"); }
		return resultMap;
	}
	
	/**
	 * 开窗分页查询相关工单
	 * @param baseEntity
	 * @author xu.yong
	 * @return
	 */
	@RequestMapping(value="/list/glgd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryGlgdPage(@RequestBody BaseEntity baseEntity, HttpServletRequest request){
		baseEntity.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
		baseEntity.getParamsMap().put("userType",SessionUtil.getUserFromSession(request).getUserType());
		Map<String, Object> map = this.workOrderService.queryGlgdPage(baseEntity);
		PageUtil.addDict(map, "gdlx", WorkOrderTypeEnum2.enumToListMap());
		return map;
	}
	
	
	/*@RequestMapping(value="/nonroutine/export/{type}/{id}")
	public String export(@PathVariable String id,@PathVariable String type,HttpServletRequest request,RedirectAttributesModelMap  model) throws BusinessException {
		try {
			Nonroutine nonroutine = new Nonroutine();
			nonroutine.setId(id);
			Nonroutine nonroutineDb = nonroutineService.selectByPrimaryKey(nonroutine);
			String logoPath = "http://".concat(request.getServerName()).concat(":")
					.concat(String.valueOf(request.getServerPort()))
					.concat(request.getContextPath())
					.concat("/static/images/report");
			model.addFlashAttribute("logo_path", logoPath);
			model.addFlashAttribute("id", id);
		    return "redirect:/report/".concat(type).concat("/").concat(nonroutineDb.getDprtcode()).concat("/Nonroutine");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("非例行工单，报表预览或导出失败");
		}
	}*/
	
	@RequestMapping(value="/nonroutine/export/{type}/{id}")
	public String export(@PathVariable String id,@PathVariable String type,HttpServletRequest request,Model  model) throws BusinessException {
		try {
			Nonroutine nonroutine = new Nonroutine();
			nonroutine.setId(id);
			Nonroutine nonroutineDb = nonroutineService.selectByPrimaryKey(nonroutine);
			String logoPath = "http://".concat(request.getServerName()).concat(":")
					.concat(String.valueOf(request.getServerPort()))
					.concat(request.getContextPath())
					.concat("/static/images/report");
			model.addAttribute("logo_path", logoPath);
			model.addAttribute("id", id);
			return outReport(type, nonroutineDb.getDprtcode(), "Nonroutine", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("非例行工单，报表预览或导出失败");
		}
	}
	
	/**
	 * @author liub
	 * @description 根据工程指令id查询EO工单数据
	 * @param gczlid
	 * @return List<WorkOrder>
	 * @develop date 2017.03.17
	 */
	@ResponseBody
	@RequestMapping(value = "queryByGczlId", method = RequestMethod.POST)
	public List<WorkOrder> queryByGczlId(String gczlid)throws BusinessException {
		List<WorkOrder> list = null;
		try {
			list = workOrderService.queryByGczlId(gczlid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
		return list;
	}
	
	/**
	 * @author liub
	 * @description 根据评估单id、工单类型查询非例行工单数据
	 * @param pgdid,gdlx
	 * @return List<WorkOrder>
	 * @develop date 2017.03.20
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPgdIdAndGdlx", method = RequestMethod.POST)
	public List<WorkOrder> queryByPgdIdAndGdlx(String pgdid,String gdlx)throws BusinessException {
		List<WorkOrder> list = null;
		try {
			list = workOrderService.queryByPgdIdAndGdlx(pgdid,gdlx);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
		return list;
	}
	/**
	 * @author liub
	 * @description 根据部件号、机构代码查询耗材
	 * @param request,hcMainData
	 * @return Map<String, Object>
	 */
	@ResponseBody
	@RequestMapping(value = "selectBjhAndDprt", method = RequestMethod.POST)
	public Map<String, Object> selectBjhAndDprt(@RequestBody HCMainData hcMainData) throws BusinessException{
		PageHelper.startPage(hcMainData.getPagination());
		List<HCMainData> list = this.hcMainDataService.selectBjhAndDprt(hcMainData);
		return PageUtil.pack4PageHelper(list, hcMainData.getPagination());
	}
	
}
