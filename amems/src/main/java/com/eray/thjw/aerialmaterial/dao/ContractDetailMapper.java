package com.eray.thjw.aerialmaterial.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.ContractDetail;
import com.eray.thjw.po.BaseEntity;

public interface ContractDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractDetail record);

    int insertSelective(ContractDetail record);

    ContractDetail selectByPrimaryKey(String id);
    
    /**
     * 根据入库航材ID查询对应合同明细
     * @param instockDetailId
     * @return
     */
    ContractDetail selectByInstockDetailId(String instockDetailId);

    int updateByPrimaryKeySelective(ContractDetail record);

    int updateByPrimaryKey(ContractDetail record);
    
    /**
	 * 按条件查询一页库存
	 * @param param
	 * @param pagination
	 * @return
	 */
	 List<ContractDetail> queryAllPageList(ContractDetail contractDetail)  throws RuntimeException;
	
	 List<ContractDetail> queryList(ContractDetail contractDetail) throws RuntimeException;
	 
	 /**
	  * 根据bjid查询航材采购合同价格历史记录
	  * @param dprtcode 组织机构ID
	  * @param bjid 部件ID
	  * @author xu.yong
	  * @return
	  */
	 List<ContractDetail> selectCostHisByBjidPage(BaseEntity entity);
	 
	 /**
	 * @author liub
	 * @description  根据mainId修改状态为无效 
	 * @param mainId
	 * @return
	 * @develop date 2016.11.09
	 */
	public void cancelByMainId(String mainId);
	 
	 /**
	 * @author liub
	 * @description  根据合同id查询提订合同详情信息
	 * @param mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	public List<Map<String, Object>> queryReserveContractDetailList(String mainid);
		
	/**
	 * @author liub
	 * @description  根据合同id查询送修合同详情信息
	 * @param mainid
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.08
	 */
	public List<Map<String, Object>> queryRepairContractMaterialList(String mainid);
	public ContractDetail queryKey(String id) throws RuntimeException;
	
	/**
	 * @author liub
	 * @description  根据idList查询合同详情信息
	 * @param mainid
	 * @return List<ContractDetail>
	 * @develop date 2016.11.10
	 */
	 List<ContractDetail> selectByIds(List<String> idList);
	 
	 /**
	  * 在原到货数量上增加到货数
	  * @param contractDetail
	  * @return
	  */
	 int updateDhslAdd(ContractDetail contractDetail);
	 /**
	  * 在原到货数量上减少到货数
	  * @param contractDetail
	  * @return
	  */
	 int updateDhslSub(ContractDetail contractDetail);
	 
}