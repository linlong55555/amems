package com.eray.thjw.material2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractInfo;

/**
 * @Description 合同明细service
 * @CreateTime 2017-8-19 下午2:57:09
 * @CreateBy 刘兵
 */
public interface ContractInfoService {
	
	/**
	 * @Description 保存多个合同明细
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param contractInfoList 合同明细集合
	 * @param mainid 父类ID
	 */
	void saveContractInfoList(List<ContractInfo> contractInfoList, String mainid);
	
	/**
	 * @Description 编辑多个合同明细
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param contractInfoList 合同明细集合
	 * @param mainid 父类ID
	 * @param isxd 是否修订
	 */
	void updateContractInfoList(List<ContractInfo> contractInfoList, String mainid, boolean isxd);
	
	/**
	 * @Description 根据mainid删除合同明细
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 */
	void deleteByMainid(String mainid);
	
	/**
	 * @Description 根据条件查询合同明细列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param contractInfo 合同明细
	 * @return List<ContractInfo> 合同明细集合
	 */
	List<ContractInfo> queryAllList(ContractInfo contractInfo);
	
	/**
	 * 
	 * @Description 查询所有在途数量
	 * @CreateTime 2018-3-14 下午3:22:02
	 * @CreateBy 孙霁
	 * @param contractInfo
	 * @return
	 */
	Map<String, Object> queryAll(ContractInfo contractInfo);
	
	/**
	 * @Description 根据查询条件分页查询合同明细信息(弹窗)
	 * @CreateTime 2018-3-15 上午11:48:48
	 * @CreateBy 刘兵
	 * @param contractInfo 合同明细
	 * @return List<ContractInfo> 合同明细集合
	 */
	List<ContractInfo> queryWinAllPageList(ContractInfo contractInfo);
	
	/**
	 * @Description 根据合同id查询合同明细(带库存)
	 * @CreateTime 2018-3-15 上午10:44:33
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return List<ContractInfo> 合同明细集合
	 */
	List<ContractInfo> queryDetailByMainid(String mainid);

	/**
	 * @Description 根据需求清单id集合查询合同明细 
	 * @CreateTime 2018-4-2 下午2:46:51
	 * @CreateBy 刘兵
	 * @param xqqdIdList
	 * @return List<ContractInfo>
	 */
	List<ContractInfo> queryByXqqdIdList(List<String> xqqdIdList);

	/**
	 * 
	 * @Description 出库-合同明细信息
	 * @CreateTime 2018年3月20日 上午9:24:17
	 * @CreateBy 林龙
	 * @param contractInfo
	 * @return
	 * @throws BusinessException
	 */
	List<ContractInfo> queryContractdetailsList(ContractInfo contractInfo)throws BusinessException;
	
}
