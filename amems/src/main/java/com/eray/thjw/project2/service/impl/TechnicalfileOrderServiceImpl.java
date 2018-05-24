package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.TechnicalfileOrderMapper;
import com.eray.thjw.project2.po.Airworthiness;
import com.eray.thjw.project2.po.Technical;
import com.eray.thjw.project2.po.TechnicalfileOrder;
import com.eray.thjw.project2.service.TechnicalfileOrderService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.AirworthinessOperationEnum;
@Service
public class TechnicalfileOrderServiceImpl implements TechnicalfileOrderService{

	@Autowired
	private TechnicalfileOrderMapper technicalfileOrderMapper;
	@Autowired
	private CommonRecService commonRecService;
	/**
	 * 
	 * @Description 添加技术指令表
	 * @CreateTime 2017-8-16 下午5:12:22
	 * @CreateBy 孙霁
	 * @param technicalfileOrder
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param user
	 * @return
	 * @throws BusinessException
	 */
	public TechnicalfileOrder insert(TechnicalfileOrder technicalfileOrder, String mainid, String czls,
			String zdid, User user, LogOperationEnum cz ) throws BusinessException {
		technicalfileOrder.setMainid(mainid);
		technicalfileOrder.setZt(1);
		technicalfileOrder.setId(UUID.randomUUID().toString());
		technicalfileOrder.setWhrid(user.getId());
		technicalfileOrder.setWhdwid(user.getBmdm());
		technicalfileOrder.setWhsj(new Date());
		technicalfileOrderMapper.insertSelective(technicalfileOrder);
		commonRecService.write(technicalfileOrder.getId(), TableEnum.B_G2_00001, user.getId(),czls,cz,UpdateTypeEnum.SAVE,zdid);
		return technicalfileOrder;
	
	}
	/**
	 * 
	 * @Description 根据适航指令ids获取数据
	 * @CreateTime 2017-8-16 下午5:12:38
	 * @CreateBy 孙霁
	 * @param arids
	 * @return
	 */
	public List<TechnicalfileOrder> queryAllByMainids(List<String> arids) {
		return technicalfileOrderMapper.queryAllByMainids(arids);
	}
	/**
	 * 
	 * @Description 修改技术指令表
	 * @CreateTime 2017-8-16 下午5:12:08
	 * @CreateBy 孙霁
	 * @param technicalfileOrder
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param user
	 * @param cz
	 * @throws BusinessException
	 */
	public void update(TechnicalfileOrder technicalfileOrder, String mainid,
			String czls, String zdid, User user, LogOperationEnum cz) throws BusinessException {
		technicalfileOrder.setZt(EffectiveEnum.YES.getId());
		technicalfileOrderMapper.updateByPrimaryKeySelective(technicalfileOrder);
		commonRecService.write(technicalfileOrder.getId(), TableEnum.B_G2_00001, user.getId(), czls, cz, UpdateTypeEnum.UPDATE, zdid);
		
	}
	/**
	 * 
	 * @Description 删除技术指令表
	 * @CreateTime 2017-8-16 下午5:11:16
	 * @CreateBy 孙霁
	 * @param technicalfileOrderId
	 * @param czls
	 * @param zdid
	 * @param user
	 * @param cz
	 * @throws BusinessException
	 */
	public void delete(String technicalfileOrderId, String czls,
			String zdid, User user,LogOperationEnum cz) throws BusinessException {
		technicalfileOrderMapper.deleteByPrimaryKey(technicalfileOrderId);
		commonRecService.write(technicalfileOrderId, TableEnum.B_G2_00001, user.getId(),czls, cz, UpdateTypeEnum.DELETE, zdid);
		
	}
	/**
	 * 
	 * @Description 根据组适航性资料id删除数据
	 * @CreateTime 2017-8-12 下午2:43:31
	 * @CreateBy 孙霁
	 * @param technicalfileOrderId
	 * @param mainid
	 * @param czls
	 * @param zdid
	 * @param dprtcode
	 * @param user
	 * @param cz
	 * @throws BusinessException
	 */
	@Override
	public void deleteByMainid(String mainid,
			String czls, String zdid, User user, LogOperationEnum cz) throws BusinessException {
		technicalfileOrderMapper.deleteByMainid(mainid);
		List<String> minidList = new ArrayList<String>();
		minidList.add(mainid);
		commonRecService.write("mainid",minidList, TableEnum.B_G2_00001, user.getId(),czls,cz,UpdateTypeEnum.DELETE,zdid);
	}
	
	/**
	 * 
	 * @Description 对适用性机型 进行增删改操作
	 * @CreateTime 2017-8-16 上午11:11:41
	 * @CreateBy 孙霁
	 * @param TechnicalfileOrderList
	 * @throws BusinessException
	 */
	@Override
	public void updateTechnicalfileOrder(Airworthiness airworthiness, String czls, String zdid, User user, LogOperationEnum cz)throws BusinessException {
		
		//根据airworthinessId查询技术评估单
		List<TechnicalfileOrder> technicalfileOrderList = technicalfileOrderMapper.queryAllByMainid(airworthiness.getId());
		//判断TechnicalfileOrderList是否为空
		if(airworthiness.getTechnicalfileOrderList() != null && airworthiness.getTechnicalfileOrderList().size() > 0){
			for (TechnicalfileOrder technicalfileOrder : airworthiness.getTechnicalfileOrderList()) {
				//判断 状态 为add==添加 ， delete==删除 ，update==修改
				if(technicalfileOrder.getZt() == AirworthinessOperationEnum.ADD.getId()){
					//判断id是否为空，如果不为空，进行修改操作，否则进行添加操作
					if(technicalfileOrder.getId() != null && !"".equals(technicalfileOrder.getId())){
						//修改
						this.update(technicalfileOrder, airworthiness.getId(), czls, zdid, user, cz);
					}else{
						boolean bOption = true;
						//判断是否机型已存在
						for (TechnicalfileOrder oldTech : technicalfileOrderList) {
							if(oldTech.getFjjx().equals( technicalfileOrder.getFjjx())){
								bOption = false;
							}
						}
						if(bOption){
							//添加
							this.insert(technicalfileOrder, airworthiness.getId(), czls, zdid, user, cz);
						}
					}
					
				} if(technicalfileOrder.getZt() == AirworthinessOperationEnum.DELETE.getId()){
					//删除
					this.delete(technicalfileOrder.getId(), czls, zdid, user, cz);
					
				}if(technicalfileOrder.getZt() == AirworthinessOperationEnum.UPDATE.getId()){
					//修改
					this.update(technicalfileOrder, airworthiness.getId(), czls, zdid, user, cz);
				}
			}
		}
		
	}
	/**
	 * 
	 * @Description 修改评估人
	 * @CreateTime 2017年11月20日 上午10:30:37
	 * @CreateBy 林龙
	 * @param jswjid
	 * @param jx
	 * @param pgrid
	 */
	@Override
	public void updatetBypgr(String jswjid, String jx, String pgrid) {
		technicalfileOrderMapper.updatetBypgr(jswjid,jx,pgrid);
	}

}
