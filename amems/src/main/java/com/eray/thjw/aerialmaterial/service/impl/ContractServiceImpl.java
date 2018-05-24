package com.eray.thjw.aerialmaterial.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.ContractDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ContractMapper;
import com.eray.thjw.aerialmaterial.dao.ContractPayMapper;
import com.eray.thjw.aerialmaterial.dao.InstockDetailMapper;
import com.eray.thjw.aerialmaterial.dao.InstockMapper;
import com.eray.thjw.aerialmaterial.dao.OutstockDetailMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveDetailMapper;
import com.eray.thjw.aerialmaterial.po.Contract;
import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.aerialmaterial.po.ContractPay;
import com.eray.thjw.aerialmaterial.po.Firm;
import com.eray.thjw.aerialmaterial.po.Instock;
import com.eray.thjw.aerialmaterial.po.InstockDetail;
import com.eray.thjw.aerialmaterial.po.Message;
import com.eray.thjw.aerialmaterial.po.ReserveDetail;
import com.eray.thjw.aerialmaterial.service.ContractService;
import com.eray.thjw.aerialmaterial.service.FirmService;
import com.eray.thjw.aerialmaterial.service.MessageService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.SaiBongEnum;
import enu.aerialmaterial.ContractDeliveryStatusEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.ContractTypeEnum;
import enu.aerialmaterial.InstockTypeEnum;
import enu.aerialmaterial.RequisitionStatusEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 合同service,用于业务逻辑处理
 * @develop date 2016.11.04
 */
@Service
public class ContractServiceImpl implements ContractService {
	
	/**
	 * @author liub
	 * @description 合同mapper
	 * @develop date 2016.11.08
	 */
	@Autowired
	private ContractMapper contractMapper;
	/**
	 * @author liub
	 * @description 合同表-提订单mapper
	 * @develop date 2016.11.08
	 */
	@Resource
	private ContractDetailMapper contractDetailMapper;
	
	/**
	 * @author liub
	 * @description 合同付款mapper
	 * @develop date 2016.11.12
	 */
	@Resource
	private ContractPayMapper contractPayMapper;
	
	/**
	 * @author liub
	 * @description 留言mapper
	 * @develop date 2016.11.16
	 */
	@Resource
	private MessageService messageService;
	
	@Resource
	private InstockMapper instockMapper;
	
	@Resource
	private InstockDetailMapper instockDetailMapper;
	
	@Resource
	private OutstockDetailMapper outstockDetailMapper;
	
	/**
	 * @author liub
	 * @description 提订详情Mapper
	 * @develop date 2016.10.13
	 */
    @Resource
    private ReserveDetailMapper reserveDetailMapper;

	
    /**
	 * @author liub
	 * @description 采番service
	 * @develop date 2016.08.16
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;
	
	/**
	 * @author liub
	 * @description 供应商service
	 */
	@Autowired
	private FirmService firmService;
	
	 /**
  	 * @author liub
  	 * @description 附件service
  	 * @develop date 2016.11.06
  	 */
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * 查询航材历史采购价格
	 * @param entity bjid、PAGINATION
	 * @author xu.yong
	 * @return
	 */
	public Map<String, Object> queryCostHisByBjId(BaseEntity entity){
		User user = ThreadVarUtil.getUser();
		entity.getParamsMap().put("dprtcode", user.getJgdm());
		
		PageHelper.startPage(entity.getPagination());
		List<ContractDetail> list = this.contractDetailMapper.selectCostHisByBjidPage(entity);
		return PageUtil.pack4PageHelper(list, entity.getPagination());
		
	}
	/**
	 * @author liub
	 * @description 新增合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	@Override
	public String add(Contract contract) throws BusinessException{
		String htlsh = null;
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		try{
			if(ContractTypeEnum.RESERVE_CONTRACT.getId().intValue() == contract.getHtlx().intValue()){
				htlsh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HTCG.getName());
			}else{
				htlsh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.SXHT.getName());
			}
		}catch(SaibongException e){
			throw new BusinessException(e);
		}
		
		try {
			Firm firm = firmService.selectByPrimaryKey(contract.getGysid());
			contract.setGysbm(firm.getGysbm());
			contract.setGysmc(firm.getGysmc());
		} catch (Exception e) {
			 throw new BusinessException("供应商不存在!",e);
		}
		
		contract.setId(id);
		contract.setHtlsh(htlsh);
		contract.setDhzt(ContractDeliveryStatusEnum.NO_DELIVERY.getId());
		contract.setDprtcode(user.getJgdm());
		contract.setZdrid(user.getId());
		contract.setZdbmid(user.getBmdm());
		contractMapper.insertSelective(contract);
		//新增合同详情
		for (ContractDetail contractDetail : contract.getContractDetailList()) {
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			contractDetail.setId(detailId);
			contractDetail.setMainid(id);
			contractDetail.setDhsl(BigDecimal.ZERO);
			contractDetail.setRksl(BigDecimal.ZERO);
			contractDetail.setZt(EffectiveEnum.YES.getId());
			contractDetail.setWhrid(user.getId());
			contractDetail.setWhdwid(user.getBmdm());
			contractDetailMapper.insertSelective(contractDetail);
		}
		
		//提交采购合同操作时,更新提订单-航材表已采购数量
		if(ContractTypeEnum.RESERVE_CONTRACT.getId().intValue() == contract.getHtlx().intValue() && contract.getZt() == ContractStatusEnum.SUBMIT.getId()){
			updateReserveDetailList(contract.getContractDetailList());
		}
		//新增留言
		Message message = contract.getMessage();
		if(null != message.getNbwjm() && !"".equals(message.getNbwjm())){
			message.setDjid(id);
			messageService.add(message);
		}
		attachmentService.eidtAttachment(contract.getAttachments(), contract.getId(),contract.getDprtcode());//编辑附件
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改合同
	 * @param contract
	 * @develop date 2016.11.08
	 * @throws BusinessException 
	 */
	@Override
	public void edit(Contract contract) throws BusinessException{
		
		Contract obj = contractMapper.selectByPrimaryKey(contract.getId());
		verification(new Integer[]{ContractStatusEnum.SAVE.getId()},obj.getZt(),"该合同已更新，请刷新后再进行操作!");
		
		User user = ThreadVarUtil.getUser();
		List<String> newContractDetailIdList = new ArrayList<String>();
		try {
			Firm firm = firmService.selectByPrimaryKey(contract.getGysid());
			contract.setGysbm(firm.getGysbm());
			contract.setGysmc(firm.getGysmc());
		} catch (Exception e) {
			 throw new BusinessException("供应商不存在!",e);
		}
		contractMapper.updateByPrimaryKeySelective(contract);
		//新增或修改合同详情
		for (ContractDetail contractDetail : contract.getContractDetailList()) {
			String contractDetailId = contractDetail.getId();
			if(null != contractDetailId && !contractDetailId.equals("")){
				newContractDetailIdList.add(contractDetail.getId());
				contractDetailMapper.updateByPrimaryKeySelective(contractDetail);
			}else{
				UUID uuid2 = UUID.randomUUID();//获取随机的uuid
				String detailId = uuid2.toString();
				contractDetail.setId(detailId);
				contractDetail.setMainid(contract.getId());
				contractDetail.setDhsl(new BigDecimal("0.0"));
				contractDetail.setRksl(new BigDecimal("0.0"));
				contractDetail.setZt(EffectiveEnum.YES.getId());
				contractDetail.setWhrid(user.getId());
				contractDetail.setWhdwid(user.getBmdm());
				contractDetailMapper.insertSelective(contractDetail);
			}
		}
		for (String contractDetailId : contract.getContractDetailIdList()) {
			if(!newContractDetailIdList.contains(contractDetailId)){
				contractDetailMapper.deleteByPrimaryKey(contractDetailId);
			}
		}
		//修改提交采购合同操作时,更新提订单-航材表已采购数量
		if(ContractTypeEnum.RESERVE_CONTRACT.getId().intValue() == contract.getHtlx().intValue() && contract.getZt() == ContractStatusEnum.SUBMIT.getId()){
			updateReserveDetailList(contract.getContractDetailList());
		}
		attachmentService.eidtAttachment(contract.getAttachments(), contract.getId(),contract.getDprtcode());//编辑附件
	}
	
	/**
	 * @author liub
	 * @description 根据条件修改合同
	 * @param contract
	 * @develop date 2016.11.09
	 * @throws BusinessException 
	 */
	@Override
	public void updateByPrimaryKeySelective(Contract contract){
		contractMapper.updateByPrimaryKeySelective(contract);
	}
	
	/**
	 * @author liub
	 * @description 收货
	 * @param contract
	 * @develop date 2016.11.10
	 * @throws BusinessException 
	 */
	@Override
	public void updateComeGood(Contract contract) throws BusinessException{
		
		String rkdh = null;
		String wpqdId = null;
		List<String> newContractDetailIdList = null;
		Map<String, ContractDetail> contractDetailMap = null;
		User user = ThreadVarUtil.getUser();
		//验证合同状态是否提交
		String id = contract.getId();
		Contract oldContract = contractMapper.selectByPrimaryKey(id);
		verification(new Integer[]{ContractStatusEnum.SUBMIT.getId()},oldContract.getZt(),"该合同已更新，请刷新后再进行操作!");
		int htlx = oldContract.getHtlx();
		//暂存合同详情信息
		newContractDetailIdList = new ArrayList<String>();
		contractDetailMap = new HashMap<String, ContractDetail>();
		for (ContractDetail contractDetail : contract.getContractDetailList()) {
			newContractDetailIdList.add(contractDetail.getId());
			contractDetailMap.put(contractDetail.getId(), contractDetail);
		}
		
		List<ContractDetail> newContractDetailList = contractDetailMapper.selectByIds(newContractDetailIdList);
		for (ContractDetail contractDetail : newContractDetailList) {
			BigDecimal htsl = contractDetail.getHtSl();
			BigDecimal dhsl = contractDetail.getDhsl();
			ContractDetail oldContractDetail = contractDetailMap.get(contractDetail.getId());
			BigDecimal bcdhs = oldContractDetail.getBcdhs();
			if(htsl.subtract(dhsl).compareTo(bcdhs) == -1){
				throw new BusinessException("其它部门已经收货!,请重新分配到货数!");
			}
			oldContractDetail.setDhsl(bcdhs.add(dhsl));
			contractDetailMapper.updateByPrimaryKeySelective(oldContractDetail);//修改到货数量
			
			//新增入库单
			Instock instock = new Instock();
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			String instockId = uuid.toString();
			try {
				rkdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.CGRK.getName());
				if(InstockTypeEnum.TYPE1.getId() == htlx){
				}else{
					wpqdId = outstockDetailMapper.getWpqdIdByRepairId(contractDetail.getTddid());
					if(null == wpqdId){
						throw new BusinessException("部件号"+oldContractDetail.getBjh()+"未送修出库!");
					}
				}
			} catch (SaibongException e) {
				throw new BusinessException(e);
			}
			instock.setId(instockId);
			instock.setDprtcode(user.getJgdm());
			instock.setRklx(htlx);
			instock.setRkdh(rkdh);
			instock.setZt(RequisitionStatusEnum.SAVED.getId());
			instock.setZdrid(user.getId());
			instock.setZdbmid(user.getBmdm());
			instock.setSqrid(user.getId());
			instock.setSqbmid(user.getBmdm());
			instock.setXgdjid(id);
			instockMapper.insertSelective(instock);
			
			//新增入库单航材
			InstockDetail instockDetail = new InstockDetail();
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String instockDetailId = uuid2.toString();
			instockDetail.setId(instockDetailId);
			instockDetail.setMainid(instockId);
			instockDetail.setBjid(oldContractDetail.getBjid());
			instockDetail.setBjh(oldContractDetail.getBjh());
			instockDetail.setSl(bcdhs);
			instockDetail.setHtid(id);
			instockDetail.setHtmxid(contractDetail.getId());
			instockDetail.setWpqdid(wpqdId);
			instockDetail.setZt(EffectiveEnum.YES.getId());
			instockDetail.setWhrid(user.getId());
			instockDetail.setWhdwid(user.getBmdm());
			instockDetailMapper.insertSelective(instockDetail);
			
		}
		
		int resultCount = contractMapper.updateFinish(id);//修改合同状态为完成
		if(resultCount > 0){
			contract.setDhzt(ContractDeliveryStatusEnum.ALL_DELIVERY.getId());
		}else{
			contract.setDhzt(ContractDeliveryStatusEnum.PART_DELIVERY.getId());
		}
		contractMapper.updateByPrimaryKeySelective(contract);
	}
	
	/**
	 * @author liub
	 * @description 新增合同付款
	 * @param contractPay
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	@Override
	public void addPay(ContractPay contractPay) throws BusinessException{
		Contract oldContract = contractMapper.selectByPrimaryKey(contractPay.getMainid());
		verification(new Integer[]{ContractStatusEnum.SUBMIT.getId(),ContractStatusEnum.FINISH.getId()},oldContract.getZt(),"该合同已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		contractPay.setId(id);
		contractPay.setZt(EffectiveEnum.YES.getId());
		contractPay.setWhrid(user.getId());
		contractPay.setWhdwid(user.getBmdm());
		contractPayMapper.insertSelective(contractPay);
	}
	
	/**
	 * @author liub
	 * @description 修改合同付款
	 * @param contractPay
	 * @develop date 2016.11.12
	 * @throws BusinessException 
	 */
	@Override
	public void editPay(ContractPay contractPay) throws BusinessException{
		Contract oldContract = contractMapper.selectByPrimaryKey(contractPay.getMainid());
		verification(new Integer[]{ContractStatusEnum.SUBMIT.getId(),ContractStatusEnum.FINISH.getId()},oldContract.getZt(),"该合同已更新，请刷新后再进行操作!");
		User user = ThreadVarUtil.getUser();
		contractPay.setWhrid(user.getId());
		contractPayMapper.updateByPrimaryKeySelective(contractPay);
	}
	
	/**
	 * @author liub
	 * @description  删除合同付款
	 * @param id
	 * @return
	 * @develop date 2016.11.12
	 */
	@Override
	public void deletePay(String id,String mainId) throws BusinessException{
		Contract oldContract = contractMapper.selectByPrimaryKey(mainId);
		verification(new Integer[]{ContractStatusEnum.SUBMIT.getId(),ContractStatusEnum.FINISH.getId()},oldContract.getZt(),"该合同已更新，请刷新后再进行操作!");
		contractPayMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 * @return
	 * @develop date 2016.11.09
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		Contract contract = contractMapper.selectByPrimaryKey(id);
		verification(new Integer[]{ContractStatusEnum.SAVE.getId()},contract.getZt(),"该合同已更新，请刷新后再进行操作!");
		contractMapper.cancel(id);
		contractDetailMapper.cancelByMainId(id);
	}
	
	/**
	 * @author liub
	 * @description 合同指定结束
	 * @param contract
	 * @develop date 2016.11.09
	 * @throws BusinessException 
	 */
	@Override
	public void updateShutDown(Contract contract) throws BusinessException{
		Contract obj = contractMapper.selectByPrimaryKey(contract.getId());
		verification(new Integer[]{ContractStatusEnum.SUBMIT.getId(),ContractStatusEnum.FINISH.getId(),ContractStatusEnum.REVOKE.getId()},obj.getZt(),"该合同已更新，请刷新后再进行操作!");
		contractMapper.updateByPrimaryKeySelective(contract);
	}
	
	/**
	 * @author liub
	 * @description 检查合同修改权限
	 * @param id
	 * @develop date 2016.11.08
	 */
	@Override
	public void checkEdit(String id) throws BusinessException{
		Contract contract = contractMapper.selectByPrimaryKey(id);
		//验证是否保存状态
		Integer zt = contract.getZt();
		if(null == zt || ContractStatusEnum.SAVE.getId().intValue() != zt.intValue()){
			throw new BusinessException("对不起,只有保存状态下的合同才能修改!");
		}
	}
	
	/**
	 * @author liub
	 * @description 根据主键id查询合同信息
	 * @param id
	 * @return Contract
	 * @develop date 2016.11.08
	 */
	@Override
	public Contract selectByPrimaryKey(String id){
		return contractMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * @author liub
	 * @description  根据id查询合同信息(制单人、指定结束人、合同费用)
	 * @param id
	 * @return Contract
	 * @develop date 2016.12.09
	 */
	@Override
	public Contract selectById(String id){
		return contractMapper.selectById(id);
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询合同信息
	 * @param contract
	 * @return List<Contract>
	 * @develop date 2016.11.08
	 */
	@Override
	public List<Contract> queryAllPageList(Contract contract){
		return contractMapper.queryAllPageList(contract);
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询合同付款信息
	 * @param contractPay
	 * @return List<ContractPay>
	 * @develop date 2016.11.12
	 */
	@Override
	public List<ContractPay> queryContractPayPageList(ContractPay contractPay){
		return contractPayMapper.queryAllPageList(contractPay);
	}
	
	/**
	 * @author liub
	 * @description  根据条件分页查询付款统计列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.08
	 */
	@Override
	public List<Map<String, Object>> queryPayStatisticsPageList(BaseEntity baseEntity){
		return contractPayMapper.queryPayStatisticsPageList(baseEntity);
	}
	
	/**
	 * @author liub
	 * @description  根据条件分页查询付款明细列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.08
	 */
	@Override
	public List<Map<String, Object>> queryPayDetailPageList(BaseEntity baseEntity){
		return contractPayMapper.queryPayDetailPageList(baseEntity);
	}
	
	private void updateReserveDetailList(List<ContractDetail> contractDetailList) throws BusinessException{
		List<String> idList = new ArrayList<String>();
		for (ContractDetail contractDetail : contractDetailList) {
			idList.add(contractDetail.getTddhcid());
		}
		if(idList.size() > 0){
			List<ReserveDetail> reserveDetailList = reserveDetailMapper.queryByIds(idList);
			for (ReserveDetail reserveDetail : reserveDetailList) {
				for (ContractDetail contractDetail : contractDetailList) {
					if(reserveDetail.getId().equals(contractDetail.getTddhcid())){
						BigDecimal tdsl = reserveDetail.getSl();
						BigDecimal ycgsl = reserveDetail.getYcgsl();
						BigDecimal htsl = contractDetail.getHtSl();
						if(tdsl.subtract(ycgsl).compareTo(htsl) == -1){
							throw new BusinessException("其它部门已经采购!,请重新分配合同数!");
						}
						reserveDetail.setYcgsl(ycgsl.add(htsl));
						reserveDetailMapper.updateYcgsl(reserveDetail);
					}
				}
			}
		}
	}
	
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}
	
	/**
	 * 模态框查询合同列表
	 */
	@Override
	public List<Contract> queryPageInModal(Contract contract) {
		return contractMapper.queryPageInModal(contract);
	}
}
