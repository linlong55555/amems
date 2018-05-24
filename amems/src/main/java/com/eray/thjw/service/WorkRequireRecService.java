package com.eray.thjw.service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.WorkRequireRec;

public interface WorkRequireRecService {
      void writeLog(WorkRequireRec workRequireRec) throws BusinessException;
}
