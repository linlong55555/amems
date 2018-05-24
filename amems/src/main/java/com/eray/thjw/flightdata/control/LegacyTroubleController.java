package com.eray.thjw.flightdata.control;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.LegacyTrouble;
import com.eray.thjw.flightdata.service.FlightRecordSheetService;
import com.eray.thjw.flightdata.service.LegacyTroubleService;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.PlaneData;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.service.UserService;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;

import enu.BinaryEnum;
import enu.LegacyTroubleStatusEnum;
import enu.MelTypeEnum;
import enu.PlanTaskSubType;
import enu.PlanTaskType;
import enu.WorkOrderStateEnum;

/**
 * 
 * @author zhuchao
 * @description 故障保留单控制器
 */
@Controller
@RequestMapping("/flightdata/legacytrouble")
public class LegacyTroubleController extends BaseController {

	@Resource
	private LegacyTroubleService legacyTroubleService;

	@Resource
	private PlaneDataService planeDataService;

	@Resource
	private FixChapterService fixChapterService;

	@Resource
	private UserService userService;

	@Resource
	private FlightRecordSheetService flightRecordSheetService;

	/**
	 * 跳转至部件履历管理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "flightdata:legacytrouble:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage(HttpServletRequest request, LegacyTrouble legacytrouble, Model model)
			throws BusinessException {

		List<PlaneData> planes = planeDataService.queryList(new PlaneData());
		User currUser = ThreadVarUtil.getUser();
		User user = new User();
		user.setJgdm(currUser.getJgdm());
		List<User> users = userService.queryUserAll(user);
		model.addAttribute("users", users);
		model.addAttribute("planes", planes);
		model.addAttribute("binaryEnum", BinaryEnum.enumToListMap());
		model.addAttribute("legacyTroubleStatusEnum", LegacyTroubleStatusEnum.enumToListMap());
		model.addAttribute("melTypeEnum", MelTypeEnum.enumToListMap());
		return new ModelAndView("flightdata/legacytrouble/legacytrouble_manage");

	}
	
	/**
	 * 跳转至质量管理的部件履历管理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "flightdata:legacytrouble:manage1")
	@RequestMapping(value = "/manage1", method = RequestMethod.GET)
	public ModelAndView manage1(HttpServletRequest request, LegacyTrouble legacytrouble, Model model)
			throws BusinessException {

		List<PlaneData> planes = planeDataService.queryList(new PlaneData());
		User currUser = ThreadVarUtil.getUser();
		User user = new User();
		user.setJgdm(currUser.getJgdm());
		List<User> users = userService.queryUserAll(user);
		model.addAttribute("users", users);
		model.addAttribute("planes", planes);
		model.addAttribute("binaryEnum", BinaryEnum.enumToListMap());
		model.addAttribute("legacyTroubleStatusEnum", LegacyTroubleStatusEnum.enumToListMap());
		model.addAttribute("melTypeEnum", MelTypeEnum.enumToListMap());
		return new ModelAndView("flightdata/legacytrouble/legacytrouble_manage");

	}

	/**
	 * 查询部件履历
	 * @param request
	 * @param legacytrouble
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryList", method = { RequestMethod.POST })
	public Map<String, Object> queryList(HttpServletRequest request, @RequestBody LegacyTrouble legacytrouble)
			throws BusinessException {
		try {
			legacytrouble.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
			Map<String, Object> result = legacyTroubleService.queryList(legacytrouble);
			return result;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询故障保留单失败", e);
		}
	}

	@Privilege(code = "flightdata:legacytrouble:manage:01,flightdata:legacytrouble:manage1:01")
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public ModelAndView add(HttpServletRequest request, Model model) throws BusinessException {
		return new ModelAndView("flightdata/legacytrouble/legacytrouble_add");
	}
	

	@Privilege(code = "flightdata:legacytrouble:manage:01")
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public Map<String, Object> add(HttpServletRequest request, @RequestBody LegacyTrouble legacyTrouble) throws BusinessException {
		Map<String, Object> result =legacyTroubleService.addValidate(legacyTrouble);
		return result;
	}
	
	/**
	 * 验证故障保留单号机构内唯一
	 * @param request
	 * @param legacyTrouble
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/validatorGzbldh", method = { RequestMethod.POST })
	public Map<String, Object> validatorGzbldh(HttpServletRequest request, @RequestBody LegacyTrouble legacyTrouble) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean isLegitimate = legacyTroubleService.validatorGzbldh(legacyTrouble);
		result.put("isLegitimate", isLegitimate);
		return result;
	}

	/**
	 * 提交故障保留单
	 * @param request
	 * @param legacyTrouble
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/addCommitted", method = { RequestMethod.POST })
	public Map<String, Object> aadCommitted(HttpServletRequest request, @RequestBody LegacyTrouble legacyTrouble)
			throws BusinessException {
		legacyTrouble.setZt(LegacyTroubleStatusEnum.COMMITTED.getId().toString());
		Map<String, Object> result = legacyTroubleService.addValidate(legacyTrouble);
		return result;
	}

	/**
	 * 修改故障保留单
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "flightdata:legacytrouble:edit,flightdata:legacytrouble:manage1:02")
	@RequestMapping(value = "/edit", method = { RequestMethod.GET })
	public ModelAndView edit(HttpServletRequest request, Model model, String id) throws BusinessException {
		 
		LegacyTrouble legacyTrouble = legacyTroubleService.load(id);
		model.addAttribute("legacyTrouble", legacyTrouble);
		return new ModelAndView("flightdata/legacytrouble/legacytrouble_edit");
	}

	/**
	 * 进入故障保留单详细页面
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/view", method = { RequestMethod.GET })
	public ModelAndView view(HttpServletRequest request, Model model, String id) throws BusinessException {
		LegacyTrouble legacyTrouble = legacyTroubleService.load(id);
		model.addAttribute("legacyTrouble", legacyTrouble);
		return new ModelAndView("flightdata/legacytrouble/legacytrouble_view");
	}

	/**
	 * 编辑故障保留单
	 * @param request
	 * @param legacyTrouble
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "flightdata:legacytrouble:edit,flightdata:legacytrouble:manage1:02")
	@ResponseBody
	@RequestMapping(value = "/edit", method = { RequestMethod.POST })
	public Map<String, Object> edit(HttpServletRequest request, @RequestBody LegacyTrouble legacyTrouble) throws BusinessException {
		
		legacyTrouble.getParamsMap().put("action", LegacyTroubleStatusEnum.SAVED);
		Map<String, Object> result = legacyTroubleService.editValidate(legacyTrouble);
		return result;
	}

	/**
	 * 编辑提交故障保留单
	 * @param request
	 * @param legacyTrouble
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/editCommitted", method = { RequestMethod.POST })
	public Map<String, Object> editCommitted(HttpServletRequest request, @RequestBody LegacyTrouble legacyTrouble)
			throws BusinessException {
		
		legacyTrouble.getParamsMap().put("action", LegacyTroubleStatusEnum.COMMITTED);
		legacyTrouble.setZt(LegacyTroubleStatusEnum.COMMITTED.getId().toString());
		Map<String, Object> result = legacyTroubleService.editValidate(legacyTrouble);
		return result;
	}

	/**
	 * 进入再次故障保留单页面
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "flightdata:legacytrouble:resave,flightdata:legacytrouble:manage1:04")
	@RequestMapping(value = "/resave", method = { RequestMethod.GET })
	public ModelAndView resave(HttpServletRequest request, Model model, String id) throws BusinessException {
		
		LegacyTrouble legacyTrouble = legacyTroubleService.load(id);
		model.addAttribute("legacyTrouble", legacyTrouble);
		return new ModelAndView("flightdata/legacytrouble/legacytrouble_resave");
	}

	/**
	 * 再次故障保留单
	 * @param request
	 * @param legacyTrouble
	 * @throws BusinessException
	 */
	@Privilege(code = "flightdata:legacytrouble:resave,flightdata:legacytrouble:manage1:04")
	@ResponseBody
	@RequestMapping(value = "/resave", method = { RequestMethod.POST })
	public void resave(HttpServletRequest request, @RequestBody LegacyTrouble legacyTrouble) throws BusinessException {

		legacyTroubleService.edit(legacyTrouble);
	}

	/**
	 * 生成工单
	 * 
	 * @param request
	 * @param legacyTrouble
	 * @throws BusinessException
	 */
	@Privilege(code = "flightdata:legacytrouble:generateorder,flightdata:legacytrouble:manage1:05")
	@ResponseBody
	@RequestMapping(value = "/generateOrder", method = { RequestMethod.POST })
	public void generateOrder(HttpServletRequest request, LegacyTrouble legacyTrouble) throws BusinessException {
		
		legacyTroubleService.generateOrder(legacyTrouble);
	}

	/**
	 * 作废一个故障保留单
	 * @param request
	 * @param legacyTrouble
	 * @throws BusinessException
	 */
	@Privilege(code = "flightdata:legacytrouble:cancel,flightdata:legacytrouble:manage1:03")
	@ResponseBody
	@RequestMapping(value = "/cancel", method = { RequestMethod.POST })
	public void cancel(HttpServletRequest request, LegacyTrouble legacyTrouble) throws BusinessException {

		legacyTroubleService.doCancel(legacyTrouble);
	}

	@Privilege(code = "flightdata:legacytrouble:end")
	@ResponseBody
	@RequestMapping(value = "/end", method = { RequestMethod.POST })
	public void end(HttpServletRequest request, LegacyTrouble legacyTrouble) throws BusinessException {
		
		legacyTroubleService.doEnd(legacyTrouble);
	}

	/**
	 * 技术质量导出，预览<br>
	 * 
	 * @todo html 预览少图片，xls导出时虚线。 预览：html<br>
	 *       预览：pdf<br>
	 *       导出:xls<br>
	 * @param id
	 *            (主键)
	 * @param type
	 *            （pdf,xls,html）
	 * @param request
	 * @param model
	 * @return
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "/export/{type}/{id}")
	public String export(@PathVariable String id, @PathVariable String type, HttpServletRequest request,
			Model model) throws BusinessException {

		try {
			LegacyTrouble legacyTrouble = legacyTroubleService.load(id);
			model.addAttribute("id", id);
			return outReport(type, legacyTrouble.getDprtcode(), "LegacyTrouble", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}
	}
	
	
	
	
	/**
	 * 导出excel
	 * @param keyword
	 * @param dprtcode
	 * @param fjzch
	 * @param isM
	 * @param mel
	 * @param zt
	 * @param gbrq
	 * @param sqrq
	 * @param dqrq
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "legacyTroubleoutExcel.xls")
	public String ypoutExcel(LegacyTrouble legacyTrouble, HttpServletRequest request, Model model)
			throws BusinessException {
		try {
			legacyTrouble.getParamsMap().put("userId", SessionUtil.getUserFromSession(request).getId());
			legacyTrouble.getParamsMap().put("userType", SessionUtil.getUserFromSession(request).getUserType());
			Map<String, Object> result = legacyTroubleService.queryList(legacyTrouble);
			List<LegacyTrouble> list1 = (List<LegacyTrouble>) result.get("rows");
			for (LegacyTrouble legacyTrouble2 : list1) {
				if(legacyTrouble2.getGdxx()!=null&&!legacyTrouble2.getGdxx().equals("")){
					String	lt=legacyTrouble2.getGdxx();
					String gd[]=lt.split(",");
					StringBuffer sbf=new StringBuffer("");
					for (int i = 0; i < gd.length; i++) {						
						sbf.append(PlanTaskType.getName(Integer.valueOf(gd[i].split("##")[0])));
						sbf.append("-");
						sbf.append(PlanTaskSubType.getName(Integer.valueOf(gd[i].split("##")[1])));
						sbf.append(" ");
						sbf.append(gd[i].split("##")[2]);
						sbf.append("-");
						sbf.append(WorkOrderStateEnum.getName(Integer.valueOf(gd[i].split("##")[4])));
						sbf.append(",");
					}
					legacyTrouble2.setOutgdxx(sbf.toString());
				}
			}
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list1);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "legacytroubleOutExcel", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("报表预览或导出失败");
		}

	}
	 
}
