package com.eray.thjw.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;

public interface UserService {

	/**
	 * @author sunji
	 * @description 根据id修改数据
	 * @develop date 2016.08.5
	 */
	public void updateByPrimaryKeySelective(User user);
	/**
	 * @author sunji
	 * @description 用户service,用于查询所有数据及所在组织机构
	 * @develop date 2016.08.5
	 */
	public Map<String, Object> SelectByUser(User user) ;
	/**
	 * @author sunji
	 * @description 用户service,添加用户
	 * @develop date 2016.08.5
	 */
	public String insertUser(User user)throws BusinessException;
	/**
	 * @author sunji
	 * @description 用户service,删除用户
	 * @develop date 2016.08.5
	 */
	public String deleteUser(String userId);
	/**
	 * @author sunji
	 * @description 用户service,修改密码
	 * @develop date 2016.08.5
	 */
	public String updateUserPassword(User user);
	/**
	 * @author peixiu
	 * @description 用户service,修改用户信息
	 * @develop date 2017.03.30
	 */
	public String updateUserInfo(User user);
	/**
	 * @author sunji
	 * @description 用户service,修改状态
	 * @develop date 2016.08.5
	 */
	public String updateUserState(User user)throws Exception;
	
	/**
	 * @author liub
	 * @description 用户登录验证
	 * @param username,password
	 * @return Map<String, Object>
	 * @develop date 2016.08.08
	 */
	public Map<String, Object> login(String username,String password, HttpServletRequest request, String ip);
	
	/**
	 * @author pingxiaojun
	 * @description 查询全部未选择组织机构的用户
	 * @param realName 用户真实姓名
	 * @develop date 2016.08.08
	 * @return List<User> 全部未选择组织机构的用户
	 */
	public List<User> queryAllNotChooseDepartmentTheUser(String realName);
	
	/**
	 * @author pingxiaojun
	 * @description 查询全部已选择组织机构的用户
	 * @param realName 用户真实姓名
	 * @param jgdm 组织机构代码
	 * @develop date 2016.08.09
	 * @return List<User> 全部已选择组织机构的用户
	 */
	public List<User> queryAllAlreadyChooseDepartmentTheUser(String realName, String jgdm);
	
	/**
	 * @author pingxiaojun
	 * @description 根据用户主键id修改用户的组织机构
	 * @param id 用户主键id
	 * @param bmdm 部门代码
	 * @param jgdm 组织机构代码
	 * @develop date 2016.08.09
	 * @return int 受影响的行数
	 */
	public int updateUserTheDepartmentById(String id, String bmdm, String jgdm);
	
	/**
	 * @author pingxiaojun
	 * @description 根据部门代码查询该组织机构下的用户的条数
	 * @param bmdm 部门代码
	 * @develop date 2016.08.10
	 * @return int 该组织机构下的用户的条数
	 */
	public int queryUserCountByBmdm(String bmdm);
	
	/**
	 * @author pingxiaojun
	 * @description 根据原始的部门代码修改用户的组织机构
	 * @param bmdm 部门代码
	 * @param jgdm 组织机构代码
	 * @param orgnBmdm 原始的部门代码
	 * @develop date 2016.08.11
	 * @return int 受影响的行数
	 */
	public int updateUserTheDepartmentByOrgnBmdm(String bmdm, String jgdm, String orgnBmdm);

	/**
	 * @author sunji
	 * @description 查询用户
	 * @param  user
	 * @develop date 2016.08.10
	 * @return user 查询单个
	 */
	public User selectByPrimaryKey(String userId);
	/**
	 * @author sunji
	 * @description 修改用户
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 * @throws BusinessException 
	 */
	public String updateUser(User user) throws BusinessException;
	/**
	 * @author sunji
	 * @description 查询所有用户的id和name
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> queryUserAll(User user);
	/**
	 * @author sunji
	 * @description 查询所有用户的id和name和部门
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> queryUserAllByDprtcode(User user);
	/**
	 * @author sunji
	 * @description 根据技术指令id，查询已圈阅的人员
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> selectUserToSendYes(String id,String dprtcode);
	/**
	 * @author sunji
	 * @description 根据技术指令id，查询未圈阅的人员
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> selectUserToSendNo(String id,String dprtcode);
	/**
	 * 通过单点登录
	 * @param username
	 * @return Map<String, Object>
	 */
	public Map<String, Object> ssoLogin(String username, String password) throws RuntimeException;
	/**
	 * 根据条件查询条数
	 * @param user
	 * @return int
	 */
	public int queryCountByName(User  user);
	/**
     * 选择培训人员联合维修人员表
     * @param authusers
     * 梅志亮
     */
	List<User> queryUserAndAttendantor(User user);
	 
	/**
	 * 解绑用户账号
	 * @param id
	 */
	public void updateUserUnboundAccount(String id);
	/**
	 * 
	 * @param id
	 */
	public List<User> queryAllByjx(User user);
	
	/**
	 * 演示登录
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	public Map<String, Object> login4demo(String username, String password, HttpServletRequest request);
	/**
	 * @author sunji
	 * @description 根据组织机构查询所有用户的id和name
	 * @param  map
	 * @develop date 2016.08.10
	 * @return String
	 */
	public List<User> getByDprtcode(String dprtcode);
	/**
	 * @author sunji
	 * @description 根据机构代码查询人员树(按部门)
	 * @param dprtcode
	 * @param str 
	 * @return List<Map<String, Object>>
	 */
	public Map<String, Object> queryDrptTreeByDprtcode(String dprtcode, String bmdm, String str);
	
	 
}
