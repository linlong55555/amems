package com.eray.thjw.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.eray.thjw.system.po.BookMark;
import com.eray.thjw.system.po.CustomShortcut;

public interface CustomShortcutMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomShortcut record);

    int insertSelective(CustomShortcut record);

    CustomShortcut selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomShortcut record);

    int updateByPrimaryKey(CustomShortcut record);
    
    /**
	 * @author liub
	 * @description  根据用户id、菜单id修改快捷菜单
	 * @param record
	 * @return int
	 * @develop date 2017.03.07
	 */
    int updateByUserMenuId(CustomShortcut record);
    
  
    
    /**
	 * @author liub
	 * @description  根据用户id、菜单id查询快捷菜单
	 * @param userId 用户ID
     * @param menuId 菜单ID
	 * @return CustomShortcut
	 * @develop date 2017.03.07
	 */
    CustomShortcut selectByUserMenuId(String userId, String menuId);
    
    /**
	 * @author liub
	 * @description  根据userId（用户ID） 查询 
	 * @param userId 用户ID
	 * @param userType 用户类型 1管理员，2普通用户
	 * @return List<CustomShortcut>
	 * @develop date 2017.03.07
	 */
    List<CustomShortcut> selectByUserId(@Param("userId") String userId, @Param("userType") String userType);
    
    /**
     * 根据mainid（用户ID） 查询 
     * @param mainid 用户ID
     * @param userType 用户类型 1管理员，2普通用户
     * @return
     */
    List<CustomShortcut> selectByMainId(@Param("mainId") String mainId, @Param("userType") String userType);
    
    /**
     * 根据mainid（用户ID）  删除
     * @param mainid 用户ID
     * @return
     */
    int deleteByMainId(String mainId);
    /**
     * 根据菜单ID、用户ID 删除
     * @param userId 用户ID
     * @param menuId 菜单ID
     * @return
     */
    int deleteByUserMenuId(String userId, String menuId);
}