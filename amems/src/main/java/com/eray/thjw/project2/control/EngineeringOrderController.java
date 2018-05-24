package com.eray.thjw.project2.control;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.project2.po.EOApplicability;
import com.eray.thjw.project2.po.EOExecutionObj;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.service.EOApplicabilityService;
import com.eray.thjw.project2.service.EOMonitorIemSetService;
import com.eray.thjw.project2.service.EngineeringOrderService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;

import enu.common.WhetherEnum;
import enu.produce.WorkorderStatusEnum;
import enu.project2.EngineeringOrderStatusEnum;
import enu.project2.MonitorProjectEnum;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @Description EO控制层
 * @CreateTime 2017-8-24 下午3:21:32
 * @CreateBy 雷伟
 */
@Controller
@RequestMapping("/project2/eo")
public class EngineeringOrderController extends BaseController {

	@Resource
	private EngineeringOrderService engineeringOrderService;
	@Resource
	private EOApplicabilityService applicabilityService;
	@Resource
	private EOMonitorIemSetService eoMonitorIemSetService;
	@Resource
	private DeptInfoService deptInfoService;
	

	/**
	 * @Description EO管理
	 * @CreateTime 2017-8-17 下午6:41:05
	 * @CreateBy 裴秀
	 * @UpdateBy 雷伟
	 * @return EO管理主页面视图
	 * @throws BusinessException
	 *             业务异常
	 */
	@Privilege(code = "project2:eo:main")
	@RequestMapping(value = "/main", method = { RequestMethod.POST, RequestMethod.GET })
	public String CommonalityPage(Model model) throws BusinessException {
		try {
			model.addAttribute("engineeringOrderStatusEnum", EngineeringOrderStatusEnum.enumToListMap());
			return "/project2/engineering/engineeringOrder_main";
		} catch (Exception e) {
			throw new BusinessException("跳转至EO管理页面失败!", e);
		}
	}

	/**
	 * @Description EO分页列表查询
	 * @CreateTime 2017-8-22 下午9:43:25
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 *            EO
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody EngineeringOrder engineeringOrder)
			throws BusinessException {
		try {
			return engineeringOrderService.queryAllPageList(engineeringOrder);
		} catch (Exception e) {
			throw new BusinessException("EO列表加载失败!", e);
		}
	}

	/**
	 * @Description 新增EO
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 *            EO
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:01")
	public String save(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.save(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("保存EO失败!", e);
		}
	}

	/**
	 * @Description 编辑EO
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 *            EO
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:02")
	public String update(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.update(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("编辑EO失败!", e);
		}
	}

	/**
	 * @Description 改版EO
	 * @CreateTime 2017-8-22 上午9:19:41
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 *            EO
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doRevision", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:03")
	public String doRevision(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.doRevision(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("编辑EO失败!", e);
		}
	}

	/**
	 * @Description EO审核
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doAudit", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:04")
	public String doAudit(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.doAudit(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("审核EO失败!", e);
		}
	}

	/**
	 * @Description EO批准
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doApprove", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:05")
	public String doApprove(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.doApprove(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("批准EO失败!", e);
		}
	}

	/**
	 * @Description EO提交
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doSubmit", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:07")
	public String doSubmit(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.doSubmit(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("EO提交失败!", e);
		}
	}

	/**
	 * @Description EO关闭
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param engineeringOrder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doClose", method = RequestMethod.POST)
	@Privilege(code="project2:eo:main:06")
	public String doClose(@RequestBody EngineeringOrder engineeringOrder) throws BusinessException {
		try {
			return engineeringOrderService.doClose(engineeringOrder);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("EO关闭失败!", e);
		}
	}
	
	/**
	 * @Description 执行对象关闭
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param eoApplicability
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doZxdxClose", method = RequestMethod.POST)
	public String doZxdxClose(@RequestBody EOApplicability eoApplicability) throws BusinessException {
		try {
			return applicabilityService.doZxdxClose(eoApplicability);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("执行对象关闭失败!", e);
		}
	}
	/**
	 * @Description 执行对象确认
	 * @CreateTime 2017-8-25 下午9:31:38
	 * @CreateBy 雷伟
	 * @param eoApplicability
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "doZxdxConfirm", method = RequestMethod.POST)
	public String doZxdxConfirm(String zxdxId) throws BusinessException {
		try {
			return applicabilityService.doZxdxConfirm(zxdxId);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("执行对象确认失败!", e);
		}
	}

	/**
	 * @Description 维修项目获取可关联EO列表
	 * @CreateTime 2017年8月23日 下午3:39:28
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "associatelist", method = RequestMethod.POST)
	public Map<String, Object> associateList(@RequestBody MaintenanceScheme scheme) throws BusinessException {
		try {
			return engineeringOrderService.queryAssociateList(scheme);
		} catch (Exception e) {
			throw new BusinessException("维修项目获取可关联EO列表失败!", e);
		}
	}

	/**
	 * @Description 根据EOId查询EO及相关信息
	 * @CreateTime 2017-8-24 上午10:59:42
	 * @CreateBy 雷伟
	 * @param eoId EOID
	 * @param viewFlag 是否是查看1:是
	 * @return EO
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectById", method = { RequestMethod.POST })
	public EngineeringOrder selectById(String eoId,String viewFlag) throws BusinessException {
		try {
			EngineeringOrder eo = engineeringOrderService.selectById(eoId);
			/**
			 * 1.只有批准状态下，查看，才标记成圈阅！
			 * 2.当前登录用户所在部门与分发部门是同一个部门  且 未圈阅
			 */
			if("1".equals(viewFlag) && eo.getZt() == EngineeringOrderStatusEnum.TAKEEFFECT.getId()){
				try {
					engineeringOrderService.view4IsCirculuation(eoId);
				} catch (Exception e) {
					super.getLogger().error("EO圈阅失败!",e);
				}
			}
			return eo;
		} catch (Exception e) {
			throw new BusinessException("查询EO失败!", e);
		}
	}
	
	/**
	 * @Description 根据执行对象ID查询适用性
	 * @CreateTime 2017-8-24 上午10:59:42
	 * @CreateBy 雷伟
	 * @param zxdxId
	 *            EOID
	 * @return EO
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectZxdxById", method = { RequestMethod.POST })
	public EOApplicability selectZxdxById(String zxdxId) throws BusinessException {
		try {
			return applicabilityService.selectById(zxdxId);
		} catch (Exception e) {
			throw new BusinessException("执行对象查询失败!", e);
		}
	}

	// @Privilege(code="project2:eo:view")
	@RequestMapping(value = "/view", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView eoView(String id) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", id);
		return new ModelAndView("/project2/engineering/eo_view", model);
	}

	@Privilege(code = "project2:eo:main")
	@RequestMapping(value = "/main2", method = { RequestMethod.POST, RequestMethod.GET })
	public String main2() {
		return "/project2/engineering/eo_main";
	}

	/**
	 * @Description 获取部件所在位置信息
	 * @CreateTime 2017年8月24日 上午10:43:59
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param bjid
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getBj", method = RequestMethod.POST)
	public Map<String, Object> getBj(String dprtcode, String bjid, String fjjx,String bjh) throws BusinessException {
		try {
			return engineeringOrderService.getBj(bjid, dprtcode, fjjx,bjh);
		} catch (Exception e) {
			throw new BusinessException("获取数据失败", e);
		}
	}
	
	/**
	 * @Description  根据EOId查询当前工卡的历史版本集合
	 * @CreateTime 2017-8-31 上午9:06:37
	 * @CreateBy 雷伟
	 * @param id EOID
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryHistoryListById",method={RequestMethod.POST,RequestMethod.GET})
	public List<WorkCard> queryHistoryListById(String id) throws BusinessException {
		try {
			return engineeringOrderService.queryHistoryListById(id);
		} catch (Exception e) {
			throw new BusinessException("根据EOid查询当前工卡的历史版本集合失败!",e);
		}
	}
	
	/**
	 * @Description 删除EO
	 * @CreateTime 2017-8-22 下午4:55:32
	 * @CreateBy 雷伟
	 * @param id EOd
	 * @throws BusinessException
	 */
	@Privilege(code="project2:eo:main:08")
	@ResponseBody
	@RequestMapping(value = "doDelete", method = RequestMethod.POST)
	public void doDelete(String eoId) throws BusinessException{
		try {
			engineeringOrderService.doDelete(eoId);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!",e);
		}
	}
	
	/**
	 * @Description EO执行对象
	 * @CreateTime 2017年9月29日 下午2:21:37
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "getEOExecutionObjs", method = RequestMethod.POST)
	public Map<String, Object> getEOExecutionObjs(String eoId) throws BusinessException {
		 Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<EOExecutionObj> exeObjs = new ArrayList<EOExecutionObj>(); //左侧执行对象
			List<EOExecutionObj> exeList = new ArrayList<EOExecutionObj>(); //右侧执行列表
			
			/**Start:对有工单的执行对象，按执行对象ID，分组*/
			Map<String,List<Map<String, Object>>> exeObjsMap = new HashMap<String, List<Map<String, Object>>>(); /**按执行对象ID分组,后的数据*/
			List<Map<String, Object>> resourceDatas = engineeringOrderService.getEOExecutionObjs(eoId); /**有工单的,执行对象*/
			String zxdxid = "";
			if(null != resourceDatas){
				for(Map<String, Object> resourceData : resourceDatas) {
					zxdxid = formatNull(resourceData.get("ZXDXID"));//执行对象ID
					List<Map<String, Object>> resourcelist = new ArrayList<Map<String,Object>>();
					if(null != exeObjsMap.get(zxdxid)){
						resourcelist = exeObjsMap.get(zxdxid);
					}
					resourcelist.add(resourceData);
					exeObjsMap.put(zxdxid, resourcelist);
				}
			}
			/**End:对有工单的执行对象，按执行对象ID，分组*/
			
			
			/**Start:所有的，执行对象*/
			List<EOApplicability> allAppList = applicabilityService.getEOApplicabilityByMainId(eoId);
			/**End:所有的，执行对象*/
			
			
			/**Start:没有工单的，执行对象*/
			List<EOApplicability> notAppList = new ArrayList<EOApplicability>();
			if(null != allAppList){
				for (EOApplicability eoApp : allAppList) {
					if(exeObjsMap.get(eoApp.getId()) == null){
						notAppList.add(eoApp);
					}
				}
			}
			/**Start:没有工单的，执行对象*/
			
			exeObjs = getExeObjs(exeObjsMap,notAppList);
			exeList = getExeList(resourceDatas);
			
			resultMap.put("exeObjs", exeObjs);
			resultMap.put("exeList", exeList);
			
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("获取EO执行对象数据失败", e);
		}
	}

	/**
	 * @Description 获取右侧EO执行对象列表
	 * @CreateTime 2017-10-19 下午2:50:01
	 * @CreateBy 雷伟
	 * @param resourceDatas 有工单的执行对象
	 * @return
	 */
	private List<EOExecutionObj> getExeList(List<Map<String, Object>> resourceDatas) {
		List<EOExecutionObj> eoExeList = new  ArrayList<EOExecutionObj>();
		
		for (Map<String, Object> map : resourceDatas) {
			EOExecutionObj eoExeObj = new EOExecutionObj();
			eoExeObj.setSyx("");//适用性
			eoExeObj.setFjzch(formatNull(map.get("FJZCH")));//工单飞机注册号
			eoExeObj.setJh(formatNull(map.get("BH")));//件号
			eoExeObj.setXlh(formatNull(map.get("XLH")));//序列号
			eoExeObj.setBjName(formatNull(map.get("BJYWMC")) +" " +formatNull(map.get("BJZWMC")) );//部件名称
			eoExeObj.setWobh(formatNull(map.get("GDBH")));//工单编号
			eoExeObj.setWobt(formatNull(map.get("GDBT")));//工单标题
			eoExeObj.setWozt(WorkorderStatusEnum.getName(Integer.valueOf(formatNumberNull(map.get("ZT")))));//工单状态
			eoExeObj.setWoid(formatNull(map.get("GDID")));//工单ID
			
			List<Map<String,String>> jhzxList = new ArrayList<Map<String,String>>(); //计划执行
			List<Map<String,String>> sjzxList = new ArrayList<Map<String,String>>(); //实际执行
			
			String zxlist = formatNull(map.get("JHSJSJ"));//监控项目+计划值+实际值
			if (!"".equals(zxlist)) {
				String[] rows = zxlist.split(",");
				for(int i = 0; rows != null && i < rows.length; i++) {
					String[] columns = rows[i].split("#_#");
					
					Map<String,String> jhMap = new HashMap<String, String>();
					Map<String,String> sjMap = new HashMap<String, String>();
					
					//监控类别号
					if(columns.length >= 1){
						jhMap.put("jklbh", columns[0]); 
						sjMap.put("jklbh", columns[0]); 
					}
					
					//计划值
					if(columns.length >= 2){
						jhMap.put("jhValue", columns[1]); 
					}else{
						jhMap.put("jhValue", "NA"); 
					}
					
					//实际值
					if(columns.length >= 3){
						sjMap.put("sjValue", columns[2]); 
					}else{
						sjMap.put("sjValue", "NA"); 
					}
					
					jhzxList.add(jhMap);
					sjzxList.add(sjMap);
				}
			}
			
			eoExeObj.setJhzxList(jhzxList);
			eoExeObj.setSjzxList(sjzxList);
			
			eoExeList.add(eoExeObj);
		}
		
		return eoExeList;
	}

	/**
	 * @Description 获取左侧EO执行对象数据
	 * @CreateTime 2017-10-19 下午2:17:54
	 * @CreateBy 雷伟
	 * @param exeObjsMap 有工单的执行对象
	 * @param notAppList 没有工单的执行对象
	 * @return
	 */
	private List<EOExecutionObj> getExeObjs(Map<String, List<Map<String, Object>>> exeObjsMap,List<EOApplicability> notAppList) {
		List<EOExecutionObj> eoExeobjList = new  ArrayList<EOExecutionObj>();
		
		for (Iterator iter = exeObjsMap.entrySet().iterator(); iter.hasNext();)  
		{  
			EOExecutionObj eoExeObj = new EOExecutionObj();
			
			Entry entry = (Entry) iter.next();  
			String key = (String) entry.getKey();  
			List<Map<String, Object>> datas = (List<Map<String, Object>>) entry.getValue();
			
			List<Workorder> woList = new ArrayList<Workorder>();
			
			if(null != datas){
				for (Map<String, Object> obj : datas) {
					eoExeObj.setZxdxid(formatNull(obj.get("ZXDXID")));
					if(WhetherEnum.NO.getId().toString().equals(formatNull(obj.get("QRZT")))){
						eoExeObj.setShowYellow(true);
					}else{
						eoExeObj.setShowYellow(false);
					}
					eoExeObj.setExeName(formatNull(obj.get("BH"))+"/"+formatNull(obj.get("XLH")));
					eoExeObj.setWoNums(datas.size());
					eoExeObj.setQrZt(formatNull(obj.get("QRZT")));
					eoExeObj.setGbZt(formatNull(obj.get("GBZT")));
					
					Workorder wo = new Workorder();
					wo.setGdbh(formatNull(obj.get("GDBH")));
					if(formatNull(obj.get("ZT")).equals("")){
						wo.setGdzt(null);
					}else{
						wo.setGdzt(WorkorderStatusEnum.getName(Integer.valueOf(formatNumberNull(obj.get("ZT")))));
					}
					woList.add(wo);
					eoExeObj.setWoList(woList);
				}
			}
			
			eoExeobjList.add(eoExeObj);
		}
		
		//没有工单的执行对象
		if (null != notAppList){
			for (EOApplicability notApp : notAppList) {
				EOExecutionObj eoExeObj = new EOExecutionObj();
				
				eoExeObj.setZxdxid(formatNull(notApp.getId()));
				
				if(WhetherEnum.NO.getId().toString().equals(formatNull(notApp.getZt()))){
					eoExeObj.setShowYellow(true);
				}else{
					eoExeObj.setShowYellow(false);
				}
				eoExeObj.setExeName(formatNull(notApp.getBh())+"/"+formatNull(notApp.getXlh()));
				eoExeObj.setWoNums(0);
				eoExeObj.setQrZt(formatNull(notApp.getQrzt()));
				eoExeObj.setGbZt(formatNull(notApp.getGbzt()));
				eoExeobjList.add(eoExeObj);
			}
		}
		
		return eoExeobjList;
	}
		
	/**
	 * @Description 根据Eo编号查询eo
	 * @CreateTime 2017-12-12 下午9:43:25
	 * @CreateBy 刘邓
	 * @param engineeringOrder
	 *            EO
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryByEobh", method = RequestMethod.POST)
	public EngineeringOrder queryByEobh(@RequestBody EngineeringOrder engineeringOrder)
			throws BusinessException {
		try {
			return engineeringOrderService.getByEobh(engineeringOrder);
		} catch (Exception e) {
			throw new BusinessException("EO列表加载失败!", e);
		}
	}
	
	
	/**
	 * @Description 根据Eo列表导出
	 * @CreateTime 2017-12-12 下午9:43:25
	 * @CreateBy 刘邓
	 * @param engineeringOrder
	 *            EO
	 * @return
	 * @throws BusinessException
	 */

	@RequestMapping(value = "EOChapter.xls", method = RequestMethod.GET)
	public String exportEoList(String params,Model model) throws BusinessException {
		try {    
			EngineeringOrder order=  JSON.parseObject(params, EngineeringOrder.class);
		    List<EngineeringOrder>  list=engineeringOrderService.exportEoList(order);
		    JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
		    model.addAttribute("jrMainDataSource", jrDataSource);
		return outReport("xls", "common", "EoChapter", model);
		} catch (Exception e) {
			throw new BusinessException("EO列表导出失败", e);
		}
	}
	
	
	

	private String formatNumberNull(Object object) {
		return object==null?"999999999":object.toString();
	}
	private String formatNull(Object object) {
		return object==null?"":object.toString();
	}
	/**
	 * 
	 * @Description EO导出pdf
	 * @CreateTime 2018年3月13日 上午11:40:11
	 * @CreateBy 岳彬彬
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "eo")
	public String export(String id, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			model.addAttribute("id", id);
			List<EOMonitorIemSet> setList= eoMonitorIemSetService.queryByMainid(id);
			StringBuffer jkx = new StringBuffer("");
			StringBuffer zqz = new StringBuffer("");
			if(null != setList && setList.size()>0){
				for (EOMonitorIemSet eoMonitorIemSet2 : setList) {
					String jklbh = eoMonitorIemSet2.getJklbh();
					String sc = eoMonitorIemSet2.getScz();
					String zq = eoMonitorIemSet2.getZqz();
					if(null != jklbh && MonitorProjectEnum.isTime(jklbh)){
						sc = StringAndDate_Util.convertToHour(sc);
						zq = StringAndDate_Util.convertToHour(zq);
					}
					if(!StringUtils.isBlank(sc)){
						if(null != eoMonitorIemSet2.getDwScz()){
							jkx.append(sc).append(MonitorProjectEnum.getUnit(jklbh,eoMonitorIemSet2.getDwScz().toString())).append(",");
						}else{
							jkx.append(sc).append(",");
						}
					}
					if(!StringUtils.isBlank(zq)){
						if(null != eoMonitorIemSet2.getDwZqz()){
							zqz.append(zq).append(MonitorProjectEnum.getUnit(jklbh,eoMonitorIemSet2.getDwZqz().toString())).append(",");
						}else{
							zqz.append(zq).append(",");
						}
						
					}
				}
				model.addAttribute("jkx", StringUtils.isBlank(jkx.toString())?"":jkx.substring(0,jkx.length()-1));
				model.addAttribute("zqz",  StringUtils.isBlank(zqz.toString())?"":zqz.substring(0,zqz.length()-1));
			}
			User user = ThreadVarUtil.getUser();
			DeptInfo DeptInfo = deptInfoService.selectById(user.getJgdm());
			String imagePath = "zx.jpg";
			if(null != DeptInfo){
				if("145".equals(DeptInfo.getDeptType())){
					imagePath = "hx.jpg";
				}
			}
			String subreport_dir = request.getSession().getServletContext().getRealPath("/WEB-INF/views/reports/common");
			String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
			String wz = subreport_dir.substring(0, 2);
			model.addAttribute("subreport_dir", subreport_dir);			
			model.addAttribute("rootPath", wz.concat(rootPath));
			String path = request.getSession().getServletContext().getRealPath("/static/images/report");
			model.addAttribute("images_path", path.concat(File.separator).concat(imagePath));
			return outReport("pdf", "-1", "EO", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("预览或导出失败");
		}

	}

}
