package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.Annunciate;
import com.eray.thjw.po.TechnicalFile;
import com.eray.thjw.po.User;

public interface AnnunciateService {

	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Annunciate> queryAll(Annunciate annunciate);
	/**
	 * @author sunji
	 * @description 添加
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public String insertAnnunciate(Annunciate annunciate)throws BusinessException;
	/**
	 * @author sunji
	 * @description 检查技术通告修改权限
	 * @param user,id
	 * @return Map<String, Object>
	 * @develop date 2016.08.17
	 */
	public Map<String, Object> checkUpdMt(User user,String id);
	
	/**
	 * @author sunji
	 * @description 根据主键查询技术通告信息并判断是否为未提交或者已提交
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public Annunciate getByPrimaryKey(String id);
	/**
	 * @author sunji
	 * @description 根据主键查询已选择的评估单号
	 * @param id
	 * @return Maintenance
	 * @develop date 2016.08.15
	 */
	public List<TechnicalFile> selectChoosePgd(String id);
	/**
	 * @author sunji
	 * @description 修改提交后的技术通告
	 * @param maintenance
	 * @return int
	 * @develop date 2016.08.15
	 */
	public Map<String, Object> updateAnnunciate(Map<String, Object> map);
	
	/**
	 * @author sunji
	 * @description  作废
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> deleteAnnunciate (User user,String ids) throws Exception;
	
	/**
	 * @author sunji
	 * @description  关闭
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public Map<String, Object> offAnnunciate (User user,Annunciate annunciate) throws Exception;
	/**
	 * @author sunji
	 * @description  修改保存状态的信息（提交）
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void modifyAnnunciate(Annunciate annunciate,int []i)throws BusinessException;
	/**
	 * @author sunji
	 * @description  审核提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updateshenheAnnunciate(Annunciate annunciate)throws BusinessException;
	/**
	 * @author sunji
	 * @description  批复提交
	 * @param ids
	 * @return Map<String, Object>
	 * @develop date 2016.08.18
	 */
	public void updatepifuAnnunciate(Annunciate annunciate)throws BusinessException;
	/**
	 * @author sunji
	 * @description  查询当前时间
	 * @param ids
	 * @return String
	 * @develop date 2016.09.24
	 */
	/*public String selectDate();*/
	/**
	 * @author sunji
	 * @description  查询当前时间
	 * @param ids
	 * @return String
	 * @develop date 2016.09.24
	 */
	public Annunciate selectByNum(String num)throws BusinessException;
	/**
	 * @author sunji
	 * @description 查询组织机构下的所有数据
	 * @develop date 2016.08.15
	 */
	public List<Annunciate> queryAll(Map <String,Object> map);
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
	 * @description 通过评估单id查询技术通告
	 * @param pgdid
	 * @develop date 2017.03.14
	*/
	public List<Annunciate> queryByPgdId(String pgdid);
	/**
	 * sunji
	 * 查询列表
	 * @param annunciate
	 * @return map
	 * @throws BusinessException
	 */
	public Map<String , Object> queryAllAnnunciate(Annunciate annunciate)throws BusinessException;
}
