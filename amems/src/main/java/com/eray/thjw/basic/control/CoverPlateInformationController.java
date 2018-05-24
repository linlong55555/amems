package com.eray.thjw.basic.control;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.eray.thjw.aerialmaterial.po.Storage;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.basic.service.CoverPlateInformationService;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.Pagination;

import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 盖板Controller
 * @UpdateBy 李高升
 */
@Controller
@RequestMapping("/basic/coverplateinformation")
public class CoverPlateInformationController extends BaseController {

	@Resource
	private CoverPlateInformationService coverPlateInformationService;
	
	@Resource(name="coverPlateInformationExcelImporter")
	private BaseExcelImporter<Storage> excelImporter;

	/**
	 * @author liub
	 * @description 根据飞机机型查询盖板多选下拉框
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	@ResponseBody
	@RequestMapping(value = "queryMultiselectByFjjx", method = RequestMethod.POST)
	public List<CoverPlateInformation> queryMultiselectByFjjx(@RequestBody CoverPlateInformation coverPlateInformation) throws BusinessException{
		try {
			return coverPlateInformationService.queryMultiselectByFjjx(coverPlateInformation);
		} catch (Exception e) {
			throw new BusinessException("查询盖板失败!",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据飞机机型查询盖板(弹窗)
	 * @param coverPlateInformation
	 * @return List<CoverPlateInformation>
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinByFjjx", method = RequestMethod.POST)
	public List<CoverPlateInformation> queryWinByFjjx(@RequestBody CoverPlateInformation coverPlateInformation) throws BusinessException{
		try {
			return coverPlateInformationService.queryWinByFjjx(coverPlateInformation);
		} catch (Exception e) {
			throw new BusinessException("查询盖板失败!",e);
		}
	}
	
	@Privilege(code = "basic:coverplateinformation:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView coverplatePage(ZoneStation zoneStation) {
		return new ModelAndView("basic/coverplateinformation/coverplate_maintenance_main");
	}
	
	
	
	@Privilege(code = "basic:coverplateinformation:main:add")
	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void addCoverPlate(@RequestBody CoverPlateInformation coverPlateInformatio) throws BusinessException {
		try {
			System.out.println("ok");
			coverPlateInformationService.addCoverPlate(coverPlateInformatio);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("添加盖板失败！", e);
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年9月19日 上午9:49:24
	 * @CreateBy 胡才秋
	 * @param coverPlateInformatio
	 * @throws BusinessException
	 */
	@Privilege(code = "basic:coverplateinformation:main:add,basic:coverplateinformation:main:update")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void saveorUpdate(@RequestBody CoverPlateInformation coverPlateInformatio) throws BusinessException {
		try {
			coverPlateInformatio.setWhsj(new Date());
			coverPlateInformationService.saveorUpdate(coverPlateInformatio);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("更改盖板失败！", e);
		}
	}
	/**
	 * 
	 * @Description 经过查询得到盖板列表
	 * @CreateTime 2017年8月31日 上午11:06:32
	 * @CreateBy 李高升
	 * @param coverPlateInformation
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCoverPlateList", method = RequestMethod.POST)
	public Map<String, Object> queryCoverPlateList(@RequestBody CoverPlateInformation coverPlateInformation) throws BusinessException{
		try {		
			return coverPlateInformationService.queryCoverPlateList(coverPlateInformation);
		} catch (Exception e) {
			throw new BusinessException("查询盖板失败!",e);
		}
	}
	
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年9月19日 上午9:52:38
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryCoverPlateList/zt", method = RequestMethod.POST)
	public Map<String, Object> queryCoverPlateListByZt(@RequestBody CoverPlateInformation coverPlateInformation) throws BusinessException{
		try {		
			coverPlateInformation.setZt(EffectiveEnum.YES.getId());
			return coverPlateInformationService.queryCoverPlateList(coverPlateInformation);
		} catch (Exception e) {
			throw new BusinessException("查询盖板失败!",e);
		}
	}
	
	/**
	 * @description 根据飞机机型查询盖板(弹窗)
	 * @CreateTime 2017年9月19日 上午11:06:32
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @throws BusinessException
	 * @return List<CoverPlateInformation>
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinById", method = RequestMethod.POST)
	public CoverPlateInformation queryWinById(@RequestBody CoverPlateInformation coverPlateInformation) throws BusinessException{
		try {
			return coverPlateInformationService.findDataById(coverPlateInformation.getId());
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("查询盖板失败!",e);
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2017年9月19日 上午9:50:26
	 * @CreateBy 胡才秋
	 * @param coverPlateInformation
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "basic:coverplateinformation:main:delete")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public boolean deleteById(@RequestBody CoverPlateInformation coverPlateInformation) throws BusinessException{
		try {
			CoverPlateInformation data = coverPlateInformationService.findDataById(coverPlateInformation.getId());
			data.setZt(EffectiveEnum.NO.getId());
			return coverPlateInformationService.saveorUpdate(data);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("盖板删除失败!",e);
		}
	}
	
	/**
	 * 盖板导入
	 * ll
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code = "basic:coverplateinformation:main:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "盖板导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "盖板导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description 导出盖板
	 * @CreateTime 2017年12月5日 下午1:56:03
	 * @CreateBy 林龙
	 * @param coverPlateInformation
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "CoverPlate.xls")
	public String export(CoverPlateInformation coverPlateInformation, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			coverPlateInformation.setDprtcode(new String (coverPlateInformation.getDprtcode().getBytes("iso-8859-1"),"utf-8"));
			Pagination p = new Pagination();
			p.setSort("auto");
			p.setRows(1000000);
			coverPlateInformation.setPagination(p);
			Map<String, Object> resultMap = coverPlateInformationService.queryCoverPlateList(coverPlateInformation);
			List<CoverPlateInformation> list = (List<CoverPlateInformation>) resultMap.get("rows");
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "gaiban", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败",e);
		}

	}
}



