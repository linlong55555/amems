package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.SubstitutionApplicabilityMapper;
import com.eray.thjw.dao.SubstitutionMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.Substitution;
import com.eray.thjw.po.SubstitutionApplicability;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.SubstitutionService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
@Service
public class SubstitutionServiceImpl implements SubstitutionService{

	@Autowired
	private SubstitutionMapper substitutionMapper;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private SubstitutionApplicabilityMapper substitutionApplicabilityMapper;
	@Autowired
	private AttachmentService attachmentService;
	/**
	 * @author sunji
	 * @description 根据条件查询所有
	 * @param substitution
	 * @return List<Substitution>
	 */
	public Map<String , Object> queryAll(Substitution substitution)throws BusinessException {
			Map<String, Object> resultMap = new HashMap<String, Object>();
			String id=substitution.getId();
			substitution.setId("");
			try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(substitution.getPagination());
			List<Substitution> list=substitutionMapper.queryAll(substitution);
			//处理文件，控制页面显示
			 for(Substitution sub:list){
				 Attachment attachment=new Attachment();
				 attachment.setMainid(sub.getId());
				 List<Attachment> attachments=attachmentService.queryListAttachments(attachment);
				   if(attachments!=null&&attachments.size()!=0){
					   sub.setAttachments(attachments);
				   }
			 }
			if(((Page)list).getTotal() > 0){
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						Substitution newRecord = new Substitution();
						newRecord.setId(id);
						List<Substitution> newRecordList = substitutionMapper.queryAll(newRecord);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				setSubstitutionApplicability(list);
				resultMap=PageUtil.pack4PageHelper(list, substitution.getPagination());
				return resultMap;
				
			}else{
				List<Substitution> newRecordList = new ArrayList<Substitution>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					Substitution newRecord = new Substitution();
					newRecord.setId(id);
					newRecordList = substitutionMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, substitution.getPagination());
						return resultMap;
					}
					
				}
				setSubstitutionApplicability(newRecordList);
				resultMap= PageUtil.pack(0, newRecordList, substitution.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败",e);
		}
	}
	
	private void setSubstitutionApplicability(List<Substitution> substitutionList){
		
		if(substitutionList == null || substitutionList.size() == 0){
			return;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();//参数
		
		List<String> idList = new ArrayList<String>();
		for (Substitution substitution : substitutionList) {
			idList.add(substitution.getId());
		}
		paramMap.put("idList", idList);
		//获取等效替代部件集合
		List<SubstitutionApplicability> substitutionApplicabilityList = substitutionApplicabilityMapper.queryByMainidList(paramMap);
		if(substitutionApplicabilityList.size()>0){
			Map<String, List<SubstitutionApplicability>> resultMap = new HashMap<String, List<SubstitutionApplicability>>();//结果map
			for (SubstitutionApplicability substitutionApplicability : substitutionApplicabilityList) {
				// 判断键是否存在，如果不存在，那么放入
				if (!resultMap.containsKey(substitutionApplicability.getMainid())) {
					resultMap.put(substitutionApplicability.getMainid(), new ArrayList<SubstitutionApplicability>());
				}
				resultMap.get(substitutionApplicability.getMainid()).add(substitutionApplicability);
			}
			for (Substitution substitution : substitutionList) {
				substitution.setApplicabilityList(resultMap.get(substitution.getId()));
			}
		}

		
	}
	/**
	 * @author sunji
	 * @description 添加
	 * @param substitution
	 * @return id
	 */
	public String save(Substitution substitution) throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		String id = UUID.randomUUID().toString();
		
		//验证是否重复
		boolean only=this.getSubstitutionOnly(substitution);
		if(only){
			throw new BusinessException("改数据已存在，请修改部件号或替换件号");
		}
				
		substitution.setId(id);
		substitution.setZt(1);
		substitution.setWhrid(user.getId());
		substitution.setWhbmid(user.getBmdm());
		substitution.setWhsj(new Date());
		substitution.setDprtcode(user.getJgdm());
		substitutionMapper.insertSelective(substitution);
		commonRecService.write(id, TableEnum.D_017, user.getId(), czls, substitution.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,substitution.getId());
		
		//添加机型适用性和工卡适用性数据
		List<String> jxbsList=substitution.getJxbsList();
		List<String> gkbsList=substitution.getGkbsList();
		
		if(jxbsList!=null){
			for (String jxbsText : jxbsList) {
				SubstitutionApplicability substitutionApplicability=new SubstitutionApplicability();
				String subAppId=UUID.randomUUID().toString();
				substitutionApplicability.setId(subAppId);
				substitutionApplicability.setMainid(id);
				substitutionApplicability.setSyxlx(1);
				substitutionApplicability.setSydx(jxbsText);
				substitutionApplicability.setZt(1);
				substitutionApplicability.setWhbmid(user.getBmdm());
				substitutionApplicability.setWhrid(user.getId());
				substitutionApplicability.setWhsj(new Date());
				substitutionApplicabilityMapper.insertSelective(substitutionApplicability);
				commonRecService.write(subAppId, TableEnum.D_01701, user.getId(), czls, substitution.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,substitution.getId());
			}
		}
		if(gkbsList!=null){
			for (String gkbsText : gkbsList) {
				SubstitutionApplicability substitutionApplicability=new SubstitutionApplicability();
				String subAppId=UUID.randomUUID().toString();
				substitutionApplicability.setId(subAppId);
				substitutionApplicability.setMainid(id);
				substitutionApplicability.setSyxlx(2);
				substitutionApplicability.setSydx(gkbsText);
				substitutionApplicability.setZt(1);
				substitutionApplicability.setWhbmid(user.getBmdm());
				substitutionApplicability.setWhrid(user.getId());
				substitutionApplicability.setWhsj(new Date());
				substitutionApplicabilityMapper.insertSelective(substitutionApplicability);
				commonRecService.write(subAppId, TableEnum.D_01701, user.getId(), czls, substitution.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,substitution.getId());
			}
		}
		  //保存附件信息
		
		attachmentService.eidtAttachment(substitution.getAttachments(), substitution.getId(), czls, substitution.getId(), substitution.getDprtcode(),LogOperationEnum.SAVE_WO);
		
		return id;
	}
	
	/**
	 * @author sunji
	 * @description 修改
	 * @param substitution
	 * @return id
	 */
	public String update(Substitution substitution) throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		//验证是否重复
		boolean only=this.getSubstitutionOnly(substitution);
		if(only){
			throw new BusinessException("改数据已存在，请修改部件号或替换件号");
		}
		
		substitutionMapper.updateByPrimaryKeySelective(substitution);
		commonRecService.write(substitution.getId(), TableEnum.D_017, user.getId(), czls, substitution.getLogOperationEnum().EDIT, UpdateTypeEnum.UPDATE,substitution.getId());
		List<String> gkbsList=substitution.getGkbsList();
		List<String> jxbsList=substitution.getJxbsList();
		
		//修改工卡适用性和机型适用性
			List<SubstitutionApplicability> substitutionApplicabilityList=substitutionApplicabilityMapper.queryAllByMainid(substitution.getId());
			List<String> oldbsList=new ArrayList<String>();
			for (SubstitutionApplicability substitutionApplicability : substitutionApplicabilityList) {
				boolean a=gkbsList.contains(substitutionApplicability.getSydx());
				boolean b=jxbsList.contains(substitutionApplicability.getSydx());
				if(!a && !b){
					substitutionApplicabilityMapper.deleteByPrimaryKey(substitutionApplicability.getId());
					commonRecService.write(substitutionApplicability.getId(), TableEnum.D_01701, user.getId(), czls, substitution.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.UPDATE,substitution.getId());
				}
				oldbsList.add(substitutionApplicability.getSydx());
			}
			for (String gkbs : gkbsList) {
				if(!oldbsList.contains(gkbs) && !gkbs.equals("00000")){
					SubstitutionApplicability substitutionApplicability=new SubstitutionApplicability();
					String subAppId=UUID.randomUUID().toString();
					substitutionApplicability.setId(subAppId);
					substitutionApplicability.setMainid(substitution.getId());
					substitutionApplicability.setSyxlx(2);
					substitutionApplicability.setSydx(gkbs);
					substitutionApplicability.setZt(1);
					substitutionApplicability.setWhbmid(user.getBmdm());
					substitutionApplicability.setWhrid(user.getId());
					substitutionApplicability.setWhsj(new Date());
					substitutionApplicabilityMapper.insertSelective(substitutionApplicability);
					commonRecService.write(subAppId, TableEnum.D_01701, user.getId(), czls, substitution.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,substitution.getId());
				}
				
			}
			for (String jxbs : jxbsList) {
				if(!oldbsList.contains(jxbs) && !jxbs.equals("00000")){
					SubstitutionApplicability substitutionApplicability=new SubstitutionApplicability();
					String subAppId=UUID.randomUUID().toString();
					substitutionApplicability.setId(subAppId);
					substitutionApplicability.setMainid(substitution.getId());
					substitutionApplicability.setSyxlx(1);
					substitutionApplicability.setSydx(jxbs);
					substitutionApplicability.setZt(1);
					substitutionApplicability.setWhbmid(user.getBmdm());
					substitutionApplicability.setWhrid(user.getId());
					substitutionApplicability.setWhsj(new Date());
					substitutionApplicabilityMapper.insertSelective(substitutionApplicability);
					commonRecService.write(subAppId, TableEnum.D_01701, user.getId(), czls, substitution.getLogOperationEnum().SAVE_WO, UpdateTypeEnum.SAVE,substitution.getId());
				}
				
			}
			
			//保存附件
			
			attachmentService.eidtAttachment(substitution.getAttachments(), substitution.getId(), czls, substitution.getId(), substitution.getDprtcode(), LogOperationEnum.EDIT);
		
		return substitution.getId();
	}
	/** 
	 * @author sunji
	 * @description 作废
	 * @param substitution
	 * @return id
	 */
	public String invalid(Substitution substitution) throws BusinessException {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		substitution.setZt(0);
		substitutionMapper.updateByPrimaryKeySelective(substitution);
		commonRecService.write(substitution.getId(), TableEnum.D_017, user.getId(), czls, substitution.getLogOperationEnum().ZUOFEI, UpdateTypeEnum.DELETE,substitution.getId());
		return substitution.getId();
	}
	
	/**
	 * @author liub
	 * @description 根据id查询等效替代部件及对应的机型适应性、工卡适应性信息
	 * @param id
	 * @return Substitution
	 */
	@Override
	public Substitution selectById(String id){
		return substitutionMapper.selectById(id);
	}
	
	/**
	 * @Description 通过部件号、替代件号、机构代码获取等效替代部件信息
	 * @CreateTime 2017-11-21 下午5:31:32
	 * @CreateBy 刘兵
	 * @param bjh 部件号
	 * @param tdjh 替代件号
	 * @param dprtcode 机构代码
	 * @return Substitution 等效替代
	 */
	@Override
	public Substitution getSubByBjhAndTdjhAndDprt(String bjh, String tdjh, String dprtcode){
		return substitutionMapper.getSubByBjhAndTdjhAndDprt(bjh, tdjh, dprtcode);
	}
	
	/**
	 * @author liub
	 * @description 通过部件号、机构代码获取等效替代部件信息
	 * @param bjh,dprtcode
	 * @return List<Substitution>
	 */
	@Override
	public List<Substitution> selectSubByBjhAndDprt(String bjh,String dprtcode){
		return substitutionMapper.selectSubByBjhAndDprt(bjh,dprtcode);
	}
	/**
	 * @author sunji
	 * @description 通过部件号、机构代码获取等效替代部件信息
	 * @param subsitution
	 * @return boolean
	 */
	private boolean getSubstitutionOnly(Substitution substitution){
		boolean b=false;
		List<Substitution> SubstitutionList=substitutionMapper.queryAllByDprtcode(substitution);
		for (Substitution sub : SubstitutionList) {
			if(sub.getBjh().equals(substitution.getBjh()) && sub.getTdjh().equals(substitution.getTdjh()) 
					&& sub.getKnxbs()==substitution.getKnxbs()){
				b=true;
			}
		}
		return b;
	}

	@Override
	public List<Substitution> validateUnique(Substitution substitution) {
		List<Substitution> list = null;
		String id = substitution.getId();
		String bjh = "";
		String tdjh = "";
		if (!StringUtils.isBlank(id)) {// 如果id为null，则是新增。如果id不为null，则为修改
			Substitution sub = substitutionMapper.selectById(id);
			if (sub != null) {
				bjh = sub.getBjh();
				tdjh = sub.getTdjh();
			}
		}
		if (!(bjh.equals(substitution.getBjh()) && tdjh.equals(substitution
				.getTdjh()))) {
			list = substitutionMapper.validateUnique(substitution);
		}
		return list;
	}

}
