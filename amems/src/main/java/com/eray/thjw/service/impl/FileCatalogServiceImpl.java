package com.eray.thjw.service.impl;


import java.io.File;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.baseinfo.po.DirectoryStructure;
import com.eray.thjw.dao.FileCatalogMapper;
import com.eray.thjw.dao.MaintenancePackageMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycled;
import com.eray.thjw.po.FileRecycledChildren;
import com.eray.thjw.po.MaintenancePackage;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FileCatalogService;
import com.eray.thjw.service.FileRecycledService;
import com.eray.thjw.service.MaintenancePackageService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.DocumentTypeEnum;
import enu.common.EffectiveEnum;

@Service
public class FileCatalogServiceImpl implements FileCatalogService {
	
	@Resource
	private FileCatalogMapper fileCatalogMapper;

	@Resource
	private MaintenancePackageService maintenancePackageService;

	@Resource
	private CommonRecService commonRecService;

	@Resource
	private FileRecycledService fileRecycledService;

	@Resource
	private AttachmentMapper attachmentMapper;

	@Resource
	private MaintenancePackageMapper maintenancePackageMapper;

	@Override
	public List<FileCatalog> findAlls(FileCatalog fileCatalog1) throws RuntimeException {

		return fileCatalogMapper.findAlls(fileCatalog1);
	}

	@Override
	public List<FileCatalog> queryAlls(FileCatalog fileCatalog) throws RuntimeException {

		return fileCatalogMapper.queryAlls(fileCatalog);
	}

	@Override
	public int queryCount(FileCatalog fileCatalog) throws RuntimeException {
		return fileCatalogMapper.queryCount(fileCatalog);
	}

	@Override
	public Map<String, Object> saveFile(FileCatalog fileCatalog) throws BusinessException {
		Map<String, Object>  resultMap=new HashMap<String, Object>();

		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();

		User user = ThreadVarUtil.getUser();//获取用户信息
		fileCatalog.setId(id);
		fileCatalog.setYxzt(1);
		fileCatalog.setDprtcode(user.getJgdm());
		if(user.getBmdm()==null){
			fileCatalog.setCzbmid("");
		}else{
			fileCatalog.setCzbmid(user.getBmdm());
		}
		fileCatalog.setCzrid(user.getId());
		fileCatalog.setCzsj("");
		fileCatalogMapper.saveFileCatalog(fileCatalog);
		String czls = UUID.randomUUID().toString();

		//保存目录日志
		commonRecService.write(id, TableEnum.D_014, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,null);
		resultMap.put("id", id);
		resultMap.put("state", "Success");

		return resultMap;
	}

	@Override
	public Map<String, Object> saveImportFile(List<DirectoryStructure> dirStructures,String upZipPath,
			String rootPath,Map<String, Object> resultMap,String mkdm) throws BusinessException {
		String fileType= "doczip";
		if(null == dirStructures || dirStructures.size() <= 0){
			resultMap.put("state", "Failure");
			resultMap.put("message", "压缩包不能为空!");
			return resultMap;
		}

		User user = ThreadVarUtil.getUser();//获取用户信息
		List<FileCatalog> dirList = new ArrayList<FileCatalog>(); //文件夹
		List<MaintenancePackage> fileList = new ArrayList<MaintenancePackage>(); //文件

		for (DirectoryStructure dirStruct : dirStructures)
		{
			if(dirStruct.getType().equals("wjj")){
				FileCatalog fileCatalog = new FileCatalog();
				fileCatalog.setId(dirStruct.getId());
				fileCatalog.setMkdm(mkdm);
				fileCatalog.setDprtcode(user.getJgdm());
				fileCatalog.setMlmc(dirStruct.getName());
				fileCatalog.setFmlid(dirStruct.getParentId());
				fileCatalog.setYxzt(1);
				fileCatalog.setSm("");
				fileCatalog.setCzrid(user.getId());
				if(user.getBmdm()==null){
					fileCatalog.setCzbmid("");
				}else{
					fileCatalog.setCzbmid(user.getBmdm());
				}
				dirList.add(fileCatalog);
			}else if(dirStruct.getType().equals("wj")){
				MaintenancePackage maintenancePackage = new MaintenancePackage();

				maintenancePackage.setMainid(dirStruct.getParentId());
				maintenancePackage.setWbwjm(dirStruct.getName());
				maintenancePackage.setYswjm(dirStruct.getName());
				maintenancePackage.setNbwjm(dirStruct.getNbwjm());
				File file = new File(upZipPath+File.separator+dirStruct.getNbwjm());
				maintenancePackage.setWjdx(new BigDecimal(file.length()));
				maintenancePackage.setWjdxs(file.length()+"");
				/*String cflj = upZipPath.replace(rootPath, "").concat(File.separator+dirStruct.getPath())
						               .replace("/", "\\")
						               .replace(wjm, "");*/
				String cflj = File.separator.concat(getDiretory(fileType));
				maintenancePackage.setCflj(cflj);

				fileList.add(maintenancePackage);
			}
		} 

		//保存文件夹
		for (int i = 0; null != dirList && i < dirList.size(); i++) {
			fileCatalogMapper.saveFileCatalog(dirList.get(i));
			String czls = UUID.randomUUID().toString();
			//保存目录日志
			commonRecService.write(dirList.get(i).getId(), TableEnum.D_014, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,null);
		}

		//保存文件
		if(null != fileList && fileList.size() > 0){
			maintenancePackageService.uploadingFile(fileList);
		}

		return resultMap;
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

	@Override
	public Map<String, Object> checkUpdMt(FileCatalog fileCatalog)throws RuntimeException {
		fileCatalog.setYxzt(EffectiveEnum.YES.getId());
		int num=fileCatalogMapper.queryCount(fileCatalog);

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map

		if(num<1){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "目录名称不能重复!");
			return returnMap;
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> updateFile(FileCatalog fileCatalog) throws RuntimeException {
		Map<String, Object>  resultMap=new HashMap<String, Object>();

		try {

			String czls = UUID.randomUUID().toString();
			User user = ThreadVarUtil.getUser();
			fileCatalogMapper.updateFileCatalog(fileCatalog);

			//保存目录日志
			commonRecService.write(fileCatalog.getId(), TableEnum.D_014, user.getId(),czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,null);
			resultMap.put("state", "Success");

		} catch (Exception e) {
			System.out.println(e);
			resultMap.put("state", "Failure");
		}
		finally{
		}
		return resultMap;
	}
	/**
	 * @Description 修改父目录
	 * @CreateTime 2018-3-16 下午1:32:24
	 * @CreateBy 刘兵
	 * @param fileCatalog
	 */
	@Override
	public Map<String, Object> updateFmlid(FileCatalog fileCatalog){
		Map<String, Object>  resultMap=new HashMap<String, Object>();
		try {
			int num = fileCatalogMapper.vilide4FolderExixts(fileCatalog);
			if(num < 1){
				String czls = UUID.randomUUID().toString();
				User user = ThreadVarUtil.getUser();
				fileCatalog.setCzrid(user.getId());
				fileCatalog.setCzbmid(user.getBmdm());
				fileCatalogMapper.updateFmlid(fileCatalog);
				//保存目录日志
				commonRecService.write(fileCatalog.getId(), TableEnum.D_014, user.getId(),czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,null);
				resultMap.put("state", "Success");
			}else{
				resultMap.put("state", "error");
				resultMap.put("message", "目标文件夹下已存在相同文件夹！");
			}
		} catch (Exception e) {
			resultMap.put("state", "Failure");
		}
		finally{
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> checkdel(String id) throws RuntimeException {
		FileCatalog fileCatalog=new FileCatalog(); 
		fileCatalog.setId(id);
		fileCatalog.setYxzt(1);
		int num=fileCatalogMapper.queryCount(fileCatalog);

		int num1=maintenancePackageService.queryCount(fileCatalog);
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map

		if(num1<1){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "该数据已更新，请刷新后再进行操作!");
			return returnMap;
		}

		if(num<1){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "该数据已更新，请刷新后再进行操作!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}
	/**
	 * @Description 检查文件夹是否存在
	 * @CreateTime 2017-9-15 上午9:27:27
	 * @CreateBy 刘兵
	 * @param id 文件夹id
	 * @throws BusinessException
	 */
	@Override
	public void checkExist(String id) throws BusinessException{
		FileCatalog fc = fileCatalogMapper.selectByPrimaryKey(id);
		if(null == fc || fc.getYxzt() == 0){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	@Override
	public Map<String, Object> deleteFolder(FileCatalog fileCatalog)
			throws RuntimeException {
		Map<String, Object>  resultMap=new HashMap<String, Object>();

		try {
			// 1.自身写入到回收站
			FileRecycled fileRecycled = fileRecycledService.save(fileCatalog.getId(), DocumentTypeEnum.FOLDER);

			// 2.获取自身下面的所有文件、文件夹
			List<FileRecycledChildren> children = getChildrenFile(fileRecycled);

			// 3.将自身下面的所有文件、文件夹写入到回收站子表
			fileRecycledService.saveChildren(children);

			// 4.修改自身以及自身下面的所有文件、文件夹的状态 = 无效
			updateFileStatus(children);

			resultMap.put("state", "Success");

		} catch (Exception e) {
			System.out.println(e);
			resultMap.put("state", "Failure");
		}

		return resultMap;

	}

	/**
	 * @Description 获取自身下面的所有文件、文件夹
	 * @CreateTime 2018年1月26日 上午10:55:01
	 * @CreateBy 韩武
	 * @param fileCatalog
	 * @return
	 */
	private List<FileRecycledChildren> getChildrenFile(FileRecycled fileRecycled){

		// 下面的所有文件夹
		List<FileCatalog> folders = fileCatalogMapper.getChildren(fileRecycled.getWjid());

		// 下面所有文件
		List<Attachment> files = attachmentMapper.query4FileCatalog(fileRecycled.getWjid());

		// 封装成回收站子表对象
		List<FileRecycledChildren> resultList = new ArrayList<FileRecycledChildren>();
		for (FileCatalog folder : folders) {
			FileRecycledChildren child = new FileRecycledChildren();
			child.setId(UUID.randomUUID().toString());
			child.setMkdm(fileRecycled.getMkdm());
			child.setDprtcode(fileRecycled.getDprtcode());
			child.setManid(fileRecycled.getId());
			child.setWjid(folder.getId());
			child.setWjlx(DocumentTypeEnum.FOLDER.getCode());
			child.setFjdid(folder.getFmlid());
			resultList.add(child);
		}
		for (Attachment file : files) {
			FileRecycledChildren child = new FileRecycledChildren();
			child.setId(UUID.randomUUID().toString());
			child.setMkdm(fileRecycled.getMkdm());
			child.setDprtcode(fileRecycled.getDprtcode());
			child.setManid(fileRecycled.getId());
			child.setWjid(file.getId());
			child.setWjlx(DocumentTypeEnum.FILE.getCode());
			resultList.add(child);
		}
		return resultList;
	}

	/**
	 * @Description 修改自身以及自身下面的所有文件、文件夹的状态 = 无效
	 * @CreateTime 2018年1月26日 上午10:03:36
	 * @CreateBy 韩武
	 * @param fileCatalog
	 */
	private void updateFileStatus(List<FileRecycledChildren> list){
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();

		for (FileRecycledChildren child : list) {
			if(DocumentTypeEnum.FOLDER.getCode().equals(child.getWjlx())){	// 文件夹

				fileCatalogMapper.deleteById(child.getWjid());
				commonRecService.write(child.getWjid(), TableEnum.D_014, user.getId(), czls, 
						LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, child.getWjid());

			} else if (DocumentTypeEnum.FILE.getCode().equals(child.getWjlx())){	// 文件

				maintenancePackageMapper.deleteById(child.getWjid());
				commonRecService.write(child.getWjid(), TableEnum.D_011, user.getId(), czls, 
						LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, child.getWjid());

			}
		}
	}


	@Override
	public List<Map<String, Object>> exportList(FileCatalog record) {
		// 下面的所有文件夹
		List<FileCatalog> folders =  fileCatalogMapper.findAlls(record);
		// 下面所有文件
		List<String> list = new ArrayList<String>();
		for (FileCatalog fileCatalog : folders) {
			list.add(fileCatalog.getId());
		}
		List<Attachment> files = new ArrayList<Attachment>();
		if(null != list && list.size()>0){
			files = attachmentMapper.queryPlaneDataListAttachmentsByMainIds(list);
		}
		List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();  
		Map<String, Object> childrenMap ;
		Map<String, Object> childrenMap1 ;
		childrenMap = new HashMap<String, Object>();
		childrenMap1 = new HashMap<String, Object>();
		childrenMap.put("id", "gml");
       	childrenMap.put("parent", "#");
       	childrenMap.put("text", "根目录");
       	childrenMap.put("type", "1");
       	childrenMap1.put("opened", false);
       	childrenMap.put("state", childrenMap1);
       	menuList.add(childrenMap);  
		boolean selected = false;
	    for (FileCatalog fileCatalog : folders) {  
	    	childrenMap1 = new HashMap<String, Object>();
	    	childrenMap = new HashMap<String, Object>();
	    	FileCatalog fileCatalog1=new FileCatalog();
	    	fileCatalog1.setId(fileCatalog.getId());
	    	fileCatalog1.setYxzt(1);
	    	fileCatalog1.setFmlid(fileCatalog.getId());
    		int num= doSumWjj(folders,files,1,fileCatalog.getId());//根据父id获取文件夹的个数
    		int num1 = doSumWjj(folders,files,2,fileCatalog.getId());//根据id获取文件的个数
    	
        	childrenMap.put("id", fileCatalog.getId());
        	childrenMap.put("parent", fileCatalog.getFmlid());
        	childrenMap.put("text", fileCatalog.getMlmc()+"("+num+"个文件夹/"+num1+"个文件)");
        	childrenMap.put("data", fileCatalog);
        	if(StringUtils.isBlank(fileCatalog.getId())){
        		if(!selected && fileCatalog.getFmlid().equals("#")){
        			selected = true;
	        		childrenMap1.put("selected", selected);
	        	}
        	}else{
        		if(fileCatalog.getId().equals(fileCatalog.getId())){
	        		childrenMap1.put("selected", false);
	        	}
        	}
        	childrenMap1.put("opened", false);
        	childrenMap.put("state", childrenMap1);
        	menuList.add(childrenMap);  
	    } 
/*    	for (Attachment attachment : files) {
				Map<String, Object> childrenMap2 = new HashMap<String, Object>();
				childrenMap2.put("id", attachment.getId());
	        	childrenMap2.put("parent", attachment.getMainid());
	        	childrenMap2.put("text", attachment.getWbwjm());
	        	childrenMap2.put("type", "2");
	        	childrenMap2.put("opened", false);
	        	menuList.add(childrenMap2);
		}  */
		return menuList;
	}  
	
	private int doSumWjj(List<FileCatalog> folders, List<Attachment> files, int type, String id) {
		int num = 0;
		if (type == 1) {
			for (FileCatalog fileCatalog : folders) {
				if (fileCatalog.getFmlid().equals(id)) {
					num++;
				}
			}
		} else if (type == 2) {
			for (Attachment attachment : files) {
				if (attachment.getMainid().equals(id)) {
					num++;
				}
			}
		}
		return num;
	}
}
