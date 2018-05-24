package com.eray.thjw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.eray.thjw.dao.WOAirMaterialMapper;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WOAirMaterial;
import com.eray.thjw.po.WorkOrder;
import com.eray.thjw.productionplan.po.ScheduledCheckItem;
import com.eray.thjw.service.CommonRecService;
import com.eray.thjw.service.WOAirMaterialService;
import com.eray.thjw.util.ThreadVarUtil;

import enu.TableEnum;
import enu.UpdateTypeEnum;

@Service
public class WOAirMaterialServiceImpl implements WOAirMaterialService {
    @Resource
    private WOAirMaterialMapper wOAirMaterialMapper;
   
    @Resource
	private CommonRecService commonRecService;
    
	@Override
	public int insertSelective(WorkOrder wrokorder)throws RuntimeException {
		int count =0;
		List<WOAirMaterial> list=wrokorder.getBaseWorkOrder().getWoAirMaterial();
		if (list!=null && !list.isEmpty()) {
	    	User user = ThreadVarUtil.getUser();
			for (WOAirMaterial woAirMaterial : list) {
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				woAirMaterial.setId(id);
				woAirMaterial.setMainid(wrokorder.getGdjcid());
				woAirMaterial.setWhdwid(user.getBmdm());
				woAirMaterial.setWhrid(user.getId());
				woAirMaterial.setDprtcode(user.getJgdm());
				count=wOAirMaterialMapper.insertSelective(woAirMaterial);
				commonRecService.write(id, TableEnum.B_G_00601, user.getId(), wrokorder.getCzls(), wrokorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,wrokorder.getId());
			}
		}		
    	return count;
	}
	
	@Override
	public int doByAirMaterial(WorkOrder workorder) {
		List<WOAirMaterial> record=workorder.getBaseWorkOrder().getWoAirMaterial();
		int count=0;
		List<String> newHcidlist=new ArrayList<String>();              //新的集合
		List<String> oldHcidslist=new ArrayList<String>();              //旧的集合
		
		WOAirMaterial wm=new WOAirMaterial();
		wm.setMainid(workorder.getGdjcid());
		List<WOAirMaterial>  oldHc=wOAirMaterialMapper.selectWOAirMaterialList(wm);
		for (WOAirMaterial woAirMaterial : oldHc) {
			oldHcidslist.add(woAirMaterial.getId());
		}
		User user = ThreadVarUtil.getUser();
		for (WOAirMaterial newHc : record) {
			newHcidlist.add(newHc.getId());
			if(!oldHcidslist.contains(newHc.getId())){
				UUID uuid = UUID.randomUUID();
				String id = uuid.toString();
				newHc.setId(id);
				newHc.setMainid(workorder.getGdjcid());
				newHc.setWhdwid(user.getBmdm());
				newHc.setWhrid(user.getId());
				newHc.setDprtcode(user.getJgdm());
				count=wOAirMaterialMapper.insertSelective(newHc);
				commonRecService.write(id, TableEnum.B_G_00601, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.SAVE,workorder.getId());
			}else{
				count=wOAirMaterialMapper.updateByAirMaterial(newHc);
				commonRecService.write(newHc.getId(), TableEnum.B_G_00601, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.UPDATE,workorder.getId());
			}
		}
		for (WOAirMaterial woAirMaterial : oldHc) {
			if(!newHcidlist.contains(woAirMaterial.getId())){
				commonRecService.write(woAirMaterial.getId(), TableEnum.B_G_00601, user.getId(), workorder.getCzls(), workorder.getLogOperationEnum(), UpdateTypeEnum.DELETE,workorder.getId());
				count=wOAirMaterialMapper.deleteById(woAirMaterial.getId());
			}
		}
		return count;
	}

	@Override
	public void save(ScheduledCheckItem scheduledCheckItem) throws Exception {
		//for工单基础表
	}

	@Override
	public List<WOAirMaterial> selectWOAirMaterialList(WOAirMaterial record) {
		return wOAirMaterialMapper.selectWOAirMaterialList(record);
	}

	 /**
	 * @author sunji
	 * @description 根据基础id查询已选航材
	 * @develop date 2016.08.15
	 */
	public List<WOAirMaterial> selectedHcList(WOAirMaterial wOAirMaterial)
			throws RuntimeException {
		return wOAirMaterialMapper.selectWOAirMaterialList(wOAirMaterial);
	}

	@Override
	public int deleteById(String id) {
		return wOAirMaterialMapper.deleteById(id);
	}

	@Override
	public int updateByAirMaterial(WOAirMaterial wOAirMaterial) {
		return wOAirMaterialMapper.updateByAirMaterial(wOAirMaterial);
	}

	
	

}
