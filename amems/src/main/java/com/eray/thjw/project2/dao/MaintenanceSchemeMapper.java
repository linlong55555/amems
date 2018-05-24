package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.project2.po.MaintenanceScheme;

/**
 * @Description 维修方案Mapper
 * @CreateTime 2017年8月16日 下午2:51:04
 * @CreateBy 韩武
 */
public interface MaintenanceSchemeMapper {
    int deleteByPrimaryKey(String id);

    int insert(MaintenanceScheme record);

    int insertSelective(MaintenanceScheme record);

    MaintenanceScheme selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MaintenanceScheme record);

    int updateByPrimaryKey(MaintenanceScheme record);
    
    /**
     * @Description 根据飞机机型查询维修方案版本
     * @CreateTime 2017年8月16日 下午2:51:41
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    List<MaintenanceScheme> queryByFjjx(MaintenanceScheme scheme);
    
    /**
     * @Description 根据机型和组织机构找到最新的维修方案
     * @CreateTime 2017年8月16日 下午2:52:50
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    MaintenanceScheme findNewest(MaintenanceScheme scheme);
    
    /**
	 * @Description 查询待审核维修方案
	 * @CreateTime 2017年8月22日 上午10:58:17
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
    List<MaintenanceScheme> queryAuditList(MaintenanceScheme scheme);
    
    /**
	 * @Description 查询待批准维修方案
	 * @CreateTime 2017年8月22日 上午10:58:29
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
    List<MaintenanceScheme> queryApproveList(MaintenanceScheme scheme);
    
    /**
	 * @Description 查询待生效维修方案
	 * @CreateTime 2017年8月22日 上午10:58:37
	 * @CreateBy 韩武
	 * @param scheme
	 * @return
	 */
    List<MaintenanceScheme> queryEffectList(MaintenanceScheme scheme);
    
    /**
     * @Description 验证新增的维修项目数量
     * @CreateTime 2017年8月28日 下午1:35:50
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    int existProjectCount(MaintenanceScheme scheme);
    
    /**
     * @Description 查询生效的维修方案详情
     * @CreateTime 2017年8月28日 下午3:39:32
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    MaintenanceScheme queryEffective(MaintenanceScheme scheme);
    
    /**
     * @Description 查询未生效的维修方案详情
     * @CreateTime 2017年8月28日 下午4:02:39
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    MaintenanceScheme queryIneffective(MaintenanceScheme scheme);
    
    /**
     * @Description 查询维修方案版本历史版本
     * @CreateTime 2017年8月30日 上午9:19:54
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    List<MaintenanceScheme> queryVersionList(MaintenanceScheme scheme);
    
    /**
     * @Description 查询维修方案详情
     * @CreateTime 2017年9月12日 下午5:08:25
     * @CreateBy 韩武
     * @param id
     * @return
     */
    MaintenanceScheme queryDetail(String id);
    
    /**
     * @Description 更新工卡编号
     * @CreateTime 2017年9月20日 上午10:32:19
     * @CreateBy 韩武
     * @param scheme
     * @return
     */
    int updateGkbh(MaintenanceScheme scheme);
    
    /** 常量：更新装机清单临时区  */
    public static final String U_INS_LIST_TEMP = "1";
    /** 常量：更新装机清单编辑区  */
    public static final String U_INS_LIST_EDIT = "2";
    /** 常量：更新装机清单生效区  */
    public static final String U_INS_LIST_EFF = "3";
    
    /**
     * @Description 更新装机清单时控/时寿标识
     * @CreateTime 2017年10月9日 上午11:22:51
     * @CreateBy 徐勇
     * @param table 要更新的表 1|2|3(临时区|编辑区|生效区)
     * @param wxfaid 生效的维修方案ID
     * @param dprtcode 待更新装机清单范围：组织机构ID
     * @param fjjx 待更新装机清单范围：飞机机型
     * @return
     */
    int updateInstallationListControlFlag(@Param("table") String table, @Param("wxfaid") String wxfaid, @Param("dprtcode") String dprtcode,  @Param("fjjx") String fjjx);

}