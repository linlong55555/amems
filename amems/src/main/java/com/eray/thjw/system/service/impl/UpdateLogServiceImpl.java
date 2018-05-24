package com.eray.thjw.system.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eray.thjw.ctx.SysContext;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.system.po.UpdateLog;
import com.eray.thjw.system.service.UpdateLogService;

/**
 * 
 * @Description 更新日志impl
 * @CreateTime 2018年4月24日 上午10:44:26
 * @CreateBy 林龙
 */
@Service
public class UpdateLogServiceImpl implements UpdateLogService {

	/**
	 * 
	 * @Description 获取更新日志数据
	 * @CreateTime 2018年4月24日 上午10:37:58
	 * @CreateBy 林龙
	 * @param updateLog
	 * @return
	 * @throws BusinessException
	 */
	public List<UpdateLog> queryByAll(UpdateLog updateLog) throws BusinessException{
		return orderbylist(SysContext.getUpdateLogList());
	}

	/**
	 * 发布时间，版本排序倒序
	 * @param list
	 * @return
	 */
	public List<UpdateLog> orderbylist(List<UpdateLog> list){
		
		  Collections.sort(list,new Comparator<UpdateLog>(){  
	            public int compare(UpdateLog arg0, UpdateLog arg1) {  
	            	if(arg1.getPubdate()!=null && !arg1.getPubdate().equals(arg0.getPubdate())){
	            		return arg1.getPubdate().compareTo(arg0.getPubdate());  
	            	}else{
	            		return arg1.getVersion().compareTo(arg0.getVersion());  
	            	}
	            }  
	        });  

		return list ;
	}

}
