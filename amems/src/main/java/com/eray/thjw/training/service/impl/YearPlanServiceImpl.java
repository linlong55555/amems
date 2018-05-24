package com.eray.thjw.training.service.impl;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.YearPlanMapper;
import com.eray.thjw.training.po.YearPlan;
import com.eray.thjw.training.service.YearPlanService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 年度计划service,用于业务逻辑处理
 */
@Service
public class YearPlanServiceImpl implements YearPlanService {
	
	/**
	 * @author liub
	 * @description 年度计划mapper
	 */
	@Resource
	private YearPlanMapper yearPlanMapper;
	
	/**
  	 * @author liub
  	 * @description 附件service
  	 */
	@Resource
	private AttachmentService attachmentService;
    
    /**
	 * @author liub
	 * @description 历史数据公共service
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	
	/**
	 * @author liub
	 * @description 新增或修改年度计划
	 * @param yearPlan
	 * @return id
	 * @throws BusinessException 
	 */
	@Override
	public String addOrUpdate(YearPlan yearPlan){
		String id = null;
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		yearPlan.setWhrid(user.getId());
		yearPlan.setWhbmid(user.getBmdm());
		YearPlan oldObj = yearPlanMapper.checkExistsByNdAndDprt(yearPlan);
		if(null == oldObj){
			//新增年度计划
			UUID uuid = UUID.randomUUID();//获取随机的uuid
			id = uuid.toString();
			yearPlan.setId(id);
			yearPlan.setZt(DelStatus.TAKE_EFFECT.getId());
			yearPlanMapper.insertSelective(yearPlan);
			commonRecService.write(yearPlan.getId(), TableEnum.B_P_007, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, yearPlan.getId());//保存历史记录信息
			attachmentService.eidtAttachment(yearPlan.getAttachments(), yearPlan.getId(), czls, yearPlan.getId(), yearPlan.getDprtcode(), LogOperationEnum.SAVE_WO);
		}else{
			id = oldObj.getId();
			//修改年度计划
			yearPlan.setId(id);
			yearPlanMapper.updateByPrimaryKeySelective(yearPlan);
			commonRecService.write(yearPlan.getId(), TableEnum.B_P_007, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, yearPlan.getId());//保存历史记录信息
			attachmentService.eidtAttachment(yearPlan.getAttachments(), yearPlan.getId(), czls, yearPlan.getId(), yearPlan.getDprtcode(), LogOperationEnum.EDIT);
		}
		return id;
	}
	
	/**
	 * @author liub
	 * @description 根据年度、机构代码查询年度计划、附件数
	 * @param nd、dprtcode
	 * @return YearPlan
	 */
	@Override
	public YearPlan selectByNdAndDprt(Integer nd,String dprtcode){
		return yearPlanMapper.selectByNdAndDprt(nd, dprtcode);
	}
	
}
