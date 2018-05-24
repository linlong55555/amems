package com.eray.thjw.project2.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.DistributionDepartmentMapper;
import com.eray.thjw.project2.po.DistributionDepartment;
import com.eray.thjw.project2.service.DistributionDepartmentService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.common.WhetherEnum;
import enu.project2.DistributionDepartmentTypeEnum;
import enu.project2.ProjectBusinessEnum;

/**
 * 
 * @Description 分发部门impl
 * @CreateTime 2017年8月21日 上午11:08:09
 * @CreateBy 林龙
 */
@Service("distributionDepartmentService")
public class DistributionDepartmentServiceImpl implements DistributionDepartmentService{

	@Resource
	private DistributionDepartmentMapper distributionDepartmentMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 新增分发部门或用户
	 * @CreateTime 2017年8月21日 上午11:03:21
	 * @CreateBy 林龙
	 * @param distributionDepartment 分发部门或用户对象
	 * @param mainid 评估单id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	@Override
	public void insertDistributionDepartment(List<DistributionDepartment> distributionDepartmentList,String mainid, String czls) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		
		for (DistributionDepartment distributionDepartment : distributionDepartmentList) {
			String uuid = UUID.randomUUID().toString();		  		 //uuid
			distributionDepartment.setId(uuid);	   				 	 //id
			distributionDepartment.setDprtcode(user.getJgdm());      //dprtcode
			distributionDepartment.setYwlx(ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId()); //业务类型为技术评估单
			distributionDepartment.setYwid(mainid);					 //业务id为技术评估单id
			distributionDepartment.setLx(DistributionDepartmentTypeEnum.department.getId());  //类型为部门
			distributionDepartment.setIsJs(WhetherEnum.NO.getId());	 //接收标识为0（否）
			distributionDepartment.setZt(EffectiveEnum.YES.getId()); //状态为有效
			distributionDepartment.setWhdwid(user.getBmdm());		 //维护单位
			distributionDepartment.setWhrid(user.getId());		     //维护人
			
			distributionDepartmentMapper.insertSelective(distributionDepartment);
			
			//添加新增涉及部门日志
		    commonRecService.write(uuid, TableEnum.B_G2_998, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,mainid);
		}
		
	}

	/**
	 * 
	 * @Description 根据评估单id查询涉及部门信息
	 * @CreateTime 2017年8月21日 下午9:33:48
	 * @CreateBy 林龙
	 * @param distributionDepartment 涉及部门
	 * @return
	 * @throws BusinessException
	 */
	@Override
	public List<DistributionDepartment> selectDepartmentList(DistributionDepartment distributionDepartment) {
		return distributionDepartmentMapper.getDepartmentByYwid(distributionDepartment.getYwid());
	}

	@Override
	public void updateDistributionDepartment(List<DistributionDepartment> distributionDepartmentList, String id,String czls) throws BusinessException {
		List<DistributionDepartment> insertDistributionDepartmentList = null;//相关涉及部门集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入相关涉及部门id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		User user = ThreadVarUtil.getUser();
		//根据业务mainid获取数据库已经存在的涉及部门信息
		List<DistributionDepartment> existsDistributionDepartmentList = distributionDepartmentMapper.getDepartmentByYwid(id);
		
		//相关涉及部门map集合,key:id,value:DistributionDepartment
		Map<String, DistributionDepartment> distributionDepartmentMap = new HashMap<String, DistributionDepartment>();
		//将数据库已存在的涉及部门数据放入referenceMap
		for (DistributionDepartment distributionDepartment : existsDistributionDepartmentList) {
			distributionDepartmentMap.put(distributionDepartment.getId(), distributionDepartment);
		}
		if(null != distributionDepartmentList && distributionDepartmentList.size() > 0){
			insertDistributionDepartmentList = new ArrayList<DistributionDepartment>();
			for (DistributionDepartment distributionDepartment : distributionDepartmentList) {
				//判断相关参考文件id是否为空,是否存在于数据库,不为空且存在:修改涉及部门信息,反之:新增涉及部门信息
				if(null != distributionDepartment.getId() && null != distributionDepartmentMap.get(distributionDepartment.getId())){
					distributionDepartment.setWhrid(user.getId());
					distributionDepartment.setWhdwid(user.getBmdm());
					idMap.put(distributionDepartment.getId(), distributionDepartment.getId());
					//修改相关参考文件
					distributionDepartmentMapper.updateByPrimaryKeySelective(distributionDepartment);
					//保存历史记录信息
					commonRecService.write(distributionDepartment.getId(), TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, id);
				}else{
					insertDistributionDepartmentList.add(distributionDepartment);
				}
			}
			//保存多个涉及部门信息
			insertDistributionDepartment(insertDistributionDepartmentList, id,czls);
		}
		for (DistributionDepartment distributionDepartment : existsDistributionDepartmentList){
			//如果数据库相关涉及部门id不在页面,那么删除
			if(null == idMap.get(distributionDepartment.getId())){
				columnValueList.add(distributionDepartment.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_998, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, id);
			//批量删除涉及部门
			distributionDepartmentMapper.delete4Batch(columnValueList);
		}		
	}

	/**
	 * 
	 * @Description 根据id 技术评估单id删除 涉及部门集合
	 * @CreateTime 2017年8月24日 下午3:35:10
	 * @CreateBy 林龙
	 * @param id 技术评估单id
	 * @param czls 流水号
	 */
	@Override
	public void deleteDistributionDepartment(String id, String czls) {
		//根据业务id获取数据库已经存在的涉及部门信息
		List<DistributionDepartment> existsDistributionDepartmentList = distributionDepartmentMapper.getDepartmentByYwid(id);
		
		for (DistributionDepartment distributionDepartment : existsDistributionDepartmentList) {
			//添加删除涉及部门信息日志
		    commonRecService.write(distributionDepartment.getId(), TableEnum.B_G2_998, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE	,id);
		
		    //删除涉及部门信息
		    distributionDepartmentMapper.deleteByPrimaryKey(distributionDepartment.getId());
		}
		
	}
	
}
