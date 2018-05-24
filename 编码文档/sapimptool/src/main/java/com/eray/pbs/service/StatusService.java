package com.eray.pbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.StatusDao;
import com.eray.pbs.dao.StatusPbsDao;
import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.Status;
import com.eray.pbs.po.StatusPbs;
import com.eray.pbs.po.WorkPbs;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class StatusService
{
	private static final Logger logger = LoggerFactory.getLogger(StatusService.class);
	@Autowired
    private StatusDao statusDao;
	@Autowired
	private StatusPbsDao statusPbsDao;
	@Autowired
	private WorkPbsDao workPbsDao;
	
	private Map<String, Object> searchParams= new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<Status> specStatus;
    
    private Specification<StatusPbs> specStatusPbs;
    private List<StatusPbs> statusPbsList;
    private StatusPbs statusPbs;
    
    private Specification<WorkPbs> specWorkPbs;
    private List<WorkPbs> workPbsList;
    private WorkPbs workPbs;
    
    private PageRequest pageRequest;
    private Page<Status> page;

    public List<Status> findAll(Map<String, Object> criteria)
    {
        return statusDao.findAll(buildProjectSpecification(criteria));
    }
    
    private Specification<Status> buildProjectSpecification(Map<String, Object> criteria) {
        Map<String, SearchFilter> filters = SearchFilter.parse(criteria);
        Specification<Status> spec = DynamicSpecifications.bySearchFilter(filters.values(), Status.class);
        return spec;
    }

    @Transactional(readOnly = false)
    public Status save(Status status,String[] para)
    {
    	if(status== null || para == null || para.length<6){
            return status;
        }
    	searchParams.clear();
		searchParams.put("EQ_wid", status.getWorkOrder());
		filters = SearchFilter.parse(searchParams);
		specWorkPbs = DynamicSpecifications.bySearchFilter(filters.values(), WorkPbs.class);
		workPbsList = workPbsDao.findAll(specWorkPbs);
		
		if(workPbsList!=null && workPbsList.size()>0){
			workPbs=workPbsList.get(0);
		}else{
			return status;
		}
        searchParams.clear();
        searchParams.put("EQ_workOrder", status.getWorkOrder());
        searchParams.put("EQ_operation", status.getOperation());
        filters = SearchFilter.parse(searchParams);
        specStatusPbs = DynamicSpecifications.bySearchFilter(filters.values(), StatusPbs.class);
        statusPbsList = statusPbsDao.findAll(specStatusPbs);
        if (statusPbsList != null && statusPbsList.size()>0)
        {
        	statusPbs=statusPbsList.get(0);
        }else{
        	statusPbs = new StatusPbs();
        	statusPbs.setWorkOrder(status.getWorkOrder());
        }
        statusPbs.setOperation(status.getOperation());
        statusPbs.setOldStatus(para[2]);
        statusPbs.setNewStatus(para[3]);
        statusPbs.setChangeBy(para[4]);
        statusPbs.setChangeOn(para[5]);
        statusPbsDao.save(statusPbs);
        
        if(workPbs.getStatus()==null || workPbs.getStatus().equals(statusPbs.getOldStatus())){
			workPbs.setStatus(statusPbs.getNewStatus());
			logger.info(workPbs.toString());
			workPbsDao.save(workPbs);
		}
        return statusDao.save(status);
    }

	public Status findLast(String workOrder, String operation)
    {
		if(workOrder==null ||operation==null||workOrder.equals("")||operation.equals("")){
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_workOrder", workOrder);
        searchParams.put("EQ_operation", operation);
        filters = SearchFilter.parse(searchParams);
        specStatus = DynamicSpecifications.bySearchFilter(filters.values(),Status.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page=statusDao.findAll(specStatus, pageRequest);
        if(page.getContent()!=null && page.getContent().size()>0){
            return page.getContent().get(0);
        }
        return null;
    }
}
