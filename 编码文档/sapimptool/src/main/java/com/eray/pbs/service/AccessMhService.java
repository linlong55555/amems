package com.eray.pbs.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.AccessMhDao;
import com.eray.pbs.dao.AccessMhPbsDao;
import com.eray.pbs.po.AccessMh;
import com.eray.pbs.po.AccessMhPbs;
import com.eray.util.jpa.BaseEntity;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;
import com.eray.util.poi.ExcelUtil;

@Component
@Transactional(readOnly = true)
public class AccessMhService
{
    @Autowired
    private AccessMhDao accessMhDao;
    @Autowired
    private AccessMhPbsDao accessMhPbsDao;
    
    private Map<String, Object> searchParams= new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<AccessMh> specAccessMh;
    private Specification<AccessMhPbs> specAccessMhPbs;
    private PageRequest pageRequest;
    private Page<AccessMh> page;
    
    private List<AccessMhPbs> accessMhPbsList;
    private AccessMhPbs accessMhPbs;
    
    private String workhour;
    private BigDecimal bdWorkhour;

    @Transactional(readOnly = false)
    public BaseEntity save(AccessMh accessMh)
    {
        if(accessMh==null ){
            return accessMh;
        }
        searchParams.clear();
        searchParams.put("EQ_accessNo", accessMh.getAccessNo());
        searchParams.put("EQ_aircraftType", accessMh.getAircraftType());
        filters = SearchFilter.parse(searchParams);
        Specification<AccessMhPbs> spec = DynamicSpecifications.bySearchFilter(filters.values(), AccessMhPbs.class);
        AccessMhPbs accessMhPbs = accessMhPbsDao.findOne(spec);
        if(accessMhPbs == null){
            accessMhPbs=new AccessMhPbs();
            accessMhPbs.setAccessNo(accessMh.getAccessNo());
            accessMhPbs.setAircraftType(accessMh.getAircraftType());
        }
        accessMhPbsDao.save(accessMhPbs);
        return accessMhDao.save(accessMh);
    }

    public AccessMh findLast(String accessNo, String aircraftType)
    {
        if(accessNo==null ||aircraftType==null||accessNo.equals("")||aircraftType.equals("")){
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_accessNo", accessNo);
        searchParams.put("EQ_aircraftType", aircraftType);
        filters = SearchFilter.parse(searchParams);
        specAccessMh = DynamicSpecifications.bySearchFilter(filters.values(), AccessMh.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page = accessMhDao.findAll(specAccessMh, pageRequest);
        if (page.getContent() != null && page.getContent().size() > 0)
        {
            return page.getContent().get(0);
        }
        return null;
    }

    @Transactional(readOnly = false)
    public AccessMh save(AccessMh accessMh, Row row)
    {
        if(accessMh==null ){
            return accessMh;
        }
        searchParams.clear();
        searchParams.put("EQ_accessNo", accessMh.getAccessNo());
        searchParams.put("EQ_aircraftType", accessMh.getAircraftType());
        filters = SearchFilter.parse(searchParams);
        specAccessMhPbs = DynamicSpecifications.bySearchFilter(filters.values(), AccessMhPbs.class);
        accessMhPbsList = accessMhPbsDao.findAll(specAccessMhPbs);
        if (accessMhPbsList != null && accessMhPbsList.size() > 0)
        {
            accessMhPbs = accessMhPbsList.get(0);
        } else
        {
            accessMhPbs = new AccessMhPbs();
            accessMhPbs.setAccessNo(accessMh.getAccessNo());
            accessMhPbs.setAircraftType(accessMh.getAircraftType());
        }
        workhour=ExcelUtil.getCellValue(row.getCell(1));
        if(workhour==null || workhour.equals("")||workhour.equals("TBD")){
            return accessMh;
        }
        try{
            bdWorkhour=new BigDecimal(workhour);
        }catch(Exception e){
            return accessMh;
        }
        bdWorkhour.setScale(1, BigDecimal.ROUND_HALF_UP);
        accessMhPbs.setOpenMh(bdWorkhour);
        workhour=ExcelUtil.getCellValue(row.getCell(2));
        if(workhour.equals("")||workhour.equals("TBD")){
            return accessMh;
        }
        try{
            bdWorkhour=new BigDecimal(workhour);
        }catch(Exception e){
            return accessMh;
        }
        bdWorkhour.setScale(1, BigDecimal.ROUND_HALF_UP);
        accessMhPbs.setCloseMh(bdWorkhour);
        accessMhPbs.setMajorZone(ExcelUtil.getCellValue(row.getCell(3)));
        accessMhPbs.setFuelTankPanel(ExcelUtil.getCellValue(row.getCell(4)));
        accessMhPbs.setLocationByZone(ExcelUtil.getCellValue(row.getCell(5)));
        accessMhPbsDao.save(accessMhPbs);
        return accessMhDao.save(accessMh);
    }
}
