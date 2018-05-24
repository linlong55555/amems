package com.eray.thjw.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.project.dao.MinimumEquipmentListMapper;
import com.eray.thjw.project.po.MelChangeSheet;
import com.eray.thjw.project.po.MinimumEquipmentList;
import com.eray.thjw.project.service.MelDetailService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
@Service
public class MelDetailServiceImpl implements MelDetailService {

	@Autowired
	private MinimumEquipmentListMapper minimumEquipmentListMapper;
	
	/**
	 * @author liub
	 * @description 机型数据service
	 */
	@Autowired
	private PlaneModelDataService planeModelDataService;
	
	/**
  	 * @author liub
  	 * @description 附件service
  	 */
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * @author liub
	 * @description 日志service
	 */
	@Autowired
	private CommonRecService commonRecService;
	
	@Override
	public String save(MinimumEquipmentList minimumEquipmentList) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(minimumEquipmentList.getJx());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), user.getJgdm(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		checkExists(minimumEquipmentList);
		//新增Mel更改单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		minimumEquipmentList.setId(id);
		minimumEquipmentList.setWhrid(user.getId());
		minimumEquipmentList.setWhbmid(user.getBmdm());
		minimumEquipmentList.setZt(EffectiveEnum.YES.getId());
		//新增Mel清单附件
		if(minimumEquipmentList.getAttachment() != null){
			minimumEquipmentList.setMelqdfjid(UUID.randomUUID().toString());
			attachmentService.addAttachment(minimumEquipmentList.getAttachment(), minimumEquipmentList.getMelqdfjid(), czls, minimumEquipmentList.getId(), minimumEquipmentList.getDprtcode(),LogOperationEnum.SAVE_WO);
		}
		minimumEquipmentListMapper.insertSelective(minimumEquipmentList);
		commonRecService.write(id, TableEnum.B_G_008, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, minimumEquipmentList.getId());//保存历史记录信息
		return id;
	}
	
	
	@Override
	public String edit(MinimumEquipmentList minimumEquipmentList) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(minimumEquipmentList.getJx());
		MinimumEquipmentList oldMinimumEquipmentList = minimumEquipmentListMapper.selectByPrimaryKey(minimumEquipmentList.getId());
		if(!planeModelDataService.existsModel(user.getId(), user.getUserType(), oldMinimumEquipmentList.getDprtcode(), jxList)){
			throw new BusinessException("没有该机型的权限,请联系管理员!");
		}
		checkExists(minimumEquipmentList);
		minimumEquipmentList.setWhrid(user.getId());
		minimumEquipmentList.setWhbmid(user.getBmdm());
		minimumEquipmentList.setZt(EffectiveEnum.YES.getId());
		//新增Mel清单附件
		if(null != oldMinimumEquipmentList.getMelqdfjid() && !"".equals(oldMinimumEquipmentList.getMelqdfjid())){
			if(minimumEquipmentList.getAttachment() == null){
				minimumEquipmentList.setMelqdfjid("");
				attachmentService.delFiles(oldMinimumEquipmentList.getMelqdfjid(), czls, minimumEquipmentList.getId(),LogOperationEnum.EDIT);
			}else{
				attachmentService.editAttachment(minimumEquipmentList.getAttachment(), oldMinimumEquipmentList.getMelqdfjid(), czls, minimumEquipmentList.getId(), minimumEquipmentList.getDprtcode(),LogOperationEnum.EDIT);
			}
		}else{
			if(minimumEquipmentList.getAttachment() != null){
				minimumEquipmentList.setMelqdfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(minimumEquipmentList.getAttachment(), minimumEquipmentList.getMelqdfjid(), czls, minimumEquipmentList.getId(), minimumEquipmentList.getDprtcode(),LogOperationEnum.EDIT);
			}
		}
		minimumEquipmentListMapper.updateByPrimaryKeySelective(minimumEquipmentList);
		commonRecService.write(minimumEquipmentList.getId(), TableEnum.B_G_008, user.getId(),czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, minimumEquipmentList.getId());//保存历史记录信息
		return minimumEquipmentList.getId();
	}
	
	
	@Override
	public List<MinimumEquipmentList> queryAllPageList(MinimumEquipmentList minimumEquipmentList){
		return minimumEquipmentListMapper.queryAllPageList(minimumEquipmentList);
	}
	
	@Override
	public MinimumEquipmentList selectById(String id){
		return minimumEquipmentListMapper.selectById(id);
	}
	
	/**
	 * @author liub
	 * @description 检查是否存在
	 * @param minimumEquipmentList
	 */
	private void checkExists(MinimumEquipmentList minimumEquipmentList) throws BusinessException{
		StringBuffer message = new StringBuffer();
		List<MinimumEquipmentList> MinimumEquipmentLists = minimumEquipmentListMapper.checkExists(minimumEquipmentList);
		for (MinimumEquipmentList me : MinimumEquipmentLists) {
			if(null != minimumEquipmentList.getId() && minimumEquipmentList.getId().equals(me.getId())){
				continue;
			}
			if(null != minimumEquipmentList.getJx() && minimumEquipmentList.getJx().equals(me.getJx())){
				message.append("机型").append(minimumEquipmentList.getJx()).append("已存在!");
				break;
			}
		}
		if(!"".equals(message) && message.length() > 0){
			throw new BusinessException(message.toString());
		}
	}
	
}
