package com.eray.thjw.training.control;

import java.awt.image.BufferedImage;
import java.io.File;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.eray.thjw.annotation.Privilege;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.control.BaseController;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.training.po.Faculty;
import com.eray.thjw.training.po.FacultyCourse;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.service.FacultyCourseService;
import com.eray.thjw.training.service.FacultyService;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.ImageUtil;
import com.eray.thjw.util.ThreadVarUtil;
/**
 * @author sunji
 * @description 教員管理控制器
 */
@Controller
@RequestMapping("/training/faculty")
public class FacultyController extends BaseController{

	@Resource
	private FacultyService facultyService;
	
	@Resource
	private FacultyCourseService facultyCourseService;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private DepartmentService departmentService;
	
	//最大文件名长度
	private final static int MAX  = 100;
	
	/**
	 * 宽
	 */
	private final static int WIDTH = 160;
	
	/**
	 * 高
	 */
	private final static int HEIGHT = 240;
	
	/**
	 * 小图宽度
	 */
	private final static int WIDTHMAX = 50;
	
	/**
	 * 小图高度
	 */
	private final static int HEIGHTMAX = 75;
	
	/**
	 * 跳转至教員管理
	 * 
	 * @return 页面视图
	 */
	@Privilege(code="training:faculty:main")
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView CommonalityPage(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){//超级机构用户 拥有的组织机构
				//所有机构代码
			model.put("accessDepartments", departmentService.queryOrg());
		}else{
				//非超级机构获取当前用户机构代码
			model.put("accessDepartments", departmentService.findDepartmentByUserId(user.getId()));
		}
		model.put("dprtcode", GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY));
		return new ModelAndView("training/faculty/faculty_main",model);
	}
	
	/**
	 * @author sunji
	 * @description 
	 * @param request,model
	 * @return resultMap
	 */
	@ResponseBody
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public Map<String, Object> queryAll(@RequestBody Faculty faculty,HttpServletRequest request)throws BusinessException{
		return facultyService.queryAll(faculty);
	}
	
	/**
	 * 
	 * @Description 教员管理列表查询
	 * @CreateTime 2017年9月25日 上午11:00:07
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return Map
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "query/list", method = RequestMethod.POST)
	public Map<String,Object> queryList(@RequestBody Faculty faculty)throws BusinessException{
		return facultyService.queryAll(faculty);
	}
	
	/**
	 * 
	 * @Description 弹出框教员
	 * @CreateTime 2017年9月25日 上午11:00:07
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return Map
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = "query/openlist", method = RequestMethod.POST)
	public Map<String,Object> openlist(@RequestBody Faculty faculty)throws BusinessException{
		return facultyService.queryAllopenlist(faculty);
	}
	
	/**
	 * 
	 * @Description 教员信息详情查询
	 * @CreateTime 2017年9月25日 下午5:18:30
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="query/dataInfo",method = RequestMethod.POST)
	public Map<String, Object> queryDataInfo(@RequestBody Faculty faculty)throws BusinessException{
		//获取课程信息
		Map<String, Object> returnParm = facultyService.queryAll(faculty);
		//获取课程列表
		FacultyCourse data = new FacultyCourse();
		data.setKcid(faculty.getId());
		List<FacultyCourse> list = facultyCourseService.queryList(data);
		returnParm.put("courseList", list);
		return returnParm;
	}
	/**
	 * 
	 * @Description 新增或修改
	 * @CreateTime 2017年9月25日 下午2:16:03
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @throws BusinessException
	 */
	@Privilege(code = "training:faculty:main:add,training:faculty:main:update")
	@ResponseBody
	@RequestMapping(value="saveorupdate",method= RequestMethod.POST)
	public Map<String, Object> saveUpdate(@RequestBody Faculty faculty)throws BusinessException{
		Map<String, Object> returnMap = new HashMap<String,Object>();
		if(StringUtils.isEmpty(faculty.getId())) {
			String id = UUID.randomUUID()+"";
			faculty.setId(id);
			facultyService.add(faculty);
			returnMap.put("state","success");
			returnMap.put("message", "添加成功");
		}else {
			facultyService.update(faculty);
			returnMap.put("state","success");
			returnMap.put("message", "修改成功");
		}
		return returnMap;
	}
	
	/**
	 * 
	 * @Description 修改时使用，查询教员的详情
	 * @CreateTime 2017年10月10日 上午9:44:28
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="queryInfo",method= RequestMethod.POST)
	public Map<String, Object> queryInfo(@RequestBody Faculty faculty)throws BusinessException{
		Map<String, Object> returnMap = facultyService.queryInfo(faculty);
		return returnMap;
	}
	
	/**
	 * 
	 * @Description 查询授权课程列表
	 * @CreateTime 2017年10月10日 下午3:12:05
	 * @CreateBy 胡才秋
	 * @param data
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="queryCourseList",method= RequestMethod.POST)
	public List<FacultyCourse> queryCourseList(@RequestBody FacultyCourse data)throws BusinessException{
		return facultyCourseService.queryPageList(data);
	}
	
	
	/**
	 * 
	 * @Description 教员管理删除
	 * @CreateTime 2017年9月25日 上午11:33:12
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @throws BusinessException
	 */
	@Privilege(code = "training:faculty:main:delete")
	@ResponseBody
	@RequestMapping(value="delete",method= RequestMethod.POST)
	public Map<String, Object> delete(@RequestBody Faculty faculty)throws BusinessException{
		boolean isTrue = false;
		isTrue = facultyService.delete(faculty);
		Map<String, Object> returnMap = new HashMap<String,Object>();
		if(isTrue) {
			returnMap.put("state", "success");
			returnMap.put("message", "删除成功");
		}else {
			returnMap.put("state", "warning");
			returnMap.put("message", "删除失败");
		}
		return returnMap;
	}
	
	/**
	 * 
	 * @Description 查询授课记录
	 * @CreateTime 2017年10月10日 下午5:01:40
	 * @CreateBy 胡才秋
	 * @param data
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="queryCourseInfoList",method= RequestMethod.POST)
	public List<TrainingPlan> queryCourseInfoList(@RequestBody TrainingPlan data)throws BusinessException{
		return facultyService.querCourseInfoList(data);
		
	}
	
	
	/**
	 * 
	 * @Description 查询授课记录
	 * @CreateTime 2017年10月10日 下午5:01:40
	 * @CreateBy 胡才秋
	 * @param data
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="deleteCourseInfo",method= RequestMethod.POST)
	public Map<String, Object> deleteCourseInfo(@RequestBody FacultyCourse data)throws BusinessException{
		Map<String, Object> returnMap = new HashMap<String,Object>();
		boolean isTrue = facultyService.deleteCourse(data);
		if(isTrue) {
			returnMap.put("state", "success");
			returnMap.put("message", "删除成功");
		}else {
			returnMap.put("state", "warning");
			returnMap.put("message", "删除失败");
		}
		return returnMap;
	}
	
	/**
	 * 
	 * @Description 上传图片方法
	 * @CreateTime 2017年10月16日 下午1:45:39
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @param multipartRequest
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value="uploadPic",method= RequestMethod.POST)
	public Map<String, Object> uploadPic(Faculty faculty,MultipartHttpServletRequest multipartRequest)throws BusinessException{
		Map<String, Object> returnMap = new HashMap<String,Object>();
		
		
		String json = multipartRequest.getParameter("json");
		JSONObject parseObject = JSONObject.parseObject(json);
		int width = (int) Math.rint((Double.valueOf(parseObject.get("width")+"").doubleValue()));
		int height = (int) Math.rint((Double.valueOf(parseObject.get("height")+"").doubleValue()));

		// 内部文件名称
		String nbwjm = null;
		// 原始文件名称
		String yswjm = null;
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String relativePath = File.separator.concat(getDiretory(""));
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
						BufferedImage bi = reader.read(0, param);
						ImageIO.write(bi, yswjmsss, destFile);

						// 大图
						String uuid1 = UUID.randomUUID().toString();
						String nbwjm1 = uuid1.concat(".").concat(File_Util.getExtensionName(yswjm));
						if (width > 500 || height > 500) {
							File destFile1 = new File(rootPathFile, nbwjm1);
							ImageUtil.zoomImage(destFile, destFile1, WIDTH, HEIGHT);
							faculty.setZpdzD(relativePath + "\\" + nbwjm1);
						}else{
							faculty.setZpdzD(relativePath + "\\" + nbwjm);
						}
					

						// 小图
						String uuid2 = UUID.randomUUID().toString();
						String nbwjm2 = uuid2.concat(".").concat(File_Util.getExtensionName(yswjm));
						if (width > WIDTHMAX || height > HEIGHTMAX) {
							File destFile1 = new File(rootPathFile, nbwjm2);
							ImageUtil.zoomImage(destFile, destFile1, WIDTHMAX, HEIGHTMAX);
							faculty.setZpdzX(relativePath + "\\" + nbwjm2);
						}else{
							faculty.setZpdzD(relativePath + "\\" + nbwjm);
						}


					}
				}
				returnMap.put("row", faculty);
				returnMap.put("state", 200);
				returnMap.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnMap;
	}
	
	
	/**
	 * 
	 * @Description 预览图片
	 * @CreateTime 2017年9月5日 上午10:02:21
	 * @CreateBy 岳彬彬
	 * @param request
	 * @param response
	 * @param id
	 * @param type
	 * @throws BusinessException
	 */
	@RequestMapping(value="/preview/{path}/{type}",method= RequestMethod.GET )
	public void previewImg(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("path")String path,@PathVariable("type")Integer type) throws BusinessException {
		try {
			String relativePath = path;
			relativePath = relativePath.replaceAll("&", "/");
			String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
			String filePath = rootPath.concat(File.separator).concat(relativePath);
			File file = new File(filePath);
			String suffix = relativePath.substring(relativePath.length()-4,relativePath.length());
			response.setContentType("image/"+suffix);
			response.setContentLength((int) file.length());
			OutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(FileUtils.readFileToByteArray(file));  
		} catch (Exception e) {
			throw new BusinessException("附件预览失败", e);
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
}
