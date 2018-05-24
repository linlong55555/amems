package com.eray.thjw.project.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.FixedCheckItem;
import com.eray.thjw.project.po.MelChangeSheet;
import com.eray.thjw.training.po.Course;



/**
 * @author liub
 * @description 
 */
public interface MelService {

	/**
	 * @author liub
	 * @description 保存Mel变更单
	 * @param melChangeSheet
	 * @return String
	 */
	public String save(MelChangeSheet melChangeSheet) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 保存Mel变更单
	 * @param melChangeSheet
	 * @return String
	 */
	public String edit(MelChangeSheet melChangeSheet) throws BusinessException;
	
	public void deleteById(String id) throws BusinessException;
	
	public void updateAudit(MelChangeSheet melChangeSheet) throws BusinessException;
	
	public void updateApprove(MelChangeSheet melChangeSheet) throws BusinessException;
	
	public void updateShutDown(MelChangeSheet melChangeSheet) throws BusinessException;
	
	public List<MelChangeSheet> queryAllPageList(MelChangeSheet melChangeSheet);
	
	public MelChangeSheet selectById(String id);
	
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017-8-30 下午4:25:34
	 * @CreateBy 孙霁
	 * @param idList
	 * @param yj
	 * @throws BusinessException
	 */
	public String updateBatchAudit(List<String> idList, String yj) throws BusinessException;
	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017-8-30 下午4:25:34
	 * @CreateBy 孙霁
	 * @param idList
	 * @param yj
	 * @throws BusinessException
	 */
	public String updateBatchApprove(List<String> idList, String yj) throws BusinessException;
	
}
