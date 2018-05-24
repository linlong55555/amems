package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.OtherBorrowApplyDetailMapper;
import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.aerialmaterial.service.OtherBorrowApplyDetailService;
import com.eray.thjw.exception.BusinessException;

/**
 * 其它飞行队借入申请附表
 * @author xu.yong
 *
 */
@Service("otherborrowApplyDetailService")
public class OtherBorrowApplyDetailServiceImpl implements OtherBorrowApplyDetailService {
	@Resource
	private OtherBorrowApplyDetailMapper otherBorrowApplyDetailMapper;
	

	@Override
	public List<BorrowApplyDetail> queryReserveDetailListByMainId(String mainid)
			throws BusinessException {
		return otherBorrowApplyDetailMapper.selectByMainId(mainid);
	}
	

	


}
