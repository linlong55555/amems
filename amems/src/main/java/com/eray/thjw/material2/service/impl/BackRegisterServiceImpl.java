	package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.BackRegisterMapper;
import com.eray.thjw.material2.dao.OutinReceiptShelvesMapper;
import com.eray.thjw.material2.po.BackRegister;
import com.eray.thjw.material2.po.OutinReceiptShelves;
import com.eray.thjw.material2.po.ReceivingRelationship;
import com.eray.thjw.material2.service.BackRegisterService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 * 
 * @Description 退料登记impl
 * @CreateTime 2018年3月5日 上午9:50:43
 * @CreateBy 林龙
 */
@Service("backRegisterService")
public class BackRegisterServiceImpl implements BackRegisterService  {

	@Resource
	private BackRegisterMapper backRegisterMapper;
	@Resource
	private OutinReceiptShelvesMapper outinReceiptShelvesMapper;
	
	
	/**
	 * @Description 退料登记分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> queryAllPageList(BackRegister backRegister)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = backRegister.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		backRegister.setId(null);
		PageHelper.startPage(backRegister.getPagination());
		List<BackRegister> list = backRegisterMapper.queryAllPageList(backRegister);
		if(((Page)list).getTotal() > 0){
			getReceiptShelves(list);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					BackRegister param = new BackRegister();
					param.setId(id);
					List<BackRegister> newRecordList = backRegisterMapper.queryAllPageList(param);
					getReceiptShelves(list);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, backRegister.getPagination());
		}else{
			List<BackRegister> newRecordList = new ArrayList<BackRegister>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				BackRegister param = new BackRegister();
				param.setId(id);
				newRecordList = backRegisterMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, backRegister.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, backRegister.getPagination());
		}
	}

	/**
	 * 
	 * @Description 获取上架库位
	 * @CreateTime 2017年9月1日 上午9:26:50
	 * @CreateBy 林龙
	 * @param bulletinList
	 */
	private void getReceiptShelves(List<BackRegister> backRegisterlist) {
		List<String> shidList = new ArrayList<String>();// 评估单id集合
		for (BackRegister backRegister : backRegisterlist) {
			for (ReceivingRelationship receivingRelationship : backRegister.getReceivingRelationshiplist()) {
				if(null != receivingRelationship.getShmxid() ){
					shidList.add(receivingRelationship.getShmxid());
				}
				
			}
		}
		if (shidList.size() > 0) {
			List<OutinReceiptShelves> isList = outinReceiptShelvesMapper.selectReceiptShelvesList(shidList);
			if (isList.size() > 0) {
				for (BackRegister backRegister : backRegisterlist) {
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
	 * @Description 查询退料登记详情
	 * @CreateTime 2018年3月5日 上午11:51:06
	 * @CreateBy 林龙
	 * @param backRegister
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public BackRegister getInfoById(BackRegister backRegister)throws BusinessException {
		BackRegister backRegisterNew=backRegisterMapper.getInfoById(backRegister);
		
		List<BackRegister> list=new ArrayList<BackRegister>();
		list.add(backRegisterNew);
		getReceiptShelves(list);
		
		return backRegisterNew;
	}
	
	
	
	
	
     
}