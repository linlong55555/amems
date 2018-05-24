package com.eray.thjw.monitorsettings.dao;

import java.util.List;

import com.eray.thjw.po.Monitorsettings;

public interface MonitorsettingsMapper {

	int insert(Monitorsettings record);

	int insertSelective(Monitorsettings record);

	int updateByKeyandDprtcode(Monitorsettings record);

	List<Monitorsettings> selectThresholdByDprtcode(String dprtcode);

	List<Monitorsettings> selectThresholdByKeys(Monitorsettings record);

	Monitorsettings selectThresholdByKeyandDprtcode(Monitorsettings monitorsettings);

	List<Monitorsettings> selectThresholdByKeyandDprtcodes(Monitorsettings monitorsettings);

	//获取适航性资料子类型
	List<Monitorsettings> selectShxzlSubTypeByDprtcode(String dprtcode);

	//批量删除
	void delete4Batch(List<Monitorsettings> delSubTypes);

	//批量新增
	void insert4Batch(List<Monitorsettings> addSubTypes);

	//批量修改
	void update4Batch(List<Monitorsettings> updateSubTypes);
}
