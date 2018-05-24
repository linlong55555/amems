package com.eray.pbs.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.eray.pbs.dao.RevisionDao;
import com.eray.pbs.dao.RevisionPbsDao;
import com.eray.pbs.po.Revision;
import com.eray.pbs.po.RevisionPbs;
import com.eray.util.jpa.DynamicSpecifications;
import com.eray.util.jpa.SearchFilter;

@Component
@Transactional(readOnly = true)
public class RevisionService
{
    @Autowired
    private RevisionDao revisionDao;
    @Autowired
    private RevisionPbsDao revisionPbsDao;

    private Map<String, Object> searchParams = new HashMap<String, Object>();
    private Map<String, SearchFilter> filters;
    private Specification<Revision> specRevision;
    private PageRequest pageRequest;
    private Page<Revision> page;

    private Specification<RevisionPbs> specRevisionPbs;
    private List<RevisionPbs> revisionPbsList;
    private RevisionPbs revisionPbs;
    
    private DateFormat sapDF = new SimpleDateFormat("yyyyMMddHHmmss");

    public List<Revision> findAll(Map<String, Object> criteria)
    {
        return revisionDao.findAll(buildRevisionSpecification(criteria));
    }

    private Specification<Revision> buildRevisionSpecification(Map<String, Object> criteria)
    {
        filters = SearchFilter.parse(criteria);
        specRevision = DynamicSpecifications.bySearchFilter(filters.values(), Revision.class);
        return specRevision;
    }

    @Transactional(readOnly = false)
    public Revision save(Revision revision, String[] para)
    {
        if (revision == null || para.length<12)
        {
            return revision;
        }
        searchParams.clear();
        searchParams.put("EQ_rid", revision.getRevisionId());
        filters = SearchFilter.parse(searchParams);
        specRevisionPbs = DynamicSpecifications.bySearchFilter(filters.values(), RevisionPbs.class);
        revisionPbsList = revisionPbsDao.findAll(specRevisionPbs);
        if (revisionPbsList != null && revisionPbsList.size() > 0)
        {
            revisionPbs = revisionPbsList.get(0);
        } else
        {
            revisionPbs = new RevisionPbs();
            revisionPbs.setRid(revision.getRevisionId());
        }
        revisionPbs.setDescription(para[3]);
        revisionPbs.setStatus(para[4]);
        revisionPbs.setAircraftTailNumber(para[5]);
        revisionPbs.setHangar(para[6]);
        revisionPbs.setAircraftModel(para[7]);
        revisionPbs.setStyle(para[8]);
        revisionPbs.setAircraftDescription(para[9]);
        if(para.length>13){
        	revisionPbs.setNetwork(para[12]);
        	revisionPbs.setAircraftType(para[13]);
        }
        try
        {
            revisionPbs.setPlannedStartDate(new Timestamp((sapDF.parse(para[1] + para[10])).getTime()));
            revisionPbs.setPlannedFinishDate(new Timestamp((sapDF.parse(para[2] + para[11])).getTime()));
            revisionPbs.setActualStartDate(revisionPbs.getPlannedStartDate());
        } catch (ParseException e)
        {
            return revision;
        }
        revisionPbsDao.save(revisionPbs);
        return revisionDao.save(revision);
    }

    public Revision findLast(String revisionId)
    {
        if (revisionId == null || revisionId.equals(""))
        {
            return null;
        }
        searchParams.clear();
        searchParams.put("EQ_revisionId", revisionId);
        filters = SearchFilter.parse(searchParams);
        specRevision = DynamicSpecifications.bySearchFilter(filters.values(), Revision.class);
        pageRequest = new PageRequest(0, 1, new Sort(Direction.DESC, "id"));
        page = revisionDao.findAll(specRevision, pageRequest);
        if (page.getContent() != null && page.getContent().size() > 0)
        {
            return page.getContent().get(0);
        }
        return null;
    }

	public Iterable<RevisionPbs> findRevisionPbsAll()
    {
	    return revisionPbsDao.findAll();
    }

	@Transactional(readOnly = false)
	public RevisionPbs savePbs(RevisionPbs revisionPbs)
    {
	    return revisionPbsDao.save(revisionPbs);
    }
}
