package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.MaterialHistoryMapper;
import com.eray.thjw.aerialmaterial.po.MaterialHistory;
import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.service.MaterialHistoryService;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

@Service
public class MaterialHistoryServiceImpl implements MaterialHistoryService {
	
	@Resource
	private MaterialHistoryMapper materialHistoryMapper;

	@Override
	public void insert(MaterialHistory materialHistory) {
		materialHistoryMapper.insertSelective(materialHistory);
		
	}

	@Override
	public List<MaterialHistory> selectKey(String  kcid, String dprt) {
		return materialHistoryMapper.selectKey(kcid,dprt);
	}
	
	/**
	 * @author liub
	 * @description 根据id,kcid修改部件履历表
	 * @param id,kcid
	 * @return int
	 * @develop date 2016.11.01
	 */
	@Override
	public int updateByStock(String id,String kcid) throws RuntimeException{
		return materialHistoryMapper.updateByStock(id, kcid);
	}

	@Override
	public MaterialHistory selectByPrimaryKey(String kcllid)
			throws RuntimeException {
		// TODO Auto-generated method stub
		return materialHistoryMapper.selectByPrimaryKey(kcllid);
	}
	/**
	 * @author meizhiliang
	 * @description 增加库存移库履历表
	 * @param id,kcid
	 * @return int
	 * @develop date 2016.11.01
	 */
	@Override
	public int insertSelective(MaterialHistory materialHistory) {
		return materialHistoryMapper.insertSelective(materialHistory);
	};
	
	/**
	 * @author liub
	 * @description 修改部件履历
	 * @param materialHistory
	 * @return int
	 * @develop date 2016.11.25
	 */
	@Override
	public int updateByPrimaryKeySelective(MaterialHistory materialHistory) {
		return materialHistoryMapper.updateByPrimaryKeySelective(materialHistory);
	}

	/**
	 * @author sunji
	 * @description 根据销毁id获取数据
	 * @param materialHistory
	 * @return list
	 */
	public List<MaterialHistory> queryChoStock(String id) {
		return materialHistoryMapper.queryChoStock(id);
	}

	/**
	 * 
	 * @Description 库存履历列表
	 * @CreateTime 2018年3月21日 下午3:50:37
	 * @CreateBy 林龙
	 * @param materialHistory
	 * @return
	 * @throws RuntimeException
	 */
	@Override
	public Map<String, Object> queryAllPageList(MaterialHistory materialHistory)throws RuntimeException {
		PageHelper.startPage(materialHistory.getPagination());
		List<MaterialHistory> list = materialHistoryMapper.queryAllPageList(materialHistory);
		return PageUtil.pack4PageHelper(list, materialHistory.getPagination());
	};
}
