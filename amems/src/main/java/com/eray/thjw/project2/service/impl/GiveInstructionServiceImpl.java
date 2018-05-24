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
import com.eray.thjw.project2.dao.GiveInstructionMapper;
import com.eray.thjw.project2.po.GiveInstruction;
import com.eray.thjw.project2.service.GiveInstructionService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 技术评估单-下达指令Impl
 * @CreateTime 2017年8月16日 上午10:22:32
 * @CreateBy 林龙
 */
@Service("giveInstructionService")
public class GiveInstructionServiceImpl implements GiveInstructionService{

	@Resource
	private GiveInstructionMapper giveInstructionMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 新增技术评估单-下达指令
	 * @CreateTime 2017年8月18日 下午10:11:47
	 * @CreateBy 林龙
	 * @param giveInstruction
	 * @param mainid 技术评估单id
	 * @param czls 流水号
	 */
	@Override
	public void insertGiveInstruction(List<GiveInstruction> giveInstructionList,String mainid, String czls) throws BusinessException{
		User user = ThreadVarUtil.getUser();//当前登陆人
		if(giveInstructionList!=null){
			for (GiveInstruction giveInstruction : giveInstructionList) {
				String uuid = UUID.randomUUID().toString();		  //uuid
				giveInstruction.setId(uuid);	   				  //id
				giveInstruction.setMainid(mainid); 				  //技术文件评估单id
				giveInstruction.setDprtcode(user.getJgdm());      //dprtcode
				giveInstruction.setZt(EffectiveEnum.YES.getId()); //状态为有效
				giveInstruction.setWhdwid(user.getBmdm());		  //维护单位
				giveInstruction.setWhrid(user.getId());		      //维护人
				
				giveInstructionMapper.insertSelective(giveInstruction);
				
				//添加新增技术评估单-下达指令日志
				commonRecService.write(uuid, TableEnum.B_G2_00101, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.SAVE_WO, UpdateTypeEnum.SAVE,mainid);
			}
		}
	}

	/**
	 * 
	 * @Description 根据技术评估单-下达指令对象查询技术评估单-下达指令List集合
	 * @CreateTime 2017年8月19日 上午10:09:47
	 * @CreateBy 林龙
	 * @param giveInstruction
	 * @return 技术评估单-下达指令List集合
	 */
	@Override
	public List<GiveInstruction> selectOrderList(GiveInstruction giveInstruction) throws BusinessException {
		return giveInstructionMapper.selectOrderList(giveInstruction);
	}

	/**
	 * 
	 * @Description 编辑技术评估单-下达指令List集合 
	 * @CreateTime 2017年8月22日 上午10:32:57
	 * @CreateBy 林龙
	 * @param giveInstructionList 下达指令List集合
	 * @param id 评估单id
	 * @param czls 流水号
	 * @throws BusinessException
	 */
	@Override
	public void updateGiveInstruction(List<GiveInstruction> giveInstructionList, String id, String czls)throws BusinessException {
		List<GiveInstruction> insertGiveInstructionList = null;//相关下达指令集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入相关下达指令id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
		User user = ThreadVarUtil.getUser();
		//根据业务mainid获取数据库已经存在的下达指令信息
		GiveInstruction giveInstruction1=new GiveInstruction();
		giveInstruction1.setMainid(id);
		List<GiveInstruction> existsGiveInstructionList = giveInstructionMapper.selectOrderList(giveInstruction1);
		
		//相关下达指令map集合,key:id,value:GiveInstruction
		Map<String, GiveInstruction> giveInstructionMap = new HashMap<String, GiveInstruction>();
		//将数据库已存在的下达指令数据放入referenceMap
		for (GiveInstruction giveInstruction : existsGiveInstructionList) {
			giveInstructionMap.put(giveInstruction.getId(), giveInstruction);
		}
		if(null != giveInstructionList && giveInstructionList.size() > 0){
			insertGiveInstructionList = new ArrayList<GiveInstruction>();
			for (GiveInstruction giveInstruction : giveInstructionList) {
				//判断相关参考文件id是否为空,是否存在于数据库,不为空且存在:修改下达指令信息,反之:新增下达指令信息
				if(null != giveInstruction.getId() && null != giveInstructionMap.get(giveInstruction.getId())){
					giveInstruction.setWhrid(user.getId());
					giveInstruction.setWhdwid(user.getBmdm());
					idMap.put(giveInstruction.getId(), giveInstruction.getId());
					//修改相关参考文件
					giveInstructionMapper.updateByPrimaryKeySelective(giveInstruction);
					//保存历史记录信息
					commonRecService.write(giveInstruction.getId(), TableEnum.B_G2_00101, user.getId(), czls, LogOperationEnum.EDIT, UpdateTypeEnum.UPDATE, id);
				}else{
					insertGiveInstructionList.add(giveInstruction);
				}
			}
			//保存多个下达指令信息
			insertGiveInstruction(insertGiveInstructionList, id,czls);
		}
		for (GiveInstruction giveInstruction : existsGiveInstructionList){
			//如果数据库相关下达指令id不在页面,那么删除
			if(null == idMap.get(giveInstruction.getId())){
				columnValueList.add(giveInstruction.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_00101, user.getId(), czls, LogOperationEnum.ZUOFEI, UpdateTypeEnum.DELETE, id);
			//批量删除下达指令
			giveInstructionMapper.delete4Batch(columnValueList);
		}
		
	}

	/**
	 * 
	 * @Description 根据mainid 技术评估单id删除 下达指令List集合 
	 * @CreateTime 2017年8月24日 下午3:35:10
	 * @CreateBy 林龙
	 * @param mainid 技术评估单id
	 * @param czls 流水号
	 */
	@Override
	public void deleteGiveInstruction(String mainid, String czls)throws BusinessException {
		
		//根据业务mainid获取数据库已经存在的下达指令信息
		GiveInstruction giveInstructionOld = new GiveInstruction();
		giveInstructionOld.setMainid(mainid);
		List<GiveInstruction> existsGiveInstructionList = giveInstructionMapper.selectOrderList(giveInstructionOld);
		
		//遍历相关文件集合
		for (GiveInstruction giveInstruction : existsGiveInstructionList) {
			
			//添加删除下达指令List集合 日志
		    commonRecService.write(giveInstruction.getId(), TableEnum.B_G2_00101, ThreadVarUtil.getUser().getId(), czls, LogOperationEnum.DELETE, UpdateTypeEnum.DELETE	,mainid);
		
		    //删除下达指令List集合 
		    giveInstructionMapper.deleteByPrimaryKey(giveInstruction.getId());
		}
	}

	/**
	 * 
	 * @Description 根据mainid集合 技术评估单id删除 下达指令List集合 
	 * @CreateTime 2017年9月1日 上午9:42:33
	 * @CreateBy 林龙
	 * @param pgdidList
	 * @return
	 */
	@Override
	public List<GiveInstruction> selectOrdersList(List<String> pgdidList) {
		return giveInstructionMapper.selectOrdersList(pgdidList);
	}
	

	
}
