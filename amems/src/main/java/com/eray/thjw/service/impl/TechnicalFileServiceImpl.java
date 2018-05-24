package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.ReserveMain;
import com.eray.thjw.dao.QualityCloseMapper;
import com.eray.thjw.dao.TechnicalFileMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.monitorsettings.service.MonitorsettingsService;
import com.eray.thjw.po.Affected;
import com.eray.thjw.po.Monitorsettings;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.Pagination;
import com.eray.thjw.po.QualityClose;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.TechnicalUpload;
import com.eray.thjw.po.User;
import com.eray.thjw.service.AffectedService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OrderSourceService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.service.TechnicalFileService;
import com.eray.thjw.service.TechnicalUploadService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.SessionUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.DemoStatus;
import enu.LatestLogoEnum;
import enu.LogOperationEnum;
import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.StatusEnum;
import enu.TableEnum;
import enu.ThresholdEnum;
import enu.UpdateTypeEnum;
import enu.ZtEnum;
import enu.aerialmaterial.StockStatusEnum;
import enu.common.EffectiveEnum;

@Service
public class TechnicalFileServiceImpl implements TechnicalFileService {

	@Autowired
	private TechnicalFileMapper technicalFileMapper;
	
	@Autowired
	private CommonRecService commonRecService;
	
	@Autowired
	private TechnicalUploadService technicalUploadService;
	
	@Autowired
	private AffectedService affectedService;
	
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	@Autowired
	private MonitorsettingsService monitorsettingsService;
	
	@Autowired
	private OrderSourceService orderSourceService;
	@Resource
	private PlaneModelDataService planeModelDataService;
	
	@Resource
	private QualityCloseMapper qualityCloseMapper;
	
	Map<String, Object> resultMap = new HashMap<String, Object>();
	
	@Override
	public List<TechnicalFile> queryAllPageList(TechnicalFile technicalFile)
			throws BusinessException {
		try {
			List<TechnicalFile> technicalFileList = technicalFileMapper.queryAllPageList(technicalFile);
			setAffectedList(technicalFileList);
			return technicalFileList;
		} catch (BusinessException e) {
			throw new BusinessException("操作失败");
		}
	}
	
	@Override
	public int validationQueryCount(TechnicalFile technicalFile) throws BusinessException {
		
		try {
			return technicalFileMapper.validationQueryCount(technicalFile);
		} catch (BusinessException e) {
			throw new BusinessException("操作失败");
		}
	}

	/**
	  * @author sunji
	  * @description 查询评估单信息总记录数
	  * @param technicalFile 评估单参数
	  * @develop date 2016.08.18
	  * @return int 评估单信息
	  */
	public List<TechnicalFile> queryAllByDocumentsId(TechnicalFile technicalFile) {
		List<TechnicalFile> list = technicalFileMapper.queryAllByDocumentsId(technicalFile);
		return list;
		
	}
	
	

	@Override
	public Map<String, Object> save(TechnicalFile technicalFile,TechnicalUpload technicalUpload)
			throws BusinessException {
		if (technicalFile == null) {
			throw new BusinessException("操作失败");
		}
		User user = ThreadVarUtil.getUser();
		//检查是否有飞机权限存在
		List<String> jxList=new ArrayList<String>();
		jxList.add(technicalFile.getJx());
		boolean b=planeModelDataService.existsModel(user.getId(),user.getUserType(),user.getJgdm(), jxList);
		if(!b){
			throw new BusinessException("机型权限已变更,请刷新后再进行操作");
		}
		String czls=UUID.randomUUID().toString();
		
		try {
			technicalFile.setId(UUID.randomUUID().toString());
			
			technicalFile.setPgdh(saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JSPG.getName()));
			
			technicalFile.setZt(ZtEnum.NOT_EVALUATED.getId());
			
			technicalFile.setDprtcode(user.getJgdm());
			technicalFile.setFzbmid(user.getBmdm());
			technicalFile.setFzrid(user.getId());
			technicalFile.setZdbmid(user.getBmdm());
			technicalFile.setZdrid(user.getId());
			resultMap.put("id", technicalFile.getId());
			technicalFileMapper.save(technicalFile);
			
			technicalUpload.setSm("");
			technicalUpload.setYxzt(DemoStatus.TAKE_EFFECT.getId());
			technicalUpload.setCzsj("1");
			technicalUpload.setCzbmid(user.getBmdm());
			technicalUpload.setCzrid(user.getId());
			technicalUpload.setDprtCode(user.getJgdm());
			technicalUpload.setMainid(technicalFile.getId());
			technicalUpload.setCzls(czls);
			technicalUpload.setTechnicalfileId(technicalFile.getId());
			List<String> list = new ArrayList<String>();
			list.add(technicalFile.getJx());
			planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), technicalFile.getDprtcode(), list);
			technicalUploadService.save(technicalUpload);
			if(technicalFile.getZt()==ZtEnum.NOT_EVALUATED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,technicalFile.getId());
			}else{
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SUBMIT_WO, UpdateTypeEnum.SAVE,technicalFile.getId());
			}
			
			resultMap.put("state", "Success");
			 
		} catch (SaibongException e) {
			resultMap.put("state", "Failure");
			throw new BusinessException("操作失败");
		} catch (Exception e) {
			throw new BusinessException("操作失败");
		}
		finally{
		}
		return	resultMap;
	}

	@Override
	public TechnicalFile queryAll(TechnicalFile technicalFile) throws BusinessException {
		
		return technicalFileMapper.queryAll(technicalFile);
	}

	@Override
	public Map<String, Object> modify(TechnicalFile technicalFile,TechnicalUpload technicalUpload)throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		if (technicalFile == null) {
			throw new BusinessException("操作失败");
		}
		/*resultMap=validatePri(user, technicalFile);*/
		int []i={0,5,6};
		boolean b=this.verification(technicalFile.getId(), i);
		if(b){
			resultMap.put("state", "error");
			resultMap.put("text", "该技术文件已更新，请刷新后再进行操作");
			return resultMap;
		}
		try {
			technicalFileMapper.modify(technicalFile);
			 commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls,technicalFile.getLogOperationEnum().EDIT,UpdateTypeEnum.UPDATE,technicalFile.getId());
			 
		 	technicalUpload.setSm("");
			technicalUpload.setYxzt(DemoStatus.TAKE_EFFECT.getId());
			technicalUpload.setCzsj("1");
			technicalUpload.setCzbmid(user.getBmdm());
			technicalUpload.setCzrid(user.getId());
			technicalUpload.setDprtCode(user.getJgdm());
			technicalUpload.setMainid(technicalFile.getId());
			technicalUpload.setCzls(czls);
			technicalUpload.setTechnicalfileId(technicalFile.getId());
			technicalUploadService.modify(technicalUpload);
			
			//commonRecService.write(technicalFile.getId(), TableEnum.B_G_00101, user.getId(), czls, technicalFile.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE);
			resultMap.put("state", "Success");
		} catch (BusinessException e) {
			resultMap.put("state", "Failure");
			throw new BusinessException("操作失败",e);
		}
		
		return	resultMap;
	}

	@Override
	public int delete(TechnicalFile technicalFile)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int i=technicalFileMapper.delete(technicalFile);
		 commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls,technicalFile.getLogOperationEnum().ZUOFEI,UpdateTypeEnum.UPDATE,technicalFile.getId());
		return i;
	}

	@Override
	public List<TechnicalFile> selectChoosePgd(String id) {
		return technicalFileMapper.selectChoosePgd(id);
	}
	
	@Override
	public Map<String, Object> assess(TechnicalFile technicalFile)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		if (technicalFile == null) {
			throw new BusinessException("操作失败");
		}
		
		int []i={ZtEnum.NOT_EVALUATED.getId(),ZtEnum.REVIEW_THE_REJECTED.getId(),ZtEnum.APPROVE_TURN_.getId()};
		boolean b=this.verification(technicalFile.getId(), i);
		if(b){
			resultMap.put("state", "error");
			resultMap.put("text", "该技术文件已更新，请刷新后再进行操作");
			return resultMap;
		}
		
		try {
			affectedService.save(technicalFile.getAffected(),technicalFile.getId(),technicalFile.getList(),technicalFile.getList1(),czls,technicalFile.getId());
			technicalFileMapper.assess(technicalFile);
			if(technicalFile.getZt()==ZtEnum.NOT_EVALUATED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.EVALUATED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().PINGGU, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.CHECKED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.APPROVE.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.SUSPEND.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.REVIEW_THE_REJECTED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.APPROVE_TURN_.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.CANCELLATION.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.CLOSE.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().GUANBI, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else {
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}
			 //commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().PINGGU, UpdateTypeEnum.UPDATE,technicalFile.getId());
			resultMap.put("state", "Success");
		} catch (BusinessException e) {
			resultMap.put("state", "Failure");
			throw new BusinessException("操作失败",e);
		}
		finally{
		}
		
		return	resultMap;
	}
	
	@Override
	public Map<String, Object> updateShenhe(TechnicalFile technicalFile)
			throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		if (technicalFile == null) {
			throw new BusinessException("操作失败");
		}
		boolean b=false;
		if(technicalFile.getZt()==ZtEnum.CHECKED.getId() || technicalFile.getZt()==ZtEnum.REVIEW_THE_REJECTED.getId()){
			int [] i ={ZtEnum.EVALUATED.getId()};
			b=this.verification(technicalFile.getId(), i);
		}else if(technicalFile.getZt()==ZtEnum.CLOSE.getId()){
			int [] i ={ZtEnum.APPROVE.getId()};
			b=this.verification(technicalFile.getId(), i);
		}else{
			int [] i ={ZtEnum.CHECKED.getId()};
			b=this.verification(technicalFile.getId(), i);
		}
		if(b){
			resultMap.put("state", "error");
			resultMap.put("text", "该技术文件已更新，请刷新后再进行操作");
			return resultMap;
		}
		
		try {
			technicalFileMapper.assess(technicalFile);
			
			if(technicalFile.getZt()==ZtEnum.NOT_EVALUATED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.EVALUATED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().PINGGU, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.CHECKED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.APPROVE.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().YIPIZHUN_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.SUSPEND.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SHOUTDOWN_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.REVIEW_THE_REJECTED.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.APPROVE_TURN_.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.CANCELLATION.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else if(technicalFile.getZt()==ZtEnum.CLOSE.getId()){
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().GUANBI, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}else {
				commonRecService.write(technicalFile.getId(), TableEnum.B_G_001, user.getId(), czls, technicalFile.getLogOperationEnum().YISHENHE_WO, UpdateTypeEnum.UPDATE,technicalFile.getId());
			}
			
			
			
			
			
			resultMap.put("state", "Success");
		} catch (BusinessException e) {
			resultMap.put("state", "Failure");
			throw new BusinessException("操作失败",e);
		}
		finally{
		}
		
		return	resultMap;
	}
	
	
	/**
	 * @author liub
	 * @description 验证技术文件
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePri(User user ,TechnicalFile technicalFile) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//验证技术文件是否存在
		if(null == technicalFile){
			returnMap.put("state", "error");
			returnMap.put("message", "技术文件不存在!");
			return returnMap;
		}
		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> checkUpdMt(User user, String id) throws BusinessException {
		TechnicalFile technicalFile=new TechnicalFile();
		technicalFile.setId(id);
		TechnicalFile technicalFile1 = technicalFileMapper.queryAll(technicalFile);
		
		Map<String, Object> returnMap = validatePri(user, technicalFile1);
	
		//验证维修方案操作权限
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		
		//验证是否生效
		Integer zt = technicalFile1.getZt();
		if( ZtEnum.APPROVE.getId().intValue()==zt.intValue()){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "只有已批准状态才能进行操作!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> checkUpdMtAssess(User user, String id)
			throws BusinessException {
		TechnicalFile technicalFile=new TechnicalFile();
		technicalFile.setId(id);
		TechnicalFile technicalFile1 = technicalFileMapper.queryAll(technicalFile);
		
		Map<String, Object> returnMap = validatePriAssess(user, technicalFile1);
	
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		
		//验证是否生效
		Integer zt = technicalFile1.getZt();
		if( ZtEnum.NOT_EVALUATED.getId().intValue() == zt.intValue()||ZtEnum.REVIEW_THE_REJECTED.getId().intValue() == zt.intValue()||ZtEnum.APPROVE_TURN_.getId().intValue() == zt.intValue()){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "只有未评估，审核驳回，审批驳回的状态才能评估!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	/**
	 * @author liub
	 * @description 验证技术文件
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePriAssess(User user ,TechnicalFile technicalFile) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//验证技术文件是否存在
		if(null == technicalFile){
			returnMap.put("state", "error");
			returnMap.put("message", "技术文件不存在!");
			return returnMap;
		}

		//验证评估人
		String pgrid = technicalFile.getPgrid();
		if(!pgrid.equals(user.getId())){
			returnMap.put("state", "error");
			returnMap.put("message", "对不起,只有此技术文件的机型工程师才有权限操作!");
			return returnMap;
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}
	
	
	/**
	 * @author liub
	 * @description 验证技术文件
	 * @param user，id
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	private Map<String, Object> validatePr(User user ,TechnicalFile technicalFile) {

		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		
		//验证技术文件是否存在
		if(null == technicalFile){
			returnMap.put("state", "error");
			returnMap.put("message", "技术文件不存在!");
			return returnMap;
		}
		
		returnMap.put("state", "success");
		return returnMap;
	}
	
	
	@Override
	public Map<String, Object> checkUpdMtAudit(User user, String id)
			throws Exception {
		TechnicalFile technicalFile=new TechnicalFile();
		technicalFile.setId(id);
		TechnicalFile technicalFile1 = technicalFileMapper.queryAll(technicalFile);
		
		Map<String, Object> returnMap = validatePr(user, technicalFile1);
	
		//验证维修方案操作权限
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		
		//验证是否生效
		Integer zt = technicalFile1.getZt();
		if( ZtEnum.EVALUATED.getId().intValue() == zt.intValue()){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "只有已评估的状态才能审核!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> checkUpdMtApproval(User user, String id)
			throws BusinessException {
		TechnicalFile technicalFile=new TechnicalFile();
		technicalFile.setId(id);
		TechnicalFile technicalFile1 = technicalFileMapper.queryAll(technicalFile);
		
		Map<String, Object> returnMap = validatePr(user, technicalFile1);
	
		//验证维修方案操作权限
		if (!"success".equals(returnMap.get("state"))) {	
			return returnMap;
		}
		
		//验证是否生效
		Integer zt = technicalFile1.getZt();
		if( ZtEnum.CHECKED.getId().intValue() == zt.intValue()){
			returnMap.put("state", "success");
		}else{
			returnMap.put("state", "error");
			returnMap.put("message", "只有已审核的状态才能批准!");
			return returnMap;
		}

		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public Map<String, Object> checkUpdMt1(User user, String id)
			throws BusinessException {
		TechnicalFile technicalFile=new TechnicalFile();
		technicalFile.setId(id);
		TechnicalFile technicalFile1;
		try {
			technicalFile1 = technicalFileMapper.queryAll(technicalFile);
			Map<String, Object> returnMap = validatePri(user, technicalFile1);
			
			//验证维修方案操作权限
			if (!"success".equals(returnMap.get("state"))) {	
				return returnMap;
			}
			
			//验证是否生效
			Integer zt = technicalFile1.getZt();
			if( ZtEnum.NOT_EVALUATED.getId().intValue()==zt.intValue() || ZtEnum.REVIEW_THE_REJECTED.getId().intValue()==zt.intValue() || ZtEnum.APPROVE_TURN_.getId().intValue()==zt.intValue()){
				returnMap.put("state", "success");
			}else{
				returnMap.put("state", "error");
				returnMap.put("message", "只有审核驳回，审批驳回，未评估状态才能进行操作!");
				return returnMap;
			}
			
			returnMap.put("state", "success");
			return returnMap;
		} catch (Exception e) {
			throw new BusinessException("操作失败");
		}
		
	}

	@Override
	public TechnicalFile selectTechnicalFileByPrimaryKey(String id) throws BusinessException {
		
		try {
			return technicalFileMapper.selectTechnicalFileByPrimaryKey(id);
		} catch (BusinessException e) {
			throw new BusinessException("操作失败");
		}
	}

	@Override
	public TechnicalFile selectByPrimaryKey (String id) throws BusinessException {
		return technicalFileMapper.selectByPrimaryKey(id);
	}

	
	public boolean verification(String id, int [] i)throws BusinessException{
		User user = ThreadVarUtil.getUser();
		boolean b=true;
		TechnicalFile technicalFile;
		try {
			technicalFile = this.selectByPrimaryKey(id);
			//检查是否有飞机权限存在
			List<String> jxList=new ArrayList<String>();
			jxList.add(technicalFile.getJx());
			if(jxList.size()>0){
				boolean fola=planeModelDataService.existsModel(user.getId(),user.getUserType(),technicalFile.getDprtcode(), jxList);
				if(!fola){
					b=false;
				}
			}
			for (int a = 0; a < i.length; a++) {
				if(technicalFile.getZt()==i[a]){
					b=false;
				}
			}
		} catch (BusinessException e) {
			throw new BusinessException("操作失败");
		}
		
		
		return b;
	}

	/**
	 * @author liub
	 * @description 设置获取执行对象,并赋值
	 * @param technicalFileList
	 */
	private void setAffectedList(List<TechnicalFile> technicalFileList) throws BusinessException{
		List<String> mainIdList = new ArrayList<String>();
		for (TechnicalFile technicalFile : technicalFileList) {
			if(1 == technicalFile.getSyx()){
				mainIdList.add(technicalFile.getId());
			}
		}
		if(0 != mainIdList.size()){
			List<Affected> resultList = affectedService.queryListByPgdIds(mainIdList);
			for (TechnicalFile technicalFile : technicalFileList) {
				if(1 == technicalFile.getSyx()){
					List<Affected> affectedList = new ArrayList<Affected>();
					for (Affected affected : resultList) {
						if(technicalFile.getId().equals(affected.getMainid())){
							affectedList.add(affected);
						}
					}
					technicalFile.setAffected(affectedList);
				}
			}
		}
	}
	/**
	 * sunji
	 * 查询列表
	 * @param technicalFile
	 * @return map
	 * @throws BusinessException
	 */
	public Map<String, Object> queryAllTechnicalfile(TechnicalFile technicalFile) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//缓存id
		String id=technicalFile.getId();
		technicalFile.setId("");
		//获取当前userd对象
		User user = ThreadVarUtil.getUser();
		//验证机型权限
		technicalFile.getParamsMap().put("userId", user.getId()); 
		technicalFile.getParamsMap().put("userType",user.getUserType());
		
		PageHelper.startPage(technicalFile.getPagination());
		// 根据条件查询列表数据
		List<TechnicalFile> list = technicalFileMapper.queryAllPageList(technicalFile);
		//拼接评估结果
		assembly(list);
		setAffectedList(list);
		if(((Page)list).getTotal() > 0){
			List <String > ids=new ArrayList<String>();
			if(list.size()>0){
				for (TechnicalFile thf : list) {
					ids.add(thf.getId());
				}
			}
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					TechnicalFile newRecord = new TechnicalFile();
					newRecord.setId(id);
					newRecord.getParamsMap().put("userId", user.getId());
					Pagination page = new Pagination();
					newRecord.setPagination(page);
					//拼接评估结果
					assembly(list);
					List<TechnicalFile> newRecordList = technicalFileMapper.queryAllPageList(newRecord);
					
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}

				}
			}
			return PageUtil.pack4PageHelper(list, technicalFile.getPagination());
			 
			
		}else{
			List<TechnicalFile> newRecordList = new ArrayList<TechnicalFile>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				TechnicalFile newRecord = new TechnicalFile();
				newRecord.setId(id);
				newRecord.getParamsMap().put("userId", user.getId());
				Pagination page = new Pagination();
				newRecord.setPagination(page);
				//拼接评估结果
				assembly(list);
				newRecordList = technicalFileMapper.queryAllPageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, technicalFile.getPagination());
				}
				
			}
			return PageUtil.pack(0, newRecordList, technicalFile.getPagination());
		}
	}
	public List<TechnicalFile> assembly(List<TechnicalFile> list)throws BusinessException{
		List<String> pgdids=new ArrayList<String>();
		if(list.size()>0){
			for (TechnicalFile technicalFile : list) {
				pgdids.add(technicalFile.getId());
			}
			List<OrderSource> orderSourceList=orderSourceService.queryByTechnicalfileIds(pgdids);
			
			if(orderSourceList.size()>0){
				acquirePgjg(list,orderSourceList);
			}
		}
		
		return list;
	}
	public List<TechnicalFile> acquirePgjg(List<TechnicalFile> list,List<OrderSource> orderSourceList){
		Map<String, StringBuffer> map=new HashMap<String, StringBuffer>();
		for (OrderSource orderSource : orderSourceList) {
			// 判断键是否存在，如果存在，那么拼接
			if (map.containsKey(orderSource.getPgdid())) {
				map.put(orderSource.getPgdid(),map.get(orderSource.getPgdid()).append(",&sun1").append(orderSource.getZlbhAndZlzt()));
			} else {
				StringBuffer str=new StringBuffer();
				map.put(orderSource.getPgdid(), str.append(orderSource.getZlbhAndZlzt()));
			}
		}
		// 遍历列表数据，拼接评估结果
		for (TechnicalFile technicalFile : list) {
			technicalFile.setZlbh(map.get(technicalFile.getId())==null?"":map.get(technicalFile.getId()).toString());
		}
		
		return list;
	}
	

	/**
	 * @author liub
	 * @description 质量关闭
	 * @param qualityClose
	 */
	@Override
	public void qualityClose(QualityClose qualityClose) throws BusinessException{
		QualityClose oldObj = qualityCloseMapper.selectByMainId(qualityClose.getMainid());
		if(oldObj != null){
			throw new BusinessException("对不起,该技术文件已经质量关闭!");
		}
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		qualityClose.setId(id);
		qualityClose.setZt(EffectiveEnum.YES.getId());
		qualityClose.setWhrid(user.getId());
		qualityClose.setWhdwid(user.getBmdm());
		qualityCloseMapper.insertSelective(qualityClose);
		commonRecService.write(id, TableEnum.B_G_00103, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, qualityClose.getId());//保存历史记录信息
	}

}
