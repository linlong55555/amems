package com.eray.thjw.system.dao;

import java.util.List;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.system.po.SynRel;
/**
 * 
 * @Description 系统同步关系Dao
 * @CreateTime 2017年9月25日 上午11:24:48
 * @CreateBy 朱超
 */
public interface SynRelMapper {
    int deleteByPrimaryKey(String id);

    int insert(SynRel record);

    int insertSelective(SynRel record);

    SynRel selectByPrimaryKey(String id);
    
    /**
     * 
     * @Description 查询同步关系列表
     * @CreateTime 2017年9月25日 上午11:44:16
     * @CreateBy 朱超
     * @param record
     * @return
     */
    List<SynRel> selectList(SynRel record);

    int updateByPrimaryKeySelective(SynRel record);

    int updateByPrimaryKey(SynRel record);

    /**
     * 
     * @Description 批量物理删除
     * @CreateTime 2017年9月25日 上午11:50:44
     * @CreateBy 朱超
     * @param baseEntity
     */
	void deletes(BaseEntity baseEntity);
	/**
	 * 
	 * @Description 工包135外部执行单位在系统同步关系表中是否存在
	 * @CreateTime 2017年9月28日 上午11:16:23
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	int getCount4Workpackage(SynRel record);
	/**
	 * 
	 * @Description 根据对象id和组织机构查询
	 * @CreateTime 2017年10月9日 下午5:52:34
	 * @CreateBy 岳彬彬
	 * @param dxid
	 * @param dprtcode
	 * @return
	 */
	SynRel getSyrelByDxidAndDprtcode(String dxid,String dprtcode);
	/**
	 * 
	 * @Description 根据管理机构代码和组织机构获取数据
	 * @CreateTime 2017年10月9日 下午5:53:32
	 * @CreateBy 岳彬彬
	 * @param GLJGDM
	 * @param dprtcode
	 * @return
	 */
	List<SynRel> getSyrelByParams(String GLJGDM,String dprtcode);
	/**
	 * 
	 * @Description 145工包中下发单位为外部，在T_SYN_REL中的数据
	 * @CreateTime 2017年11月16日 上午9:27:17
	 * @CreateBy 岳彬彬
	 * @param dxid
	 * @param dprtcode
	 * @return
	 */
	SynRel selectByDxidAndDprtcode(String dxid,String dprtcode);
	/**
	 * 
	 * @Description 145工包中下发单位为外部，在T_SYN_REL中的数据 
	 * @CreateTime 2017年11月16日 上午9:27:35
	 * @CreateBy 岳彬彬
	 * @param GLJGDM
	 * @param dprtcode
	 * @return
	 */
	List<SynRel> selectSyrelByParams(String GLJGDM,String dprtcode);
}