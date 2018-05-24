package com.eray.pbs.service;

import java.math.BigDecimal;
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

import com.eray.pbs.dao.SpentHoursDao;
import com.eray.pbs.dao.SpentHoursPbsDao;
import com.eray.pbs.dao.WorkPbsDao;
import com.eray.pbs.po.SpentHours;
import com.eray.pbs.po.SpentHoursPbs;
import com.eray.pbs.po.WorkPbs;
//import com.eray.pbs.po.WorkPbs;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class SpentHoursService
{
	private static final Logger logger = LoggerFactory.getLogger(SpentHoursService.class);
	
	@Autowired
	private SpentHoursDao spentHoursDao;
	@Autowired
	private SpentHoursPbsDao spentHoursPbsDao;
	@Autowired
	private WorkPbsDao workPbsDao;

	private Map<String, Object> searchParams = new HashMap<String, Object>();
	private Map<String, SearchFilter> filters;
	private Specification<SpentHours> specSpentHours;

	private Specification<SpentHoursPbs> specSpentHoursPbs;
	private List<SpentHoursPbs> spentHoursPbsList;
	private SpentHoursPbs spentHoursPbs;
	
	private Specification<WorkPbs> specWorkPbs;
	private List<WorkPbs> workPbsList;
	private WorkPbs workPbs;

	private PageRequest pageRequest;
	private Page<SpentHours> page;

	public List<SpentHours> findAll(Map<String, Object> criteria)
	{
		return spentHoursDao.findAll(buildProjectSpecification(criteria));
	}

	private Specification<SpentHours> buildProjectSpecification(Map<String, Object> criteria)
	{
		Map<String, SearchFilter> filters = SearchFilter.parse(criteria);
		Specification<SpentHours> spec = DynamicSpecifications.bySearchFilter(filters.values(), SpentHours.class);
		return spec;
	}

	@Transactional(readOnly = false)
	public SpentHours save(SpentHours spentHours, String[] para)
	{
		if (spentHours == null || para == null || para.length < 11)
		{
			return spentHours;
		}
		searchParams.clear();
		searchParams.put("EQ_wid", spentHours.getWorkOrder());
		filters = SearchFilter.parse(searchParams);
		specWorkPbs = DynamicSpecifications.bySearchFilter(filters.values(), WorkPbs.class);
		workPbsList = workPbsDao.findAll(specWorkPbs);
		
		if(workPbsList!=null && workPbsList.size()>0){
			workPbs=workPbsList.get(0);
			if(workPbs.getActualHours()==null){
				workPbs.setActualHours(new BigDecimal("0"));
			}
		}else{
			return spentHours;
		}
		
		searchParams.clear();
		searchParams.put("EQ_workOrder", spentHours.getWorkOrder());
		searchParams.put("EQ_operation", spentHours.getOperation());
		searchParams.put("EQ_workCenter", spentHours.getWorkCenter());
		filters = SearchFilter.parse(searchParams);
		specSpentHoursPbs = DynamicSpecifications.bySearchFilter(filters.values(), SpentHoursPbs.class);
		spentHoursPbsList = spentHoursPbsDao.findAll(specSpentHoursPbs);
		if (spentHoursPbsList != null && spentHoursPbsList.size() > 0)
		{
			spentHoursPbs = spentHoursPbsList.get(0);
		} else
		{
			spentHoursPbs = new SpentHoursPbs();
			spentHoursPbs.setWorkOrder(spentHours.getWorkOrder());
			spentHoursPbs.setOperation(spentHours.getOperation());
			spentHoursPbs.setWorkCenter(spentHours.getWorkCenter());
		}
		spentHoursPbs.setConfText(para[2]);
		try
		{
			spentHoursPbs.setSpentHours(new BigDecimal(para[4]));
		} catch (Exception e)
		{
			return spentHours;
		}
		spentHoursPbs.setSpentHoursUnit(para[5]);
		spentHoursPbs.setStartDate(para[6]);
		spentHoursPbs.setStartTime(para[7]);
		spentHoursPbs.setFinishDate(para[8]);
		spentHoursPbs.setFinishTime(para[9]);
		spentHoursPbs.setEmployeeId(para[10]);
		spentHoursPbs.setActivityType(para[11]);
		spentHoursPbsDao.save(spentHoursPbs);
		
		workPbs.setActualHours(workPbs.getActualHours().add(spentHoursPbs.getSpentHours()));
		if(spentHoursPbs.getSpentHours().compareTo(BigDecimal.ZERO)>0){
			workPbs.setActualHourChanged("1");
		}
		logger.info(workPbs.toString());
		workPbsDao.save(workPbs);
		
		return spentHoursDao.save(spentHours);
	}

	public SpentHours findLast(String workOrder, String operation, String workCenter)
	{
		if (workOrder == null || workOrder.equals("") || operation == null || operation.equals("")
		        || workCenter == null || workCenter.equals(""))
		{
			return null;
		}
		searchParams.clear();
		searchParams.put("EQ_workOrder", workOrder);
		searchParams.put("EQ_operation", operation);
		searchParams.put("EQ_workCenter", workCenter);
		filters = SearchFilter.parse(searchParams);
		specSpentHours = DynamicSpecifications.bySearchFilter(filters.values(), SpentHours.class);
		pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
		page = spentHoursDao.findAll(specSpentHours, pageRequest);
		if (page.getContent() != null && page.getContent().size() > 0)
		{
			return page.getContent().get(0);
		}
		return null;
	}
}
