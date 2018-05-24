package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;


public interface DepartmentService {
	
	
	
	public List<Department> queryDepartmentListByPage(Department department)throws RuntimeException;   //分页查询组织机构
	
	public int queryCount(Department department)throws RuntimeException;     //查询总记录数
	
	public List<Department> findAlls()throws RuntimeException;  //查询所有
	
    String insertSelective(Department record)throws RuntimeException;         //新增组织机构
	
    Department selectByDepartment(Department record)throws RuntimeException;          //修改组织机构 时获取一个组织机构信息
	
    int updateByPrimaryKeySelective(Department record)throws RuntimeException;
    
    int updateByPrimaryKey(Department record);
    
    /**
     * @Description 获得部门的（分页）
     * @CreateTime 2017-9-29 上午10:59:18
     * @CreateBy 甘清
     * @param department 部门参数
     * @return Map<String, Object> 
     */
    Map<String, Object> selectDepartmentListByPageNew(Department department);
    
	/**
	 * @author sunji
	 * @description 树状查询组织机构
	 * @param departmentList
	 * @return List<Department>
	 * @develop date 2016.08.01
	 */
	public List<Department> findAll(String parentId);
	/**
	 * @author sunji
	 * @description 根据id查询记录
	 * @param departmentId
	 * @return Department
	 * @develop date 2016.08.01
	 */
	public Department findById(String departmentId);
	/**
	 * @author sunji
	 * @description 根据id查询最上级的根节点
	 * @param departmentId
	 * @return Department 
	 * @develop date 2016.08.01
	 */
	/*public Department findByTopNode(String departmentId,String parentId);*/
	
	/**
	 * @author pingxiaojun
	 * @description 查询父组织机构列表
	 * @param id 组织机构主键id
	 * @param dprtName 组织机构名称
	 * @develop date 2016.08.08
	 * @return List<Department> 父组织机构列表
	 */
	public List<Department> queryParentDepartmentList(String id, String dprtName);
	
	/**
	 * @author pingxiaojun
	 * @description 查询组织机构信息
	 * @param id 组织机构主键id
	 * @develop date 2016.08.09
	 * @return Department 组织机构信息
	 */
	public Department queryByPrimaryKey(String id);
	
	/**
	 * @author pingxiaojun
	 * @description 修改组织机构信息
	 * @param department 修改组织机构修改信息的参数
	 * @develop date 2016.08.09
	 * @throws Exception 修改组织机构信息出现的异常
	 * @return Map<String, Object> 修改组织机构的处理结果
	 */
	/*public Map<String, Object> modifyDepartment(Department department) throws Exception;*/
	
	/**
	 * @author pingxiaojun
	 * @description 查询组织机构信息
	 * @param dprtCode 组织机构代码
	 * @develop date 2016.08.11
	 * @return Department 组织机构信息
	 */
	public Department queryDepartmentByDprtCode(String dprtCode);
	
	 
	 /**
	 * @author liub
	 * @description 根据用户查询组织机构信息
	 * @param dprtCode 组织机构代码
	 * @develop date 2016.08.23
	 * @return Department 组织机构信息
	 */
	public List<Department> findDepartmentByUserId(String userId);
	 /**
	 * @author liub
	 * @description 删除组织机构
	 * @param id 组织机构id,dprttype类型
	 * @develop date 2017.01.23
	 * @return
	 */
	public void deleteDepartment(String id , String dprttype) throws BusinessException;

	/**
	 * 查询组织机构
	 * @return
	 */
	public Department selectByPrimaryKey(String id);
	
	public List<Department> queryOrg();

	public List<Department> findAllff();

	public Map<String, Object> checkUpdMt(Department department);
	
	int getCountsByDepartment(Department department);
	
	Department getParentDepartmentByChildId(String id);
	
	void updateById(Department department);
	
	List<Department> selectChildBm(Department department);
	
	String insertdepartment(Department department);
	
	void updatedepartment(Department department);
	
	List<Department> selectChild(Department department);
	
	List<Department>  getChildrentList(String id);
	/**
	 * 
	 * @Description  查询基地
	 * @CreateTime 2017-9-20 下午4:19:38
	 * @CreateBy 孙霁
	 * @param department
	 * @return
	 */
	List<Department> queryJdByid(String id)throws BusinessException ;

	public List<Department> selectDepartmentPid(String departmentId);

	/**
	 * 
	 * @Description 查询机构列表
	 * @param BaseEntity
	 * @CreateTime 2017年9月26日 上午9:32:29
	 * @CreateBy 朱超
	 * @return
	 */
	public List<Department> queryOrgs(BaseEntity entity);
	/**
	 * 
	 * @Description 获取组织机构下所有部门
	 * @CreateTime 2017年9月27日 上午10:20:12
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	List<Department> getBmList(String id);
	/**
	 * 
	 * @Description 获取组织机构下树结构及基地
	 * @CreateTime 2017年9月28日 下午6:14:01
	 * @CreateBy 岳彬彬
	 * @param department
	 * @return
	 */
	List<Department> getBm4JdList(Department department);
	
	/**
	 * 
	 * @Description 获取组织机构下所有部门不包含组织机构
	 * @CreateTime 2017年10月26日 下午2:36:44
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	List<Department> getAllBmList(String id);
}
