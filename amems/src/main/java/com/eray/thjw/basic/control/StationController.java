package com.eray.thjw.basic.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.basic.dao.ZoneStationMapper;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.basic.service.ZoneStationService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.productionplan.po.LoadingList;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.po.TrainingPlanExportExcel;

import enu.course.CycleEnum;
import enu.training.TrainingPlanStatusEnum;
import enu.training.TrainingPlanTypeEnum;



/**
 * @Description 飞机站位管理 控制器
 * @CreateTime 2017年8月18日 下午3:18:04
 * @CreateBy 徐勇
 */
@Controller
@RequestMapping("/basic/station")
public class StationController extends BaseController {

	@Resource
	private ZoneStationService zoneStationService;
	
	@Resource(name="stationExcelImporter")
	private BaseExcelImporter<LoadingList> excelImporter;

	
	/**
	 * 
	 * @Description 根据机型和组织机构查询数据
	 * @CreateTime 2017-8-22 上午10:45:24
	 * @CreateBy 孙霁
	 * @param position
	 * @param request
	 * @param model
	 * @return map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectByJx", method = RequestMethod.POST)
	public Map<String, Object> selectByJx(@RequestBody ZoneStation zoneStation,HttpServletRequest request,Model model)throws BusinessException{
		try {
			return  zoneStationService.selectByJx(zoneStation);
		} catch (Exception e) {
			throw new BusinessException("查询失败！", e);
		}
	}

	/**
	 * 
	 * @Description 返回列表，分特
	 * @CreateTime 2017年8月18日 下午6:43:38
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 */
	@Privilege(code = "basic:station:main")
	@ResponseBody
	@RequestMapping(value = "getZoneStationList", method = RequestMethod.POST)
	public Map<String, Object> getZoneStationList(@RequestBody ZoneStation zoneStation) {		
		return zoneStationService.getStations(zoneStation);
	}

	/**
	 * 
	 * @Description 返回station_main
	 * @CreateTime 2017年8月18日 下午6:53:24
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return 
	 */
	@Privilege(code = "basic:station:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(ZoneStation zoneStation) {
		return new ModelAndView("basic/zonestation/station_main");
	}

	/**
	 * 
	 * @Description 添加操作
	 * @CreateTime 2017年8月25日 下午6:05:49
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return void
	 * @throws BusinessException 
	 * @throws Exception
	 */
	@Privilege(code = "basic:station:main:add")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void addStation(@RequestBody ZoneStation zoneStation) throws BusinessException {		
		try{
		     zoneStationService.addStation(zoneStation);
		}catch(BusinessException e){
			 throw e;
		}catch(Exception e){
			 throw new BusinessException("添加飞机站位失败！",e);
		}
	}
	
	/**
	 * 
	 * @Description 编辑操作
	 * @CreateTime 2017年8月25日 下午6:06:05
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return void
	 * @throws Exception
	 */
	@Privilege(code = "basic:station:main:edit")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void updateStation(@RequestBody ZoneStation zoneStation) throws BusinessException {				
		try{
		     zoneStationService.updateStation(zoneStation);
		}catch(Exception e){
			 throw new BusinessException("修改飞机站位失败！",e);
		}
	}
	
	/**
	 * 
	 * @Description 逻辑删除
	 * @CreateTime 2017年8月25日 下午6:06:27
	 * @CreateBy 李高升
	 * @param map
	 * @return void
	 * @throws Exception
	 */
	@Privilege(code = "basic:station:main:del")
	@ResponseBody
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable("id") String id) throws Exception {		
		try{
		    zoneStationService.deleteById(id);
		}catch(Exception e){
			 throw new BusinessException("删除飞机站位失败！",e);
		}
	}
	
	
	/**
	 * 飞机装机清单导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@Privilege(code = "basic:station:main:export")
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse respons) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "飞行站位导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "飞行站位导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017-12-7 上午10:56:32
	 * @CreateBy 孙霁
	 * @param zoneStation
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "basic:station:main:import")
	@RequestMapping(value = "station.xls")
	public String planExcel(ZoneStation zoneStation,Model model)throws BusinessException {
		try {
			List<ZoneStation> list =zoneStationService.getZoneList(zoneStation);
			String wjmc="station";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}



