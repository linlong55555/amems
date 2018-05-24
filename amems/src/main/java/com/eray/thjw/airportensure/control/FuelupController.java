package com.eray.thjw.airportensure.control;

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

import com.eray.thjw.airportensure.po.Fuelup;
import com.eray.thjw.airportensure.service.FuelupService;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.PlaneBase;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.service.PlaneDataService;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

/**
 * 
 * @author 林龙
 * @description 飞机加油单控制器
 */
@Controller
@RequestMapping("/airportensure/fuelup")
public class FuelupController extends BaseController {
	
	@Resource
	private FuelupService fuelupService;
	
	@Resource
	private PlaneBaseService planeBaseService;
	
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private PlaneDataService planeDataService;
	
	/**
	 * 跳转至借调归还管理页面
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="airportensure:fuelup:main")
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		String sjmd = GlobalConstants.OIL_DENSITY_KEY;
		User user=ThreadVarUtil.getUser();
		PlaneBase planeBase=new PlaneBase();
		planeBase.setDprtcode(user.getJgdm());
		model.put("sjmd", sjmd);
		model.put("baseList", planeBaseService.findAllBase(planeBase));
		model.put("typeList", planeModelDataService.findAllType(user.getJgdm()));
		model.put("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		return new ModelAndView("/airportensure/fuelup/fuelup_main", model);
	}
	
	/**
	 * @author ll
	 * @description 
	 * @param request,model
	 * @return 飞机加油单列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "fuelupList", method = RequestMethod.POST)
	public Map<String, Object> fuelupList(@RequestBody Fuelup fuelup,HttpServletRequest request,Model model) throws BusinessException{
		String id = fuelup.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		fuelup.setId(null);
		PageHelper.startPage(fuelup.getPagination());
		List<Fuelup> list = fuelupService.queryAllPageList(fuelup);
		if(((Page)list).getTotal() > 0){
			
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					Fuelup fuelup1 = new Fuelup();
					fuelup1.setId(id);
					List<Fuelup> newRecordList = fuelupService.queryAllPageList(fuelup1);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, fuelup.getPagination());
		}else{
			List<Fuelup> newRecordList = new ArrayList<Fuelup>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				Fuelup fuelup1 = new Fuelup();
				fuelup1.setId(id);
				newRecordList = fuelupService.queryAllPageList(fuelup1);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, fuelup.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, fuelup.getPagination());
		}
	}
	
	/**
	 * @author ll
	 * @description 新增
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:fuelup:main:01")
	@ResponseBody
	@RequestMapping(value = "saveFulup", method = RequestMethod.POST)
	public void saveFulup(@RequestBody Fuelup fuelup) throws BusinessException{
		try {
			
			fuelupService.save(fuelup);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 修改
	 * @param reserve
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:fuelup:main:02")
	@ResponseBody
	@RequestMapping(value = "editFulup", method = RequestMethod.POST)
	public void editFulup(@RequestBody Fuelup fuelup) throws BusinessException{
		try {
			
			fuelupService.update(fuelup);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
	}
	
	/**
	 * @author ll
	 * @description 作废工作记录
	 * @param request,id
	 * @return
	 * @develop date 2016.10.14
	 * @throws BusinessException
	 */
	@Privilege(code="airportensure:fuelup:main:03")
	@ResponseBody
	@RequestMapping(value = "cancelFuelup", method = RequestMethod.POST)
	public void cancelFuelup(HttpServletRequest request,String id) throws BusinessException{
		try {
			fuelupService.cancel(id);
		} catch (Exception e) {
			 throw new BusinessException(e.getMessage());
		}
	}
	
	
	
	
	
	/**
	 * 飞机加油统计列表
	 * @return 
	 * @throws Exception 
	 */
	@Privilege(code="airportensure:fuelup:statistics")
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public ModelAndView statistics(HttpServletRequest request) throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		String sjmd = GlobalConstants.OIL_DENSITY_KEY;
		User user=ThreadVarUtil.getUser();
		PlaneBase planeBase=new PlaneBase();
		planeBase.setDprtcode(user.getJgdm());
		model.put("sjmd", sjmd);
		model.put("baseList", planeBaseService.findAllBase(planeBase));
		model.put("typeList", planeModelDataService.findAllType(user.getJgdm()));
		model.put("planeData", new Gson().toJson(planeDataService.selectByDprtcodeList(SessionUtil.getDprtcodeList(request))));
		return new ModelAndView("/airportensure/fuelup/fuelup_statistics", model);
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 飞机加油单明细列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "fuelupStatisticsDetailList", method = RequestMethod.POST)
	public Map<String, Object> fuelupStatisticsDetailList(@RequestBody Fuelup fuelup,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> resultMap = this.fuelupService.queryfuelupDetailList(fuelup);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 发油人加油单明细列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "fuelupFyrDetailList", method = RequestMethod.POST)
	public Map<String, Object> fuelupFyrDetailList(@RequestBody Fuelup fuelup,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> resultMap = this.fuelupService.queryfuelupFyrDetailList(fuelup);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 飞机加油单汇总列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "fuelupSummaryList", method = RequestMethod.POST)
	public Map<String, Object> fuelupSummaryList(@RequestBody Fuelup fuelup,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> resultMap = this.fuelupService.queryfuelupSummaryList(fuelup);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 飞机加油单汇总列表
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping(value = "fuelupFyrSummaryList", method = RequestMethod.POST)
	public Map<String, Object> fuelupFyrSummaryList(@RequestBody Fuelup fuelup,HttpServletRequest request,Model model) throws BusinessException{
		try {
			Map<String, Object> resultMap = this.fuelupService.fuelupFyrSummaryList(fuelup);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
	}
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return 根据组织机构查询基地
	 * @throws Exception 
	 * @develop date 2016.07.25
	 */
	@ResponseBody
	@RequestMapping("planeBaseChange")
	public Map<String, Object> planeBaseChange(HttpServletRequest request,String dprtcode
			) throws BusinessException {
		try {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			List<PlaneBase> planeBaseList=planeBaseService.findBaseByDprtcode(dprtcode);
			resultMap.put("planeBaseList", planeBaseList);
			return resultMap;
		} catch (Exception e) {
			throw new BusinessException("查询失败",e);
		}
		
	}
}
