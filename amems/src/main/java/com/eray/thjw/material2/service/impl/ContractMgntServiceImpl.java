package com.eray.thjw.material2.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.material2.dao.ContractMgntMapper;
import com.eray.thjw.material2.po.ContractInfo;
import com.eray.thjw.material2.po.ContractMgnt;
import com.eray.thjw.material2.service.ContractInfoService;
import com.eray.thjw.material2.service.ContractMgntService;
import com.eray.thjw.material2.service.ContractPaymentService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.File_Util;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.material2.ContractMgntSaibongEnum;
import enu.material2.ContractMgntStatusEnum;
import enu.material2.ContractMgntTypeEnum;
import enu.material2.DeliveryEnum;

/**
 * @Description 合同管理service,用于业务逻辑处理
 * @CreateTime 2018-3-8 上午11:32:12
 * @CreateBy 刘兵
 */
@Service
public class ContractMgntServiceImpl implements ContractMgntService{

	@Resource
	private ContractMgntMapper contractMgntMapper;
	
	@Resource
	private ContractInfoService contractInfoService;
	
	@Resource
	private ContractPaymentService contractPaymentService;
	
	@Resource
	private AttachmentService attachmentService;
	
	
	/**
	 * @Description 保存合同
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	@Override
	public String save(ContractMgnt contractMgnt) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//检查合同是否存在
		checkExist(contractMgnt.getHth(), user.getJgdm());
		contractMgnt.setZt(ContractMgntStatusEnum.SAVE.getId());
		if(null != contractMgnt.getParamsMap() && null != contractMgnt.getParamsMap().get("operationType")){
			contractMgnt.setZt(ContractMgntStatusEnum.SUBMIT.getId());
		}
		if(StringUtils.isBlank(contractMgnt.getHth())){
			contractMgnt.setHth(buildhth(contractMgnt.getHtlx(), user.getJgdm()));
		}
		//获取随机的uuid
		String id = UUID.randomUUID().toString();
		contractMgnt.setId(id);
		contractMgnt.setWhrid(user.getId());
		contractMgnt.setWhbmid(user.getBmdm());
		contractMgnt.setDprtcode(user.getJgdm());
		//保存合同
		contractMgntMapper.insertSelective(contractMgnt);
		//保存附件信息
		attachmentService.eidtAttachment(contractMgnt.getAttachmentList(), id, czls, id, contractMgnt.getDprtcode(), LogOperationEnum.SAVE_WO);
		//保存合同明细
		contractInfoService.saveContractInfoList(contractMgnt.getContractInfoList(), id);
		if(contractMgnt.getZt() == ContractMgntStatusEnum.SUBMIT.getId()){
			//修改合同状态为批准
//			contractMgntMapper.updateZtById(ContractMgntStatusEnum.HEISHI.getId(), id);
		}
		
		return id;
	}
	
	/**
	 * @Description 编辑合同
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	@Override
	public String update(ContractMgnt contractMgnt) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		ContractMgnt cm = contractMgntMapper.selectByPrimaryKey(contractMgnt.getId());
		if(null == cm){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{ContractMgntStatusEnum.SAVE.getId()},cm.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		contractMgnt.setZt(ContractMgntStatusEnum.SAVE.getId());
		if(null != contractMgnt.getParamsMap() && null != contractMgnt.getParamsMap().get("operationType")){
			contractMgnt.setZt(ContractMgntStatusEnum.SUBMIT.getId());
		}
		contractMgnt.setWhrid(user.getId());
		contractMgnt.setWhbmid(user.getBmdm());

		//编辑工卡
		contractMgntMapper.updateByPrimaryKeySelective(contractMgnt);
		//编辑合同明细
		contractInfoService.updateContractInfoList(contractMgnt.getContractInfoList(), contractMgnt.getId(), false);
		//编辑附件信息
		attachmentService.eidtAttachment(contractMgnt.getAttachmentList(), contractMgnt.getId(), czls, contractMgnt.getId(), contractMgnt.getDprtcode(), LogOperationEnum.EDIT);
		if(contractMgnt.getZt() == ContractMgntStatusEnum.SUBMIT.getId()){
			//修改合同状态为批准
//			contractMgntMapper.updateZtById(ContractMgntStatusEnum.HEISHI.getId(), contractMgnt.getId());
		}
		return contractMgnt.getId();
	}
	
	/**
	 * @Description 修订合同
	 * @CreateTime 2018-3-13 上午10:01:06
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return String 合同id
	 * @throws BusinessException
	 */
	@Override
	public String updateRevise(ContractMgnt contractMgnt) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		ContractMgnt cm = contractMgntMapper.selectByPrimaryKey(contractMgnt.getId());
		if(null == cm){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				ContractMgntStatusEnum.SUBMIT.getId()
				,ContractMgntStatusEnum.HEISHI.getId()
				,ContractMgntStatusEnum.REVISION.getId()},cm.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		contractMgnt.setZt(ContractMgntStatusEnum.REVISION.getId());
		contractMgnt.setWhrid(user.getId());
		contractMgnt.setWhbmid(user.getBmdm());
		//编辑合同
		contractMgntMapper.updateByPrimaryKeySelective(contractMgnt);
		//编辑合同明细
		contractInfoService.updateContractInfoList(contractMgnt.getContractInfoList(), contractMgnt.getId(), true);
		//编辑附件信息
		attachmentService.eidtAttachment(contractMgnt.getAttachmentList(), contractMgnt.getId(), czls, contractMgnt.getId(), contractMgnt.getDprtcode(), LogOperationEnum.EDIT);
		return contractMgnt.getId();
	}
	
	/**
	 * @Description 删除合同
	 * @CreateTime 2018-3-13 上午10:27:55
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @throws BusinessException
	 */
	@Override
	public void delete(String id) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		ContractMgnt cm = contractMgntMapper.selectByPrimaryKey(id);
		if(null == cm){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{ContractMgntStatusEnum.SAVE.getId()},cm.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//删除合同
		contractMgntMapper.deleteByPrimaryKey(id);
		//删除合同明细
		contractInfoService.deleteByMainid(id);
		//删除合同付款
		contractPaymentService.deleteByMainid(id);
		//删除附件信息
		attachmentService.delFiles(id, czls, id, LogOperationEnum.DELETE);
	}
	
	/**
	 * @Description 关闭合同
	 * @CreateTime 2018-3-13 上午11:55:23
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @throws BusinessException
	 */
	@Override
	public void updateShutDown(ContractMgnt contractMgnt) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		ContractMgnt cm = contractMgntMapper.selectByPrimaryKey(contractMgnt.getId());
		if(null == cm){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		validation4CurrentZt(new Integer[]{
				ContractMgntStatusEnum.SUBMIT.getId()
				,ContractMgntStatusEnum.HEISHI.getId()
				,ContractMgntStatusEnum.REVISION.getId()},cm.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//关闭合同
		contractMgnt.setGbrid(user.getId());
		contractMgntMapper.updateByPrimaryKeySelective(contractMgnt);
	}
	
	/**
	 * @Description 合同分页列表查询
	 * @CreateTime 2018-3-9 上午11:19:00
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAllPageList(ContractMgnt contractMgnt){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = contractMgnt.getId();
		contractMgnt.setId("");
		PageHelper.startPage(contractMgnt.getPagination());
		List<ContractMgnt> contractMgntList = contractMgntMapper.queryAllPageList(contractMgnt);
		if (((Page) contractMgntList).getTotal() > 0) {
			// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
			if (StringUtils.isNotBlank(id)) {
				// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
				if (!PageUtil.hasRecord(contractMgntList, id)) {
					// 在查询条件中增加ID
					ContractMgnt newRecord = new ContractMgnt();
					newRecord.setId(id);
					List<ContractMgnt> newRecordList = contractMgntMapper.queryAllPageList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						contractMgntList.add(0, newRecordList.get(0));
					}
				}
			}
			resultMap = PageUtil.pack4PageHelper(contractMgntList, contractMgnt.getPagination());
			return resultMap;
		} else {
			List<ContractMgnt> newRecordList = new ArrayList<ContractMgnt>();
			if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				// 在查询条件中增加ID
				ContractMgnt newRecord = new ContractMgnt();
				newRecord.setId(id);
				newRecordList = contractMgntMapper.queryAllPageList(newRecord);
				if (newRecordList != null && newRecordList.size() == 1) {
					resultMap = PageUtil.pack(1, newRecordList, contractMgnt.getPagination());
					return resultMap;
				}

			}
			resultMap = PageUtil.pack(0, newRecordList, contractMgnt.getPagination());
			return resultMap;
		}
	}
	
	/**
	 * @Description 模态框中获取合同 
	 * @CreateTime 2018-4-3 上午11:48:28
	 * @CreateBy 刘兵
	 * @param contractMgnt 合同
	 * @return List<ContractMgnt> 合同集合
	 */
	@Override
	public List<ContractMgnt> queryModelList(ContractMgnt contractMgnt){
		return contractMgntMapper.queryModelList(contractMgnt);
	}
	
	/**
	 * @Description 根据合同id查询合同及用户信息
	 * @CreateTime 2018-3-12 下午3:43:48
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @return ContractMgnt 合同
	 */
	@Override
	public ContractMgnt selectById(String id){
		return contractMgntMapper.selectById(id);
	}
	
	/**
	 * @Description 导出
	 * @CreateTime 2018-3-27 上午9:51:28
	 * @CreateBy 刘兵
	 * @param id 合同id
	 * @return Map<String, Object> 导出数据
	 */
	@Override
	public Map<String, Object> exportWord(String id){
		double zj = 0;
		int number = 1;
		DecimalFormat dFormat= new DecimalFormat("#.00");  
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ContractMgnt contractMgnt = contractMgntMapper.exportWord(id);
		returnMap.put("dprtname", contractMgnt.getParamsMap().get("dprtname"));
		returnMap.put("hth", contractMgnt.getHth());
		returnMap.put("htrq", DateUtil.getDateStr(DateUtil.DEFAULT_FORMAT_DATE, contractMgnt.getHtrq()));
		returnMap.put("bqydm", contractMgnt.getParamsMap().get("bqydm"));
		returnMap.put("realname", contractMgnt.getParamsMap().get("realname"));
		returnMap.put("email", "");
		returnMap.put("phone", contractMgnt.getParamsMap().get("cellphone") != null?contractMgnt.getParamsMap().get("cellphone"):contractMgnt.getParamsMap().get("phone"));
		returnMap.put("biz", contractMgnt.getBiz());
		returnMap.put("bzrstr", contractMgnt.getParamsMap().get("bzrstr"));
		returnMap.put("xgfms", contractMgnt.getXgfms());
		returnMap.put("image", File_Util.getImageBase(File_Util.getFilePath("static/images/report/zx.jpg")));
		//配置合同明细
		List<ContractInfo> contractInfoList = contractMgnt.getContractInfoList();
		for (ContractInfo contractInfo : contractInfoList) {
			contractInfo.getParamsMap().put("number", number++);
			if(StringUtils.isNotBlank(contractInfo.getJhq())){
				StringBuffer jhq = new StringBuffer();
				jhq.append(contractInfo.getJhq());
				if(DeliveryEnum.CALENDAR.getId() != contractInfo.getJhqdw()){
					jhq.append(DeliveryEnum.getName(contractInfo.getJhqdw()));
				}
				contractInfo.getParamsMap().put("jhq", jhq);
			}
			if(contractMgnt.getHtlx() != ContractMgntTypeEnum.REPAIR.getId()){
				double xj = 0;
				xj = contractInfo.getSl().multiply(contractInfo.getDj()).doubleValue(); 
				contractInfo.getParamsMap().put("xj", dFormat.format(xj));
				zj += xj;
			}
		}
		returnMap.put("contractInfoList", contractInfoList);
		returnMap.put("zj", dFormat.format(zj));
		return returnMap;
	}

	/**
	 * @Description 生成编号
	 * @CreateTime 2018-3-12 下午4:24:47
	 * @CreateBy 刘兵
	 * @param htlx
	 * @param dprtcode
	 * @return
	 * @throws BusinessException
	 */
	private String buildhth(int htlx, String dprtcode) throws BusinessException {
		String hth = null;
		boolean b = true;
		while(b){ 
			
			try {
				hth = SNGeneratorFactory.generate(dprtcode, ContractMgntSaibongEnum.getName(htlx));
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			int iCount = contractMgntMapper.getCount4CheckExist(hth, dprtcode);
			if(iCount <= 0){
				b = false;
			}
		}
		return hth;
	}
	
	/**
	 * @Description 验证表单重复提交,当前状态是否存在于数组中,不存在:抛出异常message
	 * @CreateTime 2017-8-18 下午5:06:49
	 * @CreateBy 刘兵
	 * @param ztArr 可操作状态数组
	 * @param currentZt 当前状态
	 * @param message 异常信息
	 * @throws BusinessException
	 */
	private void validation4CurrentZt(Integer[] ztArr, Integer currentZt,String message) throws BusinessException{
		if(currentZt != null && !ArrayUtils.contains(ztArr,currentZt)){
			throw new BusinessException(message);
		}
	}

	
	/**
	 * @Description 检查合同是否存在
	 * @CreateTime 2018-3-12 下午3:51:11
	 * @CreateBy 刘兵
	 * @param hth 合同
	 * @param dprtcode 机构代码
	 * @throws BusinessException
	 */
	private void checkExist(String hth, String dprtcode) throws BusinessException{
		int iCount = contractMgntMapper.getCount4CheckExist(hth, dprtcode);
		if(iCount > 0){
			throw new BusinessException(new StringBuffer().append("合同号[").append(hth).append("]已存在!").toString());
		}
	}

}
