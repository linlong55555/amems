package com.eray.thjw.baseinfo.rule;

import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.exception.BusinessException;

/** 
 * @Description 项目编号生成接口
 * @CreateTime 2017-12-5 下午4:07:29
 * @CreateBy 甘清	
 */
public interface ProjectCodeRuleable {
	
	public static final String LXID = "63"; //类型ID
	
	
	public static final String  CFKEY = "JS_PROJECT_CODE"; //设置关键字

	/**
	 * @Description 创建项目编号
	 * @CreateTime 2017-12-5 下午5:03:22
	 * @CreateBy 甘清
	 * @param project 项目编号
	 * @return 项目编号
	 */
	String createProjectCode(Project project) throws BusinessException ;

}
