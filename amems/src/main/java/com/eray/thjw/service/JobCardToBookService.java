package com.eray.thjw.service;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.JobCardToBook;

public interface JobCardToBookService {

	/**
	 * @author sunji
	 * @description 保存工卡修订通知书信息
	 * @param maintenance
	 * @return 
	 */
	public void saveJobCardToBook(String gkid ,List<String> xdtzsids,String czls, String zdid) throws RuntimeException;

	/**
	 * @author sunji
	 * @description 根据mainid查询数据
	 * @param jobCardToBook
	 * @return List<JobCardToBook>
	 */
	public List<JobCardToBook> queryAllByMainid(String mainid)throws BusinessException;
	
	/**
	 * @author sunji
	 * @description 修改工卡修订通知书信息
	 * @param maintenance
	 * @return 
	 */
	public void updateJobCardToBook(String gkid ,List<String> xdtzsids,String czls, String zdid) throws BusinessException;

	
}
