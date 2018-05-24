package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.GiveInstruction;

/**
 * @Description 技术评估单-下达指令Service
 * @CreateTime 2017年8月18日 下午10:08:31
 * @CreateBy 林龙
 */
public interface GiveInstructionService {
	
	/**
	 * @Description 新增技术评估单-下达指令
	 * @CreateTime 2017年8月18日 下午10:11:47
	 * @CreateBy 林龙
	 * @param giveInstruction
	 * @param uuid 技术评估单id
	 * @param czls 流水号
	 */
	public void insertGiveInstruction(List<GiveInstruction> giveInstruction, String mainid, String czls) throws BusinessException;

	/**
	 * 
	 * @Description 根据技术评估单-下达指令对象查询技术评估单-下达指令List集合
	 * @CreateTime 2017年8月19日 上午10:09:47
	 * @CreateBy 林龙
	 * @param giveInstruction
	 * @return 技术评估单-下达指令List集合
	 */
	public List<GiveInstruction> selectOrderList(GiveInstruction giveInstruction) throws BusinessException;

	/**
	 * 
	 * @Description 编辑技术评估单-下达指令List集合 
	 * @CreateTime 2017年8月22日 上午10:32:57
	 * @CreateBy 林龙
	 * @param giveInstructionList 下达指令List集合
	 * @param id 评估单id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	public void updateGiveInstruction(List<GiveInstruction> giveInstructionList, String id, String czls)throws BusinessException;

	/**
	 * 
	 * @Description 根据mainid 技术评估单id删除 下达指令List集合 
	 * @CreateTime 2017年8月24日 下午3:35:10
	 * @CreateBy 林龙
	 * @param mainid 技术评估单id
	 * @param czls 流水号
	 */
	public void deleteGiveInstruction(String mainid, String czls)throws BusinessException;

	/**
	 * 
	 * @Description 根据mainid集合 技术评估单id删除 下达指令List集合 
	 * @CreateTime 2017年9月1日 上午9:42:33
	 * @CreateBy 林龙
	 * @param pgdidList
	 * @return
	 */
	public List<GiveInstruction> selectOrdersList(List<String> pgdidList);

}
