package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OtherBorrowApplyMapper;
import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.service.OtherBorrowApplyService;

/**
 * 其它飞行队借入申请
 * @author xu.yong
 *
 */
@Service("otherBorrowApplyService")
public class OtherBorrowApplyServiceImpl implements OtherBorrowApplyService {
	
	@Resource
	private OtherBorrowApplyMapper otherBorrowApplyMapper;
	

	@Override
	public List<BorrowApply> queryAllPageListjie(BorrowApply borrowApply) throws RuntimeException{
		return otherBorrowApplyMapper.queryAllPageListjie(borrowApply);
	}

	@Override
	public BorrowApply selectById(String id) throws RuntimeException {
		return otherBorrowApplyMapper.selectById(id);
	}
}
