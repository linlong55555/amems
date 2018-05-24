package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.DestructionDetailMapper;
import com.eray.thjw.aerialmaterial.po.Destruction;
import com.eray.thjw.aerialmaterial.po.DestructionDetail;
import com.eray.thjw.aerialmaterial.service.DestructionDetailService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.PartsDisassemblyRecord;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class DestructionDetailServiceImpl implements DestructionDetailService{

	@Autowired
	private DestructionDetailMapper destructionDetailMapper;
	/**
	 * @author sunji
	 * @description  根据条件分页销毁记录清单
	 * @param destructionDetail
	 * @return List<Destruction>
	 */
	public Map<String, Object> queryAll(DestructionDetail destructionDetail)
			throws BusinessException {
		PageHelper.startPage(destructionDetail.getPagination());
		List<DestructionDetail> list = this.destructionDetailMapper.queryAll(destructionDetail);
		return PageUtil.pack4PageHelper(list, destructionDetail.getPagination());
	}
	/**
	 * @author sunji
	 * @description  根据条件不分页销毁记录清单
	 * @param destructionDetail
	 * @return List<Destruction>
	 */
	public List<DestructionDetail> queryAllPageList(DestructionDetail destructionDetail) throws BusinessException {
		return  this.destructionDetailMapper.queryAllPageList(destructionDetail);
	}

}
