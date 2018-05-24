package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.PlaneBase;

public interface PlaneBaseService {
	
	List<PlaneBase> findAllBase(PlaneBase planeBase) throws RuntimeException;	//查询所有有效的基地
	
	List<PlaneBase> findBaseByDprtcode(String dprtcode) throws RuntimeException;	//查询有效的基地
	
}
