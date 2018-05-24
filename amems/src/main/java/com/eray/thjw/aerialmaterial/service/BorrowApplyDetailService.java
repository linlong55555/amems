package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.exception.BusinessException;


/**
 * 借入申请附表
 * @author xu.yong
 */
public interface BorrowApplyDetailService {

	List<BorrowApplyDetail> queryReserveDetailListByMainId(String mainid)throws BusinessException;

}
