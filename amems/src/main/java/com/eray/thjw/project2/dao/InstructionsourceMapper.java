package com.eray.thjw.project2.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.Instructionsource;
/**
 * 
 * @Description 下达指令
 * @CreateTime 2017年8月26日 上午11:01:00
 * @CreateBy 岳彬彬
 */
public interface InstructionsourceMapper {
    int insert(Instructionsource record);

    int insertSelective(Instructionsource record);
    /**
     * @author 岳彬彬
     * @Description 通过指令id获取下达指令集合
     * @param list
     * @return
     */
    List<Instructionsource> getListByZlidList(List<String> list);
    /**
     * @CreateBy 岳彬彬
     * @Description 根据指令id删除对应的下达指令
     * @param zlid
     */
    void deleteInstructionSourceByZlid(String zlid);
    /**
     * @CreateBy 岳彬彬
     * @Description 批量新增下达指令
     * @param list
     */
    void insertInstructionSource(List<Instructionsource> list);

    /**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月19日 下午2:46:01
	 * @CreateBy 林龙
	 * @param instructionsourc 下达指令来源
	 * @return 下达指令来源List
	 * @throws BusinessException
	 */
	List<Instructionsource> selectOrderList(Instructionsource instructionsourc);

	/**
	 * @Description 根据指令ID,获取指令及关联的来源文件
	 * @CreateTime 2017-8-30 下午1:28:03
	 * @CreateBy 雷伟
	 * @param zlidList 指令ID
	 * @return
	 */
	List<Instructionsource> getRIByZlidList(List<String> zlidList);
	/**
	 * 
	 * @Description 根据业务id更新业务单据状态
	 * @CreateTime 2017年9月1日 下午2:55:01
	 * @CreateBy 岳彬彬
	 * @param ywdjzt
	 * @param zlid
	 */
	void updateYwdjztByZlid(@Param("ywdjzt")Integer ywdjzt,@Param("zlid")String zlid);
	/**
	 * 
	 * @Description 根据业务id集合更新单据状态
	 * @CreateTime 2017年9月1日 下午2:55:24
	 * @CreateBy 岳彬彬
	 * @param ywdjzt
	 * @param list
	 */
	void updateYwdjztByZlidList(@Param("ywdjzt")Integer ywdjzt,@Param("list")List<String> list);
}