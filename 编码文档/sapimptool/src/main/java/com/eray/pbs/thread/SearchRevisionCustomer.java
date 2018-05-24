package com.eray.pbs.thread;

import java.util.HashMap;
import java.util.Map;

import com.eray.pbs.po.RevisionPbs;
import com.eray.pbs.po.WorkPbs;
import com.eray.pbs.service.RevisionService;
import com.eray.pbs.service.WorkService;
import com.eray.util.framework.SpringContextHolder;

public class SearchRevisionCustomer implements Runnable
{
	private Map<String, Object> criteria= new HashMap<String, Object>();
    private WorkPbs workPbs;
	public void run(){
		System.out.println("SearchRevisionCustomer start");
		RevisionService revisionService= SpringContextHolder.getBean("revisionService");
		WorkService workService = SpringContextHolder.getBean("workService");
		Iterable<RevisionPbs> iterableRevisionPbs = revisionService.findRevisionPbsAll();
		for (RevisionPbs revisionPbs : iterableRevisionPbs)
        {
	        if(revisionPbs.getCustomer()!=null && !revisionPbs.getCustomer().equals("")){
	        	continue;
	        }
	        criteria.clear();
	        criteria.put("EQ_rid", revisionPbs.getRid());
	        workPbs = workService.findPbs(criteria);
	        if(workPbs==null ||workPbs.getCustomerId()==null||workPbs.getCustomerId().equals("")){
	        	continue;
	        }
	        revisionPbs.setCustomer(workPbs.getCustomerId());
	        revisionService.savePbs(revisionPbs);
        }
	}
}
