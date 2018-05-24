package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.BorrowApplyDetailMapper;
import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.aerialmaterial.service.BorrowApplyDetailService;
import com.eray.thjw.exception.BusinessException;

/**
 * 借入申请附表
 * @author xu.yong
 *
 */
@Service("borrowApplyDetailService")
public class BorrowApplyDetailServiceImpl implements BorrowApplyDetailService {
	@Resource
	private BorrowApplyDetailMapper borrowApplyDetailMapper;
	

	@Override
	public List<BorrowApplyDetail> queryReserveDetailListByMainId(String mainid)
			throws BusinessException {
		return borrowApplyDetailMapper.selectByMainId(mainid);
	}
	

	


}
