package com.eray.thjw.aerialmaterial.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.SecondmentMapper;
import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.service.SecondmentService;


/**
 * 借调对象业务逻辑
 * @author xu.yong
 *
 */
@Service("secondmentService")
public class SecondmentServiceImpl implements SecondmentService{

	private  final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	private SecondmentMapper secondmentMapper;
	
	/**
	 * 根据借调对象类型查询
	 * @return
	 */
	public List<Secondment> queryByType(Secondment secondment){
		//当借调对象类型是飞行队时 不需要附加组织机构条件查询 
//		if(secondment.getJddxlx() != null && secondment.getJddxlx().intValue() == SecondmentTypeEnum.AEROCADE.getId().intValue()){
//			secondment.setDprtcode(null);
//		}
		return this.secondmentMapper.selectByType(secondment);
	}

	@Override
	public List<Secondment> selectSecondmentList(Secondment record) {
		return secondmentMapper.selectSecondmentList(record);
	}

	@Override
	public int selectCount(String jddxbh) {
		return secondmentMapper.selectCount(jddxbh);
	}

	@Override
	public int insertSelective(Secondment record) {
		return secondmentMapper.insertSelective(record);
	}

	@Override
	public Secondment selectById(String id) {
		return secondmentMapper.selectById(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Secondment record) {
		return secondmentMapper.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public List<Secondment> queryByDprtcode(String dprtcode) {
		return secondmentMapper.queryByDprtcode(dprtcode);
	}

	@Override
	public List<Secondment> queryOtherOrg(String jgdm) {
		return secondmentMapper.queryOtherOrg(jgdm);
	}

	/**
	 * 查询所有的借调对象
	 */
	@Override
	public List<Secondment> queryAll(Secondment secondment) {
		return secondmentMapper.queryAll(secondment);
	}
	
}
