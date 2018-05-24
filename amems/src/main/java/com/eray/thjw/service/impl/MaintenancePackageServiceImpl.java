package com.eray.thjw.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.MaintenancePackageMapper;
import com.eray.thjw.dao.TaskInfoMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycled;
import com.eray.thjw.po.FileRecycledChildren;
import com.eray.thjw.po.MaintenancePackage;
import com.eray.thjw.po.TaskInfo;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FileRecycledService;
import com.eray.thjw.service.MaintenancePackageService;
import com.eray.thjw.util.ThreadVarUtil;
import com.eray.thjw.util.Utils;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.DocumentTypeEnum;
import enu.task.TaskStatusEnum;
import enu.task.TaskTypeEnum;

@Service
public class MaintenancePackageServiceImpl implements MaintenancePackageService {
	
	@Resource
	private MaintenancePackageMapper maintenancePackageMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private FileRecycledService fileRecycledService;

	@Override
	public int queryCount(FileCatalog fileCatalog) throws RuntimeException {
		return maintenancePackageMapper.queryCount(fileCatalog);
	}


	@Override
	public List<MaintenancePackage> queryAllPageList(
			MaintenancePackage maintenancePackage) throws RuntimeException {
		return maintenancePackageMapper.queryAllPageList(maintenancePackage);
	}


	@Override
	public Map<String, Object> uploadingFile(List<MaintenancePackage> maintenancePackage) throws BusinessException{
		//获取登入user
		User user=ThreadVarUtil.getUser();
		Map<String, Object>  resultMap=new HashMap<String, Object>();
		
		MaintenancePackage param = new MaintenancePackage();
		param.setId(maintenancePackage.get(0).getMainid());
		param.setDprtcode(user.getJgdm());
		param.setYxzt(1);
		List<MaintenancePackage> oldList = maintenancePackageMapper.queryAllPageList(param);
		Map<String, MaintenancePackage> map = new HashMap<String, MaintenancePackage>();
		for(MaintenancePackage package1 : oldList){
			map.put(package1.getWbwjm(), package1);
		}
		for (MaintenancePackage maintenancePackage2 : maintenancePackage) {
			if(map.get(maintenancePackage2.getWbwjm()) != null){
				throw new BusinessException("文件名"+maintenancePackage2.getWbwjm()+"已存在");
			}
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			String id = uuid.toString();
		
			maintenancePackage2.setId(id);
			String hz=maintenancePackage2.getYswjm();
			
			String wjm = maintenancePackage2.getYswjm().substring(0,maintenancePackage2.getYswjm().lastIndexOf("."));
			String hzm = maintenancePackage2.getYswjm().substring(maintenancePackage2.getYswjm().lastIndexOf(".")+1,maintenancePackage2.getYswjm().length());
			
			BigDecimal bd=new BigDecimal(maintenancePackage2.getWjdxs());   
			//设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入)   
			bd=bd.setScale(2, BigDecimal.ROUND_HALF_UP);   
			maintenancePackage2.setYswjm(wjm);
			maintenancePackage2.setWbwjm(wjm);
			maintenancePackage2.setWjdx(bd);
			maintenancePackage2.setHzm(hzm);
			maintenancePackage2.setDprtcode(user.getJgdm());
			if(user.getBmdm()==null){
				maintenancePackage2.setCzbmid("");
			}else{
				maintenancePackage2.setCzbmid(user.getBmdm());
			}
			maintenancePackage2.setCzrid(user.getId());
			maintenancePackage2.setCzsj(new Date());
			maintenancePackage2.setYxzt(1);
			maintenancePackage2.setSm("");
			maintenancePackageMapper.saveMaintenancePackage(maintenancePackage2);
			
			String czls = UUID.randomUUID().toString();
			commonRecService.write(maintenancePackage2.getMainid(), TableEnum.D_014, user.getId(),czls ,LogOperationEnum.UPDATE_SAVE, UpdateTypeEnum.UPDATE,null);
			commonRecService.write(id, TableEnum.D_011, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,maintenancePackage2.getMainid());
		}
		
		
		resultMap.put("state", "Success");
		
		return resultMap;
	}

	@Override
	public Map<String, Object> updateMainid(MaintenancePackage maintenancePackage) throws BusinessException {
		Map<String, Object>  resultMap=new HashMap<String, Object>();
			
		List<MaintenancePackage> queryListByMWList = maintenancePackageMapper.queryListByMWList(maintenancePackage);
		for (MaintenancePackage maintenancePackage2 : queryListByMWList) {
			throw new BusinessException("目标文件夹存在"+maintenancePackage2.getWbwjm()+"！");
		}
		
		User user = ThreadVarUtil.getUser();
		maintenancePackage.setCzrid(user.getId());
		maintenancePackage.setCzbmid(user.getBmdm());
		maintenancePackageMapper.updateMainid(maintenancePackage);
		resultMap.put("state", "Success");
		return resultMap;
	}

	@Override
	public Map<String, Object> updtaeuploadingFile(
			MaintenancePackage maintenancePackage) throws BusinessException {
		Map<String, Object>  resultMap=new HashMap<String, Object>();
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		
		List<MaintenancePackage> oldList = maintenancePackageMapper.queryListByMW(maintenancePackage);
		for(MaintenancePackage package1 : oldList){
			if(!maintenancePackage.getId().equals(package1.getId()) && package1.getWbwjm().equals(maintenancePackage.getWbwjm())){
				throw new BusinessException("文件名"+maintenancePackage.getWbwjm()+"已存在");
			}
		}
		
		maintenancePackageMapper.updateMaintenancePackage(maintenancePackage);

		commonRecService.write(maintenancePackage.getMainid(), TableEnum.D_014, user.getId(),czls ,LogOperationEnum.UPDATE_UPDATE, UpdateTypeEnum.UPDATE,null);
		commonRecService.write(maintenancePackage.getId(), TableEnum.D_011, user.getId(),czls,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,maintenancePackage.getMainid());
		
		resultMap.put("id",maintenancePackage.getId() );
		resultMap.put("state", "Success");
			
		
		return resultMap;
	}


	@Override
	public Map<String, Object> deleteuploadingFile(MaintenancePackage maintenancePackage) throws RuntimeException {
		
		Map<String, Object>  resultMap = new HashMap<String, Object>();
		String czls = UUID.randomUUID().toString();
		
		String[] item = maintenancePackage.getIds().split(",");
		for (int i = 0; i < item.length; i++) {
			try {
				// 1.自身写入到回收站
				FileRecycled fileRecycled = fileRecycledService.save(item[i], DocumentTypeEnum.FILE);
				
				// 2.对象转换
				List<FileRecycledChildren> children = getChildrenFile(fileRecycled);
				
				// 3.将自身写入到回收站子表
				fileRecycledService.saveChildren(children);
				
				// 4.修改自身的状态 = 无效
				updateFileStatus(item[i], maintenancePackage.getMainid(), czls);
				
				resultMap.put("state", "Success");
					
			} catch (Exception e) {
				resultMap.put("state", "Failure");
				e.printStackTrace();
			}
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
		
		// 封装成回收站子表对象
		List<FileRecycledChildren> resultList = new ArrayList<FileRecycledChildren>();
		
		FileRecycledChildren child = new FileRecycledChildren();
		child.setId(UUID.randomUUID().toString());
		child.setMkdm(fileRecycled.getMkdm());
		child.setDprtcode(fileRecycled.getDprtcode());
		child.setManid(fileRecycled.getId());
		child.setWjid(fileRecycled.getWjid());
		child.setWjlx(DocumentTypeEnum.FILE.getCode());
		resultList.add(child);
		
		return resultList;
	}
	
	/**
	 * @Description 修改自身的状态 = 无效
	 * @CreateTime 2018年1月29日 下午1:37:55
	 * @CreateBy 韩武
	 * @param id
	 * @param mlid
	 * @param czls
	 */
	private void updateFileStatus(String id, String mlid, String czls){
		User user = ThreadVarUtil.getUser();
		
		maintenancePackageMapper.deleteById(id);
		commonRecService.write(id, TableEnum.D_011, user.getId(), czls, 
				LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, id);
				
		commonRecService.write(mlid, TableEnum.D_014, user.getId(), czls ,
				LogOperationEnum.UPDATE_DELETE, UpdateTypeEnum.UPDATE, mlid);
				
	}
}
