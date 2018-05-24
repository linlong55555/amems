package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.ProductionOrderPlaneMapper;
import com.eray.thjw.project2.po.ProductionOrder;
import com.eray.thjw.project2.po.ProductionOrderPlane;
import com.eray.thjw.project2.service.ProductionOrderPlaneService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

/** 
 * @Description 生产指令飞机适用性服务实现类
 * @CreateTime 2018年5月2日 上午9:43:28
 * @CreateBy 徐勇	
 */
@Service
public class ProductionOrderPlaneServiceImpl implements ProductionOrderPlaneService {

	@Resource
	private ProductionOrderPlaneMapper productionOrderPlaneMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存生产指令飞机适用性
	 * @CreateTime 2018年5月4日 上午9:48:53
	 * @CreateBy 韩武
	 * @param order
	 */
	@Override
	public void save(ProductionOrder order) {
		User user = ThreadVarUtil.getUser();
		
		// 删除维修项目对应飞机关系
		delete(order);		
		
		// 保存维修项目对应飞机关系
		if ("-".equals(order.getSyx()) && order.getPlanes() != null){
			
			List<String> idList = new ArrayList<String>();
			for (ProductionOrderPlane plane : order.getPlanes()) {
				
				plane.setId(UUID.randomUUID().toString());
				plane.setMainid(order.getId());
				plane.setWhrid(user.getId());
				plane.setWhsj(new Date());
				plane.setWhdwid(user.getBmdm());
				idList.add(plane.getId());
				productionOrderPlaneMapper.insertSelective(plane);
			}
			if(!idList.isEmpty()){
				// 保存历史记录信息
				commonRecService.write("id", idList, TableEnum.B_G2_01402, user.getId(), 
						order.getCzls(), order.getLogOperationEnum(), UpdateTypeEnum.SAVE, order.getId());
			}
		}
	}

	/**
	 * @Description 删除生产指令飞机适用性
	 * @CreateTime 2018年5月4日 上午9:56:28
	 * @CreateBy 韩武
	 * @param order
	 */
	@Override
	public void delete(ProductionOrder order) {
		User user = ThreadVarUtil.getUser();
		List<String> delList = new ArrayList<String>();
		delList.add(order.getId());
		
		// 保存历史记录信息
		commonRecService.write("mainid", delList, TableEnum.B_G2_01402, user.getId(), 
				order.getCzls(), order.getLogOperationEnum(), UpdateTypeEnum.DELETE, order.getId());
		
		// 删除维修项目对应飞机关系
		productionOrderPlaneMapper.deleteByMainid(order.getId());
	}

}
