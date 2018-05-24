package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Engineering;
import com.eray.thjw.po.TechnicalFile;

import enu.LogOperationEnum;


public interface EngineeringService {

	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Engineering> queryEngineeringAll(Engineering engineering);
	/**
	 * @author sunji
	 * @description 根据查询总条数
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int queryCount(Engineering engineering);
	/**
	 * @author sunji
	 * @description 验证根据查询总条数
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public int validationQueryCount(Engineering engineering);
	/**
	 * @author sunji
	 * @description 添加工程指令
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public String saveEngineering(Engineering engineering)throws BusinessException ;
	/**
	 * @author sunji
	 * @description 修改工程指令
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void updateEngineering(Engineering engineering,int[] i) throws BusinessException;
	/**
	 * @author sunji
	 * @description 根据主键id查询对象
	 * @param engineering
	 * @return int
	 * @develop date 2016.08.15
	 */
	public Engineering getByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheEngineering(Engineering engineering)throws BusinessException;
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuEngineering(Engineering engineering)throws BusinessException;
	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteEngineering (String ids) throws BusinessException;
	/**
	 * @author sunji
	 * @description  关闭（指定结束）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offEngineering (Engineering engineering) throws BusinessException;
	/**
	 * @author sunji
	 * @description 检查工程指令修改权限
	 * @param model,id
	 * @return 页面视图
	 * @develop date 2016.08.17
	 *
	 */
	public Map<String, Object> checkUpdMt(String id);
	/**
	 * @author sunji
	 * @description 根据主键查询已选择的评估单号
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public List<TechnicalFile> selectChoosePgd(String id);
	
	
    List<Engineering> queryEngineeringPageList(Engineering record);                //查询工单需要的工程指令
    
    /**
	 * @author sunji
	 * @description 修改工程指令（提交）
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
    public void updateEngineeringTj(Engineering recode);
    /**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<Engineering> queryAll(Map<String, Object> map);
	/**
	 * @author sunji
	 * @description  批量审核
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public String updateBatchReview(List<String> idList,String yj)throws BusinessException;
	/**
	 * @author sunji
	 * @description  批量批复
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public String updateBatchApprove(List<String> idList,String yj)throws BusinessException;
	
	/**
	 * 更新工程指令状态
	 * @param id 工程指令id
	 */
	public boolean updateStatus(String id, String czls, LogOperationEnum logOperationEnum);
	
	/**
	 * 根据EO工单id找到工程指令
	 * @param gczlid
	 * @return
	 */
	public Engineering findByEOId(String eoId);
	/**
	 * @author sunji
	 * @description  查看该id下的所有eo工卡的总条数
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public int queryCountbyEoId(String id)throws BusinessException;
	
	/**
	 * @author liub
	 * @description 通过评估单id查询EO指令
	 * @param pgdid
	 * @develop date 2017.03.16
	*/
	public List<Engineering> queryByPgdId(String pgdid);
	/**
	 * @author sunji
	 * @description 查询列表
	 * @param engineering
	*/
	public Map<String,Object> queryAllEngineering(Engineering engineering)throws BusinessException;
}
