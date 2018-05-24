package com.eray.thjw.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.dao.FileCatalogMapper;
import com.eray.thjw.dao.TaskInfoMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.TaskInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.service.TaskService;
import com.eray.thjw.util.FtpUtils;
import com.eray.thjw.util.JavaMailUtils;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;
import com.github.pagehelper.PageHelper;

import enu.common.EffectiveEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
import enu.task.TaskStatusEnum;
import enu.task.TaskTypeEnum;

@Service
public class Task4ExpServiceImpl implements TaskService {

	@Resource
	private TaskInfoMapper taskInfoMapper;
	
	@Resource
	private FileCatalogMapper fileCatalogMapper;
	
	@Resource
	private AttachmentMapper attachmentMapper;
	
	private final static String EXCEL_TEMPLET_PATH = "/templet/";
	
	private static ExecutorService singleThreadExecutor = null;

	public synchronized ExecutorService getInstance() {
		if (singleThreadExecutor == null) {
			singleThreadExecutor = Executors.newSingleThreadExecutor();
		}
		return singleThreadExecutor;
	}
	
	/**
	 * 
	 * @Description 处理单个待处理任务
	 * @CreateTime 2018年4月14日 上午11:57:25
	 * @CreateBy 岳彬彬
	 * @return
	 */
	public void doTodoTask(final TaskInfo taskInfo) throws Exception{
		this.getInstance().execute(new Runnable() {
            @Override
            public void run() {
            		try {
            			executeTask(taskInfo);
            		} catch (Exception e) {
						e.printStackTrace();
					}              
				} 	
        });	
      }
	/**
	 * 
	 * @Description 线程池处理任务
	 * @CreateTime 2018年4月14日 下午3:26:53
	 * @CreateBy 岳彬彬
	 * @param task
	 */
	private void doTask(){
		this.getInstance().execute(new Runnable() {
            @Override
            public void run() {
            		try {
            			/** 防止在处理任务过程中服务器出现问题导致任务在处理中，处理文档没有完成 */
            			taskInfoMapper.updateZt4Export();//文档
            			List<TaskInfo> list = getAllTodoList();
            			if(list.size()>0){
            				for (TaskInfo taskInfo : list) {
            					executeTask(taskInfo);
            				}
            			}
            			//处理邮件任务
            			if(SysContext.EMAILSWITCH){
            				doTodoEmailTask();
            			}
					} catch (Exception e) {
						e.printStackTrace();
					}              
				} 	
        });	
	}

	/**
	 * 
	 * @Description 处理所有待处理任务
	 * @CreateTime 2018年4月14日 上午11:56:43
	 * @CreateBy 岳彬彬
	 * @return
	 * @throws Exception
	 */
	public void  doTodoTaskList() {	
		this.doTask();
	}
	
	/**
	 * 
	 * @Description 处理待处理邮件任务
	 * @CreateTime 2018年4月28日 下午2:38:49
	 * @CreateBy 岳彬彬
	 * @param task
	 * @throws Exception 
	 */
	private void doTodoEmailTask() throws Exception{
		taskInfoMapper.updateZt4Email();//email
		List<TaskInfo> emailList = taskInfoMapper.getAllTodoEmail();
		if(emailList.size() >0){
			for (TaskInfo taskInfo : emailList) {
				doTodoEmail(taskInfo);
			}
		}	
	}
	
	
	
	/**
	 * @Description 执行导出任务
	 * @CreateTime 2018年4月11日 上午 11:35
	 * @author 胡黄驰
	 * @param TaskInfo
	 */
	public Map<String, String> executeTask(TaskInfo taskInfo) throws Exception {
		/* 变量定义 */
		Map<String, String> returnResult = new HashMap<String, String>();
		String file_ftp_url = null;

		try {
			synchronized (this) {
				/* 数据有效性验证 */
				returnResult = this.checkDataEffectiveness(taskInfo);

				if (!returnResult.isEmpty()) {
					return returnResult;
				}

				/* 标记任务状态：执行中 */
				this.updateTaskInfoByPrimaryKey(taskInfo.getId(), TaskStatusEnum.INHAND.getId(), null, null);
			}

			/* 将任务对象（所有文件和文件夹）生成ZIP,并上传FTP */
			file_ftp_url = this.generateZIP(taskInfo.getRwdxid(), taskInfo.getNbsbm(), taskInfo.getDprtcode(),taskInfo.getRwbm());
			if(StringUtils.isNotBlank(file_ftp_url)){
				/* 更新完成状态 */
				this.updateTaskInfoByPrimaryKey(taskInfo.getId(), TaskStatusEnum.COMPLETE.getId(), "已生成下载文件", file_ftp_url);
			}else{
				this.updateTaskInfoByPrimaryKey(taskInfo.getId(), TaskStatusEnum.FAIL.getId(), "下载文件失败", file_ftp_url);
			}
		} catch (Exception e) {
			/* 更新失败状态 */
			this.updateTaskInfoByPrimaryKey(taskInfo.getId(), TaskStatusEnum.FAIL.getId(), "下载文件失败", null);
			throw e;
		} finally {
			file_ftp_url = null;
		}
		return returnResult;
	}

	/**
	 * @Description 检查任务数据有效性
	 * @CreateTime 2018年4月11日 下午 14:33
	 * @author 胡黄驰
	 * @param TaskInfo
	 */
	private Map<String, String> checkDataEffectiveness(TaskInfo taskInfo) throws Exception {
		/* 变量定义 */
		Map<String, String> returnResult = new HashMap<String, String>();
		TaskInfo tempTeakInfo = null;

		if (null == taskInfo || Utils.Str.isEmpty(taskInfo.getId()) || Utils.Str.isEmpty(taskInfo.getRwdxid())
				|| Utils.Str.isEmpty(taskInfo.getNbsbm()) || Utils.Str.isEmpty(taskInfo.getDprtcode())) {
			returnResult.put("code", "E01");
			returnResult.put("info", "任务对象数据不合法（存在空数据）:任务id/任务对象id/内部识别码/组织机构");
			return returnResult;
		}

		if (TaskTypeEnum.DOCEXPORT.getId() != taskInfo.getRwlx()) {
			returnResult.put("code", "E01");
			returnResult.put("info", "任务对象数据不合法:不是文档导出任务");
			return returnResult;
		}

		tempTeakInfo = taskInfoMapper.selectByPrimaryKey(taskInfo.getId());

		if (null == tempTeakInfo || Utils.Str.isEmpty(tempTeakInfo.getId())) {
			returnResult.put("code", "E02");
			returnResult.put("info", "无法执行不存在的任务");
			return returnResult;
		}

		if (TaskStatusEnum.INHAND.getId() == tempTeakInfo.getZt()
				|| TaskStatusEnum.COMPLETE.getId() == tempTeakInfo.getZt()) {
			returnResult.put("code", "E03");
			returnResult.put("info", "任务已经被执行/正在执行");
			return returnResult;
		}

		return returnResult;
	}

	/**
	 * @Description 根据任务id修改任务信息（状态、反馈说明、文件地址）
	 * @CreateTime 2018年4月11日 下午 14:22
	 * @author 胡黄驰
	 * @param1 String 任务id,
	 * @param2 Integer 待修改的状态,
	 * @param3 String 待修改的反馈说明,
	 * @param4 String 待修改的文件地址
	 */
	private void updateTaskInfoByPrimaryKey(String taskid, Integer status, String fksm, String wjdz) throws Exception {
		/* 变量定义 */
		TaskInfo tempTeakInfo = new TaskInfo();

		tempTeakInfo.setId(taskid);
		tempTeakInfo.setZt(status);
		tempTeakInfo.setFksm(fksm);
		tempTeakInfo.setWjdz(wjdz);

		taskInfoMapper.updateTaskInfoByPrimaryKey(tempTeakInfo);
	}

	/**
	 * @Description 将任务对象（所有文件和文件夹）生成ZIP,并上传FTP
	 * @CreateTime 2018年4月11日 下午 14:48
	 * @author 胡黄驰
	 * @param1 String 任务对象id,
	 * @param2 String 内部识别码,
	 * @param3 String 组织机构
	 * @return String ZIP文件在FTP的目录路径
	 */
	private String generateZIP(String objectid, String nbsbm, String dprtcode,String rwbm) throws Exception {
		/* 变量定义 */
		String retURL = null;

		/* 获取导出文件范围 */
		
		/* 生成临时文件夹 */
		FileCatalog record = new FileCatalog();
		record.setId(objectid);
		record.setMkdm(nbsbm);
		record.setDprtcode(dprtcode);
		String path = this.doExportFile(record,rwbm);
		
		/* 压缩成zip包 */
		String fileName = path.concat(".zip");//压缩包的包名
		FileOutputStream fos1 = new FileOutputStream(new File(fileName));
		this.toZip(path,fos1,true);
		
		/* 发送zip到ftp */
		retURL = this.toUploadFtp(fileName, rwbm);
		
		/* 发送成功后删除生成的临时文件  */
		if(StringUtils.isNotBlank(retURL)){ 
			/* 删除临时文件夹 */
			this.doDeleteTemp(path);
			/* 临时zip文件 */
			this.doDeleteTemp(fileName);
		}
		return retURL;
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018年4月12日 下午3:50:36
	 * @CreateBy 岳彬彬
	 * @param record  任务文件
	 * @param rwbm 任务名称
	 * @return returnPath 临时文件夹的名称
	 * @throws RuntimeException
	 */
	private String doExportFile(FileCatalog record,String rwbm) throws RuntimeException {
		List<FileCatalog> folders;// 所有文件夹
		List<Attachment> files;// 所有文件（附件）
		String rootPath = GlobalConstants.getString(GlobalConstants.ATT_ROOT_PATH_KEY);//附件根路径
		String path = rootPath.concat(File.separator).concat("docTemp");//临时文件存放路径
		this.mkDirectory(path);//创建临时文件存放的所在文件夹
		String relativePath = null;
		String sour = null;//来源位置
		String dest = null;//目标位置
		String returnPath = path ;
		path = path.concat(File.separator).concat(rwbm);
		returnPath = path;
		this.mkDirectory(path);//创建任务编码文件夹
		if("#".equals(record.getId())){//根路径
			folders = fileCatalogMapper.findAlls(record);
			List<String> list = new ArrayList<String>();
			for (FileCatalog fileCatalog : folders) {
				list.add(fileCatalog.getId());
			}
			if(null != list && list.size() >0){
				files = attachmentMapper.queryPlaneDataListAttachmentsByMainIds(list);	
				this.mkAllWj("#",folders,files,path,relativePath,sour,rootPath,dest);
			}
		}else{		
			folders = fileCatalogMapper.getChildren(record.getId());
			files = attachmentMapper.query4FileCatalog(record.getId());
			path = path.concat(File.separator).concat(folders.get(0).getMlmc());
			this.mkDirectory(path);
			this.mkAllWj(record.getId(),folders,files,path,relativePath,sour,rootPath,dest);
		}
		return returnPath;
	}
	/**
	 * 
	 * @Description 生成文件及文件夹
	 * @CreateTime 2018年4月12日 下午3:57:30
	 * @CreateBy 岳彬彬
	 * @param id  上级id
	 * @param folders 文件夹集合
	 * @param files  附件集合
	 * @param path  路径
	 * @param relativePath
	 * @param sour  来源路径
	 * @param rootPath 
	 * @param dest 存放路径
	 */
	private void mkAllWj(String id,List<FileCatalog> folders,List<Attachment> files,String path,String relativePath,String sour,String rootPath,String dest){
		String oldPath = path;
		for (FileCatalog fileCatalog : folders) {
			if(id.equals(fileCatalog.getFmlid())){
				path = oldPath.concat(File.separator).concat(fileCatalog.getMlmc());
				this.mkDirectory(path);
				this.mkAllWj(fileCatalog.getId(),folders,files,path,relativePath,sour,rootPath,dest);
			}
			for (Attachment attachment : files) {
				if(id.equals(attachment.getMainid())){
					relativePath = attachment.getCflj().concat(File.separator).concat(attachment.getNbwjm());
					sour = rootPath.concat(File.separator).concat(relativePath);
					dest = oldPath.concat(File.separator).concat(attachment.getWbwjm()).concat(".").concat(attachment.getHzm());
					try {
						copyFile(sour, dest);
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			}
		}	
	}
	
	/**
	 * 
	 * @Description 生成文件夹
	 * @CreateTime 2018年4月12日 下午3:57:49
	 * @CreateBy 岳彬彬
	 * @param path
	 */
	private void  mkDirectory(String path) {  
        File file ;  
        try {  
            file = new File(path);  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
        } catch (Exception e) {  
        }  
    }  
	/**
	 * 
	 * @Description 生成文件
	 * @CreateTime 2018年4月12日 下午3:58:04
	 * @CreateBy 岳彬彬
	 * @param sour
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFile(String sour, String dest) throws IOException {  
		 File oldFile = new File(sour);
        File file = new File(dest);
        FileInputStream in = new FileInputStream(oldFile);
        FileOutputStream out = new FileOutputStream(file);;

        byte[] buffer=new byte[1024];
        
        while((in.read(buffer)) != -1){
            out.write(buffer);
        }
        in.close();
        out.close();
    }
	/**
	 * 压缩成ZIP 方法1
	 * @param srcDir 压缩文件夹路径 
	 * @param out    压缩文件输出流
	 * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
	 * 							false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
	 * @throws RuntimeException 压缩失败会抛出运行时异常
	 */
	private void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
			throws RuntimeException{		
		ZipOutputStream zos = null ;
		try {
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
		} catch (Exception e) {
			throw new RuntimeException("zip error from ZipUtils",e);
		}finally{
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 * 
	 * @Description 文件夹压缩成.zip文件
	 * @CreateTime 2018年4月28日 下午2:54:27
	 * @CreateBy 岳彬彬
	 * @param sourceFile
	 * @param zos
	 * @param name
	 * @param KeepDirStructure
	 * @throws Exception
	 */
	private  void compress(File sourceFile, ZipOutputStream zos, String name,
			boolean KeepDirStructure) throws Exception{
		byte[] buf = new byte[1024];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				if(KeepDirStructure){
					// 空文件夹的处理
					zos.putNextEntry(new ZipEntry(name.concat(File.separator)));
					// 没有文件，不需要文件的copy
					zos.closeEntry();
				}
				
			}else {
				for (File file : listFiles) {
					// 判断是否需要保留原来的文件结构
					if (KeepDirStructure) {
						// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
						// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
						compress(file, zos, name .concat(File.separator).concat(file.getName()),KeepDirStructure);
					} else {
						compress(file, zos, file.getName(),KeepDirStructure);
					}
					
				}
			}
		}
	}
	/**
	 * 
	 * @Description 上传到FTP
	 * @CreateTime 2018年4月13日 上午11:39:15
	 * @CreateBy 岳彬彬
	 * @param source
	 * @param rwbm
	 */
	private String toUploadFtp(String source,String rwbm){			
			String hostname = SysContext.FTP_URL;
	        int port = Integer.valueOf(SysContext.FTP_PORT);
	        String username = SysContext.FTP_USERNAME;
	        String password = SysContext.FTP_PASSWORD;
	        boolean res = FtpUtils.uploadFileFromProduction(hostname, port, username, password, rwbm, source);
	        if(res){
	        	String url = SysContext.getDownFtpUrl().concat("/").concat(rwbm);
	        	return url;
	        }
	        return "";
	}
	/**
	 * 
	 * @Description 删除临时文件和临时zip文件
	 * @CreateTime 2018年4月13日 下午3:28:39
	 * @CreateBy 岳彬彬
	 * @param fileName
	 * @return
	 */
	private boolean doDeleteTemp(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}
	
	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	private boolean deleteFile(String fileName) {
	    File file = new File(fileName);
	    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
	    if (file.exists() && file.isFile()) {
	        if (file.delete()) {	           
	            return true;
	        } else {	          
	            return false;
	        }
	    } else {        
	        return false;
	    }
	}
	 /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
	private boolean deleteDirectory(String dir) {
	    // 如果dir不以文件分隔符结尾，自动添加文件分隔符
	    if (!dir.endsWith(File.separator))
	        dir = dir + File.separator;
	    File dirFile = new File(dir);
	    // 如果dir对应的文件不存在，或者不是一个目录，则退出
	    if ((!dirFile.exists()) || (!dirFile.isDirectory())) {	      
	        return false;
	    }
	    boolean flag = true;
	    // 删除文件夹中的所有文件包括子目录
	    File[] files = dirFile.listFiles();
	    for (int i = 0; i < files.length; i++) {
	        // 删除子文件
	        if (files[i].isFile()) {
	            flag = this.deleteFile(files[i].getAbsolutePath());
	            if (!flag)
	                break;
	        }
	        // 删除子目录
	        else if (files[i].isDirectory()) {
	            flag = deleteDirectory(files[i]
	                    .getAbsolutePath());
	            if (!flag)
	                break;
	        }
	    }
	    if (!flag) {	       
	        return false;
	    }
	    // 删除当前目录
	    if (dirFile.delete()) {      
	        return true;
	    } else {
	        return false;
	    }
	}
	/**
	 * 
	 * @Description 新增任务
	 * @CreateTime 2018年4月12日 下午3:14:10
	 * @CreateBy 岳彬彬
	 * @param taskInfo
	 * @return
	 * @throws BusinessException 
	 * @throws Exception
	 */
	@Override
	public TaskInfo addTask(TaskInfo taskInfo) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		taskInfo.setId(UUID.randomUUID().toString());
		taskInfo.setZt(TaskStatusEnum.PENDING.getId());
		taskInfo.setRwlx(1);
		try {
			taskInfo.setRwbm(SNGeneratorFactory.generate(taskInfo.getDprtcode(), "9_SYS_TASK"));
		} catch (SaibongException e) {
			throw new BusinessException(e);
		}
		if(null != taskInfo.getRwdxid() && "gml".equals(taskInfo.getRwdxid())){
			taskInfo.setRwdxid("#");
			taskInfo.setRwms("导出全部文档");
		}else{
			taskInfo.setRwms("导出  " .concat(taskInfo.getParamsMap().get("wjm").toString()));
		}
		taskInfo.setSqrid(user.getId());
		taskInfo.setSqsj(new Date());
		taskInfoMapper.insertSelective(taskInfo);
		return taskInfo;
	}
	/**
	 * 
	 * @Description 获取所有待处理的任务
	 * @CreateTime 2018年4月13日 下午3:50:23
	 * @CreateBy 岳彬彬
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<TaskInfo> getAllTodoList() {
		
		return taskInfoMapper.getAllTodoList();
	}
	/**
	 * 
	 * @Description 获取所有下载任务
	 * @CreateTime 2018年4月14日 上午10:18:48
	 * @CreateBy 岳彬彬
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getAll(TaskInfo taskInfo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageHelper.startPage(taskInfo.getPagination());
		List<TaskInfo> list = taskInfoMapper.getAllList(taskInfo);
		resultMap = PageUtil.pack4PageHelper(list, taskInfo.getPagination());
		return resultMap;
	}
	
	/**
	 * 
	 * @Description 新增发送邮件任务
	 * @CreateTime 2018年4月27日 下午5:21:38
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param dbsm
	 * @param dbid
	 * @return
	 */
	@Override
	public void doEmailTask(Todo todo) {
		if(SysContext.EMAILSWITCH){
			this.doAsynSendMail(todo);
		}
	}
	
	/**
	 * 
	 * @Description 新增待处理邮件任务
	 * @CreateTime 2018年4月28日 上午10:06:11
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param dbsm
	 * @param dbid
	 * @return
	 */
	public TaskInfo addEmailTaskInfo(Todo todo){
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setId(UUID.randomUUID().toString());
		taskInfo.setDprtcode(todo.getDprtcode());
		taskInfo.setZt(TaskStatusEnum.PENDING.getId());
		taskInfo.setRwlx(2);		
		taskInfo.setRwms(todo.getSm());			
		taskInfo.setSqsj(new Date());
		taskInfo.setRwdxid(todo.getId());
		taskInfoMapper.insertSelective(taskInfo);
		return taskInfo;
	}
	
	/**
	 * 
	 * @Description 处理待办任务
	 * @CreateTime 2018年4月28日 上午10:25:00
	 * @CreateBy 岳彬彬
	 * @param todo
	 */
	private void doAsynSendMail(final Todo todo){
		this.getInstance().execute(new Runnable() {
            @Override
            public void run() {
            		try {
            			//1、新增待办
            			TaskInfo task =addEmailTaskInfo(todo);
            			//2、处理待处理任务
            			doTodoEmail(task);         			
					} catch (Exception e) {
						e.printStackTrace();
					}              
            	}	
        });	
	}
	/**
	 * 
	 * @Description 处理待处理任务
	 * @CreateTime 2018年4月28日 下午2:36:50
	 * @CreateBy 岳彬彬
	 * @param task
	 * @throws Exception
	 */
	private void doTodoEmail(TaskInfo task) throws Exception{
		//1、将任务改为处理中
		updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.INHAND.getId(), "", "");
		//2、验证是否需要发送邮件
		Map<String,Object> map =  getTaskInfo4Email(task.getId());
		if("true".equals(map.get("type"))){
			//3、 发送邮件
			task = (TaskInfo) map.get("task");
			boolean flag = doSendMail(task.getTodo());
			//4、发送成功 更新任务状态为10
			if(flag){
				updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.COMPLETE.getId(), "已发送邮件!", "");
			}else{//4、失败 更新任务状态为9
				updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.FAIL.getId(), "发送邮件失败!", "");
			}
		}
	}
	
	
	
	
	/**
	 * 
	 * @Description 获取任务信息判断是否发送邮件
	 * @CreateTime 2018年4月28日 上午10:28:48
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	private Map<String,Object> getTaskInfo4Email(String id) throws Exception{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String flag = "false";
		TaskInfo task = taskInfoMapper.getTaskInfoById(id);
		resultMap.put("task", task);
		if(null != task){
			Todo todo = task.getTodo();
			List<User> list = todo.getUserList();
			if(EffectiveEnum.NO.getId() == todo.getYxzt()){//待办.有效标识=0，直接完成任务
				updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.COMPLETE.getId(), "待办工作有效标识无效!", "");
				resultMap.put("type", flag);
				return resultMap;
			}
			if(TodoStatusEnum.YCL.getId() == todo.getZt()){//待办.是否反馈=1，直接完成任务
				updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.COMPLETE.getId(), "待办工作已反馈!", "");
				resultMap.put("type", flag);
				return resultMap;
			}
			if(null == list || list.size() == 0){ //任务收件人列表=null，直接完成任务
				updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.COMPLETE.getId(), "没有收件人!", "");
				resultMap.put("type", flag);
				return resultMap;
			}else{
				for (int i = 0; i < list.size(); i++) {
					if(StringUtils.isNotBlank(list.get(i).getEmail())){
						flag = "true";
						resultMap.put("type", flag);
						return resultMap;
					}
					if(StringUtils.isBlank(list.get(i).getEmail()) && i == list.size()-1){
						updateTaskInfoByPrimaryKey(task.getId(), TaskStatusEnum.COMPLETE.getId(), "没有收件人!", "");
						resultMap.put("type", flag);
						return resultMap;
					}
				}
			}
			
		}
		return resultMap;
	}
	
	
	/**
	 * 
	 * @Description 发送邮件
	 * @CreateTime 2018年4月26日 上午11:31:41
	 * @CreateBy 岳彬彬
	 * @param title 标题
	 * @param content 内容
	 * @throws MessagingException
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private boolean doSendMail(Todo todo) {
		boolean flag = false;
		Session session = JavaMailUtils.setProperty();
		//1.创建一封邮件的实例对象
		 MimeMessage msg = new MimeMessage(session);
    	try {
    		//2.设置发件人地址
			msg.setFrom(new InternetAddress(SysContext.SENDERADDRESS));
			
			// 3.设置收件人地址（MimeMessage.RecipientType.TO:发送  MimeMessage.RecipientType.CC：抄送  MimeMessage.RecipientType.BCC：密送）
			List<User> list = todo.getUserList();
			StringBuffer sbf = new StringBuffer();
			for (User user : list) {
				if(StringUtils.isNotBlank(user.getEmail())){
					sbf.append(user.getEmail()).append(",");
				}
			}
			InternetAddress[] internetAddressTo = new InternetAddress().parse(sbf.substring(0, sbf.length()-1).toString()); 
			msg.setRecipients(MimeMessage.RecipientType.TO, internetAddressTo);	
			
			
	        //4.设置邮件主题
	        msg.setSubject("待办工作-".concat(TodoEnum.getName(todo.getDbgzlx())),"UTF-8");
	        
	      //5.下面是设置邮件正文
	        Multipart mailContent = new MimeMultipart(); 
	        MimeBodyPart contentPart = new MimeBodyPart();
	       // 模板内容
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("content", todo.getSm());
	        //模板路径
	        String projectPath = getClass().getResource("/").getPath();
			projectPath = projectPath.replaceAll("%20", " ");  
			projectPath = projectPath.substring(0, projectPath.indexOf("WEB-INF")+"WEB-INF".length()).concat(EXCEL_TEMPLET_PATH); 
	
	        
	        // 解析 模版和 数据
	        String html = JavaMailUtils.getMailText(projectPath,"email.ftl",map); 
	        
	        contentPart.setContent(html, "text/html;charset=UTF-8");
	        mailContent.addBodyPart(contentPart);
	        msg.setContent(mailContent);
	        
	        //6.设置邮件的发送时间,默认立即发送
	        msg.setSentDate(new Date());
	        
	        //7、根据session对象获取邮件传输对象Transport
	        Transport transport = session.getTransport();
	       
	        //8.设置发件人的账户名和密码
	        transport.connect(SysContext.SENDERACCOUNT, SysContext.SENDERPASSWORD);
	        
	        //9.发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
	        transport.sendMessage(msg,msg.getAllRecipients());
	         
	        //10.关闭邮件连接
	        transport.close();
	        
	        flag = true;
		} catch (AddressException e) {
			new BusinessException("地址错误!");
		} catch (MessagingException e) {
			new BusinessException("邮件发送失败!");
		}
    	return flag;
	}
	/**
	 * 
	 * @Description 删除任务及已压缩文件
	 * @CreateTime 2018年5月9日 上午10:22:23
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return 
	 * @throws BusinessException 
	 */
	@Override
	public String deleteTask(String id) throws BusinessException {
		/* 先验证状态是否为9（失败）或10（成功） */
		TaskInfo task = taskInfoMapper.selectByPrimaryKey(id);
		if(TaskStatusEnum.FAIL.getId() != task.getZt() && TaskStatusEnum.COMPLETE.getId() != task.getZt()){
			throw new BusinessException("该任务不能删除!");
		}
		/* 删除在FTP的文件夹及下面的文件 */
		String fileName = task.getRwbm();
		String hostname = SysContext.FTP_URL;
        int port = Integer.valueOf(SysContext.FTP_PORT);
        String username = SysContext.FTP_USERNAME;
        String password = SysContext.FTP_PASSWORD;
        boolean flag = FtpUtils.deleteFile(hostname, port, username, password, fileName, fileName.concat(".zip"));
        /* 删除成功*/
        if(flag){
        	/* 删除任务*/
        	taskInfoMapper.deleteById(id);
        	return "success";
        }
		return "erro";
	}
	
}
