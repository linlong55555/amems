package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.Instruction;
import com.eray.thjw.po.User;

public interface InstructionService {

	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Instruction> queryInstructionAll(Instruction record);
	/**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<Instruction> queryAll(Map<String,Object> map);
	/**
	 * @author sunji
	 * @description 保存提交
	 * @param Instruction
	 * @return map
	 * @develop date 2016.08.15
	 */
	public String insertInstruction(Instruction insruction) throws BusinessException;
	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdIt(User user,String id);
	
	/**
	 * @author sunji
	 * @description 根据主键查询
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public Instruction getByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description 修改
	 * @param Instruction
	 * @return map
	 * @develop date 2016.08.15
	 */
	public String updateInstruction(Instruction instruction, int [] i)throws BusinessException;
	/**
	 * @author sunji
	 * @description  关闭
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offInstruction (User user,Instruction instruction) throws Exception;
	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteInstruction (User user,String ids) throws Exception;
	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheInstruction(Instruction instruction)throws BusinessException;
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuInstruction(Instruction instruction)throws BusinessException;
	/**
	 * @author sunji
	 * @description  修改接收状态
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateJszt(Instruction instruction)throws BusinessException;
	/**
	 * @author liub
	 * @description 批量审核
	 * @param idList
	 * @develop date 2016.12.28
	 */
	public String updateBatchAudit(List<String> idList,String yj);
	/**
	 * @author liub
	 * @description 批量批准
	 * @param idList
	 * @develop date 2016.12.28
	 */
	public String updateBatchApprove(List<String> idList,String yj);
	
	/**
	 * @author liub
	 * @description 通过评估单id查询技术指令
	 * @param pgdid
	 * @develop date 2017.03.15
	*/
	public List<Instruction> queryByPgdId(String pgdid);
	
	/**
	 * @author sunji
	 * @description 通过条件查询技术指令
	 * @param instruction
	*/
	public Map<String, Object> queryAllInstruction(Instruction instruction)throws BusinessException;
}
