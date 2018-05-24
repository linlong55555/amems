package com.eray.thjw.dao;

import java.util.Date;

public interface CommonMapper {
    
	/**
	 * 获取数据库系统时间
	 * @return
	 */
	Date getSysdate();
}