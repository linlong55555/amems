package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;

/**
 * 借入申请
 * @author xu.yong
 */
public interface BorrowApplyService {

	/**
	 * 查询待入库借入申请    申请单状态为 2提交、3核实
	 * @param baseEntity
	 * @return
	 */
	public Map<String, Object> queryPage4Instock(BaseEntity baseEntity);

	/**
	 * 借入申请分页
	 * @param borrowApply
	 * @return
	 */
	public List<BorrowApply> queryAllPageListjie(BorrowApply borrowApply)throws RuntimeException;
	
	public String query(BorrowApply borrowApply);


	public void checkEdit(String id)throws RuntimeException;

	public BorrowApply selectByPrimaryKey(String id)throws RuntimeException;

	public void cancel(String id)throws RuntimeException, BusinessException;

	public void updateByPrimaryKeySelective(BorrowApply borrowApply)throws RuntimeException, BusinessException;

	public BorrowApply selectById(String id)throws RuntimeException;

	String add(BorrowApply borrowApply, List<HCMainData> hCMainDatas);

	public String queryedit(BorrowApply borrowApply) throws BusinessException;

	String edit(BorrowApply borrowApply, List<HCMainData> hCMainDatas)
			throws BusinessException;



	
}
