package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;

public interface DepartmentMapper {
	List<Department> selectDepartmentListByPage(Department department);        //查询所有的组织机构
    
    int selectCount(Department department);               //查询总记录数
	
    public List<Department> findAlls();         //查询所有
	
    int insertSelective(Department record);          //新增组织机构
	
    Department selectByDepartment(Department record);          //修改组织机构 时获取一个组织机构信息
    
    int updateByPrimaryKeySelective(Department record);      //执行组织机构修改操作
    
    
    
    List<Department> selectDepartmentListByPageNew(Department department); //查询所有的组织机构
    
    
    
    Department selectByPrimaryKey(String id);

    int deleteByPrimaryKey(String id);

    int insert(Department record);

    int updateByPrimaryKey(Department record);
    
    List<Department> findAll(String parentId);
    
    Department findByTopNode(String id,String parentId);
    
    List<Department> selectParentDepartmentList(Map<String, Object> map);
    
    int selectChildDepartmentCountByParentId(String parentId);

    Department selectDepartmentByDprtCode(String dprtCode);
    
	 
	public List<Department> findDepartmentByUserId(String userId);

	/**
	 * 同步组织机构
	 * @param authOrganizations
	 */
	void synAuthInfo(Map<String, Object> param);

	List<Department> queryOrg();

	List<Department> findAllff();
	
	int getCountsByDepartment(Department department);
	
	Department getParentDepartmentByChildId(String id);
	
	void updateById(Department department);
	
	int getChildDepartment(String parentId);
	
	List<Department> selectChildBm(Department department);
	/**
	 * 
	 * @param dprtcode 组织机构id
	 * @param code     组织机构dprtcode
	 * @param name	       组织机构名称dprtname
	 * @return
	 */
	int getChildBm(String dprtcode,String code,String name);
	
	int selectExistCount(Department department);
	
	List<Department> selectChild(Department department);
	/**
	 * 获取子部门节点
	 * @param id
	 * @return
	 */
	List<Department>  getChildrentList(Map<String, Object> param);
	/**
	 * 
	 * @Description 查询基地
	 * @CreateTime 2017-9-20 下午4:19:38
	 * @CreateBy 孙霁
	 * @param department
	 * @return
	 */
	List<Department>  queryJdByid(String id);
	
	List<Department> selectDepartmentPid(String departmentId);

	/**
	 * 
	 * @Description 查询机构列表
	 * @param BaseEntity
	 * @CreateTime 2017年9月26日 上午9:33:24
	 * @CreateBy 朱超
	 * @return
	 */
	List<Department> queryOrgs(BaseEntity entity);
	/**
	 * 
	 * @Description 获取组织机构下所有部门
	 * @CreateTime 2017年9月27日 上午10:18:25
	 * @CreateBy 岳彬彬
	 * @param id
	 * @return
	 */
	List<Department> getBmList(String id);
	/**
	 * 
	 * @Description 部门树
	 * @CreateTime 2017年9月28日 下午6:40:27
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

	Department selectDepartmentByDprtCodeParentId(String bmdm, String jgdm);

	/**
	 * 
	 * @Description pid查询所有的部门机构
	 * @CreateTime 2017年12月5日 下午5:12:21
	 * @CreateBy 林龙
	 * @param jgdm
	 * @return
	 */
	List<Department> findAllparentId(String jgdm);
}