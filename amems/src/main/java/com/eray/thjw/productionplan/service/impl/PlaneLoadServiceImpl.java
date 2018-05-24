package com.eray.thjw.productionplan.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.dao.PlaneLoadInfoMapper;
import com.eray.thjw.productionplan.dao.PlaneLoadMapper;
import com.eray.thjw.productionplan.po.PlaneLoad;
import com.eray.thjw.productionplan.po.PlaneLoadInfo;
import com.eray.thjw.productionplan.service.PlaneLoadService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * 飞机基本数据service
 * @author hanwu
 *
 */
@Service
public class PlaneLoadServiceImpl implements PlaneLoadService {
		
	@Resource
	private PlaneLoadMapper planeLoadMapper;
	@Resource
	private PlaneLoadInfoMapper planeLoadInfoMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private AttachmentMapper attachmentMapper;

	/**
	 * @author sunji
	 * @description 添加
	 */
	public void insertPlaneLoad(PlaneLoad record) {
		User user=ThreadVarUtil.getUser();
		String recCzls=UUID.randomUUID().toString();
		List<PlaneLoadInfo> list=record.getPlaneLoadInfolist();
		if(list.size()>0){
			List<String> infoList = new ArrayList<String>();
			for (PlaneLoadInfo planeLoadInfo : list) {
				planeLoadInfo.setMainid(record.getId());
				String infoId=UUID.randomUUID().toString();
				planeLoadInfo.setId(infoId);
				infoList.add(infoId);
				planeLoadInfoMapper.insertPlaneLoadInfo(planeLoadInfo);
			}
			commonRecService.write("id", infoList, TableEnum.B_S_01801, user.getId(), recCzls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,record.getId());
		}
		List<Attachment> attachmentList = record.getAttachments();
		if (attachmentList != null && !attachmentList.isEmpty()) {
			List<String> columnValueList = new ArrayList<String>();
			for (Attachment attachment : attachmentList) {
				attachment.setMainid(record.getId());
				attachment.setDprtcode(user.getJgdm());
				attachment.setId(UUID.randomUUID().toString());
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				columnValueList.add(attachment.getId());
			}
			commonRecService.write("id", columnValueList, TableEnum.D_011, user.getId(), recCzls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE,record.getId());
		}
		planeLoadMapper.insertPlaneLoad(record);
		commonRecService.write(record.getId(), TableEnum.B_S_018, user.getId(), recCzls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, record.getId());
	}

	/**
	 * @author sunji
	 * @description 查询列表
	 */
	public List<PlaneLoad> getPlaneLoadList(PlaneLoad record) {
		//查询主数据
		List<PlaneLoad> list=planeLoadMapper.getPlaneLoadList(record);
		List<String> pldIds=new ArrayList<String>();
		for (PlaneLoad planeLoad : list) {
			pldIds.add(planeLoad.getId());
		}
		//根据ids查询从表数据
		if(pldIds.size()>0){
			List <PlaneLoadInfo> planeLoadList= planeLoadInfoMapper.getInfoByids(pldIds);
			for (PlaneLoad planeLoad : list) {
				for (PlaneLoadInfo planeLoadInfo : planeLoadList) {
					if(planeLoad.getId().equals(planeLoadInfo.getMainid())){
						List<PlaneLoadInfo> pldifList=null;
						if(planeLoad.getPlaneLoadInfolist()==null){
							 pldifList= new ArrayList<PlaneLoadInfo>();
						}else{
							 pldifList=planeLoad.getPlaneLoadInfolist();
						}
						pldifList.add(planeLoadInfo);
						planeLoad.setPlaneLoadInfolist(pldifList);
					}
					
				}
			}
		}
		return list;
	}
	/**
	 * @author	sunji
	 * @description  根据id查询单个
	 */
	public PlaneLoad selectByPrimaryKey(String id){
		return planeLoadMapper.selectByPrimaryKey(id);
	}

	/**
	 * @author sunji
	 * @description 修改
	 */
	public void updatePlaneLoad(PlaneLoad planeLoad) {
		//生成操作流水
		String czls=UUID.randomUUID().toString(); 
		User user=ThreadVarUtil.getUser();
		//对主表进行修改操作
		planeLoadMapper.updateByPrimaryKeySelective(planeLoad);
		commonRecService.write(planeLoad.getId(), TableEnum.B_S_018, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, planeLoad.getId());
		//对从表进行修改操作
		//1.删除
		List<String> pliIds=planeLoad.getPlaneLoadInfoIds();
		
		if(pliIds.size()>0){
			commonRecService.write("id", pliIds, TableEnum.B_S_01801, user.getId(), czls, LogOperationEnum.ZUOFEI,UpdateTypeEnum.DELETE,planeLoad.getId());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ids", pliIds);
			int a=planeLoadInfoMapper.deleteIds(paramMap);
			System.out.println(a);
		}
		//2.新增或修改
		List<PlaneLoadInfo> pliList=planeLoad.getPlaneLoadInfolist();
		if(pliList.size()>0){
			//遍历判断是否是修改还是添加
			for (PlaneLoadInfo planeLoadInfo : pliList) {
				if(planeLoadInfo.getId().equals("1")){
					String add_id=UUID.randomUUID().toString();
					planeLoadInfo.setId(add_id);
					planeLoadInfo.setMainid(planeLoad.getId());
					planeLoadInfoMapper.insertPlaneLoadInfo(planeLoadInfo);
					commonRecService.write(add_id, TableEnum.B_S_01801, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.SAVE,planeLoad.getId());
				}else{
					planeLoadInfoMapper.updateByPrimaryKeySelective(planeLoadInfo);
					commonRecService.write(planeLoadInfo.getId(), TableEnum.B_S_01801, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.UPDATE,planeLoad.getId());
				}
			}
		}
		//对附件上传进行修改操作
		//1.删除
		List<String> scwjIds=planeLoad.getScwjIds();
		if(scwjIds.size()>0){
			attachmentMapper.delFileByids(scwjIds);
			commonRecService.write("id", scwjIds, TableEnum.D_011, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.DELETE,planeLoad.getId());
		}
		//1.添加
		List<Attachment> attachmentsList= planeLoad.getAttachments();
		if(attachmentsList.size()>0){
			for (Attachment attachment : attachmentsList) {
				String fjid=UUID.randomUUID().toString();
				attachment.setMainid(planeLoad.getId());
				attachment.setDprtcode(planeLoad.getDprtcode());
				attachment.setId(fjid);
				attachment.setCzrid(user.getId());
				attachment.setCzbmid(user.getBmdm());
				attachmentMapper.addFile(attachment);
				commonRecService.write(fjid,TableEnum.D_011, user.getId(), czls, LogOperationEnum.EDIT,UpdateTypeEnum.SAVE,planeLoad.getId());
			}
		}
	}
	/**
	 * @author	sunji
	 * @description  根据id删除
	 */
	public Map<String ,Object> deletePlaneLoad(String id) {
		 Map<String ,Object> map=new HashMap<String, Object>();
		//1.删除该数据的字表
		planeLoadInfoMapper.deleteByMainid(id);
		//2.删除附件表数据
		Attachment attachment =new Attachment();
		attachment.setMainid(id);
		attachmentMapper.delFiles(attachment);
		//2.删除该数据
		int i=planeLoadMapper.deleteByPrimaryKey(id);
		if(i>0){
			map.put("state", "success");
			map.put("message", "删除成功");
		}else{
			map.put("state", "error");
			map.put("message", "删除失败");
		}
		return map;
	}

}
