package com.eray.thjw.aerialmaterial.dao;

import java.math.BigDecimal;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.OutstockDetail;

public interface OutstockDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(OutstockDetail record);

    int insertSelective(OutstockDetail record);

    OutstockDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OutstockDetail record);

    int updateByPrimaryKey(OutstockDetail record);
    
    public List<OutstockDetail> queryKey(String id) throws RuntimeException;
    
    /**
	 * @author liub
	 * @description  根据提订单id查询外派清单id
	 * @param String
	 * @return String
	 * @develop date 2016.11.10
	 */
	public String getWpqdIdByRepairId(String repairId);
	
	public List<OutstockDetail> queryKeyList(String id) throws RuntimeException ;

	OutstockDetail queryReserveDetailListByMainId(String ckdh)throws RuntimeException;

	/**
	 * 查询出库明细记录
	 * @param record
	 * @return
	 */
	List<OutstockDetail> queryPageList(OutstockDetail record);
	
	/**
	 * @Description 根据kcid统计已报废出库的数量
	 * @CreateTime 2018年1月10日 下午1:37:37
	 * @CreateBy 韩武
	 * @param kcid
	 * @return
	 */
	BigDecimal sumCountByScrap(String kcid);
}