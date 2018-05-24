package com.eray.thjw.aerialmaterial.service;

import com.eray.thjw.aerialmaterial.po.ExpatriateDetail;


public interface ExpatriateDetailService {

	 void insertSelective(ExpatriateDetail record) throws RuntimeException;

	ExpatriateDetail queryByKey(String id, String kcllid) throws RuntimeException;

	void delete(String string) throws RuntimeException;

	void update(ExpatriateDetail expatriateDetail) throws RuntimeException;
}
