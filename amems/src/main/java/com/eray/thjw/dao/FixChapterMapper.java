package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.po.FixChapter;

public interface FixChapterMapper {

	int insert(FixChapter fc); // 增加章节号

	int updateByPrimaryKey(FixChapter fc); // 修改章节号

	List<FixChapter> selectFixChapterList(FixChapter fc); // 根据条件查询章节号

	FixChapter selectFixChapter(FixChapter fc); // 查询一个章节号

	int selectCount(FixChapter fc); // 根据章节号查询记录数

	int deleteFixChapter(FixChapter fc); // 根据章节号 删除章节信息

	List<FixChapter> findAllFixChapter(); // 查询所有有效的章节号

	FixChapter selectPrimaryKeyFixChapter(String zjh, String dprtcode);

	int batchMerge(List<FixChapter> datas);

	List<FixChapter> selectByDprtcode(FixChapter fc); // 根据组织机构查询章节号

	List<HCMainData> queryLimitTen(FixChapter fc);
}
