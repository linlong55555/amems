package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eray.thjw.common.GlobalConstants;
import com.eray.thjw.dao.DepartmentMapper;
import com.eray.thjw.dao.DeptInfoMapper;
import com.eray.thjw.dao.RoleToDprtMapper;
import com.eray.thjw.dao.RoleToStoreMapper;
import com.eray.thjw.dao.UserMapper;
import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.DeptInfo;
import com.eray.thjw.po.RoleToDprt;
import com.eray.thjw.po.User;
import com.eray.thjw.service.DepartmentService;
import com.eray.thjw.service.DeptInfoService;
import com.eray.thjw.util.PageUtil;
import com.eray.thjw.util.ThreadVarUtil;
import com.github.pagehelper.PageHelper;

@Service
@Transactional(readOnly = true)
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleToDprtMapper roleToDprtMapper;
	
	@Autowired
	private DeptInfoMapper deptInfoMapper;
	
	@Autowired
	private DeptInfoService deptInfoService;
	
	/**
	 * @author liub
	 * @description 角色仓库Mapper
	 * @develop date 2016.09.09
	 */
	@Autowired
	private RoleToStoreMapper roleToStoreMapper;
   
	
	/**
	 * 查询组织机构列表并且分页的实现方法
	 */
	@Override
	public List<Department> queryDepartmentListByPage(Department department) {
		return departmentMapper.selectDepartmentListByPage(department);
	}
	
	/**
	 * 查询组织机构总记录数的实现方法
	 */
	@Override
	public int queryCount(Department department) {
		return departmentMapper.selectCount(department);
	}
	@Override
	public List<Department> findAlls() {
		return departmentMapper.findAlls();
	}
	/**
	 * 
	 * 返回一个id用于树结构定位
	 */
	@Override
	public String insertSelective(Department record) throws RuntimeException {
		departmentMapper.insertSelective(record);
		return record.getId();
	}
	
	@Override
	public Department selectByDepartment(Department record)
			throws RuntimeException {
		return departmentMapper.selectByDepartment(record);
	}
	@Override
	public int updateByPrimaryKeySelective(Department record)
			throws RuntimeException {
		return departmentMapper.updateByPrimaryKeySelective(record);
	}
	
	
	 /**
	 * @author liub
	 * @description 删除组织机构
	 * @param id 组织机构id,dprttype类型
	 * @develop date 2017.01.23
	 * @return
	 */
	public void deleteDepartment(String id , String dprttype) throws BusinessException{
		if("2".equals(dprttype)){
			int count=departmentMapper.getChildDepartment(id);
			if(count>0){
				throw new RuntimeException("该部门下面还有部门,不能删除!");
			}else{
			departmentMapper.deleteByPrimaryKey(id);
			}
		}else{
				int userCount = userMapper.queryCountByJgdm(id);
				if(userCount > 0){
					throw new RuntimeException("该组织机构下有用户，不能删除!");
				}
				int departmentCount=departmentMapper.getChildDepartment(id);
				if(departmentCount>0){
					throw new RuntimeException("该组织机构下有部门，不能删除!");
				}
				departmentMapper.deleteByPrimaryKey(id);
				deptInfoMapper.deleteDeptInfo(id);
				RoleToDprt roleToDprt = new RoleToDprt();
				roleToDprt.setDprtId(id);
				roleToDprtMapper.delete(roleToDprt);
				roleToStoreMapper.deleteByD(id);

		}
	}
	
	@Override
	public List<Department> findAll(String parentId) {
		return departmentMapper.findAll(parentId);
	}

	@Override
	public Department findById(String departmentId) {
		return departmentMapper.selectByPrimaryKey(departmentId);
	}

	/*@Override
	public Department findByTopNode(String departmentId,String parentId) {
		return departmentMapper.findByTopNode(departmentId,SysContext.getConfigItem(ConfigItem.TOP_ORG));
	}*/
	
	/**
	 * 查询父组织机构列表的实现方法
	 */
	@Override
	public List<Department> queryParentDepartmentList(String id, String dprtName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("dprtName", dprtName);
		return departmentMapper.selectParentDepartmentList(map);
	}

	/**
	 * 查询组织机构信息的实现方法
	 */
	@Override
	public Department queryByPrimaryKey(String id) {
		return departmentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 修改组织机构信息的实现方法
	 */
	/*@Override
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> modifyDepartment(Department department) throws Exception {
		//结果集参数
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		logger.debug("进入DepartmentServiceImpl的modifyDepartment方法");
		
		try {
			//第1步：更新组织机构至数据库
			int departmentAffectedRows = departmentMapper.updateByPrimaryKeySelective(department);
			
			//受影响的行大于0表示为成功，否则表示为失败
			if (departmentAffectedRows > 0) {
				//根据组织机构id找出组织机构表中的根节点
				Department rootDepartment = departmentMapper.findByTopNode(department.getId(),SysContext.getConfigItem(ConfigItem.TOP_ORG));
				String jgdm = department.getDprtcode();
				if (rootDepartment != null) {
					jgdm = rootDepartment.getDprtcode();
				}
				
				//请求参数
				Map<String, Object> paramMap = new HashMap<String, Object>();
				
				//第2步：修改用户关联的组织机构
				if (!StringUtils.isEmpty(department.getUserIds())) {
					List<Integer> userIdList = new ArrayList<Integer>();
					for (String userId : department.getUserIds()) {
						 userIdList.add(Integer.parseInt(userId));
					}
					
					if (userIdList != null && userIdList.size() > 0) {
						//用户主键id
						paramMap.put("id", userIdList);
						
						//部门代码
						paramMap.put("bmdm", department.getDprtcode());
						
						//组织机构代码
						paramMap.put("jgdm", jgdm);
						
						//执行修改操作
						int userAffectedRows = userMapper.updateUserTheDepartmentById(paramMap);
						
						//受影响的行大于0表示为成功，否则表示为失败
						if (userAffectedRows == 0) {
							resultMap.put("state", "Failure");
							resultMap.put("text", "修改失败");
							return resultMap;
						}
					}
				}
				
				//请求参数
				paramMap = new HashMap<String, Object>();
				
				//部门代码
				paramMap.put("bmdm", department.getDprtcode());
				
				//组织机构代码
				paramMap.put("jgdm", jgdm);
				
				//原始的部门代码
				paramMap.put("orgnBmdm", department.getOrgndprtcode());
				
				//执行修改操作
				userMapper.updateUserTheDepartmentByOrgnBmdm(paramMap);
				
				resultMap.put("state", "Success");
				resultMap.put("text", "修改成功");
			}else {
				resultMap.put("state", "Failure");
				resultMap.put("text", "修改失败");
			}
		} catch (Exception e) {
			resultMap.put("state", "Failure");
			resultMap.put("text", "修改失败");
			
			logger.error("DepartmentServiceImpl的modifyDepartment方法 ServiceException ：",e);
			
			//事物回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return resultMap;
	}*/

	/**
	 * 查询组织机构信息的实现方法
	 */
	@Override
	public Department queryDepartmentByDprtCode(String dprtCode) {
		return departmentMapper.selectDepartmentByDprtCode(dprtCode);
	}
	

	 /**
	 * @author liub
	 * @description 根据用户查询组织机构信息
	 * @param dprtCode 组织机构代码
	 * @develop date 2016.08.23
	 * @return Department 组织机构信息
	 */
	@Override
	public List<Department> findDepartmentByUserId(String userId) {
		return departmentMapper.findDepartmentByUserId(userId);
	}
	
	@Override
	public Department selectByPrimaryKey(String id){
		return departmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Department> queryOrg() {
		return departmentMapper.queryOrg();
	}

	@Override
	public List<Department> findAllff() {
		return departmentMapper.findAllff();
	}

	@Override
	public Map<String, Object> checkUpdMt(Department department) {
		Map<String, Object> returnMap = new HashMap<String, Object>();//返回数据map
		if(department.getDprttype().equals("1")){
			int num= departmentMapper.selectExistCount(department);
			if(num<1){
				returnMap.put("state", "success");
			}else{
				returnMap.put("state", "error");
				returnMap.put("message", "组织机构代码或名称已存在!");
				return returnMap;
			}
		}
		else if(department.getDprttype().equals("2")){
			int count=departmentMapper.getChildBm(department.getId(),department.getDprtcode(),department.getDprtname());
			if(count>0){
				returnMap.put("state", "error");
				returnMap.put("message", "部门代码或名称已存在!");
				return returnMap;
			}
				
		}		
		returnMap.put("state", "success");
		return returnMap;
	}

	@Override
	public int getCountsByDepartment(Department department) {
		
		return departmentMapper.getCountsByDepartment(department);
	}

	@Override
	public Department getParentDepartmentByChildId(String id) {
	
		return departmentMapper.getParentDepartmentByChildId(id);
	}

	@Override
	public int updateByPrimaryKey(Department record) {
		
		return departmentMapper.updateByPrimaryKey(record);
	}

	@Override
	public void updateById(Department department) {
		if(department.getDeptInfo()!=null){
		deptInfoService.updateById(department.getDeptInfo());
		}
		departmentMapper.updateById(department);	
	}

	@Override
	public List<Department> selectChildBm(Department department) {
		
		return departmentMapper.selectChildBm(department);
	}
	
	
	public String insertdepartment(Department record) throws RuntimeException {	
		
		String id=UUID.randomUUID().toString();
		record.setId(id);
		departmentMapper.insertSelective(record);//新增部门
		DeptInfo deptInfo=record.getDeptInfo();
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			deptInfo.setZcfj_max(null);
			deptInfo.setZczh_max(null);
			deptInfo.setYxqks(null);
			deptInfo.setYxqjs(null);
			deptInfo.setDeptType(null);
			deptInfoMapper.updateByPrimaryKey(deptInfo);//更新组织机构从表信息
		}
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			 deptInfoMapper.updateById(deptInfo);		
		}
		Department department=new Department();
		department.setId(deptInfo.getId());
		department.setDprttype("1");
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			department.setPxh(deptInfo.getPxh());			
		}
		department.setRemark(deptInfo.getRemark());
		departmentMapper.updateByPrimaryKeySelective(department);//更新组织机构主表信息
		return record.getId();
	}
	
	public void updatedepartment(Department department) {
		DeptInfo deptInfo=department.getDeptInfo();//获取页面上一部分
		if(!GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			deptInfo.setZcfj_max(null);
			deptInfo.setZczh_max(null);
			deptInfo.setYxqks(null);
			deptInfo.setYxqjs(null);
			deptInfo.setDeptType(null);
			deptInfoMapper.updateByPrimaryKey(deptInfo);//更新组织机构从表信息
		}
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(ThreadVarUtil.getUser().getOrgcode())){
			 deptInfoMapper.updateById(deptInfo);		
		}
		Department department1=new Department();
		department1.setId(deptInfo.getId());
		department1.setDprttype("1");
		User user = ThreadVarUtil.getUser();
		if(GlobalConstants.getString(GlobalConstants.SUPER_AGENCY_KEY).equals(user.getOrgcode())){
			department1.setPxh(deptInfo.getPxh());			
		}
		department1.setRemark(deptInfo.getRemark());
		departmentMapper.updateByPrimaryKeySelective(department1);//更新组织机构主表信息
		department.setParentid(null);
		departmentMapper.updateByPrimaryKeySelective(department);//更新部门信息	
	}

	@Override
	public List<Department> selectChild(Department department) {
		
		return departmentMapper.selectChild(department);
	}

	@Override
	public List<Department> getChildrentList(String id) {
		List<Department> retList = new ArrayList<Department>();
		if (null != id && !"".equals(id)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("dprtcode", id);
			retList = departmentMapper.getChildrentList(param);
		}
		return retList;
	}
	/**
	 * 
	 * @Description 查询基地
	 * @CreateTime 2017-9-20 下午4:19:38
	 * @CreateBy 孙霁
	 * @param department
	 * @return
	 */
	@Override
	public List<Department> queryJdByid(String id)
			throws BusinessException {
		return departmentMapper.queryJdByid(id);
	}

	@Override
	public List<Department> selectDepartmentPid(String departmentId) {
		
		return departmentMapper.selectDepartmentPid(departmentId);
	}

	@Override
	public List<Department> queryOrgs(BaseEntity entity) {
		return departmentMapper.queryOrgs(entity);
	}
	
	/**
	 * 
	 * @Description 获取组织机构下所有部门
	 * @CreateTime 2017年9月27日 上午10:20:12
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@Override
	public List<Department> getBmList(String id) {
		
		return departmentMapper.getBmList(id);
	}

	@Override
	public Map<String, Object> selectDepartmentListByPageNew(Department department) {
		PageHelper.startPage(department.getPagination());
		List<Department> list = departmentMapper.selectDepartmentListByPageNew(department);
		return PageUtil.pack4PageHelper(list, department.getPagination());
	}

	
	/**
	 * 
	 * @Description 获取组织机构下树结构部门是基地
	 * @CreateTime 2017年9月28日 下午6:14:01
	 * @CreateBy 岳彬彬
	 * @param department
	 * @return
	 */
	@Override
	public List<Department> getBm4JdList(Department department) {
		
		return departmentMapper.getBm4JdList(department);
	}
	
	/**
	 * 
	 * @Description 获取组织机构下所有部门不包含组织机构
	 * @CreateTime 2017年10月26日 下午2:36:44
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	@Override
	public List<Department> getAllBmList(String id) {
		
		return departmentMapper.getAllBmList(id);
	}

}
