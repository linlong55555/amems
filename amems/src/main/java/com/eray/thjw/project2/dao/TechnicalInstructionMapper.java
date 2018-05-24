package com.eray.thjw.project2.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.project2.po.TechnicalInstruction;

/**
 * @Description 指术指令 B_G_004
 * @CreateTime 2017年9月5日 上午10:43:38
 * @CreateBy 徐勇
 */
public interface TechnicalInstructionMapper {
    int deleteByPrimaryKey(String id);

    int insert(TechnicalInstruction record);
    /**
     * 
     * @Description 新增技术指令
     * @CreateTime 2017年9月5日 下午5:56:50
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    int insertSelective(TechnicalInstruction record);

    TechnicalInstruction selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TechnicalInstruction record);

    int updateByPrimaryKey(TechnicalInstruction record);
    /**
     * 
     * @Description 获取技术指令列表
     * @CreateTime 2017年9月5日 下午5:56:32
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<TechnicalInstruction> queryInstructionList(TechnicalInstruction record);
    /**
     * 
     * @Description 
     * @CreateTime 2017年9月5日 下午5:57:05
     * @CreateBy 岳彬彬
     * @param id
     * @return
     */
    Integer getCurrentZt4Validation(String id);
    /**
     * 
     * @Description 根据id集合获取技术指令数据
     * @CreateTime 2017年9月6日 上午10:16:56
     * @CreateBy 岳彬彬
     * @param idList
     * @return
     */
    List<TechnicalInstruction> queryByIdList(List<String> idList);
    /**
     * 
     * @Description 批量审核
     * @CreateTime 2017年9月6日 上午10:20:33
     * @CreateBy 岳彬彬
     * @param map
     * @return
     */
    int updateBatchAudit(Map<String, Object> map);
    /**
     * 
     * @Description 批量批准
     * @CreateTime 2017年9月6日 上午10:20:44
     * @CreateBy 岳彬彬
     * @param map
     * @return
     */
    int updateBatchApprove(Map<String, Object> map);
    /**
     * 
     * @Description 审核
     * @CreateTime 2017年9月6日 上午10:42:24
     * @CreateBy 岳彬彬
     * @param record
     */
    void updateShByPrimaryKey(TechnicalInstruction record);
    /**
     * 
     * @Description 审批
     * @CreateTime 2017年9月6日 上午10:42:33
     * @CreateBy 岳彬彬
     * @param record
     */
    void updatePzByPrimaryKey(TechnicalInstruction record);
    /**
     * 
     * @Description 生效
     * @CreateTime 2017年9月6日 上午11:10:13
     * @CreateBy 岳彬彬
     * @param id
     */
    void updateZt4Sx(String id);
    /**
     * 
     * @Description 批量生效
     * @CreateTime 2017年9月6日 上午11:10:41
     * @CreateBy 岳彬彬
     * @param idList
     */
    void updateZt4sxByIdlist(List<String> list);
    /**
     * 
     * @Description 更改接收状态为1
     * @CreateTime 2017年9月6日 下午4:10:14
     * @CreateBy 岳彬彬
     * @param id
     */
    void updateJsztById(String id);
    
    /**
     * @Description 查询历史版本
     * @CreateTime 2018年2月24日 下午4:36:49
     * @CreateBy 韩武
     * @param id
     * @return
     */
    List<TechnicalInstruction> queryHistoryList(String id);
    
    /**
     * @Description 更新上个版本的版本状态
     * @CreateTime 2018年2月24日 下午5:07:46
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int updatePrivousVersionStatus(TechnicalInstruction record);
    
    /**
	 * @Description 批量更新上个版本的版本状态为老版本
	 * @CreateTime 2018年2月26日 上午9:30:19
	 * @CreateBy 韩武
	 * @param map
	 * @return
	 */
	int updateBatchVersionStatus(Map<String, Object> map);
}