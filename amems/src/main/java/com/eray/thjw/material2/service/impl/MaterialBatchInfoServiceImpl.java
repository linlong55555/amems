	package com.eray.thjw.material2.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.material2.dao.MaterialBatchInfoMapper;
import com.eray.thjw.material2.po.MaterialBatchInfo;
import com.eray.thjw.material2.service.MaterialBatchInfoService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.ThreadVarUtil;

/**
 * @Description 物料批次信息service实现类
 * @CreateTime 2018年3月23日 下午3:34:26
 * @CreateBy 韩武
 */
@Service("materialBatchInfoService")
public class MaterialBatchInfoServiceImpl implements MaterialBatchInfoService  {

	@Resource
	private MaterialBatchInfoMapper materialBatchInfoMapper;
	
	
	/**
	 * 插入或更新物料批次信息
	 */
	@Override
	public int insertOrUpdate(String dprtcode, String bjh, String xlh,
			String pch, BigDecimal cb, String cbbz, BigDecimal jz, String jzbz) {
		
		User user = ThreadVarUtil.getUser(); 
		
		MaterialBatchInfo record = new MaterialBatchInfo();
		record.setBjh(bjh);
		record.setXlh(xlh);
		record.setPch(pch);
		record.setDprtcode(dprtcode);
		
		// 成本/价值不为空时，才写入对应单位
		if(cb != null){
			record.setCb(cb);
			record.setCbbz(cbbz);
		}
		if(jz != null){
			record.setJz(jz);
			record.setJzbz(jzbz);
		}
		
		record.setWhrid(user.getId());
		record.setWhrbmid(user.getBmdm());
		// 更新结果数据行数为0则进行新增
		int updateCount = materialBatchInfoMapper.updateByBusinessKey(record);
		if(updateCount == 0){
			record.setId(UUID.randomUUID().toString());
			return materialBatchInfoMapper.insert(record);
		}
		return updateCount;
	}
	
	
}