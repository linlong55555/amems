package com.eray.thjw.aerialmaterial.dao;

public interface ComponentScrapMapper {
   
    /**
     * 报废审核作废使用情况
     * @param record
     * @return
     */
    int insertComponentScrap(String bjh,String sn, String dprtcode);
    /**
     * 报废审核作废使用情况
     * @param record
     * @return
     */
    int deleteComponentScrap(String bjh,String sn, String dprtcode);
  
}