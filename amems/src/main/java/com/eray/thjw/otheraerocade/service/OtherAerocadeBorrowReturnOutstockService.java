package com.eray.thjw.otheraerocade.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;

/**
 * 外飞行借入归还出库单
 * @author xu.yong
 *
 */
public interface OtherAerocadeBorrowReturnOutstockService {
	
	/**
	 * 根据ID查询 外飞行队 借入归还出库单
	 * @param id
	 */
	public Outstock queryBorrowReturnOutstockById(String id);

	/**
	 * 查询 外飞行队 借入归还出库单
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryBorrowReturnOutstock(BaseEntity entity) throws BusinessException;
	
	/**
	 * 查询 外飞行队 借入归还出库单航材清单
	 * @param id 出库单ID
	 * @return
	 */
	public List<OutstockDetail> queryBorrowReturnOutstockDetail(String id);
	
	/**
	 * 根据外飞行队出库航材分配借出航材
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public List<Expatriate> getExpatriateByBJ(Map<String, Object> map) throws BusinessException;
}
