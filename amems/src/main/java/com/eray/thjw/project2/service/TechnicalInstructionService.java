package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.TechnicalInstruction;

public interface TechnicalInstructionService {

	/**
	 * 
	 * @Description 保存提交
	 * @CreateTime 2017年9月5日 下午2:12:44
	 * @CreateBy 岳彬彬
	 * @param insruction
	 * @return
	 * @throws BusinessException
	 */
	String insertInstruction(TechnicalInstruction instruction) throws BusinessException;
	/**
	 * 
	 * @Description 技术指令列表
	 * @CreateTime 2017年9月5日 下午3:45:17
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @return
	 * @throws BusinessException
	 */
	Map<String, Object> queryAllInstruction(TechnicalInstruction instruction)throws BusinessException;
	/**
	 * 
	 * @Description 获取技术指令
	 * @CreateTime 2017年9月6日 上午10:12:53
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	TechnicalInstruction getRecord(String id);
	/**
	 * 
	 * @Description 修改
	 * @CreateTime 2017年9月6日 上午10:12:46
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @return
	 * @throws BusinessException
	 */
	String updateRecord(TechnicalInstruction instruction) throws BusinessException;
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年9月6日 上午10:12:39
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException 
	 */
	String updateBatchAudit(List<String> idList,String yj) throws BusinessException;
	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年9月6日 上午10:13:28
	 * @CreateBy 岳彬彬
	 * @param idList
	 * @param yj
	 * @return
	 * @throws BusinessException 
	 */
	String updateBatchApprove(List<String> idList,String yj) throws BusinessException;
	/**
	 * 
	 * @Description 审核批准
	 * @CreateTime 2017年9月6日 上午10:37:27
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	String getReviewAndApproveRecord(TechnicalInstruction instruction) throws BusinessException;
	/**
	 * 
	 * @Description 作废
	 * @CreateTime 2017年9月6日 上午11:13:22
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @throws BusinessException
	 */
	void deleteRecord(TechnicalInstruction instruction) throws BusinessException;
	/**
	 * 
	 * @Description 关闭
	 * @CreateTime 2017年9月6日 下午4:01:33
	 * @CreateBy 岳彬彬
	 * @param instruction
	 * @throws BusinessException
	 */
	void update4Close(TechnicalInstruction instruction) throws BusinessException;
	/**
	 * 
	 * @Description 更改接收标识
	 * @CreateTime 2017年9月6日 下午4:02:07
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	void updateZt4Isjs(TechnicalInstruction instruction);
	
	/**
	 * @Description 查询历史版本
	 * @CreateTime 2018年2月23日 下午3:04:52
	 * @CreateBy 韩武
	 * @param id
	 * @return
	 */
	List<TechnicalInstruction> queryHistoryList(String id);
	
	/**
	 * @Description 改版技术指令
	 * @CreateTime 2018年2月24日 上午10:31:34
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	String doRevision(TechnicalInstruction record) throws BusinessException;
}
