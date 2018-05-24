package com.eray.thjw.project2.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.WorkCard;

/**
 * @Description 工卡Mapper
 * @CreateTime 2017-8-14 下午4:08:11
 * @CreateBy 刘兵
 */
public interface WorkCardMapper {

	int deleteByPrimaryKey(String id);

	int insert(WorkCard record);

	int insertSelective(WorkCard record);

	WorkCard selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(WorkCard record);
	
	/**
	 * @Description 根据id修改工卡(修改工卡专用)
	 * @CreateTime 2017-10-13 上午10:35:30
	 * @CreateBy 刘兵
	 * @param record
	 * @return int
	 */
	int updateById(WorkCard record);

	/**
	 * @Description 批量审核
	 * @CreateTime 2017-8-23 上午10:19:10
	 * @CreateBy 刘兵
	 * @param map
	 * @return int
	 */
	int updateBatchAudit(Map<String, Object> map);

	/**
	 * @Description 批量批准
	 * @CreateTime 2017-8-23 上午10:19:31
	 * @CreateBy 刘兵
	 * @param map
	 * @return int
	 */
	int updateBatchApprove(Map<String, Object> map);

	/**
	 * @Description 查询工卡数,检查工卡是否存在
	 * @CreateTime 2017-8-16 下午6:16:28
	 * @CreateBy 刘兵
	 * @param jx 机型
	 * @param gkh 工卡号
	 * @param dprtcode 机构代码
	 * @return int 工卡数
	 */
	int getCount4CheckExist(@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode);

	/**
	 * @Description 查询工卡集合,检查工卡是否存在(带维修项目)
	 * @CreateTime 2017-9-20 下午2:16:28
	 * @CreateBy 刘兵
	 * @param jx 机型
	 * @param gkh 工卡号
	 * @param dprtcode 机构代码
	 * @param wxxmbh 维修项目编号
	 * @return List<WorkCard> 工卡集合
	 */
	List<WorkCard> query4CheckExistWithWxxm(@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode, @Param("wxxmbh")String wxxmbh);

	/**
	 * @Description 工卡分页列表查询
	 * @CreateTime 2017-8-16 上午10:55:32
	 * @CreateBy 刘兵
	 * @param record 工卡
	 * @return List<WorkCard> 工卡集合
	 */
	List<WorkCard> queryAllPageList(WorkCard record);

	/**
	 * @Description 工卡分页列表查询(弹窗需要的数据)
	 * @CreateTime 2017-8-25 下午4:09:42
	 * @CreateBy 刘兵
	 * @param record 工卡
	 * @return List<WorkCard> 工卡集合
	 */
	List<WorkCard> queryWinAllPageList(WorkCard record);

	/**
	 * @Description 根据工卡id查询工卡
	 * @CreateTime 2017-8-17 下午5:23:17
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @return WorkCard 工卡
	 */
	WorkCard selectById(String id);

	/**
	 * @Description 根据id集合获取工卡
	 * @CreateTime 2017-8-23 上午10:01:59
	 * @CreateBy 刘兵
	 * @param idList 工卡id集合
	 * @return List<WorkCard> 工卡集合
	 */
	List<WorkCard> queryByIdList(List<String> idList);

	/**
	 * @Description 获取最大版本
	 * @CreateTime 2017-8-22 下午7:29:48
	 * @CreateBy 刘兵
	 * @param jx 机型
	 * @param gkh 工卡号
	 * @param dprtcode 机构代码
	 * @param id 工卡id
	 * @return BigDecimal 最大版本
	 */
	BigDecimal getMaxBb(@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode, @Param("id")String id);

	/**
	 * @Description 查询指定工卡的版本集合
	 * @CreateTime 2017年8月24日 下午4:00:37
	 * @CreateBy 韩武
	 * @param workCard
	 * @return
	 */
	List<WorkCard> queryVersionList(WorkCard workCard);

	/**
	 * @Description 根据工卡id查询当前工卡的历史版本集合
	 * @CreateTime 2017-8-28 下午5:43:59
	 * @CreateBy 刘兵
	 * @param id 工卡id
	 * @return List<WorkCard> 工卡集合
	 */
	List<WorkCard> queryHistoryListById(String id);

	/**
	 * @Description 工单135:获取工卡来源信息
	 * @CreateTime 2017-10-10 上午9:33:49
	 * @CreateBy 雷伟
	 * @param workCard 工卡
	 * @return Map<String, Object>
	 */
	List<WorkCard> queryOriginatingCardList(WorkCard workCard);
	 /**
	  * 
	  * @Description 根据机型、工卡号、组织机构获取工卡
	  * @CreateTime 2017年10月10日 上午11:56:24
	  * @CreateBy 岳彬彬
	  * @param jx
	  * @param gkh
	  * @param dprtcode
	  * @return
	  */
	 WorkCard  getWorkCardByjxAndGkbh(@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode);
	 /**
	  * 
	  * @Description 根据维修项目编号、机型、组织机构获取工卡
	  * @CreateTime 2017年10月10日 下午1:48:23
	  * @CreateBy 岳彬彬
	  * @param jx
	  * @param wxxmbh
	  * @param dprtcode
	  * @return
	  */
	 WorkCard getWorkCardByjxAndWxxmbh(@Param("jx")String jx, @Param("wxxmbh")String wxxmbh, @Param("dprtcode")String dprtcode);
	 
	/**
	 * @Description 根据组织机构查询所有有效的工卡数据
	 * @CreateTime 2017年11月15日 下午2:06:06
	 * @CreateBy 胡黄驰
	 * @param workCardList
	 */
	List<WorkCard> findByDprtcode(String dprtcode);
	
	/**
	 * @Description 根据版本、机型、工卡编号、机构代码 
	 * @CreateTime 2017-12-11 上午9:36:48
	 * @CreateBy 雷伟
	 * @param bb
	 * @param jx
	 * @param gkh
	 * @param dprtcode
	 * @return
	 */
	 WorkCard  getWorkCardByBbAndjxAndGkbh(@Param("bb")String bb,@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode);
	 
	 /**
	  * @Description 根据机构代码、工卡编号、机型、排除自身版本号 
	  * @CreateTime 2017-12-11 上午9:39:01
	  * @CreateBy 雷伟
	  * @param bb
	  * @param jx
	  * @param gkh
	  * @param dprtcode
	  * @return
	  */
	 List<WorkCard>  getWorkCardByNotBbAndjxAndGkbh(@Param("bb")String bb,@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode);
	 
	 /**
	  * @Description 获取前版本工卡,根据机构代码、工卡编号、机型、后版本id is null
	  * @CreateTime 2017-12-11 上午9:39:01
	  * @CreateBy 雷伟
	  * @param jx
	  * @param gkh
	  * @param dprtcode
	  * @return
	  */
	 WorkCard  getPreWorkCardByjxAndGkbh(@Param("jx")String jx, @Param("gkh")String gkh, @Param("dprtcode")String dprtcode);

	/**
	 * @Description 批量保存，导入专用
	 * @CreateTime 2017-12-12 上午11:13:43
	 * @CreateBy 雷伟
	 * @param addWockcards
	 */
	void insert4Batch(List<WorkCard> addWockcards);

	/**
	 * @Description 批量更新，导入专用
	 * @CreateTime 2017-12-12 上午11:13:43
	 * @CreateBy 雷伟
	 * @param addWockcards
	 */
	void update4Batch(List<WorkCard> updateWockcards);

	/**
	 * @Description 批量更新前版本的后版本ID，导入专用
	 * @CreateTime 2017-12-12 上午11:13:43
	 * @CreateBy 雷伟
	 * @param addWockcards
	 */
	void updatePreBb4Batch(List<WorkCard> preBbWockcards);
	
	/**
	 * @Description 工卡(导出)
	 * @CreateTime 2017-12-25 下午1:49:32
	 * @CreateBy 刘兵
	 * @param record 工卡
	 * @return List<WorkCard> 工卡集合
	 */
	List<WorkCard> doExportExcel(WorkCard record);
}