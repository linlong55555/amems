	package com.eray.thjw.quality.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.ProcessRecordMapper;
import com.eray.thjw.quality.po.ProcessRecord;
import com.eray.thjw.quality.service.ProcessRecordService;
import com.eray.thjw.util.ThreadVarUtil;

/**
 * @Description 流程记录service实现类
 * @CreateTime 2018年1月11日 下午2:19:23
 * @CreateBy 韩武
 */
@Service("processRecordService")
public class ProcessRecordServiceImpl implements ProcessRecordService  {

	@Resource
	private ProcessRecordMapper processRecordMapper;

	/**
	 * @Description 查询流程记录集合
	 * @CreateTime 2018年1月11日 下午2:23:49
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<ProcessRecord> queryList(ProcessRecord record) {
		return processRecordMapper.queryList(record);
	}

	/**
	 * 
	 * @Description 新增流程记录
	 * @CreateTime 2018年1月11日 上午10:09:17
	 * @CreateBy 岳彬彬
	 * @param mainid
	 * @param dprtcode
	 */
	@Override
	public void addRecord(String mainid, String dprtcode,String message) {
		ProcessRecord record = new ProcessRecord();
		User user = ThreadVarUtil.getUser();
		record.setId(UUID.randomUUID().toString());
		record.setMainid(mainid);
		if(StringUtils.isBlank(dprtcode)){
			record.setDprtcode(user.getJgdm());
		}else{
			record.setDprtcode(dprtcode);
		}
		record.setCzrmc(user.getRealname());
		record.setBmmc(null == user.getDepartment()?"":user.getDepartment().getDprtname());
		record.setCzsm(message);
		record.setCzsj(new Date());
		processRecordMapper.insertSelective(record);
	}

	/**
	 * 
	 * @Description 批量新增
	 * @CreateTime 2018年1月15日 上午11:28:24
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param dprtcode
	 * @param message
	 */
	public void addBatchRecord(List<String> idList, String dprtcode,String message) {
		ProcessRecord record = new ProcessRecord();
		User user = ThreadVarUtil.getUser();
		record.setId(UUID.randomUUID().toString());
		record.getParamsMap().put("list", idList);
		if(StringUtils.isBlank(dprtcode)){
			record.setDprtcode(user.getJgdm());
		}else{
			record.setDprtcode(dprtcode);
		}
		record.setCzrmc(user.getRealname());
		record.setBmmc(null == user.getDepartment()?"":user.getDepartment().getDprtname());
		record.setCzsm(message);
		record.setCzsj(new Date());
		processRecordMapper.batchInsert(record);
	}


	/**
	 * 
	 * @Description 根据mainid获取数据
	 * @CreateTime 2018-2-28 下午2:36:27
	 * @CreateBy 孙霁
	 * @param mainid
	 * @return
	 */
	@Override
	public List<ProcessRecord> selectByMainid(String mainid) {
		return processRecordMapper.selectByMainid(mainid);
	}
	/**
	 * 
	 * @Description 根据maind删除数据
	 * @CreateTime 2018年3月23日 下午5:59:56
	 * @CreateBy 岳彬彬
	 * @param mainid
	 */
	@Override
	public void deleteRecord(String mainid) {
		processRecordMapper.deleteByMainid(mainid);
		
	}
}