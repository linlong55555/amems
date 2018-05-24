package com.eray.thjw.aerialmaterial.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.aerialmaterial.dao.BorrowApplyDetailMapper;
import com.eray.thjw.aerialmaterial.dao.BorrowApplyMapper;
import com.eray.thjw.aerialmaterial.dao.HCMainDataMapper;
import com.eray.thjw.aerialmaterial.po.BorrowApply;
import com.eray.thjw.aerialmaterial.po.BorrowApplyDetail;
import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.aerialmaterial.service.BorrowApplyService;
import com.eray.thjw.aerialmaterial.service.OtherHCMainDataService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.ReserveStatusEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.aerialmaterial.ContractStatusEnum;
import enu.common.EffectiveEnum;

/**
 * 借入申请
 * @author xu.yong
 *
 */
@Service("borrowApplyService")
public class BorrowApplyServiceImpl implements BorrowApplyService {
	
	@Resource
	private BorrowApplyMapper borrowApplyMapper;
	
	@Resource
	private HCMainDataMapper hCMainDataMapper;
	
	@Resource
	private OtherHCMainDataService otherHCMainDataService;
	
	@Resource
	private BorrowApplyDetailMapper borrowApplyDetailMapper;
	
	@Resource
	private SaibongUtilService saibongUtilService;
	
	@Resource
	private CommonRecService commonRecService;
	/**
	 * 查询待入库借入申请    申请单状态为 2提交、3核实
	 * @param baseEntity
	 * @return
	 */
	public Map<String, Object> queryPage4Instock(BaseEntity baseEntity){
		PageHelper.startPage(baseEntity.getPagination());
		List<BorrowApply> list = this.borrowApplyMapper.select4InstockPage(baseEntity);
		return PageUtil.pack4PageHelper(list, baseEntity.getPagination());
	}

	@Override
	public List<BorrowApply> queryAllPageListjie(BorrowApply borrowApply) throws RuntimeException{
		return borrowApplyMapper.queryAllPageListjie(borrowApply);
	}

	public String query(BorrowApply borrowApply){
		List<HCMainData> hCMainDatas=otherHCMainDataService.selectByIds();
		return this.add(borrowApply, hCMainDatas);
	}

	@Override
	public String add(BorrowApply borrowApply, List<HCMainData> hCMainDatas) {
		
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		String sqdh;
		try {
			sqdh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.JRSQ.getName());
		} catch (SaibongException e) {
			throw new RuntimeException(e);
		}		
		borrowApply.setId(id);
		borrowApply.setSqdh(sqdh);
		borrowApply.setDprtcode(user.getJgdm());
		borrowApply.setSqrid(user.getId());
		borrowApply.setSqdwid(user.getBmdm());
		borrowApply.setZdbmid(user.getBmdm());
		borrowApply.setZdrid(user.getId());
		borrowApply.setZdsj(new Date());
		borrowApplyMapper.insertSelective(borrowApply);
		
		//增加借入航材
		for (BorrowApplyDetail borrowApplyDetail : borrowApply.getReserveDetailList()) {
			
		
				HCMainData hCMainData=new HCMainData();
				hCMainData.setBjh(borrowApplyDetail.getBjh());
				hCMainData.setDprtcode(user.getJgdm());
				List<HCMainData> list=hCMainDataMapper.checkMaterial(hCMainData);//查询本航材基础数据是否有
			
				if(list.size()>0){
					borrowApplyDetail.setBjid(list.get(0).getId());
				}else{
					if(borrowApplyDetail.getState().equals("OtherStock")){
					for (HCMainData hcMainData : hCMainDatas) {
						if(borrowApplyDetail.getBjid().equals(hcMainData.getId())){
							
							UUID uuid3 = UUID.randomUUID();//获取随机的uuid
							hCMainData.setId(uuid3.toString());
							hCMainData.setBjh(hcMainData.getBjh());
							hCMainData.setDprtcode(user.getJgdm());
							hCMainData.setZwms(hcMainData.getZwms());
							hCMainData.setYwms(hcMainData.getYwms());
							hCMainData.setJldw(hcMainData.getJldw());
							hCMainData.setCjjh(hcMainData.getCjjh());
							hCMainData.setZjh(hcMainData.getZjh());
							hCMainData.setHcjz(hcMainData.getHcjz());
							hCMainData.setGljb(hcMainData.getGljb());
							hCMainData.setSxkz(hcMainData.getSxkz());
							hCMainData.setHclx(hcMainData.getHclx());
							hCMainData.setHclxEj(hcMainData.getHclxEj());
							hCMainData.setIsMel(hcMainData.getIsMel());
							hCMainData.setMinKcl(hcMainData.getMinKcl());
							hCMainData.setMaxKcl(hcMainData.getMaxKcl());
							hCMainData.setBz(hcMainData.getBz());
							hCMainData.setGg(hcMainData.getGg());
							hCMainData.setXingh(hcMainData.getXingh());
							hCMainData.setXh(hcMainData.getXingh());
							hCMainData.setZt(hcMainData.getZt());
							hCMainData.setBmid(hcMainData.getBjh());
							hCMainData.setCjrid(user.getId());
							hCMainData.setCjsj(new Date());
							hCMainDataMapper.insertSelective(hCMainData);
							commonRecService.write(uuid3.toString(), TableEnum.D_008, user.getId(), UpdateTypeEnum.SAVE,uuid3.toString());//保存航材历史记录信息
							borrowApplyDetail.setBjid(uuid3.toString());
						}
					}
				}
			}
			
			UUID uuid2 = UUID.randomUUID();//获取随机的uuid
			String detailId = uuid2.toString();
			if(borrowApplyDetail.getSn().equals("null")){
				borrowApplyDetail.setSn("");
			}
			borrowApplyDetail.setId(detailId);
			borrowApplyDetail.setMainid(id);
			borrowApplyDetail.setZt(EffectiveEnum.YES.getId());
			borrowApplyDetail.setWhrid(user.getId());
			borrowApplyDetail.setWhdwid(user.getBmdm());
			borrowApplyDetail.setWhsj(new Date());
			borrowApplyDetailMapper.insertSelective(borrowApplyDetail);
		}
	
		return id;
	}

	@Override
	public void checkEdit(String id) throws RuntimeException {
		validatePri(id);
		BorrowApply obj = borrowApplyMapper.selectByPrimaryKey(id);
		//验证是否保存状态
		Integer zt = obj.getZt();
		if(null == zt || ReserveStatusEnum.SAVE.getId().intValue() != zt.intValue()){
			throw new RuntimeException("对不起,只有保存状态下的借入单才能编辑!");
		}
		
	}

	@Override
	public BorrowApply selectByPrimaryKey(String id) throws RuntimeException {
		return borrowApplyMapper.selectByPrimaryKey(id);
	}
	 
	
	/**
	 * @author ll
	 * @description 验证借入单操作权限
	 * @param id
	 * @return 
	 * @develop date 2016.09.20
	 */
	private void validatePri(String id) throws RuntimeException{
		User user = ThreadVarUtil.getUser();
		BorrowApply obj = borrowApplyMapper.selectByPrimaryKey(id);
		//判空
		if(null == obj){
			throw new RuntimeException("对不起,表单数据不存在!");
		}
	}

	public String queryedit(BorrowApply borrowApply)throws BusinessException{
		List<HCMainData> hCMainDatas=otherHCMainDataService.selectByIds();
		return this.edit(borrowApply, hCMainDatas);
	}
	
	@Override
	public String edit(BorrowApply borrowApply, List<HCMainData> hCMainDatas) throws BusinessException {
		List<String> newReserveDetailIdList = new ArrayList<String>();
		
		List<String> list1 = new ArrayList<String>(); //修改之前分配的菜单id
		List<String> list2 = new ArrayList<String>(); //修改之前分配的菜单id
		//编辑借入单
		User user = ThreadVarUtil.getUser();
		
	
		
		borrowApplyMapper.updateByPrimaryKeySelective(borrowApply);
		
		
		List<BorrowApplyDetail> borrowApplyDetaillist=borrowApplyDetailMapper.selectByMainId(borrowApply.getId());
		for (BorrowApplyDetail borrowApplyDetail : borrowApplyDetaillist) {
			list1.add(borrowApplyDetail.getId());
		}
		
		for (BorrowApplyDetail borrowApplyDetail : borrowApply.getReserveDetailList()) {
			list2.add(borrowApplyDetail.getId());
		}
		
		for (String  id : list1) {
			if(!list2.contains(id)){
				BorrowApplyDetail borrowApplyDetail =new BorrowApplyDetail();
				borrowApplyDetail.setWhsj(new Date());
				borrowApplyDetail.setZt(0);
				borrowApplyDetail.setId(id);
				borrowApplyDetailMapper.updateByPrimaryKeySelective(borrowApplyDetail);
			}else{
				//新增或编辑借入详情信息
				for(BorrowApplyDetail borrowApplyDetail : borrowApply.getReserveDetailList()){
					
					
					newReserveDetailIdList.add(borrowApplyDetail.getId());
					if(null != borrowApplyDetail.getId() && !"".equals(borrowApplyDetail.getId())){
						borrowApplyDetail.setWhsj(new Date());
						borrowApplyDetailMapper.updateByPrimaryKeySelective(borrowApplyDetail);
					}else{
						
					
							HCMainData hCMainData=new HCMainData();
							hCMainData.setBjh(borrowApplyDetail.getBjh());
							hCMainData.setDprtcode(user.getJgdm());
							List<HCMainData> list=hCMainDataMapper.checkMaterial(hCMainData);//查询本航材基础数据是否有
						
							if(list.size()>0){
								borrowApplyDetail.setBjid(list.get(0).getId());
							}else{
							if(borrowApplyDetail.getState().equals("OtherStock")){
								for (HCMainData hcMainData : hCMainDatas) {
									if(borrowApplyDetail.getBjid().equals(hcMainData.getId())){
										
										UUID uuid3 = UUID.randomUUID();//获取随机的uuid
										hCMainData.setId(uuid3.toString());
										hCMainData.setBjh(hcMainData.getBjh());
										hCMainData.setDprtcode(user.getJgdm());
										hCMainData.setZwms(hcMainData.getZwms());
										hCMainData.setYwms(hcMainData.getYwms());
										hCMainData.setJldw(hcMainData.getJldw());
										hCMainData.setCjjh(hcMainData.getCjjh());
										hCMainData.setZjh(hcMainData.getZjh());
										hCMainData.setHcjz(hcMainData.getHcjz());
										hCMainData.setGljb(hcMainData.getGljb());
										hCMainData.setSxkz(hcMainData.getSxkz());
										hCMainData.setHclx(hcMainData.getHclx());
										hCMainData.setHclxEj(hcMainData.getHclxEj());
										hCMainData.setIsMel(hcMainData.getIsMel());
										hCMainData.setMinKcl(hcMainData.getMinKcl());
										hCMainData.setMaxKcl(hcMainData.getMaxKcl());
										hCMainData.setBz(hcMainData.getBz());
										hCMainData.setGg(hcMainData.getGg());
										hCMainData.setXingh(hcMainData.getXingh());
										hCMainData.setXh(hcMainData.getXingh());
										hCMainData.setZt(hcMainData.getZt());
										hCMainData.setBmid(hcMainData.getBjh());
										hCMainData.setCjrid(user.getId());
										hCMainData.setCjsj(new Date());
										hCMainDataMapper.insertSelective(hCMainData);
										commonRecService.write(uuid3.toString(), TableEnum.D_008, user.getId(), UpdateTypeEnum.SAVE,uuid3.toString());//保存航材历史记录信息
										borrowApplyDetail.setBjid(uuid3.toString());
									}
								}
							}
						}
						
						UUID uuid2 = UUID.randomUUID();//获取随机的uuid
						String detailId = uuid2.toString();
						if(borrowApplyDetail.getSn()==null||borrowApplyDetail.getSn().equals("null")){
							borrowApplyDetail.setSn("");
						}
						borrowApplyDetail.setId(detailId);
						borrowApplyDetail.setMainid(borrowApply.getId());
						borrowApplyDetail.setZt(EffectiveEnum.YES.getId());
						borrowApplyDetail.setWhrid(user.getId());
						borrowApplyDetail.setWhdwid(user.getBmdm());
						borrowApplyDetail.setWhsj(new Date());
						borrowApplyDetailMapper.insertSelective(borrowApplyDetail);
					}
				}
			}
		}
		
		

		return borrowApply.getId();
	}

	@Override
	public void cancel(String id) throws RuntimeException, BusinessException {
		validatePri(id);
		
		BorrowApply borrowApply1=borrowApplyMapper.selectByPrimaryKey(id);
		if(borrowApply1.getZt()!=1){
			throw new BusinessException("当前申请单状态为"+ContractStatusEnum.getName(borrowApply1.getZt())+",不可作废");
		}
	
		BorrowApply reserve = new BorrowApply();
		reserve.setId(id);
		reserve.setZt(ReserveStatusEnum.CANCEL.getId());
		borrowApplyMapper.updateByPrimaryKeySelective(reserve);
		
	}

	@Override
	public void updateByPrimaryKeySelective(BorrowApply borrowApply)
			throws RuntimeException, BusinessException {
		
		BorrowApply borrowApply1=borrowApplyMapper.selectByPrimaryKey(borrowApply.getId());
		if(borrowApply1.getZt()!=2){
			throw new BusinessException("当前申请单状态为"+ContractStatusEnum.getName(borrowApply1.getZt())+",不可指定结束");
		}
		
		borrowApplyMapper.updateByPrimaryKeySelective(borrowApply);
	}

	@Override
	public BorrowApply selectById(String id) throws RuntimeException {
		return borrowApplyMapper.selectById(id);
	}



}
