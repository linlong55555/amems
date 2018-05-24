package com.eray.pbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.ImptFailedDao;
import com.eray.pbs.po.ImptFailed;

@Component
@Transactional(readOnly = true)
public class ImptFailedService
{
	@Autowired
    private ImptFailedDao imptFailedDao;
    
    @Transactional(readOnly = false)
    public ImptFailed save(ImptFailed imptFailed)
    {
        return imptFailedDao.save(imptFailed);
    }

}
