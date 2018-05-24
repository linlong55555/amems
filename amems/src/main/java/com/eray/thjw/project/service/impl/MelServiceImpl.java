package com.eray.thjw.project.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.dao.OrderSourceMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.User;
import com.eray.thjw.project.dao.MelChangeSheetAndBasisMapper;
import com.eray.thjw.project.dao.MelChangeSheetMapper;
import com.eray.thjw.project.dao.MinimumEquipmentListMapper;
import com.eray.thjw.project.po.MelChangeSheet;
import com.eray.thjw.project.po.MelChangeSheetAndBasis;
import com.eray.thjw.project.po.MinimumEquipmentList;
import com.eray.thjw.project.service.MelService;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.Todo;
import com.eray.thjw.project2.service.TodoService;
import com.eray.thjw.project2.service.TodorsService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.CommonService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.mel.MelStatusEnum;
import enu.ordersource.OrderSourceEnum;
import enu.produce.NodeEnum;
import enu.project2.BulletinStatusEnum;
import enu.project2.ProjectBusinessEnum;
import enu.project2.SendOrderEnum;
import enu.project2.TodoEnum;
import enu.project2.TodoStatusEnum;
import enu.project2.WorkCardStatusEnum;
@Service
public class MelServiceImpl implements MelService {

	@Resource
	private MelChangeSheetMapper melChangeSheetMapper;
	@Resource
	private TodoService todoService;
	@Resource
	private CommonService commonService;
	@Resource
	private MelChangeSheetAndBasisMapper melChangeSheetAndBasisMapper;
	@Resource
	private MinimumEquipmentListMapper minimumEquipmentListMapper;
	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private TodorsService todorsService;
	@Resource
	private OrderSourceMapper orderSourceMapper;
	
	/**
	 * @author liub
	 * @description 保存Mel更改单
	 * @param melChangeSheet
	 * @return String
	 */
	@Override
	public String save(MelChangeSheet melChangeSheet) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		Date currentDate = commonService.getSysdate();//当前时间		
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(melChangeSheet.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), user.getJgdm(), jxList);
		//新增Mel更改单
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		melChangeSheet.setId(id);
		melChangeSheet.setZdrid(user.getId());
		melChangeSheet.setZdbmid(user.getBmdm());
		melChangeSheet.setDprtcode(user.getJgdm());
		//检查Mel更改单文件编号是否存在
		checkExists(melChangeSheet);
		//新增Mel清单附件
		if(melChangeSheet.getAttachment() != null){
			melChangeSheet.setMelqdfjid(UUID.randomUUID().toString());
			attachmentService.addAttachment(melChangeSheet.getAttachment(), melChangeSheet.getMelqdfjid(), czls, melChangeSheet.getId(), melChangeSheet.getDprtcode(),LogOperationEnum.SAVE_WO);
		}
		melChangeSheetMapper.insertSelective(melChangeSheet);
		commonRecService.write(id, TableEnum.B_G_019, user.getId(),czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, melChangeSheet.getId());//保存历史记录信息
		attachmentService.eidtAttachment(melChangeSheet.getAttachments(), melChangeSheet.getId(), czls, melChangeSheet.getId(), melChangeSheet.getDprtcode(),LogOperationEnum.SAVE_WO);
		//新增更改单-修改依据
		List<MelChangeSheetAndBasis> melChangeSheetAndBasiList = melChangeSheet.getMelChangeSheetAndBasiList();
		if(null != melChangeSheetAndBasiList && melChangeSheetAndBasiList.size() > 0){
			List<String> columnValueList = new ArrayList<String>();
			for (MelChangeSheetAndBasis basis : melChangeSheetAndBasiList) {
				basis.setId(UUID.randomUUID().toString());
				basis.setMainid(id);
				columnValueList.add(id);
			}
			melChangeSheetAndBasisMapper.insertBatch(melChangeSheetAndBasiList);
			if(columnValueList.size() > 0){
				commonRecService.write("id", columnValueList, TableEnum.B_G_01901, user.getId(),czls,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, id);//插入日志
			}
		}
		// 下达指令
		if (melChangeSheet.getIsList() != null) {
			List<String> lyidList = doInstructionsource(melChangeSheet);
			// 新增待办事宜处理结果
			todorsService.insertTodorsList(melChangeSheet.getJx(), lyidList, SendOrderEnum.MEL.getId(), melChangeSheet.getZdrid(),
					melChangeSheet.getId(), melChangeSheet.getGgdbh(), melChangeSheet.getXghBb().toString(), melChangeSheet.getXghBb().toString(), melChangeSheet.getZt());
		}
		
		//保存附件信息
		attachmentService.eidtAttachment(melChangeSheet.getAttachmentList(), id, czls, id, melChangeSheet.getDprtcode(), LogOperationEnum.SAVE_WO);
		
		if(melChangeSheet.getZt()==2){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(melChangeSheet.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(5);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审核");
			strSm.append(melChangeSheet.getJx());
			strSm.append("机型的");
			strSm.append(melChangeSheet.getGgdbh());
			strSm.append("R");
			strSm.append(melChangeSheet.getXghBb());
			strSm.append("MEL变更。");
			todoService.insertSelectiveTechnical(melChangeSheet,strSm.toString(),"project:mel:main:05",NodeEnum.NODE2.getId(),null,TodoEnum.MEL.getId());
		}
		
		
		return id;
	}
	/**
	 * @CreateBy 孙霁
	 * @description 处理下达指令
	 * @param record
	 */
	private List<String> doInstructionsource(MelChangeSheet record) {
		List<String> lyidList = new ArrayList<String>();
		List<Instructionsource> isList = record.getIsList();
		for (Instructionsource is : isList) {
			is.setZlbh(record.getGgdbh());
			is.setZlid(record.getId());
			is.setZlxl(ProjectBusinessEnum.MEL.getId());
			is.setZlbb(record.getXghBb().toString());
			is.setYwzt(record.getXghBb().toString());
			is.setYwdjzt(record.getZt());
			lyidList.add(is.getPgdid());
		}
		instructionsourceMapper.insertInstructionSource(isList);
		return lyidList;
	}
	
	
	
	/**
	 * @author liub
	 * @description 保存Mel更改单
	 * @param melChangeSheet
	 * @return String
	 */
	@Override
	public String edit(MelChangeSheet melChangeSheet) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		Date currentDate = commonService.getSysdate();//当前时间	
		User user = ThreadVarUtil.getUser();
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(melChangeSheet.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), melChangeSheet.getDprtcode(), jxList);
		MelChangeSheet oldMelChangeSheet = melChangeSheetMapper.selectByPrimaryKey(melChangeSheet.getId());
		if(oldMelChangeSheet == null){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		/* 验证重复提交 begin */
		verification(new Integer[]{MelStatusEnum.SAVE.getId(),MelStatusEnum.AUDIT_DISMISSED.getId(),MelStatusEnum.APPROVED_DISMISSED.getId()},oldMelChangeSheet.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		//检查Mel更改单文件编号是否存在
		checkExists(melChangeSheet);
		//新增Mel更改单
		melChangeSheet.setZdrid(user.getId());
		melChangeSheet.setZdbmid(user.getBmdm());
		//新增Mel清单附件
		if(null != oldMelChangeSheet.getMelqdfjid() && !"".equals(oldMelChangeSheet.getMelqdfjid())){
			if(melChangeSheet.getAttachment() == null){
				melChangeSheet.setMelqdfjid("");
				attachmentService.delFiles(oldMelChangeSheet.getMelqdfjid(), czls, melChangeSheet.getId(),LogOperationEnum.EDIT);
			}else{
				attachmentService.editAttachment(melChangeSheet.getAttachment(), oldMelChangeSheet.getMelqdfjid(), czls, melChangeSheet.getId(), melChangeSheet.getDprtcode(),LogOperationEnum.EDIT);
			}
		}else{
			if(melChangeSheet.getAttachment() != null){
				melChangeSheet.setMelqdfjid(UUID.randomUUID().toString());
				attachmentService.addAttachment(melChangeSheet.getAttachment(), melChangeSheet.getMelqdfjid(), czls, melChangeSheet.getId(), melChangeSheet.getDprtcode(),LogOperationEnum.EDIT);
			}
		}
		
		melChangeSheetMapper.updateByPrimaryKeySelective(melChangeSheet);
		if(melChangeSheet.getZt()!=null && melChangeSheet.getZt()==MelStatusEnum.SUBMIT.getId()){
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(),czls ,LogOperationEnum.SUBMIT_WO, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//保存历史记录信息
		}else{
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(),czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//保存历史记录信息
		}
		attachmentService.eidtAttachment(melChangeSheet.getAttachments(), melChangeSheet.getId(), czls, melChangeSheet.getId(), melChangeSheet.getDprtcode(),LogOperationEnum.EDIT);
		//编辑更改单-修改依据
		Map<String, MelChangeSheetAndBasis> oldBasisMap = new HashMap<String, MelChangeSheetAndBasis>();
		List<String> newBasisIdList = new ArrayList<String>();
		List<MelChangeSheetAndBasis> oldBasisList = melChangeSheetAndBasisMapper.queryByMainid(melChangeSheet.getId());
		for (MelChangeSheetAndBasis s : oldBasisList) {
			oldBasisMap.put(s.getYjlx(), s);
		}
		List<MelChangeSheetAndBasis> melChangeSheetAndBasiList = melChangeSheet.getMelChangeSheetAndBasiList();
		if(null != melChangeSheetAndBasiList && melChangeSheetAndBasiList.size() > 0){
			for (MelChangeSheetAndBasis basis : melChangeSheetAndBasiList) {
				newBasisIdList.add(basis.getYjlx());
				MelChangeSheetAndBasis oldM = oldBasisMap.get(basis.getYjlx());
				if(null != oldM){
					basis.setId(oldM.getId());
					melChangeSheetAndBasisMapper.updateByPrimaryKeySelective(basis);
					commonRecService.write(basis.getId(), TableEnum.B_G_01901, user.getId(), czls ,LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//保存历史记录信息
				}else{
					basis.setId(UUID.randomUUID().toString());
					basis.setMainid(melChangeSheet.getId());
					melChangeSheetAndBasisMapper.insertSelective(basis);
					commonRecService.write(basis.getId(), TableEnum.B_G_01901, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, melChangeSheet.getId());//保存历史记录信息
				}
			}
		}
		//删除信息
		for (String key : oldBasisMap.keySet()) {
			if(!newBasisIdList.contains(key)){
				commonRecService.write(oldBasisMap.get(key).getId(), TableEnum.B_G_01901, user.getId(), czls ,LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, melChangeSheet.getId());//保存历史记录信息
				melChangeSheetAndBasisMapper.deleteByPrimaryKey(oldBasisMap.get(key).getId());
			}
		}

		// 删除旧的下达指令
		instructionsourceMapper.deleteInstructionSourceByZlid(melChangeSheet.getId());
		// 删除待办事宜待处理结果
		todorsService.deleteTodorsByYwid(melChangeSheet.getId());
		// 新增下达指令
		if (melChangeSheet.getIsList() != null) {
			List<String> lyidList = doInstructionsource(melChangeSheet);
			// 当前状态不为保存状态就要新增待办事宜
			if (melChangeSheet.getZt() != OrderSourceEnum.NOT_EVALUATED.getId()) {
				todorsService.insertTodorsList(melChangeSheet.getJx(), lyidList, SendOrderEnum.MEL.getId(), melChangeSheet.getZdrid(),
						melChangeSheet.getId(), melChangeSheet.getGgdbh(), melChangeSheet.getXghBb().toString(), melChangeSheet.getXghBb().toString(), melChangeSheet.getZt());
			}
		}
		
		//编辑附件信息
		attachmentService.eidtAttachment(melChangeSheet.getAttachmentList(), melChangeSheet.getId(), czls, melChangeSheet.getId(), melChangeSheet.getDprtcode(), LogOperationEnum.EDIT);
		
		if(melChangeSheet.getZt()==2){
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(melChangeSheet.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(5);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审核");
			strSm.append(melChangeSheet.getJx());
			strSm.append("机型的");
			strSm.append(melChangeSheet.getGgdbh());
			strSm.append("R");
			strSm.append(melChangeSheet.getXghBb());
			strSm.append("MEL变更。");
			todoService.insertSelectiveTechnical(melChangeSheet,strSm.toString(),"project:mel:main:05",NodeEnum.NODE2.getId(),null,TodoEnum.MEL.getId());
		}
		
		return melChangeSheet.getId();
	}
	
	/**
	 * @author liub
	 * @description  作废
	 * @param id
	 */
	@Override
	public void deleteById(String id) throws BusinessException {
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		MelChangeSheet oldMelChangeSheet = melChangeSheetMapper.selectByPrimaryKey(id);
		if(oldMelChangeSheet == null){
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(oldMelChangeSheet.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), oldMelChangeSheet.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		verification(new Integer[]{MelStatusEnum.SAVE.getId(),MelStatusEnum.AUDIT_DISMISSED.getId(),MelStatusEnum.APPROVED_DISMISSED.getId()},oldMelChangeSheet.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		commonRecService.write(id, TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.DELETE, UpdateTypeEnum.DELETE, id);//保存历史记录信息
		melChangeSheetMapper.deleteByPrimaryKey(id);
		todorsService.deleteTodorsByYwid(id);// 删除待办事宜待处理结果
		instructionsourceMapper.deleteInstructionSourceByZlid(id);// 删除下达指令
		
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(id);
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	}
	
	/**
	 * @author liub
	 * @description 审核
	 * @param melChangeSheet
	 */
	@Override
	public void updateAudit(MelChangeSheet melChangeSheet) throws BusinessException{
		Date currentDate = commonService.getSysdate();//当前时间	
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		MelChangeSheet oldMelChangeSheet = melChangeSheetMapper.selectByPrimaryKey(melChangeSheet.getId());
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(oldMelChangeSheet.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), oldMelChangeSheet.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		verification(new Integer[]{MelStatusEnum.SUBMIT.getId()},oldMelChangeSheet.getZt(),"该数据已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		melChangeSheet.setShrid(user.getId());
		melChangeSheet.setShbmid(user.getBmdm());
		melChangeSheetMapper.updateByPrimaryKeySelective(melChangeSheet);
		if(MelStatusEnum.AUDIT_DISMISSED.getId().intValue() == melChangeSheet.getZt().intValue()){
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.SHENHEBOHUI_WO, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//插入日志
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(oldMelChangeSheet.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(oldMelChangeSheet.getJx());
			strSm.append("机型的");
			strSm.append(oldMelChangeSheet.getGgdbh());
			strSm.append("R");
			strSm.append(oldMelChangeSheet.getXghBb());
			strSm.append("MEL变更已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(oldMelChangeSheet,strSm.toString(),"project:mel:main:02",NodeEnum.NODE5.getId(),oldMelChangeSheet.getZdrid(),TodoEnum.MEL.getId());
		
		}else{
			
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//插入日志
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(oldMelChangeSheet.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(2);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append("请审批");
			strSm.append(oldMelChangeSheet.getJx());
			strSm.append("机型的");
			strSm.append(oldMelChangeSheet.getGgdbh());
			strSm.append("R");
			strSm.append(oldMelChangeSheet.getXghBb());
			strSm.append("MEL变更。");
			todoService.insertSelectiveTechnical(oldMelChangeSheet,strSm.toString(),"project:mel:main:05",NodeEnum.NODE3.getId(),null,TodoEnum.MEL.getId());
		
		}
		instructionsourceMapper.updateYwdjztByZlid(melChangeSheet.getZt(), melChangeSheet.getId());// 更新下达指令业务单号状态
		todorsService.updateYwdjztByYwid(melChangeSheet.getZt(), melChangeSheet.getId());// 更新待办事宜状态
		
	}
	
	/**
	 * @author liub
	 * @description 批准
	 * @param melChangeSheet
	 */
	@Override
	public void updateApprove(MelChangeSheet melChangeSheet) throws BusinessException{
		
		Date currentDate = commonService.getSysdate();//当前时间
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		MelChangeSheet oldMelChangeSheet = melChangeSheetMapper.selectByPrimaryKey(melChangeSheet.getId());
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(oldMelChangeSheet.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), oldMelChangeSheet.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		verification(new Integer[]{MelStatusEnum.AUDITED.getId()},oldMelChangeSheet.getZt(),"该Mel已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		melChangeSheet.setPfrid(user.getId());
		melChangeSheet.setPfbmid(user.getBmdm());
		melChangeSheetMapper.updateByPrimaryKeySelective(melChangeSheet);
		if(MelStatusEnum.APPROVED_DISMISSED.getId().intValue() == melChangeSheet.getZt().intValue()){
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.SHENPIBOHUI_WO, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//插入日志
		
			
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(oldMelChangeSheet.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
			//拼接说明
			StringBuffer strSm = new StringBuffer();
			strSm.append(oldMelChangeSheet.getJx());
			strSm.append("机型的");
			strSm.append(oldMelChangeSheet.getGgdbh());
			strSm.append("R");
			strSm.append(oldMelChangeSheet.getXghBb());
			strSm.append("MEL变更已经驳回，请重新提交。");
			todoService.insertSelectiveTechnical(oldMelChangeSheet,strSm.toString(),"project:mel:main:02",NodeEnum.NODE5.getId(),oldMelChangeSheet.getZdrid(),TodoEnum.MEL.getId());
		}else{
			minimumEquipmentListMapper.updateByNotEffective(oldMelChangeSheet.getJx(), oldMelChangeSheet.getDprtcode());
			//新增Mel清单
			MinimumEquipmentList equipment = new MinimumEquipmentList();
			equipment.setId(oldMelChangeSheet.getId());
			equipment.setDprtcode(oldMelChangeSheet.getDprtcode());
			equipment.setJx(oldMelChangeSheet.getJx());
			equipment.setBb(oldMelChangeSheet.getXghBb());
			equipment.setMelbgdid(oldMelChangeSheet.getId());
			equipment.setMelqdfjid(oldMelChangeSheet.getMelqdfjid());
			equipment.setWhrid(oldMelChangeSheet.getZdrid());
			equipment.setWhbmid(oldMelChangeSheet.getZdbmid());
			equipment.setZt(EffectiveEnum.YES.getId());
			minimumEquipmentListMapper.insertSelective(equipment);
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_008, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, oldMelChangeSheet.getId());//插入日志
			commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//插入日志
		
			//关闭待办
			Todo tododbyw=new Todo();
			tododbyw.setDbywid(oldMelChangeSheet.getId());
			List<Integer> jdlist=new ArrayList<Integer>();
			jdlist.add(3);
			tododbyw.getParamsMap().put("jdlist", jdlist);
			tododbyw.setZt(TodoStatusEnum.DCL.getId());
			tododbyw.setBlrid(user.getId());
			tododbyw.setFksj(currentDate);
			todoService.updateByDbyw(tododbyw);
		}
		instructionsourceMapper.updateYwdjztByZlid(oldMelChangeSheet.getZt(), oldMelChangeSheet.getId());// 更新下达指令业务单号状态
		todorsService.updateYwdjztByYwid(melChangeSheet.getZt(), melChangeSheet.getId());// 更新待办事宜状态
	}
	
	/**
	 * @author liub
	 * @description 指定结束
	 * @param melChangeSheet
	 */
	@Override
	public void updateShutDown(MelChangeSheet melChangeSheet) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		MelChangeSheet oldMelChangeSheet = melChangeSheetMapper.selectByPrimaryKey(melChangeSheet.getId());
		//检查用户机型的权限
		List<String> jxList = new ArrayList<String>();
		jxList.add(oldMelChangeSheet.getJx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), oldMelChangeSheet.getDprtcode(), jxList);
		/* 验证重复提交 begin */
		verification(new Integer[]{MelStatusEnum.SUBMIT.getId(),
								   MelStatusEnum.AUDITED.getId(),
								   MelStatusEnum.APPROVED.getId()},oldMelChangeSheet.getZt(),"该Mel已更新，请刷新后再进行操作!");
		/* 验证重复提交 end */
		melChangeSheetMapper.updateByPrimaryKeySelective(melChangeSheet);
		commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.GUANBI, UpdateTypeEnum.UPDATE, melChangeSheet.getId());//插入日志
		instructionsourceMapper.updateYwdjztByZlid(melChangeSheet.getZt(), melChangeSheet.getId());// 更新下达指令业务单号状态
		todorsService.updateYwdjztByYwid(melChangeSheet.getZt(), melChangeSheet.getId());// 更新待办事宜状态
		 
		//删除待办
		Todo tododbyw=new Todo();
		tododbyw.setDbywid(oldMelChangeSheet.getId());
		List<Integer> jdlist=new ArrayList<Integer>();
		jdlist.add(2);
		jdlist.add(3);
		jdlist.add(5);
		tododbyw.getParamsMap().put("jdlist", jdlist);
		todoService.deletedbyw(tododbyw);
	} 
	
	@Override
	public List<MelChangeSheet> queryAllPageList(MelChangeSheet melChangeSheet){
		List<MelChangeSheet> list = melChangeSheetMapper.queryAllPageList(melChangeSheet);
		setOrderSource(list);
		return list;
	}
	
	@Override
	public MelChangeSheet selectById(String id){
		return melChangeSheetMapper.selectById(id);
	}
	
	/**
	 * @author liub
	 */
	private void setOrderSource(List<MelChangeSheet> mList){
		if(mList == null || mList.size() == 0){
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();//参数
		List<String> idList = new ArrayList<String>();
		for (MelChangeSheet ms : mList) {
			idList.add(ms.getId());
		}
		paramMap.put("ids", idList);
		List<OrderSource> orderSources = orderSourceMapper.selectOrderSourceList(paramMap);
		if (orderSources!=null && !orderSources.isEmpty()) {
			Map<String, List<OrderSource>> map = new HashMap<String, List<OrderSource>>();
			for (OrderSource orderSource : orderSources) {
				List<OrderSource> resultList = map.get(orderSource.getZlid());
				if (resultList == null) {
					resultList = new ArrayList<OrderSource>();
					map.put(orderSource.getZlid(), resultList);
				}
				resultList.add(orderSource);
			}
			for (MelChangeSheet ms : mList) {
				ms.setOrderSourceList(map.get(ms.getId()));
			}
		}
	}
	
	/**
	 * @author liub
	 * @description 检查是否存在
	 * @param melChangeSheet
	 */
	private void checkExists(MelChangeSheet melChangeSheet) throws BusinessException{
		StringBuffer message = new StringBuffer();
		List<MelChangeSheet> melChangeSheetList = melChangeSheetMapper.checkExists(melChangeSheet);
		for (MelChangeSheet ms : melChangeSheetList) {
			if(null != melChangeSheet.getId() && melChangeSheet.getId().equals(ms.getId())){
				continue;
			}
			if(null != melChangeSheet.getGgdbh() && melChangeSheet.getGgdbh().equals(ms.getGgdbh())){
				message.append("文件编号").append(melChangeSheet.getGgdbh()).append("已存在!");
			}
		}
		if(!"".equals(message) && message.length() > 0){
			throw new BusinessException(message.toString());
		}
	}
	
	/**
	 * @author liub
	 * @description 验证是否存在
	 * @param i,j,message
	 */
	private void verification(Integer[] i, Integer j,String message) throws BusinessException{
		if(j != null && !ArrayUtils.contains(i,j)){
			throw new BusinessException(message);
		}
	}
	/**
	 * 
	 * @Description 批量审核
	 * @CreateTime 2017-8-30 下午4:25:34
	 * @CreateBy 孙霁
	 * @param idList
	 * @param yj
	 * @throws BusinessException
	 */
	@Override
	public String updateBatchAudit(List<String> idList, String yj)
			throws BusinessException {
		List<String> auditIdList = new ArrayList<String>();//可审核的工卡id集合
		List<String> jxList = new ArrayList<String>();//用于检查机型权限
		StringBuffer buffer = new StringBuffer();
		User user = ThreadVarUtil.getUser();
		List<MelChangeSheet> melChangeSheetList = melChangeSheetMapper.queryByIdList(idList);
		String dprtcode = null;
		for (MelChangeSheet mel : melChangeSheetList) {
			if(null == dprtcode){
				dprtcode = mel.getDprtcode();
			}
			if(WorkCardStatusEnum.SUBMIT.getId().intValue() == mel.getZt().intValue()){
				jxList.add(mel.getJx());
				auditIdList.add(mel.getId());
				buffer.append("MEL【").append(mel.getGgdbh()).append("】审核通过!<br>");
			}else{
				buffer.append("MEL【").append(mel.getGgdbh()).append("】数据已更新,未能审核成功!<br>");
			}
		}
		//检查用户机型的权限
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
		if(null != auditIdList && auditIdList.size() > 0){
			String czls = UUID.randomUUID().toString();//操作流水
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("shrid", user.getId());
			paramMap.put("shbmid", user.getBmdm());
			paramMap.put("idList", auditIdList);
			paramMap.put("shyj", yj);
			melChangeSheetMapper.updateBatchAudit(paramMap);
			//保存历史记录信息
			commonRecService.write("id", auditIdList, TableEnum.B_G_019, user.getId(), czls, LogOperationEnum.YISHENHE_WO, UpdateTypeEnum.UPDATE, null);
			instructionsourceMapper.updateYwdjztByZlidList(BulletinStatusEnum.AUDIT.getId(), auditIdList);// 批量更新下达指令状态
			todorsService.updateZtByYwidList(BulletinStatusEnum.AUDIT.getId(), auditIdList);// 批量更新待办事宜数据状态
			
			//批量新增待办事宜
			todoService.saveBatchTodoAuditList(auditIdList,TodoEnum.MEL.getId());
		}
		return buffer.toString();
	}
	/**
	 * 
	 * @Description 批量批准
	 * @CreateTime 2017-8-30 下午4:25:34
	 * @CreateBy 孙霁
	 * @param idList
	 * @param yj
	 * @throws BusinessException
	 */
	@Override
	public String updateBatchApprove(List<String> idList, String yj) throws BusinessException {
		Date currentDate = commonService.getSysdate();//当前时间
		List<String> approveIdList = new ArrayList<String>();//可批准的工卡id集合
		List<String> jxList = new ArrayList<String>();//用于检查机型权限
		StringBuffer buffer = new StringBuffer();
		User user = ThreadVarUtil.getUser();
		List<MelChangeSheet> melChangeSheetList = melChangeSheetMapper.queryByIdList(idList);
		List<MelChangeSheet> melList = new ArrayList<MelChangeSheet>();
		String dprtcode = null;
		for (MelChangeSheet mel : melChangeSheetList) {
			if(null == dprtcode){
				dprtcode = mel.getDprtcode();
			}
			if(WorkCardStatusEnum.AUDITED.getId().intValue() == mel.getZt().intValue()){
				jxList.add(mel.getJx());
				approveIdList.add(mel.getId());
				buffer.append("MEL【").append(mel.getGgdbh()).append("】批准通过!<br>");
				melList.add(mel);
			}else{
				buffer.append("MEL【").append(mel.getGgdbh()).append("】数据已更新,未能批准成功!<br>");
			}
		}
		//检查用户机型的权限
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), dprtcode, jxList);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String czls = UUID.randomUUID().toString();//操作流水
		paramMap.put("pfrid", user.getId());
		paramMap.put("pfbmid", user.getBmdm());
		paramMap.put("idList", approveIdList);
		paramMap.put("pfyj", yj);
		if(null != approveIdList && approveIdList.size() > 0){
			melChangeSheetMapper.updateBatchApprove(paramMap);
			//保存历史记录信息
			commonRecService.write("id",approveIdList, TableEnum.B_G_019, user.getId(), czls ,LogOperationEnum.YIPIZHUN_WO, UpdateTypeEnum.UPDATE, null);
			instructionsourceMapper.updateYwdjztByZlidList(BulletinStatusEnum.APPROVAL.getId(), approveIdList);// 批量更新下达指令状态
			todorsService.updateZtByYwidList(BulletinStatusEnum.APPROVAL.getId(), approveIdList);// 批量更新待办事宜数据状态
		}
		
		if(melList != null && melList.size() > 0){
			for (MelChangeSheet melChangeSheet : melList) {
				
				minimumEquipmentListMapper.updateByNotEffective(melChangeSheet.getJx(), melChangeSheet.getDprtcode());
				//新增Mel清单
				MinimumEquipmentList equipment = new MinimumEquipmentList();
				equipment.setId(melChangeSheet.getId());
				equipment.setDprtcode(melChangeSheet.getDprtcode());
				equipment.setJx(melChangeSheet.getJx());
				equipment.setBb(melChangeSheet.getXghBb());
				equipment.setMelbgdid(melChangeSheet.getId());
				equipment.setMelqdfjid(melChangeSheet.getMelqdfjid());
				equipment.setWhrid(melChangeSheet.getZdrid());
				equipment.setWhbmid(melChangeSheet.getZdbmid());
				equipment.setZt(EffectiveEnum.YES.getId());
				minimumEquipmentListMapper.insertSelective(equipment);
				commonRecService.write(melChangeSheet.getId(), TableEnum.B_G_008, user.getId(), czls ,LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, melChangeSheet.getId());//插入日志
			
				//关闭待办
				Todo tododbyw=new Todo();
				tododbyw.setDbywid(melChangeSheet.getId());
				List<Integer> jdlist=new ArrayList<Integer>();
				jdlist.add(3);
				tododbyw.getParamsMap().put("jdlist", jdlist);
				tododbyw.setZt(TodoStatusEnum.DCL.getId());
				tododbyw.setBlrid(user.getId());
				tododbyw.setFksj(currentDate);
				todoService.updateByDbyw(tododbyw);
			}
		}
		
		
		return buffer.toString();
	}
}
