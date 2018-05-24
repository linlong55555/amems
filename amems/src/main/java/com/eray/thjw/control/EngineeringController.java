package com.eray.thjw.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.DetailEngineering;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.service.DetailEngineeringService;
import com.eray.thjw.service.EngineeringService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.service.WorkOrderService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.JobCardStatusEnum;
import enu.ordersource.OrderSourceEnum;

@Controller
@RequestMapping("/project/engineering")
public class EngineeringController extends BaseController {

	@Autowired
	private EngineeringService engineeringService;
	@Autowired
	private TechnicalFileService technicalFileService;
	@Autowired
	private DetailEngineeringService detailEngineeringService;
	@Autowired
	private WorkOrderService workOrderService;
	
	/**
	 * @author sunji
	 * @description 跳转至工程指令页面
	 * @param
	 * @return 页面视图
	 * @develop date 2016.08.26
	 *
	 */
	@Privilege(code="project:engineering:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public String main(Model model) {
		return "project/engineering/engineeringMain";
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 页面视图system/user/userMain.jsp
	 * @develop date 2016.08.05
	 */
	@ResponseBody
	@RequestMapping(value = "queryEngineeringList", method = RequestMethod.POST)
	public Object queryEngineeringList(@RequestBody Engineering engineering,HttpServletRequest request,Model model)throws BusinessException{
		return engineeringService.queryAllEngineering(engineering);
	}
	
	
	
	/**
	 * 初始化增加工程指令
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="project:engineering:main:01")
	@RequestMapping("intoAddEngineering")
	public ModelAndView intoAddEngineering(HttpServletRequest request,HttpServletResponse response) throws BusinessException {
		return new ModelAndView("project/engineering/add_engineering");
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令（修改）
	 * @param model,id
	 * @return 页面视图
	 * @throws BusinessException 
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:engineering:main:02")
	@RequestMapping("intoEditMainEngineering")
	public String intoEditMainEngineering(Model model,@RequestParam String id,@RequestParam String dprtcode
			) throws BusinessException {
		Engineering engineering=engineeringService.getByPrimaryKey(id);
		if(engineering==null){
			throw new BusinessException("查询失败");
		}
		model.addAttribute("engineering", engineering);
		//判断状态是否是保存或者是提交，分别返回到不同的页面
		if(engineering.getZt()==OrderSourceEnum.NOT_EVALUATED.getId()
		|| engineering.getZt()==OrderSourceEnum.REVIEW_THE_REJECTED.getId() 
		|| engineering.getZt()==OrderSourceEnum.APPROVE_TURN_.getId()){
			return "project/engineering/edit_engineering_bc";
		}else{
			return "project/engineering/edit_engineering_tj";
			
		}
	}
	
	
	/**
	 * 跳转至查看评估单
	 * 
	 * @return 页面视图
	 */
	@ResponseBody
	@RequestMapping(value = "selectPgdList", method = RequestMethod.POST)
	public Object selectPgdList(@RequestBody TechnicalFile technicalFile,HttpServletRequest request,Model model){
		return technicalFileService.queryAllByDocumentsId(technicalFile);
	}
	/**
	 * 新增工程指令
	 * 
	 * @return 页面视图
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "saveMainEngineering", method = RequestMethod.POST)
	public String saveMainEngineering(@RequestBody Engineering engineering,HttpServletRequest request,Model model) throws BusinessException{
		try {
			String id=engineeringService.saveEngineering(engineering);
			return id;
		} catch (BusinessException  e) {
			throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			throw new BusinessException("数据添加失败",e);
		}
	}
	/**
	 * 验证工程指令编号是否唯一
	 * 
	 * @return resultMap
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "validationEngineeringBH", method = RequestMethod.POST)
	public Object validationEngineeringBH(@RequestParam String gczlbh,@RequestParam String dprtcode,HttpServletRequest request) throws BusinessException{
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
		Engineering engineering=new Engineering();
		engineering.setGczlbh(gczlbh);
		engineering.setDprtcode(dprtcode);
		int i =engineeringService.validationQueryCount(engineering);
		resultMap.put("total", i);
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
		return resultMap;
	}
	/**
	 * 修改工程指令
	 * 
	 * @return 页面视图
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updateMainEngineeringBc", method = RequestMethod.POST)
	public String updateMainEngineeringBc(@RequestBody Engineering engineering,HttpServletRequest request,Model model) throws BusinessException{
		try {
			int[] i={OrderSourceEnum.NOT_EVALUATED.getId(),
					OrderSourceEnum.REVIEW_THE_REJECTED.getId(),
					OrderSourceEnum.APPROVE_TURN_.getId()};
			engineeringService.updateEngineering(engineering, i);
			return engineering.getId();
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令（审核）
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:engineering:main:05")
	@RequestMapping("intoShenheMainEngineering")
	public String intoShenheMainEngineering(Model model,@RequestParam String id,@RequestParam String dprtcode) {
		Engineering engineering=engineeringService.getByPrimaryKey(id);
		model.addAttribute("engineering", engineering);
		//判断状态是否是保存或者是提交，分别返回到不同的页面
			return "project/engineering/shenhe_engineering";
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令（批复）
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@Privilege(code="project:engineering:main:06")
	@RequestMapping("intoPifuMainEngineering")
	public String intoPifuMainEngineering(Model model,@RequestParam String id,@RequestParam String dprtcode
			) {
		Engineering engineering=engineeringService.getByPrimaryKey(id);
		model.addAttribute("engineering", engineering);
		return "project/engineering/pifu_engineering";
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（审核提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "submitshenheMainEngineering", method = RequestMethod.POST)
	public String submitshenheMainEngineering(@RequestBody Engineering engineering,HttpServletRequest request) throws BusinessException{
		try {
			engineeringService.updateshenheEngineering(engineering);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return engineering.getId();
	}
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（批复提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @throws BusinessException 
	 * @develop date 2016.08.18
	 */
	@ResponseBody
	@RequestMapping(value = "submitpifuMainEngineering", method = RequestMethod.POST)
	public String submitpifuMainInstruction(@RequestBody Engineering engineering,HttpServletRequest request) throws BusinessException{
		try {
			engineeringService.updatepifuEngineering(engineering);
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
		return engineering.getId();
	}
	/**
	 * @author sunji
	 * @description 作废
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("deleteEngineering")
	@Privilege(code="project:engineering:main:03")
	public Map<String, Object> deleteEngineering(HttpServletRequest request,String id
			) throws Exception {
		Map<String, Object> resultMap = engineeringService.deleteEngineering(id);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 关闭(指定结束)
	 * @param model,id
	 * @return 页面视图
	 * @throws Exception 
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("offEngineering")
	@Privilege(code="project:engineering:main:04")
	public Map<String, Object> offEngineering(@RequestBody Engineering engineering, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap = engineeringService.offEngineering(engineering);
		return resultMap;
	}
	/**
	 * @author sunji
	 * @description 检查工程指令修改权限
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("checkUpdMt")
	public Map<String, Object> checkUpdMt(HttpServletRequest request,@RequestParam String id) {
		return engineeringService.checkUpdMt(id);
	}
	/**
	 * @author sunji
	 * @description 查询已选择的评估单
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@ResponseBody
	@RequestMapping("selectChoosePgd")
	public Map<String, Object> selectChoosePgd(HttpServletRequest request,@RequestParam String id
			) {
		Map <String, Object > resultMap =new HashMap<String, Object>();
		//根据id获取已选择的评估单
		List<TechnicalFile> tchnicalFileList =engineeringService.selectChoosePgd(id);
		
		resultMap.put("tchnicalFileList", tchnicalFileList);
		return resultMap;
	}
	/**
	 * @author 查询受影响的列
	 * @description 
	 * @param djbh,model
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	@ResponseBody
	@RequestMapping(value = "queryListBymainid",method={RequestMethod.POST,RequestMethod.GET})
	public List<DetailEngineering> queryListBymainid(Model model,String mainid) throws BusinessException {
		List<DetailEngineering> list = null;
		try {
			list = detailEngineeringService.selectByPrimaryKeyMainid(mainid);
			if(list.size()>0){
				List<WorkOrder> workOrderList=workOrderService.queryAllByDetailEngineeringId();
				// 以mainid为键，指令编号为value，放入map集合
				Map<String, String> map = new HashMap<String, String>();
				
				for (WorkOrder workOrder : workOrderList) {
					// 判断键是否存在，如果存在，那么拼接
					if (map.containsKey(workOrder.getGczlzxdxid())) {
						map.put(workOrder.getGczlzxdxid(),
								map.get(workOrder.getGczlzxdxid()) + "," + workOrder.getEoidAndGdbhAndztText());
					} else {
						map.put(workOrder.getGczlzxdxid(), workOrder.getEoidAndGdbhAndztText());
					}
				}
				for (DetailEngineering detailEngineering : list) {
					detailEngineering.setScgd(map.get(detailEngineering.getId()));
				}
			}
		} catch (Exception e) {
			throw new BusinessException("获取受影响列失败!",e);
		}
		
		return list;
	}
	/**
	 * @author sunji
	 * @description 初始化技术指令(查询)
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	@RequestMapping("intoViewMainEngineering")
	public String intoViewMainEngineering(Model model,@RequestParam String id,@RequestParam String dprtcode){
		Engineering engineering=engineeringService.getByPrimaryKey(id);
		model.addAttribute("engineering", engineering);
		return "project/engineering/view_engineering";
			
	}
	/**
	 * @author meizhiliang
	 * @description 选择EO工单的工程指令
	 * @develop date 2016.10.10
	 */
	@ResponseBody
	@RequestMapping(value = "queryEnginer", method = RequestMethod.POST)
	public Map<String, Object> queryEnginer(@RequestBody Engineering engineering, HttpServletRequest request,
			Model model) {
		PageHelper.startPage(engineering.getPagination());
		List<Engineering> list = engineeringService.queryEngineeringPageList(engineering);
		return PageUtil.pack4PageHelper(list, engineering.getPagination());
	}
	/**
	 * @author meizhiliang
	 * @description 选择EO工单的工程指令的执行对象明细
	 * @develop date 2016.10.10
	 */
	@ResponseBody
	@RequestMapping(value = "queryDetailEnginer", method = RequestMethod.POST)
	public Map<String, Object> queryDetailEnginer(Model model ,@RequestBody DetailEngineering detailEngineering) throws BusinessException{
			Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			resultMap.put("rows",detailEngineeringService.selectByPrimaryKeyMainid(detailEngineering.getMainid()));
		} catch (Exception e) {
			throw new BusinessException("获取受影响列失败!",e);
		}finally{
			
		}
		return resultMap;
	}
	/**
	 * 修改工程指令（提交）
	 * 
	 * @return 页面视图
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value = "updateMainEngineering", method = RequestMethod.POST)
	public String updateMainEngineering(@RequestBody Engineering engineering,HttpServletRequest request,Model model) throws BusinessException{
		try {
			int i[]={OrderSourceEnum.NOT_EVALUATED.getId()
					,OrderSourceEnum.EVALUATED.getId()
					,OrderSourceEnum.CHECKED.getId()
					,OrderSourceEnum.APPROVE.getId()
					,OrderSourceEnum.REVIEW_THE_REJECTED.getId()
					,OrderSourceEnum.APPROVE_TURN_.getId()};
			engineeringService.updateEngineering(engineering,i);
			return engineering.getId();
		} catch (BusinessException e) {
			 throw new BusinessException(e.getMessage(),e);
		} catch (Exception e) {
			 throw new BusinessException("操作失败",e);
		}
	}

	
	@Privilege(code="project:engineering:main:07")
	@RequestMapping(value="/export/{type}/{id}")
	public String export(@PathVariable String id,@PathVariable String type,HttpServletRequest request, Model model) throws BusinessException {
		try {
			Engineering engineering = engineeringService.getByPrimaryKey(id);
			if(engineering==null){
				throw new BusinessException("技术指令查询失败");
			}
			engineering.setZtText(JobCardStatusEnum.getName(engineering.getZt()));
		    model.addAttribute("id", id);
		    String tp_id = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		    model.addAttribute("tp_id", tp_id);
		    if (SysContext.FTP_ENABLED) {
		    	model.addAttribute("tp_ftpurl", SysContext.getFtpUrl());
			}
		    
		    String logoPath = "http://".concat(request.getServerName()).concat(":")
					.concat(String.valueOf(request.getServerPort()))
					.concat(request.getContextPath())
					.concat("/static/images/report");
			model.addAttribute("logo_path", logoPath);
			
			//设置子报表与主报表同目录 开始
			String SUBREPORT_DIR = request.getRealPath("/WEB-INF/views/reports/common/");
			StringBuffer filepath = new StringBuffer();
			filepath.append("/WEB-INF/views/reports/");
			filepath.append(engineering.getDprtcode());
			filepath.append("/");
			filepath.append("Engineering");
			filepath.append(".jasper");
			
			String path = getClass().getResource("/").getPath();
			path = path.substring(1, path.indexOf("classes"));
			File file = new File(path.concat(filepath.substring(8).toString()));  
			
		    if(file.exists()){
		    	SUBREPORT_DIR = request.getRealPath("/WEB-INF/views/reports/".concat(engineering.getDprtcode().concat("/")));
		    }
		    model.addAttribute("SUBREPORT_DIR", SUBREPORT_DIR.concat(File.separator));
		    
			//设置子报表与主报表同目录 结束
		    return outReport(type, engineering.getDprtcode(), "Engineering", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
	}
	
	@RequestMapping(value = "/downfile")
	public void downfile(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {
		try {
			InputStream is = new FileInputStream("C:\\Users\\Eray\\Desktop\\baidu_jgylogo3.jpg");
			response.setContentType("image/jpeg");
			FileCopyUtils.copy(is, response.getOutputStream());
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("操作失败");
		}
		
		
		 
	}
	/**
	 * @author sunji
	 * @description 批量审核
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:engineering:main:05")
	@ResponseBody
	@RequestMapping(value = "batchReview", method = RequestMethod.POST)
	public String batchReview(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return engineeringService.updateBatchReview(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 批量批准
	 * @param request,idList
	 * @return List<Map<String, Object>>
	 * @develop date 2016.10.13
	 */
	@Privilege(code="project:engineering:main:06")
	@ResponseBody
	@RequestMapping(value = "batchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]")List<String> idList,String yj)throws BusinessException {
		try {
			return engineeringService.updateBatchApprove(idList,yj);
		} catch (Exception e) {
			throw new BusinessException("批量批准失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 根据id查询eo工卡
	 * @develop date 2016.10.10
	 */
	@ResponseBody
	@RequestMapping(value = "selectbyEoId", method = RequestMethod.POST)
	public Map<String, Object> selectbyEoId(
			@RequestParam String id, HttpServletRequest request,
			Model model)throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
		resultMap.put("total", engineeringService.queryCountbyEoId(id));
		} catch (Exception e) {
			throw new BusinessException("操作失败",e);
		}
		return resultMap;
	}
	
	/** 
	 * @author liub
	 * @description 根据评估单id查询EO指令数据
	 * @param pgdid
	 * @return List<Annunciate>
	 * @develop date 2017.03.14
	 */
	@ResponseBody
	@RequestMapping(value = "queryByPgdId", method = RequestMethod.POST)
	public List<Engineering> queryByPgdId(String pgdid)throws BusinessException {
		try {
			return engineeringService.queryByPgdId(pgdid);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
}
