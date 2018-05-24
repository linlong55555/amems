package com.eray.thjw.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.eray.thjw.dao.WorkRequireMapper;
import com.eray.thjw.dao.WorkRequireRecMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkRequire;
import com.eray.thjw.po.WorkRequireRec;
import com.eray.thjw.service.WorkRequireService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.UpdateTypeEnum;

@Service
public class WorkRequireServiceImpl implements WorkRequireService{

@Autowired
private WorkRequireMapper workRequireMapper;
@Autowired
private WorkRequireRecMapper workRequireRecMapper;


	@Override
	public List<WorkRequire> queryWorkRequiresAllByGwid(String id) {
		// TODO Auto-generated method stub
		return workRequireMapper.queryWorkRequiresAllByGwid(id);
	}

	@Override
	public void deleteWorkRequires(WorkRequire workrequire)
			throws BusinessException {
		User user=ThreadVarUtil.getUser();
		List<WorkRequire> reList= workRequireMapper.getWorkRequyireByKey(workrequire.getId());
		WorkRequire re=reList.get(0);
		workRequireMapper.deleteWorkRequiresByPrimaryKey(workrequire.getId());
		//操作日zhi
		WorkRequireRec wr=new WorkRequireRec();
		wr.setId(UUID.randomUUID().toString());
		wr.setMainid(re.getMainid());
		wr.setGwyq(re.getGwyq());
		wr.setXc(re.getXc());
		wr.setWhsj(new Date());
		wr.setWhrid(user.getId());
		wr.setWhbmid(user.getBmdm());
		wr.setZt(1);
		//修改类型
		wr.setRec_xglx(UpdateTypeEnum.DELETE.getId());
		//操作人id
		wr.setRec_czrid(user.getId());
		//操作时间
		wr.setRec_czsj(new Date());
		//主表id
		wr.setRec_zbid(workrequire.getId());	
		workRequireRecMapper.writeLog(wr);
		
	}

	@Override
	public String saveWorkRequires(WorkRequire workrequire)
			throws BusinessException {
		        Integer xc=0;
				User user=ThreadVarUtil.getUser();
				String czls=UUID.randomUUID().toString();
				workrequire.setId(czls);
				workrequire.setWhsj(new Date());
				workrequire.setWhrid(user.getId());
				workrequire.setWhbmid(user.getBmdm());
				workrequire.setZt(1);
				//获取项次
				List<WorkRequire> requires=workRequireMapper.queryXc(workrequire.getMainid());
				WorkRequire re=requires.get(0);
				xc=re.getXc();
				workrequire.setXc(xc);
				workRequireMapper.saveWorkRequires(workrequire);
				//添加日志
				WorkRequireRec wr=new WorkRequireRec();
				wr.setId(UUID.randomUUID().toString());
				wr.setMainid(workrequire.getMainid());
				wr.setGwyq(workrequire.getGwyq());
				wr.setXc(xc);
				wr.setWhsj(new Date());
				wr.setWhrid(user.getId());
				wr.setWhbmid(user.getBmdm());
				wr.setZt(1);
				//修改类型
				wr.setRec_xglx(UpdateTypeEnum.SAVE.getId());
				//操作人id
				wr.setRec_czrid(user.getId());
				//操作时间
				wr.setRec_czsj(new Date());
				//主表id
				wr.setRec_zbid(czls);
				
				workRequireRecMapper.writeLog(wr);
				
				
		        return null;
	}

	@Override
	public String updateWorkRequires(WorkRequire workrequire)
			throws BusinessException {
		User user=ThreadVarUtil.getUser();
		workrequire.setWhsj(new Date());
		workrequire.setWhrid(user.getId());
		workrequire.setWhbmid(user.getBmdm());
		workRequireMapper.updateWorkRequiresByPrimaryKey(workrequire);
		//写日志
		WorkRequireRec wr=new WorkRequireRec();
		wr.setId(UUID.randomUUID().toString());
		wr.setMainid(workrequire.getMainid());
		wr.setGwyq(workrequire.getGwyq());
		wr.setXc(workrequire.getXc());
		wr.setWhsj(new Date());
		wr.setWhrid(user.getId());
		wr.setWhbmid(user.getBmdm());
		wr.setZt(1);
		//修改类型
		wr.setRec_xglx(UpdateTypeEnum.UPDATE.getId());
		//操作人id
		wr.setRec_czrid(user.getId());
		//操作时间
		wr.setRec_czsj(new Date());
		//主表id
		wr.setRec_zbid(workrequire.getId());
		
		workRequireRecMapper.writeLog(wr);
		return null;
	}

}
