package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.po.SpareStore;

public interface SpareStoreService {

	List<SpareStore> selectSpareStoreList(SpareStore ss) throws RuntimeException;
}
