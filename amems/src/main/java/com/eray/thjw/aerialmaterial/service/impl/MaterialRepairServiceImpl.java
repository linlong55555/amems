package com.eray.thjw.aerialmaterial.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialRepairMapper;
import com.eray.thjw.aerialmaterial.po.MaterialRepair;
import com.eray.thjw.aerialmaterial.service.MaterialRepairService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MaterialRepairServiceImpl implements MaterialRepairService {
	
	@Resource
	private MaterialRepairMapper materialRepairMapper;
	
	@Override
	public List<MaterialRepair> queryAllPageList(MaterialRepair materialRepair)
			throws RuntimeException {
		return materialRepairMapper.queryAllPageList(materialRepair);
	}

	@Override
	public MaterialRepair queryAllList(MaterialRepair materialRepair)
			throws RuntimeException {
		// TODO Auto-generated method stub
		return materialRepairMapper.queryAllList(materialRepair);
	}

	/**
	 * @author liub
	 * @description  根据查询条件分页查询航材送修单信息
	 * @param materialRepair
	 * @return List<MaterialRepair>
	 * @develop date 2016.11.01
	 */
	@Override
	public Map<String, Object> queryRepairPageList(MaterialRepair materialRepair){
		User user = ThreadVarUtil.getUser();
		materialRepair.setSqrid(user.getId());
		
		String id = materialRepair.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		materialRepair.setId(null);
		PageHelper.startPage(materialRepair.getPagination());
		List<MaterialRepair> list = materialRepairMapper.queryRepairPageList(materialRepair);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					MaterialRepair newRecord = new MaterialRepair();
					newRecord.setId(id);
					List<MaterialRepair> newRecordList = materialRepairMapper.queryRepairPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, materialRepair.getPagination());
		}else{
			List<MaterialRepair> newRecordList = new ArrayList<MaterialRepair>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				MaterialRepair newRecord = new MaterialRepair();
				newRecord.setId(id);
				newRecordList = materialRepairMapper.queryRepairPageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, materialRepair.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, materialRepair.getPagination());
		}
	}

	/**
	 * @author liub
	 * @description  根据查询条件分页查询审核航材送修单信息
	 * @param materialRepair
	 * @return List<MaterialRepair>
	 * @develop date 2016.11.01
	 */
	@Override
	public Map<String, Object> queryApproveRepairPageList(MaterialRepair materialRepair){
		User user = ThreadVarUtil.getUser();
		materialRepair.setSqrid(user.getId());
		
		String id = materialRepair.getId();//用户刚编辑过的记录 ID
		//清除查询条件中的ID，保证列表查询结果集的正确性
		materialRepair.setId(null);
		
		PageHelper.startPage(materialRepair.getPagination());
		List<MaterialRepair> list = materialRepairMapper.queryApproveRepairPageList(materialRepair);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					MaterialRepair newRecord = new MaterialRepair();
					newRecord.setId(id);
					List<MaterialRepair> newRecordList = materialRepairMapper.queryApproveRepairPageList(newRecord);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, materialRepair.getPagination());
		}else{
			List<MaterialRepair> newRecordList = new ArrayList<MaterialRepair>();
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				MaterialRepair newRecord = new MaterialRepair();
				newRecord.setId(id);
				newRecordList = materialRepairMapper.queryApproveRepairPageList(newRecord);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, materialRepair.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, materialRepair.getPagination());
		}
		
	}

	/**
	 * @author liub
	 * @description  根据查询条件分页查询送修航材信息(弹窗)
	 * @param materialRepair
	 * @return Map<String, Object>
	 * @develop date 2016.11.07
	 */
	@Override
	public List<Map<String, Object>> queryRepairMaterialPageList(MaterialRepair materialRepair){
		return materialRepairMapper.queryRepairMaterialPageList(materialRepair);
	}
	
}
