package com.eray.thjw.project2.control;

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

import com.alibaba.fastjson.JSON;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.po.WorkCardImplList;
import com.eray.thjw.project2.service.WorkCardService;

/**
 * @Description 工卡控制层
 * @CreateTime 2017-8-15 下午3:19:41
 * @CreateBy 刘兵
 */
@Controller
@RequestMapping("/project2/workcard")
public class WorkCardController extends BaseController{
	
	@Resource
	private WorkCardService workCardService;
	@Resource(name="workCardExcelImporter")
	private BaseExcelImporter<WorkCardImplList> excelImporter;
	
	/**
	 * @Description 跳转至定检工卡
	 * @CreateTime 2017-8-16 上午10:54:01
	 * @CreateBy 刘兵
	 * @return 页面视图
	 */
	@Privilege(code="project2:workcard:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage() {
		return new ModelAndView("project2/workcard/workcard_main");
	}
	/**
	 * @Description 查看定检工卡
	 * @CreateTime 2017-8-16 下午4:16:54
	 * @CreateBy 刘兵
	 * @return 页面视图
	 */
	@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id")String id, Model model) {
		model.addAttribute("id", id);
		return new ModelAndView("project2/workcard/workcard_view");
	}
	
	/**
	 * @Description 新增工卡
	 * @CreateTime 2017-8-15 下午3:30:19
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:01")
	@ResponseBody
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@RequestBody WorkCard workCard) throws BusinessException{
		try {
			return workCardService.save(workCard);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("保存工卡失败!",e);
		}
	}
	
	/**
	 * @Description 编辑工卡
	 * @CreateTime 2017-8-18 下午4:42:46
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:02")
	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@RequestBody WorkCard workCard) throws BusinessException{
		try {
			return workCardService.update(workCard);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("编辑工卡失败!",e);
		}
	}
	
	/**
	 * @Description 审核工卡
	 * @CreateTime 2017-8-21 下午1:41:44
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:03")
	@ResponseBody
	@RequestMapping(value = "doAudit", method = RequestMethod.POST)
	public String doAudit(@RequestBody WorkCard workCard) throws BusinessException{
		try {
			return workCardService.doAudit(workCard);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("审核工卡失败!",e);
		}
	}
	
	/**
	 * @Description 批准工卡
	 * @CreateTime 2017-8-21 下午1:41:44
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:04")
	@ResponseBody
	@RequestMapping(value = "doApprove", method = RequestMethod.POST)
	public String doApprove(@RequestBody WorkCard workCard) throws BusinessException{
		try {
			return workCardService.doApprove(workCard);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("批准工卡失败!",e);
		}
	}
	
	/**
	 * @Description 改版工卡
	 * @CreateTime 2017-8-22 下午7:05:07
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return String 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:05")
	@ResponseBody
	@RequestMapping(value = "modify", method = RequestMethod.POST)
	public String modify(@RequestBody WorkCard workCard) throws BusinessException{
		try {
			return workCardService.modify(workCard);
		} catch (BusinessException e) {
			 throw e;
		} catch (Exception e) {
			throw new BusinessException("改版工卡失败!",e);
		}
	}
	
	/**
	 * @Description 删除工卡
	 * @CreateTime 2017-8-22 下午4:55:32
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:06")
	@ResponseBody
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public void delete(String id) throws BusinessException{
		getLogger().info("删除操作  前端参数:id{}", new Object[]{id});	
		try {
			workCardService.delete(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("删除失败!",e);
		}
	}
	
	/**
	 * @Description 失效工卡
	 * @CreateTime 2017-8-22 下午4:55:32
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:07")
	@ResponseBody
	@RequestMapping(value = "doInvalid", method = RequestMethod.POST)
	public void doInvalid(String id) throws BusinessException{
		getLogger().info("失效操作  前端参数:id{}", new Object[]{id});	
		try {
			workCardService.doInvalid(id);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException("失效失败!",e);
		}
	}
	
	/**
	 * @Description 批量审核
	 * @CreateTime 2017-8-23 上午9:51:17
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @param yj 审核意见
	 * @return String 审核结果
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:03")
	@ResponseBody
	@RequestMapping(value = "updateBatchAudit", method = RequestMethod.POST)
	public String updateBatchAudit(@RequestParam("idList[]") List<String> idList, String yj) throws BusinessException {

		try {
			return workCardService.updateBatchAudit(idList, yj);
		} catch (Exception e) {
			throw new BusinessException("批量审核失败", e);
		}
	}

	/**
	 * @Description 批量批准
	 * @CreateTime 2017-8-23 上午9:56:40
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @param yj 批准意见
	 * @return String 批准结果
	 * @throws BusinessException
	 */
	@Privilege(code="project2:workcard:main:04")
	@ResponseBody
	@RequestMapping(value = "updateBatchApprove", method = RequestMethod.POST)
	public String updateBatchApprove(@RequestParam("idList[]") List<String> idList, String yj)
			throws BusinessException {
		try {
			return workCardService.updateBatchApprove(idList, yj);
		} catch (Exception e) {
			throw new BusinessException("批量批准失败", e);
		}
	}
	
	/**
	 * @Description 工卡分页列表查询
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryAllPageList(@RequestBody WorkCard workCard)throws BusinessException{
		try {
			return workCardService.queryAllPageList(workCard);
		} catch (Exception e) {
			throw new BusinessException("工卡列表加载失败!",e);
		}	
	}
	
	/**
	 * @Description 工卡分页列表查询(弹窗需要的数据)
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 刘兵
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryWinAllPageList", method = RequestMethod.POST)
	public Map<String, Object> queryWinAllPageList(@RequestBody WorkCard workCard)throws BusinessException{
		try {
			return workCardService.queryWinAllPageList(workCard);
		} catch (Exception e) {
			throw new BusinessException("工卡列表加载失败!",e);
		}	
	}
	
	/**
	 * @Description 根据工卡id查询工卡及用户信息
	 * @CreateTime 2017-8-17 下午5:23:17
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @return WorkCard 工卡
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "selectById",method={RequestMethod.POST,RequestMethod.GET})
	public WorkCard selectById(String id) throws BusinessException {
		try {
			return workCardService.selectById(id);
		} catch (Exception e) {
			throw new BusinessException("查询工卡失败!",e);
		}
	}
	
	/**
	 * @Description 查询指定工卡的版本集合
	 * @CreateTime 2017年8月24日 下午3:59:24
	 * @CreateBy 韩武
	 * @param workCard
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "versionlist",method={RequestMethod.POST,RequestMethod.GET})
	public List<WorkCard> queryVersionList(@RequestBody WorkCard workCard) throws BusinessException {
		try {
			return workCardService.queryVersionList(workCard);
		} catch (Exception e) {
			throw new BusinessException("查询指定工卡的版本集合失败!",e);
		}
	}
	
	/**
	 * @Description 根据工卡id查询当前工卡的历史版本集合
	 * @CreateTime 2017-8-28 下午5:41:26
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @return List<WorkCard> 工卡集合
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryHistoryListById",method={RequestMethod.POST,RequestMethod.GET})
	public List<WorkCard> queryHistoryListById(String id) throws BusinessException {
		try {
			return workCardService.queryHistoryListById(id);
		} catch (Exception e) {
			throw new BusinessException("根据工卡id查询当前工卡的历史版本集合失败!",e);
		}
	}
	
	/**
	 * @Description 工单135\145:获取工卡来源信息
	 * @CreateTime 2017-8-16 上午10:47:45
	 * @CreateBy 雷伟
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "queryOriginatingCardList", method = RequestMethod.POST)
	public Map<String, Object> queryOriginatingCardList(@RequestBody WorkCard workCard)throws BusinessException{
		try {
			return workCardService.queryOriginatingCardList(workCard);
		} catch (Exception e) {
			throw new BusinessException("工卡来源加载失败!",e);
		}	
	}

	/**
	 * 工卡信息导入
	 * @Description 
	 * @CreateTime 2017-11-29 上午11:38:14
	 * @CreateBy 雷伟
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "project2:workcard:main:08")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "工卡信息导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "工卡信息导入失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramjson 当前参数
	 * @return String 页面数据
	 * @throws BusinessException
	 */
	@RequestMapping(value = "workcard.xls", method = RequestMethod.GET)
	public String exportExcelMt(String paramjson, HttpServletRequest request, Model model) throws BusinessException {
		try {
		    paramjson = new String(paramjson.getBytes("iso-8859-1"),"utf-8");
			WorkCard workCard = JSON.parseObject(paramjson, WorkCard.class);
			List<WorkCard> list = workCardService.doExportExcel(workCard);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "workcard", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败", e);
		}
	}
	
}
