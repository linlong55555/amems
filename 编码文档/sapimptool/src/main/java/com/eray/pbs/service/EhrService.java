package com.eray.pbs.service;

import java.math.BigDecimal;
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

import com.eray.pbs.dao.EhrDao;
import com.eray.pbs.dao.EhrPbsDao;
import com.eray.pbs.po.Ehr;
import com.eray.pbs.po.EhrPbs;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class EhrService
{
    @Autowired
    private EhrDao ehrDao;
    @Autowired
    private EhrPbsDao ehrPbsDao;

    private Map<String, Object> searchParams= new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<Ehr> specEhr;
    
    private Specification<EhrPbs> specEhrPbs;
    private List<EhrPbs> ehrPbsList;
    private EhrPbs ehrPbs;
    
    private PageRequest pageRequest;
    private Page<Ehr> page;

    public List<Ehr> findEhr(Map<String, Object> searchParams)
    {
        return ehrDao.findAll(buildEhrSpecification(searchParams));
    }

    private Specification<Ehr> buildEhrSpecification(Map<String, Object> searchParams)
    {
        filters = SearchFilter.parse(searchParams);
        specEhr = DynamicSpecifications.bySearchFilter(filters.values(), Ehr.class);
        return specEhr;
    }

    @Transactional(readOnly = false)
    public Ehr save(Ehr ehr,String[] para)
    {
        if(ehr== null || para == null || para.length<10){
            return ehr;
        }
        searchParams.clear();
        searchParams.put("EQ_workOrder", ehr.getWorkOrder());
        filters = SearchFilter.parse(searchParams);
        specEhrPbs = DynamicSpecifications.bySearchFilter(filters.values(), EhrPbs.class);
        ehrPbsList = ehrPbsDao.findAll(specEhrPbs);
        if (ehrPbsList != null && ehrPbsList.size()>0)
        {
            ehrPbs=ehrPbsList.get(0);
        }else{
            ehrPbs = new EhrPbs();
            ehrPbs.setWorkOrder(ehr.getWorkOrder());
        }
        ehrPbs.setControlNo(para[0]);
        ehrPbs.setOrignal(para[2]);
        ehrPbs.setDescription(para[3]);
        try{
        	ehrPbs.setWorkhours(new BigDecimal(para[4]));
        	ehrPbs.setCap(new BigDecimal(para[5]));
        }catch(Exception e){
        	return ehr;
        }
        ehrPbs.setDefectType(para[6]);
        ehrPbs.setManhourChargableStatus(para[7]);
        ehrPbs.setBillingRule(para[8]);
        ehrPbs.setPartialQuoted(para[9]);
        if(para.length>10){
        	ehrPbs.setSpecialCharges(para[10]);
        }else{
        	ehrPbs.setSpecialCharges(null);
        }
        ehrPbsDao.save(ehrPbs);
        return ehrDao.save(ehr);
    }

    public Ehr findLast(String workOder)
    {
        if(workOder==null ||workOder.equals("")){
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_workOrder", workOder);
        filters = SearchFilter.parse(searchParams);
        specEhr = DynamicSpecifications.bySearchFilter(filters.values(),Ehr.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page=ehrDao.findAll(specEhr, pageRequest);
        if(page.getContent()!=null && page.getContent().size()>0){
            return page.getContent().get(0);
        }
        return null;
    }
}
