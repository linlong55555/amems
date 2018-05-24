package com.eray.thjw.aerialmaterial.service;


public interface ComponentScrapService {
	/**
     * 报废审核报废使用情况
     * @param record
     * @return
     */
    int insertComponentScrap(String bjh,String sn, String dprtcode);
    /**
     * 报废审核报废使用情况
     * @param record
     * @return
     */
    int deleteComponentScrap(String bjh,String sn, String dprtcode);
}
