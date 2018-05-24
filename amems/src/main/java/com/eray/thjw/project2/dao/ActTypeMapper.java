package com.eray.thjw.project2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.ActType;

/**
 * 
 * @Description 机型Mapper
 * @CreateTime 2017年8月14日 上午10:30:28
 * @CreateBy 林龙
 */
public interface ActTypeMapper {
	
	/**
	 * @Description 根据对象新增机型对象
	 * @CreateTime 2017年8月14日 上午10:40:34
	 * @CreateBy 林龙
	 * @param actType
	 * @throws BusinessException
	 */
    void insertSelective(ActType actType);
	
	/**
	 * @Description 根据机型和组织机构修改状态
	 * @CreateTime 2017年8月14日 上午10:39:57
	 * @CreateBy 林龙
	 * @param actType
	 */
	void updateByPrimaryKey(ActType actType);

	/**
	 * @Description 根据对象修改机型
	 * @CreateTime 2017年8月14日 上午10:41:03
	 * @CreateBy 林龙
	 * @param 
	 * @throws BusinessException
	 */
	void updateByPrimaryKeySelective(ActType actType);
	
	/**
	 * @Description 根据飞机机型和组织机构查询机型数据
	 * @CreateTime 2017年8月14日 上午10:44:28
	 * @CreateBy 林龙
	 * @param key
	 * @return 机型对象
	 * @throws BusinessException
	 */
    ActType selectByPrimaryKey(ActType key);

    /**
     * @Description 根据机型对象查询机型分页数据
     * @CreateTime 2017年8月14日 上午10:45:17
     * @CreateBy 林龙
     * @param actType
     * @return 机型List集合
     * @throws BusinessException
     */
	List<ActType> queryAllPageList(ActType actType);

	/**
	 * @Description 根据机型和组织机构 查询飞机注册号的数量，状态为1（有效）
	 * @CreateTime 2017年8月15日 上午9:41:35
	 * @CreateBy 林龙
	 * @param actType
	 * @return 数量
	 * @throws BusinessException
	 */
	int selectFjzchCount(ActType actType);
	
	/**
	 * @Description 根据机型和组织机构查询机型数量
	 * @CreateTime 2017年8月15日 下午9:32:55
	 * @CreateBy 林龙
	 * @param actType
	 * @return 数量
	 */
	int selectActTypeCount(ActType actType);

	/**
	 * 
	 * @Description 验证当前状态是否是1
	 * @CreateTime 2017年8月17日 下午2:33:19
	 * @CreateBy 林龙
	 * @param actType
	 * @return
	 */
	int selectActTypeStuts(ActType actType);
	/**
	 * 
	 * @Description 根据组织机构查询机型
	 * @CreateTime 2017年11月8日 上午11:40:52
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @return
	 */
	List<ActType> findByDprtcode(String dprtcode);

	List<ActType> findAllPlaneModelData(ActType actType1);

	/**
	 * @Description 根据机构代码、状态=1 查询d_004 机型数据
	 * @CreateTime 2017-12-8 下午2:25:05
	 * @CreateBy 雷伟
	 * @param param
	 * @return
	 */
	List<ActType> selectByParam(ActType param);
}