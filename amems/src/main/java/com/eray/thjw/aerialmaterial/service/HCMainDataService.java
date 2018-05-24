package com.eray.thjw.aerialmaterial.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.po.MaterialToStore;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.MountLoadingList;
import com.eray.thjw.productionplan.po.LoadingList;


public interface HCMainDataService {
	
	
	/**
	 * @author liub
	 * @description 增加航材
	 * @param hcMainData
	 * @develop date 2016.09.18
	 * @throws BusinessException 
	 */
	public String add(HCMainData hcMainData) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 修改航材
	 * @param hcMainData
	 * @develop date 2016.09.19
	 * @throws BusinessException 
	 */
	public void edit(HCMainData hcMainData) throws BusinessException;
	
	/**
	 * @author liub
	 * @description  作废
	 * @param ids
	 * @return
	 * @develop date 2016.09.19
	 */
	public void cancel(String id) throws BusinessException;
	
	/**
	 * @author liub
	 * @description 根据主键id查询航材信息
	 * @param id
	 * @return HCMainData
	 */
	public HCMainData selectById(String id);
	
	/**
	 * @author liub
	 * @description 检查航材类型是否可以修改(如果存在则不能修改)
	 * @param id
	 * @return HCMainData
	 */
	public HCMainData checkUptById(String id);
	
	 List< HCMainData> selectHCMainDataList(HCMainData  hcmaindata);  //根据部件号查出多条记录
	   
	/**
	 * @author liub
	 * @description 根据查询条件分页查询航材信息(弹窗需要的数据)
	 * @param hcMainData
	 * @return List<HCMainData>
	 * @develop date 2016.10.11
	 */
	public List<HCMainData> queryWinAllPageList(HCMainData hcMainData);
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询航材信息
	 * @param hcMainData
	 * @return List<HCMainData>
	 * @develop date 2016.09.12
	 */
	public List<HCMainData> queryAllPageList(HCMainData hcMainData);
	
	/**
	 * @author liub
	 * @description  根据航材id查询航材仓库关系
	 * @param mainId
	 * @return MaterialToStore
	 * @develop date 2016.07.25
	 */
	public MaterialToStore getMaterialStoreByMainId(String mainId);
	
	/**
	 * @author liub
	 * @description  根据盘点id、关键字查询航材信息
	 * @param pdid,keyword
	 * @return List<HCMainData>
	 * @develop date 2016.11.24
	 */
	public List<HCMainData> queryMaterialListByPdid(String pdid,String keyword);
    
	/**
	 * @author meizhiliang
	 * @description  查询航材信息
	 * @param mainId
	 * @return MaterialToStore
	 * @develop date 2016.07.25
	 */
	public Map<String, Object> selectHCList(HCMainData  hcmaindata);//查询工单的航材耗材
	
	/**
	 * 查询航材成本列表
	 * meizhiliang
	 * @param record
	 * @return
	 */
	List<HCMainData> selectMaterialCostList(HCMainData record);

	/**
	 * 根据件号和组织机构查询
	 * @param bjh
	 * @return
	 */
	HCMainData selectByBjhAndDprt(HCMainData data);

	public List<HCMainData> queryAllPageLists(HCMainData hCMainData);

	/**
	 * 修改装机清单时，同步航材主数据
	 * @param ll
	 * @return
	 */
	int updateByLoadingList(LoadingList ll, String czls);
	
	/**
	 * 修改装机清单时，同步航材主数据
	 * @param ll
	 * @return
	 */
	int updateByLoadingList(MountLoadingList ll, String czls);

	/**
	 * @author liub
	 * @description 根据部件号、机构代码查询耗材
	 * @param hcmaindata
	 * @return List<HCMainData>
	 */
	public List<HCMainData> selectBjhAndDprt(HCMainData hcmaindata);
	
	/**
	 * @author liubing
	 * @description  根据条件查询航材信息(弹窗)含库存数量
	 * @param hcmaindata
	 * @return List<HCMainData>
	 * @develop date 2017.08.09
	 */
	public List<HCMainData> selectWinList(HCMainData hcmaindata);
	/**
	 * 
	 * @Description 获取部件
	 * @CreateTime 2017年8月24日 上午10:47:42
	 * @CreateBy 岳彬彬
	 * @param hcmaindata
	 * @return
	 */
	public List<HCMainData> selectPartList(HCMainData hcmaindata);
	
	/**
	 * @Description 根据组织机构获取部件前十条数据
	 * @CreateTime 2017年8月31日 下午1:44:53
	 * @CreateBy 韩武
	 * @param dprtcode
	 * @return
	 */
	List<HCMainData> queryLimitTen(HCMainData hcmaindata);
	/**
	 * 
	 * @Description 根据bjh 和 dprtcode 查询数据
	 * @CreateTime 2017-9-27 上午11:38:42
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return HCMainData
	 */
	HCMainData selectByBjhAndDprcode(HCMainData hcmaindata);
	
	/**
	 * 
	 * @Description 根据条件查询航材证书列表
	 * @CreateTime 2017-11-9 下午3:54:45
	 * @CreateBy 孙霁
	 * @param hcmaindata
	 * @return Map<String, Object>
	 */
	Map<String, Object> queryAllCertificate(HCMainData hcmaindata);
	
	/**
	 * 
	 * @Description 根据部件号 和序列号和组织机构查询数据
	 * @CreateTime 2017-11-10 下午2:28:44
	 * @CreateBy 孙霁
	 * @param hCMainData
	 * @return HCMainData
	 */
	HCMainData selectByBjhAndXlh(HCMainData hCMainData) throws BusinessException ;
	/**
	 * 
	 * @Description 部件主数据导出
	 * @CreateTime 2017年12月4日 下午5:34:00
	 * @CreateBy 岳彬彬
	 * @param hcMainData
	 * @param whrq
	 * @return
	 */
	List<HCMainData> doHcExportList(HCMainData hcMainData,String whrq);
	
}
