package com.eray.pbs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.FileImptDao;
import com.eray.pbs.po.FileImpt;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class FileImptService
{
    private FileImptDao fileImptDao;

    public List<FileImpt> findAll(Map<String, Object> criteria)
    {
        return fileImptDao.findAll(buildFileImptSpecification(criteria));
    }
    
    private Specification<FileImpt> buildFileImptSpecification(Map<String, Object> criteria) {
        Map<String, SearchFilter> filters = SearchFilter.parse(criteria);
        Specification<FileImpt> spec = DynamicSpecifications.bySearchFilter(filters.values(), FileImpt.class);
        return spec;
    }

    @Transactional(readOnly = false)
    public FileImpt save(FileImpt fileImpt)
    {
        return fileImptDao.save(fileImpt);
    }

    @Autowired
    public void setFileImptDao(FileImptDao fileImptDao)
    {
        this.fileImptDao = fileImptDao;
    }
}
