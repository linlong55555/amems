package com.eray.thjw.aerialmaterial.dao;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.aerialmaterial.po.Inspection;
import com.eray.thjw.produce.po.InstallationListEditable;

/**
 * @Description 部件证书mapper
 * @CreateTime 2017年9月28日 上午10:04:30
 * @CreateBy 韩武
 */
public interface ComponentCertificateMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComponentCertificate record);

    int insertSelective(ComponentCertificate record);

    ComponentCertificate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ComponentCertificate record);

    int updateByPrimaryKey(ComponentCertificate record);
    
    /**
     * @Description 查询部件证书集合
     * @CreateTime 2017年9月28日 上午10:04:19
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<ComponentCertificate> queryList(ComponentCertificate record);
    
    /**
     * @Description 根据业务主键删除对应证书
     * @CreateTime 2017年9月28日 下午5:11:29
     * @CreateBy 韩武
     * @param record
     * @return
     */
    int deleteByKey(InstallationListEditable record);
    
    /**
     * @Description 根据业务主键查询
     * @CreateTime 2017年9月28日 下午5:27:46
     * @CreateBy 韩武
     * @param record
     * @return
     */
    List<String> selectByKey(InstallationListEditable record);

	/**
	 * 
	 * @Description 根据条件查询数据
	 * @CreateTime 2018-3-19 下午4:57:32
	 * @CreateBy 孙霁
	 * @param record
	 */
    List<ComponentCertificate> selectByParams(ComponentCertificate record);

}