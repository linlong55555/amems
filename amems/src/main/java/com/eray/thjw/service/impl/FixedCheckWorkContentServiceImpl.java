package com.eray.thjw.service.impl;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.FixedCheckWorkContentMapper;
import com.eray.thjw.dao.FixedCheckWorkPlaneMapper;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixedCheckWorkContent;
import com.eray.thjw.po.FixedCheckWorkPlane;
import com.eray.thjw.po.JobCard;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixedCheckWorkContentService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.JobCardStatusEnum;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 定检工作内容service,用于业务逻辑处理
 * @develop date 2016.07.25
 */
@Service
public class FixedCheckWorkContentServiceImpl implements FixedCheckWorkContentService {
	
	/**
	 * @author liub
	 * @description 定检工作内容Mapper
	 * @develop date 2016.08.20
	 */
	@Autowired
	private FixedCheckWorkContentMapper checkWorkContentMapper;
	
	/**
	 * @author liub
	 * @description 定检工作对应飞机关系表Mapper
	 * @develop date 2016.10.24
	 */
	@Autowired
	private FixedCheckWorkPlaneMapper fixedCheckWorkPlaneMapper;
	
	/**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.08.24
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	/**
	 * @author liub
	 * @description 根据定检项目id工作内容数据
	 * @param 
	 * @return List<Map<String, Object>>
	 * @develop date 2016.08.29
	 */
	public List<Map<String, Object>> queryListByDjxmid(String djxmid) throws RuntimeException{
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<FixedCheckWorkContent> wList = checkWorkContentMapper.queryListByDjxmid(djxmid);
		for (FixedCheckWorkContent w : wList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", w.getId());
			map.put("nbxh", w.getNbxh());
			map.put("zjh", w.getZjh());
			map.put("fixChapter", w.getFixChapter());
			map.put("wz", w.getWz());
			map.put("cksc", w.getCksc());
			map.put("ckgk", w.getCkgk());
			List<JobCard> jobCardList = w.getJobCardList();
			Collections.sort(jobCardList, new Comparator<JobCard>() {
    	        public int compare(JobCard o1, JobCard o2) {
    	            return o1.getGdbh().compareTo(o2.getGdbh()) ;
    	        }
    	    });
			map.put("djgkList", jobCardList);
			map.put("isBj", w.getIsBj());
			map.put("isMi", w.getIsMi());
			map.put("bz", w.getBz());
			map.put("zt", w.getZt());
			map.put("xmly", w.getXmly());
			map.put("gzlx", w.getGzlx());
			map.put("scmsZw", w.getScmsZw());
			map.put("scmsYw", w.getScmsYw());
			map.put("jclx", w.getJclx());
			map.put("fjzch", w.getFjzch());
			map.put("fixedCheckWorkPlaneList", w.getFixedCheckWorkPlaneList());
			map.put("gzzw", w.getGzzw());
			map.put("dprtcode", w.getDprtcode());
			w.CompareColumn(w);
			map.put("isUpd", w.getIsUpd());
			map.put("normal", w.getNormal());
			list.add(map);
		}
		return list;
	}

	/**
	 * @author liub
	 * @description 根据定检编号删除工作内容
	 * @param 定检编号djbh
	 * @develop date 2016.08.24
	 */
	@Override
	public void deleteByDjxmid(String djxmid) throws RuntimeException{
		checkWorkContentMapper.deleteByDjxmid(djxmid);
	}
	
	/**
	 * @author liub
	 * @description 新增定检工作内容集合
	 * @param fixedCheckWorkContentList , 定检编号djbh
	 * @develop date 2016.08.24
	 */
	@Override
	public void addWorkConetenList(List<FixedCheckWorkContent> workContentList , String djxmid,String czls,String zdid, String dprtcode)throws RuntimeException {
		int num = 0;
		User user = ThreadVarUtil.getUser();
		String maxNbxh = checkWorkContentMapper.queryNbxhByDjxmid(djxmid);
		if(null != maxNbxh){
			num = Integer.parseInt(maxNbxh);
		}
		List<String> columnValueList = new ArrayList<String>();
		for (FixedCheckWorkContent workContent : workContentList) {
			num ++;
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();
			workContent.setId(id);
			workContent.setDjxmid(djxmid);
			workContent.setNbxh(num + "");
			workContent.setDprtcode(dprtcode);
			workContent.setWhrid(user.getId());
			workContent.setWhbmid(user.getBmdm());
			checkWorkContentMapper.insertSelective(workContent);
			columnValueList.add(id);
			saveFixedCheckWorkPlane(workContent.getFixedCheckWorkPlaneList(),workContent.getId(),user.getId(),czls, zdid);//新增定检工作对应飞机关系
		}
		if(columnValueList.size() > 0){
			commonRecService.write("id", columnValueList, TableEnum.B_G_01202, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//插入定检工作内容日志
		}
	}
	
	/**
	 * @author liub
	 * @description 修改检工作内容集合
	 * @param fixedCheckWorkContentList , 定检编号djbh
	 * @develop date 2016.08.29
	 */
	@Override
	public void editWorkConetenList(List<FixedCheckWorkContent> workContentList , String djxmid,String czls,String zdid, String dprtcode)throws RuntimeException {
		
		int num = 0;
		User user = ThreadVarUtil.getUser();
		String maxNbxh = checkWorkContentMapper.queryNbxhByDjxmid(djxmid);
		if(null != maxNbxh){
			num = Integer.parseInt(maxNbxh);
		}
		
		for (FixedCheckWorkContent workContent : workContentList) {
			if(null != workContent.getId() && !"".equals(workContent.getId())){
				workContent.setWhrid(user.getId());
				workContent.setWhbmid(user.getBmdm());
				checkWorkContentMapper.updateByPrimaryKeySelective(workContent);
				commonRecService.write(workContent.getId(), TableEnum.B_G_01202, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, zdid);//插入定检工作内容日志
				updateFixedCheckWorkPlane(workContent.getFixedCheckWorkPlaneList(), workContent.getId(), user.getId(), czls, zdid);
			}else{
				num++;
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();	
				workContent.setId(id);
				workContent.setDjxmid(djxmid);
				workContent.setNbxh(num + "");
				workContent.setDprtcode(dprtcode);
				workContent.setWhrid(user.getId());
				workContent.setWhbmid(user.getBmdm());
				checkWorkContentMapper.insertSelective(workContent);
				commonRecService.write(id, TableEnum.B_G_01202, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//插入定检工作内容日志
				saveFixedCheckWorkPlane(workContent.getFixedCheckWorkPlaneList(),workContent.getId(),user.getId(),czls, zdid);//新增定检工作对应飞机关系
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 根据条件分页查询差异列表
	 * @param baseEntity
	 * @return List<Map<String, Object>> 
	 * @develop date 2017.02.09
	 */
	@Override
	public List<Map<String, Object>> queryDiffPageList(BaseEntity baseEntity) throws RuntimeException {
		return checkWorkContentMapper.queryDiffPageList(baseEntity);
	}
	
	/**
	 * @author liub
	 * @description 新增定检工作对应飞机关系
	 * @param fixedCheckWorkPlaneList
	 * @develop date 2016.10.24
	 */
	private void saveFixedCheckWorkPlane(List<FixedCheckWorkPlane> fixedCheckWorkPlaneList,String mainId,String userId,String czls,String zdid){
		List<String> columnValueList = new ArrayList<String>();
		for (FixedCheckWorkPlane fixedCheckWorkPlane : fixedCheckWorkPlaneList) {
			UUID uuid = UUID.randomUUID();
			String id = uuid.toString();	
			fixedCheckWorkPlane.setId(id);
			fixedCheckWorkPlane.setMainid(mainId);
			fixedCheckWorkPlane.setWhrid(userId);
			fixedCheckWorkPlaneMapper.insertSelective(fixedCheckWorkPlane);
			columnValueList.add(id);
		}
		if(columnValueList.size() > 0){
			commonRecService.write("id", columnValueList, TableEnum.B_G_0120201, userId,czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//插入定检工作内容与机型关系日志
		}
	}
	
	/**
	 * @author liub
	 * @description 修改定检工作对应飞机关系
	 * @param fixedCheckWorkPlaneList
	 * @develop date 2016.10.24
	 */
	private void updateFixedCheckWorkPlane(List<FixedCheckWorkPlane> fixedCheckWorkPlaneList,String mainId,String userId,String czls,String zdid){
		
		List<String> fjzchList = new ArrayList<String>();//飞机注册号集合
		List<String> oldFjzchList = new ArrayList<String>();//旧的飞机注册号集合
		List<FixedCheckWorkPlane> oldFixedCheckWorkPlaneList = fixedCheckWorkPlaneMapper.selectByMainid(mainId);//获取旧的定检工作对应飞机集合
		for (FixedCheckWorkPlane fixedCheckWorkPlane : oldFixedCheckWorkPlaneList) {
			oldFjzchList.add(fixedCheckWorkPlane.getFjzch());
		}
		List<String> addColumnValueList = new ArrayList<String>();//需要新增的日志id集合
		for (FixedCheckWorkPlane fixedCheckWorkPlane : fixedCheckWorkPlaneList) {
			fjzchList.add(fixedCheckWorkPlane.getFjzch());
			if(!oldFjzchList.contains(fixedCheckWorkPlane.getFjzch())){
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();	
				fixedCheckWorkPlane.setId(id);
				fixedCheckWorkPlane.setMainid(mainId);
				fixedCheckWorkPlane.setWhrid(userId);
				fixedCheckWorkPlaneMapper.insertSelective(fixedCheckWorkPlane);
				addColumnValueList.add(id);
			}
		}
		if(addColumnValueList.size() > 0){
			commonRecService.write("id", addColumnValueList, TableEnum.B_G_0120201, userId,czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, zdid);//插入定检工作内容与机型关系日志
		}
		for (FixedCheckWorkPlane fixedCheckWorkPlane : oldFixedCheckWorkPlaneList) {
			if(!fjzchList.contains(fixedCheckWorkPlane.getFjzch())){
				commonRecService.write(fixedCheckWorkPlane.getId(), TableEnum.B_G_0120201, userId, czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, zdid);//插入定检工作内容与机型关系日志
				fixedCheckWorkPlaneMapper.deleteByPrimaryKey(fixedCheckWorkPlane.getId());
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 拼接定检工卡
	 * @param jobCardList
	 * @develop date 2016.10.12
	 */
	private String getJobCard(List<JobCard> jobCardList){
		StringBuffer buffer = new StringBuffer();
		if(null != jobCardList && 0 < jobCardList.size()){
			
			Collections.sort(jobCardList, new Comparator<JobCard>() {
    	        public int compare(JobCard o1, JobCard o2) {
    	            return o1.getGdbh().compareTo(o2.getGdbh()) ;
    	        }
    	    });
			
			for (JobCard jobCard : jobCardList) {
				buffer.append(jobCard.getGdbh()).append("@#").append(JobCardStatusEnum.getName(jobCard.getZt())).append(",");
			}
		}
		if(0 < buffer.length()){
			buffer.deleteCharAt(buffer.length()-1);
		}
		return buffer.toString();
	}
	
}
