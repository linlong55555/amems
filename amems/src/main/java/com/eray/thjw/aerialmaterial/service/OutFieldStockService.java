package com.eray.thjw.aerialmaterial.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.eray.thjw.aerialmaterial.po.OutFieldStock;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.ContractInfo;


public interface OutFieldStockService {

	void insert(OutFieldStock outFieldStock);
	
	/**
	 * 根据条件查询外场库存
	 * @param outFieldStock
	 * @return
	 */
	Map<String, Object> queryPageByParam(OutFieldStock outFieldStock);
	/**
	 * @author meizhiliang
	 * @param outFieldStock
	 * @return 查询外场库存记录
	 */
	OutFieldStock queryById(String id) throws RuntimeException;
	/**
	 * @author meizhiliang
	 * @param outFieldStock
	 * @return 更新外场库存记录
	 */
	int updateByPrimaryKeySelective(OutFieldStock record);   
	/**
	 * @author meizhiliang
	 * @param outFieldStock
	 * @return 更新外场库存记录
	 */
	int insertSelective(OutFieldStock record);            
	/**
	 * @author meizhiliang
	 * @param outFieldStock
	 * @return 更新外场库存记录
	 */
	OutFieldStock queryByKey(String id) throws RuntimeException;

	void delete(String id) throws RuntimeException;
	
	void update(OutFieldStock outFieldStock1) throws RuntimeException;
	
	OutFieldStock queryByParamKey(OutFieldStock outFieldStock) throws RuntimeException;

	List<OutFieldStock> queryAllPageList(OutFieldStock outFieldStock)throws RuntimeException;

	Map<String, Object> remove(OutFieldStock outFieldStock)throws RuntimeException;

	Map<String, Object> senda(OutFieldStock outFieldStock)throws RuntimeException;

	Map<String, Object> add(OutFieldStock outFieldStock)throws RuntimeException;

	void checkEdit(String id)throws RuntimeException;

	/**
	 * @Description 装上件时 更新外场库存
	 * @CreateTime 2017年10月31日 下午1:40:51
	 * @CreateBy 徐勇
	 * @param dprtcode 组织机构
	 * @param bjh 部件号
	 * @param xlh 序列号
	 * @param pch 批次号
	 * @param azsl 安装数量
	 * @return //0：库存id, 1:库存履历
	 */
	public String[] update4InstallComponent(String dprtcode, String bjh, String xlh, String pch, BigDecimal azsl);

	/**
	 * 
	 * @Description 获取库外列表
	 * @CreateTime 2018-3-12 下午4:22:24
	 * @CreateBy 孙霁
	 * @param outFieldStock
	 * @return
	 */
	 Map<String, Object> queryAll(OutFieldStock outFieldStock);
	 
	 /**
	  * 
	  * @Description 外场部件清单导出
	  * @CreateTime 2018-3-15 上午10:59:08
	  * @CreateBy 孙霁
	  * @param outFieldStock
	  * @return
	  */
	 List<OutFieldStock> outfieldExportExcel(OutFieldStock outFieldStock);
	 /**
	  * 
	  * @Description 在途部件清单导出
	  * @CreateTime 2018-3-15 上午10:59:08
	  * @CreateBy 孙霁
	  * @param outFieldStock
	  * @return
	  */
	 List<ContractInfo> contractInfoExportExcel(ContractInfo contractInfo);
	 
	 /**
	  * @Description 
	  * @CreateTime 2018-3-19 上午10:44:33
	  * @CreateBy 孙霁
	  * @param id
	  * @return
	  */
	 OutFieldStock selectById(String id)throws BusinessException;
	 
	 /**
	  * 
	  * @Description 根据id查询库存信息
	  * @CreateTime 2018-3-20 下午2:56:13
	  * @CreateBy 孙霁
	  * @param id
	  * @return
	  * @throws BusinessException
	  */
	 Map<String, Object> selectKcxqById(String id)throws BusinessException;
	 /**
	  * 
	  * @Description 修改外场库存数据
	  * @CreateTime 2018-3-21 上午11:28:52
	  * @CreateBy 孙霁
	  * @param outFieldStock
	  * @throws BusinessException
	  */
	 void updateByWc(OutFieldStock outFieldStock)throws BusinessException;

	OutFieldStock queryByKeywc(String kcid);

	/**
	 * 
	 * @Description 根据检验单id查询外场库存
	 * @CreateTime 2018年3月27日 下午3:17:21
	 * @CreateBy 林龙
	 * @param id
	 * @return
	 */
	List<OutFieldStock> queryjydid(String id);
}
