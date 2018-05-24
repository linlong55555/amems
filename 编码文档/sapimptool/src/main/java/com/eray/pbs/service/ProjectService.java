package com.eray.pbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.ProjectDao;
import com.eray.pbs.dao.ProjectPbsDao;
import com.eray.pbs.po.Project;
import com.eray.pbs.po.ProjectPbs;
import com.eray.util.Global;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class ProjectService
{
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectPbsDao projectPbsDao;

    private Map<String, Object> searchParams = new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<Project> specProject;
    private PageRequest pageRequest;
    private Page<Project> page;
    
    private Specification<ProjectPbs> specProjectPbs;

    private List<ProjectPbs> projectPbsList;
    private ProjectPbs projectPbs;

    public List<Project> findAll(Map<String, Object> criteria)
    {
        return projectDao.findAll(buildProjectSpecification(criteria));
    }

    private Specification<Project> buildProjectSpecification(Map<String, Object> criteria)
    {
        filters = SearchFilter.parse(criteria);
        specProject = DynamicSpecifications.bySearchFilter(filters.values(), Project.class);
        return specProject;
    }

    @Transactional(readOnly = false)
    public Project save(Project project,String[] para)
    {
        if (project == null || para.length<5)
        {
            return project;
        }
        searchParams.clear();
        searchParams.put("EQ_rid", project.getRevisionId());
        //searchParams.put("EQ_pid", project.getProjectId());
        filters = SearchFilter.parse(searchParams);
        specProjectPbs = DynamicSpecifications.bySearchFilter(filters.values(), ProjectPbs.class);
        projectPbsList = projectPbsDao.findAll(specProjectPbs);
        if (projectPbsList != null && projectPbsList.size() > 0)
        {
            projectPbs = projectPbsList.get(0);
            projectPbs.setPid(project.getProjectId());
        } else
        {
            projectPbs = new ProjectPbs();
            projectPbs.setRid(project.getRevisionId());
            projectPbs.setPid(project.getProjectId());
        }
        projectPbs.setSalesOrder(Global.removeZeroBefore(para[2]));
        projectPbs.setCid(Global.removeZeroBefore(para[3]));
        projectPbs.setStatus(para[4]);
        projectPbsDao.save(projectPbs);
        return projectDao.save(project);
    }

    public Project findLast(String revisionId, String projectId)
    {
        if(revisionId==null ||projectId==null||revisionId.equals("")||projectId.equals("")){
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_revisionId", revisionId);
        searchParams.put("EQ_projectId", projectId);
        filters = SearchFilter.parse(searchParams);
        specProject = DynamicSpecifications.bySearchFilter(filters.values(),Project.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page=projectDao.findAll(specProject, pageRequest);
        if(page.getContent()!=null && page.getContent().size()>0){
            return page.getContent().get(0);
        }
        return null;
    }
}
