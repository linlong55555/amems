	package com.eray.thjw.material2.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.material2.dao.MeasurementToolsDetailsHistoryMapper;
import com.eray.thjw.material2.dao.MeasurementToolsDetailsMapper;
import com.eray.thjw.material2.po.MeasurementTools;
import com.eray.thjw.material2.po.MeasurementToolsDetails;
import com.eray.thjw.material2.po.MeasurementToolsDetailsHistory;
import com.eray.thjw.material2.service.MeasurementToolsDetailsService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.PersonnelToPost;
import com.eray.thjw.quality.po.PostApplication;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
/**
 * 
 * @Description 计量工具serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("measurementToolsDetailsService")
public class MeasurementToolsDetailsServiceImpl implements MeasurementToolsDetailsService  {

	@Resource
	private MeasurementToolsDetailsMapper measurementToolsDetailsMapper;
	@Resource
	private MeasurementToolsDetailsHistoryMapper measurementToolsDetailsHistoryMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private AttachmentService attachmentService;
	
	
	/**
	 * @Description 计量工具分页
	 * @CreateTime 2017年8月16日 上午10:18:08
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @return 分页数据集合
	 * @throws BusinessException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> queryAllPageList(MeasurementToolsDetails measurementToolsDetails)throws BusinessException {
		//用户刚编辑过的记录 ID
		String id = measurementToolsDetails.getId();
		//清除查询条件中的ID，保证列表查询结果集的正确性
		measurementToolsDetails.setId(null);
		PageHelper.startPage(measurementToolsDetails.getPagination());
		List<MeasurementToolsDetails> list = measurementToolsDetailsMapper.queryAllPageList(measurementToolsDetails);
		if(((Page)list).getTotal() > 0){
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if(!PageUtil.hasRecord(list, id)){
					MeasurementToolsDetails param = new MeasurementToolsDetails();
					param.setId(id);
					List<MeasurementToolsDetails> newRecordList = measurementToolsDetailsMapper.queryAllPageList(param);
					if(newRecordList != null && newRecordList.size() == 1){
						list.add(0, newRecordList.get(0));
					}
				}
			}
			return PageUtil.pack4PageHelper(list, measurementToolsDetails.getPagination());
		}else{
			List<MeasurementToolsDetails> newRecordList = new ArrayList<MeasurementToolsDetails>(1);
			if(StringUtils.isNotBlank(id)){	//判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				MeasurementToolsDetails param = new MeasurementToolsDetails();
				param.setId(id);
				newRecordList = measurementToolsDetailsMapper.queryAllPageList(param);
				if(newRecordList != null && newRecordList.size() == 1){
					return PageUtil.pack(1, newRecordList, measurementToolsDetails.getPagination());
				}
			}
			return PageUtil.pack(0, newRecordList, measurementToolsDetails.getPagination());
		}
	}
	
	
	
	/**
	 * @Description 保存 计量工具
	 * @CreateTime 2017年8月14日 上午9:40:46
	 * @CreateBy 林龙
	 * @param technical 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	@Override
	public String save(MeasurementToolsDetails measurementToolsDetails) throws BusinessException {
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//新增计量工具 
		insertSelective(measurementToolsDetails,uuid,user);
		
		//新增附件
		attachmentService.eidtAttachment(measurementToolsDetails.getAttachmentList(), uuid, null, uuid, user.getJgdm(), LogOperationEnum.SAVE_WO);
		
		return uuid;
	}
	/**
	 * 
	 * @Description 新增计量工具
	 * @CreateTime 2017年9月27日 下午1:43:30
	 * @CreateBy 林龙
	 * @param defectKeep
	 * @param uuid 计量工具id
	 * @param czls 流水号
	 * @param user 当前登录人
	 */
	private void insertSelective(MeasurementToolsDetails measurementToolsDetails, String uuid,User user)throws BusinessException {
		
		measurementToolsDetails.setDprtcode(user.getJgdm());         //组织机构
		measurementToolsDetails.setId(uuid);                         //id
		measurementToolsDetails.setWhbmid(user.getBmdm());	        //部门id
		measurementToolsDetails.setWhrid(user.getId());		        //用户id
		
		//新增计量工具
		measurementToolsDetailsMapper.insertSelective(measurementToolsDetails);
		
	}

	/**
	 * 
	 * @Description //验证验证计量工具单编号 唯一
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 */
	private void validationDefectKeep(MeasurementToolsDetails measurementToolsDetails)throws BusinessException {
		int  iNum = measurementToolsDetailsMapper.queryCount(measurementToolsDetails);
		if (iNum > 0) {
			throw new BusinessException("该计量工具单编号已存在!");
		}
		
	}

	/**
	 * 
	 * @Description 根据计量工具id查询计量工具信息
	 * @CreateTime 2017年9月27日 下午3:27:48
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public MeasurementToolsDetails getInfoById(MeasurementToolsDetails measurementToolsDetails)throws BusinessException {
		return measurementToolsDetailsMapper.getInfoById(measurementToolsDetails);
	}
	
	/**
	 * 
	 * @Description 修改保存计量工具
	 * @CreateTime 2017年9月27日 下午4:38:10
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	@Override
	public String update(MeasurementToolsDetails measurementToolsDetails) throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		//修改计量工具
		updateByPrimaryKeySelective(measurementToolsDetails,user);
		
		//保存计量工具监控明细历史表
		saveMeasurementToolsDetailsHistory(measurementToolsDetails,user);
		
		//修改附件
		attachmentService.eidtAttachment(measurementToolsDetails.getAttachmentList(), measurementToolsDetails.getId(), null, measurementToolsDetails.getId(), measurementToolsDetails.getDprtcode(), LogOperationEnum.EDIT);
		
		return measurementToolsDetails.getId();
	}

	/**
	 * 
	 * @Description 保存计量工具监控明细历史表
	 * @CreateTime 2018年2月8日 下午2:19:48
	 * @CreateBy 林龙
	 * @param measurementToolsDetails
	 * @param user
	 */
	private void saveMeasurementToolsDetailsHistory(MeasurementToolsDetails measurementToolsDetails, User user) {
		String uuid = UUID.randomUUID().toString();	//uuid-主单id
		
		MeasurementToolsDetailsHistory measurementToolsDetailsHistory = new MeasurementToolsDetailsHistory();
		measurementToolsDetailsHistory.setId(uuid);
		measurementToolsDetailsHistory.setMainid(measurementToolsDetails.getId());
		measurementToolsDetailsHistory.setBjxlh(measurementToolsDetails.getBjxlh());
		measurementToolsDetailsHistory.setBjh(measurementToolsDetails.getBjh());
		measurementToolsDetailsHistory.setZwms(measurementToolsDetails.getZwms());
		measurementToolsDetailsHistory.setYwms(measurementToolsDetails.getYwms());
		measurementToolsDetailsHistory.setGg(measurementToolsDetails.getGg());
		measurementToolsDetailsHistory.setXh(measurementToolsDetails.getXh());
		measurementToolsDetailsHistory.setBjbs(measurementToolsDetails.getBjbs());
		measurementToolsDetailsHistory.setBz(measurementToolsDetails.getBz());
		measurementToolsDetailsHistory.setJyScrq(measurementToolsDetails.getJyScrq());
		measurementToolsDetailsHistory.setJyXcrq(measurementToolsDetails.getJyXcrq());
		measurementToolsDetailsHistory.setJyZq(measurementToolsDetails.getJyZq());
		measurementToolsDetailsHistory.setJyZqdw(measurementToolsDetails.getJyZqdw());
		measurementToolsDetailsHistory.setJlfs(measurementToolsDetails.getJlfs());
		measurementToolsDetailsHistory.setJldj(measurementToolsDetails.getJldj());
		measurementToolsDetailsHistory.setZt(measurementToolsDetails.getZt());
		measurementToolsDetailsHistory.setWhbmid(measurementToolsDetails.getWhbmid()); //维护部门
		measurementToolsDetailsHistory.setWhrid(measurementToolsDetails.getWhrid());    //维护人
		measurementToolsDetailsHistory.setWhsj(measurementToolsDetails.getWhsj());
		measurementToolsDetailsHistoryMapper.insertSelective(measurementToolsDetailsHistory);
	}

	/**
	 * 
	 * @Description 修改计量工具
	 * @CreateTime 2017年9月27日 下午4:40:24
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 * @param czls 流水号
	 */
	private void updateByPrimaryKeySelective(MeasurementToolsDetails measurementToolsDetails,User user)throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证单据状态是否已变更
//		validation4CurrentZt(measurementToolsDetails.getId(), Integer.valueOf((String) measurementToolsDetails.getParamsMap().get("currentZt")));
		
		measurementToolsDetails.setWhbmid(user.getBmdm()); //维护部门
		measurementToolsDetails.setWhrid(user.getId());    //维护人
		measurementToolsDetails.setWhsj(currentDate);
		measurementToolsDetailsMapper.updateByPrimaryKeySelective(measurementToolsDetails);
		
	}

	/**
	 * 
	 * @Description 删除
	 * @CreateTime 2017年9月27日 下午1:46:37
	 * @CreateBy 林龙
	 * @param defectKeep 计量工具
	 */
	@Override
	public void delete(MeasurementToolsDetails measurementToolsDetails) throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		Date currentDate = commonService.getSysdate();//当前时间
		// 验证单据状态是否已变更
		validation4CurrentZt(measurementToolsDetails.getId(), Integer.valueOf((String) measurementToolsDetails.getParamsMap().get("currentZt")));
		
		//根据mainid查询b_w_0010101最大时间排序
		List<MeasurementToolsDetailsHistory> mlist = measurementToolsDetailsHistoryMapper.selectByPrimaryMainid(measurementToolsDetails.getId());
		
		if(mlist.size() > 1){
			
			//将b_w_0010101中维护时间最大的数据还原到b_w_00101(ID不还原)
			measurementToolsDetails.setBjxlh(mlist.get(1).getBjxlh());
			measurementToolsDetails.setBjh(mlist.get(1).getBjh());
			measurementToolsDetails.setZwms(mlist.get(1).getZwms());
			measurementToolsDetails.setYwms(mlist.get(1).getYwms());
			measurementToolsDetails.setGg(mlist.get(1).getGg());
			measurementToolsDetails.setXh(mlist.get(1).getXh());
			measurementToolsDetails.setBjbs(mlist.get(1).getBjbs());
			measurementToolsDetails.setBz(mlist.get(1).getBz());
			measurementToolsDetails.setJyScrq(mlist.get(1).getJyScrq());
			measurementToolsDetails.setJyXcrq(mlist.get(1).getJyXcrq());
			measurementToolsDetails.setJyZq(mlist.get(1).getJyZq());
			measurementToolsDetails.setJyZqdw(mlist.get(1).getJyZqdw());
			measurementToolsDetails.setJlfs(mlist.get(1).getJlfs());
			measurementToolsDetails.setJldj(mlist.get(1).getJldj());
			measurementToolsDetails.setWhbmid(user.getBmdm());
			measurementToolsDetails.setWhrid(user.getId());
			measurementToolsDetails.setWhsj(currentDate);
			measurementToolsDetailsMapper.updateByPrimaryKeySelective(measurementToolsDetails);
			
			//删除b_w_0010101中维护时间最大的数据对应数据
			measurementToolsDetailsHistoryMapper.deleteByPrimaryKey(mlist.get(0).getId());
			
		}else if(mlist.size() == 1){
			//删除b_w_0010101中维护时间最大的数据对应数据
			measurementToolsDetailsHistoryMapper.deleteByPrimaryKey(mlist.get(0).getId());
			//删除计量工具
			measurementToolsDetailsMapper.deleteByPrimaryKey(measurementToolsDetails.getId());
		}else{
			//删除计量工具
			measurementToolsDetailsMapper.deleteByPrimaryKey(measurementToolsDetails.getId());
		}
	}
	
	/**
	 * 
	 * @Description 验证单据状态是否已变更
	 * @CreateTime 2017年9月27日 下午4:43:00
	 * @CreateBy 林龙
	 * @param id 单据id
	 * @param zt 当前页面状态
	 */
	private void validation4CurrentZt(String id, Integer zt)throws BusinessException {
		int bzt = measurementToolsDetailsMapper.getCurrentZt4Validation(id);
		if (bzt != zt) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 新增多个b_w_001
	 * @CreateTime 2018年2月9日 下午1:30:36
	 * @CreateBy 林龙
	 * @param measurementToolsDetails
	 * @param user
	 * @param uuid
	 * @throws BusinessException
	 */
	@Override
	public void insertList(List<MeasurementToolsDetails> measurementToolsDetailsList, User user,String mainid) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		List<String> bhList = new ArrayList<String>();// 评估单id集合
		MeasurementToolsDetails mea=new MeasurementToolsDetails();
		for (MeasurementToolsDetails measurementToolsDetails : measurementToolsDetailsList) {
			bhList.add(measurementToolsDetails.getBjxlh());
		}
		mea.setBhList(bhList);
		mea.setMainid(mainid);
		int isNum = measurementToolsDetailsMapper.selectMainidList(mea);
		if(isNum>0){
			throw new BusinessException("有重复编号，请修改编号或者重新选择工具编号!");
		}
		
		for (MeasurementToolsDetails measurementToolsDetails : measurementToolsDetailsList) {
			String uuid = UUID.randomUUID().toString();	//uuid-主单id
			String uuidHistory = UUID.randomUUID().toString();	//uuid-主单id
			measurementToolsDetails.setId(uuid);
		    measurementToolsDetails.setMainid(mainid);
		    measurementToolsDetails.setZt(1);
		    measurementToolsDetails.setWhbmid(user.getBmdm());	        	//部门id
		    measurementToolsDetails.setWhrid(user.getId());		        //用户id
		    measurementToolsDetails.setWhsj(currentDate);
		    
		    measurementToolsDetailsMapper.insertSelective(measurementToolsDetails);
		    //新增历史表
		    MeasurementToolsDetailsHistory measurementToolsDetailsHistory = new MeasurementToolsDetailsHistory();
			measurementToolsDetailsHistory.setId(uuidHistory);
			measurementToolsDetailsHistory.setMainid(uuid);
			measurementToolsDetailsHistory.setBjxlh(measurementToolsDetails.getBjxlh());
			measurementToolsDetailsHistory.setBjh(measurementToolsDetails.getBjh());
			measurementToolsDetailsHistory.setZwms(measurementToolsDetails.getZwms());
			measurementToolsDetailsHistory.setYwms(measurementToolsDetails.getYwms());
			measurementToolsDetailsHistory.setGg(measurementToolsDetails.getGg());
			measurementToolsDetailsHistory.setXh(measurementToolsDetails.getXh());
			measurementToolsDetailsHistory.setBjbs(measurementToolsDetails.getBjbs());
			measurementToolsDetailsHistory.setBz(measurementToolsDetails.getBz());
			measurementToolsDetailsHistory.setJyScrq(measurementToolsDetails.getJyScrq());
			measurementToolsDetailsHistory.setJyXcrq(measurementToolsDetails.getJyXcrq());
			measurementToolsDetailsHistory.setJyZq(measurementToolsDetails.getJyZq());
			measurementToolsDetailsHistory.setJyZqdw(measurementToolsDetails.getJyZqdw());
			measurementToolsDetailsHistory.setJlfs(measurementToolsDetails.getJlfs());
			measurementToolsDetailsHistory.setJldj(measurementToolsDetails.getJldj());
			measurementToolsDetailsHistory.setZt(measurementToolsDetails.getZt());
			measurementToolsDetailsHistory.setWhbmid(measurementToolsDetails.getWhbmid()); //维护部门
			measurementToolsDetailsHistory.setWhrid(measurementToolsDetails.getWhrid());    //维护人
			measurementToolsDetailsHistory.setWhsj(measurementToolsDetails.getWhsj());
			measurementToolsDetailsHistoryMapper.insertSelective(measurementToolsDetailsHistory);
		    
		}
		
	}



	@Override
	public String update(MeasurementTools measurementTools) throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		
		measurementTools.getMeasurementToolsDetailsList().get(0).setId(measurementTools.getId());
		measurementTools.getMeasurementToolsDetailsList().get(0).setZt(measurementTools.getZt());
		//修改计量工具
		updateByPrimaryKeySelective(measurementTools.getMeasurementToolsDetailsList().get(0),user);
		
		//保存计量工具监控明细历史表
		saveMeasurementToolsDetailsHistory(measurementTools.getMeasurementToolsDetailsList().get(0),user);
		
		//修改附件
		attachmentService.eidtAttachment(measurementTools.getMeasurementToolsDetailsList().get(0).getAttachmentList(), measurementTools.getMeasurementToolsDetailsList().get(0).getId(), null, measurementTools.getMeasurementToolsDetailsList().get(0).getId(), measurementTools.getMeasurementToolsDetailsList().get(0).getDprtcode(), LogOperationEnum.EDIT);
		
		return measurementTools.getMeasurementToolsDetailsList().get(0).getId();
	}

	/**
	 * 
	 * @Description 查询校验历史列表
	 * @CreateTime 2018年3月7日 下午2:08:41
	 * @CreateBy 林龙
	 * @param measurementToolsDetails
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<MeasurementToolsDetails> queryAllLogList(MeasurementToolsDetails measurementToolsDetails)throws BusinessException {
		List<MeasurementToolsDetails> list = measurementToolsDetailsMapper.queryAllLogList(measurementToolsDetails);
		return list;
	}
	
     
}