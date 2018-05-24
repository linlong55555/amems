package com.eray.thjw.project2.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.project2.po.TaskReplace;

public interface TaskReplaceService {
	
      List<TaskReplace> queryAll(TaskReplace taskreplace) throws BusinessException;
    
      int save(TaskReplace taskreplace) throws BusinessException;
      
      List<TaskReplace> initProjectByid(TaskReplace taskreplace) throws BusinessException;
      
      Map<String, Object> initProjectWindow(TaskReplace taskreplace) throws BusinessException;
}
