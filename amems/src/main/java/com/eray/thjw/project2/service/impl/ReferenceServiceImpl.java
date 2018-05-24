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
import com.eray.thjw.project2.dao.ReferenceMapper;
import com.eray.thjw.project2.po.Reference;
import com.eray.thjw.project2.service.ReferenceService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;
import enu.project2.ProjectBusinessEnum;

/**
 * @Description 相关参考文件service实现类
 * @CreateTime 2017-8-19 下午4:41:45
 * @CreateBy 刘兵
 */
@Service
public class ReferenceServiceImpl implements ReferenceService{
	
	@Resource
	private ReferenceMapper referenceMapper;
	
	@Resource
	private CommonRecService commonRecService;
	
	/**
	 * @Description 保存多个相关参考文件
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param referenceList 相关参考文件集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void saveReferenceList(List<Reference> referenceList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		if(null != referenceList && referenceList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (Reference reference : referenceList) {
				String id = UUID.randomUUID().toString();
				reference.setId(id);
				reference.setWhrid(user.getId());
				reference.setWhdwid(user.getBmdm());
				reference.setYwlx(ywlx);
				reference.setDprtcode(dprtcode);
				reference.setYwid(ywid);
				reference.setZt(EffectiveEnum.YES.getId());
				columnValueList.add(id);
			}
			referenceMapper.insert4Batch(referenceList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_999, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个相关参考文件
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param referenceList 相关参考文件集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void updateReferenceList(List<Reference> referenceList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		List<Reference> insertReferenceList = null;//相关参考文件集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入相关参考文件id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		User user = ThreadVarUtil.getUser();
		//根据业务id、业务类型、机构代码获取数据库已经存在的相关参考文件
		List<Reference> existsReferenceList = referenceMapper.queryByYwidAndYwlxAndDrpt(ywid, ywlx, dprtcode);
		//相关参考文件map集合,key:id,value:Reference
		Map<String, Reference> referenceMap = new HashMap<String, Reference>();
		//将数据库已存在的相关参考文件放入referenceMap
		for (Reference reference : existsReferenceList) {
			referenceMap.put(reference.getId(), reference);
		}
		if(null != referenceList && referenceList.size() > 0){
			insertReferenceList = new ArrayList<Reference>();
			for (Reference reference : referenceList) {
				//判断相关参考文件id是否为空,是否存在于数据库,不为空且存在:修改相关参考文件,反之:新增相关参考文件
				if(null != reference.getId() && null != referenceMap.get(reference.getId())){
					reference.setWhrid(user.getId());
					reference.setWhdwid(user.getBmdm());
					idMap.put(reference.getId(), reference.getId());
					//修改相关参考文件
					referenceMapper.updateByPrimaryKeySelective(reference);
					//保存历史记录信息
					commonRecService.write(reference.getId(), TableEnum.B_G2_999, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
				}else{
					insertReferenceList.add(reference);
				}
			}
			//保存多个相关参考文件
			saveReferenceList(insertReferenceList, ywlx, ywid, czls, zdid, dprtcode, logopration);
		}
		for (Reference reference : existsReferenceList){
			//如果数据库相关参考文件id不在页面,那么删除
			if(null == idMap.get(reference.getId())){
				columnValueList.add(reference.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_999, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, zdid);
			//批量删除相关参考文件
			referenceMapper.delete4Batch(columnValueList);
		}
	}
	
	/**
	 * @Description 根据ywid删除相关参考文件
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除相关参考文件
		User user = ThreadVarUtil.getUser();
		columnValueList.add(ywid);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_999, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除相关参考文件
		referenceMapper.deleteByYwid(ywid);
	}
	
	/**
	 * @Description 根据条件查询相关参考文件列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param reference 相关参考文件
	 * @return List<Reference> 相关参考文件集合
	 */
	@Override
	public List<Reference> queryAllList(Reference reference){
		return referenceMapper.queryAllList(reference);
	}

	/**
	 * @Description  新增参考文件
	 * @CreateTime 2017年8月19日 下午4:51:36
	 * @CreateBy 林龙
	 * @param referenceList 参考文件list集合
	 * @param mainid 技术评估单id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	@Override
	public void insertReference(List<Reference> referenceList, String mainid, String czls) throws BusinessException {
		User user=ThreadVarUtil.getUser();//当前登陆人
		
		for (Reference reference : referenceList) {
			String uuid=UUID.randomUUID().toString();  //uuid
			reference.setId(uuid);	   			       //id
			reference.setDprtcode(user.getJgdm());
			reference.setYwlx(ProjectBusinessEnum.TECHNICAL_ASSESSMENT.getId());//业务类型为技术评估单
			reference.setYwid(mainid);				   //业务id为评估单id
			reference.setZt(EffectiveEnum.YES.getId());//状态为有效
			reference.setWhdwid(user.getBmdm());	   //维护单位
			reference.setWhrid(user.getId());		   //维护人
			referenceMapper.insertSelective(reference);
			
			//添加新增相关参考文件日志
		    commonRecService.write(uuid, TableEnum.B_G2_999, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,mainid);
		}
		
	}

	/**
	 * 
	 * @Description 根据ywid 技术评估单id删除 相关文件信息集合
	 * @CreateTime 2017年8月24日 下午3:35:10
	 * @CreateBy 林龙
	 * @param ywid 技术评估单id
	 * @param czls 流水号
	 */
	@Override
	public void deleteReference(String ywid, String czls) {
		User user = ThreadVarUtil.getUser();//当前登陆人
		//根据业务id，业务类型为9查询相关文件集合
		List<Reference> existsReferenceList = referenceMapper.queryByYwidAndYwlxAndDrpt(ywid, 9, user.getJgdm());
		
		//遍历相关文件集合
		for (Reference reference : existsReferenceList) {
			
			//添加删除相关参考文件日志
		    commonRecService.write(reference.getId(), TableEnum.B_G2_999, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE	,ywid);
		
		    //删除相关文件
		    referenceMapper.deleteByPrimaryKey(reference.getId());
		}
	}

}
