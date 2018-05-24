package com.eray.thjw.training.dao;

import java.util.List;

import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.training.po.PlanPerson;

public interface PlanPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(PlanPerson record);

    int insertSelective(PlanPerson record);

    PlanPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PlanPerson record);

    int updateByPrimaryKey(PlanPerson record);
    
    /**
	 * @author liub
	 * @description 根据id作废
	 * @param id
	 * @return int
	 */
    int deleteById(String id);
    
    /**
	 * @author liub
	 * @description 根据id培训计划id查询
	 * @param pxjhid
	 * @return List<PlanPerson>
	 */
    List<PlanPerson> queryByPxjhId(String pxjhid);
    /**
	 * @author sunji
	 * @description 查询维修人员培训跟踪
	 * @param PlanPerson
	 * @return List<PlanPerson>
	 */
    List<PlanPerson> qeryAllPageList(PlanPerson planPerson);

	List<PlanPerson> queryAllPageplanPersonList(PlanPerson planPerson);
	
	/**
     * 删除列表中不存在的数据
     * @param personnel
     * @return
     */
    int deleteNotExist(MaintenancePersonnel personnel);
    
    /**
     * 根据维修人员档案查询对应的培训记录
     * @param id
     * @return
     */
    List<PlanPerson> queryByWxrydaid(String id);

	List<PlanPerson> queryAllPagerecordsList(PlanPerson planPerson);

	List<PlanPerson> queryByPxjhscId(String pxjhid);

	List<PlanPerson> selectpxjhid(String id);

	PlanPerson selectMAXpxjhid(String pxjhid);

	/**
	 * //根据程id，计划id，维修人员id查询培训计划-培训课程人员表数据  状态为1 实参与标识为1 获取最大实际开始日期的那条数据
	 * @param kcid
	 * @param wxrydaid
	 * @return
	 */
	PlanPerson selectMaxDate(String kcid, String wxrydaid);

	/**
	 * 根据计划id查询实参标识1的数据
	 * @param id
	 * @return
	 */
	List<PlanPerson> queryByPxjhsc(String id);
	
	/**
	 * @Description 根据组织机构查询
	 * @CreateTime 2018年1月12日 上午11:35:11
	 * @CreateBy 韩武
	 * @param dprtcode
	 * @return
	 */
	List<PlanPerson> queryByDprtcode(String dprtcode);
	
	/**
	 * @Description 维修人员档案更新培训记录
	 * @CreateTime 2018年1月19日 上午11:14:04
	 * @CreateBy 韩武
	 * @param record
	 * @return
	 */
	int updateByWxryda(PlanPerson record);
	
	
	/**
	 * @param list
	 * @return
	 */
	int batchInsert(List<PlanPerson> list);
	
	/**
	 * 
	 * @Description 根据课程编号和人员id查询数据
	 * @CreateTime 2018-3-28 下午3:31:15
	 * @CreateBy 孙霁
	 * @param trainingPlan
	 * @return
	 */
	List<PlanPerson> queryAllByBhAndRy(PlanPerson planPerson);

	List<PlanPerson> selectMaxDateList(String id, String dprtcode);

}