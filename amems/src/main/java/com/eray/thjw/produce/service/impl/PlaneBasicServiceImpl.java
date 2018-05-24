package com.eray.thjw.produce.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.po.Aircraftinfo;
import com.eray.thjw.produce.po.PlaneBasic;
import com.eray.thjw.produce.service.PlaneBasicService;
import com.eray.thjw.produce.dao.PlaneBasicMapper;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
/** 
 * @Description 
 * @CreateTime 2017-12-5 下午5:10:20
 * @CreateBy 孙霁	
 */
@Service("planeBasicServiceImpl")
public class PlaneBasicServiceImpl implements PlaneBasicService{

	@Resource
	private PlaneBasicMapper planeBasicMapper;


	/**
	 * 
	 * @Description 添加飞机基本信息表（D_010）,先查询数据库，如果有，那么进行修改操作，如果没有，进行添加
	 * @CreateTime 2017-12-5 下午5:09:12
	 * @CreateBy 孙霁
	 * @param planeBasic
	 */
	@Override
	public void insertUpdate(Aircraftinfo aircraftinfo) throws BusinessException  {
		// 获取登入user
		User user = ThreadVarUtil.getUser();
		PlaneBasic planeBasic = new PlaneBasic();
		planeBasic.setFjzch(aircraftinfo.getFjzch());
		planeBasic.setXlh(aircraftinfo.getXlh());
		planeBasic.setFjjx(aircraftinfo.getFjjx());
		planeBasic.setFjms(aircraftinfo.getBz());
		planeBasic.setBzm(aircraftinfo.getBzm());
		planeBasic.setDprtcode(aircraftinfo.getDprtcode());
		planeBasic.setZt(DelStatus.TAKE_EFFECT.getId());
		planeBasic.setWhbmid(user.getBmdm());
		planeBasic.setWhrid(user.getId());
		//1.查询数据库是否拥有改数据
		int count = planeBasicMapper.selectByFjzchAndDprtcode(planeBasic);
		//2.判断是否有数据，如果有，进行修改操作，如果没有，进行添加操作
		if(count > 0){
			planeBasicMapper.updateByPrimaryKeySelective(planeBasic);
		}else{
			planeBasicMapper.insertSelective(planeBasic);
		}
		
	}
	
	/**
     * 
     * @Description 获取组织机构下所有有效的飞机
     * @CreateTime 2017年12月13日 下午2:50:50
     * @CreateBy 岳彬彬
     * @param dprtcode
     * @return
     */
	@Override
	public List<PlaneBasic> getFjList(PlaneBasic plane) {

		return planeBasicMapper.getFjList(plane);
	}

}
