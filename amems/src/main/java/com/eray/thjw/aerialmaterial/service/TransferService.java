package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Transfer;

public interface TransferService {
	 /**
	 * 新增移库操作记录
	 * @return
	 * @author meizhiliang
	 */
	int insertSelective(Map<String, Object> map); 
	/**
	 * 分页查询移库列表
	 * @return
	 * @author meizhiliang
	 */
	List<Transfer> selectTransferList(Transfer record);  //分页查询移库列表

	/**
	 * @author meizhiliang
	 * 撤销移库操作
	 * @return
	 */
	int deleteByPrimaryKey(String id);
	/**
	 * @author meizhiliang
	 * 根绝移库单号查询移库单集合
	 * @return
	 */
	List<Transfer> selectByKey(String id);  
}
