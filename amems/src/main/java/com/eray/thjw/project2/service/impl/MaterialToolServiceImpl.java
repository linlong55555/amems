package com.eray.thjw.project2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.po.User;
import com.eray.thjw.project2.dao.MaterialToolMapper;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.service.MaterialToolService;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.LogOperationEnum;
import enu.TableEnum;
import enu.UpdateTypeEnum;
import enu.common.EffectiveEnum;

/**
 * @Description 器材/工具service实现类
 * @CreateTime 2017-8-19 下午2:58:02
 * @CreateBy 刘兵
 */
@Service
public class MaterialToolServiceImpl implements MaterialToolService{
	
	@Resource
	private MaterialToolMapper materialToolMapper;
	
	@Resource
	private CommonRecService commonRecService;

	/**
	 * @Description 保存多个器材/工具
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param workContentList 器材/工具集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void saveMaterialToolList(List<MaterialTool> materialToolList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		if(null != materialToolList && materialToolList.size() > 0){
			User user = ThreadVarUtil.getUser();
			List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志
			for (MaterialTool materialTool : materialToolList) {
				String id = UUID.randomUUID().toString();
				materialTool.setId(id);
				materialTool.setWhrid(user.getId());
				materialTool.setWhdwid(user.getBmdm());
				materialTool.setYwlx(ywlx);
				materialTool.setDprtcode(dprtcode);
				materialTool.setYwid(ywid);
				materialTool.setZt(EffectiveEnum.YES.getId());
				columnValueList.add(id);
			}
			materialToolMapper.insert4Batch(materialToolList);
			if(columnValueList.size() > 0){
				// 保存历史记录信息
				commonRecService.write("id", columnValueList, TableEnum.B_G2_997, user.getId(), czls, logopration, UpdateTypeEnum.SAVE, zdid);
			}
		}
	}
	
	/**
	 * @Description 编辑多个器材/工具
	 * @CreateTime 2017-8-19 下午4:15:15
	 * @CreateBy 刘兵
	 * @param materialToolList 器材/工具集合
	 * @param ywlx 业务类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param ywid 业务ID
	 * @param czls 操作流水
	 * @param zdid 主单id
	 * @param dprtcode 机构代码
	 * @param logopration 操作类型
	 */
	@Override
	public void updateMaterialToolList(List<MaterialTool> materialToolList, int ywlx, String ywid, String czls, String zdid, String dprtcode, LogOperationEnum logopration){
		List<MaterialTool> insertMaterialToolList = null;//器材/工具集合,用于新增
		Map<String, String> idMap = new HashMap<String, String>();//记录页面传入器材/工具id集合
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除工种工时
		User user = ThreadVarUtil.getUser();
		//根据业务id、业务类型、机构代码获取数据库已经存在的器材/工具
		List<MaterialTool> existsMaterialToolList = materialToolMapper.queryByYwidAndYwlxAndDrpt(ywid, ywlx, dprtcode);
		//器材/工具map集合,key:id,WorkContent
		Map<String, MaterialTool> materialToolMap = new HashMap<String, MaterialTool>();
		//将数据库已存在的器材/工具放入workContentMap
		for (MaterialTool materialTool : existsMaterialToolList) {
			materialToolMap.put(materialTool.getId(), materialTool);
		}
		if(null != materialToolList && materialToolList.size() > 0){
			insertMaterialToolList = new ArrayList<MaterialTool>();
			for (MaterialTool materialTool : materialToolList) {
				//判断器材/工具id是否为空,是否存在于数据库,不为空且存在:修改器材/工具,反之:新增器材/工具
				if(null != materialTool.getId() && null != materialToolMap.get(materialTool.getId())){
					materialTool.setWhrid(user.getId());
					materialTool.setWhdwid(user.getBmdm());
					idMap.put(materialTool.getId(), materialTool.getId());
					//修改器材/工具
					materialToolMapper.updateByPrimaryKeySelective(materialTool);
					//保存历史记录信息
					commonRecService.write(materialTool.getId(), TableEnum.B_G2_997, user.getId(), czls, logopration, UpdateTypeEnum.UPDATE, zdid);
				}else{
					insertMaterialToolList.add(materialTool);
				}
			}
			//保存多个器材/工具
			saveMaterialToolList(insertMaterialToolList, ywlx, ywid, czls, zdid, dprtcode, logopration);
		}
		for (MaterialTool materialTool : existsMaterialToolList){
			//如果数据库器材/工具id不在页面,那么删除
			if(null == idMap.get(materialTool.getId())){
				columnValueList.add(materialTool.getId());
			}
		}
		if(columnValueList.size() > 0){
			// 保存历史记录信息
			commonRecService.write("id", columnValueList, TableEnum.B_G2_997, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
			//批量删除器材/工具
			materialToolMapper.delete4Batch(columnValueList);
		}
	}
	
	/**
	 * @Description 根据ywid删除器材/工具
	 * @CreateTime 2017-9-13 下午5:16:13
	 * @CreateBy 刘兵
	 * @param ywid 父表id
	 * @param czls 操作流水
	 * @param zdid 主表id
	 * @param logopration 操作
	 */
	@Override
	public void deleteByYwid(String ywid, String czls, String zdid, LogOperationEnum logopration){
		List<String> columnValueList = new ArrayList<String>();//id集合,用于批量插入日志,删除器材/工具
		User user = ThreadVarUtil.getUser();
		columnValueList.add(ywid);
		// 保存历史记录信息
		commonRecService.write("ywid", columnValueList, TableEnum.B_G2_997, user.getId(), czls, logopration, UpdateTypeEnum.DELETE, zdid);
		//根据业务id删除器材/工具
		materialToolMapper.deleteByYwid(ywid);
	}
	
	/**
	 * @Description 根据条件查询器材/工具列表
	 * @CreateTime 2017-8-19 下午3:48:50
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryAllList(MaterialTool materialTool){
		return materialToolMapper.queryAllList(materialTool);
	}

	/**
	 * @Description 根据条件查询器材清单列表
	 * @CreateTime 2017-8-19 下午3:49:49
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryEquipmentList(MaterialTool materialTool){
		return materialToolMapper.queryEquipmentList(materialTool);
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(维修项目)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryToolList4Maintenance(MaterialTool materialTool) {
		return materialToolMapper.queryToolList4Maintenance(materialTool);
	}

	/**
	 * @Description 根据条件查询航材工具需求清单(EO)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryToolList4EO(MaterialTool materialTool) {
		return materialToolMapper.queryToolList4EO(materialTool);
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工单)
	 * @CreateTime 2017-10-25 下午3:03:06
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryToolList4WorkOrder(MaterialTool materialTool) {
		return materialToolMapper.queryToolList4WorkOrder(materialTool);
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(工包)
	 * @CreateTime 2017-10-28 下午1:11:56
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryToolList4Package(MaterialTool materialTool) {
		return materialToolMapper.queryToolList4Package(materialTool);
	}
	
	/**
	 * @Description 根据条件查询航材工具需求清单(选中的)
	 * @CreateTime 2017-10-27 上午10:41:26
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryAllToolList(MaterialTool materialTool) {
		return materialToolMapper.queryAllToolList(materialTool);
	}
	
	/**
	 * 
	 * @Description 根据条件查询航材工具需求清单(145工包)
	 * @CreateTime 2017年10月28日 下午4:07:35
	 * @CreateBy 岳彬彬
	 * @param materialTool
	 * @return
	 */
	@Override
	public List<MaterialTool> queryToolList4WP145(MaterialTool materialTool) {
		return materialToolMapper.queryToolList4WP145(materialTool);
	}
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(选中的)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryTaskInfoList(MaterialTool materialTool) {
		return materialToolMapper.queryTaskInfoList(materialTool);
	}
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工包)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryTaskInfoList4Package(MaterialTool materialTool) {
		return materialToolMapper.queryTaskInfoList4Package(materialTool);
	}
	
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(工单)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryTaskInfoList4WorkOrder(MaterialTool materialTool) {
		return materialToolMapper.queryTaskInfoList4WorkOrder(materialTool);
	}
	/**
	 * @Description 根据条件查询器材/工具任务信息列表(145工包)
	 * @CreateTime 2017-11-1 上午10:59:29
	 * @CreateBy 刘兵
	 * @param materialTool 器材/工具 
	 * @return List<MaterialTool> 器材/工具集合
	 */
	@Override
	public List<MaterialTool> queryTaskInfoList4WP145(MaterialTool materialTool) {
		
		return materialToolMapper.queryTaskInfoList4WP145(materialTool);
	}
}
