package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.material2.po.Demand;
import com.eray.thjw.material2.po.DemandDetails;
/**
 * 
 * @Description 需求明细mapper
 * @CreateTime 2018年2月26日 上午10:03:54
 * @CreateBy 林龙
 */
public interface DemandDetailsMapper {
    int deleteByPrimaryKey(String id);

    int insert(DemandDetails record);

    int insertSelective(DemandDetails record);

    DemandDetails selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DemandDetails record);

    void updateByPrimaryKey(DemandDetails record);

	List<DemandDetails> queryAllPageList(DemandDetails demandDetails);

	void deleteByPrimaryMainid(String id);

	List<DemandDetails> selectByPrimaryMainid(String id);

	int getCurrentZt4Validation(String id);
    
    /**
     * @Description 删除不存在的需求明细
     * @CreateTime 2018年2月27日 下午1:37:49
     * @CreateBy 韩武
     * @param demand
     * @return
     */
    int deleteNotExist(Demand demand);
    
    /**
     * @Description 获取删除的id集合
     * @CreateTime 2018年2月27日 下午4:50:20
     * @CreateBy 韩武
     * @param demand
     * @return
     */
    List<String> getDeleteList(Demand demand);

	List<DemandDetails> queryDemandProtectionInfoList(DemandDetails demandDetails);
	
	/**
	 * @Description 查询部件的详细数据
	 * @CreateTime 2018年4月12日 上午9:46:36
	 * @CreateBy 韩武
	 * @param demand
	 * @return
	 */
	List<DemandDetails> queryPartListDetail(Demand demand);
}