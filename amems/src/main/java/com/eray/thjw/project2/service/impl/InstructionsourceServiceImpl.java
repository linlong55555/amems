package com.eray.thjw.project2.service.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eray.thjw.exception.BusinessException;
import com.eray.thjw.produce.po.Workorder;
import com.eray.thjw.produce.service.WorkOrderNewService;
import com.eray.thjw.project.dao.MelChangeSheetMapper;
import com.eray.thjw.project.po.MelChangeSheet;
import com.eray.thjw.project2.dao.InstructionsourceMapper;
import com.eray.thjw.project2.po.EngineeringOrder;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.MaintenanceScheme;
import com.eray.thjw.project2.po.TechnicalInstruction;
import com.eray.thjw.project2.po.WorkCard;
import com.eray.thjw.project2.service.EngineeringOrderService;
import com.eray.thjw.project2.service.InstructionsourceService;
import com.eray.thjw.project2.service.MaintenanceSchemeService;
import com.eray.thjw.project2.service.TechnicalInstructionService;
import com.eray.thjw.project2.service.WorkCardService;
import com.eray.thjw.service.CommonRecService;

/**
 * @Description 下达指令来源表Impl
 * @CreateTime 2017年8月16日 上午10:22:32
 * @CreateBy 林龙
 */
@Service("instructionsourceService")
public class InstructionsourceServiceImpl implements InstructionsourceService{

	@Resource
	private InstructionsourceMapper instructionsourceMapper;
	
	@Resource
	private CommonRecService commonRecService;
	@Resource
	private WorkOrderNewService workOrderNewService;
	
	@Resource
	private MaintenanceSchemeService maintenanceSchemeService;
	
	@Resource
	private EngineeringOrderService engineeringOrderService;
	
	@Resource
	private MelChangeSheetMapper melChangeSheetMapper;
	@Resource
	private WorkCardService workCardService;
	@Resource
	private TechnicalInstructionService technicalInstructionService;

	/**
	 * 
	 * @Description 根据pgdid查询下达指令来源List集合信息
	 * @CreateTime 2017年8月19日 下午2:46:01
	 * @CreateBy 林龙
	 * @param instructionsourc 下达指令来源
	 * @return 下达指令来源List
	 * @throws BusinessException
	 */
	@Override
	public List<Instructionsource> selectOrderList(
			Instructionsource instructionsourc) throws BusinessException {
		return instructionsourceMapper.selectOrderList(instructionsourc);
	}

	/**
	 * @Description 根据指令id删除对应的下达指令
	 * @CreateTime 2017-8-22 上午10:35:59
	 * @CreateBy 刘兵
	 * @param zlid 指令id
	 */
	@Override
	public void deleteInstructionSourceByZlid(String zlid){
		instructionsourceMapper.deleteInstructionSourceByZlid(zlid);
	}

	/**
	 * @Description 保存多个下达指令来源
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param instructionsourceList 下达指令来源集合
	 * @param zlxl 指令类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param zlid 指令id
	 * @param dprtcode 机构代码
	 */
	@Override
	public void saveInstructionSourceList(List<Instructionsource> instructionsourceList, int zlxl, String zlid, String dprtcode) {
		if(null != instructionsourceList && instructionsourceList.size() > 0){
			for (Instructionsource instructionsource : instructionsourceList) {
				instructionsource.setZlid(zlid);
				instructionsource.setZlxl(zlxl);
				instructionsource.setDprtcode(dprtcode);
			}
			instructionsourceMapper.insertInstructionSource(instructionsourceList);
		}
	}
	
	/**
	 * @Description 编辑多个下达指令来源
	 * @CreateTime 2017-8-19 下午3:09:13
	 * @CreateBy 刘兵
	 * @param instructionsourceList 下达指令来源集合
	 * @param zlxl 指令类型：1技术通告、2技术指令、3维修方案、4非例行工单、6工程指令EO、7MEL、8工卡、9技术评估单
	 * @param zlid 指令id
	 * @param dprtcode 机构代码
	 */
	@Override
	public void updateInstructionSourceList(List<Instructionsource> instructionsourceList, int zlxl, String zlid, String dprtcode) {
		//删除旧的下达指令
		deleteInstructionSourceByZlid(zlid);
		if(null != instructionsourceList && instructionsourceList.size() > 0){
			for (Instructionsource instructionsource : instructionsourceList) {
				instructionsource.setZlid(zlid);
				instructionsource.setZlxl(zlxl);
				instructionsource.setDprtcode(dprtcode);
			}
			instructionsourceMapper.insertInstructionSource(instructionsourceList);
		}
	}

	/**
	 * 
	 * @Description 根据评估单id验证 下达的指令（除技术通告） 必须EO关闭（指定结束/完成）维修方案批准、MEL更改单批准、工卡生效/失效、 技术指令关闭 才能关闭
	 * @CreateTime 2017年8月24日 下午9:26:03
	 * @CreateBy 林龙
	 * @param id 评估单id
	 * @throws BusinessException
	 */
	@Override
	public void validation4Colse(String id) throws BusinessException {
		Instructionsource instructionsourceOld=new Instructionsource();
		instructionsourceOld.setPgdid(id);
		List<Instructionsource> instructionsourceList = instructionsourceMapper.selectOrderList(instructionsourceOld);
		
		for (Instructionsource instructionsource : instructionsourceList) {
			if(instructionsource.getZlxl() == 2){//验证技术指令
				// 技术评估单关闭验证技术指令状态
				TechnicalInstruction technicalInstruction=technicalInstructionService.getRecord(instructionsource.getZlid());
				if(technicalInstruction!=null){
					if(technicalInstruction.getZt()!=9&&technicalInstruction.getZt()!=10){
						throw new BusinessException("技术指令状态未指定结束或者完成，不可关闭技术文件评估单!");
					}
				}
			}else if(instructionsource.getZlxl() == 3){//验证维修方案
				//根据id查询维修方案信息
				MaintenanceScheme maintenanceScheme=maintenanceSchemeService.selectByPrimaryKey(instructionsource.getZlid());
				if(maintenanceScheme!=null){
					//验证维修方案是否批准
					if(maintenanceScheme.getZt() != 4 && maintenanceScheme.getZt() != 10){
						throw new BusinessException("维修方案状态未批准，或者未生效，不可关闭技术文件评估单!");
					}
				}
			}else if(instructionsource.getZlxl() == 4){//下达NRC
				//技术评估单关闭验证NRC状态
				Workorder workorder=workOrderNewService.selectByPrimaryKey(instructionsource.getZlid());
				if(workorder!=null){
					if(workorder.getZt() !=9 && workorder.getZt() != 10){
						throw new BusinessException("下达工单（维修指令）状态未指定结束或者完成，不可关闭技术文件评估单!");
					}
				}
				
			}else if(instructionsource.getZlxl() == 6){//验证EO
				//根据id查询eo信息
				EngineeringOrder engineeringOrder=engineeringOrderService.selectById(instructionsource.getZlid());
				if(engineeringOrder!=null){
					//工程指令EO关闭（指定结束/完成）
					if(engineeringOrder.getZt()!=9&&engineeringOrder.getZt()!=10){
						throw new BusinessException("工程指令EO状态未指定结束或者完成，不可关闭技术文件评估单!");
					}
				}
			}else if(instructionsource.getZlxl() == 7){//验证MEL更改
				//根据id查询MEL更改单信息
				MelChangeSheet melChangeSheet=melChangeSheetMapper.selectByPrimaryKey(instructionsource.getZlid());
				if(melChangeSheet!=null){
					//MEL更改单批准
					if(melChangeSheet.getZt()!=4&&melChangeSheet.getZt()!=9&&melChangeSheet.getZt()!=10){
						throw new BusinessException("MEL更改单状态未批准或者关闭，不可关闭技术文件评估单!");
					}
				}
			}else if(instructionsource.getZlxl() == 8){//验证工卡
				//根据id查询工卡信息
				WorkCard workCard=workCardService.selectById(instructionsource.getZlid());
				if(workCard!=null){
					//工卡生效/失效
					if(workCard.getZt()!=7&&workCard.getZt()!=8){
						throw new BusinessException("工卡状态未生效或者失效，不可关闭技术文件评估单!");
					}
				}
			}
		}
		
	}
	
}
