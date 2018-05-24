package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.HcTaskInfo;
import com.eray.thjw.aerialmaterial.po.HcTaskRelOrder;
import com.eray.thjw.produce.po.InstallationListEditable;


public interface HCMainDataMapper {
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询航材信息(弹窗需要的数据)
	 * @param record
	 * @return List<HCMainData>
	 * @develop date 2016.10.11
	 */
	public List<HCMainData> queryWinAllPageList(HCMainData record);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询航材信息
	 * @param record
	 * @return List<HCMainData>
	 * @develop date 2016.09.12
	 */
	public List<HCMainData> queryAllPageList(HCMainData record);
	
	/**
	 * @author liub
	 * @description 检查航材是否存在
	 * @param record
	 * @return List<HCMainData>
	 * @develop date 2016.09.20
	 */
	List<HCMainData> checkMaterial(HCMainData record);
	
	/**
	 * @author liub
	 * @description 检查航材是否可以修改
	 * @param id
	 * @return HCMainData
	 */
	HCMainData checkUptById(String id);
	
	int insertSelective(HCMainData record);
	
	HCMainData selectById(String id);
	
	int updateByPrimaryKeySelective(HCMainData record);
	
	/**
	 * @author liub
	 * @description 修改部件基本信息
	 * @param record
	 * @return int
	 */
	int updateBasicInformationById(HCMainData record);
	
	/**
	 * @author liub
	 * @description 修改部件生产信息
	 * @param record
	 * @return int
	 */
	int updateProductionInformationById(HCMainData record);
	
/*    int deleteByPrimaryKey(String id);

    int insert(HCMainData record);

    */

    List< HCMainData> selectHCMainDataList(HCMainData  hcmaindata);  //根据部件号查出多条记录
   
    List<HCMainData>  selectHCList(HCMainData  hcmaindata);  //查询工单的航材耗材
	
    /**
     * 航材消耗件-主表数据
     * @param params
     * @return
     */
	public List<HCMainData> selectHcxhFir(HCMainData record);

	/**
     * 航材消耗件-任务列表
     * @param params
     * @return
     */
	public List<HcTaskInfo> selectHcxhSec(HCMainData record);

	/**
     * 航材消耗件-相关工单-主单列表
     * @param params
     * @return
     */
	public List<HcTaskRelOrder> selectHcxhThi(HCMainData record);

	/**
	 * 工具缺件统计-主表数据
	 * @param record
	 * @return
	 */
	public List<HCMainData> selectToolDemandFir(HCMainData record);

	/**
	 * 工具缺件统计-任务列表
	 * @param record
	 * @return
	 */
	public List<HcTaskInfo> selectToolDemandSec(HCMainData record);

	/**
	 * 工具缺件统计-相关工单-主单列表
	 * @param record
	 * @return
	 */
	public List<HcTaskRelOrder> selectToolDemandThi(HCMainData record);
    
	/**
	 * 查询航材成本列表
	 * @param record
	 * @return
	 */
    List<HCMainData> selectMaterialCostList(HCMainData record);        
	
	/**
	 * @author liub
	 * @description  根据盘点id、关键字查询航材信息
	 * @param pdid,keyword
	 * @return List<HCMainData>
	 * @develop date 2016.11.24
	 */
	public List<HCMainData> queryMaterialListByPdid(@Param("pdid")String pdid,@Param("keyword")String keyword);
	
	/**
	 * 根据部件ID、部件号查询
	 * @param map
	 * @return
	 */
	public List<HCMainData> selectBybjs(Map<String, Object> map);
	
	/**
	 * 查询所有航材主数据
	 * @param dprtcode
	 * @return
	 */
	public List<HCMainData> selectAllByDprtcode(String dprtcode);

	public HCMainData selectByPrimaryHCMainData(String ckh, String jgdm);
	
	void updateByBjhDprtcode(HCMainData record);
	
	/**
	 * @author liub
	 * @description  作废部件
	 * @param hCMainData
	 * @return int
	 * @develop date 2016.11.24
	 */
	int cancel(HCMainData hCMainData);
	
	/**
	 * 根据件号和组织机构查询
	 * @param paramMap
	 * @return
	 */
	public HCMainData selectByBjhAndDprt(Map<String, Object> paramMap);

	public List<HCMainData> queryAllPageLists(HCMainData hCMainData);

	/**
	 * 修改装机清单时，同步航材主数据
	 * @param record
	 * @return
	 */
	int updateByLoadingList(HCMainData record);
	
	List<HCMainData> getHcByDprtcode(String dprtcode);
	
	/**
	 * @author liub
	 * @description 根据部件号、机构代码查询耗材
	 * @param hcmaindata
	 * @return List<HCMainData>
	 */
	List<HCMainData> selectBjhAndDprt(HCMainData hcmaindata);
	
	/**
	 * @author liubing
	 * @description  根据条件查询航材信息(弹窗)含库存数量
	 * @param hcmaindata
	 * @return List<HCMainData>
	 * @develop date 2017.08.09
	 */
	List<HCMainData> selectWinList(HCMainData hcmaindata);
	/**
	 * 
	 * @Description 获取部件数据
	 * @CreateTime 2017年8月22日 下午6:47:02
	 * @CreateBy 岳彬彬
	 * @param hcmaindata
	 * @return
	 */
	List<HCMainData> selectPartList(HCMainData hcmaindata);
	
	/**
	 * @Description 根据组织机构获取部件前十条数据
	 * @CreateTime 2017年8月31日 下午1:57:27
	 * @CreateBy 韩武
	 * @param hcmaindata
	 * @return
	 */
	List<HCMainData> queryLimitTen(HCMainData hcmaindata);
	/**
	 * 
	 * @Description 根据bjh 和 dprtcode 查询数据
	 * @CreateTime 2017-9-27 上午11:38:42
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	HCMainData selectByBjhAndDprcode(HCMainData hcmaindata);
	
	/**
	 * @Description 装机清单更新部件主数据
	 * @CreateTime 2017年10月10日 上午11:38:31
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateByInstallationList(InstallationListEditable record);
	
	/**
	 * 
	 * @Description 根据条件查询航材证书列表
	 * @CreateTime 2017-11-9 下午3:52:43
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	List<HCMainData> queryAllCertificate (HCMainData hcmaindata); 
	
	/**
	 * 
	 * @Description 根据条件查询单个数据和证书列表
	 * @CreateTime 2017-11-13 上午10:54:28
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	HCMainData selectByBjhAndXlh(HCMainData hcmaindata);
	
	/**
	 * 
	 * @Description 根据部件号查询所有数据
	 * @CreateTime 2018-1-29 上午9:56:51
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return
	 */
	HCMainData selectByBjh(HCMainData hcmaindata);
	
}