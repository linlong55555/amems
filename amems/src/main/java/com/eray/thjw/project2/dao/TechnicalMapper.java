package com.eray.thjw.project2.dao;
import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Bulletin;
import com.eray.thjw.project2.po.Technical;

/**
 * 
 * @Description 技术文件评估单Mapper
 * @CreateTime 2017年8月16日 上午10:32:35
 * @CreateBy 林龙
 */
public interface TechnicalMapper {
	
	/**
	 * @Description 技术文件评估单删除
	 * @CreateTime 2017年8月16日 上午10:20:25
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 技术文件评估单id
	 */
    void deleteByPrimaryKey(String id);

    /**
	 * @Description 保存技术评估单
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 技术文件评估单id
	 */
    void insertSelective(Technical technical);

    Technical selectByPrimaryKey(String id);

    /**
     * @Description 根据技术文件评估单对象修改技术评估单
     * @CreateTime 2017年8月18日 下午9:07:34
     * @CreateBy 林龙
     * @param technical 技术评估单
     */
    void updateByPrimaryKeySelective(Technical technical);

    /**
	 * @Description 技术文件评估单分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return 分页数据集合
	 */
	List<Technical> queryAllPageList(Technical technical);
	/**
	 * @Description 根据指令id、组织机构获取技术评估单
	 * @CreateTime 2017年8月16日 上午11:01:08
	 * @CreateBy 岳彬彬
	 * @param technical
	 * @return
	 */
	List<Technical> selectPgdByZlidAndDprtcode(Technical technical);

	/**
	 * @Description 根据适航性资料查询数据
	 * @CreateTime 2017-8-16 上午10:43:00
	 * @CreateBy 孙霁
	 * @param jswjid
	 * @return
	 */
	List<Technical> selectByjswjid(String jswjid);

	/**
	 * @Description 根据适航性资料id查询评估单
	 * @CreateTime 2017年8月15日 下午8:17:29
	 * @CreateBy 林龙
	 * @param technical 技术文件评估单
	 * @return List<Technical> 技术文件评估单List集合
	 */
	List<Technical> queryGljspgdList(Technical technical);

	/**
	 * @Description 根据技术文件评估单对象查询技术文件数量
	 * @CreateTime 2017年8月18日 下午9:08:48
	 * @CreateBy 林龙
	 * @param tec
	 * @return 数量
	 */
	int queryCount(Technical tec);
	
	List<Technical> selectTechnicalfileByJswjid(String jswjid);

	/**
	 * @Description 根据技术文件评估单id查询技术评估单信息
	 * @CreateTime 2017年8月15日 下午8:15:37
	 * @CreateBy 林龙
	 * @param technical 技术评估单
	 * @return
	 * @throws BusinessException
	 */
	Technical getById(Technical technical);

	/**
	 * 
	 * @Description 根据id集合查询 技术评估单集合
	 * @CreateTime 2017年8月23日 上午9:48:00
	 * @CreateBy 林龙
	 * @param idList
	 * @return
	 */
	List<Technical> queryByIdList(List<String> idList);

	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017年8月23日 上午9:53:41
	 * @CreateBy 林龙
	 * @param paramMap
	 */
	void updateBatchAudit(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017年8月23日 上午9:53:41
	 * @CreateBy 林龙
	 * @param paramMap
	 */
	void updateBatchApprovel(Map<String, Object> paramMap);

	/**
	 * 
	 * @Description 根据技术文件单号+版本查询技术评估单数量
	 * @CreateTime 2017年8月23日 上午10:49:20
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 */
	int getCount4Validation(Technical technical);

	/**
	 * 
	 * @Description  根据技术文件评估单id查询技术文件评估单状态
	 * @CreateTime 2017年8月23日 上午10:59:44
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 */
	int getCurrentZt4Validation(String id);

	/**
	 * 
	 * @Description 根据技术评估单查询最大版本
	 * @CreateTime 2017年8月28日 下午3:57:59
	 * @CreateBy 林龙
	 * @param technical
	 * @return
	 * @throws BusinessException
	 */
	Technical selectMaxBb(Technical technical);

	/**
	 * 
	 * @Description 根据id查询当前评估单的所有版本
	 * @CreateTime 2017年8月29日 上午10:50:57
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @param dprtcode 
	 * @return
	 */
	List<Technical> queryHistoryListById(String id, String dprtcode);
	/**
	 * 
	 * @Description 根据arids获取评估单数据
	 * @CreateTime 2017-9-1 下午3:59:57
	 * @CreateBy 孙霁
	 * @param arids
	 * @throws BusinessException
	 * @return
	 */
	List<Technical> queryAllByGlxswjids(List<String> arids);

	List<Technical> getgljspgdListold(Technical technical);

	
	
}