package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.dao.ContractInfoMapper;
import com.eray.thjw.material2.po.ContractInfo;
import com.eray.thjw.material2.service.ContractInfoService;
import com.eray.thjw.project2.dao.MaterialToolMapper;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.github.pagehelper.PageHelper;

import enu.common.EffectiveEnum;

/**
 * @Description 合同明细service实现类
 * @CreateTime 2017-8-19 下午2:58:02
 * @CreateBy 刘兵
 */
@Service
public class ContractInfoServiceImpl implements ContractInfoService{
	
	@Resource
	private MaterialToolMapper materialToolMapper;
	
	@Resource
	private ContractInfoMapper contractInfoMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存多个合同明细
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param contractInfoList 合同明细集合
	 * @param mainid 父类ID
	 */
	@Override
	public void saveContractInfoList(List<ContractInfo> contractInfoList, String mainid){
		if(null != contractInfoList && contractInfoList.size() > 0){
			for (ContractInfo contractInfo : contractInfoList) {
				String id = UUID.randomUUID().toString();
				contractInfo.setId(id);
				contractInfo.setMainid(mainid);
				contractInfo.setZt(EffectiveEnum.YES.getId());
			}
			contractInfoMapper.insert4Batch(contractInfoList);
		}
	}
	
	/**
	 * @Description 编辑多个合同明细
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param contractInfoList 合同明细集合
	 * @param mainid 父类ID
	 * @param isxd 是否修订
	 */
	@Override
	public void updateContractInfoList(List<ContractInfo> contractInfoList, String mainid, boolean isxd){
		List<ContractInfo> insertContractInfoList = null;//合同明细集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入合同明细id集合
		//根据mainid获取数据库已经存在的合同明细
		List<ContractInfo> existsContractInfoList = contractInfoMapper.queryByMainid(mainid);
		//合同明细map集合,key:id,contractInfo
		Map<String, ContractInfo> contractInfoMap = new HashMap<String, ContractInfo>();
		//将数据库已存在的合同明细放入workContentMap
		for (ContractInfo contractInfo : existsContractInfoList) {
			contractInfoMap.put(contractInfo.getId(), contractInfo);
		}
		if(null != contractInfoList && contractInfoList.size() > 0){
			insertContractInfoList = new ArrayList<ContractInfo>();
			for (ContractInfo contractInfo : contractInfoList) {
				//判断合同明细id是否为空,是否存在于数据库,不为空且存在:修改合同明细,反之:新增合同明细
				if(null != contractInfo.getId() && null != contractInfoMap.get(contractInfo.getId())){
					idMap.put(contractInfo.getId(), contractInfo.getId());
					//修改合同明细
					contractInfoMapper.updateByPrimaryKeySelective(contractInfo);
				}else{
					insertContractInfoList.add(contractInfo);
				}
			}
			//保存多个合同明细
			saveContractInfoList(insertContractInfoList, mainid);
		}
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量删除合同明细
		for (ContractInfo contractInfo : existsContractInfoList){
			//如果数据库合同明细id不在页面,那么删除
			if(null == idMap.get(contractInfo.getId())){
				columnValueList.add(contractInfo.getId());
			}
		}
		if(columnValueList.size() > 0){
			//批量删除合同明细
			if(isxd){
				contractInfoMapper.cancel4Batch(columnValueList);
			}else{
				contractInfoMapper.delete4Batch(columnValueList);
			}
			
		}
	}
	
	/**
	 * @Description 根据mainid删除合同明细
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param mainid 父表id
	 */
	@Override
	public void deleteByMainid(String mainid){
		contractInfoMapper.deleteByMainid(mainid);
	}
	
	/**
	 * @Description 根据条件查询合同明细列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param materialTool 合同明细
	 * @return List<MaterialTool> 合同明细集合
	 */
	@Override
	public List<ContractInfo> queryAllList(ContractInfo contractInfo){
		return contractInfoMapper.queryAllList(contractInfo);
	}
	
	/**
	 * @Description 根据查询条件分页查询合同明细信息(弹窗)
	 * @CreateTime 2018-3-15 上午11:48:48
	 * @CreateBy 刘兵
	 * @param contractInfo 合同明细
	 * @return List<ContractInfo> 合同明细集合
	 */
	@Override
	public List<ContractInfo> queryWinAllPageList(ContractInfo contractInfo){
		return contractInfoMapper.queryWinAllPageList(contractInfo);
	}
	
	/**
	 * @Description 根据合同id查询合同明细(带库存)
	 * @CreateTime 2018-3-15 上午10:44:33
	 * @CreateBy 刘兵
	 * @param mainid 合同id
	 * @return List<ContractInfo> 合同明细集合
	 */
	@Override
	public List<ContractInfo> queryDetailByMainid(String mainid){
		return contractInfoMapper.queryDetailByMainid(mainid);
	}
	
	/**
	 * @Description 根据需求清单id集合查询合同明细 
	 * @CreateTime 2018-4-2 下午2:46:51
	 * @CreateBy 刘兵
	 * @param xqqdIdList
	 * @return List<ContractInfo>
	 */
	@Override
	public List<ContractInfo> queryByXqqdIdList(List<String> xqqdIdList){
		return contractInfoMapper.queryByXqqdIdList(xqqdIdList);
	}

	/**
	 * 
	 * @Description 查询所有在途数量
	 * @CreateTime 2018-3-14 下午3:22:02
	 * @CreateBy 孙霁
	 * @param contractInfo
	 * @return
	 */
	@Override
	public Map<String, Object> queryAll(ContractInfo contractInfo) {
		PageHelper.startPage(contractInfo.getPagination());
		List<ContractInfo> list = contractInfoMapper.queryAll(contractInfo);
		return PageUtil.pack4PageHelper(list, contractInfo.getPagination());
	}

	@Override
	public List<ContractInfo> queryContractdetailsList(ContractInfo contractInfo)throws BusinessException {
		return contractInfoMapper.queryContractdetailsList(contractInfo);
	}
}
