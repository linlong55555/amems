package com.eray.thjw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.dao.PlaneBaseMapper;
import com.eray.thjw.po.PlaneBase;
import com.eray.thjw.po.User;
import com.eray.thjw.service.PlaneBaseService;
import com.eray.thjw.util.ThreadVarUtil;


@Service
public class PlaneBaseServiceImpl implements PlaneBaseService {
	
	
	@Resource
	private PlaneBaseMapper planeBaseMapper;

	@Override
	public List<PlaneBase> findAllBase(PlaneBase planeBase) throws RuntimeException {
		PlaneBase pb=new PlaneBase();
		User user=ThreadVarUtil.getUser();
		pb.setDprtcode(user.getJgdm());
		return planeBaseMapper.findAllBase(planeBase);
	}
	
	@Override
	public List<PlaneBase> findBaseByDprtcode(String dprtcode) throws RuntimeException {
		return planeBaseMapper.findBaseByDprtcode(dprtcode);
	}
	
}
