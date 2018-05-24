package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.Instruction;

public interface InstructionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Instruction record);

    int insertSelective(Instruction record);

    Instruction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Instruction record);

    int updateByPrimaryKey(Instruction record);
    
    List<Instruction> queryInstructionAll(Instruction record);

    List<Instruction> queryAll(Map<String,Object> map);
    /**
   	 * @author liub
   	 * @description 批量审核
   	 * @param idList
   	 * @develop date 2016.12.28
   	 */
       int updateBatchAudit(Map<String, Object> map);
       
    /**
     * @author liub
     * @description 批量批准
     * @param idList
     * @develop date 2016.12.28
     */
    int updateBatchApprove(Map<String, Object> map);
       
    /**
	 * @author liub
	 * @description 通过评估单id查询技术指令
	 * @param pgdid
	 * @develop date 2017.03.15
	*/
    List<Instruction> queryByPgdId(String pgdid);
    //根据id查询数据
    List<Instruction> queryByIdList(List<String> idList);
    
}