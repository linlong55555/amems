package com.eray.thjw.otheraerocade.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.aerialmaterial.dao.InstockMapper;
import com.eray.thjw.aerialmaterial.dao.SecondmentMapper;
import com.eray.thjw.aerialmaterial.po.Expatriate;
import com.eray.thjw.aerialmaterial.po.Instock;
import com.eray.thjw.aerialmaterial.po.Outstock;
import com.eray.thjw.aerialmaterial.po.OutstockDetail;
import com.eray.thjw.aerialmaterial.po.Secondment;
import com.eray.thjw.aerialmaterial.service.ExpatriateService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.otheraerocade.dao.OtherAerocadeOutstockMapper;
import com.eray.thjw.otheraerocade.service.OtherAerocadeBorrowReturnOutstockService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 外飞行借入归还出库单
 * @author xu.yong
 *
 */
@Service("otherAerocadeBorrowReturnOutstockService")
public class OtherAerocadeBorrowReturnOutstockServiceImpl implements OtherAerocadeBorrowReturnOutstockService{

	@Resource
	private OtherAerocadeOutstockMapper otherAerocadeOutstockMapper;
	
	@Resource
	private InstockMapper instockMapper;
	
	@Resource
	private SecondmentMapper secondmentMapper;
	
	@Resource
	private ExpatriateService expatriateService;
	
	/**
	 * 根据ID查询 外飞行队 借入归还出库单
	 * @param id
	 * @return 
	 */
	public Outstock queryBorrowReturnOutstockById(String id){
		return this.otherAerocadeOutstockMapper.selectBorrowReturnById(id);
	}
	
	/**
	 * 查询 外飞行队 借入归还出库单
	 * @param entity
	 * @return
	 * @throws BusinessException 
	 */
	public Map<String, Object> queryBorrowReturnOutstock(BaseEntity entity) throws BusinessException{
		
		
		String id = (String)entity.getParamsMap().get("id");
		entity.getParamsMap().remove("id");
		
		User user = ThreadVarUtil.getUser();
		entity.getParamsMap().put("dprtcode", user.getJgdm());
		
		PageHelper.startPage(entity.getPagination());
		List<Outstock> list = this.otherAerocadeOutstockMapper.selectBorrowReturnPage(entity);
		
		long count = ((Page)list).getTotal();
		if(count > 0){
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){//验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					//在查询条件中增加ID
					BaseEntity entity1 = new BaseEntity();
					Map<String, Object> paramsMap = new HashMap<String, Object>(1);
					paramsMap.put("id", id);
					entity1.setParamsMap(paramsMap);
					List<Outstock> newRecordList = this.otherAerocadeOutstockMapper.selectBorrowReturnPage(entity1);
					if(newRecordList != null && newRecordList.size() == 1){
						//将记录放入结果集第一条
						list.add(0, newRecordList.get(0));
					}
				}
			}
		}else{
			list = new ArrayList<Outstock>(1);
			if(StringUtils.isNotBlank(id)){//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				//在查询条件中增加ID
				BaseEntity entity1 = new BaseEntity();
				Map<String, Object> paramsMap = new HashMap<String, Object>(1);
				paramsMap.put("id", id);
				entity1.setParamsMap(paramsMap);
				List<Outstock> newRecordList = this.otherAerocadeOutstockMapper.selectBorrowReturnPage(entity1);
				if(newRecordList != null && newRecordList.size() == 1){
					//将记录放入结果集第一条
					list.add(0, newRecordList.get(0));
					count = 1;
				}
			}
		}
		
		if(list != null && list.size() > 0){
			//查询本队入库单
			List<String> idList = new ArrayList<String>(list.size());
			for (Outstock outstock : list) {
				idList.add(outstock.getId());
			}
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("dprtcode", user.getJgdm());
			paramsMap.put("idList", idList);
			List<Instock> instockList = this.instockMapper.selectLendReturnByXgdjid(paramsMap);
			if(instockList != null && !instockList.isEmpty()){
				Map<String, Instock> instockMap = new HashMap<String, Instock>(instockList.size());
				for (Instock instock : instockList) {
					instockMap.put(instock.getXgdjid(), instock);
				}
				for (Outstock outstock : list) {
					if(instockMap.containsKey(outstock.getId())){
						outstock.setInstock(instockMap.get(outstock.getId()));
					}
				}
			}
		}
		
		return PageUtil.pack((int)count, list, entity.getPagination());
	}
	
	/**
	 * 查询 外飞行队 借入归还出库单航材清单
	 * @param id 出库单ID
	 * @return
	 */
	public List<OutstockDetail> queryBorrowReturnOutstockDetail(String id){
		return this.otherAerocadeOutstockMapper.selectBorrowReturnDetail(id);
	}
	
	/**
	 * 根据外飞行队出库航材分配借出航材
	 * @param map
	 * @return
	 * @throws BusinessException
	 */
	public List<Expatriate> getExpatriateByBJ(Map<String, Object> map) throws BusinessException{
		String fxdid = (String)map.get("fxdid");
		if(StringUtils.isBlank(fxdid)){
			throw new BusinessException("数据异常");
		}
		User user = ThreadVarUtil.getUser();
		//根据借调对象编号查询借调对象
		Secondment secondment = this.secondmentMapper.selectByBH(null, fxdid);
		if(secondment == null){
			throw new BusinessException("借调对象不存在");
		}
		return this.expatriateService.getLendByReturn(user.getJgdm(), secondment.getId(), (List<Map<String, Object>>)map.get("bjs"));
	}
	
	
}
