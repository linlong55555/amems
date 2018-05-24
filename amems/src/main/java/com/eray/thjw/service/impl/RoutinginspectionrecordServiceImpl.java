package com.eray.thjw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.dao.RoutinginspectionrecordDetailMapper;
import com.eray.thjw.dao.RoutinginspectionrecordMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.Routinginspectionrecord;
import com.eray.thjw.po.RoutinginspectionrecordDetail;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.OrderAttachmentService;
import com.eray.thjw.service.RoutinginspectionrecordService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
@Service
public class RoutinginspectionrecordServiceImpl implements RoutinginspectionrecordService{

	@Autowired
	private RoutinginspectionrecordMapper routinginspectionrecordMapper;
	@Autowired
	private RoutinginspectionrecordDetailMapper routinginspectionrecordDetailMapper;
	@Autowired
	private OrderAttachmentService orderAttachmentService;
	@Autowired
	private CommonRecService commonRecService;
	@Autowired
	private SaibongUtilService saibongUtilService;     
	
	/**
	 * @author sunji
	 * @description 条件查询
	 * @develop date 2016.08.15
	 */
	public List<Routinginspectionrecord> queryAll(
			Routinginspectionrecord routinginspectionrecord) {
		return routinginspectionrecordMapper.queryAll(routinginspectionrecord);
	}

	/**
	 * @author sunji
	 * @description 添加
	 * @param inster
	 * @return int
	 * @develop date 2016.08.15
	 */
	public String inster(Routinginspectionrecord routinginspectionrecord) {
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		//添加巡检记录单
		String routinginspectionrecordId=UUID.randomUUID().toString();
		String xjbh;
		try {
			xjbh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JCBZXJ.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}
		routinginspectionrecord.setId(routinginspectionrecordId);
		routinginspectionrecord.setXjbh(xjbh);
		routinginspectionrecord.setZt(1);
		routinginspectionrecord.setDprtcode(user.getJgdm());
		routinginspectionrecord.setWhbmid(user.getBmdm());
		routinginspectionrecord.setWhrid(user.getId());
		routinginspectionrecord.setWhsj(new Date());
		routinginspectionrecordMapper.insertSelective(routinginspectionrecord);
		//添加历史数据表
		commonRecService.write(routinginspectionrecordId, TableEnum.B_J_002, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,routinginspectionrecord.getId());
		//添加明细表
		List<RoutinginspectionrecordDetail> detailList=routinginspectionrecord.getDetailList();
		for (RoutinginspectionrecordDetail routinginspectionrecordDetail : detailList) {
			String routinginspectionrecordDetailId=UUID.randomUUID().toString();
			routinginspectionrecordDetail.setId(routinginspectionrecordDetailId);
			routinginspectionrecordDetail.setMainid(routinginspectionrecordId);
			routinginspectionrecordDetail.setWhbmid(user.getBmdm());
			routinginspectionrecordDetail.setWhrid(user.getId());
			routinginspectionrecordDetail.setWhsj(new Date());
			routinginspectionrecordDetailMapper.insertSelective(routinginspectionrecordDetail);
			//添加历史数据表
			commonRecService.write(routinginspectionrecordDetailId, TableEnum.B_J_00201, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,routinginspectionrecord.getId());
			//添加附件表
			List<OrderAttachment> OrderAttachmentList=routinginspectionrecordDetail.getOrderAttachmentList();
			for (OrderAttachment orderAttachment : OrderAttachmentList) {
				String id = UUID.randomUUID().toString();//获取随机的uuid
				orderAttachment.setId(id);
				orderAttachment.setMainid(routinginspectionrecordDetailId);
				orderAttachment.setCzbmid(user.getBmdm());
				orderAttachment.setCzsj(new Date());
				orderAttachment.setCzrid(user.getId());
				orderAttachment.setDprtcode(user.getJgdm());
				orderAttachment.setYxzt(1);
				orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
				orderAttachmentService.insertSelective(orderAttachment);
				//添加历史数据表
				commonRecService.write(id, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,routinginspectionrecord.getId());
			}
			
		}
		return routinginspectionrecordId;
	}

	/**
	 * @author sunji
	 * @description 根据id查询数据
	 * @param id
	 * @return Routinginspectionrecord
	 * @develop date 2016.08.15
	 */
	public Routinginspectionrecord selectByPrimaryKey(String id) {
		return routinginspectionrecordMapper.selectByPrimaryKey(id);
	}

	/**
	 * @author sunji
	 * @description 修改
	 * @param inster
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void update(Routinginspectionrecord routinginspectionrecord) throws BusinessException{
		//获取登入user
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		
		int []i={1};
		boolean b=this.verification(routinginspectionrecord.getId(), i);
		if(b){
			throw new BusinessException("该巡检记录单已删除，请刷新后再进行操作");
		}
		
		//修改巡检记录单
		String routinginspectionrecordId=routinginspectionrecord.getId();
		routinginspectionrecord.setWhbmid(user.getBmdm());
		routinginspectionrecord.setWhrid(user.getId());
		routinginspectionrecord.setWhsj(new Date());
		routinginspectionrecordMapper.updateByPrimaryKeySelective(routinginspectionrecord);
		//添加历史数据表
		commonRecService.write(routinginspectionrecordId, TableEnum.B_J_002, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE,routinginspectionrecordId);
		//删除附件
		List<String> attachmentDeleteIds=routinginspectionrecord.getAttachmentDeleteIds();
		if(attachmentDeleteIds.size()>0){
			//添加历史数据表
			commonRecService.write("id",attachmentDeleteIds, TableEnum.D_011, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,routinginspectionrecordId);//插入日志
			for (String id : attachmentDeleteIds) {
				//进行删除
				orderAttachmentService.deleteByPrimaryKey(id);
			}
		}
		
		//修改明细表
		List<RoutinginspectionrecordDetail> detailList=routinginspectionrecord.getDetailList();
		for (RoutinginspectionrecordDetail routinginspectionrecordDetail : detailList) {
			String routinginspectionrecordDetailId=routinginspectionrecordDetail.getId();
			routinginspectionrecordDetail.setWhbmid(user.getBmdm());
			routinginspectionrecordDetail.setWhrid(user.getId());
			routinginspectionrecordDetail.setWhsj(new Date()); 
			routinginspectionrecordDetailMapper.updateByPrimaryKeySelective(routinginspectionrecordDetail);
			//添加历史数据表
			commonRecService.write(routinginspectionrecordDetailId, TableEnum.B_J_00201, user.getId(), czls,LogOperationEnum.EDIT,UpdateTypeEnum.UPDATE,routinginspectionrecordId);
			//添加附件表
			List<OrderAttachment> OrderAttachmentList=routinginspectionrecordDetail.getOrderAttachmentList();
			for (OrderAttachment orderAttachment : OrderAttachmentList) {
					String orderAttachmentId=orderAttachment.getId();
				if(null!=orderAttachmentId&&!"".equals(orderAttachmentId)  ){
					orderAttachment.setMainid(routinginspectionrecordDetailId);
					orderAttachment.setCzbmid(user.getBmdm());
					orderAttachment.setCzsj(new Date());
					orderAttachment.setCzrid(user.getId());
					orderAttachment.setDprtcode(routinginspectionrecord.getDprtcode());
					orderAttachment.setYxzt(1);
					orderAttachment.setHzm(orderAttachment.getNbwjm().substring(orderAttachment.getNbwjm().lastIndexOf(".")+1,orderAttachment.getNbwjm().length()));
					orderAttachmentService.insertSelective(orderAttachment);
					//添加历史数据表
					commonRecService.write(orderAttachmentId, TableEnum.D_011, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,routinginspectionrecordId);
				}
			}
			
		}
		
	}

	/**
	 * @author sunji
	 * @description 作废
	 * @param inster
	 * @return int
	 * @develop date 2016.08.15
	 */
	public void invalid(Routinginspectionrecord routinginspectionrecord) throws BusinessException{
		User user=ThreadVarUtil.getUser();
		String czls=UUID.randomUUID().toString();
		int []i={1};
		boolean b=this.verification(routinginspectionrecord.getId(), i);
		if(b){
			throw new BusinessException("该巡检记录单已删除，请刷新后再进行操作");
		}
		
		routinginspectionrecord.setZt(0);
		routinginspectionrecordMapper.updateByPrimaryKeySelective(routinginspectionrecord);
		commonRecService.write(routinginspectionrecord.getId(), TableEnum.B_J_002, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE,routinginspectionrecord.getId());
	}
	
	public boolean verification(String id, int [] i){
		boolean b=true;
		Routinginspectionrecord routinginspectionrecord=routinginspectionrecordMapper.selectByPrimaryKey(id);
		for (int a = 0; a < i.length; a++) {
			
			if(routinginspectionrecord.getZt()==i[a]){
				b=false;
			}
		}
		
		return b;
	}

}
