package com.eray.thjw.produce.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.rest.common.SysConfig;
import com.eray.rest.service.PlaneParkingService;
import com.eray.rest.vo.PlaneParking;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.service.AirworthinessStatusService;
import com.eray.thjw.production.dao.AirworthinessDirectiveMapper;
import com.eray.thjw.production.po.AirworthinessDirective;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

import enu.BinaryEnum;
import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
@Service
public class AirworthinessStatusServiceImpl implements AirworthinessStatusService  {

	@Resource
	private AirworthinessDirectiveMapper airworthinessDirectiveMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private SaibongUtilService saibongUtilService; 
	
	@Resource
	private PlaneParkingService planeParkingService; 
	
	@Override
	public Map<String, Object> queryList(AirworthinessDirective record)throws BusinessException {
		try {
			String id = record.getId();
			if (StringUtils.isNotEmpty(id)) {
				record.setId(null);
			}
			PageHelper.startPage(record.getPagination());
			List<AirworthinessDirective> list = this.airworthinessDirectiveMapper.queryList(record);
			return PageUtil.pack4PageHelper(list, record.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询适航指令失败",e);
		}
		
	}
	
	@Override
	public void insert(AirworthinessDirective record)throws BusinessException {
		try { 
			User user = ThreadVarUtil.getUser();
			record.setWhrid(user.getId());
			record.setWhdwid(user.getBmdm());
			record.setId(UUID.randomUUID().toString());
			int len = record.getFjzch().length();
			String djh = len>4?record.getFjzch().substring(record.getFjzch().length()-4):record.getFjzch();
			String lsh = null;	
			try {
				lsh = saibongUtilService.generate(user.getJgdm(), SaiBongEnum.SHZL.getName(), djh);
			} catch (SaibongException e) {
				throw new BusinessException("获取流水号失败",e);
			}
			record.setLsh(lsh);
			
			//不适航日期 等同与不适航实际日期，但实际日期为空，则取不适航预计日期，如果预计日期也为空，则同为空
			record.setBshRq(record.getBshSjrq()!=null?record.getBshSjrq():record.getBshYjrq());
			//适航日期  等同与适航实际日期，但实际日期为空，则取适航预计日期，如果预计日期也为空，则同为空
			record.setShRq(record.getShSjrq()!=null?record.getShSjrq():record.getShYjrq());
			airworthinessDirectiveMapper.insertSelective(record);
			
			String recCzls = UUID.randomUUID().toString();  
			commonRecService.write(record.getId(), TableEnum.B_S_014, user.getId(),recCzls,LogOperationEnum.SAVE_WO,UpdateTypeEnum.SAVE,record.getId());//插入日志
			
		} catch (Exception e) {
			throw new BusinessException("新增适航指令失败",e);
		}
	}

	@Override
	public void update(AirworthinessDirective record)throws BusinessException {
		try { 
			User user = ThreadVarUtil.getUser();
			record.setWhrid(user.getId());
			record.setWhdwid(user.getBmdm());
			//不适航日期 等同与不适航实际日期，但实际日期为空，则取不适航预计日期，如果预计日期也为空，则同为空
			record.setBshRq(record.getBshSjrq()!=null?record.getBshSjrq():record.getBshYjrq());
			//适航日期  等同与适航实际日期，但实际日期为空，则取适航预计日期，如果预计日期也为空，则同为空
			record.setShRq(record.getShSjrq()!=null?record.getShSjrq():record.getShYjrq());
			airworthinessDirectiveMapper.updateByPrimaryKeySelective(record);
			String recCzls = UUID.randomUUID().toString();  
			commonRecService.write(record.getId(), TableEnum.B_S_014, user.getId(),recCzls,LogOperationEnum.EDIT,UpdateTypeEnum.UPDATE,record.getId());//插入日志
		} catch (Exception e) {
			throw new BusinessException("更新适航指令失败",e);
		} 
	}

	@Override
	public AirworthinessDirective load(String id) throws BusinessException {
		try {
			return airworthinessDirectiveMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new BusinessException("查询适航指令失败",e);
		}
	}

	@Override
	public void saveOrUpdate(AirworthinessDirective record)throws BusinessException {
		 if (StringUtils.isNotBlank(record.getId())) {
			 update(record);
		 }
		 else {
			 insert(record);
		 }
		 // 同步故障保留单到海航易建运控系统
		 syncHhyk(record.getId(), UpdateTypeEnum.SAVE);
	}
	
	/**
	 * @Description 同步故障保留单到海航易建运控系统
	 * @CreateTime 2018年2月5日 上午11:45:44
	 * @CreateBy 韩武
	 * @param id
	 * @param updateTypeEnum
	 */
	private void syncHhyk(String id, UpdateTypeEnum updateTypeEnum){
		
		// 海航运控接口是否启用
		if(SysConfig.isHhykEnabled()){
			
			// 查询详情
			AirworthinessDirective ad = airworthinessDirectiveMapper.selectByPrimaryKey(id);
			
			// 对象转换
			PlaneParking pp = new PlaneParking();
			pp.setRegNum(ad.getFjzch());	// 飞机注册号
			pp.setStartTime(ad.getBshSjrq() != null ? ad.getBshSjrq() : ad.getBshYjrq());	// 优先为实际停场时间，如无，则为预计停场时间
			pp.setEndTime(ad.getShSjrq() != null ? ad.getShSjrq() : ad.getShYjrq());	// 优先为实际适航时间，如无，则为预计适航时间
			pp.setParkingType(ad.getBshyy());	// 停场种类(1: 计划停场,2: 非计划停场)
			pp.setReason(ad.getTcyy());	// 停场原因
			pp.setDetailContent(ad.getBz());	// 详细内容
			pp.setUpdateBy(ThreadVarUtil.getUser().getRealname());	// 更新人
			pp.setUpdateDate(new Date());	// 更新时间(yyyy-MM-dd HH:mm:ss)
			pp.setIsDelete(UpdateTypeEnum.DELETE.equals(updateTypeEnum));	// 是否删除
			pp.setSyncCode(ad.getId());	// 同步Code(主键id)
			
			// 飞机停场同步
			planeParkingService.doSync(pp);
		}
	}

	@Override
	public void cancel(AirworthinessDirective record)throws BusinessException {
		try {
			User user = ThreadVarUtil.getUser();
			record = airworthinessDirectiveMapper.selectByPrimaryKey(record.getId());
			record.setWhrid(user.getId());
			record.setWhdwid(user.getBmdm());
			record.setZt(BinaryEnum.NO.getId().toString());
			airworthinessDirectiveMapper.updateByPrimaryKeySelective(record);
			String recCzls = UUID.randomUUID().toString();  
			commonRecService.write(record.getId(), TableEnum.B_S_014, user.getId(),recCzls,LogOperationEnum.DELETE,UpdateTypeEnum.DELETE,record.getId());//插入日志
			
			// 同步故障保留单到海航易建运控系统
			syncHhyk(record.getId(), UpdateTypeEnum.DELETE);
		} catch (Exception e) {
			throw new BusinessException("删除适航指令失败",e); 
		}
		
	}

	@Override
	public Map<String, Object> queryPage(AirworthinessDirective record) throws BusinessException {
		try {
			PageHelper.startPage(record.getPagination());
			List<AirworthinessDirective> list = this.airworthinessDirectiveMapper.queryPage(record);
			return PageUtil.pack4PageHelper(list, record.getPagination());
		} catch (Exception e) {
			throw new BusinessException("查询适航指令失败",e);
		}
	}

	
     
}