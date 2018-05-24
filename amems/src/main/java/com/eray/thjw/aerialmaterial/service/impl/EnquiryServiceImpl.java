package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.common.jstl.StringUtils;
import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.EnquiryMapper;
import com.eray.thjw.aerialmaterial.dao.MaterialRepairMapper;
import com.eray.thjw.aerialmaterial.dao.ProcurementCatalogMapper;
import com.eray.thjw.aerialmaterial.dao.ReserveDetailMapper;
import com.eray.thjw.aerialmaterial.po.Enquiry;
import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.po.ProcurementCatalog;
import com.eray.thjw.aerialmaterial.po.ReserveDetail;
import com.eray.thjw.aerialmaterial.service.EnquiryService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

import enu.EnquiryStatusEnum;
import enu.FormTypeEnum;
import enu.SaiBongEnum;
import enu.common.EffectiveEnum;

/**
 * @author liub
 * @description 询价管理service,用于业务逻辑处理
 * @develop date 2016.10.18
 */
@Service
public class EnquiryServiceImpl implements EnquiryService {
	
	/**
	 * @author liub
	 * @description 询价管理Mapper
	 * @develop date 2016.10.18
	 */
    @Resource
    private EnquiryMapper enquiryMapper;
	
	/**
	 * @author liub
	 * @description 采购合同Mapper
	 * @develop date 2016.10.20
	 */
    @Resource
    private ProcurementCatalogMapper procurementCatalogMapper;
    
    /**
	 * @author liub
	 * @description 采番service
	 * @develop date 2016.08.16
	 */
	@Autowired
	private SaibongUtilService saibongUtilService;
	
	/**
	 * @author liub
	 * @description 提订详情Mapper
	 * @develop date 2016.10.13
	 */
    @Resource
    private ReserveDetailMapper reserveDetailMapper;
    
    /**
	 * @author liub
	 * @description 送修详情Mapper
	 * @develop date 2016.11.04
	 */
    @Resource
    private MaterialRepairMapper materialRepairMapper;
    
    /**
  	 * @author liub
  	 * @description 附件service
  	 * @develop date 2016.11.06
  	 */
    @Resource
    private AttachmentService attachmentService;
    
	
    /**
	 * @author liub
	 * @description 保存询价
	 * @param enquiryParam
	 * @develop date 2016.10.20
	 * @throws BusinessException 
	 */
	@Override
	public void save(Enquiry enquiryParam) throws BusinessException{
		String mainid = enquiryParam.getMainid();
		Integer djlx = enquiryParam.getDjlx();
		String bjid = (String) enquiryParam.getParamsMap().get("bjid");
		String bjh = (String) enquiryParam.getParamsMap().get("bjh");
		User user = ThreadVarUtil.getUser();
		List<String> newEnquirieIdList = new ArrayList<String>();//存放页面询价列表id集合
		List<String> oldEnquirieIdList = new ArrayList<String>();//存放数据库询价id集合
		Map<String, String> geMap = new HashMap<String, String>();//存放供应商id(key)和询价id(value)
		List<Enquiry> oldEnquiryList = enquiryMapper.queryEnquiryListByMainId(mainid);
		for (Enquiry enquiry : oldEnquiryList) {
			oldEnquirieIdList.add(enquiry.getId());
			if(null != enquiry.getGysid() && !"".equals(enquiry.getGysid())){
				geMap.put(enquiry.getGysid(), enquiry.getId());
			}
		}
		//编辑提订单详情数据
		if(null != djlx && djlx.intValue() == FormTypeEnum.RESERVE_FORM.getId().intValue()){
			ReserveDetail obj = reserveDetailMapper.selectByPrimaryKey(mainid);
			if(null != obj && null == obj.getXjdh()){
				String xjdh;
				try {
					xjdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCXJ.getName());
				} catch (SaibongException e) {
					throw new BusinessException(e);
				}
				//编辑提订详情信息
				ReserveDetail reserveDetail = new ReserveDetail();
				reserveDetail.setId(mainid);
				reserveDetail.setXjzt(EnquiryStatusEnum.ALREADY_TRADING.getId());
				reserveDetail.setXjdh(xjdh);
				reserveDetailMapper.updateByPrimaryKeySelective(reserveDetail);
			}
		}
		//编辑送修单详情数据
		if(null != djlx && djlx.intValue() == FormTypeEnum.REPAIR_FORM.getId().intValue()){
			MaterialRepair obj = materialRepairMapper.selectByPrimaryKey(mainid);
			if(null != obj && null == obj.getXjdh()){
				String xjdh;
				try {
					xjdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.HCXJ.getName());
				} catch (SaibongException e) {
					throw new BusinessException(e);
				}
				//编辑送修航材信息
				MaterialRepair materialRepair = new MaterialRepair();
				materialRepair.setId(mainid);
				materialRepair.setXjzt(EnquiryStatusEnum.ALREADY_TRADING.getId());
				materialRepair.setXjdh(xjdh);
				materialRepairMapper.updateByPrimaryKeySelective(materialRepair);
			}
		}
		//新增或编辑询价信息
		for(Enquiry enquiry : enquiryParam.getEnquiryList()){
			//页面和数据库同时存在询价id则编辑
			if(null != enquiry.getId() && !"".equals(enquiry.getId()) && oldEnquirieIdList.contains(enquiry.getId())){
				newEnquirieIdList.add(enquiry.getId());
				enquiryMapper.updateByPrimaryKeySelective(enquiry);
			}else{
				//数据库存在相应的供应商id则编辑
				if(null != enquiry.getGysid() && !"".equals(enquiry.getGysid()) && null != geMap.get(enquiry.getGysid())){
					newEnquirieIdList.add(geMap.get(enquiry.getGysid()));
					enquiry.setId(geMap.get(enquiry.getGysid()));
					enquiryMapper.updateByPrimaryKeySelective(enquiry);
				}else{
					enquiry.setId(UUID.randomUUID().toString());
					enquiry.setZt(EffectiveEnum.YES.getId());
					enquiry.setWhrid(user.getId());
					enquiry.setWhdwid(user.getBmdm());
					enquiryMapper.insertSelective(enquiry);
				}
			}
			if(null != enquiry.getGysid() && !"".equals(enquiry.getGysid())){
				//新增或编辑采购目录信息
				UUID uuid = UUID.randomUUID();//获取随机的uuid
				ProcurementCatalog procurementCatalog = new ProcurementCatalog();
				procurementCatalog.setId(uuid.toString());
				procurementCatalog.setBjid(bjid);
				procurementCatalog.setGysid(enquiry.getGysid());
				procurementCatalog.setYxqKs(enquiry.getYjdhrq());
				procurementCatalog.setGysbm(enquiry.getGysbm());
				procurementCatalog.setBjh(bjh);
				procurementCatalog.setBjClf(enquiry.getBjClf());
				procurementCatalog.setBjGsf(enquiry.getBjGsf());
				procurementCatalog.setBjQtf(enquiry.getBjQtf());
				procurementCatalog.setGyslb(enquiry.getDjlx());
				procurementCatalog.setBjlb(enquiry.getDjlx());
				procurementCatalog.setZt(EffectiveEnum.YES.getId());
				procurementCatalog.setDprtcode(enquiryParam.getDprtcode());
				procurementCatalog.setCjrid(user.getId());
				procurementCatalog.setBmid(user.getBmdm());
				procurementCatalogMapper.updateStatus(procurementCatalog);
				procurementCatalogMapper.updateYxqJs(procurementCatalog);
				procurementCatalogMapper.insertSelective(procurementCatalog);
			}
		}
		//删除询价信息
		for (String oldEnquirieId : oldEnquirieIdList) {
			if(!newEnquirieIdList.contains(oldEnquirieId)){
				enquiryMapper.deleteByPrimaryKey(oldEnquirieId);
			}
		}
		attachmentService.eidtAttachment(enquiryParam.getAttachments(), mainid,enquiryParam.getDprtcode());//编辑附件
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件分页查询询价列表信息
	 * @param enquiry
	 * @return Map<String, Object>
	 * @develop date 2016.10.18
	 */
	@Override
	public List<Map<String, Object>> queryAllPageList(Enquiry enquiry){
		return enquiryMapper.queryAllPageList(enquiry);
	}

	/**
	 * @author liub
	 * @description  根据提订详情id查询询价信息
	 * @param mainid
	 * @return List<Enquiry>
	 * @develop date 2016.10.20
	 */
	@Override
	public List<Enquiry> queryEnquiryListByMainId(String mainid){
		return enquiryMapper.queryEnquiryListByMainId(mainid);
	}
	
	/**
	 * @author liub
	 * @description  根据查询条件采购目录信息
	 * @param procurementCatalog
	 * @return List<ProcurementCatalog>
	 * @develop date 2016.11.07
	 */
	@Override
	public List<ProcurementCatalog> queryProcurementCatalogList(ProcurementCatalog procurementCatalog){
		return procurementCatalogMapper.queryProcurementCatalogList(procurementCatalog);
	}

}
