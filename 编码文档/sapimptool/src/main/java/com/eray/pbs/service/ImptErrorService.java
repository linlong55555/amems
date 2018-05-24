package com.eray.pbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.ImptErrorDao;
import com.eray.pbs.po.ImptError;

@Component
@Transactional(readOnly = true)
public class ImptErrorService
{
	@Autowired
    private ImptErrorDao imptErrorDao;
    
    @Transactional(readOnly = false)
    public ImptError save(ImptError imptError)
    {
        return imptErrorDao.save(imptError);
    }

}
