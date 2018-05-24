package com.eray.thjw.produce.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.framework.exception.SaibongException;
import com.eray.framework.saibong.SNGeneratorFactory;
import com.eray.thjw.baseinfo.dao.ProjectMapper;
import com.eray.thjw.baseinfo.po.Project;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.produce.dao.Workorder145Mapper;
import com.eray.thjw.produce.dao.Workpackage145Mapper;
import com.eray.thjw.produce.dao.WorkpackageMapper;
import com.eray.thjw.produce.po.Workorder145;
import com.eray.thjw.produce.po.Workpackage;
import com.eray.thjw.produce.po.Workpackage145;
import com.eray.thjw.produce.service.Workpackage145Service;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.PlaneModelDataService;
import com.eray.thjw.system.dao.SynRelMapper;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.SaiBongEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.produce.FeedbackStatusEnum;
import enu.produce.WorkpackageStatusEnum;

@Service
public class Workpackage145ServiceImpl implements Workpackage145Service {

	@Resource
	private Workpackage145Mapper workpackage145Mapper;
	@Resource
	private PlaneModelDataService planeModelDataService;
	@Resource
	private AttachmentService attachmentService;
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private Workorder145Mapper workorder145Mapper;
	@Resource
	private WorkpackageMapper workpackageMapper;
	@Resource
	private SynRelMapper synRelMapper;
	@Resource
	private ProjectMapper projectMapper;

	@Override
	public String addRecord(Workpackage145 record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		String id = UUID.randomUUID().toString();
		record.setId(id);
		

		
		record.setDprtcode(user.getJgdm());
		record.setWhrid(user.getId());
		record.setWhsj(new Date());
		record.setWhdwid(user.getBmdm());
		record.setZdrid(user.getId());
		record.setZdbmid(user.getBmdm());
		record.setZt(WorkpackageStatusEnum.SUBMIT.getId());
		record.setWgbs(FeedbackStatusEnum.NO.getId());
		
		//当页面没有填写编号时
		if(record.getGbbh() == null || "".equals(record.getGbbh())){ 
			String gbbh=setGbbh(record); //根据评估单对象获取最新编号
			record.setGbbh(gbbh);
		}else{
			// 验证编号唯一
			validationGbbh(record);
		}
		// 验证机型权限
		if(StringUtils.isNotBlank(record.getFjjx())){
			validationFjjx(record, user);
		}
		// 新增工包
		workpackage145Mapper.insertSelective(record);
		// 保存附件信息
		attachmentService.eidtAttachment(record.getAttachments(), id, czls, id, record.getDprtcode(),
				LogOperationEnum.SUBMIT_WO);
		// 工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.SAVE_WO,
				UpdateTypeEnum.SAVE, record.getId());
		//同步到135工包
//		doSync2WP135Add(record,user,czls);
		return id;
	}

	/**
	 * 
	 * @Description  自动生成编号
	 * @CreateTime 2017年8月17日 下午4:32:44
	 * @CreateBy 林龙
	 * @param technical
	 * @return pgdbhNew 编号
	 * @throws com.eray.framework.exception.SaibongException 
	 */
	private String setGbbh(Workpackage145 record) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		Workpackage tec = new Workpackage(); //new 对象
		Project project = new Project();
		project.setId(record.getXmid());
		project = projectMapper.getProjectById(project);
		record.setXmbh(project.getXmbm());
		boolean b = true;
		String dh =null;
		while(b){
			try {
				dh = SNGeneratorFactory.generate(user.getJgdm(), SaiBongEnum.GB145.getName(),record);
			} catch (SaibongException e) {
				throw new BusinessException(e);
			} 
			tec.setGbbh(dh);
			tec.setDprtcode(user.getJgdm());
			//根据技术文件对象查询技术文件数量
			int i = workpackage145Mapper.queryCount(tec); 
			if(i <= 0){
				b = false;
			}
		}
		return dh;
	}
	
	/**
	 * 
	 * @Description 145工包列表
	 * @CreateTime 2017年10月16日 下午5:28:17
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> getWorkpackageList(Workpackage145 record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<Workpackage145> recordList = workpackage145Mapper.selectAllList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						Workpackage145 newRecord = new Workpackage145();
						newRecord.setId(id);
						List<Workpackage145> newRecordList = workpackage145Mapper.selectAllList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<Workpackage145> newRecordList = new ArrayList<Workpackage145>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Workpackage145 newRecord = new Workpackage145();
					newRecord.setId(id);
					newRecordList = workpackage145Mapper.selectAllList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}
	
	/**
	 * @Description 查询航材工具需求清单工包信息 
	 * @CreateTime 2018-4-12 上午11:33:44
	 * @CreateBy 刘兵
	 * @param record
	 * @return Map<String, Object>
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryMToolDetail145List(Workpackage145 record) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		List<Workpackage145> recordList = workpackage145Mapper.selectAllList(record);
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for (Workpackage145 workpackage : recordList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", workpackage.getId());
			map.put("fjzch", workpackage.getFjzch());
			map.put("xlh", workpackage.getFjxlh());
			map.put("gbbh", workpackage.getGbbh());
			map.put("gbmc", workpackage.getGbmc());
			map.put("wxlx", workpackage.getWxlx());
			map.put("zdrq", workpackage.getZdrq());
			map.put("jhKsrq", workpackage.getJhKsrq());
			map.put("jhJsrq", workpackage.getJhJsrq());
			map.put("xfdwDprtname", workpackage.getXfdw());
			map.put("gzyq", workpackage.getGzyq());
			resultList.add(map);
		}
		resultMap.put("total", resultList.size());
		resultMap.put("rows", resultList);
		return resultMap;

	}

	/**
	 * 
	 * @Description 验证机型权限
	 * @CreateTime 2017年10月18日 上午10:47:23
	 * @CreateBy 岳彬彬
	 * @param record
	 * @param user
	 * @throws BusinessException
	 */
	private void validationFjjx(Workpackage145 record, User user) throws BusinessException {
		List<String> jxList = new ArrayList<String>();
		jxList.add(record.getFjjx());
		planeModelDataService.existsModel4Expt(user.getId(), user.getUserType(), record.getDprtcode(), jxList);
	}

	/**
	 * 
	 * @Description 验证工包编号唯一
	 * @CreateTime 2017年10月18日 上午10:50:30
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validationGbbh(Workpackage145 record) throws BusinessException {
		Integer count = workpackage145Mapper.getCountByGbbhAndDprtcode(record.getGbbh(), record.getDprtcode());
		if (count != null && count > 0) {
			throw new BusinessException("该工包编号已存在!");
		}
	}

	/**
	 * 
	 * @Description 根据id获取工包数据
	 * @CreateTime 2017年10月18日 下午2:42:34
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 * @throws BusinessException 
	 */
	@Override
	public Workpackage145 getRecord(Workpackage145 record) throws BusinessException {
		if("close".equals(record.getParamsMap().get("option"))){
			//验证工单是否都关闭或完成
			validation4Close(record);
		}else if("end".equals(record.getParamsMap().get("option"))){
			//验证工单是否都关闭或完成
			validation4Close(record);
		}
		Workpackage145 wp145 = workpackage145Mapper.getById(record.getId());
		Workpackage wp = workpackageMapper.selectByPrimaryKey(wp145.getId());//135工包
		if(null != wp) {
			wp145.getParamsMap().put("exist", "1");
		}else{
			wp145.getParamsMap().put("exist", "0");
		}
		return wp145;
	}

	/**
	 * 
	 * @Description 更新
	 * @CreateTime 2017年10月18日 下午4:21:23
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void updateRecord(Workpackage145 record) throws BusinessException {
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		// 验证机型
		if(StringUtils.isNotBlank(record.getFjjx())){
			validationFjjx(record, user);		
		}
		// 验证工包状态为1或2
		validation4Update(record);
		// 更新数据
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setZt(WorkpackageStatusEnum.SUBMIT.getId());
		workpackage145Mapper.updateWp(record);
		// 附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.UPDATE_UPDATE);
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.EDIT,
				UpdateTypeEnum.UPDATE, record.getId());
		//更新135工包
		doSync2WP135Update(record,user,czls);
		//更新工包下工单的飞机注册号
		doSync2WO145(record);
	}
	/**
	 * 
	 * @Description 验证工包状态为1或2
	 * @CreateTime 2017年10月18日 下午5:04:19
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4Update(Workpackage145 record) throws BusinessException {
		Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(record.getId());
		if (wp145 == null) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		} else if (wp145.getZt() != WorkpackageStatusEnum.SAVE.getId() && wp145.getZt() != WorkpackageStatusEnum.SUBMIT.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}

	/**
	 * 
	 * @Description 删除工包
	 * @CreateTime 2017年10月18日 下午5:11:15
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void deleteRecord(Workpackage145 record) throws BusinessException {
		//验证状态=1预组包 或 状态=提交且工包下没有工单(排除掉145工单.状态=9)
		validation4Delete(record);
		String czls = UUID.randomUUID().toString();
		User user = ThreadVarUtil.getUser();
		//工包日志
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, record.getId());
		//删除工包
		workpackage145Mapper.deleteByPrimaryKey(record.getId());
		//清除附件
		attachmentService.delFiles(record.getId(), czls, record.getId(), LogOperationEnum.DELETE);
		workpackageMapper.deleteByPrimaryKey(record.getId());
	}
	/**
	 * 
	 * @Description 状态=1预组包 或 状态=提交且工包下没有工单(排除掉145工单.状态=9)
	 * @CreateTime 2017年10月18日 下午5:20:17
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4Delete(Workpackage145 record) throws BusinessException {
		//验证状态为1或为2
		Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(record.getId());
		if (wp145 == null) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		} else if (wp145.getZt() != WorkpackageStatusEnum.SAVE.getId() && wp145.getZt() != WorkpackageStatusEnum.SUBMIT.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
		//工包状态为2 验证工包下是否有工单
		if(wp145.getZt() ==WorkpackageStatusEnum.SUBMIT.getId()){
			int count = workorder145Mapper.getCountByGbid(record.getId());
			if(count>0){
				throw new BusinessException("该工包下有未关闭工单，不能删除!");
			}
		}
	}
	/**
	 * 
	 * @Description 下发
	 * @CreateTime 2017年10月18日 下午5:36:00
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4Issued(Workpackage145 record) throws BusinessException {
		//验证工包下存在提交状态下的工单
		volidation4Issued(record);
		//验证工包下提交状态的工单,是否选择了项目编号,是否选择了主工种
		getWO4ValidationIssued(record);
		//处理 1 修改145工包数据
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setXfrid(user.getId());
		record.setXfrdwid(user.getBmdm());
		record.setZt(WorkpackageStatusEnum.TAKEEFFECT.getId());
		workpackage145Mapper.update4Issued(record);
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.ISSUED,
				UpdateTypeEnum.UPDATE, record.getId());
		
		//处理2 修改145工单数据
		workorder145Mapper.update4Issued(user.getBmdm(), user.getId(), record.getId());
		commonRecService.writeByWhere(" b.gbid = '".concat(record.getId()).concat("' ").concat("and b.zt=7").concat("and b.dxfbs=20"), TableEnum.B_S2_014, user.getId(),czls,LogOperationEnum.ISSUED, UpdateTypeEnum.UPDATE,null);
	}
	/**
	 * 
	 * @Description 验证工包下存在提交状态下的工单
	 * @CreateTime 2017年10月19日 上午9:20:54
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void volidation4Issued(Workpackage145 record) throws BusinessException {
		int count = workorder145Mapper.getSubmitCountByGbid(record.getId());
		if(count == 0){
			throw new BusinessException("该工包下没有提交状态的工单，不能下发!");
		}
	}
	/**
	 * 
	 * @Description 验证工包下提交状态的工单是否选择了主工种
	 * @CreateTime 2017年11月10日 上午11:05:21
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void getWO4ValidationIssued(Workpackage145 record) throws BusinessException {
		Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(record.getId());
		if(null == wp145.getXmid()||"".equals(wp145.getXmid())){
			throw new BusinessException("该工包没有选择项目，不能下发!");
		}
		List<Workorder145> list = workorder145Mapper.getWO4ValidationIssued(record.getId());
		StringBuffer buffer = new StringBuffer();
		boolean flag = true;
		for (Workorder145 workorder145 : list) {
			if(workorder145.getGzzid()==null||"".equals(workorder145.getGzzid())){
				buffer.append("145工单【").append(workorder145.getGdbh()).append("】没有选择主工种!<br/>");
				flag = false;
			}
		}
		if(flag==false){
			throw new BusinessException(buffer.toString());
		}
	}
	/**
	 * 
	 * @Description 完工反馈
	 * @CreateTime 2017年10月19日 上午9:42:52
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4Feedback(Workpackage145 record) throws BusinessException {
		//校验状态为7
		validation4Feedback(record);
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		//处理
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		doData(record);
		workpackage145Mapper.update4Feedback(record);
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.FEEDBACK,
				UpdateTypeEnum.UPDATE, record.getId());
		// 完工反馈附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.FEEDBACK);
		//更新135工包
		doSync2WP135Update(record,user,czls);
	}
	/**
	 * 
	 * @Description 验证工包状态为7下发
	 * @CreateTime 2017年10月19日 上午11:52:17
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4Feedback(Workpackage145 record) throws BusinessException {
		Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(record.getId());
		if (wp145 == null) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		} else if (wp145.getZt() != WorkpackageStatusEnum.TAKEEFFECT.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}
	/**
	 * 
	 * @Description 指定结束验证工包状态为2或 为7
	 * @CreateTime 2017年10月26日 上午11:32:46
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4End(Workpackage145 record) throws BusinessException {
		Workpackage145 wp145 = workpackage145Mapper.selectByPrimaryKey(record.getId());
		if (wp145 == null) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		} else if (wp145.getZt() != WorkpackageStatusEnum.TAKEEFFECT.getId() && wp145.getZt() != WorkpackageStatusEnum.SUBMIT.getId()) {
			throw new BusinessException("该数据已更新，请刷新后再进行操作!");
		}
	}
	/**
	 * 
	 * @Description 完工关闭
	 * @CreateTime 2017年10月19日 上午11:54:21
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@Override
	public void update4Close(Workpackage145 record) throws BusinessException {
		//校验工包状态为7下发
		validation4Feedback(record);
		//校验工包下工单状态都为9和10
		validation4Close(record);
		//处理时间
		doData(record);
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setGbrid(user.getId());
		workpackage145Mapper.update4Close(record);
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.WANGONG,
				UpdateTypeEnum.UPDATE, record.getId());
		// 完工反馈附件
		attachmentService.eidtAttachment(record.getAttachments(), record.getId(), czls, record.getId(),
				record.getDprtcode(), LogOperationEnum.WANGONG);
		//更新135工包
		doSync2WP135Update(record,user,czls);
	}
	/**
	 * 
	 * @Description 验证工包下工包的工单状态都为9或10
	 * @CreateTime 2017年11月3日 下午3:33:29
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	private void validation4Close(Workpackage145 record) throws BusinessException {
		int count = workorder145Mapper.getCount4Wp(record.getId());
		if(count>0){
			throw new BusinessException("该工包下有未关闭或指定结束的工单，不能关闭工包!");
		}
	}
	/**
	 * 
	 * @Description 处理时间
	 * @CreateTime 2017年10月19日 下午1:50:12
	 * @CreateBy 岳彬彬
	 * @param record
	 * @throws BusinessException
	 */
	@SuppressWarnings("deprecation")
	private void doData(Workpackage145 record) throws BusinessException {
		if (record.getSjJsrq() != null ) {
			String sjjssj = (String) record.getParamsMap().get("sjJssj");
			Date jhjsrq = record.getSjJsrq();
			if (sjjssj != null && !"".equals(sjjssj)) {
				String[] sj = sjjssj.split(":");
				jhjsrq.setHours(Integer.valueOf(sj[0]));
				jhjsrq.setMinutes(Integer.valueOf(sj[1]));
			}
			jhjsrq.setSeconds(0);
			record.setSjJsrq(jhjsrq);
		}		
	}
	/**
     * 
     * @Description 指定结束
     * @CreateTime 2017年10月19日 下午1:59:29
     * @CreateBy 岳彬彬
     * @param record
     */
	@Override
	public void update4End(Workpackage145 record) throws BusinessException {
		//校验工包状态为7下发
		validation4End(record);
		//校验工包下工单状态都为9和10
		validation4Close(record);
		User user = ThreadVarUtil.getUser();
		String czls = UUID.randomUUID().toString();
		record.setWhrid(user.getId());
		record.setWhdwid(user.getBmdm());
		record.setGbrid(user.getId());
		workpackage145Mapper.update4End(record);
		commonRecService.write(record.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.GUANBI,
				UpdateTypeEnum.UPDATE, record.getId());
	}
	/**
	 * 
	 * @Description 工包明细
	 * @CreateTime 2017年10月20日 上午9:55:05
	 * @CreateBy 岳彬彬
	 * @param record
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getWorkpackageDetail(Workpackage145 record) {		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		List<String> lx = (List<String>) record.getParamsMap().get("gdlx");
		searchMap.put("gdlx", lx);
		searchMap.put("gbid", record.getParamsMap().get("gbid"));
		if (record.getParamsMap().get("keyword") != null && !"".equals(record.getParamsMap().get("keyword"))) {
			searchMap.put("keyword", record.getParamsMap().get("keyword"));
		}

		List<Map<String, Object>> workorderList = workorder145Mapper.getWorkorderListByGbid(searchMap);
//		List<Map<String, Object>> hcList = workpackage145Mapper.getHcToolList((String) record.getParamsMap().get("gbid"),
//				(String) record.getParamsMap().get("dprtcode"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("workorderList", workorderList);
//		map.put("hcList", hcList);
		return map;
	}

	@Override
	public Map<String, Object> getWorkpackagePlanList(Workpackage145 record) throws BusinessException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = record.getId();
		record.setId("");
		User user = ThreadVarUtil.getUser();
		record.getParamsMap().put("userId", user.getId());
		record.getParamsMap().put("userType", user.getUserType());
		try {
			PageHelper.startPage(record.getPagination());
			List<Workpackage145> recordList = workpackage145Mapper.getWorkpackagePlanList(record);
			if (((Page) recordList).getTotal() > 0) {
				// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
				if (StringUtils.isNotBlank(id)) {
					// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
					if (!PageUtil.hasRecord(recordList, id)) {
						// 在查询条件中增加ID
						Workpackage145 newRecord = new Workpackage145();
						newRecord.setId(id);
						List<Workpackage145> newRecordList = workpackage145Mapper.getWorkpackagePlanList(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							recordList.add(0, newRecordList.get(0));
						}
					}
				}
				resultMap = PageUtil.pack4PageHelper(recordList, record.getPagination());
				return resultMap;

			} else {
				List<Workpackage145> newRecordList = new ArrayList<Workpackage145>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Workpackage145 newRecord = new Workpackage145();
					newRecord.setId(id);
					newRecordList = workpackage145Mapper.getWorkpackagePlanList(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, record.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, record.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败", e);
		}
	}

	/**
	 * 
	 * @Description 航材工具需求清单
	 * @CreateTime 2017年10月25日 上午10:17:56
	 * @CreateBy 林龙
	 * @param workpackage145
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getMaterialsDetail(
			Workpackage145 workpackage145) {
		return workpackage145Mapper.getMaterialsDetail(workpackage145);
	}
	/**
	 * 
	 * @Description 145新增工包同步到135
	 * @CreateTime 2017年11月15日 下午2:05:41
	 * @CreateBy 岳彬彬
	 * @param record 145工包
	 */
	private void doSync2WP135Add(Workpackage145 wp145,User user,String czls){
		if(validation4ExistSync(wp145)){
			Workpackage wp = new Workpackage();
			wp.setId(wp145.getId());
			wp.setWhdwid(wp145.getWhdwid());
			wp.setWhrid(wp145.getWhrid());
			wp.setWhsj(new Date());
			wp.setZdbmid(wp145.getZdbmid());
			wp.setZdrid(wp145.getZdrid());
			wp.setZdrq(new Date());
			wp.setZt(WorkpackageStatusEnum.TAKEEFFECT.getId());//新增时为下发状态
			wp.setXfrid(ThreadVarUtil.getUser().getId());
			wp.setXfrdwid(ThreadVarUtil.getUser().getBmdm());
			wp.setDtbs(0);
			wp.setWgbs(0);
			wp.setGbbh(wp145.getGbbh());
			wp.setGbmc(wp145.getGbmc());
			wp.setFjzch(wp145.getFjzch());	
			wp.setJhKsrq(wp145.getJhKsrq());
			wp.setJhJsrq(wp145.getJhJsrq());
			wp.setWxlx(wp145.getWxlx());
			wp.setGzyq(wp145.getGzyq());
			if(wp145.getWbbs()==0){//内部
				wp.setDprtcode(wp145.getDprtcode());
				wp.setGbxfdwid(wp145.getXfdwid());	
				wp.setJhWwbs(0);
				wp.setXfrdwid(wp145.getZxdwid());
				wp.setXfrid(wp145.getWhrid());
				wp.setXfsj(new Date());
				wp.setJhZxdwid(wp145.getZxdwid());
				wp.setJhZxdw(wp145.getZxdw());				
			}
/**			目前不考虑外部执行单位
			else{
				wp.setJhWwbs(1);
				SynRel synrel = synRelMapper.selectByDxidAndDprtcode(record.getXfdwid(), record.getDprtcode());
				wp.setDprtcode(synrel.getGljgdm());
				List<SynRel> list = synRelMapper.selectSyrelByParams(record.getDprtcode(),wp.getDprtcode());
				if (list.size() > 0) {
					wp.setJhZxdwid(list.get(0).getDxid());
					wp.setJhZxdw(list.get(0).getObjText());
				}
			}
*/
			workpackageMapper.insertSelective(wp);
			commonRecService.write(wp.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.ISSUED,
					UpdateTypeEnum.SAVE, wp.getId());
		}
	}
	/**
	 * 
	 * @Description 验证是否需要同步到135工包
	 * @CreateTime 2017年11月15日 下午5:31:57
	 * @CreateBy 岳彬彬
	 * @param wp 145工包
	 * @return
	 */
	private Boolean validation4ExistSync(Workpackage145 wp145) {
		// 工包是内部执行单位且135也有该工包
		if (wp145.getWbbs() == 0) {
			return true;
		} 
/**		目前只考虑内部执行单位，外部的不需要下发到145
		else {
			SynRel record = new SynRel();
			record.setDprtcode(wp.getDprtcode());
			record.setGxlx(2);
			record.setDxid(wp.getXfdwid());
			int count = synRelMapper.getCount4Workpackage(record);
			if (count > 0) {
				return true;
			}
		}
*/
		return false;
	}
	/**
	 * 
	 * @Description 同步135工包更新
	 * @CreateTime 2017年11月16日 下午4:10:04
	 * @CreateBy 岳彬彬
	 * @param wp145
	 */
	private void doSync2WP135Update(Workpackage145 wp145,User user,String czls){
		Workpackage145 record = workpackage145Mapper.selectByPrimaryKey(wp145.getId());//145工包
		Workpackage wp = workpackageMapper.selectByPrimaryKey(wp145.getId());//135工包
		if(null != wp && wp.getJhWwbs() == 0 && record.getWbbs() == 0){
			wp.setId(record.getId());
			wp.setWhdwid(record.getWhdwid());
			wp.setWhrid(record.getWhrid());
			wp.setWhsj(new Date());
			wp.setGbyy(record.getGbyy());
			wp.setGbrid(record.getGbrid());
			wp.setGbrq(new Date());
			wp.setDtbs(0);
			wp.setWgbs(record.getWgbs());
			wp.setGbbh(record.getGbbh());
			wp.setGbmc(record.getGbmc());
			wp.setFjzch(record.getFjzch());	
			wp.setJhKsrq(record.getJhKsrq());
			wp.setJhJsrq(record.getJhJsrq());
			wp.setWxlx(record.getWxlx());
			wp.setGzyq(record.getGzyq());
			wp.setDprtcode(record.getDprtcode());
			wp.setGbxfdwid(record.getXfdwid());	
			wp.setJhWwbs(0);
			wp.setXfrdwid(user.getBmdm());
			wp.setJhZxdwid(record.getZxdwid());
			wp.setJhZxdw(wp145.getZxdw());				
			if(record.getWgbs()==1){
				wp.setSjWwbs(0);
			}
			wp.setSjZxdw(record.getSjZxdw());
			wp.setSjZxdwid(record.getSjZxdwid());
			wp.setSjKsrq(record.getSjKsrq());
			wp.setSjJsrq(record.getSjJsrq());
			wp.setSjGzz(record.getSjGzz());
			wp.setSjJcz(record.getSjJcz());
			wp.setSjZd(record.getSjZd());
			workpackageMapper.updateByPrimaryKeySelective(wp);
			commonRecService.write(wp.getId(), TableEnum.B_S2_007, user.getId(), czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.SAVE, wp.getId());
		}
	}
	/**
	 * 
	 * @Description 同步145工包的下工单的飞机注册号和msn
	 * @CreateTime 2018年1月19日 下午2:29:57
	 * @CreateBy 岳彬彬
	 * @param id
	 */
	private void doSync2WO145(Workpackage145 wp145){
		workorder145Mapper.updateFj(wp145.getFjjx(),wp145.getFjzch(),wp145.getFjxlh(),wp145.getId());
	}
	/**
	 * 
	 * @Description 修订工包
	 * @CreateTime 2017年11月24日 下午3:56:54
	 * @CreateBy 林龙
	 * @param workpackage145
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public String doXDClose(Workpackage145 workpackage145)throws BusinessException {
		User user = ThreadVarUtil.getUser();//当前登录用户
		String czls = UUID.randomUUID().toString();//操作流水
		workpackage145.setWhrid(user.getId());
		workpackage145.setWhdwid(user.getBmdm());
		workpackage145.setGbrid(user.getId());
		if(workpackage145.getZt()==WorkpackageStatusEnum.CLOSETOFINISH.getId()){//当状态为完成时
			//处理时间
			doData(workpackage145);
			workpackage145Mapper.update4Close(workpackage145);
			commonRecService.write(workpackage145.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.REVISE,UpdateTypeEnum.UPDATE, workpackage145.getId());
			// 完工反馈附件
			attachmentService.eidtAttachment(workpackage145.getAttachments(), workpackage145.getId(), czls, workpackage145.getId(),workpackage145.getDprtcode(), LogOperationEnum.REVISE);
			//更新135工包
			doSync2WP135Update(workpackage145,user,czls);
		}else{
			workpackage145Mapper.update4End(workpackage145);
			commonRecService.write(workpackage145.getId(), TableEnum.B_S2_010, user.getId(), czls, LogOperationEnum.REVISE,UpdateTypeEnum.UPDATE, workpackage145.getId());
		}

		return workpackage145.getId();
	}
}