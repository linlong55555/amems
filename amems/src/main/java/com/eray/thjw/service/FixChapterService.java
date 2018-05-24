package com.eray.thjw.service;

/**
 * @author 梅志亮
 * @time 2016-08-19
 * @describe 维修章节号Service 用于业务逻辑处理
 */

import java.util.List;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.po.FixChapter;

public interface FixChapterService {

	int insert(FixChapter fc) throws Exception; // 增加章节号

	int updateByPrimaryKey(FixChapter fc) throws Exception; // 修改章节号

	List<FixChapter> selectFixChapterList(FixChapter fc) throws Exception; // 根据条件查询章节号

	FixChapter selectFixChapter(FixChapter fc) throws Exception; // 查询一个章节号

	int selectCount(FixChapter fc) throws Exception; // 根据章节号查询记录数

	int deleteFixChapter(FixChapter fc) throws Exception; // 根据章节号 删除章节信息

	List<FixChapter> findAllFixChapter() throws Exception; // 查询所有有效的章节号

	FixChapter selectPrimaryKeyFixChapter(String zjh, String dprtcode)
			throws Exception; // 根据章节号查询一条信息

	List<FixChapter> selectByDprtcode(FixChapter fc); // 查询所有有效的章节号

	List<HCMainData> queryLimitTen(FixChapter fc);

}
