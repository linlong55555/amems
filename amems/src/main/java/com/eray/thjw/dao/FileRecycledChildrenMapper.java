package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycledChildren;

/**
 * @Description 文件目录-回收站子表mapper
 * @CreateTime 2018年1月26日 上午10:25:55
 * @CreateBy 韩武
 */
public interface FileRecycledChildrenMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileRecycledChildren record);

    int insertSelective(FileRecycledChildren record);

    FileRecycledChildren selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileRecycledChildren record);

    int updateByPrimaryKey(FileRecycledChildren record);
    
    /**
     * @Description 批量插入
     * @CreateTime 2018年1月29日 上午9:50:11
     * @CreateBy 韩武
     * @param list
     * @return
     */
    int batchInsert(List<FileRecycledChildren> list);
    
    /**
     * @Description 根据mainid删除
     * @CreateTime 2018年1月29日 下午4:55:06
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    int deleteByMainid(String mainid);
    
    /**
     * @Description 根据mainid获取需要删除的节点（在文件和目录表的状态=0）
     * @CreateTime 2018年1月30日 上午9:34:22
     * @CreateBy 韩武
     * @param mainid
     * @return
     */
    List<FileRecycledChildren> findInvalidDataByMainid(String mainid);
    
    /**
     * @Description 根据模块代码获取取药删除的节点（在文件和目录表的状态=0）
     * @CreateTime 2018年1月30日 下午1:50:40
     * @CreateBy 韩武
     * @param fileCatalog
     * @return
     */
    List<FileRecycledChildren> findInvalidDataByMkdm(FileCatalog fileCatalog);
    
    /**
     * @Description 根据模块代码删除
     * @CreateTime 2018年1月30日 下午1:58:37
     * @CreateBy 韩武
     * @param file
     * @return
     */
    int deleteByMkdm(FileCatalog file);
    
}