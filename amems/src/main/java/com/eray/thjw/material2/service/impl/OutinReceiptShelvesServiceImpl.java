package com.eray.thjw.material2.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.material2.dao.OutinReceiptShelvesMapper;
import com.eray.thjw.material2.po.OutinReceiptShelves;
import com.eray.thjw.material2.service.OutinReceiptShelvesService;

/**
 * @Description 航材收货单-上架serivce
 * @CreateTime 2018年3月12日 上午9:51:26
 * @CreateBy 韩武
 */
@Service("outinReceiptShelvesService")
public class OutinReceiptShelvesServiceImpl implements OutinReceiptShelvesService {
	
	@Resource
	private OutinReceiptShelvesMapper outinReceiptShelvesMapper;

	/**
	 * @Description 保存航材收货单-上架信息
	 * @CreateTime 2018年3月12日 上午9:49:32
	 * @CreateBy 韩武
	 * @param mainid
	 * @param details
	 */
	@Override
	public void save(String mainid, List<OutinReceiptShelves> shelves) {
		if(shelves != null && !shelves.isEmpty()){
			for (OutinReceiptShelves shelf : shelves) {
				if (StringUtils.isBlank(shelf.getId())){	// 新增收货单-上架信息
					shelf.setId(UUID.randomUUID().toString());
					shelf.setMainid(mainid);
					outinReceiptShelvesMapper.insertSelective(shelf);
				} else {	// 修改收货单-上架信息
					outinReceiptShelvesMapper.updateByReceiptShelves(shelf);
				}
			}
		}
	}

	/**
	 * @Description 根据收货单id删除收货单-上架信息
	 * @CreateTime 2018年3月12日 上午9:50:15
	 * @CreateBy 韩武
	 * @param shdid
	 */
	@Override
	public void deleteNotExistByMainid(String mainid,
			List<OutinReceiptShelves> shelves) {
		outinReceiptShelvesMapper.deleteNotExistByMainid(mainid, shelves);
	}
	
}