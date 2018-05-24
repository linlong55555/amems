package com.eray.thjw.produce.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.po.Workorder145;

public interface Workorder145Mapper {
    int deleteByPrimaryKey(String id);

    int insert(Workorder145 record);

    int insertSelective(Workorder145 record);

    Workorder145 selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Workorder145 record);

    int updateByPrimaryKey(Workorder145 record);

	List<Workorder145> queryAllList(Workorder145 newRecord);
	/**
	 * 
	 * @Description 根据工包id获取工包下工单状态不为9的工单数量
	 * @CreateTime 2017年10月18日 下午5:18:06
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	int getCountByGbid(String gbid);
	/**
	 * 
	 * @Description 根据工包id获取提交状态下的工单数量
	 * @CreateTime 2017年10月19日 上午9:20:12
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	int getSubmitCountByGbid(String gbid);
	/**
	 * 
	 * @Description 根据工包下发工单
	 * @CreateTime 2017年10月19日 上午9:37:38
	 * @CreateBy 岳彬彬
	 * @param whrid
	 * @param whrdwid
	 * @param gbid
	 */
	void update4Issued(String xfrdwid,String xfrid,String gbid);
	/**
	 * 
	 * @Description 根据工包id获取工单状态不为9和10的工单数量
	 * @CreateTime 2017年10月19日 下午1:37:37
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	int getCount4Wp(String gbid);
	/**
	 * 
	 * @Description 获取工包明细
	 * @CreateTime 2017年10月20日 上午10:08:54
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	List<Map<String,Object>> getWorkorderListByGbid(@Param("searchMap")Map<String, Object> searchMap);
	/**
	 * 
	 * @Description 根据组织机构和飞机注册号选择没有工包id的工单
	 * @CreateTime 2017年10月20日 上午11:03:34
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	List<Workorder145> getWOList(Workorder145 record);
	/**
	 * 
	 * @Description 验证选中工单集合的状态和工包id是否为空
	 * @CreateTime 2017年10月20日 上午11:20:08
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	int getCountByztAndGdid(List<String> list);
	/**
	 * 
	 * @Description 工包添加工单
	 * @CreateTime 2017年10月20日 上午11:22:38
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void updateGbid(Workorder145 record);
	/**
	 * 
	 * @Description 根据工单id集合获取工单
	 * @CreateTime 2017年10月20日 下午5:05:56
	 * @CreateBy 岳彬彬
	 * @param list
	 * @return
	 */
	List<Workorder145> selectByIdList(List<String> list);
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年10月20日 下午5:06:33
	 * @CreateBy 岳彬彬
	 * @param record
	 */
	void update4IssuedById(Workorder145 record);

	/**
	 * @Description 145工单主列表
	 * @CreateTime 2017-10-23 上午11:19:43
	 * @CreateBy 雷伟
	 * @param workorder
	 * @return
	 */
	List<Workorder145> queryAllPageList(Workorder145 workorder);
	
	/**
	 * @Description 工单135信息查询 (弹窗)
	 * @CreateTime 2017-12-28 上午11:27:24
	 * @CreateBy 刘兵
	 * @param workorder 145工单
	 * @return 工单列表
	 */
	List<Workorder145> queryAllPageListWin(Workorder145 workorder);

	/**
	 * @Description 按参数查询145工单
	 * @CreateTime 2017-10-23 下午2:42:00
	 * @CreateBy 雷伟
	 * @param paramMap
	 * @return
	 */
	List<Workorder145> selectByParam(Map<String, Object> paramMap);

    /**
     * @Description 根据工单ID获取145工单信息
     * @CreateTime 2017-10-24 下午1:58:38
     * @CreateBy 雷伟
     * @param gdid 工单ID
     * @return
     */
	Workorder145 selectWOById(String gdid);

	/**
	 * @Description 获取145工单航材工具
	 * @CreateTime 2017-10-24 下午1:58:16
	 * @CreateBy 雷伟
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getGDHCToolDetail(Map<String, Object> map);

	List<Map<String, Object>> queryDiagramList(Workorder145 workorder145);

	List<Map<String, Object>> queryProgressList(Workorder145 workorder145);
	/**
	 * 
	 * @Description 获取工包下提交状态下工单
	 * @CreateTime 2017年11月10日 上午10:51:59
	 * @CreateBy 岳彬彬
	 * @param gbid
	 * @return
	 */
	List<Workorder145> getWO4ValidationIssued(String gbid);
	/**
	 * 
	 * @Description 验证工单编号是否重复
	 * @CreateTime 2017年12月5日 下午3:10:06
	 * @CreateBy 岳彬彬
	 * @param dprtcode
	 * @param gdbh
	 * @return
	 */
	int getCount4Validation(String dprtcode,String gdbh);
	/**
	 * 
	 * @Description 根据工单识别id更新145工单
	 * @CreateTime 2017年12月28日 上午10:28:38
	 * @CreateBy 岳彬彬
	 * @param recoed
	 */
	void updateByGdsbid(Workorder record);
	/**
	 * 
	 * @Description 根据工单识别id获取工单
	 * @CreateTime 2017年12月28日 上午10:41:35
	 * @CreateBy 岳彬彬
	 * @param gdsbid
	 * @return
	 */
	Workorder145 getByGdsbid(String gdsbid);
	/**
	 * 
	 * @Description 
	 * @CreateTime 2018年1月19日 下午3:23:24
	 * @CreateBy 岳彬彬
	 * @param fjjx
	 * @param fjzch
	 * @param fjxlh
	 * @param gbid
	 */
	void updateFj(String fjjx,String fjzch,String fjxlh,String gbid);

	int queryCount(Workorder145 fai);
}