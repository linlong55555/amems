package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ToolBorrowReturn;
import com.eray.thjw.po.User;


/**
 * @Description 工具/设备借归service
 * @CreateTime 2018年3月27日 下午3:28:06
 * @CreateBy 韩武
 */
public interface ToolBorrowReturnService {
	
	/**
	 * @Description 加载人员
	 * @CreateTime 2018年3月27日 下午3:28:50
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	User loadUser(Map<String, String> record) throws BusinessException;
	
	/**
	 * @Description 加载工具
	 * @CreateTime 2018年3月27日 下午3:31:03
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	Stock loadTool(Map<String, String> record) throws BusinessException;
	
	/**
	 * @Description 根据关键字查询人员/工具
	 * @CreateTime 2018年3月27日 下午3:31:58
	 * @CreateBy 韩武
	 * @param keyword
	 * @return
	 */
	Map<String, Object> loadByKeyword(Map<String, String> record) throws BusinessException;
	
	/**
	 * @Description 工具/设备借归提交
	 * @CreateTime 2018年4月2日 下午3:24:51
	 * @CreateBy 韩武
	 * @param list
	 * @throws BusinessException
	 */
	void doSubmit(List<ToolBorrowReturn> list) throws BusinessException;
}
