package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.project2.po.GiveInstruction;

public interface GiveInstructionMapper {
    int deleteByPrimaryKey(String id);

    int insert(GiveInstruction record);

	/**
	 * @Description 新增技术评估单-下达指令
	 * @CreateTime 2017年8月18日 下午10:11:47
	 * @CreateBy 林龙
	 * @param giveInstruction
	 * @param uuid 技术评估单id
	 * @param czls 流水号
	 */
    int insertSelective(GiveInstruction giveInstruction);

    GiveInstruction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(GiveInstruction record);

    int updateByPrimaryKey(GiveInstruction record);

    /**
	 * 
	 * @Description 根据技术评估单-下达指令对象查询技术评估单-下达指令List集合
	 * @CreateTime 2017年8月19日 上午10:09:47
	 * @CreateBy 林龙
	 * @param giveInstruction
	 * @return 技术评估单-下达指令List集合
	 */
	List<GiveInstruction> selectOrderList(GiveInstruction giveInstruction);

	void delete4Batch(List<String> columnValueList);

	List<GiveInstruction> selectOrdersList(List<String> pgdidList);
}