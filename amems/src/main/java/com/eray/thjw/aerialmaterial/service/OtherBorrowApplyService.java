package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.BorrowApply;

/**
 * 其他飞行队借入申请
 * @author xu.yong
 */
public interface OtherBorrowApplyService {

	/**
	 * 借入申请分页
	 * @param borrowApply
	 * @return
	 */
	public List<BorrowApply> queryAllPageListjie(BorrowApply borrowApply)throws RuntimeException;
	
	/**
	 * 根据id查询借入申请主表
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	public BorrowApply selectById(String id)throws RuntimeException;
	
}
