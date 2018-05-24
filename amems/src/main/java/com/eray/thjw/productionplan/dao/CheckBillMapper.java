package com.eray.thjw.productionplan.dao;

import com.eray.thjw.productionplan.po.CheckBill;


public interface CheckBillMapper {
    int deleteByPrimaryKey(String id);

    int insert(CheckBill record);

    int insertSelective(CheckBill record);

    CheckBill selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CheckBill record);

    int updateByPrimaryKey(CheckBill record);
    
    /**
   	 * 1、save b_g_016 定检工单
   	 * @param record
   	 * @return+
   	 * 
   	 * @throws Exception
   	 */
   	public void save(CheckBill checkBill) throws Exception;
}