	package com.eray.thjw.material2.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.material2.dao.MeasurementToolsDetailsHistoryMapper;
import com.eray.thjw.material2.dao.MeasurementToolsMapper;
import com.eray.thjw.material2.po.MeasurementTools;
import com.eray.thjw.material2.service.MeasurementToolsDetailsService;
import com.eray.thjw.material2.service.MeasurementToolsService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.util.ThreadVarUtil;
/**
 * 
 * @Description 计量工具serviceimpl
 * @CreateTime 2017年9月25日 下午1:57:31
 * @CreateBy 林龙
 */
@Service("measurementToolsService")
public class MeasurementToolsServiceImpl implements MeasurementToolsService  {

	@Resource
	private MeasurementToolsMapper measurementToolsMapper;
	@Resource
	private MeasurementToolsDetailsService measurementToolsDetailsService;
	@Resource
	private MeasurementToolsDetailsHistoryMapper measurementToolsDetailsHistoryMapper;
	@Resource
	private CommonService commonService;
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * @Description 保存计量工具
	 * @CreateTime 2017年8月16日 上午10:18:45
	 * @CreateBy 林龙
	 * @param measurementToolsDetails 计量工具
	 * @return 计量工具id
	 * @throws BusinessException
	 */
	@Override
	public String save(MeasurementTools measurementTools)throws BusinessException {
		User user = ThreadVarUtil.getUser();		//当前登陆人
		String id="";
		//根据部件，序列号，组织机构查询b_w_001
		MeasurementTools measurementToolsOld = measurementToolsMapper.selectBy(measurementTools);
		if(measurementToolsOld == null){
			String uuid = UUID.randomUUID().toString();	//uuid-主单id
			//新增计量工具 
			insertSelective(measurementTools,uuid,user);
			//新增b_w_001 计量工具主表
			measurementToolsDetailsService.insertList(measurementTools.getMeasurementToolsDetailsList(),user,uuid);
			id=uuid;
		}else{
			measurementToolsDetailsService.insertList(measurementTools.getMeasurementToolsDetailsList(),user,measurementToolsOld.getId());
			id=measurementToolsOld.getId();
		}
		
		
		return id;
	}

	/**
	 * 
	 * @Description 新增
	 * @CreateTime 2018年2月9日 下午1:23:46
	 * @CreateBy 林龙
	 * @param measurementTools
	 * @param uuid
	 * @param user
	 */
	private void insertSelective(MeasurementTools measurementTools,String uuid, User user) {
		Date currentDate = commonService.getSysdate();//当前时间
	
		measurementTools.setDprtcode(user.getJgdm());         	//组织机构
		measurementTools.setId(uuid);                         	//id
		measurementTools.setWhbmid(user.getBmdm());	        	//部门id
		measurementTools.setWhrid(user.getId());		        //用户id
		measurementTools.setIsJl(1);
		measurementTools.setZt(1);
		measurementTools.setWhsj(currentDate);
		
		//新增计量工具
		measurementToolsMapper.insertSelective(measurementTools);
	}
	
	
	
	
     
}