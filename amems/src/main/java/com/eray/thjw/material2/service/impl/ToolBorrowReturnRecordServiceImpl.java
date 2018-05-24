package com.eray.thjw.material2.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.material2.dao.ToolBorrowReturnRecordMapper;
import com.eray.thjw.material2.po.ToolBorrowReturnRecord;
import com.eray.thjw.material2.service.ToolBorrowReturnRecordService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/**
 * @Description 工具借用/归还记录service实现类
 * @CreateTime 2018年4月3日 下午5:39:42
 * @CreateBy 韩武
 */
@Service("toolBorrowReturnRecordService")
public class ToolBorrowReturnRecordServiceImpl implements ToolBorrowReturnRecordService  {

	@Resource
	private ToolBorrowReturnRecordMapper toolBorrowReturnRecordMapper;

	/**
	 * @Description 查询工具/设备借归记录分页列表
	 * @CreateTime 2018年4月3日 下午5:39:07
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public Map<String, Object> queryPageableList(ToolBorrowReturnRecord record) {
		PageHelper.startPage(record.getPagination());
		List<ToolBorrowReturnRecord> list = toolBorrowReturnRecordMapper.queryList(record);
		return PageUtil.pack4PageHelper(list, record.getPagination());
	}

	/**
	 * @Description 查询工具/设备借归记录列表
	 * @CreateTime 2018年4月3日 下午5:39:07
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	@Override
	public List<ToolBorrowReturnRecord> queryList(ToolBorrowReturnRecord record) {
		return toolBorrowReturnRecordMapper.queryList(record);
	}
}