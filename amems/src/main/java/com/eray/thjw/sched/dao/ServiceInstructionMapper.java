package com.eray.thjw.sched.dao;

import java.util.List;

import com.eray.thjw.sched.po.ServiceInstruction;

public interface ServiceInstructionMapper {
    int deleteByPrimaryKey(String id);

    int insert(ServiceInstruction record);

    int insertSelective(ServiceInstruction record);

    ServiceInstruction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServiceInstruction record);

    int updateByPrimaryKey(ServiceInstruction record);

    /**
     * 以飞机注册号，机构做条件，  查询出截至日期>=当前截至日期之后的指令，按批次倒序，取第一个。
     * @param serviceInstruction
     * @return
     */
    ServiceInstruction selectOne(ServiceInstruction serviceInstruction);

    /**
     * 查询所有飞机以及最新的一批指令
     * @return
     */
	List<ServiceInstruction> selectList4Planes();

	/**
	 * 删除所有预排服务指令
	 */
	void deleteAll4Sched( );
}