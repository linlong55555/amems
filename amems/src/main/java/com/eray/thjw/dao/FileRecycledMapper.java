package com.eray.thjw.dao;

import java.util.List;

import com.eray.thjw.po.FileCatalog;
import com.eray.thjw.po.FileRecycled;

/**
 * @Description 文件目录-回收站mapper
 * @CreateTime 2018年1月26日 上午10:32:34
 * @CreateBy 韩武
 */
public interface FileRecycledMapper {
    int deleteByPrimaryKey(String id);

    int insert(FileRecycled record);

    int insertSelective(FileRecycled record);

    FileRecycled selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FileRecycled record);

    int updateByPrimaryKey(FileRecycled record);
    
    /**
     * @Description 查询回收站列表
     * @CreateTime 2018年1月29日 下午2:10:39
     * @CreateBy 韩武
     * @param file
     * @return
     */
    List<FileRecycled> queryRecycledList(FileCatalog file);
    
    /**
     * @Description 根据模块代码删除
     * @CreateTime 2018年1月30日 下午1:58:37
     * @CreateBy 韩武
     * @param file
     * @return
     */
    int deleteByMkdm(FileCatalog file);
}