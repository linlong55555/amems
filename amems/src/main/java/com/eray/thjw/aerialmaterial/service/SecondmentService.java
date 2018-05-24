package com.eray.thjw.aerialmaterial.service;

import java.util.List;

import com.eray.thjw.aerialmaterial.po.Secondment;

/**
 * 借调对象业务接口
 * @author xu.yong
 *
 */
public interface SecondmentService {
	
	/**
	 * 根据借调对象类型查询
	 * @return
	 */
	public List<Secondment> queryByType(Secondment secondment);
	
	/**
	 * 借调对象列表查询
	 * @author meizhiliang
	 */
    List<Secondment> selectSecondmentList(Secondment record);  
	/**
	 * 判断借调对象编号是否重复
	 * @author meizhiliang
	 */
	int selectCount(String jddxbh);    
	/**
	 * 新增借调对象
	 * @author meizhiliang
	 */
	int insertSelective(Secondment record);   
	/**
	 * 根据id查询一个借调对象
	 * @author meizhiliang
	 */
	Secondment selectById(String id);   
	/**
	 * 更新借调对象
	 * @author meizhiliang
	 */
	int updateByPrimaryKeySelective(Secondment record); 
	/**
	 * 根据组织机构获取所有对象
	 * @author sunji
	 */
	List<Secondment> queryByDprtcode(String dprtcode);

	public List<Secondment> queryOtherOrg(String jgdm); 
	
	/**
	 * 查询所有的借调对象
	 * @return
	 */
	public List<Secondment> queryAll(Secondment secondment);
	
}
