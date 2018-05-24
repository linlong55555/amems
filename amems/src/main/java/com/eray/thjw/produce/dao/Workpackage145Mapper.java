package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.po.Workpackage145;
/**
 * 
 * @Description 145工包mapper
 * @CreateTime 2017年10月9日 下午6:59:06
 * @CreateBy 岳彬彬
 */
public interface Workpackage145Mapper {
    int deleteByPrimaryKey(String id);

    int insert(Workpackage145 record);

    int insertSelective(Workpackage145 record);

    Workpackage145 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Workpackage145 record);

    int updateByPrimaryKey(Workpackage145 record);
    /**
     * 
     * @Description 获取145工包列表
     * @CreateTime 2017年10月17日 上午9:26:54
     * @CreateBy 岳彬彬
     * @param record
     * @return
     */
    List<Workpackage145> selectAllList(Workpackage145 record);
    /**
     * 
     * @Description 验证工包编号组织机构下唯一
     * @CreateTime 2017年10月18日 上午10:52:09
     * @CreateBy 岳彬彬
     * @param gbbh
     * @param dprtcode
     * @return
     */
    int getCountByGbbhAndDprtcode(String gbbh, String dprtcode);
    /**
     * 
     * @Description 根据工包id获取数据
     * @CreateTime 2017年10月18日 下午3:25:42
     * @CreateBy 岳彬彬
     * @param id
     * @return
     */
    Workpackage145 getById(String id);
    /**
     * 
     * @Description 更新工包
     * @CreateTime 2017年10月18日 下午4:34:59
     * @CreateBy 岳彬彬
     * @param record
     */
    void updateWp(Workpackage145 record);
    /**
     * 
     * @Description 下发工包
     * @CreateTime 2017年10月19日 上午9:26:03
     * @CreateBy 岳彬彬
     * @param record
     */
    void update4Issued(Workpackage145 record);
    /**
     * 
     * @Description 完工反馈
     * @CreateTime 2017年10月19日 上午11:50:57
     * @CreateBy 岳彬彬
     * @param record
     */
    void update4Feedback(Workpackage145 record);
    /**
     * 
     * @Description 完工关闭
     * @CreateTime 2017年10月19日 下午1:59:18
     * @CreateBy 岳彬彬
     * @param record
     */
    void update4Close(Workpackage145 record);
    /**
     * 
     * @Description 指定结束
     * @CreateTime 2017年10月19日 下午1:59:29
     * @CreateBy 岳彬彬
     * @param record
     */
    void update4End(Workpackage145 record);
    /**
     * 
     * @Description 获取航材工具清单
     * @CreateTime 2017年10月20日 上午10:07:58
     * @CreateBy 岳彬彬
     * @param gbid
     * @param dprtcode
     * @return
     */
    List<Map<String,Object>> getHcToolList(@Param("gbid") String gbid, @Param("dprtcode") String dprtcode);

    /**
     * 
     * @Description 维修计划列表
     * @CreateTime 2017年10月23日 下午1:45:37
     * @CreateBy 林龙
     * @param record
     * @return
     */
	List<Workpackage145> getWorkpackagePlanList(Workpackage145 record);

	List<Map<String, Object>> getMaterialsDetail(Workpackage145 workpackage145);
	/**
	 * @Description 根据项目编号获得与之关联的工包信息
	 * @CreateTime 2017-11-15 上午10:47:00
	 * @CreateBy 甘清
	 * @param record 工包参数
	 * @return
	 */
	List<Workpackage145> getRevisonByProjectId(Workpackage145 record);

	int queryCount(Workpackage tec);
}