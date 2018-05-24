package com.eray.thjw.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.service.WOJobEnclosureService;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.ExcelTemplet;
import enu.OperateEnum;
import enu.PrivilegeTypeEnum;

/**
 * 公共工具控制器
 * 
 * @author zhuchao
 *
 */

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@Resource
	private CommonService commonService;

	@Resource
	private AttachmentService attachmentService;
	@Resource
	private WOJobEnclosureService wOJobEnclosureService;
	@Resource
	private OrderAttachmentService orderAttachmentService;
	//最大文件名长度
	private final static int MAX  = 100;
	
	private final static String EXCEL_TEMPLET_PATH = "/templet/";
	
	@ResponseBody
	@RequestMapping(value = { "loadDicAndEnums" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> loadDics(HttpServletRequest req, HttpServletResponse reps) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enumMap", SysContext.getEnumMap());
		map.put("dicMap", SysContext.getDprtDicMap());
		return map;
	}

	/**
	 * 获取系统数据库时间
	 * 
	 * @return
	 */
	@RequestMapping(value = { "sysdate" }, method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> sysdate() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysdate", this.commonService.getSysdate());
		return map;
	}

	/**
	 * 异步附件上传
	 * 
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "ajaxUploadFile" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response,
			@RequestParam(value = "fileType", required = true) String fileType, String wbwjm) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 内部文件名称
		String nbwjm = null;
		// 原始文件名称
		String yswjm = null;
		String contentType = null;
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String relativePath = File.separator.concat(getDiretory(fileType));
		String direcory = rootPath.concat(relativePath);

		File rootPathFile = new File(direcory);
		InputStream is = null;
		FileOutputStream fos = null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (!rootPathFile.exists()) {
			rootPathFile.mkdirs();
		}
		try {
			String uuid = null;
			for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
				String subfix = "";
				String key = (String) it.next();
				MultipartFile file = multipartRequest.getFile(key);
				if (null!=file && file.getOriginalFilename().length() > 0) {
					yswjm = file.getOriginalFilename();
					//截取过长的文件名
					subfix = File_Util.getExtensionName(yswjm);
					yswjm = File_Util.removeSuffix(yswjm);
					if (yswjm.length() > MAX) {
						yswjm = yswjm.substring(0, MAX);
					}
					contentType = file.getContentType();
					if (file != null && !StringUtils.isEmpty(yswjm)) {
						uuid = UUID.randomUUID().toString();
						nbwjm = uuid;
						is = file.getInputStream();
						
						//如果是导入文档压缩包，需要加后缀
						if("doczip".equals(fileType)){
							fos = new FileOutputStream(direcory.concat(File.separator).concat(nbwjm).concat(".").concat(subfix));
						}else{
							fos = new FileOutputStream(direcory.concat(File.separator).concat(nbwjm));
						}

						FileCopyUtils.copy(is, fos);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("contentType", contentType);
						map.put("yswjm", yswjm);
						map.put("nbwjm", nbwjm);
						map.put("wbwjm", (StringUtils.isEmpty(wbwjm) || "undefined".equals(wbwjm)) ? yswjm : wbwjm);

						map.put("wjdx", Utils.FileUtil.bytes2unitK(file.getSize()));
						map.put("wjdxStr", Utils.FileUtil.bytes2unitG(file.getSize()));
						map.put("cflj", relativePath);
						map.put("uuid", uuid);
						map.put("hzm", subfix);
						map.put("user", ThreadVarUtil.getUser());
						map.put("czsj", DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE_TIME));
						map.put("operate", OperateEnum.ADD);

						listMap.add(map);
					}
				}
			}
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, listMap);
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "附件上传失败");
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 异步附件上传图片
	 * 
	 * @param multipartRequest
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = { "ajaxUploadImg" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> ajaxUploadImg(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		String fileType = "image";
		Map<String, Object> result = new HashMap<String, Object>();
		// 内部文件名称
		String nbwjm = null;
		// 原始文件名称
		String yswjm = null;
		String contentType = null;
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String relativePath = File.separator.concat(getDiretory(fileType));
		String direcory = rootPath.concat(relativePath);

		File rootPathFile = new File(direcory);
		InputStream is = null;
		FileOutputStream fos = null;
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (!rootPathFile.exists()) {
			rootPathFile.mkdirs();
		}
		try {
			String uuid = null;
			for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
				String subfix = "";
				String key = (String) it.next();
				MultipartFile file = multipartRequest.getFile(key);
				if (null!=file && file.getOriginalFilename().length() > 0) {
					yswjm = file.getOriginalFilename();
					//截取过长的文件名
					subfix = File_Util.getExtensionName(yswjm);
					yswjm = File_Util.removeSuffix(yswjm);
					if (yswjm.length() > MAX) {
						yswjm = yswjm.substring(0, MAX);
					}
					contentType = file.getContentType();
					if (file != null && !StringUtils.isEmpty(yswjm)) {
						uuid = UUID.randomUUID().toString();
						nbwjm = uuid;
						is = file.getInputStream();
						fos = new FileOutputStream(direcory.concat(File.separator).concat(nbwjm));

						FileCopyUtils.copy(is, fos);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("contentType", contentType);
						map.put("yswjm", yswjm);
						map.put("nbwjm", nbwjm);
						map.put("wbwjm", (StringUtils.isEmpty(nbwjm) || "undefined".equals(nbwjm)) ? yswjm : nbwjm);

						map.put("wjdx", Utils.FileUtil.bytes2unitK(file.getSize()));
						map.put("wjdxStr", Utils.FileUtil.bytes2unitG(file.getSize()));
						map.put("cflj", relativePath);
						map.put("uuid", uuid);
						map.put("hzm", subfix);
						map.put("user", ThreadVarUtil.getUser());
						map.put("czsj", DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE_TIME));
						map.put("operate", OperateEnum.ADD);

						listMap.add(map);
					}
				}
			}
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, listMap);
		} catch (Exception e) {
			result.put(SUCCESS, Boolean.FALSE);
			result.put(MESSAGE, "图片上传失败");
			e.printStackTrace();
		}

		return result;
	}
	
	@RequestMapping(value="/downfile/{id}",method={RequestMethod.GET})
	public ResponseEntity<byte[]> downfile(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id) throws BusinessException {
		try {
			Attachment attachment = attachmentService.selectByPrimaryKey(id);
			String relativePath = attachment.getCflj().concat(File.separator).concat(attachment.getNbwjm());
			return download(relativePath, attachment.getWbwjm(), request, response);
		} catch (Exception e) {
			throw new BusinessException("附件下载失败");
		}
	}
	/**
	 * 下达指令的附件下载
	 * @param multipartRequest
	 * @param response
	 */
	@RequestMapping(value="/orderDownfile/{id}",method={RequestMethod.GET})
	public ResponseEntity<byte[]> orderDownfile(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id) throws BusinessException {
		try {
			OrderAttachment orderAttachment = orderAttachmentService.selectByPrimaryKey(id);
			String relativePath = orderAttachment.getCflj().concat(File.separator).concat(orderAttachment.getNbwjm());
			return download(relativePath, orderAttachment.getWbwjm(), request, response);
		} catch (Exception e) {
			throw new BusinessException("附件下载失败");
		}
	}
	/**
	 * 工单的附件下载
	 * @param multipartRequest
	 * @param response
	 */
	@RequestMapping(value="/gdDownfile/{id}",method={RequestMethod.GET})
	public ResponseEntity<byte[]> gdDownfile(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id) throws BusinessException {
		try {
			WOJobEnclosure workorderfile = wOJobEnclosureService.selectByPrimaryKey(id);
			String relativePath = workorderfile.getCflj().concat(File.separator).concat(workorderfile.getNbwjm());
			return download(relativePath, workorderfile.getWbwjm(), request, response);
		} catch (Exception e) {
			throw new BusinessException("附件下载失败");
		}
	}
	
	public ResponseEntity<byte[]> download(String relativePath,String displayName,
			HttpServletRequest res,HttpServletResponse resp) throws IOException{
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
		String filePath = rootPath.concat(File.separator).concat(relativePath);
		//String dfileName = new String(displayName.getBytes("UTF-8"), "iso8859-1"); 
		String dfileName = new String(displayName.getBytes("GBK"), "iso8859-1"); 
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
		headers.setContentDispositionFormData("attachment", dfileName); 
		//return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)), headers, HttpStatus.CREATED); 
		//状态码是201，ie10,11不识别  改为HttpStatus.OK  但名称会乱码  ,与操作系统有关   将UTF-8改成GBK
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(filePath)), headers, HttpStatus.OK); 
	}
	
	@ResponseBody
	@RequestMapping(value = { "loadAttachments" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> loadAttachments(HttpServletResponse response, Attachment attachment)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Attachment> attachments = attachmentService.queryListAttachments(attachment);
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, attachments);
		} catch (Exception e) {
			throw new BusinessException("附件加载失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "loadPlaneDataAttachments" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> loadPlaneDataAttachments(HttpServletResponse response, Attachment attachment)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Attachment> attachments = attachmentService.queryPlaneDataListAttachments(attachment);
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, attachments);
		} catch (Exception e) {
			throw new BusinessException("附件加载失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = { "loadPlaneDataAttachmentsByMainIds" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> loadPlaneDataAttachmentsByMainIds(HttpServletResponse response, @RequestParam("idList[]") List<String> idList)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			List<Attachment> attachments = attachmentService.queryPlaneDataListAttachmentsByMainids(idList);
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, attachments);
		} catch (Exception e) {
			throw new BusinessException("附件加载失败");
		}
		return result;
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
	
	
	/**
	 * excel模板下载
	 * @param request
	 * @param response
	 * @param index
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/templetDownfile/{index}",method={RequestMethod.GET})
	public ResponseEntity<byte[]> templetDownfile(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("index")Integer index) throws BusinessException {
		try {
			ExcelTemplet excelTemplet = ExcelTemplet.findByIndex(index);
			String relativePath = EXCEL_TEMPLET_PATH.concat(excelTemplet.getFileName());
			String absolutePath = getProjectPath().concat(relativePath);
			HttpHeaders headers = new HttpHeaders(); 
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
			String dfileName = new String(excelTemplet.getDisplayName().getBytes("GBK"), "iso8859-1"); 
			headers.setContentDispositionFormData("attachment", dfileName); 
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(absolutePath)), headers, HttpStatus.OK); 
		} catch (Exception e) {
			throw new BusinessException("excel模板下载失败",e);
		}
	}
	
	/**
	 * 附件预览
	 * @param request
	 * @param response
	 * @param id
	 * @throws BusinessException
	 */
	@RequestMapping(value="/preview/{id}_{type}",method= RequestMethod.GET )
	public void preview(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id,@PathVariable("type")Integer type) throws BusinessException {
		try {
			Attachment attachment = attachmentService.loadAttachmentByIdAndType(id, type);
			String relativePath = attachment.getCflj().concat(File.separator).concat(attachment.getNbwjm());
			
			String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
			String filePath = rootPath.concat(File.separator).concat(relativePath);
			String dfileName = new String(attachment.getWbwjm().getBytes("UTF-8"), "iso8859-1"); 
			File file = new File(filePath);
			
			response.setContentType("application/pdf");
			response.setContentLength((int) file.length());
			dfileName = dfileName.replaceAll("\\;", "").replaceAll(" ", "");
			response.addHeader("Content-Disposition", "attachment; filename=" + dfileName);
			
			OutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(FileUtils.readFileToByteArray(file));  
		} catch (Exception e) {
			throw new BusinessException("附件预览失败", e);
		}
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
	@RequestMapping(value="/preview/{id}/{type}",method= RequestMethod.GET )
	public void previewImg(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id,@PathVariable("type")Integer type) throws BusinessException {
		try {
			Attachment attachment = attachmentService.loadAttachmentByIdAndType(id, type);
			String relativePath = attachment.getCflj().concat(File.separator).concat(attachment.getNbwjm());
			
			String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);
			String filePath = rootPath.concat(File.separator).concat(relativePath);
			String dfileName = new String(attachment.getWbwjm().getBytes("UTF-8"), "iso8859-1"); 
			File file = new File(filePath);
			
			response.setContentType("image/"+attachment.getHzm());
			response.setContentLength((int) file.length());
			dfileName = dfileName.replaceAll("\\;", "").replaceAll(" ", "");			
			OutputStream responseOutputStream = response.getOutputStream();
			responseOutputStream.write(FileUtils.readFileToByteArray(file));  
		} catch (Exception e) {
			throw new BusinessException("附件预览失败", e);
		}
	}
	
	/**
	 * 获取附件
	 * @param response
	 * @param attachment
	 * @return
	 * @throws BusinessException
	 */
	@ResponseBody
	@RequestMapping(value = { "loadAttachment" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> loadAttachment(HttpServletResponse response, Attachment param)
			throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put(SUCCESS, Boolean.TRUE);
			result.put(ATTACHMENT, attachmentService.loadAttachmentByIdAndType(param.getId(), param.getType()));
		} catch (Exception e) {
			throw new BusinessException("附件加载失败", e);
		}
		return result;
	}
	
	/**
	 * 附件下载
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	@RequestMapping(value="/downloadAttachment/{id}_{type}",method={RequestMethod.GET})
	public ResponseEntity<byte[]> downloadAttachment(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("id")String id, @PathVariable("type")Integer type) throws BusinessException {
		try {
			Attachment attachment = attachmentService.loadAttachmentByIdAndType(id, type);
			String relativePath = attachment.getCflj().concat(File.separator).concat(attachment.getNbwjm());
			String wjmc = attachment.getWbwjm();
			if(StringUtils.isNotBlank(attachment.getHzm())){
				wjmc = wjmc.concat(".").concat(attachment.getHzm());
			}
			return download(relativePath, wjmc, request, response);
		} catch (Exception e) {
			throw new BusinessException("附件下载失败", e);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = { "getSessions" }, method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> getSessions(HttpServletRequest request, HttpServletResponse reps) {
		
		Map<String, Object> map = new HashMap<String, Object> ();
		PageUtil.addDict(map, "btnPriCodeListJson", SessionUtil.getFromSession(request, PrivilegeTypeEnum.BUTTON.toString().toLowerCase()));
		PageUtil.addDict(map, "accessDepartmentJson", SessionUtil.getFromSession(request, "accessDepartment"));
		PageUtil.addDict(map, "userJson", SessionUtil.getFromSession(request, "user"));
		PageUtil.addDict(map, "userStoreList", SessionUtil.getFromSession(request, "userStoreList"));
		PageUtil.addDict(map, "userMenuListJson", SessionUtil.getFromSession(request, "userMenuList"));
		PageUtil.addDict(map, "userACRegListJson", SessionUtil.getFromSession(request, "userACRegList"));
		PageUtil.addDict(map, "userACReg135145ListJson", SessionUtil.getFromSession(request, "userACReg135145List"));
		PageUtil.addDict(map, "deptInfoJson", SessionUtil.getFromSession(request, "deptInfo"));
		return map;
	}
	
}
