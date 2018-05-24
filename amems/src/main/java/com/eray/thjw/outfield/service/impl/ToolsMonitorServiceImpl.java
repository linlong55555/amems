package com.eray.thjw.outfield.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.outfield.dao.ToolsMonitorDetailMapper;
import com.eray.thjw.outfield.dao.ToolsMonitorMapper;
import com.eray.thjw.outfield.po.ToolsMonitor;
import com.eray.thjw.outfield.po.ToolsMonitorDetail;
import com.eray.thjw.outfield.service.ToolsMonitorService;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.outfield.MeasurementMarkEnum;

/**
 * @author liub
 * @description 计量工具service,用于业务逻辑处理
 * @develop date 2016.11.30
 */
@Service
public class ToolsMonitorServiceImpl implements ToolsMonitorService {
	
	/**
	 * @author liub
	 * @description 计量工具Mapper
	 * @develop date 2016.11.30
	 */
    @Resource
    private ToolsMonitorMapper toolsMonitorMapper;
	
    
    /**
	 * @author liub
	 * @description 计量工具监控明细Mapper
	 * @develop date 2016.11.30
	 */
    @Resource
    private ToolsMonitorDetailMapper toolsMonitorDetailMapper;
    
    /**
  	 * @author liub
  	 * @description 附件service
  	 * @develop date 2017.04.17
  	 */
	@Resource
	private AttachmentService attachmentService;
    
    /**
	 * @author liub
	 * @description 日志service
	 * @develop date 2016.12.29
	 */
	@Autowired
	private CommonRecService commonRecService;
	
    
    /**
	 * @author liub
	 * @description  新增计量工具监控数据
	 * @param toolsMonitorDetail
	 * @return Map<String, Object>
	 * @develop date 2016.12.01
	 */
    @Override
	public Map<String, Object> addToolsMonitor(ToolsMonitorDetail toolsMonitorDetail)throws BusinessException{
    	String detailId = null;
    	ToolsMonitorDetail oldToolsMonitorDetail = null;
    	String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		ToolsMonitor toolsMonitor = toolsMonitorDetail.getToolsMonitor();
		toolsMonitor.setWhrid(user.getId());
		toolsMonitor.setWhbmid(user.getBmdm());
		toolsMonitor.setDprtcode(user.getJgdm());
		ToolsMonitor oldToolsMonitor = toolsMonitorMapper.getByBjidAndBjxlh(toolsMonitor.getBjid(),toolsMonitor.getBjxlh());
		//判断其它人员是否已经新增
		if(null == oldToolsMonitor){
			toolsMonitor.setId(id);
			toolsMonitor.setZt(EffectiveEnum.YES.getId());
			toolsMonitorMapper.insertSelective(toolsMonitor);
			commonRecService.write(id, TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, toolsMonitor.getId());//插入日志
		}else{
			toolsMonitor.setDprtcode(oldToolsMonitor.getDprtcode());
			id = oldToolsMonitor.getId();
			oldToolsMonitorDetail = toolsMonitorDetailMapper.getByMainidAndBjxlh(id, toolsMonitor.getBjxlh());
			if(null != oldToolsMonitorDetail && null != oldToolsMonitorDetail.getJyScrq()){
				checkDate(toolsMonitor.getBjxlh(), toolsMonitorDetail.getJyScrq(), oldToolsMonitorDetail.getJyScrq());
				if(toolsMonitorDetail.getJyScrq().getTime() == oldToolsMonitorDetail.getJyScrq().getTime()){
					detailId = oldToolsMonitorDetail.getId();//如果时间相同则修改;
				}
			}
			toolsMonitor.setIsJl(MeasurementMarkEnum.METERING.getId());
			toolsMonitorMapper.updateByIdAndIsJl(toolsMonitor);
			commonRecService.write(id, TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, id);//插入日志
		}
		//新增或修改监控详情
		toolsMonitorDetail.setZt(EffectiveEnum.YES.getId());
		toolsMonitorDetail.setWhrid(user.getId());
		toolsMonitorDetail.setWhbmid(user.getBmdm());
		//判断是否新增监控详情
		if(null != detailId){
			toolsMonitorDetail.setId(detailId);
			toolsMonitorDetailMapper.updateByPrimaryKeySelective(toolsMonitorDetail);
			commonRecService.write(detailId, TableEnum.B_W_00101, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, id);//插入日志
		}else{
			//新增监控详情
			detailId = UUID.randomUUID().toString();
			toolsMonitorDetail.setId(detailId);
			toolsMonitorDetail.setMainid(id);
			toolsMonitorDetailMapper.insertSelective(toolsMonitorDetail);
			commonRecService.write(detailId, TableEnum.B_W_00101, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);//插入日志
		}
		attachmentService.eidtAttachment(toolsMonitorDetail.getAttachments(), detailId, czls, toolsMonitorDetail.getMainid(), toolsMonitor.getDprtcode(), LogOperationEnum.SAVE_WO);
		return getToolsMonitorMap(id);
	}
    
    /**
	 * @author liub
	 * @description  新增计量工具监控详情数据
	 * @param toolsMonitorDetail
	 * @return Map<String, Object>
	 * @develop date 2016.12.06
	 */
    @Override
	public Map<String, Object> addToolsMonitorDetail(ToolsMonitorDetail toolsMonitorDetail) throws BusinessException{
    	String detailId = null;
    	String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//验证编号是否重复,校验日期是否大于最大校验日期
		ToolsMonitorDetail oldToolsMonitorDetail = toolsMonitorDetailMapper.getByMainidAndBjxlh(toolsMonitorDetail.getMainid(), toolsMonitorDetail.getBjxlh());
		if(null != oldToolsMonitorDetail && null != oldToolsMonitorDetail.getJyScrq()){
			if(null == toolsMonitorDetail.getId() || "".equals(toolsMonitorDetail.getId())){
				throw new BusinessException(new StringBuffer().append("对不起,编号").append(toolsMonitorDetail.getBjxlh()).append("已经存在!").toString());
			}
			checkDate(toolsMonitorDetail.getBjxlh(), toolsMonitorDetail.getJyScrq(), oldToolsMonitorDetail.getJyScrq());
			if(toolsMonitorDetail.getJyScrq().getTime() == oldToolsMonitorDetail.getJyScrq().getTime()){
				detailId = oldToolsMonitorDetail.getId();//如果时间相同则修改;
			}
		}
		//修改计量工具监控
		ToolsMonitor toolsMonitor = toolsMonitorDetail.getToolsMonitor();
		toolsMonitor.setId(toolsMonitorDetail.getMainid());
		toolsMonitor.setWhrid(user.getId());
		toolsMonitor.setWhbmid(user.getBmdm());
		toolsMonitorMapper.updateByIdAndIsJl(toolsMonitor);
		commonRecService.write(toolsMonitorDetail.getMainid(), TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, toolsMonitor.getId());//插入日志
		//新增或修改监控详情
		toolsMonitorDetail.setZt(EffectiveEnum.YES.getId());
		toolsMonitorDetail.setWhrid(user.getId());
		toolsMonitorDetail.setWhbmid(user.getBmdm());
		//判断是否新增监控详情
		if(null != detailId){
			toolsMonitorDetail.setId(detailId);
			toolsMonitorDetailMapper.updateByPrimaryKeySelective(toolsMonitorDetail);
			commonRecService.write(detailId, TableEnum.B_W_00101, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, toolsMonitor.getId());//插入日志
		}else{
			//新增监控详情
			detailId = UUID.randomUUID().toString();
			toolsMonitorDetail.setId(detailId);
			toolsMonitorDetailMapper.insertSelective(toolsMonitorDetail);
			commonRecService.write(detailId, TableEnum.B_W_00101, user.getId(), czls , LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, toolsMonitor.getId());//插入日志
		}
		ToolsMonitor oldToolsMonitor = toolsMonitorMapper.selectByPrimaryKey(toolsMonitorDetail.getMainid());
		attachmentService.eidtAttachment(toolsMonitorDetail.getAttachments(), detailId, czls, toolsMonitorDetail.getMainid(), oldToolsMonitor.getDprtcode(), LogOperationEnum.SAVE_WO);
		return getToolsMonitorMap(toolsMonitorDetail.getMainid());
	}
    
    /**
	 * @author liub
	 * @description  批量新增计量工具监控详情数据
	 * @param toolsMonitor
	 * @return Map<String, Object>
	 * @develop date 2016.12.15
	 */
    @Override
	public Map<String, Object> addToolsMonitorDetailList(ToolsMonitor toolsMonitor)throws BusinessException{
    	String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//修改计量工具监控
		toolsMonitor.setWhrid(user.getId());
		toolsMonitor.setWhbmid(user.getBmdm());
		toolsMonitorMapper.updateByIdAndIsJl(toolsMonitor);
		commonRecService.write(toolsMonitor.getId(), TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, toolsMonitor.getId());//插入日志
		if(null != toolsMonitor.getToolsMonitorDetailList() && toolsMonitor.getToolsMonitorDetailList().size() > 0){
			Map<String, Date> map = new HashMap<String, Date>();//用于储存部件序列号及最大校验日期
			List<ToolsMonitorDetail> toolsMonitorDetailList = toolsMonitorDetailMapper.queryDetailByMainId(toolsMonitor.getId());
			for (ToolsMonitorDetail toolsMonitorDetail : toolsMonitorDetailList) {
				map.put(toolsMonitorDetail.getBjxlh(), toolsMonitorDetail.getJyScrq());
			}
			List<String> columnValueList = new ArrayList<String>();
			for(ToolsMonitorDetail toolsMonitorDetail : toolsMonitor.getToolsMonitorDetailList()){
				//校验日期是否大于最大校验日期
				checkDate(toolsMonitorDetail.getBjxlh(), toolsMonitorDetail.getJyScrq(), map.get(toolsMonitorDetail.getBjxlh()));
				if(toolsMonitorDetail.getJyScrq().getTime() == map.get(toolsMonitorDetail.getBjxlh()).getTime()){
					throw new BusinessException(new StringBuffer().append("对不起,编号").append(toolsMonitorDetail.getBjxlh()).append("最近校验日期已更新，请刷新后再进行操作!").toString());
				}
				//新增监控详情
				String detailId = UUID.randomUUID().toString();
				toolsMonitorDetail.setId(detailId);
				toolsMonitorDetail.setZt(EffectiveEnum.YES.getId());
				toolsMonitorDetail.setWhrid(user.getId());
				toolsMonitorDetail.setWhbmid(user.getBmdm());
				columnValueList.add(detailId);
				toolsMonitorDetailMapper.insertSelective(toolsMonitorDetail);
			}
			if(columnValueList.size() > 0){
				commonRecService.write("id", columnValueList, TableEnum.B_W_00101, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, toolsMonitor.getId());//插入定检工作内容日志
			}
		}
		return getToolsMonitorMap(toolsMonitor.getId());
	}
    
    /**
	 * @author liub
	 * @description  根据mainid、编号删除计量工具监控详情数据
	 * @param toolsMonitorDetail
	 * @return Map<String, Object>
	 * @develop date 2016.12.06
	 */
    @Override
	public Map<String, Object> deleteDetailByMainIdAndBjxlh(ToolsMonitorDetail toolsMonitorDetail){
    	String czls = UUID.randomUUID().toString();//操作流水
    	User user = ThreadVarUtil.getUser();
    	// 插入日志
    	StringBuilder sql = new StringBuilder();
    	sql.append("b.BJXLH = '").append(toolsMonitorDetail.getBjxlh()).append("' ");
    	sql.append("and b.MAINID = '").append(toolsMonitorDetail.getMainid()).append("' ");
    	if(EffectiveEnum.YES.getId().intValue() == toolsMonitorDetail.getBjbs().intValue()){
    		sql.append("and not exists (select 1 from B_W_00101 where ZT = 1 and BJBS = 0 and BJXLH = '");
    		sql.append(toolsMonitorDetail.getBjxlh()).append("' ");
    		sql.append("and MAINID = '").append(toolsMonitorDetail.getMainid()).append("')");
    	}
    	commonRecService.writeByWhere(sql.toString(), TableEnum.B_W_00101, user.getId(),czls,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, toolsMonitorDetail.getMainid());//插入定检工作内容日志
    	int count = toolsMonitorDetailMapper.deleteDetailByMainIdAndBjxlh(toolsMonitorDetail);
    	if(EffectiveEnum.YES.getId().intValue() == toolsMonitorDetail.getBjbs().intValue() && count > 0){
    		//删除计量工具监控
    		ToolsMonitor toolsMonitor = new ToolsMonitor();
    		toolsMonitor.setId(toolsMonitorDetail.getMainid());
    		toolsMonitor.setZt(EffectiveEnum.NO.getId());
    		toolsMonitorMapper.updateByPrimaryKeySelective(toolsMonitor);
    		commonRecService.write(toolsMonitorDetail.getMainid(), TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, toolsMonitorDetail.getMainid());//插入日志
    	}else{
    		commonRecService.write(toolsMonitorDetail.getMainid(), TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, toolsMonitorDetail.getMainid());//插入日志
    	}
		return getToolsMonitorMap(toolsMonitorDetail.getMainid());
    }
    
    /**
	 * @author liub
	 * @description  根据detailId删除计量工具监控详情
	 * @param detailId
	 * @return Map<String, Object>
	 * @develop date 2016.12.14
	 */
    @Override
   	public Map<String, Object> deleteDetail(String detailId,String mainid){
    	String czls = UUID.randomUUID().toString();//操作流水
    	User user = ThreadVarUtil.getUser();
       	toolsMonitorDetailMapper.deleteDetail(detailId);
       	commonRecService.write(detailId, TableEnum.B_W_00101, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, mainid);//插入日志
       	commonRecService.write(mainid, TableEnum.B_W_001, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, mainid);//插入日志
   		return getToolsMonitorMap(mainid);
    }
    
    /**
	 * @author liub
	 * @description  根据条件分页查询库存及外场虚拟库存列表
	 * @param toolsMonitor
	 * @return List<Map<String, Object>>
	 * @develop date 2016.11.30
	 */
    @Override
	public List<Map<String, Object>> queryAllStockPageList(ToolsMonitor toolsMonitor){
		return toolsMonitorMapper.queryAllStockPageList(toolsMonitor);
	}
	
    /**
	 * @author liub
	 * @description  根据条件分页查询计量监控列表
	 * @param baseEntity
	 * @return List<Map<String, Object>>
	 * @develop date 2016.12.15
	 */
    @Override
	public List<Map<String, Object>> queryAllPageList(BaseEntity baseEntity){
		return toolsMonitorDetailMapper.queryAllPageList(baseEntity);
	}
	
	/**
	 * @author liub
	 * @description  根据计量工具id查询详情信息(最新检查日期)
	 * @param mainid
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.06
	 */
    @Override
	public List<ToolsMonitorDetail> queryDetailByMainId(String mainid){
    	return toolsMonitorDetailMapper.queryDetailByMainId(mainid);
	}
    
    /**
	 * @author liub
	 * @description  根据计量工具id,编号查询历史详情信息
	 * @param mainid,bjxlh
	 * @return List<ToolsMonitorDetail>
	 * @develop date 2016.12.14
	 */
    @Override
	public List<ToolsMonitorDetail> queryDetailByMainIdAndBjxlh(String mainid,String bjxlh){
    	return toolsMonitorDetailMapper.queryDetailByMainIdAndBjxlh(mainid, bjxlh);
	}
	
	/**
	 * @author liub
	 * @description 根据id获取计量工具监控map
	 * @param id
	 * @return Map<String, Object>
	 * @develop date 2016.11.30
	 */
	private Map<String, Object> getToolsMonitorMap(String id){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = toolsMonitorMapper.getToolsMonitorMap(id);
		if(null != resultMap && resultMap.size() > 0){
			dataMap.put("jlid", resultMap.get("ID"));
			dataMap.put("isJl", resultMap.get("IS_JL"));
			dataMap.put("jyscrq", resultMap.get("JY_SCRQ"));
		}else{
			dataMap.put("jlid", "");
			dataMap.put("isJl", "");
			dataMap.put("jyscrq", "");
		}
		return dataMap;
	}
	
	/**
	 * @author liub
	 * @description 判断新校验日期与旧校验日期
	 * @param bjxlh,newDate,oldDate
	 * @develop date 2016.12.02
	 */
	private void checkDate(String bjxlh,Date newDate,Date oldDate)throws BusinessException{
		if(null == newDate){
			throw new BusinessException(new StringBuffer().append("对不起,编号").append(bjxlh).append("最新校验日期不能为空!").toString());
		}
		if(null == oldDate){
			throw new BusinessException(new StringBuffer().append("对不起,编号").append(bjxlh).append("原校验日期不能为空!").toString());
		}
		if(newDate.getTime() < oldDate.getTime()){
			throw new BusinessException(new StringBuffer().append("对不起,编号").append(bjxlh).append("最近校验日期不能小于原校验日期,请刷新数据!").toString());
		}
	}
}
