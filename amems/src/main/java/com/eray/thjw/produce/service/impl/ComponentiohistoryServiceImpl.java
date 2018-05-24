package com.eray.thjw.produce.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.dao.DisassemblingImportMapper;
import com.eray.thjw.produce.dao.InstallationListEffectiveMapper;
import com.eray.thjw.produce.po.InstallationListEffective;
import com.eray.thjw.produce.service.ComponentiohistoryService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

/** 
 * @Description 
 * @CreateTime 2017-10-16 下午2:32:50
 * @CreateBy 孙霁	
 */
@Service
public class ComponentiohistoryServiceImpl implements ComponentiohistoryService{

	@Resource
	private InstallationListEffectiveMapper installationListEffectiveMapper;
	@Resource
	private DisassemblingImportMapper disassemblingImportMapper;
	
	/**
	 * 
	 * @Description 查询部件拆装记录列表数据
	 * @CreateTime 2017-10-16 下午2:35:08
	 * @CreateBy 孙霁
	 * @param installationListEffective
	 * @return  Map<String, Object>
	 */
	@Override
	public Map<String, Object> queryAll(
			InstallationListEffective installationListEffective) {
		PageHelper.startPage(installationListEffective.getPagination());
		List<InstallationListEffective> list = installationListEffectiveMapper.queryComponentiohistoryList(installationListEffective);
		return PageUtil.pack4PageHelper(list, installationListEffective.getPagination());
	}

	/**
	 * 
	 * @Description 删除拆装记录的导入数据
	 * @CreateTime 2017-12-5 上午11:14:43
	 * @CreateBy 孙霁
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(String id) throws BusinessException {
		disassemblingImportMapper.deleteByPrimaryKey(id);
	}

}
