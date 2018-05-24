package com.eray.thjw.productionplan.service;

import java.util.List;

import com.eray.thjw.productionplan.po.LoadingListToSpecialCondition;

import enu.LogOperationEnum;

public interface LoadingListToSpecialConditionService {

	 List<LoadingListToSpecialCondition> select(LoadingListToSpecialCondition con)throws RuntimeException;             //根据装机清单id查询
	 
	 void saveEditable(List<LoadingListToSpecialCondition> list, String zjqdid, 
			 String czls, LogOperationEnum logOperationEnum) throws RuntimeException;             //保存时控件特殊飞行情况
	 
}
