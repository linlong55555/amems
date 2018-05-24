package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.OutinReceiptShelvesMapper;
import com.eray.thjw.material2.dao.RejectedMaterialMapper;
import com.eray.thjw.material2.po.BackRegister;
import com.eray.thjw.material2.po.DemandSafeguardDetail;
import com.eray.thjw.material2.po.OutinReceiptShelves;
import com.eray.thjw.material2.po.ReceivingRelationship;
import com.eray.thjw.material2.po.RejectedMaterial;
import com.eray.thjw.material2.service.RejectedMaterialService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/** 
 * @Description 
 * @CreateTime 2018-3-2 下午2:25:17
 * @CreateBy 孙霁	
 */
@Service
public class RejectedMaterialServiceImpl implements RejectedMaterialService{

	@Resource
	private RejectedMaterialMapper rejectedMaterialMapper;
	
	@Resource
	private OutinReceiptShelvesMapper outinReceiptShelvesMapper;
	
	/**
	 * 
	 * @Description 获取退料申请数据
	 * @CreateTime 2018-3-2 下午2:23:30
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryAll(RejectedMaterial rejectedMaterial)
			throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id=rejectedMaterial.getId();
		rejectedMaterial.setId("");
		try {
			//获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(rejectedMaterial.getPagination());
			List<RejectedMaterial> list=rejectedMaterialMapper.queryAll(rejectedMaterial);
			if(((Page)list).getTotal() > 0){
				getReceiptShelves(list);
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						//在查询条件中增加ID
						RejectedMaterial newRecord = new RejectedMaterial();
						newRecord.setId(id);
						List<RejectedMaterial> newRecordList = rejectedMaterialMapper.queryAll(newRecord);
						getReceiptShelves(list);
						if(newRecordList != null && newRecordList.size() == 1){
							list.add(0, newRecordList.get(0));
						}
						//将记录放入结果集第一条
						
					}
				}
				resultMap=PageUtil.pack4PageHelper(list, rejectedMaterial.getPagination());
				return resultMap;
				
			}else{
				List<RejectedMaterial> newRecordList = new ArrayList<RejectedMaterial>();
				if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					//在查询条件中增加ID
					RejectedMaterial newRecord = new RejectedMaterial();
					newRecord.setId(id);
					newRecordList = rejectedMaterialMapper.queryAll(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						resultMap= PageUtil.pack(1, newRecordList, rejectedMaterial.getPagination());
						return resultMap;
					}
					
				}
				resultMap= PageUtil.pack(0, newRecordList, rejectedMaterial.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败！",e);
		}
		
		
	}

	
	/**
	 * 
	 * @Description 获取上架库位
	 * @CreateTime 2017年9月1日 上午9:26:50
	 * @CreateBy 林龙
	 * @param bulletinList
	 */
	private void getReceiptShelves(List<RejectedMaterial> backRegisterlist) {
		List<String> shidList = new ArrayList<String>();// 评估单id集合
		for (RejectedMaterial rejectedMaterial : backRegisterlist) {
			for (ReceivingRelationship receivingRelationship : rejectedMaterial.getReceivingRelationshiplist()) {
				if(null != receivingRelationship.getShmxid() ){
					shidList.add(receivingRelationship.getShmxid());
				}
				
			}
		}
		if (shidList.size() > 0) {
			List<OutinReceiptShelves> isList = outinReceiptShelvesMapper.selectReceiptShelvesList(shidList);
			if (isList.size() > 0) {
				for (RejectedMaterial backRegister : backRegisterlist) {
					List<ReceivingRelationship> list = new ArrayList<ReceivingRelationship>();
					for (ReceivingRelationship receivingRelationship : backRegister.getReceivingRelationshiplist()) {
						
						StringBuffer str=new StringBuffer();
						for (OutinReceiptShelves receiptShelves : isList) {
							if (null != receivingRelationship.getShmxid() && receivingRelationship.getShmxid().equals(receiptShelves.getMainid())) {
								str.append(receiptShelves.getCkh());
								str.append(" ");
								str.append(receiptShelves.getCkmc());
								if(receiptShelves.getKwh()!=null){
									str.append(" ");
									str.append(receiptShelves.getKwh());
									str.append(",");
								}
							}
						}
						list.add(receivingRelationship);
						
						if(str.length()>0){
							receivingRelationship.getParamsMap().put("sjkw", str.substring(0,str.length() - 1));
						}
					}
					if (list.size() > 0) {
						backRegister.setReceivingRelationshiplist(list);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018-3-6 上午11:31:39
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String insert(RejectedMaterial rejectedMaterial)
			throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		
		rejectedMaterial.setId(UUID.randomUUID().toString());
		rejectedMaterial.setWhrid(user.getId());
		rejectedMaterial.setWhrbmid(user.getBmdm());
		rejectedMaterial.setWhsj(new Date());
		rejectedMaterialMapper.insertSelective(rejectedMaterial);
		return rejectedMaterial.getId();
	}

	/**
	 * 
	 * @Description 修改（修订）
	 * @CreateTime 2018-3-6 上午11:48:33
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String update(RejectedMaterial rejectedMaterial)
			throws BusinessException {
		//获取登入user
		User user = ThreadVarUtil.getUser();
		boolean b = isExistByid(rejectedMaterial.getId());
		if(b){
			throw new BusinessException("该数据已更新，请刷新后再进行操作！");
		}
		rejectedMaterial.setWhrid(user.getId());
		rejectedMaterial.setWhrbmid(user.getBmdm());
		rejectedMaterial.setWhsj(new Date());
		rejectedMaterialMapper.updateByPrimaryKeySelective(rejectedMaterial);
		return rejectedMaterial.getId();
	}
	/**
	 * 
	 * @Description 验证是否存在关联表b_h_027
	 * @CreateTime 2018-3-6 下午2:27:16
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 */
	public boolean isExistByid(String id){
		boolean b;
		RejectedMaterial rejectedMaterial = rejectedMaterialMapper.selectRejectedMaterial(id);
		if(rejectedMaterial != null){
			b = true;
		}else{
			b = false;
		}
		return b;
	}

	@Override
	public RejectedMaterial selectById(String id) throws BusinessException {
		return rejectedMaterialMapper.selectByPrimaryKey(id);
	}

}
