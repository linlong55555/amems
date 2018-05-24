package com.eray.thjw.productionplan.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.productionplan.po.PlaneData;


/**
 * 同步生效区service
 * @author hanwu
 *
 */
public interface SynchronizeEffectiveService {
	
	/**
	 * 同步监控数据，将编辑区的数据同步到生效区中
	 * @param fjzch
	 * @throws RuntimeException
	 */
	List<String> doSynchronize(PlaneData pd) throws RuntimeException;

}
