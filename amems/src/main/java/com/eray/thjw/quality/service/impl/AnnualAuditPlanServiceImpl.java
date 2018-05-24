	package com.eray.thjw.quality.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.dao.AttachmentMapper;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.AnnualAuditPlanMapper;
import com.eray.thjw.quality.dao.AnnualPlanMapper;
import com.eray.thjw.quality.dao.AuditMembersMapper;
import com.eray.thjw.quality.dao.PlanToNoticeMapper;
import com.eray.thjw.quality.po.AnnualAuditPlan;
import com.eray.thjw.quality.po.AnnualPlan;
import com.eray.thjw.quality.service.AnnualAuditPlanService;
import com.eray.thjw.quality.service.AnnualPlanService;
import com.eray.thjw.quality.service.AuditMembersService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.StringAndDate_Util;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.common.EffectiveEnum;
import enu.quality.AnnualPlanStatusEnum;
import enu.quality.BusinessTypeEnum;
/**
 * 
 * @Description 年度审核计划serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("annualAuditPlanService")
public class AnnualAuditPlanServiceImpl implements AnnualAuditPlanService  {

	@Resource
	private AnnualAuditPlanMapper annualAuditPlanMapper;
	
	@Resource
	private AnnualPlanMapper annualPlanMapper;
	@Resource
	private PlanToNoticeMapper planToNoticeMapper;
	@Resource
    private AttachmentMapper attachmentMapper;
	@Resource
	private AuditMembersMapper auditMembersMapper;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private AuditMembersService auditMembersService;
	
	@Resource
	private AnnualPlanService annualPlanService;
	
	@Resource
	private CommonService commonService;

	/**
	 * 
	 * @Description 年度审核计划分页查询列表
	 * @CreateTime 2018年1月8日 下午2:14:29
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAllPageList(AnnualAuditPlan annualAuditPlan)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = annualAuditPlan.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		annualAuditPlan.setId(null);
		PageHelper.startPage(annualAuditPlan.getPagination());
		List<AnnualAuditPlan> list = annualAuditPlanMapper.queryAllPageList(annualAuditPlan);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					AnnualAuditPlan param = new AnnualAuditPlan();
					param.setId(id);
					List<AnnualAuditPlan> newRecordList = annualAuditPlanMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, annualAuditPlan.getPagination());
		}else{
			List<AnnualAuditPlan> newRecordList = new ArrayList<AnnualAuditPlan>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				AnnualAuditPlan param = new AnnualAuditPlan();
				param.setId(id);
				newRecordList = annualAuditPlanMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, annualAuditPlan.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, annualAuditPlan.getPagination());
		}
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2017-12-22 下午1:09:33
	 * @CreateBy 刘兵
	 * @param paramObj 当前参数
	 * @return List<AnnualAuditPlan>
	 */
	@Override
	public List<AnnualAuditPlan> doExportExcel(AnnualAuditPlan paramObj) {
		List<AnnualAuditPlan> list = annualAuditPlanMapper.queryAllPageList(paramObj);
		for (AnnualAuditPlan annualAuditPlan : list) {
			annualAuditPlan.getParamsMap().put("dprtname", annualAuditPlan.getDprt() == null?"":annualAuditPlan.getDprt().getDprtname());
			String shcyStr = (String)annualAuditPlan.getParamsMap().get("shcy");
			if(StringUtils.isNotBlank(shcyStr)){
				StringBuffer zrr = new StringBuffer();
				String[] dataList = shcyStr.split(",");// 在每个逗号(,)处进行分解。
				for (int i = 0 ; i < dataList.length ; i++) {
					String[] arr = dataList[i].split("#_#",-1);
					zrr.append(arr[2]).append(" ").append(arr[3]);
					if(i < dataList.length - 1){
						zrr.append(",");
					}
				}
				annualAuditPlan.getParamsMap().put("zrr", zrr.toString());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description 新增年度审核计划
	 * @CreateTime 2018年1月8日 下午3:56:04
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String save(AnnualAuditPlan annualAuditPlan) throws BusinessException {
		String result = null;
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		AnnualPlan annualPlan = annualPlanMapper.getAnnualPlanById(annualAuditPlan.getNdjhid());
		//新增年度计划
		if(null == annualPlan){
			annualPlan = new AnnualPlan();
			annualPlan.setNf(annualAuditPlan.getNf());
			annualPlan.setBbh(1);
			//新增年度计划 
			annualPlanService.save(annualPlan);
			annualAuditPlan.setNdjhid(annualPlan.getId());
			annualAuditPlan.setBbh(1);
		}else{
			validation(annualPlan);//验证
			//版本升级
			if(AnnualPlanStatusEnum.APPROVAL.getId() == annualPlan.getZt()){
				int bbh = annualPlan.getBbh();
				//新增b_z_006新版本数据
				String newId = annualPlanService.doModify(annualPlan);
				//copy老数据
				copyOldData(newId, annualPlan.getDprtcode(), bbh, annualPlan.getNf(), "");
				annualAuditPlan.setNdjhid(newId);
				annualAuditPlan.setBbh(annualPlan.getBbh());
				result = uuid;
			}
		}
		//新增年度审核计划
		insertSelective(annualAuditPlan,uuid,user);
		//编辑附件信息
		attachmentService.eidtAttachment(annualAuditPlan.getAttachments(), uuid, null, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		//审核成员
		auditMembersService.inertSelective(annualAuditPlan.getAuditMembersList(),uuid, BusinessTypeEnum.SHNDJH.getId(), user.getJgdm(),user);
		
		return result;
	}

	/**
	 * @Description 新增数据
	 * @CreateTime 2018年1月8日 下午3:57:17
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @param uuid
	 * @param user
	 */
	private void insertSelective(AnnualAuditPlan annualAuditPlan, String uuid,User user) {
		Date currentDate = commonService.getSysdate();//当前时间
		
		annualAuditPlan.setId(uuid);						//id
		annualAuditPlan.setDprtcode(user.getJgdm());		//组织机构
		annualAuditPlan.setWhbmid(user.getBmdm());	        //部门id
		annualAuditPlan.setWhrid(user.getId());		        //用户id
		annualAuditPlan.setWhsj(currentDate);				//当前时间
		annualAuditPlan.setZt(EffectiveEnum.YES.getId());	//状态为有效
		annualAuditPlanMapper.insertSelective(annualAuditPlan);	
	}

	/**
	 * 
	 * @Description 根据id查询年度审核计划数据
	 * @CreateTime 2018年1月9日 下午2:14:38
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public AnnualAuditPlan getById(AnnualAuditPlan annualAuditPlan)throws BusinessException {
		return annualAuditPlanMapper.getById(annualAuditPlan);
	}

	/**
	 * @Description 修改年度审核计划
	 * @CreateTime 2018年1月8日 下午3:57:17
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @param uuid
	 * @param user
	 */
	@Override
	public String update(AnnualAuditPlan annualAuditPlan) throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		AnnualPlan annualPlan = annualPlanMapper.getAnnualPlanById(annualAuditPlan.getNdjhid());
		validation(annualPlan);//验证
		//版本升级
		if(AnnualPlanStatusEnum.APPROVAL.getId() == annualPlan.getZt()){
			int bbh = annualPlan.getBbh();
			//新增b_z_006新版本数据
			String newId = annualPlanService.doModify(annualPlan);
			//copy老数据
			String id = copyOldData(newId, annualPlan.getDprtcode(), bbh, annualPlan.getNf(), annualAuditPlan.getId());
			annualAuditPlan.setId(id);
			annualAuditPlan.setNdjhid(newId);
			annualAuditPlan.setBbh(annualPlan.getBbh());
		}
		
		//修改缺陷保留
		updateByPrimaryKeySelective(annualAuditPlan,user);
		//编辑附件信息
		attachmentService.eidtAttachment(annualAuditPlan.getAttachments(), annualAuditPlan.getId(), null, annualAuditPlan.getId(), user.getJgdm(), LogOperationEnum.EDIT);
		//审核成员
		auditMembersService.updateSelective(annualAuditPlan.getAuditMembersList(), annualAuditPlan.getId(), BusinessTypeEnum.SHNDJH.getId(), user.getJgdm(), user);
		
		return annualAuditPlan.getId();
	}


	/**
	 * 
	 * @Description 修改数据
	 * @CreateTime 2018年1月9日 下午2:37:57
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @param user
	 */
	private void updateByPrimaryKeySelective(AnnualAuditPlan annualAuditPlan,User user) {
		Date currentDate = commonService.getSysdate();//当前时间
		
		annualAuditPlan.setWhbmid(user.getBmdm());	        //部门id
		annualAuditPlan.setWhrid(user.getId());		        //用户id
		annualAuditPlan.setWhsj(currentDate);				//当前时间
		annualAuditPlanMapper.updateByPrimaryKeySelective(annualAuditPlan);	
	}

	/**
	 * 
	 * @Description 删除年度计划
	 * @CreateTime 2018年1月9日 下午2:37:57
	 * @CreateBy 林龙
	 * @param annualAuditPlan
	 * @param user
	 */
	@Override
	public String delete(AnnualAuditPlan annualAuditPlan)throws BusinessException {
		AnnualPlan annualPlan = annualPlanMapper.getAnnualPlanById(annualAuditPlan.getNdjhid());
		validation(annualPlan);//验证
		if(AnnualPlanStatusEnum.SAVE.getId() == annualPlan.getZt()
				|| AnnualPlanStatusEnum.AUDITDOWN.getId() == annualPlan.getZt()
				|| AnnualPlanStatusEnum.APPROVALDOWN.getId() == annualPlan.getZt()
				){
			deleteById(annualAuditPlan.getId());
			return null;
		}else{//版本升级
			int bbh = annualPlan.getBbh();
			//新增b_z_006新版本数据
			String newId = annualPlanService.doModify(annualPlan);
			//copy老数据
			String id = copyOldData(newId, annualPlan.getDprtcode(), bbh, annualPlan.getNf(), annualAuditPlan.getId());
			//根据此次删除的数据（老版本id）映射出新版本的id，执行删除操作
			deleteById(id);
			return id;
		}
		
	}
	
	/**
	 * @Description 删除年度计划
	 * @CreateTime 2018年1月9日 下午2:37:57
	 * @CreateBy 刘兵
	 * @param ndjhid 年度计划id
	 * @param dprtcode 机构代码
	 * @param nf 年份
	 * @param bbh 版本号
	 * @param oldId 老版本id
	 * @return resultId 需要操作的数据id
	 */
	private String copyOldData(String ndjhid, String dprtcode, int bbh, int nf, String ndshjhId){
		Date currentDate = commonService.getSysdate();//当前时间
		User user = ThreadVarUtil.getUser();		//当前登陆人
		List<AnnualAuditPlan> queryOldData = annualAuditPlanMapper.queryOldData(dprtcode, bbh, nf);
		String resultId = "";
		for (AnnualAuditPlan annualAuditPlan : queryOldData) {
			String uuid = UUID.randomUUID().toString();	//uuid-主单id
			String oldId = annualAuditPlan.getId();
			//老版本id映射新版本id
			if(null != annualAuditPlan.getId() && annualAuditPlan.getId().equals(ndshjhId)){
				resultId = uuid;
			}
			annualAuditPlan.setId(uuid);
			annualAuditPlan.setBbh(bbh + 1);
			annualAuditPlan.setWhbmid(user.getBmdm());	        //部门id
			annualAuditPlan.setWhrid(user.getId());		        //用户id
			annualAuditPlan.setWhsj(currentDate);				//当前时间
			annualAuditPlan.setZt(EffectiveEnum.YES.getId());	//状态为有效
			annualAuditPlanMapper.insertSelective(annualAuditPlan);	
			//copy附件
			attachmentMapper.insert4Copy(uuid, oldId);
			//copy审核成员
			auditMembersMapper.insert4Copy(uuid, oldId);
			//copy年度审核计划与审核通知单关系
			planToNoticeMapper.insert4Copy(uuid, oldId);
		}
		return resultId;
	}
	
	/**
	 * @Description 根据年度审核计划id删除
	 * @CreateTime 2018-1-19 上午10:25:05
	 * @CreateBy 刘兵
	 * @param id
	 * @throws BusinessException
	 */
	private void deleteById(String id)throws BusinessException{
		annualAuditPlanMapper.deleteByPrimaryKey(id);
		auditMembersService.delete(id);
		planToNoticeMapper.deleteByNdshjhid(id);
	}
	
	/**
	 * @Description 验证
	 * @CreateTime 2018-1-19 上午9:30:13
	 * @CreateBy 刘兵
	 * @param annualPlan 年度计划
	 * @throws BusinessException
	 */
	private void validation(AnnualPlan annualPlan) throws BusinessException{
		if(null == annualPlan){
			throw new BusinessException("年度计划不存在，请刷新后操作!");
		}
		if(!(AnnualPlanStatusEnum.SAVE.getId() == annualPlan.getZt()
				|| AnnualPlanStatusEnum.AUDITDOWN.getId() == annualPlan.getZt()
				|| AnnualPlanStatusEnum.APPROVALDOWN.getId() == annualPlan.getZt()
				||	(AnnualPlanStatusEnum.APPROVAL.getId() == annualPlan.getZt()
					&& annualPlan.getBbh() == StringAndDate_Util.ObjectToInt(annualPlan.getParamsMap().get("maxbbh"))))){
			throw new BusinessException("只有保存、驳回的数据才能操作!");
		}
	}

	



     
}