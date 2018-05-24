package com.eray.thjw.training.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.flightdata.service.AttachmentService;
import com.eray.thjw.po.User;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.training.dao.CourseMapper;
import com.eray.thjw.training.po.Course;
import com.eray.thjw.training.service.CourseService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.DelStatus;
import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;

/**
 * @author liub
 * @description 课程管理service,用于业务逻辑处理
 */
@Service
public class CourseServiceImpl implements CourseService {
    
	/**
	 * @author liub
	 * @description 课程管理mapper
	 */
	@Resource
	private CourseMapper courseMapper;
	
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
	 * @description 增加课程
	 * @param course
	 * @throws BusinessException 
	 */
	@Override
	public String add(Course course) throws BusinessException{
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		course.setDprtcode(user.getJgdm());
		checkExist(course);//检查是否存在
		//新增课程信息
		UUID uuid = UUID.randomUUID();//获取随机的uuid
		String id = uuid.toString();
		course.setId(id);
		course.setZt(DelStatus.TAKE_EFFECT.getId());
		course.setWhrid(user.getId());
		course.setWhbmid(user.getBmdm());
		courseMapper.insertSelective(course);
		commonRecService.write(id, TableEnum.B_P_003, user.getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE, course.getId());//保存历史记录信息
		attachmentService.eidtAttachment(course.getAttachments(), course.getId(), czls, course.getId(), course.getDprtcode(), LogOperationEnum.SAVE_WO);
		return id;
	}
	
	/**
	 * @author liub
	 * @description 修改课程
	 * @param course
	 * @throws BusinessException 
	 */
	@Override
	public void edit(Course course) throws BusinessException{
		checkExist(course);//检查是否存在
		String czls = UUID.randomUUID().toString();//操作流水
		//修改课程信息
		User user = ThreadVarUtil.getUser();
		course.setWhrid(user.getId());
		course.setWhbmid(user.getBmdm());
		courseMapper.updateByPrimaryKeySelective(course);
		commonRecService.write(course.getId(), TableEnum.B_P_003, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, course.getId());//保存历史记录信息
		attachmentService.eidtAttachment(course.getAttachments(), course.getId(), czls, course.getId(), course.getDprtcode(), LogOperationEnum.EDIT);
	}
	
	/**
	 * @author liub
	 * @description 作废
	 * @param id
	 */
	@Override
	public void cancel(String id) throws BusinessException{
		Course obj = courseMapper.checkUptById(id);
		if(null != obj){
			throw new BusinessException("该课程已被使用，不能作废!");
		}
		String czls = UUID.randomUUID().toString();//操作流水
		User user = ThreadVarUtil.getUser();
		Course course = new Course();
		course.setId(id);
		course.setZt(DelStatus.LOSE_EFFECT.getId());
		course.setWhrid(user.getId());
		course.setWhbmid(user.getBmdm());
		courseMapper.cancel(course);
		commonRecService.write(id, TableEnum.B_P_003, user.getId(), czls ,LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, course.getId());//保存历史记录信息
	}
	
	/**
	 * @author liub
	 * @description 根据查询条件分页查询课程信息
	 * @param course
	 * @return List<Course>
	 */
	@Override
	public List<Course> queryAllPageList(Course course){
		return courseMapper.queryAllPageList(course);
	}

	/**
	 * @author sunji
	 * @description 根据gwid查询数据
	 * @param id
	 * @return List<Course>
	 */
	public List<Course> queryAllBygwid(String gwid) {
		return courseMapper.queryAllBygwid(gwid);
	}
	
	/**
	 * @author liub
	 * @description 根据id查询课程及用户信息
	 * @param id
	 * @return Course
	 */
	@Override
	public Course selectById(String id){
		return courseMapper.selectById(id);
	}
	
	/**
	 * @Description 查询课程-培训评估
	 * @CreateTime 2017-11-17 下午4:02:14
	 * @CreateBy 刘兵
	 * @param record 课程
	 * @return List<Course> 课程集合
	 */
	public List<Course> queryCourseEval(Course record) {
		return courseMapper.queryCourseEval(record);
	}
	
	/**
	 * @author liub
	 * @description 检查课程是否存在
	 * @param course
	 */
	private void checkExist(Course course) throws BusinessException{
		Course checkParam = new Course();
		checkParam.setKcbh(course.getKcbh());
		checkParam.setDprtcode(course.getDprtcode());
		checkParam.setFjjx(course.getFjjx());
		List<Course> courseList = courseMapper.checkExist(checkParam);
		StringBuffer sb = new StringBuffer();
		for (Course oldC : courseList) {
			if(null != course.getId() && course.getId().equals(oldC.getId())){
				continue;
			}
			if(null != course.getKcbh() && course.getKcbh().equals(oldC.getKcbh())){
				sb.append("机型").append(course.getFjjx()).append("课程代码[").append(course.getKcbh()).append("]已存在!");
			}
		}
		if(sb.length() > 0){
			throw new BusinessException(sb.toString());
		}
	}

	@Override
	public Course selectkcbh(String kcbm, String dprtcode) {
		// TODO Auto-generated method stub
		return courseMapper.selectkcbh(kcbm,dprtcode);
	}

	/**
	 * 
	 * @Description 查询课程编号(去重)
	 * @CreateTime 2018-1-22 下午4:38:11
	 * @CreateBy 孙霁
	 * @param business
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<Course> selectDistinctKcbh(Course record){
		// TODO Auto-generated method stub
		return courseMapper.selectDistinctKcbh(record);
	}
}
