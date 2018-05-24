package com.eray.thjw.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.User;
import com.eray.thjw.system.po.BookMark;





public interface BookMarkMapper {
	int insertSelective(BookMark record);
	/**
	 * @author peixiu
	 * @description  根据userId（用户ID） 查询 
	 * @param userId 用户ID
	 * @return List
	 * @develop date 2017.03.07
	 */
	 List<BookMark> selectBookMarkByUserId(@Param("userId") String userId,@Param("jgdm") String jgdm);
	 
	 List<BookMark> selectBookMarkByPrimaryKey(String id);
	 
	 int deleteByPrimaryKey(String id);
	 
	 int updateBookMark(BookMark record);
    
}