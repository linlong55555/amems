package com.eray.thjw.quality.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.quality.po.AuditMembers;
/**
 * 
 * @Description 审核成员 Mapper
 * @CreateTime 2018年1月4日 上午11:02:52
 * @CreateBy 林龙
 */
public interface AuditMembersMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditMembers record);

    int insertSelective(AuditMembers record);

    AuditMembers selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditMembers record);

    int updateByPrimaryKey(AuditMembers record);

	List<AuditMembers> queryByYwidAndYwlxAndDrpt(String uuid, Integer ywlx,String dprtcode);

	void delete4Batch(List<String> columnValueList);
	
	/**
	 * 
	 * @Description 根据业务id查询数据
	 * @CreateTime 2018-1-11 上午11:22:44
	 * @CreateBy 孙霁
	 * @param ywid
	 * @return
	 */
	List<AuditMembers> selectByYwid(String ywid);
	
	int deleteByYwid(String ywid);
	
	/**
     * @Description 复制审核成员
     * @CreateTime 2018-01-20 下午5:15:09
     * @CreateBy 刘兵
     * @param newywid 新的newywid
     * @param ywid 条件ywid
     */
    void insert4Copy(@Param("newywid")String newywid, @Param("ywid")String ywid);
}