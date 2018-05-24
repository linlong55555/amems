package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.po.BaseEntity;

public interface BorrowApplyMapper {
    int deleteByPrimaryKey(String id);

    int insert(BorrowApply record);

    int insertSelective(BorrowApply record);

    BorrowApply selectByPrimaryKey(String id);
    
    BorrowApply selectById(String id);

    int updateByPrimaryKeySelective(BorrowApply record);

    int updateByPrimaryKey(BorrowApply record);
    
    /**
     * 分页查询待入库借入申请  申请单状态为 2提交、3核实
     * @author xu.yong
     * @param record
     * @return
     */
    List<BorrowApply> select4InstockPage(BaseEntity record);
    
    /**
	 * 借入申请分页
	 * @param borrowApply
	 * @return
	 */
	public List<BorrowApply> queryAllPageListjie(BorrowApply borrowApply)throws RuntimeException;
	
}