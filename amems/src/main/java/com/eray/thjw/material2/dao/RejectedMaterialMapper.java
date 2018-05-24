package com.eray.thjw.material2.dao;

import java.util.List;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.material2.po.RejectedMaterial;

public interface RejectedMaterialMapper {
    int deleteByPrimaryKey(String id);

    int insert(RejectedMaterial record);

    int insertSelective(RejectedMaterial record);

    RejectedMaterial selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RejectedMaterial record);

    int updateByPrimaryKey(RejectedMaterial record);
    
    /**
	 * 
	 * @Description 获取退料申请数据
	 * @CreateTime 2018-3-2 下午2:23:30
	 * @CreateBy 孙霁
	 * @param rejectedMaterial
	 * @return
	 * @throws BusinessException
	 */
    List<RejectedMaterial> queryAll(RejectedMaterial record);
    
    /**
	 * 
	 * @Description 验证是否存在关联表b_h_027
	 * @CreateTime 2018-3-6 下午2:27:16
	 * @CreateBy 孙霁
	 * @param id
	 * @return
	 */
    RejectedMaterial selectRejectedMaterial(String id);
    
}