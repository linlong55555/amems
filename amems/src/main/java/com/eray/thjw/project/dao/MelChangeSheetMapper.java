package com.eray.thjw.project.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.project.po.MelChangeSheet;

public interface MelChangeSheetMapper {
    int deleteByPrimaryKey(String id);

    int insert(MelChangeSheet record);

    int insertSelective(MelChangeSheet record);

    MelChangeSheet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MelChangeSheet record);

    int updateByPrimaryKey(MelChangeSheet record);
    
    /**
	 * @author liub
	 * @description 检查是否存在
	 * @param record
	 * @return List<MelChangeSheet>
	 */
	List<MelChangeSheet> checkExists(MelChangeSheet record);
	
	public List<MelChangeSheet> queryAllPageList(MelChangeSheet record);
	
	public MelChangeSheet selectById(String id);
	/**
	 * 
	 * @Description 根据idList 查询数据
	 * @CreateTime 2017-8-30 下午5:10:34
	 * @CreateBy 孙霁
	 * @param idList
	 * @return
	 */
	public List<MelChangeSheet> queryByIdList(List<String> idList);
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017-8-30 下午5:10:29
	 * @CreateBy 孙霁
	 * @param map
	 * @return
	 */
	public int updateBatchAudit(Map<String, Object> map);
	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017-8-30 下午5:10:29
	 * @CreateBy 孙霁
	 * @param map
	 * @return
	 */
	public int updateBatchApprove(Map<String, Object> map);
	
}