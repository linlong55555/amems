package com.eray.pbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.eray.pbs.dao.StageDescriptionDao;
import com.eray.pbs.po.StageDescription;

@Component
@Transactional(readOnly = true)
public class StageDecriptionService
{
	@Autowired
	private StageDescriptionDao stageDescriptionDao;

	public Iterable<StageDescription> findAll()
	{
		Sort sort=new Sort(Direction.ASC, "stage");
		return stageDescriptionDao.findAll(sort);
	}

	@Transactional(readOnly = false)
	public StageDescription save(StageDescription stageDescription)
	{
		return stageDescriptionDao.save(stageDescription);
	}
}
