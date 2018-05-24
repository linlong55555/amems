package com.eray.thjw.production.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.component.saibong.exception.SaibongException;
import com.eray.component.saibong.service.SaibongUtilService;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.production.dao.AirworthinessDirectiveMapper;
import com.eray.thjw.production.po.AirworthinessDirective;
import com.eray.thjw.production.service.AirworthinessDirectiveService;
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
public class AirworthinessDirectiveServiceImpl implements AirworthinessDirectiveService  {

	@Resource
	private AirworthinessDirectiveMapper airworthinessDirectiveMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	@Resource
	private SaibongUtilService saibongUtilService;     
	
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
			commonRecService.write(record.getId(), TableEnum.B_S_014, user.getId(),recCzls,LogOperationEnum.ZUOFEI,UpdateTypeEnum.DELETE,record.getId());//插入日志
		} catch (Exception e) {
			throw new BusinessException("作废适航指令失败",e); 
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