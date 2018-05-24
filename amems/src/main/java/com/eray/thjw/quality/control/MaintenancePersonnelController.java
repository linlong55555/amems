package com.eray.thjw.quality.control;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.excelimport.AbstractExcelImporter;
import com.eray.thjw.excelimport.BaseExcelImporter;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.exception.ExcelImportException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.quality.service.MaintenancePersonnelService;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.ImageUtil;
import com.eray.thjw.util.Utils;

/**
 * 
 * 维修人员档案控制器
 * 
 * @author xu.yong
 *
 */
@Controller
@RequestMapping("/quality/maintenancepersonnel")
public class MaintenancePersonnelController extends BaseController {
	
	// 最大文件名长度
	private final static int MAX = 40;
	/**
	 * 宽
	 */
	private final static int WIDTH = 100;
	
	/**
	 * 高
	 */
	private final static int HEIGHT = 150;
	
	/**
	 * 小图宽度
	 */
	private final static int WIDTHMAX = 24;
	
	/**
	 * 小图高度
	 */
	private final static int HEIGHTMAX = 36;

	/**
	 *  答案维修人员文件夹
	 */
	private final static String PERSONNEL = "personnel";

	@Resource
	private MaintenancePersonnelService maintenancePersonnelService;
	
	/** 新增 */
	private static final String FORWARD_TYPE_ADD = "1";
	
	/** 修改 */
	private static final String FORWARD_TYPE_EDIT = "2";
	
	/** 查看 */
	private static final String FORWARD_TYPE_VIEW = "3";
	
	@Resource(name="maintenancelistexcelimporter")
	private BaseExcelImporter<BaseEntity> excelImporter;
	
	/**
	 * 跳转至维修人员档案管理页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@Privilege(code = "quality:maintenancepersonnel:manage")
	@RequestMapping(value = "/manage", method = RequestMethod.GET)
	public ModelAndView manage() throws BusinessException {
		Map<String, Object> model = new HashMap<String, Object>();
		return new ModelAndView("/quality/maintenancepersonnel/maintenancepersonnel_main", model);
	}
	
	/**
	 * 维修人员档案分页查询
	 * @param receipt
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPage", method = RequestMethod.POST)
	public Map<String, Object> queryPage(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.queryPage(personnel);
		} catch (Exception e) {
			throw new BusinessException("维修人员档案分页查询失败!", e);
		}
	}
	
	/**
	 * 跳转至新增维修人员档案管理页面
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code = "quality:maintenancepersonnel:add")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_ADD);
			return new ModelAndView("/quality/maintenancepersonnel/maintenancepersonnel_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至新增维修人员档案管理页面失败!",e);
		}
	}
	
	/**
	 * 跳转至修改维修人员档案管理页面
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code = "quality:maintenancepersonnel:edit")
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_EDIT);
			model.put("personnel", maintenancePersonnelService.selectByPrimaryKey(id));
			return new ModelAndView("/quality/maintenancepersonnel/maintenancepersonnel_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至修改维修人员档案管理页面失败!",e);
		}
	}
	
	/**
	 * 跳转至查看维修人员档案管理页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_VIEW);
			model.put("personnel", maintenancePersonnelService.selectByPrimaryKey(id));
			return new ModelAndView("/quality/maintenancepersonnel/maintenancepersonnel_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至查看维修人员档案管理页面失败!",e);
		}
	}
	
	/**
	 * 根据维修人员id跳转至查看维修人员档案管理页面
	 * @return
	 * @throws BusinessException 
	 */
	@RequestMapping(value = "/wxryView/{id}", method = RequestMethod.GET)
	public ModelAndView wxryView(@PathVariable("id") String id) throws BusinessException{
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("type", FORWARD_TYPE_VIEW);
			model.put("personnel", maintenancePersonnelService.selectByWxryid(id));
			return new ModelAndView("/quality/maintenancepersonnel/maintenancepersonnel_detail", model);
		} catch (Exception e) {
			 throw new BusinessException("跳转至查看维修人员档案管理页面失败!",e);
		}
	}
	
	/**
	 * 保存维修人员档案
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.save(personnel);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			throw new BusinessException("保存维修人员档案失败!", e);
		}
	}
	
	/**
	 * 删除维修人员档案
	 * @param param
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "quality:maintenancepersonnel:delete")
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			maintenancePersonnelService.delete(personnel);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage(), e);
		} catch (Exception e) {
			throw new BusinessException("删除维修人员档案失败!", e);
		}
	}

	public String getDiretory(String fileType) {

		StringBuffer directory = new StringBuffer();
		Format format = new SimpleDateFormat("yyyyMMdd");
		directory.append(File.separator);
		directory.append(format.format(new Date()));
		directory.append(File.separator);
		directory.append(fileType.toLowerCase());
		return directory.toString();
	}

	// 上传图片
	public MaintenancePersonnel doSuperUpFile(MaintenancePersonnel maintenancePersonnel,
			MultipartHttpServletRequest multipartRequest) {

		int x = (int) Math.rint((Double.valueOf(multipartRequest.getParameter("x")).doubleValue()));
		int y = (int) Math.rint((Double.valueOf(multipartRequest.getParameter("y")).doubleValue()));
		int width = (int) Math.rint((Double.valueOf(multipartRequest.getParameter("width")).doubleValue()));
		int height = (int) Math.rint((Double.valueOf(multipartRequest.getParameter("height")).doubleValue()));

		// 内部文件名称
		String nbwjm = null;
		// 原始文件名称
		String yswjm = null;
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String relativePath = File.separator.concat(getDiretory(PERSONNEL));
		String direcory = rootPath.concat(relativePath);

		File rootPathFile = new File(direcory);
		if (!rootPathFile.exists()) {
			rootPathFile.mkdirs();
		}

		try {

			String uuid = null;
			for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
				String key = (String) it.next();
				MultipartFile file = multipartRequest.getFile(key);
				if (file!=null&&file.getSize() == 0) {
					continue;
				}
				if (file!=null&&file.getOriginalFilename().length() > 0) {
					// 截取过长的文件名
					if (file.getOriginalFilename().length() <= MAX) {
						yswjm = file.getOriginalFilename();
					} else {
						String subfix = "";
						if (file.getOriginalFilename().contains(".")) {
							int idx = file.getOriginalFilename().lastIndexOf(".");
							subfix = file.getOriginalFilename().substring(idx);
							yswjm = file.getOriginalFilename().substring(0, MAX - subfix.length()).concat(subfix);
						} else {
							yswjm = file.getOriginalFilename().substring(0, 100);
						}
					}

					if (file != null && !StringUtils.isEmpty(yswjm)) {
						uuid = UUID.randomUUID().toString();
						nbwjm = uuid.concat(".").concat(File_Util.getExtensionName(yswjm));

						// 剪切
						String yswjms = yswjm.substring(0, yswjm.lastIndexOf('.'));
						String yswjmsss = yswjm.substring(yswjms.length() + 1);

						File destFile = new File(rootPathFile, nbwjm);

						// 原图
						Iterator iterator = ImageIO.getImageReadersByFormatName(yswjmsss);
						ImageReader reader = (ImageReader) iterator.next();
						InputStream in = file.getInputStream();
						ImageInputStream iis = ImageIO.createImageInputStream(in);
						reader.setInput(iis, true);
						ImageReadParam param = reader.getDefaultReadParam();
						Rectangle rect = new Rectangle(x, y, width, height);
						param.setSourceRegion(rect);
						BufferedImage bi = reader.read(0, param);
						ImageIO.write(bi, yswjmsss, destFile);

						// 大图
						String uuid1 = UUID.randomUUID().toString();
						String nbwjm1 = uuid1.concat(".").concat(File_Util.getExtensionName(yswjm));
						if (width > 100 || height > 150) {
							File destFile1 = new File(rootPathFile, nbwjm1);
							ImageUtil.zoomImage(destFile, destFile1, WIDTH, HEIGHT);
							maintenancePersonnel.setZpdzD(relativePath + "\\" + nbwjm1);
						}else{
							maintenancePersonnel.setZpdzD(relativePath + "\\" + nbwjm);
						}
					

						// 小图
						String uuid2 = UUID.randomUUID().toString();
						String nbwjm2 = uuid2.concat(".").concat(File_Util.getExtensionName(yswjm));
						if (width > 50 || height > 75) {
							File destFile1 = new File(rootPathFile, nbwjm2);
							ImageUtil.zoomImage(destFile, destFile1, WIDTHMAX, HEIGHTMAX);
							maintenancePersonnel.setZpdzX(relativePath + "\\" + nbwjm2);
						}else{
							maintenancePersonnel.setZpdzD(relativePath + "\\" + nbwjm);
						}


					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return maintenancePersonnel;
	}

	@RequestMapping(value = "/downfile", method = { RequestMethod.GET })
	public void downfile(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException, IOException {

		String relativePath = request.getParameter("wj");
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String filePath = rootPath.concat(File.separator).concat(relativePath);
		
		FileInputStream fis = null;
		OutputStream os = null;
		try {
			File file = new File(filePath);

	        //判断文件是否存在如果不存在就返回默认图标
	        if(!(file.exists() && file.canRead())) {
	           return;
	        }
			fis = new FileInputStream(file);
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 8];
			while ((count = fis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null!=fis){
					fis.close();
				}
				if(null!=os){
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * @author liub
	 * @description 根据机构代码查询维修人员档案树(按部门)
	 * @param dprtcode
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "queryDrptTreeByDprtcode", method = RequestMethod.POST)
	public List<Map<String, Object>> queryDrptTreeByDprtcode(String dprtcode,String str)throws BusinessException {
		try {
			return maintenancePersonnelService.queryDrptTreeByDprtcode(dprtcode,str);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * @author liub
	 * @description 根据机构代码查询维修人员档案树(按岗位)
	 * @param dprtcode
	 * @return List<Map<String, Object>>
	 */
	@ResponseBody
	@RequestMapping(value = "queryBusinessTreeByDprtcode", method = RequestMethod.POST)
	public List<Map<String, Object>> queryBusinessTreeByDprtcode(String dprtcode,String str)throws BusinessException {
		try {
			return maintenancePersonnelService.queryBusinessTreeByDprtcode(dprtcode,str);
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	/**
	 * 加载维修人员档案详情
	 * @param personnel
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/loaddetail",method={RequestMethod.POST})
	public MaintenancePersonnel loadDetail(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.loadDetail(personnel);
		} catch (Exception e) {
			 throw new BusinessException("加载维修人员档案详情失败!", e);
		}
	}
	
	/**
	 * 加载岗位培训
	 * @param personnel
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/loadTrainingcourseFjjx",method={RequestMethod.POST})
	public List<Map<String, Object>> loadTrainingcourseFjjx(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.loadTrainingcourseFjjx(personnel);
		} catch (Exception e) {
			throw new BusinessException("加载岗位培训失败!", e);
		}
	}
	
	/**
	 * 加载岗位培训
	 * @param personnel
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="/loadTrainingcourse",method={RequestMethod.POST})
	public List<Map<String, Object>> loadTrainingcourse(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.loadTrainingcourse(personnel);
		} catch (Exception e) {
			throw new BusinessException("加载岗位培训失败!", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/validtion4RyidExist",method={RequestMethod.POST})
	public int validtion4RyidExist(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.validtion4RyidExist(personnel);
		} catch (Exception e) {
			 throw new BusinessException("验证维修人员失败!", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/findRyidExist",method={RequestMethod.POST})
	public MaintenancePersonnel findRyidExist(@RequestBody MaintenancePersonnel personnel) throws BusinessException{
		try {
			return maintenancePersonnelService.findRyidExist(personnel);
		} catch (Exception e) {
			throw new BusinessException("验证维修人员失败!", e);
		}
	}
	
	/**
	 * 维修档案人员导入
	 * ll
	 * @return
	 * @throws BusinessException 
	 */
	@Privilege(code = "quality:maintenancepersonnel:import")
	@ResponseBody
	@RequestMapping(value = "/excelImport", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> excelImport(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			excelImporter.doImport(multipartRequest.getFile(AbstractExcelImporter.FILE_NAME));
			result.put(SUCCESS, Boolean.TRUE);
			result.put(MESSAGE, "维修技术人员档案导入成功！");
		} catch (ExcelImportException e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, e.getMessage());
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "维修技术人员档案导入失败！");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 
	 * @Description 导出单个xls
	 * @CreateTime 2018年4月20日 下午2:13:58
	 * @CreateBy 岳彬彬
	 * @param personnel
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@Privilege(code = "quality:maintenancepersonnel:export")
	@RequestMapping(value = "personnelArchives.xls")
	public String export(MaintenancePersonnel personnel, HttpServletRequest request,
			Model model) throws BusinessException {
		try {
			model.addAttribute("id", personnel.getId());
			String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
			model.addAttribute("path", rootPath);
			return outReport("xls", "common", "personnelArchives", model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("预览或导出失败");
		}
	}
	/**
	 * 
	 * @Description 导出多个并可以重新导入
	 * @CreateTime 2018年5月10日 下午4:21:41
	 * @CreateBy 岳彬彬
	 * @param paramjson
	 * @param request
	 * @param model
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value = "maintenancepersonnel.xls" ,method={RequestMethod.GET})
	public String exportExcel(String paramjson, HttpServletRequest request,RedirectAttributesModelMap model) throws BusinessException {
		try {
			paramjson = new String(paramjson.getBytes("iso-8859-1"), "utf-8");
			MaintenancePersonnel record = Utils.Json.toObject(paramjson, MaintenancePersonnel.class);
			Map<String, Object> resultMap = maintenancePersonnelService.queryPage(record);
			List<MaintenancePersonnel> list = (List<MaintenancePersonnel>) resultMap.get("rows");
			StringBuffer idStr = new StringBuffer("b1.id in (");
			for (MaintenancePersonnel maintenancePersonnel : list) {
				idStr.append("'").append(maintenancePersonnel.getId()).append("'").append(",");
			}
			if(null != list && list.size() > 0){
				String str = idStr.substring(0, idStr.length()-1);
				str = str.concat(")");
				model.addFlashAttribute("idList", str);
			}else{
				idStr.append(" '')");
				model.addFlashAttribute("idList", idStr.toString());
			}
			return "redirect:/report/".concat("xls").concat("/").concat(record.getDprtcode()).concat("/maintenancepersonnelList.xls");
		} catch (Exception e) {
			throw new BusinessException("报表预览或导出失败");
		}
		
	}
	
	
	
}
