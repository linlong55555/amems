package com.eray.thjw.project2.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Instructionsource;

/**
 * 
 * @Description 下达指令来源表Service
 * @CreateTime 2017年8月19日 下午2:44:59
 * @CreateBy 林龙
 */
public interface InstructionsourceService {

	/**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月19日 下午2:46:01
	 * @CreateBy 林龙
	 * @param instructionsourc 下达指令来源
	 * @return 下达指令来源List
	 * @throws BusinessException
	 */
	public List<Instructionsource> selectOrderList(Instructionsource instructionsourc) throws BusinessException;

	/**
	 * @Description 根据指令id删除对应的下达指令
	 * @CreateTime 2017-8-22 上午10:35:59
	 * @CreateBy 刘兵
	 * @param zlid 指令id
	 */
	void deleteInstructionSourceByZlid(String zlid);
	
	/**
	 * @Description 保存多个下达指令来源
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param instructionsourceList 下达指令来源集合
	 * @param zlxl 指令类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param zlid 指令id
	 * @param dprtcode 机构代码
	 */
	void saveInstructionSourceList(List<Instructionsource> instructionsourceList, int zlxl, String zlid, String dprtcode);
	
	/**
	 * @Description 编辑多个下达指令来源
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param instructionsourceList 下达指令来源集合
	 * @param zlxl 指令类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param zlid 指令id
	 * @param dprtcode 机构代码
	 */
	void updateInstructionSourceList(List<Instructionsource> instructionsourceList, int zlxl, String zlid, String dprtcode);

	/**
	 * 
	 * @Description 根据评估单id验证 下达的指令（除技术通告） 必须EO关闭（指定结束/完成）维修方案批准、MEL更改单批准、工卡生效/失效、 技术指令关闭 才能关闭
	 * @CreateTime 2017年8月24日 下午9:26:03
	 * @CreateBy 林龙
	 * @param id 评估单id
	 * @throws BusinessException
	 */
	public void validation4Colse(String id)throws BusinessException;
}
