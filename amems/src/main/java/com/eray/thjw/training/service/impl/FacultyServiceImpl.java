package com.eray.thjw.training.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.dao.MaintenancePersonnelMapper;
import com.eray.thjw.quality.po.MaintenancePersonnel;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.FacultyMapper;
import com.eray.thjw.training.dao.TrainingPlanMapper;
import com.eray.thjw.training.po.Faculty;
import com.eray.thjw.training.po.FacultyCourse;
import com.eray.thjw.training.po.TrainingPlan;
import com.eray.thjw.training.service.FacultyCourseService;
import com.eray.thjw.training.service.FacultyService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

@Service
public class FacultyServiceImpl implements FacultyService{

	@Resource
	private FacultyMapper facultyMapper;
	
	@Resource
	private FacultyCourseService facultyCourseService;
	@Resource
	private MaintenancePersonnelMapper maintenancePersonnelMapper;
	
	@Resource
	private TrainingPlanMapper trainingPlanMapper;
	
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private CommonRecService commonRecService;
	
	/** sunji
	 * 分页查询教员信息
	 * @return List<Business>
	 * @throws Exception 
	 */
	public Map<String, Object> queryAll(Faculty faculty) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = faculty.getId();
		faculty.setId("");
		try {
			// 获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(faculty.getPagination());
			List<Faculty> list = facultyMapper.queryAll(faculty);
			if (((Page) list).getTotal() > 0) {
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if (!PageUtil.hasRecord(list, id)) {// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						// 在查询条件中增加ID
						Faculty newRecord = new Faculty();
						newRecord.setId(id);
						List<Faculty> newRecordList = facultyMapper.queryAll(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							list.add(0, newRecordList.get(0));
						}
						// 将记录放入结果集第一条

					}
				}
				resultMap = PageUtil.pack4PageHelper(list, faculty.getPagination());
				return resultMap;

			} else {
				List<Faculty> newRecordList = new ArrayList<Faculty>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Faculty newRecord = new Faculty();
					newRecord.setId(id);
					newRecordList = facultyMapper.queryAll(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, faculty.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, faculty.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!", e);
		}
	}
	
	/**
	 * 
	 * @Description 教员管理新增
	 * @CreateTime 2017年9月25日 上午11:00:07
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public boolean add(Faculty faculty) throws BusinessException {
		//写入用户id与维护部门id
		int count;
		try {
			User user = ThreadVarUtil.getUser();
			String id = user.getId();
			String departmentId = user.getBmdm();
			faculty.setWhbmid(departmentId);
			faculty.setWhrid(id);
			Date whsj = new Date();
			faculty.setWhsj(whsj);
			faculty.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			faculty.setZt(EffectiveEnum.YES.getId());
			//查重
			Faculty fparm = new Faculty();
			fparm.setZt(faculty.getZt());
			fparm.setDprtcode(faculty.getDprtcode());
			fparm.setRybh(faculty.getRybh());
			List<Faculty> queryAll = facultyMapper.queryAll(fparm);
			
			MaintenancePersonnel maintenancePersonnel=new MaintenancePersonnel();
			maintenancePersonnel.setRybh(faculty.getRybh());
			maintenancePersonnel.setDprtcode(faculty.getDprtcode());
			maintenancePersonnel.setZt(1);
			List<MaintenancePersonnel> listM = maintenancePersonnelMapper.queryPage(maintenancePersonnel);
			
			if(queryAll.size() > 0 || listM.size() > 1) {
				throw new BusinessException("该编号在教员或者维修人员里已存在!");
			}
			
			if(faculty.getWbbs()==1){
				Faculty fparm1 = new Faculty();
				fparm1.setZt(faculty.getZt());
				fparm1.setWxrydaid(faculty.getWxrydaid());
				List<Faculty> queryAll1 = facultyMapper.queryAll(fparm1);
				if(queryAll1!=null && queryAll1.size()>0) {
					throw new BusinessException("该员工已存在!");
				}
			}
			String czls = UUID.randomUUID()+"";//操作流水
			count = facultyMapper.insertSelective(faculty);
			//写日志
			commonRecService.write(faculty.getId(), TableEnum.B_P_008, id, czls, LogOperationEnum.SAVE_WO,
					UpdateTypeEnum.SAVE, faculty.getId());
			//添加所授权的课程
			List<FacultyCourse> list = faculty.getCourseList();
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			for(FacultyCourse obj :list) {
				Map<String, Object> param = obj.getParamsMap();
				Object sqksrq = param.get("sqksrq");
				Object sqjsrq = param.get("sqjsrq");
				if(sqksrq!=null && !"".equals(sqksrq)) {
					Date sqksrqDate = sdf.parse(sqksrq+"");
					obj.setSqksrq(sqksrqDate);
				}
				if(sqjsrq!=null && !"".equals(sqjsrq)) {
					Date sqjsrqDate = sdf.parse(sqjsrq+"");
					obj.setSqjsrq(sqjsrqDate);
				}
				obj.setId(UUID.randomUUID()+"");
				obj.setWhsj(whsj);
				obj.setWhrid(id);
				obj.setWhbmid(departmentId);
				obj.setMainid(faculty.getId());
				obj.setZt(EffectiveEnum.YES.getId());
				facultyCourseService.add(obj);
				commonRecService.write(obj.getId(), TableEnum.B_P_00801, id, czls, LogOperationEnum.SAVE_WO,
						UpdateTypeEnum.SAVE, faculty.getId());
			}
			//新增附件，并更新附件列表
			attachmentService.eidtAttachment(faculty.getAttachments(), faculty.getId(), czls, faculty.getId(), faculty.getDprtcode(), LogOperationEnum.SAVE_WO);
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
		return count>0;
	}
	
	
	
	/**
	 * 
	 * @Description 教员管理修改
	 * @CreateTime 2017年9月25日 下午2:35:08
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	public boolean update(Faculty faculty)throws BusinessException{
		int count;
		try {
			//写入用户id与维护部门id
			User user = ThreadVarUtil.getUser();
			String id = user.getId();
			String departmentId = user.getBmdm();
			faculty.setWhbmid(departmentId);
			faculty.setWhrid(id);
			Date whsj = new Date();
			faculty.setWhsj(whsj);
			faculty.setDprtcode(ThreadVarUtil.getUser().getJgdm());
			//查重
			Faculty fparm = new Faculty();
			fparm.setZt(EffectiveEnum.YES.getId());
			fparm.setDprtcode(faculty.getDprtcode());
			fparm.setRybh(faculty.getRybh());
			List<Faculty> queryAll = facultyMapper.queryAll(fparm);
			if(queryAll!=null && queryAll.size()>1) {
				throw new BusinessException("该编号已存在!");
			}
			
			if(queryAll!=null && queryAll.size()<=1) {
				if(queryAll.size()==1) {
					Faculty data = queryAll.get(0);
					if(!data.getId().equals(data.getId())) {
						throw new BusinessException("该编号已存在!");
					}
				}
			}
			if(faculty.getWbbs()==1){
				Faculty fparm1 = new Faculty();
				fparm1.setWxrydaid(faculty.getWxrydaid());
				fparm1.setZt(EffectiveEnum.YES.getId());
				List<Faculty> queryAll1 = facultyMapper.queryAll(fparm1);
				if(queryAll1!=null && queryAll1.size()>1) {
					throw new BusinessException("该员工已存在!");
				}
			}
			
			String czls = UUID.randomUUID()+"";//操作流水
			count = facultyMapper.updateByPrimaryKeySelective(faculty);
			//写日志
			commonRecService.write(faculty.getId(), TableEnum.B_P_008, id, czls, LogOperationEnum.EDIT,
					UpdateTypeEnum.UPDATE, faculty.getId());
			List<FacultyCourse> list =  faculty.getCourseList();
			
			//查找课程数
			FacultyCourse facultyCourse = new FacultyCourse();
			facultyCourse.setMainid(faculty.getId());
			facultyCourse.setZt(EffectiveEnum.YES.getId());
			List<FacultyCourse> courseList = facultyCourseService.queryList(facultyCourse);
			
			//删除原先的授课记录
			List<String> columnValueList = new ArrayList<String>();
			for(FacultyCourse obj :courseList) {
				if(!StringUtils.isEmpty(obj.getId())) {
					columnValueList.add(obj.getId());
				}
			}
			if(columnValueList.size()>0) {
				facultyCourseService.deleteByMainId(facultyCourse);
				commonRecService.write("id", columnValueList, TableEnum.B_P_00801, id, czls, LogOperationEnum.DELETE, 
						UpdateTypeEnum.DELETE, faculty.getId());
			}
			//重新添加授课记录
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			for(FacultyCourse obj :list) {
				Map<String, Object> param = obj.getParamsMap();
				Object sqksrq = param.get("sqksrq");
				Object sqjsrq = param.get("sqjsrq");
				if(sqksrq!=null && !"".equals(sqksrq)) {
					Date sqksrqDate = sdf.parse(sqksrq+"");
					obj.setSqksrq(sqksrqDate);
				}
				if(sqjsrq!=null && !"".equals(sqjsrq)) {
					Date sqjsrqDate = sdf.parse(sqjsrq+"");
					obj.setSqjsrq(sqjsrqDate);
				}
				obj.setId(UUID.randomUUID()+"");
				obj.setWhsj(whsj);
				obj.setWhrid(id);
				obj.setWhbmid(departmentId);
				obj.setMainid(faculty.getId());
				obj.setZt(EffectiveEnum.YES.getId());
				facultyCourseService.add(obj);
				commonRecService.write(obj.getId(), TableEnum.B_P_00801, id, czls, LogOperationEnum.SAVE_WO,
						UpdateTypeEnum.SAVE, faculty.getId());

			}
			//新增修改，并更新附件列表
			attachmentService.eidtAttachment(faculty.getAttachments(), faculty.getId(), czls, faculty.getId(), faculty.getDprtcode(), LogOperationEnum.EDIT);
		} catch (ParseException e) {
			throw new BusinessException(e);
		}
		return count>0;
		
	}
	
	
	/**
	 * 
	 * @Description 教员管理删除
	 * @CreateTime 2017年9月25日 上午11:33:12
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @throws BusinessException
	 */
	@Override
	public boolean delete(Faculty faculty) throws BusinessException {
		//写入用户id与维护部门id
		User user = ThreadVarUtil.getUser();
		String id = user.getId();
		String czls = UUID.randomUUID()+"";
		//写入用户id与维护部门id
		faculty.setZt(EffectiveEnum.NO.getId());
		commonRecService.write(faculty.getId(), TableEnum.B_P_008, id, czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, faculty.getId());
		int count = facultyMapper.updateByPrimaryKey(faculty);
		return count>0;
	}

	/**
	 * 
	 * @Description 获取详细信息
	 * @CreateTime 2017年10月9日 下午7:05:24
	 * @CreateBy 胡才秋
	 * @param faculty
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public Map<String, Object> queryInfo(Faculty faculty)throws BusinessException{
		Map<String, Object> returnMap = new HashMap<String,Object>();
		List<Faculty> list = facultyMapper.queryAll(faculty);
		Faculty data  = null;
		if(list!=null && list.size()>0) {
			data = list.get(0);
			FacultyCourse facultyCourse = new FacultyCourse();
			facultyCourse.setMainid(data.getId());
			List<FacultyCourse> courseList = facultyCourseService.queryList(facultyCourse);
			data.setCourseList(courseList);
		}
		returnMap.put("row", data);
		return returnMap;
	}

	@Override
	public List<TrainingPlan>  querCourseInfoList(TrainingPlan data) throws BusinessException {
//		PageHelper.startPage(data.getPagination());
		List<TrainingPlan> list = trainingPlanMapper.queryInfoList(data);
		return list;
//		if(list !=null && list.size()>0) {
//			return PageUtil.pack4PageHelper(list, data.getPagination());
//		}else {
//			list = new ArrayList<TrainingPlan>();
//			return PageUtil.pack(0, list, data.getPagination());
//		}
	}
	
	@Override
	public boolean deleteCourse(FacultyCourse facultyCourse)throws BusinessException {
		String czls = UUID.randomUUID()+"";
		User user = ThreadVarUtil.getUser();
		facultyCourse.setZt(EffectiveEnum.NO.getId());
		int count = facultyCourseService.update(facultyCourse);
		commonRecService.write(facultyCourse.getId(), TableEnum.B_P_00801, user.getId(), czls, LogOperationEnum.DELETE,
				UpdateTypeEnum.DELETE, facultyCourse.getMainid());
		return count>0;
	}

	@Override
	public Map<String, Object> queryAllopenlist(Faculty faculty) throws BusinessException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = faculty.getId();
		faculty.setId("");
		try {
			// 获取当前登入人对象
			User user = ThreadVarUtil.getUser();
			PageHelper.startPage(faculty.getPagination());
			List<Faculty> list = facultyMapper.queryAllopenlist(faculty);
			if (((Page) list).getTotal() > 0) {
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					if (!PageUtil.hasRecord(list, id)) {// 验证分页查询结果集中是否有最近编辑的业务记录，如果不存在记录则需要查询该记录并返回给前台
						// 在查询条件中增加ID
						Faculty newRecord = new Faculty();
						newRecord.setId(id);
						List<Faculty> newRecordList = facultyMapper.queryAllopenlist(newRecord);
						if (newRecordList != null && newRecordList.size() == 1) {
							list.add(0, newRecordList.get(0));
						}
						// 将记录放入结果集第一条

					}
				}
				resultMap = PageUtil.pack4PageHelper(list, faculty.getPagination());
				return resultMap;

			} else {
				List<Faculty> newRecordList = new ArrayList<Faculty>();
				if (StringUtils.isNotBlank(id)) {// 判断ID是否为空，不为空则表示需要查询最近编辑的业务记录
					// 在查询条件中增加ID
					Faculty newRecord = new Faculty();
					newRecord.setId(id);
					newRecordList = facultyMapper.queryAllopenlist(newRecord);
					if (newRecordList != null && newRecordList.size() == 1) {
						resultMap = PageUtil.pack(1, newRecordList, faculty.getPagination());
						return resultMap;
					}

				}
				resultMap = PageUtil.pack(0, newRecordList, faculty.getPagination());
				return resultMap;
			}
		} catch (Exception e) {
			throw new BusinessException("查询数据失败!", e);
		}
	}
	
}
