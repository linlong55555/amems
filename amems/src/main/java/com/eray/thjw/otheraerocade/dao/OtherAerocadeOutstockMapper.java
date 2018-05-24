package com.eray.thjw.otheraerocade.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.dao.UpMapper;
import com.eray.thjw.po.BaseEntity;

/**
 * 统一平台：外飞行队出库单
 * @author xu.yong
 *
 */
public interface OtherAerocadeOutstockMapper extends UpMapper {

	/**
	 * 根据ID查询借入归还出库单
	 * @param id
	 * @return
	 */
	Outstock selectBorrowReturnById(String id);
	/**
	 * 分页查询借入归还出库单
	 * @param entity
	 * @return
	 */
	List<Outstock> selectBorrowReturnPage(BaseEntity entity);
	
	/**
	 * 查询借入归还出库单航材清单
	 * @param id
	 * @return
	 */
	List<OutstockDetail> selectBorrowReturnDetail(String id);
}
