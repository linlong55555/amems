package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.dao.UpMapper;

public interface OtherBorrowApplyMapper extends UpMapper{
    
    BorrowApply selectById(String id);
   
    /**
	 * 借入申请分页
	 * @param borrowApply
	 * @return
	 */
	public List<BorrowApply> queryAllPageListjie(BorrowApply borrowApply)throws RuntimeException;
	
}