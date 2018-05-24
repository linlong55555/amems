package com.eray.thjw.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.po.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);
    
    User selectByPrimaryKey(String id);
    
    User getByUserNameAndPassword(@Param("username") String username,@Param("password") String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> queryUserFetchDepartment(User user);
    
    int updateUserPassword(User record);
    
    int updateUserInfo(User record);//修改用户信息
    
    int updateUserState(User record);
    
    int queryCountByJgdm(String jgdm);
    
    int checkUser(User user);
    
    List<User> selectAllNotChooseDepartmentTheUser(String realName);
    
    List<User> selectAllAlreadyChooseDepartmentTheUser(Map<String, Object> map);
    
    int updateUserTheDepartmentById(Map<String, Object> map);
    
    int selectUserCountByBmdm(String bmdm);
    
    int updateUserTheDepartmentByOrgnBmdm(Map<String, Object> map);
    
    List<User> queryUserAll(User user);
    
    List<User> queryUserAllByDprtcode(User user);
    
    List<User> selectUserToSendYes(String id,String dprtcode);
    
    List<User> selectUserToSendNo(String id,String dprtcode);
 /*   int updateUser(User user);*/
    int queryCountByName(User user);

    /**
     * 同步用户
     * @param authusers
     */
	void synAuthInfo(Map<String, Object> param);
	
	 /**
     * 选择培训人员联合维修人员表
     * @param authusers
     * 梅志亮
     */
	List<User> queryUserAndAttendantor(User user);
	 
	 /**
	  * 根据登陆账号ID查询用户
	  * @param drzhid
	  * @return
	  */
	 List<User> selectByDrzhid(String drzhid);
	 
	 /**
	  * 清除 登陆账号ID及账号
	  * @param id
	  * @return
	  */
	 int updateUserUnboundAccount(String id);
	 
	 /**
	  * 根据IP或MAC查询登陆限制，0为限制，非0为授权通过
	  * @param dprtcode 
	  * @param ip
	  * @param mac
	  * @return
	  */
	 int selectLimitByIpOrMac(String dprtcode, String ip, String mac);
	 /**
		 * @author sunji
	 * @param str 
		 * @description 根据组织机构查询所有用户的id和name
		 * @param  map
		 * @develop date 2016.08.10
		 * @return String
		 */
	 List<User> getByDprtcode(String dprtcode);

	
	List<User> queryAllByjx(User user);

	List<User> queryjgdm(User user1);

	List<User> selectByDrzhName(String drzhid);

	int queryCount(User user);

	void updateByPrimaryKeySelectiveImport(User user);

	List<User> getByDprtcodetree(@Param("dprtcode")String dprtcode, @Param("bmdm")String bmdm, @Param("str")String str);
	 
}