package com.eray.thjw.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.User;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author 梅志亮
 * @description  章节号维护控制层
 * @develop date 2016.08.19
 */
@Controller
@RequestMapping("/project/fixchapter")
public class FixChapterController extends BaseController{
       
	@Resource
	private FixChapterService fixChapterService;                                          
	@Resource(name="fixchapterlistexcelimporter")
	private BaseExcelImporter<FixChapter> fixChapterListExcelImporter;       
	
	/**
	 * 章节号列表页面
	 * @return
	 */
	@Privilege(code="project:fixchapter:main")
	@RequestMapping(value="main", method = RequestMethod.GET)
	public String getIndex(){
		return "project/maintenance/fixchapterlist";
	}
    /**
     * 加载章节号列表数据
     * @return list
     */
	@ResponseBody
	@RequestMapping(value="selectFixChapter", method = RequestMethod.POST)
	public Map<String, Object> selectPlaneModelList(@RequestBody FixChapter fc,HttpServletRequest request,Model model)throws Exception {
		PageHelper.startPage(fc.getPagination()); 
		List<FixChapter> list = fixChapterService.selectFixChapterList(fc);
		return PageUtil.pack4PageHelper(list, fc.getPagination());
	}

	/**
	 * 
	 * @Description 根据组织机构获取章节号前十条数据
	 * @CreateTime 2018年1月29日 上午10:41:48
	 * @CreateBy 林龙
	 * @param fc
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "limitTen", method = RequestMethod.POST)
	public List<HCMainData> limitTen(@RequestBody FixChapter fc) throws BusinessException {
	    try{
			return fixChapterService.queryLimitTen(fc);
		}catch (RuntimeException e) {
			throw new BusinessException("根据组织机构获取章节号前十条数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 查询所有有效的章节号
	 * @throws BusinessException 
	 */
	@ResponseBody
	@RequestMapping(value="findAllFixChapter", method = RequestMethod.POST)
	public List<FixChapter> findAllFixChapter(HttpServletRequest request,Model model) throws BusinessException {
		List<FixChapter> allFixChapter = null;
		try {
			allFixChapter = fixChapterService.findAllFixChapter();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询所有有效的章节号失败");
		}finally{}
		return allFixChapter;
	}
    /**
     * 判断章节号是否重复
     * @param fc
     * @throws Exception
     */
	@Privilege(code="project:fixchapter:main:01")
	@ResponseBody
	@RequestMapping("getfixchapterConut")
	public int getCont(@RequestBody FixChapter fc) throws Exception {
		User user = ThreadVarUtil.getUser();
		fc.setDprtcode(user.getJgdm());
		return fixChapterService.selectCount(fc);
	}
	/**
	 * 执行章节号的新增操作
	 * @param fc
	 * @throws Exception
	 */
	@Privilege(code="project:fixchapter:main:01")
	@ResponseBody
	@RequestMapping("addFixChapter")
	public int addFixChapter(@RequestBody FixChapter fc) throws Exception {
		return fixChapterService.insert(fc);
	}
	/**
	 * 取得单个ATA章节
	 * @param request
	 * @param resp
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("upFixChapter")
	public FixChapter upFixChapter(@RequestParam("zjh") String zjh, @RequestParam("dprtcode") String dprtcode) throws  Exception {
		FixChapter  fc1=new FixChapter();
		fc1.setZjh(zjh);
		fc1.setDprtcode(dprtcode);
		FixChapter fc =fixChapterService.selectFixChapter(fc1) ;
		return fc;
	}
    /**
     * 执行章节号的修改操作
     * @param fc
     * @throws Exception
     */
	@Privilege(code="project:fixchapter:main:02")
	@ResponseBody
	@RequestMapping("updateFixchapter")
	public int updateFixchapter(@RequestBody FixChapter fc) throws Exception {
		User user=ThreadVarUtil.getUser();
		fc.setCjrid(user.getId());
		return fixChapterService.updateByPrimaryKey(fc);
	}
	/**
	 * 执行章节号的删除操作
	 * @param zjh
	 * @param dprtcode
	 * @throws Exception
	 */
	@Privilege(code="project:fixchapter:main:03")
	@ResponseBody
	@RequestMapping("deleteFixChapter")
	   public int deleteFixChapter(@RequestParam("zjh") String zjh, @RequestParam("dprtcode") String dprtcode) throws Exception {
		FixChapter  fc1=new FixChapter();
		fc1.setZjh(zjh);
		fc1.setDprtcode(dprtcode);
		return fixChapterService.deleteFixChapter(fc1);
	}
	
	/**
	 * @author liub
	 * @description  根据章节号查询数据
	 * @param zjh
	 * @return FixChapter
	 */
	@ResponseBody
	@RequestMapping(value = "getFixChapterByZjh", method = RequestMethod.POST)
	public FixChapter getFixChapterByZjh(String zjh,String dprtcode) throws  Exception {
		FixChapter fixChapter = null;
		try {
			fixChapter = fixChapterService.selectPrimaryKeyFixChapter(zjh,dprtcode);
		} catch (Exception e) {
			throw new BusinessException("根据章节号查询数据失败!",e);
		}
		
		return fixChapter;
	}
	
	/**
	 * ATA章节号导入
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code="project:fixchapter:main:04")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,  
		    HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			fixChapterListExcelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "ATA章节号导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "ATA章节号导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description 章节号导出
	 * @CreateTime 2017年12月4日 下午4:23:20
	 * @CreateBy 岳彬彬
	 * @param fc
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code="project:fixchapter:main:05")
	@RequestMapping(value = "ATA.xls")
	public String export(FixChapter fc, HttpServletRequest request,Model model) throws BusinessException {
		try {
			if(null != fc.getKeyword() && !"".equals(fc.getKeyword())){
				String keyword = fc.getKeyword();
				keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
				if(keyword.contains("'")){
					keyword=keyword.replace("'", "'|| chr(39) ||'");
				}
				fc.setKeyword(keyword);
			}
			List<FixChapter> list = fixChapterService.selectFixChapterList(fc);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(list);
			model.addAttribute("jrMainDataSource", jrDataSource);
			return outReport("xls", "common", "fixchapter", model);
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}

	}
}
