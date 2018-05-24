package com.eray.thjw.basic.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.basic.service.ZoneStationService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.productionplan.po.LoadingList;


/**
 * @Description 区域管理 控制器
 * @CreateTime 2017年8月18日 下午3:18:04
 * @CreateBy 徐勇
 */
@Controller
@RequestMapping("/basic/zone")
public class ZoneController extends BaseController {

	@Resource
	private ZoneStationService zoneStationService;
	
	@Resource(name="zoneExcelImporter")
	private BaseExcelImporter<LoadingList> excelImporter;

	/**
	 * 
	 * @Description 查询操作
	 * @CreateTime 2017年8月18日 下午6:43:38
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 */
	@Privilege(code = "basic:zone:main")
	@ResponseBody
	@RequestMapping(value = "getZoneStationList", method = RequestMethod.POST)
	public Map<String, Object> getZoneStationList(@RequestBody ZoneStation zoneStation) {
		return zoneStationService.getZones(zoneStation);
	}

	/**
	 * 
	 * @Description 返回页面
	 * @CreateTime 2017年8月18日 下午6:53:24
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 */
	@Privilege(code = "basic:zone:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(ZoneStation zoneStation) {
		return new ModelAndView("basic/zonestation/zone_main");
	}

	/**
	 * 
	 * @Description 增加操作，分页
	 * @CreateTime 2017年8月25日 下午6:02:19
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "basic:zone:main:add")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void addZone(@RequestBody ZoneStation zoneStation) throws BusinessException {
		try {
			zoneStationService.addZone(zoneStation);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("添加区域失败！", e);
		}
	}

	/**
	 * 
	 * @Description 编辑操作
	 * @CreateTime 2017年8月25日 下午6:02:42
	 * @CreateBy 李高升
	 * @param zoneStation
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@Privilege(code = "basic:zone:main:edit")
	@ResponseBody
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public void updateZone(@RequestBody ZoneStation zoneStation) throws BusinessException {
		try {
			zoneStationService.updateZone(zoneStation);
		}catch (Exception e) {
			throw new BusinessException("修改区域失败！", e);
		}
	}

	/**
	 * 
	 * @Description 逻辑删除
	 * @CreateTime 2017年8月25日 下午6:03:02
	 * @CreateBy 李高升
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "basic:zone:main:del")
	@ResponseBody
	@RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
	public void delete(@PathVariable("id") String id) throws Exception {
		try {
			zoneStationService.deleteById(id);
		} catch (Exception e) {
			throw new BusinessException("删除区域失败！", e);
		}
	}

	/**
	 * @Description 获取区域集合（无分页）
	 * @CreateTime 2017年8月24日 下午3:25:46
	 * @CreateBy 韩武
	 * @param zoneStation
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "zoneList", method = RequestMethod.POST)
	public List<ZoneStation> getZoneList(@RequestBody ZoneStation zoneStation) throws BusinessException {
		try {
			return zoneStationService.getZoneList(zoneStation);
		} catch (Exception e) {
			throw new BusinessException("获取区域集合失败!", e);
		}
	}
	
	/**
	 * 导入
	 * 
	 * @return
	 * @throws BusinessException 
	 */
	@ResponseBody
	@Privilege(code = "basic:zone:main:export")
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response
		    ) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "区域管理导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "区域管理导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @Description 导出
	 * @CreateTime 2017-12-7 上午10:56:17
	 * @CreateBy 孙霁
	 * @param zoneStation
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "basic:zone:main:import")
	@RequestMapping(value = "zone.xls")
	public String planExcel(ZoneStation zoneStation,Model model)throws BusinessException {
		try {
			List<ZoneStation> list =zoneStationService.getZoneList(zoneStation);
			String wjmc="zone";
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", wjmc, model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
}
