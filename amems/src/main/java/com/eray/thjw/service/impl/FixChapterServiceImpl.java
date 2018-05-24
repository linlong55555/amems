package com.eray.thjw.service.impl;

/**
 * @author 梅志亮
 * @discrption  用于维护章节号的业务实现类
 */
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.dao.FixChapterMapper;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.FixChapterService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class FixChapterServiceImpl implements FixChapterService {
	@Resource
	private FixChapterMapper fixChapterMapper;
	@Resource
	private CommonRecService commonRecService;
    /**
     * 章节号的新增操作
     */
	@Override
	public int insert(FixChapter fc) throws Exception {
		int count = 0;
		// 获取登入user
		String czls = UUID.randomUUID().toString(); // 日志的操作流水
		User user = ThreadVarUtil.getUser();
		fc.setDprtcode(user.getJgdm());
		fc.setBmid(user.getBmdm());
		fc.setCjrid(user.getId());
		count = fixChapterMapper.insert(fc);

		commonRecService.writeByWhere(
				"zjh='" + fc.getZjh().replaceAll("'", "''")
						+ "' and b.dprtcode = '" + user.getJgdm() + "'",
				TableEnum.D_005, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE, null);
		return count;
	}
    /**
     * 章节号的新增操作
     */
	@Override
	public int updateByPrimaryKey(FixChapter fc) throws Exception {
		String czls = UUID.randomUUID().toString(); // 日志的操作流水
		int count = 0;
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		// 插入历史记录信息
		count = fixChapterMapper.updateByPrimaryKey(fc);
		commonRecService.writeByWhere(
				"zjh='" + fc.getZjh().replaceAll("'", "''")
						+ "' and b.dprtcode = '" + fc.getDprtcode() + "'",
				TableEnum.D_005, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE, null);
		return count;
	}

	@Override
	public List<FixChapter> selectFixChapterList(FixChapter fc)
			throws Exception {
		try {
			return fixChapterMapper.selectFixChapterList(fc);
		} catch (Exception e) {
			throw new Exception("查询章节号数据失败", e);
		}
	}

	@Override
	public FixChapter selectFixChapter(FixChapter fc) throws Exception {
		try {
			return fixChapterMapper.selectFixChapter(fc);
		} catch (Exception e) {
			throw new Exception("查询章节号数据失败", e);
		}
	}

	@Override
	public int selectCount(FixChapter fc) throws Exception {
		try {
			return fixChapterMapper.selectCount(fc);
		} catch (Exception e) {
			throw new Exception("查询章节号记录失败", e);
		}
	}

	@Override
	public int deleteFixChapter(FixChapter fc) throws Exception {
		try {
			String czls = UUID.randomUUID().toString(); // 日志的操作流水
			User user = ThreadVarUtil.getUser();
			// 插入历史记录信息
			commonRecService.writeByWhere(
					"zjh='" + fc.getZjh().replaceAll("'", "''")
							+ "' and b.dprtcode = '" + fc.getDprtcode() + "'",
					TableEnum.D_005, user.getId(), czls,
					LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, null);
			return fixChapterMapper.deleteFixChapter(fc);
		} catch (Exception e) {
			throw new Exception("删除章节号记录失败", e);
		}
	}

	@Override
	public List<FixChapter> findAllFixChapter() throws Exception {
		try {
			return fixChapterMapper.findAllFixChapter();
		} catch (Exception e) {
			throw new Exception("查询章节号数据失败", e);
		}
	}

	@Override
	public FixChapter selectPrimaryKeyFixChapter(String zjh, String dprtcode)
			throws Exception {
		return fixChapterMapper.selectPrimaryKeyFixChapter(zjh, dprtcode);
	}

	@Override
	public List<FixChapter> selectByDprtcode(FixChapter fc) {
		return fixChapterMapper.selectByDprtcode(fc);
	}
	@Override
	public List<HCMainData> queryLimitTen(FixChapter fc) {
		return fixChapterMapper.queryLimitTen(fc);
	}

}
